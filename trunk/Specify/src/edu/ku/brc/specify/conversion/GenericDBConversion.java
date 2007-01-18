/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.specify.conversion;

import static edu.ku.brc.specify.conversion.BasicSQLUtils.buildSelectFieldList;
import static edu.ku.brc.specify.conversion.BasicSQLUtils.copyTable;
import static edu.ku.brc.specify.conversion.BasicSQLUtils.createFieldNameMap;
import static edu.ku.brc.specify.conversion.BasicSQLUtils.deleteAllRecordsFromTable;
import static edu.ku.brc.specify.conversion.BasicSQLUtils.getFieldMetaDataFromSchema;
import static edu.ku.brc.specify.conversion.BasicSQLUtils.getFieldNamesFromSchema;
import static edu.ku.brc.specify.conversion.BasicSQLUtils.getStrValue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import edu.ku.brc.dbsupport.AttributeIFace;
import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.specify.config.Discipline;
import edu.ku.brc.specify.config.SpecifyAppContextMgr;
import edu.ku.brc.specify.datamodel.AttributeDef;
import edu.ku.brc.specify.datamodel.CatalogSeries;
import edu.ku.brc.specify.datamodel.CollectionObjDef;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.CollectionObjectAttr;
import edu.ku.brc.specify.datamodel.DataType;
import edu.ku.brc.specify.datamodel.Geography;
import edu.ku.brc.specify.datamodel.GeographyTreeDef;
import edu.ku.brc.specify.datamodel.GeographyTreeDefItem;
import edu.ku.brc.specify.datamodel.GeologicTimePeriod;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDefItem;
import edu.ku.brc.specify.datamodel.Location;
import edu.ku.brc.specify.datamodel.LocationTreeDef;
import edu.ku.brc.specify.datamodel.LocationTreeDefItem;
import edu.ku.brc.specify.datamodel.PickList;
import edu.ku.brc.specify.datamodel.PickListItem;
import edu.ku.brc.specify.datamodel.PrepType;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;
import edu.ku.brc.specify.datamodel.TaxonTreeDefItem;
import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.Treeable;
import edu.ku.brc.specify.treeutils.TreeFactory;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.db.PickListItemIFace;
import edu.ku.brc.util.Pair;

/**
 * This class is used for copying over the and creating all the tables that are not specify to any one collection.
 * This assumes that the "static" data members of DBConnection have been set up with the new Database's
 * driver, name, user and password. This is created with the old Database's driver, name, user and password.
 * 
 * @author rods
 *
 */
public class GenericDBConversion
{
    public enum TableType 
    {
        CollectingEvent(0),
        CollectionObject(1),
        ExternalResource(2),
        Preparation(3);
        
        TableType(final int ord)
        { 
            this.ord = (short)ord;
        }
        private short ord;
        public short getType() { return ord; }
    }
    //public enum VISIBILITY_LEVEL {All, Institution}
    public static int defaultVisibilityLevel = 0;//User/Security changes
    
    protected static final int D_STATUS_CURRENT = 1;
    protected static final int D_STATUS_UNKNOWN = 2;
    
    protected static final Logger log = Logger.getLogger(GenericDBConversion.class);

    protected static StringBuilder strBuf   = new StringBuilder("");
    protected static Calendar     calendar  = Calendar.getInstance();

    protected static SimpleDateFormat dateFormatter  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    protected String oldDriver   = "";
    protected String oldDBName   = "";
    protected String oldUserName = "";
    protected String oldPassword = "";

    protected IdMapperMgr  idMapperMgr;

    protected DBConnection oldDB;

    protected Connection oldDBConn;
    protected Connection newDBConn;

    protected String[]                  standardDataTypes    = {"Plant", "Animal", "Mineral", "Fungi", "Anthropology"};
    protected Hashtable<String, Integer> dataTypeNameIndexes = new Hashtable<String, Integer>(); // Name to Index in Array

    protected Hashtable<String, Long> dataTypeNameToIds = new Hashtable<String, Long>(); // name to Record ID

    protected Hashtable<String, TableStats> tableStatHash = new Hashtable<String, TableStats>();
    
    // Helps during debuggin
    protected static boolean shouldCreateMapTables = true;
    protected static boolean shouldDeleteMapTables = false;

    protected SpecifyAppContextMgr appContextMgr = new SpecifyAppContextMgr();
    
    protected SpecifyDBConvFrame   frame    = null;
    protected boolean              hasFrame = false;

    /**
     * "Old" means the database you want to copy "from"
     * @param oldDriver old driver
     * @param oldDBName old database name
     * @param connectionStr old server name
     * @param oldUserName old user name
     * @param oldPassword old password
     */
    public GenericDBConversion(final String oldDriver,
                               final String oldDBName,
                               final String connectionStr,
                               final String oldUserName,
                               final String oldPassword)
    {
        this.oldDriver    = oldDriver;
        this.oldDBName    = oldDBName;
        this.oldUserName  = oldUserName;
        this.oldPassword  = oldPassword;
        this.idMapperMgr  = IdMapperMgr.getInstance();
        
        this.oldDB        = DBConnection.createInstance(oldDriver, null, oldDBName, connectionStr, oldUserName, oldPassword);

        oldDBConn = oldDB.createConnection();
        newDBConn = DBConnection.getInstance().createConnection();
    }
    
    public void shutdown()
    {
        try
        {
            oldDBConn.close();
            newDBConn.close();
            
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Sets a UI feedback frame.
     * @param frame the frame
     */
    public void setFrame(final SpecifyDBConvFrame frame)
    {
        this.frame = frame;
        hasFrame = frame != null;
        
        BasicSQLUtils.setFrame(frame);
        
        if (idMapperMgr != null)
        {
            idMapperMgr.setFrame(frame);
        }
    }
    
    public void setOverall(final int min, final int max)
    {
        if (hasFrame)
        {
            frame.setOverall(min, max);
        }
    }
    
    public void setOverall(final int value)
    {
        if (hasFrame)
        {
            frame.setOverall(value);
        }
    }
    
    public void setProcess(final int min, final int max)
    {
        if (hasFrame)
        {
            frame.setProcess(min, max);
        }
    }
    
    public void setProcess(final int value)
    {
        if (hasFrame)
        {
            frame.setProcess(value);
        }
    }

    public void setDesc(final String text)
    {
        if (hasFrame)
        {
            frame.setDesc(text);
        }
    }

    /**
     * Return old DB
     * @return old DB
     */
    public DBConnection getOldDB()
    {
        return oldDB;
    }

    /**
     * Return the SQL Connection to the Old Database
     * @return the SQL Connection to the Old Database
     */
    public Connection getOldDBConnection()
    {
        return oldDBConn;
    }

    /**
     * Return the SQL Connection to the New Database
     * @return the SQL Connection to the New Database
     */
    public Connection getNewDBConnection()
    {
        return newDBConn;
    }
   
    public void showStats()
    {
        for (Enumeration<TableStats> ts=tableStatHash.elements();ts.hasMoreElements();)
        {
            ts.nextElement().compareStats();
        }
    }

    /**
     *
     */
    public void mapIds() throws SQLException
    {
        /*String[] tableNames =
        {
                "CatalogSeries",
                "CatalogSeriesDefinition",
                "TaxonName",
                "TaxonomicUnitType"                
        };*/

        String[] tableNames =
        {
        "Accession",
        "AccessionAgents",
        "AccessionAuthorizations",
        //"Address",
        //"Agent",
        //"AgentAddress",
        "Authors",
        "BiologicalObjectAttributes",
        "BiologicalObjectRelation",
        "BiologicalObjectRelationType",
        "Borrow",
        "BorrowAgents",
        "BorrowMaterial",
        "BorrowReturnMaterial",
        //"BorrowShipments",
        "CatalogSeries",
        "CatalogSeriesDefinition",
        "CollectingEvent",
        //"Collection",
        "CollectionObject",
        "CollectionObjectCatalog",
        "CollectionObjectCitation",
        "CollectionObjectType",
        "CollectionTaxonomyTypes",
        "Collectors",
        "Deaccession",
        "DeaccessionAgents",
        "DeaccessionCollectionObject",
        "Determination",
        "DeterminationCitation",
        "ExchangeIn",
        "ExchangeOut",
        //"Geography",
        "GeologicTimeBoundary",
        //"GeologicTimePeriod",
        "GroupPersons",
        "Habitat",
        "ImageAgents",
        "ImageCollectionObjects",
        "ImageLocalities",
        "Journal",
        "Loan",
        "LoanAgents",
        "LoanPhysicalObject",
        "LoanReturnPhysicalObject",
        "Locality",
        "LocalityCitation",
        "Observation",
        "OtherIdentifier",
        "Permit",
        //"Preparation",
        "Project",
        "ProjectCollectionObjects",
        "ReferenceWork",
        "Shipment",
        "Sound",
        "SoundEventStorage",
        "Stratigraphy",
        "TaxonCitation",
        "TaxonName",
        "TaxonomicUnitType",
        //"TaxonomyType"
        };

        //shouldCreateMapTables = false;
        
        IdTableMapper idMapper = null;
        for (String tableName : tableNames)
        {
            idMapper = idMapperMgr.addTableMapper(tableName, tableName+"ID");
            if (shouldCreateMapTables)
                idMapper.mapAllIds();
        }

        idMapper = idMapperMgr.addTableMapper("TaxonomyType", "TaxonomyTypeID", "select TaxonomyTypeID, TaxonomyTypeName from taxonomytype where TaxonomyTypeID in (SELECT distinct TaxonomyTypeID from taxonname where RankId <> 0)");
        if (shouldCreateMapTables)
        {
            idMapper.mapAllIdsWithSQL();
        }


        // Map all the Logical IDs
        idMapper  = idMapperMgr.addTableMapper("collectionobject", "CollectionObjectID");
        if (shouldCreateMapTables)
        {
            idMapper.mapAllIds("select CollectionObjectID from collectionobject Where collectionobject.CollectionObjectTypeID = 10 order by CollectionObjectID");
            //idMapper.mapAllIds("select CollectionObjectID from collectionobject order by CollectionObjectID");
        }

        // Map all the Physical IDs
        idMapper = idMapperMgr.addTableMapper("preparation", "PreparationID");
        if (shouldCreateMapTables)
        {
            idMapper.mapAllIds("select CollectionObjectID from collectionobject Where collectionobject.CollectionObjectTypeID = 22 order by CollectionObjectID");
        }

        // Map all the Physical IDs
//        idMapper = idMapperMgr.addTableMapper("geography", "GeographyID");
//        if (shouldCreateMapTables)
//        {
//            idMapper.mapAllIds("SELECT DISTINCT GeographyID,ContinentOrOcean,Country,State,County" +
//            		"FROM demo_fish2.geography" +
//            		"WHERE( (ContinentOrOcean IS NOT NULL) OR (Country IS NOT NULL) OR (State IS NOT NULL) OR (County IS NOT NULL) )" +
//            		"AND ( (IslandGroup IS NULL) AND (Island IS NULL) AND (WaterBody IS NULL) AND (Drainage IS NULL) ) " +
//            		"GROUP BY ContinentOrOcean,Country,State,County" );
//        }
        
        //shouldCreateMapTables = true;
        
        // Map all the CollectionObject to its TaxonomyType
        IdHashMapper idHashMapper = idMapperMgr.addHashMapper("ColObjCatToTaxonType", "Select collectionobjectcatalog.CollectionObjectCatalogID, taxonname.TaxonomyTypeID From collectionobjectcatalog Inner Join determination ON determination.BiologicalObjectID = collectionobjectcatalog.CollectionObjectCatalogID Inner Join taxonname ON taxonname.TaxonNameID = determination.TaxonNameID Where determination.IsCurrent = '-1'  group by collectionobjectcatalog.CollectionObjectCatalogID");
        if (shouldCreateMapTables)
        {
            idHashMapper.mapAllIds();
            log.info("colObjTaxonMapper: "+idHashMapper.size());

        }


        //When you run in to this table1.field, go to that table2 and look up the id
        String[] mappings = {
            "BorrowReturnMaterial", "BorrowMaterialID", "BorrowMaterial", "BorrowMaterialID",
            "BorrowReturnMaterial", "ReturnedByID", "Agent", "AgentID",

            //"Preparation", "PhysicalObjectTypeID", "PhysicalObjectType", "PhysicalObjectTypeID",
            "Preparation", "PreparedByID", "Agent", "AgentID",
            "Preparation", "ParasiteTaxonNameID", "TaxonName", "TaxonNameID",
            //"Preparation", "PreparationTypeID", "PreparationType", "PreparationTypeID",
            //"Preparation", "ContainerTypeID", "ContainerType", "ContainerTypeID",

            "LoanPhysicalObject", "PhysicalObjectID", "CollectionObjectCatalog", "CollectionObjectCatalogID",
            "LoanPhysicalObject", "LoanID", "Loan", "LoanID",

            // ??? "ExchangeIn", "CollectionID", "Collection", "CollectionID",
            "ExchangeIn", "ReceivedFromOrganizationID", "Agent", "AgentID",
            "ExchangeIn", "CatalogedByID", "Agent", "AgentID",

            // ??? "Geography", "CurrentID", "Current", "CurrentID",

            "Collection", "OrganizationID", "Agent", "AgentID",

            "GroupPersons", "GroupID", "Agent", "AgentID",
            "GroupPersons", "MemberID", "Agent", "AgentID",

            // ??? "ExchangeOut", "CollectionID", "Collection", "CollectionID",
            "ExchangeOut", "SentToOrganizationID", "Agent", "AgentID",
            "ExchangeOut", "CatalogedByID", "Agent", "AgentID",
            "ExchangeOut", "ShipmentID", "Shipment", "ShipmentID",

            //"ImageLocalities", "ImageID", "Image", "ImageID",
            //"ImageLocalities", "LocalityID", "Locality", "LocalityID",

            //"ImageCollectionObjects", "ImageID", "Image", "ImageID",
            //"ImageCollectionObjects", "CollectionlObjectID", "CollectionObject", "CollectionObjectID",

            "ReferenceWork", "JournalID", "Journal", "JournalID",
            "ReferenceWork", "ContainingReferenceWorkID", "ReferenceWork", "ReferenceWorkID",

            //"ImageAgents", "ImageID", "Image", "ImageID",
           // "ImageAgents", "AgentID", "Agent", "AgentID",

            "BiologicalObjectRelation", "BiologicalObjectID",             "CollectionObject", "CollectionObjectID",
            "BiologicalObjectRelation", "RelatedBiologicalObjectID",      "CollectionObject", "CollectionObjectID",
            "BiologicalObjectRelation", "BiologicalObjectRelationTypeID", "BiologicalObjectRelationType", "BiologicalObjectRelationTypeID",

            //"SoundEventStorage", "SoundEventID", "SoundEvent", "SoundEventID",
            //"SoundEventStorage", "SoundRecordingID", "SoundRecording", "SoundRecordingID",

            "Shipment", "ShipperID", "AgentAddress", "AgentAddressID",
            "Shipment", "ShippedToID", "AgentAddress", "AgentAddressID",
            "Shipment", "ShippedByID", "Agent", "AgentID",
            //"Shipment", "ShipmentMethodID", "ShipmentMethod", "ShipmentMethodID",

            // ??? "Habitat", "BiologicalObjectTypeCollectedID", "BiologicalObjectTypeCollected", "BiologicalObjectTypeCollectedID",
            "Habitat", "HostTaxonID", "TaxonName", "TaxonNameID",
            //"Habitat", "HabitatTypeID", "HabitatType", "HabitatTypeID",

            "Authors", "AgentID", "Agent", "AgentID",
            "Authors", "ReferenceWorkID", "ReferenceWork", "ReferenceWorkID",

            "BorrowMaterial",  "BorrowID", "Borrow", "BorrowID",

            //"BorrowShipments", "BorrowID", "Borrow", "BorrowID",
            //"BorrowShipments", "ShipmentID", "Shipment", "ShipmentID",

            "BorrowAgents",    "BorrowID", "Borrow", "BorrowID",
            "BorrowAgents",    "AgentAddressID", "AgentAddress", "AgentAddressID",
            //"BorrowAgents",    "RoleID", "Role", "RoleID",

            "DeaccessionCollectionObject", "DeaccessionID",            "Deaccession",                 "DeaccessionID",
            "DeaccessionCollectionObject", "DeaccessionPreparationID", "DeaccessionCollectionObject", "DeaccessionCollectionObjectID",
            "DeaccessionCollectionObject", "CollectionObjectID",       "CollectionObject",            "CollectionObjectID", // not sure this is needed
            "DeaccessionCollectionObject", "PreparationID",            "Preparation",                 "PreparationID",

            "CollectionObjectCitation", "ReferenceWorkID", "ReferenceWork", "ReferenceWorkID",
            "CollectionObjectCitation", "BiologicalObjectID", "CollectionObject", "CollectionObjectID",

            "Stratigraphy", "GeologicTimePeriodID", "GeologicTimePeriod", "GeologicTimePeriodID",

            //"Deaccession", "CollectionID", "Collection", "CollectionID",
            //"Deaccession", "TypeID", "Type", "TypeID",

            //"CollectingEvent", "BiologicalObjectTypeCollectedID", "BiologicalObjectTypeCollected", "BiologicalObjectTypeCollectedID",
            "CollectingEvent", "LocalityID", "Locality", "LocalityID",
            "CollectingEvent", "StratigraphyID", "Stratigraphy", "StratigraphyID",
            //"CollectingEvent", "MethodID", "Method", "MethodID",

            "Collectors", "CollectingEventID", "CollectingEvent", "CollectingEventID",
            "Collectors", "AgentID", "Agent", "AgentID",

            //"Permit", "IssuerID", "AgentAddress", "AgentAddressID",
            //"Permit", "IssueeID", "AgentAddress", "AgentAddressID",
            "Permit", "IssuedByID", "AgentAddress", "AgentAddressID",
            "Permit", "IssuedToID", "AgentAddress", "AgentAddressID",
            //"Permit", "TypeID", "Type", "TypeID",

            "Sound", "RecordedByID", "Agent", "AgentID",

            "TaxonCitation", "ReferenceWorkID", "ReferenceWork", "ReferenceWorkID",
            "TaxonCitation", "TaxonNameID", "TaxonName", "TaxonNameID",

            "Determination", "DeterminerID",           "Agent", "AgentID",
            //"Determination", "TaxonNameID",            "Taxon", "TaxonID",???
            "Determination", "TaxonNameID",            "TaxonName", "TaxonNameID",
            "Determination", "BiologicalObjectID",     "CollectionObject", "CollectionObjectID",
            
            //"Determination", "PreparationID",          "Preparation", "PreparationID",
            //"Determination", "BiologicalObjectTypeID", "BiologicalObjectType", "BiologicalObjectTypeID",
            //"Determination", "TypeStatusNameID",       "TypeStatusName", "TypeStatusNameID",
            //"Determination", "ConfidenceID",           "Confidence", "ConfidenceID",
            //"Determination", "MethodID",               "Method", "MethodID",

            // ??? "GeologicTimePeriod", "UpperBoundaryID", "UpperBoundary", "UpperBoundaryID",
            // ??? "GeologicTimePeriod", "LowerBoundaryID", "LowerBoundary", "LowerBoundaryID",

            //"CatalogSeriesDefinition", "CatalogSeriesID", "CatalogSeries", "CatalogSeriesID",
            //"CatalogSeriesDefinition", "ObjectTypeID", "ObjectType", "ObjectTypeID",

            // (not needed) "CollectionObject", "DerivedFromID", "DerivedFrom", "DerivedFromID",
            //"CollectionObject", "ContainerID", "Container", "ContainerID",

            //************************************************************************************
            // NOTE: Since we are mapping CollectionObjectType to CatalogSeriesDefinition
            // then we might as well map CollectionObjectTypeID to CatalogSeriesDefinitionID
            //
            // The Combination of the CatalogSeriesDefinition and CollectionObjectType become the
            // new CollectionObjDef
            // As you might expect the CatalogSeriesDefinitionID is mapped to the new CollectionObjDef
            //************************************************************************************
            "CollectionObjectType", "CollectionObjectTypeID", "CatalogSeriesDefinition", "CatalogSeriesDefinitionID",
            "CollectionObject", "CollectionObjectTypeID", "CatalogSeriesDefinition", "CatalogSeriesDefinitionID",

            "CollectionObject", "CollectingEventID", "CollectingEvent", "CollectingEventID",

            //"CollectionObject", "ContainerTypeID", "ContainerType", "ContainerTypeID",
            //"CollectionObject", "PreparationMethodID", "PreparationMethod", "PreparationMethodID",

            //"CollectionObjectCatalog", "CollectionObjectTypeID", "CollectionObjectType", "CollectionObjectTypeID",
            "CollectionObject", "CatalogSeriesID",        "CatalogSeries", "CatalogSeriesID",
            "CollectionObject", "AccessionID",            "Accession", "AccessionID",
            "CollectionObject", "CatalogerID",            "Agent", "AgentID",


            //"Observation", "BiologicalObjectID", "BiologicalObject", "BiologicalObjectID",
            //"Observation", "ObservationMethodID", "ObservationMethod", "ObservationMethodID",

            "Loan", "ShipmentID", "Shipment", "ShipmentID",
            //"Loan", "CollectionID", "Collection", "CollectionID",

            "AccessionAuthorizations", "AccessionID", "Accession", "AccessionID",
            "AccessionAuthorizations", "PermitID", "Permit", "PermitID",

            "AccessionAgent", "AccessionID", "Accession", "AccessionID",
            "AccessionAgent", "AgentAddressID", "AgentAddress", "AgentAddressID",
            //"AccessionAgent", "RoleID", "Role", "RoleID",

            "DeterminationCitation", "ReferenceWorkID", "ReferenceWork", "ReferenceWorkID",
            "DeterminationCitation", "DeterminationID", "Determination", "DeterminationID",

            //"CatalogSeries", "CollectionID", "Collection", "CollectionID",

            "OtherIdentifier", "CollectionObjectID", "CollectionObject", "CollectionObjectID",

            "Agent", "ParentOrganizationID", "Agent", "AgentID",

            //"CollectionTaxonomyTypes", "CollectionID", "Collection", "CollectionID",
            //"CollectionTaxonomyTypes", "BiologicalObjectTypeID", "BiologicalObjectType", "BiologicalObjectTypeID",
            //"CollectionTaxonomyTypes", "TaxonomyTypeID", "TaxonomyType", "TaxonomyTypeID",

            "AgentAddress", "AddressID", "Address", "AddressID",
            "AgentAddress", "AgentID", "Agent", "AgentID",
            "AgentAddress", "OrganizationID", "Agent", "AgentID",

            "LocalityCitation", "ReferenceWorkID", "ReferenceWork", "ReferenceWorkID",
            "LocalityCitation", "LocalityID", "Locality", "LocalityID",

            //"BiologicalObjectAttributes", "BiologicalObjectTypeID", "BiologicalObjectType", "BiologicalObjectTypeID",
            //"BiologicalObjectAttributes", "SexID", "Sex", "SexID",
            //"BiologicalObjectAttributes", "StageID", "Stage", "StageID",

            "LoanReturnPhysicalObject", "LoanPhysicalObjectID",        "LoanPhysicalObject", "LoanPhysicalObjectID",
            "LoanReturnPhysicalObject", "ReceivedByID",                "Agent",  "AgentID",
            "LoanReturnPhysicalObject", "DeaccessionPhysicalObjectID", "CollectionObject", "CollectionObjectID",

            //"Borrow", "CollectionID", "Collection", "CollectionID",

            //"Locality", "GeographyID", "Geography", "GeographyID",
            //"Locality", "ElevationMethodID", "ElevationMethod", "ElevationMethodID",
            //"Locality", "LatLongTypeID", "LatLongType", "LatLongTypeID",
            //"Locality", "LatLongMethodID", "LatLongMethod", "LatLongMethodID",

            "DeaccessionAgents", "DeaccessionID", "Deaccession", "DeaccessionID",
            "DeaccessionAgents", "AgentAddressID", "AgentAddress", "AgentAddressID",
            //"DeaccessionAgents", "RoleID", "Role", "RoleID",

            "ProjectCollectionObjects", "ProjectID", "Project", "ProjectID",
            "ProjectCollectionObjects", "CollectionObjectID", "CollectionObject", "CollectionObjectID",

            "Project", "ProjectAgentID", "Agent", "AgentID",

            "LoanAgents", "LoanID", "Loan", "LoanID",
            "LoanAgents", "AgentAddressID", "AgentAddress", "AgentAddressID",
            //"LoanAgents", "RoleID", "Role", "RoleID",

            //"Accession", "CollectionID", "Collection", "CollectionID",
            //"Accession", "StatusID", "Status", "StatusID",
            //"Accession", "TypeID", "Type", "TypeID",

        	// taxonname ID mappings
            "TaxonName", "ParentTaxonNameID", "TaxonName", "TaxonNameID",
            "TaxonName", "TaxonomicUnitTypeID", "TaxonomicUnitType", "TaxonomicUnitTypeID",
            "TaxonName", "TaxonomyTypeID", "TaxonomyType", "TaxonomyTypeID",
            "TaxonName", "AcceptedID", "TaxonName", "TaxonNameID",

            // taxonomytype ID mappings
            // NONE

            // taxonomicunittype ID mappings
            "TaxonomicUnitType", "TaxonomyTypeID", "TaxonomyType", "TaxonomyTypeID"
        };

        for (int i=0;i<mappings.length;i += 4)
        {
            idMapperMgr.mapForeignKey(mappings[i], mappings[i+1], mappings[i+2], mappings[i+3]);
        }
    }

    /**
     * Removes all the records from every table in the new database and then copies over
     * all the tables that have few if any changes to their schema
     */
    public void copyTables()
    {

        //cleanAllTables(); // from DBCOnnection which is the new DB
        
        //String[] tablesToMoveOver2 = {
        //        "LoanPhysicalObject"
        //};
        

        String[] tablesToMoveOver = {
                                    "AccessionAgent",
                                    "Accession",
                                    "AccessionAuthorizations",
                                    //"Address",
                                    //"Agent",
                                    //"AgentAddress",
                                    "Authors",
                                    "Borrow",
                                    "BorrowAgents",
                                    "BorrowMaterial",
                                    "BorrowReturnMaterial",
                                    //"BorrowShipments",
                                    "CatalogSeries",
                                    "CollectingEvent",
                                    "CollectionObjectCitation",
                                    "Collectors",
                                    "Deaccession",
                                    "DeaccessionAgents",
                                    //"DeaccessionPreparation",
                                    //"Determination",
                                    "DeterminationCitation",
                                    "ExchangeIn",
                                    "ExchangeOut",
                                    "GroupPersons",
                                    "Journal",
                                    "Loan",
                                    "LoanAgents",
                                    //"LoanPhysicalObject",
                                    "LoanReturnPhysicalObject",
                                    //"locality",
                                    "LocalityCitation",
                                    "OtherIdentifier",
                                    "Permit",
                                    "Project",
                                    "ProjectCollectionObjects",
                                    "ReferenceWork",
                                    "Shipment",
                                    "Stratigraphy",
                                    "TaxonCitation",
       };

       Map<String, Map<String, String>> tableMaps = new Hashtable<String, Map<String, String>>();
       tableMaps.put("authors", createFieldNameMap(new String[] {"OrderNumber", "Order1"}));
       tableMaps.put("borrow", createFieldNameMap(new String[] {"IsClosed", "Closed"}));
       tableMaps.put("borrowreturnmaterial", createFieldNameMap(new String[] {"ReturnedDate", "Date1"}));
       tableMaps.put("collectors", createFieldNameMap(new String[] {"OrderNumber", "Order1"}));
       //tableMaps.put("determination", createFieldNameMap(new String[] {"CollectionObjectID", "BiologicalObjectID", "IsCurrent", "Current1", "DeterminationDate", "Date1", "TaxonID", "TaxonNameID"}));
       //tableMaps.put("loanreturnphysicalobject", createFieldNameMap(new String[] {"DateField", "Date1"}));
       tableMaps.put("loanphysicalobject", createFieldNameMap(new String[] {"PreparationID", "PhysicalObjectID"}));
       tableMaps.put("loanreturnphysicalobject", createFieldNameMap(new String[] {"ReturnedDate", "Date1"}));
       tableMaps.put("referencework", createFieldNameMap(new String[] {"WorkDate", "Date1", "IsPublished", "Published"}));
       tableMaps.put("stratigraphy", createFieldNameMap(new String[] {"LithoGroup", "Group1"}));
       tableMaps.put("taxoncitation", createFieldNameMap(new String[] {"TaxonID", "TaxonNameID"}));
       tableMaps.put("collectionobjectcitation", createFieldNameMap(new String[] {"CollectionObjectID", "BiologicalObjectID"}));
       tableMaps.put("accessionagent", createFieldNameMap(new String[] {"AgentID", "AgentAddressID", "AccessionAgentID", "AccessionAgentsID"}));
       tableMaps.put("borrowagents", createFieldNameMap(new String[] {"AgentID", "AgentAddressID"}));
       tableMaps.put("deaccessionagents", createFieldNameMap(new String[] {"AgentID", "AgentAddressID"}));
       tableMaps.put("loanagents", createFieldNameMap(new String[] {"AgentID", "AgentAddressID"}));
       tableMaps.put("loan", createFieldNameMap(new String[] {"IsGift", "Category", "IsClosed", "Closed"}));
       
       
       tableMaps.put("deaccession", createFieldNameMap(new String[] {"DeaccessionDate", "Date1"}));
       tableMaps.put("projectcollectionobjects", createFieldNameMap(new String[] {"ProjectCollectionObjectID", "ProjectCollectionObjectsID"}));
       Map<String, Map<String, String>> tableDateMaps = new Hashtable<String, Map<String, String>>();
       tableDateMaps.put("collectingevent", createFieldNameMap(new String[] {"TaxonID", "TaxonNameID"}));
       tableMaps.put("permit", createFieldNameMap(new String[] {"IssuedByID", "IssuerID", "IssuedToID", "IssueeID"}));
       tableMaps.put("collectingevent", createFieldNameMap(new String[] {"StratigraphyID", "CollectingEventID"}));
       //tableMaps.put("locality", createFieldNameMap(new String[] {"NationalParkName", "", "ParentID", "TaxonParentID"}));
       

       BasicSQLUtils.setShowMappingError(true);//TODO meg change back to false
       for (String tableName : tablesToMoveOver)
       {
           String lowerCaseName = tableName.toLowerCase();
           deleteAllRecordsFromTable(lowerCaseName);
           BasicSQLUtils.setShowMappingError(true);

           TableStats tblStats = new TableStats(oldDBConn, lowerCaseName, newDBConn, lowerCaseName);
           tableStatHash.put(lowerCaseName, tblStats);
           log.info("Getting ready to copy table: " + lowerCaseName);
           if (tableName.equals("LoanPhysicalObject"))
           {
               String[] ignoredFields = {"IsResolved"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);
               
           } else if (tableName.equals("Accession") || 
                       tableName.equals("AccessionAuthorizations"))
           {
               String[] ignoredFields = {"RepositoryAgreementID"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);
               
           } 
           else if(tableName.toLowerCase().equals("accessionagent")) 
           {
               String[] ignoredFields = {"RepositoryAgreementID"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);  
           }
           else if(tableName.toLowerCase().equals("accession")) 
           {
               String[] ignoredFields = {"RepositoryAgreementID"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);  
           }
           else if(tableName.toLowerCase().equals("attachment")) 
           {
               String[] ignoredFields = {"Visibility", "VisibilitySetBy"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);  
           }
           else if(tableName.toLowerCase().equals("collectingevent")) 
           {
               String[] ignoredFields = {"Visibility", "VisibilitySetBy", "CollectingTripID", "EndDateVerbatim", "EndDatePrecision", "StartDateVerbatim", "StartDatePrecision"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);  
           }
           else if(tableName.toLowerCase().equals("determination"))
           {
               String[] ignoredFields = {"DeterminationStatusID"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);  
           }
           else if(tableName.toLowerCase().equals("otheridentifier"))
           {
               String[] ignoredFields = {"Institution"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);  
           }      
            else
           {
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);
           }
           
           if (tableName.equals("BorrowReturnMaterial") || 
                   tableName.equals("LoanReturnPhysicalObject") ||
                   tableName.equals("ReferenceWork") ||
                   tableName.equals("Shipment") ||
                   tableName.equals("Stratigraphy"))
           {
               BasicSQLUtils.setShowMappingError(false);

           }
           
           if(tableName.toLowerCase().equals("accessionagent"))
           {
               copyTable(oldDBConn, newDBConn, "select * from accessionagents", "accessionagents", "accessionagent", tableMaps.get(lowerCaseName), null);
               
           }           
           else if (!copyTable(oldDBConn, newDBConn, lowerCaseName, tableMaps.get(lowerCaseName), null))
           {
               log.error("Table ["+tableName+"] didn't copy correctly.");
               break;
               
           }

           // else
           
           tblStats.collectStats();
       }
       BasicSQLUtils.setShowMappingError(true);
    }

    /**
     * Checks to see if any of the names in the array are in passed in name
     * @param referenceNames array of reference names
     * @param name the name to be figured out
     * @return true if there is a match
     */
    protected boolean checkName(String[] referenceNames, final String name)
    {
        for (String rn : referenceNames)
        {
            if (name.toLowerCase().indexOf(rn.toLowerCase()) > -1)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Convert a CollectionObjectTypeName to a DataType
     * @param collectionObjTypeName the name
     * @return the Standard DataType
     */
    public String getStandardDataTypeName(final String collectionObjTypeName)
    {
        if (checkName(new String[] {"Plant", "Herb"}, collectionObjTypeName))
        {
            return "Plant";
        }

        if (checkName(new String[] {"Fish", "Bird", "Frog", "Insect", "Fossil", "Icth", "Orn", "Herp", "Entom", "Paleo", "Mammal", "Invertebrate"}, collectionObjTypeName))
        {
            return "Animal";
        }

        if (checkName(new String[] {"Mineral", "Rock"}, collectionObjTypeName))
        {
            return "Mineral";
        }

        if (checkName(new String[] {"Anthro"}, collectionObjTypeName))
        {
            return "Anthropology";
        }

        if (checkName(new String[] {"Fungi"}, collectionObjTypeName))
        {
            return "Fungi";
        }
        log.error("****** Unable to Map ["+collectionObjTypeName+"] to a standard DataType.");

        return null;
    }

    /**
     * Convert a CollectionObjectTypeName to a DataType
     * @param name the name
     * @return the Standard DataType
     */
    public String getStandardDisciplineName(final String name)
    {
        Discipline discipline = appContextMgr.getDiscipline(name.toLowerCase());
        if (discipline != null)
        {
            return discipline.getName();
        }
        
        if (checkName(new String[] {"Plant", "Herb"}, name))
        {
            return "plant";
        }

        if (checkName(new String[] {"FishHerps"}, name))
        {
            return "animal";
        }

        if (checkName(new String[] {"Mineral", "Rock"}, name))
        {
            return "mineral";
        }

        if (checkName(new String[] {"Anthro"}, name))
        {
            return "anthropology";
        }

        if (checkName(new String[] {"Fungi"}, name))
        {
            return "fungi";
        }
        log.error("****** Unable to Map Name["+name+"] to a Discipline type.");

        return null;
    }

    /**
     * Create the two default records that should be in the new 'determinationstatus' table.
     * The first record is an 'current' record.  The second record is a 'unknown' record
     * which corresponds to the old isCurrent=true records.
     */
    public void createDefaultDeterminationStatusRecords()
    {
    	StringBuilder insert = new StringBuilder();
    	try
    	{
    		BasicSQLUtils.deleteAllRecordsFromTable("determinationstatus");

    		// setup the insert statement
    		insert.append("INSERT INTO determinationstatus ");
    		insert.append("(DeterminationStatusID,Name,Remarks,TimestampCreated,TimestampModified) ");
    		insert.append("values ");
    		// followed by the 'current status' record
    		insert.append("(");
    		insert.append(D_STATUS_CURRENT);
    		insert.append(",'Current','mirror of the old schema isCurrent field',CURRENT_DATE,CURRENT_DATE)");
            
            // the 'unknown status' record
            insert.append(", (");
            insert.append(D_STATUS_UNKNOWN);
            insert.append(",'Unknown','',CURRENT_DATE,CURRENT_DATE)");

    		Statement st = newDBConn.createStatement();
    		st.executeUpdate(insert.toString());
            st.close();
            
    	}
    	catch(SQLException e)
        {
            log.error(insert.toString());
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a default user.
     * @param userName the user name
     * @return the record id
     */
    public long createDefaultUser(final String userName, final String userType)
    {
        //TODO: rewrite the following table descriptions
        /*
         describe usergroup;
            +-------------+-------------+------+-----+---------+----------------+
            | Field       | Type        | Null | Key | Default | Extra          |
            +-------------+-------------+------+-----+---------+----------------+
            | UserGroupID | int(11)     | NO   | PRI |         | auto_increment |
            | Name        | varchar(64) | YES  |     |         |                |
            | Remarks     | text        | YES  |     |         |                |
            +-------------+-------------+------+-----+---------+----------------+

         describe specifyuser;
            +---------------+-------------+------+-----+---------+----------------+
            | Field         | Type        | Null | Key | Default | Extra          |
            +---------------+-------------+------+-----+---------+----------------+
            | SpecifyUserID | int(11)     | NO   | PRI |         | auto_increment |
            | Name          | varchar(64) | NO   |     |         |                |
            | EMail         | varchar(64) | YES  |     |         |                |
            | UserType      | varchar(32) | NO   |     |         |                |
            | PrivLevel     | smallint(6) | NO   |     |         |                |
            | UserGroupID   | int(11)     | YES  | MUL |         |                |
            +---------------+-------------+------+-----+---------+----------------+

         */

        try
        {
            Statement  updateStatement = newDBConn.createStatement();

            BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "usergroup");
            BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "specifyuser");

            updateStatement.executeUpdate("INSERT INTO usergroup VALUES (null,'admin2', '')");
            updateStatement.clearBatch();
            updateStatement.close();
            updateStatement = null;

            long userGroupId = BasicSQLUtils.getHighestId(newDBConn, "UserGroupID", "usergroup");

            updateStatement = newDBConn.createStatement();
            strBuf = new StringBuilder(128);
            strBuf.append("INSERT INTO specifyuser VALUES (");
            strBuf.append("NULL,");
            strBuf.append("'"+userName+"',");
            strBuf.append("'"+"email@email.edu"+"',");
            strBuf.append("'"+userType+"',");
            strBuf.append("0,");
            strBuf.append("NULL");
            //strBuf.append(userGroupId+")");
            strBuf.append(")");
            updateStatement.executeUpdate(strBuf.toString());
            updateStatement.clearBatch();
            updateStatement.close();
            updateStatement = null;

            long specifyUserId = BasicSQLUtils.getHighestId(newDBConn, "SpecifyUserID", "specifyuser");
            return specifyUserId;

        } catch (SQLException e)
        {
            log.error(strBuf.toString());
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Create a data type.
     * @param taxonomyTypeName the name
     * @return the ID (record id) of the data type
     */
    public long createDataType(final String taxonomyTypeName)
    {
        long   dataTypeId   = -1;
        String dataTypeName = getStandardDataTypeName(taxonomyTypeName);
        if (dataTypeName == null)
        {
            return dataTypeId;
        }

        try
        {
            if (dataTypeNameToIds.get(dataTypeName) == null)
            {
                /*
                describe datatype;
                +------------+-------------+------+-----+---------+----------------+
                | Field      | Type        | Null | Key | Default | Extra          |
                +------------+-------------+------+-----+---------+----------------+
                | DataTypeID | int(11)     | NO   | PRI |         | auto_increment |
                | Name       | varchar(50) | YES  |     |         |                |
                +------------+-------------+------+-----+---------+----------------+
                */

                Statement updateStatement = newDBConn.createStatement();
                updateStatement.executeUpdate("INSERT INTO datatype VALUES (null,'"+dataTypeName+"')");
                updateStatement.clearBatch();
                updateStatement.close();
                updateStatement = null;

                dataTypeId = BasicSQLUtils.getHighestId(newDBConn, "DataTypeID", "datatype");
                log.info("Created new datatype["+dataTypeName+"]");

                dataTypeNameToIds.put(dataTypeName, dataTypeId);


            } else
            {
                dataTypeId = dataTypeNameToIds.get(dataTypeName);
                log.info("Reusing new datatype["+dataTypeName+"]");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }
        return dataTypeId;
    }

    /**
     * Converts Object Defs.
     * @param specifyUserId
     * @return true on success, false on failure
     */
    public boolean convertCollectionObjectDefs(final long specifyUserId)
    {
        // The Old Table catalogseriesdefinition is being converted to collectionobjdef
        IdMapperIFace catalogSeriesMapper = idMapperMgr.get("CatalogSeries", "CatalogSeriesID");
        IdMapperIFace taxonomyTypeMapper  = idMapperMgr.get("TaxonomyType", "TaxonomyTypeID");

        try
        {
            // Create a Hashtable to track which IDs have been handled during the conversion process
            BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "datatype");
            BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "collectionobjdef");
            BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "catseries_colobjdef");

            Hashtable<Long, Long> taxonomyTypeIDToColObjID     = new Hashtable<Long, Long>();
            Hashtable<Long, String>  taxonomyTypeIDToTaxonomyName = new Hashtable<Long, String>();

            // First, create a CollectionObjDef for TaxonomyType record
            Statement stmt = oldDBConn.createStatement();
            log.info(taxonomyTypeMapper.getSql());
            ResultSet rs   = stmt.executeQuery(taxonomyTypeMapper.getSql());
            int recordCnt = 0;
            while (rs.next())
            {
                long    taxonomyTypeID  = rs.getLong(1);
                String taxonomyTypeName = rs.getString(2);
                
                String disciplineName = getStandardDisciplineName(taxonomyTypeName);
                if (disciplineName == null)
                {
                    log.error("**** Had to Skip record because taxonomyTypeName couldn't be found in our Discipline lookup in SpecifyAppContextMgr["+taxonomyTypeName+"]");
                    continue;
                }
                
                Discipline discipline = appContextMgr.getDiscipline(disciplineName);
                if (discipline == null)
                {
                    log.error("**** discipline couldn't be found in our Discipline lookup in SpecifyAppContextMgr["+disciplineName+"]");
                    continue;
                }
                log.info("Creating a new CollectionObjDef for taxonomyTypeName["+taxonomyTypeName+"] discipline["+disciplineName+"]");

                taxonomyTypeName = disciplineName;
                
                // Figure out what type of standard adat type this is from the CollectionObjectTypeName
                long dataTypeId = createDataType(taxonomyTypeName);
                if (dataTypeId == -1)
                {
                    log.error("**** Had to Skip record because of DataType mapping error["+taxonomyTypeName+"]");
                    continue;
                }

                taxonomyTypeIDToTaxonomyName.put(taxonomyTypeID, taxonomyTypeName);

                /*
                CollectionObjDef
                +-----------------------------+-------------+------+-----+---------+----------------+
                | CollectionObjDefID          | int(11)     | NO   | PRI |         | auto_increment |
                | Name                        | varchar(64) | YES  |     |         |                |
                | Discipline                  | varchar(64) | YES  |     |         |                |
                | DataTypeID                  | int(11)     | YES  | MUL |         |                |
                | SpecifyUserID               | int(11)     | YES  | MUL |         |                |
                | GeographyTreeDefID          | int(11)     | YES  | MUL |         |                |
                | GeologicTimePeriodTreeDefID | int(11)     | YES  | MUL |         |                |
                | LocationTreeDefID           | int(11)     | YES  | MUL |         |                |
                +-----------------------------+-------------+------+-----+---------+----------------+
                */

                // use the old CollectionObjectTypeName as the new CollectionObjDef name

                Statement updateStatement = newDBConn.createStatement();
                StringBuilder strBuf2 = new StringBuilder();
                strBuf2.append("INSERT INTO collectionobjdef VALUES (");
                strBuf2.append(taxonomyTypeMapper.get(taxonomyTypeID)+","); // TimestampModified
                strBuf2.append("'"+dateFormatter.format(new Date())+"',");
                strBuf2.append("'"+discipline.getTitle()+"',");
                strBuf2.append("'"+discipline.getName()+"',");
                strBuf2.append("'"+dateFormatter.format(new Date())+"',");  // TimestampCreated
                strBuf2.append(dataTypeId+",");
                strBuf2.append(specifyUserId+",");
                strBuf2.append("1,"); // GeographyTreeDefID
                strBuf2.append("1,"); // GeologicTimePeriodTreeDefID
                strBuf2.append("1)"); // LocationTreeDefID
                //strBuf2.append("NULL)");//  UserPermissionID//User/Security changes
                log.info(strBuf2.toString());
                
                updateStatement.executeUpdate(strBuf2.toString());
                updateStatement.clearBatch();
                updateStatement.close();
                updateStatement = null;
                recordCnt++;

                Long colObjDefID = BasicSQLUtils.getHighestId(newDBConn, "CollectionObjDefID", "collectionobjdef");
                taxonomyTypeIDToColObjID.put(taxonomyTypeID, colObjDefID);

                log.info("Created new collectionobjdef["+taxonomyTypeName+"] is dataType ["+dataTypeId+"]");
            }
            rs.close();
            stmt.close();
            log.info("CollectionObjDef Records: "+ recordCnt);

            if (taxonomyTypeMapper.size() > 0)
            {
                // Now convert over all CatalogSeries
    
                String sql = "Select catalogseries.CatalogSeriesID, taxonomytype.TaxonomyTypeID From catalogseries Inner Join catalogseriesdefinition ON " +
                             "catalogseries.CatalogSeriesID = catalogseriesdefinition.CatalogSeriesID Inner Join collectiontaxonomytypes ON " +
                             "catalogseriesdefinition.ObjectTypeID = collectiontaxonomytypes.BiologicalObjectTypeID Inner Join taxonomytype ON " +
                             "collectiontaxonomytypes.TaxonomyTypeID = taxonomytype.TaxonomyTypeID";
                log.info(sql);
    
                stmt = oldDBConn.createStatement();
                rs   = stmt.executeQuery(sql.toString());
    
                 recordCnt = 0;
                 while (rs.next())
                 {
                     long catalogSeriesID = rs.getLong(1);
                     long taxonomyTypeID  = rs.getLong(2);
    
                     // Now craete the proper record in the  Join Table
    
                     long newCatalogSeriesID = catalogSeriesMapper.get(catalogSeriesID);
                     long newColObjdefID     = taxonomyTypeMapper.get(taxonomyTypeID);
    
                     Statement updateStatement = newDBConn.createStatement();
                     strBuf.setLength(0);
                     strBuf.append("INSERT INTO catseries_colobjdef VALUES (");
                     strBuf.append(newCatalogSeriesID+", ");
                     strBuf.append(newColObjdefID+")");
    
                     log.info("CatalogSeries Join["+newCatalogSeriesID+"]["+newColObjdefID+"]");
                     updateStatement.executeUpdate(strBuf.toString());
                     updateStatement.clearBatch();
                     updateStatement.close();
                     updateStatement = null;
    
                     recordCnt++;
    
                 } // while
    
                 log.info("CatalogSeries Join Records: "+ recordCnt);
                 rs.close();
                 stmt.close();
            } else
            {
                log.warn("taxonomyTypeMapper is empty.");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Looks up all the current Permit Types and uses them, instead of the usystable
     */
    @SuppressWarnings("unchecked")
    public void createPermitTypePickList()
    {
        /*
        try
        {
            Statement stmt   = oldDBConn.createStatement();
            String    sqlStr = "select count(Type) from (select distinct Type from permit where Type is not null) as t";

            log.info(sqlStr);

            boolean   useField = false;
            ResultSet rs       = stmt.executeQuery(sqlStr);
        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
        }*/
        
        Session session = HibernateUtil.getCurrentSession();
        PickList pl     = new PickList();
        pl.initialize();
        Set<PickListItemIFace> items  = pl.getItems();


        try
        {
            pl.setName("Permit");
            pl.setSizeLimit(-1);

            HibernateUtil.beginTransaction();
            session.saveOrUpdate(pl);
            HibernateUtil.commitTransaction();

        } catch (Exception ex)
        {
            log.error("******* " + ex);
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException("Couldn't create PickList for [Permit]");
        }
        
        try
        {
            Statement stmt   = oldDBConn.createStatement();
            String    sqlStr = "select distinct Type from permit where Type is not null";

            log.info(sqlStr);

            ResultSet rs = stmt.executeQuery(sqlStr);

            // check for no records which is OK
            if (!rs.first())
            {
                return;
            }

            int count = 0;
            do
            {
                String typeStr = rs.getString(1);
                if (typeStr != null)
                {
                    log.info("Permit Type["+typeStr+"]");
                    PickListItem pli = new PickListItem();
                    pli.setTitle(typeStr);
                    pli.setValue(typeStr);
                    pli.setTimestampCreated(new Date());
                    items.add(pli);
                    count++;

                }
            } while (rs.next());

            log.info("Processed Permit Types "+count+" records.");

            HibernateUtil.beginTransaction();

            session.saveOrUpdate(pl);

            HibernateUtil.commitTransaction();

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }

    }

    /**
     * Converts an old USYS table to a PickList.
     * @param usysTableName old table name
     * @param pickListName new pciklist name
     * @return true on success, false on failure
     */
    @SuppressWarnings("unchecked")
    public boolean convertUSYSToPicklist(final String usysTableName, final String pickListName)
    {
        List<BasicSQLUtils.FieldMetaData> fieldMetaData = new ArrayList<BasicSQLUtils.FieldMetaData>();
        getFieldMetaDataFromSchema(oldDBConn, usysTableName, fieldMetaData);

        int ifaceInx    = -1;
        int dataInx     = -1;
        int fieldSetInx = -1;
        int i= 0;
        for (BasicSQLUtils.FieldMetaData md : fieldMetaData)
        {
            if (ifaceInx == -1 && md.getName().equals("InterfaceID"))
            {
                ifaceInx = i+1;

            } else if (fieldSetInx == -1 && md.getName().equals("FieldSetSubTypeID"))
            {
                fieldSetInx = i+1;

            } else if (dataInx  == -1 && md.getType().toLowerCase().indexOf("varchar") > -1)
            {
                dataInx = i+1;
            }
            i++;
        }

        if (ifaceInx == -1 || dataInx == -1 || fieldSetInx == -1)
        {
            throw new RuntimeException("Couldn't decypher USYS table ifaceInx["+ifaceInx+"] dataInx["+dataInx+"] fieldSetInx["+fieldSetInx+"]");
        }

        Session session = HibernateUtil.getCurrentSession();
        PickList pl     = new PickList();
        pl.initialize();

        try
        {
            pl.setName(pickListName);
            pl.setReadOnly(false);
            pl.setSizeLimit(-1);


            HibernateUtil.beginTransaction();
            session.saveOrUpdate(pl);
            HibernateUtil.commitTransaction();

        } catch (Exception ex)
        {
            log.error("******* " + ex);
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException("Couldn't create PickList for ["+usysTableName+"]");
        }

        try
        {
            Statement stmt   = oldDBConn.createStatement();
            String    sqlStr = "select * from "+usysTableName+" where InterfaceID is not null";

            log.info(sqlStr);

            boolean   useField = false;
            ResultSet rs       = stmt.executeQuery(sqlStr);

            // check for no records which is OK
            if (!rs.first())
            {
                return true;
            }

            do
            {
                Object fieldObj = rs.getObject(fieldSetInx);
                if (fieldObj != null)
                {
                    useField = true;
                    break;
                }
            } while (rs.next());

            Hashtable<String, String> values = new Hashtable<String, String>();

            log.info("Using FieldSetSubTypeID "+useField);
            rs.first();
            int       count   = 0;
            do
            {
                if (!useField || rs.getObject(fieldSetInx) != null)
                {
                    String val = rs.getString(dataInx);
                    String lowerStr = val.toLowerCase();
                    if (values.get(lowerStr) == null)
                    {
                        log.info("["+val+"]");
                        pl.addPickListItem(val, val);
                        values.put(lowerStr, val);
                        count++;
                    } else
                    {
                        log.info("Discarding duplicate picklist value["+val+"]");
                    }
                }
            } while (rs.next());

            log.info("Processed "+usysTableName+"  "+count+" records.");

            HibernateUtil.beginTransaction();

            session.saveOrUpdate(pl);

            HibernateUtil.commitTransaction();

            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }

    }

    /**
     * Converts all the USYS tables to PickLists.
     * @return true on success, false on failure
     */
    public boolean convertUSYSTables()
    {
        log.info("Converting USYS Tables.");

        BasicSQLUtils.deleteAllRecordsFromTable("picklist");
        BasicSQLUtils.deleteAllRecordsFromTable("picklist_items");

        String[] tables = {
                "usysaccessionstatus",            "AccessionStatus",
                "usysaccessiontype",              "AccessionType",
                "usysborrowagenrole",             "BorrowAgentRole",
                "usysaccessionarole",             "AccessionRole",
                "usysdeaccessiorole",             "DeaccessionaRole",
                "usysloanagentsrole",             "LoanAgentsRole",
                "usysbiologicalsex",              "BiologicalSex",
                "usysbiologicalstage",            "BiologicalStage",
                "usyscollectingmethod",           "CollectingMethod",
                "usyscollobjprepmeth",            "CollObjPrepMeth",
                "usysdeaccessiotype",             "DeaccessionType",
                "usysdeterminatconfidence",       "DeterminationConfidence",
                "usysdeterminatmethod",           "DeterminationMethod",
                "usysdeterminattypestatusname",   "DeterminationTypeStatus",
                "usyshabitathabitattype",         "HabitatTtype",
                "usyslocalityelevationmethod",    "LocalityElevationMethod",
                "usysobservatioobservationmetho", "ObservationMethod",
                "usyspermittype",                 "PermitType",
                "usyspreparatiocontainertype",    "PrepContainertype",
                "usyspreparatiomedium",           "PreparatioMedium",
                "usyspreparatiopreparationtype",  "PreparationType",
                "usysshipmentshipmentmethod",     "ShipmentMethod"
                };

        setProcess(0, tables.length);
        
        for (int i=0;i<tables.length;i++)
        {
            setDesc("Converting "+tables[i]);

            setProcess(i);
            
            boolean status = convertUSYSToPicklist(tables[i], tables[i+1]);
            if (!status)
            {
                log.error(tables[i]+" failed to convert.");
                return false;
            }
            i++;
       }
        setProcess(tables.length);
        return true;
    }


    /**
     * Creates a map from a String Preparation Type to its ID in the table.
     * @return map of name to PrepType
     */
    public Map<String, PrepType> createPreparationTypesFromUSys()
    {
        deleteAllRecordsFromTable("preptype");

        Hashtable<String, PrepType> prepTypeMapper = new Hashtable<String, PrepType>();

        try
        {
            /*
            +-----------------------+-------------+------+-----+---------+-------+
            | Field                 | Type        | Null | Key | Default | Extra |
            +-----------------------+-------------+------+-----+---------+-------+
            | USYSCollObjPrepMethID | int(11)     |      | PRI | 0       |       |
            | InterfaceID           | int(11)     | YES  |     | NULL    |       |
            | FieldSetSubTypeID     | int(11)     | YES  |     | NULL    |       |
            | PreparationMethod     | varchar(50) | YES  |     | NULL    |       |
            +-----------------------+-------------+------+-----+---------+-------+
             */
            Statement stmt   = oldDBConn.createStatement();
            String    sqlStr = "select USYSCollObjPrepMethID, InterfaceID, FieldSetSubTypeID, PreparationMethod from usyscollobjprepmeth";

            log.info(sqlStr);

            boolean foundMisc = false;
            ResultSet rs      = stmt.executeQuery(sqlStr);
            int       count   = 0;
            while (rs.next())
            {
                if (rs.getObject(2) != null && rs.getObject(3) != null)
                {
                    String name = rs.getString(4);
                    PrepType prepType = AttrUtils.loadPrepType(name);
                    prepTypeMapper.put(name.toLowerCase(), prepType);
                    if (name.equalsIgnoreCase("misc"))
                    {
                        foundMisc = true;
                    }
                }
                count++;
            }

            if (!foundMisc)
            {
                String name = "Misc";
                PrepType prepType = AttrUtils.loadPrepType(name);
                prepTypeMapper.put(name.toLowerCase(), prepType);
                count++;
            }
            log.info("Processed PrepType "+count+" records.");


        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            return prepTypeMapper;
        }

        return prepTypeMapper;
   }

    /**
     * Convert the column name
     * @param name the name
     * @return the converted name
     */
    protected String convertColumnName(final String name)
    {
        StringBuilder nameStr = new StringBuilder();
        int cnt = 0;
        for (char c : name.toCharArray())
        {
            if (cnt == 0)
            {
                nameStr.append(name.toUpperCase().charAt(0));
                cnt++;

            } else if (c < 'a')
            {
                nameStr.append(' ');
                nameStr.append(c);

            } else
            {
                nameStr.append(c);
            }
        }
        return nameStr.toString();
    }

    /**
     * Returns the proper value depending on the type
     * @param value the data value from the database object
     * @param type the defined type
     * @param attr the data value from the database object
     * @return the data object for the value
     */
    protected Object getData(final AttributeIFace.FieldType type, AttributeIFace attr)
    {
        if (type == AttributeIFace.FieldType.BooleanType)
        {
            return attr.getDblValue() != 0.0;

        } else if (type == AttributeIFace.FieldType.FloatType)
        {
            return attr.getDblValue().floatValue();

        } else if (type == AttributeIFace.FieldType.DoubleType)
        {
            return attr.getDblValue();

        } else if (type == AttributeIFace.FieldType.IntegerType)
        {
            return attr.getDblValue().intValue();

        } else
        {
            return attr.getStrValue();
        }
    }

    /**
     * Returns a converted value from the old schema to the new schema
     * @param rs the resultset
     * @param index the index of the column in the resultset
     * @param type the defined type for the new schema
     * @param metaData the metat data describing the old schema column
     * @return the new data object
     */
    protected Object getData(final ResultSet                   rs,
                             final int                         index,
                             final AttributeIFace.FieldType    type,
                             final BasicSQLUtils.FieldMetaData metaData)
    {
        // Note: we need to check the old schema once again because the "type" may have been mapped
        // so now we must map the actual value

        AttributeIFace.FieldType oldType = getDataType(metaData.getName(), metaData.getType());

        try
        {
            Object value = rs.getObject(index);

            if (type == AttributeIFace.FieldType.BooleanType)
            {
                if (value == null)
                {
                   return false;

                } else if (oldType == AttributeIFace.FieldType.IntegerType)
                {
                    return rs.getInt(index) != 0;

                } else if (oldType == AttributeIFace.FieldType.FloatType)
                {
                    return rs.getFloat(index) != 0.0f;

                } else if (oldType == AttributeIFace.FieldType.DoubleType)
                {
                    return rs.getDouble(index) != 0.0;

                } else if (oldType == AttributeIFace.FieldType.StringType)
                {
                    return rs.getString(index).equalsIgnoreCase("true");
                }
                log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
                return false;

            } else if (type == AttributeIFace.FieldType.FloatType)
            {
                if (value == null)
                {
                   return 0.0f;

                } else if (oldType == AttributeIFace.FieldType.FloatType)
                {
                    return rs.getFloat(index);

                } else if (oldType == AttributeIFace.FieldType.DoubleType)
                {
                    return rs.getFloat(index);
                }
                log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
                return 0.0f;

            } else if (type == AttributeIFace.FieldType.DoubleType)
            {
                if (value == null)
                {
                   return 0.0;

                } else if (oldType == AttributeIFace.FieldType.FloatType)
                {
                    return rs.getDouble(index);

                } else if (oldType == AttributeIFace.FieldType.DoubleType)
                {
                    return rs.getDouble(index);
                }
                log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
                return 0.0;

            } else if (type == AttributeIFace.FieldType.IntegerType)
            {
                if (value == null)
                {
                   return 0;

                } else if (oldType == AttributeIFace.FieldType.IntegerType)
                {
                    return rs.getInt(index) != 0;
                }
                log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
                return 0;

            } else
            {
                return rs.getString(index);
            }
        }
        catch (SQLException ex)
        {
            log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
            log.error(ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Sets a converted value from the old schema to the new schema into the CollectionObjectAttr object
     * @param rs the resultset
     * @param index the index of the column in the resultset
     * @param type the defined type for the new schema
     * @param metaData the metat data describing the old schema column
     * @param colObjAttr the object the data is set into
     * @return the new data object
     */
    protected void setData(final ResultSet                   rs,
                           final int                         index,
                           final AttributeIFace.FieldType    type,
                           final BasicSQLUtils.FieldMetaData metaData,
                           final CollectionObjectAttr        colObjAttr)
    {
        // Note: we need to check the old schema once again because the "type" may have been mapped
        // so now we must map the actual value

        AttributeIFace.FieldType oldType = getDataType(metaData.getName(), metaData.getType());

        try
        {
            Object value = rs.getObject(index);

            if (type == AttributeIFace.FieldType.BooleanType)
            {
                if (value == null)
                {
                    colObjAttr.setDblValue(0.0); //false

                } else if (oldType == AttributeIFace.FieldType.IntegerType)
                {
                    colObjAttr.setDblValue(rs.getInt(index) != 0 ? 1.0 : 0.0);

                } else if (oldType == AttributeIFace.FieldType.FloatType)
                {
                    colObjAttr.setDblValue(rs.getFloat(index) != 0.0f ? 1.0 : 0.0);

                } else if (oldType == AttributeIFace.FieldType.DoubleType)
                {
                    colObjAttr.setDblValue(rs.getDouble(index) != 0.0 ? 1.0 : 0.0);

                } else if (oldType == AttributeIFace.FieldType.StringType)
                {
                    colObjAttr.setDblValue(rs.getString(index).equalsIgnoreCase("true") ? 1.0 : 0.0);
                } else
                {
                    log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
                }

            } else if (type == AttributeIFace.FieldType.IntegerType ||
                       type == AttributeIFace.FieldType.DoubleType ||
                       type == AttributeIFace.FieldType.FloatType)
            {
                if (value == null)
                {
                    colObjAttr.setDblValue(0.0);

                } else if (oldType == AttributeIFace.FieldType.IntegerType)
                {
                    colObjAttr.setDblValue((double)rs.getInt(index));

                } else if (oldType == AttributeIFace.FieldType.FloatType)
                {
                    colObjAttr.setDblValue((double)rs.getFloat(index));

                } else if (oldType == AttributeIFace.FieldType.DoubleType)
                {
                    colObjAttr.setDblValue(rs.getDouble(index));

                } else
                {
                    log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
                }

            } else
            {
                colObjAttr.setStrValue(rs.getString(index));
            }
        }
        catch (SQLException ex)
        {
            log.error("Error maping from schema["+metaData.getType()+"] to ["+type.toString()+"]");
            log.error(ex);
            throw new RuntimeException(ex);
        }
    }


    /**
     * Figure out the data type given the database column's field's name and data type
     * @param name the column name
     * @param type the database schema type for the column
     * @return the AttributeIFace.Type for the column
     */
    protected AttributeIFace.FieldType getDataType(final String name, final String type)
    {
        if (name.startsWith("YesNo"))
        {
            return AttributeIFace.FieldType.BooleanType;

        //} else if (name.equalsIgnoreCase("remarks"))
        //{
        //    return AttributeIFace.FieldType.MemoType;

        } else if (type.equalsIgnoreCase("float"))
        {
            return AttributeIFace.FieldType.FloatType;

        } else if (type.equalsIgnoreCase("double"))
        {
            return AttributeIFace.FieldType.DoubleType;

        } else if (type.startsWith("varchar") || type.startsWith("text") || type.startsWith("longtext"))
        {
            return AttributeIFace.FieldType.StringType;

        } else
        {
            return AttributeIFace.FieldType.IntegerType;
        }
    }

    /**
     * @param data
     * @param fromTableName
     * @param oldColName
     * @return
     */
    protected Object getMappedId(final Object data, final String fromTableName, final String oldColName)
    {
        if (idMapperMgr != null && oldColName.endsWith("ID"))
        {

            IdMapperIFace idMapper =  idMapperMgr.get(fromTableName, oldColName);
            if (idMapper != null)
            {
                return idMapper.get((Long)data);
            }
            // else
            
            //throw new RuntimeException("No Map for ["+fromTableName+"]["+oldMappedColName+"]");
            if (!oldColName.equals("MethodID") &&
                    !oldColName.equals("RoleID") &&
                    !oldColName.equals("CollectionID") &&
                    !oldColName.equals("ConfidenceID") &&
                    !oldColName.equals("TypeStatusNameID") &&
                    !oldColName.equals("ObservationMethodID"))
            {
                System.out.println("No Map for ["+fromTableName+"]["+oldColName+"]");
            }
        }
        return data;
    }


    /**
     * Convert all the biological attributes to Collection Object Attributes.
     * Each old record may end up being multiple records in the new schema. This will first figure out
     * which columns in the old schema were used and olnly map those columns to the new database.<br><br>
     * It also will use the old name if there is not mapping for it. The old name is converted from lower/upper case to
     * be space separated where each part of the name starts with a capital letter.
     *
     * @param colObjDef the Collection Object Definition
     * @param colToNameMap a mape for old names to new names
     * @param typeMap a map for changing the type of the data (meaning an old value may be a boolean stored in a float)
     * @return true for success
     */
    public boolean convertBiologicalAttrs(CollectionObjDef colObjDef, final Map<String, String> colToNameMap, final Map<String, Short> typeMap)
    {
        AttributeIFace.FieldType[] attrTypes = {AttributeIFace.FieldType.IntegerType, AttributeIFace.FieldType.FloatType,
                                                AttributeIFace.FieldType.DoubleType, AttributeIFace.FieldType.BooleanType,
                                                AttributeIFace.FieldType.StringType,
                                                //AttributeIFace.FieldType.MemoType
                                                };

        Session session = HibernateUtil.getCurrentSession();

        deleteAllRecordsFromTable(newDBConn, "collectionobjectattr");
        deleteAllRecordsFromTable(newDBConn, "attributedef");

        try
        {
            Statement stmt = oldDBConn.createStatement();

            // grab the field and their type from the old schema
            List<BasicSQLUtils.FieldMetaData>        oldFieldMetaData    = new ArrayList<BasicSQLUtils.FieldMetaData>();
            Map<String, BasicSQLUtils.FieldMetaData> oldFieldMetaDataMap = new Hashtable<String, BasicSQLUtils.FieldMetaData>();
            getFieldMetaDataFromSchema(oldDBConn, "biologicalobjectattributes", oldFieldMetaData);

            // create maps to figure which columns where used
            List<String>              columnsInUse = new ArrayList<String>();
            Map<String, AttributeDef> attrDefs     = new Hashtable<String, AttributeDef>();

            List<Integer>             counts       = new ArrayList<Integer>();

            int totalCount = 0;

            for (BasicSQLUtils.FieldMetaData md : oldFieldMetaData)
            {
                // Skip these fields
                if (md.getName().indexOf("ID") == -1 && md.getName().indexOf("Timestamp") == -1&& md.getName().indexOf("LastEditedBy") == -1)
                {
                    oldFieldMetaDataMap.put(md.getName(), md); // add to map for later

                    //log.info(convertColumnName(md.getName())+"  "+ md.getType());
                    String sqlStr = "select count("+md.getName()+") from biologicalobjectattributes where "+md.getName()+" is not null";
                    ResultSet rs  = stmt.executeQuery(sqlStr);
                    if (rs.first() && rs.getInt(1) > 0)
                    {
                        int rowCount = rs.getInt(1);
                        totalCount += rowCount;
                        counts.add(rowCount);

                        log.info(md.getName() + " has " + rowCount + " rows of values");

                        columnsInUse.add(md.getName());
                        AttributeDef attrDef = new AttributeDef();

                        String newName = convertColumnName(md.getName());
                        attrDef.setFieldName(newName);
                        System.out.println("mapping["+newName+"]["+md.getName()+"]");

                        //newNameToOldNameMap.put(newName, md.getName());

                        short dataType = -1;
                        if (typeMap != null)
                        {
                            Short type = typeMap.get(md.getName());
                            if (type == null)
                            {
                                dataType = type;
                            }
                        }

                        if (dataType == -1)
                        {
                            dataType = getDataType(md.getName(), md.getType()).getType();
                        }

                        attrDef.setDataType(dataType);
                        attrDef.setCollectionObjDef(colObjDef);
                        attrDef.setTableType(GenericDBConversion.TableType.CollectionObject.getType());

                        attrDefs.put(md.getName(), attrDef);
                        //attrDefs.setTimestampCreated(new Date());
                        //attrDefs.setTimestampModified(new Date());

                        try
                        {
                            HibernateUtil.beginTransaction();
                            session.save(attrDef);
                            HibernateUtil.commitTransaction();

                        } catch (Exception e)
                        {
                            log.error("******* " + e);
                            HibernateUtil.rollbackTransaction();
                            throw new RuntimeException(e);
                        }

                    }
                    rs.close();
                }
            } // for
            log.info("Total Number of Attrs: " + totalCount);

            // Now that we know which columns are being used we can start the conversion process

            log.info("biologicalobjectattributes columns in use: "+columnsInUse.size());
            if (columnsInUse.size() > 0)
            {
                int inx = 0;
                StringBuilder str = new StringBuilder("select BiologicalObjectAttributesID");
                for (String name : columnsInUse)
                {
                    str.append(", ");
                    str.append(name);
                    inx++;
                }

                str.append(" from biologicalobjectattributes order by BiologicalObjectAttributesID");
                log.info("sql: "+str.toString());
                ResultSet rs = stmt.executeQuery(str.toString());

                int[]         countVerify = new int[counts.size()];
                for (int i=0;i<countVerify.length;i++)
                {
                    countVerify[i] = 0;
                }
                boolean       useHibernate = false;
                StringBuilder strBufInner  = new StringBuilder();
                int           recordCount  = 0;
                while (rs.next())
                {

                    if (useHibernate)
                    {
                        Criteria criteria = session.createCriteria(CollectionObject.class);
                        criteria.add(Restrictions.eq("collectionObjectId", rs.getInt(1)));
                        List<?> list = criteria.list();
                        if (list.size() == 0)
                        {
                            log.error("**** Can't find the CollectionObject "+rs.getInt(1));
                        } else
                        {
                            CollectionObject colObj = (CollectionObject)list.get(0);

                            inx = 2; // skip the first column (the ID)
                            for (String name : columnsInUse)
                            {
                                AttributeDef                attrDef = attrDefs.get(name); // the needed AttributeDef by name
                                BasicSQLUtils.FieldMetaData md      = oldFieldMetaDataMap.get(name);

                                // Create the new Collection Object Attribute
                                CollectionObjectAttr colObjAttr = new CollectionObjectAttr();
                                colObjAttr.setCollectionObject(colObj);
                                colObjAttr.setDefinition(attrDef);
                                colObjAttr.setTimestampCreated(new Date());
                                colObjAttr.setTimestampModified(new Date());

                                //String oldName = newNameToOldNameMap.get(attrDef.getFieldName());
                                //System.out.println("["+attrDef.getFieldName()+"]["+oldName+"]");


                                //System.out.println(inx+"  "+attrTypes[attrDef.getDataType()]+"  "+md.getName()+"  "+md.getType());
                                setData(rs, inx, attrTypes[attrDef.getDataType()], md, colObjAttr);

                                HibernateUtil.beginTransaction();
                                session.save(colObjAttr);
                                HibernateUtil.commitTransaction();

                                inx++;
                                if (recordCount % 2000 == 0)
                                {
                                    log.info("CollectionObjectAttr Records Processed: "+recordCount);
                                }
                                recordCount++;
                            } // for
                            //log.info("Done - CollectionObjectAttr Records Processed: "+recordCount);
                        }
                    } else
                    {
                        inx = 2; // skip the first column (the ID)
                        for (String name : columnsInUse)
                        {
                            AttributeDef                attrDef = attrDefs.get(name); // the needed AttributeDef by name
                            BasicSQLUtils.FieldMetaData md      = oldFieldMetaDataMap.get(name);


                            if (rs.getObject(inx) != null)
                            {
                                Integer newRecId = (Integer)getMappedId(rs.getInt(1), "biologicalobjectattributes", "BiologicalObjectAttributesID");

                                Object  data  = getData(rs, inx, attrTypes[attrDef.getDataType()], md);
                                boolean isStr = data instanceof String;

                                countVerify[inx - 2]++;

                                strBufInner.setLength(0);
                                Date date = new Date();
                                strBufInner.append("INSERT INTO collectionobjectattr VALUES (");
                                strBufInner.append("NULL");//Integer.toString(recordCount));
                                strBufInner.append(",");
                                strBufInner.append(getStrValue(isStr ? data : null));
                                strBufInner.append(",");
                                strBufInner.append(getStrValue(isStr ? null : data));
                                strBufInner.append(",");
                                strBufInner.append(getStrValue(date));
                                strBufInner.append(",");
                                strBufInner.append(getStrValue(date));
                                strBufInner.append(",");
                                strBufInner.append(newRecId.intValue());
                                strBufInner.append(",");
                                strBufInner.append(getStrValue(attrDef.getAttributeDefId()));
                                strBufInner.append(")");

                                try
                                {
                                    Statement updateStatement = newDBConn.createStatement();
                                    updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                                    if (false)
                                    {
                                        System.out.println(strBufInner.toString());
                                    }
                                    updateStatement.executeUpdate(strBufInner.toString());
                                    updateStatement.clearBatch();
                                    updateStatement.close();
                                    updateStatement = null;

                                } catch (SQLException e)
                                {
                                    log.error(strBufInner.toString());
                                    log.error("Count: "+recordCount);
                                    e.printStackTrace();
                                    log.error(e);
                                    throw new RuntimeException(e);
                                }

                                if (recordCount % 2000 == 0)
                                {
                                    log.info("CollectionObjectAttr Records Processed: "+recordCount);
                                }
                                recordCount++;
                            }
                            inx++;
                        } // for
                    } // if
                } // while
                rs.close();
                stmt.close();

                log.info("Count Verification:");
                for (int i=0;i<counts.size();i++)
                {
                    log.info(columnsInUse.get(i)+" ["+counts.get(i)+"]["+countVerify[i]+"] "+(counts.get(i) - countVerify[i]));

                }
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Converts all the CollectionObject Physical records and CollectionObjectCatalog Records into the new schema Preparation table.
     * @return true if no errors
     */
    public boolean createPreparationRecords(final Map<String, PrepType> prepTypeMap)
    {
        deleteAllRecordsFromTable(newDBConn, "preparation");

        try
        {
            Statement    stmt = oldDBConn.createStatement();
            StringBuilder str  = new StringBuilder();

            List<String> oldFieldNames = new ArrayList<String>();

            StringBuilder sql = new StringBuilder("select ");
            List<String> names = new ArrayList<String>();
            getFieldNamesFromSchema(oldDBConn, "collectionobject", names);

            sql.append(buildSelectFieldList(names, "collectionobject"));
            sql.append(", ");
            oldFieldNames.addAll(names);

            names.clear();
            getFieldNamesFromSchema(oldDBConn, "collectionobjectcatalog", names);
            sql.append(buildSelectFieldList(names, "collectionobjectcatalog"));
            oldFieldNames.addAll(names);

            sql.append(" From collectionobject Inner Join collectionobjectcatalog ON collectionobject.CollectionObjectID = collectionobjectcatalog.CollectionObjectCatalogID ");
            sql.append("Where not (collectionobject.DerivedFromID Is Null) order by collectionobjectcatalog.CollectionObjectCatalogID");

            log.info(sql);

            List<BasicSQLUtils.FieldMetaData> newFieldMetaData = new ArrayList<BasicSQLUtils.FieldMetaData>();
            getFieldMetaDataFromSchema(newDBConn, "preparation", newFieldMetaData);


            log.info("Number of Fields in Preparation "+newFieldMetaData.size());
            String sqlStr = sql.toString();

            Map<String, Integer> oldNameIndex = new Hashtable<String, Integer>();
            int inx = 0;
            for (String name : oldFieldNames)
            {
                oldNameIndex.put(name, inx++);
                //System.out.println(name+" "+(inx-1));
            }
            Hashtable<String, String> newToOld = new Hashtable<String, String>();
            newToOld.put("PreparationID", "CollectionObjectID");
            newToOld.put("CollectionObjectID", "DerivedFromID");
            newToOld.put("StorageLocation", "Location");

            IdMapperIFace agentIdMapper = idMapperMgr.get("agent", "AgentID");
            //IdMapperIFace prepIdMapper =  idMapperMgr.get("preparation",  "PreparationID");

            boolean doDebug   = false;
            ResultSet rs      = stmt.executeQuery(sqlStr);
            
            if (rs.last())
            {

                setProcess(0, rs.getRow()); 

                rs.first();
                
            } else
            {
                rs.close();
                stmt.close();

                setProcess(0, 0); 

                return true;
            }
            
            Integer   idIndex = oldNameIndex.get("CollectionObjectID");
            int       count   = 0;
            do
            {
                Long      preparedById = null;
                Date      preparedDate = null;


                boolean   checkForPreps = false;
                if (checkForPreps)
                {
                    Integer   recordId     = rs.getInt(idIndex+1);
                    Statement subStmt      = oldDBConn.createStatement();
                    String    subQueryStr  = "select PreparedByID,PreparedDate from preparation where PreparationID = "+recordId;
                    ResultSet subQueryRS   = subStmt.executeQuery(subQueryStr);
                    if (subQueryRS.first())
                    {
                        preparedById = subQueryRS.getLong(1);
                        preparedDate = UIHelper.convertIntToDate(subQueryRS.getInt(2));
                    }
                    subQueryRS.close();
                    subStmt.close();
                }

                /*int catNum =  rs.getInt(oldNameIndex.get("CatalogNumber")+1);
                doDebug = catNum == 30972;

                if (doDebug)
                {
                    System.out.println("CatalogNumber      "+catNum);
                    System.out.println("CollectionObjectID "+rs.getInt(oldNameIndex.get("CollectionObjectID")+1));
                    System.out.println("DerivedFromID      "+rs.getInt(oldNameIndex.get("DerivedFromID")+1));
                }*/

                str.setLength(0);
                str.append("INSERT INTO preparation VALUES (");
                for (int i=0;i<newFieldMetaData.size();i++)
                {
                    if (i > 0) str.append(", ");

                    String newFieldName = newFieldMetaData.get(i).getName();
                    String mappedName   = newToOld.get(newFieldName);

                    if (mappedName != null)
                    {
                        newFieldName = mappedName;
                    } else
                    {
                        mappedName = newFieldName;
                    }

                    if (i == 0)
                    {
                        //Integer  recId  = count+1;
                        str.append("NULL");//getStrValue(recId));

                    } else if (newFieldName.equals("PreparedByID"))
                    {
                        if (agentIdMapper != null)
                        {
                            str.append(getStrValue(agentIdMapper.get(preparedById)));
                        } else
                        {
                            log.error("No Map for PreparedByID["+preparedById+"]");
                        }

                    } else if (newFieldName.equals("PreparedDate"))
                    {
                        str.append(getStrValue(preparedDate));

                    } else if (newFieldName.equals("DerivedFromIDX"))
                    {
                        //
                    } else if (newFieldName.equals("Count"))
                    {
                        Integer value = rs.getInt("Count");
                        if (rs.wasNull())
                        {
                            value = null;
                        }
                        str.append(getStrValue(value));
                        
                        
                    } else if (newFieldName.equals("PrepTypeID"))
                    {
                        String value = rs.getString(oldNameIndex.get("PreparationMethod")+1);
                        if (value == null || value.length() == 0)
                        {
                            value = "n/a";
                        }

                        PrepType prepType = prepTypeMap.get(value.toLowerCase());
                        if (prepType != null)
                        {
                            Long prepTypeId = prepType.getPrepTypeId();
                            if (prepTypeId != null)
                            {
                                str.append(getStrValue(prepTypeId));
    
                            } else
                            {
                                str.append("NULL");
                                log.error("***************** Couldn't find PreparationMethod["+value+"] in PrepTypeMap");
                            }
                        } else
                        {
                            log.info("Couldn't find PrepType["+value+"] creating it.");
                            prepType = new PrepType();
                            prepType.initialize();
                            prepType.setName(value);
                            try
                            {
                                Session session = HibernateUtil.getCurrentSession();
                                Transaction trans = session.beginTransaction();
                                trans.begin();
                                session.save(prepType);
                                trans.commit();
                                
                                str.append(getStrValue(prepType.getPrepTypeId()));
                                
                            } catch (Exception ex)
                            {
                                ex.printStackTrace();
                                throw new RuntimeException(ex);
                            }
                        }


                    } else if (newFieldName.equals("LocationID"))
                    {
                        str.append("NULL");

                    } else
                    {
                        Integer index = oldNameIndex.get(newFieldName);
                        if (index == null)
                        {
                            log.error("Couldn't find new field name["+newFieldName+"] in old field name in index Map");
                            stmt.close();
                            return false;
                        }
                        Object  data  = rs.getObject(index+1);

                        if (idMapperMgr != null && mappedName.endsWith("ID"))
                        {
                            IdMapperIFace idMapper;
                            if (mappedName.equals("DerivedFromID"))
                            {
                                idMapper = idMapperMgr.get("preparation", "PreparationID");

                            } else
                            {
                                idMapper =  idMapperMgr.get("collectionobject", mappedName);

                            }
                            if (idMapper != null)
                            {
                                data = idMapper.get(rs.getLong(index));
                            } else
                            {
                                System.out.println("No Map for [collectionobject]["+mappedName+"]");
                            }
                        }
                        str.append(getStrValue(data, newFieldMetaData.get(i).getType()));

                    }

                }
                str.append(")");
                //log.info("\n"+str.toString());
                if (hasFrame)
                {
                    if (count % 500 == 0)
                    {
                        setProcess(count);
                    }
                    
                } else
                {
                    if (count % 2000 == 0)
                    {
                        log.info("Preparation Records: "+count);
                    }                        
                }
                
                try
                {
                    Statement updateStatement = newDBConn.createStatement();
                    updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                    if (doDebug)
                    {
                        System.out.println(str.toString());
                    }
                    updateStatement.executeUpdate(str.toString());
                    updateStatement.clearBatch();
                    updateStatement.close();
                    updateStatement = null;

                } catch (SQLException e)
                {
                    log.error("Count: "+count);
                    e.printStackTrace();
                    log.error(e);
                    throw new RuntimeException(e);
                }

                count++;
                //if (count == 1) break;
            } while (rs.next());
            
            
            if (hasFrame)
            {

                setProcess(count);
                
            } else
            {
                if (count % 2000 == 0)
                {
                    log.info("Processed CollectionObject "+count+" records.");
                }                        
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }

        return true;

    }

    /**
     * Converts all the Determinations.
     * @return true if no errors
     */
    public boolean convertDeterminationRecords()
    {
        deleteAllRecordsFromTable(newDBConn, "determination"); // automatically closes the connection
        
        if (BasicSQLUtils.getNumRecords(oldDBConn, "determination") == 0)
        {
            return true;
        }
        
        Map<String, String> colNewToOldMap = createFieldNameMap(new String[] {"CollectionObjectID", "BiologicalObjectID", 
                "IsCurrent",           "Current1", 
                "DeterminedDate",      "Date1", 
                "TaxonID",             "TaxonNameID"});
       
        try
        {
            Statement     stmt = oldDBConn.createStatement();
            StringBuilder str  = new StringBuilder();

            List<String> oldFieldNames = new ArrayList<String>();

            StringBuilder sql = new StringBuilder("SELECT ");
            List<String> names = new ArrayList<String>();
            getFieldNamesFromSchema(oldDBConn, "determination", names);

            sql.append(buildSelectFieldList(names, "determination"));
            oldFieldNames.addAll(names);

            sql.append(" FROM determination");

            log.info(sql);

            List<BasicSQLUtils.FieldMetaData> newFieldMetaData = new ArrayList<BasicSQLUtils.FieldMetaData>();
            getFieldMetaDataFromSchema(newDBConn, "determination", newFieldMetaData);

            log.info("Number of Fields in New Determination "+newFieldMetaData.size());
            String sqlStr = sql.toString();

            Map<String, Integer> oldNameIndex = new Hashtable<String, Integer>();
            int inx = 0;
            for (String name : oldFieldNames)
            {
                oldNameIndex.put(name, inx++);
            }

            String tableName = "determination";

            int isCurrentInx = oldNameIndex.get("IsCurrent") + 1;
            
            // Get Current and Unknow Record Ids
            long currentDetStatusID = 0;
            Statement newStmt = newDBConn.createStatement();
            ResultSet rs2 = newStmt.executeQuery("select DeterminationStatusID from determinationstatus where Name = 'Current'");
            if (rs2.first())
            {
                currentDetStatusID = rs2.getLong(1);
                rs2.close();
                
            } else
            {
                throw new RuntimeException("Couldn't find Current DeterminationStatus record!");
            }
            
            long unknownDetStatusID = 0;
            rs2 = newStmt.executeQuery("select DeterminationStatusID from determinationstatus where Name = 'Unknown'");
            if (rs2.first())
            {
                unknownDetStatusID = rs2.getLong(1);
                rs2.close();
                newStmt.close();
                
            } else
            {
                throw new RuntimeException("Couldn't find Current DeterminationStatus record!");
            }

            log.info(sqlStr);
            ResultSet rs = stmt.executeQuery(sqlStr);
            
            if (hasFrame)
            {
                if (rs.last())
                {
                    setProcess(0, rs.getRow()); 
                    rs.first();
                    
                } else
                {
                    rs.close();
                    stmt.close();
                    return true;
                }
            } else
            {
                if (!rs.first())
                {
                    rs.close();
                    stmt.close();
                    return true;                   
                }
            }

            int count = 0;
            do
            {
                str.setLength(0);
                str.append("INSERT INTO determination VALUES (");
                for (int i=0;i<newFieldMetaData.size();i++)
                {
                    if (i > 0) str.append(", ");

                    String newFieldName = newFieldMetaData.get(i).getName();

                    if (i == 0)
                    {
                        Integer  recId  = count+1;
                        str.append(getStrValue(recId));

                    } else if (newFieldName.equals("DeterminationStatusID"))
                    {
                        str.append(Long.toString(rs.getShort(isCurrentInx) != 0 ? currentDetStatusID : unknownDetStatusID)); 
                        
                    } else
                    {
                        Integer index = null;
                        String  oldMappedColName = colNewToOldMap.get(newFieldName);
                        if (oldMappedColName != null)
                        {
                            index = oldNameIndex.get(oldMappedColName);

                        } else
                        {
                            index            = oldNameIndex.get(newFieldName);
                            oldMappedColName = newFieldName;
                        }
                        
                        if (index == null)
                        {
                            log.error("Couldn't find new field name["+newFieldName+"] in old field name in index Map");
                            stmt.close();
                            return false;
                        }
                        Object data  = rs.getObject(index+1);

                        if (data != null)
                        {
                            int idInx = newFieldName.lastIndexOf("ID");
                            if (idMapperMgr != null && idInx > -1)
                            {
                                IdMapperIFace idMapper =  idMapperMgr.get(tableName, oldMappedColName);
                                if (idMapper != null)
                                {
                                    data = idMapper.get(rs.getLong(index+1));
                                } else
                                {
                                    log.error("No Map for ["+tableName+"]["+oldMappedColName+"]");
                                }
                            }
                        }
                        str.append(getStrValue(data, newFieldMetaData.get(i).getType()));
                    }
                }
                str.append(")");
                
                if (hasFrame)
                {
                    if (count % 500 == 0) 
                    {
                        setProcess(count);
                    }
                    
                } else
                {
                    if (count % 2000 == 0) 
                    {
                        log.info("Determination Records: "+count);
                    }
                }

                try
                {
                    Statement updateStatement = newDBConn.createStatement();
                    updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                    updateStatement.executeUpdate(str.toString());
                    updateStatement.clearBatch();
                    updateStatement.close();
                    updateStatement = null;

                } catch (SQLException e)
                {
                    log.error("Count: "+count);
                    e.printStackTrace();
                    log.error(e);
                    rs.close();
                    stmt.close();
                    throw new RuntimeException(e);
                }

                count++;
                //if (count > 10) break;
            } while (rs.next());
            
            if (hasFrame)
            {
                setProcess(count);
            } else
            {
                log.info("Processed Determination "+count+" records.");
            }
            rs.close();

            stmt.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }


        return true;

    }
    
    /**
     * Converts all the CollectionObject and CollectionObjectCatalog Records into the new schema CollectionObject table.
     * All "logical" records are moved to the CollectionObject table and all "physical" records are moved to the Preparation table.
     * @return true if no errors
     */
    public boolean createCollectionRecords()
    {
        IdHashMapper colObjTaxonMapper = (IdHashMapper)idMapperMgr.get("ColObjCatToTaxonType");
        IdMapperIFace     taxonomyTypeMapper  = idMapperMgr.get("TaxonomyType", "TaxonomyTypeID");
        
        colObjTaxonMapper.setShowLogErrors(false); // NOTE: TURN THIS ON FOR DEBUGGING or running new Databases through it
        
        log.info("colObjTaxonMapper: "+colObjTaxonMapper.size());

        //Hashtable<Integer, Integer> colObjTypeMap = new Hashtable<Integer, Integer>();

        deleteAllRecordsFromTable(newDBConn, "collectionobject"); // automatically closes the connection
        try
        {
            Statement    stmt = oldDBConn.createStatement();
            StringBuilder str  = new StringBuilder();

            List<String> oldFieldNames = new ArrayList<String>();

            StringBuilder sql = new StringBuilder("select ");
            List<String> names = new ArrayList<String>();
            getFieldNamesFromSchema(oldDBConn, "collectionobject", names);

            sql.append(buildSelectFieldList(names, "collectionobject"));
            sql.append(", ");
            oldFieldNames.addAll(names);

            names.clear();
            getFieldNamesFromSchema(oldDBConn, "collectionobjectcatalog", names);
            sql.append(buildSelectFieldList(names, "collectionobjectcatalog"));
            oldFieldNames.addAll(names);

            sql.append(" From collectionobject Inner Join collectionobjectcatalog ON collectionobject.CollectionObjectID = collectionobjectcatalog.CollectionObjectCatalogID Where collectionobject.CollectionObjectTypeID = 10");

            log.info(sql);

            //List<String> newFieldNames = new ArrayList<String>();
            //getFieldNamesFromSchema(newDBConn, "collectionobject", newFieldNames);

            List<BasicSQLUtils.FieldMetaData> newFieldMetaData = new ArrayList<BasicSQLUtils.FieldMetaData>();
            getFieldMetaDataFromSchema(newDBConn, "collectionobject", newFieldMetaData);

            log.info("Number of Fields in New CollectionObject "+newFieldMetaData.size());
            String sqlStr = sql.toString();

            Map<String, Integer> oldNameIndex = new Hashtable<String, Integer>();
            int inx = 0;
            log.info("---- Old Names ----");
            for (String name : oldFieldNames)
            {
                log.info("["+name+"]["+inx+"]");
                oldNameIndex.put(name, inx++);
            }

            log.info("---- New Names ----");
            for (BasicSQLUtils.FieldMetaData fmd : newFieldMetaData)
            {
                log.info("["+fmd.getName()+"]");
            }
            String tableName = "collectionobject";

            int objTypeInx   = oldNameIndex.get("CollectionObjectTypeID");

            log.info(sqlStr);
            ResultSet rs = stmt.executeQuery(sqlStr);
            
            if (hasFrame)
            {
                if (rs.last())
                {
                    setProcess(0, rs.getRow()); 
                    rs.first();
                    
                } else
                {
                    rs.close();
                    stmt.close();
                    return true;
                }
            } else
            {
                if (!rs.first())
                {
                    rs.close();
                    stmt.close();
                    return true;                   
                }
            }

            int count = 0;
            do
            {
                str.setLength(0);
                str.append("INSERT INTO collectionobject VALUES (");
                for (int i=0;i<newFieldMetaData.size();i++)
                {
                    if (i > 0) str.append(", ");

                    String newFieldName = newFieldMetaData.get(i).getName();

                    if (i == 0)
                    {
                        Integer  recId  = count+1;
                        str.append(getStrValue(recId));

                    } else if (newFieldName.equals("CatalogedDateVerbatim") ||
                                newFieldName.equals("ContainerID") ||
                                newFieldName.equals("ContainerItemID") ||
                                newFieldName.equals("AltCatalogNumber") ||
                                newFieldName.equals("GUID") ||
                                newFieldName.equals("RepositoryAgreementID") ||
                                newFieldName.equals("GroupPermittedToView") ||        // this may change when converting Specify 5.x
                                newFieldName.equals("CollectionObjectID")||
                                newFieldName.equals("VisibilitySetBy"))
                    {
                        str.append("NULL");

                    } 
                    else if(newFieldName.equals("Visibility")) //User/Security changes
                    {
                        str.append(defaultVisibilityLevel);
                    }
                    else if (newFieldName.equals("CollectionObjDefID"))
                    {
                        try
                        {
                            long oldColObjectTypeId = rs.getLong(objTypeInx);
                            //log.info("1* "+oldColObjectTypeId);

                            Long taxonomyTreeId = colObjTaxonMapper.get(oldColObjectTypeId);
                            //log.info("2* "+taxonomyTreeId);

                            if (taxonomyTreeId != null)
                            {
                                Long newTaxonomyTreeId = taxonomyTypeMapper.get(taxonomyTreeId);
                                //log.info("3* "+newTaxonomyTreeId);
    
                                str.append(Long.toString(newTaxonomyTreeId));
                            } else
                            {
                                //log.debug("Was unable to Map old Index["++"] to get it's TaxonTreeID");
                                str.append("NULL");
                            }

                        } catch (Exception ex)
                        {
                            str.append("NULL");
                        }

                    } else if (newFieldName.equals("CountAmt"))
                    {
                        Integer index = oldNameIndex.get("Count1");
                        if (index == null)
                        {
                            index = oldNameIndex.get("Count");
                        }
                        Object  countObj = rs.getObject(index+1);
                        if (countObj != null)
                        {
                            str.append(getStrValue(countObj, newFieldMetaData.get(i).getType()));
                        } else
                        {
                            str.append("NULL");
                        }

                    } else
                    {

                        Integer index = oldNameIndex.get(newFieldName);
                        if (index == null)
                        {
                            log.error("Couldn't find new field name["+newFieldName+"] in old field name in index Map");
                            //for (String key : oldNameIndex.keySet())
                            //{
                            //    log.info("["+key+"]["+oldNameIndex.get(key)+"]");
                            //}
                            stmt.close();
                            return false;
                        }
                        Object data  = rs.getObject(index+1);

                        if (data != null)
                        {
                            int idInx = newFieldName.lastIndexOf("ID");
                            if (idMapperMgr != null && idInx > -1)
                            {
                                //System.out.println(newFieldName+" "+(index.intValue()+1)+"  "+rs.getInt(index+1));

                                IdMapperIFace idMapper =  idMapperMgr.get(tableName, newFieldName);
                                if (idMapper != null)
                                {
                                    data = idMapper.get(rs.getLong(index+1));
                                } else
                                {
                                    log.error("No Map for ["+tableName+"]["+newFieldName+"]");
                                }
                            }
                        }
                        str.append(getStrValue(data, newFieldMetaData.get(i).getType()));

                    }

                }
                str.append(")");
                //log.info("\n"+str.toString());
                if (hasFrame)
                {
                    if (count % 500 == 0) 
                    {
                        setProcess(count);
                    }
                    
                } else
                {
                    if (count % 2000 == 0) 
                    {
                        log.info("CollectionObject Records: "+count);
                    }
                }

                try
                {
                    Statement updateStatement = newDBConn.createStatement();
                    updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                    updateStatement.executeUpdate(str.toString());
                    updateStatement.clearBatch();
                    updateStatement.close();
                    updateStatement = null;

                } catch (SQLException e)
                {
                    log.error("Count: "+count);
                    e.printStackTrace();
                    log.error(e);
                    rs.close();
                    stmt.close();
                    throw new RuntimeException(e);
                }

                count++;
                //if (count > 10) break;
            } while (rs.next());
            
            if (hasFrame)
            {
                setProcess(count);
            } else
            {
                log.info("Processed CollectionObject "+count+" records.");
            }
            rs.close();

            stmt.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }


        return true;
    }
    
    /**
     * @param rs
     * @param columnIndex
     * @return
     */
    protected int getIntValue(final ResultSet rs, final int columnIndex)
    {
        try
        {
            int val = rs.getInt(columnIndex);
            return rs.wasNull() ? 0 : val;
            
        } catch (Exception ex)
        {
            
        }
        return 0;
    }

    /**
     * Converts all the LoanPhysicalObjects.
     * @return true if no errors
     */
    public boolean convertLoanPhysicalObjects()
    {
        deleteAllRecordsFromTable(newDBConn, "loanphysicalobject"); // automatically closes the connection
        
        if (BasicSQLUtils.getNumRecords(oldDBConn, "loanphysicalobject") == 0)
        {
            return true;
        }
       
        try
        {
            Map<String, String> colNewToOldMap = createFieldNameMap(new String[] {"PreparationID", "PhysicalObjectID"});

            Statement     stmt = oldDBConn.createStatement();
            StringBuilder str  = new StringBuilder();

            List<String> oldFieldNames = new ArrayList<String>();

            StringBuilder sql = new StringBuilder("SELECT ");
            List<String> names = new ArrayList<String>();
            getFieldNamesFromSchema(oldDBConn, "loanphysicalobject", names);

            sql.append(buildSelectFieldList(names, "loanphysicalobject"));
            oldFieldNames.addAll(names);

            sql.append(" FROM loanphysicalobject");

            log.info(sql);

            List<BasicSQLUtils.FieldMetaData> newFieldMetaData = new ArrayList<BasicSQLUtils.FieldMetaData>();
            getFieldMetaDataFromSchema(newDBConn, "loanphysicalobject", newFieldMetaData);

            log.info("Number of Fields in New loanphysicalobject "+newFieldMetaData.size());
            String sqlStr = sql.toString();

            Map<String, Integer> oldNameIndex = new Hashtable<String, Integer>();
            int inx = 0;
            for (String name : oldFieldNames)
            {
                oldNameIndex.put(name, inx++);
            }

            String tableName = "loanphysicalobject";

            int quantityIndex         = oldNameIndex.get("Quantity") + 1;
            int quantityReturnedIndex = oldNameIndex.get("QuantityReturned") + 1;
           //int quantityResolvedIndex = oldNameIndex.get("QuantityResolved") + 1;
                       
  
            log.info(sqlStr);
            ResultSet rs = stmt.executeQuery(sqlStr);
            
            if (hasFrame)
            {
                if (rs.last())
                {
                    setProcess(0, rs.getRow()); 
                    rs.first();
                    
                } else
                {
                    rs.close();
                    stmt.close();
                    return true;
                }
            } else
            {
                if (!rs.first())
                {
                    rs.close();
                    stmt.close();
                    return true;                   
                }
            }

            int count = 0;
            do
            {
                int quantity         = getIntValue(rs, quantityIndex);
                int quantityReturned = getIntValue(rs, quantityReturnedIndex);
                //int quantityResolved = getIntValue(rs, quantityResolvedIndex);
                Boolean isResolved   = quantityReturned == quantity;
                
                str.setLength(0);
                str.append("INSERT INTO loanphysicalobject VALUES (");
                for (int i=0;i<newFieldMetaData.size();i++)
                {
                    if (i > 0) str.append(", ");

                    String newFieldName = newFieldMetaData.get(i).getName();

                    if (i == 0)
                    {
                        Integer  recId  = count+1;
                        str.append(getStrValue(recId));
                        
                    } else if (newFieldName.equals("IsResolved"))
                    {
                        str.append(BasicSQLUtils.getStrValue(isResolved)); 

                    } else
                    {
                        Integer index = null;
                        String  oldMappedColName = colNewToOldMap.get(newFieldName);
                        if (oldMappedColName != null)
                        {
                            index = oldNameIndex.get(oldMappedColName);

                        } else
                        {
                            index            = oldNameIndex.get(newFieldName);
                            oldMappedColName = newFieldName;
                        }
                        
                        if (index == null)
                        {
                            log.error("Couldn't find new field name["+newFieldName+"] in old field name in index Map");
                            stmt.close();
                            return false;
                        }
                        Object data  = rs.getObject(index+1);

                        if (data != null)
                        {
                            int idInx = newFieldName.lastIndexOf("ID");
                            if (idMapperMgr != null && idInx > -1)
                            {
                                IdMapperIFace idMapper =  idMapperMgr.get(tableName, oldMappedColName);
                                if (idMapper != null)
                                {
                                    data = idMapper.get(rs.getLong(index+1));
                                } else
                                {
                                    log.error("No Map for ["+tableName+"]["+oldMappedColName+"]");
                                }
                            }
                        }
                        str.append(getStrValue(data, newFieldMetaData.get(i).getType()));
                    }
                }
                str.append(")");
                
                if (hasFrame)
                {
                    if (count % 500 == 0) 
                    {
                        setProcess(count);
                    }
                    
                } else
                {
                    if (count % 2000 == 0) 
                    {
                        log.info("LoanPhysicalObject Records: "+count);
                    }
                }

                try
                {
                    Statement updateStatement = newDBConn.createStatement();
                    updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                    updateStatement.executeUpdate(str.toString());
                    updateStatement.clearBatch();
                    updateStatement.close();
                    updateStatement = null;

                } catch (SQLException e)
                {
                    log.error("Count: "+count);
                    e.printStackTrace();
                    log.error(e);
                    rs.close();
                    stmt.close();
                    throw new RuntimeException(e);
                }

                count++;
                //if (count > 10) break;
            } while (rs.next());
            
            if (hasFrame)
            {
                setProcess(count);
            } else
            {
                log.info("Processed LoanPhysicalObject "+count+" records.");
            }
            rs.close();

            stmt.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            throw new RuntimeException(e);
        }

        return true;

    }
    


    /**
     * Creates a Standard set of DataTypes for Collections
     * @param returnName the name of a DataType to return (ok if null)
     * @return the DataType requested
     */
    public DataType createDataTypes(final String returnName)
    {
        String[] dataTypeNames = {"Animal", "Plant", "Fungi", "Mineral", "Other"};

        DataType retDataType = null;
        try
        {
            Session session = HibernateUtil.getCurrentSession();
            HibernateUtil.beginTransaction();

            for (String name : dataTypeNames)
            {
                DataType dataType = new DataType();
                dataType.setName(name);
                dataType.setCollectionObjDef(null);
                session.save(dataType);

                if (returnName != null && name.equals(returnName))
                {
                    retDataType = dataType;
                }
            }

            HibernateUtil.commitTransaction();

        } catch (Exception e)
        {
            log.error("******* " + e);
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(e);
        }
        return retDataType;
    }

    /**
     * @param name name
     * @param dataType dataType
     * @param user user
     * @param taxaTreeDef taxaTreeDef
     * @param  catalogSeries catalogSeries
     * @return set of objects
     */
    public Set<CollectionObjDef> createCollectionObjDef(final String          name,
                                                        final DataType        dataType,
                                                        final SpecifyUser     user,
                                                        final TaxonTreeDef taxaTreeDef,
                                                        final CatalogSeries   catalogSeries)
    {
        try
        {
            Set<CatalogSeries> catalogSeriesSet = new HashSet<CatalogSeries>();
            if (catalogSeries != null)
            {
                catalogSeriesSet.add(catalogSeries);
            }

            Session session = HibernateUtil.getCurrentSession();
            HibernateUtil.beginTransaction();

            CollectionObjDef colObjDef = new CollectionObjDef();
            colObjDef.initialize();
            colObjDef.setName(name);
            colObjDef.setDataType(dataType);
            colObjDef.setSpecifyUser(user);

            colObjDef.setTaxonTreeDef(taxaTreeDef);

            colObjDef.setCatalogSeries(catalogSeriesSet);

            session.save(colObjDef);

            HashSet<CollectionObjDef> set = new HashSet<CollectionObjDef>();
            set.add(colObjDef);
            user.setCollectionObjDefs(set);
            session.saveOrUpdate(user);

            HibernateUtil.commitTransaction();


           return set;

        } catch (Exception e)
        {
            log.error("******* " + e);
            e.printStackTrace();
            HibernateUtil.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    public void convertAllTaxonTreeDefs() throws SQLException
    {
    	Statement  st   = oldDBConn.createStatement();

    	TaxonTreeDef ttd = new TaxonTreeDef();
    	ttd.initialize();

    	ResultSet rs = st.executeQuery("SELECT TaxonomyTypeID FROM taxonomytype");
    	Vector<Long> ttIds = new Vector<Long>();
    	while( rs.next() )
    	{
    		ttIds.add(rs.getLong(1));
    	}

    	for( Long id: ttIds )
    	{
    		convertTaxonTreeDefinition(id);
    	}
    }

    /**
     * Converts the taxonomy tree definition from the old taxonomicunittype
     * table to the new table pair: TaxonTreeDef & TaxonTreeDefItems.
     *
     * @param taxonomyTypeId the tree def id in taxonomicunittype
     * @return the TaxonTreeDef object
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
	public TaxonTreeDef convertTaxonTreeDefinition( long taxonomyTypeId ) throws SQLException
    {
    	Statement  st   = oldDBConn.createStatement();

    	TaxonTreeDef ttd = new TaxonTreeDef();
    	ttd.initialize();

    	ResultSet rs = st.executeQuery("SELECT TaxonomyTypeName FROM taxonomytype WHERE TaxonomyTypeID="+taxonomyTypeId);
    	rs.next();
    	String taxonomyTypeName = rs.getString(1);

    	ttd.setName(taxonomyTypeName + " taxonomy tree");
    	ttd.setRemarks("Tree converted from " + oldDBName);
        ttd.setFullNameDirection(TreeDefIface.FORWARD);

    	rs = st.executeQuery("SELECT DISTINCT RankID,RankName,RequiredParentRankID FROM taxonomicunittype WHERE TaxonomyTypeID="
    			+ taxonomyTypeId + " ORDER BY RankID");

    	int rank;
    	String name;
    	int requiredRank;

    	Vector<TaxonTreeDefItem> items = new Vector<TaxonTreeDefItem>();
    	Vector<Integer> enforcedRanks = new Vector<Integer>();

    	while( rs.next() )
    	{
    		rank = rs.getInt(1);
    		name = rs.getString(2);
    		requiredRank = rs.getInt(3);
    		System.out.println( rank + "  " + name );
    		TaxonTreeDefItem i = new TaxonTreeDefItem();
    		i.initialize();
    		i.setName(name);
            i.setFullNameSeparator(" ");
    		i.setRankId(rank);
    		i.setTreeDef(ttd);
    		ttd.getTreeDefItems().add(i);

    		// setup the parent/child relationship
    		if( items.isEmpty() )
    		{
    			i.setParent(null);
    		}
    		else
    		{
    			i.setParent(items.lastElement());
    		}
    		items.add(i);

    		enforcedRanks.add(requiredRank);
    	}

    	for( TaxonTreeDefItem i: items )
    	{
    		if( enforcedRanks.contains(i.getRankId()) )
    		{
    			i.setIsEnforced(true);
    		}
    		else
    		{
    			i.setIsEnforced(false);
    		}
    	}

    	return ttd;
    }

    public void copyTaxonTreeDefs()
    {
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "taxontreedef");
    	
    	String sql = "select * from taxonomytype where taxonomytype.TaxonomyTypeId in (SELECT DISTINCT t.TaxonomyTypeId FROM taxonname t WHERE t.RankId<> 0 ORDER BY TaxonomyTypeId)";

    	Hashtable<String,String> newToOldColMap = new Hashtable<String,String>();
    	newToOldColMap.put("TaxonTreeDefID", "TaxonomyTypeID");
    	newToOldColMap.put("Name", "TaxonomyTypeName");

    	String[] ignoredFields = {"Remarks", "FullNameDirection"};
    	BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);

    	log.info("Copying taxonomy tree definitions from 'taxonomytype' table");
    	if( !copyTable(oldDBConn,
    			newDBConn,
    			sql,
    			"taxonomytype",
    			"taxontreedef",
    			newToOldColMap,
    			null) )
    	{
    		log.error("Table 'taxonomytype' didn't copy correctly");
    	}

    	BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);
    }

    /**
     * Converts the old taxonomy records to the new schema.  In general,
     * the process is...
     * 	1. Copy all columns that don't require any modification (other than col. name)
     *  2. Set the proper values in the IsEnforced column
     *  3. Set the proper values in the ParentItemID column
     *
     * @throws SQLException
     */
    public void convertTaxonTreeDefItems() throws SQLException
    {
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "taxontreedefitem");

    	String sqlStr = "SELECT * FROM taxonomicunittype where taxonomicunittype.TaxonomyTypeID in (SELECT DISTINCT t.TaxonomyTypeId FROM taxonname t WHERE t.RankId<> 0 ORDER BY TaxonomyTypeId)";

    	Hashtable<String,String> newToOldColMap = new Hashtable<String,String>();
    	newToOldColMap.put("TaxonTreeDefItemID", "TaxonomicUnitTypeID");
    	newToOldColMap.put("Name", "RankName");
    	newToOldColMap.put("TaxonTreeDefID", "TaxonomyTypeID");

    	String[] ignoredFields = {"IsEnforced", "ParentItemID", "Remarks", "IsInFullName", "FullNameSeparator", "TextBefore", "TextAfter"};
    	BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);

    	// Copy over most of the columns in the old table to the new one
    	log.info("Copying taxonomy tree definition items from 'taxonomicunittype' table");
    	if( !copyTable(oldDBConn,
    			newDBConn,
    			sqlStr,
    			"taxonomicunittype",
    			"taxontreedefitem",
    			newToOldColMap,
    			null) )
    	{
    		log.error("Table 'taxonomicunittype' didn't copy correctly");
    	}

    	BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);

    	// JDBC Statments for use throughout process
        Statement oldDbStmt = oldDBConn.createStatement();
		Statement newDbStmt = newDBConn.createStatement();

    	// get each individual TaxonomyTypeID value
    	sqlStr = "SELECT DISTINCT TaxonomyTypeID from taxonomicunittype where taxonomicunittype.TaxonomyTypeId in (SELECT DISTINCT t.TaxonomyTypeId FROM taxonname t WHERE t.RankId<> 0 ORDER BY TaxonomyTypeId)";

		ResultSet rs = oldDbStmt.executeQuery(sqlStr);

    	Vector<Long> typeIds = new Vector<Long>();
    	while(rs.next())
    	{
    		Long typeId = rs.getLong(1);
    		if( !rs.wasNull() )
    		{
        		typeIds.add(typeId);
    		}
    	}

    	// will be used to map old TaxonomyTypeID values to TreeDefID values
		IdMapperIFace typeIdMapper = idMapperMgr.get("taxonomytype", "TaxonomyTypeID");

    	// for each value of TaxonomyType...
    	for( Long typeId: typeIds )
    	{
    		// get all of the values of RequiredParentRankID (the enforced ranks)
    		sqlStr = "SELECT DISTINCT RequiredParentRankID from taxonomicunittype WHERE TaxonomyTypeID=" + typeId;
    		rs = oldDbStmt.executeQuery(sqlStr);

    		Vector<Long> enforcedIds = new Vector<Long>();
    		while( rs.next() )
    		{
    			Long reqId = rs.getLong(1);
    			if( !rs.wasNull() )
    			{
    				enforcedIds.add(reqId);
    			}
    		}
            // make sure the root item is always enforeced
            if (!enforcedIds.contains(0L))
            {
                enforcedIds.add(0L);
            }
    		// now we have a vector of the required/enforced rank IDs
    		// fix the new DB values accordingly

    		// what is the corresponding TreeDefID?
    		long treeDefId = typeIdMapper.get(typeId);

    		StringBuilder sqlUpdate = new StringBuilder("UPDATE taxontreedefitem SET IsEnforced=TRUE WHERE TaxonTreeDefID="+treeDefId+" AND RankID IN (");
    		// add all the enforced ranks
    		for( int i = 0; i < enforcedIds.size(); ++i )
    		{
    			sqlUpdate.append(enforcedIds.get(i));
    			if( i < enforcedIds.size()-1 )
    			{
    				sqlUpdate.append(",");
    			}
    		}
    		sqlUpdate.append(")");

    		log.info(sqlUpdate);

    		int rowsUpdated = newDbStmt.executeUpdate(sqlUpdate.toString());
    		log.info(rowsUpdated + " rows updated");
    		
    		StringBuilder fullNameUpdate = new StringBuilder("UPDATE taxontreedefitem SET IsInFullName=TRUE WHERE TaxonTreeDefID="+treeDefId+" AND Name IN ('Genus','Species','Subspecies','Variety','Subvariety','Forma','Subforma')");
    		log.info(fullNameUpdate);
    		
    		rowsUpdated = newDbStmt.executeUpdate(fullNameUpdate.toString());
    		log.info(fullNameUpdate);
    	}

    	// at this point, we've set all the IsEnforced fields that need to be TRUE
    	// now we need to set the others to FALSE
    	String setToFalse = "UPDATE taxontreedefitem SET IsEnforced=FALSE WHERE IsEnforced IS NULL";
    	int rowsUpdated = newDbStmt.executeUpdate(setToFalse);
    	log.info("IsEnforced set to FALSE in " + rowsUpdated + " rows");

    	// we still need to fix the ParentItemID values to point at each row's parent

    	// we'll work with the items in sets as determined by the TreeDefID
    	for( Long typeId: typeIds )
    	{
    		long treeDefId = typeIdMapper.get(typeId);
        	sqlStr = "SELECT TaxonTreeDefItemID FROM taxontreedefitem WHERE TaxonTreeDefID="+treeDefId+" ORDER BY RankID";
        	rs = newDbStmt.executeQuery(sqlStr);

        	boolean atLeastOneRecord = rs.next();
        	if( !atLeastOneRecord )
        	{
        		continue;
        	}
        	long prevTreeDefItemId = rs.getLong(1);
        	Vector<Pair<Long,Long>> idAndParentIdPairs = new Vector<Pair<Long,Long>>();
        	while( rs.next() )
        	{
        		long treeDefItemId = rs.getLong(1);
        		idAndParentIdPairs.add(new Pair<Long,Long>(treeDefItemId,prevTreeDefItemId));
        		prevTreeDefItemId = treeDefItemId;
        	}

        	// now we have all the pairs (ID,ParentID) in a Vector of Pair objects
        	rowsUpdated = 0;
        	for( Pair<Long,Long> idPair: idAndParentIdPairs )
        	{
        		sqlStr = "UPDATE taxontreedefitem SET ParentItemID=" + idPair.second + " WHERE TaxonTreeDefItemID=" + idPair.first;
        		rowsUpdated += newDbStmt.executeUpdate(sqlStr);
        	}

        	log.info("Fixed parent pointers on " + rowsUpdated + " rows");
    	}
    }

    /**
     * 
     */
    public void copyTaxonRecords()
    {
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "taxon");

    	String sql = "SELECT * FROM taxonname where taxonname.TaxonomyTypeId in (SELECT DISTINCT t.TaxonomyTypeId FROM taxonname t WHERE t.RankId <> 0 ORDER BY TaxonomyTypeId)";

    	Hashtable<String,String> newToOldColMap = new Hashtable<String,String>();
    	newToOldColMap.put("TaxonID", "TaxonNameID");
    	newToOldColMap.put("ParentID", "ParentTaxonNameID");
    	newToOldColMap.put("TaxonTreeDefID", "TaxonomyTypeID");
    	newToOldColMap.put("TaxonTreeDefItemID", "TaxonomicUnitTypeID");
    	newToOldColMap.put("Name", "TaxonName");
    	newToOldColMap.put("FullName", "FullTaxonName");
        newToOldColMap.put("IsAccepted", "Accepted");

    	String[] ignoredFields = {"GUID", "Visibility, VisibilitySetBy"};//User/Security changes
    	//log.error(arg0)
        BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);
        
        // AcceptedID is typically NULL unless they are using synonimies
        boolean showMappingErrors = false;
        try
        {
            
            Statement stmt = oldDBConn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(AcceptedID)  FROM taxonname where AcceptedID <> null");
            if (rs.first())
            {
                showMappingErrors = rs.getInt(1) > 0;
            }
            rs.close();
            stmt.close();
            
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        BasicSQLUtils.setShowMappingError(showMappingErrors);

    	log.info("Copying taxon records from 'taxonname' table");
    	if( !copyTable(oldDBConn,
    			newDBConn,
    			sql,
    			"taxonname",
    			"taxon",
    			newToOldColMap,
    			null) )
    	{
    		log.error("Table 'taxonname' didn't copy correctly");
    	}

    	BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
	public GeographyTreeDef createStandardGeographyDefinitionAndItems()
    {
    	// empty out any pre-existing tree definitions
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "geographytreedef");
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "geographytreedefitem");
    	
    	Session session = HibernateUtil.getCurrentSession();
    	HibernateUtil.beginTransaction();

    	GeographyTreeDef def = new GeographyTreeDef();
    	def.initialize();
    	def.setName("Default Geography Definition");
    	def.setRemarks("A simple continent/country/state/county geography tree");
        def.setFullNameDirection(TreeDefIface.REVERSE);
    	//session.save(def);

		GeographyTreeDefItem planet = new GeographyTreeDefItem();
		planet.initialize();
		planet.setName("Planet");
		planet.setRankId(0);
		planet.setIsEnforced(true);
        planet.setFullNameSeparator(", ");
		//session.save(planet);

		GeographyTreeDefItem cont = new GeographyTreeDefItem();
		cont.initialize();
		cont.setName("Continent");
		cont.setRankId(100);
        cont.setFullNameSeparator(", ");
		//session.save(cont);

		GeographyTreeDefItem country = new GeographyTreeDefItem();
		country.initialize();
		country.setName("Country");
		country.setRankId(200);
		country.setIsInFullName(true);
        country.setFullNameSeparator(", ");
		//session.save(country);

		GeographyTreeDefItem state = new GeographyTreeDefItem();
		state.initialize();
		state.setName("State");
		state.setRankId(300);
		state.setIsInFullName(true);
        state.setFullNameSeparator(", ");
		//session.save(state);

		GeographyTreeDefItem county = new GeographyTreeDefItem();
		county.initialize();
		county.setName("County");
		county.setRankId(400);
		county.setIsInFullName(true);
        county.setFullNameSeparator(", ");
		//session.save(county);

		// setup parents
		county.setParent(state);
		state.setParent(country);
		country.setParent(cont);
		cont.setParent(planet);

		// set the tree def for each tree def item
		planet.setTreeDef(def);
		cont.setTreeDef(def);
		country.setTreeDef(def);
		state.setTreeDef(def);
		county.setTreeDef(def);
		
		Set defItems = def.getTreeDefItems();
		defItems.add(planet);
		defItems.add(cont);
		defItems.add(country);
		defItems.add(state);
		defItems.add(county);
        
        session.save(def);

		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();

		return def;
    }

    public boolean convertDeaccessionCollectionObject()
    {
        BasicSQLUtils.deleteAllRecordsFromTable("deaccessionpreparation");

        if (BasicSQLUtils.getNumRecords(oldDBConn, "deaccessioncollectionobject") == 0)
        {
            return true;
        }
        boolean showMappingErrors = BasicSQLUtils.isShowMappingError();
        
        BasicSQLUtils.setShowMappingError(false); // turn off notification because of errors with National Parks
        
        Map<String, String> colNewToOldMap = createFieldNameMap(new String[] {"PreparationID", "CollectionObjectID", "DeaccessionPreparationID", "DeaccessionCollectionObjectID"});        
        
        if (copyTable(oldDBConn, newDBConn, "deaccessioncollectionobject", "deaccessionpreparation", colNewToOldMap, null))
        {
            log.info("deaccessionpreparation copied ok.");
        } else
        {
            log.error("problems coverting deaccessionpreparation");
        }
        BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);
        
        BasicSQLUtils.setShowMappingError(showMappingErrors);   
        return true;
    }
    
    /**
     *
     */
    public void convertLocality()
    {
        // Ignore these field names from new table schema when mapping IDs
        BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(new String[] {"NationalParkName", "GUID"});

        BasicSQLUtils.deleteAllRecordsFromTable("locality");

        //boolean showMappingErrors = BasicSQLUtils.isShowMappingError();
        BasicSQLUtils.setShowMappingError(false); // turn off notification because of errors with National Parks

        String sql = "select locality.*, geography.* from locality,geography where locality.GeographyID = geography.GeographyID";

        if (copyTable(oldDBConn, newDBConn, sql, "locality", "locality", null, null))
        {
            log.info("Locality/Geography copied ok.");
        } else
        {
            log.error("Copying locality/geography (fields) to new Locality");
        }
        BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);
        //BasicSQLUtils.setShowMappingError(showMappingErrors);
   }
    
    /**
     * @param treeDef
     * @throws SQLException
     */
    public void convertGeography(GeographyTreeDef treeDef) throws SQLException
    {
    	// empty out any pre-existing records
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "geography");

    	// get a Hibernate session for saving the new records
    	Session session = HibernateUtil.getCurrentSession();
    	HibernateUtil.beginTransaction();
    	
    	// get all of the old records
    	String sql = "SELECT GeographyID,ContinentOrOcean,Country,State,County FROM geography";
    	Statement statement = oldDBConn.createStatement();
    	ResultSet oldGeoRecords = statement.executeQuery(sql);
        
        if (hasFrame)
        {
            if (oldGeoRecords.last())
            {
                setProcess(0, oldGeoRecords.getRow()); 
                oldGeoRecords.first();
            }
        } else
        {
            oldGeoRecords.first();
        }
    	
    	// setup the root Geography record (planet Earth)
    	Geography planetEarth = new Geography();
    	planetEarth.initialize();
    	planetEarth.setName("Earth");
    	planetEarth.setCommonName("Earth");
    	planetEarth.setRankId(0);
    	planetEarth.setDefinition(treeDef);
    	for( Object o: treeDef.getTreeDefItems() )
    	{
    		GeographyTreeDefItem defItem = (GeographyTreeDefItem)o;
    		if( defItem.getRankId() == 0 )
    		{
    			planetEarth.setDefinitionItem(defItem);
    			break;
    		}
    	}
    	GeographyTreeDefItem defItem = treeDef.getDefItemByRank(0);
    	planetEarth.setDefinitionItem(defItem);
    	session.save(planetEarth);
    	
    	// create an ID mapper for the geography table (mainly for use in converting localities)
    	IdTableMapper geoIdMapper = IdMapperMgr.getInstance().addTableMapper("geography", "GeographyID");
    	
    	int counter = 0;
    	// for each old record, convert the record
    	do
    	{
            if (counter % 500 == 0)
            {
                if (hasFrame)
                {
                    setProcess(counter);
                    
                } else
            	{
            		log.info("Converted " + counter + " geography records");
            	}
            }
            
    		// grab the important data fields from the old record
    		int oldId = oldGeoRecords.getInt(1);
        	String cont = oldGeoRecords.getString(2);
        	String country = oldGeoRecords.getString(3);
        	String state = oldGeoRecords.getString(4);
        	String county = oldGeoRecords.getString(5);

        	// create a new Geography object from the old data
        	Geography newGeo = convertOldGeoRecord(cont, country, state, county, planetEarth, session);

        	counter++;
        	
        	// add this new ID to the ID mapper
        	geoIdMapper.put(oldId, newGeo.getGeographyId());
            
    	} while (oldGeoRecords.next());
        
        if (hasFrame)
        {
            setProcess(counter);
            
        } else
        {
            log.info("Converted " + counter + " geography records");
        }
      
        planetEarth.setNodeNumber(1);
        fixNodeNumbersFromRoot(planetEarth);
        planetEarth.fixFullNameForAllDescendants();
        
    	HibernateUtil.commitTransaction();
		log.info("Converted " + counter + " geography records");

		// set up Geography foreign key mapping for locality
		idMapperMgr.mapForeignKey("Locality", "GeographyID", "Geography", "GeographyID");
    }
    
    /**
     * Using the data passed in the parameters, create a new Geography object and attach
     * it to the Geography tree rooted at geoRoot.
     * 
     * @param cont continent or ocean name
     * @param country country name
     * @param state state name
     * @param county county name
     * @param geoRoot the Geography tree root node (planet)
     * @return the lowest level Geography item represented by the passed in data
     */
    protected Geography convertOldGeoRecord(String cont,
											String country,
											String state,
											String county,
											Geography geoRoot,
											Session session)
    {
    	String levelNames[] = {cont,country,state,county};
    	int levelsToBuild = 0;
    	for( int i = 4; i > 0; --i )
    	{
    		if( levelNames[i-1] != null )
    		{
    			levelsToBuild = i;
    			break;
    		}
    	}
    	
    	Geography prevLevelGeo = geoRoot;
    	for( int i = 0; i < levelsToBuild; ++i )
    	{
    		Geography newLevelGeo = buildGeoLevel( levelNames[i], prevLevelGeo, session );
    		prevLevelGeo = newLevelGeo;
    	}
    	
    	return prevLevelGeo;
    }
    
    /**
     * @param nameArg
     * @param parentArg
     * @param sessionArg
     * @return
     */
    protected Geography buildGeoLevel( String nameArg, Geography parentArg, Session sessionArg )
    {
        String name = nameArg;
    	if( name == null )
    	{
    		name = "N/A";
    	}
    	
    	// search through all of parent's children to see if one already exists with the same name
    	Set<Geography> children = parentArg.getChildren();
    	for( Geography child: children )
    	{
    		if( name.equalsIgnoreCase(child.getName()) )
    		{
    			// this parent already has a child by the given name
    			// don't create a new one, just return this one
    			return child;
    		}
    	}
    	
    	// we didn't find a child by the given name
    	// we need to create a new Geography record
    	Geography newGeo = new Geography();
    	newGeo.initialize();
    	newGeo.setName(name);
    	newGeo.setParent(parentArg);
        parentArg.addChild(newGeo);
    	newGeo.setDefinition(parentArg.getDefinition());
    	int newGeoRank = parentArg.getRankId()+100;
    	GeographyTreeDefItem defItem = parentArg.getDefinition().getDefItemByRank(newGeoRank);
    	newGeo.setDefinitionItem(defItem);
    	newGeo.setRankId(newGeoRank);
        sessionArg.save(newGeo);
    	
    	return newGeo;
    }

    public LocationTreeDef buildSampleLocationTreeDef()
    {
    	// empty out any pre-existing tree definitions
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "locationtreedef");
    	BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "locationtreedefitem");

    	log.info("Creating a sample location tree definition");
    	
    	Session session = HibernateUtil.getCurrentSession();
    	HibernateUtil.beginTransaction();
    	
    	LocationTreeDef locDef = TreeFactory.createStdLocationTreeDef("Sample location tree", null);
    	locDef.setRemarks("This definition is merely for demonstration purposes.  Consult documentation or support staff for instructions on creating one tailored for an institutions specific needs.");
        locDef.setFullNameDirection(TreeDefIface.FORWARD);
    	session.save(locDef);
    	
    	// get the root def item
    	LocationTreeDefItem rootItem = locDef.getTreeDefItems().iterator().next();
        rootItem.setFullNameSeparator(", ");
    	session.save(rootItem);
    	
    	Location rootNode = rootItem.getTreeEntries().iterator().next();
    	session.save(rootNode);
    	
    	LocationTreeDefItem building = new LocationTreeDefItem();
    	building.initialize();
    	building.setRankId(100);
    	building.setName("Building");
    	building.setIsEnforced(false);
    	building.setIsInFullName(false);
    	building.setTreeDef(locDef);
        building.setFullNameSeparator(", ");
    	session.save(building);

    	LocationTreeDefItem room = new LocationTreeDefItem();
    	room.initialize();
    	room.setRankId(200);
    	room.setName("Room");
    	room.setIsEnforced(true);
    	room.setIsInFullName(true);
    	room.setTreeDef(locDef);
        room.setFullNameSeparator(", ");
    	session.save(room);
    	
    	LocationTreeDefItem freezer = new LocationTreeDefItem();
    	freezer.initialize();
    	freezer.setRankId(300);
    	freezer.setName("Freezer");
    	freezer.setIsEnforced(true);
    	freezer.setIsInFullName(true);
    	freezer.setTreeDef(locDef);
        freezer.setFullNameSeparator(", ");
    	session.save(freezer);
    	
    	rootItem.setChild(building);
        building.setParent(rootItem);
    	building.setChild(room);
        room.setParent(building);
    	room.setChild(freezer);
        freezer.setParent(room);
    	
    	locDef.addTreeDefItem(building);
    	locDef.addTreeDefItem(room);
    	locDef.addTreeDefItem(freezer);
    	
		HibernateUtil.commitTransaction();
		HibernateUtil.closeSession();

    	return locDef;
    }
    
    /**
     * Walks the old GTP records and creates a GTP tree def and items
     * based on the ranks and rank names found in the old records
     * 
     * @return the new tree def
     * @throws SQLException on any error while contacting the old database
     */
    public GeologicTimePeriodTreeDef convertGTPDefAndItems() throws SQLException
    {
    	BasicSQLUtils.deleteAllRecordsFromTable("geologictimeperiodtreedef");
    	BasicSQLUtils.deleteAllRecordsFromTable("geologictimeperiodtreedefitem");
    	
    	log.info("Inferring geologic time period definition from old records");
    	int count = 0;

    	// get all of the old records
    	String sql = "SELECT RankCode, RankName from geologictimeperiod";
    	Statement statement = oldDBConn.createStatement();
    	ResultSet oldGtpRecords = statement.executeQuery(sql);

    	Session session = HibernateUtil.getCurrentSession();
    	HibernateUtil.beginTransaction();

    	GeologicTimePeriodTreeDef def = new GeologicTimePeriodTreeDef();
    	def.initialize();
    	def.setName("Inferred Geologic Time Period Definition");
    	def.setRemarks("");
        def.setFullNameDirection(TreeDefIface.REVERSE);
    	session.save(def);
    	
    	Vector<GeologicTimePeriodTreeDefItem> newItems = new Vector<GeologicTimePeriodTreeDefItem>();
    	
    	GeologicTimePeriodTreeDefItem rootItem = addGtpDefItem(0, "Time Root", def);
    	rootItem.setIsEnforced(true);
        rootItem.setIsInFullName(false);
        rootItem.setFullNameSeparator(", ");
    	session.save(rootItem);
    	newItems.add(rootItem);
    	++count;
    	
    	while( oldGtpRecords.next() )
    	{
    		// we're modifying the rank since the originals were 1,2,3,...
    		// to make them 100, 200, 300, ... (more like the other trees)
    		Integer rankCode = oldGtpRecords.getInt(1) * 100;
    		String rankName  = oldGtpRecords.getString(2);
    		GeologicTimePeriodTreeDefItem newItem = addGtpDefItem(rankCode, rankName, def);
    		if( newItem != null )
    		{
                newItem.setFullNameSeparator(", ");
    			session.save(newItem);
    			newItems.add(newItem);
    		}
    		if( ++count % 1000 == 0 )
    		{
    	    	log.info(count + " geologic time period records processed");
    		}
    	}
    	
    	// sort the vector to put them in parent/child order
    	Comparator<GeologicTimePeriodTreeDefItem> itemComparator = new Comparator<GeologicTimePeriodTreeDefItem>()
    	{
    		public int compare(GeologicTimePeriodTreeDefItem o1, GeologicTimePeriodTreeDefItem o2)
    		{
    			return o1.getRankId().compareTo(o2.getRankId());
    		}
    	};
    	Collections.sort(newItems, itemComparator);
    	
    	// set the parent/child pointers
    	for( int i = 0; i < newItems.size()-1; ++i )
    	{
    		newItems.get(i).setChild(newItems.get(i+1));
    	}
    	
    	HibernateUtil.commitTransaction();
    	HibernateUtil.closeSession();
    	
    	log.info("Finished inferring GTP tree definition and items");
    	return def;
    }
    
    /**
     * Creates a new GTP def item if one with the same rank doesn't exist.
     * 
     * @param rankCode the rank of the new item
     * @param rankName the name of the new item
     * @param def the def to which the item is attached
     * @return the new item, or null if one already exists with this rank
     */
    protected GeologicTimePeriodTreeDefItem addGtpDefItem( Integer rankCode, String rankName, GeologicTimePeriodTreeDef def )
    {
    	// check to see if this item already exists
    	for( Object o: def.getTreeDefItems() )
    	{
    		GeologicTimePeriodTreeDefItem item = (GeologicTimePeriodTreeDefItem)o;
    		if( item.getRankId().equals(rankCode) )
    		{
    			return null;
    		}
    	}
    	
    	//create a new item
    	GeologicTimePeriodTreeDefItem item = new GeologicTimePeriodTreeDefItem();
    	item.initialize();
    	item.setRankId(rankCode);
    	item.setName(rankName);
    	def.addTreeDefItem(item);
    	return item;
    }

    public void convertGTP( GeologicTimePeriodTreeDef treeDef ) throws SQLException
    {
    	BasicSQLUtils.deleteAllRecordsFromTable("geologictimeperiod");
    	
    	log.info("Converting old geologic time period records");
    	int count = 0;
    	
    	IdTableMapper gtpIdMapper = IdMapperMgr.getInstance().addTableMapper("geologictimeperiod", "GeologicTimePeriodID");
    	
    	String sql = "SELECT g.GeologicTimePeriodID,g.RankCode,g.Name,g.Standard,g.Remarks,g.TimestampModified,g.TimestampCreated,p1.Age as Upper,p1.AgeUncertainty as UpperUncertainty,p2.Age as Lower,p2.AgeUncertainty as LowerUncertainty FROM geologictimeperiod g, geologictimeboundary p1, geologictimeboundary p2 WHERE g.UpperBoundaryID=p1.GeologicTimeBoundaryID AND g.LowerBoundaryID=p2.GeologicTimeBoundaryID ORDER BY Lower DESC, RankCode";
    	Statement statement = oldDBConn.createStatement();
    	ResultSet rs = statement.executeQuery(sql);

    	Session session = HibernateUtil.getCurrentSession();
    	HibernateUtil.beginTransaction();
    	
    	Vector<GeologicTimePeriod> newItems = new Vector<GeologicTimePeriod>();
    	
    	GeologicTimePeriod allTime = new GeologicTimePeriod();
    	allTime.initialize();
    	allTime.setDefinition(treeDef);
    	GeologicTimePeriodTreeDefItem rootDefItem = treeDef.getDefItemByRank(0);
		allTime.setDefinitionItem(rootDefItem);
    	allTime.setRankId(0);
    	allTime.setName("Time");
        allTime.setFullName("Time");
    	allTime.setStart(100000f);
    	allTime.setEnd(0f);
    	allTime.setEndUncertainty(0f);
    	Date now = Calendar.getInstance().getTime();
    	allTime.setTimestampCreated(now);
    	allTime.setTimestampModified(now);
    	session.save(allTime);
    	++count;
    	newItems.add(allTime);
    	
    	while( rs.next() )
    	{
    		Integer id   = rs.getInt(1);
    		Integer rank = rs.getInt(2) * 100;
    		String name  = rs.getString(3);
    		String std   = rs.getString(4);
    		String rem   = rs.getString(5);
    		Date modT    = rs.getDate(6);
    		Date creT    = rs.getDate(7);
    		Float upper  = rs.getFloat(8);
    		Float uError = (Float)rs.getObject(9);
    		Float lower  = rs.getFloat(10);
    		Float lError = (Float)rs.getObject(11);
            

            if (modT == null && creT == null)
            {
                creT = Calendar.getInstance().getTime();
                modT = Calendar.getInstance().getTime();
                
            } else if (modT == null && creT != null)
            {
                modT = new Date(creT.getTime());
            } else
            {
                creT = new Date(modT != null ? modT.getTime() : (new Date()).getTime());
            }

    		
    		GeologicTimePeriod gtp = new GeologicTimePeriod();
    		gtp.initialize();
    		gtp.setName(name);
            gtp.setFullName(name);
    		GeologicTimePeriodTreeDefItem defItem = treeDef.getDefItemByRank(rank);
    		gtp.setDefinitionItem(defItem);
    		gtp.setRankId(rank);
    		gtp.setDefinition(treeDef);
    		gtp.setStart(lower);
    		gtp.setStartUncertainty(lError);
    		gtp.setEnd(upper);
    		gtp.setEndUncertainty(uError);
    		gtp.setStandard(std);
    		gtp.setRemarks(rem);
    		gtp.setTimestampCreated(creT);
    		gtp.setTimestampModified(modT);

    		
    		session.save(gtp);

    		newItems.add(gtp);
    		
    		gtpIdMapper.put(id, gtp.getGeologicTimePeriodId());
    		
    		if( ++count % 1000 == 0 )
    		{
    	    	log.info(count + " geologic time period records converted");
    		}
    	}
    	
    	// TODO: fix parent pointers
    	// now we need to fix the parent/pointers
    	for( int i = 0; i < newItems.size(); ++i )
    	{
    		GeologicTimePeriod gtp = newItems.get(i);
    		for( int j = 0; j < newItems.size(); ++j )
    		{
    			GeologicTimePeriod child = newItems.get(j);
    			if( isParentChildPair(gtp, child) )
    			{
    				gtp.addChild(child);
    			}
    		}
    	}
    	
    	// TODO: fix node number, child node number stuff
    	allTime.setNodeNumber(1);
    	fixNodeNumbersFromRoot(allTime);
    	allTime.fixFullNameForAllDescendants();
        
    	HibernateUtil.commitTransaction();
    	HibernateUtil.closeSession();
    	
    	log.info(count + " geologic time period records converted");
    }
    
	/**
	 * Regenerates all nodeNumber and highestChildNodeNumber field values for all
	 * nodes attached to the given root.  The nodeNumber field of the given root
	 * must already be set.
	 * 
	 * @param root the top of the tree to be renumbered
	 * @return the highest node number value present in the subtree rooted at <code>root</code>
	 */
	public static <T extends Treeable<T,?,?>> int fixNodeNumbersFromRoot( T root )
	{
		int nextNodeNumber = root.getNodeNumber();
		for( T child: root.getChildren() )
		{
			child.setNodeNumber(++nextNodeNumber);
			nextNodeNumber = fixNodeNumbersFromRoot(child);
		}
		root.setHighestChildNodeNumber(nextNodeNumber);
		return nextNodeNumber;
	}
    
    protected boolean isParentChildPair( GeologicTimePeriod parent, GeologicTimePeriod child )
    {
    	if( parent == child )
    	{
    		return false;
    	}
    	
    	Float startParent = parent.getStart();
    	Float endParent   = parent.getEnd();
    	
    	Float startChild  = child.getStart();
    	Float endChild    = child.getEnd();
    	
    	// remember, the numbers represent MYA (millions of yrs AGO)
    	// so the logic seems a little backwards
    	if( startParent >= startChild && endParent <= endChild && parent.getRankId() < child.getRankId() )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    /**
	 * Copies the filed names to the list and prepend the table name
	 * 
	 * @param list
	 *            the destination list
	 * @param fieldNames
	 *            the list of field names
	 * @param tableName
	 *            the table name
	 */
    protected void addNamesWithTableName(final List<String> list, final List<String> fieldNames, final String tableName)
    {
        for (String fldName : fieldNames)
        {
            list.add(tableName+"."+fldName);
        }
    }

    /**
     * @param rsmd
     * @param map
     * @param tableNames
     * @throws SQLException
     */
    protected void buildIndexMapFromMetaData(final ResultSetMetaData rsmd,
                                             final Hashtable<String, Integer> map,
                                             final String[] tableNames) throws SQLException
    {
        map.clear();

        // Find the missing table name by figuring our which one isn't used.
        Hashtable<String, Boolean> existsMap = new  Hashtable<String, Boolean>();
        for (String tblName : tableNames)
        {
            existsMap.put(tblName, true);
            System.out.println("["+tblName+"]");
        }
        for (int i=1;i<=rsmd.getColumnCount();i++)
        {
            String tableName = rsmd.getTableName(i);
//          log.info("["+tableName+"]");
            if (StringUtils.isNotEmpty(tableName))
            {
                if (existsMap.get(tableName) != null)
                {
                    existsMap.remove(tableName);
                    log.info("Removing Table Name["+tableName+"]");
                }
            }
        }

        String missingTableName = null;
        if (existsMap.size() == 1)
        {
            missingTableName = existsMap.keys().nextElement();
            log.info("Missing Table Name["+missingTableName+"]");

        } else if (existsMap.size() > 1)
        {
            throw new RuntimeException("ExistsMap cannot have more than one name in it!");
        } else
        {
            log.info("No Missing Table Names.");
        }



        for (int i=1;i<=rsmd.getColumnCount();i++)
        {
            strBuf.setLength(0);
            String tableName = rsmd.getTableName(i);
            strBuf.append(StringUtils.isNotEmpty(tableName) ? tableName : missingTableName);
            strBuf.append(".");
            strBuf.append(rsmd.getColumnName(i));
            map.put(strBuf.toString(), i);
        }
    }

    /**
     * @param rsmd
     * @param map
     * @param tableNames
     * @throws SQLException
     */
    protected void buildIndexMapFromMetaData(final ResultSetMetaData rsmd,
                                             final List<String>      origList,
                                             final Hashtable<String, Integer> map) throws SQLException
    {
        map.clear();

        StringBuilder sb = new StringBuilder();
        for (int i=1;i<=rsmd.getColumnCount();i++)
        {
            sb.setLength(0);
            String tableName = rsmd.getTableName(i);
            String fieldName = rsmd.getColumnName(i);

            if (StringUtils.isNotEmpty(tableName))
            {
                sb.append(tableName);
            } else
            {
                for (String fullName : origList)
                {
                    String[] parts = StringUtils.split(fullName, ".");
                    if (parts[1].equals(fieldName))
                    {
                        sb.append(parts[0]);
                        break;
                    }
                }
            }
            sb.append(".");
            sb.append(fieldName);
            //log.info("["+strBuf.toString()+"] "+i);
            map.put(sb.toString(), i);
        }
    }

  /**
   * This conversion method is a little wacky in that we must convert it in three parts. And instead of factoring out
   * a way to do the 3 parts, I just duplicated the code.
   */
  public boolean convertAgents() throws SQLException
  {

      // Create the mappers here, but fill them in during the AgentAddress Process
      IdMapperIFace agentIDMapper     = idMapperMgr.addTableMapper("agent", "AgentID");
      IdMapperIFace addrIDMapper      = idMapperMgr.addTableMapper("address", "AddressID");
      IdMapperIFace agentAddrIDMapper = idMapperMgr.addTableMapper("agentaddress", "AgentAddressID");
      

       BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "agent");
       BasicSQLUtils.deleteAllRecordsFromTable(newDBConn, "address");

       // Just like in the conversion of the CollectionObjects we
       // need to build up our own select clause because the MetaData of columns names returned from
       // a query doesn't include the table names for all columns, this is far more predictable
       List<String> oldFieldNames = new ArrayList<String>();

       StringBuilder sql = new StringBuilder("select ");
       List<String> agentAddrFieldNames = new ArrayList<String>();
       getFieldNamesFromSchema(oldDBConn, "agentaddress", agentAddrFieldNames);
       sql.append(buildSelectFieldList(agentAddrFieldNames, "agentaddress"));
       sql.append(", ");
       addNamesWithTableName(oldFieldNames, agentAddrFieldNames, "agentaddress");

       List<String> agentFieldNames = new ArrayList<String>();
       getFieldNamesFromSchema(oldDBConn, "agent", agentFieldNames);
       sql.append(buildSelectFieldList(agentFieldNames, "agent"));
       sql.append(", ");
       addNamesWithTableName(oldFieldNames, agentFieldNames, "agent");

       List<String> addrFieldNames = new ArrayList<String>();
       getFieldNamesFromSchema(oldDBConn, "address", addrFieldNames);
       sql.append(buildSelectFieldList(addrFieldNames, "address"));
       addNamesWithTableName(oldFieldNames, addrFieldNames, "address");

       // Create a Map from the full table/fieldname to the index in the resultset (start at 1 not zero)
       Hashtable<String, Integer> indexFromNameMap = new Hashtable<String, Integer>();

       sql.append(" From agent Inner Join agentaddress ON agentaddress.AgentID = agent.AgentID Inner Join address ON agentaddress.AddressID = address.AddressID Order By agentaddress.AgentAddressID Asc");

       // These represent the New columns of Agent Table
       // So the order of the names are for the new table
       // the names reference the old table
       String[] agentColumns = {
               "agent.AgentID",
               "agent.TimestampModified",
               "agent.AgentType",
               "agentaddress.JobTitle",
               "agent.FirstName",
               "agent.LastName",
               "agent.MiddleInitial",
               "agent.Title",
               "agent.Interests",
               "agent.Abbreviation",
               "agent.Name",
               "agentaddress.Email",
               "agentaddress.URL",
               "agent.Remarks",
               "agent.TimestampCreated",
               "agent.LastEditedBy",
               "agent.Visibility",
               "agent.VisibilitySetBy",//User/Security changes
               "agent.ParentOrganizationID"};

       // See comments for agent Columns
       String[] addressColumns = {"address.AddressID",
                                   "address.TimestampModified",
                                    "address.Address",
                                    "address.Address2",
                                    "address.City",
                                    "address.State",
                                    "address.Country",
                                    "address.Postalcode",
                                    "address.Remarks",
                                    "address.TimestampCreated",
                                    "address.LastEditedBy",
                                    "agentaddress.IsCurrent",
                                    "agentaddress.Phone1",
                                    "agentaddress.Phone2",
                                    "agentaddress.Fax",
                                    "agentaddress.RoomOrBuilding",
                                    "address.AgentID"};

       Hashtable<Long, Long> agentTracker = new Hashtable<Long, Long>();
       Hashtable<Long, Long> addressTracker = new Hashtable<Long, Long>();
       Hashtable<Long, Long> oldAddrIds = new Hashtable<Long, Long>();
       Hashtable<Long, Long> oldAgentIds = new Hashtable<Long, Long>();

       // Create a Hashtable to track which IDs have been handled during the conversion process
       try
       {
           Statement stmtX = oldDBConn.createStatement();
           ResultSet rsX   = stmtX.executeQuery("select AddressID from address order by AddressID");
           
           int cnt = 0;
           if (rsX.last())
           {
               setProcess(0, rsX.getRow()); 
               rsX.first();
           }
           
           do
           {
               long addrId = rsX.getLong(1);
               oldAddrIds.put(addrId, 0L);
               
               if (cnt % 100 == 0)
               {
                  setProcess(0, cnt); 
               }
               cnt++;
           } while (rsX.next());
           
           rsX.close();
           stmtX.close();
           
           setProcess(0, 0); 
           
           stmtX = oldDBConn.createStatement();
           rsX   = stmtX.executeQuery("select AgentID from agent order by AgentID");
           
           cnt = 0;
           if (rsX.last())
           {
               setProcess(0, rsX.getRow()); 
               rsX.first();
           }

           do
           {
               long agentId = rsX.getLong(1);
               oldAgentIds.put(agentId, 0L);
               if (cnt % 100 == 0)
               {
                  setProcess(0, cnt); 
               }
               cnt++;
           } while (rsX.next());
           
           rsX.close();
           stmtX.close();
           
           setProcess(0, 0); 
           
           //////////////////////////////////////////////////////////////////////////////////
           // This does the part of AgentAddress where it has both an Address AND an Agent
           //////////////////////////////////////////////////////////////////////////////////

           log.info(sql.toString());

           Statement           stmt      = oldDBConn.createStatement();
           ResultSet           rs        = stmt.executeQuery(sql.toString());

           // Create Map of column name to column index number
           int inx = 1;
           for (String fldName : oldFieldNames)
           {
               //log.info("["+fldName+"] "+inx+" ["+rsmd.getColumnName(inx)+"]");
               indexFromNameMap.put(fldName, inx++);
           }

           // Figure out certain icolumn indexes we will need ater
           long agentIdInx = indexFromNameMap.get("agent.AgentID");
           long addrIdInx  = indexFromNameMap.get("address.AddressID");
           long agentTypeInx  = indexFromNameMap.get("agent.AgentType");

           long newAgentId = 1;
           long newAddrId  = 1;

           long recordCnt = 0;
           while (rs.next())
           {
               byte agentType     = rs.getByte((int)agentTypeInx);
               long agentAddressId = rs.getLong(1);
               long agentId        = rs.getLong((int)agentIdInx);
               long addrId         = rs.getLong((int)addrIdInx);

               recordCnt++;
               long currentNewAgentId = newAgentId;

               // Because of the old DB relationships we want to make sure we only add each agent in one time
               boolean alreadyInserted = agentTracker.get(agentId) != null;
               if (!alreadyInserted)
               {
                   // Create Agent
                    StringBuilder sqlStr = new StringBuilder("INSERT INTO agent VALUES (");
                    for (int i=0;i<agentColumns.length;i++)
                    {
                        //log.info(agentColumns[i]);

                        if (i > 0) sqlStr.append(",");
                        //log.info(agentColumns[i]);
                        if (agentColumns[i].equals("agent.AgentType"))
                        {
                           int x = 0;
                           x++;
                        }
                        if (i == 0)
                        {
                            sqlStr.append(newAgentId);

                        } else if (agentColumns[i].equals("agent.Name"))
                        {
                            if (agentType == 1) // when it is an individual, clear the name field
                            {
                                sqlStr.append("null");
                            } else
                            {
                                inx = indexFromNameMap.get(agentColumns[i]);
                                sqlStr.append(BasicSQLUtils.getStrValue(rs.getObject(inx)));
                            }

                        } 
                        else if(agentColumns[i].equals("agent.Visibility"))//User/Security changes
                        {
                            sqlStr.append(defaultVisibilityLevel);
                        }
                        else if(agentColumns[i].equals("agent.VisibilitySetBy"))//User/Security changes
                        {
                            sqlStr.append("null");
                        }else
                        {
                            inx = indexFromNameMap.get(agentColumns[i]);
                            sqlStr.append(BasicSQLUtils.getStrValue(rs.getObject(inx)));
                        }
                    }
                    sqlStr.append(")");

                    try
                    {
                        Statement updateStatement = newDBConn.createStatement();
                        updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                        if (false)
                        {
                            log.info(sqlStr.toString());
                        }
                        updateStatement.executeUpdate(sqlStr.toString());
                        updateStatement.clearBatch();
                        updateStatement.close();
                        updateStatement = null;

                        // Here we add to the agent tracker a mapping from the Old ID to the New Id
                        agentTracker.put(agentId, newAgentId);
                        oldAgentIds.put(agentId, 1L);
                        // Tell the IDMapper about the mapping from NewID to the Old
                        agentIDMapper.put(agentId, newAgentId);
                        // Now tell the AgentAddress Mapper the New ID to the Old AgentAddressID
                        agentAddrIDMapper.put(agentAddressId, newAgentId);

                        newAgentId++;

                    } catch (SQLException e)
                    {
                        log.error(sqlStr.toString());
                        log.error("Count: "+recordCnt);
                        e.printStackTrace();
                        log.error(e);
                        throw new RuntimeException(e);
                    }

               } else
               {
                   log.info("Agent already Used ["+BasicSQLUtils.getStrValue(rs.getObject(indexFromNameMap.get("agent.LastName")))+"]");

                   long newAID = agentTracker.get(agentId);
                   agentAddrIDMapper.put(agentAddressId, newAID);
                   currentNewAgentId = newAID;
               }

               // Now make sure we only add an address in one
               boolean alreadyInsertedAddr = addressTracker.get(addrId) != null;
               if (!alreadyInsertedAddr)
               {
                   StringBuilder sqlStr = new StringBuilder("INSERT INTO address VALUES (");
                   for (int i=0;i<addressColumns.length;i++)
                   {
                       if (i > 0) sqlStr.append(",");
                       if (i == addressColumns.length-1)
                       {
                           sqlStr.append(currentNewAgentId);

                       } else
                       {
                           Integer inxInt = indexFromNameMap.get(addressColumns[i]);
                           String value;
                           if (i == 0)
                           {
                               value = Long.toString(newAddrId);

                           } else if (inxInt == null && addressColumns[i].equals("address.Address2"))
                           {
                               value = "''";

                           } else if (addressColumns[i].equals("agentaddress.IsCurrent"))
                           {
                               value = rs.getInt(inxInt) == 0 ? "0" : "1"; // mapping a boolean

                           } else if (addressColumns[i].equals("address.Address"))
                           {
                               value = BasicSQLUtils.getStrValue(StringEscapeUtils.escapeJava(rs.getString(inxInt)));

                           } else
                           {
                               //log.info(addressColumns[i]);
                               value = BasicSQLUtils.getStrValue(rs.getObject(inxInt));
                           }
                           sqlStr.append(value);
                       }
                   }
                   sqlStr.append(")");

                   try
                   {
                       Statement updateStatement = newDBConn.createStatement();
                       updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                       if (false)
                       {
                           log.info(sqlStr.toString());
                       }
                       updateStatement.executeUpdate(sqlStr.toString());
                       updateStatement.clearBatch();
                       updateStatement.close();
                       updateStatement = null;

                       addressTracker.put(addrId, newAddrId);
                       oldAddrIds.put(addrId, 1L);

                       addrIDMapper.put(addrId, newAddrId);
                       newAddrId++;

                   } catch (SQLException e)
                   {
                       log.error(sqlStr.toString());
                       log.error("Count: "+recordCnt);
                       e.printStackTrace();
                       log.error(e);
                       throw new RuntimeException(e);
                   }
               }

               if (recordCnt % 250 == 0)
               {
                   log.info("AgentAddress Records: "+ recordCnt);
               }
           } // while

           log.info("AgentAddress Records: "+ recordCnt);
           rs.close();
           stmt.close();


           //////////////////////////////////////////////////////////////////////////////////
           // This does the part of AgentAddress where it has JUST an Address
           //////////////////////////////////////////////////////////////////////////////////
           log.info("******** Doing AgentAddress JUST Address");

           sql.setLength(0);
           sql.append("select ");
           sql.append(buildSelectFieldList(agentAddrFieldNames, "agentaddress"));
           sql.append(", ");

           getFieldNamesFromSchema(oldDBConn, "address", addrFieldNames);
           sql.append(buildSelectFieldList(addrFieldNames, "address"));

           sql.append(" From address Inner Join agentaddress ON agentaddress.AddressID = address.AddressID Order By agentaddress.AgentAddressID Asc");

           stmt = oldDBConn.createStatement();
           rs   = stmt.executeQuery(sql.toString());

           oldFieldNames.clear();
           addNamesWithTableName(oldFieldNames, agentAddrFieldNames, "agentaddress");
           addNamesWithTableName(oldFieldNames, addrFieldNames, "address");

           indexFromNameMap.clear();
           inx = 1;
           for (String fldName : oldFieldNames)
           {
               //log.info("["+fldName+"] "+inx+" ["+rsmd.getColumnName(inx)+"]");
               indexFromNameMap.put(fldName, inx++);
           }

           addrIdInx  = indexFromNameMap.get("address.AddressID");

           int newRecordsAdded = 0;
           recordCnt = 0;
           while (rs.next())
           {
               //int agentAddressId = rs.getInt(1);
               long addrId         = rs.getLong((int)addrIdInx);

               recordCnt++;

               // Create Address
               boolean alreadyInsertedAddr = addressTracker.get(addrId) != null;
               if (!alreadyInsertedAddr)
               {
                   StringBuilder sqlStr = new StringBuilder("INSERT INTO address VALUES (");
                   for (int i=0;i<addressColumns.length;i++)
                   {
                       if (i > 0) sqlStr.append(",");
                       if (i == addressColumns.length-1)
                       {
                           sqlStr.append("NULL");

                       } else
                       {

                           Integer inxInt = indexFromNameMap.get(addressColumns[i]);
                           String value;
                           if (i == 0)
                           {
                               value = Long.toString(newAddrId);

                           } else if (inxInt == null && addressColumns[i].equals("address.Address2"))
                           {
                               value = "''";

                           } else if (addressColumns[i].equals("agentaddress.IsCurrent"))
                           {
                               value = rs.getInt(inxInt) == 0 ? "0" : "1";

                           } else if (addressColumns[i].equals("address.Address"))
                           {
                               value = BasicSQLUtils.getStrValue(StringEscapeUtils.escapeJava(rs.getString(inxInt)));

                           } else
                           {
                               //log.info(addressColumns[i]);
                               value = BasicSQLUtils.getStrValue(rs.getObject(inxInt));
                           }
                           sqlStr.append(value);
                       }
                   }
                   sqlStr.append(")");

                   try
                   {
                       Statement updateStatement = newDBConn.createStatement();
                       updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                       if (false)
                       {
                           log.info(sqlStr.toString());
                       }
                       updateStatement.executeUpdate(sqlStr.toString());
                       updateStatement.clearBatch();
                       updateStatement.close();
                       updateStatement = null;

                       addressTracker.put(addrId, newAddrId);
                       oldAddrIds.put(addrId, 1L);

                       addrIDMapper.put(addrId, newAddrId);
                       newAddrId++;

                       newRecordsAdded++;

                   } catch (SQLException e)
                   {
                       log.error(sqlStr.toString());
                       log.error("Count: "+recordCnt);
                       e.printStackTrace();
                       log.error(e);
                       throw new RuntimeException(e);
                   }
               }

               if (recordCnt % 250 == 0)
               {
                   log.info("AgentAddress (Address Only) Records: "+ recordCnt+"  newRecordsAdded "+newRecordsAdded);
               }
           } // while
           log.info("AgentAddress (Address Only) Records: "+ recordCnt);
           rs.close();
           stmt.close();

           //////////////////////////////////////////////////////////////////////////////////
           // This does the part of AgentAddress where it has JUST Agent
           //////////////////////////////////////////////////////////////////////////////////
           log.info("******** Doing AgentAddress JUST Agent");

           newRecordsAdded = 0;

           sql.setLength(0);
           sql.append("select ");
           sql.append(buildSelectFieldList(agentAddrFieldNames, "agentaddress"));
           sql.append(", ");

           getFieldNamesFromSchema(oldDBConn, "agent", agentFieldNames);
           sql.append(buildSelectFieldList(agentFieldNames, "agent"));

           sql.append(" From agent Inner Join agentaddress ON agentaddress.AgentID = agent.AgentID Order By agentaddress.AgentAddressID Asc");

           stmt = oldDBConn.createStatement();
           rs   = stmt.executeQuery(sql.toString());

           oldFieldNames.clear();
           addNamesWithTableName(oldFieldNames, agentAddrFieldNames, "agentaddress");
           addNamesWithTableName(oldFieldNames, agentFieldNames, "agent");

           indexFromNameMap.clear();
           inx = 1;
           for (String fldName : oldFieldNames)
           {
               //log.info("["+fldName+"] "+inx+" ["+rsmd.getColumnName(inx)+"]");
               indexFromNameMap.put(fldName, inx++);
           }

           agentIdInx = indexFromNameMap.get("agent.AgentID");

           recordCnt = 0;
           while (rs.next())
           {
               long agentAddressId = rs.getLong(1);
               long agentId        = rs.getLong((int)agentIdInx);

               recordCnt++;

               boolean alreadyInserted = agentTracker.get(agentId) != null;
               if (!alreadyInserted)
               {
                   // Create Agent
                    StringBuilder sqlStr = new StringBuilder("INSERT INTO agent VALUES (");
                    for (int i=0;i<agentColumns.length;i++)
                    {
                        if (i > 0) sqlStr.append(",");
                        if (i == 0)
                        {
                            sqlStr.append(newAgentId);


                        }else if(agentColumns[i].equals("agent.Visibility"))//User/Security changes
                        {
                            sqlStr.append(defaultVisibilityLevel);
                        }else if(agentColumns[i].equals("agent.VisibilitySetBy"))//User/Security changes
                        {
                            sqlStr.append("null");
                        }else
                        {
                            inx = indexFromNameMap.get(agentColumns[i]);
                            sqlStr.append(BasicSQLUtils.getStrValue(rs.getObject(inx)));
                        }
                    }
                    sqlStr.append(")");

                    try
                    {
                        Statement updateStatement = newDBConn.createStatement();
                        updateStatement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
                        if (false)
                        {
                            log.info(sqlStr.toString());
                        }
                        updateStatement.executeUpdate(sqlStr.toString());
                        updateStatement.clearBatch();
                        updateStatement.close();
                        updateStatement = null;

                        agentTracker.put(agentId, newAgentId);
                        oldAgentIds.put(agentId, 1L);
                        agentIDMapper.put(agentId, newAgentId);

                        agentAddrIDMapper.put(agentAddressId, newAgentId);

                        newAgentId++;

                        newRecordsAdded++;

                    } catch (SQLException e)
                    {
                        log.error(sqlStr.toString());
                        log.error("Count: "+recordCnt);
                        e.printStackTrace();
                        log.error(e);
                        throw new RuntimeException(e);
                    }

               }

               if (recordCnt % 250 == 0)
               {
                   log.info("AgentAddress (Agent Only) Records: "+ recordCnt);
               }
           } // while
           log.info("AgentAddress (Agent Only) Records: "+ recordCnt+"  newRecordsAdded "+newRecordsAdded);

           rs.close();
           stmt.close();


           if (oldAddrIds.size() > 0)
           {
               //log.info("Address Record IDs not used by AgentAddress:");

               StringBuilder sqlStr = new StringBuilder("select ");
               List<String> names = new ArrayList<String>();
               getFieldNamesFromSchema(oldDBConn, "address", names);
               sqlStr.append(buildSelectFieldList(names, "address"));
               sqlStr.append(" from address where AddressId in (");

               cnt = 0;
               for (Enumeration<Long> e=oldAddrIds.keys();e.hasMoreElements();)
               {

                   Long id = e.nextElement();
                   Long val = oldAddrIds.get(id);
                   if (val == 0)
                   {
                       addrIDMapper.put(id, newAddrId);
                       newAddrId++;

                       if (cnt > 0) sqlStr.append(",");
                       sqlStr.append(id);
                       cnt++;
                   }
               }
               sqlStr.append(")");

               Hashtable<String, String> map = new Hashtable<String, String>();
               map.put("PostalCode", "Postalcode");
               String[] ignoredFields = {"IsPrimary", "Address2", "Phone1", "Phone2", "Fax", "RoomOrBuilding", "AgentID"};
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);
               copyTable(oldDBConn, newDBConn, sqlStr.toString(), "address", "address", map, null); // closes the oldDBConn automatically
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames( null);
           }

           if (oldAgentIds.size() > 0)
           {
               StringBuilder sqlStr = new StringBuilder("select ");
               List<String> names = new ArrayList<String>();
               getFieldNamesFromSchema(oldDBConn, "agent", names);
               sqlStr.append(buildSelectFieldList(names, "agent"));
               sqlStr.append(" from agent where AgentId in (");

               cnt = 0;
               for (Enumeration<Long> e=oldAgentIds.keys();e.hasMoreElements();)
               {

                   Long id = e.nextElement();
                   Long val = oldAgentIds.get(id);
                   if (val == 0)
                   {
                       agentIDMapper.put(id, newAgentId);
                       newAgentId++;

                       if (cnt > 0) sqlStr.append(",");
                       sqlStr.append(id);
                       cnt++;
                   }
               }
               sqlStr.append(")");

               String[] ignoredFields = {"JobTitle", "Email", "URL", "Visibility", "VisibilitySetBy"};//User/Security changes
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(ignoredFields);
               copyTable(oldDBConn, newDBConn, sqlStr.toString(), "agent", "agent", null, null);
               BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);


           }
           log.info("Agent Address SQL recordCnt "+recordCnt);

           return true;

       } catch (SQLException ex)
       {
           log.error(ex);
           ex.printStackTrace();
           throw new RuntimeException(ex);
       }
   }

  
    public void createAndFillStatTable()
    {
        try
        {
            Statement stmtNew = newDBConn.createStatement();
            String str = "DROP TABLE `webstats`";
            try
            {
                stmtNew.executeUpdate(str);
                
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }

            str = "CREATE TABLE `webstats` (`WebStatsID` int(11) NOT NULL default '0', "
                + "`UniqueVisitors1` int(11), "
                + "`UniqueVisitors2` int(11), "
                + "`UniqueVisitors3` int(11), "
                + "`UniqueVisitors4` int(11), "
                + "`UniqueVisitors5` int(11), "
                + "`UniqueVisitors6` int(11), "
                + "`UniqueVisitorsMon1` varchar(32), "
                + "`UniqueVisitorsMon2` varchar(32), "
                + "`UniqueVisitorsMon3` varchar(32), "
                + "`UniqueVisitorsMon4` varchar(32), "
                + "`UniqueVisitorsMon5` varchar(32), "
                + "`UniqueVisitorsMon6` varchar(32), "
                + "`UniqueVisitorsYear` varchar(32), "
                + "`Taxon1` varchar(32), "
                + "`TaxonCnt1` int(11), "
                + "`Taxon2` varchar(32), "
                + "`TaxonCnt2` int(11), "
                + "`Taxon3` varchar(32), "
                + "`TaxonCnt3` int(11), "
                + "`Taxon4` varchar(32), "
                + "`TaxonCnt4` int(11), "
                + "`Taxon5` varchar(32), "
                + "`TaxonCnt5` int(11), "
                    + " PRIMARY KEY (`WebStatsID`) ) ENGINE=InnoDB DEFAULT CHARSET=latin1";
            // log.info(str);
            stmtNew.executeUpdate(str);
            
            
    
            str = "INSERT INTO webstats VALUES (0, 234, 189, 211, 302, 229, 276, " +
            "'Nov', 'Dec', 'Jan', 'Feb', 'Mar', 'Apr', " +
            " 2621, " +
            "'Etheostoma',  54," +
            "'notatus',  39," +
            "'lutrensis',  22," +
            "'anomalum',  12," +
            "'platostomus',  8" +
                    ")";
            
            stmtNew.executeUpdate(str);

            stmtNew.clearBatch();
            stmtNew.close();
            
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    //--------------------------------------------------------------------
    //-- Static Methods
    //--------------------------------------------------------------------

    /**
     * @return wehether it should create the Map Tables, if false it assumes they have already been created
     */
    public static boolean shouldCreateMapTables()
    {
        return shouldCreateMapTables;
    }

    /**
     * @return whether the map tables should be removed
     */
    public static boolean shouldDeleteMapTables()
    {
        return shouldDeleteMapTables;
    }

    /**
     * Sets whether to create all the mapping tables
     * @param shouldCreateMapTables true to create, false to do nothing
     */
    public static void setShouldCreateMapTables(boolean shouldCreateMapTables)
    {
        GenericDBConversion.shouldCreateMapTables = shouldCreateMapTables;
    }

    /**
     * Sets whether the mapping tables should be deleted after the conversion process completes
     * @param shouldDeleteMapTables true to delete tables, false means do nothing
     */
    public static void setShouldDeleteMapTables(boolean shouldDeleteMapTables)
    {
        GenericDBConversion.shouldDeleteMapTables = shouldDeleteMapTables;
    }
}

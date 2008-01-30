package edu.ku.brc.specify.utilapps;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;

import com.thoughtworks.xstream.XStream;

import edu.ku.brc.dbsupport.AttributeIFace;
import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.AccessionAgent;
import edu.ku.brc.specify.datamodel.AccessionAuthorization;
import edu.ku.brc.specify.datamodel.Address;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.AgentVariant;
import edu.ku.brc.specify.datamodel.Attachment;
import edu.ku.brc.specify.datamodel.AttributeDef;
import edu.ku.brc.specify.datamodel.Author;
import edu.ku.brc.specify.datamodel.Borrow;
import edu.ku.brc.specify.datamodel.BorrowAgent;
import edu.ku.brc.specify.datamodel.BorrowMaterial;
import edu.ku.brc.specify.datamodel.BorrowReturnMaterial;
import edu.ku.brc.specify.datamodel.CatalogNumberingScheme;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectingEventAttr;
import edu.ku.brc.specify.datamodel.CollectingTrip;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.CollectionObjectAttr;
import edu.ku.brc.specify.datamodel.CollectionObjectCitation;
import edu.ku.brc.specify.datamodel.CollectionType;
import edu.ku.brc.specify.datamodel.Collector;
import edu.ku.brc.specify.datamodel.Container;
import edu.ku.brc.specify.datamodel.DataModelObjBase;
import edu.ku.brc.specify.datamodel.DataType;
import edu.ku.brc.specify.datamodel.Deaccession;
import edu.ku.brc.specify.datamodel.DeaccessionAgent;
import edu.ku.brc.specify.datamodel.DeaccessionPreparation;
import edu.ku.brc.specify.datamodel.Determination;
import edu.ku.brc.specify.datamodel.DeterminationCitation;
import edu.ku.brc.specify.datamodel.DeterminationStatus;
import edu.ku.brc.specify.datamodel.Division;
import edu.ku.brc.specify.datamodel.Geography;
import edu.ku.brc.specify.datamodel.GeographyTreeDef;
import edu.ku.brc.specify.datamodel.GeographyTreeDefItem;
import edu.ku.brc.specify.datamodel.GeologicTimePeriod;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDefItem;
import edu.ku.brc.specify.datamodel.GroupPerson;
import edu.ku.brc.specify.datamodel.InfoRequest;
import edu.ku.brc.specify.datamodel.Institution;
import edu.ku.brc.specify.datamodel.Journal;
import edu.ku.brc.specify.datamodel.LithoStratTreeDef;
import edu.ku.brc.specify.datamodel.Loan;
import edu.ku.brc.specify.datamodel.LoanAgent;
import edu.ku.brc.specify.datamodel.LoanPreparation;
import edu.ku.brc.specify.datamodel.LoanReturnPreparation;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.specify.datamodel.LocalityCitation;
import edu.ku.brc.specify.datamodel.LocalityDetail;
import edu.ku.brc.specify.datamodel.Location;
import edu.ku.brc.specify.datamodel.LocationTreeDef;
import edu.ku.brc.specify.datamodel.LocationTreeDefItem;
import edu.ku.brc.specify.datamodel.OtherIdentifier;
import edu.ku.brc.specify.datamodel.Permit;
import edu.ku.brc.specify.datamodel.PickList;
import edu.ku.brc.specify.datamodel.PickListItem;
import edu.ku.brc.specify.datamodel.PrepType;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.specify.datamodel.PreparationAttr;
import edu.ku.brc.specify.datamodel.Project;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.datamodel.ReferenceWork;
import edu.ku.brc.specify.datamodel.RepositoryAgreement;
import edu.ku.brc.specify.datamodel.Shipment;
import edu.ku.brc.specify.datamodel.SpQuery;
import edu.ku.brc.specify.datamodel.SpQueryField;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.datamodel.TaxonCitation;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;
import edu.ku.brc.specify.datamodel.TaxonTreeDefItem;
import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.UserGroup;
import edu.ku.brc.specify.datamodel.UserPermission;
import edu.ku.brc.specify.datamodel.Workbench;
import edu.ku.brc.specify.datamodel.WorkbenchDataItem;
import edu.ku.brc.specify.datamodel.WorkbenchRow;
import edu.ku.brc.specify.datamodel.WorkbenchTemplate;
import edu.ku.brc.specify.datamodel.WorkbenchTemplateMappingItem;

public class DataBuilder
{
    protected static Calendar startCal = Calendar.getInstance();
    protected static Session  session  = null;

    public static Session getSession()
    {
        return session;
    }

    public static void setSession(Session session)
    {
        DataBuilder.session = session;
    }

    public static AccessionAgent createAccessionAgent(Accession accession, Agent agent)
    {
        AccessionAgent accessionAgent = new AccessionAgent();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        accessionAgent.setTimestampCreated(now);
        accessionAgent.setAccession(accession);
        accessionAgent.setAgent(agent);
        persist(accessionAgent);
        return accessionAgent;

    }

    public static AgentVariant createAgentVariant(final Byte varType, final String name, final Agent agentOwner)
    {
        AgentVariant av = new AgentVariant();
        av.initialize();
        av.setVarType(varType);
        av.setName(name);
        av.setAgent(agentOwner);
        agentOwner.getVariants().add(av);
        return av;
    }
    
    public static Institution createInstitution(final String name)
    {
        // Create Collection Type
        Institution inst = new Institution();
        inst.initialize();
        inst.setName(name);

        persist(inst);
        return inst;
    }
    
    public static Division createDivision(final Institution inst, 
                                          final String discipline, 
                                          final String name, 
                                          final String abbrev, 
                                          final String title)
    {
        // Create Collection Type
        Division division = new Division();
        division.initialize();
        division.setName(name);
        division.setDiscipline(discipline);
        division.setAbbrev(abbrev);
        division.setTitle(title);
        
        inst.addReference(division, "divisions");

        persist(division);
        return division;
    }
    
    public static Agent createAgent(final String title,
                                    final String firstName,
                                    final String middleInit,
                                    final String lastName,
                                    final String abbreviation,
                                    final String email)
    {
        return createAgent(title, firstName, middleInit, lastName, abbreviation, email, -1);
    }
    
    public static Agent createAgent(final String title,
                                    final String firstName,
                                    final String middleInit,
                                    final String lastName,
                                    final String abbreviation,
                                    final String email,
                                    final int    collectionMemberId)
    {
        // Create Collection Type
        Agent agent = new Agent();
        agent.initialize();
        agent.setAgentType((byte) 1);
        agent.setFirstName(firstName);
        agent.setLastName(lastName);
        agent.setMiddleInitial(middleInit);
        agent.setAbbreviation(abbreviation);
        agent.setTitle(title);
        agent.setEmail(email);
        agent.setCollectionMemberId(collectionMemberId);

        persist(agent);
        return agent;
    }
    public static Attachment createAttachment(final String filename,
                                              final String mimeType)
    {
        Attachment attachment = new Attachment();
        attachment.initialize();
        attachment.setOrigFilename(filename);
        attachment.setMimeType(mimeType);
        persist(attachment);
        return attachment;
    }
    
    public static SpQuery createQuery(final String      name, 
                                      final String      contextName, 
                                      final int         contextTableId,
                                      final SpecifyUser owner)
    {
        SpQuery query = new SpQuery();
        query.initialize();
        query.setName(name);
        query.setContextName(contextName);
        query.setContextTableId((short)contextTableId);
        query.setCreatedByAgent(owner.getAgent());
        query.setSpecifyUser(owner);
        return query;
        
    }
    
    public static SpQueryField createQueryField(final SpQuery query,
                                                final Short   position,
                                                final String  fieldName, 
                                                final Boolean isNot,
                                                final Byte    operStart,
                                                final Byte    operEnd,
                                                final String  startValue,
                                                final String  endValue,
                                                final Byte    sortType,
                                                final Boolean isDisplay,
                                                final String  tableList)
    {
        SpQueryField field = new SpQueryField();
        field.initialize();
        field.setPosition(position);
        field.setFieldName(fieldName);
        field.setOperStart(operStart);
        field.setOperEnd(operEnd);
        field.setEndValue(endValue);
        field.setIsDisplay(isDisplay);
        field.setIsNot(isNot);
        field.setSortType(sortType);
        field.setStartValue(startValue);
        field.setTableList(tableList);
        query.addReference(field, "fields");
        return field;
    }
                                              
    public static AttributeDef createAttributeDef(final AttributeIFace.FieldType type,
                                                  final String name,
                                                  final CollectionType collectionType,
                                                  final PrepType prepType)
    {
        AttributeDef attrDef = new AttributeDef();
        attrDef.initialize();
        attrDef.setDataType(type.getType());
        attrDef.setFieldName(name);
        attrDef.setPrepType(prepType);
        attrDef.setCollectionType(collectionType);
        
        collectionType.getAttributeDefs().add(attrDef);
        
        return attrDef;
    }

    public static CollectionType createCollectionType(final Division         division,
                                                      final String           name,
                                                      final String           disciplineName,
                                                      final DataType         dataType,
                                                      final SpecifyUser      user,
                                                      final TaxonTreeDef     taxonTreeDef,
                                                      final GeographyTreeDef geographyTreeDef,
                                                      final GeologicTimePeriodTreeDef geologicTimePeriodTreeDef,
                                                      final LocationTreeDef  locationTreeDef,
                                                      final LithoStratTreeDef lithoStratTreeDef)
    {
        CollectionType collType = new CollectionType();
        collType.initialize();
        collType.setName(name);
        collType.setDiscipline(disciplineName);
        collType.setDataType(dataType);
        collType.setSpecifyUser(user);
        collType.setTaxonTreeDef(taxonTreeDef);
        collType.setGeographyTreeDef(geographyTreeDef);//meg added to support not-null constraints
        collType.setGeologicTimePeriodTreeDef(geologicTimePeriodTreeDef);//meg added to support not-null constraints
        collType.setLocationTreeDef(locationTreeDef);//meg added to support not-null constraints
        collType.setLithoStratTreeDef(lithoStratTreeDef);
        taxonTreeDef.setCollectionType(collType);
        
        division.addReference(collType, "collectionTypes");

        persist(collType);
        return collType;
    }

    public static Collection createCollection(final String prefix,
                                              final String name,
                                              final CatalogNumberingScheme catalogNumberingScheme,
                                              final CollectionType[] collTypes)
    {
        Collection collection = new Collection();
        collection.initialize();
        collection.setCollectionPrefix(prefix);
        collection.setModifiedByAgent(null);
        collection.setRemarks("These are the remarks");
        collection.setCollectionName(name);
        collection.setCatalogNumberingScheme(catalogNumberingScheme);
        catalogNumberingScheme.getCollections().add(collection);
        
        for (CollectionType cod: collTypes)
        {
            collection.setCollectionType(cod);
        }

        persist(collection);
        return collection;
    }

    /**
     * @param prefix
     * @param name
     * @param collType
     * @return
     */
    public static Collection createCollection(final String prefix,
                                              final String name,
                                              final CatalogNumberingScheme numberingScheme,
                                              final CollectionType collType)
    {
        return createCollection(prefix, name, numberingScheme, new CollectionType[] { collType });
    }

    /**
     * @param locality
     * @param collectors
     * @return
     */
    public static CollectingEvent createCollectingEvent(final Locality locality, final Collector[] collectors)
    {
        CollectingEvent colEv = new CollectingEvent();
        colEv.initialize();

        HashSet<Collector> collectorSet = new HashSet<Collector>();
        if (collectors != null)
        {
            for (Collector c: collectors)
            {
                c.setCollectingEvent(colEv);
                collectorSet.add(c);
            }
        }
        colEv.setCollectors(collectorSet);
        colEv.setLocality(locality);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        colEv.setTimestampCreated(now);

        persist(colEv);
        persist(locality);
        return colEv;
    }
    
    public static CollectingTrip createCollectingTrip(final String remarks, final CollectingEvent[] events)
    {
        CollectingTrip trip = new CollectingTrip();
        trip.initialize();
        trip.setRemarks(remarks);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        trip.setTimestampCreated(now);

        Calendar startDate = Calendar.getInstance();
        startDate.set(9999, 12, 31);
        Calendar endDate = Calendar.getInstance();
        endDate.set(1, 1, 1);
        String verStartDate = null;
        String verEndDate = null;
        
        for (CollectingEvent e: events)
        {
            if (e.getStartDate().before(startDate))
            {
                startDate = e.getStartDate();
                verStartDate = e.getStartDateVerbatim();
            }
            if (e.getEndDate().after(endDate))
            {
                endDate = e.getEndDate();
                verEndDate = e.getEndDateVerbatim();
            }
            trip.getCollectingEvents().add(e);
        }
        
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        trip.setStartDateVerbatim(verStartDate);
        trip.setEndDateVerbatim(verEndDate);
        
        return trip;
    }

    public static CollectingEventAttr createCollectingEventAttr(final CollectingEvent colEv,
                                                                final AttributeDef colObjAttrDef,
                                                                final String strVal,
                                                                final Double dblVal)
    {
        // Create CollectionObjectAttr
        CollectingEventAttr colEvAttr = new CollectingEventAttr();
        colEvAttr.initialize();

        colEvAttr.setDefinition(colObjAttrDef);
        colObjAttrDef.getCollectingEventAttrs().add(colEvAttr);
        
        colEvAttr.setCollectingEvent(colEv);
        if (strVal != null)
        {
            colEvAttr.setStrValue(strVal);
        }
        if (dblVal != null)
        {
            colEvAttr.setDblValue(dblVal);
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        colEvAttr.setTimestampCreated(now);

        colEv.getAttrs().add(colEvAttr);

        persist(colEv);
        return colEvAttr;
    }

    public static CollectionObjectAttr createCollectionObjectAttr(final CollectionObject colObj,
                                                                  final AttributeDef colObjAttrDef,
                                                                  final String strVal,
                                                                  final Double dblVal)
    {
        // Create CollectionObjectAttr
        CollectionObjectAttr colObjAttr = new CollectionObjectAttr();
        colObjAttr.initialize();

        colObjAttr.setDefinition(colObjAttrDef);
        colObjAttr.setCollectionObject(colObj);
        if (strVal != null)
        {
            colObjAttr.setStrValue(strVal);
        }
        if (dblVal != null)
        {
            colObjAttr.setDblValue(dblVal);
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        colObjAttr.setTimestampCreated(now);

        colObj.getAttrs().add(colObjAttr);

        persist(colObj);
        return colObjAttr;
    }

    public static Collector createCollector(final Agent agent, int orderNum)
    {
        Collector collector = new Collector();
        collector.initialize();

        collector.setAgent(agent);
        collector.setModifiedByAgent(null);
        collector.setOrderNumber(orderNum);
        collector.setRemarks("");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        collector.setTimestampCreated(now);

        persist(collector);
        return collector;
    }

    public static CollectionObject createCollectionObject(final String catalogNumber,
                                                          final String fieldNumber,
                                                          final Agent cataloger,
                                                          final Collection collection,
                                                          final int count,
                                                          final CollectingEvent collectingEvent,
                                                          final Calendar catalogedDate,
                                                          final String lastEditedBy)
    {
        // Create Collection Object
        CollectionObject colObj = new CollectionObject();
        colObj.initialize();
        
        colObj.setCataloger(cataloger);
        colObj.setCatalogedDate(catalogedDate);
        colObj.setCatalogedDateVerbatim(DateFormat.getInstance().format(catalogedDate.getTime()));
        colObj.setCatalogNumber(catalogNumber);
        colObj.setCollection(collection);
        colObj.setCollectingEvent(collectingEvent);
        colObj.setCountAmt(count);
        colObj.setFieldNumber(fieldNumber);
        colObj.setModifiedByAgent(cataloger);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        colObj.setTimestampCreated(now);

        if (collectingEvent != null)
        {
            collectingEvent.getCollectionObjects().add(colObj);
        }

        persist(colObj);
        if (collectingEvent != null)
        {
            persist(collectingEvent);
        }
        return colObj;
    }

    public static DeterminationStatus createDeterminationStatus(final String name, final String remarks, final boolean isCurrent)
    {
        DeterminationStatus status = new DeterminationStatus();
        status.initialize();
        status.setName(name);
        status.setIsCurrent(isCurrent);
        status.setRemarks(remarks);

        persist(status);
        return status;
    }

    public static Determination createDetermination(final CollectionObject collectionObject,
                                                    final Agent determiner,
                                                    final Taxon taxon,
                                                    final DeterminationStatus status,
                                                    final Calendar calendar)
    {
        if (collectionObject == null)
        {
            int x = 0;
            x++;
        }
        // Create Determination
        Determination determination = new Determination();
        determination.initialize();

        determination.setStatus(status);
        determination.setCollectionObject(collectionObject);
        determination.setDeterminedDate(calendar);
        determination.setDeterminer(determiner);
        determination.setTaxon(taxon);

        status.getDeterminations().add(determination);
        collectionObject.getDeterminations().add(determination);
        taxon.getDeterminations().add(determination);

        persist(collectionObject);
        persist(determination);
        persist(status);
        persist(taxon);
        return determination;

    }

    /**
     * Create a <code>GeographyTreeDef</code> with the given name.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param name tree def name
     * @return the geography tree def
     */
    public static GeographyTreeDef createGeographyTreeDef(final String name)
    {
        GeographyTreeDef gtd = new GeographyTreeDef();
        gtd.initialize();
        gtd.setName(name);
        gtd.setFullNameDirection(TreeDefIface.REVERSE);

        persist(gtd);
        return gtd;
    }

    /**
     * Creates a <code>GeographyTreeDefItem</code> using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param parent the parent node
     * @param gtd the associated definition object
     * @param name the name of the item
     * @param rankId the rank of the itme
     * @return the new item
     */
    @SuppressWarnings("unchecked")
    public static GeographyTreeDefItem createGeographyTreeDefItem(final GeographyTreeDefItem parent,
                                                                  final GeographyTreeDef gtd,
                                                                  final String name,
                                                                  final int rankId)
    {
        GeographyTreeDefItem gtdi = new GeographyTreeDefItem();
        gtdi.initialize();
        gtdi.setName(name);
        gtdi.setParent(parent);
        gtdi.setRankId(rankId);
        gtdi.setTreeDef(gtd);
        gtdi.setFullNameSeparator(", ");
        if (gtd != null)
        {
            gtd.getTreeDefItems().add(gtdi);
        }

        persist(gtdi);
        return gtdi;
    }

    /**
     * Creates a new <code>Geography</code> object using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param gtd the associated definition
     * @param parent the parent node
     * @param name the name of the node
     * @param rankId the rank of the node
     * @return the new node
     */
    @SuppressWarnings("unchecked")
    public static Geography createGeography(final GeographyTreeDef gtd, final Geography parent,
    //final String abbrev,
                                            final String name,
                                            //final int highNode,
                                            //final int nodeNum,
                                            final int rankId)
    {
        Geography geography = new Geography();
        geography.initialize();
        geography.setDefinition(gtd);
        geography.setName(name);
        geography.setParent(parent);
        if (parent!=null)
        {
            parent.getChildren().add(geography);
        }
        geography.setRankId(rankId);
        GeographyTreeDefItem defItem = gtd.getDefItemByRank(rankId);
        if (defItem != null)
        {
            geography.setDefinitionItem(defItem);
        }
        gtd.getTreeEntries().add(geography);

        persist(geography);
        return geography;
    }
    
    public static List<Geography> createGeographyChildren(final GeographyTreeDef geoTreeDef,
                                                      final Geography        parent,
                                                      final String[]         childNames,
                                                      final int              rankId)
    {
        List<Geography> geos = new Vector<Geography>();
        for (int i=0;i<childNames.length;i++)
        {
            geos.add(createGeography(geoTreeDef, parent, childNames[i], rankId));
        }
        return geos;
    }


    /**
     * Create a <code>LocationTreeDef</code> with the given name.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param name tree def name
     * @return the location tree def
     */
    public static LocationTreeDef createLocationTreeDef(final String name)
    {
        LocationTreeDef ltd = new LocationTreeDef();
        ltd.initialize();
        ltd.setName(name);
        ltd.setFullNameDirection(TreeDefIface.REVERSE);

        persist(ltd);
        return ltd;
    }

    /**
     * Creates a <code>LocationTreeDefItem</code> using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param parent the parent node
     * @param ltd the associated definition object
     * @param name the name of the item
     * @param rankId the rank of the itme
     * @return the new item
     */
    @SuppressWarnings("unchecked")
    public static LocationTreeDefItem createLocationTreeDefItem(final LocationTreeDefItem parent,
                                                                final LocationTreeDef ltd,
                                                                final String name,
                                                                final int rankId)
    {
        LocationTreeDefItem ltdi = new LocationTreeDefItem();
        ltdi.initialize();
        ltdi.setName(name);
        ltdi.setParent(parent);
        ltdi.setRankId(rankId);
        ltdi.setTreeDef(ltd);
        ltdi.setFullNameSeparator(", ");
        if (ltd != null)
        {
            ltd.getTreeDefItems().add(ltdi);
        }

        persist(ltdi);
        return ltdi;
    }

    /**
     * Creates a new <code>Location</code> object using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param ltd the associated definition
     * @param parent the parent node
     * @param name the name of the node
     * @param rankId the rank of the node
     * @return the new node
     */
    @SuppressWarnings("unchecked")
    public static Location createLocation(final LocationTreeDef ltd, final Location parent,
    //final String abbrev,
                                          final String name,
                                          //final int highNode,
                                          //final int nodeNum,
                                          final int rankId)
    {
        Location location = new Location();
        location.initialize();
        location.setDefinition(ltd);
        location.setName(name);
        location.setParent(parent);
        if (parent!=null)
        {
            parent.getChildren().add(location);
        }
        LocationTreeDefItem defItem = ltd.getDefItemByRank(rankId);
        if (defItem != null)
        {
            location.setDefinitionItem(defItem);
        }

        location.setRankId(rankId);
        ltd.getTreeEntries().add(location);

        persist(location);
        return location;
    }

    /**
     * @param name
     * @param type
     * @param tableName
     * @param fieldName
     * @param formatter
     * @param readOnly
     * @param sizeLimit
     * @return
     */
    public static PickList createPickList(final String  name,
                                          final Integer type,
                                          final String  tableName,
                                          final String  fieldName,
                                          final String  formatter,
                                          boolean       readOnly, 
                                          int           sizeLimit,
                                          Boolean       isSystem)
    {
        PickList pickList = new PickList();
        pickList.initialize();
        pickList.setName(name);
        pickList.setType(type);
        pickList.setTableName(tableName);
        pickList.setFieldName(fieldName);
        pickList.setFormatter(formatter);
        pickList.setReadOnly(readOnly);
        pickList.setSizeLimit(sizeLimit);
        pickList.setIsSystem(isSystem);
        persist(pickList);
        return pickList;
    }
    
    /**
     * @param name
     * @param readOnly
     * @return
     */
    public static PickList createPickList(final String name, boolean readOnly, boolean isSystem)
    {
        return createPickList(name, 0, null, null, null, readOnly, -1, isSystem);
    }
    
    /**
     * @param name
     * @param readOnly
     * @param values
     * @return
     */
    public static PickList createPickList(final String name,
                                          boolean readOnly, 
                                          String[] values,
                                          boolean isSystem)
    {
        PickList pickList = createPickList(name, 0, null, null, null, readOnly, values.length, isSystem);

        for (String value: values)
        {
            pickList.addItem(value, value);
        }
        return pickList;
    }
    
    /**
     * @param name
     * @param readOnly
     * @param values
     * @param maxSize
     * @return
     */
    public static PickList createPickList(final String name,
                                          boolean readOnly, 
                                          String[] values,
                                          int maxSize,
                                          boolean isSystem)
    {
        PickList pickList = createPickList(name, 0, null, null, null, readOnly, maxSize, isSystem);

        for (String value: values)
        {
            pickList.addItem(value, value);
        }
        return pickList;
    }
    
    /**
     * @param name
     * @param readOnly
     * @param titles
     * @param values
     * @param maxSize
     * @return
     */
    public static PickList createPickList(final String name,
                                          boolean readOnly, 
                                          String[] titles,
                                          String[] values,
                                          int maxSize,
                                          boolean isSystem)
    {
        PickList pickList = createPickList(name, 0, null, null, null, readOnly, maxSize, isSystem);

        for (int i=0;i<titles.length;i++)
        {
            pickList.addItem(titles[i], values[i]);
        }
        return pickList;
    }
    
    /**
     * Create a <code>GeologicTimePeriodTreeDef</code> with the given name.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param name tree def name
     * @return the geologic time period tree def
     */
    public static GeologicTimePeriodTreeDef createGeologicTimePeriodTreeDef(final String name)
    {
        GeologicTimePeriodTreeDef gtp = new GeologicTimePeriodTreeDef();
        gtp.initialize();
        gtp.setName(name);
        gtp.setFullNameDirection(TreeDefIface.REVERSE);

        persist(gtp);
        return gtp;
    }

    /**
     * Creates a <code>GeologicTimePeriodTreeDefItem</code> using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param parent the parent node
     * @param gtptd the associated definition object
     * @param name the name of the item
     * @param rankId the rank of the itme
     * @return the new item
     */
    @SuppressWarnings("unchecked")
    public static GeologicTimePeriodTreeDefItem createGeologicTimePeriodTreeDefItem(final GeologicTimePeriodTreeDefItem parent,
                                                                                    final GeologicTimePeriodTreeDef gtptd,
                                                                                    final String name,
                                                                                    final int rankId)
    {
        GeologicTimePeriodTreeDefItem gtdi = new GeologicTimePeriodTreeDefItem();
        gtdi.initialize();
        gtdi.setName(name);
        gtdi.setParent(parent);
        gtdi.setRankId(rankId);
        gtdi.setTreeDef(gtptd);
        gtdi.setFullNameSeparator(", ");
        if (gtptd != null)
        {
            gtptd.getTreeDefItems().add(gtdi);
        }

        persist(gtdi);
        return gtdi;
    }

    /**
     * Creates a new <code>GeologicTimePeriod</code> object using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param gtptd the associated definition
     * @param parent the parent node
     * @param name the name of the node
     * @param rankId the rank of the node
     * @return the new node
     */
    @SuppressWarnings("unchecked")
    public static GeologicTimePeriod createGeologicTimePeriod(final GeologicTimePeriodTreeDef gtptd,
                                                              final GeologicTimePeriod parent,
                                                              final String name,
                                                              final float startMYA,
                                                              final float endMYA,
                                                              final int rankId)
    {
        GeologicTimePeriod gtp = new GeologicTimePeriod();
        gtp.initialize();
        gtp.setDefinition(gtptd);
        gtp.setDefinition(gtptd);
        gtp.setName(name);
        gtp.setParent(parent);
        if (parent!=null)
        {
            parent.getChildren().add(gtp);
        }
        gtp.setStartPeriod(startMYA);
        gtp.setStartUncertainty(0.0f);
        gtp.setEndPeriod(endMYA);
        gtp.setEndUncertainty(0.0f);
        GeologicTimePeriodTreeDefItem defItem = gtptd.getDefItemByRank(rankId);
        if (defItem != null)
        {
            gtp.setDefinitionItem(defItem);
        }
        gtp.setRankId(rankId);
        gtptd.getTreeEntries().add(gtp);
        persist(gtp);
        return gtp;
    }

    /**
     * Create a <code>TaxonTreeDef</code> with the given name.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param name tree def name
     * @return the taxon tree def
     */
    public static TaxonTreeDef createTaxonTreeDef(final String name)
    {
        TaxonTreeDef ttd = new TaxonTreeDef();
        ttd.initialize();
        ttd.setName(name);
        ttd.setFullNameDirection(TreeDefIface.FORWARD);
        return ttd;
    }

    /**
     * Creates a <code>TaxonTreeDefItem</code> using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param parent the parent node
     * @param ttd the associated definition object
     * @param name the name of the item
     * @param rankId the rank of the itme
     * @return the new item
     */
    @SuppressWarnings("unchecked")
    public static TaxonTreeDefItem createTaxonTreeDefItem(final TaxonTreeDefItem parent,
                                                          final TaxonTreeDef ttd,
                                                          final String name,
                                                          final int rankId)
    {
        TaxonTreeDefItem ttdi = new TaxonTreeDefItem();
        ttdi.initialize();
        ttdi.setName(name);
        ttdi.setParent(parent);
        ttdi.setRankId(rankId);
        ttdi.setTreeDef(ttd);
        ttdi.setFullNameSeparator(" ");
        if (ttd != null)
        {
            ttd.getTreeDefItems().add(ttdi);
        }

        persist(ttdi);
        return ttdi;
    }
    
    /**
     * Create a <code>LithoStratTreeDef</code> with the given name.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param name tree def name
     * @return the LithoStrat tree def
     */
    public static LithoStratTreeDef createLithoStratTreeDef(final String name)
    {
        LithoStratTreeDef lstd = new LithoStratTreeDef();
        lstd.initialize();
        lstd.setName(name);
        lstd.setFullNameDirection(TreeDefIface.FORWARD);
        return lstd;
    }



    /**
     * Creates a new <code>Taxon</code> object using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param ttd the associated definition
     * @param parent the parent node
     * @param name the name of the node
     * @param rankId the rank of the node
     * @return the new node
     */
    @SuppressWarnings("unchecked")
    public static Taxon createTaxon(final TaxonTreeDef ttd, final Taxon parent, final String name, final int rankId)
    {
        return createTaxon(ttd, parent, name, null, rankId);
    }

    /**
     * Creates a new <code>Taxon</code> object using the given values.  The object is also
     * persisted with a call to {@link #persist(Object)}.
     * 
     * @param ttd the associated definition
     * @param parent the parent node
     * @param name the name of the node
     * @param commonName the common name of the node
     * @param rankId the rank of the node
     * @return the new node
     */
    @SuppressWarnings("unchecked")
    public static Taxon createTaxon(final TaxonTreeDef ttd, final Taxon parent, final String name, final String commonName, final int rankId)
    {
        Taxon taxon = new Taxon();
        taxon.initialize();
        taxon.setDefinition(ttd);
        taxon.setName(name);
        taxon.setCommonName(commonName);
        taxon.setParent(parent);
        if (parent!=null)
        {
            parent.getChildren().add(taxon);
        }
        TaxonTreeDefItem defItem = ttd.getDefItemByRank(rankId);
        if (defItem != null)
        {
            taxon.setDefinitionItem(defItem);
        }
        taxon.setRankId(rankId);
        ttd.getTreeEntries().add(taxon);

        persist(taxon);
        return taxon;
    }
    
    public static List<Object> createTaxonChildren(final TaxonTreeDef treeDef,
                                              final Taxon parent,
                                              final String[] childNames,
                                              final String[] commonNames,
                                              final int rankId)
    {
        Vector<Object> kids = new Vector<Object>();
        for (int i = 0; i < childNames.length; i++)
        {
            kids.add(createTaxon(treeDef, parent, childNames[i], commonNames[i], rankId));
        }
        return kids;
    }

    public static Preparation createPreparation(final PrepType prepType,
                                                final Agent preparedBy,
                                                final CollectionObject colObj,
                                                final Location location,
                                                final int count,
                                                final Calendar preparedDate)
    {
        Preparation prep = new Preparation();
        prep.initialize();

        prep.setCollectionObject(colObj);
        prep.setCount(count);
        prep.setModifiedByAgent(null);
        prep.setLocation(location);
        prep.setPreparedByAgent(preparedBy);
        prep.setPreparedDate(preparedDate);
        prep.setPrepType(prepType);
        prep.setRemarks(null);
        prep.setStorageLocation(null);
        prep.setText1(null);
        prep.setText2(null);

        colObj.getPreparations().add(prep);

        persist(prep);
        return prep;
    }

    public static Locality createLocality(final String name, final Geography geo)
    {
        Locality locality = new Locality();
        locality.initialize();

        locality.setLocalityName(name);
        locality.setGeography(geo);

        persist(locality);
        return locality;
    }

    public static PreparationAttr createPreparationAttr(final AttributeDef attrDef,
                                                        final Preparation prep,
                                                        final String strVal,
                                                        final Double dblVal)
    {
        PreparationAttr prepAttr = new PreparationAttr();
        prepAttr.initialize();

        prepAttr.setDefinition(attrDef);
        prepAttr.setPreparation(prep);
        if (strVal != null)
        {
            prepAttr.setStrValue(strVal);

        }
        else if (dblVal != null)
        {
            prepAttr.setDblValue(dblVal);
        }
        prepAttr.setTimestampCreated(new Timestamp(System.currentTimeMillis()));

        prep.getAttrs().add(prepAttr);
        persist(prepAttr);
        return prepAttr;
    }

    public static Address createAddress(final Agent agent,
                                        final String address1,
                                        final String address2,
                                        final String city,
                                        final String state,
                                        final String country,
                                        final String postalCode)
    {
        Address address = new Address();
        address.initialize();
        address.setAgent(agent);
        address.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        address.setAddress(address1);
        address.setAddress2(address2);
        address.setCity(city);
        address.setCountry(country);
        address.setPostalCode(postalCode);
        address.setState(state);
        address.setIsPrimary(true);

        agent.getAddresses().add(address);
        persist(address);
        persist(agent);
        return address;
    }

    public static Permit createPermit(final String permitNumber,
                                      final String type,
                                      final Calendar issuedDate,
                                      final Calendar startDate,
                                      final Calendar endDate,
                                      final Calendar renewalDate)
    {
        Permit permit = new Permit();
        permit.initialize();
        permit.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        permit.setStartDate(startDate);
        permit.setEndDate(endDate);
        permit.setPermitNumber(permitNumber);
        permit.setIssuedDate(issuedDate);
        permit.setRenewalDate(renewalDate);
        permit.setType(type);
        persist(permit);
        return permit;
    }

    //-----------------------------------------------------------

    public static Accession createAccession(final Division division,
                                            final String type,
                                            final String status,
                                            final String number,
                                            final String verbatimDate,
                                            final Calendar dateAccessioned,
                                            final Calendar dateReceived)
    {
        Accession accession = new Accession();
        accession.initialize();
        accession.setDivision(division);
        accession.setAccessionNumber(number);
        accession.setVerbatimDate(verbatimDate);
        accession.setDateAccessioned(dateAccessioned);
        accession.setDateReceived(dateReceived);
        accession.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        accession.setStatus(status);
        accession.setType(type);
        persist(accession);
        return accession;
    }

    public static AccessionAgent createAccessionAgent(final String role,
                                                       final Agent agent,
                                                       final Accession accession,
                                                       final RepositoryAgreement repositoryAgreement)
    {
        AccessionAgent accessionagent = new AccessionAgent();
        accessionagent.initialize();
        accessionagent.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        accessionagent.setAccession(accession);
        accessionagent.setRepositoryAgreement(repositoryAgreement);
        accessionagent.setRole(role);
        accessionagent.setAgent(agent);
        persist(accessionagent);
        return accessionagent;
    }

    public static AccessionAuthorization createAccessionAuthorization(final Permit permit,
                                                                        final Accession accession,
                                                                        final RepositoryAgreement repositoryAgreement)
    {
        AccessionAuthorization accessionauthorization = new AccessionAuthorization();
        accessionauthorization.initialize();
        accessionauthorization.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        accessionauthorization.setAccession(accession);
        accessionauthorization.setPermit(permit);
        accessionauthorization.setRepositoryAgreement(repositoryAgreement);
        persist(accessionauthorization);
        return accessionauthorization;
    }

    public static Agent createAgent(final Byte agentType,
                                    final String firstName,
                                    final String lastName,
                                    final String middleInitial,
                                    final String title,
                                    final String interests,
                                    final String abbreviation,
                                    final String name,
                                    final Agent organization)
    {
        Agent agent = new Agent();
        agent.initialize();
        agent.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        agent.setOrganization(organization);
        agent.setAgentType(agentType);
        agent.setFirstName(firstName);
        agent.setLastName(lastName);
        agent.setMiddleInitial(middleInitial);
        agent.setInterests(interests);
        agent.setAbbreviation(abbreviation);
        agent.setName(name);
        agent.setTitle(title);
        persist(agent);
        return agent;
    }

    public static AttributeDef createAttributeDef(final Short tableType,
                                                  final String fieldName,
                                                  final Short dataType,
                                                  final CollectionType collectionType,
                                                  final PrepType prepType)
    {
        AttributeDef attributedef = new AttributeDef();
        attributedef.initialize();
        attributedef.setCollectionType(collectionType);
        attributedef.setPrepType(prepType);
        attributedef.setTableType(tableType);
        attributedef.setFieldName(fieldName);
        attributedef.setDataType(dataType);
        persist(attributedef);
        return attributedef;
    }

    public static Author createAuthor(final Short orderNumber, final ReferenceWork referenceWork, final Agent agent)
    {
        Author author = new Author();
        author.initialize();
        author.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        author.setAgent(agent);
        author.setOrderNumber(orderNumber);
        author.setReferenceWork(referenceWork);
        persist(author);
        return author;
    }

    public static Borrow createBorrow(final String invoiceNumber,
                                      final Calendar receivedDate,
                                      final Calendar originalDueDate,
                                      final Calendar dateClosed,
                                      final Boolean isClosed,
                                      final Calendar currentDueDate)
    {
        Borrow borrow = new Borrow();
        borrow.initialize();
        borrow.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        borrow.setInvoiceNumber(invoiceNumber);
        borrow.setReceivedDate(receivedDate);
        borrow.setOriginalDueDate(originalDueDate);
        borrow.setDateClosed(dateClosed);
        borrow.setCurrentDueDate(currentDueDate);
        borrow.setIsClosed(isClosed);
        persist(borrow);
        return borrow;
    }

    public static BorrowAgent createBorrowAgent(final String role, final Agent agent, final Borrow borrow)
    {
        BorrowAgent borrowagent = new BorrowAgent();
        borrowagent.initialize();
        borrowagent.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        borrowagent.setRole(role);
        borrowagent.setAgent(agent);
        borrowagent.setBorrow(borrow);
        persist(borrowagent);
        return borrowagent;
    }

    public static BorrowMaterial createBorrowMaterial(final String materialNumber,
                                                      final String description,
                                                      final Short quantity,
                                                      final String outComments,
                                                      final String inComments,
                                                      final Short quantityResolved,
                                                      final Short quantityReturned,
                                                      final Borrow borrow)
    {
        BorrowMaterial borrowmaterial = new BorrowMaterial();
        borrowmaterial.initialize();
        borrowmaterial.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        borrowmaterial.setQuantity(quantity);
        borrowmaterial.setBorrow(borrow);
        borrowmaterial.setMaterialNumber(materialNumber);
        borrowmaterial.setOutComments(outComments);
        borrowmaterial.setInComments(inComments);
        borrowmaterial.setQuantityResolved(quantityResolved);
        borrowmaterial.setQuantityReturned(quantityReturned);
        borrowmaterial.setDescription(description);
        persist(borrowmaterial);
        return borrowmaterial;
    }

    public static BorrowReturnMaterial createBorrowReturnMaterial(final Calendar returnedDate,
                                                                  final Short quantity,
                                                                  final Agent agent,
                                                                  final BorrowMaterial borrowMaterial)
    {
        BorrowReturnMaterial borrowreturnmaterial = new BorrowReturnMaterial();
        borrowreturnmaterial.initialize();
        borrowreturnmaterial.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        borrowreturnmaterial.setAgent(agent);
        borrowreturnmaterial.setReturnedDate(returnedDate);
        borrowreturnmaterial.setQuantity(quantity);
        borrowreturnmaterial.setBorrowMaterial(borrowMaterial);
        persist(borrowreturnmaterial);
        return borrowreturnmaterial;
    }

//    public static BorrowShipment createBorrowShipment(final Shipment shipment, final Borrow borrow)
//    {
//        BorrowShipment borrowshipment = new BorrowShipment();
//        borrowshipment.initialize();
//        borrowshipment.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
//        borrowshipment.setShipment(shipment);
//        borrowshipment.setBorrow(borrow);
//        persist(borrowshipment);
//        return borrowshipment;
//    }

    public static Collection createCollection(final Boolean isTissueSeries,
                                                    final String seriesName,
                                                    final String collectionPrefix)//,
                                                    //final Collection tissue)
    {
        Collection collection = new Collection();
        collection.initialize();
        collection.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        //collection.setIsTissueSeries(isTissueSeries);
        collection.setCollectionName(seriesName);
        collection.setCollectionPrefix(collectionPrefix);
        //collection.setTissue(tissue);
        persist(collection);
        return collection;
    }

    public static CollectingEvent createCollectingEvent(final String stationFieldNumber,
                                                        final String method,
                                                        final String verbatimDate,
                                                        final Calendar startDate,
                                                        final Short startDatePrecision,
                                                        final String startDateVerbatim,
                                                        final Calendar endDate,
                                                        final Short endDatePrecision,
                                                        final String endDateVerbatim,
                                                        final Short startTime,
                                                        final Short endTime,
                                                        final String verbatimLocality,
                                                        final Integer groupPermittedToView,
                                                        final Locality locality)
    {
        CollectingEvent collectingevent = new CollectingEvent();
        collectingevent.initialize();
        collectingevent.setVerbatimDate(verbatimDate);
        collectingevent.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        collectingevent.setGroupPermittedToView(groupPermittedToView);
        collectingevent.setStartDate(startDate);
        collectingevent.setEndDate(endDate);
        collectingevent.setStationFieldNumber(stationFieldNumber);
        collectingevent.setStartDatePrecision(startDatePrecision);
        collectingevent.setStartDateVerbatim(startDateVerbatim);
        collectingevent.setEndDatePrecision(endDatePrecision);
        collectingevent.setEndDateVerbatim(endDateVerbatim);
        collectingevent.setEndTime(endTime);
        collectingevent.setVerbatimLocality(verbatimLocality);
        collectingevent.setLocality(locality);
        collectingevent.setMethod(method);
        collectingevent.setStartTime(startTime);
        persist(collectingevent);
        return collectingevent;
    }

    public static CollectingEventAttr createCollectingEventAttr(final String strValue,
                                                                final Double dblValue,
                                                                final CollectingEvent collectingEvent,
                                                                final AttributeDef definition)
    {
        CollectingEventAttr collectingeventattr = new CollectingEventAttr();
        collectingeventattr.initialize();
        collectingeventattr.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        collectingeventattr.setCollectingEvent(collectingEvent);
        collectingeventattr.setStrValue(strValue);
        collectingeventattr.setDblValue(dblValue);
        collectingeventattr.setDefinition(definition);
        persist(collectingeventattr);
        return collectingeventattr;
    }

    public static CollectionType createCollectionType(final String name,
                                                          final DataType dataType,
                                                          final SpecifyUser user,
                                                          final GeographyTreeDef geographyTreeDef,
                                                          final GeologicTimePeriodTreeDef geologicTimePeriodTreeDef,
                                                          final LocationTreeDef locationTreeDef,
                                                          final TaxonTreeDef taxonTreeDef)
    {
        CollectionType collectiontype = new CollectionType();
        collectiontype.initialize();
        collectiontype.setDataType(dataType);
        collectiontype.setSpecifyUser(user);
        collectiontype.setGeographyTreeDef(geographyTreeDef);
        collectiontype.setGeologicTimePeriodTreeDef(geologicTimePeriodTreeDef);
        collectiontype.setLocationTreeDef(locationTreeDef);
        collectiontype.setTaxonTreeDef(taxonTreeDef);
        collectiontype.setName(name);
        persist(collectiontype);
        return collectiontype;
    }

    public static CollectionObject createCollectionObject(final String fieldNumber,
                                                          final String description,
                                                          final Integer countAmt,
                                                          final String name,
                                                          final String modifier,
                                                          final Calendar catalogedDate,
                                                          final String catalogedDateVerbatim,
                                                          final String guid,
                                                          //final String altCatalogNumber,
                                                          final Integer groupPermittedToView,
                                                          final Boolean deaccessioned,
                                                          final String catalogNumber,
                                                          final CollectingEvent collectingEvent,
                                                          final Collection collection,
                                                          final Accession accession,
                                                          final Agent cataloger,
                                                          final Container container)
    {
        CollectionObject collectionobject = new CollectionObject();
        collectionobject.initialize();
        collectionobject.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        collectionobject.setFieldNumber(fieldNumber);
        collectionobject.setCountAmt(countAmt);
        collectionobject.setModifier(modifier);
        collectionobject.setCatalogedDate(catalogedDate);
        collectionobject.setCatalogedDateVerbatim(catalogedDateVerbatim);
        collectionobject.setGuid(guid);
        //collectionobject.setAltCatalogNumber(altCatalogNumber);
        collectionobject.setGroupPermittedToView(groupPermittedToView);
        collectionobject.setDeaccessioned(deaccessioned);
        collectionobject.setCatalogNumber(catalogNumber);
        collectionobject.setCollectingEvent(collectingEvent);
        collectionobject.setCollection(collection);
        collectionobject.setAccession(accession);
        collectionobject.setCataloger(cataloger);
        collectionobject.setContainer(container);
        collectionobject.setName(name);
        collectionobject.setDescription(description);
        persist(collectionobject);
        return collectionobject;
    }

    public static CollectionObjectAttr createCollectionObjectAttr(final String strValue,
                                                                  final Double dblValue,
                                                                  final CollectionObject collectionObject,
                                                                  final AttributeDef definition)
    {
        CollectionObjectAttr collectionobjectattr = new CollectionObjectAttr();
        collectionobjectattr.initialize();
        collectionobjectattr.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        collectionobjectattr.setCollectionObject(collectionObject);
        collectionobjectattr.setStrValue(strValue);
        collectionobjectattr.setDblValue(dblValue);
        collectionobjectattr.setDefinition(definition);
        persist(collectionobjectattr);
        return collectionobjectattr;
    }

    public static CollectionObjectCitation createCollectionObjectCitation(final ReferenceWork referenceWork,
                                                                          final CollectionObject collectionObject)
    {
        CollectionObjectCitation collectionobjectcitation = new CollectionObjectCitation();
        collectionobjectcitation.initialize();
        collectionobjectcitation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        collectionobjectcitation.setCollectionObject(collectionObject);
        collectionobjectcitation.setReferenceWork(referenceWork);
        persist(collectionobjectcitation);
        return collectionobjectcitation;
    }

    public static Collector createCollector(final Integer orderNumber,
                                             final CollectingEvent collectingEvent,
                                             final Agent agent)
    {
        Collector collector = new Collector();
        collector.initialize();
        collector.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        collector.setCollectingEvent(collectingEvent);
        collector.setAgent(agent);
        collector.setOrderNumber(orderNumber);
        persist(collector);
        return collector;
    }

    public static Container createContainer(final Short type,
                                            final String name,
                                            final String description,
                                            final Integer number,
                                            final CollectionObject colObj,
                                            final Location location)
    {
        Container container = new Container();
        container.initialize();
        container.setNumber(number);
        container.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        container.getCollectionObjects().add(colObj);
        container.setName(name);
        container.setLocation(location);
        container.setDescription(description);
        container.setType(type);
        persist(container);
        return container;
    }

    public static DataType createDataType(final String name)
    {
        DataType datatype = new DataType();
        datatype.initialize();
        datatype.setName(name);
        persist(datatype);
        return datatype;
    }

    public static Deaccession createDeaccession(final String type,
                                                final String deaccessionNumber,
                                                final Calendar deaccessionDate)
    {
        Deaccession deaccession = new Deaccession();
        deaccession.initialize();
        deaccession.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        deaccession.setDeaccessionNumber(deaccessionNumber);
        deaccession.setDeaccessionDate(deaccessionDate);
        deaccession.setType(type);
        persist(deaccession);
        return deaccession;
    }

    public static DeaccessionAgent createDeaccessionAgent(final String role,
                                                           final Agent agent,
                                                           final Deaccession deaccession)
    {
        DeaccessionAgent deaccessionagent = new DeaccessionAgent();
        deaccessionagent.initialize();
        deaccessionagent.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        deaccessionagent.setRole(role);
        deaccessionagent.setAgent(agent);
        deaccessionagent.setDeaccession(deaccession);
        persist(deaccessionagent);
        return deaccessionagent;
    }

    public static DeaccessionPreparation createDeaccessionPreparation(final Short quantity,
                                                                      final CollectionObject collectionObject,
                                                                      final Deaccession deaccession)
    {
        DeaccessionPreparation deaccessionpreparation = new DeaccessionPreparation();
        deaccessionpreparation.initialize();
        deaccessionpreparation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        deaccessionpreparation.setQuantity(quantity);
        deaccessionpreparation.setDeaccession(deaccession);
        persist(deaccessionpreparation);
        return deaccessionpreparation;
    }

    public static Determination createDetermination(final DeterminationStatus status,
                                                    final String typeStatusName,
                                                    final Calendar determinedDate,
                                                    final String confidence,
                                                    final String method,
                                                    final String featureOrBasis,
                                                    final Taxon taxon,
                                                    final CollectionObject collectionObject,
                                                    final Agent determiner)
    {
        Determination determination = new Determination();
        determination.initialize();
        determination.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        determination.setStatus(status);
        determination.setCollectionObject(collectionObject);
        determination.setTypeStatusName(typeStatusName);
        determination.setDeterminedDate(determinedDate);
        determination.setConfidence(confidence);
        determination.setFeatureOrBasis(featureOrBasis);
        determination.setTaxon(taxon);
        determination.setDeterminer(determiner);
        determination.setMethod(method);
        persist(determination);
        return determination;
    }

    public static DeterminationCitation createDeterminationCitation(final ReferenceWork referenceWork,
                                                                    final Determination determination)
    {
        DeterminationCitation determinationcitation = new DeterminationCitation();
        determinationcitation.initialize();
        determinationcitation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        determinationcitation.setReferenceWork(referenceWork);
        determinationcitation.setDetermination(determination);
        persist(determinationcitation);
        return determinationcitation;
    }

    /*
     public static ExchangeIn createExchangeIn(final Calendar exchangeDate,
     final Short quantityExchanged,
     final String descriptionOfMaterial,
     final Agent agent,
     final Agent agent)
     {
     ExchangeIn exchangein = new ExchangeIn();
     exchangein.initialize();
     exchangein.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
     exchangein.setAgent(agent);
     exchangein.setAgent(agent);
     exchangein.setExchangeDate(exchangeDate);
     exchangein.setQuantityExchanged(quantityExchanged);
     exchangein.setDescriptionOfMaterial(descriptionOfMaterial);
     if (session != null)
     {
     persist(exchangein);
     }
     return exchangein;
     }

     public static ExchangeOut createExchangeOut(final Calendar exchangeDate,
     final Short quantityExchanged,
     final String descriptionOfMaterial,
     final Agent agent,
     final Agent agent,
     final Shipment shipment)
     {
     ExchangeOut exchangeout = new ExchangeOut();
     exchangeout.initialize();
     exchangeout.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
     exchangeout.setAgent(agent);
     exchangeout.setAgent(agent);
     exchangeout.setExchangeDate(exchangeDate);
     exchangeout.setQuantityExchanged(quantityExchanged);
     exchangeout.setDescriptionOfMaterial(descriptionOfMaterial);
     exchangeout.setShipment(shipment);
     if (session != null)
     {
     persist(exchangeout);
     }
     return exchangeout;
     }*/

    public static GroupPerson createGroupPerson(final Short orderNumber,
                                                 final Agent agentByGroup,
                                                 final Agent agentByMember)
    {
        GroupPerson groupperson = new GroupPerson();
        groupperson.initialize();
        groupperson.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        groupperson.setOrderNumber(orderNumber);
        groupperson.setGroup(agentByGroup);
        groupperson.setMember(agentByMember);
        persist(groupperson);
        return groupperson;
    }

    public static InfoRequest createInfoRequest(final Integer infoRequestID,
                                                final String firstName,
                                                final String lastName,
                                                final String institution,
                                                final String email,
                                                final Calendar requestDate,
                                                final Calendar replyDate,
                                                final RecordSetIFace recordSet,
                                                final Agent agent)
    {
        InfoRequest inforequest = new InfoRequest();
        inforequest.initialize();
        inforequest.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        inforequest.setEmail(email);
        inforequest.setAgent(agent);
        inforequest.setFirstName(firstName);
        inforequest.setLastName(lastName);
        inforequest.setInfoRequestID(infoRequestID);
        inforequest.setInstitution(institution);
        inforequest.setRequestDate(requestDate);
        inforequest.setReplyDate(replyDate);
        inforequest.setRecordSet(recordSet);
        persist(inforequest);
        return inforequest;
    }

    public static Journal createJournal(final String journalName, final String journalAbbreviation)
    {
        Journal journal = new Journal();
        journal.initialize();
        journal.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        journal.setJournalName(journalName);
        journal.setJournalAbbreviation(journalAbbreviation);
        persist(journal);
        return journal;
    }

    public static Loan createLoan(final String loanNumber,
                                  final Calendar loanDate,
                                  final Calendar currentDueDate,
                                  final Calendar originalDueDate,
                                  final Calendar dateClosed,
                                  final Boolean isGift,
                                  final Boolean isClosed,
                                  final Shipment shipment)
    {
        Loan loan = new Loan();
        loan.initialize();
        loan.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        //loan.setShipment(shipment);
        loan.addReference(shipment, "shipments");
        loan.setOriginalDueDate(originalDueDate);
        loan.setDateClosed(dateClosed);
        loan.setCurrentDueDate(currentDueDate);
        loan.setLoanNumber(loanNumber);
        loan.setLoanDate(loanDate);
        loan.setIsGift(isGift);
        loan.setIsClosed(isClosed);
        persist(loan);
        return loan;
    }

    public static LoanAgent createLoanAgent(final String role, final Loan loan, final Agent agent)
    {
        LoanAgent loanAgent = new LoanAgent();
        loanAgent.initialize();
        loanAgent.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        loanAgent.setRole(role);
        loanAgent.addReference(agent, "agent");
        //agent.getLoanAgents().add(loanAgent);
        loanAgent.setLoan(loan);
        persist(loanAgent);
        return loanAgent;
    }
//createLoanPreparation((short)quantity, null, null, null, (short)0, (short)0, p, closedLoan);
    public static LoanPreparation createLoanPreparation(final Integer quantity,
                                                              final String descriptionOfMaterial,
                                                              final String outComments,
                                                              final String inComments,
                                                              final Integer quantityResolved,
                                                              final Integer quantityReturned,
                                                              final Preparation preparation,
                                                              final Loan loan)
    {
        LoanPreparation loanpreparation = new LoanPreparation();
        loanpreparation.initialize();
        loanpreparation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        loanpreparation.setDescriptionOfMaterial(descriptionOfMaterial);
        loanpreparation.setQuantity(quantity);
        loanpreparation.setLoan(loan);
        loanpreparation.setPreparation(preparation);
        loanpreparation.setOutComments(outComments);
        loanpreparation.setInComments(inComments);
        loanpreparation.setQuantityResolved(quantityResolved);
        loanpreparation.setQuantityReturned(quantityReturned);
        
        loan.getLoanPreparations().add(loanpreparation);

        persist(loanpreparation);
        return loanpreparation;
    }

    public static LoanReturnPreparation createLoanReturnPreparation(final Calendar returnedDate,
                                                                          final Integer quantity,
                                                                          final LoanPreparation loanPreparation,
                                                                          final DeaccessionPreparation deaccessionPreparation,
                                                                          final Agent agent)
    {
        LoanReturnPreparation loanreturnpreparation = new LoanReturnPreparation();
        loanreturnpreparation.initialize();
        loanreturnpreparation.setReceivedBy(agent);
        loanreturnpreparation.setReturnedDate(returnedDate);
        loanreturnpreparation.setQuantity(quantity);
        loanreturnpreparation.setLoanPreparation(loanPreparation);
        loanreturnpreparation.setDeaccessionPreparation(deaccessionPreparation);
        persist(loanreturnpreparation);
        return loanreturnpreparation;
    }

    public static Locality createLocality(final String namedPlace,
                                          final String relationToNamedPlace,
                                          final String localityName,
                                          final String baseMeridian,
                                          final String range,
                                          final String rangeDirection,
                                          final String township,
                                          final String townshipDirection,
                                          final String section,
                                          final String sectionPart,
                                          final String verbatimElevation,
                                          final String originalElevationUnit,
                                          final Double minElevation,
                                          final Double maxElevation,
                                          final String elevationMethod,
                                          final Double elevationAccuracy,
                                          final Integer originalLatLongUnit,
                                          final String latLongType,
                                          final BigDecimal latitude1,
                                          final BigDecimal longitude1,
                                          final BigDecimal latitude2,
                                          final BigDecimal longitude2,
                                          final String latLongMethod,
                                          final Double latLongAccuracy,
                                          final String datum,
                                          final Integer groupPermittedToView,
                                          final String lat1text,
                                          final String lat2text,
                                          final String long1text,
                                          final String long2text,
                                          final String nationalParkName,
                                          final String islandGroup,
                                          final String island,
                                          final String waterBody,
                                          final String drainage,
                                          final Geography geography)
    {
        Locality locality = new Locality();
        locality.initialize();
        locality.setGroupPermittedToView(groupPermittedToView);
        locality.setNamedPlace(namedPlace);
        locality.setRelationToNamedPlace(relationToNamedPlace);
        locality.setLocalityName(localityName);
        locality.setVerbatimElevation(verbatimElevation);
        locality.setOriginalElevationUnit(originalElevationUnit);
        locality.setMinElevation(minElevation);
        locality.setMaxElevation(maxElevation);
        locality.setElevationMethod(elevationMethod);
        locality.setElevationAccuracy(elevationAccuracy);
        locality.setOriginalLatLongUnit(originalLatLongUnit);
        locality.setLatLongType(latLongType);
        locality.setLatitude1(latitude1);
        locality.setLongitude1(longitude1);
        locality.setLatitude2(latitude2);
        locality.setLongitude2(longitude2);
        locality.setLatLongMethod(latLongMethod);
        locality.setLatLongAccuracy(latLongAccuracy);
        locality.setDatum(datum);
        locality.setLat1text(lat1text);
        locality.setLat2text(lat2text);
        locality.setLong1text(long1text);
        locality.setLong2text(long2text);
        locality.setGeography(geography);
        
        LocalityDetail localityDetail = new LocalityDetail();
        localityDetail.initialize();
        
        localityDetail.setBaseMeridian(baseMeridian);
        localityDetail.setRange(range);
        localityDetail.setRangeDirection(rangeDirection);
        localityDetail.setTownship(township);
        localityDetail.setTownshipDirection(townshipDirection);
        localityDetail.setSection(section);
        localityDetail.setSectionPart(sectionPart);
        localityDetail.setNationalParkName(nationalParkName);
        localityDetail.setIslandGroup(islandGroup);
        localityDetail.setIsland(island);
        localityDetail.setWaterBody(waterBody);
        localityDetail.setDrainage(drainage);

        locality.addReference(localityDetail, "localityDetails");
        
        persist(locality);
        
        return locality;
    }

    public static LocalityCitation createLocalityCitation(final ReferenceWork referenceWork, final Locality locality)
    {
        LocalityCitation localitycitation = new LocalityCitation();
        localitycitation.initialize();
        localitycitation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        localitycitation.setReferenceWork(referenceWork);
        localitycitation.setLocality(locality);
        persist(localitycitation);
        return localitycitation;
    }

    public static OtherIdentifier createOtherIdentifier(final String identifier, final CollectionObject collectionObject)
    {
        OtherIdentifier otheridentifier = new OtherIdentifier();
        otheridentifier.initialize();
        otheridentifier.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        otheridentifier.setCollectionObject(collectionObject);
        otheridentifier.setIdentifier(identifier);
        persist(otheridentifier);
        return otheridentifier;
    }

    public static PrepType createPrepType(final String name)
    {
        PrepType preptype = new PrepType();
        preptype.initialize();
        preptype.setName(name);
        persist(preptype);
        return preptype;
    }

    public static Preparation createPreparation(final Integer count,
                                                final String storageLocation,
                                                final Calendar preparedDate,
                                                final PrepType prepType,
                                                final CollectionObject collectionObject,
                                                final Agent preparedByAgent,
                                                final Location location)
    {
        Preparation preparation = new Preparation();
        preparation.initialize();
        preparation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        preparation.setCount(count);
        preparation.setStorageLocation(storageLocation);
        preparation.setPreparedDate(preparedDate);
        preparation.setPrepType(prepType);
        preparation.setCollectionObject(collectionObject);
        preparation.setPreparedByAgent(preparedByAgent);
        preparation.setLocation(location);
        persist(preparation);
        return preparation;
    }

    public static PreparationAttr createPreparationAttr(final String strValue,
                                                        final Double dblValue,
                                                        final AttributeDef definition,
                                                        final Preparation preparation)
    {
        PreparationAttr preparationattr = new PreparationAttr();
        preparationattr.initialize();
        preparationattr.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        preparationattr.setStrValue(strValue);
        preparationattr.setDblValue(dblValue);
        preparationattr.setDefinition(definition);
        preparationattr.setPreparation(preparation);
        persist(preparationattr);
        return preparationattr;
    }

    public static Project createProject(final String projectName,
                                        final String projectDescription,
                                        final String url,
                                        final Calendar startDate,
                                        final Calendar endDate,
                                        final Agent agent)
    {
        Project project = new Project();
        project.initialize();
        project.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        project.setUrl(url);
        project.setAgent(agent);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setProjectName(projectName);
        project.setProjectDescription(projectDescription);
        persist(project);
        return project;
    }

    public static RecordSetIFace createRecordSet(final Integer recordSetID, final String name, final SpecifyUser owner)
    {
        RecordSet recordset = new RecordSet();
        recordset.initialize();
        recordset.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        recordset.setRecordSetId(recordSetID);
        recordset.setOwner(owner);
        recordset.setName(name);
        persist(recordset);
        return recordset;
    }

    public static ReferenceWork createReferenceWork(final Byte referenceWorkType,
                                                    final String title,
                                                    final String publisher,
                                                    final String placeOfPublication,
                                                    final String workDate,
                                                    final String volume,
                                                    final String pages,
                                                    final String url,
                                                    final String libraryNumber,
                                                    final Boolean isPublished,
                                                    final Journal journal)
    {
        ReferenceWork referencework = new ReferenceWork();
        referencework.initialize();
        referencework.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        referencework.setUrl(url);
        referencework.setReferenceWorkType(referenceWorkType);
        referencework.setPublisher(publisher);
        referencework.setPlaceOfPublication(placeOfPublication);
        referencework.setWorkDate(workDate);
        referencework.setVolume(volume);
        referencework.setPages(pages);
        referencework.setLibraryNumber(libraryNumber);
        referencework.setIsPublished(isPublished);
        referencework.setJournal(journal);
        referencework.setTitle(title);
        if (journal != null)
        {
            journal.getReferenceWorks().add(referencework);
        }
        persist(referencework);
        return referencework;
    }

    public static RepositoryAgreement createRepositoryAgreement(final Division division,
                                                                final String number,
                                                                final String status,
                                                                final Calendar startDate,
                                                                final Calendar endDate,
                                                                final Calendar dateReceived,
                                                                final Agent originator)
    {
        RepositoryAgreement repositoryagreement = new RepositoryAgreement();
        repositoryagreement.initialize();
        repositoryagreement.setDivision(division);
        repositoryagreement.setNumber(number);
        repositoryagreement.setDateReceived(dateReceived);
        repositoryagreement.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        repositoryagreement.setStartDate(startDate);
        repositoryagreement.setEndDate(endDate);
        repositoryagreement.setOriginator(originator);
        repositoryagreement.setStatus(status);
        persist(repositoryagreement);
        return repositoryagreement;
    }

    public static Shipment createShipment(final Calendar shipmentDate,
                                          final String shipmentNumber,
                                          final String shipmentMethod,
                                          final Short numberOfPackages,
                                          final String weight,
                                          final String insuredForAmount,
                                          final Agent shipper,
                                          final Agent shippedTo,
                                          final Agent agent)
    {
        Shipment shipment = new Shipment();
        shipment.initialize();
        shipment.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        shipment.setShippedBy(agent);
        shipment.setShipmentDate(shipmentDate);
        shipment.setShipmentNumber(shipmentNumber);
        shipment.setShipmentMethod(shipmentMethod);
        shipment.setNumberOfPackages(numberOfPackages);
        shipment.setWeight(weight);
        shipment.setInsuredForAmount(insuredForAmount);
        shipment.setShipper(shipper);
        shipment.setShippedTo(shippedTo);
        persist(shipment);
        return shipment;
    }

    public static SpecifyUser createSpecifyUser(final String    name,
                                                final String    email,
                                                final Short     privLevel,
                                                final String    userType)
    {
        SpecifyUser specifyuser = new SpecifyUser();
        specifyuser.initialize();
        specifyuser.setEmail(email);
        specifyuser.setPrivLevel(privLevel);
        //specifyuser.setUserGroup(userGroup);
        specifyuser.setName(name);
        specifyuser.setUserType(userType);
        persist(specifyuser);
        return specifyuser;
    }
    
    public static SpecifyUser createSpecifyUser(final String    name,
                                                final String    email,
                                                final Short     privLevel,
                                                final UserGroup userGroup,
                                                final String    userType)
    {
        SpecifyUser specifyuser = new SpecifyUser();
        specifyuser.initialize();
        specifyuser.setEmail(email);
        specifyuser.setPrivLevel(privLevel);
        //specifyuser.setUserGroup(userGroup);
        specifyuser.setName(name);
        specifyuser.setUserType(userType);
        persist(specifyuser);
        return specifyuser;
    }
    
    public static SpecifyUser createSpecifyUser(final String name,
                                                final String email,
                                                final Short privLevel,
                                                final UserGroup[] userGroups,
                                                final String userType)
    {
        SpecifyUser specifyuser = new SpecifyUser();
        specifyuser.initialize();
        specifyuser.setEmail(email);
        specifyuser.setPrivLevel(privLevel);
        //specifyuser.setUserGroup(userGroup);
        specifyuser.setName(name);
        specifyuser.setUserType(userType);
        if (userGroups!=null) 
        {
            for (UserGroup group : userGroups)
            {
                specifyuser.addUserGroups(group);
            }
        }
        persist(specifyuser);
        return specifyuser;
    }
    
    public static UserPermission createUserPermission(SpecifyUser owner, 
                                                      CollectionType objDef, 
                                                      boolean adminPrivilege, 
                                                      boolean dataAccessPrivilege)
    {
        UserPermission permission = new UserPermission();
        permission.setAdminPrivilege(adminPrivilege);
        permission.setCollectionType(objDef);
        permission.setDataAccessPrivilege(dataAccessPrivilege);
        permission.setSpecifyUser(owner);
        persist(permission);
        return permission;
    }

    public static TaxonCitation createTaxonCitation(final ReferenceWork referenceWork, final Taxon taxon)
    {
        TaxonCitation taxoncitation = new TaxonCitation();
        taxoncitation.initialize();
        taxoncitation.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
        taxoncitation.setTaxon(taxon);
        taxoncitation.setReferenceWork(referenceWork);
        persist(taxoncitation);
        return taxoncitation;
    }

    public static UserGroup createUserGroup(final String name)
    {
        UserGroup usergroup = new UserGroup();
        usergroup.initialize();
        usergroup.setName(name);
        persist(usergroup);
        return usergroup;
    }
    
    public static CatalogNumberingScheme createCatalogNumberingScheme(final String schemeName,
                                                                      final String schemeClassName,
                                                                      final boolean isNumericOnly)
    {
        CatalogNumberingScheme cns = new CatalogNumberingScheme();
        cns.initialize();
        cns.setSchemeName(schemeName);
        cns.setSchemeClassName(schemeClassName);
        cns.setIsNumericOnly(isNumericOnly);
        persist(cns);
        return cns;
    }

    public static Workbench createWorkbench(final SpecifyUser user,
                                            final String name, 
                                            final String remarks, 
                                            final String exportInstName,
                                            final WorkbenchTemplate workbenchTemplate)
    {
        Workbench workbench = new Workbench();
        workbench.initialize();
        
        workbench.setName(name);
        workbench.setSpecifyUser(user);
        workbench.setRemarks(remarks);
        workbench.setExportInstitutionName(exportInstName);

        workbench.setWorkbenchTemplate(workbenchTemplate);

        persist(workbench);

        return workbench;
    }

    public static WorkbenchRow createWorkbenchRow(final Workbench workbench,
                                                  final short rowNumber)
    {
        WorkbenchRow workbenchRow = new WorkbenchRow(workbench, rowNumber);
        // workbench.initialize(); // not needed with this constructor

        persist(workbenchRow);

        return workbenchRow;
    }

    public static WorkbenchDataItem createWorkbenchDataItem(final WorkbenchRow workbenchRow,
                                                            final String       cellData,
                                                            final Integer      columnNumber)
    {
        
        WorkbenchDataItem wbdi = workbenchRow.setData(cellData, columnNumber.shortValue());
        
        if (wbdi != null)
        {
            wbdi.setRowNumber(workbenchRow.getRowNumber());
            persist(wbdi);
            
        } else
        {
            //System.err.println("workbenchRow.setData returned a null DataItem cellData["+cellData+"] or columnNumber["+columnNumber+"]");
        }

        return wbdi;
    }

    public static WorkbenchTemplate createWorkbenchTemplate(final SpecifyUser user,
                                                            final String name, 
                                                            final String remarks)
    {
        WorkbenchTemplate wbt = new WorkbenchTemplate();
        wbt.initialize();

        wbt.setSpecifyUser(user);
        wbt.setName(name);
        wbt.setRemarks(remarks);

        user.getWorkbenchTemplates().add(wbt);
        
        persist(wbt);

        return wbt;
    }

    public static WorkbenchTemplateMappingItem createWorkbenchMappingItem(final String tableName,
                                                                          final Integer tableId,
                                                                          final String fieldName,
                                                                          final String caption,
                                                                          final int     dataLength,
                                                                          final Integer viewOrder,
                                                                          final Integer dataColumnIndex,
                                                                          final WorkbenchTemplate template)
    {
        WorkbenchTemplateMappingItem wtmi = new WorkbenchTemplateMappingItem();
        wtmi.initialize();

        wtmi.setCaption(caption);
        wtmi.setFieldName(fieldName);
        wtmi.setTableName(tableName.toLowerCase());
        wtmi.setViewOrder(viewOrder.shortValue());
        wtmi.setDataFieldLength((short)dataLength);
        wtmi.setOrigImportColumnIndex(dataColumnIndex.shortValue());
        wtmi.setWorkbenchTemplate(template);
        wtmi.setSrcTableId(tableId);
        
        template.getWorkbenchTemplateMappingItems().add(wtmi);
        
        persist(wtmi);

        return wtmi;
    }
    
    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<BldrPickList> getBldrPickLists()
    {
        XStream xstream = new XStream();
        
        xstream.alias("picklist",     BldrPickList.class);
        xstream.alias("picklistitem", BldrPickListItem.class);
        
        /*
        xstream.aliasAttribute(BldrPickList.class, "readonly", "readOnly");
        xstream.aliasAttribute(BldrPickList.class, "tablename", "tableName");
        xstream.aliasAttribute(BldrPickList.class, "sizelimit", "sizeLimit");
        
        xstream.aliasAttribute(BldrPickList.class, "readOnly", "readonly");
        xstream.aliasAttribute(BldrPickList.class, "tableName", "tablename");
        xstream.aliasAttribute(BldrPickList.class, "sizeLimit", "sizelimit");
        
        xstream.aliasField("readonly", BldrPickList.class, "readonly");
        xstream.aliasField("tablename", BldrPickList.class, "tableName");
        xstream.aliasField("sizelimit", BldrPickList.class, "sizeLimit");
        
        xstream.aliasAttribute("readonly", "readonly");
        xstream.aliasAttribute("tablename", "tableName");
        xstream.aliasAttribute("sizelimit", "sizeLimit");
        */
        //xstream.aliasAttribute(RelatedQuery.class, "isActive", "isactive");
        
        xstream.alias("picklist",     BldrPickList.class);
        xstream.alias("picklistitem", BldrPickListItem.class);
        
        xstream.omitField(BldrPickList.class, "pickListId");
        xstream.omitField(BldrPickList.class, "items");
        xstream.omitField(BldrPickList.class, "pickListItems");
        
        xstream.useAttributeFor(BldrPickList.class, "fieldName");
        xstream.useAttributeFor(BldrPickList.class, "sizeLimit");
        xstream.useAttributeFor(BldrPickList.class, "tableName");
        xstream.useAttributeFor(BldrPickList.class, "readOnly");
        xstream.useAttributeFor(BldrPickList.class, "type");
        xstream.useAttributeFor(BldrPickList.class, "formatter");
        xstream.useAttributeFor(BldrPickList.class, "name");
        xstream.useAttributeFor(BldrPickList.class, "isSystem");
        
        xstream.useAttributeFor(BldrPickListItem.class, "title");
        xstream.useAttributeFor(BldrPickListItem.class, "value");
        
        xstream.aliasAttribute("readonly",  "readOnly");
        xstream.aliasAttribute("tablename", "tableName");
        xstream.aliasAttribute("fieldname", "fieldName");
        xstream.aliasAttribute("sizelimit", "sizeLimit");
        xstream.aliasAttribute("issystem",  "isSystem");
        
        String[] omit = {"changes","timestampCreated","timestampModified","createdByAgent","modifiedByAgent","version","valueObject",};
        for (String fld : omit)
        {
            xstream.omitField(DataModelObjBase.class, fld); 
        }
        xstream.omitField(PickListItem.class,       "pickListItemId");
        xstream.omitField(PickListItem.class,       "timestampCreated");
        xstream.omitField(PickListItem.class,       "pickList");

        try
        {
            File pickListFile = new File(XMLHelper.getConfigDirPath("picklist.xml"));
            if (pickListFile.exists())
            {
                System.out.println(FileUtils.readFileToString(pickListFile));
                return (List<BldrPickList>)xstream.fromXML(FileUtils.readFileToString(pickListFile));
            }
            System.out.println("Couldn't find picklist.xml ["+pickListFile.getCanonicalPath()+"]");
            
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     */
    public static void buildPickListFromXML(List<BldrPickList> list)
    {
        if (list != null)
        {
            for (BldrPickList pl : list)
            {
                PickList pickList = createPickList(pl.getName(), pl.getType(), pl.getTableName(), pl.getFieldName(), 
                                                   pl.getFormatter(), pl.getReadOnly(), pl.getSizeLimit(), pl.getIsSystem());
                for (BldrPickListItem item : pl.getItems())
                {
                    pickList.addItem(item.getTitle(), item.getValue());
                }
                persist(pickList);
            }
            
        } else
        {
            try
            {
                System.err.println("Couldn't find file["+(new File("picklist.xml")).getCanonicalPath()+"]");
                
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

    }
    
    /**
     * 
     */
    public static void buildPickListFromXML()
    {
        buildPickListFromXML(getBldrPickLists());
    }

    /**
     * Helper method for saving when there is a session.
     * @param transientObject the object to be saved.
     * 
     */
    public static void persist(Object transientObject)
    {
        if (session != null)
        {
            session.persist(transientObject);
        }
    }

}

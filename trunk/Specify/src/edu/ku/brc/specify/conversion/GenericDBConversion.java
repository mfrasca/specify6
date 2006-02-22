/*
 * Filename:    $RCSfile: FishConversion.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.3 $
 * Date:        $Date: 2005/10/20 12:53:02 $
 *
 * This library is free software; you can redistribute it and/or
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

import static edu.ku.brc.specify.dbsupport.BasicSQLUtils.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;

import  edu.ku.brc.specify.datamodel.*;
import  edu.ku.brc.specify.dbsupport.*;
import edu.ku.brc.specify.helpers.Encryption;

import java.text.SimpleDateFormat;

/**
 * This class is used for copying over the and creating all the tables that are not specify to any one collection
 */
public class GenericDBConversion 
{
    protected static Log log = LogFactory.getLog(GenericDBConversion.class);

    protected static StringBuilder strBuf            = new StringBuilder("");
    protected static Calendar     calendar          = Calendar.getInstance();
    
    /**
     * Constructor
     */
    public GenericDBConversion()
    {

    }
    
    /**
     * Removes all the records from every table in the new database and then copies over 
     * all the tables that have few if any changes to their schema
     */
    public void copyTables()
    {
        
        cleanAllTables(); // from DBCOnnection which is the new DB
       
        DBConnection oldDB = DBConnection.createInstance("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/demo_fish2", "rods", "rods");
       
        String[] tablesToMoveOver = {
                                    "accession",
                                    "accessionagents",
                                    "accessionauthorizations",
                                    "address",
                                    "agent",
                                    "agentaddress",
                                    "authors",
                                    "borrow",
                                    "borrowagents",
                                    "borrowmaterial",
                                    "borrowreturnmaterial",
                                    "borrowshipments",
                                    "catalogseries",
                                    "collectingevent",
                                    //"collectionobject",
                                    "collectionobjectcitation",
                                    "collectors",
                                    "deaccession",
                                    "deaccessionagents",
                                    "deaccessioncollectionobject",
                                    "determination",
                                    "determinationcitation",
                                    "exchangein",
                                    "exchangeout",
                                    "geography",
                                    "geologictimeboundary",
                                    "geologictimeperiod",
                                    "grouppersons",
                                    "image",
                                    "imageagents",
                                    "imagecollectionobjects",
                                    "imagelocalities",
                                    "inforequest",
                                    "journal",
                                    "loan",
                                    "loanagents",
                                    "loanphysicalobject",
                                    "loanreturnphysicalobject",
                                    "locality",
                                    "localitycitation",
                                    "observation",
                                    "otheridentifier",
                                    "permit",
                                    "picklist",
                                    "picklist_items",
                                    //"preparation",
                                    "preptypes",
                                    "project",
                                    "projectcollectionobjects",
                                    "recordset",
                                    "referencework",
                                    "shipment",
                                    "sound",
                                    "soundeventstorage",
                                    "stratigraphy",
                                    "taxoncitation",
                                    "taxonname"
       };
       
       Map<String, Map<String, String>> tableMaps = new Hashtable<String, Map<String, String>>();
       tableMaps.put("determination", createFieldNameMap(new String[] {"CollectionObjectID", "BiologicalObjectID"}));
       
       for (String tableName : tablesToMoveOver)
       {
           if (!copyTable(oldDB.getConnectionToDB(), DBConnection.getConnection(), tableName, tableMaps.get(tableName)))
           {
               break;
           }
       }
    } 
    
    /**
     * Converts all the CollectionObject and CollectionObjectCatalog Records into the new schema CollectionObject table.
     * All "logical" records are moved to the CollectionObject table and all "physical" records are moved to the Preparation table.
     * @return true if no errors
     */
    public boolean createCollectionRecords()
    {
        
        Connection newDBConn = DBConnection.getConnection();
        deleteAllRecordsFromTable(newDBConn, "collectionobject");
        
        DBConnection oldDB     = DBConnection.createInstance("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/demo_fish2", "rods", "rods");
        Connection   oldDBConn = oldDB.getConnectionToDB();
        try 
        {
            Statement    stmt = oldDBConn.createStatement();
            StringBuilder str  = new StringBuilder();
            
            
            
            List<String> oldFieldNames = new ArrayList<String>();
            
            StringBuilder sql = new StringBuilder("Select ");
            List<String> names = new ArrayList<String>();
            getFieldNamesFromSchema(oldDBConn, "collectionobject", names);
            sql.append(buildSelectFieldList(names, "collectionobject"));
            sql.append(", ");
            oldFieldNames.addAll(names);
            
            names.clear();
            getFieldNamesFromSchema(oldDBConn, "collectionobjectcatalog", names);
            sql.append(buildSelectFieldList(names, "collectionobjectcatalog"));
            oldFieldNames.addAll(names);
            
            sql.append(" From collectionobject Inner Join collectionobjectcatalog ON collectionobject.CollectionObjectID = collectionobjectcatalog.CollectionObjectCatalogID Where collectionobject.DerivedFromID Is Null");
            
            System.out.println(sql);
            
            List<String> newFieldNames = new ArrayList<String>();
            getFieldNamesFromSchema(newDBConn, "collectionobject", newFieldNames);
            
            log.info("Number of Fields in New CollectionObject "+newFieldNames.size());
            String sqlStr = sql.toString();
            
            Map<String, Integer> oldNameIndex = new Hashtable<String, Integer>();
            int inx = 0;
            for (String name : oldFieldNames)
            {
                oldNameIndex.put(name, inx++);
            }
            
            ResultSet rs = stmt.executeQuery(sqlStr);
            
            int count = 0;
            while (rs.next()) 
            {                    
                str.setLength(0);
                str.append("INSERT INTO collectionobject VALUES (");
                for (int i=0;i<newFieldNames.size();i++)
                {
                    if (i > 0) str.append(", ");
                    
                    String newFieldName = newFieldNames.get(i);
                    
                    Integer index = oldNameIndex.get(newFieldName);
                    if (index != null)
                    {
                        str.append(getStrValue(rs.getObject(index+1)));
                    } else
                    {
                        log.error("Couldn't find new field name["+newFieldName+"] in old field name Map");
                        stmt.close();
                        oldDBConn.close();
                        newDBConn.close();
                        return false;
                    }

                }
                str.append(")");
                //System.out.println("\n"+str.toString());
                if (count % 1000 == 0) System.out.println(count);
                
                try
                {
                    Statement updateStatement = newDBConn.createStatement();
                    updateStatement.executeUpdate(str.toString());
                    updateStatement.clearBatch();
                    
                } catch (SQLException e)
                {
                    log.error("Count: "+count);
                    e.printStackTrace();
                    log.error(e);
                    return false;
                }
                
                count++;
                //if (count == 1) break;
            }
            System.out.println("Processed CollectionObject "+count+" records.");

            
        } catch (SQLException e)
        {
            e.printStackTrace();
            log.error(e);
            return false;
        }  


        return true;
    }
    
    /**
     * Creates a new User a new User 
     * @param username the user name of the user
     * @param password the password (not emcrypted)
     * @param privLevel the privLevel
     * @return the user object
     */
    public User createNewUser(final String username, final String password, final int privLevel)
    {
     
        try
        {
            Session session = HibernateUtil.getCurrentSession();
            HibernateUtil.beginTransaction();

            User user = new User();
            user.setName(username);
            user.setPassword(Encryption.encrypt(password));
            user.setPrivLevel(privLevel);
            
            session.save(user);
            
            HibernateUtil.commitTransaction();


            return user;
            
        } catch (Exception e)
        {
            log.error("******* " + e);
            HibernateUtil.rollbackTransaction();
        }

        return null;
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
        }
        return retDataType;
    }
    
    /**
     * @param name
     * @param dataType
     * @param user
     * @param taxaTreeDef
     * @param catalogSeries
     * @return
     */
    public Set<Object> createCollectionObjDef(final String          name, 
                                              final DataType        dataType, 
                                              final User            user, 
                                              final TaxonomyTreeDef taxaTreeDef,
                                              final CatalogSeries   catalogSeries)
    {
        try
        {
            Set<Object> catalogSeriesSet = new HashSet<Object>();
            if (catalogSeries != null)
            {
                catalogSeriesSet.add(catalogSeries);
            }
            
            Session session = HibernateUtil.getCurrentSession();
            HibernateUtil.beginTransaction();

            CollectionObjDef colObjDef = new CollectionObjDef();
            colObjDef.setName(name);
            colObjDef.setDataType(dataType);
            colObjDef.setUser(user);
            colObjDef.setTaxonomyTreeDef(taxaTreeDef);
            colObjDef.setCatalogSeries(catalogSeriesSet);
            colObjDef.setAttrsDefs(new HashSet<Object>());
            
            session.save(colObjDef);
            
            Set<Object> set = new HashSet<Object>();
            set.add(colObjDef);
            user.setCollectionObjDef(set);
            session.saveOrUpdate(user);
            
            HibernateUtil.commitTransaction();
           
            
           return set;
            
        } catch (Exception e)
        {
            log.error("******* " + e);
            e.printStackTrace();
            HibernateUtil.rollbackTransaction();
        }
        return null;
    }
}

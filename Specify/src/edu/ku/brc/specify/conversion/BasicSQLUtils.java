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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.ui.ProgressFrame;
import edu.ku.brc.ui.UIHelper;

/**
 * A set of basic utilities that used almost exclusively for converting old Database schemas to the new schema
 *
 * @code_status Unknown (auto-generated)
 * 
 * @author rods
 *
 */
public class BasicSQLUtils
{
    protected static final Logger           log               = Logger.getLogger(BasicSQLUtils.class);
    
    // These are the configuration Options for a View
    public static final int HIDE_ALL_ERRORS         = 0;  // Hhow no errors (Silent)
    public static final int SHOW_NAME_MAPPING_ERROR = 1;  // Show Errors when mapping from Old Name to New Name
    public static final int SHOW_VAL_MAPPING_ERROR  = 2;  // Show Errors when mapping from Old Name to New Name
    public static final int SHOW_NULL_FK            = 4;  // Show Error When a Foreign Key is Null and shouldn't be
    public static final int SHOW_FK_LOOKUP          = 8;  // Show Error when Foreign Key is not null but couldn't be mapped to a new value
    public static final int SHOW_ALL                = SHOW_NAME_MAPPING_ERROR | SHOW_VAL_MAPPING_ERROR | SHOW_FK_LOOKUP | SHOW_NULL_FK;
    
    protected static    int showErrors           = SHOW_ALL;
    
    protected static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    protected static SimpleDateFormat dateFormatter     = new SimpleDateFormat("yyyy-MM-dd");
    protected static Calendar         calendar          = new GregorianCalendar();

    protected static BasicSQLUtils    basicSQLUtils = new  BasicSQLUtils();

    protected static Map<String, String> ignoreMappingFieldNames = null;
    protected static Map<String, String> ignoreMappingFieldIDs   = null;
    protected static Map<String, String> oneToOneIDHash          = null;

    protected static Connection dbConn = null;  // (it may be shared so don't close)
    
    protected static ProgressFrame   frame = null;

    /**
     * Singleton
     */
    protected  BasicSQLUtils()
    {
        //
    }

    public static int getShowErrors()
    {
        return showErrors;
    }

    public static void setShowErrors(final int showErrors)
    {
        BasicSQLUtils.showErrors = showErrors;
    }

    /**
     * Sets the SQL connection
     * @param connection the SQL Connection
     */
    public static void setDBConnection(final Connection connection)
    {
        dbConn = connection;
    }

    /**
     * Sets a UI feedback frame.
     * @param frame the frame
     */
    public static void setFrame(final ProgressFrame frame)
    {
        BasicSQLUtils.frame = frame;
    }
    
    /**
     * Sets min to max.
     * @param min min
     * @param max max
     */
    public static void setProcess(final int min, final int max)
    {
        if (frame != null)
        {
            frame.setProcess(min, max);
        }
    }
    
    /**
     * Sets the value.
     * @param value the value
     */
    public static void setProcess(final int value)
    {
        if (frame != null)
        {
            frame.setProcess(value);
        }
    }
    
    
    /**
     * @param oneToOneIDHash the oneToOneIDHash to set
     */
    public static void setOneToOneIDHash(Map<String, String> oneToOneIDHash)
    {
        BasicSQLUtils.oneToOneIDHash = oneToOneIDHash;
    }

    /**
     * Creates or clears and fills a list
     * @param fieldNames the list of names, can be null then the list is cleared and nulled out
     * @param ignoreMap the map to be be crated or cleared and nulled
     * @return the same map or a newly created one
     */
    protected static Map<String, String> configureIgnoreMap(final String[] fieldNames, final Map<String, String> ignoreMap)
    {
        Map<String, String> ignoreMapLocal = ignoreMap;
        if (fieldNames == null)
        {
            if (ignoreMapLocal != null)
            {
                ignoreMapLocal.clear();
                ignoreMapLocal = null;
            }
        } else
        {
            if (ignoreMapLocal == null)
            {
                ignoreMapLocal  = UIHelper.createMap();
            } else
            {
                ignoreMapLocal.clear();
            }
            log.info("Ignore these Field Names when mapping:");
            for (String name : fieldNames)
            {
                ignoreMapLocal.put(name, "X");
                log.info(name);
            }
        }
        return ignoreMapLocal;
    }

    /**
     * Sets a list of field names to ignore when mapping database tables from new names to old names
     * @param fieldNames the list of names to ignore
     */
    public static void setFieldsToIgnoreWhenMappingNames(final String[] fieldNames)
    {
        ignoreMappingFieldNames = configureIgnoreMap(fieldNames, ignoreMappingFieldNames);
    }

    /**
     * Sets a list of field names to ignore when mapping IDs
     * @param fieldNames the list of names to ignore
     */
    public static void setFieldsToIgnoreWhenMappingIDs(final String[] fieldNames)
    {
        ignoreMappingFieldIDs = configureIgnoreMap(fieldNames, ignoreMappingFieldIDs);
    }

    /**
     * Executes an SQL Update command
     * @param stmt Statement object to execute the SQL
     * @param cmdStr the SQL string to execute
     * @return the return code from the executeUpdate call
     */
    public static int exeUpdateCmd(Statement stmt, String cmdStr)
    {
        try
        {
            //log.info(cmdStr);
            return stmt.executeUpdate(cmdStr);

        } catch (Exception ex)
        {
            //e.printStackTrace();
            log.error(ex.getStackTrace().toString());
            log.error(cmdStr+"\n");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //return -1;
    }

    /**
     * Deletes all the records from a table
     * @param tableName the name of the table
     * @return the return value from the SQL update statment (or -1 on an exception)
     */
    public static int deleteAllRecordsFromTable(final String tableName)
    {
        int count = 0;

        try
        {
            Connection connection = dbConn != null ? dbConn : DBConnection.getInstance().createConnection();

            count = deleteAllRecordsFromTable(connection, tableName);

            if (dbConn == null)
            {
                connection.close();
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return count;
    }

    /**
     * Deletes all the records from a table
     * @param connection connection to the DB
     * @param tableName the name of the table
     * @return the return value from the SQL update statment (or -1 on an exception)
     */
    public static int deleteAllRecordsFromTable(final Connection connection, final String tableName)
    {
        try
        {
            int count = 0;
            Statement cntStmt = connection.createStatement();
            ResultSet rs      = cntStmt.executeQuery("select count(*) from "+tableName);
            if (rs.first())
            {
                count = rs.getInt(1);
            }
            rs.close();
            cntStmt.close();

            Statement stmt = connection.createStatement();
            exeUpdateCmd(stmt, "SET FOREIGN_KEY_CHECKS = 0");
            int retVal = exeUpdateCmd(stmt, "delete from "+tableName);
            stmt.clearBatch();
            stmt.close();

            log.info("Deleted "+count+" records from "+tableName);

            return retVal;

        } catch (SQLException ex)
        {
            log.error(ex);
            ex.printStackTrace();

        }
        return -1;
    }

    /**
     * Removes all the records from all the tables from the current DBConnection
     */
    public static void cleanAllTables()
    {
        try
        {
            Connection connection = dbConn != null ? dbConn : DBConnection.getInstance().createConnection();

            cleanAllTables(connection);

            if (dbConn == null)
            {
                connection.close();
            }

        } catch (SQLException ex)
        {
            //e.printStackTrace();
            log.error(ex);
        }
    }

    /**
     * Removes all the records from all the tables
     */
    public static List<String> getTableNames(final Connection connection)
    {
        List<String> names = new Vector<String>();
        try
        {
            Statement  stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("show tables");
            if (rs.first())
            {
                do
                {
                    names.add(rs.getString(1));
                } while (rs.next());
            }
            rs.close();

            stmt.clearBatch();
            stmt.close();

        } catch (SQLException ex)
        {
            log.error(ex);
            ex.printStackTrace();
        }
        return names;
    }

    /**
     * Removes all the records from all the tables
     */
    public static void cleanAllTables(final Connection connection)
    {
        try
        {
            Statement  stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("show tables");
            if (rs.first())
            {
                do
                {
                    String tableName = rs.getString(1);
                    //System.out.println("Deleting Records from "+tableName);
                    deleteAllRecordsFromTable(connection, tableName);
                } while (rs.next());
            }
            rs.close();

            stmt.clearBatch();
            stmt.close();

        } catch (SQLException ex)
        {
            log.error(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Returns a valid String value for an Object, meaning it will put quotes around Strings and ate etc.
     * @param obj the object to convert
     * @return the string representation
     */
    public static String getStrValue(Object obj)
    {
        return getStrValue(obj, null);
    }


    /**
     * Returns a valid String value for an Object, meaning it will put quotes around Strings and ate etc.
     * @param obj the object to convert
     * @return the string representation
     */
    public static String getStrValue(final Object obj, final String newFieldType)
    {
        if (obj == null)
        {
            return "NULL";

        } else if (obj instanceof String)
        {
            String str = (String)obj;
            if (str.indexOf('"') > -1 || str.indexOf('\\') > -1)
            {
                str = StringEscapeUtils.escapeJava(str);
            }
            return '"'+str+'"';

        } else if (obj instanceof Integer)
        {
            if (newFieldType != null)
            {
                if (newFieldType.indexOf("date") ==  0)
                {
                    Date dateObj = UIHelper.convertIntToDate((Integer)obj);                   
                    return dateObj == null ? "NULL" : '"'+dateFormatter.format(dateObj) + '"';

                }
                else if (newFieldType.equalsIgnoreCase("bit(1)") || newFieldType.equalsIgnoreCase("tinyint(1)"))
                {
                    int val = ((Integer)obj).intValue();
                    return Integer.toString(val == 0? 0 : 1);
                }
                return ((Integer)obj).toString();
            }
            return ((Integer)obj).toString();

        } else if (obj instanceof Date)
        {
            return '"'+dateTimeFormatter.format((Date)obj) + '"';

        } else if (obj instanceof Float)
        {
            return ((Float)obj).toString();

        } else if (obj instanceof Double)
        {
            return ((Double)obj).toString();

        } else if (obj instanceof Character)
        {
            return '"'+((Character)obj).toString()+'"';
        } else
        {
            return obj.toString();
        }
    }

   /**
     * Fills the list with all the names of the table
     * @param connection the connection
     * @param tableName the table name
     * @param list the list to be filled
     */
    public static void getFieldNamesFromSchema(final Connection connection,
                                               final String tableName,
                                               final List<String> list)
    {
        try
        {
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery("describe "+tableName);
            while (rs.next())
            {
                list.add(rs.getString(1));
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex)
        {
            log.error(ex);
        }
    }

    /**
     * Fills the list with FieldMetaData objects for each field in the table
     * @param connection the connection
     * @param tableName the table name
     * @param fieldList the list to be filled with field/type objects (FieldMetaData)
     */
    public static void getFieldMetaDataFromSchema(final Connection          connection,
                                                  final String              tableName,
                                                  final List<FieldMetaData> fieldList)
    {
        try
        {
            Statement stmt = connection.createStatement();
            ResultSet rs   = stmt.executeQuery("describe "+tableName);
            while (rs.next())
            {
                fieldList.add(basicSQLUtils.new FieldMetaData(rs.getString(1), rs.getString(2)));
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex)
        {
            log.error(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Fills the list with FieldMetaData objects for each field in the table
     * @param rsmd the resultset's meta data
     * @param fieldList the list to be filled with field/type objects (FieldMetaData)
     */
    public static void getFieldMetaDataFromSchema(final ResultSetMetaData rsmd,
                                                  final List<FieldMetaData> fieldList)
    {
        try
        {
            StringBuilder strBuf = new StringBuilder(128);
            for (int i=1;i<=rsmd.getColumnCount();i++)
            {
                strBuf.setLength(0);
                String tableName = rsmd.getTableName(i);
                if (StringUtils.isNotEmpty(tableName))
                {
                    strBuf.append(tableName);
                    strBuf.append(".");
                }
                strBuf.append(rsmd.getColumnName(i));
                fieldList.add(basicSQLUtils.new FieldMetaData(strBuf.toString(), rsmd.getColumnClassName(i)));
            }

        } catch (SQLException ex)
        {
            log.error(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Converts an integer time in the form of YYYYMMDD to the proper Date
     * @param iDate the int to be converted
     * @return the date object
     * @return the verbatimDate strin that holds old invalid verbatim dates
     */
    public static Date convertIntToDate(final int iDate, final StringBuilder verbatimDate)
    {
        calendar.clear();

        int year  = iDate / 20000;
        if (year > 1600)
        {
            int tmp   = (iDate - (year * 20000));
            int month = tmp / 100;
            int day   = (tmp - (month * 100));

            if (month == 0 || day == 0)
            {
                verbatimDate.setLength(0);
                verbatimDate.append(Integer.toString(iDate));
                if (month == 0)
                {
                    month = 7;
                }
                if (day == 0)
                {
                    day = 1;
                }
            }

            calendar.set(year, month-1, day);
        } else
        {
            calendar.setTimeInMillis(0);
        }

        return calendar.getTime();
    }

    /**
     * Copies a table from one DB to another
     */
    /**
     * Copies a a table with the same name from one DB to another
     * @param fromConn the "from" DB
     * @param toConn the "to" DB
     * @param tableName the table name to be copied
     * @param colNewToOldMap a map of new file names toold file names
     * @param verbatimDateMapper a map from the new Vertbatim Date Field to the new date column name it is associated with
     * @return true if successful
     */
    public static boolean copyTable(final Connection fromConn,
                                    final Connection toConn,
                                    final String     tableName,
                                    final Map<String, String> colNewToOldMap,
                                    final Map<String, String> verbatimDateMapper)
    {
        return copyTable(fromConn, toConn, "select * from " + tableName, tableName, tableName, colNewToOldMap, verbatimDateMapper);
    }
    /**
     * Copies a table to a new table of a different name (same schema) within the same DB Connection
     * @param conn a connection to copy from one table to another in the same database
     * @param fromTableName the table name its coming from
     * @param toTableName the table name it is going to
     * @param colNewToOldMap a map of new file names toold file names
     * @param verbatimDateMapper a map from the new Vertbatim Date Field to the new date column name it is associated with
     * @return true if successful
     */
    public static boolean copyTable(final Connection fromConn,
                                    final Connection toConn,
                                    final String     fromTableName,
                                    final String     toTableName,
                                    final Map<String, String> colNewToOldMap,
                                    final Map<String, String> verbatimDateMapper)
    {
        return copyTable(fromConn, toConn, "select * from " + fromTableName, fromTableName, toTableName, colNewToOldMap, verbatimDateMapper);
    }
    
    /**
     * Copies a table to a new table of a different name (same schema) within the same DB Connection
     * @param conn a connection to copy from one table to another in the same database
     * @param fromTableName the table name its coming from
     * @param toTableName the table name it is going to
     * @param colNewToOldMap a map of new file names toold file names
     * @param verbatimDateMapper a map from the new Vertbatim Date Field to the new date column name it is associated with
     * @return true if successful
     */
    public static boolean copyTable(final Connection conn,
                                    final String     fromTableName,
                                    final String     toTableName,
                                    final Map<String, String> colNewToOldMap,
                                    final Map<String, String> verbatimDateMapper)
    {
        return copyTable(conn, conn, "select * from " + fromTableName, fromTableName, toTableName, colNewToOldMap, verbatimDateMapper);
    }

    /**
     * Copies from one connection/table to another connection/table. Sets the order by clause to be the first field in the
     * "from" field list.
     *
     * @param fromConn DB Connection that the data is coming from
     * @param toConn Connection that the data is going to
     * @param sql the SQL to be executed
     * @param fromTableName the table name its coming from
     * @param toTableName the table name it is going to
     * @param colNewToOldMap a map of new file names to old file names
     * @param verbatimDateMapper a map from the new Vertbatim Date Field to the new date column name it is associated with
     * @return true if successful
     */
    public static boolean copyTable(final Connection fromConn,
                                    final Connection toConn,
                                    final String     sql,
                                    final String     fromTableName,
                                    final String     toTableName,
                                    final Map<String, String> colNewToOldMap,
                                    final Map<String, String> verbatimDateMapper)
    {
        return copyTable(fromConn,toConn,sql,fromTableName,toTableName,colNewToOldMap,verbatimDateMapper,null);
    }

    public static boolean copyTable(final Connection fromConn,
                                    final Connection toConn,
                                    final String     sql,
                                    final String     fromTableName,
                                    final String     toTableName,
                                    final Map<String, String> colNewToOldMap,
                                    final Map<String, String> verbatimDateMapper,
                                    final Map<String, String> newColDefValues)
    {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        IdMapperMgr idMapperMgr = IdMapperMgr.getInstance();

        if (frame != null)
        {
            frame.setDesc("Copying Table "+fromTableName);
        }

        List<String> fromFieldNameList = new ArrayList<String>();
        getFieldNamesFromSchema(fromConn, fromTableName, fromFieldNameList);

        String sqlStr = sql + " order by " +  fromTableName + "." + fromFieldNameList.get(0);
        
        setProcess(0, getNumRecords(fromConn, fromTableName));

        String id = "";
        try
        {
            List<FieldMetaData> colMetaData = new ArrayList<FieldMetaData>();
            getFieldMetaDataFromSchema(toConn, toTableName, colMetaData);

            Statement         stmt = fromConn.createStatement();
            ResultSet         rs   = stmt.executeQuery(sqlStr);
            ResultSetMetaData rsmd = rs.getMetaData();

            Hashtable<String, Integer> fromHash = new Hashtable<String, Integer>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                fromHash.put(rsmd.getColumnName(i), i);
            }
            // System.out.println("Num Cols: "+rsmd.getColumnCount());

            Map<String, String>  vertbatimDateMap = UIHelper.createMap();
            Map<String, Date>    dateMap          = new Hashtable<String, Date>();

            // Get the columns that have dates in case we get a TimestampCreated date that is null
            // and then we can go looking for an older date to try to figure it out
            Integer timestampModifiedInx = fromHash.get("TimestampModified");
            Integer timestampCreatedInx  = fromHash.get("TimestampCreated");
            boolean isAccessionTable     = fromTableName.equals("accession");

            StringBuilder verbatimDateStr = new StringBuilder(1024);
            StringBuffer  str             = new StringBuffer(1024);
            int           count           = 0;
            while (rs.next())
            {
                if (verbatimDateMapper != null)
                {
                    // Start by going through the resultset and converting all dates from Integers
                    // to real dates and keep the verbatium date information if it is a partial date
                    for (int i = 1; i <= rsmd.getColumnCount(); i++)
                    {
                        String  oldColName  = rsmd.getColumnName(i);
                        Integer columnIndex = fromHash.get(oldColName);

                        if (columnIndex == null)
                        {
                            log.error("Couldn't find new column for old column for date for Table[" + fromTableName + "] Col Name[" + colMetaData.get(i).getName() + "]");
                            continue;
                        }

                        String newColName = colMetaData.get(columnIndex).getName();

                        Object dataObj = rs.getObject(i);
                        if (dataObj instanceof Integer && newColName.toLowerCase().indexOf("date") ==  0)
                        {
                            Date date = convertIntToDate((Integer)dataObj, verbatimDateStr);
                            dateMap.put(newColName, date);

                            if (verbatimDateStr.length() > 0)
                            {
                                vertbatimDateMap.put(newColName, verbatimDateStr.toString());
                            } else
                            {
                                log.error("No Verbatim Date Mapper for Table[" + fromTableName + "] Col Name[" + colMetaData.get(i).getName() + "]");
                            }
                        }
                    }
                }

                str.setLength(0);
                str.append("INSERT INTO " + toTableName + " VALUES (");
                
                // OK here we make sure that both the created dated ad modified date are not null
                // and we copy the date if one has a value and the other does not.
                Date timestampCreatedCached  = now;
                Date timestampModifiedCached = now;
                
                if (timestampModifiedInx != null && timestampCreatedInx != null)
                {
                    timestampModifiedCached = rs.getDate(timestampModifiedInx);
                    timestampCreatedCached  = rs.getDate(timestampCreatedInx);
                    if (timestampModifiedCached == null && timestampCreatedCached == null)
                    {
                        timestampCreatedCached  = Calendar.getInstance().getTime();
                        timestampModifiedCached = Calendar.getInstance().getTime();
                        
                    } else if (timestampModifiedCached == null && timestampCreatedCached != null)
                    {
                        timestampModifiedCached = new Date(timestampCreatedCached.getTime());
                    } else
                    {
                        timestampCreatedCached = timestampModifiedCached != null ? new Date(timestampModifiedCached.getTime()) : new Date();
                    }
                } else 
                {
                    
                    if (timestampModifiedInx != null)
                    {
                        timestampModifiedCached = rs.getDate(timestampModifiedInx);
                        if (timestampModifiedCached == null)
                        {
                            timestampModifiedCached = now;
                        }
                    }
                        
                    if (timestampCreatedInx != null)
                    {
                        timestampCreatedCached = rs.getDate(timestampCreatedInx);
                        if (timestampCreatedCached == null)
                        {
                            timestampCreatedCached = now;
                        }
                    }
                }
                    

                id = rs.getString(1);

                // For each column in the new DB table...
                for (int i = 0; i < colMetaData.size(); i++)
                {
                    FieldMetaData fieldMetaData = colMetaData.get(i);
                    String colName          = fieldMetaData.getName();
                    String oldMappedColName = null;

                    // Get the Old Column Index from the New Name
                    Integer columnIndex = fromHash.get(colName);
                    if (colName.startsWith("Habitat"))
                    {
                        int x = 0;
                        x++;
                    }
                    
                    if (columnIndex == null && colNewToOldMap != null)
                    {
                        oldMappedColName = colNewToOldMap.get(colName);
                        if (oldMappedColName != null)
                        {
                            columnIndex = fromHash.get(oldMappedColName);

                        } else if (isOptionOn(SHOW_NAME_MAPPING_ERROR) &&
                                   (ignoreMappingFieldNames == null || ignoreMappingFieldNames.get(colName) == null))
                        {
                            log.error("No Map for table ["+fromTableName+"] from New Name[" + colName + "] to Old Name["+oldMappedColName+"]");
                        }
                    } else
                    {
                        oldMappedColName = colName;
                    }

                    if (columnIndex != null)
                    {
                        if (i > 0) str.append(", ");
                        Object dataObj = rs.getObject(columnIndex);
                        
                        if (idMapperMgr != null && oldMappedColName != null && oldMappedColName.endsWith("ID"))
                        {
                            IdMapperIFace idMapper = idMapperMgr.get(fromTableName, oldMappedColName);
                            if (idMapper != null)
                            {

                                int oldPrimaryKeyId = rs.getInt(columnIndex);

                                // if the value was null, getInt() returns 0
                                // use wasNull() to distinguish real 0 from a null return
                                if( rs.wasNull())
                                {
                                    dataObj = null;
                                    
                                    if (isOptionOn(SHOW_NULL_FK))
                                    {
                                        log.error("Unable to Map Primary Id[NULL] old Name["+oldMappedColName+"] ["+columnIndex+"]");
                                    }
                                }
                                else
                                {
                                    dataObj = idMapper.get(oldPrimaryKeyId);
                                    
                                    if (dataObj == null && isOptionOn(SHOW_FK_LOOKUP))
                                    {
                                        log.error("Unable to Map Primary Id["+oldPrimaryKeyId+"] old Name["+oldMappedColName+"]");
                                    }
                                }

                            } else
                            {
                                if (isOptionOn(SHOW_NAME_MAPPING_ERROR) && ignoreMappingFieldIDs == null || ignoreMappingFieldIDs.get(oldMappedColName) == null)
                                {
                                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                    // XXX Temporary fix so it doesn't hide other errors
                                    // Josh has promised his first born if he doesn't fix this!
                                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                    if (!oldMappedColName.equals("RankID"))
                                    {
                                        idMapperMgr.dumpKeys();
                                        log.error("No ID Map for ["+fromTableName+"] Old Column Name["+oldMappedColName+"]");
                                    }
                                }
                            }
                        }

                        // First check to see if it is null
                        if (dataObj == null)
                        {
                            if (fieldMetaData.getName().equals("TimestampCreated"))
                            {
                                if (timestampCreatedInx != null)
                                {
                                    if (isAccessionTable)
                                    {
                                        str.append(getStrValue(UIHelper.convertIntToDate(rs.getInt(fromHash.get("DateAccessioned")))));
    
                                    } else
                                    {
                                        str.append(getStrValue(timestampCreatedCached, fieldMetaData.getType()));
                                    }
    
                                } else
                                {
                                    str.append(getStrValue(timestampCreatedCached, fieldMetaData.getType()));
                                }
    
                            } else if (fieldMetaData.getName().equals("TimestampModified"))
                            {
                                if (timestampModifiedInx != null)
                                {
                                    if (isAccessionTable)
                                    {
                                        str.append(getStrValue(UIHelper.convertIntToDate(rs.getInt(fromHash.get("DateAccessioned")))));
    
                                    } else
                                    {
                                        str.append(getStrValue(timestampModifiedCached, fieldMetaData.getType()));
                                    }
    
                                } else
                                {
                                    str.append(getStrValue(timestampModifiedCached, fieldMetaData.getType()));
                                }
                            } else
                            {
                                str.append("NULL");
                            }
                                

                        } else if (dataObj instanceof Integer && colName.toLowerCase().indexOf("date") ==  0 && verbatimDateMapper != null)
                        {
                            // First check to see if the current column name is that of the verbatim field
                            // it will return the new schema's date field name that this verbatim field is associated with
                            String dateFieldName = verbatimDateMapper.get(colName); // from verbatim to associated date field
                            if (dateFieldName != null)
                            {
                                str.append(getStrValue(vertbatimDateMap.get(colName)));

                            } else
                            {
                                str.append(getStrValue(dateMap.get(colName)));
                            }

                        } else 
                        {
                            str.append(getStrValue(dataObj, fieldMetaData.getType()));
                        }

                    } else if (idMapperMgr != null && colName.endsWith("ID") && oneToOneIDHash != null && oneToOneIDHash.get(colName) != null)
                    {
                        
                        IdMapperIFace idMapper = idMapperMgr.get(toTableName, colName);
                        if (idMapper != null)
                        {
                            idMapper.setShowLogErrors(false);
                            Integer newPrimaryId = idMapper.get(Integer.parseInt(id));
                            if (newPrimaryId != null)
                            {
                                if (i > 0) str.append(", ");
                                str.append(newPrimaryId);
                            } else
                            {
                                if (i > 0) str.append(", ");
                                str.append("NULL");
                                
                                if (isOptionOn(SHOW_VAL_MAPPING_ERROR))
                                {
                                    log.error("For Table[" + fromTableName + "] mapping new Column Name[" + colName + "] ID["+id+"] was not mapped");
                                }
                            }
                        }
                    }
                    else // there was no old column that maps to this new column
                    {
                        String newColValue = null;
                        if (newColDefValues!=null)
                        {
                            newColValue = newColDefValues.get(colName);
                        }
                        if (newColValue==null)
                        {
                            newColValue = "NULL";
                            //System.out.println("ignoreMappingFieldNames" + ignoreMappingFieldNames);
                            //System.out.println("ignoreMappingFieldNames.get(colName)" + ignoreMappingFieldNames.get(colName));
                            if (isOptionOn(SHOW_NAME_MAPPING_ERROR) &&
                                (ignoreMappingFieldNames == null || ignoreMappingFieldNames.get(colName) == null))
                            {
                                log.error("For Table[" + fromTableName + "] mapping new Column Name[" + colName + "] was not mapped");
                            }
                        }
                        if (i > 0) str.append(", ");
                        str.append(newColValue);
                    }

                }
                str.append(")");
                if (frame != null)
                {
                    if (count % 500 == 0)
                    {
                        frame.setProcess(count);
                    }
                    
                } else
                {
                    if (count % 2000 == 0)
                    {
                        log.info(toTableName + " processed: " + count);
                    }                        
                }
                Statement updateStatement = toConn.createStatement();
                exeUpdateCmd(updateStatement, "SET FOREIGN_KEY_CHECKS = 0");
                int retVal = exeUpdateCmd(updateStatement, str.toString());
                updateStatement.clearBatch();
                updateStatement.close();

                if (retVal == -1)
                {
                    rs.close();
                    stmt.clearBatch();
                    stmt.close();
                    return false;
                }
                count++;
                // if (count == 1) break;
            }
            
            if (frame != null)
            {
                frame.setProcess(count);
               
            } else
            {
                log.info(fromTableName + " processed " + count + " records.");         
            }            

            rs.close();
            stmt.clearBatch();
            stmt.close();

        } catch (SQLException ex)
        {
            //e.printStackTrace();
            log.error(sqlStr);
            log.error(ex);
            log.error("ID: " + id);
        }
        BasicSQLUtils.setFieldsToIgnoreWhenMappingNames(null);//meg added
        return true;
    }
    
    /**
     * Takes a list of names and creates a string with the names comma separated
     * @param list the list of names (or field names)
     * @return the string of comma separated names
     */
    public static String buildSelectFieldList(final List<String> list, final String tableName)
    {
        StringBuffer str = new StringBuffer();
        for (int i=0;i<list.size();i++)
        {
            if (i > 0) str.append(", ");

            if (tableName != null)
            {
                str.append(tableName);
                str.append('.');
            }

            str.append(list.get(i));
        }
        return str.toString();
    }

    /**
     * Takes a list of names and creates a string with the names comma separated
     * @param list the list of names (or field names)
     * @return the string of comma separated names
     */
    public static String buildSelectFieldMetaDataList(final List<FieldMetaData> list, final String tableName)
    {
        StringBuffer str = new StringBuffer();
        for (int i=0;i<list.size();i++)
        {
            if (i > 0) str.append(", ");

            if (tableName != null)
            {
                str.append(tableName);
                str.append('.');
            }

            str.append(list.get(i).getName());
        }
        return str.toString();
    }

    /**
     * Creates a mapping of the new name to the old name
     * @param pairs array of pairs of names
     * @return the map object
     */
    public static Map<String, String> createFieldNameMap(String[] pairs)
    {
        Map<String, String> map = new Hashtable<String, String>();

        for (int i=0;i<pairs.length;i++)
        {
            map.put(pairs[i], pairs[i+1]);
            i++;
        }
        return map;
    }

    /**
     * Returns the number of records in a table
     * @param connection db connection
     * @param tableName the name of the table
     * @return the number of records in a table
     */
    public static int getNumRecords(final Connection connection, final String tableName)
    {
        try
        {
            int count = 0;
            Statement cntStmt = connection.createStatement();
            ResultSet rs      = cntStmt.executeQuery("select count(*) from "+tableName);
            if (rs.first())
            {
                count = rs.getInt(1);
            }
            rs.close();
            cntStmt.close();

            return count;

        } catch (SQLException ex)
        {
            log.error(ex);
        }
        return -1;
    }

    /**
     * Returns the last ID that was inserted into the database
     * @param connection db connection
     * @param tableName the name of the table
     * @param idColName primary key column name
     * @return the last ID that was inserted into the database
     */
    public static int getHighestId(final Connection connection, final String idColName, final String tableName)
    {
        try
        {
            Statement cntStmt = connection.createStatement();
            ResultSet rs      = cntStmt.executeQuery("select "+idColName+" from "+tableName+" order by "+idColName+" asc");
            int id = 0;
            if (rs.last())
            {
                id = rs.getInt(1);
            } else
            {
                id = 1;
            }
            rs.close();
            cntStmt.close();

            return id;

        } catch (SQLException ex)
        {
            log.error(ex);
        }
        return -1;
    }

    public static boolean isOptionOn(final int opt)
    {
        return (showErrors & opt) == opt;
    }

    //-----------------------------------------------------------------------
    //-- Inner Classes
    //-----------------------------------------------------------------------
    public class FieldMetaData
    {
        protected String name;
        protected String type;

        public FieldMetaData(String name, String type)
        {
            this.name = name;
            this.type = type;
        }

        public String getName()
        {
            return name;
        }

        public String getType()
        {
            return type;
        }
    }

}

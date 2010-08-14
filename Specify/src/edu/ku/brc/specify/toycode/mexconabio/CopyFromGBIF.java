/* Copyright (C) 2009, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.specify.toycode.mexconabio;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.specify.conversion.BasicSQLUtils;

public class CopyFromGBIF
{

    private Connection dbConn    = null;
    private Connection srcDBConn = null;
    
    @SuppressWarnings("unused")
    private String     dbName;
    @SuppressWarnings("unused")
    private String     srcDBName = null;
    
    /**
     * @param server
     * @param port
     * @param dbName
     * @param username
     * @param pwd
     */
    public CopyFromGBIF()
    {
        super();
     }
    
    /**
     * @param server
     * @param port
     * @param dbName
     * @param username
     * @param pwd
     */
    public void createDBConnection(final String server, 
                                   final String port, 
                                   final String dbName, 
                                   final String username, 
                                   final String pwd)
    {
        this.dbName = dbName;

        String connStr = "jdbc:mysql://%s:%s/%s?characterEncoding=UTF-8&autoReconnect=true";
        try
        {
            dbConn = DriverManager.getConnection(String.format(connStr, server, port, dbName),
                    username, pwd);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
        
    
    /**
     * @param server
     * @param port
     * @param dbName
     * @param username
     * @param pwd
     */
    public void createSrcDBConnection(final String server, 
                                      final String port, 
                                      final String dbName, 
                                      final String username, 
                                      final String pwd)
    {

        this.srcDBName = dbName;
        
        String connStr = "jdbc:mysql://%s:%s/%s?characterEncoding=UTF-8&autoReconnect=true";
        try
        {
            srcDBConn = DriverManager.getConnection(String.format(connStr, server, port, dbName), username, pwd);
            
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
        
    
    /**
     * 
     */
    public void process()
    {
        
        String pSQL = "INSERT INTO raw (old_id,data_provider_id,data_resource_id,resource_access_point_id, institution_code, collection_code, " +
                      "catalogue_number, scientific_name, author, rank, kingdom, phylum, class, order_rank, family, genus, species, subspecies, latitude, longitude,  " +
                      "lat_long_precision, max_altitude, min_altitude, altitude_precision, min_depth, max_depth, depth_precision, continent_ocean, country, state_province, county, collector_name, " + 
                      "locality,year, month, day, basis_of_record, identifier_name, identification_date,unit_qualifier, created, modified, deleted, collector_num) " +
                      "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        String gbifSQL = "SELECT r.id, r.data_provider_id, r.data_resource_id, r.resource_access_point_id, r.institution_code, r.collection_code, " +
                         "r.catalogue_number, r.scientific_name, r.author, r.rank, r.kingdom, r.phylum, r.class, r.order_rank, r.family, r.genus, r.species, r.subspecies, " +
                         "r.latitude, r.longitude, r.lat_long_precision, r.max_altitude, r.min_altitude, r.altitude_precision, r.min_depth, r.max_depth, r.depth_precision, " +
                         "r.continent_ocean, r.country, r.state_province, r.county, r.collector_name, r.locality, r.year, r.month, r.day, r.basis_of_record, r.identifier_name, " +
                         "r.identification_date, r.unit_qualifier, r.created, r.modified, r.deleted, i.identifier " +
                         "FROM raw_occurrence_record r LEFT JOIN identifier_record i ON i.occurrence_id = r.id WHERE i.identifier_type = 3 ";

        BasicSQLUtils.update(srcDBConn, "DELETE FROM raw WHERE id > 0");
        
        long totalRecs   = BasicSQLUtils.getCount(dbConn, "SELECT COUNT(*) FROM raw_occurrence_record");
        long procRecs    = 0;
        long startTime   = System.currentTimeMillis();
        int  secsThreshold = 0;
        
        PrintWriter pw = null;
        
        final double HRS = 1000.0 * 60.0 * 60.0; 
        
        Statement         gStmt = null;
        PreparedStatement pStmt = null;
        
        try
        {
            pw = new PrintWriter("gbif.log");
            
            pStmt = srcDBConn.prepareStatement(pSQL);
            //Statement stmt = dbConn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            //stmt.setFetchSize(Integer.MIN_VALUE);
            //ResultSet rs   = stmt.executeQuery("SELECT id FROM raw_occurrence_record");
            //while (rs.next())
            //{
            
            System.out.println("Total Records: "+totalRecs);
            pw.println("Total Records: "+totalRecs);
            
                gStmt = dbConn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                gStmt.setFetchSize(Integer.MIN_VALUE);
                
                String fullSQL = gbifSQL;// + " AND r.id = " + rs.getInt(1);
                System.out.println(fullSQL);
                
                
                ResultSet         gRS        = gStmt.executeQuery(fullSQL);
                ResultSetMetaData rsmd       = gRS.getMetaData();
                int               lastColInx = rsmd.getColumnCount();
                
                while (gRS.next())
                {
                    for (int i=1;i<rsmd.getColumnCount();i++)
                    {
                        Object obj = gRS.getObject(i);
                        pStmt.setObject(i, obj);
                    }
                    
                    String collNum = gRS.getString(lastColInx-1);
                    if (StringUtils.isNotEmpty(collNum))
                    {
                        if (collNum.length() < 256)
                        {
                            pStmt.setString(lastColInx, collNum);
                            
                        } else
                        {
                            pStmt.setString(lastColInx, collNum.substring(0, 255));
                        }
                    } else
                    {
                        pStmt.setObject(lastColInx, null);
                    }
                    
                    try
                    {
                        pStmt.executeUpdate();
                        
                    } catch (Exception ex)
                    {
                        System.err.println("For ID["+gRS.getObject(1)+"]");
                        ex.printStackTrace();
                        pw.print("For ID["+gRS.getObject(1)+"] "+ex.getMessage());
                        pw.flush();
                    }
                    
                    procRecs++;
                    if (procRecs % 10000 == 0)
                    {
                        long endTime     = System.currentTimeMillis();
                        long elapsedTime = endTime - startTime;
                        
                        double avergeTime = (double)elapsedTime / (double)procRecs;
                        
                        double hrsLeft = (((double)elapsedTime / (double)procRecs) * (double)totalRecs - procRecs)  / HRS;
                        
                        int seconds = (int)(elapsedTime / 60000.0);
                        if (secsThreshold != seconds)
                        {
                            secsThreshold = seconds;
                            
                            String msg = String.format("Elapsed %8.2f hr.mn   Ave Time: %5.2f    Percent: %6.3f  Hours Left: %8.2f ", 
                                    ((double)(elapsedTime)) / HRS, 
                                    avergeTime,
                                    100.0 * ((double)procRecs / (double)totalRecs),
                                    hrsLeft);
                            System.out.println(msg);
                            pw.println(msg);
                            pw.flush();
                        }
                    }
                }
            //}
            //rs.close();
            //stmt.close();
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            
        } finally 
        {
            try
            {
                if (gStmt != null)
                {
                    gStmt.close();
                }
                if (pStmt != null)
                {
                    pStmt.close();
                }
                pw.close();
                
            } catch (Exception ex)
            {
                
            }
        }
        System.out.println("Done transferring.");
        
        /*
        int     count = 0;
        boolean cont = true;
        while (cont)
        {
            long start = System.currentTimeMillis();
            
            Statement         gStmt = null;
            PreparedStatement pStmt = null;
            
            try
            {
                gStmt = dbConn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                
                String fullSQL = gbifSQL + String.format(" AND r.id >= %d AND r.id < %d", count, count+recordStep);
                //System.out.println(fullSQL);
                
                int               cnt  = 0;
                ResultSet         rs   = gStmt.executeQuery(fullSQL);
                ResultSetMetaData rsmd = rs.getMetaData();
                
                //System.out.println("Done with query.");
                
                pStmt = srcDBConn.prepareStatement(pSQL);
                count += recordStep;
                
                while (rs.next())
                {
                    Integer id  = rs.getInt(1);
                    pStmt.setInt(1, id);
                    
                    for (int i=2;i<=rsmd.getColumnCount();i++)
                    {
                        Object obj = rs.getObject(i);
                        pStmt.setObject(i, obj);
                    }
                    
                    pStmt.executeUpdate();
                
                    cnt++;
                    procRecs++;
                }
                rs.close();
                
                if (count == 0)
                {
                    break;
                }
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
                
            } finally 
            {
                try
                {
                    if (gStmt != null)
                    {
                        gStmt.close();
                    }
                    if (pStmt != null)
                    {
                        pStmt.close();
                    }
                } catch (Exception ex)
                {
                    
                }
            }
            
            long endTime   = System.currentTimeMillis();
            long deltaTime = endTime - start;
           
            long elapsedTime = endTime - startTime;
            
            double avergeTime = (double)elapsedTime / (double)procRecs;
            
            double hrsLeft = (((double)procRecs / (double)elapsedTime) * (double)totalRecs)  / 3600000.0;
            
            int seconds = (int)(elapsedTime / 60000.0);
            if (secsThreshold != seconds)
            {
                secsThreshold = seconds;
                
                System.out.println(String.format("Elapsed %8.2f hr.mn   Time: %5.2f  Ave Time: %5.2f    Percent: %6.3f  Hours Left: Elapsed %8.2f ", 
                        ((double)(elapsedTime)) / 3600000.0, 
                        ((double)(deltaTime)) / 1000.0, 
                        avergeTime,
                        100.0 * ((double)procRecs / (double)totalRecs),
                        hrsLeft));
            }
        }
        System.out.println("Done transferring.");*/
        
        /*Statement uStmt = null;
        try
        {
            uStmt = srcDBConn.createStatement();
            int rv = uStmt.executeUpdate("ALTER TABLE raw ADD FULLTEXT(catalogue_number, genus, species, subspecies, collector_num)");
            
            System.out.println("Indexing rv = "+rv);
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            
        } finally 
        {
            try
            {
                if (uStmt != null)
                {
                    uStmt.close();
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        System.out.println("Done Indexing.");*/
    }
    
    /**
     * 
     */
    public void cleanup()
    {
        try
        {
            if (dbConn != null)
            {
                dbConn.close();
            }
            if (srcDBConn != null)
            {
                srcDBConn.close();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * @return the dbConn
     */
    public Connection getDBConn()
    {
        return dbConn;
    }

    /**
     * @return the srcDBConn
     */
    public Connection getSrcDBConn()
    {
        return srcDBConn;
    }


    
    //------------------------------------------------------------------------------------------
    public static void main(String[] args)
    {
        CopyFromGBIF awg = new CopyFromGBIF();
        awg.createDBConnection("lm2gbdb.nhm.ku.edu", "3399", "gbc20100726", "rods", "specify4us");
        awg.createSrcDBConnection("localhost", "3306", "gbif", "root", "root");
        awg.process();
        awg.cleanup();
    }
}

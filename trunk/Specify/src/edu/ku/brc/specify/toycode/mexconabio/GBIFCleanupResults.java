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

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import edu.ku.brc.af.core.db.DBFieldInfo;
import edu.ku.brc.af.core.db.DBInfoBase;
import edu.ku.brc.af.core.db.DBTableInfo;
import edu.ku.brc.specify.dbsupport.cleanuptools.BaseCleanupResults;
import edu.ku.brc.specify.dbsupport.cleanuptools.DataObjTableModel;
import edu.ku.brc.specify.dbsupport.cleanuptools.DataObjTableModelRowInfo;
import edu.ku.brc.specify.dbsupport.cleanuptools.FindItemInfo;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Aug 3, 2010
 *
 */
public class GBIFCleanupResults extends BaseCleanupResults
{
    protected AnalysisWithGBIF awg; 
    
    /**
     * @param title
     * @param itemInfo
     * @throws HeadlessException
     */
    public GBIFCleanupResults(final String title, 
                              final FindItemInfo itemInfo,
                              final AnalysisWithGBIF awg) throws HeadlessException
    {
        super(title, itemInfo);
        this.awg = awg;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.cleanuptools.BaseCleanup#createAndFillModels()
     */
    @Override
    protected void createAndFillModels()
    {
        final String[] colNames = {"Id", "Institution Code", "Collection Code", "Catalog Number", 
                "Scientific Name", "Author", "Genus", "Species", 
                "Subspecies", "Latitude", "Longitude", "Lat Long Prec", 
                "Max altitude", "Min altitude", "Alt Precision", "Min Depth", 
                "Max Depth", "Depth Precision", "Continent Ocean", "Country", 
                "State", "County", "Collector Name", "Locality", 
                "Year", "Month", "Day", "origcatnumber"};
        
        final Class<?> dataClasses[] = {Integer.class, String.class, String.class, String.class, 
                                        String.class, String.class, String.class, String.class, 
                                        String.class, String.class, String.class, String.class, 
                                        String.class, String.class, String.class, String.class, 
                                        String.class, String.class, String.class, String.class, 
                                        String.class, String.class, String.class, String.class, 
                                        String.class, String.class, String.class, String.class};

        model = new DataObjTableModel(awg.getSrcDBConn(), 100, itemInfo.getValue().toString(), false)
        {
            /* (non-Javadoc)
             * @see edu.ku.brc.specify.dbsupport.cleanuptools.DataObjTableModel#buildSQL()
             */
            @Override
            protected String buildSQL()
            {
                String gSQL = "SELECT id, institution_code, collection_code, " +
                "catalogue_number, scientific_name, author, genus, species, subspecies, latitude, longitude,  " +
                "lat_long_precision, max_altitude, min_altitude, altitude_precision, min_depth, max_depth, depth_precision, " +
                "continent_ocean, country, state_province, county, collector_name, " + 
                "locality, year, month, day, origcatnumber FROM raw WHERE origcatnumber = ?";
                
                tableInfo = new DBTableInfo(100, this.getClass().getName(), "raw", "id", "r");
                
                for (int i=0;i<colNames.length;i++)
                {
                    DBFieldInfo fi = new DBFieldInfo(tableInfo, colNames[i], dataClasses[i]);
                    fi.setTitle(colNames[i]);
                    colDefItems.add(fi);
                }
                numColumns = colNames.length;
                
                return gSQL;
            }

            /* (non-Javadoc)
             * @see edu.ku.brc.specify.dbsupport.cleanuptools.DataObjTableModel#addAdditionalRows(java.util.ArrayList)
             */
            @Override
            protected void addAdditionalRows(final ArrayList<DBInfoBase> colDefItems,
                                             final ArrayList<DataObjTableModelRowInfo> rowInfoList)
            {
                Calendar cal = Calendar.getInstance();
                
                String cSQL = String.format("SELECT BarCD, GenusName, SpeciesName, SubspeciesName, " +
                                "Latminenetq, Lngminenetq, LatLongFuente, AltMaxEtq, AltMinEtq, COUNTRY, STATE, MUNIC, Collectoragent1, " +
                                "LocalityName, Datecollstandrd, CollNr " +
                                "FROM conabio WHERE BarCD = '%s'", searchValue);
                
                Connection conn = awg.getSrcDBConn();
                
                Statement stmt  = null;
                try
                {
                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    
                    String    collNum = null;
                    ResultSet rs      = stmt.executeQuery(cSQL);
                    while (rs.next())
                    {
                        Object[] row = new Object[numColumns];
                        
                        int inx   = 0;
                        int dbInx = 1;
                        
                        row[inx++] = null;                  // id
                        row[inx++] = null;                  // institution_code
                        row[inx++] = null;                  // collection_code
                        row[inx++] = rs.getObject(dbInx++); // catalogue_number
                        row[inx++] = null;                  // scientific_name
                        row[inx++] = null;                  // author
                        row[inx++] = rs.getObject(dbInx++); // genus
                        row[inx++] = rs.getObject(dbInx++); // species
                        row[inx++] = rs.getObject(dbInx++); // subspecies
                        row[inx++] = rs.getObject(dbInx++); // latitude
                        row[inx++] = rs.getObject(dbInx++); // longitude
                        row[inx++] = rs.getObject(dbInx++); // lat_long_precision
                        row[inx++] = rs.getObject(dbInx++); // max_altitude
                        row[inx++] = rs.getObject(dbInx++); // min_altitude
                        row[inx++] = null;                  // altitude_precision
                        row[inx++] = null;                  // min_depth
                        row[inx++] = null;                  // max_depth
                        row[inx++] = null;                  // depth_precision
                        row[inx++] = null;                  // continent_ocean
                        row[inx++] = rs.getObject(dbInx++); // country
                        row[inx++] = rs.getObject(dbInx++); // state_province
                        row[inx++] = rs.getObject(dbInx++); // county
                        row[inx++] = rs.getObject(dbInx++); // collector_name
                        row[inx++] = rs.getObject(dbInx++); // locality
                        
                        Date collDate = rs.getDate(dbInx);
                        dbInx++;
                        
                        if (collDate == null)
                        {
                            row[inx++] = null;                // year
                            row[inx++] = null;                // month
                            row[inx++] = null;                // day
                            
                        } else
                        {
                            cal.setTime(collDate);
                            Integer year         = cal.get(Calendar.YEAR);
                            Integer mon          = cal.get(Calendar.MONTH) + 1;
                            Integer day          = cal.get(Calendar.DAY_OF_MONTH);
                            
                            row[inx++] = year.toString(); // year
                            row[inx++] = mon.toString(); // month
                            row[inx++] = day.toString(); // day
                        }
                        
                        collNum    = rs.getString(dbInx++); // origcatnumber
                        row[inx++] = collNum;
                        
                        values.add(row);
                        rowInfoList.add(new DataObjTableModelRowInfo(rs.getInt(1), false, false));
                    }
                    rs.close();
                    
                    for (int i=0;i<values.size()-1;i++)
                    {
                        values.get(i)[numColumns-1] = collNum;
                    }
                        
                } catch (SQLException ex)
                {
                    ex.printStackTrace();
                } finally 
                {
                    try
                    {
                        if (stmt != null)
                        {
                            stmt.close();
                        }
                    } catch (SQLException ex) {}
                }
            }
        };
        
        newModel = new DataObjTableModel(awg.getSrcDBConn(), 100, model.getItems(), model.getHasDataList(), 
                                        model.getSameValues(), model.getMapInx(), model.getIndexHash());
    }
}

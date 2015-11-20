/* Copyright (C) 2015, University of Kansas Center for Research
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
package edu.ku.brc.specify.tasks.subpane.images;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.db.DBFieldInfo;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.db.DBTableInfo;
import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.af.ui.db.PickListDBAdapterFactory;
import edu.ku.brc.af.ui.db.PickListIFace;
import edu.ku.brc.af.ui.db.PickListItemIFace;
import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.config.SpecifyAppContextMgr;
import edu.ku.brc.specify.config.SpecifyWebLinkMgr;
import edu.ku.brc.specify.conversion.BasicSQLUtils;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.AccessionAttachment;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.AgentAttachment;
import edu.ku.brc.specify.datamodel.Attachment;
import edu.ku.brc.specify.datamodel.Borrow;
import edu.ku.brc.specify.datamodel.BorrowAttachment;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectingEventAttachment;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.CollectionObjectAttachment;
import edu.ku.brc.specify.datamodel.ConservDescription;
import edu.ku.brc.specify.datamodel.ConservDescriptionAttachment;
import edu.ku.brc.specify.datamodel.ConservEvent;
import edu.ku.brc.specify.datamodel.ConservEventAttachment;
import edu.ku.brc.specify.datamodel.DNASequence;
import edu.ku.brc.specify.datamodel.DNASequenceAttachment;
import edu.ku.brc.specify.datamodel.DNASequencingRun;
import edu.ku.brc.specify.datamodel.DNASequencingRunAttachment;
import edu.ku.brc.specify.datamodel.Discipline;
import edu.ku.brc.specify.datamodel.FieldNotebook;
import edu.ku.brc.specify.datamodel.FieldNotebookAttachment;
import edu.ku.brc.specify.datamodel.FieldNotebookPage;
import edu.ku.brc.specify.datamodel.FieldNotebookPageAttachment;
import edu.ku.brc.specify.datamodel.FieldNotebookPageSet;
import edu.ku.brc.specify.datamodel.FieldNotebookPageSetAttachment;
import edu.ku.brc.specify.datamodel.Geography;
import edu.ku.brc.specify.datamodel.Gift;
import edu.ku.brc.specify.datamodel.GiftAttachment;
import edu.ku.brc.specify.datamodel.Loan;
import edu.ku.brc.specify.datamodel.LoanAttachment;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.specify.datamodel.LocalityAttachment;
import edu.ku.brc.specify.datamodel.Permit;
import edu.ku.brc.specify.datamodel.PermitAttachment;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.specify.datamodel.PreparationAttachment;
import edu.ku.brc.specify.datamodel.ReferenceWork;
import edu.ku.brc.specify.datamodel.ReferenceWorkAttachment;
import edu.ku.brc.specify.datamodel.RepositoryAgreement;
import edu.ku.brc.specify.datamodel.RepositoryAgreementAttachment;
import edu.ku.brc.specify.datamodel.SpAppResource;
import edu.ku.brc.specify.datamodel.SpAppResourceDir;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.Storage;
import edu.ku.brc.specify.datamodel.StorageAttachment;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.datamodel.TaxonAttachment;
import edu.ku.brc.specify.datamodel.TreatmentEvent;
import edu.ku.brc.specify.datamodel.busrules.AgentBusRules;
import edu.ku.brc.specify.ui.SpecifyUIFieldFormatterMgr;
import edu.ku.brc.ui.DateWrapper;
import edu.ku.brc.util.Triple;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Sep 5, 2012
 *
 */
public class CollectionDataFetcher
{
    private static final Logger log = Logger.getLogger(CollectionDataFetcher.class);

    private static final String BUBBLE_INFO   = "BubbleInfo";
    private static final String DISKLOC       = "common/bubble_info.xml";
    
    protected static boolean    doingLocal = false;
    
    protected DateWrapper                      scrDateFormat = AppPrefsCache.getDateWrapper("ui", "formatting", "scrdateformat");
    private Connection                         conn          = DBConnection.getInstance().getConnection();
    private static HashMap<Class<?>, Class<?>> clsHashMap    = new HashMap<Class<?>, Class<?>>();
    private static Class<?>[]                  attachmentClassesForMap;
    private static Class<?>[]                  attachmentClasses;
    
    private HashMap<Integer, List<BubbleDisplayInfo>> bciHash   = new HashMap<Integer, List<BubbleDisplayInfo>>();
    private HashMap<Integer,String>                   joinsHash = new HashMap<Integer, String>();

    private boolean isEmbeded;
    
    static 
    {
        CollectionDataFetcher.attachmentClassesForMap = new Class<?>[] {
                Accession.class,          AccessionAttachment.class,
                Agent.class,              AgentAttachment.class,
                Borrow.class,             BorrowAttachment.class,
                CollectingEvent.class,    CollectingEventAttachment.class,
                CollectionObject.class,   CollectionObjectAttachment.class,
                ConservDescription.class, ConservDescriptionAttachment.class,
                ConservEvent.class,       ConservEventAttachment.class,
                DNASequence.class,        DNASequenceAttachment.class,
                DNASequencingRun.class,   DNASequencingRunAttachment.class,
                FieldNotebook.class,      FieldNotebookAttachment.class,
                FieldNotebookPage.class,  FieldNotebookPageAttachment.class,
                FieldNotebookPageSet.class, FieldNotebookPageSetAttachment.class,
                Gift.class,               GiftAttachment.class,
                Loan.class,               LoanAttachment.class,
                Locality.class,           LocalityAttachment.class,
                Permit.class,             PermitAttachment.class,
                Preparation.class,        PreparationAttachment.class,
                ReferenceWork.class,      ReferenceWorkAttachment.class,
                RepositoryAgreement.class, RepositoryAgreementAttachment.class,
                Storage.class,            StorageAttachment.class,
                Taxon.class,              TaxonAttachment.class,
                TreatmentEvent.class,	  TreatmentEvent.class,
            };
        CollectionDataFetcher.attachmentClasses = new Class<?>[CollectionDataFetcher.attachmentClassesForMap.length/2];
        for (int i=0;i<CollectionDataFetcher.attachmentClassesForMap.length;i+=2)
        {
            CollectionDataFetcher.attachmentClasses[i/2] = CollectionDataFetcher.attachmentClassesForMap[i];
            //System.out.println(String.format("[%s][%s] %d / %d", attachmentClasses[i/2].getSimpleName(), attachmentClassesForMap[i].getSimpleName(), i/2, i));
        }
        
        for (int i=0;i<attachmentClassesForMap.length;i++)
        {
            clsHashMap.put(attachmentClassesForMap[i], attachmentClassesForMap[i+1]);
            //System.out.println(String.format("[%s][%s] %d/%d", attachmentClassesForMap[i].getSimpleName(), attachmentClassesForMap[i+1].getSimpleName(), i, i+1));
            i++;
        }
    }
    
    /**
     * 
     */
    public CollectionDataFetcher()
    {
        super();

        Collection collection = AppContextMgr.getInstance().getClassObject(Collection.class);
        isEmbeded  = collection.getIsEmbeddedCollectingEvent();
        loadBubbleInfo();
    }
    
    /**
     * @return the clsHashMap
     */
    public static HashMap<Class<?>, Class<?>> getAttachmentClassMap()
    {
        return clsHashMap;
    }

    /**
     * @return the attachmentClasses
     */
    public static Class<?>[] getAttachmentClasses()
    {
        return attachmentClasses;
    }
    
    /**
     * @return
     */
    protected org.dom4j.Element readXMLFromResources()
    {
        String xml = null;
        if (doingLocal)
        {
            File file = XMLHelper.getConfigDir(DISKLOC);
            try
            {
                xml = FileUtils.readFileToString(file);
                
            } catch (IOException ex)
            {
                edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyWebLinkMgr.class, ex);
                ex.printStackTrace();
            }
        } else
        {
            SpecifyAppContextMgr acMgr = (SpecifyAppContextMgr)AppContextMgr.getInstance();
            DataProviderSessionIFace session = null;
            try
            {
                session = DataProviderFactory.getInstance().createSession();
                
                SpecifyUser user       = acMgr.getClassObject(SpecifyUser.class);
                Discipline  discipline = acMgr.getClassObject(Discipline.class);
                
                SpAppResourceDir appResDir = acMgr.getAppResDir(session, user, discipline, null, null, false, BUBBLE_INFO, false);
                if (appResDir != null)
                {
                    SpAppResource appRes = appResDir.getResourceByName(BUBBLE_INFO);
                    if (appRes != null)
                    {
                        session.close();
                        session = null;
                        
                        xml = AppContextMgr.getInstance().getResourceAsXML(appRes);
                    }
                } else
                {
                    File file = XMLHelper.getConfigDir(DISKLOC);
                    try
                    {
                        xml = FileUtils.readFileToString(file);
                        
                    } catch (IOException ex)
                    {
                        edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                        edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyWebLinkMgr.class, ex);
                        ex.printStackTrace();
                    }
                }
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
                edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyUIFieldFormatterMgr.class, ex);
                
            } finally
            {
                if (session != null)
                {
                    session.close();
                }
            }
        }
        
        try
        {
            return XMLHelper.readStrToDOM4J(xml);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    
    /**
     * 
     */
    private void loadBubbleInfo()
    {
        Element root = null;//readXMLFromResources();
        if (root == null)
        {
            root = XMLHelper.readDOMFromConfigDir("bubble_info.xml");
        }
        
        for (Object bubObj : root.selectNodes("/bubbles/bubble")) 
        {
            Element bi    = (Element)bubObj;
            int     tblId = XMLHelper.getAttr(bi, "tableid", -1); 
            ArrayList<BubbleDisplayInfo> bcis = new ArrayList<BubbleDisplayInfo>();
            for (Object fldObj : bi.selectNodes("fields/field")) 
            {
                Element fld      = (Element)fldObj;
                String  colName  = XMLHelper.getAttr(fld, "col", null);
                int     colTblId = XMLHelper.getAttr(fld, "tableid", tblId);
                
                if (!isEmbeded && tblId == 1 && colName.equals("FieldNumber"))
                {
                    colName  = "StationFieldNumber";
                    colTblId = 10;
                }
                
                DBTableInfo ti = DBTableIdMgr.getInstance().getInfoById(colTblId);
                DBFieldInfo fi = ti.getFieldByColumnName(colName);
                
                bcis.add(new BubbleDisplayInfo(ti, fi));
            }
            
            Element join = (Element)bi.selectSingleNode("joins");
            String joinStr = join != null ? join.getText() : null;
            
            Collections.sort(bcis);
            
            joinsHash.put(tblId, joinStr);
            bciHash.put(tblId, bcis);
        }
    }
    
    /**
     * @param ti
     * @return
     */
    private String getSelectFields(final DBTableInfo ti)
    {
        StringBuilder sb = new StringBuilder();
        for (BubbleDisplayInfo bdi : bciHash.get(ti.getTableId()))
        {
            if (sb.length() > 0) sb.append(',');
            sb.append(bdi.getFieldInfo().getTableInfo().getAbbrev());
            sb.append('.');
            sb.append(bdi.getColumnName());
        }
        sb.append(',');
        sb.append(ti.getAbbrev());
        sb.append('.');
        sb.append(ti.getIdColumnName());

        return sb.toString();
    }
    
    /**
     * @param bdi
     * @return
     */
    private String getColumnTitle(final BubbleDisplayInfo bdi, final int tblId)
    {
        if (tblId == CollectionObject.getClassTableId() || 
            tblId == Locality.getClassTableId() || 
            tblId == Taxon.getClassTableId() || 
            tblId == CollectingEvent.getClassTableId())
        {
            if (bdi.getColumnName().equals("FullName") && bdi.getFieldInfo().getTableInfo().getTableId() == Geography.getClassTableId())
            {
                return DBTableIdMgr.getInstance().getInfoById(Geography.getClassTableId()).getTitle();
            }
            if (bdi.getColumnName().equals("FullName") && bdi.getFieldInfo().getTableInfo().getTableId() == Taxon.getClassTableId())
            {
                return DBTableIdMgr.getInstance().getInfoById(Taxon.getClassTableId()).getTitle();
            }
        }
        return bdi.getTitle();
    }
    
    /**
     * @param rs
     * @param tableId
     * @return
     * @throws SQLException 
     */
    private List<Triple<String, String, Object>> readDataIntoMap(final ResultSet rs, final int tableId) throws SQLException
    {
        List<BubbleDisplayInfo>              displayInfos = bciHash.get(tableId);
        List<Triple<String, String, Object>> dataList     = new ArrayList<Triple<String, String, Object>>();
        if (rs != null)
        {
            if (rs.next())
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                
                for (int i=1;i<rsmd.getColumnCount();i++)
                {
                    BubbleDisplayInfo bdi = displayInfos.get(i-1);
                    Object            val = rs.getObject(i);
                    
                    if (bdi.getColTblId() == Attachment.getClassTableId() &&
                        bdi.getColumnName().equals("OrigFilename"))
                    {
                        val = FilenameUtils.getName(val.toString());
                    }
                    
                    if (bdi.getFormatter() != null)
                    {
                        val = bdi.getFormatter().formatToUI(val);
                        
                    } else if (val instanceof Calendar)
                    {
                        val = scrDateFormat.format((Calendar)val);
                        
                    } else if (val instanceof Date)
                    {
                        val = scrDateFormat.format((Date)val);
                        
                    } else if (val instanceof BigDecimal)
                    {
                        val = StringUtils.stripEnd(val.toString(), "0");
                        
                    } else if (bdi.getFieldInfo().getPickListName() != null)
                    {
                        PickListIFace pl = PickListDBAdapterFactory.getInstance().getPickList(bdi.getFieldInfo().getPickListName());
                        if (pl != null)
                        {
                            for (PickListItemIFace pli : pl.getItems())
                            {
                                if (pli.getValue() != null && pli.getValue().equals(val))
                                {
                                    val = pli.getTitle();
                                    break;
                                }
                            }
                        }
                    }
                    String title = getColumnTitle(bdi, tableId) + ": ";
                    dataList.add(new Triple<String, String, Object>(bdi.getFieldInfo().getColumn(), title, val));
                }
                //System.out.println(rs.getObject(rsmd.getColumnCount()));
                dataList.add(new Triple<String, String, Object>("Id", "Id", rs.getObject(rsmd.getColumnCount())));
            }
            rs.close();
        }
        return dataList;
    }
    
    /**
     * @param attachmentID
     * @param ti
     * @param joinStr1
     * @param joinStr2
     * @param stmt
     * @return
     * @throws SQLException
     */
    private ResultSet queryGenerically(final int         attachmentID,
                                       final DBTableInfo ti, 
                                       final String      joinStr1,
                                       final String      joinStr2,
                                       final Statement   stmt) throws SQLException
    {
        
        StringBuilder sqlSB   = new StringBuilder("SELECT ");
        String        columns = getSelectFields(ti);
        sqlSB.append(columns);

        
        sqlSB.append(" FROM attachment att ");
        sqlSB.append(joinStr1);
        sqlSB.append(joinStr2);
        
        String joinStr = joinsHash.get(ti.getTableId());
        if (joinStr != null)
        {
            sqlSB.append(joinStr);
        }
        sqlSB.append("WHERE att.AttachmentID=");
        sqlSB.append(attachmentID);
        
        log.debug(sqlSB.toString());
        
        return stmt.executeQuery(sqlSB.toString());
    }
    
    /**
     * @param tableId
     * @return
     */
    public List<BubbleDisplayInfo> getBubbleDisplayInfo(final int tableId)
    {
        List<BubbleDisplayInfo> bdi = bciHash.get(tableId);
        if (bdi != null)
        {
            return bdi;
        }
        return null;
    }
    
    /**
     * @param attachmentID
     * @param ti
     * @param joinStr1
     * @param joinStr2
     * @param stmt
     * @return
     * @throws SQLException
     */
    private List<Triple<String, String, Object>> queryAgent(
          final int         attachmentID,
          final DBTableInfo ti, 
          final String      joinStr1,
          final String      joinStr2,
          final Statement   stmt) throws SQLException
    {
        ResultSet rs = queryGenerically(attachmentID, ti, joinStr1, joinStr2, stmt);
        if (rs != null)
        {
            List<Triple<String, String, Object>> dataList = readDataIntoMap(rs, Agent.getClassTableId());
            Triple<String, String, Object> p = dataList.get(0);
            if (p != null)
            {
                String[] agentTitles = AgentBusRules.getTypeTitle();
                int inx = Integer.parseInt(p.second.toString());
                if (inx > 0 && inx < agentTitles.length)
                {
                    p.second = agentTitles[inx];
                }
            }
            return dataList;
        }
        return null;
    }

    /**
     * @param attachmentID
     * @param ti
     * @param joinStr1
     * @param joinStr2
     * @param stmt
     * @return
     * @throws SQLException
     */
    private List<Triple<String, String, Object>> queryGenericTable(final int         attachmentID,
                                                                   final DBTableInfo ti, 
                                                                   final String      joinStr1,
                                                                   final String      joinStr2,
                                                                   final Statement  stmt) throws SQLException
    {
        ResultSet rs = queryGenerically(attachmentID, ti, joinStr1, joinStr2, stmt);
        if (rs != null)
        {
            return readDataIntoMap(rs, ti.getTableId());
        }
        return null;
    }

    /**
     * @param attachmentID
     * @param ti
     * @param joinStr1
     * @param joinStr2
     * @param stmt
     * @return
     * @throws SQLException
     */
    private List<Triple<String, String, Object>> queryColObj(final int attachmentID,
                                                             final DBTableInfo ti, 
                                                             final String joinStr1,
                                                             final String joinStr2,
                                                             final Statement stmt) throws SQLException
    {
        
        StringBuilder preSB = new StringBuilder("SELECT ");
        preSB.append(getSelectFields(ti));
        
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append(" FROM attachment att ");
        sqlSB.append(joinStr1);
        sqlSB.append(joinStr2);
        sqlSB.append("LEFT JOIN determination det ON co.CollectionObjectID = det.CollectionObjectID ");
        sqlSB.append("LEFT JOIN taxon tx ON det.TaxonID = tx.TaxonID ");
        sqlSB.append("LEFT JOIN collectingevent ce ON co.CollectingEventID = ce.CollectingEventID ");
        
        sqlSB.append("LEFT JOIN locality loc ON ce.LocalityID = loc.LocalityID ");
        sqlSB.append("LEFT JOIN geography geo ON loc.GeographyID = geo.GeographyID ");
        sqlSB.append("WHERE att.AttachmentID=");
        sqlSB.append(attachmentID);
        
        String detWHERE = " AND det.IsCurrent <> 0";
        
        int currCnt = BasicSQLUtils.getCountAsInt("SELECT COUNT(*) " + sqlSB.toString() + detWHERE); 
        if (currCnt > 0)
        {
            sqlSB.append(" AND (det.IsCurrent <> 0 OR det.DeterminationID IS NULL)");
        }
        
        String sql = preSB.toString() + sqlSB.toString();
        
        log.debug(sql);
        
        ResultSet rs = stmt.executeQuery(sql);
        return readDataIntoMap(rs, CollectionObject.getClassTableId());
    }
    
    /**
     * @param attachmentID
     * @param tableId
     */
    public List<Triple<String, String, Object>> queryByTableId(final int attachmentID, final int tableId)
    {
        DBTableInfo ti  = DBTableIdMgr.getInstance().getInfoById(tableId);
        Class<?>    cls = clsHashMap.get(ti.getClassObj());
        if (cls != null)
        {
            DBTableInfo joinTI = DBTableIdMgr.getInstance().getByClassName(cls.getName());
            
            String joinStr1 = String.format("LEFT JOIN %s AS %s ON att.AttachmentID = %s.AttachmentID ", joinTI.getName(), joinTI.getAbbrev(), joinTI.getAbbrev());
            String joinStr2 = String.format("LEFT JOIN %s AS %s ON %s.%s = %s.%s ", ti.getName(), ti.getAbbrev(), ti.getAbbrev(), ti.getIdColumnName(), joinTI.getAbbrev(), ti.getIdColumnName());
            
            Statement stmt = null;
            try
            {
                stmt = conn.createStatement();
                switch (tableId)
                {
                    case 1: // Collection Object
                        return queryColObj(attachmentID, ti, joinStr1, joinStr2, stmt);
                        
                    case 5: // Agent
                        return queryAgent(attachmentID, ti, joinStr1, joinStr2, stmt);
                        
                    default:
                        return queryGenericTable(attachmentID, ti, joinStr1, joinStr2, stmt);
                }
                
            } catch (SQLException ex)
            {
                
            } finally
            {
                try
                {
                    if (stmt != null) stmt.close();
                } catch (SQLException ex) {}
            }
        }
        return null;
    }
}

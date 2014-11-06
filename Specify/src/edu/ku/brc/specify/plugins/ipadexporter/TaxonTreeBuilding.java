/* Copyright (C) 2012, University of Kansas Center for Research
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
package edu.ku.brc.specify.plugins.ipadexporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.specify.conversion.BasicSQLUtils;
import edu.ku.brc.ui.ProgressDialog;
import edu.ku.brc.util.Pair;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Oct 11, 2014
 *
 */
public class TaxonTreeBuilding
{
    final int secsInHours = 3600;
    final int secInMin    = 60;

    private ProgressDialog progressDelegate;
    private SwingWorker<Integer, Integer> worker;
    
    private Connection     dbS3Conn;
    private Connection     conn;
    private iPadDBExporter ipadExporter;
    
    private int            totalRecords;
    private int            processedCount;
    
    private AtomicInteger  timeRemaining;
    private Timer          countDownTimer;
    
    private HashMap<Integer, HashMap<Integer, TreeNode>> taxonHash = new HashMap<Integer, HashMap<Integer, TreeNode>>();
    private ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
    private Vector<Integer>     treeRanks = null;
    private PreparedStatement   lookUpParentStmt = null;
    private boolean             skipNodeList = false;
    
    /**
     * @param ipadExporter
     * @param dbS3Conn
     * @param conn
     * @param colObjToCnt
     */
    public TaxonTreeBuilding(final iPadDBExporter ipadExporter, 
                             final Connection dbS3Conn, 
                             final Connection conn)
    {
        this.dbS3Conn         = dbS3Conn;
        this.conn             = conn;
        this.ipadExporter     = ipadExporter;
        this.progressDelegate = ipadExporter.getProgressDelegate();
        this.worker           = ipadExporter.getWorker();
    }
    
    /**
     * 
     */
    public void process()
    {
        exportTreeData();
    }
    
    /**
     * @param pStmt
     * @param nodenum
     * @param highNodeNum
     * @return
     * @throws SQLException
     */
    private Pair<Integer, Integer> calcTotalForTreeSpan(final PreparedStatement pStmt, 
                                                        final int nodeNum, 
                                                        final int highNodeNum) throws SQLException
    {
        pStmt.setInt(1, nodeNum);
        pStmt.setInt(2, highNodeNum);
        
        int countBelow = 0;
        int countAt    = 0;
        ResultSet rs   = pStmt.executeQuery(); // Get the GeoID and LocID
        while (rs.next())
        {
            int     nn   = rs.getInt(2);
            Integer cnt  = rs.getInt(3);
            if (rs.wasNull()) cnt = null;
            
            if (cnt != null)
            {
                if (nn == nodeNum)
                {
                    countAt += cnt;
                } else
                {
                    countBelow += cnt;
                }
            }
        }
        rs.close();
        
        return new Pair<Integer, Integer>(countBelow, countAt);
    }
    
    /**
     * @param s3Stmt
     * @param taxonId
     * @param fullName
     * @param rankId
     * @param parentId
     * @param familyId
     * @param totalCOCnt
     * @param numObjs
     * @param nodeNum
     * @param highNodeNum
     * @return
     * @throws SQLException
     */
    private boolean writeTaxon(final PreparedStatement s3Stmt, 
                               final int     taxonId, 
                               final String  fullName, 
                               final int     rankId, 
                               final Integer parentId, 
                               final Integer familyId, 
                               final Integer genusId, 
                               final Integer totalCOCnt, 
                               final Integer numObjs, 
                               final int     nodeNum,
                               final int     highNodeNum)
    {
        boolean status = false;
        try
        {
            s3Stmt.setInt(1,    taxonId); // TaxonID
            s3Stmt.setString(2, fullName);
            s3Stmt.setInt(3,    rankId);
            s3Stmt.setInt(4,    parentId);
            s3Stmt.setObject(5, familyId);
            s3Stmt.setObject(6, genusId);
            s3Stmt.setInt(7,    totalCOCnt != null ? totalCOCnt : 0);
            s3Stmt.setInt(8,    numObjs != null ? numObjs : 0);
            s3Stmt.setInt(9,    nodeNum);
            s3Stmt.setInt(10,    highNodeNum);
            
            int rv = s3Stmt.executeUpdate();
            status = rv == 1;
            if (!status)
            {
                System.out.println("Error updating taxon: "+taxonId);
            }
        } catch (SQLException ex)
        {
            System.err.println("For ID: "+taxonId+" - "+ex.getMessage());
        }
        return status;
    }

    /**
     * 
     */
    private void exportTreeData()
    {
        boolean doNew = true;
        if (doNew)
        {
            exportTreeDataNew();
        } else
        {
            exportTreeDataOld();
        }
    }
    
     
    private TreeNode getNode(final int taxonId,
                             final int rankId,
                             final Integer parentId,
                             final int parentRankId)
    {
        HashMap<Integer, TreeNode> nodeHash = taxonHash.get(rankId);
        if (nodeHash == null)
        {
            nodeHash = new HashMap<Integer, TreeNode>();
            taxonHash.put(rankId, nodeHash);
        }
        
        TreeNode pNode = nodeHash.get(taxonId);
        if (pNode == null)
        {
            pNode = new TreeNode(taxonId, rankId, parentId, parentRankId);
            nodeHash.put(taxonId, pNode);
            if (!skipNodeList)
            {
                nodeList.add(pNode);
            }
        }
        return pNode;
    }

    
    /**
     * @param node
     * @param famGen
     * @throws SQLException
     */
    private void buildTree(final TreeNode node, Pair<Integer, Integer> famGen) throws SQLException
    {
        //System.out.println(String.format("%d\t%d\t%d\t%d", node.taxonId, node.rankId, node.parentId, node.parentRankId));
        TreeNode parent = getNode(node.parentId, node.parentRankId, null, 0);
        
        if (node.rankId == 140)
        {
            famGen.first = node.taxonId;
            node.familyId = node.taxonId;
            return;
        }
        
        if (node.rankId == 180)
        {
            famGen.second = node.taxonId;
        }
        
        if (node.visited)
        {
            famGen.first = node.familyId;
            famGen.second = node.genusId;
            return;
        }
        
        node.visited = true;
        if (parent.parentId == null)
        {
            lookUpParentStmt.setInt(1, node.parentId);
            ResultSet rs = lookUpParentStmt.executeQuery();
            if (rs.next())
            {
                parent.parentId     = rs.getInt(1);
                parent.parentRankId = rs.getInt(2);

            } else
            {
                //famGen.first = node.familyId;
                //famGen.second = node.genusId;
                return;
            }
            rs.close();
        }
        
        buildTree(parent, famGen);

        node.familyId = famGen.first;
        node.genusId  = famGen.second;
    }
    
    private void dumpTree()
    {
        System.out.println("TaxonID\tName\tTotal\tCount\tRankID\tFamily\tGenus\tVisited");
        for (Integer rankID : treeRanks)
        {
            HashMap<Integer, TreeNode> pNodeHash = taxonHash.get(rankID);
            if (pNodeHash != null)
            {
                for (TreeNode pNode : pNodeHash.values())
                {
                    String name = BasicSQLUtils.querySingleObj("SELECT FullName from taxon WHERE TaxonID = "+pNode.taxonId);
                    System.out.println(String.format("%d\t%s\t%d\t%d\t%d\t%d\t%d\t%s",  
                            pNode.taxonId, name, pNode.totalCount, pNode.nodeCount, pNode.rankId, pNode.familyId, pNode.genusId, pNode.visited?"Y":"N"));
                }
            }
        }
        System.out.println("----------------------------------------------");
    }

    /**
     * 
     */
    private void exportTreeDataNew()
    {
        PreparedStatement s3Stmt       = null;        
        PreparedStatement s3Stmt2      = null; 
        PreparedStatement txLookUpStmt = null;
        Statement         stmt         = null;
        
        int transCnt = 0;
        try
        {
            String treeRanksSQL = ipadExporter.adjustSQL("SELECT RankID FROM taxontreedefitem WHERE RankID > 139 AND TaxonTreeDefID = TAXTREEDEFID ORDER BY RankID DESC");
            treeRanks = BasicSQLUtils.queryForInts(treeRanksSQL);

            // SQLite
            dbS3Conn.setAutoCommit(false);
            s3Stmt  = dbS3Conn.prepareStatement("INSERT INTO taxon (_id, FullName, RankID, ParentID, FamilyID, GenusID, TotalCOCnt, NumObjs, NodeNum, HighNodeNum) VALUES (?,?,?,?,?,?,?,?,?,?)");        
            s3Stmt2 = dbS3Conn.prepareStatement("UPDATE taxon SET FamilyID=?, GenusID=?, TotalCOCnt=?, NumObjs=? WHERE _id = ?");  
            
            // MySQL
            stmt    = conn.createStatement();
            lookUpParentStmt = conn.prepareStatement("SELECT tp.TaxonId, tp.RankID FROM taxon t INNER JOIN taxon tp ON t.ParentID = tp.TaxonID WHERE t.TaxonID = ?");
            
            String coLookUp = ipadExporter.adjustSQL("SELECT co.CollectionObjectID, t.TaxonID, t.FullName, t.RankID, t.ParentID, t.NodeNumber, t.HighestChildNodeNumber, ios_colobjcnts.NewID, tp.RankID " +
            		                                 "FROM collectionobject co " +
                                                    "INNER JOIN determination d ON co.CollectionObjectID = d.CollectionObjectID " +
                                                    "INNER JOIN taxon t ON d.TaxonID = t.TaxonID " +
                                                    "INNER JOIN taxon tp ON t.ParentID = tp.TaxonID " +
                                                    "INNER JOIN ios_colobjcnts ON co.CollectionObjectID = ios_colobjcnts.OldID " +
                                                    "WHERE co.CollectionID = COLMEMID AND d.IsCurrent = TRUE ORDER BY t.RankID DESC, t.TaxonID ASC");
            //System.out.println(coLookUp);
            
            
            //---------------------------------------
            // Loop thru Collection Objects
            //---------------------------------------
            int countAmtTotal = 0;
            boolean firstTime = true;
            int prevTaxonID   = -1;
            
            int taxonID;
            String fullName   = null;
            int rankId        = 0;
            int parentId      = 0;
            int nodeNum       = 0;
            int highNodeNum   = 0;
            int colObjCntAmt  = 0;
            int taxonParentRankID = 0;
            Integer familyId  = null;
            
            ResultSet rs = stmt.executeQuery(coLookUp); 
            while (rs.next())
            {
                // co.CollectionObjectID, t.TaxonID, t.FullName, t.RankID, t.ParentID, t.NodeNumber, t.HighestChildNodeNumber, ios_colobjcnts.NewID, tp.RankID
                //int colObjId          = rsFamily.getInt(1); 
                taxonID = rs.getInt(2); 
                
                if (firstTime)
                {
                     firstTime = false;
                } else if (taxonID != prevTaxonID)
                {
                    writeTaxon(s3Stmt, prevTaxonID, fullName, rankId, parentId, familyId, null, 0, countAmtTotal, nodeNum, highNodeNum);
                    TreeNode pNode = getNode(prevTaxonID, rankId, parentId, taxonParentRankID); 
                    pNode.written = true;
                    pNode.nodeCount += countAmtTotal;
                    countAmtTotal = 0;
                }
                prevTaxonID       = taxonID;
                
                fullName          = rs.getString(3);
                rankId            = rs.getInt(4);
                parentId          = rs.getInt(5);
                nodeNum           = rs.getInt(6);
                highNodeNum       = rs.getInt(7);
                colObjCntAmt      = rs.getInt(8);
                taxonParentRankID = rs.getInt(9);
                
                //Integer gpParentId    = rs.getInt(10);
                //if (rs.wasNull()) gpParentId = null;
                //int gpRankId          = rs.getInt(11);
                
                familyId = taxonParentRankID == 140 ? parentId : null;
                
                countAmtTotal += colObjCntAmt;
            }
            
            writeTaxon(s3Stmt, prevTaxonID, fullName, rankId, parentId, familyId, null, 0, colObjCntAmt, nodeNum, highNodeNum);
            TreeNode ppNode = getNode(prevTaxonID, rankId, parentId, taxonParentRankID);  // Parent
            ppNode.nodeCount += countAmtTotal;
            ppNode.written = true;

            rs.close();
            try
            {
                if (transCnt > 0) dbS3Conn.commit();
            } catch (Exception ex2) { ex2.printStackTrace();}
            
            if (false)
            {
                System.out.println("_id\tFullName\tRankID\tParentID\tTotalCOCnt\tNumObjs");
                Statement stmt1 = dbS3Conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery("SELECT _id, FullName, RankID, ParentID, TotalCOCnt, NumObjs FROM taxon ORDER BY RankID");
                while (rs1.next())
                {
                   for (int i=1;i<7;i++)
                   {
                       System.out.print(rs1.getObject(i)+"\t");
                   }
                   System.out.println();
                }
                rs1.close();
                stmt1.close();
                System.out.println();
            
                dumpTree();
                System.out.println();
            }
            
            skipNodeList = true;
            
            int cnt = 0;
            for (TreeNode node: nodeList)
            {
                Pair<Integer, Integer> famGen = new Pair<Integer, Integer>(0, 0);
                buildTree(node, famGen);
                node.familyId = famGen.first;
                node.genusId  = famGen.second;
                
                cnt++;
                if (cnt % 100 == 0)
                {
                    System.out.println(String.format("%d / %d", cnt, nodeList.size()));
                }
            }
            
            //dumpTree();
            //System.out.println();
            
            //System.out.println("ID\tRK\tTC\tNC\tPID\tPRK\tPTID\tPTRNK\tTC\tTC2");
            for (Integer rankID : treeRanks)
            {
                HashMap<Integer, TreeNode> pNodeHash = taxonHash.get(rankID);
                if (pNodeHash != null)
                {
                    for (TreeNode pNode : pNodeHash.values())
                    {
                        if (pNode.rankId < 180) pNode.genusId = 0;
                        
                        TreeNode parent = getNode(pNode.parentId, pNode.parentRankId, 0, 0);
                        //System.out.println(String.format("%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d\t%d", pNode.taxonId, pNode.rankId, pNode.totalCount, pNode.nodeCount,  pNode.parentId, pNode.parentRankId,
                        //                                                                   parent.taxonId, parent.rankId,  parent.totalCount, (parent.totalCount + pNode.nodeCount + pNode.totalCount)));
                        parent.totalCount += pNode.nodeCount + pNode.totalCount;
                    }
                }
            }
            
            //dumpTree();
            //System.out.println();
              
            try
            {
                if (transCnt > 0) dbS3Conn.commit();
            } catch (Exception ex2) { ex2.printStackTrace();}
            
/*
 * 
`_id` INTEGER PRIMARY KEY, 
`FullName` TEXT,
`RankID` INTEGER,
`ParentID` INTEGER,
`FamilyID` INTEGER,
`GenusID` INTEGER,
`TotalCOCnt` INTEGER,
`NumObjs` INTEGER,
`NodeNum` INTEGER,
`HighNodeNum` INTEGER
 */
            String txLookUp = ipadExporter.adjustSQL("SELECT FullName, NodeNumber, HighestChildNodeNumber FROM taxon WHERE TaxonTreeDefID = TAXTREEDEFID AND TaxonID = ?");
            txLookUpStmt = conn.prepareStatement(txLookUp);
            
            //s3Stmt = dbS3Conn.prepareStatement("UPDATE taxon SET TotalCOCnt=? WHERE _id = ?");
            int totCnt = 0;
            for (Integer rankID : treeRanks)
            {
                HashMap<Integer, TreeNode> pNodeHash = taxonHash.get(rankID);
                if (pNodeHash != null)
                {
                    totCnt += pNodeHash.size();
                }
            }
            
            cnt = 0;
            for (Integer rankID : treeRanks)
            {
                HashMap<Integer, TreeNode> pNodeHash = taxonHash.get(rankID);
                if (pNodeHash != null)
                {
                    for (TreeNode pNode : pNodeHash.values())
                    {
                        if (pNode.written)
                        {
                            s3Stmt2.setInt(1, pNode.familyId);
                            s3Stmt2.setInt(2, pNode.genusId);
                            s3Stmt2.setInt(3, pNode.totalCount);
                            s3Stmt2.setInt(4, pNode.nodeCount);
                            s3Stmt2.setInt(5, pNode.taxonId);
                            int rv = s3Stmt2.executeUpdate();
                            if (rv != 1)
                            {
                                System.err.println("Error updating taxon: "+pNode.taxonId);
                            }
                        } else
                        {
                            txLookUpStmt.setInt(1, pNode.taxonId);
                            rs = txLookUpStmt.executeQuery();
                            if (rs.next())
                            {
                                fullName          = rs.getString(1);
                                nodeNum           = rs.getInt(2);
                                highNodeNum       = rs.getInt(3);
                                writeTaxon(s3Stmt, pNode.taxonId, fullName, pNode.rankId, pNode.parentId, pNode.familyId, pNode.genusId, pNode.totalCount, pNode.nodeCount, nodeNum, highNodeNum);
                            } else
                            {
                                System.err.println("Error looking up taxon: "+pNode.taxonId);
                            }
                            rs.close();
                        }
                        cnt++;
                        if (cnt % 100 == 0)
                        {
                            System.out.println(String.format("%d / %d", cnt, totCnt));
                        }
                    }
                }
            }
            
            try
            {
                if (transCnt > 0) dbS3Conn.commit();
            } catch (Exception ex2) { ex2.printStackTrace();}
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null) stmt.close();
                if (s3Stmt != null) s3Stmt.close();
                if (s3Stmt2 != null) s3Stmt2.close();
                if (txLookUpStmt != null) txLookUpStmt.close();
                
                dbS3Conn.setAutoCommit(true);
            } catch (Exception ex2) { ex2.printStackTrace();}

        }
    }
    
    /**
     * 
     */
    @SuppressWarnings("unused")
    private void exportTreeDataOld()
    {
        Connection        conn1       = null;
        Connection        conn2       = null;
        Connection        conn3       = null;
        Connection        conn4       = null;
        
        PreparedStatement s3Stmt      = null;        
        Statement         stmt        = null;
        Statement         stmtGenera  = null;
        PreparedStatement pStmtKids   = null;
        PreparedStatement coCountLookUpStmt = null;
        
        int transCnt = 0;
        try
        {
            conn1 = DBConnection.getInstance().createConnection();
            conn2 = DBConnection.getInstance().createConnection();
            conn3 = DBConnection.getInstance().createConnection();
            conn4 = DBConnection.getInstance().createConnection();
            
            timeRemaining  = new AtomicInteger(0);
            processedCount = 0;
            
            countDownTimer = new Timer(true); // Daemon Thread
            countDownTimer.schedule(new TimerTask() {
                @Override
                public void run() 
                {
                    if (timeRemaining.get() > 0 && processedCount > 0)
                    {
                        int secondsRemaining = timeRemaining.get() - 1;
                        timeRemaining.set(secondsRemaining);
                        
                        int  hours           = secondsRemaining / secsInHours;
                        secondsRemaining    -= hours * secsInHours;
                        int minutes          = secondsRemaining / secInMin;
                        secondsRemaining    -= minutes * secInMin;
                        
                        final String timeStr = String.format("time remaining: %02d:%02d:%02d", hours, minutes, secondsRemaining);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                progressDelegate.setDesc("Calculating Taxonomic Counts, " + timeStr);
                            }
                        });
                    }
                }
            }, 1000, 1000);
            
            dbS3Conn.setAutoCommit(false);
            s3Stmt     = dbS3Conn.prepareStatement("INSERT INTO taxon (_id, FullName, RankID, ParentID, FamilyID, TotalCOCnt, NumObjs, NodeNum, HighNodeNum) VALUES (?,?,?,?,?,?,?,?,?)");        
            stmt       = conn1.createStatement();
            stmtGenera = conn2.createStatement();
            
            String coLookUp = ipadExporter.adjustSQL("SELECT co.CollectionObjectID, t.NodeNumber, i.NewID FROM taxon t " +
                                                     "INNER JOIN determination d ON t.TaxonID = d.TaxonID " +
                                                     "INNER JOIN collectionobject co ON co.CollectionObjectID = d.CollectionObjectID " +
                                                     "INNER JOIN ios_colobjcnts i ON co.CollectionObjectID = i.OldID " +
                                                     "WHERE co.CollectionID = COLMEMID AND d.IsCurrent = TRUE AND t.NodeNumber >= ? AND t.NodeNumber <= ?");
            System.out.println(coLookUp);
            coCountLookUpStmt = conn3.prepareStatement(coLookUp);
            
            String kidsSQL   = "SELECT co.CollectionObjectID, t.TaxonID, t.FullName, t.RankID, t.ParentID, t.NodeNumber, t.HighestChildNodeNumber FROM taxon t " +
                               "INNER JOIN determination d ON t.TaxonID = d.TaxonID " +
                               "INNER JOIN collectionobject co ON co.CollectionObjectID = d.CollectionObjectID " +
                               "WHERE co.CollectionID = COLMEMID AND d.IsCurrent = TRUE AND NodeNumber >= ? AND NodeNumber <= ? ORDER BY t.TaxonID";
            
            String preSQL    = "SELECT t.TaxonID, t.FullName, t.RankID, t.ParentID, t.NodeNumber, t.HighestChildNodeNumber FROM taxon t ";
            
            String familySQL = preSQL + ipadExporter.adjustSQL("WHERE t.TaxonTreeDefID = TAXTREEDEFID AND RankID = 140");
            
            String extraJoins = "LEFT OUTER JOIN taxon p1 ON t.ParentID = p1.TaxonID " +
                                "LEFT OUTER JOIN taxon p2 ON p1.ParentID = p2.TaxonID " +
                                "LEFT OUTER JOIN taxon p3 ON p2.ParentID = p3.TaxonID " +
                                "LEFT JOIN taxon p4 ON p3.ParentID = p4.TaxonID ";
            
            //String generaSQL = preSQL + extraJoins + ipadExporter.adjustSQL(" WHERE t.TaxonTreeDefID = TAXTREEDEFID AND t.RankID = 180 AND (t.ParentID = %d OR p1.ParentID = %d OR p2.ParentID = %d OR p3.ParentID = %d OR p4.ParentID = %d)");
            String generaSQL = preSQL + extraJoins + ipadExporter.adjustSQL(" WHERE t.TaxonTreeDefID = TAXTREEDEFID AND t.RankID = 180 AND t.ParentID = %d");
            
            
            pStmtKids = conn4.prepareStatement(ipadExporter.adjustSQL(kidsSQL));
            
            String cntSQL = ipadExporter.adjustSQL("SELECT COUNT(*) FROM taxon t WHERE t.TaxonTreeDefID = TAXTREEDEFID AND t.RankID = 140");
            totalRecords  = BasicSQLUtils.getCountAsInt(cntSQL);
            int percentInx = (int)Math.round((float)totalRecords *0.01f);
            if (progressDelegate != null)
            {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        progressDelegate.setDesc("Calculating taxonomic counts...");
                        progressDelegate.setProcess(0, 100);
                        progressDelegate.setProcess(0);
                        progressDelegate.setProcessPercent(true);
                    }
                });
            }
            
            //familySQL = "SELECT t.TaxonID, t.FullName, t.RankID, t.ParentID, t.NodeNumber, t.HighestChildNodeNumber FROM taxon t WHERE t.TaxonID = 369"; // Chiasmodontidae

            Calendar calendar = Calendar.getInstance();
            
            long    startTime       = calendar.getTimeInMillis();
            
            int prevPercent = 0;
            //-----------------------
            // Loop thru Families
            //-----------------------
            ResultSet rsFamily = stmt.executeQuery(familySQL); // Get the GeoID and LocID
            while (rsFamily.next())
            {
                System.out.println("Family TaxonID: "+rsFamily.getInt(1));
                
                int taxonIdFamily     = rsFamily.getInt(1); 
                String fullNameFamily = rsFamily.getString(2);
                int rankIdFamily      = rsFamily.getInt(3);
                int parentIdFamily    = rsFamily.getInt(4);
                int nodeNumFamily     = rsFamily.getInt(5);
                int highNodeNumFamily = rsFamily.getInt(6);
                
                Pair<Integer, Integer> counts = calcTotalForTreeSpan(coCountLookUpStmt, nodeNumFamily, highNodeNumFamily);
                if (counts.first == 0 && counts.second == 0)
                {
                    continue;
                }
                writeTaxon(s3Stmt, taxonIdFamily, fullNameFamily, rankIdFamily, parentIdFamily, null, null, counts.first, counts.second, nodeNumFamily, highNodeNumFamily);
                
                HashSet<Integer> usedRankSet = new HashSet<Integer>();
                //-----------------------
                // Loop thru Genera 
                //-----------------------
                //for (Integer traversalRankId : ranks)
                {
                    //System.out.println("Genera Rank: "+traversalRankId);
                    
                    String gSQL = String.format(generaSQL, taxonIdFamily);//, taxonIdFamily, taxonIdFamily, taxonIdFamily, taxonIdFamily);
                    //System.out.println(gSQL);
                    //gSQL = "SELECT t.TaxonID, t.FullName, t.RankID, t.ParentID, t.NodeNumber, t.HighestChildNodeNumber FROM taxon t WHERE t.TaxonID = 4918"; // Kali

                    ResultSet rs = stmtGenera.executeQuery(gSQL);
                    while (rs.next())
                    {
                        int taxonIdGenera = rs.getInt(1); // Genera TaxonID
                        if (usedRankSet.contains(taxonIdGenera))
                        {
                            continue;
                        }
                        //System.out.println("Genera TaxonID: "+taxonIdGenera);
                        
                        usedRankSet.add(taxonIdGenera);
                        
                        String fullNameGenera = rs.getString(2);
                        int rankIdGenera      = rs.getInt(3);
                        int parentIdGenera    = rs.getInt(4);
                        int nodeNumGenera     = rs.getInt(5);
                        int highNodeNumGenera = rs.getInt(6);
    
                        counts = calcTotalForTreeSpan(coCountLookUpStmt, nodeNumGenera, highNodeNumGenera);
                        if (counts.first == 0 && counts.second == 0)
                        {
                            continue;
                        }
                        writeTaxon(s3Stmt, taxonIdGenera, fullNameGenera, rankIdGenera, parentIdGenera, taxonIdFamily, null, counts.first, counts.second, nodeNumGenera, highNodeNumGenera);
    
                        pStmtKids.setInt(1, nodeNumGenera);
                        pStmtKids.setInt(2, highNodeNumGenera);
                        
                        //---------------------------------
                        // Loop thru all children of Genera
                        //---------------------------------
                        int prevTx = -1;
                        int txId   = -1;
                        ResultSet rsLK = pStmtKids.executeQuery();
                        while (rsLK.next())
                        {
                            txId = rsLK.getInt(2);
                            
                            // Write Children of Genera
                            if (txId != prevTx)
                            {
                                String fullName = rsLK.getString(3);
                                int rankId      = rsLK.getInt(4);
                                int parentId    = rsLK.getInt(5);
                                int nodeNN      = rsLK.getInt(6);
                                int highNN      = rsLK.getInt(7);
                                
//                                counts = calcTotalForTreeSpan(coCountLookUpStmt, nodeNN, highNN);
//                                if (counts.first == 0 && counts.second == 0)
//                                {
//                                    continue;
//                                }
//                                System.out.println("Taxon: "+txId+" CO: "+rsLK.getInt(1));
                                writeTaxon(s3Stmt, txId, fullName, rankId, parentId, taxonIdFamily, null, 1, 1, nodeNN, highNN);
                                prevTx = txId;
                            }
                        }
                        rsLK.close();
                    }
                    rs.close();
                }
                processedCount++;
                if (percentInx > 0 && processedCount % percentInx == 0) 
                {
                    float percentFloat     = (float)processedCount / (float)totalRecords;
                    float elapsedMilliSecs = (Calendar.getInstance()).getTimeInMillis() - startTime;
                    float elapsedSecs      = elapsedMilliSecs / 1000.0f;
                    int   secondsRemaining = (int)(elapsedSecs / percentFloat);
                    timeRemaining.set(secondsRemaining);
                    
                    int percent = Math.min((int)(percentFloat * 100.0f), 100);
                    if (percent != prevPercent)
                    {
                        worker.firePropertyChange(iPadDBExporter.PROGRESS, 0, percent);
                        prevPercent = percent;
                    }
                }
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    worker.firePropertyChange(iPadDBExporter.PROGRESS, 0, 100);
                    progressDelegate.setDesc("Done calculating taxonomic counts.");
                }
            });
            countDownTimer.cancel();
            countDownTimer = null;
            
            stmtGenera.close();
            rsFamily.close();
            
            try
            {
                if (transCnt > 0) dbS3Conn.commit();
            } catch (Exception ex2) { ex2.printStackTrace();}
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null) stmt.close();
                if (s3Stmt != null) s3Stmt.close();
                if (pStmtKids != null) pStmtKids.close();
                if (coCountLookUpStmt != null) coCountLookUpStmt.close();

                if (conn1 != null) conn1.close();
                if (conn2 != null) conn2.close();
                if (conn3 != null) conn3.close();
                if (conn4 != null) conn4.close();
                
                dbS3Conn.setAutoCommit(true);
            } catch (Exception ex2) { ex2.printStackTrace();}

        }
    }
    
    class TreeNode
    {
        int     taxonId;
        int     rankId;
        int     totalCount;
        int     nodeCount;
        Integer parentId;
        int     parentRankId;
        boolean visited;
        boolean written;
        
        int     familyId;
        int     genusId;
        
        /**
         * @param taxonId
         * @param totalCount
         */
        public TreeNode(int taxonId, 
                        int rankId,
                        Integer parentId,
                        int parentRankId)
        {
            super();
            this.taxonId      = taxonId;
            this.rankId       = rankId;
            this.totalCount   = 0;
            this.nodeCount    = 0;
            this.parentId     = parentId;
            this.parentRankId = parentRankId;
            this.visited      = false;
            this.written      = false;
        }
        
    }
}

/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.wb.wbuploader;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.specify.tasks.subpane.wb.graph.DirectedGraph;
import edu.ku.brc.specify.tasks.subpane.wb.graph.DirectedGraphException;
import edu.ku.brc.specify.tasks.subpane.wb.graph.Vertex;
import edu.ku.brc.specify.tasks.subpane.wb.schema.DBSchema;
import edu.ku.brc.specify.tasks.subpane.wb.schema.Field;
import edu.ku.brc.specify.tasks.subpane.wb.schema.Relationship;
import edu.ku.brc.specify.tasks.subpane.wb.schema.Table;

/**
 * @author timbo
 *
 * @code_status Alpha
 *
 *Given a database schema, this class builds a DirectedGraph of the schema (with modifications) for use in workbench uploads. 
 */
public class GraphBuilder
{
    /**
     * The DBTableIdMgr instance for the database being graphed
     */
    private DBTableIdMgr                       schema;
    /**
     * The DBSchema representing the database being graphed.
     */
    private DBSchema scheme;
    /**
     * The graph constructed for the database.
     */
    private DirectedGraph<Table, Relationship> g;
    /**
     * short class names of tables to exclude from graph
     */
    private SortedSet<String> excludeTbls; 
    private static final Logger log = Logger.getLogger(GraphBuilder.class);
        
    
    /**
     * @param schema
     * @param scheme
     * @param excludeTbls
     */
    public GraphBuilder(final DBTableIdMgr schema, final DBSchema scheme, String[] excludeTbls)
    {
        this.schema = schema;
        this.scheme = scheme;
        this.buildExcludeTables(excludeTbls);
    }
    
    /**
     * @param aSchema
     * @return a graph of the schema, modified for use during workbench uploads
     * @throws DirectedGraphException
     */
    public DirectedGraph<Table, Relationship> buildGraph()
            throws DirectedGraphException
    {
        try
        {
            g = new DirectedGraph<Table, Relationship>();

            // add vertices
            for (DBTableIdMgr.TableInfo tbl : schema.getList())
            {
                if (includeTable(tbl))
                {
                    g.addVertex(buildTableVertex(tbl));
                }
            }

            // add edges
            for (Vertex<Table> vTbl : g.getVertices())
            {
                DBTableIdMgr.TableInfo tbl = vTbl.getData().getTableInfo();
                for (DBTableIdMgr.TableRelationship rel : tbl.getRelationships())
                {
                    String relTblName = getRelShortName(rel);
                    if (relTblName != null && g.getVertexByLabel(relTblName) != null && includeEdge(tbl.getShortClassName().toLowerCase(), relTblName, rel))
                    {
                        Relationship relationship = getOneToManyRelationship(tbl, rel);
                        if (relationship != null)
                        {
                            g.addEdge(relTblName, tbl.getShortClassName().toLowerCase(), relationship);
                        }
                    }
                }
            }
            return g;
        }
        catch (DirectedGraphException ex)
        {
            throw ex;
        }
    }

    /**
     * @param tblName
     * @param relTblName
     * @param rel
     * @return false if the edge should be included in the graph
     */
    private boolean includeEdge(final String tblName, final String relTblName, final DBTableIdMgr.TableRelationship rel)
    {
        //only one-to-manys are needed
        //if (rel.getType() != DBTableIdMgr.RelationshipType.OneToMany)
        if (rel.getType() != DBTableIdMgr.RelationshipType.ManyToOne)
        {
            return false;
        }
        //no cycles
        if (tblName.equals(relTblName))
        {
            return false;
        }
        return true;
    }
    
    /**
     * @param tbl
     * @return true if tbl should be included in graph
     */
    private boolean includeTable(final DBTableIdMgr.TableInfo tbl)
    {
        return  !excludeTbls.contains(tbl.getShortClassName().toLowerCase());
    }
    
    /**
     * @param tbl
     * @param rel
     * @return a Relationship object corresponding to a TableRelationship
     */
    private Relationship getOneToManyRelationship(final DBTableIdMgr.TableInfo tbl,
                                         final DBTableIdMgr.TableRelationship rel)
    {
        try
        {
            DBTableIdMgr.TableInfo oneSideTblInfo = schema.getByClassName(rel.getClassName());
            Table oneSideTbl = scheme.getTable(oneSideTblInfo.getShortClassName());
            Field oneSideFld = oneSideTbl.getField(oneSideTblInfo.getIdColumnName());
            Table manySideTbl = scheme.getTable(tbl.getShortClassName());
            String manySideFldName = rel.getColName() == null ? oneSideFld.getName() : rel.getColName();
            Field manySideFld = manySideTbl.getField(manySideFldName);
            if (oneSideFld == null || manySideFld == null)
            {
                log.debug(rel.getName() + ": one of the fields is null."); 
            }
            return new Relationship(oneSideFld, manySideFld, "OneToMany");
        }
        catch (RuntimeException ex)
        {
            log.debug("relationship " + rel.getName() + "could not be added to database graph.");
            return null;
        }
    }

    /**
     * @param rel
     * @return rel's shortname or null in case of difficulty
     */
    private String getRelShortName(final DBTableIdMgr.TableRelationship rel)
    {
        try
        {
            return schema.getByClassName(rel.getClassName()).getShortClassName().toLowerCase();
        } catch (RuntimeException ex)
        {
            return null;
        }
    }

    /**
     * @param tbl
     * @return Vertex for tbl
     */
    private Vertex<Table> buildTableVertex(DBTableIdMgr.TableInfo tbl)
    {
        Table table = scheme.getTable(tbl.getShortClassName());
        if (table == null)
        {
            log.debug("unable to find " + tbl.getShortClassName() + " in DBSchema.");
            return null;
        }
        return new Vertex<Table>(tbl.getShortClassName().toLowerCase(), table);
    }
    
    /**
     * @param badTbls 
     * 
     * builds a sortedSet of tables to exclude from graph.
     */
    private void buildExcludeTables(final String[] badTbls)
    {
        excludeTbls = new TreeSet<String>();
        for (String tbl : badTbls)
        {
            excludeTbls.add(tbl);
        }
    }
}

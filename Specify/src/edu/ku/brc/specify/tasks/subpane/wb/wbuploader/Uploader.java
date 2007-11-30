/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.wb.wbuploader;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.log4j.Logger;

import edu.ku.brc.af.core.expresssearch.TableNameRenderer;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.Determination;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.datamodel.WorkbenchDataItem;
import edu.ku.brc.specify.datamodel.WorkbenchRow;
import edu.ku.brc.specify.tasks.ReportsBaseTask;
import edu.ku.brc.specify.tasks.WorkbenchTask;
import edu.ku.brc.specify.tasks.subpane.wb.WorkbenchPaneSS;
import edu.ku.brc.specify.tasks.subpane.wb.graph.DirectedGraph;
import edu.ku.brc.specify.tasks.subpane.wb.graph.DirectedGraphException;
import edu.ku.brc.specify.tasks.subpane.wb.graph.Edge;
import edu.ku.brc.specify.tasks.subpane.wb.graph.Vertex;
import edu.ku.brc.specify.tasks.subpane.wb.schema.Field;
import edu.ku.brc.specify.tasks.subpane.wb.schema.Relationship;
import edu.ku.brc.specify.tasks.subpane.wb.schema.Table;
import edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadMappingDefRel.ImportMappingRelFld;
import edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadTable.DefaultFieldEntry;
import edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadTable.RelatedClassEntry;
import edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadTable.UploadTableInvalidValue;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.JStatusBar;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.util.Pair;


/**
 * @author timo
 * 
 */
public class Uploader implements ActionListener, WindowStateListener
{
    //Phases in the upload process...
    protected static String CHECKING_REQS = "WB_UPLOAD_CHECKING_REQS";
    protected static String VALIDATING_DATA = "WB_UPLOAD_VALIDATING_DATA";
    protected static String READY_TO_UPLOAD = "WB_UPLOAD_READY_TO_UPLOAD";
    protected static String UPLOADING = "WB_UPLOAD_UPLOADING";
    protected static String SUCCESS = "WB_UPLOAD_SUCCESS";
    protected static String RETRIEVING_UPLOADED_DATA = "WB_RETRIEVING_UPLOADED_DATA";
    protected static String FAILURE = "WB_UPLOAD_FAILURE";
    protected static String USER_INPUT = "WB_UPLOAD_USER_INPUT";
    protected static String UNDOING_UPLOAD = "WB_UPLOAD_UNDO";
    
    /**
     * one of above statics
     */
    protected String currentOp;
    

    /**
     * used by bogusViewer
     */
    Map<String, Vector<Vector<String>>> bogusStorages = null;    
    /**
     * Displays uploaded data. Roughly.
     */
    protected DB.BogusViewer bogusViewer = null;
    
    protected DB db;

	protected UploadData uploadData;
    
    /**
     * The WorkbenchPane for the uploading dataset. 
     */
    protected WorkbenchPaneSS wbSS;

	protected Vector<UploadField> uploadFields;

	protected Vector<UploadTable> uploadTables;

	protected DirectedGraph<Table, Relationship> uploadGraph;
    
    boolean verbose = false;
    
    boolean dataValidated = false;
    
    protected UploadMainForm mainForm;
    
    /**
     * Problems with contents of cells in dataset.
     */
    protected Vector<UploadTableInvalidValue> validationIssues = null;
    /**
     * This object assigns default values for missing required fields and foreign keys.
     * And provides UI for viewing and changing the defaults.
     */
    MissingDataResolver resolver;
    
    /**
     * Required related classes that are not available in the dataset.
     */
    protected Vector<UploadTable.RelatedClassEntry> missingRequiredClasses;
    /**
     * Required fields not present in the dataset.
     */
    protected Vector<UploadTable.DefaultFieldEntry> missingRequiredFields;
        
    /**
     *  While an upload is underway, this member will be provide access to the uploader.
     */
    protected static Uploader currentUpload = null;
    
    /**
     *  A unique identifier currently used to identify the upload. Currently not used.
     *  NOTE: Would it be desirable to store info on imports - dataset imported, date, user, basic stats ??? 
     * 
     */
    protected String identifier;

    protected static final Logger log = Logger.getLogger(Uploader.class);
   
    
   private class SkippedRow implements UploadMessage
    {
        protected UploaderException cause;
        protected int row;
        /**
         * @param cause
         * @param row
         */
        public SkippedRow(UploaderException cause, int row) 
        {
            super();
            this.cause = cause;
            this.row = row;
        }
        /**
         * @return the cause
         */
        public UploaderException getCause()
        {
            return cause;
        }

        /* (non-Javadoc)
         * @see edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadMessage#getRow()
         */
        public int getRow()
        {
            return row;
        }
        
        /* (non-Javadoc)
         * @see edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadMessage#getCol()
         */
        public int getCol()
        {
            return -1;
        }
        
        /* (non-Javadoc)
         * @see edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadMessage#getMsg()
         */
        public String getMsg()
        {
            return cause.getMessage();
        }
        
        /* (non-Javadoc)
         * @see edu.ku.brc.specify.tasks.subpane.wb.wbuploader.UploadMessage#getData()
         */
        public Object getData()
        {
            return cause;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return getMsg();
        }
    }
    
    protected Vector<SkippedRow> skippedRows;
    
    protected Vector<UploadMessage> messages;
    protected Vector<UploadMessage> newMessages;
    
    
    /**
     * @return dataValidated
     */
    public boolean getDataValidated()
    {
        return dataValidated;
    }
    
    /**
     * @return the currentUpload;
     */
    public static Uploader getCurrentUpload()
    {
        return currentUpload;
    }
    
    /**
     * the index of the currently processing row in the dataset. 
     */
    protected int rowUploading;
    
    /**
     * @return rowUploading
     */
    public int getRow()
    {
        return rowUploading;
    }
    /**
     * @return the identifier.
     */
    public final String getIdentifier()
    {
        return identifier;
    }
    
    /**
     * creates an identifier for an importer
     * 
     */
    protected void buildIdentifier()
    {
        Calendar now = new GregorianCalendar();
        identifier =  uploadData.getWbRow(0).getWorkbench().getName() + "_"+ now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-"
            + now.get(Calendar.DAY_OF_MONTH) + "_" + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.SECOND);
    }
    
    /**
     * @param f
     * @return an existing importTable that contains f
     */
    protected UploadTable getUploadTable(UploadField f)
	{
		for (UploadTable result : uploadTables)
		{
			if (result.getTable().getName().equals(f.getField().getTable().getName()) 
					&& (result.getRelationship() == null && f.getRelationship() == null || (result.getRelationship() != null && f.getRelationship() != null && result.getRelationship().equals(f.getRelationship()))))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * @throws UploaderException
     * 
     * builds uploadTables member based on uploadFields' contents
	 */
	protected void buildUploadTables() throws UploaderException
	{
		uploadTables = new Vector<UploadTable>();
		for (UploadField f : uploadFields)
		{
            if (f.getField() != null)
            {
                UploadTable it = getUploadTable(f);
                boolean addIt = it == null;
                if (addIt)
                {
                    it = new UploadTable(f.getField().getTable(), f.getRelationship());
                    it.init();
                }
                if (it == null) { throw new UploaderException("failed to construct import table.",
                        UploaderException.ABORT_IMPORT); }
                it.addField(f);
                if (addIt)
                {
                    uploadTables.add(it);
                }
            }
 		}
	}


	/**
	 * @param mapping
	 * @throws UploaderException
     * 
     * Adds elements to uploadFields as required for relationship described in mapping. 
	 */
	protected void addMappingRelFlds(UploadMappingDefRel mapping) throws UploaderException
	{
		if (mapping.getSequenceFld() != null)
		{
			Field fld = db.getSchema().getField(mapping.getTable(), mapping.getSequenceFld());
            if (fld == null)
            {
                log.debug("could not find field in db: " + mapping.getTable() + "." + mapping.getField());
            }
            UploadField newFld = new UploadField(fld, -1, mapping.getWbFldName(), null);
			newFld.setSequence(mapping.getSequence());
			newFld.setValue(mapping.getSequence().toString());
			uploadFields.add(newFld);
		}
		Table t1 = db.getSchema().getTable(mapping.getTable());
		Table t2 = db.getSchema().getTable(mapping.getRelatedTable());
		for (ImportMappingRelFld fld : mapping.getLocalFields())
		{
			Field dbFld = t1.getField(fld.getFieldName());
            if (dbFld == null)
            {
                log.debug("could not find field in db: " + t1.getName() + "." + fld.getFieldName());
            }
            UploadField newFld = new UploadField(dbFld, fld.getFldIndex(), fld.getWbFldName(), null);
			newFld.setSequence(mapping.getSequence());
			uploadFields.add(newFld);
		}
		if (mapping.getRelatedFields().size() > 0)
		{
			Relationship r = null;
            Vector<Relationship> rs;
			try
			{
				rs = db.getGraph().getAllEdgeData(t1, t2);
				if (rs.size() == 0)
				{
					rs = db.getGraph().getAllEdgeData(t2, t1);
				}
			} catch (DirectedGraphException ex)
			{
				throw new UploaderException(ex, UploaderException.ABORT_IMPORT);
			}
            //find the 'right' rel. ie: discard Agent ->> ModifiedByAgentID/CreatedByAgentID
            for (Relationship rel : rs)
            {
                if (!rel.getRelatedField().getName().equalsIgnoreCase("modifiedbyagentid") && !rel.getRelatedField().getName().equalsIgnoreCase("createdbyagentid")) 
                {
                    r = rel;
                    break;
                }
            }
			if (r != null)
			{
				Vector<ImportMappingRelFld> relFlds = mapping
						.getRelatedFields();
				for (int relF = 0; relF < relFlds.size(); relF++)
				{
					Field fld = db.getSchema().getField(t2.getName(),relFlds.get(relF).getFieldName());
                    int fldIdx = relFlds.get(relF).getFldIndex();
                    String wbFldName = relFlds.get(relF).getWbFldName();
                    UploadField newFld = new UploadField(fld, fldIdx, wbFldName, r);
					newFld.setSequence(mapping.getSequence());
					uploadFields.add(newFld);
				}
			} else
			{
				throw new UploaderException(
						"could not find relationship for mapping.", UploaderException.ABORT_IMPORT);
			}
		}
	}
    
	
	
    /**
     * @throws UploaderException
     * 
     * builds ImportFields required for import and adds them to uploadFields member. 
     */
    protected void buildUploadFields() throws UploaderException
	{
		for (int f = 0; f < uploadData.getCols(); f++)
		{
			UploadMappingDef m = uploadData.getMapping(f);
			if (m.getClass() != UploadMappingDefTree.class)
			{
				Field fld = this.db.getSchema().getField(m.getTable(), m.getField());
                if (fld == null)
                {
                    log.debug("could not find field in db: " + m.getTable() + "." + m.getField());
                }
                UploadField newFld = new UploadField(fld, m.getIndex(), m.getWbFldName(),
						null);
				uploadFields.add(newFld);
				if (m.getClass() == UploadMappingDefRel.class)
				{
					UploadMappingDefRel relM = (UploadMappingDefRel) m;
					newFld.setSequence(relM.getSequence());
					try
					{
						addMappingRelFlds(relM);
						newFld.setIndex(-1);
					} catch (UploaderException ex)
					{
						throw ex;
					}
				}
			}
		}
	}
    
    /**
     * @return a "printout" of the uploadFields member.
     */
    public Vector<String> printUploadFields()
    {
        Vector<String> lines = new Vector<String>();
        for (UploadField impF : uploadFields)
        {
            lines.add(impF.getField().getTable().getName()
                    + "."
                    + impF.getField().getName()
                    + " ["
                    + Integer.toString(impF.getIndex())
                    + "] "
                    + (impF.getSequence() == null ? "" : " (" + impF.getSequence().toString()
                            + ")"));
        }
        return lines;
    }
    
    /**
     * @return a printout of info about the uploadGraph
     * 
     * @throws DirectedGraphException
     */
    public Vector<String> printGraphInfo() throws DirectedGraphException
    {
        Vector<String> lines = new Vector<String>();
            lines.add("vertices:");
            for (Vertex<Table> v : uploadGraph.getVertices())
            {
                lines.add("   " + v.getLabel());
            }
            Vector<String> graphEdges = uploadGraph.listEdges();
            lines.add("");
            lines.add("edges:");
            for (String e : graphEdges)
            {
                lines.add(e);
            }
            lines.add("");
            lines.add("Graph sources:");
            Set<Vertex<Table>> sources = uploadGraph.sources();
            for (Vertex<Table> tbl : sources)
            {
                lines.add("   " + tbl.getLabel());
            }
            lines.add("");
            if (uploadGraph.isStronglyConnected())
            {
                lines.add("graph is strongly connected.");
            }
            else
            {
                lines.add("graph is not strongly connected.");
            }
         return lines;
    }
	
    /**
     * @return a "printout" of the uploadTables member.
     */
    public Vector<String> printUploadTables()
    {
        Vector<String> lines = new Vector<String>();
        for (UploadTable impT : uploadTables)
        {
            lines.add(impT.getTable().getName());
            for (Vector<UploadField> seq : impT.getUploadFields())
            {
                for (UploadField f : seq)
                {
                    lines.add("       "
                            + f.getField().getName()
                            + (f.getSequence() == null ? "" : " (" + f.getSequence().toString()
                                    + ")"));
                }
            }
        }
        return lines;
    }
    
	/**
	 * @param db
	 * @param uploadData
	 * @throws UploaderException
	 */
	public Uploader(DB db, UploadData importData, final WorkbenchPaneSS wbSS) throws UploaderException
	{
		this.db = db;
		this.uploadData = importData;
        this.wbSS = wbSS;
		this.uploadFields = new Vector<UploadField>(importData.getCols());
        this.missingRequiredClasses = new Vector<UploadTable.RelatedClassEntry>();
        this.missingRequiredFields = new Vector<UploadTable.DefaultFieldEntry>();
        this.skippedRows = new Vector<SkippedRow>();
        this.messages = new Vector<UploadMessage>();
        this.newMessages = new Vector<UploadMessage>();
		buildUploadFields();
		buildUploadTables();
        addEmptyUploadTables();
		buildUploadGraph();
		processTreeMaps();
		orderUploadTables();
        buildUploadTableParents();
        reOrderUploadTables();
 	}

    /**
     * Adds extra upload tables.
     * Currently only adds Determination if necessary when Genus/Species are selected.
     * Also should add CollectingEvent if Locality and CollectionObject are present.
     * And others???
     */
    protected void addEmptyUploadTables() throws UploaderException
    {
        boolean genSpPresent = false, detPresent = false, locPresent = false, coPresent = false, cePresent = false;
        for (UploadTable ut : uploadTables)
        {
            if (ut.getTblClass().equals(Determination.class))
            {
                detPresent = true;
            }
            if (ut.getTblClass().equals(Locality.class))
            {
                locPresent = true;
            }
            if (ut.getTblClass().equals(CollectionObject.class))
            {
                coPresent = true;
            }
            if (ut.getTblClass().equals(CollectingEvent.class))
            {
                cePresent = true;
            }
        }
        if (!detPresent)
        {
            int maxSeq = 0;
            WorkbenchRow wbRow = uploadData.getWbRow(0);
            for (WorkbenchDataItem mapI : wbRow.getWorkbenchDataItems())
            {
                String fldName = mapI.getWorkbenchTemplateMappingItem().getFieldName();
                if (fldName.startsWith("genus") || fldName.startsWith("species")
                        || fldName.startsWith("variety") || fldName.startsWith("subspecies"))
                {
                    genSpPresent = true;
                    if (Integer.valueOf(fldName.substring(fldName.length() - 1)) > maxSeq)
                    {
                        maxSeq = Integer.valueOf(fldName.substring(fldName.length() - 1));
                    }
                }
            }
            if (genSpPresent)
            {
                UploadTable det = new UploadTable(db.getSchema().getTable("Determination"), null);
                det.init();
                for (int seq = 0; seq < maxSeq; seq++)
                {
                    UploadField fld = new UploadField(db.getSchema().getField("determination",
                            "collectionobjectid"), -1, null, null);
                    fld.setSequence(seq);
                    det.addField(fld);
                }
                uploadTables.add(det);
            }
        }
        if (!cePresent && locPresent && coPresent)
        {
            UploadTable ce = new UploadTable(db.getSchema().getTable("CollectingEvent"), null);
            ce.init();
            ce.addField(new UploadField(db.getSchema().getField("collectingevent", "stationfieldnumber"), -1, null, null));
            uploadTables.add(ce);
        }
    }
    
    /**
     * Imposes additional ordering constraints created by the matchChildren property of UploadTable.
     * I.e. if A precedes B and B is in C.matchChildren, then A must precede C.
     */
    protected void reOrderUploadTables() throws UploaderException
    {
        SortedSet<Pair<UploadTable, UploadTable>> moves = new TreeSet<Pair<UploadTable, UploadTable>>(new Comparator<Pair<UploadTable,UploadTable>>(){
            private boolean isAncestorOf(UploadTable t1, UploadTable t2)
            {
                log.debug("isAncestorOf(" + t1 + ", " + t2 + ")");
                if (t1.equals(t2))
                {
                    return true;
                }
                for (Vector<ParentTableEntry> ptes : t2.getParentTables())
                {
                    for (ParentTableEntry pte : ptes)
                    {
                        if (isAncestorOf(t1, pte.getImportTable()))
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
            public int compare(Pair<UploadTable, UploadTable> p1, Pair<UploadTable, UploadTable> p2)
            {
                if (isAncestorOf(p1.getSecond(), p2.getSecond()))
                {
                    return -1;
                }
                if (isAncestorOf(p2.getSecond(), p1.getSecond()))
                {
                    return 1;
                }
                return 0;
            }
        });
        for (UploadTable ut : uploadTables)
        {
            for (UploadTable mc : ut.getMatchChildren())
            {
                for (ParentTableEntry pte : mc.getAncestors())
                {
                    if (uploadTables.indexOf(ut) < uploadTables.indexOf(pte.getImportTable()))
                    {
                        moves.add(new Pair<UploadTable, UploadTable>(ut, pte.getImportTable()));
                    }
                    
                }
            }
        }
        for (Pair<UploadTable, UploadTable> move : moves)
        {
            int fromIdx = uploadTables.indexOf(move.getSecond());
            int toIdx = uploadTables.indexOf(move.getFirst());
            if (toIdx > fromIdx)
            {
                log.error("Can't meet ordering constraints: " + move.getSecond().getTable().getName() + "," + move.getFirst().getTable().getName());
                throw new UploaderException("The Dataset is not uploadable.", UploaderException.ABORT_IMPORT);
            }
            uploadTables.remove(fromIdx);
            uploadTables.insertElementAt(move.getSecond(), toIdx);
        }
    }
	/**
	 * @throws UploaderException
     * builds the uploadGraph.
	 */
	protected void buildUploadGraph() throws UploaderException
	{
		uploadGraph = new DirectedGraph<Table, Relationship>();
		try
		{
			for (UploadTable t : uploadTables)
			{
				String label = t.getTable().getName();
				if (uploadGraph.getVertexByLabel(label) == null)
				{
					uploadGraph.addVertex(new Vertex<Table>(label, t.getTable()));
				}
			}
            for (Edge<Table, Relationship> edge : db.getGraph().getEdges())
            {
                Vector<UploadTable> its1 = getUploadTable(edge.getPointA().getData());
                Vector<UploadTable> its2 = getUploadTable(edge.getPointB().getData());
                if (its1.size() > 0 && its2.size() > 0)
                {
                    uploadGraph.addEdge(edge.getPointA().getLabel(), edge.getPointB().getLabel(), edge.getData());
                }
            }
		} catch (DirectedGraphException e)
		{
            log.debug(e);
            throw new UploaderException(e, UploaderException.ABORT_IMPORT);
		}
	}
    
   /**
     * @param treeMap
     * @param level
     * @return a name for table representing data with rank represented by level param.
     */
    protected String getTreeTableName(final UploadMappingDefTree treeMap, final int level)
	{
		return treeMap.getTable() + Integer.toString(treeMap.getLevels().get(level).get(0).getRank());
	}
		
	/**
	 * @param treeMap
	 * @throws UploaderException
     * adds Tables, ImportTables, ImportFields required by heirarchy represented by treeMap param.
	 */
	protected void processTreeMap(UploadMappingDefTree treeMap) throws UploaderException
	{
		Table baseTbl = db.getSchema().getTable(treeMap.getTable());
		if (baseTbl == null)
		{
			throw new UploaderException(
					"Could not find base table for tree mapping.",
					UploaderException.ABORT_IMPORT);
		}
		Table parentTbl = null;
		UploadTableTree parentImpTbl = null;
		for (int level = 0; level < treeMap.getLevels().size(); level++)
		{
			// add new table to import graph for rank
            Table rankTbl = new Table(getTreeTableName(treeMap, level), baseTbl);
			try
			{
				uploadGraph.addVertex(new Vertex<Table>(rankTbl.getName(),
						rankTbl));
				if (parentTbl != null)
				{
					Relationship rankRel = new Relationship(parentTbl.getKey(),
							rankTbl.getField(treeMap.getParentField()),
							"OneToMany");
					uploadGraph.addEdge(parentTbl.getName(), rankTbl.getName(),
							rankRel);
				}
			} catch (DirectedGraphException ex)
			{
				throw new UploaderException(ex);
			}
			parentTbl = rankTbl;

			// create UploadTable for new table

			UploadTableTree it = new UploadTableTree(rankTbl, baseTbl,
					parentImpTbl, treeMap.getLevels().get(level).get(0).isRequired(),
                    treeMap.getLevels().get(level).get(0).getRank(), 
                    treeMap.getLevels().get(level).get(0).getWbFldName());
            it.init();
			
			// add ImportFields for new table
			for (int seq = 0; seq < treeMap.getLevels().get(level).size(); seq++)
			{
                Field fld = rankTbl.getField(treeMap.getField());
                int fldIdx = treeMap.getLevels().get(level).get(seq).getIndex();
                String wbFldName = treeMap.getLevels().get(level).get(seq).getWbFldName();
                UploadField newFld1 = new UploadField(fld, fldIdx, wbFldName, null);
				newFld1.setRequired(true);
				newFld1.setSequence(seq);
				uploadFields.add(newFld1);
				UploadField newFld2 = new UploadField(rankTbl.getField("rankId"),
						-1, null, null);
				newFld2.setRequired(true);
				newFld2.setValue(Integer.toString(treeMap.getLevels()
						.get(level).get(0).getRank()));
				newFld2.setSequence(seq);
				uploadFields.add(newFld2);

				// add UploadTable for new table

				it.addField(newFld1);
				it.addField(newFld2);
			}
			uploadTables.add(it);
			parentImpTbl = it;
		}

		// add relationships from base table to other tables

		if (parentTbl != null)
		{
            for (Edge<Table, Relationship> e : db.getGraph().getEdges())
            {
                if (e.getPointA().getData().equals(baseTbl))
                {
                    Vertex<Table> relTblVertex = uploadGraph.getVertexByLabel(e.getPointB().getLabel());
                    if (relTblVertex != null)
                    {
                        String relFld1Name = e.getData().getField().getName();
                        Relationship rel = new Relationship(parentTbl.getField(relFld1Name), e.getData().getRelatedField(), e.getData().getRelType());
                        try
                        {
                            uploadGraph.addEdge(parentTbl.getName(), relTblVertex.getLabel(), rel);
                        } catch (DirectedGraphException ex)
                        {
                            throw new UploaderException(ex, UploaderException.ABORT_IMPORT);
                        }
                    }
                }
            }
		}
	}
    
	
	/**
	 * @throws UploaderException
     * 
     * processes UploadMappingDefTree objects in uploadData.
	 */
	protected void processTreeMaps() throws UploaderException
	{
		for (int m = 0; m < uploadData.getCols(); m++)
		{
			if (uploadData.getMapping(m).getClass() == UploadMappingDefTree.class)
			{
				UploadMappingDefTree treeMap = (UploadMappingDefTree) uploadData
						.getMapping(m);
				processTreeMap(treeMap);
			}
		}
	}
    
	
	/**
	 * @param t
	 * @return ImportTables with Table equal to t.
	 */
	protected Vector<UploadTable> getUploadTable(Table t)
	{
		SortedSet<UploadTable> its = new TreeSet<UploadTable>();
		for (UploadTable it : uploadTables)
		{
			if (it.getTable().equals(t))
			{
				its.add(it);
			}
		}
		return new Vector<UploadTable>(its);
	}
    
    /**
     * @author timbo
     *
     * @code_status Alpha
     *
     *Handles 'parent-child' relationships between UploadTables
     */
    public class ParentTableEntry
    {
        /**
         * The parent UploadTable
         */
        protected UploadTable importTable;
        /**
         * The relationship to the parent
         */
        protected Relationship  parentRel;
         /**
         * The hibernate property name of the foreign key.
         */
        protected String propertyName;
        /**
         * the setXXX method used to set objects of importTable's class to children.
         */
        protected Method setter;
        /**
         * @param importTable
         * @param parentRel
         */
        public ParentTableEntry(UploadTable importTable, Relationship parentRel)
        {
            super();
            this.importTable = importTable;
            this.parentRel = parentRel;
        }
        /**
         * @return the importTable
         */
        public final UploadTable getImportTable()
        {
            return importTable;
        }
        /**
         * @return the parentRel
         */
        public final Relationship getParentRel()
        {
            return parentRel;
        }
        /**
         * @return the setter
         */
        public final Method getSetter()
        {
            return setter;
        }
        /**
         * @param setter the setter to set
         * Also sets the propertyName.
         */
        public final void setSetter(Method setter)
        {
            this.setter = setter;
            if (this.setter.getName().startsWith("set"))
            {
                this.propertyName = UploadTable.deCapitalize(this.setter.getName().substring(3));
            }
            else
            {
                this.propertyName = UploadTable.deCapitalize(this.setter.getName());
            }
        }
        public final String getForeignKey()
        {
            if (parentRel == null)
            {
                return importTable.getTblClass().getSimpleName();
            }
            return parentRel.getRelatedField().getName();
        }
        /**
         * @return the propertyName
         */
        public final String getPropertyName()
        {
            return propertyName;
        }
    }
    

    /**
     * @throws UploaderException
     * 
     * Determines 'parent' UploadTables for each UploadTable
     */
    protected void buildUploadTableParents() throws UploaderException
    {
        for (UploadTable it : uploadTables)
        {
            Vector<Vector<ParentTableEntry>> parentTables = new Vector<Vector<ParentTableEntry>>();
            Set<Vertex<Table>> tbls = uploadGraph.into(it.getTable());
            for (Vertex<Table> tv : tbls)
            {
                try
                {
                    Vector<Relationship> rs = uploadGraph.getAllEdgeData(tv.getData(), it.getTable());
                    for (Relationship r : rs)
                    {
                        Vector<UploadTable> impTs = getUploadTable(tv.getData());
                        Vector<ParentTableEntry> entries = new Vector<ParentTableEntry>();
                        for (UploadTable impT : impTs)
                        {
                            if (impT.getRelationship() == null || r.equals(impT.getRelationship()))
                            {
                                impT.setHasChildren(true);
                                entries.add(new ParentTableEntry(impT, r));
                            }
                        }
                        parentTables.add(entries);
                    }
                }
                catch (DirectedGraphException ex)
                {
                    throw new UploaderException(ex, UploaderException.ABORT_IMPORT);
                }
            }
            it.setParentTables(parentTables);
        }
    }
    
    
	/**
     * @throws UploaderException
     * 
     * Orders uploadTables according to dependencies in uploadGraph.
     */
	protected void orderUploadTables() throws UploaderException
	{
		try
		{
			Vector<Vertex<Table>> topoSort = uploadGraph.getTopoSort();
			Vector<UploadTable> newTables = new Vector<UploadTable>();
			for (Vertex<Table> v : topoSort)
			{
				Vector<UploadTable> its = getUploadTable(v.getData());
				for (UploadTable it : its)
				{
					newTables.add(it);
					uploadTables.remove(it);
				}
				if (uploadTables.size() == 0)
				{
					break;
				}
			}
			uploadTables = newTables;
		} catch (DirectedGraphException ex)
		{
			throw new UploaderException(ex);
		}
	}
    

    /**
     *  Validates contents of all cells in dataset.
     */
    public void validateData()
    {       
        dataValidated = false;
        
        final Vector<UploadTableInvalidValue> issues = new Vector<UploadTableInvalidValue>();
        
        final SwingWorker validateTask = new SwingWorker()
        {
            final JStatusBar statusBar = UIRegistry.getStatusBar();
 
            @Override
            public void interrupt()
            {
                super.interrupt();
            }
                        
            @SuppressWarnings("synthetic-access")
            @Override
            public Object construct()
            {
                int progress = 0;
                initProgressBar(0, uploadTables.size());
                for (UploadTable tbl : uploadTables)
                {
                    setCurrentOpProgress(++progress);
                    issues.addAll(tbl.validateValues(uploadData));
                }
                dataValidated = issues.size() == 0;
                return new Boolean(dataValidated);
            }
            
            @Override
            public void finished()
            {
                validationIssues = issues;
                if (dataValidated && resolver.isResolved())
                {
                    statusBar.setText(getResourceString("WB_DATASET_VALIDATED")); 
                    setCurrentOp(Uploader.READY_TO_UPLOAD);
                }
                else
                {
                    setCurrentOp(Uploader.USER_INPUT);
                    statusBar.setText(getResourceString("WB_INVALID_DATASET"));
                }
            }

        };
        validateTask.start();
        if (mainForm == null)
        {
            initUI(Uploader.VALIDATING_DATA);
            UIHelper.centerAndShow(mainForm);
        }
        else
        {
            setCurrentOp(Uploader.VALIDATING_DATA);
        }
    }
        

	/**
	 * @return a set of tables for which no fields are being imported, but which provide foreign keys for tables that do have fields being imported.
	 * 
	 * lots more to do here i think re agents (can occur in so many roles) and recursive tables.
	 * also needs to distinguish between collectionObject -> CollectingEvent (missing) -> Locality 
	 * which is kind of bad and CollectionObject -> CollectingEvent (missing) -> Locality (missing) which is useless but maybe ok.
	 */
	public Set<Table> checkForMissingTables()
	{
		Set<Table> result = new HashSet<Table>();
		for (UploadTable t : uploadTables)
		{
			Set<Vertex<Table>> ins = uploadGraph.into(t.getTable());
			for (Vertex<Table> in : ins)
			{
				if (!uploadTableIsPresent(in.getData()))
				{
					result.add(in.getData());
				}
			}
		}
		return result;
	}
    
	
	/**
	 * @param t
	 * @return true if there is an UploadTable defined for t.
	 */
	protected boolean uploadTableIsPresent(final Table t)
	{
		for (UploadTable it : uploadTables)
		{
			if (it.getTable() == t)
			{
				return true;
			}
		}
		return false;
	}
    
	
    /**
     * @return (eventually) tables that, if added to the dataset, will
     * probably make the dataset stucturally sufficient for upload. 
     */
    protected Vector<Table> getMissingTbls()
    {
        Vector<Table> result = new Vector<Table>();
        //just add dummy value for now
        result.add(null);
        return result;
    }
	/**
	 * @return true if the import mapping and graph are OK.
     * Also saves messages for each problem. 
	 */
	public Vector<UploadMessage> validateStructure() throws UploaderException
	{
        Vector<UploadMessage> errors = new Vector<UploadMessage>();
        try
        {
            if (!uploadGraph.isConnected())
            {
                for (Table tbl : getMissingTbls())
                {
                    String msg = getResourceString("WB_UPLOAD_MISSING_TBL");
                    if (tbl != null)
                    {
                        msg += " (" + tbl.getTableInfo().getTitle() + ")";
                    }
                    errors.add(new InvalidStructure(msg, tbl));
                }
                
            }
        }
        catch (DirectedGraphException ex)
        {
            throw new UploaderException(ex, UploaderException.ABORT_IMPORT);
        }
        for (UploadTable t : uploadTables)
        {
           errors.addAll(t.validateStructure());
        }
        
//        for (UploadMessage msg : errors)
//        {
//            addMsg(msg);
//        }
        
        return errors;
    }
    
        
    /**
     * @throws UploaderException
     * 
     * Sets up for upload.
     */
    public void prepareToUpload() throws UploaderException
    {
        try
        {
            for (UploadTable t : uploadTables)
            {
                t.prepareToUpload();
            }
        }
        catch (ClassNotFoundException cnfEx)
        {
            throw new UploaderException(cnfEx, UploaderException.ABORT_IMPORT);
        }
        catch (NoSuchMethodException nsmeEx)
        {
            throw new UploaderException(nsmeEx, UploaderException.ABORT_IMPORT);
        }
        //But may want option to ONLY upload rows that were skipped...
        skippedRows.clear();
        messages.clear();
        newMessages.clear();
    }
        
    
    /**
     * @param opName
     * 
     * Puts UI into correct state for current upload phase.
     */
    protected synchronized void setCurrentOp(final String opName)
    {
        currentOp = opName;
        if (mainForm == null)
        {
            log.error("UI does not exist.");
            return;
        }
        setupUI(currentOp);
    }
    
    
    /**
     * @param min
     * @param max
     * 
     * Initializes progress bar for upload actions. 
     * If min and max = 0, sets progress bar is indeterminate.
     */
    protected synchronized void initProgressBar(int min, int max)
    {
        if (mainForm == null)
        {
            log.error("UI does not exist.");
            return;
        }
        JProgressBar pb = mainForm.getCurrOpProgress();
        pb.setVisible(true);
        if (min == 0 && max == 0)
        {
            pb.setIndeterminate(true);
        }
        else
        {
            if (pb.isIndeterminate())
            {
                pb.setIndeterminate(false);
            }
            pb.setMinimum(min);
            pb.setMaximum(max);
            pb.setValue(min);
        }
        pb.setString("");
    }
    
    
    /**
     * @param val
     * 
     * Sets progress bar progress.
     */
    protected synchronized void setCurrentOpProgress(int val)
    {
        if (mainForm == null)
        {
            log.error("UI does not exist.");
            return;
        }
        if (!mainForm.getCurrOpProgress().isIndeterminate())
        {
            mainForm.getCurrOpProgress().setValue(val);
        }
    }
    
    protected synchronized void showUploadProgress(int val)
    {
        if (mainForm == null)
        {
            log.error("UI does not exist.");
            return;
        }
        setCurrentOpProgress(val);
        for (UploadMessage newMsg : newMessages)
        {
            mainForm.addMsg(newMsg);
            messages.add(newMsg);
        }
        newMessages.clear();
        //mainForm.updateObjectsCreated();
    }
    
    protected synchronized void updateObjectsCreated()
    {
        mainForm.updateObjectsCreated();
    }
    /**
     * @param initOp
     * 
     * builds upload ui witn initial phase of initOp
     */
    protected void initUI(final String initOp)
    {
        buildMainUI();
        setCurrentOp(initOp);
        //mainForm.pack();
    }
    
    
    
    /**
     * Gets default values for all missing required classes (foreign keys) and local fields. 
     */
    public void getDefaultsForMissingRequirements()
    {        
        final SwingWorker uploadTask = new SwingWorker()
        {
            final JStatusBar statusBar = UIRegistry.getStatusBar();
            boolean success = false;                        
            @SuppressWarnings("synthetic-access")
            @Override
            public Object construct()
            {
                UIRegistry.writeGlassPaneMsg(String.format(getResourceString("WB_UPLOAD_VALIDATING_DATASET"),
                        new Object[] { "" }), WorkbenchTask.GLASSPANE_FONT_SIZE);
                missingRequiredClasses.clear();
                missingRequiredFields.clear();
                Iterator<RelatedClassEntry> rces;
                Iterator<DefaultFieldEntry> dfes;
                for (UploadTable t : uploadTables)
                {
                    try 
                    {
                        rces = t.getRelatedClassDefaults();
                    }
                    catch (ClassNotFoundException ex)
                    {
                        log.error(ex);
                        return null;
                    }
                    while (rces.hasNext())
                    {
                        missingRequiredClasses.add(rces.next());
                    }

                    try
                    {
                        dfes = t.getMissingRequiredFlds();
                    }
                    catch (NoSuchMethodException ex)
                    {
                        log.error(ex);
                        return null;
                    }
                    while (dfes.hasNext())
                    {
                        missingRequiredFields.add(dfes.next());
                    }
                    success = true;
                }
                resolver = new MissingDataResolver(missingRequiredClasses, missingRequiredFields);
                return null;
            }
            
            @Override
            public void finished()
            {
                UIRegistry.clearGlassPaneMsg();
                if (success)
                {
                    statusBar.setText(getResourceString("WB_REQUIRED_RETRIEVED")); 
                    validateData();
                }
                else
                {
                    setCurrentOp(Uploader.FAILURE);
                }
            }

        };

        uploadTask.start();
        initUI(Uploader.CHECKING_REQS);
        mainForm.setAlwaysOnTop(true);
        UIHelper.centerAndShow(mainForm);
    }
    
      
    /**
     * Called when dataset is saved. 
     */
    public void refresh()
    {
        wbSS.getWorkbench().forceLoad();
        validateData();
    }
    
    
    /**
     * Called when dataset is closing.
     */
    public void closing()
    {
        if (mainForm != null)
        {
            closeMainForm();
        }
    }
    
    /**
     * @return count of total number of objects uploaded
     */
    protected Integer getUploadedObjects()
    {
        Integer result = 0;
        for (UploadTable ut : uploadTables)
        {
            result += ut.getUploadedKeys().size();
        }
        return result;
    }
    
    /**
     * Shuts down upload UI.
     */
    protected void closeMainForm()
    {
        mainForm.setVisible(false);
        mainForm.dispose();
        mainForm = null;
        closeUploadedDataViewers();
    }
    
    
    /**
     * Closes views of uploaded data.
     */
    protected void closeUploadedDataViewers()
    {
        if (bogusViewer != null)
        {
            bogusViewer.closeViewers();
            bogusViewer = null;
        }
    }
    
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * Responds to user actions in UI.
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(UploadMainForm.DO_UPLOAD))
        {
            uploadIt();
        }
        else if (e.getActionCommand().equals(UploadMainForm.VIEW_UPLOAD))
        {
            if (currentOp.equals(Uploader.SUCCESS))
            {
                if (bogusStorages == null)
                {
                    retrieveUploadedData();
                }
                else
                {
                    viewSelectedTable();
                }
            }
        }
        else if (e.getActionCommand().equals(UploadMainForm.VIEW_SETTINGS))
        {
            showSettings();
            if (currentOp.equals(Uploader.READY_TO_UPLOAD) && !resolver.isResolved())
            {
                setCurrentOp(Uploader.USER_INPUT);
            }
            else if (currentOp.equals(Uploader.USER_INPUT) && resolver.isResolved())
            {
                setCurrentOp(Uploader.READY_TO_UPLOAD);
            }
        }
        else if (e.getActionCommand().equals(UploadMainForm.CLOSE_UI))
        {
            if (currentUpload != null)
            {
                saveRecordSets();
            }
            closeMainForm();
            wbSS.uploadDone();
        }
        else if (e.getActionCommand().equals(UploadMainForm.UNDO_UPLOAD))
        {
            undoUpload();
            wbSS.uploadDone();
        }
        else if (e.getActionCommand().equals(UploadMainForm.CANCEL_OPERATION))
        {
            //System.out.println(UploadMainForm.CANCEL_OPERATION);
        }
        else if (e.getActionCommand().equals(UploadMainForm.TBL_DBL_CLICK))
        {
            mainForm.getViewUploadBtn().setEnabled(canViewUpload(currentOp));
            if (currentOp.equals(Uploader.SUCCESS))
            {
                if (bogusStorages == null)
                {
                    retrieveUploadedData();
                }
                else
                {
                    viewSelectedTable();
                }
            }
        }
        else if (e.getActionCommand().equals(UploadMainForm.TBL_CLICK))
        {
            mainForm.getViewUploadBtn().setEnabled(canViewUpload(currentOp));
        }
        else if (e.getActionCommand().equals(UploadMainForm.MSG_CLICK))
        {
             goToMsgWBCell();
        }
        else if (e.getActionCommand().equals(UploadMainForm.PRINT_INVALID))
        {
             printInvalidValReport();
        }
        else log.error("Unrecognized action: " + e.getActionCommand());
    }
    
    protected void showSettings()
    {
        boolean readOnly = !currentOp.equals(Uploader.READY_TO_UPLOAD) && !currentOp.equals(Uploader.USER_INPUT);
        UploadSettingsPanel usp = new UploadSettingsPanel(uploadTables);
        usp.buildUI(resolver, readOnly);
        CustomDialog cwin;
        if (!readOnly)
        {
            cwin = new CustomDialog(mainForm, getResourceString("WB_UPLOAD_SETTINGS"), true, usp); 
        }
        else
        {
            cwin = new CustomDialog(mainForm, getResourceString("WB_UPLOAD_SETTINGS"), true, CustomDialog.OK_BTN, usp, CustomDialog.OK_BTN); 
        }
            
        cwin.setModal(true);
        UIHelper.centerAndShow(cwin);
        if (!cwin.isCancelled())
        {
            usp.getMatchPanel().apply();
        }
        cwin.dispose();
        for (UploadTable tbl : uploadTables)
        {
            System.out.println(tbl);
            UploadMatchSetting matchSets = tbl.getMatchSetting();
            System.out.println("    matchEmptyValues: " + matchSets.matchEmptyValues);
            System.out.println("    remember: " + matchSets.isRemember());
            System.out.println("    blanks: " + matchSets.isMatchEmptyValues());
            System.out.println("    mode: " + UploadMatchSetting.getModeText(matchSets.getMode()));
        }
    }

    /**
     * @author timbo
     *
     * @code_status Alpha
     * 
     * Datasource for printing validation issues for current upload.
     *
     */
    class InvalidValueJRDataSource implements JRDataSource
    {
        protected int rowIndex;
        protected final Vector<UploadTableInvalidValue> rows;
        
        public InvalidValueJRDataSource(final Vector<UploadTableInvalidValue> rows)
        {
            this.rows = rows;
            rowIndex = -1;
        }
        
        /*
         * (non-Javadoc)
         * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
         */
        public Object getFieldValue(final JRField field) throws JRException
        {
            if (field.getName().equals("row"))
            {
                return String.valueOf(rows.get(rowIndex).getRow());
            }
            if (field.getName().equals("col"))
            {
                return String.valueOf(rows.get(rowIndex).getUploadFld().getWbFldName());
            }
            if (field.getName().equals("description"))
            {
                return rows.get(rowIndex).getDescription();
            }
            if (field.getName().equals("datasetName"))
            {
                return wbSS.getWorkbench().getName();
            }
            if (field.getName().equals("cellData"))
            {
                return uploadData.get(rows.get(rowIndex).getRow(), rows.get(rowIndex).getUploadFld().getIndex());                
            }
            log.error("Unrecognized field Name: " + field.getName());
            return null;
        }
        
        /*
         * (non-Javadoc)
         * @see net.sf.jasperreports.engine.JRDataSource#next()
         */
        public boolean next() throws JRException
        {
            if (rowIndex >= rows.size() - 1)
            {
                return false;
            }
            rowIndex++;
            return true;
        }
    }

    /**
     * Launches ReportTask to display report of validation issues.
     */
    protected void printInvalidValReport()
    {
        if (validationIssues == null || validationIssues.size() == 0)
        {
            //this should never be called but just in case.
            log.error("no validationIssues");
            return;
        }
        InvalidValueJRDataSource src = new InvalidValueJRDataSource(validationIssues);
        final CommandAction cmd = new CommandAction(ReportsBaseTask.REPORTS, ReportsBaseTask.PRINT_REPORT, src);
        cmd.setProperty("title", "Validation Issues");
        cmd.setProperty("file", "upload_problem_report.jrxml");
        CommandDispatcher.dispatch(cmd);
    }
    
    /**
     * Moves to dataset cell corresponding to currently selected validation issue and starts editor.
     */
    protected void goToMsgWBCell()
    {
        if (mainForm == null)
        {
            throw new RuntimeException("Upload form does not exist.");
        }
        if (wbSS != null)
        {
            UploadMessage msg = (UploadMessage)mainForm.getMsgList().getSelectedValue();
            if (msg.getRow() != -1)
            {
                if (msg.getCol() == -1)
                {
                    wbSS.getSpreadSheet().scrollToRow(msg.getRow());
                    wbSS.getSpreadSheet().getSelectionModel().clearSelection();
                    wbSS.getSpreadSheet().getSelectionModel().setSelectionInterval(msg.getRow()-1, msg.getRow()-1);
                }
                else
                {
                    Rectangle rect = wbSS.getSpreadSheet().getCellRect(msg.getRow(), msg.getCol(), false);
                    wbSS.getSpreadSheet().scrollRectToVisible(rect);
                    if (msg instanceof UploadTableInvalidValue && msg.getCol() != -1)
                    {
                        wbSS.getSpreadSheet().editCellAt(msg.getRow(), msg.getCol(), null);
                        wbSS.getSpreadSheet().grabFocus();
                    }
                }
            }
        }
    }
    
    
    public void windowStateChanged(WindowEvent e)
    {
        if (e.getNewState() == WindowEvent.WINDOW_CLOSING)
        {
            System.out.println("Closing");
        }
        else if (e.getNewState() == WindowEvent.WINDOW_ACTIVATED)
        {
            System.out.println("Activated");
        }
    }
    
    
    /**
     * Builds form for upload UI.
     */
    protected void buildMainUI()
    {
        mainForm = new UploadMainForm();
         
        SortedSet<UploadInfoRenderable> uts = new TreeSet<UploadInfoRenderable>();
        for (UploadTable ut : uploadTables)
        {
            UploadInfoRenderable render = new UploadInfoRenderable(ut);
            if (uts.contains(render))
            {
                for (UploadInfoRenderable r : uts)
                {
                    if (r.equals(render))
                    {
                        r.addTable(ut);
                        break;
                    }
                }
            }
            else
            {
                uts.add(new UploadInfoRenderable(ut));
            }
        }
        
        DefaultListModel tbls = new DefaultListModel();
        JList tableList = mainForm.getUploadTbls();
        TableNameRenderer nameRender = new TableNameRenderer(IconManager.IconSize.Std24);
        nameRender.setUseIcon("PlaceHolder");
        tableList.setCellRenderer(nameRender);
        for (UploadInfoRenderable ut : uts)
        {
            tbls.addElement(ut);
        }
        tableList.setModel(tbls);
        mainForm.setActionListener(this);
        mainForm.addWindowStateListener(this);
    }
    
    /**
     * @param op
     * 
     * Sets up mainForm for upload phase for op.
     */
    protected synchronized void setupUI(final String op)
    {
        if (mainForm == null)
        {
            log.error("UI does not exist.");
            return;
        }

        if (op.equals(Uploader.SUCCESS))
        {
            if (mainForm.getUploadTbls().getSelectedIndex() == -1)
            {
                //assuming list is not empty
                mainForm.getUploadTbls().setSelectedIndex(0);
            }
        }

        mainForm.getCancelBtn().setEnabled(canCancel(op));
        mainForm.getCancelBtn().setVisible(mainForm.getCancelBtn().isEnabled());
        
        mainForm.getDoUploadBtn().setEnabled(canUpload(op));
        //mainForm.getDoUploadBtn().setVisible(mainForm.getDoUploadBtn().isEnabled());
        
        mainForm.getViewSettingsBtn().setEnabled(canViewSettings(op));
        //mainForm.getViewSettingsBtn().setVisible(mainForm.getViewSettingsBtn().isEnabled());
        
        mainForm.getViewUploadBtn().setEnabled(canViewUpload(op));
        mainForm.getViewUploadBtn().setVisible(mainForm.getViewUploadBtn().isEnabled());
        
        mainForm.getUndoBtn().setEnabled(canUndo(op));
        mainForm.getUndoBtn().setVisible(mainForm.getUndoBtn().isEnabled());
        
        mainForm.getCloseBtn().setEnabled(canClose(op));
        
        mainForm.clearMsgs(UploadTableInvalidValue.class);
        if (validationIssues != null)
        {
            for (UploadTableInvalidValue invalid : validationIssues)
            {
                mainForm.addMsg(invalid);
            }
        }
        mainForm.getPrintBtn().setEnabled(validationIssues != null && validationIssues.size() > 0);
        
        mainForm.getCurrOpProgress().setVisible(mainForm.getCancelBtn().isVisible());
        
        
        String statText = getResourceString(op);
        if (op.equals(Uploader.SUCCESS))
        {
            statText += ". " + getUploadedObjects().toString() + " " + getResourceString("WB_UPLOAD_OBJECT_COUNT") + ".";
        }
        mainForm.getCurrOpLbl().setText(statText);
    }
    
    
    /**
     * Opens view of uploaded data for selected table.
     * Initializes viewer object if necessary. 
     */
    protected void viewSelectedTable()
    {
        if (currentOp.equals(Uploader.SUCCESS))
        {
            if (mainForm.getUploadTbls().getSelectedValue() != null)
            {
                if (bogusStorages != null)
                {
                    if (bogusViewer == null)
                    {
                        bogusViewer = db.new BogusViewer(bogusStorages);
                    }
                    if (bogusViewer != null)
                    {
                        bogusViewer.viewBogusTbl(((UploadInfoRenderable)mainForm.getUploadTbls().getSelectedValue()).getTableName(), true);
                    }
                }
            }
        }
    }
    
    
    /**
     * @param op
     * @return true if canUndo in phase op.
     */
    protected boolean canUndo(final String op)
    {
        return op.equals(Uploader.SUCCESS);
    }
    
    /**
     * @param op
     * @return true if canCancel in phase op.
     */
    protected boolean canCancel(final String op)
    {
        return op.equals(Uploader.UPLOADING)
          || op.equals(Uploader.CHECKING_REQS)
          || op.equals(Uploader.VALIDATING_DATA);
    }
    
    /**
     * @param op
     * @return true if canUpload in phase op.
     */
    protected boolean canUpload(final String op)
    {
        return op.equals(Uploader.READY_TO_UPLOAD);
    }
    
    /**
     * @param op
     * @return true if canClose in phase op.
     */
    protected boolean canClose(final String op)
    {
        return op.equals(Uploader.READY_TO_UPLOAD)
         || op.equals(Uploader.USER_INPUT)
         || op.equals(Uploader.SUCCESS)
         || op.equals(Uploader.FAILURE);
    }

    /**
     * @param op
     * @return true if canViewSettings in phase op.
     */
    protected boolean canViewSettings(final String op)
    {
        return op.equals(Uploader.READY_TO_UPLOAD)
        || op.equals(Uploader.USER_INPUT)
        || op.equals(Uploader.SUCCESS)
        || op.equals(Uploader.FAILURE);
    }

    /**
     * @param op
     * @return true if canViewUpload in phase op.
     */
    protected boolean canViewUpload(final String op)
    {
        return op.equals(Uploader.SUCCESS) && mainForm.getUploadTbls().getSelectedIndex() != -1;
    }
    
    
    /**
     * Uploads dataset.
     */
    public void uploadIt() 
    {
        buildIdentifier();
        currentUpload = this;
        
        final SwingWorker uploadTask = new SwingWorker()
        {
            final JStatusBar statusBar = UIRegistry.getStatusBar();
            boolean success = false;
            boolean cancelled = false;
            boolean holdIt = false;
            
            @Override
            public void interrupt()
            {
                //this will hold the upload process until the confirm dlg is closed
                holdIt = true;
                if (UIRegistry.displayConfirm(getResourceString("WB_CANCEL_UPLOAD_TITLE"), 
                        getResourceString("WB_CANCEL_UPLOAD_MSG"), 
                        getResourceString("OK"),
                        getResourceString("Cancel"), 
                        JOptionPane.QUESTION_MESSAGE))
                {
                    super.interrupt();
                    cancelled = true;
                }
                holdIt = false;
            }
                        
            @SuppressWarnings("synthetic-access")
            @Override
            public Object construct()
            {
                UIRegistry.writeGlassPaneMsg(String.format(getResourceString("WB_UPLOADING_DATASET"),
                        new Object[] { "" }), WorkbenchTask.GLASSPANE_FONT_SIZE);
                initProgressBar(0, uploadData.getRows());
                try
                {
                    for (rowUploading = 0; rowUploading < uploadData.getRows();)
                    {
                        log.debug("uploading row " + String.valueOf(rowUploading));
                        setCurrentOpProgress(rowUploading+1);
                        if (!holdIt)
                        {
                            for (UploadTable t : uploadTables)
                            {
                                if (cancelled)
                                {
                                    break;
                                }
                                try
                                {
                                    uploadRow(t, rowUploading);
                                }
                                catch (UploaderException ex)
                                {
                                    if (ex.getStatus() == UploaderException.ABORT_ROW)
                                    {
                                        log.debug(ex.getMessage());
                                        abortRow(ex, rowUploading);
                                        break;
                                    }
                                    throw ex;
                                }
                                updateObjectsCreated();
                            }
                            rowUploading++;
                            showUploadProgress(rowUploading);
                        }
                    }
                }
                catch (Exception ex)
                {
                    return ex;
                }
                success = !cancelled;
                return Boolean.valueOf(success);
            }
            
            @Override
            public void finished()
            {
                if (success)
                {
                    statusBar.setText(getResourceString(SUCCESS)); 
                    setCurrentOp(Uploader.SUCCESS);
                }
                else 
                {
                    mainForm.clearObjectsCreated();
                    for (int ut = uploadTables.size()-1; ut >= 0; ut--)
                    {
                        uploadTables.get(ut).undoUpload();
                    }
                    if (cancelled)
                    {
                        statusBar.setText(getResourceString("WB_UPLOAD_CANCELLED"));
                        setCurrentOp(Uploader.READY_TO_UPLOAD);
                    }
                    else
                    {
                        statusBar.setText(getResourceString(Uploader.FAILURE)); 
                        setCurrentOp(Uploader.FAILURE);
                    }
                }
                UIRegistry.clearGlassPaneMsg();
            }

        };

        JButton cancelBtn = mainForm.getCancelBtn();
        cancelBtn.addActionListener(new ActionListener()
        {
            @SuppressWarnings("synthetic-access")
            public void actionPerformed(ActionEvent ae)
            {
                log.debug("Stopping the dataset upload thread");
                uploadTask.interrupt();
            }
        });

        uploadTask.start();
        if (mainForm == null)
        {
            initUI(Uploader.UPLOADING);
            UIHelper.centerAndShow(mainForm);
        }
        else
        {
            setCurrentOp(Uploader.UPLOADING);
        }
    }
    
    protected void abortRow(UploaderException cause, int row)
    {
        log.debug("NOT undoing writes which have already occurred while processing aborted row");
        SkippedRow sr = new SkippedRow(cause, row+1);
        skippedRows.add(sr);
        newMessages.add(sr);
    }
    
    public void addMsg(final UploadMessage msg)
    {
        newMessages.add(msg);
    }
    
	/**
     * Undoes the most recent upload.
     * 
     * This is currently intended to be used as a debugging aid.
     */
	public void undoUpload()
    {
        final SwingWorker undoTask = new SwingWorker()
        {
            final JStatusBar statusBar = UIRegistry.getStatusBar();
            boolean success = false;
                                    
            @SuppressWarnings("synthetic-access")
            @Override
            public Object construct()
            {
                UIRegistry.writeGlassPaneMsg(String.format(getResourceString("WB_UPLOAD_UNDO"),
                        new Object[] { "" }), WorkbenchTask.GLASSPANE_FONT_SIZE);
                initProgressBar(0, 0);
                for (int ut = uploadTables.size()-1; ut >= 0; ut--)
                {
                    uploadTables.get(ut).undoUpload();
                }
                success = true;
                return Boolean.valueOf(success);
            }
            
            @Override
            public void finished()
            {
                UIRegistry.clearGlassPaneMsg();
                if (success)
                {
                    statusBar.setText(getResourceString("WB_UPLOAD_ROLLEDBACK"));
                    setCurrentOp(Uploader.READY_TO_UPLOAD);
                }
                else 
                {
                        statusBar.setText(getResourceString("WB_UPLOAD_ROLLBACK_FAILURE")); 
                        setCurrentOp(Uploader.FAILURE);
                }
                closeMainForm();
            }

        };
        undoTask.start();
        setCurrentOp(Uploader.UNDOING_UPLOAD);
    }
    
    
    /**
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * 
     * prints listing of uploaded data to System.out.
     */
    public void printUpload() throws IllegalAccessException, InvocationTargetException
    {
        for (UploadTable ut : uploadTables)
        {
            System.out.println(ut.getWriteTable().getName());
            Vector<Vector<String>> vals = ut.printUpload();
            for (Vector<String> row : vals)
            {
                for (String val : row)
                {
                    System.out.print(val + ", ");
                }
                System.out.println();
            }
        }
    }
    

    /**
     * Builds viewer for uploaded data.
     */
    public void retrieveUploadedData() 
    {
        bogusStorages = new HashMap<String, Vector<Vector<String>>>();

        final SwingWorker retrieverTask = new SwingWorker()
        {
            final JStatusBar statusBar = UIRegistry.getStatusBar();
            boolean cancelled = false;
            boolean holdIt = false;
            
            @Override
            public void interrupt()
            {
                //this will hold the display process until the confirm dlg is closed
                holdIt = true;
                if (UIRegistry.displayConfirm(getResourceString("WB_CANCEL_UPLOAD_RETRIEVE_TITLE"), 
                        getResourceString("WB_CANCEL_UPLOAD_RETRIEVE_MSG"), 
                        getResourceString("OK"),
                        getResourceString("Cancel"), 
                        JOptionPane.QUESTION_MESSAGE))
                {
                    super.interrupt();
                    cancelled = true;
                }
                holdIt = false;
            }
                        
            @SuppressWarnings("synthetic-access")
            @Override
            public Object construct()
            {
                initProgressBar(0, uploadTables.size());
                for (int progress = 0; progress < uploadTables.size();)
                {
                    if (cancelled)
                    {
                        break;
                    }
                    if (!holdIt)
                    {
                        UploadTable ut = uploadTables.get(progress);
                        setCurrentOpProgress(progress + 1);
                        try
                        {
                            Vector<Vector<String>> vals = ut.printUpload();
                            if (vals.size() > 0)
                            {
                                String title = ut.getWriteTable().getName();
                                if (!bogusStorages.containsKey(title))
                                {
                                    bogusStorages.put(title, vals);
                                }
                                else
                                {
                                    // delete header
                                    vals.remove(0);
                                    bogusStorages.get(title).addAll(vals);
                                }
                            }
                        }
                        catch (InvocationTargetException ex)
                        {
                            log.error(ex);
                        }
                        catch (IllegalAccessException ex)
                        {
                            log.error(ex);
                        }
                        progress++;
                    }
                }
               return null;
            }
            
            @Override
            public void finished()
            {
                setCurrentOp(Uploader.SUCCESS);
                if (!cancelled)
                {
                    viewSelectedTable();
                    statusBar.setText(getResourceString("WB_UPLOAD_DATA_FETCHED")); 
                    //undoUpload(); 
                }
                else
                {
                    bogusStorages = null;
                    statusBar.setText(getResourceString("RetrievalWB_UPLOAD_FETCH_CANCELLED cancelled"));
                    //undoUpload();
                }
                UIRegistry.clearGlassPaneMsg();
            }

        };
        if (mainForm == null)
        {
            initUI(Uploader.RETRIEVING_UPLOADED_DATA);
            UIHelper.centerAndShow(mainForm);
        }
        else
        {
            setCurrentOp(Uploader.RETRIEVING_UPLOADED_DATA);
        }
        retrieverTask.start();
    }

    
	/**
     * @param t
     * @param row
     * @throws UploaderException
     * 
     * imports data in row belonging to t's Table.
     */
	protected void uploadRow(final UploadTable t, int row) throws UploaderException
	{
		for (UploadField field : uploadFields)
		{
            if (field.getField().getTable().equals(t.getTable()))
			{
				if (field.getIndex() != -1)
				{
                    uploadCol(field, uploadData.get(row, field.getIndex()));
				}
			}
		}
		try
		{
			writeRow(t);
		} catch (UploaderException ex)
		{
			log.debug(ex.getMessage() + " (" + t.getTable().getName() + ", row " + Integer.toString(row) + ")");
            throw ex;
		}
	}
    

	/**
	 * @param f
	 * @param val
	 * 
     * imports val to f.
	 */
	protected void uploadCol(final UploadField f, final String val)
	{
		if (f != null)
		{
			f.setValue(val);
		}
	}

	/**
	 * @param t
	 * @throws UploaderException
     * 
     * writes data (if necessary) for t.
	 */
	protected void writeRow(final UploadTable t) throws UploaderException
    {
        t.writeRow();
    }
            
	protected void saveRecordSets()
    {
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        try
        {
            for (UploadTable ut : uploadTables)
            {
                RecordSet rs = ut.getRecordSet();
                if (rs.getItems().size() > 0)
                {
                    session.beginTransaction();
                    try
                    {
                        session.save(rs);
                        session.commit();
                    }
                    catch (Exception ex)
                    {
                        session.rollback();
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            session.close();
        }
    }
}


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
package edu.ku.brc.specify.tasks;

import static edu.ku.brc.helpers.XMLHelper.getAttr;
import static edu.ku.brc.ui.UICacheManager.getResourceString;

import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.Query;
import org.hibernate.Session;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.NavBox;
import edu.ku.brc.af.core.NavBoxIFace;
import edu.ku.brc.af.core.NavBoxItemIFace;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.Taskable;
import edu.ku.brc.af.plugins.MenuItemDesc;
import edu.ku.brc.af.plugins.ToolBarItemDesc;
import edu.ku.brc.af.tasks.BaseTask;
import edu.ku.brc.af.tasks.subpane.FormPane;
import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.specify.config.SpecifyAppContextMgr;
import edu.ku.brc.specify.datamodel.CollectionObjDef;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.RolloverCommand;
import edu.ku.brc.ui.ToolBarDropDownBtn;
import edu.ku.brc.ui.Trash;
import edu.ku.brc.ui.dnd.DataActionEvent;
import edu.ku.brc.ui.dnd.GhostActionable;
import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.forms.FormHelper;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.persist.View;

/**
 * This task controls the data entry forms
 
 * @code_status Alpha
 **
 * @author rods
 *
 */
public class DataEntryTask extends BaseTask
{
    private static final Logger log = Logger.getLogger(DataEntryTask.class);

    public static final String     DATA_ENTRY   = "Data_Entry";
    public static final DataFlavor DATAENTRY_FLAVOR = new DataFlavor(DataEntryTask.class, "Data_Entry");
    
    protected static Hashtable<String, ImageIcon> iconForFormClass = new Hashtable<String, ImageIcon>();

    // Data Members
    protected Vector<NavBoxIFace> extendedNavBoxes = new Vector<NavBoxIFace>();
    protected NavBox              viewsNavBox      = null;
    protected SubPaneIFace        starterPane      = null;

    /**
     * Default Constructor
     *
     */
    public DataEntryTask()
    {
        super(DATA_ENTRY, getResourceString(DATA_ENTRY));
        
        CommandDispatcher.register(DATA_ENTRY, this);
        CommandDispatcher.register("App", this);
        
        // Do this here instead of in initialize because the static method will need to access the icon mapping first
        viewsNavBox = new NavBox(getResourceString("CreateAndUpdate"));
        initializeViewsNavBox();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#initialize()
     */
    public void initialize()
    {
        if (!isInitialized)
        {
            super.initialize(); // sets isInitialized to false

            // Temporary
            NavBox navBox = new NavBox(getResourceString("Actions"));
            navBox.add(NavBox.createBtn(getResourceString("Series_Processing"), name, IconManager.IconSize.Std16));
            navBoxes.addElement(navBox);
           
            navBoxes.addElement(viewsNavBox);
        }
    }
    
    /**
     * Common method for creating a consistent name for lookups in the Icon cache for forms.
     * @param viewSetName the viewSetName
     * @param viewName the view name
     * @return the full appended name
     */
    protected static String createFullName(final String viewSetName, final String viewName)
    {
        return viewSetName + "_" + viewName;
    }

    /**
     * Opens a pane with a view to data. NOTE:If the data object is null and isNewForm = true then it will create a new dataObj.
     * @param task the owning Task
     * @param viewSetName the ViewSet Name
     * @param viewName the view's name 
     * @param mode the creation mode (View, Edit)
     * @param data the data to fill in 
     * @param isNewForm indicates that it is a "new" form for entering in new data
     */
    public void openView(final Taskable         task, 
                         final String           viewSetName, 
                         final String           viewName, 
                         final String           mode, 
                         final FormDataObjIFace data,
                         final boolean          isNewForm)
    {
        View view = AppContextMgr.getInstance().getView(viewSetName, viewName);
        
        FormDataObjIFace  dataObj = data;
        if (dataObj == null)
        {
            if (isNewForm)
            {
                try
                {
                    dataObj = FormHelper.createAndNewDataObj(Class.forName(view.getClassName()));
                    dataObj.initialize();
                    
                } catch (Exception ex)
                {
                    log.error(ex);
                    throw new RuntimeException(ex);
                }
            }
            
        } else
        {
            HibernateUtil.getSessionFactory().evict(data.getClass());    
        }
        
        FormPane formPane = new FormPane(HibernateUtil.getNewSession(), 
                                         view.getName(), task, viewSetName, viewName, mode, dataObj, 
                                         isNewForm ? (MultiView.IS_NEW_OBJECT |  MultiView.RESULTSET_CONTROLLER): 0);
        
        formPane.setIcon(iconForFormClass.get(createFullName(view.getViewSetName(), view.getName())));
        
        
        if (starterPane == null)
        {
            SubPaneMgr.getInstance().addPane(formPane);
            
        } else
        {
            SubPaneMgr.getInstance().replacePane(starterPane, formPane);
            starterPane = null;
        }
    }

    /**
     * Opens a View and fills it with a single data object
     * @param mode the mode of how it is to be opened (View, Edit) 
     * @param idStr a string that contains the Integer Id (Primary Key) of the object to be shown
     */
    public static void openView(final Taskable task, final View view, final String mode, final String idStr)
    {
        int tableId = DBTableIdMgr.lookupIdByClassName(view.getClassName());

        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Query query = DBTableIdMgr.getQueryForTable(session, tableId, Integer.parseInt(idStr));
        try
        {
            List data = query.list();
            if (data != null && data.size() > 0)
            {
                FormPane formPane = new FormPane(session, 
                                                 view.getName(), 
                                                 task, 
                                                 view.getViewSetName(), 
                                                 view.getName(), 
                                                 mode, 
                                                 data.get(0), 
                                                 MultiView.VIEW_SWITCHER);
                formPane.setIcon(iconForFormClass.get(createFullName(view.getViewSetName(), view.getName())));

            } else
            {
                // No Data Error
            }

        } catch (Exception ex)
        {
            log.error(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Create a form for a recordset.
     * @param task the task it belongs to
     * @param name the name
     * @param recordSet the record to create a form for
     * @return the FormPane
     */
    protected static FormPane createFormFor(final Taskable task, final String name, final RecordSet recordSet)
    {
        DBTableIdMgr.getInClause(recordSet);

        String defaultFormName = DBTableIdMgr.lookupDefaultFormNameById(recordSet.getDbTableId());
        
        DBTableIdMgr.TableInfo tableInfo = DBTableIdMgr.lookupInfoById(recordSet.getDbTableId());
        HibernateUtil.getSessionFactory().evict(tableInfo.getClassObj());
        
        Session session = HibernateUtil.getNewSession();
        Query query = DBTableIdMgr.getQueryForTable(session, recordSet);
        
        // "null" ViewSet name means it should use the default
        
        SpecifyAppContextMgr appContextMgr = (SpecifyAppContextMgr)AppContextMgr.getInstance();
        
        View view = appContextMgr.getView(defaultFormName, CollectionObjDef.getCurrentCollectionObjDef());
        
        FormPane formPane = new FormPane(session, name, task, view, null, query.list(), MultiView.VIEW_SWITCHER | MultiView.RESULTSET_CONTROLLER);
        formPane.setIcon(iconForFormClass.get(createFullName(view.getViewSetName(), view.getName())));
        
        return formPane;
    }
    
    /**
     * Reads the XML Definition of what Forms to load
     */
    protected void initializeViewsNavBox()
    {
        if (viewsNavBox.getCount() == 0)
        {
            try
            {
    
                Element esDOM = AppContextMgr.getInstance().getResourceAsDOM("DataEntryTaskInit"); // Describes the definitions of the full text search
    
                List tables = esDOM.selectNodes("/views/view");
                for ( Iterator iter = tables.iterator(); iter.hasNext(); )
                {
                    Element element = (Element)iter.next();
                    String nameStr  = getAttr(element, "name", "N/A");
                    String iconname = getAttr(element, "iconname", null);
                    
                    String viewset  = getAttr(element, "viewset", null);
                    String view     = getAttr(element, "view", null);
                    
                    String toolTip  = getAttr(element, "tooltip", null);
                    
                    ImageIcon iconImage = IconManager.getIcon(iconname, IconManager.IconSize.Std16);
                    iconForFormClass.put(createFullName(viewset, view), iconImage);
                    
                    ShowViewAction sva = new ShowViewAction(this, viewset, view);
                    
                    NavBoxItemIFace nbi = NavBox.createBtnWithTT(nameStr, iconname, toolTip, IconManager.IconSize.Std16, sva);
                    if (nbi instanceof RolloverCommand)
                    {
                        RolloverCommand roc = (RolloverCommand)nbi;
                        // When Being Dragged
                        roc.addDragDataFlavor(Trash.TRASH_FLAVOR);
                        roc.addDragDataFlavor(DATAENTRY_FLAVOR);
                
                        // When something is dropped on it
                        roc.addDropDataFlavor(RecordSetTask.RECORDSET_FLAVOR);
                    }

                    viewsNavBox.add(nbi);
                }
    
            } catch (Exception ex)
            {
                log.error(ex);
            }
        }
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#getNavBoxes()
     */
    public java.util.List<NavBoxIFace> getNavBoxes()
    {
        initialize();
        
        initializeViewsNavBox();

        extendedNavBoxes.clear();
        extendedNavBoxes.addAll(navBoxes);

        RecordSetTask rsTask = (RecordSetTask)ContextMgr.getTaskByClass(RecordSetTask.class);

        extendedNavBoxes.addAll(rsTask.getNavBoxes());

        return extendedNavBoxes;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    public SubPaneIFace getStarterPane()
    {
        starterPane = new DroppableFormRecordSetAccepter(title, this, "This is the Data Entry Pane");
        return starterPane;
    }

    //-------------------------------------------------------
    // Plugin Interface
    //-------------------------------------------------------

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getToolBarItems()
     */
    public List<ToolBarItemDesc> getToolBarItems()
    {
        Vector<ToolBarItemDesc> list = new Vector<ToolBarItemDesc>();

        String label = getResourceString(DATA_ENTRY);
        String iconName = name;
        String hint = getResourceString("dataentry_hint");
        ToolBarDropDownBtn btn = createToolbarButton(label,iconName,hint);

        list.add(new ToolBarItemDesc(btn));

        return list;
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getMenuItems()
     */
    public List<MenuItemDesc> getMenuItems()
    {
        Vector<MenuItemDesc> list = new Vector<MenuItemDesc>();
        return list;

    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getTaskClass()
     */
    public Class<? extends BaseTask> getTaskClass()
    {
        return this.getClass();
    }
    
    //-------------------------------------------------------
    // CommandListener Interface
    //-------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#doCommand(edu.ku.brc.ui.CommandAction)
     */
    public void doCommand(CommandAction cmdAction)
    {
        if (cmdAction.getAction().equals("Edit"))
        {
            if (cmdAction.getData() instanceof RecordSet)
            {
                addSubPaneToMgr(createFormFor(this, name, (RecordSet)cmdAction.getData()));
                
            } else if (cmdAction.getData() instanceof Object[])
            {
                Object[] dataList = (Object[])cmdAction.getData();
                if (dataList.length != 3)
                {
                    View   view = (View)dataList[0];
                    String mode = (String)dataList[1];
                    String idStr = (String)dataList[2];
                    openView(this, view, mode, idStr);
                    
                } else
                {
                    log.error("The Edit Command was sent with an object Array that was not 3 components!");
                }
            } else
            {
                log.error("The Edit Command was sent that didn't have data that was a RecordSet or an Object Array");
            }
            
        } else if (cmdAction.getAction().equals("ShowView"))
        {
            if (cmdAction.getData() instanceof Object[])
            {
                Object[] dataList = (Object[])cmdAction.getData();
                View   view = (View)dataList[0];
                String mode = (String)dataList[1];
                String idStr = (String)dataList[2];
                openView(this, view, mode, idStr);
            }
            
        } else if (cmdAction.getType().equals("App") && cmdAction.getAction().equals("Restart"))
        {
            viewsNavBox.clear();
            //initializeViewsNavBox();
        }
    }
    

    //--------------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------------

    class ShowViewAction implements ActionListener
    {
        private Taskable  task;
        private String    viewSetName;
        private String    viewName;

        public ShowViewAction(final Taskable task, 
                              final String viewSetName, 
                              final String viewName)
        {
            this.task        = task;
            this.viewSetName = viewSetName;
            this.viewName    = viewName;
        }

        public void actionPerformed(ActionEvent e)
        {
            if (e instanceof DataActionEvent)
            {
                DataActionEvent dae = (DataActionEvent)e;
                Object daeData = dae.getData();
                if (daeData != null && daeData instanceof RecordSet)
                {
                    SubPaneMgr.getInstance().addPane(DataEntryTask.createFormFor(task, "ZZZZ", (RecordSet)daeData));
                    return;
                }
            }
            ((DataEntryTask)task).openView(task, viewSetName, viewName, "edit", null, true);

        }
    }
    
    // Class for accepting RecordSet Drops
    class DroppableFormRecordSetAccepter extends FormPane
    {
       
        public DroppableFormRecordSetAccepter(final String   name, 
                                              final Taskable task,
                                              final String   desc)
        {
            super(null, name, task, desc);
            
            dropFlavors.add(RecordSetTask.RECORDSET_FLAVOR);
            this.createMouseInputAdapter();
            this.icon = IconManager.getIcon(DATA_ENTRY, IconManager.IconSize.Std16);
        }

        public void doAction(GhostActionable src)
        {
            Object srcData = src.getData();
            if (srcData instanceof RecordSet)
            {
                addSubPaneToMgr(createFormFor(task, "XXXX", (RecordSet)srcData));
            }
        }
        
        public void doCommand(CommandAction cmdAction)
        {
            // nothing going on for now (should probably be removed)
        }
    }
    
}

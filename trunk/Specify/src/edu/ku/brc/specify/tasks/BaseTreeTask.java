/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.tasks;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;

import org.apache.log4j.Logger;

import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.MenuItemDesc;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.ToolBarItemDesc;
import edu.ku.brc.af.tasks.BaseTask;
import edu.ku.brc.af.tasks.subpane.FormPane;
import edu.ku.brc.af.tasks.subpane.SimpleDescPane;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.TreeDefItemIface;
import edu.ku.brc.specify.datamodel.Treeable;
import edu.ku.brc.specify.treeutils.TreeDataService;
import edu.ku.brc.specify.treeutils.TreeDataServiceFactory;
import edu.ku.brc.specify.ui.treetables.TreeDefinitionEditor;
import edu.ku.brc.specify.ui.treetables.TreeTableViewer;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.ui.RolloverCommand;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.forms.FormViewObj;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.persist.AltView.CreationMode;
import edu.ku.brc.ui.forms.validation.ValComboBox;

/**
 * A base task that provides functionality in common to all tasks
 * that provide UI for tree-structured data.
 *
 * @code_status Beta
 * @author jstewart
 */
public abstract class BaseTreeTask <T extends Treeable<T,D,I>,
							        D extends TreeDefIface<T,D,I>,
							        I extends TreeDefItemIface<T,D,I>>
							        extends BaseTask
{
    protected static final Logger log = Logger.getLogger(BaseTreeTask.class);
            
    /** The toolbar items provided by this task. */
    protected List<ToolBarItemDesc> toolBarItems;
    
    /** The menu items provided by this task. */
    protected List<MenuItemDesc> menuItems;
    
    /** The class of {@link TreeDefIface} handled by this task. */
    protected Class<D> treeDefClass;
    
    protected D currentDef;
    
    protected boolean currentDefInUse;
    protected SubPaneIFace visibleSubPane;
    
    protected JMenuItem menuItem;
    
    protected String menuItemText;
    protected String menuItemMnemonic;
    protected String starterPaneText;
    protected String commandTypeString;
    
    public static final String OPEN_TREE        = "OpenTree";
    public static final String EDIT_TREE_DEF    = "EditTreeDef";
    public static final String SWITCH_VIEW_TYPE = "SwitchViewType";
    
    protected static DataFlavor TREE_DEF_FLAVOR = new DataFlavor(TreeDefIface.class,TreeDefIface.class.getName());

    protected RolloverCommand openTreeCmdBtn;

    protected RolloverCommand editDefCmdBtn;
    
	/**
     * Constructor.
     * 
	 * @param name the name of the task
	 * @param title the visible name of the task
	 */
	protected BaseTreeTask(final String name, final String title)
	{
		super(name,title);
        toolBarItems = new Vector<ToolBarItemDesc>();
        menuItems = new Vector<MenuItemDesc>();
        
        CommandDispatcher.register(DataEntryTask.DATA_ENTRY, this);
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.af.tasks.BaseTask#initialize()
	 */
	@Override
	public synchronized void initialize()
	{
		if(!isInitialized)
		{
			isInitialized = true;
            TreeDataService<T,D,I> dataService = TreeDataServiceFactory.createService();
			currentDef = getCurrentTreeDef();
			createMenus();
            navBoxes = Collections.emptyList();
            
            if (commandTypeString != null)
            {
                CommandDispatcher.register(commandTypeString,this);
            }
		}
	}
    
    protected abstract D getCurrentTreeDef();
	
	/**
     * Creates a simple menu item that brings this task into context.
     * 
	 * @param defs a list of tree definitions handled by this task
	 */
	protected void createMenus()
	{
        menuItem = new JMenuItem(menuItemText);
        menuItem.setMnemonic(menuItemMnemonic.charAt(0));
        MenuItemDesc miDesc = new MenuItemDesc(menuItem, "AdvMenu");
        menuItems.add(miDesc);
        
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if (!currentDefInUse)
                {
                    requestContext();
                    showTree(currentDef);
                    menuItem.setEnabled(false);
                }
                else
                {
                    SubPaneMgr.getInstance().showPane(visibleSubPane);
                }
            }
        });
	}
    
	/**
     * Displays the tree associated with the given {@link TreeDefIface}.
     * 
	 * @param treeDef the {@link TreeDefIface} corresponding to the tree to be displayed
	 * @return a {@link SubPaneIFace} for displaying the tree
	 */
	protected void showTree(final D treeDef)
	{
        SwingWorker bgWork = new SwingWorker()
        {
            @Override
            @SuppressWarnings("unchecked")
            public Object construct()
            {
                DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
                D def = (D)session.load(treeDef.getClass(), treeDef.getTreeDefId());
                session.close();
                return def;
            }

            @SuppressWarnings({ "unchecked", "synthetic-access" })
            @Override
            public void finished()
            {
                super.finished();
                D def = (D)getValue();
                showTreeInternal(def);
            }
        };
        
        bgWork.start();
	}
    
    protected TreeTableViewer<T,D,I> showTreeInternal(final D treeDef)
    {
        ContextMgr.requestContext(this);
        String tabName = getResourceString(name) + ": " + treeDef.getName();
        TreeTableViewer<T,D,I> ttv = new TreeTableViewer<T,D,I>(treeDef,tabName,this);
        addSubPaneToMgr(ttv);
        currentDefInUse = true;
        visibleSubPane = ttv;
        return ttv;
    }
    

    /**
     * Opens a {@link SubPaneIFace} for viewing/editing a {@link TreeDefIface} object.
     */
	public TreeDefinitionEditor<T,D,I> openTreeDefEditor(D treeDef)
	{
        ContextMgr.requestContext(this);
        String tabName = getResourceString("TreeDefEditor") + ": " + treeDef.getName();
	    TreeDefinitionEditor<T,D,I> defEditor = new TreeDefinitionEditor<T,D,I>(treeDef,tabName,this);
        addSubPaneToMgr(defEditor);
        currentDefInUse = true;
        visibleSubPane = defEditor;
        return defEditor;
	}
    

	/**
	 * Switches the current view from {@link TreeTableViewer} to {@link TreeDefinitionEditor}
     * or vice versa.  If the current view is neither of these, this does nothing.
	 */
	@SuppressWarnings("unchecked")
    protected void switchViewType()
	{
	    // find out what def this refers to
	    SubPaneIFace subPane = SubPaneMgr.getInstance().getCurrentSubPane();

	    if (subPane instanceof TreeTableViewer)
	    {
	        TreeDefIface treeDef = ((TreeTableViewer)subPane).getTreeDef();
	        SubPaneMgr.getInstance().closeCurrent();
	        openTreeDefEditor((D)treeDef);
	        return;
	    }
        else if (subPane instanceof TreeDefinitionEditor)
        {
            TreeDefIface treeDef = ((TreeDefinitionEditor)subPane).getDisplayedTreeDef();
            SubPaneMgr.getInstance().closeCurrent();
            showTree((D)treeDef);
            return;
        }
	    return;
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.af.tasks.BaseTask#getMenuItems()
	 */
	@Override
	public List<MenuItemDesc> getMenuItems()
	{
		return menuItems;
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.af.tasks.BaseTask#getStarterPane()
	 */
	@Override
    public SubPaneIFace getStarterPane()
    {
        // This starter pane will only be visible for a brief moment while the tree loads.
        // It doesn't need to be fancy.
        return starterPane = new SimpleDescPane(title, this, starterPaneText);
    }

	/* (non-Javadoc)
	 * @see edu.ku.brc.af.tasks.BaseTask#getToolBarItems()
	 */
	@Override
	public List<ToolBarItemDesc> getToolBarItems()
	{
        return toolBarItems;
	}
	
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getTaskClass()
     */
	@SuppressWarnings("unchecked")
    @Override
	public Class<? extends BaseTreeTask> getTaskClass()
    {
        return this.getClass();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#subPaneRemoved(edu.ku.brc.af.core.SubPaneIFace)
     */
    @SuppressWarnings("unchecked")
    @Override
	public void subPaneRemoved(SubPaneIFace subPane)
	{
        if (!subPane.getTask().equals(this))
        {
            // we don't care about this subpane being closed
            // it's not one of ours
            return;
        }
        currentDefInUse = false;
        visibleSubPane = null;
        menuItem.setEnabled(true);
	}

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#doCommand(edu.ku.brc.ui.CommandAction)
     */
    @Override
    public void doCommand(CommandAction cmdAction)
    {
        UIRegistry.getStatusBar().setText("");
        
        if (cmdAction.isType(DataEntryTask.DATA_ENTRY))
        {
            // catch when the form switches modes
            if (cmdAction.isAction(DataEntryTask.VIEW_WAS_SHOWN))
            {
                if (cmdAction.getData() instanceof FormViewObj)
                {
                    FormViewObj formViewObj = (FormViewObj)cmdAction.getData();
                    if (formViewObj != null)
                    {
                        adjustForm(formViewObj);
                    }
                }
            }
            // catch when the form is initially opened (but after the object is set into it)
            else if (cmdAction.isAction(DataEntryTask.VIEW_WAS_OPENED))
            {
                if (cmdAction.getData() instanceof FormPane)
                {
                    FormPane fp = (FormPane)cmdAction.getData();
                    FormViewObj formViewObj = (FormViewObj)fp.getViewable();
                    if (formViewObj != null)
                    {
                        adjustForm(formViewObj);
                    }
                }
            }
            else if (cmdAction.isAction("Save"))
            {
                // should we do anything here?
            }
            
        }
    }
    
    public void adjustForm(FormViewObj form)
    {
        log.debug("adjustForm( " + form.getName() + " )"); 
        
        // this method should look a lot like ...
        
        /*
        if (form.getDataObj() instanceof T)
        {
            adjustNodeForm(form);
        }
        else if (form.getDataObj() instanceof D)
        {
            adjustTreeDefForm(form);
        }
        else if (form.getDataObj() instanceof I)
        {
            adjustTreeDefItemForm(form);
        }
        */
        
        // however, instanceof won't work with generics, so we have to implement this method in the subclasses
    }
    
    @SuppressWarnings("unchecked")
    protected void adjustNodeForm(final FormViewObj form)
    {
        log.debug("adjustNodeForm(FormViewObj form)");

        if (form.getAltView().getMode() != CreationMode.Edit)
        {
            // when we're not in edit mode, we don't need to setup any listeners since the user can't change anything
            return;
        }

        final T nodeInForm = (T)form.getDataObj();

        final Component parentComboBox = form.getControlByName("parent");
        final ValComboBox rankComboBox = (ValComboBox)form.getControlByName("definitionItem");

        if (parentComboBox != null)
        {
            boolean isNew = MultiView.isOptionOn(form.getMVParent().getOptions(), MultiView.IS_NEW_OBJECT);
            
            if (!isNew)
            {
                // Don't let them CHANGE the parent in the forms system.
                // They can create new children, but not move children.
                // That is done in the tree viewer.
                parentComboBox.setEnabled(false);
            }
            
            parentComboBox.addFocusListener(new FocusListener()
            {
                public void focusGained(FocusEvent e)
                {
                    // ignore this event
                }
                public void focusLost(FocusEvent e)
                {
                    // set the contents of this combobox based on the value chosen as the parent
                    GetSetValueIFace parentField = (GetSetValueIFace)parentComboBox;
                    adjustRankComboBoxModel(parentField, rankComboBox, nodeInForm);
                    
                    // set the tree def for the object being edited by using the parent node's tree def
                    T parent = (T)parentField.getValue();
                    if (parent != null)
                    {
                        nodeInForm.setDefinition(parent.getDefinition());
                    }
                }
            });
        }
        
        if (nodeInForm.getDefinitionItem() != null)
        {
            adjustRankComboBoxModel((GetSetValueIFace)parentComboBox, rankComboBox, nodeInForm);
        }
        
        // TODO: the form system MUST require the accepted parent widget to be present if the isAccepted checkbox is present
        final JCheckBox acceptedCheckBox = (JCheckBox)form.getControlByName("isAccepted");
        final GetSetValueIFace acceptedParentWidget = (GetSetValueIFace)form.getControlByName("acceptedParent");
        if (acceptedCheckBox != null && acceptedParentWidget != null)
        {
            acceptedCheckBox.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent e)
                {
                    if (acceptedCheckBox.isSelected())
                    {
                        acceptedParentWidget.setValue(null, null);
                    }
                }
            });
        }
    }
    
    @SuppressWarnings("unchecked")
    protected void adjustRankComboBoxModel(GetSetValueIFace parentField, ValComboBox rankComboBox, T nodeInForm)
    {
        DefaultComboBoxModel model = (DefaultComboBoxModel)rankComboBox.getModel();
        model.removeAllElements();

        // this is the highest rank the edited item can possibly be
        I topItem = null;
        // this is the lowest rank the edited item can possibly be
        I bottomItem = null;

        T parent = (T)parentField.getValue();
        if (parent == null)
        {
            return;
        }

        // grab all the def items from just below the parent's item all the way to the next enforced level
        // or to the level of the highest ranked child
        topItem = parent.getDefinitionItem().getChild();
        
        if (topItem == null)
        {
            // this only happens if a parent was chosen that cannot have children b/c it is at the
            // lowest defined level in the tree
            log.warn("Chosen node cannot be a parent node.  It is at the lowest defined level of the tree.");
            log.warn("Figure out how to restrict the searching in the combobox from allowing non-parent nodes from appearing.");
            return;
        }

        // find the child with the highest rank and set that child's def item as the bottom of the range
        if (!nodeInForm.getChildren().isEmpty())
        {
            for (T child: nodeInForm.getChildren())
            {
                if (bottomItem==null || child.getRankId()>bottomItem.getRankId())
                {
                    bottomItem = child.getDefinitionItem().getParent();
                }
            }
        }

        I item = topItem;
        boolean done = false;
        while (!done)
        {
            model.addElement(item);

            if (item.getChild()==null || item.getIsEnforced()==Boolean.TRUE || item==bottomItem)
            {
                done = true;
            }
            item = item.getChild();
        }
        
        if (nodeInForm.getDefinitionItem() != null)
        {
            I defItem = nodeInForm.getDefinitionItem();
            if (model.getIndexOf(defItem) != -1)
            {
                model.setSelectedItem(defItem);
            }
        }
        else if (model.getSize() == 1)
        {
            model.setSelectedItem(model.getElementAt(0));
        }
    }
}
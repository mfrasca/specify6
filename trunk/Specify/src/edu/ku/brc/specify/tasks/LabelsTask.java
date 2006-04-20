/* Filename:    $RCSfile: LabelsTask.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2005/10/19 19:59:54 $
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
package edu.ku.brc.specify.tasks;

import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.forms.builder.ButtonBarBuilder;

import edu.ku.brc.specify.core.ContextMgr;
import edu.ku.brc.specify.core.NavBox;
import edu.ku.brc.specify.core.NavBoxIFace;
import edu.ku.brc.specify.core.NavBoxItemIFace;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.plugins.MenuItemDesc;
import edu.ku.brc.specify.plugins.ToolBarItemDesc;
import edu.ku.brc.specify.tasks.subpane.LabelsPane;
import edu.ku.brc.specify.tasks.subpane.SimpleDescPane;
import edu.ku.brc.specify.ui.CommandAction;
import edu.ku.brc.specify.ui.CommandDispatcher;
import edu.ku.brc.specify.ui.IconListCellRenderer;
import edu.ku.brc.specify.ui.IconManager;
import edu.ku.brc.specify.ui.RolloverCommand;
import edu.ku.brc.specify.ui.SubPaneIFace;
import edu.ku.brc.specify.ui.ToolBarDropDownBtn;
import edu.ku.brc.specify.ui.Trash;
import edu.ku.brc.specify.ui.UICacheManager;
import edu.ku.brc.specify.ui.db.ChooseRecordSetDlg;
import edu.ku.brc.specify.ui.dnd.DataActionEvent;
import edu.ku.brc.specify.ui.dnd.GhostActionable;
import edu.ku.brc.specify.ui.dnd.GhostActionableDropManager;
import edu.ku.brc.specify.ui.dnd.GhostMouseInputAdapter;

/**
 * A task to manage Labels and response to Label Commands
 * 
 * @author rods
 * 
 */
@SuppressWarnings("serial")
public class LabelsTask extends BaseTask
{
    // Static Data Members
    public static final DataFlavor LABEL_FLAVOR = new DataFlavor(LabelsTask.class, "Label");
    private static Log log = LogFactory.getLog(LabelsTask.class);
    
    public static final String LABELS = "Labels";
    
    public static final String DOLABELS_ACTION     = "DoLabels";
    public static final String NEWRECORDSET_ACTION = "NewRecordSet";
    
    // Data Members
    protected Vector<NavBoxIFace>     extendedNavBoxes = new Vector<NavBoxIFace>(); 
    protected Vector<NavBoxItemIFace> labelsList       = new Vector<NavBoxItemIFace>(); 

    /**
     * 
     *
     */
    public LabelsTask()
    {
        super(LABELS, getResourceString(LABELS));
        CommandDispatcher.register(LABELS, this);
    }
    
   /**
     * Helper method for registering a NavBoxItem as a GhostMouseDropAdapter
     * @param list the list of NavBoxItems that will have this nbi registered as a drop zone
     * @param navBox the parent box for the nbi to be added to
     * @param navBoxItemDropZone the nbi in question
     * @return returns the new NavBoxItem
     */
    protected NavBoxItemIFace addToNavBoxAndRegisterAsDroppable(final java.util.List<NavBoxIFace> list, 
                                                                final NavBox          navBox, 
                                                                final NavBoxItemIFace nbi,
                                                                final String          fileName)
    {
        RolloverCommand roc = (RolloverCommand)nbi;
        roc.setData(fileName);
        
        // When Being Dragged
        roc.addDragDataFlavor(Trash.TRASH_FLAVOR);
        roc.addDragDataFlavor(LABEL_FLAVOR); 
        
        // When something is dropped on it
        roc.addDropDataFlavor(RecordSetTask.RECORDSET_FLAVOR);
        
        navBox.add(nbi);
        labelsList.add(nbi);
        return nbi;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#initialize()
     */
    public void initialize()
    {
        if (!isInitialized)
        {
            super.initialize(); // sets isInitialized to false
            
            NavBox navBox = new NavBox(name);  
            
            // Get all RecordSets and register them 
            RecordSetTask rst = (RecordSetTask)ContextMgr.getTaskByClass(RecordSetTask.class);
            
            java.util.List<NavBoxIFace> list = rst.getNavBoxes();
            
            // Temporary these to come from a persistent store
            addToNavBoxAndRegisterAsDroppable(list, navBox, NavBox.createBtn("Fish Label Example", name, IconManager.IconSize.Std16, new DisplayAction("fish_label.jrxml", "Fish Label Example")), "fish_label.jrxml");
            addToNavBoxAndRegisterAsDroppable(list, navBox, NavBox.createBtn("Lichens Label Example", name, IconManager.IconSize.Std16, new DisplayAction("lichens_label.jrxml", "Lichens Label Example")), "lichens_label.jrxml");

            navBoxes.addElement(navBox);
            
        }
        
    }

    
    /**
     * Performs a command (to cfreate a label)
     * @param name the XML file name for the label
     */
    public void doLabels(final String name, final String title, final Object data)
    {
        LabelsPane labelsPane = new LabelsPane(title, this);
        UICacheManager.addSubPane(labelsPane);
        RecordSet rs = null;
        if (data instanceof RecordSet)
        {
            rs = (RecordSet)data;
        }
        labelsPane.createReport(name, rs);

    }
    
    /**
     * @return Return true if there is a small number of labels or whether the user wishes to continue.
     */
    protected boolean checkForALotOfLabels(final RecordSet recordSet)
    {
        // 
        if (recordSet.getItems().size() > 200) // XXX Pref
        {
            Object[] options = {"Create Labels", "Cancel"};
            int n = JOptionPane.showOptionDialog((Frame)UICacheManager.get(UICacheManager.FRAME),
                                                String.format(getResourceString("LotsOfLabels"), new Object[] {(recordSet.getItems().size())}),
                                                getResourceString("LotsOfLabelsTitle"),
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,     //don't use a custom Icon
                                                options,  //the titles of buttons
                                                options[0]); //default button title
            if (n == 1)
            {
                return false;
            }
            
        }

        return true;
    }
    
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    public SubPaneIFace getStarterPane()
    {
        return new SimpleDescPane(name, this, "Welcome to Specify's Label Maker");
    }
    
    /**
     * Displays UI that asks the user to select a predefined label.
     * @return the name of the label file or null if cancelled
     */
    protected String askForLabelName()
    {
        initialize();
        
        // XXX need to pass in table type
        ChooseLabel dlg = new ChooseLabel();
        dlg.setVisible(true);

        return dlg.getName();
    }
    
    /**
     * Displays UI that asks the user to select a predefined label.
     * @return
     */
    protected RecordSet askForRecordSet()
    {
        ChooseRecordSetDlg dlg = new ChooseRecordSetDlg();
        dlg.setVisible(true);
        return dlg.getSelectedRecordSet();
    }
    
    
    
    //-------------------------------------------------------
    // Taskable
    //-------------------------------------------------------
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#getNavBoxes()
     */
    public java.util.List<NavBoxIFace> getNavBoxes()
    {
        initialize();
        
        extendedNavBoxes.clear();
        extendedNavBoxes.addAll(navBoxes);
        
        RecordSetTask rsTask = (RecordSetTask)ContextMgr.getTaskByClass(RecordSetTask.class);
        
        extendedNavBoxes.addAll(rsTask.getNavBoxes());
        
        return extendedNavBoxes;
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
        ToolBarDropDownBtn btn = createToolbarButton(name, "labels.gif", "labels_hint");   
        
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
    
    
    //-------------------------------------------------------
    // CommandListener Interface
    //-------------------------------------------------------

    public void doCommand(CommandAction cmdAction)
    {
        if (cmdAction.getAction().equals(DOLABELS_ACTION))
        {
            if (cmdAction.getData() instanceof RecordSet)
            {
                RecordSet recordSet = (RecordSet)cmdAction.getData();
                            
                if (checkForALotOfLabels(recordSet))
                {
                    String labelName = askForLabelName();
                    if (labelName != null)
                    {
                        doLabels(labelName, "Labels", recordSet);
                    }
                }
            }
        } else if (cmdAction.getAction().equals(NEWRECORDSET_ACTION))
        {
            if (cmdAction.getData() instanceof GhostActionable)
            {
                GhostActionable        ga  = (GhostActionable)cmdAction.getData();
                GhostMouseInputAdapter gpa = ga.getMouseInputAdapter();
                
                for (NavBoxItemIFace nbi : labelsList)
                {
                    if (nbi instanceof GhostActionable)
                    {
                        gpa.addGhostDropListener(new GhostActionableDropManager(UICacheManager.getGlassPane(), nbi.getUIComponent(), ga));
                    }
                 }
            }
        }
    }
    
    //--------------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------------
 
     /**
     * 
     * @author rods
     *
     */
    class DisplayAction implements ActionListener 
    {
        private String   name;
        private String   title;
        private RecordSet recordSet = null;
        
        
        public DisplayAction(final String name, final String title)
        {
            this.name = name;
            this.title = title;
        }
        
        public void actionPerformed(ActionEvent e) 
        {
            Object data = null;
            if (e instanceof DataActionEvent)
            {
                data = ((DataActionEvent)e).getData();
            }
            
            if (data == null)
            {
                data = askForRecordSet();
            }
            doLabels(name, title, data);
        }
        
        public void setRecordSet(final RecordSet recordSet)
        {
            this.recordSet = recordSet;
        }
        
        public RecordSet getRecordSet()
        {
            return recordSet;
        }
    }
    
    /**
     * @author rods
     *
     */
    public class ChooseLabel extends JDialog implements ActionListener 
    {
        protected JButton        cancelBtn;
        protected JButton        okBtn;
        protected JList          list;
        protected java.util.List recordSets;
        
        public ChooseLabel() throws HeadlessException
        {
            super((Frame)UICacheManager.get(UICacheManager.FRAME), getResourceString("ChooseLabel"), true);
            createUI();
            setLocationRelativeTo((JFrame)(Frame)UICacheManager.get(UICacheManager.FRAME));
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.setAlwaysOnTop(true);
        }

        /**
         * Creates the Default UI for Lable task
         *
         */
        protected void createUI()
        {
            
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
            
            panel.add(new JLabel(getResourceString("ChooseLabel"), JLabel.CENTER), BorderLayout.NORTH);

            try
            {
                ListModel listModel = new AbstractListModel() 
                {
                    public int getSize() { return labelsList.size(); }
                    public Object getElementAt(int index) 
                    { 
                        return ((RolloverCommand)labelsList.get(index)).getLabelText(); 
                    }
                };
                
                list = new JList(listModel);
                list.setCellRenderer(new IconListCellRenderer(icon));
                
                list.setVisibleRowCount(5);
                list.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            okBtn.doClick(); //emulate button click
                        }
                    }
                });
                JScrollPane listScroller = new JScrollPane(list);
                panel.add(listScroller, BorderLayout.CENTER);
                
                // Bottom Button UI
                cancelBtn         = new JButton(getResourceString("Cancel"));
                okBtn             = new JButton(getResourceString("OK"));

                okBtn.addActionListener(this);
                getRootPane().setDefaultButton(okBtn);
                
                ButtonBarBuilder btnBuilder = new ButtonBarBuilder();
                btnBuilder.addGlue();
                btnBuilder.addGriddedButtons(new JButton[] {cancelBtn, okBtn}); 
     
                cancelBtn.addActionListener(new ActionListener()
                        {  public void actionPerformed(ActionEvent ae) { setVisible(false);} });
                
                panel.add(btnBuilder.getPanel(), BorderLayout.SOUTH);

            } catch (Exception ex)
            {
                log.error(ex);
            }
            
            setContentPane(panel);
            pack();
            //setLocationRelativeTo(locationComp);
            
        }
        
         /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) 
        {
            // Handle clicks on the OK and Cancel buttons.
           setVisible(false);
        }
        
        /* (non-Javadoc)
         * @see java.awt.Component#getName()
         */
        public String getName()
        {
            int inx = list.getSelectedIndex();
            if (inx != -1)
            {
                RolloverCommand rb = (RolloverCommand)labelsList.get(inx);
                return (String)rb.getData();
            }
            return null;
        }
    }

 
}

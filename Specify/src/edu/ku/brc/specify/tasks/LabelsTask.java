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

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.print.PrinterException;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.NavBox;
import edu.ku.brc.af.core.NavBoxAction;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.ToolBarItemDesc;
import edu.ku.brc.af.tasks.BaseTask;
import edu.ku.brc.specify.tasks.subpane.LabelsPane;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.ToolBarDropDownBtn;

/**
 * A task to manage Labels and response to Label Commands.
 *
 * @code_status Beta
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class LabelsTask extends ReportsBaseTask
{
    protected static final String PRINT_TABLE = "PrintTable";
    
    /**
     * Constructor.
     */
    public LabelsTask()
    {
        name          = "Labels";
        title         = getResourceString(name);
        defaultFlavor = new DataFlavor(ReportsBaseTask.class, name);
        mimeType      = LABELS_MIME;
        reportHintKey = "LABEL_TT";

        setIcon(this.name);
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#preInitialize()
     */
    @Override
    public void preInitialize()
    {
        super.preInitialize();
        
        actionNavBox.add(NavBox.createBtnWithTT(getResourceString("Create_New_Label"), name, getResourceString("CREATE_LABEL_TT"), IconManager.IconSize.Std16, null));
        actionNavBox.add(NavBox.createBtnWithTT(getResourceString("LabelEditor"),  "EditIcon", getResourceString("EDIT_LABEL_TT"), IconManager.IconSize.Std16, new NavBoxAction(name, OPEN_EDITOR))); // I18N

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.ReportsBaseTask#initialize()
     */
    @Override
    public void initialize()
    {
        super.initialize();
        
        CommandAction cmdAction = new CommandAction(REPORTS, PRINT_TABLE);
        ContextMgr.registerService(PRINT_TABLE, -1, cmdAction, this, "Print", getResourceString("PRINT_GRID_TT"));

    }


    /**
     * Adds a WorkbenchTemplate to the Left Pane NavBox
     * @param workbench the workbench to be added
     */
    /*protected void addLabelToNavBox(final Workbench workbench)
    {
        CommandAction cmd = new CommandAction(LABELS, PRINT_LABEL, Workbench.getClassTableId());
        RecordSet     rs  = new RecordSet(workbench.getName(), Workbench.getClassTableId());
        rs.addItem(workbench.getWorkbenchId());
        cmd.setProperty("workbench", rs);
        final RolloverCommand roc = (RolloverCommand)makeDnDNavBtn(actionNavBox, workbench.getName(), "DataSet16", cmd, 
                                                                   new CommandAction(LABELS, DELETE_CMD_ACT, rs), 
                                                                   true, true);// true means make it draggable
        roc.setToolTip(getResourceString("WB_PRINTLABEL_TT")); 
        
        // Drag Flavors
        roc.addDragDataFlavor(Trash.TRASH_FLAVOR);
        roc.addDragDataFlavor(REPORT_FLAVOR);
        roc.addDropDataFlavor(RecordSetTask.RECORDSET_FLAVOR);

        // Drop Flavors
        //roc.addDropDataFlavor(new DataFlavor(Workbench.class, EXPORT_DATA_FILE));
        //roc.addDropDataFlavor(new DataFlavor(CollectionObject.class, "Report"));
        roc.addDropDataFlavor(RecordSetTask.RECORDSET_FLAVOR);
       
    }*/

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    public SubPaneIFace getStarterPane()
    {
        //starterPane = new SimpleDescPane(name, this, "Welcome to Specify's Label Maker");
        LabelsPane labelsPane = new LabelsPane(name, this, null);
        labelsPane.setLabelText("Welcome to Specify's Label Maker"); // XXX I18N
        starterPane = labelsPane;
        return starterPane;
    }

    //-------------------------------------------------------
    // Taskable
    //-------------------------------------------------------
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getToolBarItems()
     */
    public List<ToolBarItemDesc> getToolBarItems()
    {
        Vector<ToolBarItemDesc> list = new Vector<ToolBarItemDesc>();
        String             label     = getResourceString(name);
        String             iconName  = name;
        String             hint      = getResourceString("labels_hint");
        ToolBarDropDownBtn btn       = createToolbarButton(label, iconName, hint);

        list.add(new ToolBarItemDesc(btn));
        return list;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getTaskClass()
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
    public void doCommand(final CommandAction cmdAction)
    {
        // String taskName = cmdAction.getPropertyAsString("task name");
        //if (StringUtils.isNotEmpty(taskName) && taskName.equals(getName()))
        if (cmdAction.isType(REPORTS))
        {
            if (cmdAction.isAction(PRINT_TABLE))
            {
                
                JTable table = (JTable)cmdAction.getProperty("jtable");
                if (table != null)
                {
                    try
                    {
                        
                        table.print();
                        /*
                        JFrame      frame    = new JFrame();
                        JTable      prtTable = new JTable(table.getModel());
                        
                        MyTableCellRenderer cellRenderer = new MyTableCellRenderer();
                        TableColumnModel    colModel     = prtTable.getColumnModel();
                        for (int i=0;i<colModel.getColumnCount();i++)
                        {
                            colModel.getColumn(i).setCellRenderer(cellRenderer);
                            colModel.getColumn(i).setHeaderRenderer(cellRenderer);
                        }
                        JScrollPane sp = new JScrollPane(prtTable);
                        frame.setContentPane(sp);
                        frame.pack();
                        frame.setSize(1500, 768);
                        prtTable.setShowGrid(true);
                        prtTable.print();
                        /*boolean horzLines = table.getShowHorizontalLines();
                        boolean vertLines = table.getShowVerticalLines();
                        table.setShowHorizontalLines(true);
                        table.setShowVerticalLines(true);
                        table.validate();*/
                        //table.print();
                        
                        /*table.setShowHorizontalLines(horzLines);
                        table.setShowVerticalLines(vertLines);
                        table.validate();
                        */

                    } catch (PrinterException ex)
                    {
                        System.err.println(ex);
                        ex.printStackTrace(); 
                    }
                }
            } else
            {
                super.doCommand(cmdAction);
                
                /*if (cmdAction.isType(APP_CMD_TYPE) && cmdAction.isAction(APP_RESTART_ACT))
                {
                    ContextMgr.removeServicesByTask(this);
                    ContextMgr.registerService(PRINT_TABLE, -1, cmdAction, this, "Print", "");
                }*/
            }
        }
        
    }
    
    public class MyTableCellRenderer extends JLabel implements TableCellRenderer 
    {
        public MyTableCellRenderer()
        {
            super();
            setFont(getFont().deriveFont(8.0f));
        }
        
        // This method is called each time a cell in a column
        // using this renderer needs to be rendered.
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
            // 'value' is value contained in the cell located at
            // (rowIndex, vColIndex)
    
            if (isSelected) {
                // cell (and perhaps other cells) are selected
            }
    
            if (hasFocus) {
                // this cell is the anchor and the table has the focus
            }
    
            // Configure the component with the specified value
            setText(value.toString());
    
            // Set tool tip if desired
            setToolTipText((String)value);
    
            // Since the renderer is a component, return itself
            return this;
        }
    
        // The following methods override the defaults for performance reasons
        public void validate() {}
        public void revalidate() {}
        protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
        public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
    }
}

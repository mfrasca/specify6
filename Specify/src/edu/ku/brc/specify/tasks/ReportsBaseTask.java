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
import it.businesslogic.ireport.gui.MainFrame;

import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import net.sf.jasperreports.engine.JRDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.MenuItemDesc;
import edu.ku.brc.af.core.NavBox;
import edu.ku.brc.af.core.NavBoxAction;
import edu.ku.brc.af.core.NavBoxIFace;
import edu.ku.brc.af.core.NavBoxItemIFace;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.TaskCommandDef;
import edu.ku.brc.af.core.Taskable;
import edu.ku.brc.af.core.ToolBarItemDesc;
import edu.ku.brc.af.tasks.BaseTask;
import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.tasks.subpane.LabelsPane;
import edu.ku.brc.specify.tools.IReportSpecify.MainFrameSpecify;
import edu.ku.brc.ui.ChooseFromListDlg;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.RolloverCommand;
import edu.ku.brc.ui.ToolBarDropDownBtn;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.dnd.GhostActionable;
import edu.ku.brc.ui.dnd.GhostActionableDropManager;
import edu.ku.brc.ui.dnd.GhostMouseInputAdapter;

/**
 * A task to manage Labels and response to Label Commands.
 *
 * @code_status Beta
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ReportsBaseTask extends BaseTask
{
    private static final Logger log = Logger.getLogger(ReportsBaseTask.class);
            
    // Static Data Members
    public static final String     REPORTS      = "REPORTS";
    public static final String     REPORTS_MIME = "jrxml/report";
    public static final String     LABELS_MIME  = "jrxml/label";

    //public static final String DOLABELS_ACTION     = "DoLabels";
    public static final String NEWRECORDSET_ACTION = "RPT.NewRecordSet";
    public static final String PRINT_REPORT        = "RPT.PrintReport";
    public static final String OPEN_EDITOR         = "RPT.OpenEditor";

    // Data Members
    protected DataFlavor              defaultFlavor    = null;
    protected String                  mimeType         = null;
    protected Vector<NavBoxIFace>     extendedNavBoxes = new Vector<NavBoxIFace>();
    protected Vector<NavBoxItemIFace> reportsList      = new Vector<NavBoxItemIFace>();
    protected NavBox                  actionNavBox     = null;
    
    protected String                  reportHintKey    = "REPORT_TT";

    // temp data
    protected NavBoxItemIFace         oneNbi           = null;

    //iReport MainFrame
    private static MainFrameSpecify iReportMainFrame   = null;
    
    /**
     * Constructor.
     */
    public ReportsBaseTask()
    {
        super();
        
        iReportMainFrame = null;
        isShowDefault = true;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#preInitialize()
     */
    @Override
    public void preInitialize()
    {
        CommandDispatcher.register(REPORTS, this);
        CommandDispatcher.register(RecordSetTask.RECORD_SET, this);
        CommandDispatcher.register(APP_CMD_TYPE, this);
        
        RecordSetTask.addDroppableDataFlavor(defaultFlavor);
        
        // Create and add the Actions NavBox first so it is at the top at the top
        actionNavBox = new NavBox(getResourceString("Actions"));
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#initialize()
     */
    public void initialize()
    {
        if (!isInitialized)
        {
            super.initialize(); // sets isInitialized to false
            
            extendedNavBoxes.clear();
            reportsList.clear();
            
            navBoxes.add(actionNavBox);

            if (isVisible)
            {

                NavBox navBox = new NavBox(name);
                
                addAppResourcesToCommandsByMimeType(mimeType, "Report", null); // no particular table at the moment
                
                // Then add
                if (commands != null)
                {
                    for (TaskCommandDef tcd : commands)
                    {
                        // XXX won't be needed when we start validating the XML
                        String tableIdStr = tcd.getParams().getProperty("tableid");
                        if (tableIdStr != null)
                        {
                            CommandAction cmdAction = new CommandAction(REPORTS, PRINT_REPORT, tableIdStr);
                            cmdAction.addStringProperties(tcd.getParams());
                            cmdAction.getProperties().put("icon", IconManager.getIcon(tcd.getIconName()));
                            cmdAction.getProperties().put("task name", getName());
                            
                            NavBoxItemIFace nbi = makeDnDNavBtn(navBox, 
                                                                tcd.getName(), 
                                                                tcd.getIconName(), 
                                                                cmdAction, 
                                                                null, 
                                                                true,  // true means make it draggable
                                                                true); // true means add sorted
                            reportsList.add(nbi);
                            
                            RolloverCommand roc = (RolloverCommand)nbi;
                            roc.addDropDataFlavor(RecordSetTask.RECORDSET_FLAVOR);
                            roc.addDragDataFlavor(defaultFlavor);
                            roc.setToolTip(getResourceString(reportHintKey));

                        } else
                        {
                            log.error("Interaction Command is missing the table id");
                        }
                    }

                }
                
                
                navBoxes.add(navBox);
            }
        }
    }

    /**
     * Performs a command (to create a label).
     * @param labelName the name of lable (the file name)
     * @param labelTitle the localized title to be displayed as the tab title
     * @param recordSet the recordSet to be turned into labels
     * @param params parameters for the report
     * @param originatingTask the Taskable requesting the the labels be made
     * @param paneIcon the icon of the pane(if it is null then it uses the Task's icon)
     */
    public void doLabels(final String              labelName, 
                         final String              labelTitle, 
                         final Object              data, 
                         final Properties          params,
                         final Taskable            originatingTask,
                         final ImageIcon           paneIcon)
    {
        int startPaneIndex = starterPane != null ? SubPaneMgr.getInstance().indexOfComponent(starterPane.getUIComponent()) : -1;
        
        LabelsPane labelsPane;
        if (startPaneIndex == -1)
        {
            labelsPane = new LabelsPane(labelTitle, originatingTask != null ? originatingTask : this, params);
            labelsPane.setIcon(paneIcon);
            addSubPaneToMgr(labelsPane);
            
        } else
        {
            labelsPane  = (LabelsPane)starterPane;
            SubPaneMgr.getInstance().renamePane(labelsPane, labelTitle);
        }
        labelsPane.createReport(labelName, data, params);
        starterPane = null;
    }

    /**
     * @return Return true if there is a small number of labels or whether the user wishes to continue.
     */
    protected boolean checkForALotOfLabels(final RecordSetIFace recordSet)
    {
        //
        if (recordSet.getItems().size() > 200) // XXX Pref
        {
            Object[] options = {getResourceString("Create_New_Report"), getResourceString("Cancel")};
            int n = JOptionPane.showOptionDialog(UIRegistry.get(UIRegistry.FRAME),
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
        //starterPane = new SimpleDescPane(name, this, "Welcome to Specify's Label Maker");
        LabelsPane labelsPane = new LabelsPane(name, this, null);
        labelsPane.setLabelText("Welcome to Specify's Label Maker"); // XXX I18N
        starterPane = labelsPane;
        return starterPane;
    }

    /**
     * Displays UI that asks the user to select a predefined label.
     * @return the name of the label file or null if cancelled
     */
    protected String askForLabelName()
    {
        initialize();

        // XXX Need to pass in or check table type for different types of lables.

        NavBoxItemIFace nbi = null;
        if (reportsList.size() == 1)
        {
            nbi = reportsList.get(0);

        } else
        {
            ChooseFromListDlg<NavBoxItemIFace> dlg = new ChooseFromListDlg<NavBoxItemIFace>((Frame)UIRegistry.getTopWindow(),
                                                                                            getResourceString("ChooseLabel"),
                                                                                            reportsList, 
                                                                                            IconManager.getIcon(name, IconManager.IconSize.Std24));
            dlg.setMultiSelect(false);
            dlg.setModal(true);
            dlg.setVisible(true);
            if (!dlg.isCancelled())
            {
                nbi  = dlg.getSelectedObject();
            }
        }
        
        if (nbi != null && nbi.getData() != null)
        {
            Object data = nbi.getData();
            if (data instanceof CommandAction)
            {
                return ((CommandAction)data).getPropertyAsString("file");
            }
        }
        return null;
    }

    /**
     * Single place to convert the data to a Map.
     * @param data the data in a nbi
     * @return a Properties
     */
    @SuppressWarnings("unchecked")
    protected Properties convertDataToMap(final Object data)
    {
        if (data instanceof Map)
        {
            return (Properties)data; // ok to Cast
        }
        throw new RuntimeException("Why isn't the data a Properties!");
    }

    /**
     * Counts up how many labels match the same table id as the RecordSet and sets
     * oneNbi to the non-null match which means if there is only one then it points to it.
     * @param tableId the RecordSet's Table Id
     * @param needsRecordSet indicates we should ONLY include those that require a RecordSet 
     * @return the count of matches
     */
    protected int countLabelsWithSimilarTableIds(final int tableId, final boolean needsRecordSet)
    {
        oneNbi = null;
        int count = 0;
        for (NavBoxItemIFace nbi : reportsList)
        {
            Object data = nbi.getData();
            if (data instanceof CommandAction)
            {
                CommandAction cmdAction = (CommandAction)data;
                String              tableIDStr = cmdAction.getPropertyAsString("tableid");
                boolean             needsRS    = getNeedsRecordSet(cmdAction.getPropertyAsString("reqrs"));
                if (StringUtils.isNumeric(tableIDStr) && (!needsRecordSet ||  needsRS))
                {
                    if (Integer.parseInt(tableIDStr) == tableId)
                    {
                        oneNbi = nbi;
                        count++;
                    }
                } else
                {
                    log.error("Attr [tableid] value["+tableIDStr+"] is not numeric for["+nbi.getTitle()+"]!");
                }
            }
        }
        return count;
    }

    /**
     * Checks to make sure we are the current SubPane and then creates the labels from the selected RecordSet.
     * @param data the data that "should" be a RecordSet
     */
    protected void createLabelFromSelectedRecordSet(final Object data)
    {
        if (data instanceof RecordSet && ContextMgr.getCurrentContext() == this)
        {
            RecordSetIFace rs = (RecordSetIFace)data;

            String fileName = null;
            if (countLabelsWithSimilarTableIds(rs.getDbTableId(), true) > 1) // only Count the ones that require data
            {
                fileName = askForLabelName();
                
            } else if (oneNbi != null)
            {
                Object nbData = oneNbi.getData();
                if (nbData instanceof CommandAction)
                {
                    fileName = ((CommandAction)nbData).getPropertyAsString("file");
                }
            }

            if (fileName != null)
            {
                doLabels(fileName, "Labels", data, null, this, null);
            }
        }
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

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getToolBarItems()
     */
    public List<ToolBarItemDesc> getToolBarItems()
    {
        Vector<ToolBarItemDesc> list = new Vector<ToolBarItemDesc>();
        String label = getResourceString(name);
        String iconName = name;
        String hint = getResourceString("labels_hint");
        ToolBarDropDownBtn btn = createToolbarButton(label, iconName, hint);

        list.add(new ToolBarItemDesc(btn));
        return list;
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getMenuItems()
     */
    public List<MenuItemDesc> getMenuItems()
    {
        Vector<MenuItemDesc> list = new Vector<MenuItemDesc>();
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

    /**
     * Processes all Commands of type RECORD_SET.
     * @param cmdAction the command to be processed
     */
    protected void processRecordSetCommands(final CommandAction cmdAction)
    {
        if (cmdAction.isAction("Clicked"))
        {
            Object srcObj = cmdAction.getSrcObj();
            Object dstObj = cmdAction.getDstObj();
            Object data   = cmdAction.getData();
            
            log.debug("********* In Labels doCommand src["+srcObj+"] dst["+dstObj+"] data["+data+"] context["+ContextMgr.getCurrentContext()+"]");
             
            if (ContextMgr.getCurrentContext() == this)
            {
                createLabelFromSelectedRecordSet(srcObj);
            }
        }
    }
    
    /**
     * Prints a Label / Reports from a command
     * @param cmdAction the command
     */
    protected void printReport(final CommandAction cmdAction)
    {
        // Get the original set of params
        Properties params = cmdAction.getProperties();
        if (params == null)
        {
            params = new Properties();
        }
        
        // No add the additional params
        String paramList = cmdAction.getPropertyAsString("params");
        if (StringUtils.isNotEmpty(paramList))
        {
            params = UIHelper.parseProperties(paramList);
        }
        
        if (cmdAction.getData() instanceof RecordSet)
        {
            RecordSetIFace recordSet = (RecordSetIFace)cmdAction.getData();
            
            // XXX For the Demo and until I revist a generalized way of associating a default set of reports and labels
            // to To things. One way to get here with a null title is to click on the Labels btn from the search results
            if (recordSet.getDbTableId() == 1 && cmdAction.getPropertyAsString("title") == null)
            {
                cmdAction.setProperty("file", "fish_label.jrxml");
                cmdAction.setProperty("title", "Fish Labels");
                
            } else if (recordSet.getDbTableId() == 52 && cmdAction.getPropertyAsString("title") == null)
            {
                // XXX For the Demo and until I revist a generalized way of associating a default set of reports and labels
                // to To things. One way to get here with a null title is to click on the Labels btn from the search results
                cmdAction.setProperty("file",  "LoanInvoice.jrxml");
                cmdAction.setProperty("title", "Loan Invoice");
            }


            if (checkForALotOfLabels(recordSet))
            {
                String labelFileName = cmdAction.getPropertyAsString("file");
                
                if (StringUtils.isEmpty(labelFileName))
                {
                    labelFileName = askForLabelName();
                }
                
                if (StringUtils.isNotEmpty(labelFileName))
                {
                    Taskable originatingTask = (Taskable)cmdAction.getProperty(NavBoxAction.ORGINATING_TASK);
                    //System.out.println("************** "+(new File("/home/rods/Specify/reportsCache_Linux/fish_label.jrxml").lastModified()));
                    doLabels(labelFileName, cmdAction.getPropertyAsString("title"), recordSet, params, originatingTask, (ImageIcon)cmdAction.getProperty("icon"));
                }
            }
            
        } else if (cmdAction.getData() instanceof JRDataSource)
        {
            String labelFileName = cmdAction.getPropertyAsString("file");
            
            if (StringUtils.isEmpty(labelFileName))
            {
                labelFileName = askForLabelName();
            }
            
            if (StringUtils.isNotEmpty(labelFileName))
            {
                Taskable originatingTask = (Taskable)cmdAction.getProperty(NavBoxAction.ORGINATING_TASK);
                doLabels(labelFileName, cmdAction.getPropertyAsString("title"), cmdAction.getData(), params, originatingTask, (ImageIcon)cmdAction.getProperty("icon"));
            }
            
            
        } else
        {
            String tableIDStr = cmdAction.getPropertyAsString("tableid");
            if (StringUtils.isNotEmpty(tableIDStr) && StringUtils.isNumeric(tableIDStr))
            {
                RecordSetIFace recordSet = askForRecordSet(Integer.parseInt(tableIDStr));
                if (recordSet != null)
                {
                    doLabels(cmdAction.getPropertyAsString("file"), cmdAction.getPropertyAsString("title"), recordSet, params, this, (ImageIcon)cmdAction.getProperty("icon"));
                }
            }
            
        }

    }
    
    /**
     * Processes all Commands of type LABELS.
     * @param cmdAction the command to be processed
     */
    protected void processReportCommands(final CommandAction cmdAction)
    {
        
        //---------------------------------------------------------------------------
        // This Code here needs to be refactored and moved to the NavBoxAction
        // so it can happen in a single generic place (Each task has this code)
        //---------------------------------------------------------------------------
        /*if (cmdAction.getData() instanceof RecordSetIFace)
        {
            if (((RecordSetIFace)cmdAction.getData()).getDbTableId() != cmdAction.getTableId())
            {
                JOptionPane.showMessageDialog(null, getResourceString("ERROR_RECORDSET_TABLEID"), getResourceString("Error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
        }*/
           
        if (cmdAction.isAction(NEWRECORDSET_ACTION))
        {
            if (cmdAction.getData() instanceof GhostActionable)
            {
                GhostActionable        ga  = (GhostActionable)cmdAction.getData();
                GhostMouseInputAdapter gpa = ga.getMouseInputAdapter();

                for (NavBoxItemIFace nbi : reportsList)
                {
                    if (nbi instanceof GhostActionable)
                    {
                        gpa.addGhostDropListener(new GhostActionableDropManager(UIRegistry.getGlassPane(), nbi.getUIComponent(), ga));
                    }
                 }
            }
        } else if (cmdAction.isAction(PRINT_REPORT))
        {
            printReport(cmdAction);
            
        } else if (cmdAction.isAction(OPEN_EDITOR))
        {
            if (cmdAction.getData() == null) //no dropping yet.
            {
                openIReportEditor();
            }
        }
    }
    
    /**
     * Open the IReport editor.
     * @param cmdAction the command to be processed
     */
    private void openIReportEditor() 
    {
        if (iReportMainFrame == null)
        {
            MainFrame.reportClassLoader.rescanLibDirectory();
            Thread.currentThread().setContextClassLoader( MainFrame.reportClassLoader );
            Map<?,?> args = MainFrameSpecify.getArgs();
            iReportMainFrame = new MainFrameSpecify(args);
        }
        SwingUtilities.invokeLater( new Runnable()
        {
             public void run()
             {
                 iReportMainFrame.setVisible(true);
             }
        });
    }
        
    /*private void dumpProps(final Properties props, final int level)
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<level;i++) sb.append(" ");
        
        log.debug("--------------------");
        if (props != null)
        {
            for (Object key : props.keySet())
            {
                Object val = props.get(key);
                if (val instanceof Properties)
                {
                    dumpProps((Properties)val, level+2);
                } else
                {
                    log.debug(sb.toString()+"["+key+"]\t["+val+"]");
                }
                
            }
        }
    }*/

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#doCommand(edu.ku.brc.ui.CommandAction)
     */
    public void doCommand(final CommandAction cmdAction)
    {
        /*if (true)
        {
            log.debug("Direct Properties:");
            dumpProps(cmdAction.getProperties(), 0);
        }*/
        
        //log.debug("*************** "+this);
        if (cmdAction.isType(REPORTS))
        {
            processReportCommands(cmdAction);
            
        } else if (cmdAction.isType(RecordSetTask.RECORD_SET))
        {
            processRecordSetCommands(cmdAction);
            
        } else if (cmdAction.isType(APP_CMD_TYPE) && cmdAction.isAction(APP_RESTART_ACT))
        {
            isInitialized = false;
            this.initialize();
            ContextMgr.removeServicesByTask(this);
        }
    }
    /**
     * Returns the boolean value of "reqrs" from the metaData and true if it doesn't exist.
     * @param needsRSStr the string value of the map
     * @return true or false
     */
    protected boolean getNeedsRecordSet(final String needsRSStr)
    {
        boolean needsRS = true;
        if (StringUtils.isNotEmpty(needsRSStr) && StringUtils.isAlpha(needsRSStr))
        {
            needsRS = Boolean.parseBoolean(needsRSStr);
        } 
        return needsRS;
    }

}

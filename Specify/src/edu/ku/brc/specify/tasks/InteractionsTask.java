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
import java.awt.Frame;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.AppResourceIFace;
import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.MenuItemDesc;
import edu.ku.brc.af.core.NavBox;
import edu.ku.brc.af.core.NavBoxAction;
import edu.ku.brc.af.core.NavBoxButton;
import edu.ku.brc.af.core.NavBoxIFace;
import edu.ku.brc.af.core.NavBoxItemIFace;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.TaskCommandDef;
import edu.ku.brc.af.core.TaskMgr;
import edu.ku.brc.af.core.Taskable;
import edu.ku.brc.af.core.ToolBarItemDesc;
import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.PreferencesDlg;
import edu.ku.brc.af.tasks.BaseTask;
import edu.ku.brc.af.tasks.subpane.SimpleDescPane;
import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.dbsupport.RecordSetItemIFace;
import edu.ku.brc.dbsupport.TableModel2Excel;
import edu.ku.brc.helpers.EMailHelper;
import edu.ku.brc.helpers.Encryption;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.specify.config.DisciplineType;
import edu.ku.brc.specify.config.SpecifyAppContextMgr;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.Discipline;
import edu.ku.brc.specify.datamodel.ExchangeIn;
import edu.ku.brc.specify.datamodel.ExchangeOut;
import edu.ku.brc.specify.datamodel.Gift;
import edu.ku.brc.specify.datamodel.InfoRequest;
import edu.ku.brc.specify.datamodel.Loan;
import edu.ku.brc.specify.datamodel.LoanPreparation;
import edu.ku.brc.specify.datamodel.LoanReturnPreparation;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.datamodel.Shipment;
import edu.ku.brc.specify.ui.LoanReturnDlg;
import edu.ku.brc.specify.ui.LoanSelectPrepsDlg;
import edu.ku.brc.specify.ui.LoanReturnDlg.LoanReturnInfo;
import edu.ku.brc.ui.ChooseFromListDlg;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.DataFlavorTableExt;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.JStatusBar;
import edu.ku.brc.ui.RolloverCommand;
import edu.ku.brc.ui.ToolBarDropDownBtn;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.db.CommandActionForDB;
import edu.ku.brc.ui.db.PickListItemIFace;
import edu.ku.brc.ui.db.ViewBasedDisplayDialog;
import edu.ku.brc.ui.db.ViewBasedDisplayIFace;
import edu.ku.brc.ui.dnd.Trash;
import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.forms.FormViewObj;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.TableViewObj;
import edu.ku.brc.ui.forms.Viewable;
import edu.ku.brc.ui.forms.persist.ViewIFace;
import edu.ku.brc.ui.forms.validation.UIValidatable;


/**
 * This task manages Loans, Gifts, Exchanges and provide actions and forms to do the interactions.
 *
 * @code_status Beta
 *
 * @author rods
 *
 */
public class InteractionsTask extends BaseTask
{
    private static final Logger log = Logger.getLogger(InteractionsTask.class);

    public static final String             INTERACTIONS        = "Interactions";
    public static final DataFlavorTableExt INTERACTIONS_FLAVOR = new DataFlavorTableExt(DataEntryTask.class, INTERACTIONS);
    public static final DataFlavorTableExt INFOREQUEST_FLAVOR  = new DataFlavorTableExt(InfoRequest.class, "InfoRequest");
    public static final DataFlavorTableExt LOAN_FLAVOR         = new DataFlavorTableExt(Loan.class, "Loan");
    public static final DataFlavorTableExt GIFT_FLAVOR         = new DataFlavorTableExt(Loan.class, "Gift");
    public static final DataFlavorTableExt EXCHGIN_FLAVOR      = new DataFlavorTableExt(ExchangeIn.class, "ExchangeIn");
    public static final DataFlavorTableExt EXCHGOUT_FLAVOR     = new DataFlavorTableExt(ExchangeOut.class, "ExchangeOut");
    
    public static final  String   IS_USING_INTERACTIONS_PREFNAME = "Interactions.Using.Interactions.";
    public static final  String   IS_DOING_GIFT_PREFNAME = "Interactions.Doing.Gifts.";
    public static final  String   IS_DOING_EXCH_PREFNAME = "Interactions.Doing.Exchanges.";

    protected static final String InfoRequestName      = "InfoRequest";
    protected static final String NEW_LOAN             = "NEW_LOAN";
    protected static final String NEW_GIFT             = "NEW_GIFT";
    protected static final String NEW_EXCHANGE         = "NEW_EXCHANGE";
    protected static final String PRINT_LOAN           = "PRINT_LOANORGIFT";
    protected static final String INFO_REQ_MESSAGE     = "Specify Info Request";
    protected static final String CREATE_MAILMSG       = "CreateMailMsg";
    
    protected static final int    loanTableId;
    protected static final int    infoRequestTableId;
    protected static final int    colObjTableId;

    // Data Members
    protected NavBox                  infoRequestNavBox;
    //protected NavBox                  loansNavBox;
    //protected NavBox                  giftsNavBox;
    protected Vector<NavBoxIFace>     extendedNavBoxes = new Vector<NavBoxIFace>();
    protected Vector<NavBoxItemIFace> invoiceList      = new Vector<NavBoxItemIFace>();
    protected NavBoxItemIFace         exchgNavBtn      = null; 
    protected NavBoxItemIFace         giftsNavBtn      = null; 
    protected NavBox                  actionsNavBox;
    
    protected boolean                 isUsingInteractions = true;
    protected boolean                 isDoingGifts     = false;
    protected boolean                 isDoingExchanges = false;
    protected ToolBarDropDownBtn      toolBarBtn       = null;
    protected int                     indexOfTBB       = -1;
    
    static 
    {
        loanTableId        = DBTableIdMgr.getInstance().getIdByClassName(Loan.class.getName());
        infoRequestTableId = DBTableIdMgr.getInstance().getIdByClassName(InfoRequest.class.getName());
        colObjTableId      = DBTableIdMgr.getInstance().getIdByClassName(CollectionObject.class.getName());
        
        INFOREQUEST_FLAVOR.addTableId(50);
        
    }

   /**
     * Default Constructor
     *
     */
    public InteractionsTask()
    {
        super(INTERACTIONS, getResourceString("Interactions"));
        
        CommandDispatcher.register(INTERACTIONS, this);
        CommandDispatcher.register(RecordSetTask.RECORD_SET, this);
        CommandDispatcher.register(APP_CMD_TYPE, this);
        CommandDispatcher.register(DB_CMD_TYPE, this);
        CommandDispatcher.register(DataEntryTask.DATA_ENTRY, this);
        CommandDispatcher.register(PreferencesDlg.PREFERENCES, this);
    }
    
    /**
     * Retrieves the prefs that we cache.
     */
    protected void setUpCachedPrefs()
    {
        AppPreferences remotePrefs = AppPreferences.getRemote();
        String ds = Discipline.getCurrentDiscipline().getDiscipline();
        isUsingInteractions = remotePrefs.getBoolean(IS_USING_INTERACTIONS_PREFNAME+ds, true);
        isDoingGifts        = remotePrefs.getBoolean(IS_DOING_GIFT_PREFNAME+ds, true);
        isDoingExchanges    = remotePrefs.getBoolean(IS_DOING_EXCH_PREFNAME+ds, Discipline.isCurrentDiscipline(DisciplineType.STD_DISCIPLINES.botany));
  
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#initialize()
     */
    @Override
    public void initialize()
    {
        if (!isInitialized)
        {
            super.initialize(); // sets isInitialized to false
            
            setUpCachedPrefs();
            
            extendedNavBoxes.clear();
            invoiceList.clear();

            actionsNavBox = new NavBox(getResourceString("Actions"));
            
            // New Loan Action
            // A New loan can accept RecordSets that contain CollectionObjects or InfoRequests
            // or InfoRequests
            addCommand(actionsNavBox, NEW_LOAN, NEW_LOAN, "Loan", new int[] {Loan.getClassTableId(), CollectionObject.getClassTableId(), InfoRequest.getClassTableId()});
            if (isDoingGifts)
            {
                createGiftNavBtn();
            }
            if (isDoingExchanges)
            {
                createExchangeNavBtn();
            }
            addCommand(actionsNavBox, InfoRequestName, InfoRequestName, "InfoRequest", new int[] {ExchangeIn.getClassTableId(), ExchangeOut.getClassTableId(), CollectionObject.getClassTableId(), InfoRequest.getClassTableId()});
            addCommand(actionsNavBox, OPEN_FORM_CMD_ACT, "INTER_OPEN", name, new int[] {ExchangeIn.getClassTableId(), ExchangeOut.getClassTableId(), CollectionObject.getClassTableId(), InfoRequest.getClassTableId()});
            
            
            // These need to be loaded as Resources
            //navBox = new NavBox(getResourceString(ReportsTask.REPORTS));
            //navBox.add(NavBox.createBtn(getResourceString("All_Overdue_Loans_Report"), ReportsTask.REPORTS, IconManager.IconSize.Std16));
            //navBox.add(NavBox.createBtn(getResourceString("All_Open_Loans_Report"), ReportsTask.REPORTS, IconManager.IconSize.Std16));
            //navBox.add(NavBox.createBtn(getResourceString("All_Loans_Report"), ReportsTask.REPORTS, IconManager.IconSize.Std16));
            //addToNavBoxAndRegisterAsDroppable(navBox, NavBox.createBtn(getResourceString(PRINT_LOAN),  ReportsTask.REPORTS, IconManager.IconSize.Std16, new NavBoxAction(INTERACTIONS, PRINT_LOAN, this)), null);
            //navBoxes.addElement(navBox);
            
            // Then add
            if (commands != null)
            {
                for (AppResourceIFace ap : AppContextMgr.getInstance().getResourceByMimeType("jrxml/report"))
                {
                    Properties params = ap.getMetaDataMap();
                    if (params.getProperty("reporttype", "").equals("Invoice"))
                    {
                        params.put("title", ap.getDescription());
                        params.put("file", ap.getName());
                        //log.info("["+ap.getDescription()+"]["+ap.getName()+"]");
                        
                        commands.add(new TaskCommandDef(ap.getDescription(), name, params));
                    }
                }
                
                for (TaskCommandDef tcd : commands)
                {
                    // XXX won't be needed when we start validating the XML
                    String tableIdStr = tcd.getParams().getProperty("tableid");
                    if (tableIdStr != null)
                    {
                        //addToNavBoxAndRegisterAsDroppable(navBox, NavBox.createBtn(tcd.getName(), "Loan", IconManager.IconSize.Std16, new NavBoxAction(tcd, this)), tcd.getParams());
                        
                        CommandAction cmdAction = new CommandAction(INTERACTIONS, PRINT_LOAN, Loan.getClassTableId());
                        cmdAction.addStringProperties(tcd.getParams());
                        NavBoxItemIFace nbi = makeDnDNavBtn(actionsNavBox, tcd.getName(), "Reports", cmdAction, null, true, false);
                        RolloverCommand roc = (NavBoxButton)nbi;
                        invoiceList.add(nbi);// true means make it draggable
                        //setUpDraggable(nbi, new DataFlavor[]{Trash.TRASH_FLAVOR, INFOREQUEST_FLAVOR}, new NavBoxAction("", ""));
                        roc.addDragDataFlavor(INFOREQUEST_FLAVOR);
                        roc.addDragDataFlavor(Trash.TRASH_FLAVOR);
                        
                        DataFlavorTableExt dfx = new DataFlavorTableExt(RecordSetTask.RECORDSET_FLAVOR.getDefaultRepresentationClass(), 
                                RecordSetTask.RECORDSET_FLAVOR.getHumanPresentableName(), new int[] {52});
                        roc.addDropDataFlavor(dfx);
                        
                    } else
                    {
                        log.error("Interaction Command is missing the table id");
                    }
                }
            }
            navBoxes.add(actionsNavBox);
           
            infoRequestNavBox  = new NavBox(getResourceString("InfoRequest"));
            loadNavBox(infoRequestNavBox, InfoRequest.class, INFOREQUEST_FLAVOR);
            
            /*
            loansNavBox  = new NavBox(getResourceString("LOANS"));
            loadNavBox(loansNavBox, Loan.class, LOAN_FLAVOR);
            
            giftsNavBox  = new NavBox(getResourceString("GIFTS"));
            loadNavBox(giftsNavBox, Gift.class, GIFT_FLAVOR);
            
            exchangesNavBox  = new NavBox(getResourceString("EXCHANGES"));
            loadNavBox(exchangesNavBox, ExchangeIn.class, EXCHANGE_FLAVOR);
            
            exchangesNavBox  = new NavBox(getResourceString("EXCHANGES"));
            loadNavBox(exchangesNavBox, ExchangeOut.class, EXCHANGE_FLAVOR);
            */
            
        }
    }
    
    /**
     * 
     */
    private void createGiftNavBtn()
    {
        giftsNavBtn = addCommand(actionsNavBox, NEW_GIFT, NEW_GIFT, "Gift", new int[] {Gift.getClassTableId(), CollectionObject.getClassTableId(), InfoRequest.getClassTableId()});
    }
    
    /**
     * 
     */
    private void createExchangeNavBtn()
    {
        exchgNavBtn = addCommand(actionsNavBox, NEW_EXCHANGE,NEW_EXCHANGE, name, new int[] {Gift.getClassTableId(), CollectionObject.getClassTableId(), InfoRequest.getClassTableId()});
    }
    
    protected NavBoxButton addCommand(final NavBox navBox, 
                              final String action, 
                              final String name, 
                              final String iconName,
                              final int[]  tableIds)
    {
        CommandAction cmdAction = new CommandAction(INTERACTIONS, action);
        NavBoxButton roc = (NavBoxButton)makeDnDNavBtn(navBox, getResourceString(name), iconName, cmdAction, null, true, false);// true means make it draggable
        DataFlavorTableExt dfx = new DataFlavorTableExt(RecordSetTask.RECORDSET_FLAVOR.getDefaultRepresentationClass(), 
                                                        RecordSetTask.RECORDSET_FLAVOR.getHumanPresentableName(), tableIds);
        roc.addDropDataFlavor(dfx);
        return roc;
    }
    
    /**
     * Helper function to load all the items for a class of data objects (i.e. Loans, Gifts)
     * @param navBox the parent
     * @param dataClass the class to be loaded
     * @param dragFlav the drag flavor of the item
     */
    protected void loadNavBox(final NavBox     navBox, 
                              final Class<?>   dataClass,
                              final DataFlavor dragFlav)
    {
        DBTableInfo ti = DBTableIdMgr.getInstance().getByClassName(dataClass.getName());
        DataProviderSessionIFace session = null;
        try
        {
            session = DataProviderFactory.getInstance().createSession();
            
            String sql = "FROM " + ti.getClassName() + " WHERE collectionMemberId = COLMEMID";
            List<?> list = session.getDataList(QueryAdjusterForDomain.getInstance().adjustSQL(sql));
            for (Iterator<?> iter=list.iterator();iter.hasNext();)
            {
                FormDataObjIFace dataObj = (FormDataObjIFace)iter.next();
                
                CommandActionForDB   delCmd = new CommandActionForDB(INTERACTIONS, DELETE_CMD_ACT, ti.getTableId(), dataObj.getId());
                NavBoxItemIFace nbi    = makeDnDNavBtn(navBox, dataObj.getIdentityTitle(), ti.getShortClassName(), 
                                                       delCmd, null, true, true);
                RolloverCommand roc = (NavBoxButton)nbi;
                nbi.setData(new CommandActionForDB(INTERACTIONS, OPEN_FORM_CMD_ACT, ti.getTableId(), dataObj.getId()));
                roc.addDragDataFlavor(INFOREQUEST_FLAVOR);
                roc.addDragDataFlavor(Trash.TRASH_FLAVOR);
            }      
            navBoxes.add(navBox);
            
        } catch (Exception ex)
        {
            
        } finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }

    /**
     * Helper method for registering a NavBoxItem as a GhostMouseDropAdapter
     * @param navBox the parent box for the nbi to be added to
     * @param navBoxItemDropZone the nbi in question
     * @return returns the new NavBoxItem
     */
    protected NavBoxItemIFace addToNavBoxAndRegisterAsDroppable(final NavBox              navBox,
                                                                final NavBoxItemIFace     nbi,
                                                                final Properties params)
    {
        NavBoxButton roc = (NavBoxButton)nbi;
        roc.setData(params);

        // When Being Dragged
        roc.addDragDataFlavor(Trash.TRASH_FLAVOR);
        roc.addDragDataFlavor(INTERACTIONS_FLAVOR);

        // When something is dropped on it
        roc.addDropDataFlavor(RecordSetTask.RECORDSET_FLAVOR);

        navBox.add(nbi);
        //labelsList.add(nbi);
        return nbi;
    }
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#getNavBoxes()
     */
    @Override
    public java.util.List<NavBoxIFace> getNavBoxes()
    {
        initialize();

        extendedNavBoxes.clear();
        extendedNavBoxes.addAll(navBoxes);

        RecordSetTask rsTask = (RecordSetTask)ContextMgr.getTaskByClass(RecordSetTask.class);

        extendedNavBoxes.addAll(rsTask.getNavBoxes());

        return extendedNavBoxes;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    @Override
    public SubPaneIFace getStarterPane()
    {
        if (starterPane != null)
        {
            //SubPaneMgr.getInstance().showPane(starterPane);
            return starterPane;
            
        } else
        {
            return starterPane = new SimpleDescPane(title, this, getResourceString("INTERACTIONS_OVERVIEW"));
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getToolBarItems()
     */
    @Override
    public List<ToolBarItemDesc> getToolBarItems()
    {
        Vector<ToolBarItemDesc> list = new Vector<ToolBarItemDesc>();
        String label = getResourceString(name);
        String iconName = name;
        String hint = getResourceString("interactions_hint");
        toolBarBtn = createToolbarButton(label, iconName, hint);

        AppPreferences remotePrefs = AppPreferences.getRemote();
        if (remotePrefs == AppPreferences.getRemote())
        {
            String ds = Discipline.getCurrentDiscipline().getDiscipline();
            isUsingInteractions = remotePrefs.getBoolean(IS_USING_INTERACTIONS_PREFNAME+ds, true);
        }

        if (isUsingInteractions)
        {
            list.add(new ToolBarItemDesc(toolBarBtn));
        }

        return list;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getMenuItems()
     */
    @Override
    public List<MenuItemDesc> getMenuItems()
    {
        Vector<MenuItemDesc> list = new Vector<MenuItemDesc>();
        return list;

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getTaskClass()
     */
    @Override
    public Class<? extends BaseTask> getTaskClass()
    {
        return this.getClass();
    }
    
    /**
     * Creates a new loan from a RecordSet.
     * @param fileNameArg the filename of the report (Invoice) to use (can be null)
     * @param recordSet the recordset to use to create the loan
     */
    protected void printLoan(final String fileNameArg, final Object data)
    {
        if (data instanceof RecordSetIFace)
        {
            RecordSetIFace rs = (RecordSetIFace)data;
            
            if (rs.getDbTableId() != Loan.getClassTableId())
            {
                return;
            }
            
            String fileName = fileNameArg;
            if (fileName == null)
            {
                if (invoiceList.size() == 0)
                {
                    // XXX Need Error Dialog that there are no Invoices (can this happen?)
                    
                } else if (invoiceList.size() > 1) // only Count the ones that require data
                {
                    fileName = askForInvoiceName();
                    
                } else  
                {
                    NavBoxItemIFace nbi = invoiceList.get(0);
                    Object nbData = nbi.getData();
                    if (nbData instanceof CommandAction)
                    {
                        fileName = ((CommandAction)nbData).getPropertyAsString("file");
                    }
                }
            }

            if (fileName != null)
            {
                // XXX For Demo purposes only we need to be able to look up report and labels
                final CommandAction cmd = new CommandAction(ReportsBaseTask.REPORTS, ReportsBaseTask.PRINT_REPORT, rs);
                cmd.setProperty("file", "LoanInvoice.jrxml");
                cmd.setProperty("title", "Loan Invoice");
                cmd.setProperty(NavBoxAction.ORGINATING_TASK, this);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        CommandDispatcher.dispatch(cmd);
                    }
                });
            } else
            {
                // XXX need error message about not having an invoice
            }
        }
    }
    
    /**
     * Creates a new loan from a InfoRequest.
     * @param infoRequest the infoRequest to use to create the loan
     */
    protected void createNewLoan(final InfoRequest infoRequest)
    {   
        RecordSetIFace rs = null;
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        try
        {
            session.attach(infoRequest);
            rs = infoRequest.getRecordSet();
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            // Error Dialog
            
        } finally
        {
            if (session != null)
            {
                session.close();
            }
        }
        
        if (rs != null)
        {
            createNewLoan(infoRequest, rs);
        }
    }
    
    /**
     * Creates a new loan from a RecordSet.
     * @param recordSet the recordset to use to create the loan
     */
    @SuppressWarnings("unchecked")
    protected void createNewLoan(final InfoRequest infoRequest, final RecordSetIFace recordSet)
    {

        DBTableIdMgr.getInClause(recordSet);

        DBTableInfo tableInfo = DBTableIdMgr.getInstance().getInfoById(recordSet.getDbTableId());
        
        DataProviderFactory.getInstance().evict(tableInfo.getClassObj()); // XXX Not sure if this is really needed
        
        DataProviderSessionIFace session = null;
        try
        {
            session = DataProviderFactory.getInstance().createSession();
            
            // OK, it COULD be a RecordSet contain one or more InfoRequest, 
            // we will only accept an RS with one InfoRequest
            if (infoRequest == null && recordSet.getDbTableId() == infoRequestTableId)
            {
                if (recordSet.getNumItems() == 1)
                {
                    RecordSetItemIFace item = recordSet.getOnlyItem();
                    if (item != null)
                    {
                        InfoRequest infoReq = session.get(InfoRequest.class, item.getRecordId().intValue());
                        if (infoReq != null)
                        {
                            createNewLoan(infoReq, infoReq.getRecordSet());
                            
                        } else
                        {
                            // error about missing info request
                            // Error Dialog
                        }
                    } else
                    {
                        // error about item being null for some unbelievable reason 
                     // Error Dialog
                    }
                } else 
                {
                    // error about item having more than one or none
                    // Error Dialog
                }
                return;
            }
            
            // OK, here we have a recordset of CollectionObjects
            // First we process all the CollectionObjects in the RecordSet
            // and create a list of Preparations that can be loaned
            String sqlStr = DBTableIdMgr.getInstance().getQueryForTable(recordSet);
            if (StringUtils.isNotBlank(sqlStr))
            {
                final LoanSelectPrepsDlg loanSelectPrepsDlg = new LoanSelectPrepsDlg((List<CollectionObject>)session.getDataList(sqlStr));
                loanSelectPrepsDlg.setModal(true);
                
                UIHelper.centerAndShow(loanSelectPrepsDlg);
    
                final Taskable thisTask = this;
                final Hashtable<Preparation, Integer> prepsHash = loanSelectPrepsDlg.getPreparationCounts();
                if (prepsHash.size() > 0)
                {
                    final SwingWorker worker = new SwingWorker()
                    {
                        @Override
                        public Object construct()
                        {
                            JStatusBar statusBar = UIRegistry.getStatusBar();
                            statusBar.setIndeterminate(true);
                            statusBar.setText(getResourceString("CreatingLoan"));
                            
                            Loan loan = new Loan();
                            loan.initialize();
                            
                            Calendar dueDate = Calendar.getInstance();
                            dueDate.add(Calendar.MONTH, 6);                 // XXX PREF Due Date
                            loan.setCurrentDueDate(dueDate);
                            
                            Shipment shipment = new Shipment();
                            shipment.initialize();
                            
                            // Get Defaults for Certain fields
                            SpecifyAppContextMgr appContextMgr     = (SpecifyAppContextMgr)AppContextMgr.getInstance();
                            PickListItemIFace    defShipmentMethod = appContextMgr.getDefaultPickListItem("ShipmentMethod", getResourceString("SHIPMENT_METHOD"));
                            if (defShipmentMethod != null)
                            {
                                 shipment.setShipmentMethod(defShipmentMethod.getValue());
                            }
                            
                            FormDataObjIFace shippedBy = appContextMgr.getDefaultObject(Agent.class, "ShippedBy", getResourceString("SHIPPED_BY"), true, false);
                            if (shippedBy != null)
                            {
                                shipment.setShippedBy((Agent)shippedBy);
                            }
                            
                            if (infoRequest != null && infoRequest.getAgent() != null)
                            {
                                shipment.setShippedTo(infoRequest.getAgent());
                            }
                            
                            loan.addReference(shipment, "shipments");
                            
                            for (Preparation prep : prepsHash.keySet())
                            {
                                Integer count = prepsHash.get(prep);
                                
                                LoanPreparation lpo = new LoanPreparation();
                                lpo.initialize();
                                lpo.setPreparation(prep);
                                lpo.setQuantity(count);
                                lpo.setLoan(loan);
                                loan.getLoanPreparations().add(lpo);
                            }
                            
                            DataEntryTask dataEntryTask = (DataEntryTask)TaskMgr.getTask(DataEntryTask.DATA_ENTRY);
                            if (dataEntryTask != null)
                            {
                                DBTableInfo loanTableInfo = DBTableIdMgr.getInstance().getInfoById(loan.getTableId());
                                dataEntryTask.openView(thisTask, null, loanTableInfo.getDefaultFormName(), "edit", loan, true);
                            }
                            return null;
                        }
    
                        //Runs on the event-dispatching thread.
                        @Override
                        public void finished()
                        {
                            JStatusBar statusBar = UIRegistry.getStatusBar();
                            statusBar.setIndeterminate(false);
                            statusBar.setText("");
                        }
                    };
                    worker.start();
                }
                
            } else
            {
                log.error("Query String empty for RecordSet tableId["+recordSet.getDbTableId()+"]");
            }
            
        } catch (Exception ex)
        {
            log.error(ex);
            ex.printStackTrace();
            
        } finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }
    
    /**
     * Displays UI that asks the user to select a predefined label.
     * @return the name of the label file or null if cancelled
     */
    protected String askForInvoiceName()
    {
        initialize();

        // XXX Need to pass in or check table type for different types of lables.

        NavBoxItemIFace nbi = null;
        if (invoiceList.size() == 1)
        {
            nbi = invoiceList.get(0);

        } else
        {
            ChooseFromListDlg<NavBoxItemIFace> dlg = new ChooseFromListDlg<NavBoxItemIFace>((Frame)UIRegistry.getTopWindow(),
                                                                                            getResourceString("ChooseInvoice"), 
                                                                                            invoiceList, 
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
     * Creates a new InfoRequest from a RecordSet.
     * @param recordSet the recordSet to use to create the InfoRequest
     */
    protected void createInfoRequest(final RecordSetIFace recordSet)
    {
        DBTableInfo tableInfo = DBTableIdMgr.getInstance().getByShortClassName(InfoRequest.class.getSimpleName());
        
        ViewIFace view = AppContextMgr.getInstance().getView(tableInfo.getDefaultFormName());

        InfoRequest infoRequest = new InfoRequest();
        infoRequest.initialize();
        if (recordSet != null)
        {
            infoRequest.setRecordSet(recordSet);
            
        } else
        {
            infoRequest.setRecordSet(askForRecordSet(CollectionObject.getClassTableId()));
        }
        
        if (infoRequest.getRecordSet() != null &&
            infoRequest.getRecordSet().getItems() != null &&
            infoRequest.getRecordSet().getNumItems() > 0)
        {
            createFormPanel(name, view.getViewSetName(), view.getName(), "edit", infoRequest, MultiView.IS_NEW_OBJECT, null);
            
        } else
        {
            UIRegistry.displayErrorDlgLocalized("ERROR_MISSING_RS_OR_NOITEMS");  
        }
    }

    
    /**
     * @param cmdAction
     */
    protected void checkToPrintLoan(final CommandAction cmdAction)
    {
        if (cmdAction.getData() instanceof Loan)
        {
            Loan loan = (Loan)cmdAction.getData();
            
            Boolean     printLoan   = null;
            FormViewObj formViewObj = getCurrentFormViewObj();
            if (formViewObj != null)
            {
                Component comp = formViewObj.getControlByName("generateInvoice");
                if (comp instanceof JCheckBox)
                {
                    printLoan = ((JCheckBox)comp).isSelected();
                }
            }
            
            if (printLoan == null)
            {
                Object[] options = {getResourceString("CreateLoanInvoice"), getResourceString("Cancel")};
                int n = JOptionPane.showOptionDialog(UIRegistry.get(UIRegistry.FRAME),
                                                    String.format(getResourceString("CreateLoanInvoiceForNum"), new Object[] {(loan.getLoanNumber())}),
                                                    getResourceString("CreateLoanInvoice"),
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE,
                                                    null,     //don't use a custom Icon
                                                    options,  //the titles of buttons
                                                    options[0]); //default button title
                printLoan = n == 0;
            }
            
            // XXX DEBUG
            //printLoan = false;
            if (printLoan)
            {
                DataProviderSessionIFace session = null;
                try
                {
                    session = DataProviderFactory.getInstance().createSession();
                    //session.attach(loan);
                    loan = (Loan)session.getData("From Loan where loanId = "+loan.getLoanId());
                    
                    if (loan.getShipments().size() == 0)
                    {
                        UIRegistry.displayErrorDlg(getResourceString("NO_SHIPMENTS_ERROR"));
                        
                    } else if (loan.getShipments().size() > 1)
                    {
                        // XXX Do we allow them to pick a shipment or print all?
                        UIRegistry.displayErrorDlg(getResourceString("MULTI_SHIPMENTS_NOT_SUPPORTED"));
                        
                    } else
                    {
                        // XXX At the moment this is just checking to see if there is at least one "good/valid" shipment
                        // but the hard part will be sending the correct info so the report can be printed
                        // using bouth a Loan Id and a Shipment ID, and at some point distinguishing between using
                        // the shipped by versus the shipper.
                        Shipment shipment = loan.getShipments().iterator().next();
                        if (shipment.getShippedBy() == null)
                        {
                            UIRegistry.displayErrorDlg(getResourceString("SHIPMENT_MISSING_SHIPPEDBY"));
                        } else if (shipment.getShippedBy().getAddresses().size() == 0)
                        {
                            UIRegistry.displayErrorDlg(getResourceString("SHIPPEDBY_MISSING_ADDR"));
                        } else if (shipment.getShippedTo() == null)
                        {
                            UIRegistry.displayErrorDlg(getResourceString("SHIPMENT_MISSING_SHIPPEDTO"));
                        } else if (shipment.getShippedTo().getAddresses().size() == 0)
                        {
                            UIRegistry.displayErrorDlg(getResourceString("SHIPPEDTO_MISSING_ADDR"));
                        } else
                        {
                            //session.close();
                            //session = null;
                            
                            RecordSet rs = new RecordSet();
                            rs.initialize();
                            rs.setName(loan.getIdentityTitle());
                            rs.setDbTableId(loan.getTableId());
                            rs.addItem(loan.getId());
                            printLoan(null, rs);
                        }
                    }
                } finally
                {
                    if (session != null)
                    {
                        session.close();
                    }
                }
            }
        }
    }
    
    /**
     * Creates an Excel SpreadSheet or CVS file and attaches it to an email and send it to an agent.
     */
    public void createAndSendEMail()
    {
        FormViewObj formViewObj = getCurrentFormViewObj();
        if (formViewObj != null) // Should never happen
        {
            InfoRequest infoRequest = (InfoRequest)formViewObj.getDataObj();
            Agent       toAgent     = infoRequest.getAgent();
            
            boolean   sendEMail = true; // default to true
            Component comp      = formViewObj.getControlByName("sendEMail");
            if (comp instanceof JCheckBox)
            {
                sendEMail = ((JCheckBox)comp).isSelected();
            }
            
            MultiView mv = formViewObj.getSubView("InfoRequestColObj");
            if (mv != null && sendEMail)
            {
                final Viewable viewable = mv.getCurrentView();
                if (viewable instanceof TableViewObj)
                {
                    final Hashtable<String, String> emailPrefs = new Hashtable<String, String>();
                    if (!EMailHelper.isEMailPrefsOK(emailPrefs))
                    {
                        JOptionPane.showMessageDialog(UIRegistry.getTopWindow(), 
                                getResourceString("NO_EMAIL_PREF_INFO"), 
                                getResourceString("NO_EMAIL_PREF_INFO_TITLE"), JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    final File tempExcelFileName = TableModel2Excel.getTempExcelName();
                    
                    emailPrefs.put("to", toAgent.getEmail() != null ? toAgent.getEmail() : "");
                    emailPrefs.put("from", emailPrefs.get("email"));
                    emailPrefs.put("subject", String.format(getResourceString("INFO_REQUEST_SUBJECT"), new Object[] {infoRequest.getIdentityTitle()}));
                    emailPrefs.put("bodytext", "");
                    emailPrefs.put("attachedFileName", tempExcelFileName.getName());
                    
                    final Frame topFrame = (Frame)UIRegistry.getTopWindow();
                    final ViewBasedDisplayDialog dlg = new ViewBasedDisplayDialog(topFrame,
                                                  "SystemSetup",
                                                  "SendMail",
                                                  null,
                                                  getResourceString("SEND_MAIL_TITLE"),
                                                  getResourceString("SEND_BTN"),
                                                  null, // className,
                                                  null, // idFieldName,
                                                  true, // isEdit,
                                                  0);
                    dlg.setData(emailPrefs);
                    dlg.setModal(true);
                    dlg.setVisible(true);
                    
                    if (dlg.getBtnPressed() == ViewBasedDisplayIFace.OK_BTN)
                    {
                        dlg.getMultiView().getDataFromUI();
                        
                        //System.out.println("["+emailPrefs.get("bodytext")+"]");
                        
                        TableViewObj  tblViewObj = (TableViewObj)viewable;
                        File          excelFile  = TableModel2Excel.convertToExcel(tempExcelFileName, 
                                                                                   getResourceString("CollectionObject"), 
                                                                                   tblViewObj.getTable().getModel());
                        StringBuilder sb         = TableModel2Excel.convertToHTML(getResourceString("CollectionObject"), 
                                                                                  tblViewObj.getTable().getModel());
                        
                        //EMailHelper.setDebugging(true);
                        String text = emailPrefs.get("bodytext").replace("\n", "<br>") + "<BR><BR>" + sb.toString();
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run()
                            {
                                UIRegistry.displayLocalizedStatusBarText("SENDING_EMAIL");
                            }
                        });
                        
                        String password = Encryption.decrypt(emailPrefs.get("password"));
                        if (StringUtils.isEmpty(password))
                        {
                            password = EMailHelper.askForPassword(topFrame);
                        }
                        
                        if (StringUtils.isNotEmpty(password))
                        {
                            final boolean status = EMailHelper.sendMsg(emailPrefs.get("servername"), 
                                                                       emailPrefs.get("username"), 
                                                                       password, 
                                                                       emailPrefs.get("email"), 
                                                                       emailPrefs.get("to"), 
                                                                       emailPrefs.get("subject"), text, EMailHelper.HTML_TEXT, excelFile);
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run()
                                {
                                    UIRegistry.displayLocalizedStatusBarText(status ? "EMAIL_SENT_ERROR" : "EMAIL_SENT_OK");
                                }
                            });
                        }
                    }
                }
            }
        } else
        {
            log.error("Why doesn't the current SubPane have a main FormViewObj?");
        }
    }
    
    /**
     * Delete a InfoRequest..
     * @param infoRequest the infoRequest to be deleted
     */
    protected void deleteInfoRequest(final InfoRequest infoRequest)
    {
        // delete from database
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        try
        {
            // ???? session.attach(infoRequest);
            session.beginTransaction();
            session.delete(infoRequest);
            session.commit();
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex);
        }
        session.close();

    }
    
    /**
     * Delete the InfoRequest from the UI, which really means remove the NavBoxItemIFace. 
     * This method first checks to see if the boxItem is not null and uses that, if
     * it is null then it looks the box up by name ans used that
     * @param boxItem the box item to be deleted
     * @param infoRequest the infoRequest that is "owned" by some UI object that needs to be deleted (used for secodary lookup
     */
    protected void deleteInfoRequestFromUI(final NavBoxItemIFace boxItem, final InfoRequest infoRequest)
    {
        deleteDnDBtn(infoRequestNavBox, boxItem != null ? boxItem : getBoxByTitle(infoRequest.getIdentityTitle()));
    }
    
    /**
     * Starts process to return a loan.
     * @param agent the agent doing the return
     * @param returns the list of items being returned
     */
    protected void doReturnLoan(final Agent  agent, 
                                final List<LoanReturnInfo> returns)
    {
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                JStatusBar statusBar = UIRegistry.getStatusBar();
                statusBar.setIndeterminate(true);
                statusBar.setText(getResourceString("ReturningLoanItems"));
                
                DataProviderSessionIFace session = null;
                try
                {
                    session = DataProviderFactory.getInstance().createSession();
                    
                    session.beginTransaction();
                    
                    for (LoanReturnInfo loanRetInfo : returns)
                    {   
                        LoanPreparation loanPrep  = loanRetInfo.getLoanPreparation();
                        session.attach(loanPrep);
                        
                        LoanReturnPreparation loanRetPrep = new LoanReturnPreparation();
                        loanRetPrep.initialize();
                        loanRetPrep.setReceivedBy(agent);
                        loanRetPrep.setModifiedByAgent(Agent.getUserAgent());
                        loanRetPrep.setReturnedDate(Calendar.getInstance());
                        loanRetPrep.setQuantity(loanRetInfo.getQuantity());
                        loanRetPrep.setRemarks(loanRetInfo.getRemarks());
                        
                        if (loanRetInfo.isResolved() != null)
                        {
                            loanRetInfo.getLoanPreparation().setIsResolved(loanRetInfo.isResolved());
                            session.save(loanRetInfo.getLoanPreparation());
                        }
                        loanPrep.addReference(loanRetPrep, "loanReturnPreparations");
                        
                        session.save(loanRetPrep);
                        session.saveOrUpdate(loanPrep);
                    }
                    
                    session.commit();
                    
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                    // Error Dialog
                    
                } finally
                {
                    if (session != null)
                    {
                        session.close();
                    }
                }
                return null;
            }

            //Runs on the event-dispatching thread.
            public void finished()
            {
                JStatusBar statusBar = UIRegistry.getStatusBar();
                statusBar.setIndeterminate(false);
                statusBar.setText("");
            }
        };
        worker.start();
    }
    
    /**
     * Starts process to return a loan
     * @param doPartial true means show dialog and do partial, false means just return the loan
     */
    protected void returnLoan()
    {
        Loan         loan    = null;
        MultiView    mv      = null;
        SubPaneIFace subPane = SubPaneMgr.getInstance().getCurrentSubPane();
        if (subPane != null)
        {
            mv = subPane.getMultiView();
            if (mv != null)
            {
                if (mv.getData() instanceof Loan)
                {
                    loan = (Loan)mv.getData();
                }
            }
        }
        
        if (mv != null && loan != null)
        {
            LoanReturnDlg dlg = new LoanReturnDlg(loan);
            if (dlg.createUI())
            {
                dlg.setModal(true);
                UIHelper.centerAndShow(dlg);
                dlg.dispose();
                
                if (!dlg.isCancelled())
                {
                    FormViewObj fvp = mv.getCurrentViewAsFormViewObj();
                    fvp.setHasNewData(true);
                    //fvp.getValidator().validateForm();
                    fvp.getValidator().setHasChanged(true);
                    fvp.validationWasOK(fvp.getValidator().getState() == UIValidatable.ErrorType.Valid);
                   
                    List<LoanReturnInfo> returns = dlg.getLoanReturnInfo();
                    if (returns.size() > 0)
                    {
                        doReturnLoan(dlg.getAgent(), returns);
                    }
                }
            }
            
        } else
        {
            // XXX Show some kind of error dialog
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#subPaneRemoved(edu.ku.brc.af.core.SubPaneIFace)
     */
    public void subPaneRemoved(final SubPaneIFace subPane)
    {
        super.subPaneRemoved(subPane);
        
        if (subPane == starterPane)
        {
            starterPane = null;
        }
    }
    
    //-------------------------------------------------------
    // CommandListener Interface
    //-------------------------------------------------------
    /**
     * 
     */
    protected void prefsChanged(final CommandAction cmdAction)
    {
        boolean hasGifts = false;
        AppPreferences remotePrefs = (AppPreferences)cmdAction.getData();
        if (remotePrefs == AppPreferences.getRemote())
        {
            String ds = Discipline.getCurrentDiscipline().getDiscipline();
            isUsingInteractions = remotePrefs.getBoolean(IS_USING_INTERACTIONS_PREFNAME+ds, true);
            
            boolean isDoingGiftsNew        = remotePrefs.getBoolean(IS_DOING_GIFT_PREFNAME+ds, true);
            boolean isDoingExchangesNew    = remotePrefs.getBoolean(IS_DOING_EXCH_PREFNAME+ds, Discipline.isCurrentDiscipline(DisciplineType.STD_DISCIPLINES.botany));            
            
            JToolBar toolBar = (JToolBar)UIRegistry.get(UIRegistry.TOOLBAR);
            if (!isUsingInteractions)
            {
                indexOfTBB = toolBar.getComponentIndex(toolBarBtn);
                TaskMgr.removeToolbarBtn(toolBarBtn);
                toolBar.validate();
                toolBar.repaint();
                
            } else
            {
                int curInx = toolBar.getComponentIndex(toolBarBtn);
                if (curInx == -1)
                {
                    int inx = indexOfTBB != -1 ? indexOfTBB : 4;
                    TaskMgr.addToolbarBtn(toolBarBtn, inx);
                    toolBar.validate();
                    toolBar.repaint();
                }
            }
            
            if (this.isDoingGifts && !isDoingGiftsNew)
            {
                actionsNavBox.remove(giftsNavBtn);

            } else if (!this.isDoingGifts && isDoingGiftsNew)
            {
                createGiftNavBtn();
                actionsNavBox.remove(giftsNavBtn);
                actionsNavBox.insert(giftsNavBtn, true, false, 1);
                
                hasGifts = true;
                
            } else if (this.isDoingGifts)
            {
                hasGifts = true;
            }
            
            if (this.isDoingExchanges && !isDoingExchangesNew)
            {
                actionsNavBox.remove(exchgNavBtn);

            } else if (!this.isDoingExchanges && isDoingExchangesNew)
            {
                createExchangeNavBtn();
                actionsNavBox.remove(exchgNavBtn);
                actionsNavBox.insert(exchgNavBtn, true, false, hasGifts ? 2 : 1);
            }
            
            // I hate that I have to do this, why?
            actionsNavBox.validate();
            actionsNavBox.invalidate();
            actionsNavBox.doLayout();
            actionsNavBox.repaint();
            
            isDoingExchanges = isDoingExchangesNew;
            isDoingGifts     = isDoingGiftsNew;
        }
    }
    
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
                if (dstObj instanceof RecordSet)
                {
                    RecordSet rs = (RecordSet)dstObj;
                    
                    DBTableInfo ti = DBTableIdMgr.getInstance().getInfoById(rs.getDbTableId());
                    
                    super.createFormPanel("XXX", "view", (RecordSetIFace)rs, IconManager.getIcon(ti.getShortClassName(), IconManager.IconSize.Std16));
                }
            }
        }
    }
    
    /**
     * Processes all Commands of type DB_CMD_TYPE.
     * @param cmdAction the command to be processed
     */
    protected void processDatabaseCommands(final CommandAction cmdAction)
    {
        if (cmdAction.getData() instanceof InfoRequest)
        {
            if (cmdAction.isAction(INSERT_CMD_ACT) || cmdAction.isAction(UPDATE_CMD_ACT))
            {
                // Create Specify Application
                SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        CommandDispatcher.dispatch(new CommandAction(INTERACTIONS, CREATE_MAILMSG, SubPaneMgr.getInstance().getCurrentSubPane()));
                    }
                });
                
                if (cmdAction.isAction(INSERT_CMD_ACT))
                {
                    InfoRequest infoRequest = (InfoRequest)cmdAction.getData();
                    NavBoxItemIFace nbi = addNavBoxItem(infoRequestNavBox, infoRequest.getIdentityTitle(), INTERACTIONS, new CommandAction(INTERACTIONS, DELETE_CMD_ACT, infoRequest), infoRequest);
                    setUpDraggable(nbi, new DataFlavor[]{Trash.TRASH_FLAVOR, INFOREQUEST_FLAVOR}, new NavBoxAction("", ""));
                }
            }
        }
    }
    
    /**
     * Processes all Commands of type INTERACTIONS.
     * @param cmdAction the command to be processed
     */
    protected void processInteractionsCommands(final CommandAction cmdAction)
    {
        if (cmdAction.isAction(CREATE_MAILMSG))
        {
            createAndSendEMail();
                
        } else if (cmdAction.isAction(PRINT_LOAN))
        {
            if (cmdAction.getData() instanceof RecordSetIFace)
            {
                if (((RecordSetIFace)cmdAction.getData()).getDbTableId() != cmdAction.getTableId())
                {
                    JOptionPane.showMessageDialog(UIRegistry.getTopWindow(), 
                                                  getResourceString("ERROR_RECORDSET_TABLEID"), 
                                                  getResourceString("Error"), 
                                                  JOptionPane.ERROR_MESSAGE);
                }

                printLoan(null, cmdAction.getData());
                
            } if (cmdAction.getData() instanceof CommandAction)
            {
                String tableIDStr = cmdAction.getPropertyAsString("tableid");
                if (StringUtils.isNotEmpty(tableIDStr) && StringUtils.isNumeric(tableIDStr))
                {
                    RecordSetIFace recordSet = askForRecordSet(Integer.parseInt(tableIDStr));
                    if (recordSet != null)
                    {
                        printLoan(cmdAction.getPropertyAsString("file"), recordSet);
                    }
                }
            }
            
        } else if (cmdAction.isAction("CreateInfoRequest") && cmdAction.getData() instanceof RecordSet)
        {
            Object data = cmdAction.getData();
            if (data instanceof RecordSet)
            {
                createInfoRequest((RecordSetIFace)data);
            }
            
        } else if (cmdAction.isAction("ReturnLoan"))
        {
            returnLoan();
            
        } else if (cmdAction.isAction(DELETE_CMD_ACT) && cmdAction.getData() instanceof InfoRequest)
        {
            InfoRequest inforRequest = (InfoRequest)cmdAction.getData();
            deleteInfoRequest(inforRequest);
            deleteInfoRequestFromUI(null, inforRequest);
            
        } else 
        {
            Object cmdData = cmdAction.getData();
            if (cmdData == null)
            {
                //LabelsTask.askForRecordSet(tableId)   
            }
            
            // These all assume there needs to be a recordsset
            if (cmdData != null)
            {
                if (cmdData instanceof RecordSetIFace)
                {
                    RecordSetIFace rs = (RecordSetIFace)cmdData;
                    
                    if (rs.getDbTableId() == colObjTableId || rs.getDbTableId() == infoRequestTableId)
                    {
                        if (cmdAction.isAction(NEW_LOAN))
                        {    
                            createNewLoan(null, rs);
                                
                        } else if (cmdAction.isAction(InfoRequestName))
                        {
                            createInfoRequest(rs);      
                        }
                    } else
                    {
                        log.error("Dropped wrong table type.");
                        // this shouldn't happen
                    }
                } else if (cmdData instanceof InfoRequest)
                {
                    createNewLoan((InfoRequest)cmdData);
                    
                } else if (cmdData instanceof CommandAction)
                {
                    if (cmdAction.isAction(InfoRequestName))
                    {
                        createInfoRequest(null);   
                        
                    } else if (cmdAction.isAction(NEW_LOAN))
                    {
                        createNewLoan(null, null);
                    }
                }
            }
        }
    }

    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#doCommand(edu.ku.brc.ui.CommandAction)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void doCommand(final CommandAction cmdAction)
    {
        //log.debug("Processing Command ["+cmdAction.getType()+"]["+cmdAction.getAction()+"]");
        
        super.doCommand(cmdAction);
        
        if (cmdAction.isConsumed())
        {
            return;
        }
        
        //log.debug(cmdAction);
        
        if (cmdAction.isType(DB_CMD_TYPE))
        {
            processDatabaseCommands(cmdAction);

        } else if (cmdAction.isType(DataEntryTask.DATA_ENTRY))
        {
            if (cmdAction.isAction("SaveBeforeSetData"))
            {
                checkToPrintLoan(cmdAction);
            }
            
        } else if (cmdAction.isType(INTERACTIONS))
        {
            processInteractionsCommands(cmdAction);
            
        } else if (cmdAction.isType(PreferencesDlg.PREFERENCES))
        {
            prefsChanged(cmdAction);
            
        } else if (cmdAction.isType(RecordSetTask.RECORD_SET))
        {
            processRecordSetCommands(cmdAction);
            
        } else if (cmdAction.isType(APP_CMD_TYPE) && cmdAction.isAction(APP_RESTART_ACT))
        {
            //setUpCachedPrefs();
            // Now simulate the prefs changing to adjust the UI
            prefsChanged(new CommandAction(PreferencesDlg.PREFERENCES, "Change", AppPreferences.getRemote()));
        }
    }
}

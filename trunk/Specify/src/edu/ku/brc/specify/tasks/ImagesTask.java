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
package edu.ku.brc.specify.tasks;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JToolBar;

import org.apache.commons.io.FilenameUtils;

import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.MenuItemDesc;
import edu.ku.brc.af.core.NavBox;
import edu.ku.brc.af.core.NavBoxAction;
import edu.ku.brc.af.core.NavBoxIFace;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.TaskMgr;
import edu.ku.brc.af.core.ToolBarItemDesc;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.db.DBTableInfo;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.PreferencesDlg;
import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.specify.datamodel.AttachmentImageAttribute;
import edu.ku.brc.specify.datamodel.Borrow;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.ConservDescription;
import edu.ku.brc.specify.datamodel.ConservEvent;
import edu.ku.brc.specify.datamodel.DNASequence;
import edu.ku.brc.specify.datamodel.DNASequencingRun;
import edu.ku.brc.specify.datamodel.FieldNotebook;
import edu.ku.brc.specify.datamodel.FieldNotebookPage;
import edu.ku.brc.specify.datamodel.FieldNotebookPageSet;
import edu.ku.brc.specify.datamodel.Gift;
import edu.ku.brc.specify.datamodel.Loan;
import edu.ku.brc.specify.datamodel.Locality;
import edu.ku.brc.specify.datamodel.Permit;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.specify.datamodel.ReferenceWork;
import edu.ku.brc.specify.datamodel.RepositoryAgreement;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.tasks.subpane.images.ImagesPane;
import edu.ku.brc.specify.utilapps.morphbank.BatchAttachFiles;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.RolloverCommand;
import edu.ku.brc.ui.ToolBarDropDownBtn;
import edu.ku.brc.ui.UIRegistry;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Jan 25, 2012
 *
 */
public class ImagesTask extends BaseTask
{
    private static final String  ON_TASKBAR        = "ImagesTask.OnTaskbar";
    private static final String  IMAGES            = "IMAGES";
    private static final String  IMAGES_SEARCH     = "IMAGES.SEARCH";
    //private static final String  IMAGES_TITLE     = "IMAGES_TITLE";
    //private static final String  IMAGES_SECURITY  = "IMAGESEDIT";
    
    // Data Members
    protected ImagesPane              imagesPane       = null;
    protected NavBox                  actionNavBox     = null;
    protected NavBox                  viewsNavBox      = null;
    protected NavBox                  adminNavBox       = null;

    protected Vector<NavBoxIFace>     extendedNavBoxes = new Vector<NavBoxIFace>();
    protected ToolBarDropDownBtn      toolBarBtn       = null;
    
    /**
     * 
     */
    public ImagesTask()
    {
        super(getResourceString(IMAGES), "Images");
        this.iconName = "image";
        CommandDispatcher.register(PreferencesDlg.PREFERENCES, this);
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
            
            extendedNavBoxes.clear();
            
            // Actions
            RolloverCommand showAllBtn = (RolloverCommand)addNavBoxItem(actionNavBox, "Show All Images", "image", null, null);
            RolloverCommand uploadImagesBtn = (RolloverCommand)addNavBoxItem(actionNavBox, "Import Images", "image", null, null);
            RolloverCommand uploadIndexBtn  = (RolloverCommand)addNavBoxItem(actionNavBox, "Import Image Index", "image", null, null);
            //RolloverCommand uploadOCRBtn    = (RolloverCommand)addNavBoxItem(actionNavBox, "Import OCR Data", "image", null, null);
            //addNavBoxItem(actionNavBox, "Import OCR Data", "network_node_del", null, null);
            //RolloverCommand showStatusBtn = (RolloverCommand)addNavBoxItem(actionNavBox, "Status",   "InfoIcon", null, null);
            
            //DBTableInfo coTI  = DBTableIdMgr.getInstance().getInfoById(CollectionObject.getClassTableId());
            //DBTableInfo geoTI = DBTableIdMgr.getInstance().getInfoById(Geography.getClassTableId());
            //DBTableInfo taxTI = DBTableIdMgr.getInstance().getInfoById(Taxon.getClassTableId());

            // Query Panels
            //RolloverCommand showAllBtn = (RolloverCommand)addNavBoxItem(viewsNavBox, "Show All Images", "image", null, null);
            //RolloverCommand coBtn      = (RolloverCommand)addNavBoxItem(viewsNavBox, coTI.getTitle(), "image", null, null);
            //coBtn.setIcon(coTI.getIcon(IconManager.STD_ICON_SIZE));
            
            //RolloverCommand configOutgoingBtn = (RolloverCommand)addNavBoxItem(viewsNavBox, "Config Outgoing", "notify", null, null);
            //RolloverCommand notiLogBtn        = (RolloverCommand)addNavBoxItem(viewsNavBox, "Review Notifications", "logfile", null, null);
            
            // Admin
            /* RolloverCommand connectBtn    = (RolloverCommand)addNavBoxItem(adminNavBox, "Connect", "connect_net", null, null);
            RolloverCommand disconnectBtn = (RolloverCommand)addNavBoxItem(adminNavBox, "Disconnect", "disconnect_net", null, null);
            RolloverCommand configNetBtn  = (RolloverCommand)addNavBoxItem(adminNavBox, "Config Connection",   "SystemSetup", null, null);

            connectBtn.setEnabled(false);
            */
            
            navBoxes.add(actionNavBox);
            //navBoxes.add(viewsNavBox);
            //navBoxes.add(adminNavBox);
            
            int[] attachmentTableIDs = {AttachmentImageAttribute.getClassTableId(),
                    Borrow.getClassTableId(),
                    CollectingEvent.getClassTableId(),
                    CollectionObject.getClassTableId(),
                    ConservDescription.getClassTableId(),
                    ConservEvent.getClassTableId(),
                    DNASequence.getClassTableId(),
                    DNASequencingRun.getClassTableId(),
                    FieldNotebook.getClassTableId(),
                    FieldNotebookPage.getClassTableId(),
                    FieldNotebookPageSet.getClassTableId(),
                    Gift.getClassTableId(),
                    Loan.getClassTableId(),
                    Locality.getClassTableId(),
                    Permit.getClassTableId(),
                    Preparation.getClassTableId(),
                    ReferenceWork.getClassTableId(),
                    RepositoryAgreement.getClassTableId()};
            for (int tblId : attachmentTableIDs)
            {
                DBTableInfo   tblInfo   = DBTableIdMgr.getInstance().getInfoById(1);
                CommandAction cmdAction = new CommandAction(IMAGES, IMAGES_SEARCH);
                cmdAction.setProperty("id",    Integer.toString(tblId));
                
                cmdAction.setProperty(NavBoxAction.ORGINATING_TASK, this);
                String serviceName = String.format("Image Search %s", tblInfo.getTitle());
                ContextMgr.registerService(10, serviceName, tblId, cmdAction, this, "image", tblInfo.getTitle(), true); // the Name gets Hashed
            }

            
            uploadImagesBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    uploadImages(false);
                }
            });
            
            uploadIndexBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    uploadIndexFile();
                }
            });
            
            /*uploadOCRBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    uploadOCRData(false);
                }
            });*/
            
//            coBtn.addActionListener(new ActionListener()
//            {
//                @Override
//                public void actionPerformed(ActionEvent e)
//                {
//                    if (starterPane != null) removeSubPaneFromMgr(starterPane);
//                    starterPane = imagesPane = new ImagesPane(getResourceString(IMAGES), ImagesTask.this, false); 
//                    addSubPaneToMgr(starterPane);
//                }
//            });
            
            showAllBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (starterPane != null) 
                    {
                        removeSubPaneFromMgr(starterPane);
                    }
                    starterPane = imagesPane = new ImagesPane(getResourceString(IMAGES), ImagesTask.this, true); 
                    addSubPaneToMgr(starterPane);
                }
            });
        }
        isShowDefault = true;
    }
    
    /**
     * @param doCreateNewRecs
     */
    private void uploadImages(final boolean doCreateNewRecs)
    {
        JFileChooser chooser = new JFileChooser("Choose a Directory"); // I18N
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        String path      = null;
        int    returnVal = chooser.showOpenDialog(UIRegistry.getTopWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getAbsolutePath();
            try
            {
                BatchAttachFiles batchFile = new BatchAttachFiles(CollectionObject.class, "CatalogNumber", new File(path));
                batchFile.attachFilesByFieldName();
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * 
     */
    private void uploadIndexFile()
    {
        JFileChooser chooser = new JFileChooser("Choose a Index File"); // I18N
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        File indexFile      = null;
        int    returnVal = chooser.showOpenDialog(UIRegistry.getTopWindow());
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            indexFile = chooser.getSelectedFile();
            try
            {
                String path = FilenameUtils.getFullPath(indexFile.getAbsolutePath());
                BatchAttachFiles batchFile = new BatchAttachFiles(CollectionObject.class, "CatalogNumber", new File(path));
                batchFile.attachFileFromIndexFile(indexFile);
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * @param rs
     */
    private void searchForImages(final RecordSetIFace rs)
    {
        if (starterPane != null)
        {
            removeSubPaneFromMgr(starterPane);
        }
        starterPane = imagesPane = new ImagesPane(getResourceString(IMAGES), ImagesTask.this, rs); 
        addSubPaneToMgr(starterPane);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#preInitialize()
     */
    @Override
    public void preInitialize()
    {
        CommandDispatcher.register(IMAGES, this);

        // Create and add the Actions NavBox first so it is at the top at the top
        actionNavBox = new NavBox(getResourceString("Actions"));
        
        //viewsNavBox = new NavBox("Image Search");
        //adminNavBox  = new NavBox("Admin");
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    public SubPaneIFace getStarterPane()
    {
        return starterPane = StartUpTask.createFullImageSplashPanel(title, this);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#subPaneRemoved(edu.ku.brc.af.core.SubPaneIFace)
     */
    @Override
    public void subPaneRemoved(final SubPaneIFace subPane)
    {
        super.subPaneRemoved(subPane);
        
        if (subPane == starterPane)
        {
            starterPane = imagesPane = null;
        }
    }

    //-------------------------------------------------------
    // Taskable
    //-------------------------------------------------------

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
//        
//        RecordSetTask     rsTask = (RecordSetTask)ContextMgr.getTaskByClass(RecordSetTask.class);
//        List<NavBoxIFace> nbs    = rsTask.getNavBoxes();
//        if (nbs != null)
//        {
//            extendedNavBoxes.addAll(nbs);
//        }

        return extendedNavBoxes;
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.Taskable#getToolBarItems()
     */
    @Override
    public List<ToolBarItemDesc> getToolBarItems()
    {
        String label    = getResourceString(IMAGES);
        String hint     = getResourceString(IMAGES);
        toolBarBtn      = createToolbarButton(label, iconName, hint);
        
        toolbarItems = new Vector<ToolBarItemDesc>();
        if (AppPreferences.getRemote().getBoolean(ON_TASKBAR, false))
        {
            toolbarItems.add(new ToolBarItemDesc(toolBarBtn));
        }
        return toolbarItems;
    }
    
    //-------------------------------------------------------
    // SecurityOption Interface
    //-------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#getAdditionalSecurityOptions()
     */
    /*@Override
    public List<SecurityOptionIFace> getAdditionalSecurityOptions()
    {
        List<SecurityOptionIFace> list = new ArrayList<SecurityOptionIFace>();
        
        SecurityOption secOpt = new SecurityOption(IMAGES_SECURITY, 
                                                    getResourceString("IMAGES_TITLE"), 
                                                    securityPrefix,
                                                    new BasicPermisionPanel(IMAGES_TITLE, 
                                                                            "IMAGES_VIEW", 
                                                                            "IMAGES_EDIT"));
        addPerms(secOpt, new boolean[][] 
                   {{true, true, true, false},
                   {false, false, false, false},
                   {false, false, false, false},
                   {false, false, false, false}});
        
        list.add(secOpt);

        return list;
    }*/
    
    //-------------------------------------------------------
    // BaseTask
    //-------------------------------------------------------
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#getMenuItems()
     */
    @Override
    public List<MenuItemDesc> getMenuItems()
    {
        menuItems = new Vector<MenuItemDesc>();
        /*
        final String DATA_MENU = "Specify.DATA_MENU";
        
        SecurityMgr secMgr = SecurityMgr.getInstance();
        
        MenuItemDesc mid;
        JMenuItem mi;
        String    menuDesc = getResourceString(IMAGES_MENU);

        String securityName = buildTaskPermissionName(IMAGES_SECURITY);
        if (!AppContextMgr.isSecurityOn() || 
            (secMgr.getPermission(securityName) != null && 
             !secMgr.getPermission(securityName).hasNoPerm()))
        {
            mi       = UIHelper.createLocalizedMenuItem(IMAGES_MENU, IMAGES_MNU, IMAGES_TITLE, true, null); 
            mi.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            LifeMapperTask.this.requestContext();
                        }
                    });
                }
            });
            mid = new MenuItemDesc(mi, DATA_MENU);
            mid.setPosition(MenuItemDesc.Position.Bottom, menuDesc);
            menuItems.add(mid);
        }
        */
        return menuItems;
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
        if (cmdAction.isAction("Display"))
        {
            if (cmdAction.getData() instanceof RecordSetIFace)
            {
                RecordSetIFace rs = (RecordSetIFace)cmdAction.getData();
                if (rs.getDbTableId() == Taxon.getClassTableId())
                {
                    // Loop through all the RS ids and get the names
                    //BasicSQLUtils.querySingleObj("SELECT)
                    //((LifeMapperPane)getStarterPane()).doSearchGenusSpecies(searchStr)
                }
            } /*else if (cmdAction.getData() instanceof Pair<?,?>)
            {
                @SuppressWarnings("unchecked")
                Pair<Taxon, RecordSet> p = (Pair<Taxon, RecordSet>)cmdAction.getData();
                getStarterPane(); // make sure it is loaded
                imagesPane.resetWWPanel();
                imagesPane.setDoResetWWPanel(false);
                imagesPane.doSearchGenusSpecies(p.first.getFullName(), true);
                requestContext();
            }*/
        } if (cmdAction.isAction(IMAGES_SEARCH))
        {
            if (cmdAction.getData() instanceof RecordSetIFace)
            {
                RecordSetIFace rs = (RecordSetIFace)cmdAction.getData();
                searchForImages(rs);
            } 
        }
    }
    
    /**
     * @param cmdAction
     */
    protected void prefsChanged(final CommandAction cmdAction)
    {
        AppPreferences appPrefs = (AppPreferences) cmdAction.getData();

        if (appPrefs == AppPreferences.getRemote())
        {
            // Note: The event send with the name of pref from the form
            // not the name that was saved. So we don't need to append the discipline name on the
            // end
            Object value = cmdAction.getProperties().get(ON_TASKBAR);
            if (value != null && value instanceof Boolean)
            {
                /*
                 * This doesn't work because it isn't added to the Toolbar correctly
                 */
                JToolBar toolBar = (JToolBar) UIRegistry.get(UIRegistry.TOOLBAR);

                Boolean isChecked = (Boolean) value;
                if (isChecked)
                {
                    TaskMgr.addToolbarBtn(toolBarBtn, toolBar.getComponentCount() - 1);
                } else
                {
                    TaskMgr.removeToolbarBtn(toolBarBtn);
                }
                toolBar.validate();
                toolBar.repaint();

            }
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.CommandListener#doCommand(edu.ku.brc.specify.ui.CommandAction)
     */
    @Override
    public void doCommand(CommandAction cmdAction)
    {
        super.doCommand(cmdAction);
        
        if (cmdAction.isType(IMAGES))
        {
            processRecordSetCommands(cmdAction);
            
        } else if (cmdAction.isType(PreferencesDlg.PREFERENCES))
        {
            prefsChanged(cmdAction);
        }
    }

}

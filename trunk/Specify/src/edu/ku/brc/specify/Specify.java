/* Copyright (C) 2009, University of Kansas Center for Research
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
package edu.ku.brc.specify;

import static edu.ku.brc.ui.UIHelper.createLabel;
import static edu.ku.brc.ui.UIRegistry.getAction;
import static edu.ku.brc.ui.UIRegistry.getLocalizedMessage;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.prefs.BackingStoreException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.install4j.api.launcher.ApplicationLauncher;
import com.install4j.api.update.ApplicationDisplayMode;
import com.install4j.api.update.UpdateChecker;
import com.install4j.api.update.UpdateDescriptor;
import com.install4j.api.update.UpdateDescriptorEntry;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;

import edu.ku.brc.af.auth.SecurityMgr;
import edu.ku.brc.af.auth.UserAndMasterPasswordMgr;
import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.FrameworkAppIFace;
import edu.ku.brc.af.core.GenericLSIDGeneratorFactory;
import edu.ku.brc.af.core.MacOSAppHandler;
import edu.ku.brc.af.core.MainPanel;
import edu.ku.brc.af.core.RecordSetFactory;
import edu.ku.brc.af.core.SchemaI18NService;
import edu.ku.brc.af.core.SpecialMsgNotifier;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.TaskMgr;
import edu.ku.brc.af.core.Taskable;
import edu.ku.brc.af.core.UsageTracker;
import edu.ku.brc.af.core.db.BackupServiceFactory;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.af.prefs.AppPrefsEditor;
import edu.ku.brc.af.prefs.PreferencesDlg;
import edu.ku.brc.af.tasks.BaseTask;
import edu.ku.brc.af.tasks.StatsTrackerTask;
import edu.ku.brc.af.tasks.subpane.FormPane;
import edu.ku.brc.af.ui.ProcessListUtil;
import edu.ku.brc.af.ui.db.DatabaseLoginListener;
import edu.ku.brc.af.ui.db.DatabaseLoginPanel;
import edu.ku.brc.af.ui.forms.FormHelper;
import edu.ku.brc.af.ui.forms.FormViewObj;
import edu.ku.brc.af.ui.forms.MultiView;
import edu.ku.brc.af.ui.forms.ResultSetController;
import edu.ku.brc.af.ui.forms.ViewFactory;
import edu.ku.brc.af.ui.forms.formatters.DataObjFieldFormatMgr;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.af.ui.forms.validation.TypeSearchForQueryFactory;
import edu.ku.brc.af.ui.forms.validation.ValComboBoxFromQuery;
import edu.ku.brc.af.ui.weblink.WebLinkMgr;
import edu.ku.brc.dbsupport.CustomQueryFactory;
import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.DBMSUserMgr;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.dbsupport.QueryExecutor;
import edu.ku.brc.dbsupport.SchemaUpdateService;
import edu.ku.brc.exceptions.ExceptionTracker;
import edu.ku.brc.helpers.Encryption;
import edu.ku.brc.helpers.ProxyHelper;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.services.gpx.GPXPanel;
import edu.ku.brc.specify.config.CollectingEventsAndAttrsMaint;
import edu.ku.brc.specify.config.DebugLoggerDialog;
import edu.ku.brc.specify.config.DisciplineType;
import edu.ku.brc.specify.config.FeedBackDlg;
import edu.ku.brc.specify.config.FixDBAfterLogin;
import edu.ku.brc.specify.config.LoggerDialog;
import edu.ku.brc.specify.config.SpecifyAppContextMgr;
import edu.ku.brc.specify.config.SpecifyAppPrefs;
import edu.ku.brc.specify.config.init.DataBuilder;
import edu.ku.brc.specify.config.init.RegisterSpecify;
import edu.ku.brc.specify.config.init.SpecifyDBSetupWizardFrame;
import edu.ku.brc.specify.conversion.BasicSQLUtils;
import edu.ku.brc.specify.datamodel.AccessionAttachment;
import edu.ku.brc.specify.datamodel.AgentAttachment;
import edu.ku.brc.specify.datamodel.Attachment;
import edu.ku.brc.specify.datamodel.CollectingEventAttachment;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.CollectionObjectAttachment;
import edu.ku.brc.specify.datamodel.ConservDescriptionAttachment;
import edu.ku.brc.specify.datamodel.ConservEventAttachment;
import edu.ku.brc.specify.datamodel.DNASequencingRunAttachment;
import edu.ku.brc.specify.datamodel.Determination;
import edu.ku.brc.specify.datamodel.Discipline;
import edu.ku.brc.specify.datamodel.Division;
import edu.ku.brc.specify.datamodel.FieldNotebookAttachment;
import edu.ku.brc.specify.datamodel.FieldNotebookPageAttachment;
import edu.ku.brc.specify.datamodel.FieldNotebookPageSetAttachment;
import edu.ku.brc.specify.datamodel.Institution;
import edu.ku.brc.specify.datamodel.LoanAttachment;
import edu.ku.brc.specify.datamodel.LocalityAttachment;
import edu.ku.brc.specify.datamodel.PermitAttachment;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.specify.datamodel.PreparationAttachment;
import edu.ku.brc.specify.datamodel.RepositoryAgreementAttachment;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.Storage;
import edu.ku.brc.specify.datamodel.Taxon;
import edu.ku.brc.specify.datamodel.TaxonAttachment;
import edu.ku.brc.specify.prefs.SystemPrefs;
import edu.ku.brc.specify.tasks.subpane.JasperReportsCache;
import edu.ku.brc.specify.tasks.subpane.wb.wbuploader.Uploader;
import edu.ku.brc.specify.ui.AppBase;
import edu.ku.brc.specify.ui.HelpMgr;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.CommandListener;
import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.CustomFrame;
import edu.ku.brc.ui.DefaultClassActionHandler;
import edu.ku.brc.ui.GraphicsUtils;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.JStatusBar;
import edu.ku.brc.ui.JTiledToolbar;
import edu.ku.brc.ui.RolloverCommand;
import edu.ku.brc.ui.ToolbarLayoutManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.VerticalSeparator;
import edu.ku.brc.ui.IconManager.IconSize;
import edu.ku.brc.ui.dnd.GhostGlassPane;
import edu.ku.brc.ui.skin.SkinItem;
import edu.ku.brc.ui.skin.SkinsMgr;
import edu.ku.brc.util.AttachmentManagerIface;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.CacheManager;
import edu.ku.brc.util.FileCache;
import edu.ku.brc.util.FileStoreAttachmentManager;
import edu.ku.brc.util.MemoryWarningSystem;
import edu.ku.brc.util.Pair;
import edu.ku.brc.util.thumbnails.Thumbnailer;
/**
 * Specify Main Application Class
 *
 * @code_status Beta
 * 
 * @author rods
 */
@SuppressWarnings("serial") //$NON-NLS-1$
public class Specify extends JPanel implements DatabaseLoginListener, CommandListener, FrameworkAppIFace
{

    private static final Logger  log                = Logger.getLogger(Specify.class);
    
    public static final boolean IS_DEVELOPMENT       = true;
    private static final String sendStatsPrefName    = "usage_tracking.send_stats";
    private static final String sendISAStatsPrefName = "usage_tracking.send_isa_stats";
    private static final String ATTACHMENT_PATH_PREF = "attachment.path";
    
    // The preferred size of the demo
    private static final int    PREFERRED_WIDTH    = 1024;
    private static final int    PREFERRED_HEIGHT   = 768;

    private static Specify      specifyApp         = null; // needed for ActionListeners etc.

    // Status Bar
    private JStatusBar          statusField        = null;
    private JMenuBar            menuBar            = null;
    private JFrame              topFrame           = null;
    private MainPanel           mainPanel          = null;
    private JMenuItem           changeCollectionMenuItem = null;
    private JLabel              appIcon            = null;

    protected boolean           hasChanged         = false;

    protected String             currentDatabaseName = null;
    protected DatabaseLoginPanel dbLoginPanel        = null;
    protected String             databaseName        = null;
    protected String             userName            = null;
    //DatabaseLoginPanel dbLoginPanel
    protected GhostGlassPane     glassPane;

    private boolean              isWorkbenchOnly     = false;
    
    private String               appName             = "Specify"; //$NON-NLS-1$
    private String               appVersion          = "6.0"; //$NON-NLS-1$
    private String               appBuildVersion     = "(Unknown)"; //$NON-NLS-1$
    
    protected static CacheManager cacheManager        = new CacheManager();

    /**
     * Constructor.
     */
    public Specify()
    {
        isWorkbenchOnly = UIRegistry.isMobile();
    }
    
    /**
     * The very very first step in initializing Specify. 
     */
    protected void preStartUp()
    {
        //UIHelper.attachUnhandledException();
        
        // we simply need to create this class, not use it
        new MacOSAppHandler(this);

        // Name factories
        setUpSystemProperties();
    }
    
    /**
     * This is used to pre-initialize any preferences. This is needed because a user may
     * try to edit the preferences before they eve get set by the classes that use them.
     */
    protected void preInitializePrefs()
    {
        AppPreferences remotePrefs = AppPreferences.getRemote();
       
        // Set the default values
        int i = 0;
        String[] classNames = {"Taxon", "Geography", "LithoStrat", "GeologicTimePeriod", "Storage"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        int[]    ranks      = {140,     200,         200,           200,                 200};
        for (String className : classNames)
        {
            remotePrefs.getColor("Treeeditor.TreeColColor1."+className, new Color(202, 238, 255), true); //$NON-NLS-1$
            remotePrefs.getColor("Treeeditor.TreeColColor2."+className, new Color(151, 221, 255), true); //$NON-NLS-1$
            remotePrefs.getColor("Treeeditor.SynonymyColor."+className, Color.BLUE, true); //$NON-NLS-1$
            remotePrefs.getInt("TreeEditor.Rank.Threshold."+className, ranks[i], true); //$NON-NLS-1$
            i++;
        }
        
        remotePrefs.getBoolean("TreeEditor.RestoreTreeExpansionState", true, true); //$NON-NLS-1$
        remotePrefs.getBoolean("google.earth.useorigheaders", true, true); //$NON-NLS-1$
        remotePrefs.getInt("SubPaneMgr.MaxPanes", 12, true); //$NON-NLS-1$
        
        String ds = AppContextMgr.getInstance().getClassObject(Discipline.class).getType();
        remotePrefs.getBoolean("Interactions.Using.Interactions."+ds, true, true); //$NON-NLS-1$
        remotePrefs.getBoolean("Interactions.Doing.Gifts."+ds, true, true); //$NON-NLS-1$
        remotePrefs.getBoolean("Interactions.Doing.Exchanges."+ds, Discipline.isCurrentDiscipline(DisciplineType.STD_DISCIPLINES.botany), true); //$NON-NLS-1$
        remotePrefs.getBoolean("Agent.Use.Variants."+ds, Discipline.isCurrentDiscipline(DisciplineType.STD_DISCIPLINES.botany), true); //$NON-NLS-1$
        
        remotePrefs.load(); // Loads prefs from the database
        
        /*try
        {
            remotePrefs.flush();
            
        } catch (BackingStoreException ex) {}
        */
    }
    
    
    /**
     * Check for and kills and existing embedded MySQl processes.
     */
    public static void checkForSpecifyAppsRunning()
    {
        List<Integer> ids = ProcessListUtil.getProcessIdWithText("exe4j.moduleName", "specify.jar", "SpiReport");
        if (ids.size() > 1)
        {
            UIRegistry.showLocalizedMsg("WARNING", "Specify.TOO_MANY_SP");
            System.exit(0);
        }
    }
    
    /**
     * Start up without the initializer, assumes there is at least one database to connect to.
     */
    public void startUp()
    {
    	log.debug("StartUp..."); //$NON-NLS-1$
    	
    	if (UIHelper.isLinux())
    	{
    	    checkForSpecifyAppsRunning();
    	}
    	
    	if (UIRegistry.isEmbedded())
    	{
    	    SpecifyDBSetupWizardFrame.checkForMySQLProcesses();
    	}
    	
        // Adjust Default Swing UI Default Resources (Color, Fonts, etc) per Platform
        UIHelper.adjustUIDefaults();
        
        setupDefaultFonts();
        
        if (!UIRegistry.doesAppversionsMatch())
        {
            System.exit(0);
        }
        
        // Attachment related helpers
        Thumbnailer thumb = new Thumbnailer();
        File thumbnailDir = null;
        try
        {
            thumbnailDir = XMLHelper.getConfigDir("thumbnail_generators.xml"); //$NON-NLS-1$
            thumb.registerThumbnailers(thumbnailDir);
        }
        catch (Exception e1)
        {
            throw new RuntimeException("Couldn't find thumbnailer xml ["+(thumbnailDir != null ? thumbnailDir.getAbsolutePath() : "")+"]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        thumb.setQuality(.5f);
        thumb.setMaxHeight(128);
        thumb.setMaxWidth(128);
        
        // Load Local Prefs
        AppPreferences localPrefs = AppPreferences.getLocalPrefs();
        //localPrefs.setDirPath(UIRegistry.getAppDataDir());
        //localPrefs.load(); moved to end for not-null constraint
        /*String         derbyPath  = localPrefs.get("javadb.location", null);
        if (StringUtils.isNotEmpty(derbyPath))
        {
            UIRegistry.setJavaDBDir(derbyPath);
            log.debug("JavaDB Path: "+UIRegistry.getJavaDBPath());
        }*/
        
        String userSplashIconPath = AppPreferences.getLocalPrefs().get("specify.bg.image", null);
        if (userSplashIconPath != null)
        {
            SystemPrefs.changeSplashImage();
        }
        
        AttachmentManagerIface attachMgr          = null;
        File                   attachmentLocation = null;
        final File             location           = UIRegistry.getAppDataSubDir("AttachmentStorage", true); //$NON-NLS-1$
            
        try
        {
            String path = localPrefs.get(ATTACHMENT_PATH_PREF, null);
            attachmentLocation = path != null && !UIRegistry.isMobile() ? new File(path) : location;
            if (!AttachmentUtils.isAttachmentDirMounted(attachmentLocation))
            {
                final File attchLocDir = attachmentLocation;
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String pathStr;
                        try
                        {
                            pathStr = attchLocDir.getCanonicalPath();
                        } catch (IOException e)
                        {
                            pathStr = attchLocDir.getAbsolutePath();
                        }
                        UIRegistry.showLocalizedError("AttachmentUtils.LOC_BAD", pathStr);
                    }
                });
                
            } else
            {
                attachMgr = new FileStoreAttachmentManager(attachmentLocation);
            }
            
            if (path == null)
            {
                localPrefs.put(ATTACHMENT_PATH_PREF, location.getAbsolutePath());
            }
        }
        catch (IOException e1)
        {
            log.warn("Problems setting the FileStoreAttachmentManager at ["+location+"]"); //$NON-NLS-1$ //$NON-NLS-2$
            // TODO RELEASE -  Instead of exiting we need to disable Attachments
            //throw new RuntimeException("Problems setting the FileStoreAttachmentManager at ["+location+"]"); //$NON-NLS-1$ //$NON-NLS-2$
        }
        
        AttachmentUtils.setAttachmentManager(attachMgr);
        AttachmentUtils.setThumbnailer(thumb);
        ActionListener attachmentDisplayer = AttachmentUtils.getAttachmentDisplayer();
        
        DefaultClassActionHandler defClassActionHandler = DefaultClassActionHandler.getInstance();
        
        defClassActionHandler.registerActionHandler(Attachment.class,                     attachmentDisplayer);
        defClassActionHandler.registerActionHandler(AccessionAttachment.class,            attachmentDisplayer);
        defClassActionHandler.registerActionHandler(AgentAttachment.class,                attachmentDisplayer);
        defClassActionHandler.registerActionHandler(CollectingEventAttachment.class,      attachmentDisplayer);
        defClassActionHandler.registerActionHandler(CollectionObjectAttachment.class,     attachmentDisplayer);
        defClassActionHandler.registerActionHandler(ConservDescriptionAttachment.class,   attachmentDisplayer);
        defClassActionHandler.registerActionHandler(ConservEventAttachment.class,         attachmentDisplayer);
        defClassActionHandler.registerActionHandler(DNASequencingRunAttachment.class,     attachmentDisplayer);
        defClassActionHandler.registerActionHandler(FieldNotebookAttachment.class,        attachmentDisplayer);
        defClassActionHandler.registerActionHandler(FieldNotebookPageAttachment.class,    attachmentDisplayer);
        defClassActionHandler.registerActionHandler(FieldNotebookPageSetAttachment.class, attachmentDisplayer);
        defClassActionHandler.registerActionHandler(LoanAttachment.class,                 attachmentDisplayer);
        defClassActionHandler.registerActionHandler(LocalityAttachment.class,             attachmentDisplayer);
        defClassActionHandler.registerActionHandler(PermitAttachment.class,               attachmentDisplayer);
        defClassActionHandler.registerActionHandler(PreparationAttachment.class,          attachmentDisplayer);
        defClassActionHandler.registerActionHandler(RepositoryAgreementAttachment.class,  attachmentDisplayer);
        defClassActionHandler.registerActionHandler(TaxonAttachment.class,                attachmentDisplayer);
        
        //defClassActionHandler.registerActionHandler(Collector.class, new CollectorActionListener());
       
        UsageTracker.incrUsageCount("RunCount"); //$NON-NLS-1$
        
        //UIHelper.attachUnhandledException();

        FileCache.setDefaultPath(UIRegistry.getAppDataDir() + File.separator + "cache"); //$NON-NLS-1$

        cacheManager.registerCache(UIRegistry.getLongTermFileCache());
        cacheManager.registerCache(UIRegistry.getShortTermFileCache());
        cacheManager.registerCache(UIRegistry.getFormsCache());
        cacheManager.registerCache(JasperReportsCache.getInstance());
        
        
        UIRegistry.register(UIRegistry.MAINPANE, this); // important to be done immediately
 
        specifyApp = this;
        
        // this code simply demonstrates the creation of a system tray icon for Sp6
        // perhaps someday we may want to use this capability
//        SystemTray sysTray = SystemTray.getSystemTray();
//        PopupMenu popup = new PopupMenu("Sp6");
//        MenuItem exitItem = new MenuItem("Exit");
//        exitItem.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent ae)
//            {
//                doExit();
//            }
//        });
//        popup.add(exitItem);
//        TrayIcon sp6icon = new TrayIcon(IconManager.getIcon("Specify16").getImage(),"Sepcify 6",popup);
//        try
//        {
//            sysTray.add(sp6icon);
//        }
//        catch (AWTException e1)
//        {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
        
        //log.info("Creating Database configuration "); //$NON-NLS-1$

        if (!isWorkbenchOnly)
        {
            HibernateUtil.setListener("post-commit-update", new edu.ku.brc.specify.dbsupport.PostUpdateEventListener()); //$NON-NLS-1$
            HibernateUtil.setListener("post-commit-insert", new edu.ku.brc.specify.dbsupport.PostInsertEventListener()); //$NON-NLS-1$
            // SInce Update get called when deleting an object there is no need to register this class.
            // The update deletes because first it removes the Lucene document and then goes to add it back in, but since the
            // the record is deleted it doesn't get added.
            HibernateUtil.setListener("post-commit-delete", new edu.ku.brc.specify.dbsupport.PostDeleteEventListener()); //$NON-NLS-1$
            //HibernateUtil.setListener("delete", new edu.ku.brc.specify.dbsupport.DeleteEventListener());
        }
        adjustLocaleFromPrefs();
        
        CommandDispatcher.register(BaseTask.APP_CMD_TYPE, this);
        
        DatabaseLoginPanel.MasterPasswordProviderIFace usrPwdProvider = new DatabaseLoginPanel.MasterPasswordProviderIFace()
        {
            @Override
            public boolean hasMasterUserAndPwdInfo(final String username, final String password, final String dbName)
            {
                if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password))
                {
                    UserAndMasterPasswordMgr.getInstance().set(username, password, dbName);
                    return UserAndMasterPasswordMgr.getInstance().hasMasterUsernameAndPassword();
                }
                return false;
            }

            @Override
            public Pair<String, String> getUserNamePassword(final String username, final String password, final String dbName)
            {
                UserAndMasterPasswordMgr.getInstance().set(username, password, dbName);
                
                Pair<String, String> usrPwd = UserAndMasterPasswordMgr.getInstance().getUserNamePasswordForDB();
                
                return usrPwd;
            }

            @Override
            public boolean editMasterInfo(final String username, final String databaseNameArg, final boolean askForCredentials)
            {
                return UserAndMasterPasswordMgr.getInstance().editMasterInfo(username, databaseNameArg, askForCredentials);
            }
            
        };
        
        if (UIRegistry.isMobile())
        {
            DBConnection.setShutdownUI(new DBConnection.ShutdownUIIFace() 
            {
                CustomDialog processDlg;
                
                /* (non-Javadoc)
                 * @see edu.ku.brc.dbsupport.DBConnection.ShutdownUIIFace#displayInitialDlg()
                 */
                @Override
                public void displayInitialDlg()
                {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            UIRegistry.showLocalizedMsg(JOptionPane.INFORMATION_MESSAGE, "MOBILE_INFO", "MOBILE_INTRO");
                        }
                    });
                }
    
                /* (non-Javadoc)
                 * @see edu.ku.brc.dbsupport.DBConnection.ShutdownUIIFace#displayFinalShutdownDlg()
                 */
                @Override
                public void displayFinalShutdownDlg()
                {
                    processDlg.setVisible(false);
                    UIRegistry.showLocalizedMsg(JOptionPane.INFORMATION_MESSAGE, "MOBILE_INFO", "MOBILE_FINI");
                }
    
                /* (non-Javadoc)
                 * @see edu.ku.brc.dbsupport.DBConnection.ShutdownUIIFace#displayShutdownMsgDlg()
                 */
                @Override
                public void displayShutdownMsgDlg()
                {
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
                    
                    panel.add(new JLabel(IconManager.getIcon(getLargeIconName()), SwingConstants.CENTER), BorderLayout.WEST);
                    panel.add(UIHelper.createI18NLabel("MOBILE_SHUTTING_DOWN", SwingConstants.CENTER), BorderLayout.CENTER);
                    processDlg = new CustomDialog((Frame)null, "Shutdown", false, CustomDialog.NONE_BTN, panel);
                    processDlg.setAlwaysOnTop(true);
                    
                    UIHelper.centerAndShow(processDlg);
                    
                }
            });
        }
        
        /*long lastSaved = AppPreferences.getLocalPrefs().getLong("update_time", 0L);
        if (lastSaved > 0)
        {
            Date now = Calendar.getInstance().getTime();
            
            double elapsedMinutes = (now.getTime() - lastSaved) / 60000.0;
            log.debug(elapsedMinutes);
            if (elapsedMinutes < 1.0)
            {
                AppPreferences.setBlockTimer();
                UIRegistry.showError("You are currently logged in.\n Logging in twice will cause problems for your account.");
                System.exit(0);
            }
        }*/
        UIRegistry.dumpPaths();

        dbLoginPanel = UIHelper.doLogin(usrPwdProvider, true, false, false, this, getLargeIconName(), getTitle(), null, getOpaqueIconName(), "login"); // true means do auto login if it can, second bool means use dialog instead of frame
        localPrefs.load();
    }
    

    /**
     * Setup all the System properties. This names all the needed factories. 
     */
    public static void setUpSystemProperties()
    {
        // Name factories
        System.setProperty(ViewFactory.factoryName,                     "edu.ku.brc.specify.config.SpecifyViewFactory");        // Needed by ViewFactory //$NON-NLS-1$
        
        System.setProperty(AppContextMgr.factoryName,                   "edu.ku.brc.specify.config.SpecifyAppContextMgr");      // Needed by AppContextMgr //$NON-NLS-1$
        System.setProperty(AppPreferences.factoryName,                  "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");         // Needed by AppReferences //$NON-NLS-1$
        System.setProperty(AppPreferences.factoryGlobalName,            "edu.ku.brc.specify.config.AppPrefsGlobalDBIOIImpl");         // Needed by AppReferences //$NON-NLS-1$
        System.setProperty("edu.ku.brc.ui.ViewBasedDialogFactoryIFace", "edu.ku.brc.specify.ui.DBObjDialogFactory");            // Needed By UIRegistry //$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("edu.ku.brc.ui.forms.DraggableRecordIdentifierFactory", "edu.ku.brc.specify.ui.SpecifyDraggableRecordIdentiferFactory"); // Needed By the Form System //$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("edu.ku.brc.dbsupport.AuditInterceptor",     "edu.ku.brc.specify.dbsupport.AuditInterceptor");       // Needed By the Form System for updating Lucene and logging transactions //$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty(DataProviderFactory.factoryName,             "edu.ku.brc.specify.dbsupport.HibernateDataProvider");  // Needed By the Form System and any Data Get/Set //$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty("edu.ku.brc.ui.db.PickListDBAdapterFactory", "edu.ku.brc.specify.ui.db.PickListDBAdapterFactory");   // Needed By the Auto Cosmplete UI //$NON-NLS-1$ //$NON-NLS-2$
        System.setProperty(CustomQueryFactory.factoryName,              "edu.ku.brc.specify.dbsupport.SpecifyCustomQueryFactory"); //$NON-NLS-1$
        System.setProperty(UIFieldFormatterMgr.factoryName,             "edu.ku.brc.specify.ui.SpecifyUIFieldFormatterMgr");           // Needed for CatalogNumberign //$NON-NLS-1$
        System.setProperty(QueryAdjusterForDomain.factoryName,          "edu.ku.brc.specify.dbsupport.SpecifyQueryAdjusterForDomain"); // Needed for ExpressSearch //$NON-NLS-1$
        System.setProperty(SchemaI18NService.factoryName,               "edu.ku.brc.specify.config.SpecifySchemaI18NService");         // Needed for Localization and Schema //$NON-NLS-1$
        System.setProperty(WebLinkMgr.factoryName,                      "edu.ku.brc.specify.config.SpecifyWebLinkMgr");                // Needed for WebLnkButton //$NON-NLS-1$
        System.setProperty(DataObjFieldFormatMgr.factoryName,           "edu.ku.brc.specify.config.SpecifyDataObjFieldFormatMgr");     // Needed for WebLnkButton //$NON-NLS-1$
        System.setProperty(RecordSetFactory.factoryName,                "edu.ku.brc.specify.config.SpecifyRecordSetFactory");          // Needed for Searching //$NON-NLS-1$
        System.setProperty(DBTableIdMgr.factoryName,                    "edu.ku.brc.specify.config.SpecifyDBTableIdMgr");              // Needed for Tree Field Names //$NON-NLS-1$
        System.setProperty(SecurityMgr.factoryName,                     "edu.ku.brc.af.auth.specify.SpecifySecurityMgr");              // Needed for Tree Field Names //$NON-NLS-1$
        //System.setProperty(UserAndMasterPasswordMgr.factoryName,               "edu.ku.brc.af.auth.specify.SpecifySecurityMgr");              // Needed for Tree Field Names //$NON-NLS-1$
        System.setProperty(BackupServiceFactory.factoryName,            "edu.ku.brc.af.core.db.MySQLBackupService");                   // Needed for Backup and Restore //$NON-NLS-1$
        System.setProperty(ExceptionTracker.factoryName,                "edu.ku.brc.specify.config.SpecifyExceptionTracker");                   // Needed for Backup and Restore //$NON-NLS-1$
        System.setProperty(TypeSearchForQueryFactory.factoryName,       "edu.ku.brc.specify.ui.SpecifyTypeSearchForQueryFactory"); // Needed by ViewFactory //$NON-NLS-1$
        
        System.setProperty(DBMSUserMgr.factoryName,                     "edu.ku.brc.dbsupport.MySQLDMBSUserMgr");
        System.setProperty(SchemaUpdateService.factoryName,             "edu.ku.brc.specify.dbsupport.SpecifySchemaUpdateService");   // needed for updating the schema
        System.setProperty(GenericLSIDGeneratorFactory.factoryName,     "edu.ku.brc.specify.config.SpecifyLSIDGeneratorFactory");
    }

    /**
     * Creates the initial panels that will be shown at start up and sets up the Application Context
     * @param databaseNameArg the database name
     * @param userNameArg the user name
     */
    protected void initStartUpPanels(final String databaseNameArg, final String userNameArg)
    {

        if( !SwingUtilities.isEventDispatchThread() )
        {
            SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        initStartUpPanels(databaseNameArg, userNameArg);
                    }
                });
            return;
        }
 
        TaskMgr.readRegistry(UIRegistry.isMobile());
        
        TaskMgr.initializePlugins();
        
        Taskable bkTask = TaskMgr.getTask("BackupTask");
        if (bkTask != null)
        {
            bkTask.setIconName("MySQL");
        }
        validate();

        add(mainPanel, BorderLayout.CENTER);
        doLayout();

        mainPanel.setBackground(Color.WHITE);

        JToolBar toolBar = (JToolBar)UIRegistry.get(UIRegistry.TOOLBAR);
        if (toolBar != null && toolBar.getComponentCount() < 2)
        {
            toolBar.setVisible(false);
        }
     }

    /**
     * Determines if this is an applet or application
     */
    public boolean isApplet()
    {
        return false;
    }


    /**
     * General Method for initializing the class
     *
     */
    private void initialize(GraphicsConfiguration gc)
    {
        setLayout(new BorderLayout());

        // set the preferred size of the demo
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setPreferredSize(new Dimension(1024, 768)); // For demo

        topFrame = new JFrame(gc);
        topFrame.setIconImage(IconManager.getImage(getIconName()).getImage()); //$NON-NLS-1$
        //topFrame.setAlwaysOnTop(true);
        
        topFrame.setGlassPane(glassPane = GhostGlassPane.getInstance());
        topFrame.setLocationRelativeTo(null);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        UIRegistry.register(UIRegistry.GLASSPANE, glassPane);
        
        // Don't check everytime, too annoying
        //AppPreferences.getLocalPrefs().remove("SYSTEM.HasOpenGL"); // clear prop so it is checked
        UIHelper.checkForOpenGL();
        
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);

        UIRegistry.setTopWindow(topFrame);

        menuBar = createMenus();
        if (menuBar != null)
        {
            topFrame.setJMenuBar(menuBar);
        }
        UIRegistry.register(UIRegistry.MENUBAR, menuBar);


        JToolBar toolBar = createToolBar();
        if (toolBar != null)
        {
            top.add(toolBar, BorderLayout.CENTER);
        }
        UIRegistry.register(UIRegistry.TOOLBAR, toolBar);

        mainPanel = new MainPanel();

        int[] sections = {5, 5, 5, 1};
        statusField = new JStatusBar(sections);
        statusField.setErrorIcon(IconManager.getIcon("Error", IconManager.IconSize.Std16)); //$NON-NLS-1$
        statusField.setWarningIcon(IconManager.getIcon("Warning", IconManager.IconSize.Std16)); //$NON-NLS-1$
        UIRegistry.setStatusBar(statusField);
        
        JLabel secLbl = statusField.getSectionLabel(3);
        if (secLbl != null)
        {
            boolean isSecurityOn = AppContextMgr.isSecurityOn();
            secLbl.setIcon(IconManager.getImage(isSecurityOn ? "SecurityOn" : "SecurityOff", IconManager.IconSize.Std16));
            secLbl.setHorizontalAlignment(SwingConstants.CENTER);
            secLbl.setHorizontalTextPosition(SwingConstants.LEFT);
            secLbl.setText("");
            secLbl.setToolTipText(getResourceString("Specify.SEC_" + (isSecurityOn ? "ON" : "OFF")));
        }

        add(statusField, BorderLayout.SOUTH);
    }

    /**
     * @return
     */
    public static String getIconName()
    {
        return IconManager.makeIconName("AppIcon");
    }
    
    /**
     * @return icon for Windows or Linux.
     */
    public static String getOpaqueIconName()
    {
        return IconManager.makeIconName("SpecifyWhite32");
    }
    
    /**
     * @return
     */
    public static String getLargeIconName()
    {
        return IconManager.makeIconName("SpecifyLargeIcon");
    }
    
    /**
     * @param imgEncoded uuencoded image string
     */
    protected void setAppIcon(final String imgEncoded)
    {
        String appIconName      = getIconName();
        String innerAppIconName = "InnerAppIcon";
        
        ImageIcon appImgIcon = null;
        if (StringUtils.isNotEmpty(imgEncoded))
        {
            appImgIcon = GraphicsUtils.uudecodeImage("", imgEncoded); //$NON-NLS-1$
            if (appImgIcon != null && appImgIcon.getIconWidth() == 32 && appImgIcon.getIconHeight() == 32)
            {
                appIcon.setIcon(appImgIcon);
                CustomDialog.setAppIcon(appImgIcon);
                CustomFrame.setAppIcon(appImgIcon);
                IconManager.register(innerAppIconName, appImgIcon, null, IconManager.IconSize.Std32);
                return;
            }
        }
        appImgIcon = IconManager.getImage(appIconName, IconManager.IconSize.Std32); //$NON-NLS-1$
        appIcon.setIcon(appImgIcon);
        if (!UIHelper.isMacOS())
        {
            ImageIcon otherAppIcon = IconManager.getImage(getOpaqueIconName(), IconManager.IconSize.Std32); //$NON-NLS-1$
            if (otherAppIcon != null)
            {
                appImgIcon = otherAppIcon;
            }
        }
        
        CustomDialog.setAppIcon(appImgIcon);
        CustomFrame.setAppIcon(appImgIcon);
        IconManager.register(innerAppIconName, appImgIcon, null, IconManager.IconSize.Std32);
        
        this.topFrame.setIconImage(appImgIcon.getImage());
    }

    /**
     *
     * @return the toolbar for the app
     */
    public JToolBar createToolBar()
    {
        
        JToolBar toolBar;
        SkinItem skinItem = SkinsMgr.getSkinItem("ToolBar");
        if (SkinsMgr.hasSkins() && skinItem != null && (skinItem.getBGImage() != null || skinItem.getBgColor() != null))
        {
            JTiledToolbar ttb = new JTiledToolbar(skinItem.getBGImage());
            if (skinItem.getBgColor() != null)
            {
                ttb.setBackground(skinItem.getBgColor());
                ttb.setOpaque(true);
            } else
            {
                ttb.setOpaque(false);  
            }
            toolBar = ttb;
        } else
        {
            toolBar = new JToolBar();
        }
        toolBar.setLayout(new ToolbarLayoutManager(2, 2));
        
        appIcon = new JLabel("  "); //$NON-NLS-1$
        
        setAppIcon(AppPreferences.getRemote().get("ui.formatting.user_icon_image", null)); //$NON-NLS-1$
        
        CommandDispatcher.register("Preferences", new CommandListener() { //$NON-NLS-1$
            public void doCommand(CommandAction cmdAction)
            {
                if (cmdAction.isAction("Updated")) //$NON-NLS-1$
                {
                    setAppIcon(AppPreferences.getRemote().get("ui.formatting.user_icon_image", null)); //$NON-NLS-1$
                }
            }
        });

        return toolBar;
    }

    /**
     * Show preferences.
     */
    public void doPreferences()
    {
        //AppContextMgr acm        = AppContextMgr.getInstance();
        if (AppContextMgr.getInstance().hasContext())
        {
            PreferencesDlg dlg = new PreferencesDlg(false);
            dlg.setVisible(true);
        } else
        {
            UIRegistry.showLocalizedMsg("Specify.NOTAVAIL");
        }
    }
    
    /**
     * 
     */
    private void openLocalPrefs()
    {
        String titleStr = UIRegistry.getResourceString("Specify.LOCAL_PREFS"); //$NON-NLS-1$
        final CustomDialog dialog = new CustomDialog(topFrame, titleStr, true, CustomDialog.OK_BTN, new AppPrefsEditor(false));
        String okLabel = UIRegistry.getResourceString("Specify.CLOSE"); //$NON-NLS-1$
        dialog.setOkLabel(okLabel);
        dialog.pack();
        UIHelper.centerAndShow(dialog);
        if (!dialog.isCancelled())
        {
            try
            {
                AppPreferences.getLocalPrefs().flush();
            } catch (BackingStoreException ex) { }
            
            CommandDispatcher.dispatch(new CommandAction("Preferences", "Changed", AppPreferences.getLocalPrefs()));
        }
    }

    /**
     * 
     */
    private void openRemotePrefs()
    {
        String titleStr = UIRegistry.getResourceString("Specify.REMOTE_PREFS"); //$NON-NLS-1$
        final CustomDialog dialog = new CustomDialog(topFrame, titleStr, true, CustomDialog.OK_BTN, new AppPrefsEditor(true));
        String okLabel = getResourceString("Specify.CLOSE");//$NON-NLS-1$
        dialog.setOkLabel(okLabel); 
        dialog.pack();
        UIHelper.centerAndShow(dialog);
        if (!dialog.isCancelled())
        {
            try
            {
                AppPreferences.getRemote().flush();
            } catch (BackingStoreException ex)
            {
                
            }
            CommandDispatcher.dispatch(new CommandAction("Preferences", "Changed", AppPreferences.getRemote())); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Create menus
     */
    public JMenuBar createMenus()
    {
        JMenuBar mb = new JMenuBar();
        JMenuItem mi;

        //--------------------------------------------------------------------
        //-- File Menu
        //--------------------------------------------------------------------
        
        JMenu menu = null;
        
        if (!UIHelper.isMacOS() || !isWorkbenchOnly)
        {
            menu = UIHelper.createLocalizedMenu(mb, "Specify.FILE_MENU", "Specify.FILE_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (!isWorkbenchOnly)
        {
            // Add Menu for switching Collection
            String title = "Specify.CHANGE_COLLECTION"; //$NON-NLS-1$
            String mnu = "Specify.CHANGE_COLL_MNEU"; //$NON-NLS-1$
            changeCollectionMenuItem = UIHelper.createLocalizedMenuItem(menu, title, mnu, title, false, null); 
            changeCollectionMenuItem.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            if (SubPaneMgr.getInstance().aboutToShutdown())
                            {
                               
                                // Actually we really need to start over
                                // "true" means that it should NOT use any cached values it can find to automatically initialize itself
                                // instead it should ask the user any questions as if it were starting over
                                restartApp(null, databaseName, userName, true, false);
                            }
                        }
                    });
    
            
            menu.addMenuListener(new MenuListener() {
                @Override public void menuCanceled(MenuEvent e) {}
                @Override public void menuDeselected(MenuEvent e) {}
                @Override
                public void menuSelected(MenuEvent e)
                {
                    boolean enable = Uploader.getCurrentUpload() == null &&
                                    ((SpecifyAppContextMgr)AppContextMgr.getInstance()).getNumOfCollectionsForUser() > 1
                                    && !TaskMgr.areTasksDisabled();

                    changeCollectionMenuItem.setEnabled(enable);
                }
                
            });
        }

        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            if (!UIRegistry.isMobile())
            {
                menu.addSeparator();
            }
            String title = "Specify.EXIT"; //$NON-NLS-1$
            String mnu = "Specify.Exit_MNEU"; //$NON-NLS-1$
            mi = UIHelper.createLocalizedMenuItem(menu, title, mnu, title, true, null);
            if (!UIHelper.isMacOS())
            {
                mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
            }
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            doExit(true);
                        }
                    });
        }
       
        menu = UIRegistry.getInstance().createEditMenu();
        mb.add(menu);
        
        //menu = UIHelper.createMenu(mb, "EditMenu", "EditMneu");
        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            menu.addSeparator();
            String title = "Specify.PREFERENCES"; //$NON-NLS-1$
            String mnu = "Specify.PREFERENCES_MNEU";//$NON-NLS-1$
            mi = UIHelper.createLocalizedMenuItem(menu, title, mnu, title, false, null); 
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            doPreferences();
                        }
                    });
            mi.setEnabled(true);
        }
        
        //--------------------------------------------------------------------
        //-- Data Menu
        //--------------------------------------------------------------------
        JMenu dataMenu = UIHelper.createLocalizedMenu(mb, "Specify.DATA_MENU", "Specify.DATA_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
        ResultSetController.addMenuItems(dataMenu);
        dataMenu.addSeparator();
        
        // Save And New Menu Item
        Action saveAndNewAction = new AbstractAction(getResourceString("Specify.SAVE_AND_NEW")) { //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                FormViewObj fvo = getCurrentFVO();
                if (fvo != null)
                {
                    fvo.setSaveAndNew(((JCheckBoxMenuItem)e.getSource()).isSelected());
                }
            }
        };
        saveAndNewAction.setEnabled(false);
        JCheckBoxMenuItem saveAndNewCBMI = new JCheckBoxMenuItem(saveAndNewAction);
        dataMenu.add(saveAndNewCBMI);
        UIRegistry.register("SaveAndNew", saveAndNewCBMI); //$NON-NLS-1$
        UIRegistry.registerAction("SaveAndNew", saveAndNewAction); //$NON-NLS-1$
        mb.add(dataMenu);
        
        // Configure Carry Forward
        Action configCarryForwardAction = new AbstractAction(getResourceString("Specify.CONFIG_CARRY_FORWARD_MENU")) { //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                FormViewObj fvo = getCurrentFVO();
                if (fvo != null)
                {
                    fvo.configureCarryForward();
                }
            }
        };
        configCarryForwardAction.setEnabled(false);
        JMenuItem configCFWMI = new JMenuItem(configCarryForwardAction);
        dataMenu.add(configCFWMI);
        UIRegistry.register("ConfigCarryForward", configCFWMI); //$NON-NLS-1$
        UIRegistry.registerAction("ConfigCarryForward", configCarryForwardAction); //$NON-NLS-1$
        mb.add(dataMenu);

        //---------------------------------------
        // Carry Forward Menu Item (On / Off)
        Action carryForwardAction = new AbstractAction(getResourceString("Specify.CARRY_FORWARD_CHECKED_MENU")) { //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                FormViewObj fvo = getCurrentFVO();
                if (fvo != null)
                {
                    fvo.toggleCarryForward();
                    ((JCheckBoxMenuItem)e.getSource()).setSelected(fvo.isDoCarryForward());
                }
            }
        };
        carryForwardAction.setEnabled(false);
        JCheckBoxMenuItem carryForwardCBMI = new JCheckBoxMenuItem(carryForwardAction);
        dataMenu.add(carryForwardCBMI);
        UIRegistry.register("CarryForward", carryForwardCBMI); //$NON-NLS-1$
        UIRegistry.registerAction("CarryForward", carryForwardAction); //$NON-NLS-1$
        mb.add(dataMenu);
        
        if (!isWorkbenchOnly)
        {
            //---------------------------------------
            // AutoNumber Menu Item (On / Off)
            Action autoNumberOnOffAction = new AbstractAction(getResourceString("FormViewObj.SET_AUTONUMBER_ONOFF")) { //$NON-NLS-1$
                public void actionPerformed(ActionEvent e)
                {
                    FormViewObj fvo = getCurrentFVO();
                    if (fvo != null)
                    {
                        fvo.toggleAutoNumberOnOffState();
                        ((JCheckBoxMenuItem)e.getSource()).setSelected(fvo.isAutoNumberOn());
                    }
                }
            };
            autoNumberOnOffAction.setEnabled(false);
            JCheckBoxMenuItem autoNumCBMI = new JCheckBoxMenuItem(autoNumberOnOffAction);
            dataMenu.add(autoNumCBMI);
            UIRegistry.register("AutoNumbering", autoNumCBMI); //$NON-NLS-1$
            UIRegistry.registerAction("AutoNumbering", autoNumberOnOffAction); //$NON-NLS-1$
        }


        if (System.getProperty("user.name").equals("rods"))
        {
            dataMenu.addSeparator();
            
            
            AbstractAction gpxAction = new AbstractAction("GPS Data") {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    GPXPanel.getDlgInstance().setVisible(true);
                }
            };
            JMenuItem gpxMI = new JMenuItem(gpxAction);
            dataMenu.add(gpxMI);
            UIRegistry.register("GPXDlg", gpxMI); //$NON-NLS-1$
            UIRegistry.registerAction("GPXDlg", gpxAction); //$NON-NLS-1$
        }
        
        
        mb.add(dataMenu);

        SubPaneMgr.getInstance(); // force creating of the Mgr so the menu Actions are created.

        //--------------------------------------------------------------------
        //-- System Menu
        //--------------------------------------------------------------------
        
        if (!isWorkbenchOnly)
        {
            // TODO This needs to be moved into the SystemTask, but right now there is no way
            // to ask a task for a menu.
            menu = UIHelper.createLocalizedMenu(mb, "Specify.SYSTEM_MENU", "Specify.SYSTEM_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
            
            /*if (true)
            {
                menu = UIHelper.createMenu(mb, "Forms", "o");
                Action genForms = new AbstractAction()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        FormGenerator fg = new FormGenerator();
                        fg.generateForms();
                    }
                };
                mi = UIHelper.createMenuItemWithAction(menu, "Generate All Forms", "G", "", true, genForms);
            }*/
        }
        
        //--------------------------------------------------------------------
        //-- Tab Menu
        //--------------------------------------------------------------------
        menu = UIHelper.createLocalizedMenu(mb, "Specify.TABS_MENU", "Specify.TABS_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
        
        String ttl = UIRegistry.getResourceString("Specify.SBP_CLOSE_CUR_MENU"); 
        String mnu = UIRegistry.getResourceString("Specify.SBP_CLOSE_CUR_MNEU"); 
        mi = UIHelper.createMenuItemWithAction(menu, ttl, mnu, ttl, true, getAction("CloseCurrent")); 
        if (!UIHelper.isMacOS())
        {
            mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        }

        ttl = UIRegistry.getResourceString("Specify.SBP_CLOSE_ALL_MENU"); 
        mnu = UIRegistry.getResourceString("Specify.SBP_CLOSE_ALL_MNEU"); 
        mi = UIHelper.createMenuItemWithAction(menu, ttl, mnu, ttl, true, getAction("CloseAll")); 
        if (!UIHelper.isMacOS())
        {
            mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        }
        
        ttl = UIRegistry.getResourceString("Specify.SBP_CLOSE_ALLBUT_MENU"); 
        mnu = UIRegistry.getResourceString("Specify.SBP_CLOSE_ALLBUT_MNEU"); 
        mi = UIHelper.createMenuItemWithAction(menu, ttl, mnu, ttl, true, getAction("CloseAllBut")); 
        
        menu.addSeparator();
        
        // Configure Task
        JMenuItem configTaskMI = new JMenuItem(getAction("ConfigureTask"));
        menu.add(configTaskMI);
        //UIRegistry.register("ConfigureTask", configTaskMI); //$NON-NLS-1$

        //--------------------------------------------------------------------
        //-- Debug Menu
        //--------------------------------------------------------------------

        boolean doDebug = AppPreferences.getLocalPrefs().getBoolean("debug.menu", false);
        if (!UIRegistry.isRelease() || doDebug)
        {
            menu = UIHelper.createLocalizedMenu(mb, "Specify.DEBUG_MENU", "Specify.DEBUG_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
            String ttle =  "Specify.SHOW_LOC_PREFS";//$NON-NLS-1$ 
            String mneu = "Specify.SHOW_LOC_PREF_MNEU";//$NON-NLS-1$ 
            String desc = "Specify.SHOW_LOC_PREFS";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(menu,ttle, mneu, desc, true, null); 
            mi.addActionListener(new ActionListener()
            {
                @SuppressWarnings("synthetic-access")//$NON-NLS-1$ 
                public void actionPerformed(ActionEvent ae)
                {
                    openLocalPrefs();
                }
            });
                            

            ttle = "Specify.SHOW_REM_PREFS";//$NON-NLS-1$ 
            mneu = "Specify.SHOW_REM_PREFS_MNEU";//$NON-NLS-1$ 
            desc = "Specify.SHOW_REM_PREFS";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(menu, ttle,mneu , desc, true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            openRemotePrefs();
                        }
                    });
    
            menu.addSeparator();
            
            ttle = "Specify.CONFIG_LOGGERS";//$NON-NLS-1$ 
            mneu = "Specify.CONFIG_LOGGERS_MNEU";//$NON-NLS-1$ 
            desc = "Specify.CONFIG_LOGGER";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(menu,ttle, mneu, desc, true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            final LoggerDialog dialog = new LoggerDialog(topFrame);
                            UIHelper.centerAndShow(dialog);
                        }
                    });
            
            ttle = "Specify.CONFIG_DEBUG_LOGGERS";//$NON-NLS-1$ 
            mneu = "Specify.CONFIG_DEBUG_LOGGERS_MNEU";//$NON-NLS-1$ 
            desc = "Specify.CONFIG_DEBUG_LOGGER";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(menu, ttle , mneu, desc, true, null);  
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            DebugLoggerDialog dialog = new DebugLoggerDialog(topFrame);
                            UIHelper.centerAndShow(dialog);
                        }
                    });
            
            menu.addSeparator();
            
            ttle = "Specify.SHOW_MEM_STATS";//$NON-NLS-1$ 
            mneu = "Specify.SHOW_MEM_STATS_MNEU";//$NON-NLS-1$ 
            desc = "Specify.SHOW_MEM_STATS";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(menu, ttle , mneu, desc, true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            System.gc();
                            System.runFinalization();
                            
                            // Get current size of heap in bytes
                            double meg = 1024.0 * 1024.0;
                            double heapSize = Runtime.getRuntime().totalMemory() / meg;
                            
                            // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
                            // Any attempt will result in an OutOfMemoryException.
                            double heapMaxSize = Runtime.getRuntime().maxMemory() / meg;
                            
                            // Get amount of free memory within the heap in bytes. This size will increase
                            // after garbage collection and decrease as new objects are created.
                            double heapFreeSize = Runtime.getRuntime().freeMemory() / meg;
                            
                            UIRegistry.getStatusBar().setText(String.format("Heap Size: %7.2f    Max: %7.2f    Free: %7.2f   Used: %7.2f", heapSize, heapMaxSize, heapFreeSize, (heapSize - heapFreeSize))); //$NON-NLS-1$
                        }
                    });

            
            JMenu prefsMenu = new JMenu(UIRegistry.getResourceString("Specify.PREFS_IMPORT_EXPORT")); //$NON-NLS-1$
            menu.add(prefsMenu);
            ttle = "Specify.IMPORT_MENU";//$NON-NLS-1$ 
            mneu = "Specify.IMPORT_MNEU";//$NON-NLS-1$ 
            desc = "Specify.IMPORT_PREFS";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(prefsMenu, ttle , mneu, desc, true, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            importPrefs();
                        }
                    });
            ttle = "Specify.EXPORT_MENU";//$NON-NLS-1$ 
            mneu = "Specify.EXPORT_MNEU";//$NON-NLS-1$ 
            desc = "Specify.EXPORT_PREFS";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(prefsMenu, ttle , mneu, desc, true, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            exportPrefs();
                        }
                    });
            
            ttle = "Associate Storage Items";//$NON-NLS-1$ 
            mneu = "A";//$NON-NLS-1$ 
            desc = "";//$NON-NLS-1$ 
            mi = UIHelper.createMenuItemWithAction(menu, ttle, mneu , desc, true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            associateStorageItems();
                        }
                    });

            ttle = "Load GPX Points";//$NON-NLS-1$ 
            mneu = "a";//$NON-NLS-1$ 
            desc = "";//$NON-NLS-1$ 
            mi = UIHelper.createMenuItemWithAction(menu, ttle, mneu , desc, true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access") //$NON-NLS-1$
                        public void actionPerformed(ActionEvent ae)
                        {
                            CustomDialog dlg = GPXPanel.getDlgInstance();
                            if (dlg != null)
                            {
                                dlg.setVisible(true);
                            }
                        }
                    });

        
            JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Security Activated"); //$NON-NLS-1$
            menu.add(cbMenuItem);
            cbMenuItem.setSelected(AppContextMgr.isSecurityOn());
            cbMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae)
                        {
                            boolean isSecurityOn = !SpecifyAppContextMgr.isSecurityOn();                 
                            AppContextMgr.getInstance().setSecurity(isSecurityOn);
                            ((JMenuItem)ae.getSource()).setSelected(isSecurityOn);
                            
                            JLabel secLbl = statusField.getSectionLabel(3);
                            if (secLbl != null)
                            {
                                secLbl.setIcon(IconManager.getImage(isSecurityOn ? "SecurityOn" : "SecurityOff", IconManager.IconSize.Std16));
                                secLbl.setHorizontalAlignment(SwingConstants.CENTER);
                                secLbl.setToolTipText(getResourceString("Specify.SEC_" + (isSecurityOn ? "ON" : "OFF")));
                            }
                        }});
            
            JMenuItem sizeMenuItem = new JMenuItem("Set to "+PREFERRED_WIDTH+"x"+PREFERRED_HEIGHT); //$NON-NLS-1$
            menu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae)
                        {
                            topFrame.setSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
                        }});
        }
        
        //----------------------------------------------------
        //-- Helper Menu
        //----------------------------------------------------
        
        JMenu helpMenu = UIHelper.createLocalizedMenu(mb, "Specify.HELP_MENU", "Specify.HELP_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
        HelpMgr.createHelpMenuItem(helpMenu, getResourceString("SPECIFY_HELP")); //$NON-NLS-1$
        helpMenu.addSeparator();
        
        String ttle = "Specify.LOG_SHOW_FILES";//$NON-NLS-1$ 
        String mneu = "Specify.LOG_SHOW_FILES_MNEU";//$NON-NLS-1$ 
        String desc = "Specify.LOG_SHOW_FILES";//$NON-NLS-1$      
        mi = UIHelper.createLocalizedMenuItem(helpMenu, ttle , mneu, desc,  true, null);
        helpMenu.addSeparator();
        mi.addActionListener(new ActionListener()
        {
            @SuppressWarnings("synthetic-access") //$NON-NLS-1$
            public void actionPerformed(ActionEvent ae)
            {
                AppBase.displaySpecifyLogFiles();
            }
        });
                
        ttle = "Specify.CHECK_UPDATE";//$NON-NLS-1$ 
        mneu = "Specify.CHECK_UPDATE_MNEU";//$NON-NLS-1$ 
        desc = "Specify.CHECK_UPDATE_DESC";//$NON-NLS-1$      
        mi = UIHelper.createLocalizedMenuItem(helpMenu, ttle , mneu, desc,  true, null);
        mi.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                checkForUpdates();
            }
        });
        
        ttle = "Specify.AUTO_REG";//$NON-NLS-1$ 
        mneu = "Specify.AUTO_REG_MNEU";//$NON-NLS-1$ 
        desc = "Specify.AUTO_REG_DESC";//$NON-NLS-1$      
        mi = UIHelper.createLocalizedMenuItem(helpMenu, ttle , mneu, desc,  true, null);
        mi.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                RegisterSpecify.register(true, 0);
            }
        });
        
        ttle = "Specify.SA_REG";//$NON-NLS-1$ 
        mneu = "Specify.SA_REG_MNEU";//$NON-NLS-1$ 
        desc = "Specify.SA_REG_DESC";//$NON-NLS-1$      
        mi = UIHelper.createLocalizedMenuItem(helpMenu, ttle , mneu, desc,  true, null);
        mi.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                RegisterSpecify.registerISA();
            }
        });
        
        ttle = "Specify.FEEDBACK";//$NON-NLS-1$ 
        mneu = "Specify.FB_MNEU";//$NON-NLS-1$ 
        desc = "Specify.FB_DESC";//$NON-NLS-1$      
        mi = UIHelper.createLocalizedMenuItem(helpMenu, ttle , mneu, desc,  true, null);
        mi.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                FeedBackDlg feedBackDlg = new FeedBackDlg();
                feedBackDlg.sendFeedback();
            }
        });
        helpMenu.addSeparator();
        
                
        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            ttle = "Specify.ABOUT";//$NON-NLS-1$ 
            mneu = "Specify.ABOUTMNEU";//$NON-NLS-1$ 
            desc = "Specify.ABOUT";//$NON-NLS-1$ 
            mi = UIHelper.createLocalizedMenuItem(helpMenu,ttle , mneu, desc,  true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                           doAbout();
                        }
                    });
        }
        return mb;
    }
    
    
    /**
     * 
     */
    protected void checkForUpdates()
    {
        try
        {
            UpdateDescriptor updateDesc = UpdateChecker.getUpdateDescriptor(UIRegistry.getResourceString("UPDATE_PATH"),
                                                                           ApplicationDisplayMode.UNATTENDED);

            UpdateDescriptorEntry entry = updateDesc.getPossibleUpdateEntry();

            if (entry != null)
            {
                Object[] options = { getResourceString("Specify.INSTALLUPDATE"),  //$NON-NLS-1$
                        getResourceString("Specify.SKIP")  //$NON-NLS-1$
                      };
                int userChoice = JOptionPane.showOptionDialog(UIRegistry.getTopWindow(), 
                                                             getLocalizedMessage("Specify.UPDATE_AVAIL", entry.getNewVersion()),  //$NON-NLS-1$
                                                             getResourceString("Specify.UPDATE_AVAIL_TITLE"),  //$NON-NLS-1$
                                                             JOptionPane.YES_NO_CANCEL_OPTION,
                                                             JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (userChoice == JOptionPane.YES_OPTION)
                {
                    if (!doExit(false))
                    {
                        return;
                    }
                    
                } else
                {
                    return;
                }
            } else
            {
                UIRegistry.showLocalizedError("Specify.NO_UPDATE_AVAIL");
                return ;
            }
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex);
            UIRegistry.showLocalizedError("Specify.UPDATE_CHK_ERROR");
            return;
        }
        
        try
        {
            ApplicationLauncher.Callback callback = new ApplicationLauncher.Callback()
           {
               public void exited(int exitValue)
               {
                   log.error("exitValue: "+exitValue);
                   //startApp(doConfig);
               }
               public void prepareShutdown()
               {
                   log.error("prepareShutdown");
                   
               }
            };
            ApplicationLauncher.launchApplication("100", null, true, callback);
            
        } catch (Exception ex)
        {
            log.error(ex);
            log.error("EXPCEPTION");
        }
    }
    
    /**
     * @return the FormViewObj for the current SubPane or null
     */
    protected FormViewObj getCurrentFVO()
    {
        SubPaneIFace sp = SubPaneMgr.getInstance().getCurrentSubPane();
        if (sp instanceof FormPane)
        {
            MultiView mv = ((FormPane)sp).getMultiView();
            if (mv != null)
            {
                return mv.getCurrentViewAsFormViewObj();
            }
        }
        return null;
    }
    
    public Storage getStorageItem(final DataProviderSessionIFace session,
                                  final String path, 
                                  final Storage parentArg)
    {
        Storage parent = parentArg;
        String nodeStr = StringUtils.substringBefore(path, "/");
        String pathStr = StringUtils.substringAfter(path, "/");
        if (parent == null)
        {
            parent = session.getData(Storage.class, "name", nodeStr, DataProviderSessionIFace.CompareType.Equals);
            if (StringUtils.isNotEmpty(pathStr))
            {
                return getStorageItem(session, pathStr, parent);
            }
            return parent;
        }
        
        for (Storage node : parent.getChildren())
        {
            //log.debug("["+node.getName()+"]["+nodeStr+"]");
            if (node.getName().equals(nodeStr))
            {
                if (StringUtils.isNotEmpty(pathStr))
                {
                    return getStorageItem(session, pathStr, node);
                }
                return node;
            }
        }
        return null;
    }
    
    protected void addCOToStorage(final DataProviderSessionIFace session,
                                  final Storage storage, 
                                  final CollectionObject co) throws Exception
    {
        if (co != null && storage != null)
        {
            Set<Preparation> preps = storage.getPreparations();
            for (Preparation prep : co.getPreparations())
            {
                preps.add(prep);
                prep.setStorage(storage);
                session.saveOrUpdate(prep);
            }
            session.saveOrUpdate(co);
        }
    }
    
    /**
     * 
     */
    protected void associateStorageItems()
    {
        
        if (true)
        {
            showStorageHelperDlg();
            return;
        }
        
        //SpecifyDBConverter.addStorageTreeFomrXML();
        
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        Storage storage = getStorageItem(session, "Dyche Hall/Basement/Storage Room #1/Shelf A1", null);
        
        try
        {
            Taxon taxon = session.getData(Taxon.class, "fullName", "Ammocrypta clara", DataProviderSessionIFace.CompareType.Equals);
            session.beginTransaction();
            if (taxon != null)
            {
                for (Determination deter : taxon.getDeterminations())
                {
                    addCOToStorage(session, storage, deter.getCollectionObject());
                }
            }
            session.commit();
            
            storage = getStorageItem(session, "Dyche Hall/Basement/Storage Room #1/Shelf A2", null);
            taxon = session.getData(Taxon.class, "fullName", "Ammocrypta beanii", DataProviderSessionIFace.CompareType.Equals);
            session.beginTransaction();
            if (taxon != null)
            {
                for (Determination deter : taxon.getDeterminations())
                {
                    addCOToStorage(session, storage, deter.getCollectionObject());
                }
            }
            session.commit();
            
        } catch (Exception ex)
        {
            session.rollback();
            ex.printStackTrace();
        }
        
        session.close();
    }
    
    /**
     * @param taxon
     * @param storage
     */
    protected void associate(final Taxon taxon, final Storage storage)
    {
        if (taxon != null && storage != null)
        {
            DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
            try
            {
                session.attach(taxon);
                session.attach(storage);
                
                session.beginTransaction();
                if (taxon != null)
                {
                    for (Determination deter : taxon.getDeterminations())
                    {
                        addCOToStorage(session, storage, deter.getCollectionObject());
                    }
                }
                session.commit();
                
            } catch (Exception ex)
            {
                session.rollback();
                ex.printStackTrace();
            }
            
            session.close();
        }
    }
    
    /**
     * 
     */
    protected void showStorageHelperDlg()
    {
        
        //ViewBasedDisplayPanel panel = new ViewBasedDisplayPanel(null, null, "StorageAssignment", null, null, null, true, false, null, null, 0);
        
        int btnOpts = 0;
        //btnOpts |= cellField.getPropertyAsBoolean("editbtn", true) ? ValComboBoxFromQuery.CREATE_EDIT_BTN : 0;
        //btnOpts |= cellField.getPropertyAsBoolean("newbtn", true) ? ValComboBoxFromQuery.CREATE_NEW_BTN : 0;
        //btnOpts |= cellField.getPropertyAsBoolean("searchbtn", true) ? ValComboBoxFromQuery.CREATE_SEARCH_BTN : 0;
        
        ValComboBoxFromQuery taxonCBX = TypeSearchForQueryFactory.getInstance().createValComboBoxFromQuery("Taxon", btnOpts, null, null);
        taxonCBX.setRequired(true);
        
        ValComboBoxFromQuery storageCBX = TypeSearchForQueryFactory.getInstance().createValComboBoxFromQuery("Storage", btnOpts, null, null);
        storageCBX.setRequired(true);
        //cbx.setSearchDlgName("TaxonSearch");
        //cbx.setDisplayDlgName("Taxon");

        PanelBuilder pb = new PanelBuilder(new FormLayout("p,2px,p", "p,4px,p"));
        CellConstraints cc = new CellConstraints();
        pb.add(UIHelper.createI18NLabel("Taxon"), cc.xy(1, 1));
        pb.add(taxonCBX, cc.xy(3, 1));
        
        pb.add(UIHelper.createI18NLabel("Storage"), cc.xy(1, 3));
        pb.add(storageCBX, cc.xy(3, 3));
        
        pb.setDefaultDialogBorder();
        CustomDialog dlg = new CustomDialog((Frame)UIRegistry.getMostRecentWindow(), "", true, pb.getPanel());
        dlg.setVisible(true);
        
        if (dlg.isCancelled())
        {
            associate((Taxon)taxonCBX.getValue(), (Storage)storageCBX.getValue());
        }
        
    }
    
    /**
     * @param name
     * @param type
     * @param action
     * @return
     */
    protected Action createAction(final String name, final String type, final String action)
    {
        AbstractAction actionObj = new AbstractAction(getResourceString(name)) 
        {
            public void actionPerformed(ActionEvent e)
            {
                //CommandDispatcher.dispatch(new CommandAction(type, action, null));
            }
        };
        UIRegistry.registerAction(name, actionObj);
        return actionObj;
    }
    
    /**
     * 
     */
    protected void localities()
    {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stmt        = null;
        Statement stmt2       = null;
        try
        {
            stmt  = connection.createStatement();
            stmt2 = connection.createStatement();
            String    sql = "SELECT l.LocalityID, l.LocalityName, g.FullName, l.Latitude1, l.Longitude1 FROM locality l INNER JOIN geography g ON l.GeographyID = g.GeographyID";
            ResultSet rs  = stmt.executeQuery(sql);
            while (rs.next())
            {
                String currLocalityName = rs.getString(2);
                ResultSet rs1 = stmt2.executeQuery(sql+" WHERE g.FullName = \""+rs.getString(3)+"\" AND l.LocalityID <> " + rs.getInt(1));
                while (rs1.next())
                {
                    String localityName = rs1.getString(2);
                    int    distance     = StringUtils.getLevenshteinDistance(currLocalityName, localityName);
                    //log.error(rs.getInt(1) + "  "+ rs1.getInt(1) + "  "+ distance);
                    if (distance < 6)
                    {
                        log.error("----- "+distance+"\n"+currLocalityName+"\n"+localityName);
                        log.error(rs.getBigDecimal(4)+","+rs.getBigDecimal(5)+"\n"+rs1.getBigDecimal(4)+","+rs1.getBigDecimal(5));
                    }
                }
                rs1.close();
            }
            rs.close();
            
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
                if (stmt2 != null)
                {
                    stmt2.close();
                }
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Shows the About dialog.
     */
    public void doAbout()
    {
        AppContextMgr acm        = AppContextMgr.getInstance();
        boolean       hasContext = acm.hasContext();
        
        int baseNumRows = 11;
        String serverName = AppPreferences.getLocalPrefs().get("login.servers_selected", null);
        if (serverName != null)
        {
            baseNumRows++;
        }
        
        CellConstraints cc     = new CellConstraints();
        PanelBuilder    infoPB = new PanelBuilder(new FormLayout("p,6px,f:p:g", "p,4px,p,4px," + UIHelper.createDuplicateJGoodiesDef("p", "2px", baseNumRows)));
        
        JLabel       iconLabel = new JLabel(IconManager.getIcon("SpecifyLargeIcon"), SwingConstants.CENTER); //$NON-NLS-1$
        PanelBuilder iconPB    = new PanelBuilder(new FormLayout("p", "20px,t:p,f:p:g"));
        iconPB.add(iconLabel, cc.xy(1, 2));
        
        if (hasContext)
        {
            DBTableIdMgr tableMgr = DBTableIdMgr.getInstance();
            boolean      hasReged = !RegisterSpecify.isAnonymous() && RegisterSpecify.hasInstitutionRegistered();
            
            int y = 1;
            infoPB.addSeparator(getResourceString("Specify.SYS_INFO"), cc.xyw(1, y, 3)); y += 2;
            
            JLabel lbl = UIHelper.createLabel(databaseName);
            infoPB.add(UIHelper.createI18NFormLabel("Specify.DB"), cc.xy(1, y));
            infoPB.add(lbl,   cc.xy(3, y)); y += 2;
            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 2)
                    {
                        openLocalPrefs();
                    }
                }
            });
            
            infoPB.add(UIHelper.createFormLabel(tableMgr.getTitleForId(Institution.getClassTableId())),  cc.xy(1, y));
            infoPB.add(lbl = UIHelper.createLabel(acm.getClassObject(Institution.class).getName()), cc.xy(3, y)); y += 2;
            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (e.getClickCount() == 2)
                    {
                        openRemotePrefs();
                    }
                }
            });
            infoPB.add(UIHelper.createFormLabel(tableMgr.getTitleForId(Division.getClassTableId())), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(acm.getClassObject(Division.class).getName()),      cc.xy(3, y)); y += 2;
            
            infoPB.add(UIHelper.createFormLabel(tableMgr.getTitleForId(Discipline.getClassTableId())), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(acm.getClassObject(Discipline.class).getName()),      cc.xy(3, y)); y += 2;
            
            infoPB.add(UIHelper.createFormLabel(tableMgr.getTitleForId(Collection.getClassTableId())), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(acm.getClassObject(Collection.class).getCollectionName()),cc.xy(3, y)); y += 2;
            
            infoPB.add(UIHelper.createI18NFormLabel("Specify.BLD"), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(appBuildVersion),cc.xy(3, y)); y += 2;
            
            UIRegistry.loadAndPushResourceBundle("bld");
            infoPB.add(UIHelper.createFormLabel("SVN"), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(getResourceString("build")),cc.xy(3, y)); y += 2;
            
            infoPB.add(UIHelper.createI18NFormLabel("Specify.BLD_TM"), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(getResourceString("buildtime")),cc.xy(3, y)); y += 2;
            UIRegistry.popResourceBundle();
            
            infoPB.add(UIHelper.createI18NFormLabel("Specify.REG"), cc.xy(1, y));
            infoPB.add(UIHelper.createI18NLabel(hasReged ? "Specify.HASREG" : "Specify.NOTREG"),cc.xy(3, y)); y += 2;
            
            String isaNumber = RegisterSpecify.getISANumber();
            infoPB.add(UIHelper.createI18NFormLabel("Specify.ISANUM"), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(StringUtils.isNotEmpty(isaNumber) ? isaNumber : ""),cc.xy(3, y)); y += 2;
            
            if (serverName != null)
            {
                infoPB.add(UIHelper.createI18NFormLabel("Specify.SERVER"), cc.xy(1, y));
                infoPB.add(UIHelper.createLabel(StringUtils.isNotEmpty(serverName) ? serverName : ""),cc.xy(3, y)); y += 2;
            }
            
            if (StringUtils.contains(DBConnection.getInstance().getConnectionStr(), "mysql"))
            {
                Vector<Object[]> list = BasicSQLUtils.query("select version() as ve");
                if (list != null && list.size() > 0)
                {
                    infoPB.add(UIHelper.createFormLabel("MySQL Version"), cc.xy(1, y));
                    infoPB.add(UIHelper.createLabel(list.get(0)[0].toString()),cc.xy(3, y)); y += 2;
                }
            }
    
            
            infoPB.add(UIHelper.createFormLabel("Java Version"), cc.xy(1, y));
            infoPB.add(UIHelper.createLabel(System.getProperty("java.version")),cc.xy(3, y)); y += 2;
        }
        
        String txt = getAboutText(appName, appVersion);
        JLabel txtLbl = createLabel(txt);
        txtLbl.setFont(UIRegistry.getDefaultFont());
        
        final JEditorPane txtPane = new JEditorPane("text/html", txt);
        txtPane.setEditable(false);
        txtPane.setBackground(new JPanel().getBackground());
        
        PanelBuilder pb = new PanelBuilder(new FormLayout("p,20px,f:min(400px;p):g,10px,8px,10px,p:g", "f:p:g"));

        pb.add(iconPB.getPanel(), cc.xy(1, 1));
        pb.add(txtPane,           cc.xy(3, 1));
        Color bg = getBackground();
        
        if (hasContext)
        {
            pb.add(new VerticalSeparator(bg.darker(), bg.brighter()), cc.xy(5, 1));
            pb.add(infoPB.getPanel(), cc.xy(7, 1));
        }
        
        pb.setDefaultDialogBorder();
        
        String       title    = getResourceString("Specify.ABOUT");//$NON-NLS-1$
        CustomDialog aboutDlg = new CustomDialog(topFrame,  title + " " +appName, true, CustomDialog.OK_BTN, pb.getPanel()); //$NON-NLS-1$ 
        String       okLabel  = getResourceString("Specify.CLOSE");//$NON-NLS-1$
        aboutDlg.setOkLabel(okLabel); 
        
        aboutDlg.createUI();
        aboutDlg.pack();
        
        // for some strange reason I can't get the dialog to size itself correctly
        Dimension size = aboutDlg.getSize();
        size.height += 120;
        aboutDlg.setSize(size);
        
        txtPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent event)
            {
                if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    try
                    {
                        AttachmentUtils.openURI(event.getURL().toURI());
                        
                    }
                    catch (Exception e)
                    {
                        edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                    }
                }
            }
        });
        
        UIHelper.centerAndShow(aboutDlg);
    }
    
    /**
     * Returns a standard String for the about box
     * @param appNameArg the application name  
     * @param appVersionArg the application version
     * @return the about string
     */
    public static String getAboutText(final String appNameArg, final String appVersionArg)
    {
        return "<html><font face=\"sans-serif\" size=\"11pt\">"+appNameArg+" " + appVersionArg +  //$NON-NLS-1$ //$NON-NLS-2$
        "<br><br>Specify Software Project<br>" +//$NON-NLS-1$
        "Biodiversity Institute<br>University of Kansas<br>1345 Jayhawk Blvd.<br>Lawrence, KS  USA 66045<br><br>" +  //$NON-NLS-1$
        "<a href=\"http://www.specifysoftware .org\">www.specifysoftware.org</a>"+ //$NON-NLS-1$
        "<br><a href=\"mailto:specify@ku.edu\">specify@ku.edu</a><br>" +  //$NON-NLS-1$
        "<p>The Specify Software Project is "+ //$NON-NLS-1$
        "funded by the Advances in Biological Informatics Program, " + //$NON-NLS-1$
        "U.S. National Science Foundation  (Award DBI-0960913 and earlier awards).<br><br>" + //$NON-NLS-1$
        "Specify 6.0 Copyright \u00A9 2011 University of Kansas Center for Research. " + 
        "Specify comes with ABSOLUTELY NO WARRANTY.<br><br>" + //$NON-NLS-1$
        "This is free software licensed under GNU General Public License 2 (GPL2).</P></font></html>"; //$NON-NLS-1$

    }

    /**
     * Checks to see if cache has changed before exiting.
     *
     */
    public boolean doExit(boolean doAppExit)
    {
        boolean okToShutdown = true;
        try
        {
            if (AttachmentUtils.getAttachmentManager() != null)
            {
                AttachmentUtils.getAttachmentManager().cleanup();
            }
            
            okToShutdown = SubPaneMgr.getInstance().aboutToShutdown();
            if (okToShutdown)
            {
                UsageTracker.save();
                
                try
                {
                    DataProviderSessionIFace session     = null;
                    SpecifyUser              currentUser = AppContextMgr.getInstance().getClassObject(SpecifyUser.class);
                    if (currentUser != null)
                    {
                        session = DataProviderFactory.getInstance().createSession();
                        
                        SpecifyUser user = session.getData(SpecifyUser.class, "id", currentUser.getId(), DataProviderSessionIFace.CompareType.Equals);
                        user.setIsLoggedIn(false);
                        user.setLoginDisciplineName(null);
                        user.setLoginCollectionName(null);
                        user.setLoginOutTime(new Timestamp(System.currentTimeMillis()));
                        
                        try
                        {
                            session.beginTransaction();
                            session.saveOrUpdate(user);
                            session.commit();
                            
                        } catch (Exception ex)
                        {
                            log.error(ex);
                            
                        } finally
                        {
                            if (session != null)
                            {
                                session.close();
                            }
                        }
                    }
                    
                } catch (Exception ex)
                {
                    log.error(ex);
                }
                
                // Returns false if it isn't doing a backup.
                // passing true tells it to send an App exit command
                if (!BackupServiceFactory.getInstance().checkForBackUp(true))
                {
                
            		log.info("Application shutdown"); //$NON-NLS-1$
            		
            		if (topFrame != null)
            		{
                        Rectangle r = topFrame.getBounds();
                        AppPreferences.getLocalPrefs().putInt("APP.X", r.x);
                        AppPreferences.getLocalPrefs().putInt("APP.Y", r.y);
                        AppPreferences.getLocalPrefs().putInt("APP.W", r.width);
                        AppPreferences.getLocalPrefs().putInt("APP.H", r.height);
            		}
            
                    AppPreferences.getLocalPrefs().flush();
                    
             		// save the long term cache mapping info
            		try
            		{
            			UIRegistry.getLongTermFileCache().saveCacheMapping();
            			log.info("Successfully saved long term cache mapping"); //$NON-NLS-1$
            		}
            		catch( IOException ioe )
            		{
            			log.warn("Error while saving long term cache mapping.",ioe); //$NON-NLS-1$
            		}
                    
                    // clear the contents of the short term cache
                    log.info("Clearing the short term cache"); //$NON-NLS-1$
                    UIRegistry.getShortTermFileCache().clear();
            
                    // save the forms cache mapping info
                    try
                    {
                        UIRegistry.getFormsCache().saveCacheMapping();
                        log.info("Successfully saved forms cache mapping"); //$NON-NLS-1$
                    }
                    catch( IOException ioe )
                    {
                        log.warn("Error while saving forms cache mapping.",ioe); //$NON-NLS-1$
                    }
                    
                    if (topFrame != null)
                    {
                        topFrame.setVisible(false);
                    }
                    QueryExecutor.getInstance().shutdown();
                    
                } else
                {
                    okToShutdown = false;
                }
            }
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            
        } finally
        {
            if (okToShutdown && doAppExit)
            {
                Boolean canSendStats = false;
                if (AppContextMgr.getInstance().hasContext())
                {
                    canSendStats = AppPreferences.getRemote().getBoolean(sendStatsPrefName, true); //$NON-NLS-1$
                }
                
                if (canSendStats)
                {
                    Boolean          canSendISAStats  = AppPreferences.getRemote().getBoolean(sendISAStatsPrefName, true); //$NON-NLS-1$
                    StatsTrackerTask statsTrackerTask = (StatsTrackerTask)TaskMgr.getTask(StatsTrackerTask.STATS_TRACKER);
                    if (statsTrackerTask != null)
                    {
                        UIRegistry.getTopWindow().setVisible(false);
                        statsTrackerTask.setSendSecondaryStatsAllowed(canSendISAStats);
                        statsTrackerTask.sendStats(!UIRegistry.isMobile(), false, UIRegistry.isMobile()); // false means don't do it silently
                        
                        if (UIRegistry.isMobile())
                        {
                            AppPreferences.shutdownAllPrefs();
                            DataProviderFactory.getInstance().shutdown();
                            DBConnection.shutdown();
                        }
                        return false;
                    }
                    AppPreferences.shutdownAllPrefs();
                    DataProviderFactory.getInstance().shutdown();
                    DBConnection.shutdown();
                    
                } else if (UIRegistry.isMobile())
                {
                    // Fake like we sent stats
                    CommandDispatcher.dispatch(new CommandAction(BaseTask.APP_CMD_TYPE, "STATS_SEND_DONE", null));
                    
                } else
                {
                    AppPreferences.shutdownAllPrefs();
                    DataProviderFactory.getInstance().shutdown();
                    DBConnection.shutdown();
                    System.exit(0);
                }
                
            }
        }
        return okToShutdown;
    }
    
    /**
     * (To be replaced by method in AppBase)
     */
    protected String getTitle()
    {
        String resAppVersion = UIRegistry.getAppVersion();
        if (StringUtils.isNotEmpty(resAppVersion))
        {
            appVersion = resAppVersion;
        }
        
        return AppBase.getTitle(appVersion, appBuildVersion, appName);
    }

    /**
     * Bring up the PPApp demo by showing the frame (only applicable if coming up
     * as an application, not an applet);
     */
    public void showApp()
    {
        JFrame f = getFrame();
        f.setTitle(getTitle());
        f.getContentPane().add(this, BorderLayout.CENTER);
        f.pack();

        f.addWindowListener(new WindowAdapter()
        		{
        			@Override
                    public void windowClosing(WindowEvent e)
        			{
        				doExit(true);
        			}
        		});
        
        UIHelper.centerWindow(f);
        Rectangle r = f.getBounds();
        int x = AppPreferences.getLocalPrefs().getInt("APP.X", r.x);
        int y = AppPreferences.getLocalPrefs().getInt("APP.Y", r.y);
        int w = AppPreferences.getLocalPrefs().getInt("APP.W", r.width);
        int h = AppPreferences.getLocalPrefs().getInt("APP.H", r.height);
        UIHelper.positionAndFitToScreen(f, x, y, w, h);
        
        f.setVisible(true);
    }
    
    /**
     * Returns the frame instance
     */
    public JFrame getFrame()
    {
        if (topFrame == null)
        {
            topFrame = new JFrame(); // There was an error at start up
        }
        return topFrame;
    }

    /**
     * Returns the menubar
     */
    public JMenuBar getMenuBar()
    {
      return menuBar;
    }

    /**
     * Set the status
     */
    public void setStatus(final String s)
    {
        // do the following on the gui thread
        SwingUtilities.invokeLater(new SpecifyRunnable(this, s)
        {
            @SuppressWarnings("synthetic-access") //$NON-NLS-1$
            @Override
            public void run()
            {
                mApp.statusField.setText((String) obj);
            }
        });
    }
    
    /**
     * 
     */
    protected static void setupDefaultFonts()
    {
        Font labelFont = (createLabel("")).getFont(); //$NON-NLS-1$
        Font defaultFont;
        if (!UIHelper.isMacOS())
        {
            defaultFont = labelFont;
        } else
        {
            //if (labelFont.getSize() == 13)
            //{
            //    defaultFont = labelFont.deriveFont((float)labelFont.getSize()-2);
            //} else
            {
                defaultFont = labelFont;
            }
        }
        BaseTask.setToolbarBtnFont(defaultFont); // For ToolbarButtons
        RolloverCommand.setDefaultFont(defaultFont);
    }
    
    /**
     * This is called when switching Collections and NOT logging off.
     */
    protected void checkAndSendStats()
    {
        AppPreferences appPrefs             = AppPreferences.getRemote();
        Boolean        canSendStats         = appPrefs.getBoolean(sendStatsPrefName, null); //$NON-NLS-1$
        Boolean        canSendISAStats      = appPrefs.getBoolean(sendISAStatsPrefName, null); //$NON-NLS-1$
        
        if (canSendStats == null)
        {
            canSendStats = true;
            appPrefs.putBoolean(sendStatsPrefName, canSendStats); //$NON-NLS-1$
        }
        
        if (canSendStats)
        {
            StatsTrackerTask statsTrackerTask = (StatsTrackerTask)TaskMgr.getTask("StatsTracker");
            if (statsTrackerTask != null)
            {
                statsTrackerTask.setSendSecondaryStatsAllowed(canSendISAStats);
                statsTrackerTask.sendStats(false, true, false);
            }
        }
    }
    
    /**
     * Restarts the app with a new or old database and user name and creates the core app UI.
     * @param window the login window
     * @param databaseNameArg the database name
     * @param userNameArg the user name
     * @param startOver tells the AppContext to start over
     * @param firstTime indicates this is the first time in the app and it should create all the UI for the core app
     */
    public void restartApp(final Window  window, 
                           final String  databaseNameArg, 
                           final String  userNameArg, 
                           final boolean startOver, 
                           final boolean firstTime)
    {
        log.debug("restartApp"); //$NON-NLS-1$
        if (dbLoginPanel != null)
        {
            dbLoginPanel.getStatusBar().setText(getResourceString("Specify.INITIALIZING_APP")); //$NON-NLS-1$
        }

        if (!firstTime)
        {
            checkAndSendStats();
        }
        
        UIRegistry.dumpPaths();
        
        try
        {
            AppPreferences.getLocalPrefs().flush();
        } catch (BackingStoreException ex) {}
        
        AppPreferences.shutdownRemotePrefs();
        AppPreferences.shutdownPrefs();
        AppPreferences.setConnectedToDB(false);
        
        // Moved here because context needs to be set before loading prefs, we need to know the SpecifyUser
        //
        // NOTE: AppPreferences.startup(); is called inside setContext's implementation.
        //
        AppContextMgr.CONTEXT_STATUS status = AppContextMgr.getInstance().setContext(databaseNameArg, userNameArg, startOver, true);
        if (status == AppContextMgr.CONTEXT_STATUS.OK)
        {
            // XXX Temporary Fix!
            SpecifyUser spUser = AppContextMgr.getInstance().getClassObject(SpecifyUser.class);
            
            if (spUser != null)
            {
                String dbPassword = spUser.getPassword();
                
                if (StringUtils.isNotEmpty(dbPassword) && 
                        (!StringUtils.isAlphanumeric(dbPassword) ||
                         !UIHelper.isAllCaps(dbPassword) ||
                         dbPassword.length() < 25))
                {
                    String encryptedPassword = Encryption.encrypt(spUser.getPassword(), spUser.getPassword());
                    spUser.setPassword(encryptedPassword);
                    DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
                    try
                    {
                        session.beginTransaction();
                        session.saveOrUpdate(session.merge(spUser));
                        session.commit();
                        
                    } catch (Exception ex)
                    {
                        session.rollback();
                        ex.printStackTrace();
                    } finally
                    {
                        session.close();     
                    }
                }
            }
        }
        
        UsageTracker.setUserInfo(databaseNameArg, userNameArg);
        
        SpecifyAppPrefs.initialPrefs();
        
        // Check Stats (this is mostly for the first time in.
        AppPreferences appPrefs             = AppPreferences.getRemote();
        Boolean        canSendStats         = appPrefs.getBoolean(sendStatsPrefName, null); //$NON-NLS-1$
        Boolean        canSendISAStats      = appPrefs.getBoolean(sendISAStatsPrefName, null); //$NON-NLS-1$
        
        // Make sure we have the proper defaults
        if (canSendStats == null)
        {
            canSendStats = true;
            appPrefs.putBoolean("usage_tracking.send_stats", canSendStats); //$NON-NLS-1$
        }
        
        if (canSendISAStats == null)
        {
            canSendISAStats = true;
            appPrefs.putBoolean(sendISAStatsPrefName, canSendISAStats); //$NON-NLS-1$
        }
        
        if (status == AppContextMgr.CONTEXT_STATUS.OK)
        {
             // XXX Get the current locale from prefs PREF
            
            if (AppContextMgr.getInstance().getClassObject(Discipline.class) == null)
            {
                return;
            }
            
            // "false" means that it should use any cached values it can find to automatically initialize itself
            if (firstTime)
            {
                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
                
                initialize(gc);
    
                topFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                UIRegistry.register(UIRegistry.FRAME, topFrame);
            } else
            {
                SubPaneMgr.getInstance().closeAll();
            }
            
            preInitializePrefs();
            
            initStartUpPanels(databaseNameArg, userNameArg);
            
            AppPrefsCache.addChangeListener("ui.formatting.scrdateformat", UIFieldFormatterMgr.getInstance());

            
            if (changeCollectionMenuItem != null)
            {
                changeCollectionMenuItem.setEnabled(((SpecifyAppContextMgr)AppContextMgr.getInstance()).getNumOfCollectionsForUser() > 1);
            }
            
            if (window != null)
            {
                window.setVisible(false);
            }
            
            // General DB Fxies independent of a release.
            if (!AppPreferences.getGlobalPrefs().getBoolean("CollectingEventsAndAttrsMaint1", false))
            {
                // Temp Code to Fix issues with Release 6.0.9 and below
                SwingUtilities.invokeLater(new Runnable() 
                {
                    @Override
                    public void run()
                    {
                        CollectingEventsAndAttrsMaint dbMaint = new CollectingEventsAndAttrsMaint();
                        dbMaint.performMaint();
                    }
                });
            }
            
            /*if (!AppPreferences.getGlobalPrefs().getBoolean("FixAgentToDisciplinesV2", false))
            {
                // Temp Code to Fix issues with Release 6.0.9 and below
                SwingUtilities.invokeLater(new Runnable() 
                {
                    @Override
                    public void run()
                    {
                        FixDBAfterLogin fixer = new FixDBAfterLogin();
                        fixer.fixAgentToDisciplines();
                    }
                });
            }*/

            if (!AppPreferences.getGlobalPrefs().getBoolean("FixUploaderRecordsets", false))
            {
                // Temp Code to Fix issues with Release 6.0.9 and below
                SwingUtilities.invokeLater(new Runnable() 
                {
                    @Override
                    public void run()
                    {
                        FixDBAfterLogin fixer = new FixDBAfterLogin();
                        fixer.fixUploaderRecordsets();
                    }
                });
            }

            
            if (!AppPreferences.getGlobalPrefs().getBoolean("FixNullEmbeddedCollectingEvents", false))
            {
                // Temp Code to Fix issues with Release 6.0.9 and below
                SwingUtilities.invokeLater(new Runnable() 
                {
                    @Override
                    public void run()
                    {
                        //FixDBAfterLogin fixer = new FixDBAfterLogin();
                    	FixDBAfterLogin.fixNullEmbeddedCollectingEvents();
                    }
                });
            }

            // Check for Duplicate LocalityDetail and GeoCoordDetail
            SwingUtilities.invokeLater(new Runnable() 
            {
                @Override
                public void run()
                {
                    FixDBAfterLogin fixer = new FixDBAfterLogin();
                    fixer.checkMultipleLocalities();
                }
            });
            
            DataBuilder.mergeStandardGroups(AppContextMgr.getInstance().getClassObject(Collection.class));
            
        } else if (status == AppContextMgr.CONTEXT_STATUS.Error)
        {

            if (dbLoginPanel != null)
            {
                dbLoginPanel.getWindow().setVisible(false);
            }
            
            if (AppContextMgr.getInstance().getClassObject(Collection.class) == null)
            {
                
                // TODO This is really bad because there is a Database Login with no Specify login
                JOptionPane.showMessageDialog(null, 
                                              getResourceString("Specify.LOGIN_USER_MISMATCH"),  //$NON-NLS-1$
                                              getResourceString("Specify.LOGIN_USER_MISMATCH_TITLE"),  //$NON-NLS-1$
                                              JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        
        }
        
        CommandDispatcher.dispatch(new CommandAction("App", firstTime ? "StartUp" : "AppRestart", null)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        
        TaskMgr.requestInitalContext();
        
        if (!UIRegistry.isRelease())
        {
            DebugLoggerDialog dialog = new DebugLoggerDialog(null);
            dialog.configureLoggers();
        }

        showApp();

        
        if (dbLoginPanel != null)
        {
            dbLoginPanel.getWindow().setVisible(false);
            dbLoginPanel = null;
        }
        setDatabaseNameAndCollection();
    }
    
    /**
     * 
     */
    protected void importPrefs()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String title = getResourceString("Specify.SELECT_FILE_OR_DIR");//$NON-NLS-1$
        if (chooser.showDialog(null, title) != JFileChooser.CANCEL_OPTION) 
        {
            File destFile = chooser.getSelectedFile();
            
            Properties properties = new Properties();
            try
            {
                properties.load(new FileInputStream(destFile));
                AppPreferences remotePrefs = AppPreferences.getRemote();
                
                for (Object key : properties.keySet())
                {
                    String keyStr = (String)key;
                    remotePrefs.getProperties().put(keyStr, properties.get(key)); 
                }
                
            } catch (Exception ex)
            {
                log.error(ex); // XXX Error Dialog
            }
            
        } else 
        {
            throw new NoSuchElementException("The External File Repository needs a valid directory."); //$NON-NLS-1$
        } 
    }

    /**
     * 
     */
    protected void exportPrefs()
    {
        AppPreferences remotePrefs = AppPreferences.getRemote();
        Properties     props       = remotePrefs.getProperties();
        try
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            String title = getResourceString("Specify.SELECT_FILE_OR_DIR");//$NON-NLS-1$
            if (chooser.showDialog(null, title) != JFileChooser.CANCEL_OPTION)
            {
                File destFile = chooser.getSelectedFile();
                props.store(new FileOutputStream(destFile), "User Prefs"); //$NON-NLS-1$
            } else 
            {
                throw new NoSuchElementException("The External File Repository needs a valid directory."); //$NON-NLS-1$
            } 
            
        } catch (Exception ex)
        {
            log.error(ex); // XXX Error Dialog
        }
    }

    //---------------------------------------------------------
    // DatabaseLoginListener Interface
    //---------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.DatabaseLoginListener#loggedIn(java.awt.Window, java.lang.String, java.lang.String)
     */
    public void loggedIn(final Window window, final String databaseNameArg, final String userNameArg)
    {
        log.debug("loggedIn - database["+databaseNameArg+"] username["+ userNameArg +"]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        boolean firstTime = this.databaseName == null;
        
        this.databaseName = databaseNameArg;
        this.userName     = userNameArg;
        
        // This is used to fill who editted the object
        FormHelper.setCurrentUserEditStr(userNameArg);
        
        AppPreferences.setConnectedToDB(true);
        
        restartApp(window, databaseName, userName, false, firstTime);
        
        if (statusField != null)
        {
            statusField.setSectionText(2, userName);
            statusField.setSectionToolTipText(2, DBTableIdMgr.getInstance().getTitleForId(SpecifyUser.getClassTableId()));
        }
    }
    
    /**
     * Sets the Database Name and the Collection Name into the Status Bar. 
     */
    protected void setDatabaseNameAndCollection()
    {
        if (statusField != null)
        {
            AppContextMgr mgr = AppContextMgr.getInstance();
            String disciplineName = mgr.getClassObject(Discipline.class).getName();
            String collectionName = mgr.getClassObject(Collection.class) != null ? mgr.getClassObject(Collection.class).getCollectionName() : ""; //$NON-NLS-1$ //$NON-NLS-2$
            if (!UIRegistry.isMobile())
            {
                statusField.setSectionText(0, disciplineName);
                statusField.setSectionText(1, collectionName);
                
                statusField.setSectionToolTipText(0, DBTableIdMgr.getInstance().getTitleForId(Discipline.getClassTableId()));
                statusField.setSectionToolTipText(1, DBTableIdMgr.getInstance().getTitleForId(Collection.getClassTableId()));
            } else
            {
                statusField.setSectionText(0, "Specify Mobile");
                statusField.setSectionText(1, "WorkBench");
                
                statusField.setSectionToolTipText(0, null);
                statusField.setSectionToolTipText(1, null); 
            }
            
            AppPreferences.getLocalPrefs().put("CURRENT_DB", databaseName);
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.DatabaseLoginListener#cancelled()
     */
    public void cancelled()
    {
        DataProviderFactory.getInstance().shutdown();
        DBConnection.shutdown();
        DBConnection.shutdownFinalConnection(true, false); // true means System.exit
    }
    
    /**
     * Reads Local Preferences for the Locale setting.
     */
    public static void adjustLocaleFromPrefs()
    {
        String language = AppPreferences.getLocalPrefs().get("locale.lang", null); //$NON-NLS-1$
        if (language != null)
        {
            String country  = AppPreferences.getLocalPrefs().get("locale.country", ""); //$NON-NLS-1$
            String variant  = AppPreferences.getLocalPrefs().get("locale.var",     ""); //$NON-NLS-1$
            
            Locale prefLocale = new Locale(language, country, variant);
            
            Locale.setDefault(prefLocale);
            UIRegistry.setResourceLocale(prefLocale);
        }
        
        try
        {
            ResourceBundle.getBundle("resources", Locale.getDefault()); //$NON-NLS-1$
            
        } catch (MissingResourceException ex)
        {
            Locale.setDefault(Locale.ENGLISH);
            UIRegistry.setResourceLocale(Locale.ENGLISH);
        }
    }

    /**
     * @return the appVersion
     */
    public String getAppVersion()
    {
        return appVersion;
    }

    /**
     * @return the appBuildVersion
     */
    public String getAppBuildVersion()
    {
        return appBuildVersion;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CommandListener#doCommand(edu.ku.brc.ui.CommandAction)
     */
    @Override
    public void doCommand(final CommandAction cmdAction)
    {
        if (cmdAction.isType(BaseTask.APP_CMD_TYPE))
        {
            if (cmdAction.isAction(BaseTask.APP_REQ_RESTART))
            {
                UIRegistry.writeGlassPaneMsg(getResourceString("Specify.RESET_ENV"), 24);
                
                SwingWorker workerThread = new SwingWorker()
                {
                    @Override
                    public Object construct()
                    {
                        restartApp(null, databaseName, userName, true, false);
                        return null;
                    }
                    
                    @Override
                    public void finished()
                    {
                        UIRegistry.clearGlassPaneMsg();
                    }
                };
                
                // start the background task
                workerThread.start();
                
            } else if (cmdAction.isAction(BaseTask.APP_REQ_EXIT))
            {
                doExit(true);
                
            } else if (cmdAction.isAction("CheckForUpdates"))
            {
                checkForUpdates();
            } else if (cmdAction.isAction("STATS_SEND_DONE"))
            {
                if (UIRegistry.isMobile())
                {
                    
                    DataProviderFactory.getInstance().shutdown();
                    DBConnection.shutdown();
                    DBConnection.shutdownFinalConnection(true, false); // true means System.exit
                    
                } else
                {
                    DataProviderFactory.getInstance().shutdown();
                    DBConnection.shutdown();
                    System.exit(0);
                }
            }
        }
    }

    // *******************************************************
    // *****************   Static Methods  *******************
    // *******************************************************

    /**
     *
     * @return the specify app object
     */
    public static Specify getSpecify()
    {
        return specifyApp;
    }
    
    /**
     * @return the cache manager
     */
    public static CacheManager getCacheManager()
    {
        return cacheManager;
    }

  // *******************************************************
  // ******************   Runnables  ***********************
  // *******************************************************

  /**
   * Generic Specify runnable. This is intended to run on the
   * AWT gui event thread so as not to muck things up by doing
   * gui work off the gui thread. Accepts a Specify and an Object
   * as arguments, which gives subtypes of this class the two
   * "must haves" needed in most runnables for this demo.
   */
  class SpecifyRunnable implements Runnable
  {

      protected Specify mApp;

      protected Object    obj;

      public SpecifyRunnable(Specify aApp, Object obj)
      {
        this.mApp = aApp;
        this.obj = obj;
      }

      public void run()
      {
          // do nothing
      }
  }

  //-----------------------------------------------------------------------------
  //-- Application MAIN
  //-----------------------------------------------------------------------------

  public static void startApp()
  {
      
      // XXX RELEASE
      boolean isRelease = true;
      UIRegistry.setRelease(isRelease);
      UIRegistry.setTesting(!isRelease);

      boolean doCheckSum = false;
      XMLHelper.setUseChecksum(isRelease && doCheckSum); 

      // Then set this
      IconManager.setApplicationClass(Specify.class);
      IconManager.loadIcons(XMLHelper.getConfigDir("icons_datamodel.xml")); //$NON-NLS-1$
      IconManager.loadIcons(XMLHelper.getConfigDir("icons_plugins.xml")); //$NON-NLS-1$
      IconManager.loadIcons(XMLHelper.getConfigDir("icons_disciplines.xml")); //$NON-NLS-1$
      
      /*if (UIHelper.isMacOS())
      {
          Toolkit toolkit = Toolkit.getDefaultToolkit( );
          Image image = toolkit.getImage( "NSImage://NSUserGroup" );

          IconEntry entry = IconManager.getIconEntryByName("AdminGroup");
          entry.setIcon(new ImageIcon(image));
          
          image = toolkit.getImage( "NSImage://NSUser" );
          entry = IconManager.getIconEntryByName("person");
          entry.setIcon(new ImageIcon(image));
      }*/
      
      if (!UIRegistry.isRelease())
      {
          MemoryWarningSystem.setPercentageUsageThreshold(0.75);

          MemoryWarningSystem mws = new MemoryWarningSystem();
          mws.addListener(new MemoryWarningSystem.Listener()
          {
              protected void setMessage(final String msg, final boolean isError)
              {
                  JStatusBar statusBar = UIRegistry.getStatusBar();
                  if (statusBar != null)
                  {
                      if (isError)
                      {
                          statusBar.setErrorMessage(msg);
                      } else
                      {
                          statusBar.setText(msg);
                      }
                  } else
                  {
                      log.error(msg);
                  }
              }
              
              public void memoryUsage(long usedMemory, long maxMemory)
              {
                  double percentageUsed = ((double) usedMemory) / maxMemory;
                  
                  String msg = String.format("Percent Memory Used %6.2f of Max %d", new Object[] {(percentageUsed * 100.0), maxMemory}); //$NON-NLS-1$
                  setMessage(msg, false);

              }

              public void memoryUsageLow(long usedMemory, long maxMemory)
              {
                  double percentageUsed = ((double) usedMemory) / maxMemory;
                    
                  String msg = String.format("Memory is Low! Percentage Used = %6.2f of Max %d", new Object[] {(percentageUsed * 100.0), maxMemory}); //$NON-NLS-1$
                  setMessage(msg, true);
                    
                  if (MemoryWarningSystem.getThresholdPercentage() < 0.8)
                  {
                      MemoryWarningSystem.setPercentageUsageThreshold(0.8);
                  }
                }
            });
      }
      
      // Setup base font AFTER setting Look and Feel
      Font defFont = (createLabel("")).getFont();
      UIRegistry.setDefaultFont(defFont);

      setupDefaultFonts();
      
      Font sysBaseFont = UIRegistry.getBaseFont(); // forces loading of System Base Font before anything happens

      String  key = "ui.formatting.controlSizes"; //$NON-NLS-1$
      String  fontName = AppPreferences.getLocalPrefs().get(key+".FN", UIRegistry.getBaseFont().getFamily());
      Integer fontSize = AppPreferences.getLocalPrefs().getInt(key+".SZ", UIRegistry.getBaseFont().getSize());

      Font newBaseFont = fontName != null && fontSize != null ? new Font(fontName, Font.PLAIN, fontSize) : sysBaseFont;
      UIRegistry.setBaseFont(newBaseFont);
          
      //SkinsMgr.getInstance().setSkin("giraffe");
      
      BaseTask.setToolbarBtnFont(newBaseFont); // For ToolbarButtons
      RolloverCommand.setDefaultFont(newBaseFont);
      
      ImageIcon helpIcon = IconManager.getIcon(getIconName(),IconSize.Std16); //$NON-NLS-1$
      HelpMgr.initializeHelp("SpecifyHelp", helpIcon.getImage()); //$NON-NLS-1$
      
      // Startup Specify
      Specify specify = new Specify();
      
      RolloverCommand.setHoverImg(IconManager.getIcon("DropIndicator")); //$NON-NLS-1$
      
      specify.preStartUp();
      specify.startUp();
  }
  
  /**
   * @return
   */
  /*private static String getLastVersion()
  {
      try
      {
          return  FileUtils.readFileToString(new File("lastversion.dat"));
          
      } catch (Exception ex)
      {
          //ex.printStackTrace();  
      }
      return null;
  }
  
  private static void setLastVersion(final String version)
  {
      try
      {
          FileUtils.writeStringToFile(new File("lastversion.dat"), version);
          
      } catch (Exception ex)
      {
          //ex.printStackTrace();
      } 
  }*/
  
  public static void checkDebugLoggerSettings()
  {
      AppPreferences localPrefs = AppPreferences.getLocalPrefs();
      
      for (Enumeration<?> e=LogManager.getCurrentLoggers(); e.hasMoreElements();)
      {
          Logger logger = (Logger)e.nextElement();
          int inx = logger.getName().lastIndexOf('.');
          if (inx > -1)
          {
              String  className = logger.getName().substring(inx+1);
              boolean isOn      = localPrefs.getBoolean(className+".debug", false);
              if (isOn)
              {
                  logger.setLevel(Level.DEBUG); 
              }
          }
      }
  }
  
  /**
   *
   */
  public static void main(String[] args)
  {
      
      // Set App Name, MUST be done very first thing!
      UIRegistry.setAppName("Specify");  //$NON-NLS-1$

      //log.debug("********* Current ["+(new File(".").getAbsolutePath())+"]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      
      AppBase.processArgs(args);
      AppBase.setupTeeForStdErrStdOut(true, false);
      
      SwingUtilities.invokeLater(new Runnable() {
          @SuppressWarnings("synthetic-access") //$NON-NLS-1$
        public void run()
          {
              log.debug("Checking for update....");
              try
              {
                  try
                  {
                      UIHelper.OSTYPE osType = UIHelper.getOSType();
                      if (osType == UIHelper.OSTYPE.Windows )
                      {
                          //UIManager.setLookAndFeel(new WindowsLookAndFeel());
                          UIManager.setLookAndFeel(new PlasticLookAndFeel());
                          PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
                          
                      } else if (osType == UIHelper.OSTYPE.Linux )
                      {
                          //UIManager.setLookAndFeel(new GTKLookAndFeel());
                          UIManager.setLookAndFeel(new PlasticLookAndFeel());
                          //PlasticLookAndFeel.setPlasticTheme(new SkyKrupp());
                          //PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
                          //PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
                          //PlasticLookAndFeel.setPlasticTheme(new DesertGreen());
                         
                      } else
                      {
                          //PlafOptions.setAsLookAndFeel();
                      }
                  }
                  catch (Exception e)
                  {
                      log.error("Can't change L&F: ", e); //$NON-NLS-1$
                  }
                  
                  // Load Local Prefs
                  AppPreferences localPrefs = AppPreferences.getLocalPrefs();
                  localPrefs.setDirPath(UIRegistry.getAppDataDir());
                  
                  ProxyHelper.setProxySettingsFromPrefs();
                  
                  checkDebugLoggerSettings();
                  
                  //log.error("LocalPrefs: "+(new File(UIRegistry.getAppDataDir())).getCanonicalPath());
                  
                  // Check to see if we should check for a new version
                  String VERSION_CHECK = "version_check.auto";
                  if (localPrefs.getBoolean(VERSION_CHECK, null) == null)
                  {
                      localPrefs.putBoolean(VERSION_CHECK, true);
                  }
                  
                  SpecialMsgNotifier smn = new SpecialMsgNotifier();
                  smn.checkForMessages();
                  
                  
                  if (UIRegistry.isEmbedded() && !UIRegistry.isMobile())
                  {
                      String EZDB_FIRSTTIME = "ezdb.firsttime";
                      if (localPrefs.getBoolean(EZDB_FIRSTTIME, null) == null)
                      {
                          UIRegistry.showLocalizedMsg("EZDB_FIRSTTIME");
                          localPrefs.putBoolean(EZDB_FIRSTTIME, true);
                          localPrefs.flush();
                      }
                  }

                  String EXTRA_CHECK = "extra.check";
                  Boolean isExtraCheck = localPrefs.getBoolean(EXTRA_CHECK, true);
                  if (isExtraCheck == null)
                  {
                      isExtraCheck = true;
                      localPrefs.putBoolean(EXTRA_CHECK, isExtraCheck);
                  }

                  if (localPrefs.getBoolean(VERSION_CHECK, true))
                  {
                      try
                      {
                    	 com.install4j.api.launcher.SplashScreen.hide();
                         ApplicationLauncher.Callback callback = new ApplicationLauncher.Callback()
                         {
                             @Override
                             public void exited(int exitValue)
                             {
                                 startApp();
                             }
                             
                             @Override
                             public void prepareShutdown()
                             {
                                 
                             }
                          };
                          ApplicationLauncher.launchApplication("100", null, true, callback);
                          
                      } catch (Exception ex)
                      {
                          ex.printStackTrace();
                          log.error(ex);
                          startApp();
                      }
                  } else
                  {
                      if (!isExtraCheck && StringUtils.isNotEmpty(UIRegistry.getAppVersion()))
                      {
                          UIRegistry.showLocalizedMsg(null, "SpReg.NOT_REGISTERED");
                      }
                      startApp( );
                  }
              } catch (Exception ex)
              {
                  ex.printStackTrace();
                  log.error(ex);
              }
          }
      });

  }
}

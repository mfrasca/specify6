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

package edu.ku.brc.specify;

import static edu.ku.brc.ui.UICacheManager.getResourceString;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.prefs.BackingStoreException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyKrupp;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.ContextMgr;
import edu.ku.brc.af.core.MainPanel;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.TaskMgr;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.AppPrefsEditor;
import edu.ku.brc.af.prefs.PrefMainPanel;
import edu.ku.brc.dbsupport.CustomQueryFactory;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.specify.config.LoggerDialog;
import edu.ku.brc.specify.config.SpecifyAppContextMgr;
import edu.ku.brc.specify.datamodel.Attachment;
import edu.ku.brc.specify.datamodel.CatalogSeries;
import edu.ku.brc.specify.datamodel.Collector;
import edu.ku.brc.specify.tasks.ExpressSearchTask;
import edu.ku.brc.specify.tests.SpecifyAppPrefs;
import edu.ku.brc.specify.ui.CollectorActionListener;
import edu.ku.brc.specify.ui.HelpMgr;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.DefaultClassActionHandler;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.JStatusBar;
import edu.ku.brc.ui.ToolbarLayoutManager;
import edu.ku.brc.ui.UICacheManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.db.DatabaseLoginListener;
import edu.ku.brc.ui.db.DatabaseLoginPanel;
import edu.ku.brc.ui.dnd.GhostGlassPane;
import edu.ku.brc.util.AttachmentManagerIface;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.FileCache;
import edu.ku.brc.util.FileStoreAttachmentManager;
import edu.ku.brc.util.MemoryWarningSystem;
import edu.ku.brc.util.thumbnails.Thumbnailer;
/**
 * Specify Main Application Class

 * @code_status Unknown (auto-generated)
 * @author rods
 */
@SuppressWarnings("serial")
public class Specify extends JPanel implements DatabaseLoginListener
{
    private static final Logger log                = Logger.getLogger(Specify.class);
    public static final boolean IS_DEVELOPMENT     = true;
    
    // The preferred size of the demo
    private static final int    PREFERRED_WIDTH    = 900;
    private static final int    PREFERRED_HEIGHT   = 800;

    private static Specify      specifyApp         = null; // needed for ActionListeners etc.

    // Status Bar
    private JStatusBar          statusField        = null;
    private JMenuBar            menuBar            = null;
    private JFrame              topFrame           = null;
    private MainPanel           mainPanel          = null;
    private JMenuItem           changeCatSeriesBtn = null;

    protected  boolean          hasChanged         = false;

    protected String             currentDatabaseName = null;
    protected DatabaseLoginPanel dbLoginPanel        = null;
    protected String             databaseName        = null;
    protected String             userName            = null;

    protected GhostGlassPane     glassPane;

    private JLabel               splashLabel = null;

    // Used only if swingset is an application
    private JWindow              splashWindow        = null;
    private ImageIcon            specifyImageIcon    = null;
    
    
    private boolean              isWorkbenchOnly     = true;
    private boolean              isRelease           = true;

    /**
     * Constructor.
     */
    public Specify()
    {
        UICacheManager.setRelease(isRelease);
    }
    
    /**
     * The very very first step in initializing Specify. 
     */
    protected void preStartUp()
    {
        UICacheManager.setUseCurrentLocation(true);
        
        //UIHelper.attachUnhandledException();
        
        // we simply need to create this class, not use it
        //@SuppressWarnings("unused") MacOSAppHandler macoshandler = new MacOSAppHandler(this);
        new MacOSAppHandler(this);

        // Name factories
        setUpSystemProperties();
        
        IconManager.setApplicationClass(Specify.class);
        UICacheManager.setAppName("Specify");        
    }
    
    /**
     * Starts up Specify with the initializer that enables the user to create a new empty database. 
     */
    public void startWithInitializer()
    {
        preStartUp();
        
        if (true)
        {
            SpecifyInitializer specifyInitializer = new SpecifyInitializer();
            specifyInitializer.setup(this);
            
        } else
        {
            startUp();
        }
    }
    
    /**
     * Start up without the initializer, assumes there is at least one database to connect to.
     */
    public void startUp()
    {
        // This sets a global flag to tell Specify to put the User Data directory in the user's
        // home directory,
        if (!SpecifyInitializer.setUseCurrentLocation())
        {
            UICacheManager.setUseCurrentLocation(false);
        }

        System.setProperty("derby.system.home", UICacheManager.getDefaultWorkingPath() + File.separator + "DerbyDatabases");
        
        // Attachment related helpers
        Thumbnailer thumb = new Thumbnailer();
        try
        {
            thumb.registerThumbnailers("config/thumbnail_generators.xml");
        }
        catch (Exception e1)
        {
            // TODO: fix this up
            System.exit(-1);
        }
        thumb.setQuality(.5f);
        thumb.setMaxHeight(128);
        thumb.setMaxWidth(128);

        AttachmentManagerIface attachMgr = null;
        
        File location = UICacheManager.getDefaultWorkingPathSubDir("AttachmentStorage", true);
        try
        {
            attachMgr = new FileStoreAttachmentManager(location);
        }
        catch (IOException e1)
        {
            log.warn("Problems setting the FileStoreAttachmentManager at ["+location+"]");
            // TODO RELEASE -  Instead of exiting we need to disable Attchements
            System.exit(-1);
        }
        
        AttachmentUtils.setAttachmentManager(attachMgr);
        AttachmentUtils.setThumbnailer(thumb);
        ActionListener attachmentDisplayer = AttachmentUtils.getAttachmentDisplayer();
        DefaultClassActionHandler.getInstance().registerActionHandler(Attachment.class, attachmentDisplayer);
        DefaultClassActionHandler.getInstance().registerActionHandler(Collector.class, new CollectorActionListener());
        
        
        // Load Local Prefs
        AppPreferences localPrefs = AppPreferences.getLocalPrefs();
        localPrefs.setDirPath(UICacheManager.getDefaultWorkingPath());
        //localPrefs.load(); moved to end for not-null constraint
        
        FileCache.setDefaultPath(UICacheManager.getDefaultWorkingPath()+ File.separator + "cache");

        UICacheManager.register(UICacheManager.MAINPANE, this); // important to be done immediately
 
        specifyApp = this;
        
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
        
        // Setup base font AFTER setting Look and Feel
        UICacheManager.setBaseFont((new JLabel()).getFont());

        log.info("Creating Database configuration ");

        if (!isWorkbenchOnly)
        {
            HibernateUtil.setListener("post-commit-update", new edu.ku.brc.specify.dbsupport.PostUpdateEventListener());
            HibernateUtil.setListener("post-commit-insert", new edu.ku.brc.specify.dbsupport.PostInsertEventListener());
            // SInce Update get called when deleting an object there is no need to register this class.
            // The update deletes becuase first it removes the Lucene document and then goes to add it back in, but since the
            // the record is deleted it doesn't get added.
            HibernateUtil.setListener("post-commit-delete", new edu.ku.brc.specify.dbsupport.PostDeleteEventListener());
            //HibernateUtil.setListener("delete", new edu.ku.brc.specify.dbsupport.DeleteEventListener());
        }
        dbLoginPanel = UIHelper.doLogin(true, false, false, this); // true means do auto login if it can, second bool means use dialog instead of frame
        localPrefs.load();
    }
    
    /**
     * Setup all the System properties. This names all the needed factories. 
     */
    protected void setUpSystemProperties()
    {
        // Name factories
        System.setProperty(AppContextMgr.factoryName,                   "edu.ku.brc.specify.config.SpecifyAppContextMgr");      // Needed by AppContextMgr
        System.setProperty(AppPreferences.factoryName,                  "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");         // Needed by AppReferences
        System.setProperty("edu.ku.brc.ui.ViewBasedDialogFactoryIFace", "edu.ku.brc.specify.ui.DBObjDialogFactory");            // Needed By UICacheManager
        System.setProperty("edu.ku.brc.ui.forms.DraggableRecordIdentifierFactory", "edu.ku.brc.specify.ui.SpecifyDraggableRecordIdentiferFactory"); // Needed By the Form System
        System.setProperty("edu.ku.brc.dbsupport.AuditInterceptor",     "edu.ku.brc.specify.dbsupport.AuditInterceptor");       // Needed By the Form System for updating Lucene and logging transactions
        System.setProperty("edu.ku.brc.dbsupport.DataProvider",         "edu.ku.brc.specify.dbsupport.HibernateDataProvider");  // Needed By the Form System and any Data Get/Set
        System.setProperty("edu.ku.brc.ui.db.PickListDBAdapterFactory", "edu.ku.brc.specify.ui.db.PickListDBAdapterFactory");   // Needed By the Auto Cosmplete UI
        System.setProperty("edu.ku.brc.ui.db.TreeFinderFactory",        "edu.ku.brc.specify.treeutils.TreeFinderFactoryImpl");  // needed for treequerycbx components
        System.setProperty(CustomQueryFactory.factoryName,              "edu.ku.brc.specify.dbsupport.SpecifyCustomQueryFactory");
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
        
 
        TaskMgr.readRegistry();
        
        TaskMgr.initializePlugins();

        validate();

        add(mainPanel, BorderLayout.CENTER);
        doLayout();

        mainPanel.setBackground(Color.WHITE);

        JToolBar toolBar = (JToolBar)UICacheManager.get(UICacheManager.TOOLBAR);
        if (toolBar != null && toolBar.getComponentCount() < 2)
        {
            toolBar.setVisible(false);
        }
        
        TaskMgr.requestInitalContext();

        showApp();
    }

    /**
     * Determines if this is an applet or application
     */
    public boolean isApplet()
    {
        return false;
    }

    /**
     * Show the spash screen while the rest of the demo loads
     */
    public void createSplashScreen()
    {
        specifyImageIcon = new ImageIcon(Specify.class.getResource("images/specify_splash.gif"));
        splashLabel = new JLabel(specifyImageIcon);
        if(!isApplet())
        {
            splashWindow = new JWindow(getFrame());
            splashWindow.getContentPane().add(splashLabel);
            splashWindow.getContentPane().setBackground(Color.WHITE);
            splashWindow.pack();
            Dimension scrSize = getToolkit().getScreenSize();// getFrame().getGraphicsConfiguration().getBounds();
            splashWindow.setLocation(scrSize.width/2 - splashWindow.getSize().width/2,
                                     scrSize.height/2 - splashWindow.getSize().height/2);
            /*
            specifyspecifyImageIcon = new ImageIcon(Specify.class.getResource("images/specify_splash.gif"));
            JPanel panel = new JPanel() {

                public void paintComponent(Graphics g)
                {
                    if (specifyspecifyImageIcon != null)
                    {
                        g.drawImage(specifyspecifyImageIcon.getImage(),0,0,null);
                        //g.setColor(Color.BLACK);
                       // g.draw3DRect(0,0,199,199, true);
                        //System.out.println("Paint RECT");

                    }
                }
                public Dimension getSize()
                {
                    //return new Dimension(200,200);
                    return new Dimension(specifyspecifyImageIcon.getIconWidth(), specifyspecifyImageIcon.getIconHeight());
                }
                public Dimension getPreferredSize()
                {
                    return getSize();
                }
            };
            panel.setOpaque(false);
            JFrame splashFrame = new JFrame("Transparent Window");
            splashScreen = new TransparentBackground(splashFrame, specifyspecifyImageIcon);
            splashFrame.setSize(specifyspecifyImageIcon.getIconWidth(), specifyspecifyImageIcon.getIconHeight());
            splashScreen.setSize(specifyspecifyImageIcon.getIconWidth(), specifyspecifyImageIcon.getIconHeight());

            //splashScreen.setLayout(new BorderLayout());
            //splashScreen.add(panel, BorderLayout.CENTER);

            splashFrame.setUndecorated(true);
            //splashFrame.getContentPane().setLayout(new BorderLayout());
            //splashFrame.getContentPane().add(splashScreen, BorderLayout.CENTER);
            splashFrame.pack();
            Dimension scrSize = getToolkit().getScreenSize();// getFrame().getGraphicsConfiguration().getBounds();
            splashFrame.setLocation(scrSize.width/2 - splashFrame.getSize().width/2,
                                    scrSize.height/2 - splashFrame.getSize().height/2);
            System.out.println(splashFrame.getLocation());
            System.out.println(splashFrame.getSize());
            splashFrame.setVisible(true);
            */
        }

    }

    public void showSplashScreen()
    {
        if (!isApplet())
        {
            if (splashWindow != null)
            {
                splashWindow.setVisible(true);
                splashWindow.validate();
                splashWindow.repaint();
            }
            //splashScreen.getFrame().setVisible(true);
        } else
        {
            add(splashLabel, BorderLayout.CENTER);
            validate();
            repaint();
        }
    }

    /**
     * pop down the spash screen
     */
    public void hideSplash()
    {
        if (!isApplet() && splashWindow != null)
        {
            //splashScreen.hideAll();
            splashWindow.setVisible(false);
            splashWindow = null;
            splashLabel = null;
        }
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
        topFrame.setIconImage( IconManager.getImage("Specify16", IconManager.IconSize.Std16).getImage() );
        //topFrame.setAlwaysOnTop(true);
        
        topFrame.setGlassPane(glassPane = new GhostGlassPane());
        topFrame.setLocationRelativeTo(null);
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        UICacheManager.register(UICacheManager.GLASSPANE, glassPane);

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);

        UICacheManager.register(UICacheManager.TOPFRAME, topFrame);

        menuBar = createMenus();
        if (menuBar != null)
        {
            //top.add(menuBar, BorderLayout.NORTH);
            topFrame.setJMenuBar(menuBar);
        }
        UICacheManager.register(UICacheManager.MENUBAR, menuBar);


        JToolBar toolBar = createToolBar();
        if (toolBar != null)
        {
            top.add(toolBar, BorderLayout.CENTER);
        }
        UICacheManager.register(UICacheManager.TOOLBAR, toolBar);

        mainPanel = new MainPanel();

        int[] sections = {30, 50};
        statusField = new JStatusBar(sections);
        statusField.setErrorIcon(IconManager.getIcon("Error",IconManager.IconSize.Std16));
        statusField.setWarningIcon(IconManager.getIcon("Warning", IconManager.IconSize.Std16));
        UICacheManager.register(UICacheManager.STATUSBAR, statusField);

        add(statusField, BorderLayout.SOUTH);

    }

    /**
     *
     * @return the toolbar for the app
     */
    public JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new ToolbarLayoutManager(2, 2));

        return toolBar;
    }

    /**
     * Create menus
     */
    public void preferences()
    {

        JDialog dlg = new JDialog();
        dlg.setModal(true);
        PrefMainPanel pane = new PrefMainPanel(dlg);
        dlg.setContentPane(pane);
        dlg.pack();
        dlg.doLayout();
        dlg.setPreferredSize(dlg.getPreferredSize());
        dlg.setSize(dlg.getPreferredSize());
        UIHelper.centerAndShow(dlg);
    }

    /**
     * Create menus
     */
    public JMenuBar createMenus()
    {
        JMenuBar mb = new JMenuBar();
        JMenuItem mi;

        JMenu menu = UIHelper.createMenu(mb, "FileMenu", "FileMneu");
        mi = UIHelper.createMenuItem(menu, "Login", "L", "Database Login", true, null);
        mi.addActionListener(new ActionListener()
                {
                    @SuppressWarnings("synthetic-access")
                    public void actionPerformed(ActionEvent ae)
                    {
                        class DBListener implements DatabaseLoginListener
                        {
                            @SuppressWarnings("synthetic-access")
                            public void loggedIn(final String databaseNameArg, final String userNameArg)
                            {
                                specifyApp.loggedIn(databaseNameArg, userNameArg);
                            }

                            public void cancelled()
                            {
                                // Do not call this it will exit the application
                                //specifyApp.cancelled();
                            }
                        }

                        if (SubPaneMgr.getInstance().aboutToShutdown())
                        {
                            // Make sure the prefs are saved before logging out and loggin back in.
                            try
                            {
                                AppPreferences.getLocalPrefs().flush();
                                AppPreferences.getRemote().flush();
                                
                            } catch (BackingStoreException ex)
                            {
                                log.error(ex);
                            }
                            
                            AppPreferences.setConnectedToDB(false);
                            UIHelper.doLogin(false, true, true, new DBListener()); // true means do auto login if it can, second bool means use dialog instead of frame
                        }
                    }
                });

        if (!isWorkbenchOnly)
        {
            // Add Menu for switching CatalogSeries
            changeCatSeriesBtn = UIHelper.createMenuItem(menu, "Change Catalog Series", "C", "Change Catalog Series", false, null);
            changeCatSeriesBtn.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            if (SubPaneMgr.getInstance().aboutToShutdown())
                            {
                               
                                // Actually we really need to start over
                                // "true" means that it should NOT use any cached values it can find to automatically initialize itself
                                // instead it should ask the user any questions as if it were starting over
                                restartApp(databaseName, userName, true, false);
                            }
                        }
                    });
    
            changeCatSeriesBtn.setEnabled(((SpecifyAppContextMgr)AppContextMgr.getInstance()).getNumOfCatalogSeriesForUser() > 1);
        }

        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            menu.addSeparator();
            mi = UIHelper.createMenuItem(menu, "Exit", "x", "Exit Appication", true, null);
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            doExit();
                        }
                    });
        }
       
        menu = UICacheManager.getInstance().createEditMenu();
        mb.add(menu);
        //menu = UIHelper.createMenu(mb, "EditMenu", "EditMneu");
        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            menu.addSeparator();
            mi = UIHelper.createMenuItem(menu, "Preferences", "P", "Preferences", false, null);
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            preferences();
                        }
                    });
        }
                


        /*JMenuItem mi2;
        JMenu fileMenu2 = (JMenu) mb.add(new JMenu("Log off"));


        fileMenu2.setMnemonic('O');
        mi2 = UIHelper.createMenuItem(fileMenu2, "Log off", "O", "Log off database", false, null);
        mi2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        if (hasChanged)
                        {

                        }
                        try {
                            if (mSessionFactory != null)
                            {
                                mSessionFactory.close();
                            }
                            if (mSession != null)
                            {
                                mSession.close();
                            }
                        } catch (Exception e)
                        {
                            log.error("UIHelper.createMenus - ", e);
                        }
                        //frame.dispose();
                        final Window parentWindow = SwingUtilities.getWindowAncestor(Specify.this);
                        parentWindow.dispose();
                        Specify ha = new Specify(grc);
                    }
                });
        */
        
        if (!isWorkbenchOnly)
        {
            menu = UIHelper.createMenu(mb, "AdvMenu", "AdvMneu");
            mi = UIHelper.createMenuItem(menu, getResourceString("ESConfig"), getResourceString("ESConfig_Mn"), getResourceString("ESConfig"), false, null);
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            ExpressSearchTask expressSearchTask = (ExpressSearchTask)ContextMgr.getTaskByName(ExpressSearchTask.EXPRESSSEARCH);
                            expressSearchTask.showIndexerPane();
                        }
                    });
            
            menu.add(UIHelper.createMenu(mb, "SystemMenu", "SystemMneu"));
        }


        menu = UIHelper.createMenu(mb, "TabsMenu", "TabsMneu");
        /*mi = UIHelper.createMenuItem(menu, "Close Current", "C", "Close C", false, null);
        mi.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        SubPaneMgr.getInstance().closeCurrent();
                    }
                });*/

        Action closeAll = new AbstractAction() {
            public void actionPerformed(ActionEvent ae)
            {
                SubPaneMgr.getInstance().closeAll();
            }
        };
        mi = UIHelper.createMenuItemWithAction(menu, "Close All", "A", "Close All", false, closeAll);
        UICacheManager.registerAction("CloseAll", closeAll);

        if (!isRelease)
        {
            menu = UIHelper.createMenu(mb, "DebugMenu", "DebugMneu");
            mi = UIHelper.createMenuItem(menu, "Show Local Prefs", "L", "Show Local Prefs", false, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access")
                        public void actionPerformed(ActionEvent ae)
                        {
                            final JDialog dialog = new JDialog(topFrame, "Local Prefs", true);
                            dialog.setContentPane(new AppPrefsEditor(false));
                            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            dialog.pack();
                            UIHelper.centerAndShow(dialog);
                        }
                    });
    
            mi = UIHelper.createMenuItem(menu, "Show Remote Prefs", "R", "Show Remote Prefs", false, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access")
                        public void actionPerformed(ActionEvent ae)
                        {
                            final JDialog dialog = new JDialog(topFrame, "Remote Prefs", true);
                            dialog.setContentPane(new AppPrefsEditor(true));
                            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            dialog.pack();
                            UIHelper.centerAndShow(dialog);
                        }
                    });
    
            menu.addSeparator();
            JCheckBoxMenuItem cbMenuItem = new JCheckBoxMenuItem("Reload Views");
            menu.add(cbMenuItem);
            //mi = UIHelper.createMenuItem(menu, "Reload Views", "V", "Reload Views", false, null);
            cbMenuItem.setSelected(AppPreferences.getLocalPrefs().getBoolean("reload_views", false));
            cbMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae)
                        {
                            boolean isReload = !AppPreferences.getLocalPrefs().getBoolean("reload_views", false);                       
                            AppPreferences.getLocalPrefs().putBoolean("reload_views", isReload);
                            ((JMenuItem)ae.getSource()).setSelected(isReload);
                        }});
    
            mi = UIHelper.createMenuItem(menu, "Config Loggers", "C", "Config Logger", false, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access")
                        public void actionPerformed(ActionEvent ae)
                        {
                            final LoggerDialog dialog = new LoggerDialog(topFrame);
                            UIHelper.centerAndShow(dialog);
                        }
                    });
    
            JMenu prefsMenu = new JMenu("Prefs Import/Export");
            menu.add(prefsMenu);
            mi = UIHelper.createMenuItem(prefsMenu, "Import", "I", "Import Prefs", false, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access")
                        public void actionPerformed(ActionEvent ae)
                        {
                            importPrefs();
                        }
                    });
    
            mi = UIHelper.createMenuItem(prefsMenu, "Export", "E", "Export Prefs", false, null);
            mi.addActionListener(new ActionListener()
                    {
                        @SuppressWarnings("synthetic-access")
                        public void actionPerformed(ActionEvent ae)
                        {
                            exportPrefs();
                        }
                    });
        }
        return mb;
    }

    /**
     * Checks to see if cache has changed before exiting.
     */
    protected void doAbout()
    {

        PanelBuilder    builder    = new PanelBuilder(new FormLayout("l:p:g,30px,r:p:g", "f:p:g"));
        CellConstraints cc         = new CellConstraints();

        builder.add(new JLabel("Specify 6.0"), cc.xy(1,1));
        builder.add(new JLabel(IconManager.getIcon("SpecifyLargeIcon")), cc.xy(3,1));

        final JDialog dialog = new JDialog(topFrame, "About Specify 6.0", true);
        //dialog.setContentPane(builder.getPanel());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Specify 6.0"), BorderLayout.WEST);
        panel.add(new JLabel(IconManager.getIcon("SpecifyLargeIcon")), BorderLayout.EAST);
        dialog.setContentPane(panel);

        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //dialog.validate();
        //dialog.setSize(dialog.getPreferredSize());
        dialog.pack();
        UIHelper.centerAndShow(dialog);
    }

    /**
     * Checks to see if cache has changed before exiting.
     *
     */
    protected void doExit()
    {
        if (AttachmentUtils.getAttachmentManager() != null)
        {
            AttachmentUtils.getAttachmentManager().cleanup();
        }
        
        if (SubPaneMgr.getInstance().aboutToShutdown())
        {
    		log.info("Application shutdown");
    
    		// save the long term cache mapping info
    		try
    		{
    			UICacheManager.getLongTermFileCache().saveCacheMapping();
    			log.info("Successfully saved long term cache mapping");
    		}
    		catch( IOException e1 )
    		{
    			log.warn("Error while saving long term cache mapping.",e1);
    		}
    
            if (topFrame != null)
            {
                topFrame.setVisible(false);
            }
            System.exit(0);
        }
    }

    /**
     * Bring up the PPApp demo by showing the frame (only applicable if coming up
     * as an application, not an applet);
     */
    public void showApp()
    {
        // put PPApp in a frame and show it
        JFrame f = getFrame();
        f.setTitle("Specify 6.0");
        f.getContentPane().add(this, BorderLayout.CENTER);
        f.pack();

        f.addWindowListener(new WindowAdapter()
        		{
        			@Override
                    public void windowClosing(WindowEvent e)
        			{
        				doExit();
        			}
        		});
        UIHelper.centerAndShow(f);
        
        //hideSplash();
    }

    /**
     * Returns the frame instance
     */
    public JFrame getFrame()
    {
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
            @SuppressWarnings("synthetic-access")
            @Override
            public void run()
            {
                mApp.statusField.setText((String) obj);
            }
        });
    }
    
    
    /**
     * Restarts the app with a new or old database and user name and creates the core app UI.
     * @param databaseNameArg the database name
     * @param userNameArg the user name
     * @param startOver tells the AppContext to start over
     * @param firstTime indicates this is the first time in the app and it should create all the UI for the core app
     */
    public void restartApp(final String databaseNameArg, final String userNameArg, final boolean startOver, final boolean firstTime)
    {
        if (dbLoginPanel != null)
        {
            dbLoginPanel.getStatusBar().setText(getResourceString("InitializingApp"));
        }
        
        AppPreferences.shutdownRemotePrefs();
        //moved here because context needs to be set before loading prefs, we need to know the SpecifyUser
        AppContextMgr.CONTEXT_STATUS status = AppContextMgr.getInstance().setContext(databaseNameArg, userNameArg, startOver);
        SpecifyAppPrefs.initialPrefs();

        
        //CatalogSeries.setCurrentCatalogSeries(null);
        //CollectionObjDef.setCurrentCollectionObjDef(null);
        
        // "false" means that it should use any cached values it can find to automatically initialize itself
        //AppContextMgr.CONTEXT_STATUS status = AppContextMgr.getInstance().setContext(databaseNameArg, userNameArg, startOver);
        if (status == AppContextMgr.CONTEXT_STATUS.OK)
        { 
            if (firstTime)
            {
                GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
                
                initialize(gc);
    
                topFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                UICacheManager.register(UICacheManager.FRAME, topFrame);
                
                ((JMenuBar)UICacheManager.get(UICacheManager.MENUBAR)).add(HelpMgr.createHelpMenuItem("Specify"));
            }
            
            initStartUpPanels(databaseNameArg, userNameArg);
            
            if (changeCatSeriesBtn != null)
            {
                changeCatSeriesBtn.setEnabled(((SpecifyAppContextMgr)AppContextMgr.getInstance()).getNumOfCatalogSeriesForUser() > 1);
            }
            
        } else if (status == AppContextMgr.CONTEXT_STATUS.Error)
        {

            if (dbLoginPanel != null)
            {
                dbLoginPanel.getWindow().setVisible(false);
            }
            
            if (CatalogSeries.getCurrentCatalogSeries().size() == 0)
            {
                
                // TODO This is really bad because there is a Database Login with no Specify login
                JOptionPane.showMessageDialog(null, 
                                              getResourceString("LoginUserMismatch"), 
                                              getResourceString("LoginUserMismatchTitle"), 
                                              JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        
        }
        
        CommandDispatcher.dispatch(new CommandAction("App", "Restart", null));
        
        if (dbLoginPanel != null)
        {
            dbLoginPanel.getWindow().setVisible(false);
            dbLoginPanel = null;
        }
    }
    
    protected void importPrefs()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showDialog(null, "Select File or Directory") != JFileChooser.CANCEL_OPTION) // XXX I18N
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
            throw new NoSuchElementException("The External File Repository needs a valid directory.");// XXX LOCALIZE
        } 
    }

    protected void exportPrefs()
    {
        AppPreferences remotePrefs = AppPreferences.getRemote();
        Properties     props       = remotePrefs.getProperties();
        try
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (chooser.showDialog(null, "Select File or Directory") != JFileChooser.CANCEL_OPTION) // XXX LOCALIZE
            {
                File destFile = chooser.getSelectedFile();
                props.store(new FileOutputStream(destFile), "User Prefs");
            } else 
            {
                throw new NoSuchElementException("The External File Repository needs a valid directory.");// XXX LOCALIZE
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
     * @see edu.ku.brc.ui.db.DatabaseLoginListener#loggedIn(java.lang.String, java.lang.String)
     */
    public void loggedIn(final String databaseNameArg, final String userNameArg)
    {
        boolean firstTime = this.databaseName == null;
        
        this.databaseName = databaseNameArg;
        this.userName     = userNameArg;
        
        
        restartApp(databaseName, userName, false, firstTime);
        
        statusField.setSectionText(0, userName);
        statusField.setSectionText(1, databaseName);
        
        AppPreferences.setConnectedToDB(true);

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.DatabaseLoginListener#cancelled()
     */
    public void cancelled()
    {
        System.exit(0);
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

  /**
   *
   */
  public static void main(String[] args)
  {
      // Create Specify Application
      SwingUtilities.invokeLater(new Runnable() {
          public void run()
          {
              MemoryWarningSystem.setPercentageUsageThreshold(0.75);

              MemoryWarningSystem mws = new MemoryWarningSystem();
              mws.addListener(new MemoryWarningSystem.Listener()
              {
                  protected void setMessage(final String msg, final boolean isError)
                  {
                      JStatusBar statusBar = (JStatusBar)UICacheManager.get(UICacheManager.STATUSBAR);
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
                          System.err.println(msg);
                      }
                  }
                  
                  public void memoryUsage(long usedMemory, long maxMemory)
                  {
                      double percentageUsed = ((double) usedMemory) / maxMemory;
                      
                      String msg = String.format("Percent Memory Used %6.2f of Max %d", new Object[] {(percentageUsed * 100.0), maxMemory});
                      setMessage(msg, false);

                  }

                  public void memoryUsageLow(long usedMemory, long maxMemory)
                  {
                      double percentageUsed = ((double) usedMemory) / maxMemory;
                        
                      String msg = String.format("Memory is Low! Percentage Used = %6.2f of Max %d", new Object[] {(percentageUsed * 100.0), maxMemory});
                      setMessage(msg, true);
                        
                      if (MemoryWarningSystem.getThresholdPercentage() < 0.8)
                      {
                          MemoryWarningSystem.setPercentageUsageThreshold(0.8);
                      }
                    }
                });
              
              try
              {
                  if (!System.getProperty("os.name").equals("Mac OS X"))
                  {
                      UIManager.setLookAndFeel(new PlasticLookAndFeel());
                      PlasticLookAndFeel.setPlasticTheme(new SkyKrupp());
                  }
              }
              catch (Exception e)
              {
                  log.error("Can't change L&F: ", e);
              }
              
              
              HelpMgr.initializeHelp("SpecifyHelp");
              
              // Startup Specify
              Specify specify = new Specify();
              
              
              boolean startAsWorkBench = false; // XXX Workbench Testing (start up testing)
              if (startAsWorkBench)
              {
                  specify.startWithInitializer(); // For a WorkBench Only Release  
                  
              } else
              {
                  // THis type of start up ALWAYS assumes the .Specify directory is in there "home" directory.
                  specify.preStartUp();
                  specify.startUp();    
              }
          }
      });

  }
}


/*
 * Copyright (C) 2008  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.specify.config.init;

import static edu.ku.brc.ui.UIHelper.createButton;
import static edu.ku.brc.ui.UIRegistry.getLocalizedMessage;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;

import edu.ku.brc.af.auth.SecurityMgr;
import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.FrameworkAppIFace;
import edu.ku.brc.af.core.MacOSAppHandler;
import edu.ku.brc.af.core.RecordSetFactory;
import edu.ku.brc.af.core.SchemaI18NService;
import edu.ku.brc.af.core.db.BackupServiceFactory;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.ui.forms.formatters.DataObjFieldFormatMgr;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.af.ui.weblink.WebLinkMgr;
import edu.ku.brc.dbsupport.CustomQueryFactory;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DatabaseDriverInfo;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.Specify;
import edu.ku.brc.specify.ui.HelpMgr;
import edu.ku.brc.specify.utilapps.BuildSampleDatabase;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.IconManager.IconSize;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Oct 15, 2008
 *
 */
public class SpecifyDBSetupWizard extends JFrame implements FrameworkAppIFace
{
    private static final Logger log = Logger.getLogger(SpecifyDBSetupWizard.class);
    
    protected boolean                assumeDerby = false;
    protected final String           HOSTNAME = "localhost";
    protected boolean                doLoginOnly = false;
    
    protected Properties             props = new Properties();
    
    protected JButton                helpBtn;
    protected JButton                backBtn;
    protected JButton                nextBtn;
    protected JButton                cancelBtn;
    
    protected DatabasePanel          userPanel;
    protected DBLocationPanel        locationPanel;
    
    protected int                    step     = 0;
    protected int                    lastStep = 3;
    
    protected boolean                isCancelled;
    protected JPanel                 cardPanel;
    protected CardLayout             cardLayout = new CardLayout();
    protected Vector<BaseSetupPanel> panels     = new Vector<BaseSetupPanel>();
    
    protected String                 setupXMLPath;
    
    /**
     * @param specify
     */
    public SpecifyDBSetupWizard()
    {
        super();
        
        new MacOSAppHandler(this);
        
        // Now initialize
        AppPreferences localPrefs = AppPreferences.getLocalPrefs();
        localPrefs.setDirPath(UIRegistry.getDefaultWorkingPath());

        ImageIcon helpIcon = IconManager.getIcon("WizardIcon",IconSize.Std16); //$NON-NLS-1$
        HelpMgr.initializeHelp("SpecifyHelp", helpIcon.getImage()); //$NON-NLS-1$
        
        UIRegistry.loadAndPushResourceBundle("specifydbsetupwiz");
        
        setupXMLPath = UIRegistry.getUserHomeAppDir() + File.separator + "setup_prefs.xml";
        try
        {
            props.loadFromXML(new FileInputStream(new File(setupXMLPath)));
            
        } catch (Exception ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, ex);
            
        }
        
        setIconImage(IconManager.getIcon("WizardIcon", IconManager.IconSize.Std16).getImage());
        
        setTitle(getResourceString("MAIN_TITLE"));
        cardPanel = new JPanel(cardLayout);
        
        cancelBtn  = createButton(UIRegistry.getResourceString("EXIT"));
        helpBtn    = createButton(UIRegistry.getResourceString("HELP"));
        
        JPanel btnBar;
        backBtn    = createButton(UIRegistry.getResourceString("BACK"));
        nextBtn    = createButton(UIRegistry.getResourceString("NEXT"));
        
        HelpMgr.registerComponent(helpBtn, "SetupSpecifyDB");
        CellConstraints cc = new CellConstraints();
        PanelBuilder bbpb = new PanelBuilder(new FormLayout("f:p:g,p,4px,p,4px,p,4px,p,4px", "p"));
        bbpb.add(helpBtn, cc.xy(2,1));
        bbpb.add(backBtn, cc.xy(4,1));
        bbpb.add(nextBtn, cc.xy(6,1));
        bbpb.add(cancelBtn, cc.xy(8,1));
        btnBar = bbpb.getPanel();

        boolean doTesting = true;
        if (doTesting)
        {
            props.put("hostName",   "localhost");
            props.put("dbName",     "testfish");
            props.put("dbUserName", "Specify");
            props.put("dbPassword", "Specify");
            
            props.put("saUserName", "Master");
            props.put("saPassword", "Master");
            
            props.put("firstName", "Test");
            props.put("lastName",  "User");
            props.put("middleInitial", "a");
            props.put("email", "tester@ku.edu");
            props.put("usrUsername", "testuser");
            props.put("usrPassword", "testuser");
    
            props.put("instName", "KU natural History Museum");
            props.put("instAbbrev", "KU-NHM");
    
            props.put("divName", "Fish");
            props.put("divAbbrev", "IT");
    
            props.put("collPrefix", "KUFSH");
            props.put("collName", "Fish");
            
            // Address
            props.put("addr1", "1345 Jayhawk Blvd");
            props.put("addr2", "606 Dyche Hall");
            props.put("city", "Lawrence");
            props.put("state", "KS");
            props.put("country", "USA");
            props.put("zip", "66044");
            props.put("phone", "785-864-5555");
        }

        props.put("userType", "CollectionManager");
        
        userPanel = new DatabasePanel(nextBtn, true);
        panels.add(userPanel);
               
        UIFieldFormatterMgr.setDoingLocal(true);
        
        panels.add(new GenericFormPanel("sa", 
                "ENTER_SA_INFO", 
                new String[] { "SA_USERNAME", "SA_PASSWORD"}, 
                new String[] { "saUserName", "saPassword"}, 
                nextBtn));

        panels.add(new GenericFormPanel("agent", 
                "ENTER_COLMGR_INFO", 
                new String[] { "FIRSTNAME", "LASTNAME", "MIDNAME",       "EMAIL", "USERLOGININFO", "USERNAME", "PASSWORD"}, 
                new String[] { "firstName", "lastName", "middleInitial", "email", "-", "usrUsername", "usrPassword"}, 
                nextBtn));
         
        panels.add(new GenericFormPanel("inst", 
                "ENTER_INST_INFO",
                new String[] { "NAME",     "ABBREV"}, 
                new String[] { "instName", "instAbbrev"}, 
                nextBtn));
        
        panels.add(new GenericFormPanel("addr", 
                "ENTER_ADDR_INFO",
                new String[] { "ADDR1", "ADDR2", "CITY",  "STATE", "COUNTRY", "ZIP", "PHONE"}, 
                new String[] { "addr1", "addr2", "city", "state", "country", "zip", "phone"}, 
                new boolean[] {true, false, true, true, true, true, true},
                nextBtn));
        
        panels.add(new GenericFormPanel("div", 
                "ENTER_DIV_INFO",
                new String[] { "NAME",    "ABBREV"}, 
                new String[] { "divName", "divAbbrev"}, 
                nextBtn));
         
        panels.add(new GenericFormPanel("collection", 
                "ENTER_COL_INFO",
                new String[] { "PREFIX", "NAME"}, 
                new String[] { "collPrefix", "collName"}, 
                nextBtn));
        
        panels.add(new FormatterPickerPanel(nextBtn, true));
        panels.add(new FormatterPickerPanel(nextBtn, false));
         
         
        lastStep = panels.size();
        
        if (backBtn != null)
        {
            backBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae)
                {
                    if (step > 0)
                    {
                        step--;
                        cardLayout.show(cardPanel, Integer.toString(step));
                    }
                    updateBtnBar();
                }
            });
            
            backBtn.setEnabled(false);
        }
        
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                if (step < lastStep-1)
                {
                    step++;
                    cardLayout.show(cardPanel, Integer.toString(step));
                    updateBtnBar();
                      
                } else
                {
                    setVisible(false);
                    configureDatabase();
                    dispose();
                }
            }
        });
        
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                isCancelled = true;
                setVisible(false);
                dispose();
                doExit(true);
            }
         });

        for (int i=0;i<panels.size();i++)
        {
            cardPanel.add(Integer.toString(i), panels.get(i));
            panels.get(i).setValues(props);
        }
        cardLayout.show(cardPanel, "0");
        
        userPanel.updateBtnUI();

        PanelBuilder    builder = new PanelBuilder(new FormLayout("f:p:g", "f:p:g,10px,p"));
        builder.add(cardPanel, cc.xy(1, 1));
        builder.add(btnBar, cc.xy(1, 3));
        
        builder.setDefaultDialogBorder();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        PanelBuilder    iconBldr    = new PanelBuilder(new FormLayout("8px, f:p:g,130px,f:p:g", "8px,f:p:g,130px,f:p:g, 8px"));
        iconBldr.add(new JLabel(IconManager.getIcon("WizardIcon")), cc.xy(3, 3));
        mainPanel.add(iconBldr.getPanel(), BorderLayout.WEST);
        mainPanel.add(builder.getPanel(), BorderLayout.CENTER);
            
        
        setContentPane(mainPanel);
        
        JMenuBar menuBar = createMenus();
        if (menuBar != null)
        {
            setJMenuBar(menuBar);
        }
        UIRegistry.register(UIRegistry.MENUBAR, menuBar);
        
        pack();
    }
    
    /**
     * 
     */
    protected void updateBtnBar()
    {
        if (step == lastStep-1)
        {
            nextBtn.setEnabled(panels.get(step).isUIValid());
            nextBtn.setText("Finished");
            
        } else
        {
            nextBtn.setEnabled(panels.get(step).isUIValid());
            nextBtn.setText("Next");
        }
        
        backBtn.setEnabled(step > 0); 
    }
    
    /**
     * @param path
     * @return
     */
    protected String stripSpecifyDir(final String path)
    {
        String appPath = path;
        int endInx = appPath.indexOf("Specify.app");
        if (endInx > -1)
        {
            appPath = appPath.substring(0, endInx-1);
        }
        return appPath;
    }

    /**
     * 
     */
    public void configureDatabase()
    {
        try
        {
            for (SetupPanelIFace panel : panels)
            {
                panel.getValues(props);
            }
            props.storeToXML(new FileOutputStream(new File(setupXMLPath)), "SetUp Props");
            
        } catch (Exception ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, ex);
            
        }
        
        // Clear and Reset Everything!
        AppPreferences.shutdownLocalPrefs();
        //UIRegistry.setDefaultWorkingPath(null);
        
        log.debug("********** WORK["+UIRegistry.getDefaultWorkingPath()+"]");
        log.debug("********** USER LOC["+stripSpecifyDir(UIRegistry.getAppDataDir())+"]");
        
        String baseAppDir;
        if (UIHelper.getOSType() == UIHelper.OSTYPE.MacOSX)
        {
            baseAppDir = stripSpecifyDir(UIRegistry.getAppDataDir());
            
        } else
        {
            baseAppDir = UIRegistry.getDefaultWorkingPath();
        }
        
        baseAppDir = UIHelper.stripSubDirs(baseAppDir, 1);
        UIRegistry.setDefaultWorkingPath(baseAppDir);
        
        log.debug("********** Working path for App ["+baseAppDir+"]");
        
       //System.err.println(UIRegistry.getDefaultWorkingPath() + File.separator + "DerbyDatabases");
        try
        {
            final SwingWorker worker = new SwingWorker()
            {
                protected boolean isOK = false;
                
                public Object construct()
                {
                    try
                    {
                        DatabaseDriverInfo driverInfo = userPanel.getDriver();
                        props.put("driver", driverInfo);
                        
                        if (driverInfo == null)
                        {
                            throw new RuntimeException("Couldn't find driver by name ["+driverInfo+"] in driver list.");
                        }

                        BuildSampleDatabase builder = new BuildSampleDatabase();
                        
                        //builder.getFrame().setIconImage(IconManager.getImage("Specify16", IconManager.IconSize.Std16).getImage());
                        
                        props.put("disciplineType", userPanel.getDisciplineType());
                        
                        boolean proceed = true;
                        if (checkForDatabase(props))
                        {
                            Object[] options = { 
                                    getResourceString("PROCEED"), 
                                    getResourceString("EXIT")
                                  };
                            int userChoice = JOptionPane.showOptionDialog(UIRegistry.getTopWindow(), 
                                                                         getResourceString("DEL_CUR_DB"), 
                                                                         getResourceString("DEL_CUR_DB_TITLE"), 
                                                                         JOptionPane.YES_NO_OPTION,
                                                                         JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            proceed = userChoice == JOptionPane.YES_OPTION;
                            
                        } 

                        if (proceed)
                        {
                            isOK = builder.buildEmptyDatabase(props);

                            JOptionPane.showMessageDialog(UIRegistry.getTopWindow(), 
                                    getLocalizedMessage("BLD_DONE", getResourceString(isOK ? "BLD_OK" :"BLD_NOTOK")),
                                    getResourceString("COMPLETE"), JOptionPane.INFORMATION_MESSAGE);                                
                        }
                        
                        UIRegistry.popResourceBundle();
                            
                        } catch (Exception ex)
                        {
                            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, ex);
                            ex.printStackTrace();
                        }
                        return null;
                    }

                    //Runs on the event-dispatching thread.
                    public void finished()
                    {
                        if (isOK)
                        {
                            HibernateUtil.shutdown();
                        }
                        System.exit(0);
                    }
                };
                worker.start();
            
            } catch (Exception ex)
            {
                edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, ex);
                ex.printStackTrace();
            }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.FrameworkAppIFace#doExit(boolean)
     */
    public boolean doExit(boolean doAppExit)
    {
        System.exit(0);
        return true;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.FrameworkAppIFace#doAbout()
     */
    public void doAbout()
    {
        Specify specify = new Specify();
        specify.doAbout();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.FrameworkAppIFace#doPreferences()
     */
    public void doPreferences()
    {
        
    }
    
    /**
     * @return
     */
    public JMenuBar createMenus()
    {
        JMenuBar mb = new JMenuBar();
        JMenuItem mi;

        //--------------------------------------------------------------------
        //-- File Menu
        //--------------------------------------------------------------------

        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            JMenu menu = UIHelper.createLocalizedMenu(mb, "Specify.FILE_MENU", "Specify.FILE_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
            
            menu.addSeparator();
            String title = "Specify.EXIT"; //$NON-NLS-1$
            String mnu = "Specify.Exit_MNEU"; //$NON-NLS-1$
            mi = UIHelper.createLocalizedMenuItem(menu, title, mnu, title, true, null); 
            mi.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            doExit(true);
                        }
                    });
        }
        
        JMenu helpMenu = UIHelper.createLocalizedMenu(mb, "Specify.HELP_MENU", "Specify.HELP_MNEU"); //$NON-NLS-1$ //$NON-NLS-2$
        HelpMgr.createHelpMenuItem(helpMenu, "Specify"); //$NON-NLS-1$
        helpMenu.addSeparator();
        
        if (UIHelper.getOSType() != UIHelper.OSTYPE.MacOSX)
        {
            String ttle = "Specify.ABOUT";//$NON-NLS-1$ 
            String mneu = "Specify.ABOUTMNEU";//$NON-NLS-1$ 
            String desc = "Specify.ABOUT";//$NON-NLS-1$ 
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
     * @param properties
     * @return
     */
    private boolean checkForDatabase(final Properties properties)
    {
        final String dbName = properties.getProperty("dbName");
        
        DatabaseDriverInfo driverInfo = (DatabaseDriverInfo)properties.get("driver");
        
        try
        {
            
            String connStr = driverInfo.getConnectionStr(DatabaseDriverInfo.ConnectionType.Create, 
                                                         properties.getProperty("hostName"), 
                                                         dbName);
            if (connStr == null)
            {
                connStr = driverInfo.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, properties.getProperty("hostName"),  dbName);
            }
            String userName = properties.getProperty("userName");
            String password = properties.getProperty("password");
            
            if (!UIHelper.tryLogin(driverInfo.getDriverClassName(), 
                    driverInfo.getDialectClassName(), 
                    dbName, 
                    connStr, 
                    userName, 
                    password))
            {
                return false;
            }
        } catch (Exception ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, ex);
            return false;
        }
        return true;
    }
    
    /**
     * Setup all the System properties. This names all the needed factories. 
     */
    public static void setUpSystemProperties()
    {
        // Name factories
        System.setProperty(AppContextMgr.factoryName,                   "edu.ku.brc.specify.config.SpecifyAppContextMgr");      // Needed by AppContextMgr //$NON-NLS-1$
        System.setProperty(AppPreferences.factoryName,                  "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");         // Needed by AppReferences //$NON-NLS-1$
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
        System.setProperty(DataObjFieldFormatMgr.factoryName,           "edu.ku.brc.specify.ui.SpecifyDataObjFieldFormatMgr");         // Needed for WebLnkButton //$NON-NLS-1$
        System.setProperty(RecordSetFactory.factoryName,                "edu.ku.brc.specify.config.SpecifyRecordSetFactory");          // Needed for Searching //$NON-NLS-1$
        System.setProperty(DBTableIdMgr.factoryName,                    "edu.ku.brc.specify.config.SpecifyDBTableIdMgr");              // Needed for Tree Field Names //$NON-NLS-1$
        System.setProperty(SecurityMgr.factoryName,                     "edu.ku.brc.af.auth.specify.SpecifySecurityMgr");              // Needed for Tree Field Names //$NON-NLS-1$
        System.setProperty(BackupServiceFactory.factoryName,            "edu.ku.brc.af.core.db.MySQLBackupService");                   // Needed for Backup and Restore //$NON-NLS-1$
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            ResourceBundle.getBundle("resources", Locale.getDefault()); //$NON-NLS-1$
            
        } catch (MissingResourceException ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, ex);
            Locale.setDefault(Locale.ENGLISH);
            UIRegistry.setResourceLocale(Locale.ENGLISH);
        }
        
        try
        {
            if (!System.getProperty("os.name").equals("Mac OS X"))
            {
                UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
                PlasticLookAndFeel.setPlasticTheme(new DesertBlue());
            }
        }
        catch (Exception e)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SpecifyDBSetupWizard.class, e);
            e.printStackTrace();
        }
        
        // Now check the System Properties
        String appDir = System.getProperty("appdir");
        if (StringUtils.isNotEmpty(appDir))
        {
            UIRegistry.setDefaultWorkingPath(appDir);
        }
        
        String appdatadir = System.getProperty("appdatadir");
        if (StringUtils.isNotEmpty(appdatadir))
        {
            UIRegistry.setBaseAppDataDir(appdatadir);
        }
        
        String javadbdir = System.getProperty("javadbdir");
        if (StringUtils.isNotEmpty(javadbdir))
        {
            UIRegistry.setJavaDBDir(javadbdir);
        }
        
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                // Set App Name, MUST be done very first thing!
                UIRegistry.setAppName("Specify");  //$NON-NLS-1$
                
                // Then set this
                IconManager.setApplicationClass(Specify.class);
                IconManager.loadIcons(XMLHelper.getConfigDir("icons_datamodel.xml")); //$NON-NLS-1$
                IconManager.loadIcons(XMLHelper.getConfigDir("icons_plugins.xml")); //$NON-NLS-1$
                IconManager.loadIcons(XMLHelper.getConfigDir("icons_disciplines.xml")); //$NON-NLS-1$
                
                setUpSystemProperties();
                SpecifyDBSetupWizard setup = new SpecifyDBSetupWizard();
                UIHelper.centerAndShow(setup);
            }
        });
    }
}

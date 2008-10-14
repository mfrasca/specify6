/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
package edu.ku.brc.specify.config.init;

import static edu.ku.brc.specify.config.init.BaseSetupPanel.makeName;
import static edu.ku.brc.ui.UIHelper.createButton;

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

import javax.swing.JButton;
import javax.swing.JFrame;
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

import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.dbsupport.DatabaseDriverInfo;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.helpers.Encryption;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.specify.Specify;
import edu.ku.brc.specify.ui.HelpMgr;
import edu.ku.brc.specify.utilapps.BuildSampleDatabase;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;

public class SetupDialog extends JFrame
{
    private static final Logger log = Logger.getLogger(SetupDialog.class);
    
    private static final boolean     DO_CHANGE_USERNAME = false;
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
    
    protected Specify                specify;
    protected String                 setupXMLPath;
    
    /**
     * @param specify
     */
    public SetupDialog(final Specify specify)
    {
        super();
        
        setupXMLPath = UIRegistry.getUserHomeAppDir() + File.separator + "setup_prefs.xml";
        try
        {
            props.loadFromXML(new FileInputStream(new File(setupXMLPath)));
            
        } catch (Exception ex)
        {
            
        }
        
        this.specify = specify;
        
        setTitle("Configuring a Database");
        cardPanel = new JPanel(cardLayout);
        
        
        cancelBtn  = createButton(UIRegistry.getResourceString("CANCEL"));
        helpBtn    = createButton(UIRegistry.getResourceString("HELP"));
        
        JPanel btnBar;
        backBtn    = createButton(UIRegistry.getResourceString("BACK"));
        nextBtn    = createButton(UIRegistry.getResourceString("NEXT"));
        
        HelpMgr.registerComponent(helpBtn, "ConfiguringDatabase");
        CellConstraints cc = new CellConstraints();
        PanelBuilder bbpb = new PanelBuilder(new FormLayout("f:p:g,p,4px,p,4px,p,4px,p,4px", "p"));
        bbpb.add(helpBtn, cc.xy(2,1));
        bbpb.add(backBtn, cc.xy(4,1));
        bbpb.add(nextBtn, cc.xy(6,1));
        bbpb.add(cancelBtn, cc.xy(8,1));
        //btnBar = ButtonBarFactory.buildWizardBar(helpBtn, backBtn, nextBtn, cancelBtn);
        btnBar = bbpb.getPanel();
            
        
        //agentPanel    = new NewAgentPanel(nextBtn);
        //panels.add(agentPanel);
        
        userPanel     = new DatabasePanel(nextBtn, true);
        panels.add(userPanel);
        //locationPanel = new DBLocationPanel(nextBtn);
        
        UIFieldFormatterMgr.setDoingLocal(true);
        
        
        //panels.add(locationPanel);
        panels.add(new GenericFormPanel("agent", 
                "Enter Collection Manager Information", 
                new String[] { "First Name", "Last Name", "Middle Initial"}, 
                new String[] { "firstName", "lastName", "middleInitial"}, 
                nextBtn));
         
        panels.add(new GenericFormPanel("inst", 
                "Enter Institution Information",
                new String[] { "Name", "Title", "Abbrev"}, 
                new String[] { "name", "title", "abbrev"}, 
                nextBtn));
         
        panels.add(new GenericFormPanel("div", 
                "Enter Division Information", 
                new String[] { "Name", "Title", "Abbrev"}, 
                new String[] { "name", "title", "abbrev"}, 
                nextBtn));
         
        panels.add(new GenericFormPanel("collection", 
                "Enter Collection Information", 
                new String[] { "Prefix", "Name"}, 
                new String[] { "prefix", "name"}, 
                nextBtn));
        
        panels.add(new CatNumScheme(nextBtn));
         
         
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
                        //((BaseSetupPanel)panels.get(step)).updateBtnUI();
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
            }
         });

        //boolean isAllOK = true;
        for (int i=0;i<panels.size();i++)
        {
            cardPanel.add(Integer.toString(i), panels.get(i));
            panels.get(i).setValues(props);
            if (!panels.get(i).isUIValid())
            {
                //isAllOK = false;
            }
        }
        cardLayout.show(cardPanel, "0");
        
        userPanel.updateBtnUI();

        PanelBuilder    builder = new PanelBuilder(new FormLayout("f:p:g", "f:p:g,10px,p"));
        builder.add(cardPanel, cc.xy(1, 1));
        builder.add(btnBar, cc.xy(1, 3));
        
        builder.setDefaultDialogBorder();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(builder.getPanel());
        
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
     * @param doAutoLogin
     * @return
     */
    protected boolean fillPrefs(final boolean doAutoLogin)
    {
        AppPreferences.getLocalPrefs().putBoolean("login.rememberuser", true);
        AppPreferences.getLocalPrefs().putBoolean("login.rememberpassword", true);
        AppPreferences.getLocalPrefs().putBoolean("login.autologin", doAutoLogin);
        
        AppPreferences.getLocalPrefs().put("login.username", "guest");
        AppPreferences.getLocalPrefs().put("login.password", Encryption.encrypt("guest"));
        
        AppPreferences.getLocalPrefs().put("login.databases", userPanel.getDbName());
        AppPreferences.getLocalPrefs().put("login.servers",   HOSTNAME);
        
        AppPreferences.getLocalPrefs().put("login.servers_selected", HOSTNAME);
        AppPreferences.getLocalPrefs().put("login.dbdriver_selected", userPanel.getDriver().getName());
        AppPreferences.getLocalPrefs().put("login.databases_selected", userPanel.getDbName());
        
        AppPreferences.getLocalPrefs().put("javadb.location", UIRegistry.getJavaDBPath());
        log.debug(UIRegistry.getJavaDBPath());

        if (DO_CHANGE_USERNAME)
        {
            AppPreferences.getLocalPrefs().put("startup.username",  userPanel.getUsername());
            AppPreferences.getLocalPrefs().put("startup.password",  Encryption.encrypt(new String(userPanel.getPassword())));
        }
        
        try
        {
            AppPreferences.getLocalPrefs().flush();
            return true;
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
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
        
        // Now initialize
        AppPreferences localPrefs = AppPreferences.getLocalPrefs();
        localPrefs.setDirPath(UIRegistry.getDefaultWorkingPath());
        System.out.println(UIRegistry.getDefaultWorkingPath()+" "+doLoginOnly+" "+assumeDerby);

        if (doLoginOnly && assumeDerby)
        {
            if (fillPrefs(true))
            {
                specify.startUp();
            }
            
        } else
        {
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
                            if (driverInfo == null)
                            {
                                throw new RuntimeException("Couldn't find driver by name ["+driverInfo+"] in driver list.");
                            }

                            BuildSampleDatabase builder = new BuildSampleDatabase();
                            builder.getFrame().setIconImage(IconManager.getImage("Specify16", IconManager.IconSize.Std16).getImage());
                            
                            DBConfigInfo config = new DBConfigInfo(driverInfo,
                                                                    HOSTNAME,
                                                                    userPanel.getDbName(),
                                                                    userPanel.getUsername(), 
                                                                    userPanel.getPassword(), 
                                                                    props.getProperty(makeName("agent", "lastName")), 
                                                                    props.getProperty(makeName("agent", "firstName")), 
                                                                    "", // email
                                                                    userPanel.getDiscipline(),
                                                                    props.getProperty(makeName("inst", "name")), 
                                                                    props.getProperty(makeName("div", "name"))
                                                                    );
                            config.setCollectionName(props.getProperty(makeName("collection", "name")));
                            config.setCollectionPrefix(props.getProperty(makeName("collection", "prefix")));
                            config.setDivAbbrev(props.getProperty(makeName("div", "prefix")));
                            config.setDivTitle(props.getProperty(makeName("div", "title")));
                            
                            config.setInstTitle(props.getProperty(makeName("inst", "title")));

                            isOK = builder.buildEmptyDatabase(config);
                                                              
                        } catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        return null;
                    }

                    //Runs on the event-dispatching thread.
                    public void finished()
                    {
                        log.debug("isOK "+isOK);
                        if (isOK)
                        {
                            //if (fillPrefs(false))
                            //{
                                HibernateUtil.shutdown();
                                //specify.startUp();
                            //}
                        }
                    }
                };
                worker.start();
            
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
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
                SetupDialog setup = new SetupDialog(null);
                UIHelper.centerAndShow(setup);
            }
        });
    }
}

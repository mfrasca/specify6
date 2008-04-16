package edu.ku.brc.specify.conversion;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBlue;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.SchemaI18NService;
import edu.ku.brc.af.core.expresssearch.QueryAdjusterForDomain;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.dbsupport.CustomQueryFactory;
import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.DatabaseDriverInfo;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.dbsupport.ResultsPager;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.specify.config.init.DataBuilder;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.GeographyTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.PrepType;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;
import edu.ku.brc.specify.datamodel.TaxonTreeDefItem;
import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.UserGroup;
import edu.ku.brc.specify.tools.SpecifySchemaGenerator;
import edu.ku.brc.specify.utilapps.BuildSampleDatabase;
import edu.ku.brc.ui.ChooseFromListDlg;
import edu.ku.brc.ui.ProgressFrame;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.db.DatabaseConnectionProperties;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterMgr;

/**
 * Create more sample data, letting Hibernate persist it for us.
 *
 * @code_status Beta
 * @author rods
 */
public class SpecifyDBConverter
{
    protected static final Logger log = Logger.getLogger(SpecifyDBConverter.class);

    protected static Hashtable<String, Integer> prepTypeMapper    = new Hashtable<String, Integer>();
    protected static int                        attrsId           = 0;
    protected static SimpleDateFormat           dateFormatter     = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    protected static StringBuffer               strBuf            = new StringBuffer("");
    protected static Calendar                   calendar          = Calendar.getInstance();
    
    protected static List<String>               dbNamesToConvert  = null;
    protected static int                        currentIndex      = 0;
    protected static Hashtable<String, String>  old2NewDBNames    = null;
    
    protected static ProgressFrame              frame             = null;

    /**
     * Constructor.
     */
    public SpecifyDBConverter()
    {
        setUpSystemProperties();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception
    {
        /*
        for (Enumeration e=LogManager.getCurrentLoggers(); e.hasMoreElements();)
        {
            Logger logger = (Logger)e.nextElement();
            logger.setLevel(Level.ALL);
            System.out.println("Setting "+ logger.getName() + " to " + logger.getLevel());
        }*/
        
        final SpecifyDBConverter converter = new  SpecifyDBConverter();
        
        Logger logger = LogManager.getLogger("edu.ku.brc");
        if (logger != null)
        {
            logger.setLevel(Level.ALL);
            System.out.println("Setting "+ logger.getName() + " to " + logger.getLevel());
        }
        
        logger = LogManager.getLogger(edu.ku.brc.dbsupport.HibernateUtil.class);
        if (logger != null)
        {
            logger.setLevel(Level.INFO);
            System.out.println("Setting "+ logger.getName() + " to " + logger.getLevel());
        }
        
        // Create Specify Application
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
        
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
                    log.error("Can't change L&F: ", e);
                }
                
                frame = new ProgressFrame("Converting");
                old2NewDBNames = new Hashtable<String, String>();
                String[] names = {
                        "Custom", "",
                        //"Fish", "sp4_fish", 
                        //"Accessions", "sp4_accessions", 
                        //"Cranbrook", "sp4_cranbrook", 
                        //"Ento", "sp4_ento", 
                        "Bird_Deleware", "sp5_dmnhbird",
                        "Ento_CAS", "sp5_cas_ento",
                        "Fish_Kansas", "sp5_kufish",                        
                        "Herbarium_NewHampshire","sp5_unhnhaherbarium",
                        "Herps_Guatemala","sp5_uvgherps",
                        "Invert_Alabama", "sp5_uamc_invert",
                        "Mammals_SAfrica", "sp5_mmpemammals",
                        "Multi_ChicagoAS", "sp5_chias_multi",
                        "Paleo_Colorado", "sp5_cupaleo",
                        };
                for (int i=0;i<names.length;i++)
                {
                    old2NewDBNames.put(names[i], names[++i]);
                }
                UIRegistry.setAppName("Specify");
                
                dbNamesToConvert = converter.selectedDBsToConvert(names);
                log.debug("size of name to conver: " + dbNamesToConvert.size());
                
                if ((dbNamesToConvert.size()>=1)&&(dbNamesToConvert.get(0).equals("Custom")))
                {
                    log.debug("Running custom converter");
                    CustomDBConverterDlg dlg = converter.runCustomConverter();
                    CustomDBConverterPanel panel = dlg.getCustomDBConverterPanel();
                    dbNamesToConvert = panel.getSelectedObjects();
                    log.debug("Source db: " + dbNamesToConvert.get(0));
                    log.debug("Dest db: " + dbNamesToConvert.get(1));
                    log.debug(panel.getSourceDriverType());
                    log.debug(panel.getSourceServerName());
                    log.debug(panel.getSourceUserName());
                    log.debug(panel.getSourcePassword());
                    log.debug(panel.getDestDriverType());
                    log.debug(panel.getDestServerName());
                    log.debug(panel.getDestUserName());
                    log.debug(panel.getDestPassword());
                    
                    //String driverType, String host, String userName, String password)
                    //dlg.setVisible(false);
                    currentIndex = 0;
                    converter.processDB(true, 
                            new DatabaseConnectionProperties(
                                    panel.getSourceDriverType(),
                                    panel.getSourceServerName(),
                                    panel.getSourceUserName(),
                                    panel.getSourcePassword()),
                            new DatabaseConnectionProperties(
                                    panel.getDestDriverType(),
                                    panel.getDestServerName(),
                                    panel.getDestUserName(),
                                    panel.getDestPassword()));
                }
                else {
                    log.debug("Running test case converter");
                    currentIndex = 0;
                    converter.processDB(false, null, null);
                }
            }
        });
    }
    
    /**
     * @param isCustomConvert
     * @param sourceDbProps
     * @param destDbProps
     */
    protected  void processDB(final boolean isCustomConvert, 
                              final DatabaseConnectionProperties sourceDbProps, 
                              final DatabaseConnectionProperties destDbProps)
    {
        
        if (!(isCustomConvert) && (dbNamesToConvert.size() > 0 && currentIndex < dbNamesToConvert.size()))
        {
            
            final SwingWorker worker = new SwingWorker()
            {
                @Override
                public Object construct()
                {
                    try
                    {
                        String currentDBName = dbNamesToConvert.get(currentIndex++);
                        frame.setTitle("Converting "+currentDBName+"...");
                        convertDB(old2NewDBNames.get(currentDBName), 
                                  currentDBName.toLowerCase());
                        
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    return null;
                }

                //Runs on the event-dispatching thread.
                @Override
                public void finished()
                {
                    processDB(isCustomConvert, sourceDbProps, destDbProps);
                }
            };
            worker.start();
        }
        else if (isCustomConvert)
        {
            final SwingWorker worker = new SwingWorker()
            {
                @Override
                public Object construct()
                {
                    try
                    {
                        //String currentDBName = dbNamesToConvert.get(currentIndex++);
                        frame.setTitle("Converting "+dbNamesToConvert.get(0)+"...");
                        convertDB(  dbNamesToConvert.get(0), 
                                    dbNamesToConvert.get(1), 
                                    isCustomConvert,
                                    sourceDbProps,
                                    destDbProps);
                        
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    return null;
                }

                //Runs on the event-dispatching thread.
                @Override
                public void finished()
                {
                    //processDB(isCustomConvert, sourceDbProps, destDbProps);
                }
            };
            worker.start();        
        }
    }
    
    /**
     * @param sourceDatabaseName
     * @param destDatabaseName
     * @throws Exception
     */
    protected void convertDB(final String sourceDatabaseName, final String destDatabaseName)throws Exception
    {
        convertDB(sourceDatabaseName, destDatabaseName, false, null,  null);
    }
    
    protected void setUpSystemProperties()
    {
        // Name factories
        System.setProperty(AppContextMgr.factoryName,                   "edu.ku.brc.specify.config.SpecifyAppContextMgr");      // Needed by AppContextMgr
        System.setProperty(AppPreferences.factoryName,                  "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");         // Needed by AppReferences
        System.setProperty("edu.ku.brc.ui.ViewBasedDialogFactoryIFace", "edu.ku.brc.specify.ui.DBObjDialogFactory");            // Needed By UIRegistry
        System.setProperty("edu.ku.brc.ui.forms.DraggableRecordIdentifierFactory", "edu.ku.brc.specify.ui.SpecifyDraggableRecordIdentiferFactory"); // Needed By the Form System
        System.setProperty("edu.ku.brc.dbsupport.AuditInterceptor",     "edu.ku.brc.specify.dbsupport.AuditInterceptor");       // Needed By the Form System for updating Lucene and logging transactions
        System.setProperty("edu.ku.brc.dbsupport.DataProvider",         "edu.ku.brc.specify.dbsupport.HibernateDataProvider");  // Needed By the Form System and any Data Get/Set
        System.setProperty("edu.ku.brc.ui.db.PickListDBAdapterFactory", "edu.ku.brc.specify.ui.db.PickListDBAdapterFactory");   // Needed By the Auto Cosmplete UI
        System.setProperty(CustomQueryFactory.factoryName,              "edu.ku.brc.specify.dbsupport.SpecifyCustomQueryFactory");
        System.setProperty(UIFieldFormatterMgr.factoryName,             "edu.ku.brc.specify.ui.SpecifyUIFieldFormatterMgr");    // Needed for CatalogNumberign
        System.setProperty(QueryAdjusterForDomain.factoryName,        "edu.ku.brc.specify.dbsupport.SpecifyExpressSearchSQLAdjuster");    // Needed for ExpressSearch
        System.setProperty(SchemaI18NService.factoryName,               "edu.ku.brc.specify.config.SpecifySchemaI18NService");    // Needed for Localization and Schema
        
    }

    /**
     * Convert old Database to New 
     * @param databaseNameSource name of an old database
     * @param databaseNameDest name of new DB
     * @throws Exception xx
     */
    protected  void convertDB(final String databaseNameSource, 
                              final String databaseNameDest, 
                              final boolean isCustomConvert,
                              final DatabaseConnectionProperties sourceDbProps,
                              final DatabaseConnectionProperties destDbProps) throws Exception
    {
        boolean doAll            = true; 
        boolean startfromScratch = true; 
        
        System.out.println("************************************************************");
        System.out.println("From "+databaseNameSource+" to "+databaseNameDest);
        System.out.println("************************************************************");

        HibernateUtil.shutdown();    
        
        Properties initPrefs = BuildSampleDatabase.getInitializePrefs(databaseNameDest);
        
        String userNameSource     = "";
        String passwordSource     = "";
        String driverNameSource   = "";
        String databaseHostSource = "";
        DatabaseDriverInfo driverInfoSource = null;
        
        String userNameDest     = "";
        String passwordDest     = "";
        String driverNameDest   = "";
        String databaseHostDest = "";
        DatabaseDriverInfo driverInfoDest = null;
        
        if (isCustomConvert)
        {
            log.debug("Running a custom convert");
            userNameSource      = sourceDbProps.getUserName();
            passwordSource      = sourceDbProps.getPassword();
            driverNameSource    = sourceDbProps.getDriverType();
            databaseHostSource  = sourceDbProps.getHost();
            
            userNameDest        = destDbProps.getUserName();
            passwordDest        = destDbProps.getPassword();
            driverNameDest      = destDbProps.getDriverType();
            databaseHostDest    = destDbProps.getHost();
        }
        else
        {
            log.debug("Running an non-custom MySQL convert, using old default login creds");
            userNameSource      = initPrefs.getProperty("initializer.username", "rods");
            passwordSource      = initPrefs.getProperty("initializer.password", "rods");
            driverNameSource    = initPrefs.getProperty("initializer.driver",   "MySQL");
            databaseHostSource  = initPrefs.getProperty("initializer.host",     "localhost"); 
            
            userNameDest        = initPrefs.getProperty("initializer.username", "rods");
            passwordDest        = initPrefs.getProperty("initializer.password", "rods");
            driverNameDest      = initPrefs.getProperty("initializer.driver",   "MySQL");
            databaseHostDest    = initPrefs.getProperty("initializer.host",     "localhost");  
        }
        
        log.debug("Custom Convert Source Properties ----------------------");
        log.debug("databaseNameSource: " + databaseNameSource);        
        log.debug("userNameSource: " + userNameSource);
        log.debug("passwordSource: " + passwordSource);
        log.debug("driverNameSource: " + driverNameSource);
        log.debug("databaseHostSource: " + databaseHostSource);
        
        log.debug("Custom Convert Destination Properties ----------------------");
        log.debug("databaseNameDest: " + databaseNameDest);
        log.debug("userNameDest: " + userNameDest);
        log.debug("passwordDest: " + passwordDest);
        log.debug("driverNameDest: " + driverNameDest);
        log.debug("databaseHostDest: " + databaseHostDest);

        driverInfoSource = DatabaseDriverInfo.getDriver(driverNameSource);
        driverInfoDest = DatabaseDriverInfo.getDriver(driverNameDest);
        
        if (driverInfoSource == null)
        {
            throw new RuntimeException("Couldn't find Source DB driver by name ["+driverInfoSource+"] in driver list.");
        }
        if (driverInfoDest == null)
        {
            throw new RuntimeException("Couldn't find Destination driver by name ["+driverInfoDest+"] in driver list.");
        }
        
        if (driverNameDest.equals("MySQL"))BasicSQLUtils.myDestinationServerType = BasicSQLUtils.SERVERTYPE.MySQL;
        else if (driverNameDest.equals("Derby"))BasicSQLUtils.myDestinationServerType = BasicSQLUtils.SERVERTYPE.Derby;
        else if (driverNameDest.equals("SQLServer"))BasicSQLUtils.myDestinationServerType = BasicSQLUtils.SERVERTYPE.MS_SQLServer;
        
        if (driverNameSource.equals("MySQL"))BasicSQLUtils.mySourceServerType = BasicSQLUtils.SERVERTYPE.MySQL;
        else if (driverNameSource.equals("Derby"))BasicSQLUtils.mySourceServerType = BasicSQLUtils.SERVERTYPE.Derby;
        else if (driverNameSource.equals("SQLServer"))BasicSQLUtils.mySourceServerType = BasicSQLUtils.SERVERTYPE.MS_SQLServer;
        
        else 
        {
            log.error("Error setting ServerType for destination database for conversion.  Could affect the"
                    + " way that SQL string are generated and executed on differetn DB egnines");
        }
        String destConnectionString = driverInfoDest.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, databaseHostDest, "", userNameDest, passwordDest, driverNameDest);
        log.debug("attempting login to destination: " + destConnectionString);
        // This will log us in and return true/false
        // This will connect without specifying a DB, which allows us to create the DB
        if (!UIHelper.tryLogin(driverInfoDest.getDriverClassName(), 
                driverInfoDest.getDialectClassName(), 
                databaseNameDest, 
                destConnectionString,
                userNameDest, 
                passwordDest))
        {
            log.error("Failed connection string: "  +driverInfoSource.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, databaseHostDest, databaseNameDest, userNameDest, passwordDest, driverNameDest) );
            throw new RuntimeException("Couldn't login into ["+databaseNameDest+"] "+DBConnection.getInstance().getErrorMsg());
        }
        
        //MEG WHY IS THIS COMMENTED OUT???
        //DataBuilder.setSession(HibernateUtil.getNewSession());
        
        log.debug("Preparing new database");
        
        if (startfromScratch)
        {
            log.debug("Starting from scratch and generating the schema");
            SpecifySchemaGenerator.generateSchema(driverInfoDest, databaseHostDest, databaseNameDest, userNameDest, passwordDest);
        }
        
        log.debug("Preparing new database: completed");

        log.debug("DESTINATION driver class: " + driverInfoDest.getDriverClassName());
        log.debug("DESTINATION dialect class: " + driverInfoDest.getDialectClassName());               
        log.debug("DESTINATION Connection String: " + driverInfoDest.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, databaseHostDest, databaseNameDest, userNameDest, passwordDest, driverNameDest)); 
        
        // This will log us in and return true/false
        if (!UIHelper.tryLogin(driverInfoDest.getDriverClassName(), 
                driverInfoDest.getDialectClassName(), 
                databaseNameDest, 
                driverInfoDest.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, databaseHostDest, databaseNameDest, userNameDest, passwordDest, driverNameDest),                 
                userNameDest, 
                passwordDest))
        {
            throw new RuntimeException("Couldn't login into ["+databaseNameDest+"] "+DBConnection.getInstance().getErrorMsg());
        }
        DataBuilder.setSession(HibernateUtil.getNewSession());
        HibernateUtil.getCurrentSession(); 
        IdMapperMgr idMapperMgr = null;
        try
        {
        	GenericDBConversion.setShouldCreateMapTables(startfromScratch);
            GenericDBConversion.setShouldDeleteMapTables(false);
            
            frame.setOverall(0, 19);
            SwingUtilities.invokeLater(new Runnable() {
                public void run()
                {
                    UIHelper.centerAndShow(frame);
                }
            });

            boolean doConvert = true;
            if (doConvert)
            {
                log.debug("SOURCE driver class: " + driverInfoSource.getDriverClassName());
                log.debug("SOURCE dialect class: " + driverInfoSource.getDialectClassName());       
                log.debug("SOURCE Connection String: " + driverInfoSource.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, databaseHostSource, databaseNameSource, userNameSource, passwordSource, driverNameSource)); 
          
                GenericDBConversion conversion = new GenericDBConversion(driverInfoSource.getDriverClassName(),
                                                                         databaseNameSource,
                                                                        // "jdbc:mysql://"+databaseHostSource+ "/"+databaseNameSource,
                                                                         driverInfoSource.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, databaseHostSource, databaseNameSource, userNameSource, passwordSource, driverNameSource),                                                                                          
                                                                         userNameSource,
                                                                         passwordSource);

                conversion.setFrame(frame);

                idMapperMgr = IdMapperMgr.getInstance();
                Connection oldConn = conversion.getOldDBConnection();
                Connection newConn = conversion.getNewDBConnection();
                if (oldConn == null || newConn == null)
                {
                	log.error("One of the DB connections is null.  Cannot proceed.  Check your DB install to make sure both DBs exist.");
                	System.exit(-1);
                }
                idMapperMgr.setDBs(oldConn, newConn);

                // NOTE: Within BasicSQLUtils the connection is for removing tables and records
                BasicSQLUtils.setDBConnection(conversion.getNewDBConnection());
                
                //---------------------------------------------------------------------------------------
                //-- Create basic set of information.
                //---------------------------------------------------------------------------------------
                conversion.doInitialize();

                BasicSQLUtils.deleteAllRecordsFromTable(conversion.getNewDBConnection(), "agent", BasicSQLUtils.myDestinationServerType);
                BasicSQLUtils.deleteAllRecordsFromTable(conversion.getNewDBConnection(), "address", BasicSQLUtils.myDestinationServerType);

                conversion.initializeAgentInfo(startfromScratch);
                
                frame.setDesc("Mapping Tables.");
                log.info("Mapping Tables.");
                boolean mapTables = true;
                
                //GenericDBConversion.setShouldCreateMapTables(false);
                if (mapTables || doAll)
                {
                    // Ignore these field names from new table schema when mapping OR
                    // when mapping IDs
                    BasicSQLUtils.setFieldsToIgnoreWhenMappingIDs(new String[] {"MethodID",  "RoleID",  "CollectionID",  "ConfidenceID",
                                                                                "TypeStatusNameID",  "ObservationMethodID",  "StatusID",
                                                                                "TypeID",  "ShipmentMethodID", "RankID", "DirectParentRankID",
                                                                                "RequiredParentRankID", "MediumID"});
                    conversion.mapIds();//MEG LOOK HERE
                    BasicSQLUtils.setFieldsToIgnoreWhenMappingIDs(null);
                }
                //GenericDBConversion.setShouldCreateMapTables(startfromScratch);
                
                frame.incOverall();

                conversion.convertDivision();
                frame.incOverall();
                
                ///////////////////////////////
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                // Really need to create or get a proper Discipline Record
                ///////////////////////////////
                String discipline = "fish";
                
                frame.setDesc("Converting CollectionObjectDefs.");
                log.info("Converting CollectionObjectDefs.");
                boolean convertDiscipline = false;
                if (convertDiscipline || doAll)
                {
                    DataBuilder.getSession().beginTransaction();
                    
                    String           username         = initPrefs.getProperty("initializer.username", "rods");
                    String           title            = initPrefs.getProperty("useragent.title",    "Mr.");
                    String           firstName        = initPrefs.getProperty("useragent.firstname", "Rod");
                    String           lastName         = initPrefs.getProperty("useragent.lastname", "Spears");
                    String           midInit          = initPrefs.getProperty("useragent.midinit", "C");
                    String           abbrev           = initPrefs.getProperty("useragent.abbrev", "rs");
                    String           email            = initPrefs.getProperty("useragent.email", "rods@ku.edu");
                    String           userType         = initPrefs.getProperty("useragent.usertype", "CollectionManager");   
                    
                    BasicSQLUtils.deleteAllRecordsFromTable(newConn, "usergroup", BasicSQLUtils.myDestinationServerType);
                    BasicSQLUtils.deleteAllRecordsFromTable(newConn, "specifyuser", BasicSQLUtils.myDestinationServerType);
                    UserGroup userGroup = DataBuilder.createUserGroup("admin2");
                    
                    Criteria criteria = DataBuilder.getSession().createCriteria(Agent.class);
                    criteria.add(Restrictions.eq("lastName", lastName));
                    criteria.add(Restrictions.eq("firstName", firstName));
                    
                    Agent   userAgent = null;
                    List<?> list      = criteria.list();
                    if (list != null && list.size() == 1)
                    {
                        userAgent = (Agent)list.get(0);
                    } else
                    {
                        userAgent = DataBuilder.createAgent(title, firstName, lastName, midInit, abbrev, email);
                    }
                    
                    SpecifyUser specifyUser = DataBuilder.createSpecifyUser(username, email, (short)0, userGroup, userType);
                    specifyUser.addReference(userAgent, "agents");
                    
                    DataBuilder.getSession().getTransaction().commit();

                    boolean isEmbddedCE = false; // XXX Hard-coded for now
                    int     catSeriesId = 0;
                    conversion.convertCollectionObjectDefs(specifyUser.getSpecifyUserId(), isEmbddedCE, catSeriesId);
                    SpecifyUser.setCurrentUser(specifyUser);

                } else
                {
                    idMapperMgr.addTableMapper("CatalogSeriesDefinition", "CatalogSeriesDefinitionID");
                    idMapperMgr.addTableMapper("CollectionObjectType", "CollectionObjectTypeID");
                }
                frame.incOverall();

                
                frame.setDesc("Converting Agents.");
                log.info("Converting Agents.");
                
                // This MUST be done before any of the table copies because it
                // creates the IdMappers for Agent, Address and more importantly AgentAddress
                // NOTE: AgentAddress is actually mapping from the old AgentAddress table to the new Agent table
                boolean copyAgentAddressTables = false;
                if (copyAgentAddressTables || doAll)
                {
                    log.info("Calling - convertAgents");
                    conversion.convertAgents();

                } else
                {
                    idMapperMgr.addTableMapper("agent", "AgentID");
                    idMapperMgr.addTableMapper("address", "AddressID");
                    idMapperMgr.addTableMapper("agentaddress", "AgentAddressID");
                }
                frame.incOverall();
                
                frame.setDesc("Mapping Agent Tables.");
                log.info("MappingAgent Tables.");
                if (mapTables || doAll)
                {
                    // Ignore these field names from new table schema when mapping OR
                    // when mapping IDs
                    BasicSQLUtils.setFieldsToIgnoreWhenMappingIDs(new String[] {"MethodID",  "RoleID",  "CollectionID",  "ConfidenceID",
                                                                                "TypeStatusNameID",  "ObservationMethodID",  "StatusID",
                                                                                "TypeID",  "ShipmentMethodID", "RankID", "DirectParentRankID",
                                                                                "RequiredParentRankID", "MediumID"});
                    conversion.mapAgentRelatedIds();//MEG LOOK HERE
                    BasicSQLUtils.setFieldsToIgnoreWhenMappingIDs(null);
                }
                frame.incOverall();


                frame.setDesc("Converting Geologic Time Period.");
                log.info("Converting Geologic Time Period.");
                // GTP needs to be converted here so the stratigraphy conversion can use
                // the IDs
                boolean doGTP = false;
                if (doGTP || doAll )
                {
                    GeologicTimePeriodTreeDef treeDef = conversion.convertGTPDefAndItems();
                    conversion.convertGTP(treeDef);
                } else
                {
                    idMapperMgr.addTableMapper("geologictimeperiod", "GeologicTimePeriodID");
                    idMapperMgr.mapForeignKey("Stratigraphy", "GeologicTimePeriodID", "GeologicTimePeriod", "GeologicTimePeriodID");
                }

                frame.incOverall();
                
               frame.setDesc("Converting Determinations Records");
                log.info("Converting Determinations Records");
                boolean doDeterminations = false;
                if (doDeterminations || doAll)
                {
                    conversion.createDefaultDeterminationStatusRecords();
                    frame.incOverall();

                    conversion.convertDeterminationRecords();
                } else
                {
                    frame.incOverall();
                }
                frame.incOverall();
                
                frame.setDesc("Converting LoanPreparations Records");
                log.info("Converting LoanPreparations Records");
                boolean doLoanPreparations = false;
                if (doLoanPreparations || doAll)
                {
                    conversion.convertLoanPreparations();
                    frame.incOverall();
                    
                } else
                {
                    frame.incOverall();
                }

                frame.setDesc("Copying Tables");
                log.info("Copying Tables");
                boolean copyTables = false;
                if (copyTables || doAll)
                {
                    conversion.copyTables();
                }

                frame.incOverall();

                
                frame.setDesc("Converting DeaccessionCollectionObject");
                log.info("Converting DeaccessionCollectionObject");
                boolean doDeaccessionCollectionObject = false;
                if (doDeaccessionCollectionObject || doAll)
                {
                    conversion.convertDeaccessionCollectionObject();
                }
                frame.incOverall();                

                frame.setDesc("Converting CollectionObjects");
                log.info("Converting CollectionObjects");
                boolean doCollectionObjects = true;
                if (doCollectionObjects || doAll)
                {
                    if (true)
                    {
                        Session session = HibernateUtil.getCurrentSession();
                        try
                        {
                            Hashtable<Integer, Map<String, PrepType>> collToPrepTypeHash = new Hashtable<Integer, Map<String,PrepType>>();
                            Query   q = session.createQuery("FROM Collection");
                            for (Object dataObj :  q.list())
                            {
                                Collection collection = (Collection)dataObj;
                                Map<String, PrepType> prepTypeMap = conversion.createPreparationTypesFromUSys(collection);
                                PrepType miscPT = prepTypeMap.get("misc");
                                if (miscPT != null)
                                {
                                    prepTypeMap.put("n/a", miscPT);
                                } else
                                {
                                    miscPT = prepTypeMap.get("Misc"); 
                                    if (miscPT != null)
                                    {
                                        prepTypeMap.put("n/a", miscPT);
                                    } else
                                    {
                                        log.error("******************************* Couldn't find 'Misc' PrepType!");
                                    }
                                }
                                collToPrepTypeHash.put(collection.getCollectionId(), prepTypeMap);
                            }
                            conversion.convertPreparationRecords(collToPrepTypeHash);
                        } catch (Exception ex)
                        {
                            throw new RuntimeException(ex);
                            
                        } finally
                        {
                            if (session != null)
                            {
                                session.close();
                            }
                        }
                    }
                    
                    // Arg1 - Use Numeric Catalog Number
                    // Arg2 - Use the Prefix from Catalog Series
                    conversion.convertCollectionObjects(true, false);
                    frame.incOverall();

                    
                }  else
                {
                    frame.incOverall();
                    frame.incOverall();
                }
                
                frame.setDesc("Converting Geography");
                log.info("Converting Geography");
                boolean doGeography = false;
                if (!databaseNameDest.startsWith("accessions") && (doGeography || doAll))
                {
                    GeographyTreeDef treeDef = conversion.createStandardGeographyDefinitionAndItems();
                    conversion.convertGeography(treeDef);
                    frame.incOverall();
                    conversion.convertLocality();
                    frame.incOverall();
                    
                } else
                {
                    frame.incOverall();
                    frame.incOverall();
                }
                
                frame.setDesc("Creating Storage");
                log.info("Creating Storage");
                boolean doStorage = false;
                if (doStorage || doAll )
                {
                    conversion.buildSampleStorageTreeDef();
                }
                frame.incOverall();
                
                frame.setDesc("Converting Taxonomy");
                log.info("Converting Taxonomy");
                boolean doTaxonomy = true;
                if (doTaxonomy || doAll )
                {
                	conversion.copyTaxonTreeDefs();
                	conversion.convertTaxonTreeDefItems();
                    
                    // fix the fullNameDirection field in each of the converted tree defs
                    Session session = HibernateUtil.getCurrentSession();
                    Query q = session.createQuery("FROM TaxonTreeDef");
                    List<?> allTTDs = q.list();
                    HibernateUtil.beginTransaction();
                    for(Object o: allTTDs)
                    {
                        TaxonTreeDef ttd = (TaxonTreeDef)o;
                        ttd.setFullNameDirection(TreeDefIface.FORWARD);
                        session.saveOrUpdate(ttd);
                    }
                    try
                    {
                        HibernateUtil.commitTransaction();
                    }
                    catch(Exception e)
                    {
                        log.error("Error while setting the fullname direction of taxonomy tree definitions.");
                    }
                    
                    // fix the fullNameSeparator field in each of the converted tree def items
                    session = HibernateUtil.getCurrentSession();
                    q = session.createQuery("FROM TaxonTreeDefItem");
                    List<?> allTTDIs = q.list();
                    HibernateUtil.beginTransaction();
                    for(Object o: allTTDIs)
                    {
                        TaxonTreeDefItem ttdi = (TaxonTreeDefItem)o;
                        ttdi.setFullNameSeparator(" ");
                        session.saveOrUpdate(ttdi);
                    }
                    try
                    {
                        HibernateUtil.commitTransaction();
                    }
                    catch(Exception e)
                    {
                        log.error("Error while setting the fullname separator of taxonomy tree definition items.");
                    }
                    
                	conversion.copyTaxonRecords();
                }
                frame.incOverall();
                
                frame.setDesc("Converting USYS Tables.");
                log.info("Converting USYS Tables.");
                boolean copyUSYSTables = false;
                if (copyUSYSTables || doAll)
                {
                    conversion.convertUSYSTables();
                    frame.incOverall();
                    
                    frame.setDesc("Converting Division.");
                    conversion.createPickListsFromXML(null);
                    conversion.createPickListsFromXML(discipline);
                    frame.incOverall();
                    
                } else
                {
                    frame.incOverall();
                    frame.incOverall();
                }
                
                frame.setDesc("Localizing the Schema");
                conversion.doLocalizeSchema();
                frame.incOverall();

                System.setProperty(AppPreferences.factoryName, "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");    // Needed by AppReferences
                System.setProperty("edu.ku.brc.dbsupport.DataProvider",         "edu.ku.brc.specify.dbsupport.HibernateDataProvider");  // Needed By the Form System and any Data Get/Set
                
                // Initialize the Prefs
                AppPreferences remoteProps = AppPreferences.getRemote();
                
                for (Object key : initPrefs.keySet())
                {
                    String keyStr = (String)key;
                    if (!keyStr.startsWith("initializer.") && !keyStr.startsWith("useragent."))
                    {
                        remoteProps.put(keyStr, (String)initPrefs.get(key)); 
                    }
                }
                AppPreferences.getRemote().flush();
                
                boolean doFurtherTesting = false;
                if (doFurtherTesting)
                {
                    /*
                    BasicSQLUtils.deleteAllRecordsFromTable("datatype", BasicSQLUtils.myDestinationServerType);
                    BasicSQLUtils.deleteAllRecordsFromTable("specifyuser", BasicSQLUtils.myDestinationServerType);
                    BasicSQLUtils.deleteAllRecordsFromTable("usergroup", BasicSQLUtils.myDestinationServerType);
                    BasicSQLUtils.deleteAllRecordsFromTable("discipline", BasicSQLUtils.myDestinationServerType);

                    DataType          dataType  = createDataType("Animal");
                    UserGroup         userGroup = createUserGroup("Fish");
                    SpecifyUser       user      = createSpecifyUser("rods", "rods@ku.edu", (short)0, new UserGroup[] {userGroup}, "CollectionManager");



                    Criteria criteria = HibernateUtil.getCurrentSession().createCriteria(Collection.class);
                    criteria.add(Restrictions.eq("collectionId", new Integer(0)));
                    List<?> collectionList = criteria.list();

                    boolean doAddTissues = false;
                    if (doAddTissues)
                    {
                        deleteAllRecordsFromTable("collection", BasicSQLUtils.myDestinationServerType);
                        try
                        {
                            Session session = HibernateUtil.getCurrentSession();
                            HibernateUtil.beginTransaction();

                            Collection voucherSeries = null;
                            if (collectionList.size() == 0)
                            {
                                voucherSeries = new Collection();
                               // voucherSeries.setIsTissueSeries(false);
                                voucherSeries.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
                                voucherSeries.setCollectionId(100);
                                voucherSeries.setCollectionPrefix("KUFISH");
                                voucherSeries.setCollectionName("Fish Collection");
                                session.saveOrUpdate(voucherSeries);

                            } else
                            {
                                voucherSeries = (Collection)collectionList.get(0);
                            }

                            if (voucherSeries != null)
                            {
                                Collection tissueSeries = new Collection();
                               // tissueSeries.setIsTissueSeries(true);
                                tissueSeries.setTimestampCreated(new Timestamp(System.currentTimeMillis()));
                                tissueSeries.setCollectionId(101);
                                tissueSeries.setCollectionPrefix("KUTIS");
                                tissueSeries.setCollectionName("Fish Tissue");
                                session.saveOrUpdate(tissueSeries);

                                //voucherSeries.setTissue(tissueSeries);
                                session.saveOrUpdate(voucherSeries);

                                HibernateUtil.commitTransaction();
                            }

                        } catch (Exception e)
                        {
                            log.error("******* " + e);
                            e.printStackTrace();
                            HibernateUtil.rollbackTransaction();
                        }
                        return;
                    }

                    Set<Discipline>  disciplineSet = conversion.createDiscipline("Fish", dataType, user, null, null);


                    Object obj = disciplineSet.iterator().next();
                    Discipline discipline = (Discipline)obj;

                    conversion.convertBiologicalAttrs(discipline, null, null);*/
                }
                //conversion.showStats();
            }

            if (idMapperMgr != null && GenericDBConversion.shouldDeleteMapTables())
            {
                idMapperMgr.cleanup();
            }
            log.info("Done - " + databaseNameDest);
            frame.setDesc("Done - " + databaseNameDest);
            frame.setTitle("Done - " + databaseNameDest);
            frame.incOverall();
            frame.processDone();
            
            DataBuilder.getSession().close();

        } catch (Exception ex)
        {
            ex.printStackTrace();

            if (idMapperMgr != null && GenericDBConversion .shouldDeleteMapTables())
            {
                idMapperMgr.cleanup();
            }

        }
    }

    protected void testPaging()
    {
        boolean testPaging = false;
        if (testPaging)
        {
            /*
            long start;
            List list;
            ResultSet rs;
            java.sql.Statement stmt;

            start = System.currentTimeMillis();
            stmt = DBConnection.getConnection().createStatement();
            rs  = stmt.executeQuery("SELECT * FROM collectionobject c LIMIT 31000,32000");
            log.info("JDBC ******************** "+(System.currentTimeMillis() - start));

            Session session = HibernateUtil.getCurrentSession();
            //start = System.currentTimeMillis();
            //list = session.createQuery("from collection in class Collection").setFirstResult(1).setMaxResults(1000).list();
            //log.info("HIBR ******************** "+(System.currentTimeMillis() - start));


            start = System.currentTimeMillis();
            stmt = DBConnection.getConnection().createStatement();
            rs  = stmt.executeQuery("SELECT * FROM collectionobject c LIMIT 31000,32000");
            log.info("JDBC ******************** "+(System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            list = session.createQuery("from collectionobject in class CollectionObject").setFirstResult(30000).setMaxResults(1000).list();
            log.info("HIBR ******************** "+(System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            list = session.createQuery("from collectionobject in class CollectionObject").setFirstResult(10000).setMaxResults(1000).list();
            log.info("HIBR ******************** "+(System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            list = session.createQuery("from collectionobject in class CollectionObject").setFirstResult(1000).setMaxResults(1000).list();
            log.info("HIBR ******************** "+(System.currentTimeMillis() - start));

            start = System.currentTimeMillis();
            stmt = DBConnection.getConnection().createStatement();
            rs  = stmt.executeQuery("SELECT * FROM collectionobject c LIMIT 1000,2000");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.first();
            while (rs.next())
            {
                for (int i=1;i<=rsmd.getColumnCount();i++)
                {
                    Object o = rs.getObject(i);
                }
            }
            log.info("JDBC ******************** "+(System.currentTimeMillis() - start));

           */

            /*
            HibernatePage.setDriverName("com.mysql.jdbc.Driver");

            int pageNo = 1;
            Pagable page = HibernatePage.getHibernatePageInstance(HibernateUtil.getCurrentSession().createQuery("from collectionobject in class CollectionObject"), 0, 100);
            log.info("Number Pages: "+page.getLastPageNumber());
            int cnt = 0;
            for (Object list : page.getThisPageElements())
            {
                //cnt += list.size();

                log.info("******************** Page "+pageNo++);
            }
            */

            ResultsPager pager = new ResultsPager(HibernateUtil.getCurrentSession().createQuery("from collectionobject in class CollectionObject"), 0, 10);
            //ResultsPager pager = new ResultsPager(HibernateUtil.getCurrentSession().createCriteria(CollectionObject.class), 0, 100);
            int pageNo = 1;
            do
            {
                long start = System.currentTimeMillis();
                List<?> list = pager.getList();
                if (pageNo % 100 == 0)
                {
                    log.info("******************** Page "+pageNo+" "+(System.currentTimeMillis() - start) / 1000.0);
                }
                pageNo++;

                for (Object co : list)
                {
                    if (pageNo % 1000 == 0)
                    {
                        log.info(((CollectionObject)co).getCatalogNumber());
                    }
                }
                list.clear();
                System.gc();
            } while (pager.isNextPage());

        }

    }
    
    /**
     * Loads the dialog
     * @param hashNames every other one is the new name
     * @return the list of selected DBs
     */
    protected  List<String> selectedDBsToConvert(final String[] hashNames)
    {
        String initStr = "";
        String selKey  = "Database_Selected";
        
        for (int i=0;i<hashNames.length;i++)
        {
            initStr += hashNames[i++];
        }
        
        boolean isNew = false;
        Properties props = new Properties();
        File propsFile = new File(UIRegistry.getDefaultWorkingPath() + File.separator + "convert.properties");
        if (!propsFile.exists())
        {
             isNew = true;
            
        } else
        {
            try
            {
                props.loadFromXML(new FileInputStream(propsFile));

            } catch (Exception ex)
            {
                log.error(ex);
            }
        } 
        
        List<String> list = new ArrayList<String>();
        for (int i=0;i<hashNames.length;i++)
        {
            list.add(hashNames[i++]);
        }
        
        int[] indices = null;
        if (isNew)
        {
            indices = new int[] {0};
            
        } else
        {
            String[] indicesStr = props.getProperty(selKey).split(",");
            indices = new int[indicesStr.length];
            for (int i=0;i<indicesStr.length;i++)
            {
                indices[i] = Integer.parseInt(indicesStr[i]);
            }
        }
        
        
         
        class ChooseDBs<T> extends ChooseFromListDlg<T>
        {
            public ChooseDBs(final String title, final List<T> items) throws HeadlessException
            {
                super(null, title, items);
                
                final SwingWorker worker = new SwingWorker()
                {
                    @Override
                    public Object construct()
                    {
                        try
                        {
                            Thread.sleep(10000); // 10 seconds
                            
                        }
                        catch (Exception ex)
                        {
                            // ignore
                        }
                        return null;
                    }

                    //Runs on the event-dispatching thread.
                    @SuppressWarnings("synthetic-access")
                    @Override
                    public void finished()
                    {
                        if (list.getSelectedIndex() > -1)
                        {
                            okBtn.doClick();
                        }
                    }
                };
                worker.start();
            }

        }
        
        ChooseDBs<String> dlg = new ChooseDBs<String>("Choose Database(s) to Convert", list);
        dlg.setMultiSelect(true);
        dlg.setIndices(indices);
        UIHelper.centerAndShow(dlg);
        
        dlg.dispose();
        if (dlg.isCancelled())
        {           
            return new ArrayList<String>();
        }
        
        StringBuilder sb = new StringBuilder();
        for (int inx : dlg.getSelectedIndices())
        {
            if (sb.length() > 0) sb.append(",");
            sb.append(Integer.toString(inx));
        }
        props.put(selKey, sb.toString());
        
        try
        {
            props.storeToXML(new FileOutputStream(propsFile), "Databases to Convert");
            
        } catch (Exception ex)
        {
            log.error(ex);
        }
        
        return dlg.getSelectedObjects();
    }
    
    public CustomDBConverterDlg runCustomConverter()
    {       
        return UIHelper.doSpecifyConvert();
    }
}

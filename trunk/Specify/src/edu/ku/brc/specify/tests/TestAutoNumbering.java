/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tests;


import static edu.ku.brc.specify.utilapps.DataBuilder.createAccession;
import static edu.ku.brc.specify.utilapps.DataBuilder.createAgent;
import static edu.ku.brc.specify.utilapps.DataBuilder.createCatalogNumberingScheme;
import static edu.ku.brc.specify.utilapps.DataBuilder.createCollection;
import static edu.ku.brc.specify.utilapps.DataBuilder.createCollectionObject;
import static edu.ku.brc.specify.utilapps.DataBuilder.createDataType;
import static edu.ku.brc.specify.utilapps.DataBuilder.createDiscipline;
import static edu.ku.brc.specify.utilapps.DataBuilder.createDivision;
import static edu.ku.brc.specify.utilapps.DataBuilder.createGeographyTreeDef;
import static edu.ku.brc.specify.utilapps.DataBuilder.createGeologicTimePeriodTreeDef;
import static edu.ku.brc.specify.utilapps.DataBuilder.createInstitution;
import static edu.ku.brc.specify.utilapps.DataBuilder.createLithoStratTreeDef;
import static edu.ku.brc.specify.utilapps.DataBuilder.createSpecifyUser;
import static edu.ku.brc.specify.utilapps.DataBuilder.createStorageTreeDef;
import static edu.ku.brc.specify.utilapps.DataBuilder.createTaxonTreeDef;
import static edu.ku.brc.specify.utilapps.DataBuilder.createUserGroup;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.dbsupport.CustomQueryFactory;
import edu.ku.brc.dbsupport.DatabaseDriverInfo;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.config.DisciplineType;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.CatalogNumberingScheme;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.DataType;
import edu.ku.brc.specify.datamodel.Discipline;
import edu.ku.brc.specify.datamodel.Division;
import edu.ku.brc.specify.datamodel.GeographyTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.Institution;
import edu.ku.brc.specify.datamodel.LithoStratTreeDef;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.StorageTreeDef;
import edu.ku.brc.specify.datamodel.TaxonTreeDef;
import edu.ku.brc.specify.datamodel.UserGroup;
import edu.ku.brc.specify.dbsupport.CollectionAutoNumber;
import edu.ku.brc.specify.tools.SpecifySchemaGenerator;
import edu.ku.brc.specify.ui.CatalogNumberUIFieldFormatter;
import edu.ku.brc.specify.ui.SpecifyUIFieldFormatterMgr;
import edu.ku.brc.specify.utilapps.BuildSampleDatabase;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.ui.forms.validation.ValFormattedTextField;
import edu.ku.brc.util.AttachmentManagerIface;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.FileStoreAttachmentManager;
import edu.ku.brc.util.thumbnails.Thumbnailer;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Aug 1, 2007
 *
 */
public class TestAutoNumbering extends TestCase
{
    private final Logger         log      = Logger.getLogger(TestAutoNumbering.class);
            
    protected static boolean  doInit  = true;
    protected static boolean  fillDB  = true;
    protected static Session  session = null;
    protected static MyFmtMgr fmtMgr  = null;

    /**
     * Setup all the System properties. This names all the needed factories. 
     */
    protected void setUpSystemProperties()
    {
        // Name factories
        System.setProperty(AppContextMgr.factoryName,                   "edu.ku.brc.specify.config.SpecifyAppContextMgr");      // Needed by AppContextMgr
        System.setProperty(AppPreferences.factoryName,                  "edu.ku.brc.specify.config.AppPrefsDBIOIImpl");         // Needed by AppReferences
        System.setProperty("edu.ku.brc.ui.ViewBasedDialogFactoryIFace", "edu.ku.brc.specify.ui.DBObjDialogFactory");            // Needed By UIRegistry
        System.setProperty("edu.ku.brc.ui.forms.DraggableRecordIdentifierFactory", "edu.ku.brc.specify.ui.SpecifyDraggableRecordIdentiferFactory"); // Needed By the Form System
        System.setProperty("edu.ku.brc.dbsupport.AuditInterceptor",     "edu.ku.brc.specify.dbsupport.AuditInterceptor");       // Needed By the Form System for updating Lucene and logging transactions
        System.setProperty("edu.ku.brc.dbsupport.DataProvider",         "edu.ku.brc.specify.dbsupport.HibernateDataProvider");  // Needed By the Form System and any Data Get/Set
        System.setProperty("edu.ku.brc.ui.db.PickListDBAdapterFactory", "edu.ku.brc.specify.ui.db.PickListDBAdapterFactory");   // Needed By the Auto Complete UI
        System.setProperty(CustomQueryFactory.factoryName,              "edu.ku.brc.specify.dbsupport.SpecifyCustomQueryFactory");
        System.setProperty(UIFieldFormatterMgr.factoryName,             "edu.ku.brc.specify.ui.SpecifyUIFieldFormatterMgr");    // Needed for CatalogNumberign
    }
    
    public static void persist(Object o)
    {
        if (session != null)
        {
            session.saveOrUpdate(o);
        }
    }

    public static void persist(List<?> oList)
    {
        for (Object o: oList)
        {
            persist(o);
        }
    }
    
    /**
     * Creates a single disciplineType collection.
     * @param disciplineName the name of the Discipline to use
     * @param disciplineTypeName the disciplineType name
     * @return the entire list of DB object to be persisted
     */
    public List<Object> createEmptyDiscipline(final String disciplineName, 
                                              final String disciplineTypeName,
                                              final String username, 
                                              final String userType, 
                                              final String firstName, 
                                              final String lastName, 
                                              final String email)
    {
        Vector<Object> dataObjects = new Vector<Object>();

        Agent             userAgent         = createAgent("", firstName, "", lastName, "", email);
        UserGroup         userGroup         = createUserGroup(disciplineTypeName);
        SpecifyUser       user              = createSpecifyUser(username, email, (short) 0, userGroup, userType);
        DataType          dataType          = createDataType(disciplineTypeName);

        // create tree defs (later we will make the def items and nodes)
        TaxonTreeDef              taxonTreeDef      = createTaxonTreeDef("Sample Taxon Tree");
        GeographyTreeDef          geoTreeDef        = createGeographyTreeDef("Sample Geography Tree");
        GeologicTimePeriodTreeDef gtpTreeDef        = createGeologicTimePeriodTreeDef("Sample Geologic Time Period Tree");
        LithoStratTreeDef         lithoStratTreeDef = createLithoStratTreeDef("Sample LithoStrat Tree");
        StorageTreeDef           locTreeDef        = createStorageTreeDef("Sample Storage Tree");
        
        Institution    institution    = createInstitution("Natural History Museum");
        Division       division       = createDivision(institution, "fish", "Icthyology", "IT", "Icthyology");
        Discipline discipline = createDiscipline(division, disciplineName, disciplineTypeName, dataType, taxonTreeDef, null, null, null, lithoStratTreeDef);

        SpecifyUser.setCurrentUser(user);
        user.addReference(userAgent, "agents");

        dataObjects.add(discipline);
        dataObjects.add(userGroup);
        dataObjects.add(user);
        dataObjects.add(dataType);
        dataObjects.add(taxonTreeDef);
        dataObjects.add(userAgent);
        
        ////////////////////////////////
        // build the tree def items and nodes
        ////////////////////////////////
        List<Object> taxa        = BuildSampleDatabase.createSimpleFishTaxonTree(taxonTreeDef, false);
        List<Object> geos        = BuildSampleDatabase.createSimpleGeography(geoTreeDef);
        List<Object> locs        = BuildSampleDatabase.createSimpleStorage(locTreeDef);
        List<Object> gtps        = BuildSampleDatabase.createSimpleGeologicTimePeriod(gtpTreeDef);
        List<Object> lithoStrats = BuildSampleDatabase.createSimpleLithoStrat(lithoStratTreeDef);
        
        //startTx();
        persist(taxa);
        persist(geos);
        persist(locs);
        persist(gtps);
        persist(lithoStrats);
        
        CatalogNumberingScheme cns = createCatalogNumberingScheme("CatalogNumber", "", true);
        dataObjects.add(cns);

        Collection collection = createCollection("Fish", "Fish", cns, discipline);
        dataObjects.add(collection);

        return dataObjects;
    }

    
    /** 
     * Drops, Creates and Builds the Database.
     * 
     * @throws SQLException
     * @throws IOException
     */
    public boolean setupDatabase(final DatabaseDriverInfo driverInfo,
                                 final String hostName, 
                                 final String dbName, 
                                 final String username, 
                                 final String password, 
                                 final String firstName, 
                                 final String lastName, 
                                 final String email,
                                 final DisciplineType  disciplineType)
    {
        String actionStr = fillDB ? "Creating" : "Initializing";
        log.info(actionStr + " Specify Database Username["+username+"]");
        log.info(actionStr + " Database Schema for "+dbName);
        log.info(actionStr + " schema");
          
        if (fillDB)
        {
            try
            {
                SpecifySchemaGenerator.generateSchema(driverInfo, hostName, dbName, username, password);
            } catch (SQLException ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        
        log.info("Logging into "+dbName+"....");
        
        String connStr = driverInfo.getConnectionStr(DatabaseDriverInfo.ConnectionType.Create, hostName, dbName);
        if (connStr == null)
        {
            connStr = driverInfo.getConnectionStr(DatabaseDriverInfo.ConnectionType.Open, hostName, dbName);
        }
        
        if (!UIHelper.tryLogin(driverInfo.getDriverClassName(), 
                driverInfo.getDialectClassName(), 
                dbName, 
                connStr, 
                username, 
                password))
        {
            log.info("Login Failed!");
            return false;
        }         
        
        session = HibernateUtil.getCurrentSession();

        log.info("Creating database "+dbName+"....");
        
        try
        {
            Thumbnailer thumb = new Thumbnailer();
            File thumbFile = XMLHelper.getConfigDir("thumbnail_generators.xml");
            thumb.registerThumbnailers(thumbFile);
            thumb.setQuality(.5f);
            thumb.setMaxHeight(128);
            thumb.setMaxWidth(128);
    
            AttachmentManagerIface attachMgr = new FileStoreAttachmentManager(UIRegistry.getAppDataSubDir("AttachmentStorage", true));
            AttachmentUtils.setAttachmentManager(attachMgr);
            AttachmentUtils.setThumbnailer(thumb);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        
        if (fillDB)
        {
            log.info("Creating Empty Database");
            
            List<Object> dataObjects = createEmptyDiscipline(disciplineType.getTitle(), 
                                                             disciplineType.getName(),
                                                             username,
                                                             "CollectionManager",
                                                             firstName,
                                                             lastName,
                                                             email);
            
    
            log.info("Saving data into "+dbName+"....");
            log.info("Persisting Data...");
            HibernateUtil.beginTransaction();
            persist(dataObjects);
            HibernateUtil.commitTransaction();
        }
        
        
        SpecifyAppPrefs.initialPrefs();
        
        fmtMgr = new MyFmtMgr();
        
        //tester();

        return true;
    }
    
    protected void myTester()
    {
        StringBuilder  sql = new StringBuilder("From CollectionObject c Join c.collection col Join col.catalogNumberingScheme cns where cns.catalogNumberingSchemeId = 1");
        Session session = HibernateUtil.getNewSession();
        
        try
        {
            
            System.out.println(sql.toString());
            List<?> list = session.createQuery(sql.toString()).setMaxResults(1).list();
            if (list.size() == 1)
            {
                Object[] obj = (Object[])list.get(0);
                CollectionObject co = (CollectionObject)obj[0];
                System.out.println(co.getCatalogNumber());
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            session.close();
        }
        int x = 0;
        x++;
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        if (doInit)
        {
            UIRegistry.setAppName("Specify");
            
            //UIRegistry.setJavaDBDir(derbyPath != null ? derbyPath : UIRegistry.getDefaultWorkingPath() + File.separator + "DerbyDatabases");
            
            setUpSystemProperties();
            
            DisciplineType         disciplineType = DisciplineType.getDiscipline("fish");
            DatabaseDriverInfo driverInfo = DatabaseDriverInfo.getDriver("MySQL");
            
            // This may just initialize the DB and not build it, check data member 'fillDB'
            setupDatabase(driverInfo, "localhost", "Test", "rods", "rods", "guest", "guest", "guest@ku.edu", disciplineType);
            doInit = false;
        }
    }
    
    /**
     * Tests a Numeric CatalogNumberScheme with an empty database.
     */
    public void testNumericNumWithEmptyDB()
    {
        log.info("testEmptyDBNumericNum");
        
        // Test Empty Database
        CatalogNumberUIFieldFormatter catalogNumber = new CatalogNumberUIFieldFormatter();
        catalogNumber.setAutoNumber(new CollectionAutoNumber());

        ValFormattedTextField valText = new ValFormattedTextField(catalogNumber, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        assertEquals(valText.getValue(), "000000001");

    }

    /**
     * Tests a Numeric CatalogNumberScheme with a single Collection.
     */
    public void testNumericDBNum()
    {
        log.info("testNumericDBNum");
        
        Agent      agent      = (Agent)session.createCriteria(Agent.class).list().get(0);
        Collection collection = (Collection)session.createCriteria(Collection.class).list().get(0);
        
        CollectionObject colObj = createCollectionObject("000000100", "RSC100", agent, collection,  3, null, Calendar.getInstance(), "BuildSampleDatabase");
        HibernateUtil.beginTransaction();
        persist(colObj);
        HibernateUtil.commitTransaction();
        
        CatalogNumberUIFieldFormatter catalogNumber = new CatalogNumberUIFieldFormatter();
        catalogNumber.setAutoNumber(new CollectionAutoNumber());
        
        ValFormattedTextField valText = new ValFormattedTextField(catalogNumber, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info(valText.getValue());
        
        assertEquals(valText.getValue(), "000000101");
        
        HibernateUtil.beginTransaction();
        session.delete(colObj);
        HibernateUtil.commitTransaction();
    }
    
    /**
     * Tests a Numeric CatalogNumberScheme with two  Collections sharing the same CatalogNumberScheme.
     */
    public void testNumericTwoCollDBNum()
    {
        log.info("testNumericTwoCollDBNum");
        
        Agent      agent      = (Agent)session.createCriteria(Agent.class).list().get(0);
        Collection collection = (Collection)session.createCriteria(Collection.class).list().get(0);
        
        Discipline disciplinee = (Discipline)session.createCriteria(Discipline.class).list().get(0);
        
        Collection collection2 = createCollection("Fish", "Fish Tissue", collection.getCatalogNumberingScheme(), disciplinee);

        Collection.setCurrentCollection(collection);

        CollectionObject colObj1 = createCollectionObject("000000100", "RSC100", agent, collection,  3, null, Calendar.getInstance(), "BuildSampleDatabase");
        CollectionObject colObj2 = createCollectionObject("000000200", "RSC200", agent, collection2,  3, null, Calendar.getInstance(), "BuildSampleDatabase");
        
        HibernateUtil.beginTransaction();
        persist(colObj1);
        persist(collection2);
        persist(colObj2);
        HibernateUtil.commitTransaction();
        
        CatalogNumberUIFieldFormatter catalogNumber = new CatalogNumberUIFieldFormatter();
        catalogNumber.setAutoNumber(new CollectionAutoNumber());
        
        ValFormattedTextField valText = new ValFormattedTextField(catalogNumber, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info(valText.getValue());
        
        //colObj2.setCollection(null);
        //colObj1.setCollection(null);
        collection2.setCatalogNumberingScheme(null);
        collection.getCatalogNumberingScheme().getCollections().remove(collection2);
        
        HibernateUtil.beginTransaction();
        session.delete(colObj1);
        session.delete(colObj2);
        session.delete(collection2);
        HibernateUtil.commitTransaction();
        
        assertEquals(valText.getValue(), "000000201");
    }

    /**
     * Tests an AlphaNumeric CatalogNumberScheme for Catalog Numbers.
     */
    public void testAlphaNumCatNumEmptyDB()
    {
        log.info("testNumericDBNum");
        
        Discipline disciplinee = (Discipline)session.createCriteria(Discipline.class).list().get(0);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric = createCatalogNumberingScheme("CatalogNumberAN", "", false);
        Collection collection = createCollection("Fish", "Fish Tissue", catNumSchemeAlphaNumeric, disciplinee);

        HibernateUtil.beginTransaction();
        persist(collection);
        HibernateUtil.commitTransaction();

        Collection.setCurrentCollection(collection);
        
        String currentYearCatNum = Calendar.getInstance().get(Calendar.YEAR) + "-000001";
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("CatalogNumber");
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info("["+currentYearCatNum+"]["+valText.getValue()+"]");
        
        Collection.setCurrentCollection(null);
        
        collection.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection);
        
        HibernateUtil.beginTransaction();
        session.delete(collection);
        HibernateUtil.commitTransaction();
        
        assertEquals(valText.getValue(), currentYearCatNum);
    }
    
    /**
     * This tests where two collections share the same CatalogNumberScheme.
     */
    public void testAlphaNumCatNum()
    {
        log.info("testAlphaNumCatNum");
        
        Agent          agent   = (Agent)session.createCriteria(Agent.class).list().get(0);
        Discipline disciplinee = (Discipline)session.createCriteria(Discipline.class).list().get(0);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric = createCatalogNumberingScheme("CatalogNumberAN", "", false);
        Collection collection = createCollection("Fish", "Fish Tissue", catNumSchemeAlphaNumeric, disciplinee);

        int    currentYear       = Calendar.getInstance().get(Calendar.YEAR);
        String currentYearCatNum = currentYear + "-000001";
        String currentYearNext   = currentYear + "-000002";

        CollectionObject colObj = createCollectionObject(currentYearCatNum, "RSC100", agent, collection,  3, null, Calendar.getInstance(), "me");
        HibernateUtil.beginTransaction();
        persist(catNumSchemeAlphaNumeric);
        persist(collection);
        persist(colObj);
        HibernateUtil.commitTransaction();
        
        Collection.setCurrentCollection(collection);
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("CatalogNumber");
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info("["+currentYearNext+"]["+valText.getValue()+"]");
        
        Collection.setCurrentCollection(null);
        
        collection.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection);
        
        HibernateUtil.beginTransaction();
        session.delete(colObj);
        session.delete(collection);
        session.delete(catNumSchemeAlphaNumeric);
        HibernateUtil.commitTransaction();
        
        assertEquals(valText.getValue(), currentYearNext);

        
    }
    
    /**
     * Test Database with three Collections (all Fish) Where two of the Collections share a single CatalogNumberScheme and the third Collection
     * has its own CatalogNumberScheme. This test where the code will get the highest of the two Collections and ignore the Third.
     */
    public void testAlphaNumCatNumTwoColl()
    {
        log.info("testAlphaNumCatNumTwoColl");
        
        Agent          agent      = (Agent)session.createCriteria(Agent.class).list().get(0);
        Discipline disciplinee    = (Discipline)session.createCriteria(Discipline.class).list().get(0);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric = createCatalogNumberingScheme("CatalogNumberAN", "", false);
        Collection collection1 = createCollection("Fish", "Fish Two",       catNumSchemeAlphaNumeric, disciplinee);
        Collection collection2 = createCollection("Fish", "Fish Tissue Two",catNumSchemeAlphaNumeric, disciplinee);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric3 = createCatalogNumberingScheme("CatalogNumber3", "", false);
        Collection collection3 = createCollection("Fish", "Fish Three", catNumSchemeAlphaNumeric3, disciplinee);
        
        int    currentYear        = Calendar.getInstance().get(Calendar.YEAR);
        String currentYearCatNum  = currentYear + "-000001";
        String currentYearCatNum2 = currentYear + "-000002";
        String currentYearCatNum3 = currentYear + "-000300";
        String currentYearNext    = currentYear + "-000003";

        CollectionObject colObj  = createCollectionObject(currentYearCatNum,  "RSC100", agent, collection1, 3, null, Calendar.getInstance(), "me");
        CollectionObject colObj2 = createCollectionObject(currentYearCatNum2, "RSC200", agent, collection2, 3, null, Calendar.getInstance(), "me");
        CollectionObject colObj3 = createCollectionObject(currentYearCatNum3, "RSC300", agent, collection3, 3, null, Calendar.getInstance(), "me");
        
        HibernateUtil.beginTransaction();
        persist(catNumSchemeAlphaNumeric);
        persist(collection1);
        persist(collection2);
        persist(collection3);
        persist(colObj);
        persist(colObj2);
        persist(colObj3);
        HibernateUtil.commitTransaction();
        
        Collection.setCurrentCollection(collection1);
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("CatalogNumber");
        assertNotNull(fmt);
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info("["+currentYearNext+"]["+valText.getValue()+"]");
        
        Collection.setCurrentCollection(null);
        
        collection2.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection2);
        
        collection1.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection1);
        
        collection3.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric3.getCollections().remove(collection3);
        
        HibernateUtil.beginTransaction();
        session.delete(colObj);
        session.delete(colObj2);
        session.delete(colObj3);
        session.delete(collection3);
        session.delete(collection2);
        session.delete(collection1);
        session.delete(catNumSchemeAlphaNumeric);
        session.delete(catNumSchemeAlphaNumeric3);
        HibernateUtil.commitTransaction();
        
        assertEquals(valText.getValue(), currentYearNext);
    }
    
    /**
     * Test Database with three Collections (all Fish) Where two of the Collections share a single CatalogNumberScheme and the third Collection
     * has its own CatalogNumberScheme. This test where the code will get the highest of the two Collections and ignore the Third.
     */
    public void testAlphaNumCatNumTwoCollByYearSameYear()
    {
        log.info("testAlphaNumCatNumTwoCollByYearSameYear");
        
        Agent          agent      = (Agent)session.createCriteria(Agent.class).list().get(0);
        Discipline disciplinee    = (Discipline)session.createCriteria(Discipline.class).list().get(0);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric = createCatalogNumberingScheme("CatalogNumberAlphaNumByYear", "", false);
        Collection collection1 = createCollection("Fish", "Fish Two",       catNumSchemeAlphaNumeric, disciplinee);
        Collection collection2 = createCollection("Fish", "Fish Tissue Two",catNumSchemeAlphaNumeric, disciplinee);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric3 = createCatalogNumberingScheme("CatalogNumber3", "", false);
        Collection collection3 = createCollection("Fish", "Fish Three", catNumSchemeAlphaNumeric3, disciplinee);
        
        int    currentYear        = Calendar.getInstance().get(Calendar.YEAR);
        String currentYearCatNum  = currentYear + "-000001";
        String currentYearCatNum2 = currentYear + "-000002";
        String currentYearCatNum3 = currentYear + "-000300";
        String currentYearNext    = currentYear + "-000003";

        CollectionObject colObj  = createCollectionObject(currentYearCatNum,  "RSC100", agent, collection1, 3, null, Calendar.getInstance(), "me");
        CollectionObject colObj2 = createCollectionObject(currentYearCatNum2, "RSC200", agent, collection2, 3, null, Calendar.getInstance(), "me");
        CollectionObject colObj3 = createCollectionObject(currentYearCatNum3, "RSC300", agent, collection3, 3, null, Calendar.getInstance(), "me");
        
        HibernateUtil.beginTransaction();
        persist(catNumSchemeAlphaNumeric);
        persist(collection1);
        persist(collection2);
        persist(collection3);
        persist(colObj);
        persist(colObj2);
        persist(colObj3);
        HibernateUtil.commitTransaction();
        
        Collection.setCurrentCollection(collection1);
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("CatalogNumberAlphaNumByYear");
        assertNotNull(fmt);
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info("["+currentYearNext+"]["+valText.getValue()+"]");
        
        Collection.setCurrentCollection(null);
        
        collection2.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection2);
        
        collection1.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection1);
        
        collection3.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric3.getCollections().remove(collection3);
        
        HibernateUtil.beginTransaction();
        session.delete(colObj);
        session.delete(colObj2);
        session.delete(colObj3);
        session.delete(collection3);
        session.delete(collection2);
        session.delete(collection1);
        session.delete(catNumSchemeAlphaNumeric);
        session.delete(catNumSchemeAlphaNumeric3);
        HibernateUtil.commitTransaction();
        
        assertEquals(valText.getValue(), currentYearNext);
    }
    
    /**
     * Test Database with three Collections (all Fish) Where two of the Collections share a single CatalogNumberScheme and the third Collection
     * has its own CatalogNumberScheme. This test where the code will get the highest of the two Collections and ignore the Third.
     */
    public void testAlphaNumCatNumTwoCollByYearDiffYear()
    {
        log.info("testAlphaNumCatNumTwoCollByYearDiffYear");
        
        Agent          agent      = (Agent)session.createCriteria(Agent.class).list().get(0);
        Discipline disciplinee    = (Discipline)session.createCriteria(Discipline.class).list().get(0);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric = createCatalogNumberingScheme("CatalogNumberAlphaNumByYear", "", false);
        Collection collection1 = createCollection("Fish", "Fish Two",       catNumSchemeAlphaNumeric, disciplinee);
        Collection collection2 = createCollection("Fish", "Fish Tissue Two",catNumSchemeAlphaNumeric, disciplinee);
        
        CatalogNumberingScheme catNumSchemeAlphaNumeric3 = createCatalogNumberingScheme("CatalogNumber3", "", false);
        Collection collection3 = createCollection("Fish", "Fish Three", catNumSchemeAlphaNumeric3, disciplinee);
        
        int    currentYear        = Calendar.getInstance().get(Calendar.YEAR);
        String currentYearCatNum  = "2005-000001";
        String currentYearCatNum2 = "2005-000002";
        String currentYearCatNum3 = currentYear + "-000300";
        String currentYearNext    = currentYear + "-000001";

        CollectionObject colObj  = createCollectionObject(currentYearCatNum,  "RSC100", agent, collection1, 3, null, Calendar.getInstance(), "me");
        CollectionObject colObj2 = createCollectionObject(currentYearCatNum2, "RSC200", agent, collection2, 3, null, Calendar.getInstance(), "me");
        CollectionObject colObj3 = createCollectionObject(currentYearCatNum3, "RSC300", agent, collection3, 3, null, Calendar.getInstance(), "me");
        
        HibernateUtil.beginTransaction();
        persist(catNumSchemeAlphaNumeric);
        persist(collection1);
        persist(collection2);
        persist(collection3);
        persist(colObj);
        persist(colObj2);
        persist(colObj3);
        HibernateUtil.commitTransaction();
        
        Collection.setCurrentCollection(collection1);
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("CatalogNumberAlphaNumByYear");
        assertNotNull(fmt);
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.updateAutoNumbers();
        
        log.info("["+currentYearNext+"]["+valText.getValue()+"]");
        
        Collection.setCurrentCollection(null);
        
        collection2.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection2);
        
        collection1.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric.getCollections().remove(collection1);
        
        collection3.setCatalogNumberingScheme(null);
        catNumSchemeAlphaNumeric3.getCollections().remove(collection3);
        
        HibernateUtil.beginTransaction();
        session.delete(colObj);
        session.delete(colObj2);
        session.delete(colObj3);
        session.delete(collection3);
        session.delete(collection2);
        session.delete(collection1);
        session.delete(catNumSchemeAlphaNumeric);
        session.delete(catNumSchemeAlphaNumeric3);
        HibernateUtil.commitTransaction();
        
        assertEquals(valText.getValue(), currentYearNext);
    }
    
    /**
     * Tests the highest Year in the database is less than the current year.
     * and this tests when the user doesn't change the year in the text field.
     */
    public void testAccessionAlphaNumericDBNumOldYear()
    {
        log.info("testAccessionAlphaNumericDBNumOldYear");
        
        Accession accession = createAccession(null, "XXX", "XXXX", "2005-IC-001", "XXXX", Calendar.getInstance(), Calendar.getInstance());
        HibernateUtil.beginTransaction();
        persist(accession);
        HibernateUtil.commitTransaction();
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("AccessionNumber");
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        try
        {
            valText.updateAutoNumbers();
            
            String currentYearCatNum = Calendar.getInstance().get(Calendar.YEAR) + "-AA-001";
            log.info(currentYearCatNum);
            log.info(valText.getValue());
            assertEquals(valText.getValue(), currentYearCatNum);
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        HibernateUtil.beginTransaction();
        session.delete(accession);
        HibernateUtil.commitTransaction();
    }
    
    /**
     * Tests the highest Year in the database is less than the current year.
     * and this tests when the user doesn't change the year in the text field.
     */
    public void testAccessionAlphaNumericDBNumOldYearWithEdit()
    {
        log.info("testAlphaNumericDBNumOldYear");
        
        Accession accession = createAccession(null, "XXX", "XXXX", "2005-IC-001", "XXXX", Calendar.getInstance(), Calendar.getInstance());
        HibernateUtil.beginTransaction();
        persist(accession);
        HibernateUtil.commitTransaction();
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("AccessionNumber");
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.setText("2005-IC-###");
        valText.updateAutoNumbers();
        
        log.info(valText.getValue());
        
        // NOTE: Must delete before the assert
        HibernateUtil.beginTransaction();
        session.delete(accession);
        HibernateUtil.commitTransaction();  
        
        assertEquals(valText.getValue(), "2005-IC-002");
    }

    /**
     * Tests the highest Year in the database is less than the current year.
     * and this tests when the user doesn't change the year in the text field.
     */
    public void testAlphaNumWithTwoCollections()
    {
        log.info("testAlphaNumericDBNumOldYear");
        
        Accession accession = createAccession(null, "XXX", "XXXX", "2005-IC-001", "XXXX", Calendar.getInstance(), Calendar.getInstance());
        HibernateUtil.beginTransaction();
        persist(accession);
        HibernateUtil.commitTransaction();
        
        UIFieldFormatterIFace fmt = fmtMgr.getFmt("AccessionNumber");
        ValFormattedTextField valText = new ValFormattedTextField(fmt, false);
        valText.setValue(null, null);
        
        valText.setText("2005-IC-###");
        valText.updateAutoNumbers();
        
        log.info(valText.getValue());
        
        // NOTE: Must delete before the assert
        HibernateUtil.beginTransaction();
        session.delete(accession);
        HibernateUtil.commitTransaction();  
        
        assertEquals(valText.getValue(), "2005-IC-002");
    }


    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        log.info("tearDown");
    }

    class MyFmtMgr extends SpecifyUIFieldFormatterMgr
    {
        /**
         * Returns the DOM it is suppose to load the formatters from.
         * @return Returns the DOM it is suppose to load the formatters from.
         */
        protected Element getDOM() throws Exception
        {
            return XMLHelper.readDOMFromConfigDir("backstop/uiformatters.xml");
        }
        
        public UIFieldFormatterIFace getFmt(final String name)
        {
            return getFormatterInternal(name);

        }
    }
}

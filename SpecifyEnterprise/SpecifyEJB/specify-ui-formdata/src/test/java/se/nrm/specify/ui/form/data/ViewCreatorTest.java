package se.nrm.specify.ui.form.data;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.ui.form.data.xml.model.DataFormatter;
import se.nrm.specify.ui.form.data.xml.model.DataObjFormatter;
import se.nrm.specify.ui.form.data.xml.model.ViewData;

/**
 *
 * @author idali
 */
public class ViewCreatorTest {

    private final Logger logger = LoggerFactory.getLogger(ViewCreatorTest.class);
    private ViewCreator testInstance;
    private String discipline = "fish";
    private String viewname = "CollectionObject";
    private String viewInCommon = "Borrow";
    private String viewInGlobal = "Address";
    private String commonview = "common";
    private String globalview = "global";
       
    @Before
    public void setUp() {
        testInstance = new ViewCreator(discipline);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createDisciplineView method, of class ViewCreator.
     */
    @Test
    public void testCreateDisciplineView() throws Exception {

        logger.info("testCreateDisciplineView");

        testInstance.createDisciplineView(viewname);
        ViewData viewdata = testInstance.getViewdata(viewname);

        String expResult = "Collection Object";
        assertNotNull(viewdata);
        assertEquals(expResult, viewdata.getView().getViewDefName());
        assertEquals(expResult, viewdata.getViewdef().getViewDefName());
    }

    /**
     * Test of createDisciplineView method, of class ViewCreator.  
     * View is not exist in discipline view, but in common view.
     */
    @Test
    public void testCreateViewInCommonView() throws Exception {

        logger.info("testCreateViewInCommonView");

        testInstance.createDisciplineView(viewInCommon);
        ViewData viewdata = testInstance.getViewdata(viewInCommon);

        String expResult = "Borrow";
        assertNotNull(viewdata);
        assertEquals(expResult, viewdata.getView().getViewDefName());
        assertEquals(expResult, viewdata.getViewdef().getViewDefName());
    }

    /**
     * Test of createDisciplineView method, of class ViewCreator.  
     * View is not exist in discipline and common view, but in global view.
     */
    @Test
    public void testCreateViewInGlobalView() throws Exception {

        logger.info("testCreateViewInGlobalView");

        testInstance.createDisciplineView(viewInGlobal);
        ViewData viewdata = testInstance.getViewdata(viewInGlobal);

        String expResult = "Address";
        assertNotNull(viewdata);
        assertEquals(expResult, viewdata.getView().getViewDefName());
        assertEquals(expResult, viewdata.getViewdef().getViewDefName());
    }

    /**
     * Test of createView method, of class ViewCreator.
     */
    @Test
    public void testCreateView() throws Exception {

        logger.info("testCreateView");

        testInstance.createView(commonview, viewInCommon);

        ViewData viewdata = testInstance.getViewdata(viewInCommon);

        String expResult = "Borrow";
        assertNotNull(viewdata);
        assertEquals(expResult, viewdata.getView().getViewDefName());
        assertEquals(expResult, viewdata.getViewdef().getViewDefName());
    }
    
    
    /**
     * Test of createView method, of class ViewCreator.
     */
    @Test
    public void testCreateViewInGlobal() throws Exception {

        logger.info("testCreateView");

        testInstance.createView(commonview, viewInGlobal);

        ViewData viewdata = testInstance.getViewdata(viewInGlobal);

        String expResult = "Address";
        assertNotNull(viewdata);
        assertEquals(expResult, viewdata.getView().getViewDefName());
        assertEquals(expResult, viewdata.getViewdef().getViewDefName());
    }
      
    /**
     * Test of createDataObjectFormatter method, of class ViewCreator.
     */
    @Test
    public void testCreateDataObjectFormatter() throws Exception { 
        
        logger.info("testCreateView");
         
        testInstance.createDataObjectFormatter(); 
        
        DataFormatter formatter = DataObjFormatter.getDataFormatter("determination");
        
        assertNotNull(formatter);
        assertEquals("Determination", formatter.getName()); 
    }

    /**
     * Test of getViewDataMap method, of class ViewCreator.
     */
    @Test
    public void testGetViewDataMap() throws Exception {  
        
        logger.info("testGetViewDataMap");
        
        Map expResult = new HashMap();
        Map result = testInstance.getViewDataMap();
         
        assertEquals(expResult, result); 
        assertEquals(0, result.size());
        
        testInstance.createDisciplineView(viewname);
        assertEquals(1, result.size());
    }

    /**
     * Test of getViewdata method, of class ViewCreator.
     */
    @Test
    public void testGetViewdata() throws Exception {
       
        logger.info("testGetViewdata");
           
        ViewData result = testInstance.getViewdata(viewname);
        String expResult = "Collection Object";
        assertNotNull(result);
        assertEquals(expResult, result.getView().getViewDefName());
        assertEquals(expResult, result.getViewdef().getViewDefName());
    }

    /**
     * Test of getFormatter method, of class ViewCreator.
     */
    @Test
    public void testGetFormatter() throws Exception {
        
        logger.info("testGetFormatter");
   
        DataObjFormatter result = testInstance.getFormatter();
        assertEquals(null, result); 
        
        testInstance.createDataObjectFormatter(); 
        
        DataFormatter formatter = DataObjFormatter.getDataFormatter("determination");
        
        assertNotNull(formatter);
        assertEquals("Determination", formatter.getName()); 
    }
}

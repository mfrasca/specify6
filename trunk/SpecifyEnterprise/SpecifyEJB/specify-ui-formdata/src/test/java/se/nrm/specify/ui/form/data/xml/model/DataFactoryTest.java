package se.nrm.specify.ui.form.data.xml.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author idali
 */
public class DataFactoryTest {

    private final Logger logger = LoggerFactory.getLogger(DataFactoryTest.class);
    
    private String DATASOURCE_PATH = "/xmlfiles/";
    private String VIEW_XML = ".views.xml";
    private String DATA_OBJ_FORMATTER_XML = "dataobj_formatters.xml";
    private String view = "fish";
    private InputStream viewIs;
    private InputStream formatterIs;
    
    private String strResource;
    private String strResource1;
    
    private DataFactory testInstance;

    @Before
    public void setUp() {
        testInstance = new DataFactory();
        
        StringBuilder sb1 = new StringBuilder();
        sb1.append(DATASOURCE_PATH);
        sb1.append(view);
        sb1.append(VIEW_XML);
        strResource = sb1.toString();
        
        viewIs = this.getClass().getResourceAsStream(strResource);
        
        
        
        StringBuilder sb2 = new StringBuilder();
        sb2.append(DATASOURCE_PATH);
        sb2.append(DATA_OBJ_FORMATTER_XML);
        
        strResource1 = sb2.toString();
        formatterIs = this.getClass().getResourceAsStream(strResource1);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class DataFactory.
     */
    @Test
    public void testGetInstance() {
        
        logger.info("testGetInstance");
     
        DataFactory result = DataFactory.getInstance();
        assertNotNull(result);
    }

    /**
     * Test of getViewdataByInputStream method, of class DataFactory.
     */
    @Test
    public void testGetViewdataByInputStream() {
        
        logger.info("testGetViewdataByInputStream");
         
        ViewXMLData result = testInstance.getViewdataByInputStream(viewIs); 
        assertNotNull(result);
        assertNotNull(result.getViewData("CollectionObject")); 
    }

    /**
     * Test of getViewDataFromResource method, of class DataFactory.
     */
    @Test
    public void testGetViewDataFromResource() {
        
        logger.info("testGetViewdataByInputStream");
           
        ViewXMLData result = testInstance.getViewDataFromResource(strResource);
        assertNotNull(result);
    }

    /**
     * Test of getViewDataByDiscipline method, of class DataFactory.
     */
    @Test
    public void testGetViewDataByDiscipline() {
        
        logger.info("testGetViewDataByDiscipline");
           
        ViewXMLData result = testInstance.getViewDataByDiscipline("fish");
        assertNotNull(result);
    }

    /**
     * Test of getDataFormatterByInputStream method, of class DataFactory.
     */
    @Test
    public void testGetDataFormatterByInputStream() {
      
        logger.info("testGetDataFormatterByInputStream");
           
        DataObjFormatter result = testInstance.getDataFormatterByInputStream(formatterIs);
        assertNotNull(result);
    }

    /**
     * Test of getDataFormatterByResource method, of class DataFactory.
     */
    @Test
    public void testGetDataFormatterByResource() {
        
        logger.info("testGetDataFormatterByResource");
           
        DataObjFormatter result = testInstance.getDataFormatterByResource(strResource1);
        assertNotNull(result); 
    }
}

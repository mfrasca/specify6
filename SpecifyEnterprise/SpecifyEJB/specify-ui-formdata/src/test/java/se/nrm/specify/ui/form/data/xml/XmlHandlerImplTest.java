package se.nrm.specify.ui.form.data.xml;
 
import java.io.File; 
import java.io.InputStream; 
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author idali
 */
public class XmlHandlerImplTest {
    
    private final Logger logger = LoggerFactory.getLogger(XmlHandlerImplTest.class);

    private XmlHandler xmlHandlerInstance;

    public XmlHandlerImplTest() {
    }
 
    @Before
    public void setUp() {
        xmlHandlerInstance = new XmlHandlerImpl();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseXml method, of class XmlHandler.
     */
    @Test
    public void testParseXml() throws Exception {
        
        logger.info("testParseXml");
        
        // First create an InputStream to use in the test,
        // its origin is a local xml file
        InputStream inputStream = XmlHandlerImplTest.class.getResourceAsStream("/xmlfiles/fish.views.xml");

        assertNotNull(inputStream);

        Document parsedDoc = xmlHandlerInstance.parseXml(inputStream);
        assertNotNull(parsedDoc);

        Element rootElement = parsedDoc.getDocumentElement();
        // assert that we can extract the attribute "OBJID" from the parsed document
        String result = rootElement.getAttribute("name");
        String expResult = "Fish Views";

        assertEquals(expResult, result);
    }

    /**
     * Test of serializeXml method, of class XmlHandler.
     */
    @Test
    public void testSerializeXml() throws Exception {
        
        logger.info("testSerializeXml");
        
        // first, create a DOM document which we can use in the test
        // we create it from a local xml resource
        InputStream inputStream = XmlHandlerImplTest.class.getResourceAsStream("/xmlfiles/fish.views.xml");

        assertNotNull(inputStream);
        Document parsedDoc = xmlHandlerInstance.parseXml(inputStream);
        assertNotNull(parsedDoc);
        // Test that the size of the returned array is what we expect it to be
        byte[] serializedXml = xmlHandlerInstance.serializeXml(parsedDoc);
        assertTrue(serializedXml.length > 0);

    }

    /**
     * Test of readFile method, of class XmlHandler.
     */
    @Test
    public void testReadFile() throws Exception {
        
        logger.info("testReadFile");
        // first, create a "dummy" File which we can use in the test
        File testFile = File.createTempFile("test", "file");
        // Test that the size of the returned array is 0
        byte[] resultArray = xmlHandlerInstance.readFile(testFile);
        assertEquals(resultArray.length, 0);
    }

    /**
     * Test of writeFile method, of class XmlHandler.
     */
    @Test
    public void testWriteFile() throws Exception {
        
        logger.info("testWriteFile");
        // first, create a "dummy" File which we can use in the test
        File testFile = File.createTempFile("test", "file");
        // Create a test string which will be used as a byte array
        String test = "test";
        // not sure how to assert since its a void method
        xmlHandlerInstance.writeFile(testFile, test.getBytes());
    } 
}

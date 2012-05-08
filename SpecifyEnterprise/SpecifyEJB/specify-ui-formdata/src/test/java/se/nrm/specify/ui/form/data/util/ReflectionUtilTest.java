package se.nrm.specify.ui.form.data.util;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.lang.reflect.Field;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author idali
 */
public class ReflectionUtilTest {
    
    private final Logger logger = LoggerFactory.getLogger(ReflectionUtilTest.class);
    
    private ReflectionUtil testInstance;
     
    @Before
    public void setUp() {
        testInstance = new ReflectionUtil();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getField method, of class ReflectionUtil.
     */
    @Test
    public void testGetField() throws ClassNotFoundException {
        
        logger.info("testGetField");
        
        String classname = "se.nrm.specify.datamodel.Collectionobject";
        Class clazz = Class.forName(classname);
        String fieldName = "catalogNumber";
   
        Field result = ReflectionUtil.getField(clazz, fieldName);
        assertNotNull(result);
         
        assertEquals("catalogNumber", result.getName());
    }

    /**
     * Test of getClassByName method, of class ReflectionUtil.
     */
    @Test
    public void testGetClassByName() throws ClassNotFoundException {
        
        logger.info("testGetClassByName");
        
        String classname = "CollectionObject";
         
        Class result = ReflectionUtil.getClassByName(classname);
        assertEquals("Collectionobject", result.getSimpleName());
    }
}

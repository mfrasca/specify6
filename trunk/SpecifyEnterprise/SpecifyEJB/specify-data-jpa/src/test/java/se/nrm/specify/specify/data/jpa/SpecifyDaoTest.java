package se.nrm.specify.specify.data.jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;   
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;  
import org.junit.After; 
import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*; 
import static org.mockito.MockitoAnnotations.initMocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Geography;

import se.nrm.specify.datamodel.Geographytreedefitem;   

/**
 *
 * @author idali
 */
public class SpecifyDaoTest {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private SpecifyDao testInstance; 
    private Address address;
    private List<Geography> geographyList;
    
    @Mock private EntityManager entityManager;
    @Mock private Query query;
    
    public SpecifyDaoTest() {
    } 
    
    @Before
    public void setUp() {
        
        initMocks(this);
        
        // prepair test data
        address = new Address();
        address.setAddressID(1);
        address.setAddress("Frescativägen 40");
        address.setCity("Stockholm");
        address.setCountry("Sweden");
        
        // prepair test data
        geographyList = new ArrayList<Geography>();
        geographyList.add(new Geography(1, null, "Africa", 100));
        geographyList.add(new Geography(2, null, "Asia", 100));
        geographyList.add(new Geography(3, null, "Europe", 100));
        geographyList.add(new Geography(4, null, "North America", 100));
        geographyList.add(new Geography(5, null, "South America", 100)); 
         
        testInstance = new SpecifyDaoImpl(entityManager);
    }
    
    @After
    public void tearDown() {
        testInstance = null; 
    }

    /**
     * Test of getById method, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetById() {
        
        logger.info("testGetById");
        
        when(entityManager.find(Address.class, 1, LockModeType.OPTIMISTIC)).thenReturn(address); 
        
        int id = 1; 
       
        Address expResult = address;
        Address result = testInstance.getById(id, Address.class);
        assertEquals(expResult, result); 
        assertEquals(expResult.getAddress(), result.getAddress());
        assertEquals(expResult.getCity(), result.getCity());
        assertEquals(expResult.getCountry(), result.getCountry());
    }
    
    /**
     * Test of createEntity method, of class SpecifyDaoImpl
     */
    @Test
    public void testCreateEntity() {
        
        logger.info("createEntity");
        
        testInstance.createEntity(address);
        
        verify(entityManager).persist(address);
    }
    
    /**
     * Test of deleteEntity method, of class SpecifyDaoImpl
     */
    @Test
    public void testDeleteEntity() {
        
        logger.info("createEntity");
        
        testInstance.deleteEntity(address);
        verify(entityManager).remove(address);
    }

    /**
     * Test of editEntity method, of class SpecifyDaoImpl.
     */
    @Test
    public void testEditEntity() throws Throwable {
        
        logger.info("testEditEntity");
        
        address.setAddress("Renlavsgangen");
        address.setCity("Tyreso");
         
        String expected = "Successful";
        String result = testInstance.editEntity(address); 
        
        assertEquals(expected, result);
        verify(entityManager).merge(address); 
        verify(entityManager).flush();
    }
    
    /**
     * Test of OptimisticLockException 
     */
    @Test 
    public void testOptimisticLockExceptionWhenEditEntity() {
        
        logger.info("testOptimisticLockExceptionWhenEditEntity");
        
        doThrow(new OptimisticLockException()).when(entityManager).flush();
        
        String expected = address.toString() + "cannot be updated because it has changed or been deleted since it was last read. ";
        String result = testInstance.editEntity(address);
         
        assertEquals(expected, result);
        verify(entityManager, times(0)).merge(address); 
    }
    

    /**
     * Test of getGeographyListByGeographytreedefitemId method, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetGeographyListByGeographytreedefitemId() {
        
        logger.info("testGetGeographyListByGeographytreedefitemId");
        
        Geographytreedefitem g = new Geographytreedefitem(2);
         
        when(entityManager.createNamedQuery("Geography.findByGeographytreedefitemId")).thenReturn(query); 
        query.setParameter("geographyTreeDefItemID", g);
        when(query.getResultList()).thenReturn(geographyList); 
         
        List<Geography> expResult = geographyList;
        List<Geography> result = testInstance.getGeographyListByGeographytreedefitemId(g);
        
        assertNotNull(result);
        assertEquals(expResult, result); 
        assertEquals(5, result.size());
    }    
}

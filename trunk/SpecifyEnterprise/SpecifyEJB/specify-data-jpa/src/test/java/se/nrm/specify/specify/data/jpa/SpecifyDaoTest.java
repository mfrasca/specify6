package se.nrm.specify.specify.data.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List; 
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query; 
import javax.validation.ConstraintViolationException;
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
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.Geography;

import se.nrm.specify.datamodel.Geographytreedefitem;
import se.nrm.specify.datamodel.Sppermission;

/**
 *
 * @author idali
 */
public class SpecifyDaoTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private SpecifyDao testInstance;
    
    private Address address;
    private List<Geography> geographyList;
    private Sppermission spPermission;
    
    
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

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
        spPermission = new Sppermission();
        spPermission.setSpPermissionID(1);
        spPermission.setActions("add");

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
     * Test of getById method for optimistic Lock Entities, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetByIdForOptimisticLockEntity() {

        logger.info("optimisticLockEntity");

        when(entityManager.find(Address.class, 1, LockModeType.OPTIMISTIC)).thenReturn(address);

        int id = 1;

        Address expResult = address;
        Address result = testInstance.getById(id, Address.class);
        assertEquals(expResult, result);
        assertEquals(expResult.getAddress(), result.getAddress());
        assertEquals(expResult.getCity(), result.getCity());
        assertEquals(expResult.getCountry(), result.getCountry());
        
        verify(entityManager).find(Address.class, 1, LockModeType.OPTIMISTIC);
        verify(entityManager, times(0)).find(Address.class, 1, LockModeType.PESSIMISTIC_WRITE);
    }
    
    /**
     * Test of getById method for PESSIMISTIC_WRITE lock entities, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetByIdForPessimisticLockEntity() {
        
        logger.info("testGetByIdForPessimisticLockEntity");
        
        when(entityManager.find(Sppermission.class, 1, LockModeType.PESSIMISTIC_WRITE)).thenReturn(spPermission);
        
        int id = 1;
        Sppermission expResult = spPermission;
        Sppermission result = testInstance.getById(id, Sppermission.class);
        
        assertEquals(expResult, result);
        
        verify(entityManager).find(Sppermission.class, 1, LockModeType.PESSIMISTIC_WRITE);
        verify(entityManager, times(0)).find(Sppermission.class, 1, LockModeType.OPTIMISTIC);
    }

    /**
     * Test of getAll method, of class SpecifyDaoImpl
     */
    @Test
    public void testGetAll() {

        logger.info("testGetAll");

        when(entityManager.createNamedQuery("Geography.findAll")).thenReturn(query);
        when(query.getResultList()).thenReturn(geographyList);

        List<Geography> result = testInstance.getAll(Geography.class);

        assertEquals(result, geographyList);
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
     * Test of ConstrainViolationException when perisiting
     */
    @Test
    public void testConstraintViolationExceptionWhenCreateEntity() {

        logger.info("testConstraintViolationExceptionWhenCreateEntity");

        ConstraintViolationException e = mock(ConstraintViolationException.class);

        when(e.getConstraintViolations()).thenReturn(new HashSet());
        doThrow(new ConstraintViolationException(e.getConstraintViolations())).when(entityManager).persist(address);

        verifyZeroInteractions(entityManager);
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
     * Test of ConstrainViolationException when deleting
     */
    @Test
    public void testConstraintViolationExceptionWhenDeleteEntity() {

        logger.info("testConstraintViolationExceptionWhenDeleteEntity");

        ConstraintViolationException e = mock(ConstraintViolationException.class);

        when(e.getConstraintViolations()).thenReturn(new HashSet());
        doThrow(new ConstraintViolationException(e.getConstraintViolations())).when(entityManager).remove(address);

        verifyZeroInteractions(entityManager);
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
    }

    /**
     * Test of testGetCollectingEventByStationFieldNumber method, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetCollectingEventByStationFieldNumber() {
        
        logger.info("testGetCollectingEventByStationFieldNumber");
        
        String stationFieldNumber = "test number";
        Collectingevent event = new Collectingevent();
        event.setStationFieldNumber(stationFieldNumber);
        
        when(entityManager.createNamedQuery("Collectingevent.findByStationFieldNumber")).thenReturn(query);
        query.setParameter("stationFieldNumber", stationFieldNumber);
        when(query.getSingleResult()).thenReturn(event);
        
        Collectingevent result = testInstance.getCollectingEventByStationFieldNumber(stationFieldNumber);
        assertNotNull(result);
        assertEquals(event, result); 
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

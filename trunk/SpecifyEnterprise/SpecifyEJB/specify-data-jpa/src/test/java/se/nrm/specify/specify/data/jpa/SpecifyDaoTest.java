package se.nrm.specify.specify.data.jpa;
 
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
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
import se.nrm.specify.datamodel.Collectionobject; 
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Geography;
 
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.Sppermission;
import se.nrm.specify.datamodel.Taxon;

/**
 *
 * @author idali
 */
public class SpecifyDaoTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SpecifyDao testInstance;
    private Address address;
    private List<Geography> geographyList;
    private List<Dnasequence> dnasequenceList;
    private Sppermission spPermission;
    private List<String> stringList;
    private List<Integer> intList;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;
    @Mock
    private TypedQuery<String> stringquery;
    @Mock
    private TypedQuery<Integer> intquery;
    @Mock
    private CriteriaBuilder cb;
    @Mock
    private CriteriaQuery<String> q;
    @Mock
    private Root<Determination> d;
    @Mock
    private CompoundSelection<java.lang.String> cs;
    @Mock
    private Path path;
    @Mock
    private ParameterExpression<Collectingevent> collectingEvent;
    @Mock
    private ParameterExpression<Locality> localityId;
    @Mock
    private ParameterExpression<String> code;
    @Mock
    private ParameterExpression<Boolean> isCurrent;
    @Mock
    private Taxon taxon;

    public SpecifyDaoTest() {
    }

    @Before
    public void setUp() {

        initMocks(this);

        // prepair test data
        address = new Address();
        address.setAddressId(1);
        address.setAddress("Frescativägen 40");
        address.setCity("Stockholm");
        address.setCountry("Sweden");

        // prepair test data
        spPermission = new Sppermission();
        spPermission.setPermissionId(1);
        spPermission.setActions("add");

        // prepair test data
        geographyList = new ArrayList<Geography>();
        geographyList.add(new Geography(1, null, "Africa", 100));
        geographyList.add(new Geography(2, null, "Asia", 100));
        geographyList.add(new Geography(3, null, "Europe", 100));
        geographyList.add(new Geography(4, null, "North America", 100));
        geographyList.add(new Geography(5, null, "South America", 100));

        // prepair thes data
        dnasequenceList = new ArrayList<Dnasequence>();
        dnasequenceList.add(new Dnasequence(1));
        dnasequenceList.add(new Dnasequence(2));

        stringList = new ArrayList<String>();
        stringList.add("test 1");
        stringList.add("test 2");
        stringList.add("test 3");

        intList = new ArrayList<Integer>();
        intList.add(Integer.valueOf(12));
        intList.add(Integer.valueOf(2));
        intList.add(Integer.valueOf(3));
        intList.add(Integer.valueOf(6));
        intList.add(Integer.valueOf(6));
        intList.add(Integer.valueOf(12));
        intList.add(Integer.valueOf(12));
 
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
     * Test throw OptimisticLockException whe getById method, of class SpecifyDaoImpl.
     */
    @Test
    public void testOptimisticLockExceptionWhenGetEntityById() {

        logger.info("testOptimisticLockExceptionWhenGetEntityById");

        doThrow(new OptimisticLockException()).when(entityManager).flush();

        int id = 1;

        Address bean = testInstance.getById(id, Address.class);

        verify(entityManager).find(Address.class, 1, LockModeType.OPTIMISTIC);
        verify(entityManager, times(1)).refresh(bean);
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
    public void testCreateEntity() throws MySQLIntegrityConstraintViolationException {

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
//    @Test
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
//    @Test
    public void testOptimisticLockExceptionWhenEditEntity() {

        logger.info("testOptimisticLockExceptionWhenEditEntity");

        doThrow(new OptimisticLockException()).when(entityManager).flush();

        String expected = address.toString() + "cannot be updated because it has changed or been deleted since it was last read. ";
        String result = testInstance.editEntity(address);

        assertEquals(expected, result);
    }

 
    /**
     * Test of testGetDeterminationsByTaxonNameAndCollectingevent method, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetDeterminationsByTaxonNameAndCollectingevent() {

        logger.info("testGetDeterminationsByTaxonNameAndCollectingevent");

        String taxonName = "taxonname";
        Collectingevent event = new Collectingevent(18);
        String collectionCode = "test";

        List<Determination> list = new ArrayList<Determination>();
        list.add(new Determination(10));
        list.add(new Determination(18));

        when(entityManager.createNamedQuery("Determination.findCurrentByTaxonNameAndEvent")).thenReturn(query);
        query.setParameter("fullName", taxonName);
        query.setParameter("collectingEventID", event);
        query.setParameter("code", collectionCode);
        query.setParameter("isCurrent", true);

        when(query.getResultList()).thenReturn(list);

        List<Determination> result = testInstance.getDeterminationsByTaxonNameAndCollectingevent(taxonName, event, collectionCode);
        assertNotNull(result);
        assertEquals(list, result);
        assertEquals(2, result.size());
    }

    /**
     * Test of testGetLastCollectionobjectByGroup method, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetLastCollectionobjectByGroup() {

        logger.info("testGetLastCollectionobjectByGroup");

        Collectionobject obj = new Collectionobject(18);

        List<Collectionobject> list = new ArrayList<Collectionobject>();
        list.add(new Collectionobject(18));

        String code = "testcode";
        when(entityManager.createNamedQuery("Collectionobject.findLastRecordByCollectionCode")).thenReturn(query);
        query.setParameter("code", code);
        query.setMaxResults(1);

        when(query.getResultList()).thenReturn(list);
        Collectionobject result = testInstance.getLastCollectionobjectByGroup(code);

        assertNotNull(result);
        assertEquals(obj, result);
    }

    /**
     * Test of testGetCollectionobjectByCollectingEventAndYesno2 method, of class SpecifyDaoImpl.
     */
    @Test
    public void testGetCollectionobjectByCollectingEventAndYesno2() {

        logger.info("testGetCollectionobjectByCollectingEventAndYesno2");

        List<Collectionobject> list = new ArrayList<Collectionobject>();
        list.add(new Collectionobject(18));
        list.add(new Collectionobject(32));

        Collectingevent event = new Collectingevent(18);
        when(entityManager.createNamedQuery("Collectionobject.findByCollectingEventIDAndYesNo2")).thenReturn(query);
        query.setParameter("collectingEventID", event);
        when(query.getResultList()).thenReturn(list);

        List<Collectionobject> result = testInstance.getCollectionobjectByCollectingEventAndYesno2(event);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(list, result);
    }

//    /**
//     * Test of getDeterminationsByCollectingEvent method, of class SpecifyDaoImpl.
//     */
//    @Test
//    public void testGetDeterminationsByCollectingEvent() {
//
//        logger.info("getDeterminationsByCollectingEvent");
//
//        Collectingevent event = new Collectingevent(18);
//        String collectionCode = "test";
//
//        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
//        when(cb.createQuery(String.class)).thenReturn(q);
//        when(q.from(Determination.class)).thenReturn(d);
//        when(d.get(anyString())).thenReturn(path);
//        when(path.get(anyString())).thenReturn(path);
//
//        when(cb.construct(String.class, path)).thenReturn(cs);
//
//        q.select(cs);
//
//        when(cb.parameter(Collectingevent.class)).thenReturn(collectingEvent);
//        when(cb.parameter(String.class)).thenReturn(code);
//        when(cb.parameter(Boolean.class)).thenReturn(isCurrent);
//
//        q.where(cb.equal(path, collectingEvent),
//                cb.equal(path, code),
//                cb.equal(path, isCurrent));
//
//        when(entityManager.createQuery(q)).thenReturn(stringquery);
//        stringquery.setParameter(collectingEvent, event);
//        stringquery.setParameter(code, collectionCode);
//        stringquery.setParameter(isCurrent, true);
//
//
//        when(stringquery.getResultList()).thenReturn(stringList);
//
//        DataWrapper result = testInstance.getDeterminationsByCollectingEvent(event, collectionCode);
//        assertNotNull(result);
//        assertEquals(3, result.getList().size());
//    }
 

//    /**
//     * Test of testGetDeterminationByLocalityID method, of class SpecifyDaoImpl.
//     */
//    @Test
//    public void testGetDeterminationByLocalityID() {
//
//        logger.info("testGetDeterminationByLocalityID");
//
//        Locality locality = new Locality(5);
//        String collectionCode = "test";
// 
//        when(entityManager.getCriteriaBuilder()).thenReturn(cb);
//        when(cb.createQuery(String.class)).thenReturn(q);
//        when(q.from(Determination.class)).thenReturn(d);
//        when(d.get(anyString())).thenReturn(path);
//        when(path.get(anyString())).thenReturn(path); 
//        when(cb.construct(String.class, path)).thenReturn(cs);
//         
//        q.select(cs);
//
//        when(cb.parameter(Locality.class)).thenReturn(localityId);
//        when(cb.parameter(String.class)).thenReturn(code);
//        when(cb.parameter(Boolean.class)).thenReturn(isCurrent);
//
//        q.where(cb.equal(path, localityId),
//                cb.equal(path, code),
//                cb.equal(path, isCurrent));
//
//        when(entityManager.createQuery(q)).thenReturn(stringquery);
//        stringquery.setParameter(localityId, locality);
//        stringquery.setParameter(code, collectionCode);
//        stringquery.setParameter(isCurrent, true);
//    
//        when(stringquery.getResultList()).thenReturn(stringList);
//        List<String> result = testInstance.getDeterminationByLocalityID(locality, collectionCode);
//
//        assertNotNull(result);
//        assertEquals(3, result.size());
//    }

//    /**
//     * Test of getDeterminationsByTaxon method, of class SpecifyDaoImpl.
//     */
//    @Test
//    public void testGetDeterminationsByTaxon() {
//
//        logger.info("getDeterminationsByTaxon");
//
//        Taxon taxonId = new Taxon(12);
//
//        String collectionCode = "test";
//
//        when(entityManager.find(Taxon.class, 12, LockModeType.OPTIMISTIC)).thenReturn(taxon);
//        when(taxon.getHighestChildNodeNumber()).thenReturn(18);
//        when(taxon.getNodeNumber()).thenReturn(8);
//
//        StringBuilder queryBuilder = new StringBuilder();
//        queryBuilder.append("SELECT d.collectionObjectID.collectingEventID.localityID.localityID FROM Determination AS d where d.collectionObjectID.collectionID.code = '");
//        queryBuilder.append(collectionCode);
//        queryBuilder.append("' and d.taxonID.nodeNumber BETWEEN ");
//        queryBuilder.append(8);
//        queryBuilder.append(" AND ");
//        queryBuilder.append(18);
//        queryBuilder.append(" and d.isCurrent = true group by d.collectionObjectID.collectingEventID.collectingEventID");
// 
//        when(entityManager.createQuery(queryBuilder.toString(), Integer.class)).thenReturn(intquery);
//        when(intquery.getResultList()).thenReturn(intList);
//        
//        queryBuilder = new StringBuilder();
//        queryBuilder.append("SELECT d.taxonID.fullName FROM Determination AS d where d.collectionObjectID.collectionID.code = '");
//        queryBuilder.append(collectionCode);
//        queryBuilder.append("' and d.taxonID.nodeNumber BETWEEN ");
//        queryBuilder.append(8);
//        queryBuilder.append(" AND ");
//        queryBuilder.append(18);
//        queryBuilder.append(" and d.isCurrent = true");
//        
//
//        when(entityManager.createQuery(queryBuilder.toString(), String.class)).thenReturn(stringquery);
//        when(stringquery.getResultList()).thenReturn(stringList);
//
//        DataWrapper result = testInstance.getDeterminationsByTaxon(taxonId, collectionCode);
//        assertNotNull(result);
//        assertEquals(3, result.getList().size());
//        assertEquals("7", result.getEventcount());
//        assertEquals("4", result.getTrapcount());
//    }

//    /**
//     * Test of getGeographyListByGeographytreedefitemId method, of class SpecifyDaoImpl.
//     */
//    @Test
//    public void testGetGeographyListByGeographytreedefitemId() {
//
//        logger.info("testGetGeographyListByGeographytreedefitemId");
//
//        Geographytreedefitem g = new Geographytreedefitem(2);
//
//        when(entityManager.createNamedQuery("Geography.findByGeographytreedefitemId")).thenReturn(query);
//        query.setParameter("geographyTreeDefItemID", g);
//        when(query.getResultList()).thenReturn(geographyList);
//
//        List<Geography> expResult = geographyList;
//        List<Geography> result = testInstance.getGeographyListByGeographytreedefitemId(g);
//
//        assertNotNull(result);
//        assertEquals(expResult, result);
//        assertEquals(5, result.size());
//    } 
}

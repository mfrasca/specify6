package se.nrm.specify.specify.data.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Geography;
import se.nrm.specify.datamodel.Geographytreedefitem;
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.Recordsetitem;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.Sppermission;
import se.nrm.specify.datamodel.Taxon;
import se.nrm.specify.datamodel.Workbenchrow;

/**
 *
 * @author idali
 */
@Stateless
public class SpecifyDaoImpl implements SpecifyDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PersistenceContext(unitName = "jpa-local")         //  persistence unit connect to local database
    private EntityManager entityManager;

    public SpecifyDaoImpl() {
    }

    public SpecifyDaoImpl(EntityManager em) {
        this.entityManager = em;
    }

    /**
     * Generic method returning an entity by id
     *
     * @param <T>   Entity
     * @param id:   the id of the entity 
     * @param clazz:  the class of the entity 
     * @return the entity
     */
    public <T extends SpecifyBean> T getById(int id, Class<T> clazz) {

        logger.info("getById - Class: {} - id: {}", clazz.toString(), id);

        // Entity has no version can not have Optimistic lock
        if (clazz.getSimpleName().equals(Recordsetitem.class.getSimpleName())
                || clazz.getSimpleName().equals(Sppermission.class.getSimpleName())
                || clazz.getSimpleName().equals(Workbenchrow.class.getSimpleName())) {

            return entityManager.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
        }

        T bean = null;
        try {
            bean = entityManager.find(clazz, id, LockModeType.OPTIMISTIC);
            entityManager.flush();
        } catch (OptimisticLockException ex) {
            logger.error(ex.getMessage());
            entityManager.refresh(bean);
        }

        logger.info("bean: {}", bean);
        return bean;
    }

    /**
     * Generic method returning a list of entity with named query - ClassName.findAll
     * 
     * @param <T>       Entity
     * @param clazz:    the class of the entity
     * @return :        List<Entity>
     */
    public <T extends SpecifyBean> List getAll(Class<T> clazz) {

        logger.info("getAll - Clazz: {}", clazz);

        Query query = entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll");
        return query.getResultList();
    }

    /**
     * generic method for creating entities
     *
     * @param sBean , the entity to be created
     */
    public void createEntity(SpecifyBean sBean) {

        logger.info("Persisting: {}", sBean);

        try {
            entityManager.persist(sBean);

            logger.info("{} persisted", sBean);
        } catch (ConstraintViolationException e) {
            handleConstraintViolation(e);
        }
    }

    /**
     * generic method for deleted entity
     *
     * @param sBean ,the entity to be deleted
     */
    public void deleteEntity(SpecifyBean sBean) {

        logger.info("deleteEntity {}", sBean);

        try {
            entityManager.remove(sBean);
        } catch (ConstraintViolationException e) {
            handleConstraintViolation(e);
        }
    }

    /**
     * method for editing optional entity
     *
     * @param sBean, the entity to be edited
     */
    public String editEntity(SpecifyBean sBean) {

        logger.info("editEntity: {}", sBean.toString());

        try {
            //    entityManager.flush();                      // for throwing OptimisticLockException before merge
            entityManager.merge(sBean);

            entityManager.flush();                      // this one used for throwing OptimisticLockException if method called with web service
        } catch (OptimisticLockException e) {
            logger.error("OptimisticLockException - error messages: {}", e.getMessage());
            return sBean.toString() + "cannot be updated because it has changed or been deleted since it was last read. ";
        } catch (ConstraintViolationException e) {
            handleConstraintViolation(e);
        }
        return "Successful";
    }

    /**
     * method retrieves Collectingevent by given stationFieldNumber
     * 
     * @param stationFieldNumber
     * 
     * @return Collectingevent
     */
    public Collectingevent getCollectingEventByStationFieldNumber(String stationFieldNumber) {

        logger.info("getCollectingEventByStationFieldNumber - stationFieldNumber: {}", stationFieldNumber);

        Query query = entityManager.createNamedQuery("Collectingevent.findByStationFieldNumber");
        query.setParameter("stationFieldNumber", stationFieldNumber);

        try {
            return (Collectingevent) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * get all the taxon fullname from database
     * 
     * @return - List<String>
     */
    public List<String> getAllTaxonName() {

        logger.info("getAllTaxonName");

        TypedQuery<String> query = entityManager.createQuery("SELECT t.fullName FROM Taxon AS t", String.class); 
        return query.getResultList();
    }

    /**
     * get taxon by taxon fullname
     * 
     * @param taxonName
     * @return Taxon
     */
    public Taxon getTaxonByTaxonName(String taxonName) {

        logger.info("getTaxonByTaxonName - taxon name: {}", taxonName);

        Query query = entityManager.createNamedQuery("Taxon.findByFullName");
        query.setParameter("fullName", taxonName);

        try {
            return (Taxon) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            logger.info(ex.getMessage());
            return null;                // if no result, return null
        } catch (javax.persistence.NonUniqueResultException ex) {
            logger.info(ex.getMessage());
            return new Taxon();         // if result not unique, return new taxon
        }
    }

    /**
     * get current determinations by taxonname and collectingevent
     * 
     * @param taxonName
     * @param collectionevent
     * @return List<Determination>
     */
    public List<Determination> getDeterminationsByTaxonNameAndCollectingevent(String taxonName, Collectingevent event, String code) {

        logger.info("getDeterminationsByTaxonNameAndCollection - taxon name: {}, code: {}", taxonName, code);

        Query query = entityManager.createNamedQuery("Determination.findCurrentByTaxonNameAndEvent");
        query.setParameter("fullName", taxonName);
        query.setParameter("collectingEventID", event);
        query.setParameter("code", code);
        query.setParameter("isCurrent", true);


        return query.getResultList();
    }

    /**
     * get last collectionobject for collection
     * 
     * @param collectionId
     * @return Collectionobject
     */
    public Collectionobject getLastCollectionobjectByGroup(String collectionCode) {

        logger.info("getLastCatalogNumber");

        Query query = entityManager.createNamedQuery("Collectionobject.findLastRecordByCollectionCode");
        query.setParameter("code", collectionCode);
        query.setMaxResults(1);

        return (Collectionobject) query.getResultList().get(0);
    }

    public List<Collectionobject> getCollectionobjectByCollectingEventAndYesno2(Collectingevent event) {

        logger.info("getCollectionobjectByCollectingEventAndYesno2");

        Query query = entityManager.createNamedQuery("Collectionobject.findByCollectingEventIDAndYesNo2");
        query.setParameter("collectingEventID", event);

        return query.getResultList();
    }

    /**
     * get current deterimations by collectingevent
     * 
     * @param collectionevent
     * @return List<Determination>
     */
    public DataWrapper getDeterminationsByCollectingEvent(Collectingevent event, String collectionCode) {

        logger.info("getDeterminationsByCollectingEvent - event: {}, collectionCode: {}", event, collectionCode);

        TypedQuery<String> query = entityManager.createQuery("SELECT d.taxonID.fullName FROM Determination AS d where d.collectionObjectID.collectingEventID.collectingEventID = "
                + event.getCollectingEventID() + " and d.collectionObjectID.collectionID.code = '" + collectionCode
                + "' and d.isCurrent = true", String.class);

        return new DataWrapper(query.getResultList());
    }

    public List<Collectingevent> getCollectingeventsByLocality(String localityName) {

        logger.info("getCollectingeventsByLocality");

        Query query = entityManager.createNamedQuery("Collectingevent.findByLocality");

        query.setParameter("localityName", localityName + "%");
        return query.getResultList();
    }

    public List<String> getDeterminationByLocalityID(Locality locality, String collectionCode) {

        logger.info("getDeterminationByLocalityID: {}, {}", locality, collectionCode);

        List<String> determinations = new ArrayList<String>();

        TypedQuery<String> typedQuery = entityManager.createQuery("SELECT d.taxonID.fullName FROM Determination AS d where d.collectionObjectID.collectingEventID.localityID.localityID = "
                + locality.getLocalityID() + " and d.collectionObjectID.collectionID.code = '" + collectionCode
                + "' and d.isCurrent = true", String.class);
        determinations = typedQuery.getResultList();

        return determinations;
    }

    public DataWrapper getDeterminationsByTaxon(Taxon taxonId, String collectionCode) {

        logger.info("getDeterminationsByTaxonId - taxon: {}, collection: {}", taxonId, collectionCode);

//        List<Integer> taxonIdList = new ArrayList<Integer>();
        Taxon taxon = getById(taxonId.getTaxonID(), Taxon.class);
//        taxonIdList.add(taxon.getTaxonID());
        int highestChildNode = taxon.getHighestChildNodeNumber();
        int node = taxon.getNodeNumber();
 
        TypedQuery<Integer> typedQuery = entityManager.createQuery("SELECT d.collectionObjectID.collectingEventID.localityID.localityID FROM Determination AS d where d.collectionObjectID.collectionID.code = '"
                + collectionCode + "' and d.taxonID.nodeNumber BETWEEN " + node + " AND " + highestChildNode
                + " and d.isCurrent = true group by d.collectionObjectID.collectingEventID.collectingEventID", Integer.class);
        List<Integer> localityIds = typedQuery.getResultList();
        
        TypedQuery<String> query = entityManager.createQuery("SELECT d.taxonID.fullName FROM Determination AS d where d.collectionObjectID.collectionID.code = '"
                + collectionCode + "' and d.taxonID.nodeNumber BETWEEN " + node + " AND " + highestChildNode
                + "and d.isCurrent = true", String.class);
 

        Set<Integer> s = new HashSet<Integer>();
        for (Integer localityId : localityIds) {
            s.add(localityId);
        }
        return new DataWrapper(query.getResultList(), String.valueOf(localityIds.size()), String.valueOf(s.size()));
    }

    /**
     * Method retrieves list of Geography entity by a given GeographytreedefitemID from database 
     * 
     * @param g - Geographytreedefitem 
     * @return List<Geography>
     */
    public List<Geography> getGeographyListByGeographytreedefitemId(Geographytreedefitem g) {

        logger.info("getGeographyListByGeographytreedefitemId: {}", g.toString());

        Query query = entityManager.createNamedQuery("Geography.findByGeographytreedefitemId");
        query.setParameter("geographyTreeDefItemID", g);
        return (List<Geography>) query.getResultList();
    }

    /**
     * Method retrieves all dna sequences from database
     * 
     * @return List<Dnasequence>
     */
    public List<Dnasequence> getAllDnasequences() {

        logger.info("getAllDnasequences");

        Query query = entityManager.createNamedQuery("Dnasequence.findAll");
        return (List<Dnasequence>) query.getResultList();
    }

    /**
     * Method handles ConstraintViolationException.  
     * It logs exception messages, entity properties with invalid values.
     * 
     * @param e 
     */
    private void handleConstraintViolation(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        for (ConstraintViolation<?> cv : cvs) {
            logger.info("------------------------------------------------");
            logger.info("Violation: {}", cv.getMessage());
            logger.info("Entity: {}", cv.getRootBeanClass().getSimpleName());
            // The violation occurred on a leaf bean (embeddable)
            if (cv.getLeafBean() != null && cv.getRootBean() != cv.getLeafBean()) {
                logger.info("Embeddable: {}", cv.getLeafBean().getClass().getSimpleName());
            }
            logger.info("Attribute: {}", cv.getPropertyPath());
            logger.info("Invalid value: {}", cv.getInvalidValue());
        }
    }
}

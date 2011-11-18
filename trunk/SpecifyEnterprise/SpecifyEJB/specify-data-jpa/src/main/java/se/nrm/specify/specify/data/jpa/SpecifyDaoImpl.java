package se.nrm.specify.specify.data.jpa;
 
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Collectingevent; 
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Geography;
import se.nrm.specify.datamodel.Geographytreedefitem;
import se.nrm.specify.datamodel.Recordsetitem;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.Sppermission;
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
        if(clazz.getSimpleName().equals(Recordsetitem.class.getSimpleName()) ||                                         
                                        clazz.getSimpleName().equals(Sppermission.class.getSimpleName()) || 
                                        clazz.getSimpleName().equals(Workbenchrow.class.getSimpleName())) {  
            
            return entityManager.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
        } 
        
        return entityManager.find(clazz, id, LockModeType.OPTIMISTIC);
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
        
        logger.info("getCollectingEventByEventId");
        
        Query query = entityManager.createNamedQuery("Collectingevent.findByStationFieldNumber");
        query.setParameter("stationFieldNumber", stationFieldNumber);
        
        return (Collectingevent)query.getSingleResult();
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

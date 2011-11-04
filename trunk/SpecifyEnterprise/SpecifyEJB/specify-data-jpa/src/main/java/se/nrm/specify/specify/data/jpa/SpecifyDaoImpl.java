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
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Geography;
import se.nrm.specify.datamodel.Geographytreedefitem;
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
@Stateless
public class SpecifyDaoImpl implements SpecifyDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PersistenceContext(unitName = "jpa-local")
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
        return entityManager.find(clazz, id, LockModeType.OPTIMISTIC);
    }

    /**
     * generic method for creating entities
     *
     * @param sBean , the entity to be created
     */
    public void createEntity(SpecifyBean sBean) {
        
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
        
        entityManager.remove(sBean);
    }

    /**
     * method for editing optional entity
     *
     * @param sBean, the entity to be edited
     */
    public String editEntity(SpecifyBean sBean) {

        logger.info("editEntity: {}", sBean.toString());
 
        try {
            entityManager.flush();                      // for throw OptimisticLockException
            entityManager.merge(sBean);
        } catch (OptimisticLockException e) {
            logger.error("OptimisticLockException - error messages: {}", e.getMessage());
            return sBean.toString() + "cannot be updated because it has changed or been deleted since it was last read. ";
        } catch (ConstraintViolationException e) {
            handleConstraintViolation(e);
        }
        return "Successful";
    }

    /**
     * Method retrieves list of Geography entity by a given GeographytreedefitemID from database 
     * 
     * @param g - Geographytreedefitem 
     * @return List<Geography>
     */
    @SuppressWarnings("unchecked")
	public List<Geography> getGeographyListByGeographytreedefitemId(Geographytreedefitem g) {

        logger.info("getGeographyListByGeographytreedefitemId: {}", g.toString());

        Query query = entityManager.createNamedQuery("Geography.findByGeographytreedefitemId");
        query.setParameter("geographyTreeDefItemID", g);
        return query.getResultList();
    }

    /**
     * Method retrieves all dna sequences from database
     * 
     * @return List<Dnasequence>
     */
    @SuppressWarnings("unchecked")
	public List<Dnasequence> getAllDnasequences() {
        
        logger.info("getAllDnasequences");
        
        Query query = entityManager.createNamedQuery("Dnasequence.findAll");
        return query.getResultList(); 
    } 
    
    /**
     * 
     * handleConstrainViolation method logs all the ConstraintViolations
     * 
     * @param e - exception
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

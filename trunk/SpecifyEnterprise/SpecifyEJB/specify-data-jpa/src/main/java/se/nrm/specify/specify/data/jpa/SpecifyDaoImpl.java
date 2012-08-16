package se.nrm.specify.specify.data.jpa;
 
import java.sql.Timestamp;
import java.util.*; 
import javax.ejb.Stateless;  
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;  
import org.eclipse.persistence.jpa.JpaEntityManager; 
import org.eclipse.persistence.sessions.CopyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.*;
import se.nrm.specify.specify.data.jpa.exceptions.DataStoreException;
import se.nrm.specify.specify.data.jpa.exceptions.ExceptionUtil;
import se.nrm.specify.specify.data.jpa.util.JPAUtil;

/**
 *
 * @author idali
 */
@Stateless
public class SpecifyDaoImpl<T extends SpecifyBean> implements SpecifyDao<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String OPTIMISTIC_LOCK_EXCEPTION_MSG = " cannot be updated because it has changed or bean deleted since it was last read.";
    
    private static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    
    @Inject
    private EntityMapping entitymapping;
     
    @PersistenceContext(unitName = "jpa-test")                  //  persistence unit connect to test database
    private EntityManager entityManager;

    public SpecifyDaoImpl() {
    }

    /**
     * Pass the EntityManager for this instance.  This is only here for 
     * testing purposes, and should not be used in production.  In production,
     * an entityManager will be injected via the PersistenceContext annotation.
     * 
     * @return The entity manager in use
     */
    public SpecifyDaoImpl(EntityManager em) {
        this.entityManager = em;
    }

    /**
     * Generic method create an entities
     *
     * @param sBean , the entity to newBaseEntity created 
     */ 
    public T create(T entity) {

        logger.trace("save(T) : {}", entity);

        T tmp = entity;
        try {
            entityManager.persist(entity);
            entityManager.flush();
            entityManager.refresh(tmp);
        } catch (ConstraintViolationException e) {
            throw new DataStoreException(handleConstraintViolation(e));
        } catch (Exception e) {
            throw new DataStoreException(ExceptionUtil.unwindException(e).getMessage());
        }   
        return tmp;
    }
 
 
    
    /**
     * Generic method merge an entity
     * 
     * @param entity, the entity to newBaseEntity merged
     *  
     */
    public T merge(T entity) {
        
        logger.info("merge: {}", entity);

        T tmp = entity;
        try { 
            tmp = entityManager.merge(entity); 
            entityManager.flush();                              // this one used for throwing OptimisticLockException if method called with web service
        } catch (OptimisticLockException e) {
            throw new DataStoreException(entity.toString() + OPTIMISTIC_LOCK_EXCEPTION_MSG);
        } catch (ConstraintViolationException e) {
            throw new DataStoreException(handleConstraintViolation(e));
        } catch (Exception e) { 
            throw new DataStoreException(ExceptionUtil.unwindException(e).getMessage());
        }  
        return tmp;
    }
     
    
    
    public T findById(int id, Class<T> clazz) {

        logger.info("findById - class : {} - id : {}", clazz, id);
 
         // Entity has no version can not have Optimistic lock
        if (clazz.getSimpleName().equals(Recordsetitem.class.getSimpleName())
                || clazz.getSimpleName().equals(Sppermission.class.getSimpleName())
                || clazz.getSimpleName().equals(Workbenchrow.class.getSimpleName())) {

            return entityManager.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
        }

        T tmp = null;
        try {
            tmp = entityManager.find(clazz, id, LockModeType.OPTIMISTIC);
            entityManager.flush();
        } catch (OptimisticLockException ex) { 
            entityManager.refresh(tmp);
        } catch(Exception e) {
            throw new DataStoreException(ExceptionUtil.unwindException(e).getMessage());
        } 
        return tmp; 
    }
    
    
    public T findByReference(int id, Class<T> clazz) {
        return entityManager.getReference(clazz, id);
    }
     

    /**
     * Generic method delete an entity
     *
     * @param sBean, the entity to newBaseEntity deleted 
     */
    public void delete(T entity) throws DataStoreException {

        logger.info("delete - {}", entity);

        try {
            entityManager.remove(entity);
            entityManager.flush();                              // this is needed for throwing internal exception
        } catch (ConstraintViolationException e) {
            throw new DataStoreException(handleConstraintViolation(e), e);
        } catch (Exception e) {
            throw new DataStoreException(ExceptionUtil.unwindException(e).getMessage());
        }
    }
    
    /**
     * 
     * @param bean
     * @param namedQry
     * @return 
     * 
     * TODO: current
     */
    public int getCountByJPQL(SpecifyBean bean, String jpql) {

        logger.info("getCountByJPQL: {} - {}", bean, jpql);

        Query query = entityManager.createQuery(jpql);
        try {
            if (bean != null) {
                query.setParameter(bean.getClass().getSimpleName().toLowerCase(), bean);
            }
            Number number = (Number) query.getSingleResult();
            return number.intValue();
        } catch (Exception e) {
            throw new DataStoreException(e);
        }
    }
    
 
 

    /**
     * getListByJPQLByFetchGroup - only fetches required fields as fetch group.
     * 
     * @param <T> 
     * @param classname - entity name
     * @param jpql  
     * @param fields -fields required.
     * 
     * @return SpecifyBean
     */ 
    public List<T> getListByJPQLByFetchGroup(String classname, String jpql, List<String> fields) {

        logger.info("getListByJPQLByFetchGroup - classname: {} - jpql: {}", classname, jpql);

        Query query = entityManager.createQuery(jpql);
//        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        if (fields == null) {
            fields = new ArrayList<String>();
        }
        List<String> removelist = JPAUtil.addFetchGroup(fields, query, classname);

        List<T> beans = (List<T>) query.getResultList();
        
        logger.info("getListByJPQLByFetchGroup : fetched. {}", beans);
  
        if (removelist != null) {
            fields.removeAll(removelist);
        }

        
//        return copyEntity(beans, fields); 
        return copyEntityGroup(beans, fields, classname); 
    }
 
    public T getFetchGroupByNamedQuery(String classname, String namedQuery, Map<String, Object> conditions, List<String> fields) {
        
        logger.info("getFetchGroupByNamedQuery - classname: {} - jpql: {}", classname, namedQuery);
     

        Query query = entityManager.createNamedQuery(namedQuery);
//        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        for (Map.Entry<String, Object> map : conditions.entrySet()) {
            query.setParameter(map.getKey(), map.getValue());
        }
        
        

        if (fields == null) {
            fields = new ArrayList<String>();
        }
        List<String> removelist = JPAUtil.addFetchGroup(fields, query, classname);
        if (removelist != null) {
            fields.removeAll(removelist);
        }
        
 
 
        logger.info("fields : {} - removelist: {}", fields, removelist);
        
        
        SpecifyBean bean = (SpecifyBean) query.getSingleResult();
          
        logger.info("getFetchGroupByNamedQuery : fetched. {}", bean);
        
//        return copyEntity(bean, fields); 
        
        return copyEntity(bean, fields, classname);
    }
 
     private List copyEntityGroup(List<T> beans, List<String> fields, String classname) {

        List newBeans = new ArrayList();

        for (T bean : beans) {
            newBeans.add(copyEntity(bean, fields, classname));
        }
        return newBeans;
    }

    private T copyEntity(SpecifyBean bean, List<String> fields, String classname) {

//        logger.info("copyEntity: {} - {}", bean, classname);

        Map<String, SpecifyBean> beanmap = new HashMap<String, SpecifyBean>();
        beanmap.put(classname, bean);
        T obj = JPAUtil.createNewInstance(classname);
        entitymapping.setEntityValue(obj, classname, fields, beanmap);

        return obj;
    }

 
    /**
     * Generic method retrieves all the entity with named query - ClassName.findAll
     * 
     * @param <T>       Entity
     * @param clazz:    the class of the entity
     * @return :        List<Entity>
     * 
     * TODO: current
     */
    public List<T> findAll(Class<T> clazz) {

        logger.info("getAll - Clazz: {}", clazz);

        Query query = entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll");
        return query.getResultList();
    }

    /**
     * Generic method gets an entity by named query with parameters
    
     * @param parameters
     * 
     * @return Entity
     */
    public T getEntityByNamedQuery(String namedQuery, Map<String, Object> parameters) {

        logger.info("getEntityByNamedQuery - namedquery: {}, parameters: {}", namedQuery, parameters);

        Query query = createQuery(namedQuery, parameters);
        try {
            return (T) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            logger.error(ex.getMessage());
            return null;                        // if no result, return null
        } catch (javax.persistence.NonUniqueResultException ex) {
            logger.error(ex.getMessage());
            return null;                        // if result not unique, return null
        }
    }

    public T getEntityByJPQL(String jpql) {

        logger.info("getEntityByJPQL - jpql: {}", jpql);

        Query query = entityManager.createQuery(jpql);
        try {
            return (T)query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            logger.error(ex.getMessage());
            return null;                        // if no result, return null
        } catch (javax.persistence.NonUniqueResultException ex) {
            logger.error(ex.getMessage());
            return null;                        // if result not unique, return null
        }
    }

    /**
     * Generic method gets all the entities by named query with parameters
     * @param <T>
     * @param parameters
     * @return 
     */
    public List getAllEntitiesByNamedQuery(String namedQuery, Map<String, Object> parameters) {

        logger.info("getAllEntitiesByNamedQuery - parameters: {}", parameters);

        List<T> list = createQuery(namedQuery, parameters).getResultList();
        
        logger.info("get...: {}", list);
        return list;
    }

    public  List<T> getAllEntitiesByJPQL(String jpql) {

        logger.info("getAllEntitiesByJPQL - jpql: {}", jpql);

        Query query = entityManager.createQuery(jpql);
        List<T> list = query.getResultList();
        return list;
    }
 
     
    
    
//    private List<T> copyEntity(List<T> beans, List<String> copyFields) {
//
//        logger.info("copy fleld : {}", copyFields);
//        List<T> copyBeans = new ArrayList<T>();
//        for (SpecifyBean bean : beans) {
//            copyBeans.add((T) copyEntity(bean, copyFields));
//        } 
//        logger.info("copyEntity : fetched. {}", copyBeans);
//        return copyBeans;
//    }
//
//    private T copyEntity(SpecifyBean entity, List<String> copyFields) {
//        
//        logger.info("copy fleld : {}", copyFields);
//        CopyGroup cg = new CopyGroup();
//        cg.setDepth(3);
//        cg.cascadePrivateParts();
//         
//
//        for (String string : copyFields) {
//            cg.addAttribute(string);
//        }
//        JpaEntityManager nativeEM = entityManager.unwrap(JpaEntityManager.class);
//
//        return (T) nativeEM.copy(entity, cg);
//    }

    private Query createQuery(String namedQuery, Map<String, Object> parameters) {

        Set<String> keys = parameters.keySet();

        Query query = entityManager.createNamedQuery(namedQuery);
        for (String key : keys) {
            query.setParameter(key, parameters.get(key));
        }
        return query;
    }

    /**
     * Method handles ConstraintViolationException.  
     * It logs exception messages, entity properties with invalid values.
     * 
     * @param e 
     */
    private String handleConstraintViolation(ConstraintViolationException e) {

        StringBuilder sb = new StringBuilder();

        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        for (ConstraintViolation<?> cv : cvs) {
            logger.info("------------------------------------------------");
            logger.info("Violation: {}", cv.getMessage());
            sb.append("Violation:");
            sb.append(cv.getMessage());

            logger.info("Entity: {}", cv.getRootBeanClass().getSimpleName());
            sb.append(" - Entity: ");
            sb.append(cv.getRootBeanClass().getSimpleName());
            // The violation occurred on a leaf bean (embeddable)
            if (cv.getLeafBean() != null && cv.getRootBean() != cv.getLeafBean()) {
                logger.info("Embeddable: {}", cv.getLeafBean().getClass().getSimpleName());
                sb.append(" - Embeddable: ");
                sb.append(cv.getLeafBean().getClass().getSimpleName());
            }
            logger.info("Attribute: {}", cv.getPropertyPath());
            sb.append(" - Attribute: ");
            sb.append(cv.getPropertyPath());

            logger.info("Invalid value: {}", cv.getInvalidValue());
            sb.append(" - Invalid value: ");
            sb.append(cv.getInvalidValue());
        }
        return sb.toString();
    } 
}

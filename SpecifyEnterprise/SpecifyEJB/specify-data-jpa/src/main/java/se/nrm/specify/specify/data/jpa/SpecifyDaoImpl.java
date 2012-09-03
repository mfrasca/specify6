package se.nrm.specify.specify.data.jpa;
        
import java.util.*;    
import javax.ejb.Stateless;   
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;    
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.FetchGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.*;
import se.nrm.specify.specify.data.jpa.exceptions.DataReflectionException;
import se.nrm.specify.specify.data.jpa.exceptions.DataStoreException;
import se.nrm.specify.specify.data.jpa.exceptions.ExceptionUtil;
import se.nrm.specify.specify.data.jpa.util.Common;
import se.nrm.specify.specify.data.jpa.util.JPAUtil;  
import se.nrm.specify.specify.data.jpa.util.ReflectionUtil;

/**
 *
 * @author idali
 */
@Stateless
public class SpecifyDaoImpl<T extends SpecifyBean> implements SpecifyDao<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String OPTIMISTIC_LOCK_EXCEPTION_MSG = " cannot be updated because it has changed or bean deleted since it was last read.";
      
    
//    @Inject
//    private EntityMapping entitymapping;
    
    @Inject
    private PartialCopy partialCopy;
     
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
//            entityManager.refresh(tmp);
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
     
   /**
     * Partially update entity
     * @param entity
     * @param fields
     * @return 
     */ 
    public T partialMerge(T entity, List<String> fields) {

        logger.info("partialMerge: entity : {} - fields : {}", entity, fields);
 
        T orgEntity = reWriteEntity(entity, fields);  
        logger.info("part : orgEntity : {}", orgEntity);
         
        if(!partialCopy.isIsRelatedEntityChanged() && !partialCopy.isIsCollectionChanged()) {
            logger.info("first merge");
             return merge(orgEntity);  
        }   
        
        T mergedEntity = null;
        try {
              
            mergedEntity = solveSubEntities(orgEntity);
            // TODO: check if orgEntity already merged
//            mergedEntity = merge(orgEntity);  
        } catch (DataStoreException e) {
            throw new DataStoreException(e);
        }
          
        return mergedEntity;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private T solveSubEntities(T topBean) {
        
        logger.info("solveSubEntities : {}", partialCopy.getMergeDataList()); 
           
        SpecifyBean parent = null;
        boolean isOrgEntityMerged = false;
        T temp = null;
        List<MergeData> dataList = partialCopy.getMergeDataList();
        for (MergeData data : dataList) {  
            
            parent = data.getParent();
            if(parent.getClass().isInstance(topBean)) {
                parent = topBean;
            }   
            if(data.isIsList()) { 
                
                List<SpecifyBean> children = data.getChildren();
                for(SpecifyBean child : children) {
                    // TODO : if target list is new
                } 
                ReflectionUtil.setChildrenValue(parent, data.getChildren());  
            } else {
                if(data.isTargetEmpty() && !data.isNew()) {  
                    T orgSubEntity = copyToOrgChild(data.getChild(), data.getFetchFields()); 
                    ReflectionUtil.setEntityValue(parent, orgSubEntity, data.getFieldName());  
                } else {
                    ReflectionUtil.setEntityValue(parent, data.getChild(), data.getFieldName()); 
                } 
            }
        }

        if (parent.getClass().isInstance(topBean)) {
            temp = merge((T) parent);
            isOrgEntityMerged = true;
        } 
        if(!isOrgEntityMerged) {
            temp = merge(topBean);
        }
        return temp;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private T copyToOrgChild(SpecifyBean child, List<String> fields) {
        
        logger.info("doNewMerge :  {}", child, fields);
        
        int id = Common.getInstance().stringToInt(child.getIdentityString());
        if(id != 0) {
            T orgEntity = getOrginalEntityById(id, child.getClass().getSimpleName(), fields);
            if(orgEntity != null) {
                entityManager.detach(orgEntity);
                partialCopy.copyPartialEntityForMerge(child, orgEntity, fields, entityManager);
            }
            return orgEntity;
        }
        
        
        
        
//        ReflectionUtil.setEntityValue(parent, orgEntity);  
        return null;
    }
    
    private T reWriteEntity(T copyBean, List<String> fields) { 
        logger.info("reWriteEntity: {} -- {}", copyBean, fields);
            
        int id = Common.getInstance().stringToInt(copyBean.getIdentityString());
        if(id != 0) {
            T orgEntity = getOrginalEntityById(id, copyBean.getClass().getSimpleName(), fields); 
            if(orgEntity != null) {
                entityManager.detach(orgEntity);    
                partialCopy.copyPartialEntityForMerge(copyBean, orgEntity, fields, entityManager);
            } 
            return (T) orgEntity;
        }
        return null;
    }
    
 
   
    
    private T getOrginalEntityById(int id, String entityName, List<String> fields) {
        
        logger.info("getOrginalEntity -  {} -- {}", id + " --- " + entityName, fields);
          
        fields = JPAUtil.addValidFields(fields, entityName); 
         
        SpecifyBean instance = JPAUtil.createNewInstance(entityName);
        String idFieldName = ReflectionUtil.getIDFieldName(instance);   
        String namedQuery = entityName + ".findBy" + Common.getInstance().uppercaseFirstCharacter(idFieldName);
        Query query = entityManager.createNamedQuery(namedQuery);
           
        query.setParameter(idFieldName, id);
         
        FetchGroup group = new FetchGroup(); 
        for (String field : fields) {
            group.addAttribute(field.trim());
        }
        query.setHint(QueryHints.FETCH_GROUP, group);
         
        try { 
            return (T) query.getSingleResult();   
        } catch(NoResultException ex) {
            return null;
        }   
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

//        logger.info("getCountByJPQL: {} - {}", bean, jpql);

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
 
        fields = JPAUtil.addValidFields(fields, classname);
        FetchGroup group = new FetchGroup(); 
        for (String field : fields) {
            group.addAttribute(field);
        }
        query.setHint(QueryHints.FETCH_GROUP, group);

        List<T> beans = (List<T>) query.getResultList();
  
        return copyEntityGroup(beans, fields, classname); 
    }
   
        
    public T getFetchGroupByNamedQuery(Map<String, Object> map) {
        
        logger.info("getFetchGroupByNamedQuery -  {}", map);
       
        String namedQueery = (String)map.get("namedQuery");
        Query query = entityManager.createNamedQuery(namedQueery); 
        String classname = StringUtils.substringBefore(namedQueery, ".");
         
        
        List<String> fields = (List)map.get("fields");
        map.remove("namedQuery");
        map.remove("fields");
        
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
         
        try {
            fields = JPAUtil.addValidFields(fields, classname);
        } catch(DataReflectionException e) {
            throw new DataReflectionException(e);
        }  
        
        FetchGroup group = new FetchGroup(); 
        for (String field : fields) {
            group.addAttribute(field.trim());
        }
        query.setHint(QueryHints.FETCH_GROUP, group);
         
        try {
            T bean = (T) query.getSingleResult();  
            return copyEntity(bean, fields, classname); 
        } catch(NoResultException ex) {
            return null;
        }  
    }
     
    
    /**
     * Copy entity use reflection
     */
     private List copyEntityGroup(List<T> beans, List<String> fields, String classname) {

        List newBeans = new ArrayList();
        
        for (T bean : beans) {
            List<String> newFields = new ArrayList<String>();
            newFields.addAll(fields);
            newBeans.add(copyEntity(bean, newFields, classname));
        }
        return newBeans;
    }

    private T copyEntity(SpecifyBean bean, List<String> fields, String classname) {
         
//        logger.info("copyEntity: {} - {}", bean, fields);
 
        T copyObject = JPAUtil.createNewInstance(classname);

        partialCopy.copyPartialEntity(bean, copyObject, fields, false); 
        return copyObject;  
    }

 
    /**
     * Generic method retrieves all the entity with named query - ClassName.findAll
     * 
     * @param <T>       Entity
     * @param clazz:    the class of the entity
     * @return :        List<Entity>
     * SpecifyDaoImpl
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
         
        return list;
    }

    public  List<T> getAllEntitiesByJPQL(String jpql) {

        logger.info("getAllEntitiesByJPQL - jpql: {}", jpql);

        Query query = entityManager.createQuery(jpql);
        List<T> list = query.getResultList();
        return list;
    }
     
    
    public T getPartialEntity(Object searchValue, String fieldName, String entityName, List<String> fields) {
        
        logger.info("getPartialEntity -  {} -- {}", searchValue + " --- " + fieldName + " -- " + entityName, fields);
          
        fields = JPAUtil.addValidFields(fields, entityName);  
        
        String namedQuery = entityName + ".findBy" + Common.getInstance().uppercaseFirstCharacter(fieldName); 
        Query query = entityManager.createNamedQuery(namedQuery);
           
        query.setParameter(fieldName, searchValue);
         
        FetchGroup group = new FetchGroup(); 
        for (String field : fields) {
            group.addAttribute(field.trim());
        }
        query.setHint(QueryHints.FETCH_GROUP, group);
         
        try { 
            return (T) query.getSingleResult();   
        } catch(NoResultException ex) {
            return null;
        }   
    }
    
    
    public List<T> getPartialEntities(Object searchValue, String fieldName, String entityName, List<String> fields) {
        
        logger.info("getPartialEntities -  {} -- {}", searchValue + " --- " + fieldName + " -- " + entityName, fields);
        
        fields = JPAUtil.addValidFields(fields, entityName);
        String namedQuery = entityName + ".findBy" + Common.getInstance().uppercaseFirstCharacter(fieldName); 
        
        logger.info("namedquery : {}", namedQuery);
        
        Query query = entityManager.createNamedQuery(namedQuery);
        query.setParameter(fieldName, searchValue); 
        FetchGroup group = new FetchGroup(); 
        for (String field : fields) {
            group.addAttribute(field.trim());
        }
        query.setHint(QueryHints.FETCH_GROUP, group);
        
        try { 
            List<T> list = query.getResultList();  
            return copyEntityGroup(list, fields, entityName);
        } catch(NoResultException ex) {
            logger.error(ex.getMessage());
            return null;
        }    
    }
    
    
    
    
    /**
     * get last collectionobject for collection
     * 
     * @param collectionId
     * @return Collectionobject
     */
    public T getLastRecord(String namedQuery, Map<String, Object> map) {

        logger.info("getLastCollectionobjectByGroup: {} -- {}", namedQuery, map);

        Query query = entityManager.createNamedQuery("Collectionobject.findLastRecordByCollectionCode");
        
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        } 
        query.setMaxResults(1);

        T bean = (T) query.getResultList().get(0);
        logger.info("last co : {}", bean);
        return bean;
    }
    
    
    
    /**
     * Generic method search by jpql
     * 
     * @param jpql
     * @return List<String>
     */
    public List<String> getTextListByJPQL(String jpql) {

        logger.info("getTextListByJPQL: {}", jpql);

        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        return query.getResultList();
    }
  

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

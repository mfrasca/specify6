package se.nrm.specify.specify.data.jpa;
  
import java.sql.Timestamp;
import java.util.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.FetchGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.*;
import se.nrm.specify.specify.data.jpa.util.JPAUtil;

/**
 *
 * @author idali
 */
@Stateless
public class SpecifyDaoImpl implements SpecifyDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private EntityMapping entitymapping;
//    @PersistenceContext(unitName = "jpa-local")               //  persistence unit connect to local database 
//    @PersistenceContext(unitName = "jpa-development")         //  persistence unit connect to development database
//    @PersistenceContext(unitName = "jpa-production")          //  persistence unit connect to development database
    @PersistenceContext(unitName = "jpa-test")                  //  persistence unit connect to test database
    private EntityManager entityManager;

    public SpecifyDaoImpl() {
    }

    public SpecifyDaoImpl(EntityManager em) {
        this.entityManager = em;
    }

    /**
     * Generic method create an entities
     *
     * @param sBean , the entity to newBaseEntity created
     */
    public void createEntity(SpecifyBean sBean)  {

        logger.info("Persisting: {}", sBean);

        try {
            entityManager.persist(sBean);
            logger.info("{} persisted", sBean);
        } catch (ConstraintViolationException e) {
            handleConstraintViolation(e);
        }  
    }

    /**
     * Generic method edit an entity
     *
     * @param sBean, the entity to newBaseEntity edited
     */
    public String editEntity(SpecifyBean sBean) {

        logger.info("editEntity: {}", sBean.toString());

        try {
            //    entityManager.flush();                        // for throwing OptimisticLockException before merge
            entityManager.merge(sBean);

            entityManager.flush();                              // this one used for throwing OptimisticLockException if method called with web service
        } catch (OptimisticLockException e) {
            logger.error("OptimisticLockException - error messages: {}", e.getMessage());
            return sBean.toString() + "cannot be updated because it has changed or been deleted since it was last read. ";
        } catch (ConstraintViolationException e) {
            handleConstraintViolation(e);
        }
        return "Successful";
    }

    /**
     * Generic method delete an entity
     *
     * @param sBean, the entity to newBaseEntity deleted
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
     * Generic method retrieves an entity by entity id
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
        return bean;
    }

    public <T extends SpecifyBean> T getByReference(int id, Class<T> clazz) {
        return entityManager.getReference(clazz, id);
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
    public <T extends SpecifyBean> List getListByJPQLByFetchGroup(String classname, String jpql, List<String> fields) {

        logger.info("getListByJPQLByFetchGroup - classname: {} - jpql: {}", classname, jpql);

        Query query = entityManager.createQuery(jpql);

        if (fields == null) {
            fields = new ArrayList<String>();
        }  
        List<String> removelist = JPAUtil.addFetchGroup(fields, query, classname);
          
        List<T> beans = (List<T>) query.getResultList(); 
        
        if (removelist != null) {
            fields.removeAll(removelist);
        }

        
        List<SpecifyBean> list = copyEntityGroup(beans, fields, classname);
        return list;
    }

    public <T extends SpecifyBean> List getAllByFetchGroup(Class<T> clazz, List<String> fields) {

        logger.info("getAllByFetchGroup - Clazz: {}", clazz);

        Query query = entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll");
        JPAUtil.addFetchGroup(fields, query, clazz.getSimpleName());

        List<T> beans = (List<T>) query.getResultList();

        List<SpecifyBean> list = copyEntityGroup(beans, fields, clazz.getSimpleName()); 
        return list;
    }

    private <T extends SpecifyBean> List copyEntityGroup(List<T> beans, List<String> fields, String classname) {

        List newBeans = new ArrayList();

        for (T bean : beans) {
            newBeans.add(copyEntity(bean, fields, classname));
        } 
        return newBeans;
    }

    private SpecifyBean copyEntity(SpecifyBean bean, List<String> fields, String classname) {
        
        logger.info("copyEntity: {} - {}", bean, classname);

        Map<String, SpecifyBean> beanmap = new HashMap<String, SpecifyBean>();
        beanmap.put(classname, bean);
        SpecifyBean obj = JPAUtil.createNewInstance(classname);
        entitymapping.setEntityValue(obj, classname, fields, beanmap); 

        return obj;
    }

    /**
     * Generic method retrieves all the entity with named query - ClassName.findAll
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
     * Generic method gets an entity by named query with parameters
    
     * @param parameters
     * 
     * @return Entity
     */
    public SpecifyBean getEntityByNamedQuery(String namedQuery, Map<String, Object> parameters) {

        logger.info("getEntityByNamedQuery - namedquery: {}, parameters: {}", namedQuery, parameters);

        Query query = createQuery(namedQuery, parameters);
        try {
            return (SpecifyBean) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            logger.error(ex.getMessage());
            return null;                        // if no result, return null
        } catch (javax.persistence.NonUniqueResultException ex) {
            logger.error(ex.getMessage());
            return null;                        // if result not unique, return null
        }
    }

    public SpecifyBean getEntityByJPQL(String jpql) {

        logger.info("getEntityByJPQL - jpql: {}", jpql);

        Query query = entityManager.createQuery(jpql);
        try {
            return (SpecifyBean) query.getSingleResult();
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
    public <T extends SpecifyBean> List getAllEntitiesByNamedQuery(String namedQuery, Map<String, Object> parameters) {

        logger.info("getAllEntitiesByNamedQuery - parameters: {}", parameters);

        return createQuery(namedQuery, parameters).getResultList();
    }

    public <T extends SpecifyBean> List getAllEntitiesByJPQL(String jpql) {

        logger.info("getAllEntitiesByJPQL - jpql: {}", jpql);

        Query query = entityManager.createQuery(jpql);
        List<SpecifyBean> list = query.getResultList();
        return list;
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

    public List<Object[]> getDataListByJPQL(String jpql) {

        logger.info("getDataListByJPQL: {}", jpql);

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        List list = query.getResultList();

        return list;
    }

    public List<String> getSynomyList(Taxon taxon) {

        logger.info("getSynomyList - taxon: {}", taxon);

        List<String> synomyList = new ArrayList<String>();

        Taxon t = getById(taxon.getTaxonId(), Taxon.class);
        for (Taxon tx : t.getAcceptedChildren()) {
            if (tx.getIsAccepted()) {
                synomyList.add(taxon.getFullName());
            }
        }
        return synomyList;
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

        logger.info("getLastCollectionobjectByGroup: {}", collectionCode);

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
     * get taxon by Collectionobject.
     * 
     * @param object - Collectionobject. 
     * @return Taxon
     */
    public Taxon getTaxonByCollectionobject(Collectionobject object) {

        Query query = entityManager.createNamedQuery("Determination.findCurrentByCollectionobjectID");
        query.setParameter("collectionObjectId", object);
        query.setParameter("isCurrent", true);
        Determination determination = (Determination) query.getSingleResult();
        return (determination == null) ? null : determination.getTaxon();
    }

    public SpecifyBean getFetchgroupByNameedQuery(String namedQuery, Map<String, Object> conditions, List<String> fields) {

        logger.info("getFetchgroupByNameedQuery: {}", namedQuery);
        Query query = entityManager.createNamedQuery(namedQuery);

        for (Map.Entry<String, Object> map : conditions.entrySet()) {
            query.setParameter(map.getKey(), map.getValue());
        }

        FetchGroup group = new FetchGroup();
        for (String string : fields) {
            group.addAttribute(string);
        }

        query.setHint(QueryHints.FETCH_GROUP, group);
         
        try {
            SpecifyBean bean = (SpecifyBean) query.getSingleResult(); 
            return copyEntity(bean, fields, bean.getClass().getSimpleName()); 
        } catch (NoResultException e) {
            return null;
        }  
    }

    public <T extends SpecifyBean> List getAllFetchgroupByNameedQuery(String namedQuery, String classname, Map<String, Object> conditions, List<String> fields) {

        logger.info("getFetchgroupByNameedQuery: {}", namedQuery);

        Query query = entityManager.createNamedQuery(namedQuery);

        for (Map.Entry<String, Object> map : conditions.entrySet()) {
            query.setParameter(map.getKey(), map.getValue());
        }

        FetchGroup group = new FetchGroup();
        for (String string : fields) {
            group.addAttribute(string);
        }

        query.setHint(QueryHints.FETCH_GROUP, group);

        List<SpecifyBean> beans = query.getResultList();
        if(beans != null) {
            return copyEntityGroup(beans, fields, classname);
        }
        return new ArrayList();
    }
    
 
    
    public boolean isCatalogNumberExist(String catalognumber) {
        Query qry = entityManager.createNamedQuery("Collectionobject.findByCatalogNumber");
        qry.setParameter("catalogNumber", catalognumber);
        
        List<Collectionobject> list = (List<Collectionobject>)qry.getResultList();
        return list.size() > 0 ? true : false;
    }

    public Taxon getTaxonAndParents(String taxonName) {

        Query qry = entityManager.createNamedQuery("Taxon.findByFullName");
        qry.setParameter("fullName", taxonName);
        return ((List<Taxon>) qry.getResultList()).get(0); 
    }

    public Specifyuser loginSpecifyUser(Specifyuser user) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        logger.info("loginSpecifyUser: {}", user);

        List<String> fields = new ArrayList<String>();
        fields.add("specifyUserId");
        fields.add("name"); 
        fields.add("email"); 
        fields.add("loginOutTime"); 
        fields.add("userType");  
        fields.add("isLoggedIn");  
        
        Query query = entityManager.createNamedQuery("Specifyuser.findByName");
        FetchGroup group = new FetchGroup();

        for (String string : fields) {
            group.addAttribute(string);
        }

        query.setHint(QueryHints.FETCH_GROUP, group);
        query.setParameter("name", user.getName());
        user = (Specifyuser) query.getSingleResult();

        if (user != null) {
            Specifyuser spUser = getByReference(user.getSpecifyUserId(), Specifyuser.class);
            spUser.setIsLoggedIn(true);
            spUser.setLoginOutTime(timestamp);
            editEntity(spUser);
        }
        return (Specifyuser) copyEntity(user, fields, Specifyuser.class.getSimpleName());
    }

    public void logoutSpecifyUser(Specifyuser user) {

        logger.info("logoutSpecifyUser: {}", user);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Specifyuser spUser = getByReference(user.getSpecifyUserId(), Specifyuser.class);
        if (spUser != null) {
            spUser.setIsLoggedIn(false);
            spUser.setLoginOutTime(timestamp);
            editEntity(spUser);
        }
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

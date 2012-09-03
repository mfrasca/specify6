package se.nrm.specify.specify.data.jpa;
 
import java.util.List; 
import java.util.Map;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *  
 * @author idali
 */
public interface SpecifyDao<T extends SpecifyBean> {
     
    /**
     * Finds all the instances of an entity in the database.
     * @return a <code>List</code> of all the entities in the database.
     */
    public List<T> findAll(Class<T> clazz);
    
    /**
     * Finds a {@link BaseEntity} by its database ID.
     * @param id the database id of the entity we want.
     * 
     * @return the instance of the entity from the database with the given id.
     */
    public T findById(int id, Class<T> clazz);
    
    /**
     * Find an instance whose state may be lazily fetched
     * 
     * @param <T> entityClass
     * @param id the database id of the entity we want.
     * @param clazz
     * 
     * @return the instance of the entity from the database with the given id.
     */ 
    public T findByReference(int id, Class<T> clazz);
    
    /**
     * Saves a transient or persistent {@link BaseEntity} to the database. 
     * 
     * @param entity the entity to save.
     * @return a persistent copy of the entity.
     */
    public T create(T entity);
    
    
    /**
     * Saves a transient {@link BaseEntity} to the database. 
     * 
     * @param entity the entity to save.
     * @return a persistent copy of the entity.
     */
    public T merge(T entity);
    
    
    /**
     * Method for save and delete entity validation
     * 
     * @param bean
     * @param jpql
     * @return int
     */
    public int getCountByJPQL(T bean, String jpql);
    
     

    /**
     * Deletes a {@link BaseEntity} from the database. If the delete
     * was successful, the entity's ID will be null.
     * 
     * @param entity the entity to delete. 
     */
    public void delete(T entity);
    
    
    /**
     * Get partial entity with search value. 
     * 
     * @param searchValue -- the value to search
     * @param fieldName -- the field to search
     * @param entityName -- the search entity
     * @param fields -- the fields are partially fetched
     * @return partially fetched entity
     */
    public T getPartialEntity(Object searchValue, String fieldName, String entityName, List<String> fields);
    
    public List<T> getPartialEntities(Object searchValue, String fieldName, String entityName, List<String> fields);
    
    
    
    /**
     * 
     * @param jpql
     * @return List<String>
     */
    public List<String> getTextListByJPQL(String jpql);
    
    
    public T getLastRecord(String namedQuery, Map<String, Object> map);
    
    
    
    
    
    
    public List<T> getListByJPQLByFetchGroup(String classname, String jpql, List<String> fields);
    
    public T getFetchGroupByNamedQuery(Map<String, Object> map);
     
    

    public T getEntityByNamedQuery(String namedQuery, Map<String, Object> parameters);
  
    public List getAllEntitiesByNamedQuery(String namedQuery, Map<String, Object> parameters);
    
    public T getEntityByJPQL(String jpql);  
    
    
    public T partialMerge(T entity, List<String> fields);
}

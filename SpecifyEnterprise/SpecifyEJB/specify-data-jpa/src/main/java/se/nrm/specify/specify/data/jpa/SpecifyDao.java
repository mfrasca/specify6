package se.nrm.specify.specify.data.jpa;

import java.util.List;
import java.util.Map;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.Taxon;

/**
 *  
 * @author idali
 */
public interface SpecifyDao {

    /**
     * Persist entity in database
     * 
     * @param sBean the entity bean
     */
    public void createEntity(SpecifyBean sBean);

    /**
     * Edit entity
     * 
     * @param sBean the entity bean
     */
    public String editEntity(SpecifyBean sBean);

    /**
     * Delete entity in database
     * 
     * @param sBean the entity bean
     */
    public void deleteEntity(SpecifyBean sBean);

    /**
     * Get the entity by entity id
     * 
     * @param <T> the entity class
     * @param id the entity id
     * @param clazz entity
     * 
     * @return the entity
     */
    public <T extends SpecifyBean> T getById(int id, Class<T> clazz);

    /**
     * getByReference - Get an instance whose state may be lazily fetched
     * @param <T> entityClass
     * @param id
     * @param clazz
     * @return entity instance
     */
    public <T extends SpecifyBean> T getByReference(int id, Class<T> clazz);

    public <T extends SpecifyBean> List getAllByFetchGroup(Class<T> clazz, List<String> fields);

    public <T extends SpecifyBean> List getListByJPQLByFetchGroup(String classname, String jpql, List<String> fields);

    public List<Object[]> getDataListByJPQL(String jpql);

    /**
     * Get the list of entity
     * 
     * @param <T> the entity class 
     * @param clazz entity
     * 
     * @return the List<entity>
     */
    public <T extends SpecifyBean> List getAll(Class<T> clazz);

    public SpecifyBean getEntityByJPQL(String jpql);

    public SpecifyBean getEntityByNamedQuery(String namedQuery, Map<String, String> parameters);

    public List<String> getTextListByJPQL(String jpql);

    public <T extends SpecifyBean> List getAllEntitiesByNamedQuery(String namedQuery, Map<String, String> parameters);

    public <T extends SpecifyBean> List getAllEntitiesByJPQL(String jpql);

    public List<String> getSynomyList(Taxon taxon);

    /**
     * Get List of Determination by taxon name. collectingevent and collection code
     * @param taxonName
     * @param event
     * @param code
     * @return List<Determination>
     */
    public List<Determination> getDeterminationsByTaxonNameAndCollectingevent(String taxonName, Collectingevent event, String code);

    public Collectionobject getLastCollectionobjectByGroup(String collectionCode);

    public List<Collectionobject> getCollectionobjectByCollectingEventAndYesno2(Collectingevent event);

    public DataWrapper getDeterminationsByCollectingEvent(Collectingevent event, String collectionCode);

    public List<String> getDeterminationByLocalityID(Locality locality, String collectionCode);

    public DataWrapper getDeterminationsByTaxon(Taxon taxonId, String collectionCode);

    public Taxon getTaxonByCollectionobject(Collectionobject collectionobject);
    //    public <T extends SpecifyBean> T getPatialObjects(Class<T> clazz, List<String> fields);
//    
//    public <T extends SpecifyBean> T getPartialObject(Class<T> clazz, String field);
//    public <T extends SpecifyBean> T fatchByGroup(String classname, List<String> fields, Map<String, Object> conditions);
//    public <T extends SpecifyBean> T getByJPQLByFetchGroup(String classname, String jpql, List<String> fields);
}

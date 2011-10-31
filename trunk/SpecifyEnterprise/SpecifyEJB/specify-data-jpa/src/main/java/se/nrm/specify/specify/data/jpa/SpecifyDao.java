package se.nrm.specify.specify.data.jpa;

import java.util.List;  
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Geography;
import se.nrm.specify.datamodel.Geographytreedefitem;
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *  
 * @author idali
 */
public interface SpecifyDao {

 
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
     * Persist entity in database
     * 
     * @param sBean the entity bean
     */
    public void createEntity(SpecifyBean sBean);
    
    /**
     * Delete entity in database
     * 
     * @param sBean the entity bean
     */
    public void deleteEntity(SpecifyBean sBean);

    /**
     * Edit entity
     * 
     * @param sBean the entity bean
     */
    public String editEntity(SpecifyBean sBean);
    
    /**
     * Get the list of Geography by GeographytreedefitemId
     * 
     * @param g the Geographytreedefitem
     * 
     * @return a List of Geography
     */
    public List<Geography> getGeographyListByGeographytreedefitemId(Geographytreedefitem g);
    
    /**
     * Get the list of Dnasequences
     * 
     * @return the list of Dnasequences
     */
    public List<Dnasequence> getAllDnasequences();
    
//    public List<Object[]> getTaxonByCollectionMemberID(int id, String groupName);
//    
//    public List<Determination> getDeterminationsByTaxonId(Taxon t);
//    
//    public List<Object[]> getCollectionTree(int collectionId);
}

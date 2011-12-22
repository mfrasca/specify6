package se.nrm.specify.specify.data.jpa;

import java.util.List;   
import se.nrm.specify.datamodel.Collectingevent; 
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Dnasequence;
import se.nrm.specify.datamodel.Geography;
import se.nrm.specify.datamodel.Geographytreedefitem;
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.SpecifyBean; 
import se.nrm.specify.datamodel.Taxon;

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
     * Get the list of entity
     * 
     * @param <T> the entity class 
     * @param clazz entity
     * 
     * @return the List<entity>
     */
    public <T extends SpecifyBean> List getAll(Class<T> clazz);
    
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
     * Get Collectingevent by StationFieldNumber
     * @param stationFieldNumber
     * 
     * @return Collectingevent
     */
    public Collectingevent getCollectingEventByStationFieldNumber(String stationFieldNumber); 
    
    /**
     * Get all the taxon full name from database
     * @return List<String>
     */
    public List<String> getAllTaxonName();
    
    /**
     * Get taxon by taxon full name
     * @param taxonName
     * @return Taxon
     */
    public Taxon getTaxonByTaxonName(String taxonName);
     
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
    
    public List<Collectingevent> getCollectingeventsByLocality(String localityName);
    
    public List<String> getDeterminationByLocalityID(Locality locality, String collectionCode);
    
    public DataWrapper getDeterminationsByTaxon(Taxon taxonId, String collectionCode);
            
    
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
}

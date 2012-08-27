package se.nrm.specify.business.logic.specify;
 
import java.util.List;
import java.util.Map;
import se.nrm.specify.business.logic.validation.Validation;  
import se.nrm.specify.business.logic.validation.SpecifyBeanId;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.SpecifyDao;

/**
 *
 * @author ida_ho_99
 */
public interface SpecifyLogic {
    
    public SpecifyDao getDao();
      
    /**
     * Delete en entity with entity id
     * 
     * @param spId
     * @return Validation
     */
    public Validation deleteEntity(SpecifyBeanId spId); 
    
    /**
     * 
     * Persist entity
     * 
     * @param bean
     * @return Validation
     */
    public Validation createEntity(SpecifyBean bean); 
    
    /**
     * Merge entity
     * 
     * @param bean
     * @return Validation
     */
    public Validation mergeEntity(SpecifyBean bean);
    
    /**
     * Merge partial entity, merge only data according the fields list
     * 
     * @param bean
     * @param fields
     * @return Validation
     */
    public Validation mergeEntity(SpecifyBean bean, List<String> fields);
    
//    public Validation mergeEntity(SpecifyBean bean, Map<String, Object> map);
}

package se.nrm.specify.business.logic.specify;
 
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
      
    public Validation deleteEntity(SpecifyBeanId spId); 
    
    public Validation createEntity(SpecifyBean bean);
    
    public Validation mergeEntity(SpecifyBean bean);
}

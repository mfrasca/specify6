package se.nrm.specify.business.logic.validation;

import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class DeterminationValidation extends BaseValidationRules {
    
    private Determination determination;
     
    public DeterminationValidation() {
        super(Determination.class);
    }
    
    public DeterminationValidation(SpecifyBean bean) {   
        super(bean);
        this.determination = (Determination) bean; 
    }
    
    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Determination) bean;
        this.determination = (Determination) bean;  
        this.sbId = new SpecifyBeanId(determination.getIdentityString(), Determination.class.getSimpleName());
    } 
}

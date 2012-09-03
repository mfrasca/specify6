package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Deaccessionpreparation;
import se.nrm.specify.datamodel.Loanpreparation;
import se.nrm.specify.datamodel.Preparation;
import se.nrm.specify.datamodel.Preparationattachment;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public final class PreparationValidation extends BaseValidationRules {
      
    private Preparation preparation; 

    public PreparationValidation() {
        super(Preparation.class);
    }

    public PreparationValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        
        this.bean = (Preparation) bean;
        this.preparation = (Preparation) bean;
        
        this.sbId = new SpecifyBeanId(preparation);

        map = new HashMap<String, Object>();  
         
        relatedTables = new ArrayList<String>();
        relatedTables.add(Preparationattachment.class.getSimpleName());
        relatedTables.add(Loanpreparation.class.getSimpleName());
        relatedTables.add(Deaccessionpreparation.class.getSimpleName());
        
        map.put(Preparationattachment.class.getSimpleName(), "preparation"); 
        map.put(Loanpreparation.class.getSimpleName(), "preparation");
        map.put(Deaccessionpreparation.class.getSimpleName(), "preparation");
    }
    
     
 
    @Override
    public boolean isCheckForDelete() {
        return true;
    }  
}

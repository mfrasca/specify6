package se.nrm.specify.business.logic.validation;

import java.util.ArrayList; 
import se.nrm.specify.datamodel.Accessionauthorization;
import se.nrm.specify.datamodel.Permit;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public final class PermitValidation extends BaseValidationRules {
     
    private Permit permit;   
    
    private final static String PERMIT_NUMBER = "permitNumber";
    private final static String PRIMARY_FIELD = "permitId";

    public PermitValidation() {
        super(Permit.class);
    }

    public PermitValidation(SpecifyBean bean) {
        super(bean);
        initialize(bean); 
    }
     
    @Override
    public void initialize(final SpecifyBean bean) {
        this.bean = (Permit) bean;
        this.permit = (Permit) bean;
        
        this.sbId = new SpecifyBeanId(permit); 
    
        map.put(PERMIT_NUMBER, permit.getPermitNumber() == null ? "" : permit.getPermitNumber());
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, permit.getPermitId());
        }
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Permit.class.getSimpleName());   
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(PERMIT_NUMBER);
        
        relatedTables = new ArrayList<String>();
        relatedTables.add(Accessionauthorization.class.getSimpleName());
        map.put(Accessionauthorization.class.getSimpleName(), "permit");    
    }
  
    
    @Override
    public boolean isCheckForDuplication() {
        return true;
    } 

    @Override
    public boolean isCheckForDelete() {
        return true;
    } 
}

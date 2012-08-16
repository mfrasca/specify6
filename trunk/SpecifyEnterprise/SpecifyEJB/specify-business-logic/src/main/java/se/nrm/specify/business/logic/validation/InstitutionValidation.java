package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Institution;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.Constants;

/**
 *
 * @author idali
 */
public class InstitutionValidation extends BaseValidationRules {
    
    private final static String NAME = "name";
    private final static String PRIMARY_FIELD = "userGroupScopeId";  
    
    private Institution institution; 

    public InstitutionValidation() {
        super(Institution.class);
    }

    public InstitutionValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public final void initialize(final SpecifyBean bean) {
        this.bean = (Institution) bean;
        this.institution = (Institution) bean;
        
        this.sbId = new SpecifyBeanId(institution.getIdentityString(), Institution.class.getSimpleName());

        map = new HashMap<String, Object>();  
        map.put(NAME, (institution.getName() == null) ? "" : institution.getName());
       
        if(!isNew()) {
            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(Constants.getInstance().ID, institution.getUserGroupScopeId());
        }
        
        map.put(Constants.getInstance().TABLE_NAME, Institution.class.getSimpleName());  
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(NAME); 
    }

 
    @Override
    public boolean isCheckForDuplication() {
        return true;
    }
}

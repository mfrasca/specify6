package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Division;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.Constants;

/**
 *
 * @author idali
 */
public final class DivisionValidation extends BaseValidationRules {

    private final static String NAME = "name";
    private final static String PRIMARY_FIELD = "userGroupScopeId"; 
    private final static String SPECIAL_FIELD_NAME = "institution.userGroupScopeId";
    
    private Division division; 

    public DivisionValidation() {
        super(Division.class);
    }

    public DivisionValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Division) bean;
        this.division = (Division) bean;
        
        this.sbId = new SpecifyBeanId(division.getIdentityString(), Division.class.getSimpleName());

        map = new HashMap<String, Object>();  
        map.put(NAME, (division.getName() == null) ? "" : division.getName());
        if(!isNew()) {
            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(Constants.getInstance().ID, division.getUserGroupScopeId());
        }
        
        map.put(Constants.getInstance().TABLE_NAME, Division.class.getSimpleName()); 
        map.put(Constants.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(Constants.getInstance().SPECIAL_FIELD_VALUE, 
                                        division.getInstitution() == null ? null : division.getInstitution().getUserGroupScopeId());
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(NAME); 
    } 
  
    @Override
    public boolean isCheckForDuplication() {
        return true;
    } 
}

package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap; 
import org.apache.commons.lang.StringUtils;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.Specifyuser;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public final class SpecifyUserValidation extends BaseValidationRules {
    
    private final static String NAME = "name";
    private final static String PRIMARY_FIELD = "specifyUserId";  
    
    private Specifyuser spUser; 

    public SpecifyUserValidation() {
        super(Specifyuser.class);
    }

    public SpecifyUserValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Specifyuser) bean;
        this.spUser = (Specifyuser) bean;
        
        this.sbId = new SpecifyBeanId(bean);

        map = new HashMap<String, Object>();  
        map.put(NAME, (spUser.getName() == null) ? "" : spUser.getName());
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, spUser.getSpecifyUserId());
        } 
        map.put(ConstantsClass.getInstance().TABLE_NAME, Specifyuser.class.getSimpleName());   
        
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(NAME); 
    }

    @Override
    public Validation validationBeforeSave() { 
          
        String name = spUser.getName();
        
        if(name != null && name.length() > 0) {
            if(!StringUtils.deleteWhitespace(name).equals(name)) {
                return new ValidationError(sbId, Status.InvalidUserName, "The username can not have empty space.");
            } else if(StringUtils.contains(name, ",")) {
                return new ValidationError(sbId, Status.InvalidUserName, "The username can not have ','.");
            }
        } else {
            return new ValidationError(sbId, Status.InvalidUserName, "The username is required.");
        }
        
         
        return new ValidationOK(sbId, Status.Save);
    }
  
    @Override
    public boolean isCheckForDuplication() {
        return true;
    }

    @Override
    public boolean isCheckForSaving() {
        return true;
    } 
}

package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Picklist;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.Constants;

/**
 *
 * @author idali
 */
public final class PicklistValidation extends BaseValidationRules {
    
    private final static String NAME = "name";
    private final static String PRIMARY_FIELD = "pickListId"; 
    private final static String SPECIAL_FIELD_NAME = "collection.userGroupScopeId";
    
    private Picklist picklist; 

    public PicklistValidation() {
        super(Picklist.class);
    }

    public PicklistValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(final SpecifyBean bean) {
        this.bean = (Picklist) bean;
        this.picklist = (Picklist) bean;
        
        this.sbId = new SpecifyBeanId(picklist.getIdentityString(), Picklist.class.getSimpleName());

        map = new HashMap<String, Object>();  
        map.put(NAME, (picklist.getName() == null) ? "" : picklist.getName());
        if(!isNew()) {
            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(Constants.getInstance().ID, picklist.getPickListId());
        }
        
        map.put(Constants.getInstance().TABLE_NAME, Picklist.class.getSimpleName()); 
        map.put(Constants.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(Constants.getInstance().SPECIAL_FIELD_VALUE, 
                                        picklist.getCollection() == null ? null : picklist.getCollection().getUserGroupScopeId());
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(NAME); 
    }

 
    @Override
    public boolean isCheckForDuplication() {
        return true;
    } 
    
}

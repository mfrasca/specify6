package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Preparation;
import se.nrm.specify.datamodel.Preptype;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;


/**
 *
 * @author idali
 */
public final class PreptypeValidation extends BaseValidationRules {
    
    private final static String NAME = "name";
    private final static String PRIMARY_FIELD = "prepTypeId"; 
    private final static String SPECIAL_FIELD_NAME = "collection.userGroupScopeId";
    
    private Preptype preptype; 

    public PreptypeValidation() {
        super(Preptype.class);
    }

    public PreptypeValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        
        this.bean = (Preptype) bean;
        this.preptype = (Preptype) bean;
        
        this.sbId = new SpecifyBeanId(preptype);

        map = new HashMap<String, Object>();  
        map.put(NAME, (preptype.getName() == null) ? "" : preptype.getName());
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, preptype.getPrepTypeId());
        }
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Preptype.class.getSimpleName()); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD_VALUE, 
                                        preptype.getCollection() == null ? null : preptype.getCollection().getUserGroupScopeId());
        
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(NAME);
        
        
        relatedTables = new ArrayList<String>();
        relatedTables.add(Preparation.class.getSimpleName());
        
        map.put(Preparation.class.getSimpleName(), "prepType"); 
    }
 
    @Override
    public boolean isCheckForDelete() {
        return true;
    }  
    
    @Override
    public boolean isCheckForDuplication() {
        return true;
    }
}

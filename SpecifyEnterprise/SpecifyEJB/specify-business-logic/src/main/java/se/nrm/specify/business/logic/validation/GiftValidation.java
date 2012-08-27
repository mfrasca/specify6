package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Gift;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public final class GiftValidation extends BaseValidationRules {
    
    private final static String GIFT_NUMBER = "giftNumber";
    private final static String PRIMARY_FIELD = "giftId"; 
    private final static String SPECIAL_FIELD_NAME = "discipline.userGroupScopeId";
    
    private Gift gift; 

    public GiftValidation() {
        super(GiftValidation.class);
    }

    public GiftValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Gift) bean;
        this.gift = (Gift) bean;
        
        this.sbId = new SpecifyBeanId(gift);

        map = new HashMap<String, Object>();  
        map.put(GIFT_NUMBER, (gift.getGiftNumber() == null) ? "" : gift.getGiftNumber());
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, gift.getGiftId());
        }
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Gift.class.getSimpleName()); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD_VALUE, 
                                        gift.getDiscipline() == null ? null : gift.getDiscipline().getUserGroupScopeId());
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(GIFT_NUMBER); 
    }
     

    @Override
    public boolean isCheckForDuplication() {
        return true;
    }  
}

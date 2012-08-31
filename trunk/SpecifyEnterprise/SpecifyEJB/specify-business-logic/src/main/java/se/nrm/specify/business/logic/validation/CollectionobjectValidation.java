package se.nrm.specify.business.logic.validation;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.nrm.specify.business.logic.util.ValidationMessage;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.Determination;  
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public final class CollectionobjectValidation extends BaseValidationRules {
    
    private final static String CATALOG_NUMBER = "catalogNumber";
    private final static String PRIMARY_FIELD = "collectionObjectId";
    private final static String SPECIAL_FIELD_NAME = "collection.userGroupScopeId";
    
    private Collectionobject collectionObject; 
    private List<String> errorMsgs; 

    public CollectionobjectValidation() {
        super(Collectionobject.class);
    }

    public CollectionobjectValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Collectionobject) bean;
        this.collectionObject = (Collectionobject) bean;
        
        this.sbId = new SpecifyBeanId(collectionObject);

        map = new HashMap<String, Object>();  
        map.put(CATALOG_NUMBER, (collectionObject.getCatalogNumber() == null) ? "" : collectionObject.getCatalogNumber());
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, collectionObject.getCollectionObjectId());
        }
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Collectionobject.class.getSimpleName()); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD_VALUE, 
                                        collectionObject.getCollection() == null ? null : collectionObject.getCollection().getUserGroupScopeId());
 
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(CATALOG_NUMBER); 
        
        validationRuleMap =  new HashMap<String, IBaseValidationRules>();
        if(collectionObject.getAccession() != null) { 
            validationRuleMap.put("accession", new AccessionValidation(collectionObject.getAccession())); 
        } 
        
        if(collectionObject.getCollectingEvent() != null) {
            validationRuleMap.put("collectionEvent", new CollectingeventValidation(collectionObject.getCollectingEvent()));
        } 
    }

    @Override
    public Validation validationBeforeSave() {
          
        boolean isValidForSaving = true;
        List<String> msgs = new ArrayList<String>();
        
        int count = 0;
        Collection<Determination> determinations = collectionObject.getDeterminations(); 
        if (determinations != null && !determinations.isEmpty()) {
            for (Determination determination : determinations) {
 
                // There is one and only one current determination within collectionObject
                if (determination.getIsCurrent()) {
                    count++;
                }
            } 
            if (count > 1) {
                isValidForSaving = false;
                msgs.add(ValidationMessage.getInstance().CURRENT_DETERMINATION);
            } else if (count < 1) {
                isValidForSaving = false;
                msgs.add(ValidationMessage.getInstance().NO_CURRENT_DETERMINATION);
            }
        }
        
        
         
        for(Map.Entry<String, IBaseValidationRules> entry : validationRuleMap.entrySet()) {
            Validation validation = entry.getValue().validationBeforeSave();
            if(validation.isValidationNotOK()) {
                isValidForSaving = false;
                ValidationError error = (ValidationError)validation;
                msgs.add(error.getMessage());  
            }
        }
        if(!isValidForSaving) {
            return new ValidationError(sbId, isNew() ? Status.CreateNew : Status.Update, msgs); 
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
 
    @Override
    public String createMsg(Status validation, ValidationStatus status, String addition, Map map) {  
        return status == ValidationStatus.ERROR ? errorMsgs.toString() : "";
    } 
}

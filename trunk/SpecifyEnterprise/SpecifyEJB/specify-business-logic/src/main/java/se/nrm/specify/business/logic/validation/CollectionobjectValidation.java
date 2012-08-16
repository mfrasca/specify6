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
import se.nrm.specify.specify.data.jpa.util.Constants;

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
        
        this.sbId = new SpecifyBeanId(collectionObject.getIdentityString(), Collectionobject.class.getSimpleName());

        map = new HashMap<String, Object>();  
        map.put(CATALOG_NUMBER, (collectionObject.getCatalogNumber() == null) ? "" : collectionObject.getCatalogNumber());
        if(!isNew()) {
            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(Constants.getInstance().ID, collectionObject.getCollectionObjectId());
        }
        
        map.put(Constants.getInstance().TABLE_NAME, Collectionobject.class.getSimpleName()); 
        map.put(Constants.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(Constants.getInstance().SPECIAL_FIELD_VALUE, 
                                        collectionObject.getCollection() == null ? null : collectionObject.getCollection().getUserGroupScopeId());
 
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(CATALOG_NUMBER);
        
//        relatedTables = new ArrayList<String>();
//        relatedTables.add(Collection.class.getSimpleName());
//        
//        map.put(Collection.class.getSimpleName(), "collectionObject"); 
    }

    @Override
    public Validation validationBeforeSave() {
          
        int count = 0;
        Collection<Determination> determinations = collectionObject.getDeterminations();
        if(determinations != null) {
            for(Determination determination : determinations) {
                // There is one and only one current determination within collectionObject
                if(determination.getIsCurrent()) {
                    count++;
                } 
            }
        } 
        if(count > 1) {
            return new ValidationError(sbId, Status.CurrentDetermination, ValidationMessage.getInstance().CURRENT_DETERMINATION); 
        } else if(count < 1) {
            return new ValidationError(sbId, Status.CurrentDetermination, ValidationMessage.getInstance().NO_CURRENT_DETERMINATION); 
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

//    @Override
//    public boolean isCheckForDelete() {
//        return true;
//    } 
    
//    @Override
//    public boolean isOkToDelete() {
//        
//        boolean isOkToDelete = true;
//        Collection<Preparation> preparations = collectionObject.getPreparations();
//        if(preparations != null && preparations.isEmpty()) {
//            for(Preparation preparation : preparations) {
//                if(preparation.getLoanPreparations() != null && !preparation.getLoanPreparations().isEmpty()) {
//                    isOkToDelete = false;
//                    errorMsgs.add(collectionObject + " can not be deleted, because there are associated prepartion with loanPreparations.");
//                }
//                if(preparation.getDeaccessionPreparations() != null && !preparation.getDeaccessionPreparations().isEmpty()) {
//                    isOkToDelete = false;
//                    errorMsgs.add(collectionObject + " can not be deleted, because there are associated prepartion with deaccessionPreparations.");
//                }
//            } 
//        }
//        return isOkToDelete;
//    }
 
    @Override
    public String createMsg(Status validation, ValidationStatus status, String addition, Map map) {  
        return status == ValidationStatus.ERROR ? errorMsgs.toString() : "";
    } 
}

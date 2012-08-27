package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import se.nrm.specify.datamodel.Collection;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public class CollectionValidation extends BaseValidationRules {
    
    private final static String COLLECTION_NAME = "collectionName";
    private final static String CODE = "code";  
    private final static String PRIMARY_FIELD = "userGroupScopeId"; 
    
    private Collection collection; 

    public CollectionValidation() {
        super(Collection.class);
    }

    public CollectionValidation(SpecifyBean bean) {
        super(bean);
        this.collection = (Collection) bean; 
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Collection) bean;
        this.collection = (Collection) bean;

        map = new HashMap<String, Object>(); 
        map.put(COLLECTION_NAME, (collection.getCollectionName() == null) ? "" : collection.getCollectionName());
        map.put(CODE, collection.getCode() == null ? "" : collection.getCode());
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, collection.getUserGroupScopeId());
        } 
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Collection.class.getSimpleName());  
        
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(COLLECTION_NAME);
        duplicationCheckFields.add(CODE);
        
        
        this.sbId = new SpecifyBeanId(collection);

        relatedTables = new ArrayList<String>();
        relatedTables.add(Collectionobject.class.getSimpleName());
        map.put(Collectionobject.class.getSimpleName(), "collection"); 
    }

    @Override
    public Validation validationBeforeSave() {
        
        Validation validation = isCollectionValid();
        if(validation.isValidationNotOK()) {
            return validation;
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
    public boolean isCheckForDelete() {
        return true;
    } 

    private Validation isCollectionValid() {
        
        List<String> msgs = new ArrayList();
        boolean isValid = true;
        if(collection.getCatalogNumFormatName() == null || collection.getCatalogNumFormatName().length() <= 0) {
            isValid = false;
            msgs.add("catalogNumFormatName must be specified."); 
        }
        if(collection.getDiscipline() == null) {
            isValid = false;
            msgs.add("Discipline must be specified.");
        }
       
        return isValid ? new ValidationOK(sbId, isNew() ? Status.CreateNew : Status.Update) : new ValidationError(sbId, Status.FieldCanNotBeNull, msgs);
    } 
    
}

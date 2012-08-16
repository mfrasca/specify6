package se.nrm.specify.business.logic.validation;

import java.util.List;
import java.util.Map;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public interface IBaseValidationRules {
    
    public enum ValidationMethod{SAVE, GET, UPDATE, DELETE; };
      
    public abstract void initialize(SpecifyBean bean); 
     
    public abstract boolean isCheckForDuplication();
    
    public abstract boolean isCheckForDelete();
    
    public abstract boolean isCheckForSaving();
    
    public abstract boolean isNew();
    
    public abstract boolean isOkToDelete();
    
    public Validation validationBeforeSave();
    
    public abstract SpecifyBeanId getSpecifyBeanId(); 
    
    public abstract Map<String, Object> getMap(); 
    
    public abstract List<String> getRelatedTables(); 
    
    public abstract List<String> getDuplicationCheckFields();
    
    public String createMsg(Status validation, ValidationStatus status, String addition, Map map);
    
    public String createDuplicationErrorMsg(Map map, String field);
    
    public String createCheckDuplicationSQL(Map map, String field);
    
    public String createCheckForDeleteJPQL(String entityName, String relatedField, String field);
    
    public String createCheckForDeleteJPQL(String entityName, String relatedField);
    
    public int countForDelete();
}

package se.nrm.specify.business.logic.validation;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.BaseEntity;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.Constants;

/**
 *
 * @author idali
 */
public class BaseValidationRules implements IBaseValidationRules {
    
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected SpecifyBean bean = null;
    protected Class<?>[] dataClasses;
    protected SpecifyBeanId sbId; 
    
    protected Map<String, Object> map = new HashMap<String, Object>();
    protected List<String> relatedTables = new ArrayList<String>();  
    protected List<String> duplicationCheckFields = new ArrayList<String>();
      
    public BaseValidationRules(final Class<?>... dataClasses) {
        this.dataClasses = dataClasses;
    }

    public BaseValidationRules(final SpecifyBean bean) {
        this.bean = bean;
        this.sbId = new SpecifyBeanId("", bean.getClass().getSimpleName());
    }

    public void initialize(SpecifyBean bean) {
        this.bean = bean;
    }

    public boolean isCheckForDuplication() {
        return false;
    }

    public boolean isCheckForDelete() {
        return false;
    }
    
    public boolean isCheckForSaving() {
        return false;
    }
    
    public boolean isOkToDelete() {
        return true;
    }
    
    public Validation validationBeforeSave() {
        return new ValidationOK(sbId, Status.Save);
    } 

    public Map<String, Object> getMap() {
        return map;
    } 

    public List<String> getRelatedTables() {
        return relatedTables;
    }

    public List<String> getDuplicationCheckFields() {
        return duplicationCheckFields;
    }
        
    public ValidationStatus checkDuplicateNumber(String fldName, String tblName, String colName, String value) { 
        return ValidationStatus.OK;
    }

     
    public SpecifyBeanId getSpecifyBeanId() {
        return sbId;
    }
    
    public String createMsg(Status validation, ValidationStatus status, String addition, Map map) {
        StringBuilder sb = new StringBuilder();
        sb.append(bean.getClass().getSimpleName());

        if (validation.equals(Status.Delete)) {
            switch (status) {
                case OK: 
                    sb.append(" ok to delete.");
                    break; 
                case ERROR:
                    sb.append(" Cannot be deleted, because there is/are associated ");
                    sb.append(addition);
                    sb.append("(s).");
                    break;  
                default:
                    sb.append("ok to delete.");
                    break;
            } 
        }  
        return sb.toString();
    }
    
    public String createDuplicationErrorMsg(Map map, String field) {
        StringBuilder sb = new StringBuilder();
        sb.append(bean.getClass().getSimpleName()); 
        sb.append(" : Duplicate entry '");
        sb.append(map.get(field));
        sb.append("' for key '");
        sb.append(field); 
        if (map.containsKey(Constants.getInstance().SPECIAL_FIELD)) {
            sb.append(", ");
            sb.append(" within ");
            sb.append(map.get(Constants.getInstance().SPECIAL_FIELD));
            sb.append(" = ");
            sb.append(map.get(Constants.getInstance().SPECIAL_FIELD_VALUE));
        }
        return sb.toString();
    }
     
    public String createCheckForDeleteJPQL(String entityName, String relatedField) {  
        return createCheckForDeleteJPQL(entityName, relatedField, relatedField.toLowerCase());
    }
    
    public String createCheckForDeleteJPQL(String entityName, String relatedField, String field) {
        
        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT count(o) FROM ");
        sb.append(entityName);
        sb.append(" o Where o.");
        sb.append(relatedField);
        sb.append(" = :");
        sb.append(field);
          
        return sb.toString();
    }
     
    public int countForDelete() {
        return 0;
    }
    
    public String createCheckDuplicationSQL(Map map, String field) {
          
        String tableName = (String)map.get(Constants.getInstance().TABLE_NAME); 
        Object fieldValue = map.get(field); 
         
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(o.");
        sb.append(field);
        sb.append(") FROM ");
        sb.append(tableName);
        sb.append(" o where o.");
        sb.append(field);
        
        if(fieldValue instanceof java.lang.String) {
            sb.append(" = '");
            sb.append(fieldValue);
            sb.append("'");
        } else if(fieldValue instanceof java.lang.Integer) {
            sb.append(" = ");
            sb.append(fieldValue); 
        } 
          
        if(map.containsKey(Constants.getInstance().PRIMARY_FIELD_NAME)) {
            String primaryField = (String)map.get(Constants.getInstance().PRIMARY_FIELD_NAME);
            Object primaryFieldValue = (Object) map.get(Constants.getInstance().ID);
            
            sb.append(" AND o.");
            sb.append(primaryField);
            sb.append(" <> ");
            if(primaryFieldValue instanceof Integer) {
                primaryFieldValue = (Integer)primaryFieldValue;
                sb.append(primaryFieldValue);
            } else if(primaryFieldValue instanceof String) {
                primaryFieldValue = (String)primaryFieldValue;
                sb.append("'");
                sb.append(primaryFieldValue);
                sb.append("'");
            } 
        }
        
        if(map.containsKey(Constants.getInstance().SPECIAL_FIELD)) {
            
            String specialField = (String)map.get(Constants.getInstance().SPECIAL_FIELD);
            Object specialFieldValue = (Object)map.get(Constants.getInstance().SPECIAL_FIELD_VALUE);
            
            if(specialFieldValue != null) {
                sb.append(" AND o.");
                sb.append(specialField);
                sb.append("=");

                if (specialFieldValue instanceof Integer) {
                    sb.append(specialFieldValue);
                } else if (specialFieldValue instanceof String) {
                    sb.append("'");
                    sb.append(specialFieldValue);
                    sb.append("'");
                }
            } 
        }  

        logger.info("query: {}", sb.toString());
        return sb.toString();
    }

    public boolean isNew() {
        BaseEntity bEntity = (BaseEntity)bean; 
        return bEntity.getIdentityString().equals("0") ? true : false;
    } 
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bean.getClass().getSimpleName());
        sb.append(" : ");
        sb.append(bean);
        return sb.toString();
    }
}

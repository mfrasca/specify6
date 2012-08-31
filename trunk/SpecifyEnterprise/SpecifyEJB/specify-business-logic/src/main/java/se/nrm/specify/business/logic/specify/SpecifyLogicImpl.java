package se.nrm.specify.business.logic.specify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.business.logic.util.ValidationMessage; 
import se.nrm.specify.business.logic.validation.IBaseValidationRules;
import se.nrm.specify.business.logic.validation.ValidationHelpClass;
import se.nrm.specify.business.logic.validation.Validation;
import se.nrm.specify.business.logic.validation.SpecifyBeanId;
import se.nrm.specify.business.logic.validation.Status;
import se.nrm.specify.business.logic.validation.ValidationError;
import se.nrm.specify.business.logic.validation.ValidationOK;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.SpecifyDao;
import se.nrm.specify.business.logic.validation.ValidationStatus;
import se.nrm.specify.business.logic.validation.ValidationWarning; 
import se.nrm.specify.specify.data.jpa.exceptions.DataStoreException; 

/**
 *
 * @author ida_ho_99
 */
@Stateless 
public class SpecifyLogicImpl implements SpecifyLogic {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private IBaseValidationRules validationRules;
    private List<String> msgs;
    
    @EJB
    private SpecifyDao dao;
     

    public SpecifyLogicImpl() {
    }

    public SpecifyLogicImpl(SpecifyDao dao) {
        this.dao = dao;
    }

    public SpecifyDao getDao() {
        return dao;
    }
    
    public Validation createEntity(SpecifyBean bean) {
        return mergeEntity(bean);
    }
    
    public Validation mergeEntity(SpecifyBean bean) {
        Validation validation = mergeValidation(bean);
        if(validation.isValidationNotOK()) {
            return validation;
        }
        try {
            bean = dao.merge(bean); 
            return new ValidationOK(new SpecifyBeanId(bean), (validationRules.isNew()) ? Status.CreateNew : Status.Update, (validationRules.isNew()) ? bean + " is persisted" : bean + " is updated.");
        } catch (DataStoreException e) {
            return new ValidationError(validationRules.getSpecifyBeanId(), (validationRules.isNew()) ? Status.CreateNew : Status.Update, e);
        }
    }
 
    public Validation mergeEntity(SpecifyBean bean, List<String> fields) {
        
        logger.info("mergeEntity : {} - {}", bean, fields);
    
        Validation validation = mergeValidation(bean);
        if(validation.isValidationNotOK()) {
            return validation;
        } 
        try {
            bean = dao.partialMerge(bean, fields); 
            return new ValidationOK(new SpecifyBeanId(bean), (validationRules.isNew()) ? Status.CreateNew : Status.Update, (validationRules.isNew()) ? bean + " is persisted" : bean + " is updated.");
        } catch (DataStoreException e) {
            return new ValidationError(validationRules.getSpecifyBeanId(), (validationRules.isNew()) ? Status.CreateNew : Status.Update, e);
        }
    }

    public Validation mergeValidation(SpecifyBean bean) {

        logger.info("mergeValidation: {}", bean);

        validationRules = getValidationRules(bean);
        Validation validation = null; 
        if (validationRules.isCheckForSaving()) {
            validation = validationRules.validationBeforeSave(); 
            if (validation.isValidationNotOK()) {
                return validation;
            }
        } 
        if (validationRules.isCheckForDuplication()) {
            validation = checkForDuplication();
            if (validation.isValidationNotOK()) {
                return validation;
            }
        } 
        return new ValidationOK(new SpecifyBeanId(bean), (validationRules.isNew()) ? Status.CreateNew : Status.Update);
    }
     

    public Validation deleteEntity(SpecifyBeanId spId) {

        logger.info("deleteEntity: {}", spId);
        
        msgs = new ArrayList<String>(); 
        SpecifyBean bean = dao.findById(spId.asInt(), spId.getClazz());  
        if (bean != null) {
            
            validationRules = getValidationRules(bean);
             
            if (validationRules.isOkToDelete()) { 
                if (validationRules.isCheckForDelete()) { 
                    if (!validateDelete(bean)) {
                        return new ValidationError(spId, Status.Delete, msgs);
                    }
                }
                try {
                    dao.delete(bean);
                    return new ValidationOK(spId, Status.Delete, ValidationMessage.getInstance().ENTITY_DELETE_OK);
                } catch (DataStoreException e) {
                    return new ValidationError(spId, e);
                } catch (Exception e) {
                    return new ValidationError(spId, e);
                }
            } else {
                String msg = validationRules.createMsg(Status.Delete, ValidationStatus.ERROR, null, null);
                return new ValidationError(spId, Status.Delete, msg);
            }

        }
        return new ValidationWarning(spId, Status.Delete, ValidationMessage.getInstance().ENTITY_CAN_NOT_DELETE);
    }

  

    private Validation checkForDuplication() {

        logger.info("checkForDuplication : {}", validationRules );
        Validation validation = duplicationValidation(validationRules);
        if(validation.isValidationNotOK()) {
            return validation;
        }
  
        Map<String, IBaseValidationRules> ruleMap = validationRules.getValidationRuleMap();
        if(ruleMap != null && !ruleMap.isEmpty()) {
            for(Map.Entry<String, IBaseValidationRules> entry : ruleMap.entrySet()) {
                IBaseValidationRules rule = entry.getValue();
                if(rule.isCheckForDuplication()) {
                    validation = duplicationValidation(rule); 
                    if(validation.isValidationNotOK()) {
                        return validation;
                    }
                }
            }
        }  
        return validation;  
    }
    
    private Validation duplicationValidation(IBaseValidationRules validationRules) {
        
        Map map = validationRules.getMap();
        List<String> duplicationFields = validationRules.getDuplicationCheckFields();

        boolean isValid = true;
        msgs = new ArrayList<String>();
        for (String field : duplicationFields) { 
            int count = 0;
            try {
                count = dao.getCountByJPQL(null, validationRules.createCheckDuplicationSQL(map, field));
            } catch (DataStoreException e) {
                return new ValidationError(validationRules.getSpecifyBeanId(), e);
            }
            if (count > 0) {
                isValid = false;
                String msg = validationRules.createDuplicationErrorMsg(map, field);
                msgs.add(msg); 
            } 
        }
        
        return isValid ? new ValidationOK(validationRules.getSpecifyBeanId(), Status.DuplicateNumber) : 
                         new ValidationError(validationRules.getSpecifyBeanId(), Status.DuplicateNumber, msgs);  
        
    }

    private IBaseValidationRules getValidationRules(SpecifyBean bean) {
        validationRules = ValidationHelpClass.getValidationRules(bean);

        if (validationRules != null) {
            validationRules.initialize(bean);
        }
        return validationRules;
    }

    private boolean validateDelete(SpecifyBean bean) {
        
        msgs = new ArrayList<String>();
        boolean isOkToDelete = true;
        List<String> relatedTables = validationRules.getRelatedTables();
        Map map = validationRules.getMap();
        for (String relatedTable : relatedTables) {
            String jpql = validationRules.createCheckForDeleteJPQL(relatedTable, (String) map.get(relatedTable));
            int count = dao.getCountByJPQL(bean, jpql);
 
            if (count > validationRules.countForDelete()) {
                String msg = validationRules.createMsg(Status.Delete, ValidationStatus.ERROR, relatedTable, null);
                msgs.add(msg);
                isOkToDelete = false;
            }
        }

        return isOkToDelete;
    }
}

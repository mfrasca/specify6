package se.nrm.specify.business.logic.validation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@XmlRootElement(name="validationResult")
public class ValidationWarning extends ValidationResult {
    
    public ValidationWarning() { 
    }
    
    public ValidationWarning(SpecifyBeanId specifyBeanId, Status validation, String message) {
        super(specifyBeanId, validation, message, null);
    }

    public ValidationWarning(SpecifyBeanId specifyBeanId, Status validation, Throwable throwable) {
        super(specifyBeanId, validation, "", throwable);
    }

    public ValidationWarning(SpecifyBeanId specifyBeanId, Exception exception) {
        super(specifyBeanId, Status.fromException(exception), exception.getMessage(), exception);
    }

    public ValidationWarning(SpecifyBeanId specifyBeanId, Status validation, String message, Throwable throwable) {
        super(specifyBeanId, validation, message, throwable);
    }

    @Override
    public ValidationStatus getStatus() {
        return ValidationStatus.WARNING;
    }

    public boolean isValidationNotOK() {
        return true;
    }

    public boolean isValidationError() {
        return false;
    }
  
    
}

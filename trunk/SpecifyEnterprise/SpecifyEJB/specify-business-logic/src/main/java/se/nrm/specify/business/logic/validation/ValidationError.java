package se.nrm.specify.business.logic.validation;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@XmlRootElement(name = "validationResult")
public class ValidationError extends ValidationResult {
 
    public ValidationError() {
    }

    public ValidationError(SpecifyBeanId specifyBeanId, Status validation, String message) {
        super(specifyBeanId, validation, message, null); 
    }

    public ValidationError(SpecifyBeanId specifyBeanId, Status validation, List<String> messages) {
        super(specifyBeanId, validation, messages, null); 
    }
    
    public ValidationError(SpecifyBeanId specifyBeanId, Status validation, String message, Throwable throwable) {
        super(specifyBeanId, validation, message, throwable); 
    }

    public ValidationError(SpecifyBeanId specifyBeanId, Exception exception) {
        super(specifyBeanId, Status.fromException(exception), exception.getMessage(), exception); 
    }

    public ValidationError(SpecifyBeanId specifyBeanId, Status validation, Throwable throwable) {
        super(specifyBeanId, validation, throwable.getMessage(), throwable); 
    }

    @Override
    public ValidationStatus getStatus() {
        return ValidationStatus.ERROR;
    }

    public boolean isValidationNotOK() {
        return true;
    }

    public boolean isValidationError() {
        return true;
    }
}

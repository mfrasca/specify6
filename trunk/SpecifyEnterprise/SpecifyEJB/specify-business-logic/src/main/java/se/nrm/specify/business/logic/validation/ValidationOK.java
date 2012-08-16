package se.nrm.specify.business.logic.validation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@XmlRootElement(name="validationResult")
public class ValidationOK extends ValidationResult {
    
    public ValidationOK() { 
    }

    public ValidationOK(SpecifyBeanId specifyBeanId, Status validation, String message) {
        super(specifyBeanId, validation, message, null);
    }

    public ValidationOK(SpecifyBeanId specifyBeanId, Status validation) {
        super(specifyBeanId, validation, "", null);
    }

    @Override
    public ValidationStatus getStatus() {
        return ValidationStatus.OK;
    }

    public boolean isValidationNotOK() {
        return false;
    }

    public boolean isValidationError() {
        return false;
    } 
}

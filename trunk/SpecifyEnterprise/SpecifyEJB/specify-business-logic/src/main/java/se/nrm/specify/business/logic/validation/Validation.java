package se.nrm.specify.business.logic.validation;

/**
 *
 * @author idali
 */
public interface Validation {
    
    public ValidationStatus getStatus(); 
    public boolean isValidationNotOK();
    public boolean isValidationError();
    
}

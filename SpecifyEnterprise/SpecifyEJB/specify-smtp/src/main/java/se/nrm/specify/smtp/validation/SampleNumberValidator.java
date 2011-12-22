package se.nrm.specify.smtp.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author idali
 */
public class SampleNumberValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent uic, Object obj) throws ValidatorException {
        String sampleNumber = (String) obj;
        if (!isDigital(sampleNumber)) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Invalid number.");
            message.setDetail("Number of vials is not valid.");
            context.addMessage(uic.getClientId(), message);
               
            throw new ValidatorException(message);
        }
    }

    private boolean isDigital(String sampleNumber) {
        for (int i = 0; i < sampleNumber.length(); i++) {
            //If a non-digit character find, return false.
            if (!Character.isDigit(sampleNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

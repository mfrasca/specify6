package se.nrm.specify.business.logic.validation;
 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement; 

/**
 *
 * @author idali
 */
@XmlRootElement  
public class ValidationWrapper {
    
    @XmlElements({
        @XmlElement(name = "validationOk", type = ValidationOK.class),
        @XmlElement(name = "validationError", type = ValidationError.class),
        @XmlElement(name = "validationWarning", type = ValidationWarning.class) 
    }) 
    private Validation validation;
       
    public ValidationWrapper() {
        
    }
    
    public ValidationWrapper(Validation validation) {
        this.validation = validation;
    }

    public Validation getValidation() {
        return validation;
    } 
}

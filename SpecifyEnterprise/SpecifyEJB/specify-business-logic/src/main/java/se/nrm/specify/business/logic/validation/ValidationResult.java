package se.nrm.specify.business.logic.validation;

import java.io.Serializable;      
import java.util.List;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author idali
 */  
@XmlSeeAlso({ValidationOK.class, ValidationError.class, ValidationWarning.class})
public abstract class ValidationResult implements Serializable, Validation {
   
    private String validation;
    private String message;
    private List<String> messages;
    private String stringRepresentation;
    private String id;
    private String entity;
    
    public ValidationResult() { 
    }
    
    public ValidationResult(SpecifyBeanId specifyBeanId, Status validation, String message, Throwable throwable) {   
        this.id = specifyBeanId.asString();
        this.entity = specifyBeanId.getClassName();
        this.validation = validation.getValidationText();
        this.message = message; 
        StringBuilder stringBuilder = new StringBuilder("<" + getLevel() + ":" + validation + " : " + specifyBeanId);
        
        if (message != null) {
            stringBuilder.append(",   \" ");
            stringBuilder.append(message);
            stringBuilder.append("\"");
        }
        
        if (throwable != null) {
            stringBuilder.append(", ");
            stringBuilder.append(throwable);
        }
        stringBuilder.append(">");
        stringRepresentation = stringBuilder.toString();
    }
    
    public ValidationResult(SpecifyBeanId specifyBeanId, Status validation, List<String> messages, Throwable throwable) { 
        this.validation = validation.getValidationText();
        this.messages = messages; 
        StringBuilder stringBuilder = new StringBuilder("<" + getLevel() + ":" + validation + " : " + specifyBeanId);
        
        if (messages != null) {
            for(String msg : messages) {
                stringBuilder.append(",   \" ");
                stringBuilder.append(msg);
                stringBuilder.append("\"");
            } 
        }
        
        if (throwable != null) {
            stringBuilder.append(", ");
            stringBuilder.append(throwable);
        }
        stringBuilder.append(">");
        stringRepresentation = stringBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
    
     

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
 
    
    
    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
  
    @Override
    public String toString() {
        return stringRepresentation;
    }

    public final String getLevel() { 
        return getClass().getSimpleName();
    }

    public abstract ValidationStatus getStatus(); 
}

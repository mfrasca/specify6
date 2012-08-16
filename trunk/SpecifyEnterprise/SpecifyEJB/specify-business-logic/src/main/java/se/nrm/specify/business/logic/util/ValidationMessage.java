package se.nrm.specify.business.logic.util;

import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class ValidationMessage {
    
     
    public final String ENTITY_CAN_NOT_BE_NULL = " can not be null";
    public final String AGENT_PRIMARY_ADDRESS = "Primary address for agent is already exsit";
    public final String ENTITY_CAN_NOT_DELETE = "Entity can not be deleted, because it is not in database";
    public final String ENTITY_DELETE_OK = "Entity is deleted";
    public final String CURRENT_DETERMINATION = "There are more than 1 current determinations in Collectionobject.";
    public final String NO_CURRENT_DETERMINATION = "There is no current determinations in Collectionobject.";

    private static ValidationMessage instance = null;

    public static synchronized ValidationMessage getInstance() {
        if (instance == null) {
            instance = new ValidationMessage();
        }
        return instance;
    }
     
    public String createEntityCanNotBeNullMsg(String entityName, Class<?>... dataClasses) {
        return  createErrorMsg(ENTITY_CAN_NOT_BE_NULL, entityName, dataClasses);
    }
    
    public String createDuplicateAgentRoleMsg(String entityName, String role, String agent, Class dataClass) {
  
        StringBuilder sb = new StringBuilder();
        sb.append("Duplicate ");
        sb.append(dataClass.getSimpleName());
        sb.append(" role - ");
        sb.append(agent);
        sb.append(" - ");
        sb.append(role);
        
        return createErrorMsg(sb.toString(), entityName, dataClass);
    }
    
    public String createDuplicateAccessionAuthorizationPermitMsg(String entityName, SpecifyBean bean, String parent, Class dataClass) {
  
        StringBuilder sb = new StringBuilder();
        sb.append("Duplicate ");
        sb.append(bean);  
        sb.append(" within ");
        sb.append(parent);
        
        return createErrorMsg(sb.toString(), entityName, dataClass);
    }
    
    public String createErrorMsg(String msg, String entityName, Class<?>... dataClasses) { 
        StringBuilder sb = new StringBuilder();
        sb.append(entityName);
        for(Class clazz : dataClasses) {
            sb.append(" - ");
            sb.append(clazz.getSimpleName()); 
        }
        sb.append(" ");
        sb.append(msg);
        return sb.toString();
    }
}

package se.nrm.specify.business.logic.validation;
 
import java.util.HashSet;
import java.util.List;
import java.util.Set; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.business.logic.util.ValidationMessage; 
import se.nrm.specify.datamodel.Accession;
import se.nrm.specify.datamodel.Accessionagent;
import se.nrm.specify.datamodel.Accessionauthorization;
import se.nrm.specify.datamodel.Agent; 
import se.nrm.specify.datamodel.Borrow;
import se.nrm.specify.datamodel.Borrowagent;
import se.nrm.specify.datamodel.Permit; 
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class ValidationUtil {
    
    private static Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    
    public static Validation accessionAgentRoleValidation(List<Accessionagent> aaList, SpecifyBeanId sbId) {
        
        logger.info("accessionAgentRoleValidation");
        
        Set<String> set = new HashSet<String>();
        if (aaList != null) {
            logger.info("aaList : {}", aaList);
            for (Accessionagent aa : aaList) { 
                // Accessionagent role is required
                if(aa.getRole() == null || aa.getRole().length() <= 0) {
                    return new ValidationError(sbId, Status.FieldCanNotBeNull, "Accessionagent role must be specified.");
                } 
                
                // Accessionagent agent is required  
                Class[] clazzes = {Accessionagent.class, Agent.class};
                Validation validation = entityConstrainValidation(aa.getAgent(), Accession.class.getSimpleName(), sbId, clazzes);
                if(validation.isValidationNotOK()) {
                    return validation;
                }
                  
                // Check for duplicate Accessionagent role in Accession
                Agent agent = aa.getAgent(); 
                if(agent != null) {
                    String agentRole = agent.getAgentId() + "_" + aa.getRole();  
                    if (!set.add(agentRole)) {
                        String msg = ValidationMessage.getInstance().createDuplicateAgentRoleMsg(sbId.getClassName(), aa.getRole(), aa.getAgent().toString(), Accessionagent.class);   
                        return new ValidationError(sbId, Status.DuplicateAgentRole, msg); 
                    }
                } 
            } 
        }
        return new ValidationOK();
    } 
    
    
    
    public static Validation accessionAuthorizationPermitValidation(List<Accessionauthorization> aaList, SpecifyBeanId sbId, SpecifyBean parent) {
         
        logger.info("accessionAuthorizationPermitValidation");
        
        Set<String> set = new HashSet<String>();
        if (aaList != null) {
            logger.info("aaList : {}", aaList);
            for (Accessionauthorization aa : aaList) { 
                
                logger.info(" permit isNull : {}", aa.getPermit());
                // Accssionauthorization permit is required
                if(aa.getPermit() == null) {
                    return new ValidationError(sbId, Status.FieldCanNotBeNull, "Accessionauthorization Permit must be specified.");
                }  
                Permit permit = aa.getPermit();
                if(permit.getPermitId() != null) {
                    String permitIdAndNumber = permit.getPermitId() + permit.getPermitNumber();
                    if (!set.add(permitIdAndNumber)) {
                        String msg = ValidationMessage.getInstance().createDuplicateAccessionAuthorizationPermitMsg(parent.getClass().getSimpleName(), permit, parent.toString(), Accessionauthorization.class);
                        return new ValidationError(sbId, Status.DuplicatePermit, msg);
                    }
                } 
            } 
        }
        return new ValidationOK();
    } 
    
    public static Validation borrowAgentRoleValidation(List<Borrowagent> baList, SpecifyBeanId sbId) {
        
        Set<String> set = new HashSet<String>();
        if (baList != null) {
            for (Borrowagent ba : baList) { 
                // Borrowagent role is required
                if(ba.getRole() == null || ba.getRole().length() <= 0) {
                    return new ValidationError(sbId, Status.FieldCanNotBeNull, "Borrowagent role must be specified.");
                } 
                
                // Borrowagent agent is required  
                Class[] clazzes = {Borrowagent.class, Agent.class};
                Validation validation = entityConstrainValidation(ba.getAgent(), Borrow.class.getSimpleName(), sbId, clazzes);
                if(validation.isValidationNotOK()) {
                    return validation;
                }
                  
                // Check for duplicate Accessionagent role in Accession
                Agent agent = ba.getAgent(); 
                if(agent != null) {
                    String agentRole = agent.getAgentId() + "_" + ba.getRole();  
                    if (!set.add(agentRole)) {
                        String msg = ValidationMessage.getInstance().createDuplicateAgentRoleMsg(sbId.getClassName(), ba.getRole(), ba.getAgent().toString(), Borrowagent.class);   
                        return new ValidationError(sbId, Status.DuplicateAgentRole, msg); 
                    }
                } 
            } 
        }
        return new ValidationOK();
    } 
     
    
    public static Validation entityConstrainValidation(SpecifyBean bean, String entityName, SpecifyBeanId sbId, Class<?>... dataClasses) {
           
        if (bean == null) {
            String msg = ValidationMessage.getInstance().createEntityCanNotBeNullMsg(entityName, dataClasses); 
            return new ValidationError(sbId, Status.ConstraintViolation, msg);
        }
        return new ValidationOK();
    }
}

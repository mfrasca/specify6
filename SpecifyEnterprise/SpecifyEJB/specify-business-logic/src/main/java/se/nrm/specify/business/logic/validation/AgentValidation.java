package se.nrm.specify.business.logic.validation;
 
import java.util.List; 
import java.util.Map;
import se.nrm.specify.business.logic.util.ValidationMessage;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Agent; 
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
public class AgentValidation extends BaseValidationRules {
      
    private Agent agent;
     
    public AgentValidation() {
        super(Agent.class);
    }

    public AgentValidation(SpecifyBean bean) {
        super(bean);
        this.agent = (Agent) bean; 
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Agent) bean;
        this.agent = (Agent) bean; 
        
        this.sbId = new SpecifyBeanId(agent); 
    }

    @Override
    public Validation validationBeforeSave() {
        
        logger.info("validationBeforeSave: {} - is new: {}", agent, isNew());
        
        List<Address> addresses = (List<Address>)agent.getAddresses();  
         
        // Check if agent type, if agentType is Other or Group, remove all the addresses associated with this agent.
        if(agent.getAgentType() >= 2) {
            for(Address address : addresses) {
                address.setAgent(null); 
            } 
            agent.setAddresses(null);
        } else { 
            if (addresses != null) {
                int count = 0;
                for (Address a : addresses) {  
                    if (a.getIsPrimary() != null && a.getIsPrimary()) {
                        count++;
                    }
                }
                if (count > 1) {
                    return new ValidationError(sbId, Status.PrimaryAddressForAgent, ValidationMessage.getInstance().AGENT_PRIMARY_ADDRESS);
                }
            }
        }
        return new ValidationOK(sbId, Status.PrimaryAddressForAgent);
    }
  
    @Override
    public boolean isCheckForSaving() {
        return true;
    }
  
    @Override
    public boolean isCheckForDelete() {
        return false;
    }
 
    @Override
    public boolean isOkToDelete() { 
        return agent.getSpecifyUser() == null ? true : false; 
    }
    
    @Override
    public String createMsg(Status validation, ValidationStatus status, String addition, Map map) {  
        return status == ValidationStatus.ERROR ? agent + " can not be deleted, because there are associated SpecifyUsers" : "";
    } 
}

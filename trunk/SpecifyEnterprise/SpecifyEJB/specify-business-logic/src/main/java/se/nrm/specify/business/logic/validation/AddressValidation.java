package se.nrm.specify.business.logic.validation;
 
import java.util.List;
import se.nrm.specify.business.logic.util.ValidationMessage; 
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Agent;  
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
public class AddressValidation extends BaseValidationRules { 
    
    private Address address; 

    public AddressValidation() {
        super(Address.class);
    }

    public AddressValidation(SpecifyBean bean) {
        super(bean);
        this.address = (Address) bean; 
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Address) bean;
        this.address = (Address) bean;
  
        this.sbId = new SpecifyBeanId(String.valueOf(address.getAddressId()), Address.class.getSimpleName()); 
    }

    @Override
    public Validation validationBeforeSave() {
        
        logger.info("validationBeforeSave: {} - is new: {}", address, isNew());
         
        Agent agent = address.getAgent(); 
        if(agent != null) {  
            List<Address> addresses = (List<Address>)agent.getAddresses();
            logger.info("addresses: {}", addresses);
        
            if(addresses != null) {
                int count = 0;
                for(Address a : addresses) {
                    logger.info("is primary: {}", a.getIsPrimary() );
                    if (a.getIsPrimary()) {
                        count++;
                    }
                }
                if(count > 1) {
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
    public boolean isNew() {
        return address.getAddressId() == null ? true : false;
    }
    
    @Override
    public String toString() {
        return "AddressValidation: Address[ addressId=" + address + " ]";
    }
}

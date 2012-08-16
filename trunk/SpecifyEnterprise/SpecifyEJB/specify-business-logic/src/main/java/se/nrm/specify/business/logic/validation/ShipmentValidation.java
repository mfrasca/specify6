package se.nrm.specify.business.logic.validation;

import java.util.Collection;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Agent;
import se.nrm.specify.datamodel.Shipment;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public final class ShipmentValidation extends BaseValidationRules {
     
    private Shipment shipment; 

    public ShipmentValidation() {
        super(Shipment.class);
    }

    public ShipmentValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(final SpecifyBean bean) {
        this.bean = (Shipment) bean;
        this.shipment = (Shipment) bean;
        
        this.sbId = new SpecifyBeanId(shipment.getIdentityString(), Shipment.class.getSimpleName()); 
    }

    @Override
    public Validation validationBeforeSave() { 
          
        Agent agent = shipment.getShippedTo();
        if(agent == null) {
            return new ValidationError(sbId, Status.ShipToAgentNotExist, "Shipment - shippedTo agent is required. ");
        } else {
            Collection<Address> addresses = agent.getAddresses();
            if(addresses == null || addresses.isEmpty()) {
                return new ValidationError(sbId, Status.ShipToAddressNotExist, "Shipment - shippedTo address is required. ");
            }
        }
        return new ValidationOK(sbId, Status.Save);
    }
   
    @Override
    public boolean isCheckForSaving() {
        return true;
    } 
}

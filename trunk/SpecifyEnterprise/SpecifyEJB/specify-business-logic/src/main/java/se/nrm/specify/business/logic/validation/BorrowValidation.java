package se.nrm.specify.business.logic.validation;
  
import java.util.List;
import se.nrm.specify.datamodel.Borrow;
import se.nrm.specify.datamodel.Borrowagent;
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
public class BorrowValidation extends BaseValidationRules {
      
    private Borrow borrow;  

    public BorrowValidation() {
        super(Borrow.class);
    }

    public BorrowValidation(SpecifyBean bean) {
        super(bean);
        this.borrow = (Borrow) bean; 
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Borrow) bean;
        this.borrow = (Borrow) bean;
 
        this.sbId = new SpecifyBeanId(String.valueOf(borrow.getBorrowId()), Borrow.class.getSimpleName()); 
    }
    
    @Override
    public Validation validationBeforeSave() {  
        
        if(borrow.getInvoiceNumber() == null) { 
            return new ValidationError(sbId, Status.FieldCanNotBeNull, borrow + " - invioceNumber must be specified."); 
        } 
        
        Validation validation = isBorrowAgentRolesValid();
        if(validation.isValidationNotOK()) {
            return validation;
        } 
        return new ValidationOK(sbId, Status.Save);
    }
    
    @Override
    public boolean isCheckForSaving() {
        return true;
    }
  
    @Override
    public boolean isNew() {
        return borrow.getBorrowId() == null ? true : false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" : ");
        sb.append(borrow);
        return sb.toString();
    }

    private Validation isBorrowAgentRolesValid() {
        return ValidationUtil.borrowAgentRoleValidation((List<Borrowagent>)borrow.getBorrowAgents(), sbId);
    } 
    
}

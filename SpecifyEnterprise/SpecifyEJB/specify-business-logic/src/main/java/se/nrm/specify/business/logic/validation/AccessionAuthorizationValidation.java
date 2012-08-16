package se.nrm.specify.business.logic.validation;
  
import java.util.List;
import se.nrm.specify.datamodel.Accessionauthorization; 
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
public class AccessionAuthorizationValidation extends BaseValidationRules  {
    
    private Accessionauthorization accessionAuth; 
    private SpecifyBean parent;
    
    public AccessionAuthorizationValidation() {
        super(Accessionauthorization.class);
    }

    public AccessionAuthorizationValidation(SpecifyBean bean) {
        super(bean);
        this.accessionAuth = (Accessionauthorization) bean;  
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Accessionauthorization) bean;
        this.accessionAuth = (Accessionauthorization) bean;  
        
        if(accessionAuth.getAccession() != null) {
            parent = accessionAuth.getAccession();
        } else if(accessionAuth.getRepositoryAgreement() != null) {
            parent = accessionAuth.getRepositoryAgreement();
        }
        
        this.sbId = new SpecifyBeanId(String.valueOf(accessionAuth.getAccessionAuthorizationId()), Accessionauthorization.class.getSimpleName()); 
         
    }

    @Override
    public Validation validationBeforeSave() {
        
        if(accessionAuth.getAccession() != null) {
            List<Accessionauthorization> aas = (List<Accessionauthorization> )accessionAuth.getAccession().getAccessionAuthorizations();
            return ValidationUtil.accessionAuthorizationPermitValidation(aas, sbId, parent); 
            
        }
 
        return new ValidationOK(sbId, Status.Save);
    }
  
    @Override
    public boolean isCheckForDuplication() {
        return false;
    }

    @Override
    public boolean isCheckForSaving() {
        return true;
    }

    @Override
    public boolean isCheckForDelete() {
        return false;
    }
}

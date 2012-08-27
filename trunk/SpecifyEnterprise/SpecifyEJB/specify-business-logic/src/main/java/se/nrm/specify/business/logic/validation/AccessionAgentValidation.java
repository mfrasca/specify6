package se.nrm.specify.business.logic.validation;
 
import java.util.List;
import se.nrm.specify.datamodel.Accession;
import se.nrm.specify.datamodel.Accessionagent;  
import se.nrm.specify.datamodel.Repositoryagreement;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class AccessionAgentValidation extends BaseValidationRules {
    
    private Accessionagent accessionAgent;
     
    public AccessionAgentValidation() {
        super(AccessionAgentValidation.class);
    }
    
    public AccessionAgentValidation(SpecifyBean bean) {   
        super(bean);
        this.accessionAgent = (Accessionagent) bean; 
    }
    
    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Accessionagent) bean;
        this.accessionAgent = (Accessionagent) bean;  
        this.sbId = new SpecifyBeanId(accessionAgent);
    }
    
    @Override
    public Validation validationBeforeSave() {
          
        if(accessionAgent.getAccession() != null) {
            Accession accession = accessionAgent.getAccession();
            List<Accessionagent> aaList = (List<Accessionagent>) accession.getAccessionAgents(); 
            return ValidationUtil.accessionAgentRoleValidation(aaList, sbId);
        } else if(accessionAgent.getRepositoryAgreement() != null) {
            Repositoryagreement agreement = accessionAgent.getRepositoryAgreement();
            List<Accessionagent> aaList = (List<Accessionagent>) agreement.getRepositoryAgreementAgents();
            return ValidationUtil.accessionAgentRoleValidation(aaList, sbId);
        }   
        return new ValidationOK(sbId, Status.Save);
    }
 
    @Override
    public boolean isNew() {
        return accessionAgent.getAccessionAgentId() == null ? true : false;
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
    public boolean isCheckForDuplication() {
        return false;
    }
  
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(" : ");
        sb.append(accessionAgent);
        return sb.toString();
    }
    
}

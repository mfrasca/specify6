package se.nrm.specify.business.logic.validation;
 
import java.util.List;
import se.nrm.specify.datamodel.Accessionagent;
import se.nrm.specify.datamodel.Accessionauthorization;
import se.nrm.specify.datamodel.Division;
import se.nrm.specify.datamodel.Repositoryagreement;
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
public class RepositoryAgreementValidation extends BaseValidationRules {
    
    private final static String ACCESSION_NUMBER = "accessionNumber";
    private final static String PRIMARY_FIELD = "accessionId"; 
    private final static String SPECIAL_FIELD_NAME = "division.divisionId";
    
    private Repositoryagreement repositoryAgreement; 

    public RepositoryAgreementValidation() {
        super(Repositoryagreement.class);
    }

    public RepositoryAgreementValidation(SpecifyBean bean) {
        super(bean);
        this.repositoryAgreement = (Repositoryagreement) bean; 
    }
    
    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Repositoryagreement) bean;
        this.repositoryAgreement = (Repositoryagreement) bean;
        
        this.sbId = new SpecifyBeanId(repositoryAgreement.getIdentityString(), Repositoryagreement.class.getSimpleName());

//        map = new HashMap<String, Object>(); 
//        map.put(Constants.getInstance().CHECK_FIELD_NAME, ACCESSION_NUMBER);
//        map.put(Constants.getInstance().FIELD_VALUE, (accession.getAccessionNumber() == null) ? "" : accession.getAccessionNumber());
//        if(!isNew()) {
//            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
//            map.put(Constants.getInstance().ID, accession.getAccessionId());
//        }
//        
//        map.put(Constants.getInstance().TABLE_NAME, Accession.class.getSimpleName()); 
//        map.put(Constants.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
//        map.put(Constants.getInstance().SPECIAL_FIELD_VALUE, 
//                                        accession.getDivision() == null ? null : accession.getDivision().getDivisionId());
//        
//        
//        
//        
//
//        relatedTables = new ArrayList<String>();
//        relatedTables.add(Collectionobject.class.getSimpleName());
//        map.put(Collectionobject.class.getSimpleName(), "accession"); 
    }

    @Override
    public Validation validationBeforeSave() {
           
        Validation validation = isAccessionAgentRolesValid();
        if(validation.isValidationNotOK()) {
            return validation;
        }
        
        validation = isAccessionAuthorizationPermitValid();
        if(validation.isValidationNotOK()) {
            return validation;
        }
        
        validation = isRepositoryNumberValid();
        if(validation.isValidationNotOK()) {
            return validation;
        }  
        return new ValidationOK(sbId, Status.Save);
    }
    
    @Override
    public boolean isCheckForSaving() {
        return true;
    }
    
     private Validation isAccessionAgentRolesValid() {    
        return ValidationUtil.accessionAgentRoleValidation((List<Accessionagent>)repositoryAgreement.getRepositoryAgreementAgents(), sbId);  
    }
   
    private Validation isAccessionAuthorizationPermitValid() {
        return ValidationUtil.accessionAuthorizationPermitValidation((List<Accessionauthorization>)repositoryAgreement.getRepositoryAgreementAuthorizations(), sbId, repositoryAgreement);
    }
    
    
    private Validation isRepositoryNumberValid() {
        
        // RepositoryAgreementNumber is required
        if(repositoryAgreement.getRepositoryAgreementNumber() == null || repositoryAgreement.getRepositoryAgreementNumber().length() <= 0) {
            return new ValidationError(sbId, Status.FieldCanNotBeNull, "accessionNumber must be specified.");
        }
        
        // Division is required in Accession 
        return ValidationUtil.entityConstrainValidation(repositoryAgreement.getDivision(), repositoryAgreement.getClass().getSimpleName(), sbId, Division.class); 
    }   
}

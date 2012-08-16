package se.nrm.specify.business.logic.validation;

import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.List; 
import se.nrm.specify.specify.data.jpa.util.Constants; 
import se.nrm.specify.datamodel.Accession;
import se.nrm.specify.datamodel.Accessionagent; 
import se.nrm.specify.datamodel.Accessionauthorization;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.Division;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public final class AccessionValidation extends BaseValidationRules {

    private final static String ACCESSION_NUMBER = "accessionNumber";
    private final static String PRIMARY_FIELD = "accessionId"; 
    private final static String SPECIAL_FIELD_NAME = "division.userGroupScopeId";
    
    private Accession accession; 

    public AccessionValidation() {
        super(Accession.class);
    }

    public AccessionValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Accession) bean;
        this.accession = (Accession) bean;
        
        this.sbId = new SpecifyBeanId(accession.getIdentityString(), Accession.class.getSimpleName());

        map = new HashMap<String, Object>();  
        map.put(ACCESSION_NUMBER, (accession.getAccessionNumber() == null) ? "" : accession.getAccessionNumber());
        if(!isNew()) {
            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(Constants.getInstance().ID, accession.getAccessionId());
        }
        
        map.put(Constants.getInstance().TABLE_NAME, Accession.class.getSimpleName()); 
        map.put(Constants.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(Constants.getInstance().SPECIAL_FIELD_VALUE, 
                                        accession.getDivision() == null ? null : accession.getDivision().getUserGroupScopeId());
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(ACCESSION_NUMBER);
        
        relatedTables = new ArrayList<String>();
        relatedTables.add(Collectionobject.class.getSimpleName());
        
        map.put(Collectionobject.class.getSimpleName(), "accession"); 
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
        
        validation = isAccessionNumberValid();
        if(validation.isValidationNotOK()) {
            return validation;
        }  
        return new ValidationOK(sbId, Status.Save);
    }
  
    @Override
    public boolean isCheckForDuplication() {
        return true;
    }

    @Override
    public boolean isCheckForSaving() {
        return true;
    }

    @Override
    public boolean isCheckForDelete() {
        return true;
    } 

    private Validation isAccessionAgentRolesValid() {    
        return ValidationUtil.accessionAgentRoleValidation((List<Accessionagent>)accession.getAccessionAgents(), sbId);  
    }
   
    private Validation isAccessionAuthorizationPermitValid() {
        return ValidationUtil.accessionAuthorizationPermitValidation((List<Accessionauthorization>)accession.getAccessionAuthorizations(), sbId, accession);
    }
    
    
    private Validation isAccessionNumberValid() {
        
        // Accessionnumber is required
        if(accession.getAccessionNumber() == null || accession.getAccessionNumber().length() <= 0) {
            return new ValidationError(sbId, Status.FieldCanNotBeNull, "accessionNumber must be specified.");
        }
        
        // Division is required in Accession 
        return ValidationUtil.entityConstrainValidation(accession.getDivision(), accession.getClass().getSimpleName(), sbId, Division.class); 
    }   
}

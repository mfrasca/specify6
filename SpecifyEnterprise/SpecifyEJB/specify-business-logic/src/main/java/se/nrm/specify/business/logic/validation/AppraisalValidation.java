package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import se.nrm.specify.datamodel.Appraisal;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;

/**
 *
 * @author idali
 */
public class AppraisalValidation extends BaseValidationRules {
    
    private final static String APPRAISAL_NUMBER = "appraisalNumber";
    private final static String PRIMARY_FIELD = "appraisalId"; 
    private final static String SPECIAL_FIELD_NAME = "accession.accessionId";
    
    private Appraisal appraisal; 

    public AppraisalValidation() {
        super(Appraisal.class);
    }

    public AppraisalValidation(SpecifyBean bean) {
        super(bean);
        this.appraisal = (Appraisal) bean; 
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Appraisal) bean;
        this.appraisal = (Appraisal) bean;

        map = new HashMap<String, Object>();  
        map.put(APPRAISAL_NUMBER, (appraisal.getAppraisalNumber() == null) ? "" : appraisal.getAppraisalNumber()); 
        if(!isNew()) {
            map.put(ConstantsClass.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(ConstantsClass.getInstance().ID, appraisal.getAppraisalId());
        }
        
        map.put(ConstantsClass.getInstance().TABLE_NAME, Appraisal.class.getSimpleName()); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(ConstantsClass.getInstance().SPECIAL_FIELD_VALUE, 
                                        appraisal.getAccession() == null ? null : appraisal.getAccession().getAccessionId()); 
        
        
        this.sbId = new SpecifyBeanId(appraisal);

        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(APPRAISAL_NUMBER);
        
        relatedTables = new ArrayList<String>();
        relatedTables.add(Collectionobject.class.getSimpleName());
        map.put(Collectionobject.class.getSimpleName(), "appraisal"); 
    }

    @Override
    public Validation validationBeforeSave() { 
        
        boolean isValid = true;
        List<String> msgs = new ArrayList<String>();
        if(appraisal.getAppraisalNumber() == null) {
            isValid = false;
            msgs.add("AppraisalNumber must be specified."); 
        } 
            
        if(appraisal.getAgent() == null) {
            isValid = false;
            msgs.add("Agent must be specified."); 
        }
        
        if(appraisal.getAppraisalDate() == null) {
            isValid = false;
            msgs.add("Appraisaldate must be specified.");
        } 
        return isValid ? new ValidationOK(sbId, Status.Save) : new ValidationError(sbId, Status.Save, msgs);
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
    
}

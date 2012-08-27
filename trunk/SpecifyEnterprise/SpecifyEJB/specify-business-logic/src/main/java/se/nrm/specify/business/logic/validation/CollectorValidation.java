package se.nrm.specify.business.logic.validation;
 
import java.util.ArrayList;
import java.util.List;
import se.nrm.specify.datamodel.Collector; 
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public final class CollectorValidation extends BaseValidationRules {
     
    
    private Collector collector; 

    public CollectorValidation() {
        super(Collector.class);
    }

    public CollectorValidation(final SpecifyBean bean) {
        super(bean);
        this.collector = (Collector) bean; 
        initialize(bean);
    }

    @Override
    public void initialize(final SpecifyBean bean) {
        this.bean = (Collector) bean;
        this.collector = (Collector) bean;
        
        this.sbId = new SpecifyBeanId(collector); 
    }

    @Override
    public Validation validationBeforeSave() { 
         
        boolean isValid = true;
        List<String> msgs = new ArrayList<String>();  
        if(collector.getDivision() == null) {
            isValid = false;
            msgs.add("Division must be specified."); 
        }
        
        if(collector.getAgent() == null) {
            isValid = false;
            msgs.add("Agent must be specified.");
        }
        
        if(collector.getCollectingEvent() == null) {
            isValid = false;
            msgs.add("CollectingEvent must be specified.");
        }
        
        return isValid ? new ValidationOK(sbId, Status.Save) : new ValidationError(sbId, Status.FieldCanNotBeNull, msgs);
    }
  
    @Override
    public boolean isCheckForSaving() {
        return true;
    } 
    
}

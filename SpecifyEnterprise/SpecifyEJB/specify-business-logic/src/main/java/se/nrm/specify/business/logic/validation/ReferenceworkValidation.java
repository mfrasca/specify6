package se.nrm.specify.business.logic.validation;
 
import se.nrm.specify.datamodel.Referencework;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class ReferenceworkValidation extends BaseValidationRules {
    
    private Referencework rw; 

    public ReferenceworkValidation() {
        super(Referencework.class);
    }

    public ReferenceworkValidation(SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public final void initialize(SpecifyBean bean) {
        this.bean = (Referencework) bean;
        this.rw = (Referencework) bean;
        
        this.sbId = new SpecifyBeanId(rw.getIdentityString(), Referencework.class.getSimpleName());

//        map = new HashMap<String, Object>();    
//        relatedTables = new ArrayList<String>();
//        relatedTables.add(Referencework.class.getSimpleName());
//        
//        map.put(Journal.class.getSimpleName(), "referencework"); 
    }
 
//    @Override
//    public boolean isCheckForDelete() {
//        return true;
//    } 
}

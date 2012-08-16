package se.nrm.specify.business.logic.validation;
 
import java.util.ArrayList;
import java.util.HashMap; 
import se.nrm.specify.datamodel.Discipline; 
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.Constants;

/**
 *
 * @author idali
 */
public final class DisciplineValidation extends BaseValidationRules {
    
    private final static String NAME = "name";
    private final static String PRIMARY_FIELD = "userGroupScopeId"; 
    private final static String SPECIAL_FIELD_NAME = "division.userGroupScopeId";
    
    private Discipline discipline; 

    public DisciplineValidation() {
        super(Discipline.class);
    }

    public DisciplineValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(final SpecifyBean bean) {
        this.bean = (Discipline) bean;
        this.discipline = (Discipline) bean;
        
        this.sbId = new SpecifyBeanId(discipline.getIdentityString(), Discipline.class.getSimpleName());

        map = new HashMap<String, Object>();  
        map.put(NAME, (discipline.getName() == null) ? "" : discipline.getName());
       
        if(!isNew()) {
            map.put(Constants.getInstance().PRIMARY_FIELD_NAME, PRIMARY_FIELD);
            map.put(Constants.getInstance().ID, discipline.getUserGroupScopeId());
        }
        
        map.put(Constants.getInstance().TABLE_NAME, Discipline.class.getSimpleName()); 
        map.put(Constants.getInstance().SPECIAL_FIELD, SPECIAL_FIELD_NAME); 
        map.put(Constants.getInstance().SPECIAL_FIELD_VALUE, 
                                        discipline.getDivision() == null ? null : discipline.getDivision().getUserGroupScopeId());
          
        duplicationCheckFields = new ArrayList<String>();
        duplicationCheckFields.add(NAME); 
    }

 
    @Override
    public boolean isCheckForDuplication() {
        return true;
    } 
}

package se.nrm.specify.business.logic.validation;

import java.util.ArrayList;
import java.util.HashMap;
import se.nrm.specify.datamodel.Journal;
import se.nrm.specify.datamodel.Referencework;
import se.nrm.specify.datamodel.SpecifyBean; 

/**
 *
 * @author idali
 */
public final class JournalValidation extends BaseValidationRules {
      
    
    private Journal journal; 

    public JournalValidation() {
        super(Journal.class);
    }

    public JournalValidation(final SpecifyBean bean) {
        super(bean);
        initialize(bean);
    }

    @Override
    public void initialize(SpecifyBean bean) {
        this.bean = (Journal) bean;
        this.journal = (Journal) bean;
        
        this.sbId = new SpecifyBeanId(journal.getIdentityString(), Journal.class.getSimpleName());

        map = new HashMap<String, Object>();    
        relatedTables = new ArrayList<String>();
        relatedTables.add(Referencework.class.getSimpleName());
        
        map.put(Referencework.class.getSimpleName(), "journal"); 
    }
 
    @Override
    public boolean isCheckForDelete() {
        return true;
    }  
}

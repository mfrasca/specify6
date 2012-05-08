package se.nrm.specify.business.logic.specify;

import se.nrm.specify.specify.data.jpa.SpecifyDao;

/**
 *
 * @author ida_ho_99
 */
public interface SpecifyLogic {
    
    public SpecifyDao getDao();
    
    public String getDNASequenceList();
    
}

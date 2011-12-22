package se.nrm.specify.business.logic.specify;

import javax.ejb.EJB;
import javax.ejb.Stateless; 
import se.nrm.specify.specify.data.jpa.SpecifyDao;

/**
 *
 * @author ida_ho_99
 */
@Stateless
public class SpecifyLogicImpl implements SpecifyLogic {
    
    @EJB
    private SpecifyDao dao;
    
    public SpecifyLogicImpl() {
        
    }
    
    public SpecifyLogicImpl(SpecifyDao dao) {
        this.dao = dao;
    }

    public SpecifyDao getDao() {
        return dao;
    }
    
    
}

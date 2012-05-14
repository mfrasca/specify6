package se.nrm.specify.business.logic.smtp;

import se.nrm.specify.datamodel.DataWrapper; 

/**
 *
 * @author ida_ho_99
 */ 
public interface SMTPLogic {
    
    public void saveSMTPBatchData(DataWrapper wrapper, String userid);
}

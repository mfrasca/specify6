package se.nrm.specify.business.logic.smtp;

import java.util.List;
import se.nrm.specify.datamodel.DataWrapper; 
import se.nrm.specify.datamodel.Locality;

/**
 *
 * @author ida_ho_99
 */ 
public interface SMTPLogic {
    
    public DataWrapper getDeterminationsData(String taxonNode, String highestChildNode, String CollectionCode);
    public String getTaxonAndParents(String taxonname);
    public List<Locality> getLocalityByCollectionCode(String collectionCode);
    public void saveSMTPBatchData(DataWrapper wrapper, String userid);
}

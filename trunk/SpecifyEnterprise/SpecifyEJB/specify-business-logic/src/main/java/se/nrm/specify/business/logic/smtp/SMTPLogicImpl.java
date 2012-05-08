package se.nrm.specify.business.logic.smtp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Agent;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.Collection;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Preparation;
import se.nrm.specify.datamodel.Preptype;
import se.nrm.specify.datamodel.Taxon;
import se.nrm.specify.specify.data.jpa.SpecifyDao;

/**
 *
 * @author ida_ho_99
 */
@Stateless
public class SMTPLogicImpl implements SMTPLogic {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @EJB
    private SpecifyDao dao;

    public SMTPLogicImpl() {
    }

    public SMTPLogicImpl(SpecifyDao dao) {
        this.dao = dao;
    }
 
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void saveSMTPBatchData(DataWrapper wrapper) {
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//        Agent agent = dao.getById(1, Agent.class);
        
        Agent agent = dao.getByReference(1, Agent.class); 
        Preptype pretype = dao.getByReference(24, Preptype.class);
        
        Collectingevent event = wrapper.getEvent();
        String collectionCode = wrapper.getCollectionCode();

        for (String taxonName : wrapper.getList()) {
              
            Map<String, String> map = new HashMap<String, String>(); 
            map.put("fullName", taxonName);
             
            Taxon taxon = (Taxon)dao.getEntityByNamedQuery("Taxon.findByFullName", map); 

            if (taxon != null) {

                // create Collectionobject
                Collectionobject newObject = new Collectionobject();
                newObject.setTimestampCreated(timestamp);
                newObject.setCatalogedDate(timestamp);
                newObject.setCatalogedDatePrecision(Short.valueOf("1"));
                newObject.setDescription("test desc");
                newObject.setCreatedByAgentID(agent);
                newObject.setRemarks("test remark");
                newObject.setCollectingEventID(event);
                newObject.setCatalogerID(agent);

                Collectionobject lastObject = dao.getLastCollectionobjectByGroup(collectionCode);                // get last catalognumber from specific group
                Collection collection = lastObject.getCollectionID();

                int collectionMemberId = lastObject.getCollectionMemberID();
                newObject.setCollectionMemberID(collectionMemberId);
                newObject.setCatalogNumber(getNewCatalogNumber(lastObject.getCatalogNumber()));
                newObject.setCollectionID(collection);

                // Create preparation
                Preparation preparation = new Preparation();
                preparation.setTimestampCreated(timestamp);
                preparation.setCollectionMemberID(collectionMemberId);
                preparation.setDescription("SMTP unsorted trap sample");
                preparation.setPreparedDate(timestamp);
                preparation.setPreparedDatePrecision(Short.valueOf("1"));
                preparation.setPrepTypeID(pretype);
                preparation.setCreatedByAgentID(agent);
                preparation.setCollectionObjectID(newObject); 

                List<Preparation> preparations = new ArrayList<Preparation>();
                preparations.add(preparation);
                newObject.setPreparationCollection(preparations);

                // create Determination
                Determination determination = new Determination();
                determination.setTimestampCreated(timestamp);
                determination.setIsCurrent(true);
                determination.setCreatedByAgentID(agent);
                determination.setTaxonID(taxon);
                determination.setPreferredTaxonID(taxon);
                determination.setCollectionMemberID(collectionMemberId);
                determination.setCollectionObjectID(newObject);

                // Save determination
                dao.createEntity(determination);
            }
        }
        updateCollectionobjectYesNo2(event, wrapper.getNumSortedVials());
    }

    private void updateCollectionobjectYesNo2(Collectingevent event, int count) {

        logger.info("updateCollectionobjectYesNo2 - collectingevent: {}", event);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (count > 0) {
            List<Collectionobject> objects = dao.getCollectionobjectByCollectingEventAndYesno2(event);

            for (int i = 0; i < count; i++) {
                if (i < objects.size()) {
                    Collectionobject object = objects.get(i);
                    object.setYesNo2(Boolean.TRUE);
                    object.setTimestampModified(timestamp);
                    dao.editEntity(object);
                }
            }
        }
    }

    /**
     * create new catalogNumber 
     * @param catalogNumber
     * 
     * @return String- catalogNumner
     *  
     */
    private String getNewCatalogNumber(String catalogNumber) {

        String prefix = catalogNumber.substring(0, 7);
        int number = Integer.parseInt(catalogNumber.substring(7));

        int newNumber = 1000000000 + number + 1;
        String strNumber = String.valueOf(newNumber).substring(1);
        return prefix.concat(strNumber);
    }
}

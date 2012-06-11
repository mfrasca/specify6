package se.nrm.specify.business.logic.smtp;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import se.nrm.specify.datamodel.Locality;
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

    public List<Locality> getLocalityByCollectionCode(String collectionCode) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT lc FROM Locality lc ");
        sb.append("JOIN lc.collectingevents ce ");
        sb.append("JOIN ce.collectionObjects co ");
        sb.append("WHERE lc = ce.locality ");
        sb.append("AND ce = co.collectingEvent ");
        sb.append("AND co.collection.code= '");
        sb.append(collectionCode);
        sb.append("' ");
        sb.append("AND lc.longitude1 IS NOT NULL ");
        sb.append("AND lc.latitude1 IS NOT NULL");

        String entity = Locality.class.getSimpleName();

        List<String> fields = new ArrayList<String>();
        fields.add("localityName");
        fields.add("longitude1");
        fields.add("latitude1");
        fields.add("lat1text");
        fields.add("long1text");
        fields.add("localityId");
        
        return (List<Locality>) dao.getListByJPQLByFetchGroup(entity, sb.toString(), fields);
    }

    public DataWrapper getDeterminationsData(String taxonNode, String highestChildNode, String collectionCode) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT d FROM Determination AS d where d.collectionObject.collection.code = '");
        queryBuilder.append(collectionCode);
        queryBuilder.append("' AND d.taxon.nodeNumber BETWEEN ");
        queryBuilder.append(taxonNode);
        queryBuilder.append(" AND ");
        queryBuilder.append(highestChildNode);
        queryBuilder.append(" and d.isCurrent = true");


        String entity = Determination.class.getSimpleName();

        List<String> fields = new ArrayList<String>();
        fields.add("taxon.fullName");
        fields.add("collectionObject.collectionObjectId");
//        fields.add("collectionObject.collectingEvent");
        fields.add("collectionObject.collectingEvent.collectingEventId");
        fields.add("collectionObject.collectingEvent.collectingEventId");
        fields.add("collectionObject.collectingEvent.locality.localityId");

        List<Determination> determinations = (List<Determination>) dao.getListByJPQLByFetchGroup(entity, queryBuilder.toString(), fields);


        List<Integer> localityIds = new ArrayList<Integer>();
        List<Integer> eventIds = new ArrayList<Integer>();
        List<String> taxonNames = new ArrayList<String>();

        if (determinations != null) {
            for (Determination determination : determinations) {
                Taxon taxon = determination.getTaxon();
                if (taxon != null) {
                    taxonNames.add(taxon.getFullName());
                }

                Collectionobject collectionobject = determination.getCollectionObject();
                if (collectionobject != null) {
                    Collectingevent event = determination.getCollectionObject().getCollectingEvent();
                    if (event != null) {
                        eventIds.add(event.getCollectingEventId());
                        Locality lc = event.getLocality();
                        if (lc != null) {
                            localityIds.add(lc.getLocalityId());
                        }
                    }
                }

            }
        }
        int totalEvents = new ArrayList(new HashSet(eventIds)).size();
        int totalTraps = new ArrayList(new HashSet(localityIds)).size();
        return new DataWrapper(taxonNames, String.valueOf(totalEvents), String.valueOf(totalTraps));
    }

    public String getTaxonAndParents(String taxonname) {

        Taxon taxon = dao.getTaxonAndParents(taxonname);

        int node = taxon.getNodeNumber();
        int highestChild = taxon.getHighestChildNodeNumber();

        StringBuilder taxonSB = new StringBuilder();
        if (taxon != null) {
            Taxon parent = taxon.getParent();
            if (parent != null) {
                Taxon gparent = parent.getParent();
                if (gparent != null) {
                    Taxon ggparent = gparent.getParent();
                    if (ggparent != null) {
                        Taxon gggparent = ggparent.getParent();
                        if (gggparent != null) {
                            taxonSB.append(gggparent.getFullName());
                            taxonSB.append(" : ");
                        }
                        taxonSB.append(ggparent.getFullName());
                        taxonSB.append(" : ");
                    }
                    taxonSB.append(gparent.getFullName());
                    taxonSB.append(" : ");
                }
                taxonSB.append(parent.getFullName());
                taxonSB.append(" : ");
            }
            taxonSB.append(taxon.getFullName());
        }

        taxonSB.append("=");
        taxonSB.append(node);
        taxonSB.append(":");
        taxonSB.append(highestChild);
        return taxonSB.toString();
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void saveSMTPBatchData(DataWrapper wrapper, String userid) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Map<String, Object> conditionmap = new HashMap<String, Object>();
        conditionmap.put("specifyUserID", Integer.parseInt(userid));

        Agent agent = (Agent) dao.getEntityByNamedQuery("Agent.findBySpecifyuserid", conditionmap);

        Preptype pretype = dao.getByReference(24, Preptype.class);                              // TODO hard coded for smtp

        Collectingevent event = wrapper.getEvent();
        event = dao.getByReference(event.getCollectingEventId(), Collectingevent.class);
        String collectionCode = wrapper.getCollectionCode();

        Collectionobject lastObject = dao.getLastCollectionobjectByGroup(collectionCode);                // get last catalognumber from specific group
        Collection collection = lastObject.getCollection();
        int collectionMemberId = lastObject.getCollectionMemberId();

        String catalogNumber = lastObject.getCatalogNumber();
        boolean catalogNumberExist = true;

        String newCatalogNumber = catalogNumber;
        Taxon taxon = null;
        List<String> taxonlist = new ArrayList<String>();

        for (String taxonName : wrapper.getList()) { 
            if (!taxonlist.contains(taxonName)) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("fullName", taxonName);
                taxon = (Taxon) dao.getEntityByNamedQuery("Taxon.findByFullName", map);
                taxonlist.add(taxonName);
            }
            if (taxon != null) {

                
                // create Collectionobject
                Collectionobject newObject = new Collectionobject();
                newObject.setTimestampCreated(timestamp);
                newObject.setCatalogedDate(timestamp);
                newObject.setCatalogedDatePrecision(Short.valueOf("1"));
//                newObject.setDescription("test desc");
                newObject.setCreatedByAgent(agent);
//                newObject.setRemarks("test remark");
                newObject.setCollectingEvent(event);
                newObject.setCataloger(agent);

                newObject.setCollectionMemberId(collectionMemberId);
 
                while (catalogNumberExist) {
                    newCatalogNumber = getNewCatalogNumber(newCatalogNumber);
                    catalogNumberExist = dao.isCatalogNumberExist(newCatalogNumber); 
                }
                newObject.setCatalogNumber(newCatalogNumber);
                newObject.setCollection(collection);

                // Create preparation
                Preparation preparation = new Preparation();
                preparation.setTimestampCreated(timestamp);
                preparation.setCollectionMemberId(collectionMemberId);
                preparation.setDescription("SMTP unsorted trap sample");
                preparation.setPreparedDate(timestamp);
                preparation.setPreparedDatePrecision(Short.valueOf("1"));
                preparation.setPrepType(pretype);
                preparation.setCreatedByAgent(agent);
                preparation.setCollectionObject(newObject);

                List<Preparation> preparations = new ArrayList<Preparation>();
                preparations.add(preparation);
                newObject.setPreparations(preparations);
 

                // create Determination
                Determination determination = new Determination();
                determination.setTimestampCreated(timestamp);
                determination.setIsCurrent(true);
                determination.setCreatedByAgent(agent);
                determination.setTaxon(taxon);
                determination.setPreferredTaxon(taxon);
                determination.setCollectionMemberId(collectionMemberId);
                determination.setCollectionObject(newObject);

                List<Determination> determinations = new ArrayList<Determination>();
                determinations.add(determination);

                newObject.setDeterminations(determinations);
                // Save collectionobject
                dao.createEntity(newObject);
                catalogNumberExist = true;
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

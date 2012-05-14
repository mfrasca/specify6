package se.nrm.specify.data.service.client;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import se.nrm.specify.datamodel.*;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class SpecifyClient {

    private static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    private static WebResource service;
    private static Gson gson;

    public static void main(String[] args) {

        gson = new Gson();

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        service = client.resource(getBaseURI());

//                testGetEntity();
//        codetest();
//        viewmapping();

//        testGetSynomy();


        //        testCreateEntity();
        //        testCreateGenericEntity();
        //        testDeleteEntity();
        //        testDeleteGenericEntity();
//        testUpdateEntity();
        //        testGetLocalitiesByCollectionCode();
        //        testUpdateCollectionobjectEntity();

        //        testGetAll();
        //        testUpdateCollectionobjectEntity();


        //        testGetPartialObjects();
//        testQry();
//        testQry1();
//        testFetchGroup();

//        testGetEntityByNamedQuery();
        //        testGetTextListByJPQL();
//                testGetAllEntitiesByNamedQuery();
        //        testGetEntitiesByJPQL(); 

//        testGetEntityById();
        testUIView();

    }

    private static void testUIView() {
        
        String discipline = "fish";
        String view = "CollectionObject"; 
        
        
        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("catalogNumber", "NHRS-GULI000000970");
        
        String xml = service.path("search").path("uidata").path(discipline).path(view).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml: " + xml);
    }
    
    private static void testGetEntityById() {

        String entity = Agent.class.getName();
        String id = "1";

        // JSON
        Agent agent = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(Agent.class);
        List<Address> addresses = (List<Address>) agent.getAddressCollection();
        System.out.println("size: " + addresses.size());
    }

    private static void testQry1() {

        List<String> fetchFields = new ArrayList<String>();
        fetchFields.add("geneSequence");
        fetchFields.add("collectionObjectID.collectionObjectID");

        String classname = Dnasequence.class.getName();

        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.put(classname, fetchFields);

        SpecifyBeanWrapper wrapper = service.path("search").path("all").path("bygroup").path(classname).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        for (SpecifyBean bean : wrapper.getBeans()) {
            Dnasequence dna = (Dnasequence) bean;
            System.out.println("dna: " + dna.getCollectionObjectID().getCollectionObjectID());
        }
    }

    private static void testQry() {

        String fieldname = "catalogNumber";
        String fieldvalue = "NHRS-COLE000008661";
//        String fieldvalue = "SMTPINV000007096";
        String classname = "Collectionobject"; 
 
        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT o FROM ");
        jpqlSB.append(classname);
        jpqlSB.append(" o where o.");
        jpqlSB.append(fieldname);
        jpqlSB.append(" = '");
        jpqlSB.append(fieldvalue);
        jpqlSB.append("'");
 
//
        List<String> fields = new ArrayList<String>();
//        fields.add("collectionObjectID");
        fields.add("timestampModified");
//        fields.add("catalogNumber");
//        fields.add("remarks");
//        fields.add("collectingEventID.stationFieldNumber");
//        fields.add("collectingEventID.startDate");
//        fields.add("collectingEventID.localityID.localityName");
//        fields.add("collectingEventID.collectorID.agentID.firstName");
//        fields.add("collectingEventID.collectorID.agentID.lastName");
//        fields.add("collectingEventID.localityID.geographyID.fullName");
//        fields.add("collectionID.collectionName");
//        fields.add("collectionID.code");
//        fields.add("collectingEventID.localityID.geographyID.geographyTreeDefItemID.name");
//        fields.add("determinationCollection.preferredTaxonID.fullName");
//        fields.add("determinationCollection.isCurrent");
//        fields.add("determinationCollection.typeStatusName");
//        fields.add("determinationCollection.taxonID.fullName");
//        fields.add("determinationCollection.determinerID.addressCollection.address");
//        fields.add("catalogerID.addressCollection.address");
//        fields.add("preparationCollection.collectionMemberID");


  
        MultivaluedMap queryParams = new MultivaluedMapImpl();
  
        List<String> jpqlarry = new ArrayList<String>();
        jpqlarry.add(jpqlSB.toString());

        queryParams.put("jpql", jpqlarry);
//        queryParams.put(classname, fields);


        String bean = service.path("search").path("list").path("bygroup").path(classname).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("bean: " + bean);

//        
//        SpecifyBeanWrapper wrapper = service.path("search").path("list").path("bygroup").path(entity).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
//        System.out.println("list: " + wrapper.getBeans());

//        Collectionobject co = service.path("search").path("list").path("bygrouptest").path(entity).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(Collectionobject.class);
//        System.out.println("bean: " + co);
//
//        System.out.println("viersion: " + co.getVersion());
//        System.out.println("created: " + co.getTimestampCreated());
//
//        co.setRemarks("test");
//        Agent agent = co.getCollectingEventID().getCollectorID().getAgentID();
//        agent.setRemarks("this is a test");
//        
//        Collector col = co.getCollectingEventID().getCollectorID();
//        col.setAgentID(agent);
//        Collectingevent ce = co.getCollectingEventID();
//        ce.setCollectorID(col);
//        ce.setRemarks("this is a test");
//        
//        co.setCollectingEventID(ce);
//
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException ex) { 
//        }
//        
//        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(co);
//         
//        ClientResponse response = service.path("updateentity").put(ClientResponse.class, wrapper);
//        System.out.println(response);
//        


    }

    private static void testGetEntityByNamedQuery() {

        MultivaluedMap queryParams = new MultivaluedMapImpl();

        queryParams.add("namedquery", "Specifyuser.findByName");
        queryParams.add("name", "idali");


        SpecifyBeanWrapper wrapper = service.path("search").path("entity").path("namedquery").queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);

        Specifyuser user = (Specifyuser) wrapper.getBean();

        System.out.println("g: {}" + user);
        System.out.println("name: " + user.getName());

    }

    private static void viewmapping() {
    }

    private static void codetest() {


        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT co FROM Collectionobject AS co JOIN co.determinationCollection AS d "
                + "where d.taxonID.taxonID = 164465  AND d.isCurrent = true");

        String entity = Collectionobject.class.getSimpleName();

        MultivaluedMap queryParams = new MultivaluedMapImpl();

        queryParams.add("jpql", jpqlSB.toString());
        queryParams.add("fields1", "collectionObjectID");
        queryParams.add("fields2", "catalogNumber");
        queryParams.add("fields3", "remarks");
        queryParams.add("fields4", "collectingEventID.stationFieldNumber");
        queryParams.add("fields5", "collectingEventID.localityID.localityID");
        queryParams.add("fields6", "collectingEventID.localityID.localityName");
        queryParams.add("fields9", "collectingEventID.collectorID.agentID.firstName");
        queryParams.add("fields10", "collectingEventID.collectorID.agentID.lastName");
        queryParams.add("fields11", "collectingEventID.localityID.geographyID");
// 
//        
        SpecifyBeanWrapper wrapper = service.path("search").path("list").path("bygroup").path(entity).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);


//        SpecifyBeanWrapper wrapper = service.path("search").path("entitiesbyjpql").path(jpqlSB.toString()).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);

        System.out.println("list: " + wrapper.getBeans());

        for (SpecifyBean bean : wrapper.getBeans()) {
            Collectionobject c = (Collectionobject) bean;
            System.out.println("co: " + c.getCatalogNumber());
            System.out.println("remark: " + c.getRemarks());
            System.out.println("event: " + c.getCollectingEventID().getStationFieldNumber());
            System.out.println("locality: " + c.getCollectingEventID().getLocalityID().getLocalityName());

            String name = (c.getCollectingEventID().getCollectorID() != null)
                    ? c.getCollectingEventID().getCollectorID().getAgentID().getFirstName() + " "
                    + c.getCollectingEventID().getCollectorID().getAgentID().getLastName() : "";
            System.out.println("Name: " + name);
            System.out.println("collectorid: " + c.getCollectingEventID().getCollectorID());

            System.out.println("country: " + c.getCollectingEventID().getLocalityID().getGeographyID().getFullName());
            System.out.println("country: " + c.getCollectingEventID().getLocalityID().getGeographyID().getCountry().getName());
            System.out.println("country: " + c.getCollectingEventID().getLocalityID().getGeographyID().getName());
        }


//        String determinationClass = Determination.class.getSimpleName();  
//        MultivaluedMap queryParams = new MultivaluedMapImpl(); 
//
//        queryParams = new MultivaluedMapImpl();
//        queryParams.add("jpql", jpqlSB.toString());
//
//        queryParams.add("fields1", "determinationID"); 
//        queryParams.add("fields2", "typeStatusName"); 
//        queryParams.add("fields3", "taxonID.fullName"); 
//        queryParams.add("fields4", "taxonID.commonName"); 
//        queryParams.add("fields5", "collectionObjectID.catalogNumber"); 

//        SpecifyBeanWrapper wrapper = service.path("search").path("list").path("bygroup").path(determinationClass).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
//
//        List<Determination> list = (List<Determination>)wrapper.getBeans();
//        logger.info("list: {}", list);
//        
//        
//        
//        
//        
//        for(Determination d : list) { 
//            logger.info("Collectionobject: {}", d.getCollectionObjectID());
//            
//            logger.info("catalognumber: {}", d.getCollectionObjectID().getCatalogNumber());
//        }
    }

    private static void testGetSynomy() {
        String id = "17830";


//        String entity = Collectionobject.class.getName();
//        String id = "205180";
//
////        // JSON
////        String json = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(String.class);
////        System.out.println("json: " + json);

        //XML
//        String xml = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_XML).get(String.class);
//        System.out.println("xml: " + xml);
//
        DataWrapper wrapper = service.path("search").path("synomys").path(id).accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);
        System.out.println("bean:  " + wrapper.getList());
    }

    private static void testFetchGroup() {

        String taxonClass = Taxon.class.getSimpleName();
        String taxonname = "fullName";
        String value = "Schistocerca";
        MultivaluedMap queryParams = new MultivaluedMapImpl();

        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT t FROM Taxon t WHERE t.");
        jpqlSB.append(taxonname);
        jpqlSB.append("='");
        jpqlSB.append(value);
        jpqlSB.append("'");

        queryParams.add("jpql", jpqlSB.toString());
        queryParams.add("fields1", "nodeNumber");
        queryParams.add("fields2", "highestChildNodeNumber");

        Taxon taxon = service.path("search").path("bygroup").path(taxonClass).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(Taxon.class);

        System.out.println("taxon: " + taxon);

        StringBuilder jpqlSB1 = new StringBuilder();
        jpqlSB1.append("SELECT t FROM Taxon AS t where ");
        jpqlSB1.append(" t.nodeNumber BETWEEN ");
        jpqlSB1.append(taxon.getNodeNumber());
        jpqlSB1.append(" AND ");
        jpqlSB1.append(taxon.getHighestChildNodeNumber());

        queryParams = new MultivaluedMapImpl();
        queryParams.add("jpql", jpqlSB1.toString());
        queryParams.add("fields1", "fullName");
        queryParams.add("fields2", "commonName");

        SpecifyBeanWrapper wrapper = service.path("search").path("list").path("bygroup").path(taxonClass).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        List<? extends SpecifyBean> beans = wrapper.getBeans();
        for (SpecifyBean bean : beans) {
            Taxon t = (Taxon) bean;
            System.out.println(t.getFullName() + "...." + t.getCommonName() + t.getTaxonFamily());
        }

    }

    private static void testGetPartialObjects() {
        String coClass = Collectionobject.class.getSimpleName();


        String bean = service.path("search").path("partial").path(coClass).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("bean:  " + bean);




    }

    private static void testGetEntitiesByJPQL() {

//        String jpql = "Select c.collectingEventID.localityID from Collectionobject AS c where c.collectionID.code= 'smtpinv'"
//                + " and c.collectingEventID.localityID.longitude1 IS NOT NULL and  c.collectingEventID.localityID.latitude1 IS NOT NULL"
//                + " group by c.collectingEventID.localityID";



        String jpql1 = "SELECT co.catalogNumber, "
                + "co.countAmt, "
                + "ce.startDate, "
                + "ce.stationFieldNumber, "
                + "det.typeStatusName, "
                + "tx.fullName, "
                + "tx.name, "
                + "tx.rankID, "
                + "loc.latitude1, "
                + "loc.longitude1, "
                + "loc.localityName, "
                + "loc.maxElevation, "
                + "loc.minElevation, "
                + "geo.fullName, "
                + "geo.name, "
                + "geo.rankID, "
                + "ag.lastName, "
                + "ag.firstName, "
                + "ag.middleInitial, "
                + "co.text1, "
                + "co.collectionObjectID, "
                + "det.determinationID, "
                + "tx.taxonID, "
                + "ce.collectingEventID, "
                + "loc.localityID, "
                + "geo.geographyID, "
                + "ag.agentID, "
                + "tx.parentID, "
                + "geo.parentID "
                + "FROM Collectionobject AS co LEFT JOIN Determination AS det ON co.collectionObjectID = det.collectionObjectID "
                + "LEFT JOIN Taxon AS tx ON det.taxonID = tx.taxonID LEFT JOIN Collectingevent AS ce ON "
                + "co.collectingEventID = ce.collectingEventID LEFT JOIN Collector AS col ON "
                + "ce.collectingEventID = col.collectingEventID LEFT JOIN Agent AS ag ON col.agentID = ag.agentID LEFT JOIN "
                + "Locality AS loc ON ce.localityID = loc.localityID LEFT JOIN Geography AS geo ON loc.geographyID = "
                + "geo.geographyID WHERE det.isCurrent <> 0 AND  co.collectionMemberID = 163840 AND col.orderNumber = 0 "
                + "ORDER BY co.collectionObjectID";



        String jpql = "SELECT d.collectionObjectID.catalogNumber, "
                + "d.collectionObjectID.countAmt, "
                + "d.collectionObjectID.collectingEventID.startDate, "
                + "d.collectionObjectID.collectingEventID.stationFieldNumber, "
                + "d.typeStatusName, "
                + "d.taxonID.fullName, "
                + "d.taxonID.name, "
                + "d.taxonID.rankID, "
                + "d.collectionObjectID.collectingEventID.localityID.latitude1, "
                + "d.collectionObjectID.collectingEventID.localityID.longitude1, "
                + "d.collectionObjectID.collectingEventID.localityID.localityName, "
                + "d.collectionObjectID.collectingEventID.localityID.maxElevation, "
                + "d.collectionObjectID.collectingEventID.localityID.minElevation, "
                + "d.collectionObjectID.collectingEventID.localityID.geographyID.fullName, "
                + "d.collectionObjectID.collectingEventID.localityID.geographyID.name, "
                + "d.collectionObjectID.collectingEventID.localityID.geographyID.rankID, "
                + "d.collectionObjectID.collectingEventID.collectorID.agentID.lastName, "
                + "d.collectionObjectID.collectingEventID.collectorID.agentID.firstName, "
                + "d.collectionObjectID.collectingEventID.collectorID.agentID.middleInitial, "
                + "d.collectionObjectID.text1, "
                + "d.collectionObjectID.collectionObjectID, "
                + "d.determinationID, "
                + "d.taxonID.taxonID, "
                + "d.collectionObjectID.collectingEventID.collectingEventID, "
                + "d.collectionObjectID.collectingEventID.localityID.localityID, "
                + "d.collectionObjectID.collectingEventID.localityID.geographyID.geographyID, "
                + "d.collectionObjectID.collectingEventID.collectorID.agentID.agentID, "
                + "d.taxonID.parentID.taxonID, "
                + "d.collectionObjectID.collectingEventID.localityID.geographyID.parentID.geographyID "
                + "FROM Determination as d WHERE "
                + "d.isCurrent <> 0 AND "
                + "d.collectionMemberID = 163840 AND "
                + "d.collectionObjectID.collectingEventID.collectorID.orderNumber = 0 "
                + "ORDER BY d.collectionObjectID.collectionObjectID";


        DataWrapper wrapper = service.path("search").path("data").path(jpql).accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);

//        List<Object[]> beans = wrapper.getDataList();
//        
//        System.out.println("size:...." + beans);

//        for (SpecifyBean bean : beans) {
//            System.out.println("bean: " + bean);
//        }
    }

    private static void testGetAllEntitiesByNamedQuery() {

        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("namedquery", "Collectingevent.findByLocality");
        queryParams.add("localityName", "Trap ID 18%");

        SpecifyBeanWrapper wrapper = service.path("search").path("entities").path("namedquery").queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);

        List<? extends SpecifyBean> beans = wrapper.getBeans();

        for (SpecifyBean bean : beans) {
            System.out.println("bean: " + bean);
        }
    }

    private static void testGetTextListByJPQL() {
        String jpql = "SELECT t.fullName FROM Taxon AS t";

        DataWrapper wrapper = service.path("search").path("textlist").path(jpql).accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);
        List<String> list = wrapper.getList();
        for (String string : list) {
            System.out.println(string);
        }
    }

    private static void testUpdateCollectionobjectEntity() {

        String coClass = Collectionobject.class.getName();
        String coId = "206015";

        Collectionobject bean = service.path("search").path("entity").path(coClass).path(coId).accept(MediaType.APPLICATION_JSON).get(Collectionobject.class);
        System.out.println("bean:  " + bean);

        java.util.Collection<Determination> determinations = bean.getDeterminationCollection();
        System.out.println("Determination: " + determinations);

        java.util.Collection<Preparation> preparations = bean.getPreparationCollection();
        System.out.println("preparation: " + preparations);
// 
//        
//
        String agentClass = Agent.class.getName();
        String agentId = "1";
        Agent agent = service.path("search").path("entity").path(agentClass).path(agentId).accept(MediaType.APPLICATION_JSON).get(Agent.class);
        System.out.println("Agent: " + agent);
//
        String taxonClass = Taxon.class.getName();
        String taxonId = "149801";
        Taxon taxon = service.path("search").path("entity").path(taxonClass).path(taxonId).accept(MediaType.APPLICATION_JSON).get(Taxon.class);
        System.out.println("taxon: " + taxon);
//
        Determination determination = new Determination();
        determination.setTimestampCreated(timestamp);
        determination.setCollectionMemberID(262144);
        determination.setIsCurrent(true);
        determination.setCollectionObjectID(bean);
        determination.setCreatedByAgentID(agent);
        determination.setTaxonID(taxon);
        determination.setPreferredTaxonID(taxon);

        determinations.add(determination);
        bean.setDeterminationCollection(determinations);


        // Create preparation
        String pretypeClassName = Preptype.class.getName();
        String preptypeId = "24";
        Preptype preptype = service.path("search").path("entity").path(pretypeClassName).path(preptypeId).accept(MediaType.APPLICATION_JSON).get(Preptype.class);

        System.out.println("preptype: " + preptype);
        Preparation preparation = new Preparation();
        preparation.setTimestampCreated(timestamp);
        preparation.setCollectionMemberID(bean.getCollectionMemberID());
        preparation.setDescription("test");
        preparation.setPreparedDate(timestamp);
        preparation.setPreparedDatePrecision(Short.valueOf("1"));
        preparation.setPrepTypeID(preptype);
        preparation.setCreatedByAgentID(agent);
        preparation.setCollectionObjectID(bean);

        preparations.add(preparation);
        bean.setPreparationCollection(preparations);


        SpecifyBeanWrapper data = new SpecifyBeanWrapper(bean);
        ClientResponse response = service.path("updateentity").put(ClientResponse.class, data);
        System.out.println(response);



//        String addressClassName = Address.class.getName();
//        String id = "4015";
//        Address address = service.path("search").path("entity").path(addressClassName).path(id).accept(MediaType.APPLICATION_JSON).get(Address.class);
//        
//        String city = address.getCity().equals("Ann Arbor") ? "new city" : "Ann Arbor";
//        String addressName = address.getAddress().equals("address") ? "U.S. Geological Survey, Great Lakes Science" : "address";
//        address.setAddress(addressName);
//        address.setCity(city); 
//        
//        data = new SpecifyBeanWrapper(address);
//
//        response = service.path("updateentity").put(ClientResponse.class, data);
//        System.out.println("Response: " + response);

    }

    /**
     * test webservice client get entity
     */
    private static void testGetEntity() {

        String entity = Address.class.getName();
        String id = "18";


//        String entity = Collectionobject.class.getName();
//        String id = "205180";  
//
////        // JSON
////        String json = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(String.class);
////        System.out.println("json: " + json);

        //XML
//        String xml = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_XML).get(String.class);
//        System.out.println("xml: " + xml);
//
        String bean = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("bean:  test: " + bean);

//
//        java.util.Collection<Determination> determinations = bean.getDeterminationCollection();
//        System.out.println("Determination: " + determinations);
//
//        java.util.Collection<Preparation> prepartions = bean.getPreparationCollection();
//        System.out.println("prepartion: " + prepartions);
//
//        for (Preparation prepartion : prepartions) {
//            System.out.println("prepartion - collectionobject: " + prepartion.getCollectionObjectID());
//        }
//
//        for (Determination determination : determinations) {
//            System.out.println("determination - collectionobject: " + determination.getCollectionObjectID());
//            System.out.println("determination - Taxon: " + determination.getTaxonID());
//        }


//        String entity = Preptype.class.getName();
//        String id = "24";

//        // JSON
//        String json = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(String.class);
//        System.out.println("json: " + json);
//
//        //XML
//        String xml = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_XML).get(String.class);
//        System.out.println("xml: " + xml);

//        Preptype bean = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(Preptype.class);
//        System.out.println("bean:  " + bean);
//
//
//        java.util.Collection<Preparation> prepartions = bean.getPreparationCollection();
//        for (Preparation prepartion : prepartions) {
//            System.out.println("preparation - preptype: " + prepartion.getPrepTypeID());
//        }
    }

    /**
     * test webservice client createEntity
     */
    private static void testCreateEntity() {

        String entity = Address.class.getName();    // class name must be for qualified name - ex: se.nrm.specify.datamodel.Address       

        // Create entity
        Address address = new Address();

        address.setAddress("Renlavsgangen 128");
        address.setCity("Tyres‚àö‚àÇ");
        address.setCountry("Sweden");
        address.setTimestampCreated(timestamp);

        String json = gson.toJson(address);

        ClientResponse response = service.path("add").path(entity).path(json).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
        System.out.println("response..." + response);
    }

    private static void testCreateGenericEntity() {
        Address address = new Address();

        address.setAddress("Renlavsgangen 128");
        address.setCity("Tyres‚àö‚àÇ");
        address.setCountry("Sweden");
        address.setTimestampCreated(timestamp);

        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(address);

        ClientResponse response = service.path("add").path("entity").accept(MediaType.APPLICATION_JSON).entity(wrapper).post(ClientResponse.class);
        System.out.println("response..." + response);
    }

    /**
     * test webservice client deleteEntity
     */
    private static void testDeleteEntity() {

        String entity = Address.class.getName();    // class name must be for qualified name - ex: se.nrm.specify.datamodel.Address    
        String id = "4022";

        ClientResponse response = service.path("delete").path(entity).path(id).delete(ClientResponse.class);
        System.out.println("response: " + response);
    }

    /**
     * Test webservice client updateEntity;
     */
    private static void testUpdateEntity() {

        String entity = Collectionobject.class.getName();
        String id = "203859";
        Collectionobject co = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(Collectionobject.class);

        System.out.println("address: " + co);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
        Collectingevent event = co.getCollectingEventID();
        event.setRemarks("my test");
        co.setCollectingEventID(event);
        co.setRemarks("tttt");

        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(co);

        ClientResponse response = service.path("updateentity").put(ClientResponse.class, wrapper);
        System.out.println(response);


    }

    private static void testGetLocalitiesByCollectionCode() {
        GenericType<List<Locality>> genericType = new GenericType<List<Locality>>() {
        };

        List<Locality> localities = service.path("search").path("localities").path("smtpinv").accept(MediaType.APPLICATION_JSON).get(genericType);
        System.out.println("locality: " + localities);
        for (Locality locality : localities) {
            System.out.println("locality: " + locality.getLocalityName());
        }
    }

    private static void testGetAll() {
        String entity = Collection.class.getName();

        GenericType<List<SpecifyBeanWrapper>> genericType = new GenericType<List<SpecifyBeanWrapper>>() {
        };

        List<SpecifyBeanWrapper> list = service.path("search").path("all").path(entity).accept(MediaType.APPLICATION_JSON).get(genericType);

        for (SpecifyBeanWrapper sb : list) {
            System.out.println("Collections: " + sb.getBean());
        }

    }

    private static void testGetAllEntities() {
        String entity = Accession.class.getName();

        SpecifyBeanWrapper wrapper = service.path("search").path("allentities").path(entity).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);

        List<? extends SpecifyBean> beans = wrapper.getBeans();

        for (SpecifyBean bean : beans) {
            System.out.println("bean: " + bean);
        }

    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/specify-data-service/").build();            // service deployed in local
//        return UriBuilder.fromUri("http://barcode.nrm.se:80/specify-data-service/").build(); 
//        return UriBuilder.fromUri("http://172.16.0.145:8080/jpa-service/").build();           // service deployed in development server
    }
}

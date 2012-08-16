package se.nrm.specify.data.service.client;
 
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client; 
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl; 
import java.net.URI;
import java.sql.Timestamp; 
import java.util.ArrayList; 
import java.util.List; 
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder; 
import se.nrm.specify.datamodel.*;

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
  

//        testGetEntity();    
//        testFetchGroup(); 
//        testGetAllEntitiesByNamedQuery(); 
//        testDeleteEntity();   
//        testCreateGenericEntity();     
//        testUpdateEntity();             
//        testFetchGroupByNamedQuery();  
//        
//        testGetEntityByEntityID();  
//        testGetAllEntities();
//        
//        testUIView(); 
        testCreateNewEntity();
         
    }
    
    private static void testCreateNewEntity() {
        SpecifyBeanWrapper beanWrapper1 = service.path("searchbyid").path(Agent.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Agent agent = (Agent) beanWrapper1.getBean();
//        
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Division.class.getName()).path("2").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Division division = (Division) beanWrapper.getBean();
//
//  
        Accession accession = new Accession();
        accession.setTimestampCreated(timestamp);
        accession.setAccessionNumber("newNumber1");
        accession.setDivision(division);

        List<Accessionagent> aas = new ArrayList();
        Accessionagent aa = new Accessionagent();
        aa.setTimestampCreated(timestamp);
        aa.setRole("test2");
        aa.setAccession(accession);
        aa.setAgent(agent);
        

        aas.add(aa);

        accession.setAccessionAgents(aas);
        
        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(accession);


        String response = service.path("newEntity").post(String.class, wrapper);

        System.out.println("response..." + response);
        
 


//        Agent agent = new Agent();
//        agent.setTimestampCreated(timestamp);
//        agent.setDivision(division);

//        Borrow borrow = new Borrow();
//        borrow.setInvoiceNumber("test 2");
//        borrow.setTimestampCreated(timestamp);
//
//        List<Borrowagent> bas = new ArrayList<Borrowagent>();
//        Borrowagent ba = new Borrowagent();
//        ba.setTimestampCreated(timestamp);
//        ba.setRole("test2");
//        ba.setBorrow(borrow);
//        ba.setAgent(agent);
//
//        bas.add(ba);
//
//        borrow.setBorrowAgents(bas);
//
//
//        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(borrow);
//
//        String response = service.path("updateentity").put(String.class, wrapper);
//        System.out.println(response);

    }
    
 
    private static void testCreateGenericEntity() { 
        
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Collection.class.getName()).path("163840").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Collection collection = (Collection) beanWrapper.getBean();
        
        System.out.println("collection : " + collection);
        
        beanWrapper = service.path("searchbyid").path(Taxon.class.getName()).path("600").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Taxon taxon = (Taxon) beanWrapper.getBean();
        
        Collectionobject co = new Collectionobject();
        co.setTimestampCreated(timestamp);
        co.setCollection(collection);
        co.setCatalogNumber("test");
        
        List<Determination> determinations = new ArrayList<Determination>();
        Determination determination = new Determination();
        determination.setTimestampCreated(timestamp);
        determination.setCollectionMemberId(262144);
        determination.setIsCurrent(true);
        determination.setCollectionObject(co); 
        determination.setTaxon(taxon);
        determination.setPreferredTaxon(taxon);

        determinations.add(determination);
        co.setDeterminations(determinations);
        
        Determination determination1 = new Determination();
        determination1.setTimestampCreated(timestamp);
        determination1.setCollectionMemberId(262144);
        determination1.setIsCurrent(false);
        determination1.setCollectionObject(co); 
        determination1.setTaxon(taxon);
        determination1.setPreferredTaxon(taxon);

        determinations.add(determination1);
        co.setDeterminations(determinations);
 
        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(co);


        String response = service.path("newEntity").post(String.class, wrapper);

        System.out.println("response..." + response);
    }
    
    
    /**
     * Test webservice client updateEntity;
     */
    private static void testUpdateEntity() {
 

        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Collectionobject.class.getName()).path("217849").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Collectionobject co = (Collectionobject) beanWrapper.getBean();
   
        co.setCatalogNumber(co.getCatalogNumber() + "-1");
  

        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(co);

        String response = service.path("updateentity").put(String.class, wrapper);
        System.out.println(response); 
    }
 
    
    /**
     * test webservice client get entity
     */
    private static void testGetEntity() {
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Address.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Address address = (Address) beanWrapper.getBean(); 
        System.out.println("Address : " + address); 
        
        String xml = service.path("searchbyid").path(Address.class.getName()).path("1").accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml : " + xml);

        String json = service.path("searchbyid").path(Address.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("json : " + json); 
    }
     
    
    private static void testGetAllEntitiesByNamedQuery() {

        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("namedquery", "Collectionobject.findByCollectionObjectID");
        queryParams.add("collectionObjectID", 425); 

        String xml = service.path("search").path("entities").path("namedquery").queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);

        System.out.println("json : " + xml);
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
        
        
        List<String> jpqlList = new ArrayList<String>();
        jpqlList.add(jpqlSB.toString());
        
        List<String> fields = new ArrayList<String>();
        fields.add("nodeNumber");
        fields.add("highestChildNodeNumber");
        fields.add("fullName");
        fields.add("commonName");

        queryParams.put("jpql", jpqlList);
        queryParams.put(taxonClass, fields); 

        String xml = service.path("search").path("list").path("bygroup").path(taxonClass).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml : " + xml);

    } 
        
    private static void testFetchGroupByNamedQuery() {

        String namedQuery = "Accession.findByAccessionID";

        List<String> fields = new ArrayList<String>();
        fields.add("accessionNumber");
        fields.add("dateReceived");
        fields.add("remarks");
        fields.add("timestampCreated");
        fields.add("timestampModified");
        fields.add("division.name");
        fields.add("dateAccessioned"); 

        List<String> list = new ArrayList<String>();
        list.add("7");


        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.put("accessionID", list);
        queryParams.put("fields", fields);

        String xml = service.path("search").path("bygroup").path("bynqry").path(namedQuery).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);

        System.out.println("result: " + xml);

        SpecifyBeanWrapper wrapper = service.path("search").path("bygroup").path("bynqry").path(namedQuery).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Accession accession = (Accession) wrapper.getBean();
        System.out.println("accession : " + accession + " ---- " + accession.getAccessionNumber());
  
    }
    
    private static void testGetEntityByEntityID() {
        String xml = service.path("search").path("uidata").path("fish").path("CollectionObject").path("424").accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml: " + xml);
    }
    
    /**
     * test webservice client deleteEntity 
     */
    private static void testDeleteEntity() { 

        String wrapper = service.path("delete").path(Accession.class.getName()).path("12").delete(String.class);
        System.out.println("response: " + wrapper);
    }
 
 
    /**
     * Test ui data with view
     * current
     */
    private static void testUIView() {

        String discipline = "fish";
        String view = "CollectionObject";

        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
        queryParams.add("catalogNumber", "NHRS-COLE000008661");

        String xml = service.path("search").path("uidata").path(discipline).path(view).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml: " + xml);
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

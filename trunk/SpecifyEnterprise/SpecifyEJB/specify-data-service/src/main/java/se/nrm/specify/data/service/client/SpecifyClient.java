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
import se.nrm.specify.ui.form.data.ViewCreator;
import se.nrm.specify.ui.form.data.service.SpecifyRSClient;
 

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
//        testCreateNewEntity();
        
        testUIDataFetch();
//        testFindById();
        
         
    }
    
    
    
    private static void testUIDataFetch()  {
        
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Preptype.class.getName()).path("21").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Preptype preptype = (Preptype) beanWrapper.getBean();
        
        
        
        SpecifyBeanWrapper beanWrapper1 = service.path("searchbyid").path(Taxon.class.getName()).path("1272").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Taxon taxon = (Taxon) beanWrapper1.getBean();

        beanWrapper1 = service.path("searchbyid").path(Agent.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Agent agent = (Agent) beanWrapper1.getBean();

//        beanWrapper = service.path("searchbyid").path(Accession.class.getName()).path("6").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
//        Accession accession = (Accession)beanWrapper.getBean();

        beanWrapper = service.path("searchbyid").path(Division.class.getName()).path("2").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Division division = (Division) beanWrapper.getBean();
        
        String discipline = "fish";
        String view = "CollectionObject";

        ViewCreator creator = new ViewCreator(discipline);
        SpecifyRSClient client = new SpecifyRSClient(creator);
        

        MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
//        queryParams.add("catalogNumber", "NHRS-GULI000000970");
//         queryParams.add("collectionObjectId", 9439);
        
        
        queryParams.add("collectionObjectId", 137234);
          
        String json = client.getJSONResult(discipline, view, queryParams);
        String xml = client.getXMLResult(discipline, view, queryParams);
        
        
        List<SpecifyBean> beans = client.getEntityResult(discipline, view, queryParams);
        
//        System.out.println("json : " + json);
        System.out.println("xml : " + xml);
        System.out.println("beans : " + beans);
        
        
        Collectionobject collectionobject = (Collectionobject)beans.get(0);
        collectionobject.setCollectionMemberId(collectionobject.getCollectionMemberId() + 1);
        
        Agent cataloger = collectionobject.getCataloger();
          
        if(cataloger == null) { 
//            cataloger = new Agent();
//            cataloger.setTimestampCreated(timestamp);
//            cataloger.setDivision(division);
//            cataloger.setFirstName("tst");
//            cataloger.setLastName("tesetdd");

            
            cataloger = agent;
            cataloger.setFirstName(cataloger.getFirstName() + "ww");
            collectionobject.setCataloger(cataloger);
        } else {
            collectionobject.setCataloger(null);
        }
//        collectionobject.setCataloger(cataloger);
        
        taxon.setFullName(taxon.getFullName() + "11");
//         
        
        List<Determination> determinations = (List<Determination>)collectionobject.getDeterminations();
        if(determinations == null) {
            determinations = new ArrayList<Determination>();
            Determination d = new Determination();
            d.setTimestampCreated(timestamp);
            d.setCollectionMemberId(123);
            d.setIsCurrent(true);
            d.setTaxon(taxon);

            determinations.add(d);

            d = new Determination();
            d.setTimestampCreated(timestamp);
            d.setCollectionMemberId(123);
            d.setTaxon(taxon);

            determinations.add(d);  
        } else {
            for(Determination d : determinations) {
                Taxon t = d.getTaxon();
                t.setName(taxon.getName() + "2");
                d.setTaxon(t);
                d.setCollectionMemberId(d.getCollectionMemberId() + 12);
                 
                Agent determiner = d.getDeterminer(); 
                if(determiner == null) {
                    determiner = agent;
                    determiner.setLastName(determiner.getLastName() + "k");
                    
                    
//                    determiner = new Agent(); 
//                    determiner.setTimestampCreated(timestamp);
//                    determiner.setLastName("eerer");
                    
                    d.setDeterminer(determiner);
                } else {
                    determiner.setLastName(determiner.getLastName() + "d");
                    d.setDeterminer(null);
                } 
            }  
        } 
        Collection collection = collectionobject.getCollection();
        System.out.println("collection : " + collection); 
        collectionobject.setDeterminations(determinations);
         
        
        Collectingevent ce = collectionobject.getCollectingEvent();
        ce.setStartDate(timestamp); 
        collectionobject.setCollectingEvent(ce);
          
        Accession accession = collectionobject.getAccession(); 
        if(accession == null) {
            accession = new Accession(); 
            accession.setTimestampCreated(timestamp);
            accession.setAccessionNumber("dasdfa");
            accession.setDivision(division);
            accession.setTimestampCreated(timestamp);
        } else {
            accession.setAccessionNumber(accession.getAccessionNumber() + "r"); 
        }
        List<Accessionagent> aas = (List<Accessionagent>) accession.getAccessionAgents();
        if (aas == null) {
            aas = new ArrayList<Accessionagent>();
            Accessionagent aa = new Accessionagent();
            aa.setTimestampCreated(timestamp);
            aa.setRole("test");
            aa.setAgent(agent);

            aas.add(aa);
            aa = new Accessionagent();
            aa.setTimestampCreated(timestamp);
            aa.setRole("test 2");
            aa.setAgent(agent);

            aas.add(aa); 
        } else {
            for(Accessionagent aa : aas) {
                aa.setRole(aa.getRole()+ "i");
            }
        }
        
        accession.setAccessionAgents(aas);
        collectionobject.setAccession(accession);
 
        List<Preparation> preparations = (List<Preparation>) collectionobject.getPreparations();
        if(preparations == null) {
            preparations = new ArrayList<Preparation>();
            
//            Preptype pt = new Preptype();
//            pt.setTimestampCreated(timestamp);
//            pt.setName(preptype.getName() + 1);
//            pt.setCollection(collection);
//            
            Preparation p = new Preparation();
            p.setTimestampCreated(timestamp);  
            p.setCollectionMemberId(123); 
            p.setPreparedDate(timestamp);
            p.setPreparedDatePrecision(Short.valueOf("1"));
            p.setPrepType(preptype);
            p.setCreatedByAgent(agent);
            p.setCollectionObject(collectionobject);
            
            
            preparations.add(p);
        } else {
            for(Preparation p : preparations) {
                p.setCollectionMemberId(p.getCollectionMemberId() + 12); 
            }
        }
         
        
        
        
        
        collectionobject.setPreparations(preparations);
        
        
        
        
        SpecifyBeanWrapper wp = new SpecifyBeanWrapper(collectionobject);
     
        String response = service.path("uidatamerge").path("fish").accept(MediaType.APPLICATION_XML).put(String.class, wp); 
        System.out.println("response : " + response); 
//      
    
//        "http://localhost:8080/specify-data-service/search/list/bygroup/Collectionobject?jpql=SELECT%20o%20FROM%20Collectionobject%20o%20where%20o.catalogNumber=%27NHRS-COLE000008661%27"
   
    
//    http://localhost:8080/specify-data-service/search/uidata/fish/CollectionObject?catalogNumber=NHRS-GULI000000970
        
        
 //       http://localhost:8080/specify-data-service/search/uidata/fish/CollectionObject/425
        
        // NRM-ORTH0004377
        
    } 
    
    private static void testCreateNewEntity() {
        SpecifyBeanWrapper beanWrapper1 = service.path("searchbyid").path(Preptype.class.getName()).path("17").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Preptype preptype = (Preptype) beanWrapper1.getBean();
        System.out.println("preptype : " + preptype);
        
        
//        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Division.class.getName()).path("2").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
//        Division division = (Division) beanWrapper.getBean();
        
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Collection.class.getName()).path("163840").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Collection collection = (Collection) beanWrapper.getBean();
 
        Collectionobject co = new Collectionobject();
        co.setTimestampCreated(timestamp);
        co.setCatalogNumber("test-19561288");
        co.setCollection(collection);
        
        Preparation p = new Preparation();
        p.setTimestampCreated(timestamp);
        p.setCollectionMemberId(123);
        p.setPrepType(preptype);
        
        List<Preparation> pts = new ArrayList<Preparation>();
        pts.add(p);
        
        
        co.setPreparations(pts);
        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(co);
        
        String response = service.path("newEntity").post(String.class, wrapper);
        System.out.println("response : " + response);

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
    
    
    private static void testFindById() {
        String xml = service.path("searchbyid").path(Collectionobject.class.getName()).path("137234").accept(MediaType.APPLICATION_XML).get(String.class);
        
        System.out.println("cco : " + xml);
    }
    
    /**
     * Test webservice client updateEntity;
     */
    private static void testUpdateEntity() {
 

        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Agent.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Agent agent = (Agent) beanWrapper.getBean();
   
        agent.setLastName(agent.getLastName() + "i");
  

        SpecifyBeanWrapper wrapper = new SpecifyBeanWrapper(agent);

        String response = service.path("updateentity").put(String.class, wrapper);
        System.out.println(response); 
    }
 
    
    /**
     * test webservice client get entity
     */
    private static void testGetEntity() {
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Collectionobject.class.getName()).path("245").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Collectionobject accession = (Collectionobject) beanWrapper.getBean(); 
        System.out.println("Address : " + accession); 
        
        String xml = service.path("searchbyid").path(Collectionobject.class.getName()).path("245").accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml : " + xml);
//
//        String json = service.path("searchbyid").path(Accession.class.getName()).path("2").accept(MediaType.APPLICATION_JSON).get(String.class);
//        System.out.println("json : " + json); 
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
    
    private static void testFetchGroupByNamedQuery1() {
        
        String namedQuery = "Accession.findByAccessionId";
//        String namedQuery = "Accession.findByAccessionNumber";

        List<String> fields = new ArrayList<String>();
        fields.add("accessionNumber");
        fields.add("dateAccessioned");
        fields.add("remarks");
        fields.add("timestampCreated");
        fields.add("timestampModified");
        fields.add("division.name");
        fields.add("dateAccessioned");
        fields.add("accessionAgents.role"); 
        
        MultivaluedMap queryParams = new MultivaluedMapImpl();  
        queryParams.putSingle("accessionId", "7"); 
        queryParams.put("fields", fields);
        queryParams.putSingle("namedQuery", namedQuery);
         
        
         

        String xml = service.path("search").path("bygroup").path("bynqry").queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);

        System.out.println("result: " + xml);

        SpecifyBeanWrapper wrapper = service.path("search").path("bygroup").path("bynqry").queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Accession accession = (Accession) wrapper.getBean();
        System.out.println("accession : " + accession + " ---- " + accession.getAccessionNumber());
        
        
        wrapper = service.path("searchbyid").path(Agent.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Agent agent = (Agent)wrapper.getBean();
         
//        List<Accessionagent> ags = (List<Accessionagent>)accession.getAccessionAgents();
//        ags = null;
        accession.setRemarks("test 4"); 
     
        List<Accessionagent> aas = new ArrayList<Accessionagent>();
        Accessionagent aa = new Accessionagent();
        aa.setTimestampCreated(timestamp);
        aa.setRole("new role 1");
        aa.setAgent(agent);
        
        aas.add(aa);
        aa = new Accessionagent();
        aa.setTimestampCreated(timestamp);
        aa.setRole("new role");
        aa.setAgent(agent);
        
        aas.add(aa);
        
        
        
        accession.setAccessionAgents(aas);
//        int count = 0;
//        List<Accessionagent> ags = (List<Accessionagent>)accession.getAccessionAgents();
//        for(Accessionagent aa : ags) {
//            aa.setRole(aa.getRole() + count++);
//        }
//         
     
        SpecifyBeanWrapper wp = new SpecifyBeanWrapper(accession);
     
        String response = service.path("partialmearge").queryParams(queryParams).accept(MediaType.APPLICATION_XML).put(String.class, wp); 
        System.out.println("response : " + response); 
        
    }
        
    private static void testFetchGroupByNamedQuery() {
         
        
        SpecifyBeanWrapper beanWrapper = service.path("searchbyid").path(Division.class.getName()).path("2").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Division division = (Division)beanWrapper.getBean();
        
        
//        beanWrapper = service.path("searchbyid").path(Accession.class.getName()).path("6").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
//        Accession accession = (Accession)beanWrapper.getBean();
        
        beanWrapper = service.path("searchbyid").path(Agent.class.getName()).path("1").accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Agent agent = (Agent)beanWrapper.getBean();
                
        
        String namedQuery = "Collectionobject.findByCollectionObjectId";

        List<String> fields = new ArrayList<String>();
        fields.add("collectionMemberId");
        fields.add("altCatalogNumber");
        fields.add("remarks");
        fields.add("catalogNumber");
        fields.add("timestampModified");
        fields.add("name");
        fields.add("accession.accessionNumber");
        fields.add("accession.remarks");
        fields.add("accession.dateAccessioned");
        fields.add("accession.timestampCreated");
//        fields.add("determinations.isCurrent");
//        fields.add("determinations.taxon.fullName"); 
        fields.add("preparations.timestampModified"); 
        fields.add("preparations.collectionMemberId"); 
        
        
  
        MultivaluedMap queryParams = new MultivaluedMapImpl();  
//        queryParams.putSingle("collectionObjectID", "152071");
        queryParams.putSingle("collectionObjectId", "424");
        queryParams.put("fields", fields);
        queryParams.putSingle("namedQuery", namedQuery);
        
          
        String xml = service.path("search").path("bygroup").path("bynqry").queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);

        System.out.println("result: " + xml);

        SpecifyBeanWrapper wrapper = service.path("search").path("bygroup").path("bynqry").queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);
        Collectionobject collectionobject = (Collectionobject) wrapper.getBean();
        System.out.println("collectionobject : " + collectionobject + " ---- " + collectionobject.getCatalogNumber());
         
//        Division division = collectionobject.getCollection().getDiscipline().getDivision();
//        System.out.println("division : " + division);
      
        Accession accession = collectionobject.getAccession(); 
        if(accession == null) {
            accession = new Accession();
            accession.setAccessionNumber("sdfdsadfsdaf");
            accession.setDivision(division);
            accession.setTimestampCreated(timestamp);

            List<Accessionagent> aas = new ArrayList<Accessionagent>();
            Accessionagent aa = new Accessionagent();
            aa.setTimestampCreated(timestamp);
            aa.setRole("test");
            aa.setAgent(agent);

            aas.add(aa);
            aa = new Accessionagent();
            aa.setTimestampCreated(timestamp);
            aa.setRole("test 2");
            aa.setAgent(agent);

            aas.add(aa);
            accession.setAccessionAgents(aas);
        } else {
            accession.setAccessionNumber(accession.getAccessionNumber() + "ww");
            accession.setDivision(division);

            List<Accessionagent> aas = new ArrayList<Accessionagent>();
            Accessionagent aa = new Accessionagent(); 
            aa.setRole("test");
            aa.setAgent(agent);
            aa.setTimestampCreated(timestamp);

            aas.add(aa);
            aa = new Accessionagent(); 
            aa.setRole("test 2");
            aa.setAgent(agent);
            aa.setTimestampCreated(timestamp);

            aas.add(aa);
            accession.setAccessionAgents(aas);
        }
        
        collectionobject.setAccession(accession); 
        
//        List<Determination> determinations = (List<Determination>)collectionobject.getDeterminations();
//        for(Determination d : determinations) {
//            Taxon t = d.getTaxon();
//            t.setFullName(t.getFullName() + "22");
//            d.setTaxon(t);
//        }
//        collectionobject.setDeterminations(determinations);
        
        List<Preparation> preparations = (List<Preparation>)collectionobject.getPreparations();
        Preparation p = preparations.get(0);
        p.setCollectionMemberId(p.getCollectionMemberId() + 1);
        
        Preptype preptype = p.getPrepType();
        preptype.setTimestampCreated(timestamp);
        
        
        collectionobject.setPreparations(preparations);
        
        collectionobject.setRemarks(collectionobject.getRemarks() + "ee");
        
   
        SpecifyBeanWrapper wp = new SpecifyBeanWrapper(collectionobject);
     
        String response = service.path("partialmearge").queryParams(queryParams).accept(MediaType.APPLICATION_XML).put(String.class, wp);
        System.out.println("response : " + response); 
         
        
    }
    
    private static void testGetEntityByEntityID() {
        String xml = service.path("search").path("uidata").path("fish").path("CollectionObject").path("202841").accept(MediaType.APPLICATION_XML).get(String.class);
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

package se.nrm.specify.data.service.client;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
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

        testGetEntity();
//        testCreateEntity();
//        testCreateAddress();
//        testDeleteEntity();
//        testUpdateEntity();
//        testGetAllAccessions();
//        testGetCollectingEventByStationFieldNumber();
 
    }

    /**
     * test webservice client get entity
     */
    private static void testGetEntity() {

        String entity = Address.class.getName();
        String id = "2";

        // JSON
        String json = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("json: " + json);

        //XML
        String xml = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("xml: " + xml);

        Address bean = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(Address.class);
        System.out.println("bean:  " + bean);
    }

    /**
     * test webservice client createEntity
     */
    private static void testCreateEntity() {

        String entity = Address.class.getName();    // class name must be for qualified name - ex: se.nrm.specify.datamodel.Address       

        // Create entity
        Address address = new Address();

        address.setAddress("Renlavsgangen 128");
        address.setCity("Tyresö");
        address.setCountry("Sweden");
        address.setTimestampCreated(timestamp);

        String json = gson.toJson(address);

        ClientResponse response = service.path("add").path(entity).path(json).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class);
        System.out.println("response..." + response);
    }

    /**
     * Test webservice client createAddress;
     */
    private static void testCreateAddress() {
        Address address = new Address();

        address.setAddress("Renlavsgangen 128");
        address.setCity("Tyresö");
        address.setCountry("Sweden");
        address.setTimestampCreated(timestamp);

        ClientResponse response = service.path("add").path("address").accept(MediaType.APPLICATION_JSON).entity(address).post(ClientResponse.class);
        System.out.println("response..." + response);
    }

    /**
     * test webservice client deleteEntity
     */
    private static void testDeleteEntity() {

        String entity = Address.class.getName();    // class name must be for qualified name - ex: se.nrm.specify.datamodel.Address    
        String id = "4015";

        service.path("delete").path(entity).path(id).delete(ClientResponse.class);
    }

    /**
     * Test webservice client updateEntity;
     */
    private static void testUpdateEntity() {

        String entity = Address.class.getName();
        String id = "4015";
        Address address = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(Address.class);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SpecifyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        address.setAddress("address");
        address.setCity("test city");
        String json = gson.toJson(address);

        ClientResponse response = service.path("update").path(entity).path(json).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class);
        System.out.println("Response: " + response); 
    }

    /**
     * test webservice client getAllAccessions;
     */
    private static void testGetAllAccessions() {
        // Get all Accession
        GenericType<List<Accession>> genericType = new GenericType<List<Accession>>() {
        };
        List<Accession> accessions = service.path("search").path("all").path("accessions").accept(MediaType.APPLICATION_JSON).get(genericType);
        System.out.println("accessions: " + accessions);

    }

    /**
     * Test webservice client getCollectingEventByStationFieldNumber
     */
    private static void testGetCollectingEventByStationFieldNumber() {

        Collectingevent event = service.path("search").path("collectingevent").path("Event ID 1096").accept(MediaType.APPLICATION_JSON).get(Collectingevent.class);

        Date date = event.getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        String endDate = format.format(event.getEndDate());

        System.out.println("start date: " + strDate);
        System.out.println("end date: " + endDate);

        Locality locality = event.getLocalityID();
        System.out.println("locality id: " + locality.getLocalityName());
        System.out.println("lat. : " + locality.getLat1Text());
        System.out.println("long: " + locality.getLong1Text());
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/specify-data-service/").build();            // service deployed in local
//        return UriBuilder.fromUri("http://172.16.0.145:8080/jpa-service/").build();           // service deployed in development server
    }
}

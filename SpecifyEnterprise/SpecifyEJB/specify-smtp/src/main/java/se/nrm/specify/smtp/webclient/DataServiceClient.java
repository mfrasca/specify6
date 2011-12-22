package se.nrm.specify.smtp.webclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Taxon;
import se.nrm.specify.smtp.beans.SampleBean; 

/**
 *
 * @author idali
 */
@Stateless
public class DataServiceClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebResource service;

    public DataServiceClient() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        service = client.resource(getBaseURI());
    }

    /**
     * getCollectingevent - method call web service getCollectingEventByStationFieldNumber
     * @param stationFieldNumber
     * @return Collectingevent
     */
    public Collectingevent getCollectingevent(String stationFieldNumber) {

        logger.info("getCollectingevent - stationFieldNumber: {}", stationFieldNumber);

        try {
            return service.path("search").path("collectingevent").path(stationFieldNumber).accept(MediaType.APPLICATION_JSON).get(Collectingevent.class);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param taxonName
     * @return 
     */
    public Taxon getTaxonByTaxonname(String taxonName) {

        logger.info("getTaxonByTaxonname: {}", taxonName);

        try {
            return service.path("search").path("taxon").path(taxonName).accept(MediaType.APPLICATION_JSON).get(Taxon.class);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * call web service getAllTaxon
     * @return List<String>
     */
    public List<String> getAllTaxonNames() {

        logger.info("getAllTaxonNames");

        DataWrapper result = service.path("search").path("all").path("taxon").accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);

        return result.getList();
    }

    /**
     * Call web servie getTaxonByTaxonname to validate samplelist with taxon in database.  
     * If taxon does not find in database or taxon has more than one in database, 
     * return false
     * 
     * @param sampleBeans
     * @return boolean
     */
    public boolean validateSampleList(List<SampleBean> sampleBeans) {

        boolean isValid = true;
        for (SampleBean bean : sampleBeans) {
            try {
                Taxon taxon = service.path("search").path("taxon").path(bean.getSampleName()).accept(MediaType.APPLICATION_JSON).get(Taxon.class);
                if (taxon.getTaxonID() == null) {
                    isValid = false;
                    bean.setDesc("Taxon " + bean.getSampleName() + " has more than one in database.");
                }
            } catch (Exception ex) {
                logger.info("Taxon: {} not exist.", bean.getSampleName());
                isValid = false;
                bean.setDesc("Taxon " + bean.getSampleName() + " is not in database!");
            }
        }
        return isValid;
    }

    /**
     * Call web service getDeterminationsbyTaxonnameAndCollecdtingevent
     * @param taxon
     * @param event
     * @param collection
     * @return 
     */
    public List<Determination> getDeterminationsByTaxonAndCollection(String taxon, String event, String collection) {

        logger.info("getDeterminationsByTaxonAndEvent - taxon: {}, eventId: {}", taxon, event);

        GenericType<List<Determination>> genericType = new GenericType<List<Determination>>() {
        };
        return service.path("search").path("determinations").path(taxon).path(event).path(collection).accept(MediaType.APPLICATION_JSON).get(genericType);
    }
    
    /**
     * Call web service uploadData to save batch data in database
     * @param sampleData
     * @param event
     * @param numberofsortedvial 
     */
    public void uploadData(List<SampleBean> sampleData, Collectingevent event, String numberofsortedvial, String collectionCode) {

        logger.info("uploadData: {}", sampleData);
  
        List<String> taxonnames = new ArrayList<String>();
        for (SampleBean bean : sampleData) {
            int count = Integer.parseInt(bean.getSampleNumber());
            for (int i = 0; i < count; i++) {
                taxonnames.add(bean.getSampleName());
            }
        }

        DataWrapper wrapper = new DataWrapper(taxonnames, event, Integer.parseInt(numberofsortedvial), collectionCode);
        service.path("upload").accept(MediaType.APPLICATION_JSON).entity(event).entity(wrapper).post(ClientResponse.class);
    }
    
    /**
     * Call web service getDeterminationsByCollectingevent
     * @param event
     * @param collectionId
     * @return 
     */
    public List<String> getDeterminationsByCollectingevent(Collectingevent event, String collectionCode) {

        logger.info("getDeterminationsByCollectingevent");

        DataWrapper result = service.path("search").path("determinationsbyevent").path(String.valueOf(event.getCollectingEventID())).path(collectionCode).accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);
        return result.getList();
    }
    
    /**
     * Call web service getCollectingeventByLocalityName
     * @param trapId
     * @return 
     */
    public List<Collectingevent> getCollectingeventByLocality(String trapId) {

        logger.info("getCollectingeventByLocality");
        
        String localityName = "Trap ID " + trapId;          // localityName prefixed with 'Trap ID '

        GenericType<List<Collectingevent>> genericType = new GenericType<List<Collectingevent>>() {
        };
        return service.path("search").path("collectingeventbylocality").path(localityName).accept(MediaType.APPLICATION_JSON).get(genericType);
    }
    
    /**
     * Call web service getDeterminationsByLocalityId
     * @param localityId
     * @param collectionCode
     * @return 
     */
    public List<String> getDeterminationByLocality(String localityId, String collectionCode) {

        logger.info("getDeterminationByLocality");

        DataWrapper result = service.path("search").path("determinationsbylocalityid").path(localityId).path(collectionCode).accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);
        return result.getList();
    }  

    /**
     * Call web service getDeterminationsByTaxonId
     * @param taxon
     * @param collectionCode
     * @return 
     */
    public DataWrapper getDeterminationByTaxon(Taxon taxon, String collectionCode) {

        logger.info("getDeterminationByTaxon");

        return service.path("search").path("determinations").path("taxon").path(String.valueOf(taxon.getTaxonID())).path(collectionCode).accept(MediaType.APPLICATION_JSON).get(DataWrapper.class);
    }

    public static void main(String[] args) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());

        String entity = Address.class.getName();
        String id = "4012";

        Address bean = service.path("search").path("entity").path(entity).path(id).accept(MediaType.APPLICATION_JSON).get(Address.class);
        System.out.println("bean:  " + bean); 
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/specify-data-service/").build();
//        return UriBuilder.fromUri("http://172.16.0.145:8080/specify-service/").build();
    }
}

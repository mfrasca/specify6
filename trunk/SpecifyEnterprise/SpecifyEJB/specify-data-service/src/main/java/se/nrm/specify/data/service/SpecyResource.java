package se.nrm.specify.data.service;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.PerRequest;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.business.logic.smtp.SMTPLogic;
import se.nrm.specify.business.logic.specify.SpecifyLogic;
import se.nrm.specify.datamodel.Accession;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.Taxon;

/**
 * REST Web Service
 *
 * @author idali
 */
@PerRequest
@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Stateless
public class SpecyResource {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject
    private SMTPLogic smtp;
    @Inject
    private SpecifyLogic specify;

    /** Creates a new instance of SpecyResource */
    public SpecyResource() {
    }

    /**
     * Generic method to get entity from database.  
     * 
     * @param entity - name of the entity
     * @param id - entity id
     * 
     * @return entity
     */
    @GET
    @Path("search/entity/{entity}/{id}")
    public SpecifyBean getEntity(@PathParam("entity") String entity, @PathParam("id") String id) {

        logger.info("getEntity - entity: {}, id: {}", entity, id);

        return specify.getDao().getById(Integer.parseInt(id), getEntityClass(entity));
    }

    /**
     * Generic method - createEntity method add the entity into database
     * 
     * @param entity - the name of the entity
     * 
     * @param json - an entity in json
     */
    @POST
    @Path("add/{entity}/{json}")
    public void createEntity(@PathParam("entity") String entity, @PathParam("json") String json) {

        logger.info("createEntity - entity: {}, json: {}", entity, json);

        specify.getDao().createEntity(jsonToEntity(entity, json));
    }

    /**
     * Create a specific entity
     * 
     * @param entity
     * @param id 
     */
    @POST
    @Path("add/address")
    public void createAddress(Address address) {

        logger.info("createEntity - entity: {}", address);

        specify.getDao().createEntity(address);
    }

    /**
     * Generic method delete entity by entityid
     * 
     * @param entity
     * @param id 
     */
    @DELETE
    @Path("delete/{entity}/{id}")
    public void delete(@PathParam("entity") String entity, @PathParam("id") String id) {

        logger.info("delete entity - entity: {}, id: {}", entity, id);

        SpecifyBean sBean = specify.getDao().getById(Integer.parseInt(id), getEntityClass(entity));
        if (sBean != null) {
            specify.getDao().deleteEntity(sBean);
        }
    }

    /**
     * Generic method update entity
     * 
     * @param entity
     * @param json 
     */
    @PUT
    @Path("update/{entity}/{json}")
    public String update(@PathParam("entity") String entity, @PathParam("json") String json) {

        logger.info("update entity: {}, json: {}", entity, json);

        String result = specify.getDao().editEntity(jsonToEntity(entity, json));

        logger.info("Edit entity result:  {}", result);

        return result;
    }

    /**
     * Search for Accessions
     * @return List<Accession>
     */
    @GET
    @Path("search/all/accessions")
    public List<Accession> getAllAccessions() {
        return specify.getDao().getAll(Accession.class);
    }

    /**
     * 
     * @param eventId
     * @return 
     */
    @GET
    @Path("search/collectingevent/{stationFieldNumber}")
    public SpecifyBean getCollectingEventByStationFieldNumber(@PathParam("stationFieldNumber") String stationFieldNumber) {

        logger.info("getCollectingEventByStationFieldNumber: {}", stationFieldNumber);

        return specify.getDao().getCollectingEventByStationFieldNumber(stationFieldNumber);
    }

    /**
     * Search for Taxon
     * @return List<Taxon>
     */
    @GET
    @Path("search/all/taxon")
    public DataWrapper getAllTaxon() {

        logger.info("getAllTaxon");

        return new DataWrapper(specify.getDao().getAllTaxonName());
    }

    /**
     * get taxon by taxonname
     * @param taxonname
     * @return Taxon
     */
    @GET
    @Path("search/taxon/{taxonname}")
    public Taxon getTaxonByTaxonname(@PathParam("taxonname") String taxonname) {

        logger.info("getTaxonByTaxonname- taxonname: {}", taxonname);

        return specify.getDao().getTaxonByTaxonName(taxonname);
    }

    /**
     * Get list of determination by taxon name, stationfieldnumber and collection code
     * @param taxonname
     * @param eventId
     * @param collection code
     * @return 
     */
    @GET
    @Path("search/determinations/{taxonname}/{eventid}/{collection}")
    public List<Determination> getDeterminationsbyTaxonnameAndCollecdtingevent(@PathParam("taxonname") String taxonname, @PathParam("eventid") String eventId, @PathParam("collection") String collection) {

        logger.info("getDeterminations");

        return specify.getDao().getDeterminationsByTaxonNameAndCollectingevent(taxonname, new Collectingevent(Integer.parseInt(eventId)), collection);
    }

    @POST
    @Path("upload")
    public void uploadData(DataWrapper wrapper) {

        logger.info("upload data - event: {}, number of sorted vials: {}", wrapper.getEvent(), wrapper.getNumSortedVials());

        smtp.saveSMTPBatchData(wrapper);
    }

    @GET
    @Path("search/determinationsbyevent/{eventid}/{code}")
    public DataWrapper getDeterminationsByCollectingevent(@PathParam("eventid") String eventid, @PathParam("code") String collectionCode) {

        logger.info("getDeterminationsByCollectingevent: {}, {}", eventid, collectionCode);
        return specify.getDao().getDeterminationsByCollectingEvent(new Collectingevent(Integer.parseInt(eventid)), collectionCode);
    }

    @GET
    @Path("search/collectingeventbylocality/{localityname}")
    public List<Collectingevent> getCollectingeventByLocalityName(@PathParam("localityname") String localityName) {

        logger.info("getCollectingeventByLocalityName - localityName: {}", localityName);

        return specify.getDao().getCollectingeventsByLocality(localityName);
    }

    /**
     * get determination taxon names by localityId
     * 
     * @param locality
     * @param collection
     * @return 
     * 
     */
    @GET
    @Path("search/determinationsbylocalityid/{locality}/{collection}")
    public DataWrapper getDeterminationsByLocalityId(@PathParam("locality") String locality, @PathParam("collection") String collectionCode) {

        logger.info("getDeterminationsByLocalityId: {}, {}", locality, collectionCode);

        return new DataWrapper(specify.getDao().getDeterminationByLocalityID(new Locality(Integer.parseInt(locality)), collectionCode));
    }

    @GET
    @Path("search/determinations/taxon/{taxonid}/{collection}")
    public DataWrapper getDeterminationsByTaxonId(@PathParam("taxonid") String taxonid, @PathParam("collection") String collectionCode) {

        logger.info("getDeterminationsByTaxonId");

        return specify.getDao().getDeterminationsByTaxon(new Taxon(Integer.parseInt(taxonid)), collectionCode);
    }

    /**
     * Convent json string to entity
     * 
     * @param <T> - entity
     * @param entity - String entity full qualified class name
     * @param json - entity json string
     * 
     * @return - entity
     */
    private <T extends SpecifyBean> T jsonToEntity(String entity, String json) {

        Gson gson = new Gson();

        return (T) gson.fromJson(json, getEntityClass(entity));
    }

    /**
     * Get entity class by class name
     * 
     * @param entity - Full qualified class name
     * @return - entity class
     */
    private Class getEntityClass(String entity) {
        try {
            return Class.forName(entity);
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}

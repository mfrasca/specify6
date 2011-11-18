package se.nrm.specify.data.service;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.PerRequest; 
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
import se.nrm.specify.datamodel.Accession;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.SpecifyDao;

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
    SpecifyDao dao;

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

        return dao.getById(Integer.parseInt(id), getEntityClass(entity));
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

        dao.createEntity(jsonToEntity(entity, json));
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

        dao.createEntity(address);
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

        SpecifyBean sBean = dao.getById(Integer.parseInt(id), getEntityClass(entity));
        if (sBean != null) {
            dao.deleteEntity(sBean);
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

        String result = dao.editEntity(jsonToEntity(entity, json));

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
        return dao.getAll(Accession.class);
    }

    /**
     * 
     * @param eventId
     * @return 
     */
    @GET
    @Path("search/collectingevent/{eventId}")
    public SpecifyBean testGetCollectingEventByStationFieldNumber(@PathParam("eventId") String eventId) {

        logger.info("getCollectingEventByEventId: {}", eventId);

        return dao.getCollectingEventByStationFieldNumber(eventId);
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

package se.nrm.specify.data.service;

import com.google.gson.Gson;
import com.sun.jersey.spi.resource.PerRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.business.logic.smtp.SMTPLogic;
import se.nrm.specify.business.logic.specify.SpecifyLogic;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.SpecifyBeanWrapper;
import se.nrm.specify.datamodel.Specifyuser;
import se.nrm.specify.datamodel.Taxon; 
import se.nrm.specify.ui.form.data.service.UIDataConstractor; 
import se.nrm.specify.ui.form.data.xml.model.ViewData;

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
    @Inject
    private UIDataConstractor uidata;

    /** Creates a new instance of SpecyResource */
    public SpecyResource() {
    }

    /**
     * Generic method to get an entity by entity id from database.  
     * This method passes in a PathParam entity class name and entity id
     * 
     * @param entity - class name of the entity
     * @param id - entity id
     * 
     * @return entity
     */
    @GET
    @Path("search/entity/{entity}/{id}")
    public SpecifyBeanWrapper getEntity(@PathParam("entity") String entity, @PathParam("id") String id) {

        logger.info("getEntity - entity: {}, id: {}", entity, id);

        SpecifyBean bean = specify.getDao().getById(Integer.parseInt(id), getEntityClass(entity));

        logger.info("result: {}", bean);
        return new SpecifyBeanWrapper(bean);
    }

    /**
     * Generic method to get an entity by entity id from database.  
     * This method passes in a PathParam entity class name and entity id
     * 
     * @param entity - class name of the entity
     * @param id - entity id
     * 
     * @return entity
     */
    @GET
    @Path("search/entitybyid/{entity}/{id}")
    public SpecifyBeanWrapper getEntityById(@PathParam("entity") String entity, @PathParam("id") String id) {

        logger.info("getEntity - entity: {}, id: {}", entity, id);
        SpecifyBean bean = specify.getDao().getById(Integer.parseInt(id), getEntityClass(entity));
        return new SpecifyBeanWrapper(bean);
    }

    /**
     * Generic method to create an entity.  This method passes entity class name and an entity as json string
     * 
     * @param entity - the name of the entity
     * 
     * @param json - an entity in json string
     */
    @POST
    @Path("add/{entity}/{json}")
    public void createJsonEntity(@PathParam("entity") String entity, @PathParam("json") String json) {

        logger.info("createEntity - entity: {}, json: {}", entity, json);

        specify.getDao().createEntity(jsonToEntity(entity, json));
    }

    /**
     * Generic method to create an entity by passing SpecifyBeanWrapper, the entity to be created is wrapped into SpecifyBeanWrapper
     * 
     * @param wrapper - SpecifyBeanWrapper
     */
    @POST
    @Path("add/entity")
    public void createEntity(SpecifyBeanWrapper wrapper) {

        logger.info("createEntity - entity: {}", wrapper.getBean());

        specify.getDao().createEntity(wrapper.getBean());
    }

    /**
     * Generic method delete an entity by entityid
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
     * Generic method update entity in json string
     * 
     * @param entity
     * @param json 
     */
    @PUT
    @Path("update/{entity}/{json}")
    public String update(@PathParam("entity") String entity, @PathParam("json") String json) {

        logger.info("update entity: {}, json: {}", entity, json);

        return specify.getDao().editEntity(jsonToEntity(entity, json));
    }

    /**
     * Generic method update an entity
     * 
     * @param data - SpecifyBeanWrapper
     * @return 
     */
    @PUT
    @Path("updateentity")
    public String updateEntity(SpecifyBeanWrapper data) {

        logger.info("update entity: {}", data.getBean());

        return specify.getDao().editEntity(data.getBean());
    }

    /**
     * Login Specifyuser
     * 
     * @param data - SpecifyBeanWrapper
     * @return SpecifyBeanWrapper
     */
    @PUT
    @Path("loginSpUser")
    public SpecifyBeanWrapper loginSPUser(SpecifyBeanWrapper data) {

        logger.info("loginSPUser: {}", data.getBean());
        return new SpecifyBeanWrapper(specify.getDao().loginSpecifyUser((Specifyuser) data.getBean()));
    }

    /**
     * Generic method update an entity
     * 
     * @param data - SpecifyBeanWrapper
     * @return 
     */
    @PUT
    @Path("logoutSpUser")
    public void logoutSPUser(SpecifyBeanWrapper data) {

        logger.info("logoutSPUser: {}", data.getBean());
        specify.getDao().logoutSpecifyUser((Specifyuser) data.getBean());
    }

    /**
     * Generic method get all entities by entity class name
     * 
     * @param entity - entity class name
     * @return 
     */
    @GET
    @Path("search/allentities/{entity}")
    public SpecifyBeanWrapper getAllEntities(@PathParam("entity") String entity) {

        logger.info("getAllEntities - entity: {}", entity);

        Class clazz = getEntityClass(entity);
        return new SpecifyBeanWrapper(specify.getDao().getAll(clazz));
    }

    @GET
    @Path("search/entity/{jpql}")
    public SpecifyBean getEntityByJPQL(@PathParam("jpql") String jpql) {

        logger.info("getEntityByJPQL: {}", jpql);

        return specify.getDao().getEntityByJPQL(jpql);
    }

    @GET
    @Path("search/entity/namedquery")
    public SpecifyBeanWrapper getEntityByNamedQuery(@Context UriInfo uri) {

        logger.info("getEntityByNamedQuery");

        MultivaluedMap map = uri.getQueryParameters();

        Set<String> set = map.keySet();
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<String> query = (List<String>) map.get("namedquery");
        for (String key : set) {
            if (!key.equals("namedquery")) {
                List<String> params = (List<String>) map.get(key);
                parameters.put(key, params.get(0));
            }
        }

        return new SpecifyBeanWrapper(specify.getDao().getEntityByNamedQuery(query.get(0), parameters));
    }

    /**
     * Generic method search by jpql
     * 
     * @return List<String>
     */
    @GET
    @Path("search/textlist/{jpql}")
    public DataWrapper getTextListByJPQL(@PathParam("jpql") String jpql) {

        logger.info("getTextListByJPQL: {}", jpql);

        return new DataWrapper(specify.getDao().getTextListByJPQL(jpql));
    }

    /**
     * Generic method search by jpql
     * 
     * @return List<String>
     */
    @GET
    @Path("search/synomys/{id}")
    public DataWrapper getTextSynomysByTaxonId(@PathParam("id") String id) {

        logger.info("getTextSynomysByTaxonId: {}", id);

        List<String> list = specify.getDao().getSynomyList(new Taxon(Integer.parseInt(id)));

        return new DataWrapper(list);
    }

    @GET
    @Path("search/entities/namedquery")
    public SpecifyBeanWrapper getAllEntitiesByNamedQuery(@Context UriInfo uri) {

        logger.info("getAllEntitiesByJPQL");

        MultivaluedMap map = uri.getQueryParameters();

        Set<String> set = map.keySet();
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<String> query = (List<String>) map.get("namedquery");
        map.remove("namedquery");

        for (String key : set) {
            List<String> params = (List<String>) map.get(key);
            parameters.put(key, params.get(0));
        }
        return new SpecifyBeanWrapper(specify.getDao().getAllEntitiesByNamedQuery(query.get(0), parameters));
    }

    @GET
    @Path("search/uidata/{discipline}/{view}")
    public SpecifyBeanWrapper fetchUIData(@PathParam("discipline") String discipline, @PathParam("view") String view, @Context UriInfo uri) {
        logger.info("fetchUIData - discipline: {} - view: {}", discipline, view);

        MultivaluedMap map = uri.getQueryParameters();
        
        ViewData viewdata = uidata.initData(discipline, view);
        String jpql = uidata.getJpql(discipline, view, map, viewdata);
        String entity = uidata.getEntityName(viewdata); 
        List<String> fields = uidata.constructSearchFields(viewdata);
        
        List<SpecifyBean> list = (List<SpecifyBean>) specify.getDao().getListByJPQLByFetchGroup(entity, jpql, fields);
        return new SpecifyBeanWrapper(list);
    }

    @GET
    @Path("search/list/bygroup/{entity}")
    public SpecifyBeanWrapper fetchListByGroup(@PathParam("entity") String entity, @Context UriInfo uri) {
        logger.info("fetchByGroup - entity: {}", entity);

        MultivaluedMap map = uri.getQueryParameters();

        List<String> params = (List<String>) map.get("jpql");
        String jpql = params.get(0);
        List<String> fields = (List<String>) map.get(entity);

        List<SpecifyBean> list = (List<SpecifyBean>) specify.getDao().getListByJPQLByFetchGroup(entity, jpql, fields);
        return new SpecifyBeanWrapper(list);
    }

    @GET
    @Path("search/bygroup/bynqry/{namedqry}")
    public SpecifyBeanWrapper fetchGroupByNamedQuery(@PathParam("namedqry") String namedqry, @Context UriInfo uri) {
        logger.info("fetchByGroup - entity: {}", namedqry);

        MultivaluedMap map = uri.getQueryParameters();
        List<String> fields = (List<String>) map.get("fields");
        map.remove("fields");

        Map<String, Object> conditions = new HashMap<String, Object>();

        Set<String> set = map.keySet();
        for (String key : set) {
            List<Object> list = (List<Object>) map.get(key);
            conditions.put(key, list.get(0));
        }

        SpecifyBean bean = specify.getDao().getFetchgroupByNameedQuery(namedqry, conditions, fields);
        return new SpecifyBeanWrapper(bean);
    }

    @GET
    @Path("search/all/bygroup/bynqry/{namedqry}/{classname}")
    public SpecifyBeanWrapper fetchAllGroupByNamedQuery(@PathParam("namedqry") String namedqry, @PathParam("classname") String classname, @Context UriInfo uri) {
        logger.info("fetchByGroup - entity: {}", namedqry);

        MultivaluedMap map = uri.getQueryParameters();
        List<String> fields = (List<String>) map.get("fields");
        map.remove("fields");

        Map<String, Object> conditions = new HashMap<String, Object>();

        Set<String> set = map.keySet();
        for (String key : set) {
            List<Object> list = (List<Object>) map.get(key);
            conditions.put(key, list.get(0));
        }

        List<SpecifyBean> beans = (List<SpecifyBean>) specify.getDao().getAllFetchgroupByNameedQuery(namedqry, classname, conditions, fields);
        return new SpecifyBeanWrapper(beans);
    }

    @GET
    @Path("search/all/bygroup/{entity}")
    public SpecifyBeanWrapper fetchAllByGroup(@PathParam("entity") String entity, @Context UriInfo uri) {
        logger.info("fetchAllByGroup - entity: {}", entity);

        MultivaluedMap map = uri.getQueryParameters();

        Class clazz = getEntityClass(entity);
        List<String> fields = (List<String>) map.get(entity);

        List<SpecifyBean> list = (List<SpecifyBean>) specify.getDao().getAllByFetchGroup(clazz, fields);
        return new SpecifyBeanWrapper(list);
    }

    @GET
    @Path("search/entitiesbyjpql/{jpql}")
    public SpecifyBeanWrapper getAllEntitiesByJPQL(@PathParam("jpql") String jpql) {

        logger.info("getAllEntitiesByJPQL: {}", jpql);
        List<SpecifyBean> list = (List<SpecifyBean>) specify.getDao().getAllEntitiesByJPQL(jpql);
        return new SpecifyBeanWrapper(list);
    }

    @GET
    @Path("search/data/{jpql}")
    public DataWrapper getData(@PathParam("jpql") String jpql) {

        List<Object[]> list = specify.getDao().getDataListByJPQL(jpql);
        for (int i = 0; i < 10; i++) {
            Object[] objs = list.get(i);
            StringBuilder sb = new StringBuilder();
            for (Object obj : objs) {
                sb.append(String.valueOf(obj));
                sb.append(" - ");
            }
            logger.info(sb.toString());
        }
 
        DataWrapper data = new DataWrapper();
        data.setDataList(list);
        return new DataWrapper();
    }

    @POST
    @Path("upload/smtp/{userid}")
    public void uploadData(@PathParam("userid") String userid, DataWrapper wrapper) {

        logger.info("upload data - event: {}, user: {}", wrapper.getEvent());
 
        smtp.saveSMTPBatchData(wrapper, userid);
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

    @GET
    @Path("search/determinationsbyevent/{eventid}/{code}")
    public DataWrapper getDeterminationsByCollectingevent(@PathParam("eventid") String eventid, @PathParam("code") String collectionCode) {

        logger.info("getDeterminationsByCollectingevent: {}, {}", eventid, collectionCode);
        return specify.getDao().getDeterminationsByCollectingEvent(new Collectingevent(Integer.parseInt(eventid)), collectionCode);
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

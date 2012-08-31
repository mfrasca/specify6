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
import org.xml.sax.SAXException; 
import se.nrm.specify.business.logic.specify.SpecifyLogic;
import se.nrm.specify.business.logic.validation.Validation; 
import se.nrm.specify.business.logic.validation.ValidationWrapper; 
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.SpecifyBeanWrapper; 
import se.nrm.specify.business.logic.validation.SpecifyBeanId;    
import se.nrm.specify.business.logic.validation.Status;
import se.nrm.specify.business.logic.validation.ValidationError;  
import se.nrm.specify.business.logic.validation.ValidationWarning;  
import se.nrm.specify.specify.data.jpa.exceptions.DataStoreException;  
import se.nrm.specify.specify.data.jpa.util.Common;
import se.nrm.specify.ui.form.data.service.UIDataConstractor;  
import se.nrm.specify.ui.form.data.util.UIXmlUtil;
import se.nrm.specify.ui.form.data.xml.model.ViewData;

/**
 * REST Web Service
 *
 * @author idali
 */
@PerRequest
@Path("/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
@Stateless
public class SpecyResource {

    Logger logger = LoggerFactory.getLogger(this.getClass());
      
    
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
    @Path("searchbyid/{entity}/{id}")
    public SpecifyBeanWrapper getEntity(@PathParam("entity") String entity, @PathParam("id") int id) throws SAXException {
        logger.info("getEntity - entity: {}, id: {}", entity, id);

        SpecifyBean bean = specify.getDao().findById(id, getEntityClass(entity));
     
        return new SpecifyBeanWrapper(bean);
    }
    
    @GET
    @Path("searchreference/{entity}/{id}")
    public SpecifyBeanWrapper getReference(@PathParam("entity") String entity, @PathParam("id") int id) throws SAXException {
        logger.info("getReference - entity: {}, id: {}", entity, id);

        SpecifyBean bean = specify.getDao().findByReference(id, getEntityClass(entity));
     
        return new SpecifyBeanWrapper(bean);
    }
     
    /**
     * Generic method to create an entity by passing SpecifyBeanWrapper, the entity to be created is wrapped into SpecifyBeanWrapper
     * 
     * @param wrapper - SpecifyBeanWrapper
     *  
     */
    @POST
    @Path("newEntity")
    public ValidationWrapper createNewEntity(SpecifyBeanWrapper wrapper) {
        logger.info("createNewEntity - entity: {}", wrapper.getBean());
         
        Validation validation;
        try {
            validation = specify.createEntity(wrapper.getBean()); 
        } catch (DataStoreException e) {
            validation = new ValidationError();
        } 
        return new ValidationWrapper(validation);
    }
    
    
        /**
     * Generic method delete an entity by entityid
     * 
     * @param entity
     * @param id 
     *  
     */
    @DELETE
    @Path("delete/{entity}/{id}")
    public ValidationWrapper delete(@PathParam("entity") String entity, @PathParam("id") String id) {

        logger.info("delete entity - entity: {}, id: {}", entity, id);

        Validation validation = specify.deleteEntity(new SpecifyBeanId(id, entity));
        return new ValidationWrapper(validation);
    }
 

    /**
     * Generic method update an entity
     * 
     * @param data - SpecifyBeanWrapper
     * 
     * @return 
     *  
     */
    @PUT
    @Path("updateentity")
    public ValidationWrapper updateEntity(SpecifyBeanWrapper wrapper ) {

        logger.info("update entity: {}", wrapper.getBean());
        
        Validation validation = specify.mergeEntity(wrapper.getBean());
        return new ValidationWrapper(validation);
    }
    
    /**
     * Generic method update an entity
     * 
     * @param data - SpecifyBeanWrapper
     * 
     * @return 
     * 
     * TODO: current
     */
    @PUT
    @Path("uidatamerge/{discipline}")
    public ValidationWrapper updatePartialUIDataEntity(SpecifyBeanWrapper wrapper, @PathParam("discipline") String discipline) {

        logger.info("updatePartialUIDataEntity: {} -- {}", wrapper.getBean(), discipline);
        
        SpecifyBean bean = wrapper.getBean();  
          
        ViewData viewdata = uidata.initData(discipline, Common.getInstance().uppercaseFirstCharacter(bean.getEntityName()));   
        if (viewdata != null) {
            List<String> fields = uidata.constructSearchFields(viewdata); 
            Validation validation = specify.mergeEntity(bean, fields);  
            return new ValidationWrapper(validation);
        }  
        return new ValidationWrapper(new ValidationWarning(null, Status.Update, "View not found"));
    }
     
     
    
    /**
     * Generic method update an entity
     * 
     * @param data - SpecifyBeanWrapper
     * 
     * @return  
     */
    @PUT
    @Path("partialmearge")
    public ValidationWrapper updatePartialEntity(SpecifyBeanWrapper wrapper, @Context UriInfo uri) {

        logger.info("updatePartialEntity: {} ", wrapper.getBean());
         
        MultivaluedMap map = uri.getQueryParameters(); 
 
        List<String> fields = (List<String>) map.get("fields");   
  
        Validation validation = specify.mergeEntity(wrapper.getBean(), fields);  
        return new ValidationWrapper(validation);
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
        return new SpecifyBeanWrapper(specify.getDao().findAll(clazz));
    }

    @GET
    @Path("search/entity/{jpql}")
    public SpecifyBean getEntityByJPQL(@PathParam("jpql") String jpql) {

        logger.info("getEntityByJPQL: {}", jpql);

        return specify.getDao().getEntityByJPQL(jpql);
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
            String value =  ((List<String>)map.get(key)).get(0); 
            if(key.contains("Id")) {
                parameters.put(key, (Integer.parseInt(value)));  
            } else {
                parameters.put(key, value); 
            } 
        }
        
        List<SpecifyBean> beans = specify.getDao().getAllEntitiesByNamedQuery(query.get(0), parameters);
        
        logger.info("beans : {}", beans );
        return new SpecifyBeanWrapper(beans);
    }
 
 
    
 
    
    
    
    
    

    @GET
    @Path("search/uidata/{discipline}/{entity}/{id}")
    public SpecifyBean fetchGroupEntityById(@PathParam("discipline") String discipline, @PathParam("entity") String entity, @PathParam("id") String id) {

        logger.info("fetchGroupEntityById: {} - {}", entity, id);

        String entityName = UIXmlUtil.entityNameConvert(entity);
        String namedQuery = entityName + ".findBy" + entity + "Id";

        ViewData viewdata = uidata.initData(discipline, entity);
        if (viewdata != null) {
            List<String> fields = uidata.constructSearchFields(viewdata);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put(UIXmlUtil.entityFieldNameConvert(entity) + "Id", Integer.parseInt(id));
            map.put("namedQuery", namedQuery);
            map.put("fields", fields);

            return specify.getDao().getFetchGroupByNamedQuery(map);
        } else {
            return null;
        }
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
    @Path("search/bygroup/bynqry")
    public SpecifyBeanWrapper fetchGroupByNamedQuery(@Context UriInfo uri) {
        
        logger.info("fetchByGroup");
         
        MultivaluedMap map = uri.getQueryParameters();
 
        List<String> fields = (List<String>) map.get("fields");   
        String neamedQuery = ((List<String>)map.get("namedQuery")).get(0);
        map.remove("namedQuery");
        map.remove("fields");

        Map<String, Object> newMap = new HashMap<String, Object>();
        Set<String> set = map.keySet();
        for (String key : set) {
            String value = ((List<String>) map.get(key)).get(0);
            if (key.endsWith("Id")) {
                newMap.put(key, Integer.parseInt(value));
            } else {
                newMap.put(key, value);
            } 
        } 
        newMap.put("namedQuery", neamedQuery);
        newMap.put("fields", fields);
          
        SpecifyBean bean = specify.getDao().getFetchGroupByNamedQuery(newMap);

        if (bean != null) {
            return new SpecifyBeanWrapper(bean);
        }
        return new SpecifyBeanWrapper();
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

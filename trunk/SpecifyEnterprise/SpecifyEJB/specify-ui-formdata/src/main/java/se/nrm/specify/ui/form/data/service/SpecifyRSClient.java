package se.nrm.specify.ui.form.data.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.datamodel.SpecifyBeanWrapper;
import se.nrm.specify.ui.form.data.ViewCreator;
import se.nrm.specify.ui.form.data.util.FormDataType;
import se.nrm.specify.ui.form.data.util.UIXmlUtil;
import se.nrm.specify.ui.form.data.xml.model.ViewData;

/**
 *
 * @author idali
 */
@Stateless
public class SpecifyRSClient {

    private static Logger logger = LoggerFactory.getLogger(SpecifyRSClient.class);
    private WebResource service;
    @Inject
    private ViewCreator creator;
    private String entityname;

    public SpecifyRSClient() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        service = client.resource(getBaseURI());
    }

    public SpecifyRSClient(ViewCreator creator) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        service = client.resource(getBaseURI());

        this.creator = creator;
    }

    public List<SpecifyBean> getEntityResult(String dicsipline, String view, Map<String, Object> searchConditions) {
        MultivaluedMap queryParams = createSearchData(dicsipline, view, searchConditions);
        SpecifyBeanWrapper wrapper = service.path("search").path("list").path("bygroup").path(entityname).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(SpecifyBeanWrapper.class);

        return (List<SpecifyBean>) wrapper.getBeans();
    }

    public String getJSONResult(String dicsipline, String view, Map<String, Object> searchConditions) {
        MultivaluedMap queryParams = createSearchData(dicsipline, view, searchConditions);
        return service.path("search").path("list").path("bygroup").path(entityname).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public String getXMLResult(String dicsipline, String view, Map<String, Object> searchConditions) {
        MultivaluedMap queryParams = createSearchData(dicsipline, view, searchConditions);
        return service.path("search").path("list").path("bygroup").path(entityname).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);
    }

    private ViewData initData(String discipline, String view) {
        creator.createDataObjectFormatter();
        creator.createDisciplineView(discipline, view);
        return creator.getViewdata(view);
    }

    private List<String> constructSearchFields(ViewData viewdata) {
        List<String> fieldlist = new ArrayList<String>();
        try {
            fieldlist.addAll(viewdata.getViewdef().getFieldList());
            List<String> plugins = viewdata.getViewdef().getPluginList();
            
            Map<String, String> subviews = viewdata.getViewdef().getSubviewList(FormDataType.SUBVIEW); 
            Map<String, String> queryboxes = viewdata.getViewdef().getSubviewList(FormDataType.QUERYCBX);

            int levelCount = 0;
            List<String> subfields = getSubviews("", queryboxes, FormDataType.QUERYCBX, levelCount);
            List<String> subviewfields = getSubviews("", subviews, FormDataType.SUBVIEW, levelCount);

            fieldlist.addAll(plugins);
            fieldlist.addAll(subviewfields);
            fieldlist.addAll(subfields);
              
        } catch (ClassNotFoundException ex) {
            logger.error("ClassNotFoundException: {}", ex.getMessage());
        }
        return fieldlist;
    }

    private MultivaluedMap createSearchData(String discipline, String view, Map<String, Object> searchConditions) {

        logger.info("createSearchData");
         
        ViewData viewdata = initData(discipline, view);
        List<String> fieldlist = constructSearchFields(viewdata);

        String entityName = UIXmlUtil.entityNameConvert(viewdata.getViewdef().getViewDefClass());

        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT o FROM ");
        jpqlSB.append(entityName);
        jpqlSB.append(" o ");

        if (searchConditions != null) {
            jpqlSB.append("where o.");

            int count = 0;
            for (Map.Entry<String, Object> map : searchConditions.entrySet()) {
                Object value = map.getValue();
                if (value instanceof java.lang.String) {
                    jpqlSB.append(map.getKey());
                    jpqlSB.append(" = '");
                    jpqlSB.append(map.getValue());
                    jpqlSB.append("'");
                } else if (value instanceof java.lang.Integer || value instanceof java.lang.Boolean) {
                    jpqlSB.append(map.getKey());
                    jpqlSB.append(" = ");
                    jpqlSB.append(map.getValue());
                }

                count++;
                if (count < searchConditions.size()) {
                    jpqlSB.append(" AND o.");
                }
            }
        }

        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("jpql", jpqlSB.toString());
        queryParams.put(entityName, fieldlist);
 
        this.entityname = entityName;

        return queryParams;
    }

    private List<String> getSubviews(String parent, Map<String, String> map, FormDataType type, int levelCount) {
        
//        logger.info("subview: {} {}", map, parent);
          
        List<String> fieldList = new ArrayList<String>();
        for (Map.Entry<String, String> m : map.entrySet()) {
            try {
                String s = m.getValue();
                ViewData viewData = creator.getViewdata(m.getKey());

                String classname = viewData.getViewdef().getViewDefClass();
                if (type == FormDataType.QUERYCBX) {
                    classname = s;
                }
                classname = UIXmlUtil.entityFieldNameConvert(classname);

                List<String> fields = new ArrayList<String>();
                fields.addAll(viewData.getViewdef().getFieldList());
                fields.addAll(viewData.getViewdef().getPluginList());
                
                if (s.endsWith("s")) {
                    classname = classname.toLowerCase() + "Collection.";
                } else {
                    classname += "ID.";
                }
 
                if (levelCount <= 1) {   
                 
                    Map<String, String> subQueryMap = viewData.getViewdef().getSubviewList(FormDataType.QUERYCBX);
                    Map<String, String> subViewMap = viewData.getViewdef().getSubviewList(FormDataType.SUBVIEW);
                    levelCount++; 
                    
                    if (subQueryMap != null && subQueryMap.size() > 0) {
                        List<String> list = getSubviews(parent + classname, subQueryMap, FormDataType.QUERYCBX, levelCount);
                        fieldList.addAll(list);
                    }
                    
                    if (subViewMap != null && subViewMap.size() > 0) {
                        List<String> list = getSubviews(parent + classname, subViewMap, FormDataType.SUBVIEW, levelCount);
                        fieldList.addAll(list);
                    }
                }
                
            
                        
                
                for (String field : fields) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(parent);
                    sb.append(classname);
                    sb.append(field);
                    fieldList.add(sb.toString());
                }
            } catch (ClassNotFoundException ex) {
                logger.error("ClassNotFoundException: {}", ex.getMessage());
            }
        }
        return fieldList;
    }
 
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/specify-data-service/").build();            // service deployed in local
//        return UriBuilder.fromUri("http://barcode.nrm.se:80/specify-data-service/").build(); 
//        return UriBuilder.fromUri("http://172.16.0.145:8080/jpa-service/").build();           // service deployed in development server
    }

    public static void main(String[] args) {
        
        String discipline = "fish";
        String view = "CollectionObject";

        ViewCreator creator = new ViewCreator(discipline);
        SpecifyRSClient client = new SpecifyRSClient(creator);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("catalogNumber", "NHRS-GULI000000970");
          
//        String json = client.getJSONResult(discipline, view, map);
        String xml = client.getXMLResult(discipline, view, map);
//        List<SpecifyBean> beans = client.getEntityResult("fish", "CollectionObject", map);

//        logger.info("Result in JSON: {}", json);
        logger.info("Result in XML: {}", xml);
//        logger.info("Result in Entity: {}", beans); 
    } 
}

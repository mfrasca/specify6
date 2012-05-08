package se.nrm.specify.ui.form.data;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Address;
import se.nrm.specify.datamodel.Collectionobject;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.ui.form.data.util.FormDataType;
import se.nrm.specify.ui.form.data.util.UIXmlUtil;
import se.nrm.specify.ui.form.data.xml.model.ViewData;

/**
 *
 * @author idali
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private final static String ENTITY_PACKAGE = "se.nrm.specify.datamodel.";
    private static String discipline = "fish";
    private static String viewname = "CollectionObject";
//    private static String viewname = "Borrow"; 
//    private static String viewname = "TreeDefEditor";
//    private static String viewname = "Determination";
    private static WebResource service;
    private static ViewCreator creator;

    public static void main(String[] args) {
        try {
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            service = client.resource(getBaseURI());

            logger.info("start");

            Map<String, List<String>> map = new HashMap<String, List<String>>();

            creator = new ViewCreator(discipline);
            creator.createDataObjectFormatter();
            creator.createDisciplineView(viewname);

            ViewData viewdata = creator.getViewdata(viewname);

            List<String> fieldlist = new ArrayList<String>();
            fieldlist.addAll(viewdata.getViewdef().getFieldList());
            Map<String, String> subviews = viewdata.getViewdef().getSubviewList(FormDataType.SUBVIEW);
            List<String> plugins = viewdata.getViewdef().getPluginList();
            Map<String, String> queryboxes = viewdata.getViewdef().getSubviewList(FormDataType.QUERYCBX);

            List<String> subfields = getSubviews(queryboxes, FormDataType.QUERYCBX);
            List<String> subviewfields = getSubviews(subviews, FormDataType.SUBVIEW);

            fieldlist.addAll(plugins);
            fieldlist.addAll(subviewfields);
            fieldlist.addAll(subfields);

            Map<String, String> searchquery = new HashMap<String, String>();
            searchquery.put("catalogNumber", "NHRS-GULI000000970");

            for (String string : fieldlist) {
                System.out.println("field: " + string);
            }
 
            testCode(UIXmlUtil.entityNameConvert(viewname), fieldlist, searchquery);
        } catch (ClassNotFoundException ex) {
            logger.error("ClassNotFoundException: sss {}", ex.getMessage());
        }
    }

    private static List<String> getSubviews(Map<String, String> map, FormDataType type) {


        List<String> fieldList = new ArrayList<String>();

        for (String key : map.keySet()) {
            try {
                System.out.println("subview: " + key);
                String s = map.get(key);
                ViewData viewData = creator.getViewdata(key);

                String classname = viewData.getViewdef().getViewDefClass();
                if (type == FormDataType.QUERYCBX) {
                    classname = s;
                }

                classname = UIXmlUtil.entityFieldNameConvert(classname);
                if (s.endsWith("s")) {
                    classname += "Collection.";
                } else {
                    classname += "ID.";
                }

                List<String> fields = new ArrayList<String>();
                fields.addAll(viewData.getViewdef().getFieldList());
                fields.addAll(viewData.getViewdef().getPluginList());
                Map<String, String> submap = viewData.getViewdef().getSubviewList(FormDataType.QUERYCBX);

                if (submap != null && submap.size() > 0) {
                    List<String> list = getSubSubviews(classname, submap, FormDataType.QUERYCBX);
                    fieldList.addAll(list);
                }

                for (String field : fields) {
                    fieldList.add(classname + field);
                }
            } catch (ClassNotFoundException ex) {
                logger.error("ClassNotFoundException: {}", ex.getMessage());
            }
        }

        return fieldList;
    }

    private static List<String> getSubSubviews(String parent, Map<String, String> map, FormDataType type) {
        List<String> fieldList = new ArrayList<String>();

        for (String key : map.keySet()) {
            try {
                String s = map.get(key);
                ViewData viewData = creator.getViewdata(key);

                String classname = viewData.getViewdef().getViewDefClass();
                if (type == FormDataType.QUERYCBX) {
                    classname = s;
                }
                classname = UIXmlUtil.entityFieldNameConvert(classname);

                List<String> fields = new ArrayList<String>();
                fields.addAll(viewData.getViewdef().getFieldList());
                fields.addAll(viewData.getViewdef().getPluginList());

                if (s.endsWith("s")) {
                    classname += "Collection.";
                } else {
                    classname += "ID.";
                }
                for (String field : fields) {
                    fieldList.add(parent + classname + field);
                }
            } catch (ClassNotFoundException ex) {
                logger.error("ClassNotFoundException: {}", ex.getMessage());
            }
        }
        return fieldList;
    }

    private static void testCode(String classname, List<String> fields, Map<String, String> searchMap) {


        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT o FROM ");
        jpqlSB.append(classname);
        jpqlSB.append(" o where ");

        for (String key : searchMap.keySet()) {
            jpqlSB.append(" o.");
            jpqlSB.append(key);
            jpqlSB.append(" = '");
            jpqlSB.append(searchMap.get(key));
            jpqlSB.append("'");
        }


        String entity = classname;
        MultivaluedMap queryParams = new MultivaluedMapImpl();

        queryParams.add("jpql", jpqlSB.toString());
        queryParams.put(classname, fields);

        String bean = service.path("search").path("list").path("bygroup").path(entity).queryParams(queryParams).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println("bean: " + bean);

    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/specify-data-service/").build();            // service deployed in local
//        return UriBuilder.fromUri("http://barcode.nrm.se:80/specify-data-service/").build(); 
//        return UriBuilder.fromUri("http://172.16.0.145:8080/jpa-service/").build();           // service deployed in development server
    }
}

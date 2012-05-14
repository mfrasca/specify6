package se.nrm.specify.ui.form.data.service;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.ui.form.data.ViewCreator;
import se.nrm.specify.ui.form.data.util.FormDataType;
import se.nrm.specify.ui.form.data.util.UIXmlUtil;
import se.nrm.specify.ui.form.data.xml.model.ViewData;

/**
 *
 * @author idali
 */
@Stateless
public class UIDataConstractor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Inject
    private ViewCreator creator;
    private String entityname; 

    public UIDataConstractor() {
    }
    
    public UIDataConstractor(ViewCreator creator) { 
        this.creator = creator; 
    }
 

    public ViewData initData(String discipline, String view) {
        creator.createDataObjectFormatter();
        creator.createDisciplineView(discipline, view);
        return creator.getViewdata(view);
    }

    public List<String> constructSearchFields(ViewData viewdata) {
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
 
    public String getEntityName(ViewData viewdata) {
        return UIXmlUtil.entityNameConvert(viewdata.getViewdef().getViewDefClass());
    }

    
    public String getJpql(String discipline, String view, MultivaluedMap<String, List<Object>> searchConditions, ViewData viewdata) {
        
        logger.info("getJpql: {}", searchConditions); 
        String entityName = UIXmlUtil.entityNameConvert(viewdata.getViewdef().getViewDefClass());
        
        StringBuilder jpqlSB = new StringBuilder();
        jpqlSB.append("SELECT o FROM ");
        jpqlSB.append(entityName);
        jpqlSB.append(" o ");

        if (searchConditions != null) {
            jpqlSB.append("where o.");

            int count = 0;
            for (String key : searchConditions.keySet()) {
                Object value =  searchConditions.get(key).get(0); 
                if (value instanceof java.lang.String) {
                    jpqlSB.append(key);
                    jpqlSB.append(" = '");
                    jpqlSB.append(value);
                    jpqlSB.append("'");
                } else if (value instanceof java.lang.Integer || value instanceof java.lang.Boolean) {
                    jpqlSB.append(key);
                    jpqlSB.append(" = ");
                    jpqlSB.append(value);
                }

                count++;
                if (count < searchConditions.size()) {
                    jpqlSB.append(" AND o.");
                }
            }
        }
        
        logger.info("jpql: {}", jpqlSB.toString());
        return jpqlSB.toString(); 
    }
    
    
    public MultivaluedMap createSearchData(String discipline, String view, Map<String, Object> searchConditions) {

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
}

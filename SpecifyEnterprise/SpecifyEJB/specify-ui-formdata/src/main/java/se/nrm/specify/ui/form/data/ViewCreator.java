package se.nrm.specify.ui.form.data;
 
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.ui.form.data.util.CommonString;
import se.nrm.specify.ui.form.data.xml.model.DataFactory;
import se.nrm.specify.ui.form.data.xml.model.DataObjFormatter;
import se.nrm.specify.ui.form.data.xml.model.View;
import se.nrm.specify.ui.form.data.xml.model.ViewData;
import se.nrm.specify.ui.form.data.xml.model.ViewXMLData;
import se.nrm.specify.ui.form.data.xml.model.Viewdef;

/**
 *
 * @author idali
 */
@Stateless
public class ViewCreator {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DATASOURCE_PATH = "/xmlfiles/";
    private static final String VIEW_XML = ".views.xml";
    private static final String DATA_OBJ_FORMATTER_XML = "dataobj_formatters.xml";
    private String discipline;    
    private DataObjFormatter formatter;
    private Map<String, ViewData> map;
    
    public ViewCreator() {
        map = new HashMap<String, ViewData>();
    }
    
    public ViewCreator(String discipline) {
        this.discipline = discipline;
        map = new HashMap<String, ViewData>();
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
      
    public void createDisciplineView(String viewname) {     
        createDisciplineView(discipline, viewname);
    }
    
    public void createDisciplineView(String disciplinename, String viewname) { 
        this.discipline = disciplinename; 
        ViewXMLData viewxmldata = DataFactory.getInstance().getViewDataByDiscipline(disciplinename);
        setViewDataMap(viewxmldata, viewname, CommonString.getInstance().DISCIPLINE);        
    }
    
    public void createView(String xmlview, String viewname) {
        
        StringBuilder sb = new StringBuilder();
        sb.append(DATASOURCE_PATH);
        sb.append(xmlview);
        sb.append(VIEW_XML); 
        
        ViewXMLData viewxmldata = DataFactory.getInstance().getViewDataFromResource(sb.toString());
        setViewDataMap(viewxmldata, viewname, xmlview);        
    }
    
    private void setViewDataMap(ViewXMLData xmldata, String viewname, String xmlname) {
        View view = xmldata.getViewByViewName(viewname);
        if (view != null) {
            Viewdef viewdef = xmldata.getViewDefByView(view);
            map.put(viewname, new ViewData(view, viewdef));
        } else {
            if (xmlname.equals(CommonString.getInstance().COMMON_VIEW_XML)) {
                xmlname = CommonString.getInstance().GLOBAL_VIEW_XML;                
            } else if (xmlname.equals(CommonString.getInstance().DISCIPLINE)) {
                xmlname = CommonString.getInstance().COMMON_VIEW_XML;                
            } else if (xmlname.equals(CommonString.getInstance().GLOBAL_VIEW_XML)) {
                xmlname = null;
            }            
            if (xmlname != null) {
                createView(xmlname, viewname);
            }
        }        
    }
    
    public void createDataObjectFormatter() {
        StringBuilder sb = new StringBuilder();
        sb.append(DATASOURCE_PATH);
        sb.append(DATA_OBJ_FORMATTER_XML);
         
        formatter = DataFactory.getInstance().getDataFormatterByResource(sb.toString());
    }
    
    public Map<String, ViewData> getViewDataMap() {
        return map;
    }
    
    public ViewData getViewdata(String viewName) {
        
//        logger.info("getViewdata: {}", viewName);
        
        boolean isViewFound = findView(viewName);        
        if (isViewFound) {
            return map.get(viewName);
        }
        return null;
    }
    
    private boolean isViewExist(String viewName) {
        return map.containsKey(viewName);
    }
    
    private boolean findView(String viewName) {
        
        if (!isViewExist(viewName)) {                                                       // if view is now created, get view from discipline view xml
            createDisciplineView(viewName);
            if (!isViewExist(viewName)) {                                                   // if view is not in discipline view, get from common view
                createView(CommonString.getInstance().COMMON_VIEW_XML, viewName);
                if (!isViewExist(viewName)) {                                               // if view is not in common view, get from global view
                    createView(CommonString.getInstance().GLOBAL_VIEW_XML, viewName);
                    return isViewExist(viewName);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }    

    public DataObjFormatter getFormatter() {
        return formatter;
    }
}

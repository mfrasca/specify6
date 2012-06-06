package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.ui.form.data.util.CommonString;
import se.nrm.specify.ui.form.data.util.FormDataType;
import se.nrm.specify.ui.form.data.util.ReflectionUtil;
import se.nrm.specify.ui.form.data.util.UIXmlUtil;

/**
 *
 * @author idali
 */
public class Viewdef implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String viewDefName;
    private String viewDefClass;
    private String type;
    private List<Formdata> formdatalist;

    Viewdef(String defname, String viewDefClass, String type, List<Formdata> formdatalist) {
        this.viewDefName = defname;
        this.viewDefClass = viewDefClass;
        this.type = type;
        this.formdatalist = formdatalist;
    }

    public String getViewDefName() {
        return viewDefName;
    }

    public String getViewDefClass() {
        return viewDefClass;
    }

    public List<Formdata> getDefdatalist() {
        return formdatalist;
    }

    public String getType() {
        return type;
    }

    public List<String> getFieldList() throws ClassNotFoundException {

        List<String> fieldlist = new ArrayList<String>();
        Class c = ReflectionUtil.getClassByName(viewDefClass);
        for (Formdata cell : formdatalist) {
            String cellname = cell.getName();
            if (cell.isFieldType()) {


                if ((cell.isTextField() || cell.isCombobox() || cell.isCheckbox())
                        && isFieldNameValide(cellname, c)) {
                    Field field = ReflectionUtil.getField(c, cellname);

                    if (field != null) {
                        if (StringUtils.startsWith(field.getType().getName(), CommonString.getInstance().ENTITY_PACKAGE)) {
                            DataFormatter dataFormatter = DataObjFormatter.getDataFormatter(UIXmlUtil.entityNameConvert(viewDefClass).toLowerCase());

                            List<String> list = dataFormatter.getFields();
                            for (String string : list) {
                                if (StringUtils.contains(string, cell.getName())) {
                                    if (StringUtils.contains(string, ".")) {
                                        String[] strs = StringUtils.split(string, ".");
                                        fieldlist.add(cellname + "." + strs[1]);
                                    } else {
                                        String entityName = UIXmlUtil.entityNameConvert(string); 
                                        dataFormatter = DataObjFormatter.getDataFormatter(entityName.toLowerCase());
                                        addFieldsIntoFieldList(dataFormatter, cellname, fieldlist);
                                    }
                                }
                            }
                        } else {
                            fieldlist.add(cellname);
                        }
                    }
                } else if (cell.isLabel() && isFieldNameValide(cellname, c)) {
                    if (cell.isIsUiFormatted()) {
                        String uiformattername = cell.getUifieldformatter();
                        DataFormatter dataFormatter = DataObjFormatter.getDataFormatter(uiformattername.toLowerCase());
                        addFieldsIntoFieldList(dataFormatter, cellname, fieldlist); 
                    } else {
                        fieldlist.add(cellname);
                    } 
                } 
            } 
        } 
        return fieldlist;
    }

    private boolean isFieldNameValide(String fieldname, Class clazz) {
        return ReflectionUtil.getField(clazz, fieldname) == null ? false : true;
    }

    public Map<String, String> getSubviewList(FormDataType type) {
        Map<String, String> map = new HashMap<String, String>();

        for (Formdata cell : formdatalist) {
            if (cell.isQueryBox() && type == FormDataType.QUERYCBX) {
                String initData = cell.getInitialize();
                String[] initList = StringUtils.split(initData, ";");
                map.put(StringUtils.substringAfter(initList[0], "="), cell.getName());
            } else if (cell.isSubviewType() && type == FormDataType.SUBVIEW) {
                map.put(cell.getSubviewname(), cell.getName());
            }
        }
        return map;
    }

    public List<String> getPluginList() {
        List<String> pluginlist = new ArrayList<String>();

        for (Formdata cell : formdatalist) {
            if (cell.isPlugin()) {
                String initData = cell.getInitialize();
                String[] initList = StringUtils.split(initData, ";");
                if (initData.contains(CommonString.getInstance().XML_ATT_VALUE_PARTIALDATEUI)) {
                    pluginlist.add(StringUtils.substringAfter(initList[1], "="));
                    pluginlist.add(StringUtils.substringAfter(initList[2], "="));
                }
            }
        }
        return pluginlist;
    }

    private void addFieldsIntoFieldList(DataFormatter dataFormatter, String name, List<String> fieldlist) {
        List<String> flist = dataFormatter.getFields();

        for (String s : flist) {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(".");
            sb.append(s);

            fieldlist.add(sb.toString());
        }
    }

    @Override
    public String toString() {
        return "Viewdef - [viewNam:" + viewDefName + " ] - [viewdefclass: " + viewDefClass + " ] - [ type: " + type + " ] ";
    }
}

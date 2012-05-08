package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List; 
import org.apache.commons.lang.StringUtils;
import se.nrm.specify.ui.form.data.util.CommonString;

/**
 *
 * @author idali
 */
public class DefData implements Serializable {

    private List<Formdata> celllist;

    DefData(List<Formdata> celllist) {
        this.celllist = celllist;
    }

    public List<Formdata> getCelllist() {
        return celllist;
    }

    public List<String> getFieldList(Viewdef viewdef) {
        List<String> fieldlist = new ArrayList<String>(); 
        for (Formdata cell : celllist) { 
            if (cell.isFieldType() && (cell.isTextField() || cell.isLabel())) {
                fieldlist.add(cell.getName());
            }  
        }
        
        for(String string : fieldlist) {
            
        }
        return fieldlist;
    }
    
    public List<String> getFieldLabelList() {
        List<String> fieldlist = new ArrayList<String>();

        for (Formdata cell : celllist) {
            if (cell.isFieldType() && cell.isLabel()) {    
                if(cell.isIsUiFormatted()) {
                    String uiformattername = cell.getUifieldformatter();
                    DataFormatter dataFormatter = DataObjFormatter.getDataFormatter(uiformattername.toLowerCase());
                    List<String> list = dataFormatter.getFields();
                     
                    for(String string : list) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(cell.getName());
                        sb.append("ID.");
                        sb.append(string);
                                
                        fieldlist.add(sb.toString());
                    } 
                }  
            } 
        }
        return fieldlist;
    }

    public List<String> getQuerycbx() {
        List<String> fieldlist = new ArrayList<String>();

        for (Formdata cell : celllist) {
            if (cell.isFieldType()) {
                if (cell.isQueryBox()) {
                    fieldlist.add(cell.getName());
                }
            }
        }
        return fieldlist;
    }

    public List<String> getInitializeList() {
        List<String> fieldlist = new ArrayList<String>();

        for (Formdata cell : celllist) {
            if (cell.isQueryBox()) {
                String initData = cell.getInitialize();
                String[] initList = StringUtils.split(initData, ";");
                fieldlist.add(StringUtils.substringAfter(initList[0], "="));
            }
        }
        return fieldlist;
    }

    public List<String> getSubviewList() {
        List<String> subviewlist = new ArrayList<String>();

        for (Formdata cell : celllist) {
            if (cell.isSubviewType()) {
                subviewlist.add(cell.getName());
            }
        }
        return subviewlist;
    }

    public List<String> getPluginList() {
        List<String> pluginlist = new ArrayList<String>();

        for (Formdata cell : celllist) {
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
}

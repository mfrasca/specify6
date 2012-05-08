package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author idali
 */
public class DataFormatter implements Serializable {

    private String name;
    private String title;
    private String classname;
    private boolean isDefault;
    private boolean isSingleSwitchValue;
    private List<String> fields;

    public DataFormatter(String name, String title, String classname, boolean isDefault, boolean isSingleSwitchValue, List<String> fields) {
        this.name = name;
        this.title = title;
        this.classname = classname;
        this.fields = fields;
        this.isDefault = isDefault;
        this.isSingleSwitchValue = isSingleSwitchValue;
    }

    public String getClassname() {
        return classname;
    }

    public List<String> getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public boolean isIsSingleSwitchValue() {
        return isSingleSwitchValue;
    }
 
    
    @Override
    public String toString() {
        return "DataFormatter - [ name: " + name + " ] - [ title: " + title + " ] - [ classname: " + classname + " ]";
    }
}
package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable; 
import se.nrm.specify.ui.form.data.util.FormDataType;

/**
 *
 * @author idali
 */
public class Formdata implements Serializable {

    private String name;
    private String subviewname;
    private FormDataType type;
    private FormDataType uitype;
    private String initialize;
    private String uifieldformatter;

    public Formdata(String name, String subviewname, FormDataType type, FormDataType uitype, String initialize, String uifieldformatter) {
        this.name = name;
        this.subviewname = subviewname;
        this.type = type;
        this.uitype = uitype;
        this.initialize = initialize;
        this.uifieldformatter = uifieldformatter;
    }

    public String getName() {
        return name;
    }

    public String getSubviewname() {
        return subviewname;
    }
     
    public FormDataType getType() {
        return type;
    }

    public FormDataType getUitype() {
        return uitype;
    }

    public String getInitialize() {
        return initialize;
    }

    public String getUifieldformatter() {
        return uifieldformatter;
    }

    public boolean isFieldType() {
        return type == FormDataType.FIELD;
    }

    public boolean isQueryBox() {
        return uitype == FormDataType.QUERYCBX;
    }

    public boolean isPlugin() { 
        return uitype == FormDataType.PLUGIN;
    }

    public boolean isCombobox() { 
        return uitype == FormDataType.COMBOBOX;
    }
    
    public boolean isCheckbox() { 
        return uitype == FormDataType.CHECKBOX;
    }

    public boolean isSubviewType() { 
        return type == FormDataType.SUBVIEW;
    }
    
    public boolean isLabel() { 
        return uitype == FormDataType.LABEL;
    }
    
    public boolean isTextField() {
        return ((uitype == FormDataType.TEXT) || (uitype == FormDataType.FORMATTEDTEXT) || (uitype == FormDataType.TEXTAREABRIEF));
    }
    
    public boolean isIsUiFormatted() {
        return uifieldformatter != null ? true : false;
    }
     
    @Override
    public String toString() {
        return "FormData[ naem = " + name + " ] [ type = " + type.toString() + " ] [ uitype = " + uitype.toString() + " ]";
    }
}

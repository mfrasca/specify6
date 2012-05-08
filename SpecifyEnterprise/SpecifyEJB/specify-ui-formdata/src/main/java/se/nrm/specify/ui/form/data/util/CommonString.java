package se.nrm.specify.ui.form.data.util;

/**
 *
 * @author idali
 */
public class CommonString {

    public final String XML_ATT_NAME = "name";
    public final String XML_ATT_CLASS = "class";
    public final String XML_ATT_TITLE = "title";
    public final String XML_ATT_VIEWDEF = "viewdef";
    public final String XML_ATT_MODE = "mode";
    public final String XML_ATT_DEFAULT = "default";
    public final String XML_ATT_TYPE = "type";
    public final String XML_ATT_UITYPE = "uitype";
    public final String XML_ATT_INIIALIZE = "initialize";
    public final String XML_ATT_VIEWNAME = "viewname";
    public final String XML_ATT_UIFIELDFORMATTER = "uifieldformatter";
    public final String XML_ATT_SINGLE = "single";
    
    public final String XML_ATT_VALUE_FIELD = "field";
    public final String XML_ATT_VALUE_QUERYCBX = "querycbx";
    public final String XML_ATT_VALUE_PLUGIN = "plugin";
    public final String XML_ATT_VALUE_SUBVIEW = "subview";
    public final String XML_ATT_VALUE_COMBOBOX = "combobox";
    public final String XML_ATT_VALUE_CHECKBOX = "checkbox";
    public final String XML_ATT_VALUE_FORMATTEDTEXT = "formattedtext";
    public final String XML_ATT_VALUE_TEXT = "text";
    public final String XML_ATT_VALUE_PANEL = "panel";
    public final String XML_ATT_VALUE_LABEL = "label";
    public final String XML_ATT_VALUE_TABLE = "table";
    public final String XML_ATT_VALUE_TEXTAREABRIEF = "textareabrief";
    
    public final String XML_ATT_VALUE_PARTIALDATEUI = "PartialDateUI";
    
    
    public final String COMMON_VIEW_XML = "common";
    public final String GLOBAL_VIEW_XML = "global";
    public final String DISCIPLINE = "discipline";
    
    private static CommonString instance = null;

    public static synchronized CommonString getInstance() {
        if (instance == null) {
            instance = new CommonString();
        }
        return instance;
    }
}

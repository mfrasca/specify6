package se.nrm.specify.ui.form.data.util;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author idali
 */
public class UIXmlUtil {

    public static String entityNameConvert(final String string) {
        if (string == null || string.length() == 0) {
            throw new NullPointerException("string");
        }
        
        String name = string;
        if(StringUtils.contains(string, ".")) {
            name = StringUtils.substringAfterLast(string, ".");
        } 
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase(); 
    }
    
    public static String entityFieldNameConvert(final String string) {
        if (string == null || string.length() == 0) {
            throw new NullPointerException("string");
        }
        
        String name = string;
        if(StringUtils.contains(string, ".")) {
            name = StringUtils.substringAfterLast(string, ".");
        } 
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    public static boolean isTrue(final String string) {
        if (string == null || string.length() == 0) {
            return Boolean.FALSE;
        }
        return string.equals("true") ? Boolean.TRUE : Boolean.FALSE;
    }

    public static FormDataType getType(String type) { 
        if(type != null) {
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_FIELD)) {
                return FormDataType.FIELD;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_CHECKBOX)) {
                return FormDataType.CHECKBOX;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_FORMATTEDTEXT)) {
                return FormDataType.FORMATTEDTEXT;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_LABEL)) {
                return FormDataType.LABEL;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_PANEL)) {
                return FormDataType.PANEL;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_PLUGIN)) {
                return FormDataType.PLUGIN;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_QUERYCBX)) {
                return FormDataType.QUERYCBX;
            } 
            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_SUBVIEW)) {
                return FormDataType.SUBVIEW;
            }

            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_TABLE)) {
                return FormDataType.TABLE;
            }

            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_TEXT)) {
                return FormDataType.TEXT;
            }

            if (type.equals(CommonString.getInstance().XML_ATT_VALUE_TEXTAREABRIEF)) {
                return FormDataType.TEXTAREABRIEF;
            }
            
            if(type.equals(CommonString.getInstance().XML_ATT_VALUE_COMBOBOX)) {
                return FormDataType.COMBOBOX;
            }
        }
        return FormDataType.FIELD;
    }
}

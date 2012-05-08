package se.nrm.specify.ui.form.data.util;

/**
 *
 * @author idali
 */
public enum FormDataType {
 
    CHECKBOX,
    COMBOBOX,
    FIELD,
    FORMATTEDTEXT,
    LABEL,
    PANEL,
    PLUGIN,
    QUERYCBX,
    SUBVIEW,
    TABLE,
    TEXT,
    TEXTAREABRIEF;

    public String getFormDataTypeText() {
        return this.toString();
    }  
}

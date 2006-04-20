package edu.ku.brc.specify.ui.forms;

import java.awt.Component;
import java.util.List;
import java.util.Map;

import edu.ku.brc.specify.ui.forms.persist.View;
import edu.ku.brc.specify.ui.forms.persist.ViewDef;
import edu.ku.brc.specify.ui.validation.FormValidator;

public interface Viewable
{
    /**
     * Returns a unique name
     * @return a unique name
     */
    public String getName();
    /**
     * Returns the form's type (field, form, table)
     * @return the form's type (field, form, table)
     */
    public ViewDef.ViewType getType();
    
    /**
     * Returns the Form's UI Component
     * @return Returns the Form's UI Component
     */
    public Component getUIComponent();
    
    /**
     * Returns whether it is a sub form or not
     * @return Returns whether it is a sub form or not
     */
    public boolean isSubform();
    
    /**
     * Returns a component by name
     * @param name the name of the component
     * @return the component
     */
    public Component getComp(final String name);

    /**
     * Returns the mapping of name to control
     * @return Returns the mapping of name to control
     */
    public Map<String, Component> getControlMapping();
    
    
    /**
     * Returns the validator for the form
     * @return Returns the validator for the form
     */
    public FormValidator getValidator();
    
    /**
     * Sets the Data Object into the form
     * @param dataObj the data
     */
    public void setDataObj(final Object dataObj);
    
    /**
     * Returns the data object for the form
     * @return Returns the data object for the form
     */
    public Object getDataObj();
    
    /**
     * Sets the Parent Data Object into the Viewable. This is usually when the form will manage a list (Set)
     * of items that are "owned" in the Hibernate sense by a parent object. This is typically
     * a One-to-Many where the parent data object is the "One" and the List (Set) of objects is the "Many"
     * @param parentDataObj the parent data object
     */
    public void setParentDataObj(Object parentDataObj);
    
    /**
     * Returns the parent data object for the form
     * @return Returns the parent data object for the form
     */
    public Object getParentDataObj();
    
    /**
     * Fill the form from the data obj
     */
    public void setDataIntoUI();
    
    /**
     * Get the data from the form and put into the data object
     */
    public void getDataFromUI();
    
    /**
     * Return the data from the UI control
     * @param name the name of the control
     * @return return the value or null if the control was not found.
     */
    public Object getDataFromUIComp(final String name);
    
    /**
     * Sets data into a single field
     * @param name the name of the control
     * @param data the data for the control
     */
    public void setDataIntoUIComp(final String name, Object data);


    /**
     * Returns a subform by name
     * @param name the name of the sub form to be returned
     * @return a subform (Viewable)
     */
    public MultiView getSubView(final String name);
    
    /**
     * List the List with all the names of the cells of type "field"
     * @param fieldNames the list to be filled
     */
    public void getFieldNames(final List<String> fieldNames);
    
    /**
     * Tells the object it is abut to be shown
     * @param show whether it is being shown or hidden
     */
    public void aboutToShow(boolean show);
    
    /**
     * Returns the View definition for this viewable
     * @return the View definition for this viewable
     */
    public View getView();
    
    /**
     * Indicates it should hide the UI that enables switching between different AltViews
     *  This is used for children MultiView when there are only two of the same ViewDef differing only by edit mode or view mode
     * @param hide true - hide, false show
     */
    public void hideMultiViewSwitch(boolean hide);
    
    public void validationWasOK(boolean wasOK);

}

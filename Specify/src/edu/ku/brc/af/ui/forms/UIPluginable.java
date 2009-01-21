/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.af.ui.forms;

import java.beans.PropertyChangeListener;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

/*
 * Implementers 'should' but are not required to send a PropertyChangeEvent with the propertyName set to 'data'
 * when the data has been changed. This way the UI can be updated appropriately.
 * 
 * @code_status Beta
 *
 * @author rods
 *
 */
public interface UIPluginable
{
    /**
     * Sets the property list into the plugin control
     * @param properties the map of properties
     * @param isViewMode indicates whether the plugin is being created to be viewed or is in edit mode.
     */
    public abstract void initialize(Properties properties, boolean isViewMode);
    
    /**
     * Sets the name of the cell that this represents.
     * @param cellName the name
     */
    public abstract void setCellName(String cellName);
    
     /**
     * Sets a single ChangeListener.
     * @param listener the listener
     */
    public abstract void addChangeListener(ChangeListener listener);
    
    /**
     * @return the UI component for the plugin
     */
    public abstract JComponent getUIComponent();
    
    /**
     * Tells the plugin to cleanup because the form is going away. 
     */
    public abstract void shutdown();
    
    /**
     * Registers a property change listener to be notified of changes to the internal state.
     * @param l the listener
     */
    public abstract void addPropertyChangeListener(PropertyChangeListener l);
    
    /**
     * Sets the parent FormViewObj, the parent that contains this plugin.
     * This caller should be guaranteeing that the FormViewObj is completely built.
     * @param parent the FormViewObj parent (or null)
     */
    public abstract void setParent(FormViewObj parent);
    
    /**
     * @return whether the plugin has values
     */
    public abstract boolean isNotEmpty();
    
    /**
     * @return whether this control has data that can be carried forward in a data form.
     */
    public abstract boolean canCarryForward();
    
    /**
     * Returns an array containing the names of the fields that should be 
     * carried forward because this plugin UI manipulates them when the plugin is passed 'this' as the field.
     * If 'canCarryForward' returns false or used a field name from the data object 
     * then this call can/should return null.
     * 
     * @return an array containing the names of the fields.
     */
    public abstract String[] getCarryForwardFields();
    
    /**
     * @return a (localized) title of this type of UIPlugin. This is needed (mostly)
     * for Carry Forward when there is no label describing it and the UI needs to refer to it.
     */
    public abstract String getTitle();
    
}

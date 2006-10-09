/**
* Copyright (C) 2006  The University of Kansas
*
* [INSERT KU-APPROVED LICENSE TEXT HERE]
*
*/
package edu.ku.brc.ui.forms;

import java.beans.PropertyChangeListener;
import java.util.Date;

/**
 * Interface that all Data Model class MUST implement to play nice in the form system.
 * 
 * @author rods
 *
 * @code_status Complete
 *
 */
public interface FormDataObjIFace
{
    
    /**
     * Initialize the object (this is not called by Hibernate).
     */
    public abstract void initialize();
    
    /**
     * The Record Id.
     * @return Record Id.
     */
    public Long getId();
    
    /**
     * Return a String that uniquely identifies this object, usually this is a String field in the object (This should never return null).
     * @returna String that uniquely identifies this object, usually this is a String field in the object.
     */
    public String getIdentityTitle();
    
    /**
     * Return the Timestamp Created.
     * @return the Timestamp Created
     */
    public Date getTimestampCreated();

    /**
     * Sets the Timestamp Created.
     * @param timestampCreated
     */
    public void setTimestampCreated(Date timestampCreated);

    /**
     * Returns the Timestamp Modified.
     * @return the Timestamp Modified.
     */
    public Date getTimestampModified();

    /**
     * Sets the Timestamp Modified.
     * @param timestampModified the new timestamp 
     */
    public void setTimestampModified(Date timestampModified);
    
    /**
     * Returns lastEditedBy.
     * @return lastEditedBy
     */
    public String getLastEditedBy();

    /**
     * Sets lastEditedBy.
     * @param lastEditedBy the text string usually the user name
     */
    public void setLastEditedBy(String lastEditedBy);
    
    /**
     * Add a new foreign key reference to this object.  This method provides
     * a generic way to call other methods such as addAgent(Agent a) or
     * addLocality(Locality l).
     * 
     * @param ref the new foreign key record
     * @param type a String indicating which relationship to add the reference to
     */
    public void addReference(FormDataObjIFace ref, String type);
    
    /**
     * Remvoes a foreign key reference to this object.  This method provides
     * a generic way to call other methods such as removeAgent(Agent a) or
     * removeLocality(Locality l).
     * 
     * @param ref the foreign key record to detach from this object
     * @param type a String indicating which relationship to remove the reference from
     */
    public void removeReference(FormDataObjIFace ref, String type);
    
    /**
     * Returns the internal Table Id.
     * @return the internal Table Id
     */
    public Integer getTableId();
    
    //---------------------------------------------------------------------------
    // Property Change Support
    //---------------------------------------------------------------------------
    
    /**
     * Adds a property change listener for any property (all properties).
     * @param l the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener l);
    
    /**
     * Adds a property change listener.
     * @param propertyName
     * @param l the listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l);
    
    /**
     * Removes a property change listener for any property (all properties).
     * @param l the listener
     */
    public void removePropertyChangeListener(PropertyChangeListener l);
    
    /**
     * Removes a property change listener.
     * @param propertyName the property name
     * @param l the listener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l);
    
}

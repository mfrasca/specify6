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
package edu.ku.brc.ui.forms;

import java.util.List;

import edu.ku.brc.dbsupport.DataProviderSessionIFace;


/**
 * This interface represents the actions that can be performed on a data object by a business rules object.<br>
 * NOTE: This cannot be turned into a generic because of how it is called.
 *
 * @code_status Beta
 * 
 * @author rods, jstewart
 *
 */
public interface BusinessRulesIFace
{

    /**
     * The status of the processing of the business rules.
     */
    public enum STATUS {None, OK, Warning, Error}
    
    /**
     * Notification a form was just filled with data.
     * 
     * @param dataObj the data object that went into the form
     * @param viewable the viewable that recieved the dataObject (using getDataOject would return the same object)
     */
    public void fillForm(Object dataObj, Viewable viewable);
    
    /**
     * Processes the business rules for the data object.
     * 
     * @param dataObj the data object for rthe rules to be processed on.
     * @return the result status after processing the busniess rules.
     */
    public STATUS processBusinessRules(Object dataObj);
    
    /**
     * Returns a list of warnings and errors after processing the business rules.
     * 
     * @return a list of warnings and errors after processing the business rules.
     */
    public List<String> getWarningsAndErrors();
    
    /**
     * Asks if the object can be deleted.
     * 
     * @param dataObj the data object in question
     * @return true if it can be deleted, false if not
     */
    public boolean okToDelete(Object dataObj);
    
    
    /**
     * Returns a message for the user describing what was deleted (intended to be a single line of text).
     * 
     * @param dataObj the data object that will be or has been deleted but still continas its values
     * @return the single line text string
     */
    public String getDeleteMsg(Object dataObj);
    
    /**
     * Called BEFORE saving an object to the DB.  This can be called on newly
     * created objects or existing data objects that have been editted.
     * 
     * @param dataObj the object to be saved
     * @param session the data provider session
     */
    public void beforeSave(Object dataObj, DataProviderSessionIFace session);
    
    /**
     * Called BEFORE committing a transaction in which the passed in data object will
     * be saved to the DB.  When this is called, the transaction has already been started.
     * Any new DB actions will be added to the open transaction.  This can be called on
     * newly created objects or existing data objects that have been editted.
     * 
     * @param dataObj the object that is being saved
     * @param session the data provider session
     */
    public void beforeSaveCommit(Object dataObj, DataProviderSessionIFace session);
    
    /**
     * Called AFTER committing a transaction in which the passed in data object was
     * saved to the DB.  This can be called on newly created objects or existing data
     * objects that have been editted.
     * 
     * @param dataObj the object that was saved
     */
    public boolean afterSaveCommit(Object dataObj);
    
    /**
     * Called BEFORE deleting an object from the DB.  This is called before the object is even
     * slated for deletion within the DB access code (e.g. Hibernate, etc).
     * 
     * @param dataObj the object to be deleted
     * @param session the data provider session
     */
    public void beforeDelete(Object dataObj, DataProviderSessionIFace session);
    
    /**
     * Called BEFORE committing a transaction in which the passed in data object will
     * be deleted from the DB.
     * 
     * @param dataObj the object being deleted
     * @param session the data provider session
     */
    public void beforeDeleteCommit(Object dataObj, DataProviderSessionIFace session);
    
    /**
     * Called AFTER committing a transaction in which the passed in data object was
     * deleted from the DB.
     * 
     * @param dataObj the object that was deleted
     */
    public void afterDeleteCommit(Object dataObj);
    
    public void setObjectIdentity(final Object dataObj, final DraggableRecordIdentifier draggableIcon);
    
}

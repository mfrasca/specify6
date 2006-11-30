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
package edu.ku.brc.ui.validation;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.apache.log4j.Logger;

import edu.ku.brc.ui.GetSetValueIFace;

/**
 * Implements several listener interfaces and listens for the various types of notifications
 * to figure out if the component has changed.
 *
 * @code_status Complete
 * 
 * @author rods
 *
 */
public class DataChangeNotifier implements FocusListener, 
                                           KeyListener, 
                                           ChangeListener, 
                                           ListDataListener,
                                           ListSelectionListener,
                                           PropertyChangeListener, 
                                           ActionListener,
                                           DocumentListener
{
    private static final Logger log = Logger.getLogger(DataChangeNotifier.class);
    
    protected UIValidator                uiv;
    protected boolean                    hasDataChanged = false;
    protected Vector<DataChangeListener> dcListeners    = new Vector<DataChangeListener>();

    protected Component                  comp;
    protected String                     id;
    protected String                     cachedData     = null;
    
    /**
     * Constructor.
     * @param id the id
     * @param comp the component
     * @param uiv the UI validator
     */
    public DataChangeNotifier(String id, Component comp, UIValidator uiv)
    {
        this.id   = id;
        this.comp = comp;
        this.uiv  = uiv;
    }
    
    /**
     * Constructor with no validator
     * @param id the id
     * @param comp the component
     */
    public DataChangeNotifier(String id, Component comp)
    {
        this.id   = id;
        this.comp = comp;
        this.uiv  = null;
    }
    
    /**
     * Add a data change listener that is notified any time the data or state has change for the component.
     * @param dcl the listener
     */
    public void addDataChangeListener(DataChangeListener dcl)
    {
        dcListeners.addElement(dcl);
    }
    
    /**
     * Remove the listener.
     * @param dcl the listener to be removed
     */
    public void removeDataChangeListener(DataChangeListener dcl)
    {
        dcListeners.removeElement(dcl);
    }
    
    /**
     * Notify all the listeners that the data or state was change
     */
    protected void notifyDataChangeListeners()
    {
        //log.debug("DataChangeNotifier - notifyDataChangeListeners");
        
        hasDataChanged = true;
        
        if (comp instanceof UIValidatable)
        {
            ((UIValidatable)comp).setChanged(true);
        }
        
        for (DataChangeListener dcl : dcListeners)
        {
            dcl.dataChanged(id, comp, this);
        }
    }
    
    /**
     * Returns a string value for a control to compare to see if the value has changed.
     * @param component the component to get the value fromn
     * @return Returns a string value for a control to compare to see if the value has changed
     */
    public String getValueForControl(Component component)
    {
        log.debug("DataChangeNotifier - getValueForControl "+component);
        
        if (component instanceof JTextComponent)
        {
            return  ((JTextComponent)component).getText();
            
        } else if (component instanceof JToggleButton)
        {
            return Boolean.toString(((JToggleButton)component).isSelected());
            
        } else if (component instanceof JComboBox)
        {
            return ((JComboBox)component).getSelectedItem().toString();
            
        } else if (component instanceof JList)
        {
            JList list = (JList)component;
            int   inx = list.getSelectedIndex();
            return inx == -1 ? "" : list.getModel().getElementAt(inx).toString();
            
        } else if (component instanceof JCheckBox)
        {
            return ((JCheckBox)component).isSelected() ? "true" : "false";
            
        } else if (component instanceof GetSetValueIFace)
        {
            return ((GetSetValueIFace)component).getValue().toString();
            
        } else
        {
            throw new RuntimeException("Can't get a value for componentonent: "+component);
        }
    }
    
    /**
     * Reset it back to not having been changed and uses the current value as the new cached value.
     */
    public void reset()
    {
        hasDataChanged = false;
        
        if (comp instanceof UIValidatable)
        {
            UIValidatable uiVal = (UIValidatable)comp;
            uiVal.setChanged(false);
            uiVal.setState(UIValidatable.ErrorType.Valid);
            
        } else
        {
            cachedData  = getValueForControl(comp);
        }
    }
    
    /**
     * Manual check to see if the data has changed. It doesn't call any notifications.
     * @return Returns wether the control has changed values
     */
    public boolean manualCheckForDataChanged()
    {
        if (!hasDataChanged)
        {
            if (comp instanceof UIValidatable)
            {
                hasDataChanged = ((UIValidatable)comp).isChanged();
                
            } else if (cachedData != null)
            {
                if (!cachedData.equals(getValueForControl(comp)))
                {
                    hasDataChanged = true;
                }
            } else 
            {
                String str = getValueForControl(comp);
                if (isNotEmpty(str))
                {
                  hasDataChanged = true;
                }
            }
        }
        return hasDataChanged;
    }
    

    /**
     * Returns the hasDataChanged.
     * @return Returns the hasDataChanged.
     */
    public boolean isDataChanged()
    {
        return hasDataChanged;
    }

    /**
     * Sets that the data has changed.
     * @param hasDataChanged The hasDataChanged to set.
     */
    public void setDataChanged(boolean hasDataChanged)
    {
        this.hasDataChanged = hasDataChanged;
    }

    /**
     * Returns the id.
     * @return the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the ID.
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Returns the uiv.
     * @return the uiv.
     */
    public UIValidator getUIV()
    {
        return uiv;
    }
    
    /**
     * Clean up.
     */
    public void cleanUp()
    {
        uiv = null;
        dcListeners.clear();
        comp = null;
    }
    
    /**
     * Helper for processing a change in the data
     */
    protected void doValidateOnChange()
    {
        if (uiv != null && uiv.getType() == UIValidator.Type.Changed)
        {
            uiv.validate();
        }        
        notifyDataChangeListeners();
    }
    
    /**
     * Helper function to return the UIValidator's type if it has a UIV, if not it returns 'None' 
     * @return the UIValidator's type if it has a UIV, if not it returns 'None' 
     */
    public UIValidator.Type getValidationType()
    {
        return uiv != null ? uiv.getType() :  UIValidator.Type.None;
    }
    
    //---------------------------
    // FocusListener
    //---------------------------
    
    /* (non-Javadoc)
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    public void focusGained(FocusEvent e)
    {
        if (!(comp instanceof UIValidatable))
        {
            cachedData = getValueForControl(comp);
        }  
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    public void focusLost(FocusEvent e)
    {
        //log.debug("["+((JTextComponent)comp).getText()+"]["+cachedData+"]");

        if (comp instanceof UIValidatable)
        {
            if (comp instanceof JTextArea)
            {
                int x = 0;
                x++;
            }
            if (((UIValidatable)comp).isChanged())
            {
                if (uiv != null && uiv.getType() == UIValidator.Type.Focus)
                {
                    uiv.validate();
                }
                
                notifyDataChangeListeners();
            }
            
        } else
        {
            // XXX Not sure we should be validating unless it changes
            if (uiv != null && uiv.getType() == UIValidator.Type.Focus)
            {
                uiv.validate();
            }
            
            if (cachedData != null && !cachedData.equals(getValueForControl(comp)))
            {
                notifyDataChangeListeners();
            } 
        }
        


    }
    
    //--------------------------------------------------------
    // ChangeListener
    //--------------------------------------------------------
    public void stateChanged(ChangeEvent e) 
    {
        notifyDataChangeListeners();
    }
    
    //--------------------------------------------------------
    // KeyListener
    //--------------------------------------------------------
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e)    
    {
        // do nothing
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e)
    {
        notifyDataChangeListeners();
        
        if (uiv != null && uiv.getType() == UIValidator.Type.Changed)
        {
            uiv.validate();
        }
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e)
    {
        // do nothing
    }
    
    //--------------------------------------------------------
    // ListDataListener (JComboxBox)
    //--------------------------------------------------------
    
    /* (non-Javadoc)
     * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e)
    {
        doValidateOnChange();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
     */
    public void intervalAdded(ListDataEvent e)
    {
        doValidateOnChange();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
     */
    public void intervalRemoved(ListDataEvent e)
    {
        doValidateOnChange();
    }
    
    //--------------------------------------------------------
    // PropertyChangeListener
    //--------------------------------------------------------
    public void propertyChange(PropertyChangeEvent evt) 
    {
        doValidateOnChange();
    }
    
    //--------------------------------------------------------
    // ActionListener
    //--------------------------------------------------------
    public void actionPerformed(ActionEvent e) 
    {
        doValidateOnChange();
    }
    
    //--------------------------------------------------------
    // ListSelectionListener
    //--------------------------------------------------------
    public void valueChanged(ListSelectionEvent e) 
    {
        doValidateOnChange();
    }
    
    //--------------------------------------------------------
    // DocumentListener
    //--------------------------------------------------------
    
    public void changedUpdate(DocumentEvent e)
    {
        doValidateOnChange();
    }
    
    public void insertUpdate(DocumentEvent e)
    {
        doValidateOnChange();
    }
    
    public void removeUpdate(DocumentEvent e) 
    {
        doValidateOnChange();
    }
}

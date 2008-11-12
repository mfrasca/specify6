package edu.ku.brc.ui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @code_status Beta
 * 
 * @author rods
 *
 */
public interface GhostActionable
{

    /**
     * Asks the destination to perform its action and it is given the source
     * @param source the source object that caused the action to happen
     */
    public abstract void doAction(GhostActionable source);
    
    /**
     * Set the data into the objet
     * @param data
     */
    public abstract void setData(final Object data);
    
    /**
     * Returns the data as type Object
     * @return Return the data
     */
    public abstract Object getData();
    
    /**
     * Support Aggregation, allow consumers to ask for data of a specific class. 
     * That way any data Object that supports the GhostDataAggregatable interface can be asked specifically for 
     * a Class of data.If the data object does implement the  GhostDataAggregatable then the implmenetor should check to see
     * if the data object is an instance of the Class in question.
     * If the object can't vend it it will return null.
     * @param classObj the object of this desired class type
     * @return the object representing that type of object
     */
    public abstract Object getDataForClass(Class<?> classObj);
    
    /**
     * Creates a adpator for the DnD action 
     *
     */
    public abstract void createMouseInputAdapter();
    
    /**
     * Returns the adaptor for tracking mouse drop gestures
     * @return Returns the adaptor for tracking mouse drop gestures
     */
    public abstract GhostMouseInputAdapter getMouseInputAdapter();


    /**
     * Returns a BufferedImage representing a "snapshot" of what the UI looks like before a Drag
     * @return Returns a BufferedImage representing a "snapshot" of what the UI looks like before a Drag
     */
    public abstract BufferedImage getBufferedImage();
    
    
    /**
     * Returns the DataFlavor that this accept as a Drop 
     * @return Returns the DataFlavor that this accept as a Drag or Drop 
     */
    public abstract List<DataFlavor> getDropDataFlavors();
    
    /**
     * Returns the Drag flavor
     * @return Returns the Drag flavor
     */
    public abstract List<DataFlavor> getDragDataFlavors();
    
    /**
     * Sets it into an "active" state which means it should show some UI
     * that implies it can be dropped on.
     * @param isActive true/false
     */
    public abstract void setActive(boolean isActive);
    
    /**
     * Returns whether it is enabled or not.
     * @return whether it is enabled or not.
     */
    public abstract boolean isEnabled();
    
}

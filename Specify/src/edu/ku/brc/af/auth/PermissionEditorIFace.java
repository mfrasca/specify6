/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.af.auth;

import java.awt.Component;
import java.util.List;

import javax.swing.event.ChangeListener;

import edu.ku.brc.af.core.PermissionIFace;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Oct 21, 2008
 *
 */
public interface PermissionEditorIFace
{
    /**
     * @param permissions
     */
    public abstract void setPermissions(List<PermissionIFace> permissions);
    
    /**
     * @return
     */
    public abstract List<PermissionIFace> getPermissions();
    
    /**
     * @return
     */
    public abstract Component getUIComponent();
    
    /**
     * @return
     */
    public abstract boolean hasChanged();
    
    /**
     * 
     */
    public abstract void setChanged(boolean changed);
    
    /**
     * @param l
     */
    public abstract void addChangeListener(ChangeListener l);
    
    /**
     * @param l
     */
    public abstract void removeChangeListener(ChangeListener l);
    
}

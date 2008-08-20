/*
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.specify.tasks.subpane.security;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import edu.ku.brc.specify.datamodel.SpPermission;


/**
 * Wraps an SpPermission object and adds functionality needed to display and handle it when edited 
 * in a permission table.
 *  
 * @author Ricardo
 *
 */
public class GeneralPermissionEditorRow implements PermissionEditorRowIFace
{
	protected SpPermission permission;
	protected SpPermission overrulingPermission;
	protected String       title;
	protected String       description;
	protected ImageIcon    icon;
	
	public GeneralPermissionEditorRow(final SpPermission permission, 
			                          final SpPermission overrulingPermission,
			                          final String title,
                                      final String description,
                                      final ImageIcon icon) 
	{
		this.permission 			= permission;
		this.overrulingPermission 	= overrulingPermission;
		this.title					= title;
        this.description            = description;
        this.icon                   = icon;
	}
	
	public SpPermission getPermission() 
	{
		return permission;
	}
	
	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public void addTableRow(DefaultTableModel model, ImageIcon defaultIcon)
	{
		// FIXME: adjust this to work with 3-state checkbox
		model.addRow(new Object[] 
		        				{ 
		                            icon != null ? icon : defaultIcon, this, 
		        					new Boolean(permission.canView()), 
		        					new Boolean(permission.canAdd()), 
		        					new Boolean(permission.canModify()), 
		        					new Boolean(permission.canDelete())
		        				}
		        			);
	}
	
	public String toString()
	{
		return getTitle();
	}
}

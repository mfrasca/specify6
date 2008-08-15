/*
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.specify.tasks.subpane.security;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import edu.ku.brc.af.auth.specify.permission.BasicSpPermission;
import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.specify.datamodel.SpPermission;
import edu.ku.brc.specify.datamodel.SpPrincipal;

/**
 * This class enumerates Data Objects permissions associated with a principal in a given scope
 * 
 * @code_status Beta
 *
 * Aug 15, 2008
 *
 */
public class DataObjPermissionEnumerator extends PermissionEnumerator 
{
	protected final String permissionBaseName = "DO";

	/**
     * 
     */
    public DataObjPermissionEnumerator()
    {
        super();
    }

    /* (non-Javadoc)
	 * @see edu.ku.brc.specify.tasks.subpane.security.PermissionEnumerator#getPermissions(edu.ku.brc.specify.datamodel.SpPrincipal, java.util.Hashtable, java.util.Hashtable)
	 */
	public List<PermissionEditorRowIFace> getPermissions(final SpPrincipal principal, 
			                                             final Hashtable<String, SpPermission> existingPerms,
			                                             final Hashtable<String, SpPermission> overrulingPerms) 
	{
	    List<PermissionEditorRowIFace> perms = new ArrayList<PermissionEditorRowIFace>();

        // create a special permission that allows user to see all forms
        perms.add(getStarPermission(permissionBaseName, "Forms: permissions to all forms", 
                "Permissions to view, add, modify and delete data using all forms", existingPerms, overrulingPerms));

        // get all views
        for (DBTableInfo tblInfo : DBTableIdMgr.getInstance().getTables())
        {
            String dataObjName  = tblInfo.getShortClassName();
            String taskName     = permissionBaseName + "." + dataObjName;
            
            // first check if there is a permission with this name
            SpPermission perm  = existingPerms.get(taskName);
            SpPermission oPerm = (overrulingPerms != null)? overrulingPerms.get(taskName) : null;
            
            if (perm == null)
            {
                // no permission with this name, create new one
                perm = new SpPermission();
                perm.setName(taskName);
                perm.setActions("");
                perm.setPermissionClass(BasicSpPermission.class.getCanonicalName());
            }
            
            String title = "Data Object: " + tblInfo.getTitle();
            String desc  = "Permissions to view, add, modify and delete data using form " + tblInfo.getTitle(); // I18N
            
            // add newly created permission to the bag that will be returned
            perms.add(new GeneralPermissionEditorRow(perm, oPerm, title, desc));
        }


		return perms;
	}
}

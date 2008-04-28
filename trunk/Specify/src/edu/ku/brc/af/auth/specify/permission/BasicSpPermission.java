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
package edu.ku.brc.af.auth.specify.permission;

import java.security.BasicPermission;
import java.security.Permission;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

/**
 * The BasicSpPermission (Specify Permission) extends BasicPermission to implement
 * permissions for the various Specify objects. An instance of this defines  
 * the permission for a principal (user or user group) to view, modify, add, 
 * or delete a Specify object data object. The property "name" defines the object
 * which permission is being granted to, as well as the type of permission using
 * the following syntax:
 * 
 * <code>permission-type.target</code>
 * 
 * 
 * 
 * Examples of values for the name property are-
 * <ul>
 *   <li>Task.TaxonTreeTask</li>
 *   <li>Task.SecurityAdminTask</li>
 *   <li>Workbench.124</li>
 * </ul>
 */

@SuppressWarnings("serial")
public class BasicSpPermission extends BasicPermission
{
    protected static final Logger log = Logger.getLogger(BasicSpPermission.class);
    //private Integer id;
    private String actions;
    public static String view   = "view";
    public static String modify = "modify";
    public static String add    = "add";
    public static String delete = "delete";

    /**
     * @param id
     * @param name
     * @param actions
     */
    public BasicSpPermission(final String name, final String actions)
    {
        super(name);
        this.actions = actions;
    }

    /**
     * @param id
     * @param name
     */
    public BasicSpPermission(final String name)
    {
        this(name, null);
    }

    /**
     * Checks if permission passed as parameter is implied by this permission
     */
    public boolean implies(Permission p)
    {
		// check implication of name according to BasicPermission rules
    	if (!super.implies(p) || !(p instanceof BasicSpPermission))
    	{
    		// short circuit and return false if p doesn't imply this
    		return false;
    	}

    	// TODO: now check if p implies this according to both permissions actions 
    	return true;
    }
    
    /* (non-Javadoc)
     * @see java.security.BasicPermission#hashCode()
     */
    public int hashCode()
    {
        final HashCodeBuilder b = new HashCodeBuilder();
        b.append(getName());
        b.append(getActions());
        return b.toHashCode();
    }

    /* (non-Javadoc)
     * @see java.security.BasicPermission#equals(java.lang.Object)
     */
    public boolean equals(final Object obj)
    {
        if (!(obj instanceof BasicSpPermission)) { return false; }

        final BasicSpPermission other = (BasicSpPermission)obj;
        final EqualsBuilder b = new EqualsBuilder();
        b.append(getName(), other.getName());
        b.append(getActions(), other.getActions());
        return b.isEquals();
    }

    /* (non-Javadoc)
     * @see java.security.Permission#toString()
     */
    public String toString()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append("(name=");
        buf.append(getName());
        buf.append(", actions=");
        buf.append(getActions());
        buf.append(")");
        return buf.toString();
    }

//    public Integer getId()
//    {
//        return id;
//    }

    /**
     * @return the actions
     */
    public String getActions()
    {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(final String actions)
    {
        this.actions = actions;
    }
}

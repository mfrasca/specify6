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
package edu.ku.brc.specify.datamodel;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;

/**

 */
@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "spprincipal")
public class SpPrincipal extends DataModelObjBase implements java.io.Serializable, Principal, Comparable<SpPrincipal>
{
	// Fields

	protected Integer spUserGroupId;
	protected String name;
    protected String groupType;
	protected String remarks;
	protected String groupSubClass;
	protected UserGroupScope scope;
    protected Set<SpecifyUser> specifyUsers;
    protected Set<SpPermission> permissions;

	// Constructors

	/** default constructor */
	public SpPrincipal()
	{
	}

	/** constructor with id */
	public SpPrincipal(Integer userGroupId)
	{
		this.spUserGroupId = userGroupId;
	}

	// Initializer
	//@Override
	public void initialize()
	{
		super.init();
		spUserGroupId = null;
		name = null;
		remarks = null;
		groupSubClass = null;
		scope = null;
		specifyUsers = new HashSet<SpecifyUser>();
	}

	// End Initializer

	// Property accessors

	/**
	 *
	 */
	@Id
	@GeneratedValue
	@Column(name = "SpPrincipalID", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getUserGroupId()
	{
		return this.spUserGroupId;
	}

	/**
	 * Generic Getter for the ID Property.
	 * @returns ID Property.
	 */
	@Transient
	//@Override
	public Integer getId()
	{
		return this.spUserGroupId;
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
	 */
	@Transient
	//@Override
	public Class<?> getDataClass()
	{
		return SpPrincipal.class;
	}

	//    /* (non-Javadoc)
	//     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#isChangeNotifier()
	//     */
	//    @Transient
	//    //@Override
	//    public boolean isChangeNotifier()
	//    {
	//        return false;
	//    }

	/**
	 * @param spUserGroupId
	 */
	public void setUserGroupId(Integer userGroupId)
	{
		this.spUserGroupId = userGroupId;
	}

	/**
	 *
	 */
	@Column(name = "Name", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 *
	 */
	@Lob
	@Column(name = "Remarks", unique = false, nullable = true, updatable = true, insertable = true)
	public String getRemarks()
	{
		return this.remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	/**
	 *
	 */
	@ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "spPrincipals")
	public Set<SpecifyUser> getSpecifyUsers()
	{
		return this.specifyUsers;
	}

	public void setSpecifyUsers(Set<SpecifyUser> specifyUsers)
	{
		this.specifyUsers = specifyUsers;
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
	 */
	//@Override
	@Transient
	public int getTableId()
	{
		return getClassTableId();
	}

	/**
	 * @return the Table ID for the class.
	 */
	public static int getClassTableId()
	{
		return 522;
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
	 */
	//@Override
	@Transient
	public String getIdentityTitle()
	{
		if (name != null)
			return name;
		return "Undefined";
		//return super.getIdentityTitle();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SpPrincipal))
		{
			return false;
		}
		SpPrincipal other = (SpPrincipal) obj;
		return getName().equals(other.getName());
	}

	/**
	 * Return a string representation of this <code>SpPrincipal</code>.
	 * @return a string representation of this <code>SpPrincipal</code>.
	 */
	public String toString()
	{
		String className = getClass().getName();
		return className.substring(className.lastIndexOf('.') + 1) + ": " + name + " [" + getName()
				+ "]";
	}

	/**
	 * @return the groupSubClass
	 */
	/**
	 *
	 */
	@Column(name = "GroupSubClass", nullable = false, insertable = true, updatable = true, length = 255)
	public String getGroupSubClass()
	{
		return groupSubClass;
	}

	/**
	 * @param groupSubClass the groupSubClass to set
	 */
	public void setGroupSubClass(String groupClass)
	{
		this.groupSubClass = groupClass;
	}

	/**
	 */
	@ManyToMany(cascade = {}, fetch = FetchType.LAZY)
	@JoinTable(name = "spprincipal_sppermission", 
            joinColumns = 
            { 
            @JoinColumn(name = "SpPrincipalID", unique = false, nullable = false, insertable = true, updatable = false) 
            }, 
            inverseJoinColumns = 
            { @JoinColumn(name = "SpPermissionID", unique = false, nullable = false, insertable = true, updatable = false) 
            }
    
    )
	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	public Set<SpPermission> getPermissions()
	{
		return this.permissions;
	}

	/**
	 * @param  permissions- 
	 * void
	 */
	public void setPermissions(Set<SpPermission> permissions)
	{
		this.permissions = permissions;
	}

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userGroupScopeID", unique = false, nullable = true, insertable = true, updatable = true)
	public UserGroupScope getScope() {
		return scope;
	}

	public void setScope(UserGroupScope scope) {
		this.scope = scope;
	}

    @Column(name = "groupType", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
    //----------------------------------------------------------------------
    //-- Comparable Interface
    //----------------------------------------------------------------------
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SpPrincipal obj)
    {
        if (name != null && obj != null && StringUtils.isNotEmpty(obj.name))
        {
            return name.compareTo(obj.name);
        }
        
        // else
        return timestampCreated.compareTo(obj.timestampCreated);
    }
}


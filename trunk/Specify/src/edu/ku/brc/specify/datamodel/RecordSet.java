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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.ImageIcon;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.dbsupport.RecordSetItemIFace;


/**
 * RecordSet generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "recordset")
public class RecordSet extends DataModelObjBase implements java.io.Serializable, RecordSetIFace {

    // Fields
     protected Long                    recordSetId;
     protected String                  name;
     protected Integer                 dbTableId;
     protected String                  remarks;
     protected Set<RecordSetItemIFace> items;
     protected SpecifyUser             specifyUser;
     protected Integer                 ownerPermissionLevel;
     protected Integer                 groupPermissionLevel;
     protected Integer                 allPermissionLevel;
     protected UserGroup               group;


     // Non-Database Memebers
     protected ImageIcon dataSpecificIcon = null;
     
    // Constructors

    /** default constructor */
    public RecordSet() 
    {
        //
    }

    /** constructor with id */
    public RecordSet(Long recordSetId) 
    {
        this.recordSetId = recordSetId;
    }

    /** constructor with id */
    public RecordSet(final String name, final int dbTableId) 
    {
        initialize();
        this.name = name;
        this.dbTableId = dbTableId;
    }

    // Initializer
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.RecordSet#initialize()
     */
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#initialize()
     */
    @Override
    public void initialize()
    {
        super.init();
        recordSetId = null;
        //name = null;
        //tableId = null;
        remarks = null;
        ownerPermissionLevel = null;
        groupPermissionLevel = null;
        allPermissionLevel = null;
        items = new HashSet<RecordSetItemIFace>();
        specifyUser = null;
        group = null;
    }
    // End Initializer

    // Property accessors

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getRecordSetId()
     */
    @Id
    @GeneratedValue
    @Column(name = "RecordSetID", unique = false, nullable = false, insertable = true, updatable = true)
    public Long getRecordSetId()
    {
        return this.recordSetId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setRecordSetId(java.lang.Long)
     */
    public void setRecordSetId(Long recordSetId)
    {
        this.recordSetId = recordSetId;
    }

    @Transient
    @Override
    public Long getId()
    {
        return recordSetId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return RecordSet.class;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#isIndexable()
     */
    @Transient
    @Override
    public boolean isIndexable()
    {
        return false;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getName()
     */
    @Column(name = "Name", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getName()
    {
        return this.name;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setName(java.lang.String)
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getDbTableId()
     */
    @Column(name = "TableID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getDbTableId()
    {
        return this.dbTableId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setDbTableId(java.lang.Integer)
     */
    public void setDbTableId(Integer tableId)
    {
        this.dbTableId = tableId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getItems()
     */
    @OneToMany(cascade = {}, targetEntity=RecordSetItem.class, fetch = FetchType.EAGER, mappedBy = "recordSet")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<RecordSetItemIFace> getItems() {
        return this.items;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setItems(java.util.Set)
     */
    public void setItems(Set<RecordSetItemIFace> items) {
        this.items = items;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SpecifyUserID", unique = false, nullable = false, insertable = true, updatable = true)
    public SpecifyUser getSpecifyUser() {
        return this.specifyUser;
    }
    
    public void setSpecifyUser(SpecifyUser owner) {
        this.specifyUser = owner;
    }
    
    @Transient
    public SpecifyUser getOwner()
    {
        return getSpecifyUser();
    }
    
    public void setOwner(SpecifyUser owner)
    {
        setSpecifyUser(owner);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getRemarks()
     */
    @Lob
    @Column(name="Remarks", unique=false, nullable=true, updatable=true, insertable=true)
    public String getRemarks()
    {
        return remarks;
    }
    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserGroupID", unique = false, nullable = true, insertable = true, updatable = true)
    public UserGroup getGroup() {
        return this.group;
    }
    
    public void setGroup(UserGroup group) {
        this.group = group;
    }
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setRemarks(java.lang.String)
     */
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    /**
     * 
     */
    @Column(name = "OwnerPermissionLevel", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getOwnerPermissionLevel() {
        return this.ownerPermissionLevel;
    }
    
    public void setOwnerPermissionLevel(Integer ownerPermissionLevel) {
        this.ownerPermissionLevel = ownerPermissionLevel;
    }

    /**
     * 
     */
    @Column(name = "GroupPermissionLevel", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getGroupPermissionLevel() {
        return this.groupPermissionLevel;
    }
    
    public void setGroupPermissionLevel(Integer groupPermissionLevel) {
        this.groupPermissionLevel = groupPermissionLevel;
    }

    /**
     * 
     */
    @Column(name = "AllPermissionLevel", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getAllPermissionLevel() {
        return this.allPermissionLevel;
    }
    
    public void setAllPermissionLevel(Integer allPermissionLevel) {
        this.allPermissionLevel = allPermissionLevel;
    }
    // Add Methods


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#addItem(java.lang.Long)
     */
    public RecordSetItemIFace addItem(final Long recordId)
    {
        RecordSetItem rsi = new RecordSetItem(recordId);
        this.items.add(rsi);
        rsi.setRecordSet(this);
        return rsi;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#addItem(java.lang.String)
     */
    public RecordSetItemIFace addItem(final String recordId)
    {
        RecordSetItem rsi = new RecordSetItem(recordId);
        this.items.add(rsi);
        rsi.setRecordSet(this);
        return rsi;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#addItems(edu.ku.brc.specify.datamodel.RecordSetItemIFace)
     */
    public RecordSetItemIFace addItem(final RecordSetItemIFace item)
    {
        this.items.add(item);
        ((RecordSetItem)item).setRecordSet(this);
        return item;
    }

    // Done Add Methods

    // Delete Methods

    
    //--------------------------------------------------------------
    //-- Non-Database Methods
    //--------------------------------------------------------------
    
    /**
     * Returns the only item in the RecordSet and return null if there is more than one.
     * @return returns the only item
     */
    @Transient
    public RecordSetItemIFace getOnlyItem()
    {
        if (items != null && items.size() == 1)
        {
            return items.iterator().next();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getDataSpecificIcon()
     */
    @Transient
    public ImageIcon getDataSpecificIcon()
    {
        return dataSpecificIcon;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setDataSpecificIcon(javax.swing.ImageIcon)
     */
    public void setDataSpecificIcon(ImageIcon dataSpecificIcon)
    {
        this.dataSpecificIcon = dataSpecificIcon;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
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
        return 68;
    }
}

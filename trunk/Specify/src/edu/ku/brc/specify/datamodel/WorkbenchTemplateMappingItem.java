/*
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package edu.ku.brc.specify.datamodel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Items are sorted by ViewOrder
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "workbenchtemplatemappingitem")
@org.hibernate.annotations.Proxy(lazy = false)
public class WorkbenchTemplateMappingItem extends DataModelObjBase implements java.io.Serializable, Comparable<WorkbenchTemplateMappingItem>
{
    public final static short UNKNOWN   = 0;
    public final static short TEXTFIELD = 1;
    public final static short TEXTAREA  = 2;
    
    // Fields

    protected Integer              workbenchTemplateMappingItemId;
    protected String            tableName;
    protected Integer           srcTableId;
    protected String            fieldName;
    protected String            importedColName;
    protected String            caption;
    protected Short             viewOrder;              // The Current View Order
    protected Short             origImportColumnIndex;  // The index from the imported data file
    protected Short             dataFieldLength;        // the length of the data from the specify Schema, usually for strings.
    protected Short             fieldType;              // the type of field 
    protected WorkbenchTemplate workbenchTemplate;
    protected Boolean           isExportableToContent;
    protected Boolean           isIncludedInTitle;
    protected Boolean           isRequired;
    protected Set<WorkbenchDataItem> workbenchDataItems;

    // UI Layout extras
    protected String            metaData;
    protected Short             xCoord;
    protected Short             yCoord;
    protected Boolean           carryForward;
    
    // Transient
    protected Class<?>          dataFieldClass = null;
    
    // Constructors

    /** default constructor */
    public WorkbenchTemplateMappingItem()
    {
        //
    }

    /** constructor with id */
    public WorkbenchTemplateMappingItem(Integer workbenchTemplateMappingItemId)
    {
        this.workbenchTemplateMappingItemId = workbenchTemplateMappingItemId;
    }

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        
        workbenchTemplateMappingItemId = null;
        tableName = null;
        srcTableId = null;
        fieldName = null;
        importedColName = null;
        caption = null;
        viewOrder = null;
        origImportColumnIndex = null;
        dataFieldLength = -1;
        fieldType   = null;
        workbenchTemplate = null;
        metaData = null;
        xCoord = -1;
        yCoord = -1;
        carryForward          = false;
        isExportableToContent = true;
        isIncludedInTitle     = false;
        isRequired            = false;
        
        workbenchDataItems = new HashSet<WorkbenchDataItem>();
        
        // Transient
        dataFieldClass = null;

    }

    // End Initializer

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "WorkbenchTemplateMappingItemID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getWorkbenchTemplateMappingItemId()
    {
        return this.workbenchTemplateMappingItemId;
    }

    /**
     * Generic Getter for the ID Property.
     * 
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.workbenchTemplateMappingItemId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return WorkbenchTemplateMappingItem.class;
    }

    public void setWorkbenchTemplateMappingItemId(Integer workbenchTemplateMappingItemId)
    {
        this.workbenchTemplateMappingItemId = workbenchTemplateMappingItemId;
    }

    /**
     * 
     */
    @Column(name = "TableName", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getTableName()
    {
        return this.tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    /**
     * 
     */
    @Column(name = "TableId", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public Integer getSrcTableId()
    {
        return this.srcTableId;
    }

    public void setSrcTableId(Integer srcTableId)
    {
        this.srcTableId = srcTableId;
    }

    /**
     * 
     */
    @Column(name = "FieldName", unique = false, nullable = true, insertable = true, updatable = true)
    public String getFieldName()
    {
        return this.fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    private static int importedColNameMaxLength = 64;
    /**
     * @return the importedColName
     */
    @Column(name = "ImportedColName", unique = false, nullable = true, insertable = true, updatable = true)
    public String getImportedColName()
    {
        return importedColName;
    }

    /**
     * @param importedColName the importedColName to set
     */
    public void setImportedColName(String importedColName)
    {
        this.importedColName = importedColName;
    }

    /**
     * 
     */
    @Column(name = "Caption", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getCaption()
    {
        return this.caption;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    /**
     * 
     */
    @Column(name = "ViewOrder", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getViewOrder()
    {
        return this.viewOrder;
    }

    public void setViewOrder(Short viewOrder)
    {
        this.viewOrder = viewOrder;
    }

    /**
     * 
     */
    @Column(name = "DataColumnIndex", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getOrigImportColumnIndex()
    {
        return this.origImportColumnIndex;
    }

    public void setOrigImportColumnIndex(Short dataColumnIndex)
    {
        this.origImportColumnIndex = dataColumnIndex;
    }

    /**
     * @return the dataFieldLength
     */
    @Column(name = "DataFieldLength", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getDataFieldLength()
    {
        return dataFieldLength;
    }

    /**
     * @param dataFieldLength the dataFieldLength to set
     */
    public void setDataFieldLength(Short dataLength)
    {
        this.dataFieldLength = dataLength;
    }

    /**
     * @return the fieldType
     */
    @Column(name = "FieldType", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getFieldType()
    {
        return fieldType;
    }

    /**
     * @param fieldType the dataFieldLength to set
     */
    public void setFieldType(Short fieldType)
    {
        this.fieldType = fieldType;
    }
    
    @Column(name = "MetaData", length=128, unique = false, nullable = true, insertable = true, updatable = true)
    public String getMetaData()
    {
        return metaData;
    }

    public void setMetaData(String metaData)
    {
        this.metaData = metaData;
    }

    @Column(name = "XCoord", unique = false, nullable = true, insertable = true, updatable = true)
    public Short getXCoord()
    {
        return xCoord;
    }

    public void setXCoord(Short coord)
    {
        xCoord = coord;
    }

    @Column(name = "YCoord", unique = false, nullable = true, insertable = true, updatable = true)
   public Short getYCoord()
    {
        return yCoord;
    }

    public void setYCoord(Short coord)
    {
        yCoord = coord;
    }

    @Column(name="CarryForward",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getCarryForward()
    {
        return carryForward;
    }

    public void setCarryForward(Boolean carryForward)
    {
        this.carryForward = carryForward;
    }

    @Column(name="IsExportableToContent",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getIsExportableToContent()
    {
        return isExportableToContent;
    }

    public void setIsExportableToContent(Boolean isExportableToContent)
    {
        this.isExportableToContent = isExportableToContent;
    }

    @Column(name="IsIncludedInTitle",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getIsIncludedInTitle()
    {
        return isIncludedInTitle;
    }

    public void setIsIncludedInTitle(Boolean isIncludedInTitle)
    {
        this.isIncludedInTitle = isIncludedInTitle;
    }

    @Column(name="IsRequired",unique=false,nullable=true,updatable=true,insertable=true)
    public Boolean getIsRequired()
    {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired)
    {
        this.isRequired = isRequired;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkbenchTemplateID", unique = false, nullable = false, insertable = true, updatable = true)
    public WorkbenchTemplate getWorkbenchTemplate()
    {
        return this.workbenchTemplate;
    }

    public void setWorkbenchTemplate(WorkbenchTemplate workbenchTemplate)
    {
        this.workbenchTemplate = workbenchTemplate;
    }
    
    
    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "workbenchTemplateMappingItem")
    @org.hibernate.annotations.Cascade( { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<WorkbenchDataItem> getWorkbenchDataItems() 
    {
        return this.workbenchDataItems;
    }
    
    public void setWorkbenchDataItems(Set<WorkbenchDataItem> workbenchDataItems) 
    {
        this.workbenchDataItems = workbenchDataItems;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(WorkbenchTemplateMappingItem obj)
    {
        return viewOrder.compareTo(obj.viewOrder);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return caption != null ? caption : fieldName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public int getTableId()
    {
        return getClassTableId();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        WorkbenchTemplateMappingItem wbtmi = (WorkbenchTemplateMappingItem)super.clone();
        wbtmi.workbenchTemplateMappingItemId = null;
        wbtmi.tableName             = tableName;
        wbtmi.srcTableId            = srcTableId;
        wbtmi.fieldName             = fieldName;
        wbtmi.importedColName       = fieldName;
        wbtmi.caption               = caption;
        wbtmi.viewOrder             = viewOrder;
        wbtmi.origImportColumnIndex = origImportColumnIndex;
        wbtmi.dataFieldLength       = dataFieldLength;
        wbtmi.fieldType             = fieldType;
        wbtmi.workbenchTemplate     = null;
        wbtmi.metaData              = metaData;
        wbtmi.xCoord                = xCoord;
        wbtmi.yCoord                = yCoord;
        wbtmi.carryForward          = carryForward;
        wbtmi.isExportableToContent = isExportableToContent;
        wbtmi.isIncludedInTitle     = isIncludedInTitle;
        wbtmi.isRequired            = isRequired;
        wbtmi.workbenchDataItems    = new HashSet<WorkbenchDataItem>();
        
        wbtmi.timestampCreated      = new Date();
        wbtmi.timestampModified     = null;
        wbtmi.lastEditedBy          = null;

        return wbtmi;
    }

    /**
     * @return the Table ID for the class.
     */
    public static int getClassTableId()
    {
        return 82;
    }

    /**
     * @return the importedColNameMaxLength
     */
    public static int getImportedColNameMaxLength()
    {
        return importedColNameMaxLength;
    }

}

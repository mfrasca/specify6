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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * WorkbenchDataItem generated by hbm2java
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@Table(name = "workbenchdataitem")
public class WorkbenchDataItem extends DataModelObjBase implements java.io.Serializable {

    // Fields    

     protected Long workbenchDataItemId;
     protected String cellData;
     protected String rowNumber;
     protected String columnNumber;
     protected Workbench owner;


    // Constructors

    /** default constructor */
    public WorkbenchDataItem() {
        //
    }
    
    /** constructor with id */
    public WorkbenchDataItem(Long workbenchDataItemId) {
        this.workbenchDataItemId = workbenchDataItemId;
    }
   
    
    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        workbenchDataItemId = null;
        cellData = null;
        rowNumber = null;
        columnNumber = null;
        owner = null;
    }
    
    // End Initializer

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "WorkbenchDataItemID", unique = false, nullable = false, insertable = true, updatable = true)
    public Long getWorkbenchDataItemId() {
        return this.workbenchDataItemId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Long getId()
    {
        return this.workbenchDataItemId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return WorkbenchDataItem.class;
    }
    
    public void setWorkbenchDataItemId(Long workbenchDataItemId) {
        this.workbenchDataItemId = workbenchDataItemId;
    }

    /**
     * 
     */
    @Column(name = "CellData", length = 65535, unique = false, nullable = true, insertable = true, updatable = true)
    public String getCellData() {
        return this.cellData;
    }
    
    public void setCellData(String cellData) {
        this.cellData = cellData;
    }

    /**
     * 
     */
    @Column(name = "RowNumber", length=32, unique = false, nullable = true, insertable = true, updatable = true)
    public String getRowNumber() {
        return this.rowNumber;
    }
    
    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * 
     */
    @Column(name = "ColumnNumber", length = 32, unique = false, nullable = true, insertable = true, updatable = true)
    public String getColumnNumber() {
        return this.columnNumber;
    }
    
    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkbenchID", unique = false, nullable = false, insertable = true, updatable = true)
    public Workbench getWorkbench() {
        return this.owner;
    }
    
    public void setWorkbench(Workbench owner) {
        this.owner = owner;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public Integer getTableId()
    {
        return 80;
    }


}

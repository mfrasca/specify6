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





/**
 * WorkbenchDataItem generated by hbm2java
 */
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
    }
    
    /** constructor with id */
    public WorkbenchDataItem(Long workbenchDataItemId) {
        this.workbenchDataItemId = workbenchDataItemId;
    }
   
    
    // Initializer
    public void initialize()
    {
        throw new RuntimeException("Meg need to implement me!");

    }
    // End Initializer

    // Property accessors

    /**
     * 
     */
    public Long getWorkbenchDataItemId() {
        return this.workbenchDataItemId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    public Long getId()
    {
        return this.workbenchDataItemId;
    }
    
    public void setWorkbenchDataItemId(Long workbenchDataItemId) {
        this.workbenchDataItemId = workbenchDataItemId;
    }

    /**
     * 
     */
    public String getCellData() {
        return this.cellData;
    }
    
    public void setCellData(String cellData) {
        this.cellData = cellData;
    }

    /**
     * 
     */
    public String getRowNumber() {
        return this.rowNumber;
    }
    
    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * 
     */
    public String getColumnNumber() {
        return this.columnNumber;
    }
    
    public void setColumnNumber(String columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * 
     */
    public Workbench getOwner() {
        return this.owner;
    }
    
    public void setOwner(Workbench owner) {
        this.owner = owner;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 80;
    }


}

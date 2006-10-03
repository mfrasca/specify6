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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;




/**
 * Workbench generated by hbm2java
 */
public class Workbench extends DataModelObjBase implements java.io.Serializable {

    // Fields    

     protected Long workbenchId;
     protected String name;
     protected Integer dbTableId;
     protected String remarks;
     protected Integer formId;
     protected String exportInstitutionName;
     protected WorkbenchTemplate workbenchTemplates;
     protected Set<WorkbenchDataItem> workbenchItems;


    // Constructors

    /** default constructor */
    public Workbench() {
    }
    
    /** constructor with id */
    public Workbench(Long workbenchId) {
        this.workbenchId = workbenchId;
    }
   
    // Initializer
    public void initialize()
    {
        workbenchId = null;
        name = null;
        dbTableId = null;
        remarks = null;
        formId = null;
        exportInstitutionName = null;
        timestampCreated = new Date();
        timestampModified = null;
        workbenchTemplates = null;
        workbenchItems = new HashSet<WorkbenchDataItem>();

    }
    // End Initializer

    

    // Property accessors

    /**
     * 
     */
    public Long getWorkbenchId() {
        return this.workbenchId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    public Long getId()
    {
        return this.workbenchId;
    }
    
    public void setWorkbenchId(Long workbenchId) {
        this.workbenchId = workbenchId;
    }

    /**
     *      * Name of workbench
     */
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public Integer getDbTableId() {
        return this.dbTableId;
    }
    
    public void setDbTableId(Integer tableId) {
        this.dbTableId = tableId;
    }

    /**
     * 
     */
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     */
    public Integer getFormId() {
        return this.formId;
    }
    
    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    /**
     Name of Institution being exported from
     */
    public String getExportInstitutionName() {
        return this.exportInstitutionName;
    }
    
    public void setExportInstitutionName(String exportInstitutionName) {
        this.exportInstitutionName = exportInstitutionName;
    }

    /**
     * 
     */
    public WorkbenchTemplate getWorkbenchTemplates() {
        return this.workbenchTemplates;
    }
    
    public void setWorkbenchTemplates(WorkbenchTemplate workbenchTemplates) {
        this.workbenchTemplates = workbenchTemplates;
    }

    /**
     * 
     */
    public Set<WorkbenchDataItem> getWorkbenchItems() {
        return this.workbenchItems;
    }
    
    public void setWorkbenchItems(Set<WorkbenchDataItem> workbenchItems) {
        this.workbenchItems = workbenchItems;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 79;
    }
}

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

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@Table(name = "appresourcedata")
public class AppResourceData extends DataModelObjBase implements java.io.Serializable {

    // Fields    

     protected Long appResourceDataId;
     protected java.sql.Blob data;
     private AppResource AppResource;
     private ViewSetObj ViewSetObj;


    // Constructors

    /** default constructor */
    public AppResourceData() {
        //
    }
    
    /** constructor with id */
    public AppResourceData(Long appResourceDataId) {
        this.appResourceDataId = appResourceDataId;
    }
   
    
    @Override
    public void initialize()
    {
        super.init();
        appResourceDataId = null;
        data = null;
    }
    

    // Property accessors

    /**
     * 
     */
    @Id
    @GeneratedValue
    @Column(name = "AppResourceDataID", unique = false, nullable = false, insertable = true, updatable = true)
    public Long getAppResourceDataId() {
        return this.appResourceDataId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Long getId()
    {
        return this.appResourceDataId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return AppResourceData.class;
    }
    
    public void setAppResourceDataId(Long appResourceDataId) {
        this.appResourceDataId = appResourceDataId;
    }

    /**
     * 
     */
    @Column(name = "data", unique = false, nullable = false, insertable = true, updatable = true, length = 1073741823)
    public java.sql.Blob getData() {
        return this.data;
    }
    
    public void setData(java.sql.Blob data) {
        this.data = data;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AppResourceID", unique = false, nullable = false, insertable = true, updatable = true)
    public AppResource getAppResource() {
        return this.AppResource;
    }
    
    public void setAppResource(AppResource AppResource) {
        this.AppResource = AppResource;
    }

    /**
     * 
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ViewSetObjID", unique = false, nullable = true, insertable = true, updatable = true)
    public ViewSetObj getViewSetObj() {
        return this.ViewSetObj;
    }
    
    public void setViewSetObj(ViewSetObj ViewSetObj) {
        this.ViewSetObj = ViewSetObj;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public Integer getTableId()
    {
        return 84;
    }
}

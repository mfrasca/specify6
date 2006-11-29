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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.dbsupport.RecordSetItemIFace;




/**
 * RecordSet generated by hbm2java
 */
@SuppressWarnings("serial")
public class RecordSet extends DataModelObjBase implements java.io.Serializable, RecordSetIFace {

    // Fields

     protected Long                    recordSetId;
     protected String                  name;
     protected Integer                 dbTableId;
     protected String                  remarks;
     protected Set<RecordSetItemIFace> items;
     protected SpecifyUser             owner;


     // Non-Database Memebers
     protected ImageIcon dataSpecificIcon = null;
     
    // Constructors

    /** default constructor */
    public RecordSet() 
    {
    }

    /** constructor with id */
    public RecordSet(Long recordSetId) 
    {
        this.recordSetId = recordSetId;
    }

    /** constructor with id */
    public RecordSet(final String name, final int dbTableId) 
    {
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
    public void initialize()
    {
        recordSetId = null;
        //name = null;
        //tableId = null;
        remarks = null;
        timestampModified = null;
        timestampCreated = new Date();
        items = new HashSet<RecordSetItemIFace>();
        owner = null;
    }
    // End Initializer

    // Property accessors

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getRecordSetId()
     */
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

    @Override
    public Long getId()
    {
        return recordSetId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    public Class<?> getDataClass()
    {
        return RecordSet.class;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getName()
     */
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
    public Set<RecordSetItemIFace> getItems() {
        return this.items;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setItems(java.util.Set)
     */
    public void setItems(Set<RecordSetItemIFace> items) {
        this.items = items;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getOwner()
     */
    public SpecifyUser getOwner() {
        return this.owner;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setOwner(edu.ku.brc.specify.datamodel.SpecifyUser)
     */
    public void setOwner(SpecifyUser owner) {
        this.owner = owner;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getRemarks()
     */
    public String getRemarks()
    {
        return remarks;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#setRemarks(java.lang.String)
     */
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    // Add Methods


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#addItem(java.lang.Long)
     */
    public void addItem(final Long recordId)
    {
        this.items.add(new RecordSetItem(recordId));
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#addItem(java.lang.String)
     */
    public void addItem(final String recordId)
    {
        this.items.add(new RecordSetItem(recordId));
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#addItems(edu.ku.brc.specify.datamodel.RecordSetItemIFace)
     */
    public void addItems(final RecordSetItemIFace item)
    {
        this.items.add(item);
    }

    // Done Add Methods

    // Delete Methods

    
    //--------------------------------------------------------------
    //-- Non-Database Methods
    //--------------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getDataSpecificIcon()
     */
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
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getTableId()
     */
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.RecordSetIFace#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 68;
    }
}

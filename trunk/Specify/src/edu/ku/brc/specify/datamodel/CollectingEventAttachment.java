/**
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */

package edu.ku.brc.specify.datamodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.ku.brc.util.Orderable;

/**
 * @author jstewart
 * @code_status Alpha
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "collectingeventattachment")
public class CollectingEventAttachment extends DataModelObjBase implements ObjectAttachmentIFace<CollectingEvent>, Orderable, Serializable
{
    protected Integer    collectingEventAttachmentId;
    protected CollectingEvent     collectingEvent;
    protected Attachment attachment;
    protected Integer    ordinal;
    protected String     remarks;
    
    public CollectingEventAttachment()
    {
        // do nothing
    }
    
    public CollectingEventAttachment(Integer id)
    {
        this.collectingEventAttachmentId = id;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        super.init();
        collectingEventAttachmentId = null;
        collectingEvent             = null;
        attachment         = null;
        ordinal            = null;
    }

    @Id
    @GeneratedValue
    @Column(name = "CollectingEventAttachmentID")
    public Integer getCollectingEventAttachmentId()
    {
        return collectingEventAttachmentId;
    }

    public void setCollectingEventAttachmentId(Integer collectingEventAttachmentId)
    {
        this.collectingEventAttachmentId = collectingEventAttachmentId;
    }

    @ManyToOne
    @JoinColumn(name = "CollectingEventID", nullable = false)
    public CollectingEvent getCollectingEvent()
    {
        return collectingEvent;
    }

    public void setCollectingEvent(CollectingEvent collectingEvent)
    {
        this.collectingEvent = collectingEvent;
    }

    @ManyToOne
    @JoinColumn(name = "AttachmentID", nullable = false)
    public Attachment getAttachment()
    {
        return attachment;
    }

    public void setAttachment(Attachment attachment)
    {
        this.attachment = attachment;
    }

    @Column(name = "Ordinal")
    public Integer getOrdinal()
    {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal)
    {
        this.ordinal = ordinal;
    }

    @Lob
    @Column(name = "Remarks")
    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderableFormDataObj#getOrderIndex()
     */
    @Transient
    public int getOrderIndex()
    {
        return (this.ordinal != null) ? this.ordinal : 0;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderableFormDataObj#setOrderIndex(int)
     */
    public void setOrderIndex(int ordinal)
    {
        this.ordinal = ordinal;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getDataClass()
     */
    @Override
    @Transient
    public Class<?> getDataClass()
    {
        return CollectingEventAttachment.class;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getId()
     */
    @Override
    @Transient
    public Integer getId()
    {
        return getCollectingEventAttachmentId();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getTableId()
     */
    @Override
    @Transient
    public int getTableId()
    {
        return 110;
    }

    @Transient
    public CollectingEvent getObject()
    {
        return getCollectingEvent();
    }

    public void setObject(CollectingEvent object)
    {
        setCollectingEvent(object);
    }
}

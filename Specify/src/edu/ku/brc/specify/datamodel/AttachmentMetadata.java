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


/**
 * AttachmentMetadata generated by hbm2java
 */
public class AttachmentMetadata extends DataModelObjBase implements java.io.Serializable
{

    // Fields

    private Long       attachmentMetadataID;
    private String     name;
    private String     value;
    private Attachment attachment;

    // Constructors

    /** default constructor */
    public AttachmentMetadata()
    {
        // do nothing
    }

    /** constructor with id */
    public AttachmentMetadata(Long attachmentMetadataID)
    {
        this.attachmentMetadataID = attachmentMetadataID;
    }

    // Initializer
    @Override
    public void initialize()
    {
        attachmentMetadataID = null;
        name = null;
        value = null;
        attachment = null;
        timestampCreated = new Date();
        timestampModified = null;
        lastEditedBy = null;
    }
    // End Initializer
    
    // Property accessors

    /**
     * 
     */
    public Long getAttachmentMetadataID()
    {
        return this.attachmentMetadataID;
    }

    public void setAttachmentMetadataID(Long attachmentMetadataID)
    {
        this.attachmentMetadataID = attachmentMetadataID;
    }

    @Override
    public Long getId()
    {
        return attachmentMetadataID;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    public Class getDataClass()
    {
        return AttachmentMetadata.class;
    }

    /**
     * 
     */
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
    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * 
     */
    public Attachment getAttachment()
    {
        return this.attachment;
    }

    public void setAttachment(Attachment attachment)
    {
        this.attachment = attachment;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 42;
    }

}

package edu.ku.brc.specify.datamodel;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.util.AttachmentManagerIface;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.Orderable;
import edu.ku.brc.util.thumbnails.Thumbnailer;

public class Attachment extends DataModelObjBase implements Serializable, Orderable
{
    private Long                    attachmentID;
    private String                  mimeType;
    private String                  origFilename;
    private Calendar                fileCreatedDate;
    private Integer                 ordinal;
    private String                  remarks;
    private String                  attachmentLocation;
    private Set<AttachmentMetadata> metadata;
    private Agent                   agent;
    private CollectionObject        collectionObject;
    private CollectingEvent         collectingEvent;
    private Loan                    loan;
    private Locality                locality;
    private Permit                  permit;
    private Preparation             preparation;
    private Taxon                   taxon;

    /** default constructor */
    public Attachment()
    {
        // do nothing
    }

    /** constructor with id */
    public Attachment(Long attachmentID)
    {
        this.attachmentID = attachmentID;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        attachmentID = null;
        mimeType = null;
        origFilename = null;
        fileCreatedDate = null;
        ordinal = null;
        remarks = null;
        attachmentLocation = null;
        timestampCreated = new Date();
        timestampModified = null;
        lastEditedBy = null;
        metadata = new HashSet<AttachmentMetadata>();
        agent = null;
        collectionObject = null;
        collectingEvent = null;
        loan = null;
        locality = null;
        permit = null;
        preparation = null;
        taxon = null;
    }

    public Long getAttachmentID()
    {
        return this.attachmentID;
    }

    public void setAttachmentID(Long attachmentID)
    {
        this.attachmentID = attachmentID;
    }
    
    @Override
    public Long getId()
    {
        return attachmentID;
    }

    public String getMimeType()
    {
        return this.mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public String getOrigFilename()
    {
        return this.origFilename;
    }

    public void setOrigFilename(String origFilename)
    {
        this.origFilename = origFilename;

        // for newly created attachments, setup the attachmentLocation field
        if (this.attachmentID == null && origFilename != null)
        {
            // set the attachmentLocation field
            AttachmentUtils.getAttachmentManager().setStorageLocationIntoAttachment(this);
        }
    }

    public Calendar getFileCreatedDate()
    {
        return this.fileCreatedDate;
    }

    public void setFileCreatedDate(Calendar fileCreatedDate)
    {
        this.fileCreatedDate = fileCreatedDate;
    }

    public Integer getOrdinal()
    {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal)
    {
        this.ordinal = ordinal;
    }

    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getAttachmentLocation()
    {
        return this.attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation)
    {
        this.attachmentLocation = attachmentLocation;
    }

    public Set<AttachmentMetadata> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(Set<AttachmentMetadata> metadata)
    {
        this.metadata = metadata;
    }
    
    public void addAttachmentMetadata(AttachmentMetadata meta)
    {
        this.metadata.add(meta);
        meta.setAttachment(this);
    }
    
    public void removeAttachmentMetadata(AttachmentMetadata meta)
    {
        this.metadata.remove(meta);
        meta.setAttachment(null);
    }

    public Agent getAgent()
    {
        return agent;
    }

    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    public CollectingEvent getCollectingEvent()
    {
        return collectingEvent;
    }

    public void setCollectingEvent(CollectingEvent collectingEvent)
    {
        this.collectingEvent = collectingEvent;
    }

    public CollectionObject getCollectionObject()
    {
        return collectionObject;
    }

    public void setCollectionObject(CollectionObject collectionObject)
    {
        this.collectionObject = collectionObject;
    }

    public Loan getLoan()
    {
        return loan;
    }

    public void setLoan(Loan loan)
    {
        this.loan = loan;
    }

    public Locality getLocality()
    {
        return locality;
    }

    public void setLocality(Locality locality)
    {
        this.locality = locality;
    }

    public Permit getPermit()
    {
        return permit;
    }

    public void setPermit(Permit permit)
    {
        this.permit = permit;
    }

    public Preparation getPreparation()
    {
        return preparation;
    }

    public void setPreparation(Preparation preparation)
    {
        this.preparation = preparation;
    }

    public Taxon getTaxon()
    {
        return taxon;
    }

    public void setTaxon(Taxon taxon)
    {
        this.taxon = taxon;
    }
    
    @Override
    public void addReference(FormDataObjIFace ref, String refType)
    {
        if (ref instanceof AttachmentMetadata)
        {
            addAttachmentMetadata((AttachmentMetadata)ref);
            return;
        }
        super.addReference(ref, refType);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderableFormDataObj#getOrderIndex()
     */
    public int getOrderIndex()
    {
        if (ordinal != null) { return this.ordinal; }
        return 0;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderableFormDataObj#setOrderIndex(int)
     */
    public void setOrderIndex(int ordinal)
    {
        this.ordinal = ordinal;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    public Integer getTableId()
    {
        return 41;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    public String getIdentityTitle()
    {
        return this.getOrigFilename();
    }

    @Override
    public void onDelete()
    {
        // TODO Delete the attachment file from the file storage system
    }

    @Override
    public void onSave()
    {
        // Copy the attachment file to the file storage system
        try
        {
            File origFile = new File(origFilename);
            File thumbFile = File.createTempFile("sp6_thumb_", null);
            thumbFile.deleteOnExit();

            Thumbnailer thumbnailGen = AttachmentUtils.getThumbnailer();
            AttachmentManagerIface attachmentMgr = AttachmentUtils.getAttachmentManager();

            thumbnailGen.generateThumbnail(origFilename, thumbFile.getAbsolutePath());
            attachmentMgr.storeAttachmentFile(this, origFile, thumbFile);
        }
        catch (IOException e)
        {
            // TODO how should I handle this problem?
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate()
    {
        // TODO Possibly update the attachment file in the file storage system
    }
}

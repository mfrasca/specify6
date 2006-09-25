package edu.ku.brc.specify.datamodel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import edu.ku.brc.ui.MimeTypeIconProvider;
import edu.ku.brc.ui.OrderedTrayable;

/**
 * Attachment generated by hbm2java
 */
public class Attachment implements java.io.Serializable, OrderedTrayable
{
    // Fields

    private Long                    attachmentID;
    private String                  mimeType;
    private String                  origFilename;
    private Calendar                fileCreatedDate;
    private Integer                 order;
    private String                  remarks;
    private String                  attachmentLocation;
    private Date                    timestampCreated;
    private Date                    timestampModified;
    private String                  lastEditedBy;
    private Set<AttachmentMetadata> metadata;
    private Agent                   agent;
    private CollectionObject        collectionObject;
    private CollectingEvent         collectingEvent;
    private Loan                    loan;
    private Locality                locality;
    private Permit                  permit;
    private Preparation             preparation;
    private Taxon                   taxon;

    // Constructors

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

    public void initialize()
    {
        attachmentID = null;
        mimeType = null;
        origFilename = null;
        fileCreatedDate = null;
        order = null;
        remarks = null;
        attachmentLocation = null;
        timestampCreated = null;
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

    // Property accessors

    /**
     * 
     */
    public Long getAttachmentID()
    {
        return this.attachmentID;
    }

    public void setAttachmentID(Long attachmentID)
    {
        this.attachmentID = attachmentID;
    }

    /**
     * 
     */
    public String getMimeType()
    {
        return this.mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    /**
     * 
     */
    public String getOrigFilename()
    {
        return this.origFilename;
    }

    public void setOrigFilename(String origFilename)
    {
        this.origFilename = origFilename;
    }

    /**
     * 
     */
    public Calendar getFileCreatedDate()
    {
        return this.fileCreatedDate;
    }

    public void setFileCreatedDate(Calendar fileCreatedDate)
    {
        this.fileCreatedDate = fileCreatedDate;
    }

    public Integer getOrder()
    {
        return order;
    }

    public void setOrder(Integer order)
    {
        this.order = order;
    }

    /**
     * 
     */
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    /**
     * 
     */
    public String getAttachmentLocation()
    {
        return this.attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation)
    {
        this.attachmentLocation = attachmentLocation;
    }

    /**
     * 
     */
    public Date getTimestampCreated()
    {
        return this.timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated)
    {
        this.timestampCreated = timestampCreated;
    }

    /**
     * 
     */
    public Date getTimestampModified()
    {
        return this.timestampModified;
    }

    public void setTimestampModified(Date timestampModified)
    {
        this.timestampModified = timestampModified;
    }

    /**
     * 
     */
    public String getLastEditedBy()
    {
        return this.lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy)
    {
        this.lastEditedBy = lastEditedBy;
    }

    /**
     * 
     */
    public Set<AttachmentMetadata> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(Set<AttachmentMetadata> metadata)
    {
        this.metadata = metadata;
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

    public void setLocality(Locality localitie)
    {
        this.locality = localitie;
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

    public ImageIcon getIcon()
    {
        return MimeTypeIconProvider.getInstance().getIconForMimeType(this.mimeType);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderedTrayable#getOrderIndex()
     */
    public int getOrderIndex()
    {
        if (order != null) { return this.order; }
        return 0;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderedTrayable#setOrderIndex(int)
     */
    public void setOrderIndex(int order)
    {
        this.order = order;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.Trayable#getName()
     */
    public String getName()
    {
        return origFilename;
    }
}

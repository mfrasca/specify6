/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.datamodel;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.ku.brc.util.AttachmentManagerIface;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.Orderable;
import edu.ku.brc.util.thumbnails.Thumbnailer;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "attachments")
public class Attachment extends DataModelObjBase implements Serializable, Orderable
{
    private Integer                 attachmentId;
    private String                  mimeType;
    private String                  origFilename;
    private Calendar                fileCreatedDate;
    private Integer                 ordinal;
    private String                  remarks;
    private String                  attachmentLocation;
    protected Integer               visibility;
    protected String                visibilitySetBy;
    private Set<AttachmentMetadata> metadata;
    private Agent                   agent;
    private CollectionObject        collectionObject;
    private CollectingEvent         collectingEvent;
    private Loan                    loan;
    private Locality                locality;
    private Permit                  permit;
    private Preparation             preparation;
    private Taxon                   taxon;
    private Accession accession;
    private RepositoryAgreement     repositoryAgreement;
    protected ConservEvent          conservEvent;
    protected ConservDescription    conservDescription;
    
    /** default constructor */
    public Attachment()
    {
        // do nothing
    }

    /** constructor with id */
    public Attachment(Integer attachmentId)
    {
        this.attachmentId = attachmentId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        super.init();
        attachmentId = null;
        mimeType = null;
        origFilename = null;
        fileCreatedDate = null;
        ordinal = null;
        remarks = null;
        attachmentLocation = null;
        metadata = new HashSet<AttachmentMetadata>();
        agent = null;
        collectionObject = null;
        collectingEvent = null;
        loan = null;
        locality = null;
        permit = null;
        preparation = null;
        taxon = null;
        conservEvent = null;
        conservDescription = null;

    }

    @Id
    @GeneratedValue
    @Column(name = "AttachmentID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getAttachmentId()
    {
        return this.attachmentId;
    }

    public void setAttachmentId(Integer attachmentId)
    {
        this.attachmentId = attachmentId;
    }
    
    @Transient
    @Override
    public Integer getId()
    {
        return attachmentId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return Attachment.class;
    }

    @Column(name = "MimeType", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getMimeType()
    {
        return this.mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    @Column(name = "OrigFilename", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getOrigFilename()
    {
        return this.origFilename;
    }

    public void setOrigFilename(String origFilename)
    {
        if ((origFilename != null && origFilename.equals(this.origFilename)) || (origFilename == null && this.origFilename == null))
        {
            // nothing is being changed
            return;
        }
        
        this.origFilename = origFilename;

        // for newly created attachments, setup the attachmentLocation field
        if (this.attachmentId == null && origFilename != null)
        {
            // set the attachmentLocation field
            AttachmentUtils.getAttachmentManager().setStorageLocationIntoAttachment(this);
            this.mimeType = AttachmentUtils.getMimeType(origFilename);
        }
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FileCreatedDate", unique = false, nullable = true, insertable = true, updatable = true)
    public Calendar getFileCreatedDate()
    {
        return this.fileCreatedDate;
    }

    public void setFileCreatedDate(Calendar fileCreatedDate)
    {
        this.fileCreatedDate = fileCreatedDate;
    }

    @Column(name = "Ordinal", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getOrdinal()
    {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal)
    {
        this.ordinal = ordinal;
    }

    @Lob
    @Column(name="Remarks", unique=false, nullable=true, updatable=true, insertable=true)
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    @Column(name = "AttachmentLocation", unique = false, nullable = true, insertable = true, updatable = true)
    public String getAttachmentLocation()
    {
        return this.attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation)
    {
        this.attachmentLocation = attachmentLocation;
    }

    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "attachment")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<AttachmentMetadata> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(Set<AttachmentMetadata> metadata)
    {
        this.metadata = metadata;
    }
    
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AgentID", unique = false, nullable = true, insertable = true, updatable = true)
    public Agent getAgent()
    {
        return agent;
    }

    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CollectingEventID", unique = false, nullable = true, insertable = true, updatable = true)
    public CollectingEvent getCollectingEvent()
    {
        return collectingEvent;
    }

    public void setCollectingEvent(CollectingEvent collectingEvent)
    {
        this.collectingEvent = collectingEvent;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CollectionObjectID", unique = false, nullable = true, insertable = true, updatable = true)
    public CollectionObject getCollectionObject()
    {
        return collectionObject;
    }

    public void setCollectionObject(CollectionObject collectionObject)
    {
        this.collectionObject = collectionObject;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "LoanID", unique = false, nullable = true, insertable = true, updatable = true)
    public Loan getLoan()
    {
        return loan;
    }

    public void setLoan(Loan loan)
    {
        this.loan = loan;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "LocalityID", unique = false, nullable = true, insertable = true, updatable = true)
    public Locality getLocality()
    {
        return locality;
    }

    public void setLocality(Locality locality)
    {
        this.locality = locality;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PermitID", unique = false, nullable = true, insertable = true, updatable = true)
    public Permit getPermit()
    {
        return permit;
    }

    public void setPermit(Permit permit)
    {
        this.permit = permit;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PreparationID", unique = false, nullable = true, insertable = true, updatable = true)
    public Preparation getPreparation()
    {
        return preparation;
    }

    public void setPreparation(Preparation preparation)
    {
        this.preparation = preparation;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "TaxonID", unique = false, nullable = true, insertable = true, updatable = true)
    public Taxon getTaxon()
    {
        return taxon;
    }

    public void setTaxon(Taxon taxon)
    {
        this.taxon = taxon;
    }
    
    /**
     *
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ConservEventID", unique = false, nullable = true, insertable = true, updatable = true)
    public ConservEvent getConservEvent()
    {
        return this.conservEvent;
    }

    public void setConservEvent(final ConservEvent conservEvent)
    {
        this.conservEvent = conservEvent;
    }

    /**
     *
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ConservDescriptionID", unique = false, nullable = true, insertable = true, updatable = true)
    public ConservDescription getConservDescription()
    {
        return this.conservDescription;
    }

    public void setConservDescription(final ConservDescription conservDescription)
    {
        this.conservDescription = conservDescription;
    }
    
    /**
     *      * Indicates whether this record can be viewed - by owner, by instituion, or by all
     */
    @Column(name = "Visibility", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public Integer getVisibility() 
    {
        return this.visibility;
    }
    
    public void setVisibility(Integer visibility) 
    {
        this.visibility = visibility;
    }   
    
    /**
     * 
     */
    @Column(name = "VisibilitySetBy", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getVisibilitySetBy() 
    {
        return this.visibilitySetBy;
    }
    
    public void setVisibilitySetBy(String visibilitySetBy) 
    {
        this.visibilitySetBy = visibilitySetBy;
    }   

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.OrderableFormDataObj#getOrderIndex()
     */
    @Transient
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
    @Transient
    public int getTableId()
    {
        return getClassTableId();
    }
    
    /**
     * @return the Table ID for the class.
     */
    public static int getClassTableId()
    {
        return 41;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    {
        if (getOrigFilename() != null)
        {
            File f = new File(getOrigFilename());
            return f.getName();
        }
        return super.getIdentityTitle();
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
        Thumbnailer thumbnailGen = AttachmentUtils.getThumbnailer();
        AttachmentManagerIface attachmentMgr = AttachmentUtils.getAttachmentManager();
        File origFile = new File(origFilename);
        File thumbFile = null;
        
        try
        {
            thumbFile = File.createTempFile("sp6_thumb_", null);
            thumbFile.deleteOnExit();
            thumbnailGen.generateThumbnail(origFilename, thumbFile.getAbsolutePath());
        }
        catch (IOException e)
        {
            // unable to create thumbnail
            thumbFile = null;
        }
        
        try
        {
            attachmentMgr.storeAttachmentFile(this, origFile, thumbFile);
        }
        catch (IOException e)
        {
            // exception while saving copying attachments to storage system
            e.printStackTrace();
        }
    }

    /**
     * @return the accession
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "AccessionID", unique = false, nullable = true, insertable = true, updatable = true)
    public Accession getAccession()
    {
        return this.accession;
    }

    /**
     * @param accession the accession to set
     */
    public void setAccession(Accession accession)
    {
        this.accession = accession;
    }

    /**
     * @return the repositoryAgreement
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "RepositoryAgreementID", unique = false, nullable = true, insertable = true, updatable = true)
    public RepositoryAgreement getRepositoryAgreement()
    {
        return this.repositoryAgreement;
    }

    /**
     * @param repositoryAgreement the repositoryAgreement to set
     */
    public void setRepositoryAgreement(RepositoryAgreement repositoryAgreement)
    {
        this.repositoryAgreement = repositoryAgreement;
    }
    @Override
    public void onUpdate()
    {
        // TODO Possibly update the attachment file in the file storage system
    }
}

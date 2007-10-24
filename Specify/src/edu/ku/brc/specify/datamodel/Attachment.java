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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.ku.brc.util.AttachmentManagerIface;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.thumbnails.Thumbnailer;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "attachment")
public class Attachment extends DataModelObjBase implements Serializable
{
    protected Integer                 attachmentId;
    protected String                  mimeType;
    protected String                  origFilename;
    protected Calendar                fileCreatedDate;
    protected String                  remarks;
    protected String                  attachmentLocation;
    protected Integer                 visibility;
    protected String                  visibilitySetBy;
    protected Set<AttachmentMetadata> metadata;
    
    // transient field
    protected boolean                 storeFile;
    
    // data model classes that can have Attachments
    protected Set<AccessionAttachment>               accessionAttachments;
    protected Set<AgentAttachment>                   agentAttachments;
    protected Set<CollectingEventAttachment>         collectingEventAttachments;
    protected Set<CollectionObjectAttachment>        collectionObjectAttachments;
    protected Set<ConservDescriptionAttachment>      conservDescriptionAttachments;
    protected Set<ConservEventAttachment>            conservEventAttachments;
    protected Set<DNASequenceAttachment>             dnaSequenceAttachments;
    protected Set<FieldNotebookAttachment>           fieldNotebookAttachments;
    protected Set<FieldNotebookPageAttachment>       fieldNotebookPageAttachments;
    protected Set<FieldNotebookPageSetAttachment>    fieldNotebookPageSetAttachments;
    protected Set<LoanAttachment>                    loanAttachments;
    protected Set<LocalityAttachment>                localityAttachments;
    protected Set<PermitAttachment>                  permitAttachments;
    protected Set<PreparationAttachment>             preparationAttachments;
    protected Set<RepositoryAgreementAttachment>     repositoryAgreementAttachments;
    protected Set<TaxonAttachment>                   taxonAttachments;
    
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
        attachmentId       = null;
        mimeType           = null;
        origFilename       = null;
        fileCreatedDate    = null;
        remarks            = null;
        attachmentLocation = null;
        metadata           = new HashSet<AttachmentMetadata>();
        
        storeFile          = false;
        
        accessionAttachments           = new HashSet<AccessionAttachment>();
        agentAttachments               = new HashSet<AgentAttachment>();
        collectionObjectAttachments    = new HashSet<CollectionObjectAttachment>();
        collectingEventAttachments     = new HashSet<CollectingEventAttachment>();
        conservDescriptionAttachments  = new HashSet<ConservDescriptionAttachment>();
        conservEventAttachments        = new HashSet<ConservEventAttachment>();
        loanAttachments                = new HashSet<LoanAttachment>();
        localityAttachments            = new HashSet<LocalityAttachment>();
        permitAttachments              = new HashSet<PermitAttachment>();
        preparationAttachments         = new HashSet<PreparationAttachment>();
        repositoryAgreementAttachments = new HashSet<RepositoryAgreementAttachment>();
        taxonAttachments               = new HashSet<TaxonAttachment>();
    }

    @Id
    @GeneratedValue
    @Column(name = "AttachmentID")
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

    @Column(name = "MimeType", length = 32)
    public String getMimeType()
    {
        return this.mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    @Column(name = "OrigFilename", length = 128)
    public String getOrigFilename()
    {
        return this.origFilename;
    }

    public void setOrigFilename(String origFilename)
    {
//        if ((origFilename != null && origFilename.equals(this.origFilename)) || (origFilename == null && this.origFilename == null))
//        {
//            // nothing is being changed
//            return;
//        }
        
        this.origFilename = origFilename.trim();

//        // if there isn't a set location yet, set one
//        if (origFilename != null && this.attachmentLocation == null)
//        {
//            // set the attachmentLocation field
//            AttachmentUtils.getAttachmentManager().setStorageLocationIntoAttachment(this);
//        }
//        
//        // if a MIME type isn't already set, try to determine it
//        if (this.mimeType == null)
//        {
//            this.mimeType = AttachmentUtils.getMimeType(origFilename);
//        }
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FileCreatedDate")
    public Calendar getFileCreatedDate()
    {
        return this.fileCreatedDate;
    }

    public void setFileCreatedDate(Calendar fileCreatedDate)
    {
        this.fileCreatedDate = fileCreatedDate;
    }

    @Lob
    @Column(name = "Remarks", length = 4096)
    public String getRemarks()
    {
        return this.remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    @Column(name = "AttachmentLocation", length = 128)
    public String getAttachmentLocation()
    {
        return this.attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation)
    {
        this.attachmentLocation = attachmentLocation;
    }

    @Column(name = "Visibility")
    public Integer getVisibility() 
    {
        return this.visibility;
    }
    
    public void setVisibility(Integer visibility) 
    {
        this.visibility = visibility;
    }   

    @Column(name = "VisibilitySetBy", length = 50)
    public String getVisibilitySetBy() {
        return this.visibilitySetBy;
    }
    
    public void setVisibilitySetBy(String visibilitySetBy) {
        this.visibilitySetBy = visibilitySetBy;
    }

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "attachment")
    @Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN} )
    public Set<AttachmentMetadata> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(Set<AttachmentMetadata> metadata)
    {
        this.metadata = metadata;
    }
    
    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<AccessionAttachment> getAccessionAttachments()
    {
        return accessionAttachments;
    }

    public void setAccessionAttachments(Set<AccessionAttachment> accessionAttachments)
    {
        this.accessionAttachments = accessionAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<AgentAttachment> getAgentAttachments()
    {
        return agentAttachments;
    }

    public void setAgentAttachments(Set<AgentAttachment> agentAttachments)
    {
        this.agentAttachments = agentAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<CollectingEventAttachment> getCollectingEventAttachments()
    {
        return collectingEventAttachments;
    }

    public void setCollectingEventAttachments(Set<CollectingEventAttachment> collectingEventAttachments)
    {
        this.collectingEventAttachments = collectingEventAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<CollectionObjectAttachment> getCollectionObjectAttachments()
    {
        return collectionObjectAttachments;
    }

    public void setCollectionObjectAttachments(Set<CollectionObjectAttachment> collectionObjectAttachments)
    {
        this.collectionObjectAttachments = collectionObjectAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<ConservDescriptionAttachment> getConservDescriptionAttachments()
    {
        return conservDescriptionAttachments;
    }

    public void setConservDescriptionAttachments(Set<ConservDescriptionAttachment> conservDescriptionAttachments)
    {
        this.conservDescriptionAttachments = conservDescriptionAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<ConservEventAttachment> getConservEventAttachments()
    {
        return conservEventAttachments;
    }

    public void setConservEventAttachments(Set<ConservEventAttachment> conservEventAttachments)
    {
        this.conservEventAttachments = conservEventAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<DNASequenceAttachment> getDnaSequenceAttachments()
    {
        return dnaSequenceAttachments;
    }

    public void setDnaSequenceAttachments(Set<DNASequenceAttachment> dnaSequenceAttachments)
    {
        this.dnaSequenceAttachments = dnaSequenceAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<FieldNotebookAttachment> getFieldNotebookAttachments()
    {
        return fieldNotebookAttachments;
    }

    public void setFieldNotebookAttachments(Set<FieldNotebookAttachment> fieldNotebookAttachments)
    {
        this.fieldNotebookAttachments = fieldNotebookAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<FieldNotebookPageAttachment> getFieldNotebookPageAttachments()
    {
        return fieldNotebookPageAttachments;
    }

    public void setFieldNotebookPageAttachments(Set<FieldNotebookPageAttachment> fieldNotebookPageAttachments)
    {
        this.fieldNotebookPageAttachments = fieldNotebookPageAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<FieldNotebookPageSetAttachment> getFieldNotebookPageSetAttachments()
    {
        return fieldNotebookPageSetAttachments;
    }

    public void setFieldNotebookPageSetAttachments(Set<FieldNotebookPageSetAttachment> fieldNotebookPageSetAttachments)
    {
        this.fieldNotebookPageSetAttachments = fieldNotebookPageSetAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<LoanAttachment> getLoanAttachments()
    {
        return loanAttachments;
    }

    public void setLoanAttachments(Set<LoanAttachment> loanAttachments)
    {
        this.loanAttachments = loanAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<LocalityAttachment> getLocalityAttachments()
    {
        return localityAttachments;
    }

    public void setLocalityAttachments(Set<LocalityAttachment> localityAttachments)
    {
        this.localityAttachments = localityAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<PreparationAttachment> getPreparationAttachments()
    {
        return preparationAttachments;
    }

    public void setPreparationAttachments(Set<PreparationAttachment> preparationAttachments)
    {
        this.preparationAttachments = preparationAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<PermitAttachment> getPermitAttachments()
    {
        return permitAttachments;
    }

    public void setPermitAttachments(Set<PermitAttachment> permitAttachments)
    {
        this.permitAttachments = permitAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<RepositoryAgreementAttachment> getRepositoryAgreementAttachments()
    {
        return repositoryAgreementAttachments;
    }

    public void setRepositoryAgreementAttachments(Set<RepositoryAgreementAttachment> repositoryAgreementAttachments)
    {
        this.repositoryAgreementAttachments = repositoryAgreementAttachments;
    }

    @OneToMany(mappedBy = "attachment")
    @Cascade( {CascadeType.ALL} )
    public Set<TaxonAttachment> getTaxonAttachments()
    {
        return taxonAttachments;
    }

    public void setTaxonAttachments(Set<TaxonAttachment> taxonAttachments)
    {
        this.taxonAttachments = taxonAttachments;
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

    @Transient
    public boolean isStoreFile()
    {
        return storeFile;
    }

    public void setStoreFile(boolean storeFile)
    {
        this.storeFile = storeFile;
    }

    public void storeFile() throws IOException
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
            thumbFile = null;
        }
        attachmentMgr.storeAttachmentFile(this, origFile, thumbFile);
    }
}

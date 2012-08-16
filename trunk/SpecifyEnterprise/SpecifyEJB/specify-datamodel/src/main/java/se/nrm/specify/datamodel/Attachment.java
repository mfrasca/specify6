package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "attachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a"),
    @NamedQuery(name = "Attachment.findByAttachmentID", query = "SELECT a FROM Attachment a WHERE a.attachmentId = :attachmentID"),
    @NamedQuery(name = "Attachment.findByTimestampCreated", query = "SELECT a FROM Attachment a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Attachment.findByTimestampModified", query = "SELECT a FROM Attachment a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Attachment.findByVersion", query = "SELECT a FROM Attachment a WHERE a.version = :version"),
    @NamedQuery(name = "Attachment.findByAttachmentLocation", query = "SELECT a FROM Attachment a WHERE a.attachmentLocation = :attachmentLocation"),
    @NamedQuery(name = "Attachment.findByCopyrightDate", query = "SELECT a FROM Attachment a WHERE a.copyrightDate = :copyrightDate"),
    @NamedQuery(name = "Attachment.findByCopyrightHolder", query = "SELECT a FROM Attachment a WHERE a.copyrightHolder = :copyrightHolder"),
    @NamedQuery(name = "Attachment.findByCredit", query = "SELECT a FROM Attachment a WHERE a.credit = :credit"),
    @NamedQuery(name = "Attachment.findByDateImaged", query = "SELECT a FROM Attachment a WHERE a.dateImaged = :dateImaged"),
    @NamedQuery(name = "Attachment.findByFileCreatedDate", query = "SELECT a FROM Attachment a WHERE a.fileCreatedDate = :fileCreatedDate"),
    @NamedQuery(name = "Attachment.findByLicense", query = "SELECT a FROM Attachment a WHERE a.license = :license"),
    @NamedQuery(name = "Attachment.findByMimeType", query = "SELECT a FROM Attachment a WHERE a.mimeType = :mimeType"),
    @NamedQuery(name = "Attachment.findByOrigFilename", query = "SELECT a FROM Attachment a WHERE a.origFilename = :origFilename"),
    @NamedQuery(name = "Attachment.findByTitle", query = "SELECT a FROM Attachment a WHERE a.title = :title"),
    @NamedQuery(name = "Attachment.findByVisibility", query = "SELECT a FROM Attachment a WHERE a.visibility = :visibility"),
    @NamedQuery(name = "Attachment.findByAttachmentImageAttributeID", query = "SELECT a FROM Attachment a WHERE a.attachmentImageAttribute = :attachmentImageAttributeID")})
public class Attachment extends BaseEntity {
  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttachmentID")
    private Integer attachmentId;
     
    @Size(max = 128)
    @Column(name = "AttachmentLocation")
    private String attachmentLocation;
    
    @Size(max = 64)
    @Column(name = "CopyrightDate")
    private String copyrightDate;
    
    @Size(max = 64)
    @Column(name = "CopyrightHolder")
    private String copyrightHolder;
    
    @Size(max = 64)
    @Column(name = "Credit")
    private String credit;
    
    @Column(name = "FileCreatedDate")
    @Temporal(TemporalType.DATE)
    private Date fileCreatedDate;
    
    @Size(max = 64)
    @Column(name = "DateImaged")
    private String dateImaged;
    
    @Size(max = 64)
    @Column(name = "License")
    private String license;
    
    @Size(max = 64)
    @Column(name = "MimeType")
    private String mimeType;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "OrigFilename")
    private String origFilename;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "Title")
    private String title;
    
    @Column(name = "Visibility")
    private Short visibility;
    
    @Column(name = "AttachmentImageAttributeID")
    private Integer attachmentImageAttribute;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Loanattachment> loanAttachments;
    
    @OneToMany(mappedBy = "attachment", cascade= CascadeType.ALL)
    private Collection<Attachmentmetadata> metadata;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Agentattachment> agentAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Fieldnotebookpagesetattachment> fieldNotebookPageSetAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Repositoryagreementattachment> repositoryAgreementAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Permitattachment> permitAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Dnasequenceattachment> dnaSequenceAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Preparationattachment> preparationAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Localityattachment> localityAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Collectionobjectattachment> collectionObjectAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Taxonattachment> taxonAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Fieldnotebookpageattachment> fieldNotebookPageAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Fieldnotebookattachment> fieldNotebookAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Conserveventattachment> conservEventAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Accessionattachment> accessionAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Conservdescriptionattachment> conservDescriptionAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Collectingeventattachment> collectingEventAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Dnasequencerunattachment> dnasequencerunattachments;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetBy;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachment")
    private Collection<Attachmenttag> tags;

    public Attachment() {
    }

    public Attachment(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Attachment(Integer attachmentId, Date timestampCreated, String origFilename) {
        super(timestampCreated);
        this.attachmentId = attachmentId; 
        this.origFilename = origFilename;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (attachmentId != null) ? attachmentId.toString() : "0";
    }
    public Integer getAttachmentID() {
        return attachmentId;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentLocation() {
        return attachmentLocation;
    }

    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

 
 

    public String getCopyrightDate() {
        return copyrightDate;
    }

    public void setCopyrightDate(String copyrightDate) {
        this.copyrightDate = copyrightDate;
    }

    public String getCopyrightHolder() {
        return copyrightHolder;
    }

    public void setCopyrightHolder(String copyrightHolder) {
        this.copyrightHolder = copyrightHolder;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDateImaged() {
        return dateImaged;
    }

    public void setDateImaged(String dateImaged) {
        this.dateImaged = dateImaged;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getOrigFilename() {
        return origFilename;
    }

    public void setOrigFilename(String origFilename) {
        this.origFilename = origFilename;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getVisibility() {
        return visibility;
    }

    public void setVisibility(Short visibility) {
        this.visibility = visibility;
    }

    public Integer getAttachmentImageAttribute() {
        return attachmentImageAttribute;
    }

    public void setAttachmentImageAttribute(Integer attachmentImageAttribute) {
        this.attachmentImageAttribute = attachmentImageAttribute;
    }

    @XmlTransient
    public Collection<Dnasequencerunattachment> getDnasequencerunattachments() {
        return dnasequencerunattachments;
    }

    public void setDnasequencerunattachments(Collection<Dnasequencerunattachment> dnasequencerunattachments) {
        this.dnasequencerunattachments = dnasequencerunattachments;
    }

  
    @XmlTransient
    public Collection<Accessionattachment> getAccessionAttachments() {
        return accessionAttachments;
    }

    public void setAccessionAttachments(Collection<Accessionattachment> accessionAttachments) {
        this.accessionAttachments = accessionAttachments;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentAttachments() {
        return agentAttachments;
    }

    public void setAgentAttachments(Collection<Agentattachment> agentAttachments) {
        this.agentAttachments = agentAttachments;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingEventAttachments() {
        return collectingEventAttachments;
    }

    public void setCollectingEventAttachments(Collection<Collectingeventattachment> collectingEventAttachments) {
        this.collectingEventAttachments = collectingEventAttachments;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionObjectAttachments() {
        return collectionObjectAttachments;
    }

    public void setCollectionObjectAttachments(Collection<Collectionobjectattachment> collectionObjectAttachments) {
        this.collectionObjectAttachments = collectionObjectAttachments;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservDescriptionAttachments() {
        return conservDescriptionAttachments;
    }

    public void setConservDescriptionAttachments(Collection<Conservdescriptionattachment> conservDescriptionAttachments) {
        this.conservDescriptionAttachments = conservDescriptionAttachments;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConservEventAttachments() {
        return conservEventAttachments;
    }

    public void setConservEventAttachments(Collection<Conserveventattachment> conservEventAttachments) {
        this.conservEventAttachments = conservEventAttachments;
    }

 
    public Date getFileCreatedDate() {
        return fileCreatedDate;
    }

    public void setFileCreatedDate(Date fileCreatedDate) {
        this.fileCreatedDate = fileCreatedDate;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Dnasequenceattachment> getDnaSequenceAttachments() {
        return dnaSequenceAttachments;
    }

    public void setDnaSequenceAttachments(Collection<Dnasequenceattachment> dnaSequenceAttachments) {
        this.dnaSequenceAttachments = dnaSequenceAttachments;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldNotebookAttachments() {
        return fieldNotebookAttachments;
    }

    public void setFieldNotebookAttachments(Collection<Fieldnotebookattachment> fieldNotebookAttachments) {
        this.fieldNotebookAttachments = fieldNotebookAttachments;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldNotebookPageAttachments() {
        return fieldNotebookPageAttachments;
    }

    public void setFieldNotebookPageAttachments(Collection<Fieldnotebookpageattachment> fieldNotebookPageAttachments) {
        this.fieldNotebookPageAttachments = fieldNotebookPageAttachments;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldNotebookPageSetAttachments() {
        return fieldNotebookPageSetAttachments;
    }

    public void setFieldNotebookPageSetAttachments(Collection<Fieldnotebookpagesetattachment> fieldNotebookPageSetAttachments) {
        this.fieldNotebookPageSetAttachments = fieldNotebookPageSetAttachments;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanAttachments() {
        return loanAttachments;
    }

    public void setLoanAttachments(Collection<Loanattachment> loanAttachments) {
        this.loanAttachments = loanAttachments;
    }

    @XmlTransient
    public Collection<Localityattachment> getLocalityAttachments() {
        return localityAttachments;
    }

    public void setLocalityAttachments(Collection<Localityattachment> localityAttachments) {
        this.localityAttachments = localityAttachments;
    }

    @XmlTransient
    public Collection<Attachmentmetadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(Collection<Attachmentmetadata> metadata) {
        this.metadata = metadata;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitAttachments() {
        return permitAttachments;
    }

    public void setPermitAttachments(Collection<Permitattachment> permitAttachments) {
        this.permitAttachments = permitAttachments;
    }

    @XmlTransient
    public Collection<Preparationattachment> getPreparationAttachments() {
        return preparationAttachments;
    }

    public void setPreparationAttachments(Collection<Preparationattachment> preparationAttachments) {
        this.preparationAttachments = preparationAttachments;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryAgreementAttachments() {
        return repositoryAgreementAttachments;
    }

    public void setRepositoryAgreementAttachments(Collection<Repositoryagreementattachment> repositoryAgreementAttachments) {
        this.repositoryAgreementAttachments = repositoryAgreementAttachments;
    }

    @XmlTransient
    public Collection<Attachmenttag> getTags() {
        return tags;
    }
 
    public void setTags(Collection<Attachmenttag> tags) {
        this.tags = tags;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonAttachments() {
        return taxonAttachments;
    }

    public void setTaxonAttachments(Collection<Taxonattachment> taxonAttachments) {
        this.taxonAttachments = taxonAttachments;
    }

    public Specifyuser getVisibilitySetBy() {
        return visibilitySetBy;
    }

    public void setVisibilitySetBy(Specifyuser visibilitySetBy) {
        this.visibilitySetBy = visibilitySetBy;
    }

 
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachmentId != null ? attachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.attachmentId == null && other.attachmentId != null) || (this.attachmentId != null && !this.attachmentId.equals(other.attachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachment[ attachmentId=" + attachmentId + " ]";
    }

    public Date getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Date getTimestampModified() {
        return timestampModified;
    }

    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
 
}

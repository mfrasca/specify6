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
    @NamedQuery(name = "Attachment.findByAttachmentID", query = "SELECT a FROM Attachment a WHERE a.attachmentID = :attachmentID"),
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
    @NamedQuery(name = "Attachment.findByAttachmentImageAttributeID", query = "SELECT a FROM Attachment a WHERE a.attachmentImageAttributeID = :attachmentImageAttributeID")})
public class Attachment extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttachmentID")
    private Integer attachmentID;
     
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
    
    @Size(max = 64)
    @Column(name = "DateImaged")
    private String dateImaged;
    
    @Column(name = "FileCreatedDate")
    @Temporal(TemporalType.DATE)
    private Date fileCreatedDate;
    
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
    private Integer attachmentImageAttributeID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Loanattachment> loanattachmentCollection;
    
    @OneToMany(mappedBy = "attachmentID")
    private Collection<Attachmentmetadata> attachmentmetadataCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Agentattachment> agentattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Repositoryagreementattachment> repositoryagreementattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Permitattachment> permitattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Dnasequenceattachment> dnasequenceattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Preparationattachment> preparationattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Localityattachment> localityattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Collectionobjectattachment> collectionobjectattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Taxonattachment> taxonattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Conserveventattachment> conserveventattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Accessionattachment> accessionattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Conservdescriptionattachment> conservdescriptionattachmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Collectingeventattachment> collectingeventattachmentCollection;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetByID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attachmentID")
    private Collection<Attachmenttag> attachmenttagCollection;

    public Attachment() {
    }

    public Attachment(Integer attachmentID) {
        this.attachmentID = attachmentID;
    }

    public Attachment(Integer attachmentID, Date timestampCreated, String origFilename) {
        super(timestampCreated);
        this.attachmentID = attachmentID; 
        this.origFilename = origFilename;
    }

    public Integer getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Integer attachmentID) {
        this.attachmentID = attachmentID;
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

    public Date getFileCreatedDate() {
        return fileCreatedDate;
    }

    public void setFileCreatedDate(Date fileCreatedDate) {
        this.fileCreatedDate = fileCreatedDate;
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

    public Integer getAttachmentImageAttributeID() {
        return attachmentImageAttributeID;
    }

    public void setAttachmentImageAttributeID(Integer attachmentImageAttributeID) {
        this.attachmentImageAttributeID = attachmentImageAttributeID;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanattachmentCollection() {
        return loanattachmentCollection;
    }

    public void setLoanattachmentCollection(Collection<Loanattachment> loanattachmentCollection) {
        this.loanattachmentCollection = loanattachmentCollection;
    }

    @XmlTransient
    public Collection<Attachmentmetadata> getAttachmentmetadataCollection() {
        return attachmentmetadataCollection;
    }

    public void setAttachmentmetadataCollection(Collection<Attachmentmetadata> attachmentmetadataCollection) {
        this.attachmentmetadataCollection = attachmentmetadataCollection;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentattachmentCollection() {
        return agentattachmentCollection;
    }

    public void setAgentattachmentCollection(Collection<Agentattachment> agentattachmentCollection) {
        this.agentattachmentCollection = agentattachmentCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachmentCollection() {
        return fieldnotebookpagesetattachmentCollection;
    }

    public void setFieldnotebookpagesetattachmentCollection(Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection) {
        this.fieldnotebookpagesetattachmentCollection = fieldnotebookpagesetattachmentCollection;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryagreementattachmentCollection() {
        return repositoryagreementattachmentCollection;
    }

    public void setRepositoryagreementattachmentCollection(Collection<Repositoryagreementattachment> repositoryagreementattachmentCollection) {
        this.repositoryagreementattachmentCollection = repositoryagreementattachmentCollection;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitattachmentCollection() {
        return permitattachmentCollection;
    }

    public void setPermitattachmentCollection(Collection<Permitattachment> permitattachmentCollection) {
        this.permitattachmentCollection = permitattachmentCollection;
    }

    @XmlTransient
    public Collection<Dnasequenceattachment> getDnasequenceattachmentCollection() {
        return dnasequenceattachmentCollection;
    }

    public void setDnasequenceattachmentCollection(Collection<Dnasequenceattachment> dnasequenceattachmentCollection) {
        this.dnasequenceattachmentCollection = dnasequenceattachmentCollection;
    }

    @XmlTransient
    public Collection<Preparationattachment> getPreparationattachmentCollection() {
        return preparationattachmentCollection;
    }

    public void setPreparationattachmentCollection(Collection<Preparationattachment> preparationattachmentCollection) {
        this.preparationattachmentCollection = preparationattachmentCollection;
    }

    @XmlTransient
    public Collection<Localityattachment> getLocalityattachmentCollection() {
        return localityattachmentCollection;
    }

    public void setLocalityattachmentCollection(Collection<Localityattachment> localityattachmentCollection) {
        this.localityattachmentCollection = localityattachmentCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionobjectattachmentCollection() {
        return collectionobjectattachmentCollection;
    }

    public void setCollectionobjectattachmentCollection(Collection<Collectionobjectattachment> collectionobjectattachmentCollection) {
        this.collectionobjectattachmentCollection = collectionobjectattachmentCollection;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonattachmentCollection() {
        return taxonattachmentCollection;
    }

    public void setTaxonattachmentCollection(Collection<Taxonattachment> taxonattachmentCollection) {
        this.taxonattachmentCollection = taxonattachmentCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldnotebookpageattachmentCollection() {
        return fieldnotebookpageattachmentCollection;
    }

    public void setFieldnotebookpageattachmentCollection(Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection) {
        this.fieldnotebookpageattachmentCollection = fieldnotebookpageattachmentCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldnotebookattachmentCollection() {
        return fieldnotebookattachmentCollection;
    }

    public void setFieldnotebookattachmentCollection(Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection) {
        this.fieldnotebookattachmentCollection = fieldnotebookattachmentCollection;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConserveventattachmentCollection() {
        return conserveventattachmentCollection;
    }

    public void setConserveventattachmentCollection(Collection<Conserveventattachment> conserveventattachmentCollection) {
        this.conserveventattachmentCollection = conserveventattachmentCollection;
    }

    @XmlTransient
    public Collection<Accessionattachment> getAccessionattachmentCollection() {
        return accessionattachmentCollection;
    }

    public void setAccessionattachmentCollection(Collection<Accessionattachment> accessionattachmentCollection) {
        this.accessionattachmentCollection = accessionattachmentCollection;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservdescriptionattachmentCollection() {
        return conservdescriptionattachmentCollection;
    }

    public void setConservdescriptionattachmentCollection(Collection<Conservdescriptionattachment> conservdescriptionattachmentCollection) {
        this.conservdescriptionattachmentCollection = conservdescriptionattachmentCollection;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingeventattachmentCollection() {
        return collectingeventattachmentCollection;
    }

    public void setCollectingeventattachmentCollection(Collection<Collectingeventattachment> collectingeventattachmentCollection) {
        this.collectingeventattachmentCollection = collectingeventattachmentCollection;
    }

    public Specifyuser getVisibilitySetByID() {
        return visibilitySetByID;
    }

    public void setVisibilitySetByID(Specifyuser visibilitySetByID) {
        this.visibilitySetByID = visibilitySetByID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @XmlTransient
    public Collection<Attachmenttag> getAttachmenttagCollection() {
        return attachmenttagCollection;
    }

    public void setAttachmenttagCollection(Collection<Attachmenttag> attachmenttagCollection) {
        this.attachmenttagCollection = attachmenttagCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachmentID != null ? attachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.attachmentID == null && other.attachmentID != null) || (this.attachmentID != null && !this.attachmentID.equals(other.attachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachment[ attachmentID=" + attachmentID + " ]";
    }
    
}

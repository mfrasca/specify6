package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table; 
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "repositoryagreementattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repositoryagreementattachment.findAll", query = "SELECT r FROM Repositoryagreementattachment r"),
    @NamedQuery(name = "Repositoryagreementattachment.findByRepositoryAgreementAttachmentID", query = "SELECT r FROM Repositoryagreementattachment r WHERE r.repositoryAgreementAttachmentId = :repositoryAgreementAttachmentID"),
    @NamedQuery(name = "Repositoryagreementattachment.findByTimestampCreated", query = "SELECT r FROM Repositoryagreementattachment r WHERE r.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Repositoryagreementattachment.findByTimestampModified", query = "SELECT r FROM Repositoryagreementattachment r WHERE r.timestampModified = :timestampModified"),
    @NamedQuery(name = "Repositoryagreementattachment.findByVersion", query = "SELECT r FROM Repositoryagreementattachment r WHERE r.version = :version"),
    @NamedQuery(name = "Repositoryagreementattachment.findByOrdinal", query = "SELECT r FROM Repositoryagreementattachment r WHERE r.ordinal = :ordinal")})
public class Repositoryagreementattachment extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "RepositoryAgreementAttachmentID")
    private Integer repositoryAgreementAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "RepositoryAgreementID", referencedColumnName = "RepositoryAgreementID")
    @ManyToOne(optional = false)
    private Repositoryagreement repositoryAgreement;

    public Repositoryagreementattachment() {
    }

    public Repositoryagreementattachment(Integer repositoryAgreementAttachmentId) {
        this.repositoryAgreementAttachmentId = repositoryAgreementAttachmentId;
    }

    public Repositoryagreementattachment(Integer repositoryAgreementAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.repositoryAgreementAttachmentId = repositoryAgreementAttachmentId; 
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
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

 

    public Repositoryagreement getRepositoryAgreement() {
        return repositoryAgreement;
    }

    public void setRepositoryAgreement(Repositoryagreement repositoryAgreement) {
        this.repositoryAgreement = repositoryAgreement;
    }

    public Integer getRepositoryAgreementAttachmentId() {
        return repositoryAgreementAttachmentId;
    }

    public void setRepositoryAgreementAttachmentId(Integer repositoryAgreementAttachmentId) {
        this.repositoryAgreementAttachmentId = repositoryAgreementAttachmentId;
    }
 

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repositoryAgreementAttachmentId != null ? repositoryAgreementAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repositoryagreementattachment)) {
            return false;
        }
        Repositoryagreementattachment other = (Repositoryagreementattachment) object;
        if ((this.repositoryAgreementAttachmentId == null && other.repositoryAgreementAttachmentId != null) || (this.repositoryAgreementAttachmentId != null && !this.repositoryAgreementAttachmentId.equals(other.repositoryAgreementAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Repositoryagreementattachment[ repositoryAgreementAttachmentID=" + repositoryAgreementAttachmentId + " ]";
    }
    
}

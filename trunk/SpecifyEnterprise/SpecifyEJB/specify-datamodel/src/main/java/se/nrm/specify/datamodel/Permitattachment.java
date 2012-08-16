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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "permitattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permitattachment.findAll", query = "SELECT p FROM Permitattachment p"),
    @NamedQuery(name = "Permitattachment.findByPermitAttachmentID", query = "SELECT p FROM Permitattachment p WHERE p.permitAttachmentId = :permitAttachmentID"),
    @NamedQuery(name = "Permitattachment.findByTimestampCreated", query = "SELECT p FROM Permitattachment p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Permitattachment.findByTimestampModified", query = "SELECT p FROM Permitattachment p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Permitattachment.findByVersion", query = "SELECT p FROM Permitattachment p WHERE p.version = :version"),
    @NamedQuery(name = "Permitattachment.findByOrdinal", query = "SELECT p FROM Permitattachment p WHERE p.ordinal = :ordinal")})
public class Permitattachment extends BaseEntity {
 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PermitAttachmentID")
    private Integer permitAttachmentId;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "PermitID", referencedColumnName = "PermitID")
    @ManyToOne(optional = false)
    private Permit permit;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Permitattachment() {
    }

    public Permitattachment(Integer permitAttachmentId) {
        this.permitAttachmentId = permitAttachmentId;
    }

    public Permitattachment(Integer permitAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.permitAttachmentId = permitAttachmentId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (permitAttachmentId != null) ? permitAttachmentId.toString() : "0";
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
        hash += (permitAttachmentId != null ? permitAttachmentId.hashCode() : 0);
        return hash;
    }

    @NotNull(message="Attachment must be specified.")
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

    @NotNull(message="Permit must be specified.")
    public Permit getPermit() {
        return permit;
    }

    public void setPermit(Permit permit) {
        this.permit = permit;
    }

    public Integer getPermitAttachmentId() {
        return permitAttachmentId;
    }

    public void setPermitAttachmentId(Integer permitAttachmentId) {
        this.permitAttachmentId = permitAttachmentId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permitattachment)) {
            return false;
        }
        Permitattachment other = (Permitattachment) object;
        if ((this.permitAttachmentId == null && other.permitAttachmentId != null) || (this.permitAttachmentId != null && !this.permitAttachmentId.equals(other.permitAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Permitattachment[ permitAttachmentID=" + permitAttachmentId + " ]";
    }
 
}

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
@Table(name = "permitattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permitattachment.findAll", query = "SELECT p FROM Permitattachment p"),
    @NamedQuery(name = "Permitattachment.findByPermitAttachmentID", query = "SELECT p FROM Permitattachment p WHERE p.permitAttachmentID = :permitAttachmentID"),
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
    private Integer permitAttachmentID;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "PermitID", referencedColumnName = "PermitID")
    @ManyToOne(optional = false)
    private Permit permitID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Permitattachment() {
    }

    public Permitattachment(Integer permitAttachmentID) {
        this.permitAttachmentID = permitAttachmentID;
    }

    public Permitattachment(Integer permitAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.permitAttachmentID = permitAttachmentID; 
    }

    public Integer getPermitAttachmentID() {
        return permitAttachmentID;
    }

    public void setPermitAttachmentID(Integer permitAttachmentID) {
        this.permitAttachmentID = permitAttachmentID;
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

    public Attachment getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Attachment attachmentID) {
        this.attachmentID = attachmentID;
    }

    public Permit getPermitID() {
        return permitID;
    }

    public void setPermitID(Permit permitID) {
        this.permitID = permitID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permitAttachmentID != null ? permitAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permitattachment)) {
            return false;
        }
        Permitattachment other = (Permitattachment) object;
        if ((this.permitAttachmentID == null && other.permitAttachmentID != null) || (this.permitAttachmentID != null && !this.permitAttachmentID.equals(other.permitAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Permitattachment[ permitAttachmentID=" + permitAttachmentID + " ]";
    }
    
}

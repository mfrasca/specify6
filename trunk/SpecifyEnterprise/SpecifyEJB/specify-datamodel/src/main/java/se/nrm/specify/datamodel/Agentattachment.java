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
@Table(name = "agentattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agentattachment.findAll", query = "SELECT a FROM Agentattachment a"),
    @NamedQuery(name = "Agentattachment.findByAgentAttachmentID", query = "SELECT a FROM Agentattachment a WHERE a.agentAttachmentID = :agentAttachmentID"),
    @NamedQuery(name = "Agentattachment.findByTimestampCreated", query = "SELECT a FROM Agentattachment a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Agentattachment.findByTimestampModified", query = "SELECT a FROM Agentattachment a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Agentattachment.findByVersion", query = "SELECT a FROM Agentattachment a WHERE a.version = :version"),
    @NamedQuery(name = "Agentattachment.findByOrdinal", query = "SELECT a FROM Agentattachment a WHERE a.ordinal = :ordinal")})
public class Agentattachment extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AgentAttachmentID")
    private Integer agentAttachmentID;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Agentattachment() {
    }

    public Agentattachment(Integer agentAttachmentID) {
        this.agentAttachmentID = agentAttachmentID;
    }

    public Agentattachment(Integer agentAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.agentAttachmentID = agentAttachmentID; 
    }

    public Integer getAgentAttachmentID() {
        return agentAttachmentID;
    }

    public void setAgentAttachmentID(Integer agentAttachmentID) {
        this.agentAttachmentID = agentAttachmentID;
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

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentAttachmentID != null ? agentAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agentattachment)) {
            return false;
        }
        Agentattachment other = (Agentattachment) object;
        if ((this.agentAttachmentID == null && other.agentAttachmentID != null) || (this.agentAttachmentID != null && !this.agentAttachmentID.equals(other.agentAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Agentattachment[ agentAttachmentID=" + agentAttachmentID + " ]";
    }
    
}

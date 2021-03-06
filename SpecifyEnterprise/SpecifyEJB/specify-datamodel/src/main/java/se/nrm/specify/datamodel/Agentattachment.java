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
@Table(name = "agentattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agentattachment.findAll", query = "SELECT a FROM Agentattachment a"),
    @NamedQuery(name = "Agentattachment.findByAgentAttachmentId", query = "SELECT a FROM Agentattachment a WHERE a.agentAttachmentId = :agentAttachmentId"),
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
    private Integer agentAttachmentId;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @NotNull
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @NotNull
    @ManyToOne(optional = false)
    private Agent agent;

    public Agentattachment() {
    }

    public Agentattachment(Integer agentAttachmentId) {
        this.agentAttachmentId = agentAttachmentId;
    }

    public Agentattachment(Integer agentAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.agentAttachmentId = agentAttachmentId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (agentAttachmentId != null) ? agentAttachmentId.toString() : "0";
    }

    @NotNull(message="Agent must be specified.") 
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Integer getAgentAttachmentId() {
        return agentAttachmentId;
    }

    public void setAgentAttachmentId(Integer agentAttachmentId) {
        this.agentAttachmentId = agentAttachmentId;
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
    public String getEntityName() {
        return "agentAttachment";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentAttachmentId != null ? agentAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agentattachment)) {
            return false;
        }
        Agentattachment other = (Agentattachment) object;
        if ((this.agentAttachmentId == null && other.agentAttachmentId != null) || (this.agentAttachmentId != null && !this.agentAttachmentId.equals(other.agentAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Agentattachment[ agentAttachmentID=" + agentAttachmentId + " ]";
    }
 
    
}

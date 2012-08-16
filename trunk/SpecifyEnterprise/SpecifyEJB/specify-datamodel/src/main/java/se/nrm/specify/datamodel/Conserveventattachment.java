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
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "conserveventattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conserveventattachment.findAll", query = "SELECT c FROM Conserveventattachment c"),
    @NamedQuery(name = "Conserveventattachment.findByConservEventAttachmentID", query = "SELECT c FROM Conserveventattachment c WHERE c.conservEventAttachmentId = :conservEventAttachmentID"),
    @NamedQuery(name = "Conserveventattachment.findByTimestampCreated", query = "SELECT c FROM Conserveventattachment c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Conserveventattachment.findByTimestampModified", query = "SELECT c FROM Conserveventattachment c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Conserveventattachment.findByVersion", query = "SELECT c FROM Conserveventattachment c WHERE c.version = :version"),
    @NamedQuery(name = "Conserveventattachment.findByOrdinal", query = "SELECT c FROM Conserveventattachment c WHERE c.ordinal = :ordinal")})
public class Conserveventattachment extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ConservEventAttachmentID")
    private Integer conservEventAttachmentId;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "ConservEventID", referencedColumnName = "ConservEventID")
    @ManyToOne(optional = false)
    private Conservevent conservEvent;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Conserveventattachment() {
    }

    public Conserveventattachment(Integer conservEventAttachmentId) {
        this.conservEventAttachmentId = conservEventAttachmentId;
    }

    public Conserveventattachment(Integer conservEventAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.conservEventAttachmentId = conservEventAttachmentId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (conservEventAttachmentId != null) ? conservEventAttachmentId.toString() : "0";
    }

    @NotNull(message="Attachment must be specified.")
    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @NotNull(message="ConservEvent must be specified.")
    public Conservevent getConservEvent() {
        return conservEvent;
    }

    public void setConservEvent(Conservevent conservEvent) {
        this.conservEvent = conservEvent;
    }

    public Integer getConservEventAttachmentId() {
        return conservEventAttachmentId;
    }

    public void setConservEventAttachmentId(Integer conservEventAttachmentId) {
        this.conservEventAttachmentId = conservEventAttachmentId;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
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
    public int hashCode() {
        int hash = 0;
        hash += (conservEventAttachmentId != null ? conservEventAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conserveventattachment)) {
            return false;
        }
        Conserveventattachment other = (Conserveventattachment) object;
        if ((this.conservEventAttachmentId == null && other.conservEventAttachmentId != null) || (this.conservEventAttachmentId != null && !this.conservEventAttachmentId.equals(other.conservEventAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conserveventattachment[ conservEventAttachmentId=" + conservEventAttachmentId + " ]";
    }
 
}

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
@Table(name = "conservdescriptionattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conservdescriptionattachment.findAll", query = "SELECT c FROM Conservdescriptionattachment c"),
    @NamedQuery(name = "Conservdescriptionattachment.findByConservDescriptionAttachmentID", query = "SELECT c FROM Conservdescriptionattachment c WHERE c.conservDescriptionAttachmentId = :conservDescriptionAttachmentID"),
    @NamedQuery(name = "Conservdescriptionattachment.findByTimestampCreated", query = "SELECT c FROM Conservdescriptionattachment c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Conservdescriptionattachment.findByTimestampModified", query = "SELECT c FROM Conservdescriptionattachment c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Conservdescriptionattachment.findByVersion", query = "SELECT c FROM Conservdescriptionattachment c WHERE c.version = :version"),
    @NamedQuery(name = "Conservdescriptionattachment.findByOrdinal", query = "SELECT c FROM Conservdescriptionattachment c WHERE c.ordinal = :ordinal")})
public class Conservdescriptionattachment extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ConservDescriptionAttachmentID")
    private Integer conservDescriptionAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "ConservDescriptionID", referencedColumnName = "ConservDescriptionID")
    @ManyToOne(optional = false)
    private Conservdescription conservDescription;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Conservdescriptionattachment() {
    }

    public Conservdescriptionattachment(Integer conservDescriptionAttachmentId) {
        this.conservDescriptionAttachmentId = conservDescriptionAttachmentId;
    }

    public Conservdescriptionattachment(Integer conservDescriptionAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.conservDescriptionAttachmentId = conservDescriptionAttachmentId; 
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

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Conservdescription getConservDescription() {
        return conservDescription;
    }

    public void setConservDescription(Conservdescription conservDescription) {
        this.conservDescription = conservDescription;
    }

    public Integer getConservDescriptionAttachmentId() {
        return conservDescriptionAttachmentId;
    }

    public void setConservDescriptionAttachmentId(Integer conservDescriptionAttachmentId) {
        this.conservDescriptionAttachmentId = conservDescriptionAttachmentId;
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

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conservDescriptionAttachmentId != null ? conservDescriptionAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conservdescriptionattachment)) {
            return false;
        }
        Conservdescriptionattachment other = (Conservdescriptionattachment) object;
        if ((this.conservDescriptionAttachmentId == null && other.conservDescriptionAttachmentId != null) || (this.conservDescriptionAttachmentId != null && !this.conservDescriptionAttachmentId.equals(other.conservDescriptionAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conservdescriptionattachment[ conservDescriptionAttachmentId=" + conservDescriptionAttachmentId + " ]";
    }
    
}

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
@Table(name = "conserveventattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conserveventattachment.findAll", query = "SELECT c FROM Conserveventattachment c"),
    @NamedQuery(name = "Conserveventattachment.findByConservEventAttachmentID", query = "SELECT c FROM Conserveventattachment c WHERE c.conservEventAttachmentID = :conservEventAttachmentID"),
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
    private Integer conservEventAttachmentID;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "ConservEventID", referencedColumnName = "ConservEventID")
    @ManyToOne(optional = false)
    private Conservevent conservEventID;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Conserveventattachment() {
    }

    public Conserveventattachment(Integer conservEventAttachmentID) {
        this.conservEventAttachmentID = conservEventAttachmentID;
    }

    public Conserveventattachment(Integer conservEventAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.conservEventAttachmentID = conservEventAttachmentID; 
    }

    public Integer getConservEventAttachmentID() {
        return conservEventAttachmentID;
    }

    public void setConservEventAttachmentID(Integer conservEventAttachmentID) {
        this.conservEventAttachmentID = conservEventAttachmentID;
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

    public Conservevent getConservEventID() {
        return conservEventID;
    }

    public void setConservEventID(Conservevent conservEventID) {
        this.conservEventID = conservEventID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conservEventAttachmentID != null ? conservEventAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conserveventattachment)) {
            return false;
        }
        Conserveventattachment other = (Conserveventattachment) object;
        if ((this.conservEventAttachmentID == null && other.conservEventAttachmentID != null) || (this.conservEventAttachmentID != null && !this.conservEventAttachmentID.equals(other.conservEventAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conserveventattachment[ conservEventAttachmentID=" + conservEventAttachmentID + " ]";
    }
    
}

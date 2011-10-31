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
    @NamedQuery(name = "Conservdescriptionattachment.findByConservDescriptionAttachmentID", query = "SELECT c FROM Conservdescriptionattachment c WHERE c.conservDescriptionAttachmentID = :conservDescriptionAttachmentID"),
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
    private Integer conservDescriptionAttachmentID;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "ConservDescriptionID", referencedColumnName = "ConservDescriptionID")
    @ManyToOne(optional = false)
    private Conservdescription conservDescriptionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Conservdescriptionattachment() {
    }

    public Conservdescriptionattachment(Integer conservDescriptionAttachmentID) {
        this.conservDescriptionAttachmentID = conservDescriptionAttachmentID;
    }

    public Conservdescriptionattachment(Integer conservDescriptionAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.conservDescriptionAttachmentID = conservDescriptionAttachmentID; 
    }

    public Integer getConservDescriptionAttachmentID() {
        return conservDescriptionAttachmentID;
    }

    public void setConservDescriptionAttachmentID(Integer conservDescriptionAttachmentID) {
        this.conservDescriptionAttachmentID = conservDescriptionAttachmentID;
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

    public Conservdescription getConservDescriptionID() {
        return conservDescriptionID;
    }

    public void setConservDescriptionID(Conservdescription conservDescriptionID) {
        this.conservDescriptionID = conservDescriptionID;
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
        hash += (conservDescriptionAttachmentID != null ? conservDescriptionAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conservdescriptionattachment)) {
            return false;
        }
        Conservdescriptionattachment other = (Conservdescriptionattachment) object;
        if ((this.conservDescriptionAttachmentID == null && other.conservDescriptionAttachmentID != null) || (this.conservDescriptionAttachmentID != null && !this.conservDescriptionAttachmentID.equals(other.conservDescriptionAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conservdescriptionattachment[ conservDescriptionAttachmentID=" + conservDescriptionAttachmentID + " ]";
    }
    
}

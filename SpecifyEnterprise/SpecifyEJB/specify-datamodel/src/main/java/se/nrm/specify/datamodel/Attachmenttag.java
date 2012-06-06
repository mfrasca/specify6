package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "attachmenttag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachmenttag.findAll", query = "SELECT a FROM Attachmenttag a"),
    @NamedQuery(name = "Attachmenttag.findByAttachmentTagID", query = "SELECT a FROM Attachmenttag a WHERE a.attachmentTagID = :attachmentTagID"),
    @NamedQuery(name = "Attachmenttag.findByTimestampCreated", query = "SELECT a FROM Attachmenttag a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Attachmenttag.findByTimestampModified", query = "SELECT a FROM Attachmenttag a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Attachmenttag.findByVersion", query = "SELECT a FROM Attachmenttag a WHERE a.version = :version"),
    @NamedQuery(name = "Attachmenttag.findByTag", query = "SELECT a FROM Attachmenttag a WHERE a.tag = :tag")})
public class Attachmenttag extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttachmentTagID")
    private Integer attachmentTagID;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Tag")
    private String tag;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Attachmenttag() {
    }

    public Attachmenttag(Integer attachmentTagID) {
        this.attachmentTagID = attachmentTagID;
    }

    public Attachmenttag(Integer attachmentTagID, Date timestampCreated, String tag) {
        super(timestampCreated);
        this.attachmentTagID = attachmentTagID; 
        this.tag = tag;
    }

    public Integer getAttachmentTagID() {
        return attachmentTagID;
    }

    public void setAttachmentTagID(Integer attachmentTagID) {
        this.attachmentTagID = attachmentTagID;
    } 

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachmentTagID != null ? attachmentTagID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachmenttag)) {
            return false;
        }
        Attachmenttag other = (Attachmenttag) object;
        if ((this.attachmentTagID == null && other.attachmentTagID != null) || (this.attachmentTagID != null && !this.attachmentTagID.equals(other.attachmentTagID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachmenttag[ attachmentTagID=" + attachmentTagID + " ]";
    }
    
}

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
@Table(name = "attachmentmetadata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachmentmetadata.findAll", query = "SELECT a FROM Attachmentmetadata a"),
    @NamedQuery(name = "Attachmentmetadata.findByAttachmentMetadataID", query = "SELECT a FROM Attachmentmetadata a WHERE a.attachmentMetadataID = :attachmentMetadataID"),
    @NamedQuery(name = "Attachmentmetadata.findByTimestampCreated", query = "SELECT a FROM Attachmentmetadata a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Attachmentmetadata.findByTimestampModified", query = "SELECT a FROM Attachmentmetadata a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Attachmentmetadata.findByVersion", query = "SELECT a FROM Attachmentmetadata a WHERE a.version = :version"),
    @NamedQuery(name = "Attachmentmetadata.findByName", query = "SELECT a FROM Attachmentmetadata a WHERE a.name = :name"),
    @NamedQuery(name = "Attachmentmetadata.findByValue", query = "SELECT a FROM Attachmentmetadata a WHERE a.value = :value")})
public class Attachmentmetadata extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttachmentMetadataID")
    private Integer attachmentMetadataID;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "Value")
    private String value;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne
    private Attachment attachmentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Attachmentmetadata() {
    }

    public Attachmentmetadata(Integer attachmentMetadataID) {
        this.attachmentMetadataID = attachmentMetadataID;
    }

    public Attachmentmetadata(Integer attachmentMetadataID, Date timestampCreated, String name, String value) {
        super(timestampCreated);
        this.attachmentMetadataID = attachmentMetadataID; 
        this.name = name;
        this.value = value;
    }

    public Integer getAttachmentMetadataID() {
        return attachmentMetadataID;
    }

    public void setAttachmentMetadataID(Integer attachmentMetadataID) {
        this.attachmentMetadataID = attachmentMetadataID;
    } 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        hash += (attachmentMetadataID != null ? attachmentMetadataID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachmentmetadata)) {
            return false;
        }
        Attachmentmetadata other = (Attachmentmetadata) object;
        if ((this.attachmentMetadataID == null && other.attachmentMetadataID != null) || (this.attachmentMetadataID != null && !this.attachmentMetadataID.equals(other.attachmentMetadataID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachmentmetadata[ attachmentMetadataID=" + attachmentMetadataID + " ]";
    }
    
}

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
@Table(name = "localityattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localityattachment.findAll", query = "SELECT l FROM Localityattachment l"),
    @NamedQuery(name = "Localityattachment.findByLocalityAttachmentID", query = "SELECT l FROM Localityattachment l WHERE l.localityAttachmentId = :localityAttachmentID"),
    @NamedQuery(name = "Localityattachment.findByTimestampCreated", query = "SELECT l FROM Localityattachment l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Localityattachment.findByTimestampModified", query = "SELECT l FROM Localityattachment l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Localityattachment.findByVersion", query = "SELECT l FROM Localityattachment l WHERE l.version = :version"),
    @NamedQuery(name = "Localityattachment.findByOrdinal", query = "SELECT l FROM Localityattachment l WHERE l.ordinal = :ordinal")})
public class Localityattachment extends BaseEntity {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LocalityAttachmentID")
    private Integer localityAttachmentId;
    
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne(optional = false)
    private Locality locality;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Localityattachment() {
    }

    public Localityattachment(Integer localityAttachmentId) {
        this.localityAttachmentId = localityAttachmentId;
    }

    public Localityattachment(Integer localityAttachmentId, Date timestampCreated) {
        this.localityAttachmentId = localityAttachmentId;
//        this.timestampCreated = timestampCreated;
        setTimestampCreated(timestampCreated);
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (localityAttachmentId != null) ? localityAttachmentId.toString() : "0";
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

    @NotNull(message="Locality must be specified.")
    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Integer getLocalityAttachmentId() {
        return localityAttachmentId;
    }

    public void setLocalityAttachmentId(Integer localityAttachmentId) {
        this.localityAttachmentId = localityAttachmentId;
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
        hash += (localityAttachmentId != null ? localityAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localityattachment)) {
            return false;
        }
        Localityattachment other = (Localityattachment) object;
        if ((this.localityAttachmentId == null && other.localityAttachmentId != null) || (this.localityAttachmentId != null && !this.localityAttachmentId.equals(other.localityAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localityattachment[ localityAttachmentID=" + localityAttachmentId + " ]";
    }
 
}

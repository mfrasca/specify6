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
@Table(name = "localityattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localityattachment.findAll", query = "SELECT l FROM Localityattachment l"),
    @NamedQuery(name = "Localityattachment.findByLocalityAttachmentID", query = "SELECT l FROM Localityattachment l WHERE l.localityAttachmentID = :localityAttachmentID"),
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
    private Integer localityAttachmentID;
    
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne(optional = false)
    private Locality localityID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Localityattachment() {
    }

    public Localityattachment(Integer localityAttachmentID) {
        this.localityAttachmentID = localityAttachmentID;
    }

    public Localityattachment(Integer localityAttachmentID, Date timestampCreated) {
        this.localityAttachmentID = localityAttachmentID;
//        this.timestampCreated = timestampCreated;
        setTimestampCreated(timestampCreated);
    }

    public Integer getLocalityAttachmentID() {
        return localityAttachmentID;
    }

    public void setLocalityAttachmentID(Integer localityAttachmentID) {
        this.localityAttachmentID = localityAttachmentID;
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

    public Locality getLocalityID() {
        return localityID;
    }

    public void setLocalityID(Locality localityID) {
        this.localityID = localityID;
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
        hash += (localityAttachmentID != null ? localityAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localityattachment)) {
            return false;
        }
        Localityattachment other = (Localityattachment) object;
        if ((this.localityAttachmentID == null && other.localityAttachmentID != null) || (this.localityAttachmentID != null && !this.localityAttachmentID.equals(other.localityAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localityattachment[ localityAttachmentID=" + localityAttachmentID + " ]";
    }
    
}

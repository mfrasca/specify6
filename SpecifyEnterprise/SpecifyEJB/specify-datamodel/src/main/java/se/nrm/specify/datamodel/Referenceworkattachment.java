package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "referenceworkattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Referenceworkattachment.findAll", query = "SELECT r FROM Referenceworkattachment r"),
    @NamedQuery(name = "Referenceworkattachment.findByReferenceWorkAttachmentID", query = "SELECT r FROM Referenceworkattachment r WHERE r.referenceWorkAttachmentId = :referenceWorkAttachmentID"),
    @NamedQuery(name = "Referenceworkattachment.findByTimestampCreated", query = "SELECT r FROM Referenceworkattachment r WHERE r.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Referenceworkattachment.findByTimestampModified", query = "SELECT r FROM Referenceworkattachment r WHERE r.timestampModified = :timestampModified"),
    @NamedQuery(name = "Referenceworkattachment.findByVersion", query = "SELECT r FROM Referenceworkattachment r WHERE r.version = :version"),
    @NamedQuery(name = "Referenceworkattachment.findByOrdinal", query = "SELECT r FROM Referenceworkattachment r WHERE r.ordinal = :ordinal"),
    @NamedQuery(name = "Referenceworkattachment.findByReferenceWorkID", query = "SELECT r FROM Referenceworkattachment r WHERE r.referenceWorkId = :referenceWorkID"),
    @NamedQuery(name = "Referenceworkattachment.findByModifiedByAgentID", query = "SELECT r FROM Referenceworkattachment r WHERE r.modifiedByAgentId = :modifiedByAgentID"),
    @NamedQuery(name = "Referenceworkattachment.findByCreatedByAgentID", query = "SELECT r FROM Referenceworkattachment r WHERE r.createdByAgentId = :createdByAgentID"),
    @NamedQuery(name = "Referenceworkattachment.findByAttachmentID", query = "SELECT r FROM Referenceworkattachment r WHERE r.attachmentId = :attachmentID")})
public class Referenceworkattachment extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceWorkAttachmentID")
    private Integer referenceWorkAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceWorkID")
    private int referenceWorkId;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentId;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AttachmentID")
    private int attachmentId;

    public Referenceworkattachment() {
    }

    public Referenceworkattachment(Integer referenceWorkAttachmentId) {
        this.referenceWorkAttachmentId = referenceWorkAttachmentId;
    }

    public Referenceworkattachment(Integer referenceWorkAttachmentId, Date timestampCreated, int referenceWorkId, int attachmentId) {
        this.referenceWorkAttachmentId = referenceWorkAttachmentId;
        this.timestampCreated = timestampCreated;
        this.referenceWorkId = referenceWorkId;
        this.attachmentId = attachmentId;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (referenceWorkAttachmentId != null) ? referenceWorkAttachmentId.toString() : "0";
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

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getCreatedByAgentId() {
        return createdByAgentId;
    }

    public void setCreatedByAgentId(Integer createdByAgentId) {
        this.createdByAgentId = createdByAgentId;
    }

    public Integer getModifiedByAgentId() {
        return modifiedByAgentId;
    }

    public void setModifiedByAgentId(Integer modifiedByAgentId) {
        this.modifiedByAgentId = modifiedByAgentId;
    }

    public Integer getReferenceWorkAttachmentId() {
        return referenceWorkAttachmentId;
    }

    public void setReferenceWorkAttachmentId(Integer referenceWorkAttachmentId) {
        this.referenceWorkAttachmentId = referenceWorkAttachmentId;
    }

    public int getReferenceWorkId() {
        return referenceWorkId;
    }

    public void setReferenceWorkId(int referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }

    
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenceWorkAttachmentId != null ? referenceWorkAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Referenceworkattachment)) {
            return false;
        }
        Referenceworkattachment other = (Referenceworkattachment) object;
        if ((this.referenceWorkAttachmentId == null && other.referenceWorkAttachmentId != null) || (this.referenceWorkAttachmentId != null && !this.referenceWorkAttachmentId.equals(other.referenceWorkAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Referenceworkattachment[ referenceWorkAttachmentID=" + referenceWorkAttachmentId + " ]";
    }
    
}

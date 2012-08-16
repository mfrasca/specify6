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
@Table(name = "giftattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Giftattachment.findAll", query = "SELECT g FROM Giftattachment g"),
    @NamedQuery(name = "Giftattachment.findByGiftAttachmentID", query = "SELECT g FROM Giftattachment g WHERE g.giftAttachmentId = :giftAttachmentID"),
    @NamedQuery(name = "Giftattachment.findByTimestampCreated", query = "SELECT g FROM Giftattachment g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Giftattachment.findByTimestampModified", query = "SELECT g FROM Giftattachment g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Giftattachment.findByVersion", query = "SELECT g FROM Giftattachment g WHERE g.version = :version"),
    @NamedQuery(name = "Giftattachment.findByOrdinal", query = "SELECT g FROM Giftattachment g WHERE g.ordinal = :ordinal"),
    @NamedQuery(name = "Giftattachment.findByCreatedByAgentID", query = "SELECT g FROM Giftattachment g WHERE g.createdByAgentId = :createdByAgentID"),
    @NamedQuery(name = "Giftattachment.findByAttachmentID", query = "SELECT g FROM Giftattachment g WHERE g.attachmentId = :attachmentID"),
    @NamedQuery(name = "Giftattachment.findByGiftID", query = "SELECT g FROM Giftattachment g WHERE g.giftId = :giftID"),
    @NamedQuery(name = "Giftattachment.findByModifiedByAgentID", query = "SELECT g FROM Giftattachment g WHERE g.modifiedByAgentId = :modifiedByAgentID")})
public class Giftattachment extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "GiftAttachmentID")
    private Integer giftAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AttachmentID")
    private int attachmentId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "GiftID")
    private int giftId;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentId;

    public Giftattachment() {
    }

    public Giftattachment(Integer giftAttachmentId) {
        this.giftAttachmentId = giftAttachmentId;
    }

    public Giftattachment(Integer giftAttachmentId, Date timestampCreated, int attachmentId, int giftId) {
        this.giftAttachmentId = giftAttachmentId;
        this.timestampCreated = timestampCreated;
        this.attachmentId = attachmentId;
        this.giftId = giftId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (giftAttachmentId != null) ? giftAttachmentId.toString() : "0";
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

    public Integer getGiftAttachmentId() {
        return giftAttachmentId;
    }

    public void setGiftAttachmentId(Integer giftAttachmentId) {
        this.giftAttachmentId = giftAttachmentId;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public Integer getModifiedByAgentId() {
        return modifiedByAgentId;
    }

    public void setModifiedByAgentId(Integer modifiedByAgentId) {
        this.modifiedByAgentId = modifiedByAgentId;
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
        hash += (giftAttachmentId != null ? giftAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giftattachment)) {
            return false;
        }
        Giftattachment other = (Giftattachment) object;
        if ((this.giftAttachmentId == null && other.giftAttachmentId != null) || (this.giftAttachmentId != null && !this.giftAttachmentId.equals(other.giftAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Giftattachment[ giftAttachmentID=" + giftAttachmentId + " ]";
    }
    
}

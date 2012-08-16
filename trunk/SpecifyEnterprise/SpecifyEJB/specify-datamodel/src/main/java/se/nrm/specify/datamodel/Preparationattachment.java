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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "preparationattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preparationattachment.findAll", query = "SELECT p FROM Preparationattachment p"),
    @NamedQuery(name = "Preparationattachment.findByPreparationAttachmentID", query = "SELECT p FROM Preparationattachment p WHERE p.preparationAttachmentId = :preparationAttachmentID"),
    @NamedQuery(name = "Preparationattachment.findByTimestampCreated", query = "SELECT p FROM Preparationattachment p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Preparationattachment.findByTimestampModified", query = "SELECT p FROM Preparationattachment p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Preparationattachment.findByVersion", query = "SELECT p FROM Preparationattachment p WHERE p.version = :version"),
    @NamedQuery(name = "Preparationattachment.findByCollectionMemberID", query = "SELECT p FROM Preparationattachment p WHERE p.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Preparationattachment.findByOrdinal", query = "SELECT p FROM Preparationattachment p WHERE p.ordinal = :ordinal")})
public class Preparationattachment extends BaseEntity {
     
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PreparationAttachmentID")
    private Integer preparationAttachmentId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne(optional = false)
    private Preparation preparation;

    public Preparationattachment() {
    }

    public Preparationattachment(Integer preparationAttachmentId) {
        this.preparationAttachmentId = preparationAttachmentId;
    }

    public Preparationattachment(Integer preparationAttachmentId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.preparationAttachmentId = preparationAttachmentId; 
        this.collectionMemberId = collectionMemberId;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (preparationAttachmentId != null) ? preparationAttachmentId.toString() : "0";
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

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
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

    @XmlTransient
    @NotNull(message="Preparation must be specified.")
    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

    public Integer getPreparationAttachmentId() {
        return preparationAttachmentId;
    }

    public void setPreparationAttachmentId(Integer preparationAttachmentId) {
        this.preparationAttachmentId = preparationAttachmentId;
    }
    
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        
        if(parent instanceof Preparation) {
            this.preparation = (Preparation)parent;
        } 
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preparationAttachmentId != null ? preparationAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preparationattachment)) {
            return false;
        }
        Preparationattachment other = (Preparationattachment) object;
        if ((this.preparationAttachmentId == null && other.preparationAttachmentId != null) || (this.preparationAttachmentId != null && !this.preparationAttachmentId.equals(other.preparationAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preparationattachment[ preparationAttachmentID=" + preparationAttachmentId + " ]";
    }
 
    
}

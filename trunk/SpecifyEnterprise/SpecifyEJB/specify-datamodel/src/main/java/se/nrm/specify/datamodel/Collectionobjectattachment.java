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
@Table(name = "collectionobjectattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectattachment.findAll", query = "SELECT c FROM Collectionobjectattachment c"),
    @NamedQuery(name = "Collectionobjectattachment.findByCollectionObjectAttachmentId", query = "SELECT c FROM Collectionobjectattachment c WHERE c.collectionObjectAttachmentId = :collectionObjectAttachmentId"),
    @NamedQuery(name = "Collectionobjectattachment.findByTimestampCreated", query = "SELECT c FROM Collectionobjectattachment c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobjectattachment.findByTimestampModified", query = "SELECT c FROM Collectionobjectattachment c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobjectattachment.findByVersion", query = "SELECT c FROM Collectionobjectattachment c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobjectattachment.findByCollectionMemberID", query = "SELECT c FROM Collectionobjectattachment c WHERE c.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Collectionobjectattachment.findByOrdinal", query = "SELECT c FROM Collectionobjectattachment c WHERE c.ordinal = :ordinal")})
public class Collectionobjectattachment extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionObjectAttachmentID")
    private Integer collectionObjectAttachmentId;
     
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
    @NotNull
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @NotNull
    @ManyToOne(optional = false)
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Collectionobjectattachment() {
    }

    public Collectionobjectattachment(Integer collectionObjectAttachmentId) {
        this.collectionObjectAttachmentId = collectionObjectAttachmentId;
    }

    public Collectionobjectattachment(Integer collectionObjectAttachmentId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.collectionObjectAttachmentId = collectionObjectAttachmentId; 
        this.collectionMemberId = collectionMemberId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectionObjectAttachmentId != null) ? collectionObjectAttachmentId.toString() : "0";
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Integer getCollectionObjectAttachmentId() {
        return collectionObjectAttachmentId;
    }

    public void setCollectionObjectAttachmentId(Integer collectionObjectAttachmentId) {
        this.collectionObjectAttachmentId = collectionObjectAttachmentId;
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

    @XmlTransient
    @NotNull(message="Collectionobject must be specified.")
    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
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

     /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Collectionobject) {
            this.collectionObject = (Collectionobject)parent;   
        }
    }
    
    @Override
    public String getEntityName() {
        return "collectionObjectAttachment";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionObjectAttachmentId != null ? collectionObjectAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectattachment)) {
            return false;
        }
        Collectionobjectattachment other = (Collectionobjectattachment) object;
        if ((this.collectionObjectAttachmentId == null && other.collectionObjectAttachmentId != null) || (this.collectionObjectAttachmentId != null && !this.collectionObjectAttachmentId.equals(other.collectionObjectAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobjectattachment[ collectionObjectAttachmentID=" + collectionObjectAttachmentId + " ]";
    }

 
}

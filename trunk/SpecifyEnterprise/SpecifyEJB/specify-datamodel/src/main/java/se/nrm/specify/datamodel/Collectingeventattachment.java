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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectingeventattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectingeventattachment.findAll", query = "SELECT c FROM Collectingeventattachment c"),
    @NamedQuery(name = "Collectingeventattachment.findByCollectingEventAttachmentID", query = "SELECT c FROM Collectingeventattachment c WHERE c.collectingEventAttachmentId = :collectingEventAttachmentID"),
    @NamedQuery(name = "Collectingeventattachment.findByTimestampCreated", query = "SELECT c FROM Collectingeventattachment c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectingeventattachment.findByTimestampModified", query = "SELECT c FROM Collectingeventattachment c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectingeventattachment.findByVersion", query = "SELECT c FROM Collectingeventattachment c WHERE c.version = :version"),
    @NamedQuery(name = "Collectingeventattachment.findByCollectionMemberID", query = "SELECT c FROM Collectingeventattachment c WHERE c.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Collectingeventattachment.findByOrdinal", query = "SELECT c FROM Collectingeventattachment c WHERE c.ordinal = :ordinal")})
public class Collectingeventattachment extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectingEventAttachmentID")
    private Integer collectingEventAttachmentId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ordinal")
    private int ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CollectingEventID", referencedColumnName = "CollectingEventID")
    @ManyToOne(optional = false)
    private Collectingevent collectingEvent;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Collectingeventattachment() {
    }

    public Collectingeventattachment(Integer collectingEventAttachmentId) {
        this.collectingEventAttachmentId = collectingEventAttachmentId;
    }

    public Collectingeventattachment(Integer collectingEventAttachmentId, Date timestampCreated, int collectionMemberId, int ordinal) {
        super(timestampCreated);
        this.collectingEventAttachmentId = collectingEventAttachmentId; 
        this.collectionMemberId = collectionMemberId;
        this.ordinal = ordinal;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectingEventAttachmentId != null) ? collectingEventAttachmentId.toString() : "0";
    }

    public Integer getCollectingEventAttachmentId() {
        return collectingEventAttachmentId;
    }
 
    
    public void setCollectingEventAttachmentId(Integer collectingEventAttachmentId) {
        this.collectingEventAttachmentId = collectingEventAttachmentId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

 

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
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
    @NotNull(message="Collectingevent must be specified.")
    public Collectingevent getCollectingEvent() {
        return collectingEvent;
    }

    public void setCollectingEvent(Collectingevent collectingEvent) {
        this.collectingEvent = collectingEvent;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
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
        if(parent instanceof Collectingevent) {
            this.collectingEvent = (Collectingevent)parent;   
        }
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectingEventAttachmentId != null ? collectingEventAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectingeventattachment)) {
            return false;
        }
        Collectingeventattachment other = (Collectingeventattachment) object;
        if ((this.collectingEventAttachmentId == null && other.collectingEventAttachmentId != null) || (this.collectingEventAttachmentId != null && !this.collectingEventAttachmentId.equals(other.collectingEventAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectingeventattachment[ collectingEventAttachmentID=" + collectingEventAttachmentId + " ]";
    } 
}

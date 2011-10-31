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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectionobjectattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectattachment.findAll", query = "SELECT c FROM Collectionobjectattachment c"),
    @NamedQuery(name = "Collectionobjectattachment.findByCollectionObjectAttachmentID", query = "SELECT c FROM Collectionobjectattachment c WHERE c.collectionObjectAttachmentID = :collectionObjectAttachmentID"),
    @NamedQuery(name = "Collectionobjectattachment.findByTimestampCreated", query = "SELECT c FROM Collectionobjectattachment c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobjectattachment.findByTimestampModified", query = "SELECT c FROM Collectionobjectattachment c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobjectattachment.findByVersion", query = "SELECT c FROM Collectionobjectattachment c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobjectattachment.findByCollectionMemberID", query = "SELECT c FROM Collectionobjectattachment c WHERE c.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Collectionobjectattachment.findByOrdinal", query = "SELECT c FROM Collectionobjectattachment c WHERE c.ordinal = :ordinal")})
public class Collectionobjectattachment extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionObjectAttachmentID")
    private Integer collectionObjectAttachmentID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Collectionobjectattachment() {
    }

    public Collectionobjectattachment(Integer collectionObjectAttachmentID) {
        this.collectionObjectAttachmentID = collectionObjectAttachmentID;
    }

    public Collectionobjectattachment(Integer collectionObjectAttachmentID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.collectionObjectAttachmentID = collectionObjectAttachmentID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getCollectionObjectAttachmentID() {
        return collectionObjectAttachmentID;
    }

    public void setCollectionObjectAttachmentID(Integer collectionObjectAttachmentID) {
        this.collectionObjectAttachmentID = collectionObjectAttachmentID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
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

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Collectionobject getCollectionObjectID() {
        return collectionObjectID;
    }

    public void setCollectionObjectID(Collectionobject collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
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
        hash += (collectionObjectAttachmentID != null ? collectionObjectAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectattachment)) {
            return false;
        }
        Collectionobjectattachment other = (Collectionobjectattachment) object;
        if ((this.collectionObjectAttachmentID == null && other.collectionObjectAttachmentID != null) || (this.collectionObjectAttachmentID != null && !this.collectionObjectAttachmentID.equals(other.collectionObjectAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobjectattachment[ collectionObjectAttachmentID=" + collectionObjectAttachmentID + " ]";
    }
    
}

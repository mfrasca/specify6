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
@Table(name = "preparationattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preparationattachment.findAll", query = "SELECT p FROM Preparationattachment p"),
    @NamedQuery(name = "Preparationattachment.findByPreparationAttachmentID", query = "SELECT p FROM Preparationattachment p WHERE p.preparationAttachmentID = :preparationAttachmentID"),
    @NamedQuery(name = "Preparationattachment.findByTimestampCreated", query = "SELECT p FROM Preparationattachment p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Preparationattachment.findByTimestampModified", query = "SELECT p FROM Preparationattachment p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Preparationattachment.findByVersion", query = "SELECT p FROM Preparationattachment p WHERE p.version = :version"),
    @NamedQuery(name = "Preparationattachment.findByCollectionMemberID", query = "SELECT p FROM Preparationattachment p WHERE p.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Preparationattachment.findByOrdinal", query = "SELECT p FROM Preparationattachment p WHERE p.ordinal = :ordinal")})
public class Preparationattachment extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PreparationAttachmentID")
    private Integer preparationAttachmentID;
     
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
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne(optional = false)
    private Preparation preparationID;

    public Preparationattachment() {
    }

    public Preparationattachment(Integer preparationAttachmentID) {
        this.preparationAttachmentID = preparationAttachmentID;
    }

    public Preparationattachment(Integer preparationAttachmentID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.preparationAttachmentID = preparationAttachmentID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getPreparationAttachmentID() {
        return preparationAttachmentID;
    }

    public void setPreparationAttachmentID(Integer preparationAttachmentID) {
        this.preparationAttachmentID = preparationAttachmentID;
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

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Preparation getPreparationID() {
        return preparationID;
    }

    public void setPreparationID(Preparation preparationID) {
        this.preparationID = preparationID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preparationAttachmentID != null ? preparationAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preparationattachment)) {
            return false;
        }
        Preparationattachment other = (Preparationattachment) object;
        if ((this.preparationAttachmentID == null && other.preparationAttachmentID != null) || (this.preparationAttachmentID != null && !this.preparationAttachmentID.equals(other.preparationAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preparationattachment[ preparationAttachmentID=" + preparationAttachmentID + " ]";
    }
    
}

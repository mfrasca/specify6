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
@Table(name = "collectionobjectcitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectcitation.findAll", query = "SELECT c FROM Collectionobjectcitation c"),
    @NamedQuery(name = "Collectionobjectcitation.findByCollectionObjectCitationID", query = "SELECT c FROM Collectionobjectcitation c WHERE c.collectionObjectCitationID = :collectionObjectCitationID"),
    @NamedQuery(name = "Collectionobjectcitation.findByTimestampCreated", query = "SELECT c FROM Collectionobjectcitation c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobjectcitation.findByTimestampModified", query = "SELECT c FROM Collectionobjectcitation c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobjectcitation.findByVersion", query = "SELECT c FROM Collectionobjectcitation c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobjectcitation.findByCollectionMemberID", query = "SELECT c FROM Collectionobjectcitation c WHERE c.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Collectionobjectcitation.findByIsFigured", query = "SELECT c FROM Collectionobjectcitation c WHERE c.isFigured = :isFigured")})
public class Collectionobjectcitation extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionObjectCitationID")
    private Integer collectionObjectCitationID;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Column(name = "IsFigured")
    private Boolean isFigured;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWorkID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Collectionobjectcitation() {
    }

    public Collectionobjectcitation(Integer collectionObjectCitationID) {
        this.collectionObjectCitationID = collectionObjectCitationID;
    }

    public Collectionobjectcitation(Integer collectionObjectCitationID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.collectionObjectCitationID = collectionObjectCitationID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getCollectionObjectCitationID() {
        return collectionObjectCitationID;
    }

    public void setCollectionObjectCitationID(Integer collectionObjectCitationID) {
        this.collectionObjectCitationID = collectionObjectCitationID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public Boolean getIsFigured() {
        return isFigured;
    }

    public void setIsFigured(Boolean isFigured) {
        this.isFigured = isFigured;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Referencework getReferenceWorkID() {
        return referenceWorkID;
    }

    public void setReferenceWorkID(Referencework referenceWorkID) {
        this.referenceWorkID = referenceWorkID;
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
        hash += (collectionObjectCitationID != null ? collectionObjectCitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectcitation)) {
            return false;
        }
        Collectionobjectcitation other = (Collectionobjectcitation) object;
        if ((this.collectionObjectCitationID == null && other.collectionObjectCitationID != null) || (this.collectionObjectCitationID != null && !this.collectionObjectCitationID.equals(other.collectionObjectCitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobjectcitation[ collectionObjectCitationID=" + collectionObjectCitationID + " ]";
    }
    
}

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
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectionobjectcitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectcitation.findAll", query = "SELECT c FROM Collectionobjectcitation c"),
    @NamedQuery(name = "Collectionobjectcitation.findByCollectionObjectCitationID", query = "SELECT c FROM Collectionobjectcitation c WHERE c.collectionObjectCitationId = :collectionObjectCitationID"),
    @NamedQuery(name = "Collectionobjectcitation.findByTimestampCreated", query = "SELECT c FROM Collectionobjectcitation c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobjectcitation.findByTimestampModified", query = "SELECT c FROM Collectionobjectcitation c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobjectcitation.findByVersion", query = "SELECT c FROM Collectionobjectcitation c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobjectcitation.findByCollectionMemberID", query = "SELECT c FROM Collectionobjectcitation c WHERE c.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Collectionobjectcitation.findByIsFigured", query = "SELECT c FROM Collectionobjectcitation c WHERE c.isFigured = :isFigured")})
public class Collectionobjectcitation extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionObjectCitationID")
    private Integer collectionObjectCitationId;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Column(name = "IsFigured")
    private Boolean isFigured;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWork;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Collectionobjectcitation() {
    }

    public Collectionobjectcitation(Integer collectionObjectCitationId) {
        this.collectionObjectCitationId = collectionObjectCitationId;
    }

    public Collectionobjectcitation(Integer collectionObjectCitationId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.collectionObjectCitationId = collectionObjectCitationId; 
        this.collectionMemberId = collectionMemberId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectionObjectCitationId != null) ? collectionObjectCitationId.toString() : "0";
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Integer getCollectionObjectCitationId() {
        return collectionObjectCitationId;
    }

    public void setCollectionObjectCitationId(Integer collectionObjectCitationId) {
        this.collectionObjectCitationId = collectionObjectCitationId;
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

    @XmlTransient
    @NotNull(message="CollectionObject must be specified.")
    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
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

    @NotNull(message="Reference must be specified.")
    public Referencework getReferenceWork() {
        return referenceWork;
    }

    public void setReferenceWork(Referencework referenceWork) {
        this.referenceWork = referenceWork;
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
    public int hashCode() {
        int hash = 0;
        hash += (collectionObjectCitationId != null ? collectionObjectCitationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectcitation)) {
            return false;
        }
        Collectionobjectcitation other = (Collectionobjectcitation) object;
        if ((this.collectionObjectCitationId == null && other.collectionObjectCitationId != null) || (this.collectionObjectCitationId != null && !this.collectionObjectCitationId.equals(other.collectionObjectCitationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobjectcitation[ collectionObjectCitationID=" + collectionObjectCitationId + " ]";
    }

  
}

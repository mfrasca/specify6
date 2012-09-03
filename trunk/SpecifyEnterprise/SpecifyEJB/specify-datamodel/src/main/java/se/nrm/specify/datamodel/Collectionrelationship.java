package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "collectionrelationship")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionrelationship.findAll", query = "SELECT c FROM Collectionrelationship c"),
    @NamedQuery(name = "Collectionrelationship.findByCollectionRelationshipId", query = "SELECT c FROM Collectionrelationship c WHERE c.collectionRelationshipId = :collectionRelationshipId"),
    @NamedQuery(name = "Collectionrelationship.findByTimestampCreated", query = "SELECT c FROM Collectionrelationship c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionrelationship.findByTimestampModified", query = "SELECT c FROM Collectionrelationship c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionrelationship.findByVersion", query = "SELECT c FROM Collectionrelationship c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionrelationship.findByText1", query = "SELECT c FROM Collectionrelationship c WHERE c.text1 = :text1"),
    @NamedQuery(name = "Collectionrelationship.findByText2", query = "SELECT c FROM Collectionrelationship c WHERE c.text2 = :text2")})
public class Collectionrelationship extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionRelationshipID")
    private Integer collectionRelationshipId;
     
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    private String text2;
    
    @JoinColumn(name = "RightSideCollectionID", referencedColumnName = "CollectionObjectID")
    @NotNull
    @ManyToOne(optional = false)
    private Collectionobject rightSide;
    
    @JoinColumn(name = "LeftSideCollectionID", referencedColumnName = "CollectionObjectID")
    @NotNull
    @ManyToOne(optional = false)
    private Collectionobject leftSide;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionRelTypeID", referencedColumnName = "CollectionRelTypeID")
    @ManyToOne
    private Collectionreltype collectionRelType;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Collectionrelationship() {
    }

    public Collectionrelationship(Integer collectionRelationshipId) {
        this.collectionRelationshipId = collectionRelationshipId;
    }

    public Collectionrelationship(Integer collectionRelationshipId, Date timestampCreated) {
        super(timestampCreated);
        this.collectionRelationshipId = collectionRelationshipId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectionRelationshipId != null) ? collectionRelationshipId.toString() : "0";
    }
 
    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Collectionreltype getCollectionRelType() {
        return collectionRelType;
    }

    public void setCollectionRelType(Collectionreltype collectionRelType) {
        this.collectionRelType = collectionRelType;
    }

    public Integer getCollectionRelationshipId() {
        return collectionRelationshipId;
    }

    public void setCollectionRelationshipId(Integer collectionRelationshipId) {
        this.collectionRelationshipId = collectionRelationshipId;
    }
 
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="LeftSide must be specified.")
    public Collectionobject getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(Collectionobject leftSide) {
        this.leftSide = leftSide;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @NotNull(message="RightSide must be specified.")
    public Collectionobject getRightSide() {
        return rightSide;
    }

    public void setRightSide(Collectionobject rightSide) {
        this.rightSide = rightSide;
    }

    @Override
    public String getEntityName() {
        return "collectionRelationship";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionRelationshipId != null ? collectionRelationshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionrelationship)) {
            return false;
        }
        Collectionrelationship other = (Collectionrelationship) object;
        if ((this.collectionRelationshipId == null && other.collectionRelationshipId != null) || (this.collectionRelationshipId != null && !this.collectionRelationshipId.equals(other.collectionRelationshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionrelationship[ collectionRelationshipId=" + collectionRelationshipId + " ]";
    }

  
}

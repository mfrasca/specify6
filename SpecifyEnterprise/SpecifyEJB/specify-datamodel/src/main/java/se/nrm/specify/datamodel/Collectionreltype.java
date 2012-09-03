package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectionreltype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionreltype.findAll", query = "SELECT c FROM Collectionreltype c"),
    @NamedQuery(name = "Collectionreltype.findByCollectionRelTypeId", query = "SELECT c FROM Collectionreltype c WHERE c.collectionRelTypeId = :collectionRelTypeId"),
    @NamedQuery(name = "Collectionreltype.findByTimestampCreated", query = "SELECT c FROM Collectionreltype c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionreltype.findByTimestampModified", query = "SELECT c FROM Collectionreltype c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionreltype.findByVersion", query = "SELECT c FROM Collectionreltype c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionreltype.findByName", query = "SELECT c FROM Collectionreltype c WHERE c.name = :name")})
public class Collectionreltype extends BaseEntity {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionRelTypeID")
    private Integer collectionRelTypeId;
     
    @Size(max = 32)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "collectionRelType", cascade= CascadeType.ALL)
    private Collection<Collectionrelationship> collectionrelationships;
    
    @JoinColumn(name = "RightSideCollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection rightSideCollection;
    
    @JoinColumn(name = "LeftSideCollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection leftSideCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Collectionreltype() {
    }

    public Collectionreltype(Integer collectionRelTypeId) {
        this.collectionRelTypeId = collectionRelTypeId;
    }

    public Collectionreltype(Integer collectionRelTypeID, Date timestampCreated) {
        super(timestampCreated);
        this.collectionRelTypeId = collectionRelTypeID; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectionRelTypeId != null) ? collectionRelTypeId.toString() : "0";
    }
    
    public Integer getCollectionRelTypeId() {
        return collectionRelTypeId;
    }

    public void setCollectionRelTypeId(Integer collectionRelTypeId) {
        this.collectionRelTypeId = collectionRelTypeId;
    }
 
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public se.nrm.specify.datamodel.Collection getLeftSideCollection() {
        return leftSideCollection;
    }

    public void setLeftSideCollection(se.nrm.specify.datamodel.Collection leftSideCollection) {
        this.leftSideCollection = leftSideCollection;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public se.nrm.specify.datamodel.Collection getRightSideCollection() {
        return rightSideCollection;
    }

    public void setRightSideCollection(se.nrm.specify.datamodel.Collection rightSideCollection) {
        this.rightSideCollection = rightSideCollection;
    }
 
 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<Collectionrelationship> getCollectionrelationships() {
        return collectionrelationships;
    }

    public void setCollectionrelationships(Collection<Collectionrelationship> collectionrelationships) {
        this.collectionrelationships = collectionrelationships;
    }

 
    @Override
    public String getEntityName() {
        return "collectionRelType";
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionRelTypeId != null ? collectionRelTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionreltype)) {
            return false;
        }
        Collectionreltype other = (Collectionreltype) object;
        if ((this.collectionRelTypeId == null && other.collectionRelTypeId != null) || (this.collectionRelTypeId != null && !this.collectionRelTypeId.equals(other.collectionRelTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionreltype[ collectionRelTypeId=" + collectionRelTypeId + " ]";
    }
 
}

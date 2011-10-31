package se.nrm.specify.datamodel;
 
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table; 
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Collectionreltype.findByCollectionRelTypeID", query = "SELECT c FROM Collectionreltype c WHERE c.collectionRelTypeID = :collectionRelTypeID"),
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
    private Integer collectionRelTypeID;
     
    @Size(max = 32)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "collectionRelTypeID")
    private Collection<Collectionrelationship> collectionrelationshipCollection;
    
    @JoinColumn(name = "RightSideCollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection rightSideCollectionID;
    
    @JoinColumn(name = "LeftSideCollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection leftSideCollectionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Collectionreltype() {
    }

    public Collectionreltype(Integer collectionRelTypeID) {
        this.collectionRelTypeID = collectionRelTypeID;
    }

    public Collectionreltype(Integer collectionRelTypeID, Date timestampCreated) {
        super(timestampCreated);
        this.collectionRelTypeID = collectionRelTypeID; 
    }

    public Integer getCollectionRelTypeID() {
        return collectionRelTypeID;
    }

    public void setCollectionRelTypeID(Integer collectionRelTypeID) {
        this.collectionRelTypeID = collectionRelTypeID;
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
    public Collection<Collectionrelationship> getCollectionrelationshipCollection() {
        return collectionrelationshipCollection;
    }

    public void setCollectionrelationshipCollection(Collection<Collectionrelationship> collectionrelationshipCollection) {
        this.collectionrelationshipCollection = collectionrelationshipCollection;
    }

    public se.nrm.specify.datamodel.Collection getRightSideCollectionID() {
        return rightSideCollectionID;
    }

    public void setRightSideCollectionID(se.nrm.specify.datamodel.Collection rightSideCollectionID) {
        this.rightSideCollectionID = rightSideCollectionID;
    }

    public se.nrm.specify.datamodel.Collection getLeftSideCollectionID() {
        return leftSideCollectionID;
    }

    public void setLeftSideCollectionID(se.nrm.specify.datamodel.Collection leftSideCollectionID) {
        this.leftSideCollectionID = leftSideCollectionID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionRelTypeID != null ? collectionRelTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionreltype)) {
            return false;
        }
        Collectionreltype other = (Collectionreltype) object;
        if ((this.collectionRelTypeID == null && other.collectionRelTypeID != null) || (this.collectionRelTypeID != null && !this.collectionRelTypeID.equals(other.collectionRelTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionreltype[ collectionRelTypeID=" + collectionRelTypeID + " ]";
    }
    
}

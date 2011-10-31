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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "container")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Container.findAll", query = "SELECT c FROM Container c"),
    @NamedQuery(name = "Container.findByContainerID", query = "SELECT c FROM Container c WHERE c.containerID = :containerID"),
    @NamedQuery(name = "Container.findByTimestampCreated", query = "SELECT c FROM Container c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Container.findByTimestampModified", query = "SELECT c FROM Container c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Container.findByVersion", query = "SELECT c FROM Container c WHERE c.version = :version"),
    @NamedQuery(name = "Container.findByCollectionMemberID", query = "SELECT c FROM Container c WHERE c.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Container.findByDescription", query = "SELECT c FROM Container c WHERE c.description = :description"),
    @NamedQuery(name = "Container.findByName", query = "SELECT c FROM Container c WHERE c.name = :name"),
    @NamedQuery(name = "Container.findByNumber", query = "SELECT c FROM Container c WHERE c.number = :number"),
    @NamedQuery(name = "Container.findByType", query = "SELECT c FROM Container c WHERE c.type = :type")})
public class Container extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ContainerID")
    private Integer containerID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "Number")
    private Integer number;
    
    @Column(name = "Type")
    private Short type;
    
    @JoinColumn(name = "StorageID", referencedColumnName = "StorageID")
    @ManyToOne
    private Storage storageID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "parentID")
    private Collection<Container> containerCollection;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "ContainerID")
    @ManyToOne
    private Container parentID;
    
    @OneToMany(mappedBy = "containerOwnerID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @OneToMany(mappedBy = "containerID")
    private Collection<Collectionobject> collectionobjectCollection1;

    public Container() {
    }

    public Container(Integer containerID) {
        this.containerID = containerID;
    }

    public Container(Integer containerID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.containerID = containerID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getContainerID() {
        return containerID;
    }

    public void setContainerID(Integer containerID) {
        this.containerID = containerID;
    } 

    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Storage getStorageID() {
        return storageID;
    }

    public void setStorageID(Storage storageID) {
        this.storageID = storageID;
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

    @XmlTransient
    public Collection<Container> getContainerCollection() {
        return containerCollection;
    }

    public void setContainerCollection(Collection<Container> containerCollection) {
        this.containerCollection = containerCollection;
    }

    public Container getParentID() {
        return parentID;
    }

    public void setParentID(Container parentID) {
        this.parentID = parentID;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection1() {
        return collectionobjectCollection1;
    }

    public void setCollectionobjectCollection1(Collection<Collectionobject> collectionobjectCollection1) {
        this.collectionobjectCollection1 = collectionobjectCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (containerID != null ? containerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Container)) {
            return false;
        }
        Container other = (Container) object;
        if ((this.containerID == null && other.containerID != null) || (this.containerID != null && !this.containerID.equals(other.containerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Container[ containerID=" + containerID + " ]";
    }
    
}

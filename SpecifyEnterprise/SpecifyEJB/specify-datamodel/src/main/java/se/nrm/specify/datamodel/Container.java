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
@Table(name = "container")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Container.findAll", query = "SELECT c FROM Container c"),
    @NamedQuery(name = "Container.findByContainerID", query = "SELECT c FROM Container c WHERE c.containerId = :containerID"),
    @NamedQuery(name = "Container.findByTimestampCreated", query = "SELECT c FROM Container c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Container.findByTimestampModified", query = "SELECT c FROM Container c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Container.findByVersion", query = "SELECT c FROM Container c WHERE c.version = :version"),
    @NamedQuery(name = "Container.findByCollectionMemberID", query = "SELECT c FROM Container c WHERE c.collectionMemberId = :collectionMemberID"),
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
    private Integer containerId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
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
    private Storage storage;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Container> children;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "ContainerID")
    @ManyToOne
    private Container parent;
    
    @OneToMany(mappedBy = "containerOwner")
    private Collection<Collectionobject> collectionObjectKids;
    
    @OneToMany(mappedBy = "container")
    private Collection<Collectionobject> collectionObjects;

    public Container() {
    }

    public Container(Integer containerId) {
        this.containerId = containerId;
    }

    public Container(Integer containerId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.containerId = containerId; 
        this.collectionMemberId = collectionMemberId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (containerId != null) ? containerId.toString() : "0";
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

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @XmlTransient
    public Collection<Container> getChildren() {
        return children;
    }

    public void setChildren(Collection<Container> children) {
        this.children = children;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionObjectKids() {
        return collectionObjectKids;
    }

    public void setCollectionObjectKids(Collection<Collectionobject> collectionObjectKids) {
        this.collectionObjectKids = collectionObjectKids;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionObjects() {
        return collectionObjects;
    }

    public void setCollectionObjects(Collection<Collectionobject> collectionObjects) {
        this.collectionObjects = collectionObjects;
    }

    public Integer getContainerId() {
        return containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (containerId != null ? containerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Container)) {
            return false;
        }
        Container other = (Container) object;
        if ((this.containerId == null && other.containerId != null) || (this.containerId != null && !this.containerId.equals(other.containerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Container[ containerID=" + containerId + " ]";
    } 
}

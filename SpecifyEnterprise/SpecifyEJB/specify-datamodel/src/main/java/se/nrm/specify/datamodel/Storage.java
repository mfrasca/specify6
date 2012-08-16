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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
@Table(name = "storage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storage.findAll", query = "SELECT s FROM Storage s"),
    @NamedQuery(name = "Storage.findByStorageID", query = "SELECT s FROM Storage s WHERE s.storageId = :storageID"),
    @NamedQuery(name = "Storage.findByTimestampCreated", query = "SELECT s FROM Storage s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Storage.findByTimestampModified", query = "SELECT s FROM Storage s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Storage.findByVersion", query = "SELECT s FROM Storage s WHERE s.version = :version"),
    @NamedQuery(name = "Storage.findByAbbrev", query = "SELECT s FROM Storage s WHERE s.abbrev = :abbrev"),
    @NamedQuery(name = "Storage.findByFullName", query = "SELECT s FROM Storage s WHERE s.fullName = :fullName"),
    @NamedQuery(name = "Storage.findByHighestChildNodeNumber", query = "SELECT s FROM Storage s WHERE s.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Storage.findByIsAccepted", query = "SELECT s FROM Storage s WHERE s.isAccepted = :isAccepted"),
    @NamedQuery(name = "Storage.findByName", query = "SELECT s FROM Storage s WHERE s.name = :name"),
    @NamedQuery(name = "Storage.findByNodeNumber", query = "SELECT s FROM Storage s WHERE s.nodeNumber = :nodeNumber"),
    @NamedQuery(name = "Storage.findByNumber1", query = "SELECT s FROM Storage s WHERE s.number1 = :number1"),
    @NamedQuery(name = "Storage.findByNumber2", query = "SELECT s FROM Storage s WHERE s.number2 = :number2"),
    @NamedQuery(name = "Storage.findByRankID", query = "SELECT s FROM Storage s WHERE s.rankId = :rankID"),
    @NamedQuery(name = "Storage.findByText1", query = "SELECT s FROM Storage s WHERE s.text1 = :text1"),
    @NamedQuery(name = "Storage.findByText2", query = "SELECT s FROM Storage s WHERE s.text2 = :text2"),
    @NamedQuery(name = "Storage.findByTimestampVersion", query = "SELECT s FROM Storage s WHERE s.timestampVersion = :timestampVersion")})
public class Storage extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "StorageID")
    private Integer storageId;
     
    @Size(max = 16)
    @Column(name = "Abbrev")
    private String abbrev;
    
    @Size(max = 255)
    @Column(name = "FullName")
    private String fullName;
    
    @Column(name = "HighestChildNodeNumber")
    private Integer highestChildNodeNumber;
    
    @Column(name = "IsAccepted")
    private Boolean isAccepted;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "TimestampVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampVersion;
    
    @Column(name = "NodeNumber")
    private Integer nodeNumber;
    
    @Column(name = "Number1")
    private Integer number1;
    
    @Column(name = "Number2")
    private Integer number2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    private String text2;
    
    @OneToMany(mappedBy = "storage")
    private Collection<Preparation> preparations;
    
    @OneToMany(mappedBy = "storage")
    private Collection<Container> containers;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Storage> children;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "StorageID")
    @ManyToOne
    private Storage parent;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "StorageTreeDefID", referencedColumnName = "StorageTreeDefID")
    @ManyToOne(optional = false)
    private Storagetreedef definition;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "StorageTreeDefItemID", referencedColumnName = "StorageTreeDefItemID")
    @ManyToOne(optional = false)
    private Storagetreedefitem definitionItem;
    
    @OneToMany(mappedBy = "acceptedStorage")
    private Collection<Storage> acceptedChildren;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "StorageID")
    @ManyToOne
    private Storage acceptedStorage;

    public Storage() {
    }

    public Storage(Integer storageId) {
        this.storageId = storageId;
    }

    public Storage(Integer storageId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.storageId = storageId; 
        this.name = name;
        this.rankId = rankId;
    }
     
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (storageId != null) ? storageId.toString() : "0";
    }
    @XmlTransient
    public Collection<Storage> getAcceptedChildren() {
        return acceptedChildren;
    }

    public void setAcceptedChildren(Collection<Storage> acceptedChildren) {
        this.acceptedChildren = acceptedChildren;
    }

    public Storage getAcceptedStorage() {
        return acceptedStorage;
    }

    public void setAcceptedStorage(Storage acceptedStorage) {
        this.acceptedStorage = acceptedStorage;
    }

    @XmlTransient
    public Collection<Storage> getChildren() {
        return children;
    }

    public void setChildren(Collection<Storage> children) {
        this.children = children;
    }

    @XmlTransient
    public Collection<Container> getContainers() {
        return containers;
    }

    public void setContainers(Collection<Container> containers) {
        this.containers = containers;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Definition must be specified.")
    public Storagetreedef getDefinition() {
        return definition;
    }

    public void setDefinition(Storagetreedef definition) {
        this.definition = definition;
    }

    @NotNull(message="DefinitionItem must be specified.")
    public Storagetreedefitem getDefinitionItem() {
        return definitionItem;
    }

    public void setDefinitionItem(Storagetreedefitem definitionItem) {
        this.definitionItem = definitionItem;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Storage getParent() {
        return parent;
    }

    public void setParent(Storage parent) {
        this.parent = parent;
    }

    @XmlTransient
    public Collection<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(Collection<Preparation> preparations) {
        this.preparations = preparations;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

 
 
    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getHighestChildNodeNumber() {
        return highestChildNodeNumber;
    }

    public void setHighestChildNodeNumber(Integer highestChildNodeNumber) {
        this.highestChildNodeNumber = highestChildNodeNumber;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }
 

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    
    public Date getTimestampVersion() {
        return timestampVersion;
    }

    public void setTimestampVersion(Date timestampVersion) {
        this.timestampVersion = timestampVersion;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storageId != null ? storageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storage)) {
            return false;
        }
        Storage other = (Storage) object;
        if ((this.storageId == null && other.storageId != null) || (this.storageId != null && !this.storageId.equals(other.storageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storage[ storageId=" + storageId + " ]";
    } 
 
}

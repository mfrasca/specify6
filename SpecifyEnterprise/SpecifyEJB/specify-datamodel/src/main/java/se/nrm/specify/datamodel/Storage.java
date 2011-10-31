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
    @NamedQuery(name = "Storage.findByStorageID", query = "SELECT s FROM Storage s WHERE s.storageID = :storageID"),
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
    @NamedQuery(name = "Storage.findByRankID", query = "SELECT s FROM Storage s WHERE s.rankID = :rankID"),
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
    private Integer storageID;
     
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
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "NodeNumber")
    private Integer nodeNumber;
    
    @Column(name = "Number1")
    private Integer number1;
    
    @Column(name = "Number2")
    private Integer number2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankID;
    
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
    
    @Column(name = "TimestampVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampVersion;
    
    @OneToMany(mappedBy = "storageID")
    private Collection<Preparation> preparationCollection;
    
    @OneToMany(mappedBy = "storageID")
    private Collection<Container> containerCollection;
    
    @OneToMany(mappedBy = "parentID")
    private Collection<Storage> storageCollection;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "StorageID")
    @ManyToOne
    private Storage parentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "StorageTreeDefID", referencedColumnName = "StorageTreeDefID")
    @ManyToOne(optional = false)
    private Storagetreedef storageTreeDefID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "StorageTreeDefItemID", referencedColumnName = "StorageTreeDefItemID")
    @ManyToOne(optional = false)
    private Storagetreedefitem storageTreeDefItemID;
    
    @OneToMany(mappedBy = "acceptedID")
    private Collection<Storage> storageCollection1;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "StorageID")
    @ManyToOne
    private Storage acceptedID;

    public Storage() {
    }

    public Storage(Integer storageID) {
        this.storageID = storageID;
    }

    public Storage(Integer storageID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.storageID = storageID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getStorageID() {
        return storageID;
    }

    public void setStorageID(Integer storageID) {
        this.storageID = storageID;
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

    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
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

    @XmlTransient
    public Collection<Preparation> getPreparationCollection() {
        return preparationCollection;
    }

    public void setPreparationCollection(Collection<Preparation> preparationCollection) {
        this.preparationCollection = preparationCollection;
    }

    @XmlTransient
    public Collection<Container> getContainerCollection() {
        return containerCollection;
    }

    public void setContainerCollection(Collection<Container> containerCollection) {
        this.containerCollection = containerCollection;
    }

    @XmlTransient
    public Collection<Storage> getStorageCollection() {
        return storageCollection;
    }

    public void setStorageCollection(Collection<Storage> storageCollection) {
        this.storageCollection = storageCollection;
    }

    public Storage getParentID() {
        return parentID;
    }

    public void setParentID(Storage parentID) {
        this.parentID = parentID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Storagetreedef getStorageTreeDefID() {
        return storageTreeDefID;
    }

    public void setStorageTreeDefID(Storagetreedef storageTreeDefID) {
        this.storageTreeDefID = storageTreeDefID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Storagetreedefitem getStorageTreeDefItemID() {
        return storageTreeDefItemID;
    }

    public void setStorageTreeDefItemID(Storagetreedefitem storageTreeDefItemID) {
        this.storageTreeDefItemID = storageTreeDefItemID;
    }

    @XmlTransient
    public Collection<Storage> getStorageCollection1() {
        return storageCollection1;
    }

    public void setStorageCollection1(Collection<Storage> storageCollection1) {
        this.storageCollection1 = storageCollection1;
    }

    public Storage getAcceptedID() {
        return acceptedID;
    }

    public void setAcceptedID(Storage acceptedID) {
        this.acceptedID = acceptedID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storageID != null ? storageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storage)) {
            return false;
        }
        Storage other = (Storage) object;
        if ((this.storageID == null && other.storageID != null) || (this.storageID != null && !this.storageID.equals(other.storageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storage[ storageID=" + storageID + " ]";
    }
    
}

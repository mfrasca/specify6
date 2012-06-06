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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "storagetreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storagetreedefitem.findAll", query = "SELECT s FROM Storagetreedefitem s"),
    @NamedQuery(name = "Storagetreedefitem.findByStorageTreeDefItemID", query = "SELECT s FROM Storagetreedefitem s WHERE s.storageTreeDefItemId = :storageTreeDefItemID"),
    @NamedQuery(name = "Storagetreedefitem.findByTimestampCreated", query = "SELECT s FROM Storagetreedefitem s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Storagetreedefitem.findByTimestampModified", query = "SELECT s FROM Storagetreedefitem s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Storagetreedefitem.findByVersion", query = "SELECT s FROM Storagetreedefitem s WHERE s.version = :version"),
    @NamedQuery(name = "Storagetreedefitem.findByFullNameSeparator", query = "SELECT s FROM Storagetreedefitem s WHERE s.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Storagetreedefitem.findByIsEnforced", query = "SELECT s FROM Storagetreedefitem s WHERE s.isEnforced = :isEnforced"),
    @NamedQuery(name = "Storagetreedefitem.findByIsInFullName", query = "SELECT s FROM Storagetreedefitem s WHERE s.isInFullName = :isInFullName"),
    @NamedQuery(name = "Storagetreedefitem.findByName", query = "SELECT s FROM Storagetreedefitem s WHERE s.name = :name"),
    @NamedQuery(name = "Storagetreedefitem.findByRankID", query = "SELECT s FROM Storagetreedefitem s WHERE s.rankId = :rankID"),
    @NamedQuery(name = "Storagetreedefitem.findByTextAfter", query = "SELECT s FROM Storagetreedefitem s WHERE s.textAfter = :textAfter"),
    @NamedQuery(name = "Storagetreedefitem.findByTextBefore", query = "SELECT s FROM Storagetreedefitem s WHERE s.textBefore = :textBefore"),
    @NamedQuery(name = "Storagetreedefitem.findByTitle", query = "SELECT s FROM Storagetreedefitem s WHERE s.title = :title")})
public class Storagetreedefitem extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "StorageTreeDefItemID")
    private Integer storageTreeDefItemId;
     
    @Size(max = 32)
    @Column(name = "FullNameSeparator")
    private String fullNameSeparator;
    
    @Column(name = "IsEnforced")
    private Boolean isEnforced;
    
    @Column(name = "IsInFullName")
    private Boolean isInFullName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "TextAfter")
    private String textAfter;
    
    @Size(max = 64)
    @Column(name = "TextBefore")
    private String textBefore;
    
    @Size(max = 64)
    @Column(name = "Title")
    private String title;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "StorageTreeDefID", referencedColumnName = "StorageTreeDefID")
    @ManyToOne(optional = false)
    private Storagetreedef treeDef;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Storagetreedefitem> children;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "StorageTreeDefItemID")
    @ManyToOne
    private Storagetreedefitem parent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definitionItem")
    private Collection<Storage> treeEntries;

    public Storagetreedefitem() {
    }

    public Storagetreedefitem(Integer storageTreeDefItemId) {
        this.storageTreeDefItemId = storageTreeDefItemId;
    }

    public Storagetreedefitem(Integer storageTreeDefItemId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.storageTreeDefItemId = storageTreeDefItemId; 
        this.name = name;
        this.rankId = rankId;
    }

     
    public String getFullNameSeparator() {
        return fullNameSeparator;
    }

    public void setFullNameSeparator(String fullNameSeparator) {
        this.fullNameSeparator = fullNameSeparator;
    }

    public Boolean getIsEnforced() {
        return isEnforced;
    }

    public void setIsEnforced(Boolean isEnforced) {
        this.isEnforced = isEnforced;
    }

    public Boolean getIsInFullName() {
        return isInFullName;
    }

    public void setIsInFullName(Boolean isInFullName) {
        this.isInFullName = isInFullName;
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

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public Collection<Storagetreedefitem> getChildren() {
        return children;
    }

    public void setChildren(Collection<Storagetreedefitem> children) {
        this.children = children;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Storagetreedefitem getParent() {
        return parent;
    }

    public void setParent(Storagetreedefitem parent) {
        this.parent = parent;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public Integer getStorageTreeDefItemId() {
        return storageTreeDefItemId;
    }

    public void setStorageTreeDefItemId(Integer storageTreeDefItemId) {
        this.storageTreeDefItemId = storageTreeDefItemId;
    }

    public Storagetreedef getTreeDef() {
        return treeDef;
    }

    public void setTreeDef(Storagetreedef treeDef) {
        this.treeDef = treeDef;
    }

    @XmlTransient
    public Collection<Storage> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Storage> treeEntries) {
        this.treeEntries = treeEntries;
    }

     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storageTreeDefItemId != null ? storageTreeDefItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storagetreedefitem)) {
            return false;
        }
        Storagetreedefitem other = (Storagetreedefitem) object;
        if ((this.storageTreeDefItemId == null && other.storageTreeDefItemId != null) || (this.storageTreeDefItemId != null && !this.storageTreeDefItemId.equals(other.storageTreeDefItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storagetreedefitem[ storageTreeDefItemID=" + storageTreeDefItemId + " ]";
    }
    
}

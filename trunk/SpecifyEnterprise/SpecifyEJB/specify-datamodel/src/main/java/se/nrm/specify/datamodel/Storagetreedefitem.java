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
    @NamedQuery(name = "Storagetreedefitem.findByStorageTreeDefItemID", query = "SELECT s FROM Storagetreedefitem s WHERE s.storageTreeDefItemID = :storageTreeDefItemID"),
    @NamedQuery(name = "Storagetreedefitem.findByTimestampCreated", query = "SELECT s FROM Storagetreedefitem s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Storagetreedefitem.findByTimestampModified", query = "SELECT s FROM Storagetreedefitem s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Storagetreedefitem.findByVersion", query = "SELECT s FROM Storagetreedefitem s WHERE s.version = :version"),
    @NamedQuery(name = "Storagetreedefitem.findByFullNameSeparator", query = "SELECT s FROM Storagetreedefitem s WHERE s.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Storagetreedefitem.findByIsEnforced", query = "SELECT s FROM Storagetreedefitem s WHERE s.isEnforced = :isEnforced"),
    @NamedQuery(name = "Storagetreedefitem.findByIsInFullName", query = "SELECT s FROM Storagetreedefitem s WHERE s.isInFullName = :isInFullName"),
    @NamedQuery(name = "Storagetreedefitem.findByName", query = "SELECT s FROM Storagetreedefitem s WHERE s.name = :name"),
    @NamedQuery(name = "Storagetreedefitem.findByRankID", query = "SELECT s FROM Storagetreedefitem s WHERE s.rankID = :rankID"),
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
    private Integer storageTreeDefItemID;
     
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
    private int rankID;
    
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
    private Agent createdByAgentID;
    
    @JoinColumn(name = "StorageTreeDefID", referencedColumnName = "StorageTreeDefID")
    @ManyToOne(optional = false)
    private Storagetreedef storageTreeDefID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "parentItemID")
    private Collection<Storagetreedefitem> storagetreedefitemCollection;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "StorageTreeDefItemID")
    @ManyToOne
    private Storagetreedefitem parentItemID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageTreeDefItemID")
    private Collection<Storage> storageCollection;

    public Storagetreedefitem() {
    }

    public Storagetreedefitem(Integer storageTreeDefItemID) {
        this.storageTreeDefItemID = storageTreeDefItemID;
    }

    public Storagetreedefitem(Integer storageTreeDefItemID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.storageTreeDefItemID = storageTreeDefItemID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getStorageTreeDefItemID() {
        return storageTreeDefItemID;
    }

    public void setStorageTreeDefItemID(Integer storageTreeDefItemID) {
        this.storageTreeDefItemID = storageTreeDefItemID;
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

    @XmlTransient
    public Collection<Storagetreedefitem> getStoragetreedefitemCollection() {
        return storagetreedefitemCollection;
    }

    public void setStoragetreedefitemCollection(Collection<Storagetreedefitem> storagetreedefitemCollection) {
        this.storagetreedefitemCollection = storagetreedefitemCollection;
    }

    public Storagetreedefitem getParentItemID() {
        return parentItemID;
    }

    public void setParentItemID(Storagetreedefitem parentItemID) {
        this.parentItemID = parentItemID;
    }

    @XmlTransient
    public Collection<Storage> getStorageCollection() {
        return storageCollection;
    }

    public void setStorageCollection(Collection<Storage> storageCollection) {
        this.storageCollection = storageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storageTreeDefItemID != null ? storageTreeDefItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storagetreedefitem)) {
            return false;
        }
        Storagetreedefitem other = (Storagetreedefitem) object;
        if ((this.storageTreeDefItemID == null && other.storageTreeDefItemID != null) || (this.storageTreeDefItemID != null && !this.storageTreeDefItemID.equals(other.storageTreeDefItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storagetreedefitem[ storageTreeDefItemID=" + storageTreeDefItemID + " ]";
    }
    
}

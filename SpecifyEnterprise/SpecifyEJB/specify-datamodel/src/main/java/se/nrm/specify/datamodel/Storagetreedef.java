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
@Table(name = "storagetreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storagetreedef.findAll", query = "SELECT s FROM Storagetreedef s"),
    @NamedQuery(name = "Storagetreedef.findByStorageTreeDefID", query = "SELECT s FROM Storagetreedef s WHERE s.storageTreeDefId = :storageTreeDefID"),
    @NamedQuery(name = "Storagetreedef.findByTimestampCreated", query = "SELECT s FROM Storagetreedef s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Storagetreedef.findByTimestampModified", query = "SELECT s FROM Storagetreedef s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Storagetreedef.findByVersion", query = "SELECT s FROM Storagetreedef s WHERE s.version = :version"),
    @NamedQuery(name = "Storagetreedef.findByFullNameDirection", query = "SELECT s FROM Storagetreedef s WHERE s.fullNameDirection = :fullNameDirection"),
    @NamedQuery(name = "Storagetreedef.findByName", query = "SELECT s FROM Storagetreedef s WHERE s.name = :name")})
public class Storagetreedef extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "StorageTreeDefID")
    private Integer storageTreeDefId;
     
    @Column(name = "FullNameDirection")
    private Integer fullNameDirection;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treeDef")
    private Collection<Storagetreedefitem> treeDefItems;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "storageTreeDef")
    private Collection<Institution> institutions;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Storage> treeEntries;

    public Storagetreedef() {
    }

    public Storagetreedef(Integer storageTreeDefId) {
        this.storageTreeDefId = storageTreeDefId;
    }

    public Storagetreedef(Integer storageTreeDefId, Date timestampCreated, String name) {
        super(timestampCreated);
        this.storageTreeDefId = storageTreeDefId; 
        this.name = name;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (storageTreeDefId != null) ? storageTreeDefId.toString() : "0";
    }
    
    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Collection<Institution> institutions) {
        this.institutions = institutions;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getStorageTreeDefId() {
        return storageTreeDefId;
    }

    public void setStorageTreeDefId(Integer storageTreeDefId) {
        this.storageTreeDefId = storageTreeDefId;
    }

    @XmlTransient
    public Collection<Storagetreedefitem> getTreeDefItems() {
        return treeDefItems;
    }

    public void setTreeDefItems(Collection<Storagetreedefitem> treeDefItems) {
        this.treeDefItems = treeDefItems;
    }

    @XmlTransient
    public Collection<Storage> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Storage> treeEntries) {
        this.treeEntries = treeEntries;
    }

 
    public Integer getFullNameDirection() {
        return fullNameDirection;
    }

    public void setFullNameDirection(Integer fullNameDirection) {
        this.fullNameDirection = fullNameDirection;
    }

    @NotNull(message="Name must be specified.")
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

   

 

     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storageTreeDefId != null ? storageTreeDefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storagetreedef)) {
            return false;
        }
        Storagetreedef other = (Storagetreedef) object;
        if ((this.storageTreeDefId == null && other.storageTreeDefId != null) || (this.storageTreeDefId != null && !this.storageTreeDefId.equals(other.storageTreeDefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storagetreedef[ storageTreeDefID=" + storageTreeDefId + " ]";
    }
 
}

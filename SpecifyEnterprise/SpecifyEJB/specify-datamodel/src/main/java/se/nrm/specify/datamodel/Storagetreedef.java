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
@Table(name = "storagetreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storagetreedef.findAll", query = "SELECT s FROM Storagetreedef s"),
    @NamedQuery(name = "Storagetreedef.findByStorageTreeDefID", query = "SELECT s FROM Storagetreedef s WHERE s.storageTreeDefID = :storageTreeDefID"),
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
    private Integer storageTreeDefID;
     
    @Column(name = "FullNameDirection")
    private Integer fullNameDirection;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageTreeDefID")
    private Collection<Storagetreedefitem> storagetreedefitemCollection;
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    @OneToMany(mappedBy = "storageTreeDefID")
    private Collection<Institution> institutionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storageTreeDefID")
    private Collection<Storage> storageCollection;

    public Storagetreedef() {
    }

    public Storagetreedef(Integer storageTreeDefID) {
        this.storageTreeDefID = storageTreeDefID;
    }

    public Storagetreedef(Integer storageTreeDefID, Date timestampCreated, String name) {
        super(timestampCreated);
        this.storageTreeDefID = storageTreeDefID; 
        this.name = name;
    }

    public Integer getStorageTreeDefID() {
        return storageTreeDefID;
    }

    public void setStorageTreeDefID(Integer storageTreeDefID) {
        this.storageTreeDefID = storageTreeDefID;
    }
 
    public Integer getFullNameDirection() {
        return fullNameDirection;
    }

    public void setFullNameDirection(Integer fullNameDirection) {
        this.fullNameDirection = fullNameDirection;
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
    public Collection<Storagetreedefitem> getStoragetreedefitemCollection() {
        return storagetreedefitemCollection;
    }

    public void setStoragetreedefitemCollection(Collection<Storagetreedefitem> storagetreedefitemCollection) {
        this.storagetreedefitemCollection = storagetreedefitemCollection;
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
    public Collection<Institution> getInstitutionCollection() {
        return institutionCollection;
    }

    public void setInstitutionCollection(Collection<Institution> institutionCollection) {
        this.institutionCollection = institutionCollection;
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
        hash += (storageTreeDefID != null ? storageTreeDefID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storagetreedef)) {
            return false;
        }
        Storagetreedef other = (Storagetreedef) object;
        if ((this.storageTreeDefID == null && other.storageTreeDefID != null) || (this.storageTreeDefID != null && !this.storageTreeDefID.equals(other.storageTreeDefID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Storagetreedef[ storageTreeDefID=" + storageTreeDefID + " ]";
    }
    
}

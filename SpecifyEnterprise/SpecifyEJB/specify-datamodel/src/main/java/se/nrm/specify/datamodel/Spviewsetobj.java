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
@Table(name = "spviewsetobj")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spviewsetobj.findAll", query = "SELECT s FROM Spviewsetobj s"),
    @NamedQuery(name = "Spviewsetobj.findBySpViewSetObjID", query = "SELECT s FROM Spviewsetobj s WHERE s.spViewSetObjID = :spViewSetObjID"),
    @NamedQuery(name = "Spviewsetobj.findByTimestampCreated", query = "SELECT s FROM Spviewsetobj s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spviewsetobj.findByTimestampModified", query = "SELECT s FROM Spviewsetobj s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spviewsetobj.findByVersion", query = "SELECT s FROM Spviewsetobj s WHERE s.version = :version"),
    @NamedQuery(name = "Spviewsetobj.findByDescription", query = "SELECT s FROM Spviewsetobj s WHERE s.description = :description"),
    @NamedQuery(name = "Spviewsetobj.findByFileName", query = "SELECT s FROM Spviewsetobj s WHERE s.fileName = :fileName"),
    @NamedQuery(name = "Spviewsetobj.findByLevel", query = "SELECT s FROM Spviewsetobj s WHERE s.level = :level"),
    @NamedQuery(name = "Spviewsetobj.findByMetaData", query = "SELECT s FROM Spviewsetobj s WHERE s.metaData = :metaData"),
    @NamedQuery(name = "Spviewsetobj.findByName", query = "SELECT s FROM Spviewsetobj s WHERE s.name = :name")})
public class Spviewsetobj extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpViewSetObjID")
    private Integer spViewSetObjID;
     
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 255)
    @Column(name = "FileName")
    private String fileName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Level")
    private short level;
    
    @Size(max = 255)
    @Column(name = "MetaData")
    private String metaData;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @OneToMany(mappedBy = "spViewSetObjID")
    private Collection<Spappresourcedata> spappresourcedataCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpAppResourceDirID", referencedColumnName = "SpAppResourceDirID")
    @ManyToOne(optional = false)
    private Spappresourcedir spAppResourceDirID;

    public Spviewsetobj() {
    }

    public Spviewsetobj(Integer spViewSetObjID) {
        this.spViewSetObjID = spViewSetObjID;
    }

    public Spviewsetobj(Integer spViewSetObjID, Date timestampCreated, short level, String name) {
        super(timestampCreated);
        this.spViewSetObjID = spViewSetObjID; 
        this.level = level;
        this.name = name;
    }

    public Integer getSpViewSetObjID() {
        return spViewSetObjID;
    }

    public void setSpViewSetObjID(Integer spViewSetObjID) {
        this.spViewSetObjID = spViewSetObjID;
    }
 
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Spappresourcedata> getSpappresourcedataCollection() {
        return spappresourcedataCollection;
    }

    public void setSpappresourcedataCollection(Collection<Spappresourcedata> spappresourcedataCollection) {
        this.spappresourcedataCollection = spappresourcedataCollection;
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

    public Spappresourcedir getSpAppResourceDirID() {
        return spAppResourceDirID;
    }

    public void setSpAppResourceDirID(Spappresourcedir spAppResourceDirID) {
        this.spAppResourceDirID = spAppResourceDirID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spViewSetObjID != null ? spViewSetObjID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spviewsetobj)) {
            return false;
        }
        Spviewsetobj other = (Spviewsetobj) object;
        if ((this.spViewSetObjID == null && other.spViewSetObjID != null) || (this.spViewSetObjID != null && !this.spViewSetObjID.equals(other.spViewSetObjID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spviewsetobj[ spViewSetObjID=" + spViewSetObjID + " ]";
    }
    
}

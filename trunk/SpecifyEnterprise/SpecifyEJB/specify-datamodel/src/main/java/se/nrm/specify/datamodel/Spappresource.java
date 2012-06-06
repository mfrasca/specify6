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
@Table(name = "spappresource")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spappresource.findAll", query = "SELECT s FROM Spappresource s"),
    @NamedQuery(name = "Spappresource.findBySpAppResourceID", query = "SELECT s FROM Spappresource s WHERE s.spAppResourceId = :spAppResourceID"),
    @NamedQuery(name = "Spappresource.findByTimestampCreated", query = "SELECT s FROM Spappresource s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spappresource.findByTimestampModified", query = "SELECT s FROM Spappresource s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spappresource.findByVersion", query = "SELECT s FROM Spappresource s WHERE s.version = :version"),
    @NamedQuery(name = "Spappresource.findByAllPermissionLevel", query = "SELECT s FROM Spappresource s WHERE s.allPermissionLevel = :allPermissionLevel"),
    @NamedQuery(name = "Spappresource.findByDescription", query = "SELECT s FROM Spappresource s WHERE s.description = :description"),
    @NamedQuery(name = "Spappresource.findByGroupPermissionLevel", query = "SELECT s FROM Spappresource s WHERE s.groupPermissionLevel = :groupPermissionLevel"),
    @NamedQuery(name = "Spappresource.findByLevel", query = "SELECT s FROM Spappresource s WHERE s.level = :level"),
    @NamedQuery(name = "Spappresource.findByMetaData", query = "SELECT s FROM Spappresource s WHERE s.metaData = :metaData"),
    @NamedQuery(name = "Spappresource.findByMimeType", query = "SELECT s FROM Spappresource s WHERE s.mimeType = :mimeType"),
    @NamedQuery(name = "Spappresource.findByName", query = "SELECT s FROM Spappresource s WHERE s.name = :name"),
    @NamedQuery(name = "Spappresource.findByOwnerPermissionLevel", query = "SELECT s FROM Spappresource s WHERE s.ownerPermissionLevel = :ownerPermissionLevel")})
public class Spappresource extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpAppResourceID")
    private Integer spAppResourceId;
     
    @Column(name = "AllPermissionLevel")
    private Integer allPermissionLevel;
    
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Column(name = "GroupPermissionLevel")
    private Integer groupPermissionLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Level")
    private short level;
    
    @Size(max = 255)
    @Column(name = "MetaData")
    private String metaData;
    
    @Size(max = 255)
    @Column(name = "MimeType")
    private String mimeType;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "OwnerPermissionLevel")
    private Integer ownerPermissionLevel;
    
    @OneToMany(mappedBy = "spAppResource")
    private Collection<Spappresourcedata> spAppResourceDatas;
    
    @JoinColumn(name = "SpPrincipalID", referencedColumnName = "SpPrincipalID")
    @ManyToOne
    private Spprincipal group;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne(optional = false)
    private Specifyuser specifyUser;
    
    @JoinColumn(name = "SpAppResourceDirID", referencedColumnName = "SpAppResourceDirID")
    @ManyToOne(optional = false)
    private Spappresourcedir spAppResourceDir;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appResource")
    private Collection<Spreport> spReports;

    public Spappresource() {
    }

    public Spappresource(Integer spAppResourceId) {
        this.spAppResourceId = spAppResourceId;
    }

    public Spappresource(Integer spAppResourceId, Date timestampCreated, short level, String name) {
        super(timestampCreated);
        this.spAppResourceId = spAppResourceId; 
        this.level = level;
        this.name = name;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Spprincipal getGroup() {
        return group;
    }

    public void setGroup(Spprincipal group) {
        this.group = group;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Spappresourcedata> getSpAppResourceDatas() {
        return spAppResourceDatas;
    }

    public void setSpAppResourceDatas(Collection<Spappresourcedata> spAppResourceDatas) {
        this.spAppResourceDatas = spAppResourceDatas;
    }

    public Spappresourcedir getSpAppResourceDir() {
        return spAppResourceDir;
    }

    public void setSpAppResourceDir(Spappresourcedir spAppResourceDir) {
        this.spAppResourceDir = spAppResourceDir;
    }

    public Integer getSpAppResourceId() {
        return spAppResourceId;
    }

    public void setSpAppResourceId(Integer spAppResourceId) {
        this.spAppResourceId = spAppResourceId;
    }

    @XmlTransient
    public Collection<Spreport> getSpReports() {
        return spReports;
    }

    public void setSpReports(Collection<Spreport> spReports) {
        this.spReports = spReports;
    }

    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

 
 
    public Integer getAllPermissionLevel() {
        return allPermissionLevel;
    }

    public void setAllPermissionLevel(Integer allPermissionLevel) {
        this.allPermissionLevel = allPermissionLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGroupPermissionLevel() {
        return groupPermissionLevel;
    }

    public void setGroupPermissionLevel(Integer groupPermissionLevel) {
        this.groupPermissionLevel = groupPermissionLevel;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerPermissionLevel() {
        return ownerPermissionLevel;
    }

    public void setOwnerPermissionLevel(Integer ownerPermissionLevel) {
        this.ownerPermissionLevel = ownerPermissionLevel;
    }

    
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAppResourceId != null ? spAppResourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spappresource)) {
            return false;
        }
        Spappresource other = (Spappresource) object;
        if ((this.spAppResourceId == null && other.spAppResourceId != null) || (this.spAppResourceId != null && !this.spAppResourceId.equals(other.spAppResourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spappresource[ spAppResourceID=" + spAppResourceId + " ]";
    }
    
}

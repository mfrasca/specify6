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
import javax.persistence.ManyToMany;
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
@Table(name = "spprincipal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spprincipal.findAll", query = "SELECT s FROM Spprincipal s"),
    @NamedQuery(name = "Spprincipal.findBySpPrincipalID", query = "SELECT s FROM Spprincipal s WHERE s.userGroupId = :spPrincipalID"),
    @NamedQuery(name = "Spprincipal.findByTimestampCreated", query = "SELECT s FROM Spprincipal s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spprincipal.findByTimestampModified", query = "SELECT s FROM Spprincipal s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spprincipal.findByVersion", query = "SELECT s FROM Spprincipal s WHERE s.version = :version"),
    @NamedQuery(name = "Spprincipal.findByGroupSubClass", query = "SELECT s FROM Spprincipal s WHERE s.groupSubClass = :groupSubClass"),
    @NamedQuery(name = "Spprincipal.findByGroupType", query = "SELECT s FROM Spprincipal s WHERE s.groupType = :groupType"),
    @NamedQuery(name = "Spprincipal.findByName", query = "SELECT s FROM Spprincipal s WHERE s.name = :name"),
    @NamedQuery(name = "Spprincipal.findByPriority", query = "SELECT s FROM Spprincipal s WHERE s.priority = :priority"),
    @NamedQuery(name = "Spprincipal.findByUserGroupScopeID", query = "SELECT s FROM Spprincipal s WHERE s.scope = :scope")})
public class Spprincipal extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpPrincipalID")
    private Integer userGroupId;
     
    @Basic(optional = false) 
    @Size(min = 1, max = 255)
    @Column(name = "GroupSubClass")
    private String groupSubClass;
    
    @Size(max = 32)
    @Column(name = "groupType")
    private String groupType;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Priority")
    private short priority;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "userGroupScopeID")
    private Integer scope;
    
    @ManyToMany(mappedBy = "principals")
    private Collection<Sppermission> permissions;
    
    @ManyToMany(mappedBy = "spPrincipals")
    private Collection<Specifyuser> specifyUsers;
    
    @OneToMany(mappedBy = "group")
    private Collection<Workbench> workbenchs;
    
    @OneToMany(mappedBy = "group")
    private Collection<Recordset> recordsets;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "group")
    private Collection<Spappresource> spappresources;

    public Spprincipal() {
    }

    public Spprincipal(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Spprincipal(Integer userGroupId, Date timestampCreated, String groupSubClass, String name, short priority) {
        super(timestampCreated);
        this.userGroupId = userGroupId; 
        this.groupSubClass = groupSubClass;
        this.name = name;
        this.priority = priority;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (userGroupId != null) ? userGroupId.toString() : "0";
    }
 
    @NotNull(message="GroupSubClass must be specified.")
    public String getGroupSubClass() {
        return groupSubClass;
    }

    public void setGroupSubClass(String groupSubClass) {
        this.groupSubClass = groupSubClass;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPriority() {
        return priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @XmlTransient
    public Collection<Sppermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Sppermission> permissions) {
        this.permissions = permissions;
    }

    @XmlTransient
    public Collection<Recordset> getRecordsets() {
        return recordsets;
    }

    public void setRecordsets(Collection<Recordset> recordsets) {
        this.recordsets = recordsets;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    @XmlTransient
    public Collection<Spappresource> getSpappresources() {
        return spappresources;
    }

    public void setSpappresources(Collection<Spappresource> spappresources) {
        this.spappresources = spappresources;
    }

    @XmlTransient
    public Collection<Specifyuser> getSpecifyUsers() {
        return specifyUsers;
    }

    public void setSpecifyUsers(Collection<Specifyuser> specifyUsers) {
        this.specifyUsers = specifyUsers;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchs() {
        return workbenchs;
    }

    public void setWorkbenchs(Collection<Workbench> workbenchs) {
        this.workbenchs = workbenchs;
    }

     
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userGroupId != null ? userGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spprincipal)) {
            return false;
        }
        Spprincipal other = (Spprincipal) object;
        if ((this.userGroupId == null && other.userGroupId != null) || (this.userGroupId != null && !this.userGroupId.equals(other.userGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spprincipal[ spPrincipalID=" + userGroupId + " ]";
    }
 
}

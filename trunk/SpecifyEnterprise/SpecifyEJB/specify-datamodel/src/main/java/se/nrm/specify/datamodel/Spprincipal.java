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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Spprincipal.findBySpPrincipalID", query = "SELECT s FROM Spprincipal s WHERE s.spPrincipalID = :spPrincipalID"),
    @NamedQuery(name = "Spprincipal.findByTimestampCreated", query = "SELECT s FROM Spprincipal s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spprincipal.findByTimestampModified", query = "SELECT s FROM Spprincipal s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spprincipal.findByVersion", query = "SELECT s FROM Spprincipal s WHERE s.version = :version"),
    @NamedQuery(name = "Spprincipal.findByGroupSubClass", query = "SELECT s FROM Spprincipal s WHERE s.groupSubClass = :groupSubClass"),
    @NamedQuery(name = "Spprincipal.findByGroupType", query = "SELECT s FROM Spprincipal s WHERE s.groupType = :groupType"),
    @NamedQuery(name = "Spprincipal.findByName", query = "SELECT s FROM Spprincipal s WHERE s.name = :name"),
    @NamedQuery(name = "Spprincipal.findByPriority", query = "SELECT s FROM Spprincipal s WHERE s.priority = :priority"),
    @NamedQuery(name = "Spprincipal.findByUserGroupScopeID", query = "SELECT s FROM Spprincipal s WHERE s.userGroupScopeID = :userGroupScopeID")})
public class Spprincipal extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpPrincipalID")
    private Integer spPrincipalID;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "GroupSubClass")
    private String groupSubClass;
    
    @Size(max = 32)
    @Column(name = "groupType")
    private String groupType;
    
    @Basic(optional = false)
    @NotNull
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
    private Integer userGroupScopeID;
    
    @ManyToMany(mappedBy = "spprincipalCollection")
    private Collection<Sppermission> sppermissionCollection;
    
    @ManyToMany(mappedBy = "spprincipalCollection")
    private Collection<Specifyuser> specifyuserCollection;
    
    @OneToMany(mappedBy = "spPrincipalID")
    private Collection<Workbench> workbenchCollection;
    
    @OneToMany(mappedBy = "spPrincipalID")
    private Collection<Recordset> recordsetCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "spPrincipalID")
    private Collection<Spappresource> spappresourceCollection;

    public Spprincipal() {
    }

    public Spprincipal(Integer spPrincipalID) {
        this.spPrincipalID = spPrincipalID;
    }

    public Spprincipal(Integer spPrincipalID, Date timestampCreated, String groupSubClass, String name, short priority) {
        super(timestampCreated);
        this.spPrincipalID = spPrincipalID; 
        this.groupSubClass = groupSubClass;
        this.name = name;
        this.priority = priority;
    }

    public Integer getSpPrincipalID() {
        return spPrincipalID;
    }

    public void setSpPrincipalID(Integer spPrincipalID) {
        this.spPrincipalID = spPrincipalID;
    }
 
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

    public Integer getUserGroupScopeID() {
        return userGroupScopeID;
    }

    public void setUserGroupScopeID(Integer userGroupScopeID) {
        this.userGroupScopeID = userGroupScopeID;
    }

    @XmlTransient
    public Collection<Sppermission> getSppermissionCollection() {
        return sppermissionCollection;
    }

    public void setSppermissionCollection(Collection<Sppermission> sppermissionCollection) {
        this.sppermissionCollection = sppermissionCollection;
    }

    @XmlTransient
    public Collection<Specifyuser> getSpecifyuserCollection() {
        return specifyuserCollection;
    }

    public void setSpecifyuserCollection(Collection<Specifyuser> specifyuserCollection) {
        this.specifyuserCollection = specifyuserCollection;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchCollection() {
        return workbenchCollection;
    }

    public void setWorkbenchCollection(Collection<Workbench> workbenchCollection) {
        this.workbenchCollection = workbenchCollection;
    }

    @XmlTransient
    public Collection<Recordset> getRecordsetCollection() {
        return recordsetCollection;
    }

    public void setRecordsetCollection(Collection<Recordset> recordsetCollection) {
        this.recordsetCollection = recordsetCollection;
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
    public Collection<Spappresource> getSpappresourceCollection() {
        return spappresourceCollection;
    }

    public void setSpappresourceCollection(Collection<Spappresource> spappresourceCollection) {
        this.spappresourceCollection = spappresourceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spPrincipalID != null ? spPrincipalID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spprincipal)) {
            return false;
        }
        Spprincipal other = (Spprincipal) object;
        if ((this.spPrincipalID == null && other.spPrincipalID != null) || (this.spPrincipalID != null && !this.spPrincipalID.equals(other.spPrincipalID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spprincipal[ spPrincipalID=" + spPrincipalID + " ]";
    }
    
}

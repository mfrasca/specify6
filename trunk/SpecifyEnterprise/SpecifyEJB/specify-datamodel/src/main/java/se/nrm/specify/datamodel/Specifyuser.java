package se.nrm.specify.datamodel;
 
import java.math.BigInteger;
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
import javax.persistence.JoinTable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "specifyuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specifyuser.findAll", query = "SELECT s FROM Specifyuser s"),
    @NamedQuery(name = "Specifyuser.findBySpecifyUserID", query = "SELECT s FROM Specifyuser s WHERE s.specifyUserID = :specifyUserID"),
    @NamedQuery(name = "Specifyuser.findByTimestampCreated", query = "SELECT s FROM Specifyuser s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Specifyuser.findByTimestampModified", query = "SELECT s FROM Specifyuser s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Specifyuser.findByVersion", query = "SELECT s FROM Specifyuser s WHERE s.version = :version"),
    @NamedQuery(name = "Specifyuser.findByAccumMinLoggedIn", query = "SELECT s FROM Specifyuser s WHERE s.accumMinLoggedIn = :accumMinLoggedIn"),
    @NamedQuery(name = "Specifyuser.findByEMail", query = "SELECT s FROM Specifyuser s WHERE s.eMail = :eMail"),
    @NamedQuery(name = "Specifyuser.findByIsLoggedIn", query = "SELECT s FROM Specifyuser s WHERE s.isLoggedIn = :isLoggedIn"),
    @NamedQuery(name = "Specifyuser.findByIsLoggedInReport", query = "SELECT s FROM Specifyuser s WHERE s.isLoggedInReport = :isLoggedInReport"),
    @NamedQuery(name = "Specifyuser.findByLoginCollectionName", query = "SELECT s FROM Specifyuser s WHERE s.loginCollectionName = :loginCollectionName"),
    @NamedQuery(name = "Specifyuser.findByLoginDisciplineName", query = "SELECT s FROM Specifyuser s WHERE s.loginDisciplineName = :loginDisciplineName"),
    @NamedQuery(name = "Specifyuser.findByLoginOutTime", query = "SELECT s FROM Specifyuser s WHERE s.loginOutTime = :loginOutTime"),
    @NamedQuery(name = "Specifyuser.findByName", query = "SELECT s FROM Specifyuser s WHERE s.name = :name"),
    @NamedQuery(name = "Specifyuser.findByPassword", query = "SELECT s FROM Specifyuser s WHERE s.password = :password"),
    @NamedQuery(name = "Specifyuser.findByUserType", query = "SELECT s FROM Specifyuser s WHERE s.userType = :userType")})
public class Specifyuser extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpecifyUserID")
    private Integer specifyUserID;
     
    @Column(name = "AccumMinLoggedIn")
    private BigInteger accumMinLoggedIn;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 64)
    @Column(name = "EMail")
    private String eMail;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsLoggedIn")
    private boolean isLoggedIn;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsLoggedInReport")
    private boolean isLoggedInReport;
    
    @Size(max = 64)
    @Column(name = "LoginCollectionName")
    private String loginCollectionName;
    
    @Size(max = 64)
    @Column(name = "LoginDisciplineName")
    private String loginDisciplineName;
    
    @Column(name = "LoginOutTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginOutTime;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 255)
    @Column(name = "Password")
    private String password;
    
    @Size(max = 32)
    @Column(name = "UserType")
    private String userType;
    
    @JoinTable(name = "specifyuser_spprincipal", joinColumns = {
        @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")}, inverseJoinColumns = {
        @JoinColumn(name = "SpPrincipalID", referencedColumnName = "SpPrincipalID")})
    @ManyToMany
    private Collection<Spprincipal> spprincipalCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUserID")
    private Collection<Workbench> workbenchCollection;
    
    @OneToMany(mappedBy = "visibilitySetByID")
    private Collection<Taxon> taxonCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUserID")
    private Collection<Workbenchtemplate> workbenchtemplateCollection;
    
    @OneToMany(mappedBy = "ownerID")
    private Collection<Sptasksemaphore> sptasksemaphoreCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUserID")
    private Collection<Recordset> recordsetCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUserID")
    private Collection<Spappresource> spappresourceCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUserID")
    private Collection<Spreport> spreportCollection;
    
    @OneToMany(mappedBy = "visibilitySetByID")
    private Collection<Collectingevent> collectingeventCollection;
    
    @OneToMany(mappedBy = "specifyUserID")
    private Collection<Agent> agentCollection;
    
    @OneToMany(mappedBy = "visibilitySetByID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @OneToMany(mappedBy = "visibilitySetByID")
    private Collection<Attachment> attachmentCollection;
    @OneToMany(mappedBy = "specifyUserID")
    private Collection<Spappresourcedir> spappresourcedirCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUserID")
    private Collection<Spquery> spqueryCollection;
    
    @OneToMany(mappedBy = "visibilitySetByID")
    private Collection<Locality> localityCollection;

    public Specifyuser() {
    }

    public Specifyuser(Integer specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    public Specifyuser(Integer specifyUserID, Date timestampCreated, boolean isLoggedIn, boolean isLoggedInReport, String name) {
        super(timestampCreated);
        this.specifyUserID = specifyUserID; 
        this.isLoggedIn = isLoggedIn;
        this.isLoggedInReport = isLoggedInReport;
        this.name = name;
    }

    public Integer getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(Integer specifyUserID) {
        this.specifyUserID = specifyUserID;
    }
 
    public BigInteger getAccumMinLoggedIn() {
        return accumMinLoggedIn;
    }

    public void setAccumMinLoggedIn(BigInteger accumMinLoggedIn) {
        this.accumMinLoggedIn = accumMinLoggedIn;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean getIsLoggedInReport() {
        return isLoggedInReport;
    }

    public void setIsLoggedInReport(boolean isLoggedInReport) {
        this.isLoggedInReport = isLoggedInReport;
    }

    public String getLoginCollectionName() {
        return loginCollectionName;
    }

    public void setLoginCollectionName(String loginCollectionName) {
        this.loginCollectionName = loginCollectionName;
    }

    public String getLoginDisciplineName() {
        return loginDisciplineName;
    }

    public void setLoginDisciplineName(String loginDisciplineName) {
        this.loginDisciplineName = loginDisciplineName;
    }

    public Date getLoginOutTime() {
        return loginOutTime;
    }

    public void setLoginOutTime(Date loginOutTime) {
        this.loginOutTime = loginOutTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpprincipalCollection() {
        return spprincipalCollection;
    }

    public void setSpprincipalCollection(Collection<Spprincipal> spprincipalCollection) {
        this.spprincipalCollection = spprincipalCollection;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchCollection() {
        return workbenchCollection;
    }

    public void setWorkbenchCollection(Collection<Workbench> workbenchCollection) {
        this.workbenchCollection = workbenchCollection;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection() {
        return taxonCollection;
    }

    public void setTaxonCollection(Collection<Taxon> taxonCollection) {
        this.taxonCollection = taxonCollection;
    }

    @XmlTransient
    public Collection<Workbenchtemplate> getWorkbenchtemplateCollection() {
        return workbenchtemplateCollection;
    }

    public void setWorkbenchtemplateCollection(Collection<Workbenchtemplate> workbenchtemplateCollection) {
        this.workbenchtemplateCollection = workbenchtemplateCollection;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphoreCollection() {
        return sptasksemaphoreCollection;
    }

    public void setSptasksemaphoreCollection(Collection<Sptasksemaphore> sptasksemaphoreCollection) {
        this.sptasksemaphoreCollection = sptasksemaphoreCollection;
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

    @XmlTransient
    public Collection<Spreport> getSpreportCollection() {
        return spreportCollection;
    }

    public void setSpreportCollection(Collection<Spreport> spreportCollection) {
        this.spreportCollection = spreportCollection;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingeventCollection() {
        return collectingeventCollection;
    }

    public void setCollectingeventCollection(Collection<Collectingevent> collectingeventCollection) {
        this.collectingeventCollection = collectingeventCollection;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection() {
        return agentCollection;
    }

    public void setAgentCollection(Collection<Agent> agentCollection) {
        this.agentCollection = agentCollection;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    @XmlTransient
    public Collection<Attachment> getAttachmentCollection() {
        return attachmentCollection;
    }

    public void setAttachmentCollection(Collection<Attachment> attachmentCollection) {
        this.attachmentCollection = attachmentCollection;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirCollection() {
        return spappresourcedirCollection;
    }

    public void setSpappresourcedirCollection(Collection<Spappresourcedir> spappresourcedirCollection) {
        this.spappresourcedirCollection = spappresourcedirCollection;
    }

    @XmlTransient
    public Collection<Spquery> getSpqueryCollection() {
        return spqueryCollection;
    }

    public void setSpqueryCollection(Collection<Spquery> spqueryCollection) {
        this.spqueryCollection = spqueryCollection;
    }

    @XmlTransient
    public Collection<Locality> getLocalityCollection() {
        return localityCollection;
    }

    public void setLocalityCollection(Collection<Locality> localityCollection) {
        this.localityCollection = localityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specifyUserID != null ? specifyUserID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specifyuser)) {
            return false;
        }
        Specifyuser other = (Specifyuser) object;
        if ((this.specifyUserID == null && other.specifyUserID != null) || (this.specifyUserID != null && !this.specifyUserID.equals(other.specifyUserID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Specifyuser[ specifyUserID=" + specifyUserID + " ]";
    }
    
}

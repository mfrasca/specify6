package se.nrm.specify.datamodel;
 
import com.sun.xml.bind.CycleRecoverable;
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
@Table(name = "specifyuser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specifyuser.findAll", query = "SELECT s FROM Specifyuser s"),
    @NamedQuery(name = "Specifyuser.findBySpecifyUserID", query = "SELECT s FROM Specifyuser s WHERE s.specifyUserId = :specifyUserID"),
    @NamedQuery(name = "Specifyuser.findByTimestampCreated", query = "SELECT s FROM Specifyuser s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Specifyuser.findByTimestampModified", query = "SELECT s FROM Specifyuser s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Specifyuser.findByVersion", query = "SELECT s FROM Specifyuser s WHERE s.version = :version"),
    @NamedQuery(name = "Specifyuser.findByAccumMinLoggedIn", query = "SELECT s FROM Specifyuser s WHERE s.accumMinLoggedIn = :accumMinLoggedIn"),
    @NamedQuery(name = "Specifyuser.findByEMail", query = "SELECT s FROM Specifyuser s WHERE s.email = :eMail"),
    @NamedQuery(name = "Specifyuser.findByIsLoggedIn", query = "SELECT s FROM Specifyuser s WHERE s.isLoggedIn = :isLoggedIn"),
    @NamedQuery(name = "Specifyuser.findByIsLoggedInReport", query = "SELECT s FROM Specifyuser s WHERE s.isLoggedInReport = :isLoggedInReport"),
    @NamedQuery(name = "Specifyuser.findByLoginCollectionName", query = "SELECT s FROM Specifyuser s WHERE s.loginCollectionName = :loginCollectionName"),
    @NamedQuery(name = "Specifyuser.findByLoginDisciplineName", query = "SELECT s FROM Specifyuser s WHERE s.loginDisciplineName = :loginDisciplineName"),
    @NamedQuery(name = "Specifyuser.findByLoginOutTime", query = "SELECT s FROM Specifyuser s WHERE s.loginOutTime = :loginOutTime"),
    @NamedQuery(name = "Specifyuser.findByName", query = "SELECT s FROM Specifyuser s WHERE s.name = :name"),
    @NamedQuery(name = "Specifyuser.findByPassword", query = "SELECT s FROM Specifyuser s WHERE s.password = :password"),
    @NamedQuery(name = "Specifyuser.findByUserType", query = "SELECT s FROM Specifyuser s WHERE s.userType = :userType")})
public class Specifyuser extends BaseEntity implements CycleRecoverable {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpecifyUserID")
    private Integer specifyUserId;
     
    @Column(name = "AccumMinLoggedIn")
    private BigInteger accumMinLoggedIn;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 64)
    @Column(name = "EMail")
    private String email;
    
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
    
    @Basic(optional = false) 
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 255)
    @Column(name = "Password")
    private String password;
    
    @Size(max = 32)
    @Column(name = "UserType")
    private String userType;
    
    @Column(name = "LoginOutTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginOutTime;
    
    @JoinTable(name = "specifyuser_spprincipal", joinColumns = {
        @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")}, inverseJoinColumns = {
        @JoinColumn(name = "SpPrincipalID", referencedColumnName = "SpPrincipalID")})
    @ManyToMany
    private Collection<Spprincipal> spPrincipals;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUser")
    private Collection<Workbench> workbenches;
    
    @OneToMany(mappedBy = "visibilitySetBy")
    private Collection<Taxon> taxons;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUser")
    private Collection<Workbenchtemplate> workbenchTemplates;
    
    @OneToMany(mappedBy = "owner")
    private Collection<Sptasksemaphore> taskSemaphores;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUser")
    private Collection<Recordset> recordsets;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUser")
    private Collection<Spappresource> spAppResources;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUser")
    private Collection<Spreport> spreports;
    
    @OneToMany(mappedBy = "visibilitySetBy")
    private Collection<Collectingevent> collectingevents;
    
    @OneToMany(mappedBy = "specifyUser")
    private Collection<Agent> agents;
    
    @OneToMany(mappedBy = "visibilitySetBy")
    private Collection<Collectionobject> collectionobjects;
    
    @OneToMany(mappedBy = "visibilitySetBy")
    private Collection<Attachment> attachments;
    
    @OneToMany(mappedBy = "specifyUser")
    private Collection<Spappresourcedir> spAppResourceDirs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specifyUser")
    private Collection<Spquery> spQuerys;
    
    @OneToMany(mappedBy = "visibilitySetBy")
    private Collection<Locality> localities;

    public Specifyuser() {
    }

    public Specifyuser(Integer specifyUserId) {
        this.specifyUserId = specifyUserId;
    }

    public Specifyuser(Integer specifyUserId, Date timestampCreated, boolean isLoggedIn, boolean isLoggedInReport, String name) {
        super(timestampCreated);
        this.specifyUserId = specifyUserId; 
        this.isLoggedIn = isLoggedIn;
        this.isLoggedInReport = isLoggedInReport;
        this.name = name;
    }
 
    
    @Override
    public Specifyuser onCycleDetected(Context context) {
       // Context provides access to the Marshaller being used:
       System.out.println("JAXB Marshaller is: " + context.getMarshaller() + " -- " + this.getClass().getSimpleName());
        
       Specifyuser su = new Specifyuser(specifyUserId);  
       su.setName(name);
       
       return su;
   }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (specifyUserId != null) ? specifyUserId.toString() : "0";
    }
    
    public Integer getSpecifyUserId() {
        return specifyUserId;
    }

    public void setSpecifyUserId(Integer specifyUserId) {
        this.specifyUserId = specifyUserId;
    }
 
    public BigInteger getAccumMinLoggedIn() {
        return accumMinLoggedIn;
    }

    public void setAccumMinLoggedIn(BigInteger accumMinLoggedIn) {
        this.accumMinLoggedIn = accumMinLoggedIn;
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
  
    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    @NotNull(message="Password must be specified.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Agent> getAgents() {
        return agents;
    }

    public void setAgents(Collection<Agent> agents) {
        this.agents = agents;
    }

    @XmlTransient
    public Collection<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Attachment> attachments) {
        this.attachments = attachments;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingevents() {
        return collectingevents;
    }

    public void setCollectingevents(Collection<Collectingevent> collectingevents) {
        this.collectingevents = collectingevents;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjects() {
        return collectionobjects;
    }

    public void setCollectionobjects(Collection<Collectionobject> collectionobjects) {
        this.collectionobjects = collectionobjects;
    }
  

    @XmlTransient
    public Collection<Locality> getLocalities() {
        return localities;
    }

    public void setLocalities(Collection<Locality> localities) {
        this.localities = localities;
    }
     
    @XmlTransient
    public Collection<Recordset> getRecordsets() {
        return recordsets;
    }

    public void setRecordsets(Collection<Recordset> recordsets) {
        this.recordsets = recordsets;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpAppResourceDirs() {
        return spAppResourceDirs;
    }

    public void setSpAppResourceDirs(Collection<Spappresourcedir> spAppResourceDirs) {
        this.spAppResourceDirs = spAppResourceDirs;
    }

    @XmlTransient
    public Collection<Spappresource> getSpAppResources() {
        return spAppResources;
    }

    public void setSpAppResources(Collection<Spappresource> spAppResources) {
        this.spAppResources = spAppResources;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpPrincipals() {
        return spPrincipals;
    }

    public void setSpPrincipals(Collection<Spprincipal> spPrincipals) {
        this.spPrincipals = spPrincipals;
    }

    @XmlTransient
    public Collection<Spquery> getSpQuerys() {
        return spQuerys;
    }

    public void setSpQuerys(Collection<Spquery> spQuerys) {
        this.spQuerys = spQuerys;
    }

    

    @XmlTransient
    public Collection<Spreport> getSpreports() {
        return spreports;
    }

    public void setSpreports(Collection<Spreport> spreports) {
        this.spreports = spreports;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getTaskSemaphores() {
        return taskSemaphores;
    }

    public void setTaskSemaphores(Collection<Sptasksemaphore> taskSemaphores) {
        this.taskSemaphores = taskSemaphores;
    }

    @XmlTransient
    public Collection<Taxon> getTaxons() {
        return taxons;
    }

    public void setTaxons(Collection<Taxon> taxons) {
        this.taxons = taxons;
    }

    @XmlTransient
    public Collection<Workbenchtemplate> getWorkbenchTemplates() {
        return workbenchTemplates;
    }

    public void setWorkbenchTemplates(Collection<Workbenchtemplate> workbenchTemplates) {
        this.workbenchTemplates = workbenchTemplates;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenches() {
        return workbenches;
    }

    public void setWorkbenches(Collection<Workbench> workbenches) {
        this.workbenches = workbenches;
    }

    public Date getLoginOutTime() {
        return loginOutTime;
    }

    public void setLoginOutTime(Date loginOutTime) {
        this.loginOutTime = loginOutTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specifyUserId != null ? specifyUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specifyuser)) {
            return false;
        }
        Specifyuser other = (Specifyuser) object;
        if ((this.specifyUserId == null && other.specifyUserId != null) || (this.specifyUserId != null && !this.specifyUserId.equals(other.specifyUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Specifyuser[ specifyUserID=" + specifyUserId + " ]";
    }
 
}

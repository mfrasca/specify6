package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "institution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institution.findAll", query = "SELECT i FROM Institution i"),
    @NamedQuery(name = "Institution.findByUserGroupScopeId", query = "SELECT i FROM Institution i WHERE i.userGroupScopeId = :userGroupScopeId"),
    @NamedQuery(name = "Institution.findByTimestampCreated", query = "SELECT i FROM Institution i WHERE i.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Institution.findByTimestampModified", query = "SELECT i FROM Institution i WHERE i.timestampModified = :timestampModified"),
    @NamedQuery(name = "Institution.findByVersion", query = "SELECT i FROM Institution i WHERE i.version = :version"),
    @NamedQuery(name = "Institution.findByAltName", query = "SELECT i FROM Institution i WHERE i.altName = :altName"),
    @NamedQuery(name = "Institution.findByCode", query = "SELECT i FROM Institution i WHERE i.code = :code"),
    @NamedQuery(name = "Institution.findByHasBeenAsked", query = "SELECT i FROM Institution i WHERE i.hasBeenAsked = :hasBeenAsked"),
    @NamedQuery(name = "Institution.findByIconURI", query = "SELECT i FROM Institution i WHERE i.iconURI = :iconURI"),
    @NamedQuery(name = "Institution.findByInstitutionId", query = "SELECT i FROM Institution i WHERE i.institutionId = :institutionId"),
    @NamedQuery(name = "Institution.findByIsAccessionsGlobal", query = "SELECT i FROM Institution i WHERE i.isAccessionsGlobal = :isAccessionsGlobal"),
    @NamedQuery(name = "Institution.findByIsAnonymous", query = "SELECT i FROM Institution i WHERE i.isAnonymous = :isAnonymous"),
    @NamedQuery(name = "Institution.findByIsSecurityOn", query = "SELECT i FROM Institution i WHERE i.isSecurityOn = :isSecurityOn"),
    @NamedQuery(name = "Institution.findByIsServerBased", query = "SELECT i FROM Institution i WHERE i.isServerBased = :isServerBased"),
    @NamedQuery(name = "Institution.findByIsSingleGeographyTree", query = "SELECT i FROM Institution i WHERE i.isSingleGeographyTree = :isSingleGeographyTree"),
    @NamedQuery(name = "Institution.findByIsSharingLocalities", query = "SELECT i FROM Institution i WHERE i.isSharingLocalities = :isSharingLocalities"),
    @NamedQuery(name = "Institution.findByLsidAuthority", query = "SELECT i FROM Institution i WHERE i.lsidAuthority = :lsidAuthority"),
    @NamedQuery(name = "Institution.findByName", query = "SELECT i FROM Institution i WHERE i.name = :name"),
    @NamedQuery(name = "Institution.findByRegNumber", query = "SELECT i FROM Institution i WHERE i.regNumber = :regNumber"),
    @NamedQuery(name = "Institution.findByUri", query = "SELECT i FROM Institution i WHERE i.uri = :uri"),
    @NamedQuery(name = "Institution.findByCurrentManagedRelVersion", query = "SELECT i FROM Institution i WHERE i.currentManagedRelVersion = :currentManagedRelVersion"),
    @NamedQuery(name = "Institution.findByCurrentManagedSchemaVersion", query = "SELECT i FROM Institution i WHERE i.currentManagedSchemaVersion = :currentManagedSchemaVersion"),
    @NamedQuery(name = "Institution.findByIsReleaseManagedGlobally", query = "SELECT i FROM Institution i WHERE i.isReleaseManagedGlobally = :isReleaseManagedGlobally"),
    @NamedQuery(name = "Institution.findByMinimumPwdLength", query = "SELECT i FROM Institution i WHERE i.minimumPwdLength = :minimumPwdLength")})
public class Institution extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
//    @NotNull
    @Column(name = "UserGroupScopeId")
    private Integer userGroupScopeId;
     
    @Size(max = 128)
    @Column(name = "AltName")
    private String altName;
    
    @Size(max = 64)
    @Column(name = "Code")
    private String code;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Copyright")
    private String copyright;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Disclaimer")
    private String disclaimer;
    
    @Column(name = "HasBeenAsked")
    private Boolean hasBeenAsked;
    
    @Size(max = 255)
    @Column(name = "IconURI")
    private String iconURI;
    
    @Column(name = "institutionId")
    private Integer institutionId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Ipr")
    private String ipr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsAccessionsGlobal")
    private boolean isAccessionsGlobal;
    
    @Column(name = "IsAnonymous")
    private Boolean isAnonymous;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsSecurityOn")
    private boolean isSecurityOn;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsServerBased")
    private boolean isServerBased;
    
    @Column(name = "IsSingleGeographyTree")
    private Boolean isSingleGeographyTree;
    
    @Column(name = "IsSharingLocalities")
    private Boolean isSharingLocalities;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "License")
    private String license;
    
    @Size(max = 64)
    @Column(name = "LsidAuthority")
    private String lsidAuthority;
    
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 24)
    @Column(name = "RegNumber")
    private String regNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "TermsOfUse")
    private String termsOfUse;
    
    @Size(max = 255)
    @Column(name = "Uri")
    private String uri;
    
    @Size(max = 8)
    @Column(name = "CurrentManagedRelVersion")
    private String currentManagedRelVersion;
    
    @Size(max = 8)
    @Column(name = "CurrentManagedSchemaVersion") 
    private String currentManagedSchemaVersion;
    
    @Column(name = "IsReleaseManagedGlobally")
    private Boolean isReleaseManagedGlobally;
    
    @Column(name = "MinimumPwdLength")
    private Short minimumPwdLength;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institutionID")
    private Collection<Division> divisionCollection;
    
    @OneToMany(mappedBy = "institutionTCID")
    private Collection<Agent> agentCollection;
    
    @OneToMany(mappedBy = "institutionCCID")
    private Collection<Agent> agentCollection1;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AddressID", referencedColumnName = "AddressID")
    @ManyToOne
    private Address addressID;
    
    @JoinColumn(name = "StorageTreeDefID", referencedColumnName = "StorageTreeDefID")
    @ManyToOne
    private Storagetreedef storageTreeDefID;

    public Institution() {
    }

    public Institution(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Institution(Integer userGroupScopeId, Date timestampCreated, boolean isAccessionsGlobal, boolean isSecurityOn, boolean isServerBased) {
        super(timestampCreated);
        this.userGroupScopeId = userGroupScopeId; 
        this.isAccessionsGlobal = isAccessionsGlobal;
        this.isSecurityOn = isSecurityOn;
        this.isServerBased = isServerBased;
    }

    public Integer getUserGroupScopeId() {
        return userGroupScopeId;
    }

    public void setUserGroupScopeId(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }   

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Boolean getHasBeenAsked() {
        return hasBeenAsked;
    }

    public void setHasBeenAsked(Boolean hasBeenAsked) {
        this.hasBeenAsked = hasBeenAsked;
    }

    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getIpr() {
        return ipr;
    }

    public void setIpr(String ipr) {
        this.ipr = ipr;
    }

    public boolean getIsAccessionsGlobal() {
        return isAccessionsGlobal;
    }

    public void setIsAccessionsGlobal(boolean isAccessionsGlobal) {
        this.isAccessionsGlobal = isAccessionsGlobal;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public boolean getIsSecurityOn() {
        return isSecurityOn;
    }

    public void setIsSecurityOn(boolean isSecurityOn) {
        this.isSecurityOn = isSecurityOn;
    }

    public boolean getIsServerBased() {
        return isServerBased;
    }

    public void setIsServerBased(boolean isServerBased) {
        this.isServerBased = isServerBased;
    }

    public Boolean getIsSingleGeographyTree() {
        return isSingleGeographyTree;
    }

    public void setIsSingleGeographyTree(Boolean isSingleGeographyTree) {
        this.isSingleGeographyTree = isSingleGeographyTree;
    }

    public Boolean getIsSharingLocalities() {
        return isSharingLocalities;
    }

    public void setIsSharingLocalities(Boolean isSharingLocalities) {
        this.isSharingLocalities = isSharingLocalities;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLsidAuthority() {
        return lsidAuthority;
    }

    public void setLsidAuthority(String lsidAuthority) {
        this.lsidAuthority = lsidAuthority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCurrentManagedRelVersion() {
        return currentManagedRelVersion;
    }

    public void setCurrentManagedRelVersion(String currentManagedRelVersion) {
        this.currentManagedRelVersion = currentManagedRelVersion;
    }

    public String getCurrentManagedSchemaVersion() {
        return currentManagedSchemaVersion;
    }

    public void setCurrentManagedSchemaVersion(String currentManagedSchemaVersion) {
        this.currentManagedSchemaVersion = currentManagedSchemaVersion;
    }

    public Boolean getIsReleaseManagedGlobally() {
        return isReleaseManagedGlobally;
    }

    public void setIsReleaseManagedGlobally(Boolean isReleaseManagedGlobally) {
        this.isReleaseManagedGlobally = isReleaseManagedGlobally;
    }

    public Short getMinimumPwdLength() {
        return minimumPwdLength;
    }

    public void setMinimumPwdLength(Short minimumPwdLength) {
        this.minimumPwdLength = minimumPwdLength;
    }

    @XmlTransient
    public Collection<Division> getDivisionCollection() {
        return divisionCollection;
    }

    public void setDivisionCollection(Collection<Division> divisionCollection) {
        this.divisionCollection = divisionCollection;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection() {
        return agentCollection;
    }

    public void setAgentCollection(Collection<Agent> agentCollection) {
        this.agentCollection = agentCollection;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection1() {
        return agentCollection1;
    }

    public void setAgentCollection1(Collection<Agent> agentCollection1) {
        this.agentCollection1 = agentCollection1;
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

    public Address getAddressID() {
        return addressID;
    }

    public void setAddressID(Address addressID) {
        this.addressID = addressID;
    }

    public Storagetreedef getStorageTreeDefID() {
        return storageTreeDefID;
    }

    public void setStorageTreeDefID(Storagetreedef storageTreeDefID) {
        this.storageTreeDefID = storageTreeDefID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userGroupScopeId != null ? userGroupScopeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institution)) {
            return false;
        }
        Institution other = (Institution) object;
        if ((this.userGroupScopeId == null && other.userGroupScopeId != null) || (this.userGroupScopeId != null && !this.userGroupScopeId.equals(other.userGroupScopeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Institution[ userGroupScopeId=" + userGroupScopeId + " ]";
    }
    
}

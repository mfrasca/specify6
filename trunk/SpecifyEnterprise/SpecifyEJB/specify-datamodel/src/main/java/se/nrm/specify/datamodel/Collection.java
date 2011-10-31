package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
 * 
 * Entity bean mapping to collection table
 */
@Entity
@Table(name = "collection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collection.findAll", query = "SELECT c FROM Collection c"),
    @NamedQuery(name = "Collection.findByUserGroupScopeId", query = "SELECT c FROM Collection c WHERE c.userGroupScopeId = :userGroupScopeId"),
    @NamedQuery(name = "Collection.findByTimestampCreated", query = "SELECT c FROM Collection c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collection.findByTimestampModified", query = "SELECT c FROM Collection c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collection.findByVersion", query = "SELECT c FROM Collection c WHERE c.version = :version"),
    @NamedQuery(name = "Collection.findByCatalogFormatNumName", query = "SELECT c FROM Collection c WHERE c.catalogFormatNumName = :catalogFormatNumName"),
    @NamedQuery(name = "Collection.findByCode", query = "SELECT c FROM Collection c WHERE c.code = :code"),
    @NamedQuery(name = "Collection.findByCollectionId", query = "SELECT c FROM Collection c WHERE c.collectionId = :collectionId"),
    @NamedQuery(name = "Collection.findByCollectionName", query = "SELECT c FROM Collection c WHERE c.collectionName = :collectionName"),
    @NamedQuery(name = "Collection.findByCollectionType", query = "SELECT c FROM Collection c WHERE c.collectionType = :collectionType"),
    @NamedQuery(name = "Collection.findByDbContentVersion", query = "SELECT c FROM Collection c WHERE c.dbContentVersion = :dbContentVersion"),
    @NamedQuery(name = "Collection.findByDevelopmentStatus", query = "SELECT c FROM Collection c WHERE c.developmentStatus = :developmentStatus"),
    @NamedQuery(name = "Collection.findByEstimatedSize", query = "SELECT c FROM Collection c WHERE c.estimatedSize = :estimatedSize"),
    @NamedQuery(name = "Collection.findByInstitutionType", query = "SELECT c FROM Collection c WHERE c.institutionType = :institutionType"),
    @NamedQuery(name = "Collection.findByIsEmbeddedCollectingEvent", query = "SELECT c FROM Collection c WHERE c.isEmbeddedCollectingEvent = :isEmbeddedCollectingEvent"),
    @NamedQuery(name = "Collection.findByIsaNumber", query = "SELECT c FROM Collection c WHERE c.isaNumber = :isaNumber"),
    @NamedQuery(name = "Collection.findByKingdomCoverage", query = "SELECT c FROM Collection c WHERE c.kingdomCoverage = :kingdomCoverage"),
    @NamedQuery(name = "Collection.findByPreservationMethodType", query = "SELECT c FROM Collection c WHERE c.preservationMethodType = :preservationMethodType"),
    @NamedQuery(name = "Collection.findByPrimaryFocus", query = "SELECT c FROM Collection c WHERE c.primaryFocus = :primaryFocus"),
    @NamedQuery(name = "Collection.findByPrimaryPurpose", query = "SELECT c FROM Collection c WHERE c.primaryPurpose = :primaryPurpose"),
    @NamedQuery(name = "Collection.findByRegNumber", query = "SELECT c FROM Collection c WHERE c.regNumber = :regNumber"),
    @NamedQuery(name = "Collection.findByWebPortalURI", query = "SELECT c FROM Collection c WHERE c.webPortalURI = :webPortalURI"),
    @NamedQuery(name = "Collection.findByWebSiteURI", query = "SELECT c FROM Collection c WHERE c.webSiteURI = :webSiteURI")})
public class Collection extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserGroupScopeId")
    private Integer userGroupScopeId;
      
    @Basic(optional = false)
//    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "CatalogFormatNumName")
    private String catalogFormatNumName;
    
    @Size(max = 50)
    @Column(name = "Code")
    private String code;
    
    @Column(name = "collectionId")
    private Integer collectionId;
    
    @Size(max = 50)
    @Column(name = "CollectionName")
    private String collectionName;
    
    @Size(max = 32)
    @Column(name = "CollectionType")
    private String collectionType;
    
    @Size(max = 32)
    @Column(name = "DbContentVersion")
    private String dbContentVersion;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 32)
    @Column(name = "DevelopmentStatus")
    private String developmentStatus;
    
    @Column(name = "EstimatedSize")
    private Integer estimatedSize;
    
    @Size(max = 32)
    @Column(name = "InstitutionType")
    private String institutionType;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsEmbeddedCollectingEvent")
    private boolean isEmbeddedCollectingEvent;
    
    @Size(max = 24)
    @Column(name = "IsaNumber")
    private String isaNumber;
    
    @Size(max = 32)
    @Column(name = "KingdomCoverage")
    private String kingdomCoverage;
    
    @Size(max = 32)
    @Column(name = "PreservationMethodType")
    private String preservationMethodType;
    
    @Size(max = 32)
    @Column(name = "PrimaryFocus")
    private String primaryFocus;
    
    @Size(max = 32)
    @Column(name = "PrimaryPurpose")
    private String primaryPurpose;
    
    @Size(max = 24)
    @Column(name = "RegNumber")
    private String regNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Scope")
    private String scope;
    
    @Size(max = 255)
    @Column(name = "WebPortalURI")
    private String webPortalURI;
    
    @Size(max = 255)
    @Column(name = "WebSiteURI")
    private String webSiteURI;
    
    @JoinTable(name = "autonumsch_coll", joinColumns = {
        @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")}, inverseJoinColumns = {
        @JoinColumn(name = "AutoNumberingSchemeID", referencedColumnName = "AutoNumberingSchemeID")})
    @ManyToMany
    private java.util.Collection<Autonumberingscheme> autonumberingschemeCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID")
    private java.util.Collection<Preptype> preptypeCollection;
    
    @OneToMany(mappedBy = "collectionID")
    private java.util.Collection<Sptasksemaphore> sptasksemaphoreCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID")
    private java.util.Collection<Picklist> picklistCollection;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "rightSideCollectionID")
    private java.util.Collection<Collectionreltype> collectionreltypeCollection;
    
    @OneToMany(mappedBy = "leftSideCollectionID")
    private java.util.Collection<Collectionreltype> collectionreltypeCollection1;
    
    @OneToMany(mappedBy = "collectionTCID")
    private java.util.Collection<Agent> agentCollection;
    
    @OneToMany(mappedBy = "collectionCCID")
    private java.util.Collection<Agent> agentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID")
    private java.util.Collection<Collectionobject> collectionobjectCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionID")
    private java.util.Collection<Fieldnotebook> fieldnotebookCollection;
    
    @OneToMany(mappedBy = "collectionID")
    private java.util.Collection<Spappresourcedir> spappresourcedirCollection;

    public Collection() {
    }

    public Collection(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Collection(Integer userGroupScopeId, Date timestampCreated, String catalogFormatNumName, boolean isEmbeddedCollectingEvent) {
        super(timestampCreated);
        this.userGroupScopeId = userGroupScopeId; 
        this.catalogFormatNumName = catalogFormatNumName;
        this.isEmbeddedCollectingEvent = isEmbeddedCollectingEvent;
    }

    public Integer getUserGroupScopeId() {
        return userGroupScopeId;
    }

    public void setUserGroupScopeId(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }
 
    public String getCatalogFormatNumName() {
        return catalogFormatNumName;
    }

    public void setCatalogFormatNumName(String catalogFormatNumName) {
        this.catalogFormatNumName = catalogFormatNumName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getDbContentVersion() {
        return dbContentVersion;
    }

    public void setDbContentVersion(String dbContentVersion) {
        this.dbContentVersion = dbContentVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDevelopmentStatus() {
        return developmentStatus;
    }

    public void setDevelopmentStatus(String developmentStatus) {
        this.developmentStatus = developmentStatus;
    }

    public Integer getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Integer estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public boolean getIsEmbeddedCollectingEvent() {
        return isEmbeddedCollectingEvent;
    }

    public void setIsEmbeddedCollectingEvent(boolean isEmbeddedCollectingEvent) {
        this.isEmbeddedCollectingEvent = isEmbeddedCollectingEvent;
    }

    public String getIsaNumber() {
        return isaNumber;
    }

    public void setIsaNumber(String isaNumber) {
        this.isaNumber = isaNumber;
    }

    public String getKingdomCoverage() {
        return kingdomCoverage;
    }

    public void setKingdomCoverage(String kingdomCoverage) {
        this.kingdomCoverage = kingdomCoverage;
    }

    public String getPreservationMethodType() {
        return preservationMethodType;
    }

    public void setPreservationMethodType(String preservationMethodType) {
        this.preservationMethodType = preservationMethodType;
    }

    public String getPrimaryFocus() {
        return primaryFocus;
    }

    public void setPrimaryFocus(String primaryFocus) {
        this.primaryFocus = primaryFocus;
    }

    public String getPrimaryPurpose() {
        return primaryPurpose;
    }

    public void setPrimaryPurpose(String primaryPurpose) {
        this.primaryPurpose = primaryPurpose;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getWebPortalURI() {
        return webPortalURI;
    }

    public void setWebPortalURI(String webPortalURI) {
        this.webPortalURI = webPortalURI;
    }

    public String getWebSiteURI() {
        return webSiteURI;
    }

    public void setWebSiteURI(String webSiteURI) {
        this.webSiteURI = webSiteURI;
    }

    @XmlTransient
    public java.util.Collection<Autonumberingscheme> getAutonumberingschemeCollection() {
        return autonumberingschemeCollection;
    }

    public void setAutonumberingschemeCollection(java.util.Collection<Autonumberingscheme> autonumberingschemeCollection) {
        this.autonumberingschemeCollection = autonumberingschemeCollection;
    }

    @XmlTransient
    public java.util.Collection<Preptype> getPreptypeCollection() {
        return preptypeCollection;
    }

    public void setPreptypeCollection(java.util.Collection<Preptype> preptypeCollection) {
        this.preptypeCollection = preptypeCollection;
    }

    @XmlTransient
    public java.util.Collection<Sptasksemaphore> getSptasksemaphoreCollection() {
        return sptasksemaphoreCollection;
    }

    public void setSptasksemaphoreCollection(java.util.Collection<Sptasksemaphore> sptasksemaphoreCollection) {
        this.sptasksemaphoreCollection = sptasksemaphoreCollection;
    }

    @XmlTransient
    public java.util.Collection<Picklist> getPicklistCollection() {
        return picklistCollection;
    }

    public void setPicklistCollection(java.util.Collection<Picklist> picklistCollection) {
        this.picklistCollection = picklistCollection;
    }

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
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
    public java.util.Collection<Collectionreltype> getCollectionreltypeCollection() {
        return collectionreltypeCollection;
    }

    public void setCollectionreltypeCollection(java.util.Collection<Collectionreltype> collectionreltypeCollection) {
        this.collectionreltypeCollection = collectionreltypeCollection;
    }

    @XmlTransient
    public java.util.Collection<Collectionreltype> getCollectionreltypeCollection1() {
        return collectionreltypeCollection1;
    }

    public void setCollectionreltypeCollection1(java.util.Collection<Collectionreltype> collectionreltypeCollection1) {
        this.collectionreltypeCollection1 = collectionreltypeCollection1;
    }

    @XmlTransient
    public java.util.Collection<Agent> getAgentCollection() {
        return agentCollection;
    }

    public void setAgentCollection(java.util.Collection<Agent> agentCollection) {
        this.agentCollection = agentCollection;
    }

    @XmlTransient
    public java.util.Collection<Agent> getAgentCollection1() {
        return agentCollection1;
    }

    public void setAgentCollection1(java.util.Collection<Agent> agentCollection1) {
        this.agentCollection1 = agentCollection1;
    }

    @XmlTransient
    public java.util.Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(java.util.Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    @XmlTransient
    public java.util.Collection<Fieldnotebook> getFieldnotebookCollection() {
        return fieldnotebookCollection;
    }

    public void setFieldnotebookCollection(java.util.Collection<Fieldnotebook> fieldnotebookCollection) {
        this.fieldnotebookCollection = fieldnotebookCollection;
    }

    @XmlTransient
    public java.util.Collection<Spappresourcedir> getSpappresourcedirCollection() {
        return spappresourcedirCollection;
    }

    public void setSpappresourcedirCollection(java.util.Collection<Spappresourcedir> spappresourcedirCollection) {
        this.spappresourcedirCollection = spappresourcedirCollection;
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
        if (!(object instanceof Collection)) {
            return false;
        }
        Collection other = (Collection) object;
        if ((this.userGroupScopeId == null && other.userGroupScopeId != null) || (this.userGroupScopeId != null && !this.userGroupScopeId.equals(other.userGroupScopeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collection[ userGroupScopeId=" + userGroupScopeId + " ]";
    }
    
}

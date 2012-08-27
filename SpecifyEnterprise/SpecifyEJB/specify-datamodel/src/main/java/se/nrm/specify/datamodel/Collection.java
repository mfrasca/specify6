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
import javax.xml.bind.annotation.XmlAttribute; 
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
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
    @NamedQuery(name = "Collection.findByCatalogFormatNumName", query = "SELECT c FROM Collection c WHERE c.catalogNumFormatName = :catalogFormatNumName"),
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
    @NotNull(message="CatalogFormatNumName must be specified.")
    @Size(min = 1, max = 64)
    @Column(name = "CatalogFormatNumName")
    private String catalogNumFormatName;
    
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
    @NotNull(message="IsEmbeddedCollectingEvent must be specified.")
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
    private java.util.Collection<Autonumberingscheme> numberingSchemes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collection")
    private java.util.Collection<Preptype> prepTypes;
    
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private java.util.Collection<Sptasksemaphore> sptasksemaphores;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collection")
    private java.util.Collection<Picklist> pickLists;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId") 
    @ManyToOne(optional = false)
    private Discipline discipline;
    
    @JoinColumn(name = "InstitutionNetworkID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Institution institution;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "rightSideCollection")
    private java.util.Collection<Collectionreltype> rightSideRelTypes;
    
    @OneToMany(mappedBy = "leftSideCollection")
    private java.util.Collection<Collectionreltype> leftSideRelTypes;
    
    @OneToMany(mappedBy = "collTechContact")
    private java.util.Collection<Agent> technicalContacts;
    
    @OneToMany(mappedBy = "collContentContact")
    private java.util.Collection<Agent> contentContacts;
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "collection")
    private java.util.Collection<Collectionobject> collectionobjects;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collection")
    private java.util.Collection<Fieldnotebook> fieldnotebooks;
    
    @OneToMany(mappedBy = "collection")
    private java.util.Collection<Spappresourcedir> spappresourcedirs;

    public Collection() {
    }

    public Collection(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Collection(Integer userGroupScopeId, Date timestampCreated, String catalogNumFormatName, boolean isEmbeddedCollectingEvent) {
        super(timestampCreated);
        this.userGroupScopeId = userGroupScopeId;
        this.catalogNumFormatName = catalogNumFormatName;
        this.isEmbeddedCollectingEvent = isEmbeddedCollectingEvent;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (userGroupScopeId != null) ? userGroupScopeId.toString() : "0";
    }
  
    public Integer getUserGroupScopeId() {
        return userGroupScopeId;
    }

    public void setUserGroupScopeId(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public String getCatalogNumFormatName() {
        return catalogNumFormatName;
    }

    public void setCatalogNumFormatName(String catalogNumFormatName) {
        this.catalogNumFormatName = catalogNumFormatName;
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
    public java.util.Collection<Agent> getContentContacts() {
        return contentContacts;
    }

    public void setContentContacts(java.util.Collection<Agent> contentContacts) {
        this.contentContacts = contentContacts;
    }

    @XmlTransient
    public java.util.Collection<Collectionreltype> getLeftSideRelTypes() {
        return leftSideRelTypes;
    }

    public void setLeftSideRelTypes(java.util.Collection<Collectionreltype> leftSideRelTypes) {
        this.leftSideRelTypes = leftSideRelTypes;
    }

    @XmlTransient
    public java.util.Collection<Autonumberingscheme> getNumberingSchemes() {
        return numberingSchemes;
    }

    public void setNumberingSchemes(java.util.Collection<Autonumberingscheme> numberingSchemes) {
        this.numberingSchemes = numberingSchemes;
    }

    @XmlTransient
    public java.util.Collection<Picklist> getPickLists() {
        return pickLists;
    }

    public void setPickLists(java.util.Collection<Picklist> pickLists) {
        this.pickLists = pickLists;
    }

    @XmlTransient
    public java.util.Collection<Preptype> getPrepTypes() {
        return prepTypes;
    }

    public void setPrepTypes(java.util.Collection<Preptype> prepTypes) {
        this.prepTypes = prepTypes;
    }

    @XmlTransient
    public java.util.Collection<Collectionreltype> getRightSideRelTypes() {
        return rightSideRelTypes;
    }

    public void setRightSideRelTypes(java.util.Collection<Collectionreltype> rightSideRelTypes) {
        this.rightSideRelTypes = rightSideRelTypes;
    }

    @XmlTransient
    public java.util.Collection<Agent> getTechnicalContacts() {
        return technicalContacts;
    }

    public void setTechnicalContacts(java.util.Collection<Agent> technicalContacts) {
        this.technicalContacts = technicalContacts;
    }
 
    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

     
    @XmlTransient 
    public java.util.Collection<Collectionobject> getCollectionobjects() {
        return collectionobjects;
    }

    public void setCollectionobjects(java.util.Collection<Collectionobject> collectionobjects) {
        this.collectionobjects = collectionobjects;
    }

    @XmlTransient
    public java.util.Collection<Fieldnotebook> getFieldnotebooks() {
        return fieldnotebooks;
    }

    public void setFieldnotebooks(java.util.Collection<Fieldnotebook> fieldnotebooks) {
        this.fieldnotebooks = fieldnotebooks;
    }

    @XmlTransient
    public java.util.Collection<Spappresourcedir> getSpappresourcedirs() {
        return spappresourcedirs;
    }

    public void setSpappresourcedirs(java.util.Collection<Spappresourcedir> spappresourcedirs) {
        this.spappresourcedirs = spappresourcedirs;
    }

    @XmlTransient
    public java.util.Collection<Sptasksemaphore> getSptasksemaphores() {
        return sptasksemaphores;
    }

    public void setSptasksemaphores(java.util.Collection<Sptasksemaphore> sptasksemaphores) {
        this.sptasksemaphores = sptasksemaphores;
    }

    @XmlIDREF
    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
 

    @Override
    public String getEntityName() {
        return "collection";
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

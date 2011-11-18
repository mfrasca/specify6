package se.nrm.specify.datamodel;
 
import java.util.Collection;
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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient; 

/**
 *
 * @author idali
 */
@Entity
@Table(name = "division")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Division.findAll", query = "SELECT d FROM Division d"),
    @NamedQuery(name = "Division.findByUserGroupScopeId", query = "SELECT d FROM Division d WHERE d.userGroupScopeId = :userGroupScopeId"),
    @NamedQuery(name = "Division.findByTimestampCreated", query = "SELECT d FROM Division d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Division.findByTimestampModified", query = "SELECT d FROM Division d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Division.findByVersion", query = "SELECT d FROM Division d WHERE d.version = :version"),
    @NamedQuery(name = "Division.findByAbbrev", query = "SELECT d FROM Division d WHERE d.abbrev = :abbrev"),
    @NamedQuery(name = "Division.findByAltName", query = "SELECT d FROM Division d WHERE d.altName = :altName"),
    @NamedQuery(name = "Division.findByDisciplineType", query = "SELECT d FROM Division d WHERE d.disciplineType = :disciplineType"),
    @NamedQuery(name = "Division.findByDivisionId", query = "SELECT d FROM Division d WHERE d.divisionId = :divisionId"),
    @NamedQuery(name = "Division.findByIconURI", query = "SELECT d FROM Division d WHERE d.iconURI = :iconURI"),
    @NamedQuery(name = "Division.findByName", query = "SELECT d FROM Division d WHERE d.name = :name"),
    @NamedQuery(name = "Division.findByRegNumber", query = "SELECT d FROM Division d WHERE d.regNumber = :regNumber"),
    @NamedQuery(name = "Division.findByUri", query = "SELECT d FROM Division d WHERE d.uri = :uri")})
public class Division extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
//    @NotNull
    @Column(name = "UserGroupScopeId")
    private Integer userGroupScopeId;
      
    @Size(max = 64)
    @Column(name = "Abbrev")
    private String abbrev;
    
    @Size(max = 128)
    @Column(name = "AltName")
    private String altName;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 64)
    @Column(name = "DisciplineType")
    private String disciplineType;
    
    @Column(name = "divisionId")
    private Integer divisionId;
    
    @Size(max = 255)
    @Column(name = "IconURI")
    private String iconURI;
    
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
    
    @Size(max = 255)
    @Column(name = "Uri")
    private String uri;
    
    @JoinTable(name = "autonumsch_div", joinColumns = {
        @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")}, inverseJoinColumns = {
        @JoinColumn(name = "AutoNumberingSchemeID", referencedColumnName = "AutoNumberingSchemeID")})
    @ManyToMany
    private Collection<Autonumberingscheme> autonumberingschemeCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Treatmentevent> treatmenteventCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Loan> loanCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionID")
    private Collection<Exchangein> exchangeinCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Gift> giftCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Conservdescription> conservdescriptionCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
//    @XmlInverseReference(mappedBy = "divisionCollection")  
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
//    @XmlInverseReference(mappedBy = "divisionCollection1")  
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AddressID", referencedColumnName = "AddressID")
    @ManyToOne
//    @XmlInverseReference(mappedBy = "divisionCollection") 
    @XmlTransient 
    private Address addressID;
    
    @JoinColumn(name = "InstitutionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
//    @XmlInverseReference(mappedBy = "divisionCollection") 
    @XmlTransient 
    private Institution institutionID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionID")
    private Collection<Exchangeout> exchangeoutCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionID")
    private Collection<Repositoryagreement> repositoryagreementCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionID")
    private Collection<Accession> accessionCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Groupperson> grouppersonCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Agent> agentCollection;
    
    @OneToMany(mappedBy = "divisionID")
    private Collection<Collector> collectorCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisionID")
    private Collection<Discipline> disciplineCollection;

    public Division() {
    }

    public Division(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Division(Integer userGroupScopeId, Date timestampCreated) {
        super(timestampCreated);
        this.userGroupScopeId = userGroupScopeId; 
    }

    public Integer getUserGroupScopeId() {
        return userGroupScopeId;
    }

    public void setUserGroupScopeId(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    } 
    
    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisciplineType() {
        return disciplineType;
    }

    public void setDisciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @XmlTransient
    public Collection<Autonumberingscheme> getAutonumberingschemeCollection() {
        return autonumberingschemeCollection;
    }

    public void setAutonumberingschemeCollection(Collection<Autonumberingscheme> autonumberingschemeCollection) {
        this.autonumberingschemeCollection = autonumberingschemeCollection;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmenteventCollection() {
        return treatmenteventCollection;
    }

    public void setTreatmenteventCollection(Collection<Treatmentevent> treatmenteventCollection) {
        this.treatmenteventCollection = treatmenteventCollection;
    }

    @XmlTransient
    public Collection<Loan> getLoanCollection() {
        return loanCollection;
    }

    public void setLoanCollection(Collection<Loan> loanCollection) {
        this.loanCollection = loanCollection;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeinCollection() {
        return exchangeinCollection;
    }

    public void setExchangeinCollection(Collection<Exchangein> exchangeinCollection) {
        this.exchangeinCollection = exchangeinCollection;
    }

    @XmlTransient
    public Collection<Gift> getGiftCollection() {
        return giftCollection;
    }

    public void setGiftCollection(Collection<Gift> giftCollection) {
        this.giftCollection = giftCollection;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptionCollection() {
        return conservdescriptionCollection;
    }

    public void setConservdescriptionCollection(Collection<Conservdescription> conservdescriptionCollection) {
        this.conservdescriptionCollection = conservdescriptionCollection;
    }

    @XmlTransient 
    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlTransient 
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

    public Institution getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(Institution institutionID) {
        this.institutionID = institutionID;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeoutCollection() {
        return exchangeoutCollection;
    }

    public void setExchangeoutCollection(Collection<Exchangeout> exchangeoutCollection) {
        this.exchangeoutCollection = exchangeoutCollection;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreementCollection() {
        return repositoryagreementCollection;
    }

    public void setRepositoryagreementCollection(Collection<Repositoryagreement> repositoryagreementCollection) {
        this.repositoryagreementCollection = repositoryagreementCollection;
    }

    @XmlTransient
    public Collection<Accession> getAccessionCollection() {
        return accessionCollection;
    }

    public void setAccessionCollection(Collection<Accession> accessionCollection) {
        this.accessionCollection = accessionCollection;
    }

    @XmlTransient
    public Collection<Groupperson> getGrouppersonCollection() {
        return grouppersonCollection;
    }

    public void setGrouppersonCollection(Collection<Groupperson> grouppersonCollection) {
        this.grouppersonCollection = grouppersonCollection;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection() {
        return agentCollection;
    }

    public void setAgentCollection(Collection<Agent> agentCollection) {
        this.agentCollection = agentCollection;
    }

    @XmlTransient
    public Collection<Collector> getCollectorCollection() {
        return collectorCollection;
    }

    public void setCollectorCollection(Collection<Collector> collectorCollection) {
        this.collectorCollection = collectorCollection;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplineCollection() {
        return disciplineCollection;
    }

    public void setDisciplineCollection(Collection<Discipline> disciplineCollection) {
        this.disciplineCollection = disciplineCollection;
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
        if (!(object instanceof Division)) {
            return false;
        }
        Division other = (Division) object;
        if ((this.userGroupScopeId == null && other.userGroupScopeId != null) || (this.userGroupScopeId != null && !this.userGroupScopeId.equals(other.userGroupScopeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Division[ userGroupScopeId=" + userGroupScopeId + " ]";
    }
    
}

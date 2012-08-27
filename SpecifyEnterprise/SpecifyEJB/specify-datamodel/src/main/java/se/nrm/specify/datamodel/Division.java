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
    @NamedQuery(name = "Division.findByDisciplineType", query = "SELECT d FROM Division d WHERE d.discipline = :disciplineType"),
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
    private String discipline;
    
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
    private Collection<Autonumberingscheme> numberingSchemes;
    
    @OneToMany(mappedBy = "division")
    private Collection<Treatmentevent> treatmentevents;
    
    @OneToMany(mappedBy = "division")
    private Collection<Loan> loans;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    private Collection<Exchangein> exchangeins;
    
    @OneToMany(mappedBy = "division")
    private Collection<Gift> gifts;
    
    @OneToMany(mappedBy = "division")
    private Collection<Conservdescription> conservdescriptions;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE })  
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AddressID", referencedColumnName = "AddressID")
    @ManyToOne  
    private Address address;
    
    @JoinColumn(name = "InstitutionID", referencedColumnName = "UserGroupScopeId") 
    @ManyToOne(optional = false)  
    private Institution institution;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    private Collection<Exchangeout> exchangeouts;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    private Collection<Repositoryagreement> repositoryagreements;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    private Collection<Accession> accessions;
    
    @OneToMany(mappedBy = "division")
    private Collection<Groupperson> grouppersons;
    
    @OneToMany(mappedBy = "division")
    private Collection<Agent> members;
    
    @OneToMany(mappedBy = "division")
    private Collection<Collector> collectors;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    private Collection<Discipline> disciplines;

    public Division() {
    }

    public Division(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Division(Integer userGroupScopeId, Date timestampCreated) {
        super(timestampCreated);
        this.userGroupScopeId = userGroupScopeId; 
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
    public Collection<Accession> getAccessions() {
        return accessions;
    }
 
    public void setAccessions(Collection<Accession> accessions) {
        this.accessions = accessions;
    }

    @XmlTransient
    public Collection<Collector> getCollectors() {
        return collectors;
    }

    public void setCollectors(Collection<Collector> collectors) {
        this.collectors = collectors;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptions() {
        return conservdescriptions;
    }

    public void setConservdescriptions(Collection<Conservdescription> conservdescriptions) {
        this.conservdescriptions = conservdescriptions;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeins() {
        return exchangeins;
    }

    public void setExchangeins(Collection<Exchangein> exchangeins) {
        this.exchangeins = exchangeins;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeouts() {
        return exchangeouts;
    }

    public void setExchangeouts(Collection<Exchangeout> exchangeouts) {
        this.exchangeouts = exchangeouts;
    }

    @XmlTransient
    public Collection<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Collection<Gift> gifts) {
        this.gifts = gifts;
    }

    @XmlTransient
    public Collection<Groupperson> getGrouppersons() {
        return grouppersons;
    }

    public void setGrouppersons(Collection<Groupperson> grouppersons) {
        this.grouppersons = grouppersons;
    }

    @XmlTransient
    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreements() {
        return repositoryagreements;
    }

    public void setRepositoryagreements(Collection<Repositoryagreement> repositoryagreements) {
        this.repositoryagreements = repositoryagreements;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmentevents() {
        return treatmentevents;
    }

    public void setTreatmentevents(Collection<Treatmentevent> treatmentevents) {
        this.treatmentevents = treatmentevents;
    }
 
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
 
    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
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
    public Collection<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Collection<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
  
    @NotNull(message="Institution must be specified.")
    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }
 
    @XmlTransient
    public Collection<Agent> getMembers() {
        return members;
    }

    public void setMembers(Collection<Agent> members) {
        this.members = members;
    }

    @XmlTransient
    public Collection<Autonumberingscheme> getNumberingSchemes() {
        return numberingSchemes;
    }

    public void setNumberingSchemes(Collection<Autonumberingscheme> numberingSchemes) {
        this.numberingSchemes = numberingSchemes;
    }

    @Override
    public String getEntityName() {
        return "division";
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

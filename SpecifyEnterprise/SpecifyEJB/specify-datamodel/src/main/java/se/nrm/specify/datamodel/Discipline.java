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
@Table(name = "discipline")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discipline.findAll", query = "SELECT d FROM Discipline d"),
    @NamedQuery(name = "Discipline.findByUserGroupScopeId", query = "SELECT d FROM Discipline d WHERE d.userGroupScopeId = :userGroupScopeId"),
    @NamedQuery(name = "Discipline.findByTimestampCreated", query = "SELECT d FROM Discipline d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Discipline.findByTimestampModified", query = "SELECT d FROM Discipline d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Discipline.findByVersion", query = "SELECT d FROM Discipline d WHERE d.version = :version"),
    @NamedQuery(name = "Discipline.findByDisciplineId", query = "SELECT d FROM Discipline d WHERE d.disciplineId = :disciplineId"),
    @NamedQuery(name = "Discipline.findByName", query = "SELECT d FROM Discipline d WHERE d.name = :name"),
    @NamedQuery(name = "Discipline.findByRegNumber", query = "SELECT d FROM Discipline d WHERE d.regNumber = :regNumber"),
    @NamedQuery(name = "Discipline.findByType", query = "SELECT d FROM Discipline d WHERE d.type = :type")})
public class Discipline extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
//    @NotNull
    @Column(name = "UserGroupScopeId") 
    private Integer userGroupScopeId;
      
    @Column(name = "disciplineId")
    private Integer disciplineId;
    
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 24)
    @Column(name = "RegNumber")
    private String regNumber;
    
    @Size(max = 64)
    @Column(name = "Type")
    private String type;
    
    @JoinTable(name = "autonumsch_dsp", joinColumns = {
        @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")}, inverseJoinColumns = {
        @JoinColumn(name = "AutoNumberingSchemeID", referencedColumnName = "AutoNumberingSchemeID")})
    @ManyToMany
    private Collection<Autonumberingscheme> numberingSchemes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Localitynamealias> localitynamealiases;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Shipment> shipments;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Loanagent> loanagents;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Sptasksemaphore> sptasksemaphores;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Loan> loans;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline") 
    private Collection<Collectingtrip> collectingtrips;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Gift> gifts;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesets;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Giftagent> giftagents;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<se.nrm.specify.datamodel.Collection> collections;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Collectingeventattribute> collectingeventattributes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Spexportschema> spExportSchemas;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Collectingevent> collectingevents;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Attributedef> attributeDefs;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Loanreturnpreparation> loanreturnpreparations;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Giftpreparation> giftpreparations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Splocalecontainer> spLocaleContainers;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Fieldnotebookpage> fieldnotebookpages;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Fieldnotebook> fieldnotebooks;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID")
    @ManyToOne
    private Taxontreedef taxonTreeDef;
    
    @JoinColumn(name = "DataTypeID", referencedColumnName = "DataTypeID")
    @NotNull
    @ManyToOne(optional = false)
    private Datatype dataType;
    
    @JoinColumn(name = "GeographyTreeDefID", referencedColumnName = "GeographyTreeDefID")
    @NotNull
    @ManyToOne(optional = false)
    private Geographytreedef geographyTreeDef;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @NotNull
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedef geologicTimePeriodTreeDef;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Division division;
    
    @JoinColumn(name = "LithoStratTreeDefID", referencedColumnName = "LithoStratTreeDefID")
    @ManyToOne
    private Lithostrattreedef lithoStratTreeDef;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Spappresourcedir> spappresourcedirs;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Loanpreparation> loanpreparations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discipline")
    private Collection<Locality> localitys;
    
    @OneToMany(mappedBy = "discipline")
    private Collection<Localitycitation> localitycitations;

    public Discipline() {
    }

    public Discipline(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Discipline(Integer userGroupScopeId, Date timestampCreated) {
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
    
    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

  

    @XmlTransient
    public Collection<Attributedef> getAttributeDefs() {
        return attributeDefs;
    }

    public void setAttributeDefs(Collection<Attributedef> attributeDefs) {
        this.attributeDefs = attributeDefs;
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
    public Collection<Autonumberingscheme> getNumberingSchemes() {
        return numberingSchemes;
    }

    public void setNumberingSchemes(Collection<Autonumberingscheme> numberingSchemes) {
        this.numberingSchemes = numberingSchemes;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpExportSchemas() {
        return spExportSchemas;
    }

    public void setSpExportSchemas(Collection<Spexportschema> spExportSchemas) {
        this.spExportSchemas = spExportSchemas;
    }

    @XmlTransient
    public Collection<Splocalecontainer> getSpLocaleContainers() {
        return spLocaleContainers;
    }

    public void setSpLocaleContainers(Collection<Splocalecontainer> spLocaleContainers) {
        this.spLocaleContainers = spLocaleContainers;
    }

    public Taxontreedef getTaxonTreeDef() {
        return taxonTreeDef;
    }

    public void setTaxonTreeDef(Taxontreedef taxonTreeDef) {
        this.taxonTreeDef = taxonTreeDef;
    }

    @NotNull(message="Datatype must be specified.")
    public Datatype getDataType() {
        return dataType;
    }

    public void setDataType(Datatype dataType) {
        this.dataType = dataType;
    }

    @NotNull(message="Division must be specified.")
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @NotNull(message="Geographytreedef must be specified.")
    public Geographytreedef getGeographyTreeDef() {
        return geographyTreeDef;
    }

    public void setGeographyTreeDef(Geographytreedef geographyTreeDef) {
        this.geographyTreeDef = geographyTreeDef;
    }

    @NotNull(message="Geologictimeperiodtreedef must be specified.")
    public Geologictimeperiodtreedef getGeologicTimePeriodTreeDef() {
        return geologicTimePeriodTreeDef;
    }

    public void setGeologicTimePeriodTreeDef(Geologictimeperiodtreedef geologicTimePeriodTreeDef) {
        this.geologicTimePeriodTreeDef = geologicTimePeriodTreeDef;
    }

    public Lithostrattreedef getLithoStratTreeDef() {
        return lithoStratTreeDef;
    }

    public void setLithoStratTreeDef(Lithostrattreedef lithoStratTreeDef) {
        this.lithoStratTreeDef = lithoStratTreeDef;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributes() {
        return collectingeventattributes;
    }

    public void setCollectingeventattributes(Collection<Collectingeventattribute> collectingeventattributes) {
        this.collectingeventattributes = collectingeventattributes;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingevents() {
        return collectingevents;
    }

    public void setCollectingevents(Collection<Collectingevent> collectingevents) {
        this.collectingevents = collectingevents;
    }

    @XmlTransient
    public Collection<Collectingtrip> getCollectingtrips() {
        return collectingtrips;
    }

    public void setCollectingtrips(Collection<Collectingtrip> collectingtrips) {
        this.collectingtrips = collectingtrips;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollections() {
        return collections;
    }

    public void setCollections(Collection<se.nrm.specify.datamodel.Collection> collections) {
        this.collections = collections;
    }

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpages() {
        return fieldnotebookpages;
    }

    public void setFieldnotebookpages(Collection<Fieldnotebookpage> fieldnotebookpages) {
        this.fieldnotebookpages = fieldnotebookpages;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesets() {
        return fieldnotebookpagesets;
    }

    public void setFieldnotebookpagesets(Collection<Fieldnotebookpageset> fieldnotebookpagesets) {
        this.fieldnotebookpagesets = fieldnotebookpagesets;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebooks() {
        return fieldnotebooks;
    }

    public void setFieldnotebooks(Collection<Fieldnotebook> fieldnotebooks) {
        this.fieldnotebooks = fieldnotebooks;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagents() {
        return giftagents;
    }

    public void setGiftagents(Collection<Giftagent> giftagents) {
        this.giftagents = giftagents;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftpreparations() {
        return giftpreparations;
    }

    public void setGiftpreparations(Collection<Giftpreparation> giftpreparations) {
        this.giftpreparations = giftpreparations;
    }

    @XmlTransient
    public Collection<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Collection<Gift> gifts) {
        this.gifts = gifts;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagents() {
        return loanagents;
    }

    public void setLoanagents(Collection<Loanagent> loanagents) {
        this.loanagents = loanagents;
    }

    @XmlTransient
    public Collection<Loanpreparation> getLoanpreparations() {
        return loanpreparations;
    }

    public void setLoanpreparations(Collection<Loanpreparation> loanpreparations) {
        this.loanpreparations = loanpreparations;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparations() {
        return loanreturnpreparations;
    }

    public void setLoanreturnpreparations(Collection<Loanreturnpreparation> loanreturnpreparations) {
        this.loanreturnpreparations = loanreturnpreparations;
    }

    @XmlTransient
    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitations() {
        return localitycitations;
    }

    public void setLocalitycitations(Collection<Localitycitation> localitycitations) {
        this.localitycitations = localitycitations;
    }

    @XmlTransient
    public Collection<Localitynamealias> getLocalitynamealiases() {
        return localitynamealiases;
    }

    public void setLocalitynamealiases(Collection<Localitynamealias> localitynamealiases) {
        this.localitynamealiases = localitynamealiases;
    }

    @XmlTransient
    public Collection<Locality> getLocalitys() {
        return localitys;
    }

    public void setLocalitys(Collection<Locality> localitys) {
        this.localitys = localitys;
    }

    @XmlTransient
    public Collection<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(Collection<Shipment> shipments) {
        this.shipments = shipments;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirs() {
        return spappresourcedirs;
    }

    public void setSpappresourcedirs(Collection<Spappresourcedir> spappresourcedirs) {
        this.spappresourcedirs = spappresourcedirs;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphores() {
        return sptasksemaphores;
    }

    public void setSptasksemaphores(Collection<Sptasksemaphore> sptasksemaphores) {
        this.sptasksemaphores = sptasksemaphores;
    }

     
    @Override
    public String getEntityName() {
        return "discipline";
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
        if (!(object instanceof Discipline)) {
            return false;
        }
        Discipline other = (Discipline) object;
        if ((this.userGroupScopeId == null && other.userGroupScopeId != null) || (this.userGroupScopeId != null && !this.userGroupScopeId.equals(other.userGroupScopeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Discipline[ userGroupScopeId=" + userGroupScopeId + " ]";
    }
 
    
}

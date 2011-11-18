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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Collection<Autonumberingscheme> autonumberingschemeCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Localitynamealias> localitynamealiasCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Shipment> shipmentCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Loanagent> loanagentCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Sptasksemaphore> sptasksemaphoreCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Loan> loanCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID") 
    private Collection<Collectingtrip> collectingtripCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Gift> giftCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection;
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Giftagent> giftagentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<se.nrm.specify.datamodel.Collection> collectionCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Collectingeventattribute> collectingeventattributeCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Spexportschema> spexportschemaCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Collectingevent> collectingeventCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Attributedef> attributedefCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Loanreturnpreparation> loanreturnpreparationCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Giftpreparation> giftpreparationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Splocalecontainer> splocalecontainerCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Fieldnotebookpage> fieldnotebookpageCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Fieldnotebook> fieldnotebookCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID")
    @ManyToOne
    private Taxontreedef taxonTreeDefID;
    
    @JoinColumn(name = "DataTypeID", referencedColumnName = "DataTypeID")
    @ManyToOne(optional = false)
    private Datatype dataTypeID;
    
    @JoinColumn(name = "GeographyTreeDefID", referencedColumnName = "GeographyTreeDefID")
    @ManyToOne(optional = false)
    private Geographytreedef geographyTreeDefID;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedef geologicTimePeriodTreeDefID;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Division divisionID;
    
    @JoinColumn(name = "LithoStratTreeDefID", referencedColumnName = "LithoStratTreeDefID")
    @ManyToOne
    private Lithostrattreedef lithoStratTreeDefID;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Spappresourcedir> spappresourcedirCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Loanpreparation> loanpreparationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplineID")
    private Collection<Locality> localityCollection;
    
    @OneToMany(mappedBy = "disciplineID")
    private Collection<Localitycitation> localitycitationCollection;

    public Discipline() {
    }

    public Discipline(Integer userGroupScopeId) {
        this.userGroupScopeId = userGroupScopeId;
    }

    public Discipline(Integer userGroupScopeId, Date timestampCreated) {
        super(timestampCreated);
        this.userGroupScopeId = userGroupScopeId; 
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
    public Collection<Autonumberingscheme> getAutonumberingschemeCollection() {
        return autonumberingschemeCollection;
    }

    public void setAutonumberingschemeCollection(Collection<Autonumberingscheme> autonumberingschemeCollection) {
        this.autonumberingschemeCollection = autonumberingschemeCollection;
    }

    @XmlTransient
    public Collection<Localitynamealias> getLocalitynamealiasCollection() {
        return localitynamealiasCollection;
    }

    public void setLocalitynamealiasCollection(Collection<Localitynamealias> localitynamealiasCollection) {
        this.localitynamealiasCollection = localitynamealiasCollection;
    }

    @XmlTransient
    public Collection<Shipment> getShipmentCollection() {
        return shipmentCollection;
    }

    public void setShipmentCollection(Collection<Shipment> shipmentCollection) {
        this.shipmentCollection = shipmentCollection;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagentCollection() {
        return loanagentCollection;
    }

    public void setLoanagentCollection(Collection<Loanagent> loanagentCollection) {
        this.loanagentCollection = loanagentCollection;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphoreCollection() {
        return sptasksemaphoreCollection;
    }

    public void setSptasksemaphoreCollection(Collection<Sptasksemaphore> sptasksemaphoreCollection) {
        this.sptasksemaphoreCollection = sptasksemaphoreCollection;
    }

    @XmlTransient
    public Collection<Loan> getLoanCollection() {
        return loanCollection;
    }

    public void setLoanCollection(Collection<Loan> loanCollection) {
        this.loanCollection = loanCollection;
    }

    @XmlTransient
    public Collection<Collectingtrip> getCollectingtripCollection() {
        return collectingtripCollection;
    }

    public void setCollectingtripCollection(Collection<Collectingtrip> collectingtripCollection) {
        this.collectingtripCollection = collectingtripCollection;
    }

    @XmlTransient
    public Collection<Gift> getGiftCollection() {
        return giftCollection;
    }

    public void setGiftCollection(Collection<Gift> giftCollection) {
        this.giftCollection = giftCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesetCollection() {
        return fieldnotebookpagesetCollection;
    }

    public void setFieldnotebookpagesetCollection(Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection) {
        this.fieldnotebookpagesetCollection = fieldnotebookpagesetCollection;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagentCollection() {
        return giftagentCollection;
    }

    public void setGiftagentCollection(Collection<Giftagent> giftagentCollection) {
        this.giftagentCollection = giftagentCollection;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollectionCollection() {
        return collectionCollection;
    }

    public void setCollectionCollection(Collection<se.nrm.specify.datamodel.Collection> collectionCollection) {
        this.collectionCollection = collectionCollection;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributeCollection() {
        return collectingeventattributeCollection;
    }

    public void setCollectingeventattributeCollection(Collection<Collectingeventattribute> collectingeventattributeCollection) {
        this.collectingeventattributeCollection = collectingeventattributeCollection;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpexportschemaCollection() {
        return spexportschemaCollection;
    }

    public void setSpexportschemaCollection(Collection<Spexportschema> spexportschemaCollection) {
        this.spexportschemaCollection = spexportschemaCollection;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingeventCollection() {
        return collectingeventCollection;
    }

    public void setCollectingeventCollection(Collection<Collectingevent> collectingeventCollection) {
        this.collectingeventCollection = collectingeventCollection;
    }

    @XmlTransient
    public Collection<Attributedef> getAttributedefCollection() {
        return attributedefCollection;
    }

    public void setAttributedefCollection(Collection<Attributedef> attributedefCollection) {
        this.attributedefCollection = attributedefCollection;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparationCollection() {
        return loanreturnpreparationCollection;
    }

    public void setLoanreturnpreparationCollection(Collection<Loanreturnpreparation> loanreturnpreparationCollection) {
        this.loanreturnpreparationCollection = loanreturnpreparationCollection;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftpreparationCollection() {
        return giftpreparationCollection;
    }

    public void setGiftpreparationCollection(Collection<Giftpreparation> giftpreparationCollection) {
        this.giftpreparationCollection = giftpreparationCollection;
    }

    @XmlTransient
    public Collection<Splocalecontainer> getSplocalecontainerCollection() {
        return splocalecontainerCollection;
    }

    public void setSplocalecontainerCollection(Collection<Splocalecontainer> splocalecontainerCollection) {
        this.splocalecontainerCollection = splocalecontainerCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpageCollection() {
        return fieldnotebookpageCollection;
    }

    public void setFieldnotebookpageCollection(Collection<Fieldnotebookpage> fieldnotebookpageCollection) {
        this.fieldnotebookpageCollection = fieldnotebookpageCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebookCollection() {
        return fieldnotebookCollection;
    }

    public void setFieldnotebookCollection(Collection<Fieldnotebook> fieldnotebookCollection) {
        this.fieldnotebookCollection = fieldnotebookCollection;
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

    public Taxontreedef getTaxonTreeDefID() {
        return taxonTreeDefID;
    }

    public void setTaxonTreeDefID(Taxontreedef taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
    }

    public Datatype getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID(Datatype dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    public Geographytreedef getGeographyTreeDefID() {
        return geographyTreeDefID;
    }

    public void setGeographyTreeDefID(Geographytreedef geographyTreeDefID) {
        this.geographyTreeDefID = geographyTreeDefID;
    }

    public Geologictimeperiodtreedef getGeologicTimePeriodTreeDefID() {
        return geologicTimePeriodTreeDefID;
    }

    public void setGeologicTimePeriodTreeDefID(Geologictimeperiodtreedef geologicTimePeriodTreeDefID) {
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID;
    }

    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
    }

    public Lithostrattreedef getLithoStratTreeDefID() {
        return lithoStratTreeDefID;
    }

    public void setLithoStratTreeDefID(Lithostrattreedef lithoStratTreeDefID) {
        this.lithoStratTreeDefID = lithoStratTreeDefID;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirCollection() {
        return spappresourcedirCollection;
    }

    public void setSpappresourcedirCollection(Collection<Spappresourcedir> spappresourcedirCollection) {
        this.spappresourcedirCollection = spappresourcedirCollection;
    }

    @XmlTransient
    public Collection<Loanpreparation> getLoanpreparationCollection() {
        return loanpreparationCollection;
    }

    public void setLoanpreparationCollection(Collection<Loanpreparation> loanpreparationCollection) {
        this.loanpreparationCollection = loanpreparationCollection;
    }

    @XmlTransient
    public Collection<Locality> getLocalityCollection() {
        return localityCollection;
    }

    public void setLocalityCollection(Collection<Locality> localityCollection) {
        this.localityCollection = localityCollection;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitationCollection() {
        return localitycitationCollection;
    }

    public void setLocalitycitationCollection(Collection<Localitycitation> localitycitationCollection) {
        this.localitycitationCollection = localitycitationCollection;
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

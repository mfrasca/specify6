package se.nrm.specify.datamodel;
 
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 * 
 * Entity bean mapping to determination table
 */
@Entity
@Table(name = "determination")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Determination.findAll", query = "SELECT d FROM Determination d"),
    @NamedQuery(name = "Determination.findByDeterminationID", query = "SELECT d FROM Determination d WHERE d.determinationID = :determinationID"),
    @NamedQuery(name = "Determination.findByTimestampCreated", query = "SELECT d FROM Determination d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Determination.findByTimestampModified", query = "SELECT d FROM Determination d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Determination.findByVersion", query = "SELECT d FROM Determination d WHERE d.version = :version"),
    @NamedQuery(name = "Determination.findByCollectionMemberID", query = "SELECT d FROM Determination d WHERE d.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Determination.findByAddendum", query = "SELECT d FROM Determination d WHERE d.addendum = :addendum"),
    @NamedQuery(name = "Determination.findByAlternateName", query = "SELECT d FROM Determination d WHERE d.alternateName = :alternateName"),
    @NamedQuery(name = "Determination.findByConfidence", query = "SELECT d FROM Determination d WHERE d.confidence = :confidence"),
    @NamedQuery(name = "Determination.findByDeterminedDate", query = "SELECT d FROM Determination d WHERE d.determinedDate = :determinedDate"),
    @NamedQuery(name = "Determination.findByDeterminedDatePrecision", query = "SELECT d FROM Determination d WHERE d.determinedDatePrecision = :determinedDatePrecision"),
    @NamedQuery(name = "Determination.findByFeatureOrBasis", query = "SELECT d FROM Determination d WHERE d.featureOrBasis = :featureOrBasis"),
    @NamedQuery(name = "Determination.findByIsCurrent", query = "SELECT d FROM Determination d WHERE d.isCurrent = :isCurrent"),
    @NamedQuery(name = "Determination.findByMethod", query = "SELECT d FROM Determination d WHERE d.method = :method"),
    @NamedQuery(name = "Determination.findByNameUsage", query = "SELECT d FROM Determination d WHERE d.nameUsage = :nameUsage"),
    @NamedQuery(name = "Determination.findByNumber1", query = "SELECT d FROM Determination d WHERE d.number1 = :number1"),
    @NamedQuery(name = "Determination.findByNumber2", query = "SELECT d FROM Determination d WHERE d.number2 = :number2"),
    @NamedQuery(name = "Determination.findByQualifier", query = "SELECT d FROM Determination d WHERE d.qualifier = :qualifier"),
    @NamedQuery(name = "Determination.findByVarQualifer", query = "SELECT d FROM Determination d WHERE d.varQualifer = :varQualifer"),
    @NamedQuery(name = "Determination.findBySubSpQualifier", query = "SELECT d FROM Determination d WHERE d.subSpQualifier = :subSpQualifier"),
    @NamedQuery(name = "Determination.findByVarQualifier", query = "SELECT d FROM Determination d WHERE d.varQualifier = :varQualifier"),
    @NamedQuery(name = "Determination.findByTypeStatusName", query = "SELECT d FROM Determination d WHERE d.typeStatusName = :typeStatusName"),
    @NamedQuery(name = "Determination.findByYesNo1", query = "SELECT d FROM Determination d WHERE d.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Determination.findByYesNo2", query = "SELECT d FROM Determination d WHERE d.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Determination.findByPreferredTaxonID", query = "SELECT d FROM Determination d WHERE d.preferredTaxonID = :preferredTaxonID"),
    @NamedQuery(name = "Determination.findCurrentByTaxonNameAndEvent", query = "SELECT d FROM Determination d WHERE d.taxonID.fullName = :fullName and d.collectionObjectID.collectingEventID = :collectingEventID and d.collectionObjectID.collectionID.code = :code and d.isCurrent = :isCurrent"),
    @NamedQuery(name = "Determination.findByTaxonID", query = "SELECT d FROM Determination d WHERE d.taxonID = :taxonId"),
    @NamedQuery(name = "Determination.findCurrentByCollectionobjectID", query = "SELECT d FROM Determination d WHERE d.collectionObjectID = :collectionObjectID and d.isCurrent = :isCurrent")})
public class Determination extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DeterminationID")
    private Integer determinationID;
  
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Size(max = 16)
    @Column(name = "Addendum")
    private String addendum;
    
    @Size(max = 128)
    @Column(name = "AlternateName")
    private String alternateName;
    
    @Size(max = 50)
    @Column(name = "Confidence")
    private String confidence;
    
    @Column(name = "DeterminedDate")
    @Temporal(TemporalType.DATE)
    private Date determinedDate;
    
    @Column(name = "DeterminedDatePrecision")
    private Short determinedDatePrecision;
    
    @Size(max = 50)
    @Column(name = "FeatureOrBasis")
    private String featureOrBasis;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsCurrent")
    private boolean isCurrent;
    
    @Size(max = 50)
    @Column(name = "Method")
    private String method;
    
    @Size(max = 64)
    @Column(name = "NameUsage")
    private String nameUsage;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Size(max = 16)
    @Column(name = "Qualifier")
    private String qualifier;
    
    @Size(max = 16)
    @Column(name = "VarQualifer")
    private String varQualifer;
    
    @Size(max = 16)
    @Column(name = "SubSpQualifier")
    private String subSpQualifier;
    
    @Size(max = 16)
    @Column(name = "VarQualifier")
    private String varQualifier;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 50)
    @Column(name = "TypeStatusName")
    private String typeStatusName;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "determinationID")
    private Collection<Determinationcitation> determinationcitationCollection;
    
    @JoinColumn(name = "DeterminerID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent determinerID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false, cascade= CascadeType.ALL)
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "PreferredTaxonID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon preferredTaxonID;
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon taxonID;
     

    public Determination() {
    }

    public Determination(Integer determinationID) {
        this.determinationID = determinationID;
    }

    public Determination(Integer determinationID, Date timestampCreated, int collectionMemberID, boolean isCurrent) {
        super(timestampCreated);
        this.determinationID = determinationID; 
        this.collectionMemberID = collectionMemberID;
        this.isCurrent = isCurrent;
    }

    public Integer getDeterminationID() {
        return determinationID;
    }

    public void setDeterminationID(Integer determinationID) {
        this.determinationID = determinationID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public String getAddendum() {
        return addendum;
    }

    public void setAddendum(String addendum) {
        this.addendum = addendum;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public Date getDeterminedDate() {
        return determinedDate;
    }

    public void setDeterminedDate(Date determinedDate) {
        this.determinedDate = determinedDate;
    }

    public Short getDeterminedDatePrecision() {
        return determinedDatePrecision;
    }

    public void setDeterminedDatePrecision(Short determinedDatePrecision) {
        this.determinedDatePrecision = determinedDatePrecision;
    }

    public String getFeatureOrBasis() {
        return featureOrBasis;
    }

    public void setFeatureOrBasis(String featureOrBasis) {
        this.featureOrBasis = featureOrBasis;
    }

    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNameUsage() {
        return nameUsage;
    }

    public void setNameUsage(String nameUsage) {
        this.nameUsage = nameUsage;
    }

    public Float getNumber1() {
        return number1;
    }

    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    public Float getNumber2() {
        return number2;
    }

    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getVarQualifer() {
        return varQualifer;
    }

    public void setVarQualifer(String varQualifer) {
        this.varQualifer = varQualifer;
    }

    public String getSubSpQualifier() {
        return subSpQualifier;
    }

    public void setSubSpQualifier(String subSpQualifier) {
        this.subSpQualifier = subSpQualifier;
    }

    public String getVarQualifier() {
        return varQualifier;
    }

    public void setVarQualifier(String varQualifier) {
        this.varQualifier = varQualifier;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getTypeStatusName() {
        return typeStatusName;
    }

    public void setTypeStatusName(String typeStatusName) {
        this.typeStatusName = typeStatusName;
    }

    public Boolean getYesNo1() {
        return yesNo1;
    }

    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    public Boolean getYesNo2() {
        return yesNo2;
    }

    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    @XmlTransient
    public Collection<Determinationcitation> getDeterminationcitationCollection() {
        return determinationcitationCollection;
    }

    public void setDeterminationcitationCollection(Collection<Determinationcitation> determinationcitationCollection) {
        this.determinationcitationCollection = determinationcitationCollection;
    }

    public Agent getDeterminerID() {
        return determinerID;
    }

    public void setDeterminerID(Agent determinerID) {
        this.determinerID = determinerID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlTransient
    public Collectionobject getCollectionObjectID() {
        return collectionObjectID;
    }

    public void setCollectionObjectID(Collectionobject collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }
  
    public Taxon getPreferredTaxonID() {
        return preferredTaxonID;
    }

    public void setPreferredTaxonID(Taxon preferredTaxonID) {
        this.preferredTaxonID = preferredTaxonID;
    }
  
    public Taxon getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Taxon taxonID) {
        this.taxonID = taxonID;
    } 
     
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Collectionobject) {
            this.collectionObjectID = (Collectionobject)parent;   
        }
    }
     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (determinationID != null ? determinationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Determination)) {
            return false;
        }
        Determination other = (Determination) object;
        if ((this.determinationID == null && other.determinationID != null) || (this.determinationID != null && !this.determinationID.equals(other.determinationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Determination[ determinationID=" + determinationID + " ]";
    } 
}

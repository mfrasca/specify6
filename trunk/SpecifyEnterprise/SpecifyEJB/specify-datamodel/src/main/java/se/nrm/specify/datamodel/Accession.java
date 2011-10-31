package se.nrm.specify.datamodel;
 
import java.math.BigDecimal;
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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "accession")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accession.findAll", query = "SELECT a FROM Accession a"),
    @NamedQuery(name = "Accession.findByAccessionID", query = "SELECT a FROM Accession a WHERE a.accessionID = :accessionID"),
    @NamedQuery(name = "Accession.findByTimestampCreated", query = "SELECT a FROM Accession a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Accession.findByTimestampModified", query = "SELECT a FROM Accession a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Accession.findByVersion", query = "SELECT a FROM Accession a WHERE a.version = :version"),
    @NamedQuery(name = "Accession.findByAccessionCondition", query = "SELECT a FROM Accession a WHERE a.accessionCondition = :accessionCondition"),
    @NamedQuery(name = "Accession.findByAccessionNumber", query = "SELECT a FROM Accession a WHERE a.accessionNumber = :accessionNumber"),
    @NamedQuery(name = "Accession.findByDateAccessioned", query = "SELECT a FROM Accession a WHERE a.dateAccessioned = :dateAccessioned"),
    @NamedQuery(name = "Accession.findByDateAcknowledged", query = "SELECT a FROM Accession a WHERE a.dateAcknowledged = :dateAcknowledged"),
    @NamedQuery(name = "Accession.findByDateReceived", query = "SELECT a FROM Accession a WHERE a.dateReceived = :dateReceived"),
    @NamedQuery(name = "Accession.findByNumber1", query = "SELECT a FROM Accession a WHERE a.number1 = :number1"),
    @NamedQuery(name = "Accession.findByNumber2", query = "SELECT a FROM Accession a WHERE a.number2 = :number2"),
    @NamedQuery(name = "Accession.findByStatus", query = "SELECT a FROM Accession a WHERE a.status = :status"),
    @NamedQuery(name = "Accession.findByTotalValue", query = "SELECT a FROM Accession a WHERE a.totalValue = :totalValue"),
    @NamedQuery(name = "Accession.findByType", query = "SELECT a FROM Accession a WHERE a.type = :type"),
    @NamedQuery(name = "Accession.findByVerbatimDate", query = "SELECT a FROM Accession a WHERE a.verbatimDate = :verbatimDate"),
    @NamedQuery(name = "Accession.findByYesNo1", query = "SELECT a FROM Accession a WHERE a.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Accession.findByYesNo2", query = "SELECT a FROM Accession a WHERE a.yesNo2 = :yesNo2")})
public class Accession extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AccessionID")
    private Integer accessionID;
      
    @Size(max = 255)
    @Column(name = "AccessionCondition")
    private String accessionCondition;
    
    @Basic(optional = false)
//    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "AccessionNumber")
    private String accessionNumber;
    
    @Column(name = "DateAccessioned")
    @Temporal(TemporalType.DATE)
    private Date dateAccessioned;
    
    @Column(name = "DateAcknowledged")
    @Temporal(TemporalType.DATE)
    private Date dateAcknowledged;
    
    @Column(name = "DateReceived")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Status")
    private String status;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text3")
    private String text3;
    
    @Column(name = "TotalValue")
    private BigDecimal totalValue;
    
    @Size(max = 32)
    @Column(name = "Type")
    private String type;
    
    @Size(max = 50)
    @Column(name = "VerbatimDate")
    private String verbatimDate;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(mappedBy = "accessionID")
    private Collection<Treatmentevent> treatmenteventCollection;
    
    @OneToMany(mappedBy = "accessionID")
    private Collection<Appraisal> appraisalCollection;
    
    @OneToMany(mappedBy = "accessionID")
    private Collection<Accessionagent> accessionagentCollection;
    
    @JoinColumn(name = "AddressOfRecordID", referencedColumnName = "AddressOfRecordID")
    @ManyToOne
    private Addressofrecord addressOfRecordID;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Division divisionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "RepositoryAgreementID", referencedColumnName = "RepositoryAgreementID")
    @ManyToOne
    private Repositoryagreement repositoryAgreementID;
    
    @OneToMany(mappedBy = "accessionID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @OneToMany(mappedBy = "accessionID")
    private Collection<Deaccession> deaccessionCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessionID")
    private Collection<Accessionattachment> accessionattachmentCollection;
    
    @OneToMany(mappedBy = "accessionID")
    private Collection<Accessionauthorization> accessionauthorizationCollection;

    public Accession() {
    }

    public Accession(Integer accessionID) {
        this.accessionID = accessionID;
    }

    public Accession(Integer accessionID, Date timestampCreated, String accessionNumber) {
        super(timestampCreated);
        this.accessionID = accessionID; 
        this.accessionNumber = accessionNumber;
    }

    public Integer getAccessionID() {
        return accessionID;
    }

    public void setAccessionID(Integer accessionID) {
        this.accessionID = accessionID;
    } 

    public String getAccessionCondition() {
        return accessionCondition;
    }

    public void setAccessionCondition(String accessionCondition) {
        this.accessionCondition = accessionCondition;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public Date getDateAccessioned() {
        return dateAccessioned;
    }

    public void setDateAccessioned(Date dateAccessioned) {
        this.dateAccessioned = dateAccessioned;
    }

    public Date getDateAcknowledged() {
        return dateAcknowledged;
    }

    public void setDateAcknowledged(Date dateAcknowledged) {
        this.dateAcknowledged = dateAcknowledged;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerbatimDate() {
        return verbatimDate;
    }

    public void setVerbatimDate(String verbatimDate) {
        this.verbatimDate = verbatimDate;
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
    public Collection<Treatmentevent> getTreatmenteventCollection() {
        return treatmenteventCollection;
    }

    public void setTreatmenteventCollection(Collection<Treatmentevent> treatmenteventCollection) {
        this.treatmenteventCollection = treatmenteventCollection;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisalCollection() {
        return appraisalCollection;
    }

    public void setAppraisalCollection(Collection<Appraisal> appraisalCollection) {
        this.appraisalCollection = appraisalCollection;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagentCollection() {
        return accessionagentCollection;
    }

    public void setAccessionagentCollection(Collection<Accessionagent> accessionagentCollection) {
        this.accessionagentCollection = accessionagentCollection;
    }

    public Addressofrecord getAddressOfRecordID() {
        return addressOfRecordID;
    }

    public void setAddressOfRecordID(Addressofrecord addressOfRecordID) {
        this.addressOfRecordID = addressOfRecordID;
    }

    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
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

    public Repositoryagreement getRepositoryAgreementID() {
        return repositoryAgreementID;
    }

    public void setRepositoryAgreementID(Repositoryagreement repositoryAgreementID) {
        this.repositoryAgreementID = repositoryAgreementID;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    @XmlTransient
    public Collection<Deaccession> getDeaccessionCollection() {
        return deaccessionCollection;
    }

    public void setDeaccessionCollection(Collection<Deaccession> deaccessionCollection) {
        this.deaccessionCollection = deaccessionCollection;
    }

    @XmlTransient
    public Collection<Accessionattachment> getAccessionattachmentCollection() {
        return accessionattachmentCollection;
    }

    public void setAccessionattachmentCollection(Collection<Accessionattachment> accessionattachmentCollection) {
        this.accessionattachmentCollection = accessionattachmentCollection;
    }

    @XmlTransient
    public Collection<Accessionauthorization> getAccessionauthorizationCollection() {
        return accessionauthorizationCollection;
    }

    public void setAccessionauthorizationCollection(Collection<Accessionauthorization> accessionauthorizationCollection) {
        this.accessionauthorizationCollection = accessionauthorizationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionID != null ? accessionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accession)) {
            return false;
        }
        Accession other = (Accession) object;
        if ((this.accessionID == null && other.accessionID != null) || (this.accessionID != null && !this.accessionID.equals(other.accessionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accession[ accessionID=" + accessionID + " ]";
    }
    
}

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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "repositoryagreement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Repositoryagreement.findAll", query = "SELECT r FROM Repositoryagreement r"),
    @NamedQuery(name = "Repositoryagreement.findByRepositoryAgreementId", query = "SELECT r FROM Repositoryagreement r WHERE r.repositoryAgreementId = :repositoryAgreementId"),
    @NamedQuery(name = "Repositoryagreement.findByTimestampCreated", query = "SELECT r FROM Repositoryagreement r WHERE r.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Repositoryagreement.findByTimestampModified", query = "SELECT r FROM Repositoryagreement r WHERE r.timestampModified = :timestampModified"),
    @NamedQuery(name = "Repositoryagreement.findByVersion", query = "SELECT r FROM Repositoryagreement r WHERE r.version = :version"),
    @NamedQuery(name = "Repositoryagreement.findByDateReceived", query = "SELECT r FROM Repositoryagreement r WHERE r.dateReceived = :dateReceived"),
    @NamedQuery(name = "Repositoryagreement.findByEndDate", query = "SELECT r FROM Repositoryagreement r WHERE r.endDate = :endDate"),
    @NamedQuery(name = "Repositoryagreement.findByNumber1", query = "SELECT r FROM Repositoryagreement r WHERE r.number1 = :number1"),
    @NamedQuery(name = "Repositoryagreement.findByNumber2", query = "SELECT r FROM Repositoryagreement r WHERE r.number2 = :number2"),
    @NamedQuery(name = "Repositoryagreement.findByRepositoryAgreementNumber", query = "SELECT r FROM Repositoryagreement r WHERE r.repositoryAgreementNumber = :repositoryAgreementNumber"),
    @NamedQuery(name = "Repositoryagreement.findByStartDate", query = "SELECT r FROM Repositoryagreement r WHERE r.startDate = :startDate"),
    @NamedQuery(name = "Repositoryagreement.findByStatus", query = "SELECT r FROM Repositoryagreement r WHERE r.status = :status"),
    @NamedQuery(name = "Repositoryagreement.findByText1", query = "SELECT r FROM Repositoryagreement r WHERE r.text1 = :text1"),
    @NamedQuery(name = "Repositoryagreement.findByText2", query = "SELECT r FROM Repositoryagreement r WHERE r.text2 = :text2"),
    @NamedQuery(name = "Repositoryagreement.findByText3", query = "SELECT r FROM Repositoryagreement r WHERE r.text3 = :text3"),
    @NamedQuery(name = "Repositoryagreement.findByYesNo1", query = "SELECT r FROM Repositoryagreement r WHERE r.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Repositoryagreement.findByYesNo2", query = "SELECT r FROM Repositoryagreement r WHERE r.yesNo2 = :yesNo2")})
public class Repositoryagreement extends BaseEntity {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "RepositoryAgreementID")
    private Integer repositoryAgreementId;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "DateReceived")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "RepositoryAgreementNumber")
    private String repositoryAgreementNumber;
    
    @Size(max = 32)
    @Column(name = "Status")
    private String status;
    
    @Size(max = 255)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 255)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 255)
    @Column(name = "Text3")
    private String text3;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repositoryAgreement")
    private Collection<Repositoryagreementattachment> repositoryAgreementAttachments;
    
    @OneToMany(mappedBy = "repositoryAgreement")
    private Collection<Accessionagent> repositoryAgreementAgents;
    
    @JoinColumn(name = "AddressOfRecordID", referencedColumnName = "AddressOfRecordID")
    @ManyToOne
    private Addressofrecord addressOfRecord;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Division division;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
     
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @NotNull
    @ManyToOne(optional = false)
    private Agent originator;
    
    @OneToMany(mappedBy = "repositoryAgreement")
    private Collection<Accession> accessions;
    
    @OneToMany(mappedBy = "repositoryAgreement")
    private Collection<Accessionauthorization> repositoryAgreementAuthorizations;

    public Repositoryagreement() {
    }

    public Repositoryagreement(Integer repositoryAgreementId) {
        this.repositoryAgreementId = repositoryAgreementId;
    }

    public Repositoryagreement(Integer repositoryAgreementId, Date timestampCreated, String repositoryAgreementNumber) {
        super(timestampCreated);
        this.repositoryAgreementId = repositoryAgreementId; 
        this.repositoryAgreementNumber = repositoryAgreementNumber;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (repositoryAgreementId != null) ? repositoryAgreementId.toString() : "0";
    }

    @XmlTransient
    public Collection<Accession> getAccessions() {
        return accessions;
    }

    public void setAccessions(Collection<Accession> accessions) {
        this.accessions = accessions;
    }

    public Addressofrecord getAddressOfRecord() {
        return addressOfRecord;
    }

    public void setAddressOfRecord(Addressofrecord addressOfRecord) {
        this.addressOfRecord = addressOfRecord;
    }
 
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    
    @NotNull(message="Division must be specified.")
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Agent getOriginator() {
        return originator;
    }

    public void setOriginator(Agent originator) {
        this.originator = originator;
    }

//    @XmlTransient
    public Collection<Accessionagent> getRepositoryAgreementAgents() {
        return repositoryAgreementAgents;
    }

    public void setRepositoryAgreementAgents(Collection<Accessionagent> repositoryAgreementAgents) {
        this.repositoryAgreementAgents = repositoryAgreementAgents;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryAgreementAttachments() {
        return repositoryAgreementAttachments;
    }

    public void setRepositoryAgreementAttachments(Collection<Repositoryagreementattachment> repositoryAgreementAttachments) {
        this.repositoryAgreementAttachments = repositoryAgreementAttachments;
    }
 
    public Collection<Accessionauthorization> getRepositoryAgreementAuthorizations() {
        return repositoryAgreementAuthorizations;
    }

    public void setRepositoryAgreementAuthorizations(Collection<Accessionauthorization> repositoryAgreementAuthorizations) {
        this.repositoryAgreementAuthorizations = repositoryAgreementAuthorizations;
    }

    public Integer getRepositoryAgreementId() {
        return repositoryAgreementId;
    }

    public void setRepositoryAgreementId(Integer repositoryAgreementId) {
        this.repositoryAgreementId = repositoryAgreementId;
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

    @NotNull(message="repositoryAgreementNumber must be specified.")
    public String getRepositoryAgreementNumber() {
        return repositoryAgreementNumber;
    }

    public void setRepositoryAgreementNumber(String repositoryAgreementNumber) {
        this.repositoryAgreementNumber = repositoryAgreementNumber;
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

     
    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEntityName() {
        return "repositoryAgreement";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (repositoryAgreementId != null ? repositoryAgreementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repositoryagreement)) {
            return false;
        }
        Repositoryagreement other = (Repositoryagreement) object;
        if ((this.repositoryAgreementId == null && other.repositoryAgreementId != null) || (this.repositoryAgreementId != null && !this.repositoryAgreementId.equals(other.repositoryAgreementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Repositoryagreement[ repositoryAgreementID=" + repositoryAgreementId + " ]";
    } 
    
}

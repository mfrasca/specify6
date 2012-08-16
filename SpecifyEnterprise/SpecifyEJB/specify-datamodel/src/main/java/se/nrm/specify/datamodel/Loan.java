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
@Table(name = "loan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l"),
    @NamedQuery(name = "Loan.findByLoanID", query = "SELECT l FROM Loan l WHERE l.loanId = :loanID"),
    @NamedQuery(name = "Loan.findByTimestampCreated", query = "SELECT l FROM Loan l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Loan.findByTimestampModified", query = "SELECT l FROM Loan l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Loan.findByVersion", query = "SELECT l FROM Loan l WHERE l.version = :version"),
    @NamedQuery(name = "Loan.findByCurrentDueDate", query = "SELECT l FROM Loan l WHERE l.currentDueDate = :currentDueDate"),
    @NamedQuery(name = "Loan.findByDateClosed", query = "SELECT l FROM Loan l WHERE l.dateClosed = :dateClosed"),
    @NamedQuery(name = "Loan.findByDateReceived", query = "SELECT l FROM Loan l WHERE l.dateReceived = :dateReceived"),
    @NamedQuery(name = "Loan.findByIsClosed", query = "SELECT l FROM Loan l WHERE l.isClosed = :isClosed"),
    @NamedQuery(name = "Loan.findByIsFinancialResponsibility", query = "SELECT l FROM Loan l WHERE l.isFinancialResponsibility = :isFinancialResponsibility"),
    @NamedQuery(name = "Loan.findByLoanDate", query = "SELECT l FROM Loan l WHERE l.loanDate = :loanDate"),
    @NamedQuery(name = "Loan.findByLoanNumber", query = "SELECT l FROM Loan l WHERE l.loanNumber = :loanNumber"),
    @NamedQuery(name = "Loan.findByNumber1", query = "SELECT l FROM Loan l WHERE l.number1 = :number1"),
    @NamedQuery(name = "Loan.findByNumber2", query = "SELECT l FROM Loan l WHERE l.number2 = :number2"),
    @NamedQuery(name = "Loan.findByOriginalDueDate", query = "SELECT l FROM Loan l WHERE l.originalDueDate = :originalDueDate"),
    @NamedQuery(name = "Loan.findByOverdueNotiSetDate", query = "SELECT l FROM Loan l WHERE l.overdueNotiSentDate = :overdueNotiSetDate"),
    @NamedQuery(name = "Loan.findByPurposeOfLoan", query = "SELECT l FROM Loan l WHERE l.purposeOfLoan = :purposeOfLoan"),
    @NamedQuery(name = "Loan.findByReceivedComments", query = "SELECT l FROM Loan l WHERE l.receivedComments = :receivedComments"),
    @NamedQuery(name = "Loan.findBySrcGeography", query = "SELECT l FROM Loan l WHERE l.srcGeography = :srcGeography"),
    @NamedQuery(name = "Loan.findBySrcTaxonomy", query = "SELECT l FROM Loan l WHERE l.srcTaxonomy = :srcTaxonomy"),
    @NamedQuery(name = "Loan.findByYesNo1", query = "SELECT l FROM Loan l WHERE l.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Loan.findByYesNo2", query = "SELECT l FROM Loan l WHERE l.yesNo2 = :yesNo2")})
public class Loan extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LoanID")
    private Integer loanId;
    
    @Column(name = "IsClosed")
    private Boolean isClosed;
    
    @Column(name = "IsFinancialResponsibility")
    private Boolean isFinancialResponsibility;
    
    @Column(name = "CurrentDueDate")
    @Temporal(TemporalType.DATE)
    private Date currentDueDate;
    
    @Column(name = "DateClosed")
    @Temporal(TemporalType.DATE)
    private Date dateClosed;
    
    @Column(name = "DateReceived")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    
    @Column(name = "LoanDate")
    @Temporal(TemporalType.DATE)
    private Date loanDate;
    
    @Column(name = "OriginalDueDate")
    @Temporal(TemporalType.DATE)
    private Date originalDueDate;
    
    @Column(name = "OverdueNotiSetDate")
    @Temporal(TemporalType.DATE)
    private Date overdueNotiSentDate;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 50)
    @Column(name = "LoanNumber")
    private String loanNumber;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Size(max = 64)
    @Column(name = "PurposeOfLoan")
    private String purposeOfLoan;
    
    @Size(max = 255)
    @Column(name = "ReceivedComments")
    private String receivedComments;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "SpecialConditions")
    private String specialConditions;
    
    @Size(max = 32)
    @Column(name = "SrcGeography")
    private String srcGeography;
    
    @Size(max = 32)
    @Column(name = "SrcTaxonomy")
    private String srcTaxonomy;
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(mappedBy = "loan")
    private Collection<Shipment> shipments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loan")
    private Collection<Loanagent> loanAgents;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loan")
    private Collection<Loanattachment> loanAttachments;
    
    @JoinColumn(name = "AddressOfRecordID", referencedColumnName = "AddressOfRecordID")
    @ManyToOne
    private Addressofrecord addressOfRecord;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Division division;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline discipline;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loan")
    private Collection<Loanpreparation> loanPreparations;

    public Loan() {
    }

    public Loan(Integer loanId) {
        this.loanId = loanId;
    }

    public Loan(Integer loanId, Date timestampCreated, String loanNumber) {
        super(timestampCreated);
        this.loanId = loanId; 
        this.loanNumber = loanNumber;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (loanId != null) ? loanId.toString() : "0";
    }
    
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Date getOverdueNotiSentDate() {
        return overdueNotiSentDate; 
    }

    public void setOverdueNotiSentDate(Date overdueNotiSentDate) {
        this.overdueNotiSentDate = overdueNotiSentDate;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Boolean getIsFinancialResponsibility() {
        return isFinancialResponsibility;
    }

    public void setIsFinancialResponsibility(Boolean isFinancialResponsibility) {
        this.isFinancialResponsibility = isFinancialResponsibility;
    }

    @NotNull(message="LoanNumber must be specified.")
    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
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

 

    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }

    public void setPurposeOfLoan(String purposeOfLoan) {
        this.purposeOfLoan = purposeOfLoan;
    }

    public String getReceivedComments() {
        return receivedComments;
    }

    public void setReceivedComments(String receivedComments) {
        this.receivedComments = receivedComments;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public String getSrcGeography() {
        return srcGeography;
    }

    public void setSrcGeography(String srcGeography) {
        this.srcGeography = srcGeography;
    }

    public String getSrcTaxonomy() {
        return srcTaxonomy;
    }

    public void setSrcTaxonomy(String srcTaxonomy) {
        this.srcTaxonomy = srcTaxonomy;
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

    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

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

    @XmlTransient
    public Collection<Loanagent> getLoanAgents() {
        return loanAgents;
    }

    public void setLoanAgents(Collection<Loanagent> loanAgents) {
        this.loanAgents = loanAgents;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanAttachments() {
        return loanAttachments;
    }

    public void setLoanAttachments(Collection<Loanattachment> loanAttachments) {
        this.loanAttachments = loanAttachments;
    }

    @XmlTransient
    public Collection<Loanpreparation> getLoanPreparations() {
        return loanPreparations;
    }

    public void setLoanPreparations(Collection<Loanpreparation> loanPreparations) {
        this.loanPreparations = loanPreparations;
    }

    @XmlTransient
    public Collection<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(Collection<Shipment> shipments) {
        this.shipments = shipments;
    }
  
        public Date getCurrentDueDate() {
        return currentDueDate;
    }

    public void setCurrentDueDate(Date currentDueDate) {
        this.currentDueDate = currentDueDate;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getOriginalDueDate() {
        return originalDueDate;
    }

    public void setOriginalDueDate(Date originalDueDate) {
        this.originalDueDate = originalDueDate;
    } 
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanId != null ? loanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loan)) {
            return false;
        }
        Loan other = (Loan) object;
        if ((this.loanId == null && other.loanId != null) || (this.loanId != null && !this.loanId.equals(other.loanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loan[ loanID=" + loanId + " ]";
    } 
}

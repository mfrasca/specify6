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
@Table(name = "borrow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrow.findAll", query = "SELECT b FROM Borrow b"),
    @NamedQuery(name = "Borrow.findByBorrowId", query = "SELECT b FROM Borrow b WHERE b.borrowId = :borrowId"),
    @NamedQuery(name = "Borrow.findByTimestampCreated", query = "SELECT b FROM Borrow b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrow.findByTimestampModified", query = "SELECT b FROM Borrow b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrow.findByVersion", query = "SELECT b FROM Borrow b WHERE b.version = :version"),
    @NamedQuery(name = "Borrow.findByCollectionMemberID", query = "SELECT b FROM Borrow b WHERE b.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Borrow.findByCurrentDueDate", query = "SELECT b FROM Borrow b WHERE b.currentDueDate = :currentDueDate"),
    @NamedQuery(name = "Borrow.findByDateClosed", query = "SELECT b FROM Borrow b WHERE b.dateClosed = :dateClosed"),
    @NamedQuery(name = "Borrow.findByInvoiceNumber", query = "SELECT b FROM Borrow b WHERE b.invoiceNumber = :invoiceNumber"),
    @NamedQuery(name = "Borrow.findByIsClosed", query = "SELECT b FROM Borrow b WHERE b.isClosed = :isClosed"),
    @NamedQuery(name = "Borrow.findByIsFinancialResponsibility", query = "SELECT b FROM Borrow b WHERE b.isFinancialResponsibility = :isFinancialResponsibility"),
    @NamedQuery(name = "Borrow.findByNumber1", query = "SELECT b FROM Borrow b WHERE b.number1 = :number1"),
    @NamedQuery(name = "Borrow.findByNumber2", query = "SELECT b FROM Borrow b WHERE b.number2 = :number2"),
    @NamedQuery(name = "Borrow.findByOriginalDueDate", query = "SELECT b FROM Borrow b WHERE b.originalDueDate = :originalDueDate"),
    @NamedQuery(name = "Borrow.findByReceivedDate", query = "SELECT b FROM Borrow b WHERE b.receivedDate = :receivedDate"),
    @NamedQuery(name = "Borrow.findByYesNo1", query = "SELECT b FROM Borrow b WHERE b.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Borrow.findByYesNo2", query = "SELECT b FROM Borrow b WHERE b.yesNo2 = :yesNo2")})
public class Borrow extends BaseEntity {
     
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "BorrowID")
    private Integer borrowId;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "InvoiceNumber")
    private String invoiceNumber;
    
    @Column(name = "IsClosed")
    private Boolean isClosed;
    
    @Column(name = "CurrentDueDate")
    @Temporal(TemporalType.DATE)
    private Date currentDueDate;
    
    @Column(name = "DateClosed")
    @Temporal(TemporalType.DATE)
    private Date dateClosed;
    
    @Column(name = "OriginalDueDate")
    @Temporal(TemporalType.DATE)
    private Date originalDueDate;
    
    @Column(name = "ReceivedDate")
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    
    @Column(name = "IsFinancialResponsibility")
    private Boolean isFinancialResponsibility;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
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
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(mappedBy = "borrow")
    private Collection<Shipment> shipments;
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval=true, mappedBy = "borrow")
    private Collection<Borrowmaterial> borrowMaterials;
    
    @JoinColumn(name = "AddressOfRecordID", referencedColumnName = "AddressOfRecordID")
    @ManyToOne
    private Addressofrecord addressOfRecord;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "borrow", orphanRemoval=true)
    private Collection<Borrowagent> borrowAgents;

    public Borrow() {
    }

    public Borrow(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public Borrow(Integer borrowId, Date timestampCreated, int collectionMemberId, String invoiceNumber) {
        super(timestampCreated);
        this.borrowId = borrowId; 
        this.collectionMemberId = collectionMemberId;
        this.invoiceNumber = invoiceNumber;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (borrowId != null) ? borrowId.toString() : "0";
    }
  
    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    @NotNull(message="InvoiceNumber must be specified.")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public Collection<Borrowagent> getBorrowAgents() {
        return borrowAgents;
    }

    public void setBorrowAgents(Collection<Borrowagent> borrowAgents) {
        this.borrowAgents = borrowAgents;
    }

    
    public Collection<Borrowmaterial> getBorrowMaterials() {
        return borrowMaterials;
    }

    public void setBorrowMaterials(Collection<Borrowmaterial> borrowMaterials) {
        this.borrowMaterials = borrowMaterials;
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

    public Date getOriginalDueDate() {
        return originalDueDate;
    }

    public void setOriginalDueDate(Date originalDueDate) {
        this.originalDueDate = originalDueDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
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
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }
  
    @Override
    public String getEntityName() {
        return "borrow";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (borrowId != null ? borrowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrow)) {
            return false;
        }
        Borrow other = (Borrow) object;
        if ((this.borrowId == null && other.borrowId != null) || (this.borrowId != null && !this.borrowId.equals(other.borrowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Borrow[ borrowID=" + borrowId + " ]";
    }
 
   
    
}

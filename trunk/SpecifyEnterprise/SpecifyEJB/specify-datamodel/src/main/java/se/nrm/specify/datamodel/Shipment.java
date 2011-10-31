package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "shipment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shipment.findAll", query = "SELECT s FROM Shipment s"),
    @NamedQuery(name = "Shipment.findByShipmentID", query = "SELECT s FROM Shipment s WHERE s.shipmentID = :shipmentID"),
    @NamedQuery(name = "Shipment.findByTimestampCreated", query = "SELECT s FROM Shipment s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Shipment.findByTimestampModified", query = "SELECT s FROM Shipment s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Shipment.findByVersion", query = "SELECT s FROM Shipment s WHERE s.version = :version"),
    @NamedQuery(name = "Shipment.findByInsuredForAmount", query = "SELECT s FROM Shipment s WHERE s.insuredForAmount = :insuredForAmount"),
    @NamedQuery(name = "Shipment.findByNumber1", query = "SELECT s FROM Shipment s WHERE s.number1 = :number1"),
    @NamedQuery(name = "Shipment.findByNumber2", query = "SELECT s FROM Shipment s WHERE s.number2 = :number2"),
    @NamedQuery(name = "Shipment.findByNumberOfPackages", query = "SELECT s FROM Shipment s WHERE s.numberOfPackages = :numberOfPackages"),
    @NamedQuery(name = "Shipment.findByShipmentDate", query = "SELECT s FROM Shipment s WHERE s.shipmentDate = :shipmentDate"),
    @NamedQuery(name = "Shipment.findByShipmentMethod", query = "SELECT s FROM Shipment s WHERE s.shipmentMethod = :shipmentMethod"),
    @NamedQuery(name = "Shipment.findByShipmentNumber", query = "SELECT s FROM Shipment s WHERE s.shipmentNumber = :shipmentNumber"),
    @NamedQuery(name = "Shipment.findByWeight", query = "SELECT s FROM Shipment s WHERE s.weight = :weight"),
    @NamedQuery(name = "Shipment.findByYesNo1", query = "SELECT s FROM Shipment s WHERE s.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Shipment.findByYesNo2", query = "SELECT s FROM Shipment s WHERE s.yesNo2 = :yesNo2")})
public class Shipment extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ShipmentID")
    private Integer shipmentID;
     
    @Size(max = 50)
    @Column(name = "InsuredForAmount")
    private String insuredForAmount;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Column(name = "NumberOfPackages")
    private Short numberOfPackages;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "ShipmentDate")
    @Temporal(TemporalType.DATE)
    private Date shipmentDate;
    
    @Size(max = 50)
    @Column(name = "ShipmentMethod")
    private String shipmentMethod;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ShipmentNumber")
    private String shipmentNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 50)
    @Column(name = "Weight")
    private String weight;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @JoinColumn(name = "BorrowID", referencedColumnName = "BorrowID")
    @ManyToOne
    private Borrow borrowID;
    
    @JoinColumn(name = "ShipperID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent shipperID;
    
    @JoinColumn(name = "ShippedToID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent shippedToID;
    
    @JoinColumn(name = "ShippedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent shippedByID;
    
    @JoinColumn(name = "ExchangeOutID", referencedColumnName = "ExchangeOutID")
    @ManyToOne
    private Exchangeout exchangeOutID;
    
    @JoinColumn(name = "LoanID", referencedColumnName = "LoanID")
    @ManyToOne
    private Loan loanID;
    
    @JoinColumn(name = "GiftID", referencedColumnName = "GiftID")
    @ManyToOne
    private Gift giftID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline disciplineID;

    public Shipment() {
    }

    public Shipment(Integer shipmentID) {
        this.shipmentID = shipmentID;
    }

    public Shipment(Integer shipmentID, Date timestampCreated, String shipmentNumber) {
        super(timestampCreated);
        this.shipmentID = shipmentID; 
        this.shipmentNumber = shipmentNumber;
    }

    public Integer getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(Integer shipmentID) {
        this.shipmentID = shipmentID;
    }
 
    public String getInsuredForAmount() {
        return insuredForAmount;
    }

    public void setInsuredForAmount(String insuredForAmount) {
        this.insuredForAmount = insuredForAmount;
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

    public Short getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(Short numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public Borrow getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(Borrow borrowID) {
        this.borrowID = borrowID;
    }

    public Agent getShipperID() {
        return shipperID;
    }

    public void setShipperID(Agent shipperID) {
        this.shipperID = shipperID;
    }

    public Agent getShippedToID() {
        return shippedToID;
    }

    public void setShippedToID(Agent shippedToID) {
        this.shippedToID = shippedToID;
    }

    public Agent getShippedByID() {
        return shippedByID;
    }

    public void setShippedByID(Agent shippedByID) {
        this.shippedByID = shippedByID;
    }

    public Exchangeout getExchangeOutID() {
        return exchangeOutID;
    }

    public void setExchangeOutID(Exchangeout exchangeOutID) {
        this.exchangeOutID = exchangeOutID;
    }

    public Loan getLoanID() {
        return loanID;
    }

    public void setLoanID(Loan loanID) {
        this.loanID = loanID;
    }

    public Gift getGiftID() {
        return giftID;
    }

    public void setGiftID(Gift giftID) {
        this.giftID = giftID;
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

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shipmentID != null ? shipmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shipment)) {
            return false;
        }
        Shipment other = (Shipment) object;
        if ((this.shipmentID == null && other.shipmentID != null) || (this.shipmentID != null && !this.shipmentID.equals(other.shipmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Shipment[ shipmentID=" + shipmentID + " ]";
    }
    
}

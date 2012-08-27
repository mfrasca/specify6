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
@Table(name = "gift")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gift.findAll", query = "SELECT g FROM Gift g"),
    @NamedQuery(name = "Gift.findByGiftId", query = "SELECT g FROM Gift g WHERE g.giftId = :giftId"),
    @NamedQuery(name = "Gift.findByTimestampCreated", query = "SELECT g FROM Gift g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Gift.findByTimestampModified", query = "SELECT g FROM Gift g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Gift.findByVersion", query = "SELECT g FROM Gift g WHERE g.version = :version"),
    @NamedQuery(name = "Gift.findByDateReceived", query = "SELECT g FROM Gift g WHERE g.dateReceived = :dateReceived"),
    @NamedQuery(name = "Gift.findByGiftDate", query = "SELECT g FROM Gift g WHERE g.giftDate = :giftDate"),
    @NamedQuery(name = "Gift.findByGiftNumber", query = "SELECT g FROM Gift g WHERE g.giftNumber = :giftNumber"),
    @NamedQuery(name = "Gift.findByIsFinancialResponsibility", query = "SELECT g FROM Gift g WHERE g.isFinancialResponsibility = :isFinancialResponsibility"),
    @NamedQuery(name = "Gift.findByNumber1", query = "SELECT g FROM Gift g WHERE g.number1 = :number1"),
    @NamedQuery(name = "Gift.findByNumber2", query = "SELECT g FROM Gift g WHERE g.number2 = :number2"),
    @NamedQuery(name = "Gift.findByPurposeOfGift", query = "SELECT g FROM Gift g WHERE g.purposeOfGift = :purposeOfGift"),
    @NamedQuery(name = "Gift.findByReceivedComments", query = "SELECT g FROM Gift g WHERE g.receivedComments = :receivedComments"),
    @NamedQuery(name = "Gift.findBySrcGeography", query = "SELECT g FROM Gift g WHERE g.srcGeography = :srcGeography"),
    @NamedQuery(name = "Gift.findBySrcTaxonomy", query = "SELECT g FROM Gift g WHERE g.srcTaxonomy = :srcTaxonomy"),
    @NamedQuery(name = "Gift.findByYesNo1", query = "SELECT g FROM Gift g WHERE g.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Gift.findByYesNo2", query = "SELECT g FROM Gift g WHERE g.yesNo2 = :yesNo2")})
public class Gift extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GiftID")
    private Integer giftId;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GiftNumber")
    private String giftNumber;
    
    @Column(name = "IsFinancialResponsibility")
    private Boolean isFinancialResponsibility;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1") 
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Size(max = 64)
    @Column(name = "PurposeOfGift")
    private String purposeOfGift;
    
    @Size(max = 255)
    @Column(name = "ReceivedComments")
    private String receivedComments;
    
    @Column(name = "DateReceived")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    
    @Column(name = "GiftDate")
    @Temporal(TemporalType.DATE)
    private Date giftDate;
    
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
    
    @OneToMany(mappedBy = "gift")
    private Collection<Shipment> shipments;
    
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
    @NotNull
    @ManyToOne(optional = false)
    private Discipline discipline;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gift")
    private Collection<Giftagent> giftAgents;
    
    @OneToMany(mappedBy = "gift")
    private Collection<Giftpreparation> giftPreparations;

    public Gift() {
    }

    public Gift(Integer giftId) {
        this.giftId = giftId;
    }

    public Gift(Integer giftId, Date timestampCreated, String giftNumber) {
        super(timestampCreated);
        this.giftId = giftId; 
        this.giftNumber = giftNumber;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (giftId != null) ? giftId.toString() : "0";
    }
    
    public Addressofrecord getAddressOfRecord() {
        return addressOfRecord;
    }

    public void setAddressOfRecord(Addressofrecord addressOfRecord) {
        this.addressOfRecord = addressOfRecord;
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    @NotNull(message="GiftNumber must be specified.")
    public String getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(String giftNumber) {
        this.giftNumber = giftNumber;
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

    public String getPurposeOfGift() {
        return purposeOfGift;
    }

    public void setPurposeOfGift(String purposeOfGift) {
        this.purposeOfGift = purposeOfGift;
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
    public Collection<Giftagent> getGiftAgents() {
        return giftAgents;
    }

    public void setGiftAgents(Collection<Giftagent> giftAgents) {
        this.giftAgents = giftAgents;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftPreparations() {
        return giftPreparations;
    }

    public void setGiftPreparations(Collection<Giftpreparation> giftPreparations) {
        this.giftPreparations = giftPreparations;
    }

    @XmlTransient
    public Collection<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(Collection<Shipment> shipments) {
        this.shipments = shipments;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getGiftDate() {
        return giftDate;
    }

    public void setGiftDate(Date giftDate) {
        this.giftDate = giftDate;
    }
    
    @Override
    public String getEntityName() {
        return "gift";
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (giftId != null ? giftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gift)) {
            return false;
        }
        Gift other = (Gift) object;
        if ((this.giftId == null && other.giftId != null) || (this.giftId != null && !this.giftId.equals(other.giftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Gift[ giftID=" + giftId + " ]";
    }
 
}

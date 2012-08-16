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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "exchangein")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exchangein.findAll", query = "SELECT e FROM Exchangein e"),
    @NamedQuery(name = "Exchangein.findByExchangeInID", query = "SELECT e FROM Exchangein e WHERE e.exchangeInId = :exchangeInID"),
    @NamedQuery(name = "Exchangein.findByTimestampCreated", query = "SELECT e FROM Exchangein e WHERE e.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Exchangein.findByTimestampModified", query = "SELECT e FROM Exchangein e WHERE e.timestampModified = :timestampModified"),
    @NamedQuery(name = "Exchangein.findByVersion", query = "SELECT e FROM Exchangein e WHERE e.version = :version"),
    @NamedQuery(name = "Exchangein.findByDescriptionOfMaterial", query = "SELECT e FROM Exchangein e WHERE e.descriptionOfMaterial = :descriptionOfMaterial"),
    @NamedQuery(name = "Exchangein.findByExchangeDate", query = "SELECT e FROM Exchangein e WHERE e.exchangeDate = :exchangeDate"),
    @NamedQuery(name = "Exchangein.findByNumber1", query = "SELECT e FROM Exchangein e WHERE e.number1 = :number1"),
    @NamedQuery(name = "Exchangein.findByNumber2", query = "SELECT e FROM Exchangein e WHERE e.number2 = :number2"),
    @NamedQuery(name = "Exchangein.findByQuantityExchanged", query = "SELECT e FROM Exchangein e WHERE e.quantityExchanged = :quantityExchanged"),
    @NamedQuery(name = "Exchangein.findBySrcGeography", query = "SELECT e FROM Exchangein e WHERE e.srcGeography = :srcGeography"),
    @NamedQuery(name = "Exchangein.findBySrcTaxonomy", query = "SELECT e FROM Exchangein e WHERE e.srcTaxonomy = :srcTaxonomy"),
    @NamedQuery(name = "Exchangein.findByYesNo1", query = "SELECT e FROM Exchangein e WHERE e.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Exchangein.findByYesNo2", query = "SELECT e FROM Exchangein e WHERE e.yesNo2 = :yesNo2")})
public class Exchangein extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ExchangeInID")
    private Integer exchangeInId;
     
    @Size(max = 120)
    @Column(name = "DescriptionOfMaterial")
    private String descriptionOfMaterial;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Column(name = "QuantityExchanged")
    private Short quantityExchanged;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "SrcGeography")
    private String srcGeography;
    
    @Size(max = 32)
    @Column(name = "SrcTaxonomy")
    private String srcTaxonomy;
    
    @Column(name = "ExchangeDate")
    @Temporal(TemporalType.DATE)
    private Date exchangeDate;
    
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
    
    @JoinColumn(name = "ReceivedFromOrganizationID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentReceivedFrom;
    
    @JoinColumn(name = "AddressOfRecordID", referencedColumnName = "AddressOfRecordID")
    @ManyToOne
    private Addressofrecord addressOfRecord;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Division division;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "CatalogedByID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentCatalogedBy;

    public Exchangein() {
    }

    public Exchangein(Integer exchangeInId) {
        this.exchangeInId = exchangeInId;
    }

    public Exchangein(Integer exchangeInId, Date timestampCreated) {
        super(timestampCreated);
        this.exchangeInId = exchangeInId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (exchangeInId != null) ? exchangeInId.toString() : "0";
    }
    public Integer getExchangeInId() {
        return exchangeInId;
    }

    public void setExchangeInId(Integer exchangeInId) {
        this.exchangeInId = exchangeInId;
    }

 
 
    public String getDescriptionOfMaterial() {
        return descriptionOfMaterial;
    }

    public void setDescriptionOfMaterial(String descriptionOfMaterial) {
        this.descriptionOfMaterial = descriptionOfMaterial;
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

    public Short getQuantityExchanged() {
        return quantityExchanged;
    }

    public void setQuantityExchanged(Short quantityExchanged) {
        this.quantityExchanged = quantityExchanged;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
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

    @NotNull(message="AgentCatalogedBy must be specified.")
    public Agent getAgentCatalogedBy() {
        return agentCatalogedBy;
    }

    public void setAgentCatalogedBy(Agent agentCatalogedBy) {
        this.agentCatalogedBy = agentCatalogedBy;
    }

    @NotNull(message="AgentReceivedFrom must be specified.")
    public Agent getAgentReceivedFrom() {
        return agentReceivedFrom;
    }

    public void setAgentReceivedFrom(Agent agentReceivedFrom) {
        this.agentReceivedFrom = agentReceivedFrom;
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
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exchangeInId != null ? exchangeInId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exchangein)) {
            return false;
        }
        Exchangein other = (Exchangein) object;
        if ((this.exchangeInId == null && other.exchangeInId != null) || (this.exchangeInId != null && !this.exchangeInId.equals(other.exchangeInId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exchangein[ exchangeInID=" + exchangeInId + " ]";
    } 
}

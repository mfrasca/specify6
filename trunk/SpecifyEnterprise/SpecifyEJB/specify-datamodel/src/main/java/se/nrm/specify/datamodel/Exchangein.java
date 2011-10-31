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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Exchangein.findByExchangeInID", query = "SELECT e FROM Exchangein e WHERE e.exchangeInID = :exchangeInID"),
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
    private Integer exchangeInID;
     
    @Size(max = 120)
    @Column(name = "DescriptionOfMaterial")
    private String descriptionOfMaterial;
    
    @Column(name = "ExchangeDate")
    @Temporal(TemporalType.DATE)
    private Date exchangeDate;
    
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
    private Agent receivedFromOrganizationID;
    
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
    
    @JoinColumn(name = "CatalogedByID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent catalogedByID;

    public Exchangein() {
    }

    public Exchangein(Integer exchangeInID) {
        this.exchangeInID = exchangeInID;
    }

    public Exchangein(Integer exchangeInID, Date timestampCreated) {
        super(timestampCreated);
        this.exchangeInID = exchangeInID; 
    }

    public Integer getExchangeInID() {
        return exchangeInID;
    }

    public void setExchangeInID(Integer exchangeInID) {
        this.exchangeInID = exchangeInID;
    }
 
    public String getDescriptionOfMaterial() {
        return descriptionOfMaterial;
    }

    public void setDescriptionOfMaterial(String descriptionOfMaterial) {
        this.descriptionOfMaterial = descriptionOfMaterial;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
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

    public Boolean getYesNo2() {
        return yesNo2;
    }

    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    public Agent getReceivedFromOrganizationID() {
        return receivedFromOrganizationID;
    }

    public void setReceivedFromOrganizationID(Agent receivedFromOrganizationID) {
        this.receivedFromOrganizationID = receivedFromOrganizationID;
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

    public Agent getCatalogedByID() {
        return catalogedByID;
    }

    public void setCatalogedByID(Agent catalogedByID) {
        this.catalogedByID = catalogedByID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exchangeInID != null ? exchangeInID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exchangein)) {
            return false;
        }
        Exchangein other = (Exchangein) object;
        if ((this.exchangeInID == null && other.exchangeInID != null) || (this.exchangeInID != null && !this.exchangeInID.equals(other.exchangeInID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exchangein[ exchangeInID=" + exchangeInID + " ]";
    }
    
}

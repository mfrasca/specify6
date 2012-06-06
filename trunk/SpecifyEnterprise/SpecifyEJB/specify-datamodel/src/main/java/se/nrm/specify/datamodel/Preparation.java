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
 */
@Entity
@Table(name = "preparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preparation.findAll", query = "SELECT p FROM Preparation p"),
    @NamedQuery(name = "Preparation.findByPreparationID", query = "SELECT p FROM Preparation p WHERE p.preparationId = :preparationID"),
    @NamedQuery(name = "Preparation.findByTimestampCreated", query = "SELECT p FROM Preparation p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Preparation.findByTimestampModified", query = "SELECT p FROM Preparation p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Preparation.findByVersion", query = "SELECT p FROM Preparation p WHERE p.version = :version"),
    @NamedQuery(name = "Preparation.findByCollectionMemberID", query = "SELECT p FROM Preparation p WHERE p.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Preparation.findByCountAmt", query = "SELECT p FROM Preparation p WHERE p.countAmt = :countAmt"),
    @NamedQuery(name = "Preparation.findByDescription", query = "SELECT p FROM Preparation p WHERE p.description = :description"),
    @NamedQuery(name = "Preparation.findByNumber1", query = "SELECT p FROM Preparation p WHERE p.number1 = :number1"),
    @NamedQuery(name = "Preparation.findByNumber2", query = "SELECT p FROM Preparation p WHERE p.number2 = :number2"),
    @NamedQuery(name = "Preparation.findByPreparedDate", query = "SELECT p FROM Preparation p WHERE p.preparedDate = :preparedDate"),
    @NamedQuery(name = "Preparation.findByPreparedDatePrecision", query = "SELECT p FROM Preparation p WHERE p.preparedDatePrecision = :preparedDatePrecision"),
    @NamedQuery(name = "Preparation.findBySampleNumber", query = "SELECT p FROM Preparation p WHERE p.sampleNumber = :sampleNumber"),
    @NamedQuery(name = "Preparation.findByStatus", query = "SELECT p FROM Preparation p WHERE p.status = :status"),
    @NamedQuery(name = "Preparation.findByStorageLocation", query = "SELECT p FROM Preparation p WHERE p.storageLocation = :storageLocation"),
    @NamedQuery(name = "Preparation.findByYesNo1", query = "SELECT p FROM Preparation p WHERE p.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Preparation.findByYesNo2", query = "SELECT p FROM Preparation p WHERE p.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Preparation.findByYesNo3", query = "SELECT p FROM Preparation p WHERE p.yesNo3 = :yesNo3")})
public class Preparation extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PreparationID")
    private Integer preparationId;
 
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Column(name = "CountAmt")
    private Integer countAmt;
    
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Column(name = "PreparedDate")
    @Temporal(TemporalType.DATE)
    private Date preparedDate;
    
    @Column(name = "PreparedDatePrecision")
    private Short preparedDatePrecision;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "SampleNumber")
    private String sampleNumber;
    
    @Size(max = 32)
    @Column(name = "Status")
    private String status;
    
    @Size(max = 50)
    @Column(name = "StorageLocation")
    private String storageLocation;
    
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
    
    @Column(name = "YesNo3")
    private Boolean yesNo3;
    
    @JoinColumn(name = "PreparedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent preparedByAgent;            // change name to match common.view.xml
    
    @JoinColumn(name = "StorageID", referencedColumnName = "StorageID")
    @ManyToOne
    private Storage storage;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "PrepTypeID", referencedColumnName = "PrepTypeID")
    @ManyToOne(optional = false, cascade= CascadeType.ALL)
    private Preptype prepType;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "PreparationAttributeID", referencedColumnName = "PreparationAttributeID")
    @ManyToOne
    private Preparationattribute preparationAttribute;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preparation")
    private Collection<Preparationattachment> preparationAttachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preparation")
    private Collection<Preparationattr> preparationAttrs;
    
    @OneToMany(mappedBy = "preparation")
    private Collection<Giftpreparation> giftPreparations;
    
    @OneToMany(mappedBy = "preparation")
    private Collection<Deaccessionpreparation> deaccessionPreparations;
    
    @OneToMany(mappedBy = "preparation")
    private Collection<Loanpreparation> loanPreparations;

    public Preparation() {
    }

    public Preparation(Integer preparationId) {
        this.preparationId = preparationId;
    }

    public Preparation(Integer preparationId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.preparationId = preparationId; 
        this.collectionMemberId = collectionMemberId;
    }
 
    public Integer getCountAmt() {
        return countAmt;
    }

    public void setCountAmt(Integer countAmt) {
        this.countAmt = countAmt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(Date preparedDate) {
        this.preparedDate = preparedDate;
    }

    public Short getPreparedDatePrecision() {
        return preparedDatePrecision;
    }

    public void setPreparedDatePrecision(Short preparedDatePrecision) {
        this.preparedDatePrecision = preparedDatePrecision;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
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

    public Boolean getYesNo3() {
        return yesNo3;
    }

    public void setYesNo3(Boolean yesNo3) {
        this.yesNo3 = yesNo3;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    @XmlTransient
    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
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

    public Preptype getPrepType() {
        return prepType;
    }

    public void setPrepType(Preptype prepType) {
        this.prepType = prepType;
    }

    public Preparationattribute getPreparationAttribute() {
        return preparationAttribute;
    }

    public void setPreparationAttribute(Preparationattribute preparationAttribute) {
        this.preparationAttribute = preparationAttribute;
    }

    public Integer getPreparationId() {
        return preparationId;
    }

    public void setPreparationId(Integer preparationId) {
        this.preparationId = preparationId;
    }

    public Agent getPreparedByAgent() {
        return preparedByAgent;
    }

    public void setPreparedByAgent(Agent preparedByAgent) {
        this.preparedByAgent = preparedByAgent;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Collection<Deaccessionpreparation> getDeaccessionPreparations() {
        return deaccessionPreparations;
    }

    public void setDeaccessionPreparations(Collection<Deaccessionpreparation> deaccessionPreparations) {
        this.deaccessionPreparations = deaccessionPreparations;
    }

    public Collection<Giftpreparation> getGiftPreparations() {
        return giftPreparations;
    }

    public void setGiftPreparations(Collection<Giftpreparation> giftPreparations) {
        this.giftPreparations = giftPreparations;
    }

    public Collection<Loanpreparation> getLoanPreparations() {
        return loanPreparations;
    }

    public void setLoanPreparations(Collection<Loanpreparation> loanPreparations) {
        this.loanPreparations = loanPreparations;
    }

    public Collection<Preparationattachment> getPreparationAttachments() {
        return preparationAttachments;
    }

    public void setPreparationAttachments(Collection<Preparationattachment> preparationAttachments) {
        this.preparationAttachments = preparationAttachments;
    }

    public Collection<Preparationattr> getPreparationAttrs() {
        return preparationAttrs;
    }

    public void setPreparationAttrs(Collection<Preparationattr> preparationAttrs) {
        this.preparationAttrs = preparationAttrs;
    }

    
 
   

     
   
 
    
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        
        if(parent instanceof Collectionobject) {
            this.collectionObject = (Collectionobject)parent;
        } 
    }
     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preparationId != null ? preparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preparation)) {
            return false;
        }
        Preparation other = (Preparation) object;
        if ((this.preparationId == null && other.preparationId != null) || (this.preparationId != null && !this.preparationId.equals(other.preparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preparation[ preparationID=" + preparationId + " ]";
    }
    
}

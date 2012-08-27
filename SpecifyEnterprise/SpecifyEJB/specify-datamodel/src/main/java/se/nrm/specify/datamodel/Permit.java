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
@Table(name = "permit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permit.findAll", query = "SELECT p FROM Permit p"),
    @NamedQuery(name = "Permit.findByPermitId", query = "SELECT p FROM Permit p WHERE p.permitId = :permitId"),
    @NamedQuery(name = "Permit.findByTimestampCreated", query = "SELECT p FROM Permit p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Permit.findByTimestampModified", query = "SELECT p FROM Permit p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Permit.findByVersion", query = "SELECT p FROM Permit p WHERE p.version = :version"),
    @NamedQuery(name = "Permit.findByEndDate", query = "SELECT p FROM Permit p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "Permit.findByIssuedDate", query = "SELECT p FROM Permit p WHERE p.issuedDate = :issuedDate"),
    @NamedQuery(name = "Permit.findByNumber1", query = "SELECT p FROM Permit p WHERE p.number1 = :number1"),
    @NamedQuery(name = "Permit.findByNumber2", query = "SELECT p FROM Permit p WHERE p.number2 = :number2"),
    @NamedQuery(name = "Permit.findByPermitNumber", query = "SELECT p FROM Permit p WHERE p.permitNumber = :permitNumber"),
    @NamedQuery(name = "Permit.findByRenewalDate", query = "SELECT p FROM Permit p WHERE p.renewalDate = :renewalDate"),
    @NamedQuery(name = "Permit.findByStartDate", query = "SELECT p FROM Permit p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "Permit.findByType", query = "SELECT p FROM Permit p WHERE p.type = :type"),
    @NamedQuery(name = "Permit.findByYesNo1", query = "SELECT p FROM Permit p WHERE p.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Permit.findByYesNo2", query = "SELECT p FROM Permit p WHERE p.yesNo2 = :yesNo2")})
public class Permit extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PermitID")
    private Integer permitId;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PermitNumber")
    private String permitNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "IssuedDate")
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
    
    @Column(name = "RenewalDate")
    @Temporal(TemporalType.DATE)
    private Date renewalDate;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob 
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 50)
    @Column(name = "Type")
    private String type;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permit")
    private Collection<Permitattachment> permitAttachments;
    
    
    @JoinColumn(name = "IssuedToID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent issuedTo;
    
    @JoinColumn(name = "IssuedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent issuedBy;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "permit", orphanRemoval=true)
    private Collection<Accessionauthorization> accessionAuthorizations;

    public Permit() {
    }

    public Permit(Integer permitId) {
        this.permitId = permitId;
    }

    public Permit(Integer permitId, Date timestampCreated, String permitNumber) {
        super(timestampCreated);
        this.permitId = permitId; 
        this.permitNumber = permitNumber;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (permitId != null) ? permitId.toString() : "0";
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

    @NotNull(message="PermitNumber must be specified.")
    public String getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public Collection<Accessionauthorization> getAccessionAuthorizations() {
        return accessionAuthorizations;
    }

    public void setAccessionAuthorizations(Collection<Accessionauthorization> accessionAuthorizations) {
        this.accessionAuthorizations = accessionAuthorizations;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(Agent issuedBy) {
        this.issuedBy = issuedBy;
    }

    public Agent getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(Agent issuedTo) {
        this.issuedTo = issuedTo;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitAttachments() {
        return permitAttachments;
    }

    public void setPermitAttachments(Collection<Permitattachment> permitAttachments) {
        this.permitAttachments = permitAttachments;
    }

    public Integer getPermitId() {
        return permitId;
    }

    public void setPermitId(Integer permitId) {
        this.permitId = permitId;
    }

    @Override
    public String getEntityName() {
        return "permit";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permitId != null ? permitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permit)) {
            return false;
        }
        Permit other = (Permit) object;
        if ((this.permitId == null && other.permitId != null) || (this.permitId != null && !this.permitId.equals(other.permitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Permit[ permitID=" + permitId + " ]";
    } 
}

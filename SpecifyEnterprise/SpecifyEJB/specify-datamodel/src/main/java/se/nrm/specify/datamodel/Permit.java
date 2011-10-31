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
    @NamedQuery(name = "Permit.findByPermitID", query = "SELECT p FROM Permit p WHERE p.permitID = :permitID"),
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
    private Integer permitID;
     
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "IssuedDate")
    @Temporal(TemporalType.DATE)
    private Date issuedDate;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permitID")
    private Collection<Permitattachment> permitattachmentCollection;
    @JoinColumn(name = "IssuedToID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent issuedToID;
    
    @JoinColumn(name = "IssuedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent issuedByID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permitID")
    private Collection<Accessionauthorization> accessionauthorizationCollection;

    public Permit() {
    }

    public Permit(Integer permitID) {
        this.permitID = permitID;
    }

    public Permit(Integer permitID, Date timestampCreated, String permitNumber) {
        super(timestampCreated);
        this.permitID = permitID; 
        this.permitNumber = permitNumber;
    }

    public Integer getPermitID() {
        return permitID;
    }

    public void setPermitID(Integer permitID) {
        this.permitID = permitID;
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

    @XmlTransient
    public Collection<Permitattachment> getPermitattachmentCollection() {
        return permitattachmentCollection;
    }

    public void setPermitattachmentCollection(Collection<Permitattachment> permitattachmentCollection) {
        this.permitattachmentCollection = permitattachmentCollection;
    }

    public Agent getIssuedToID() {
        return issuedToID;
    }

    public void setIssuedToID(Agent issuedToID) {
        this.issuedToID = issuedToID;
    }

    public Agent getIssuedByID() {
        return issuedByID;
    }

    public void setIssuedByID(Agent issuedByID) {
        this.issuedByID = issuedByID;
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
        hash += (permitID != null ? permitID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permit)) {
            return false;
        }
        Permit other = (Permit) object;
        if ((this.permitID == null && other.permitID != null) || (this.permitID != null && !this.permitID.equals(other.permitID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Permit[ permitID=" + permitID + " ]";
    }
    
}

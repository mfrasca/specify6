package se.nrm.specify.datamodel;
 
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "appraisal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appraisal.findAll", query = "SELECT a FROM Appraisal a"),
    @NamedQuery(name = "Appraisal.findByAppraisalId", query = "SELECT a FROM Appraisal a WHERE a.appraisalId = :appraisalId"),
    @NamedQuery(name = "Appraisal.findByTimestampCreated", query = "SELECT a FROM Appraisal a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Appraisal.findByTimestampModified", query = "SELECT a FROM Appraisal a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Appraisal.findByVersion", query = "SELECT a FROM Appraisal a WHERE a.version = :version"),
    @NamedQuery(name = "Appraisal.findByAppraisalDate", query = "SELECT a FROM Appraisal a WHERE a.appraisalDate = :appraisalDate"),
    @NamedQuery(name = "Appraisal.findByAppraisalNumber", query = "SELECT a FROM Appraisal a WHERE a.appraisalNumber = :appraisalNumber"),
    @NamedQuery(name = "Appraisal.findByAppraisalValue", query = "SELECT a FROM Appraisal a WHERE a.appraisalValue = :appraisalValue"),
    @NamedQuery(name = "Appraisal.findByMonetaryUnitType", query = "SELECT a FROM Appraisal a WHERE a.monetaryUnitType = :monetaryUnitType")}) 
public class Appraisal extends BaseEntity {

    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AppraisalID")
    private Integer appraisalId; 
    
    @Basic(optional = false)  
    @NotNull(message="AppraisalNumber must be specified.")
    @Size(min = 1, max = 64)
    @Column(name = "AppraisalNumber")
    private String appraisalNumber;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AppraisalValue")
    private BigDecimal appraisalValue;
    
    @Size(max = 8)
    @Column(name = "MonetaryUnitType")
    private String monetaryUnitType;
    
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "AppraisalDate")
    @Temporal(TemporalType.DATE)
    private Date appraisalDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Notes")
    private String notes;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne
    private Accession accession;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @NotNull
    @ManyToOne(optional = false)
    private Agent agent;
    
    @OneToMany(mappedBy = "appraisal")
    private Collection<Collectionobject> collectionObjects;

    public Appraisal() {
    }

    public Appraisal(Integer appraisalId) {
        this.appraisalId = appraisalId;
    }

    public Appraisal(Integer appraisalId, Date timestampCreated, Date appraisalDate, String appraisalNumber) {
        super(timestampCreated);
        this.appraisalId = appraisalId;
        this.appraisalDate = appraisalDate;
        this.appraisalNumber = appraisalNumber;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (appraisalId != null) ? appraisalId.toString() : "0";
    }
    
    public Integer getAppraisalId() {
        return appraisalId;
    }

    public void setAppraisalId(Integer appraisalId) {
        this.appraisalId = appraisalId;
    }

    public String getAppraisalNumber() {
        return appraisalNumber;
    }

    public void setAppraisalNumber(String appraisalNumber) {
        this.appraisalNumber = appraisalNumber;
    }

    public BigDecimal getAppraisalValue() {
        return appraisalValue;
    }

    public void setAppraisalValue(BigDecimal appraisalValue) {
        this.appraisalValue = appraisalValue;
    }

    public String getMonetaryUnitType() {
        return monetaryUnitType;
    }

    public void setMonetaryUnitType(String monetaryUnitType) {
        this.monetaryUnitType = monetaryUnitType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    @XmlIDREF
    @NotNull(message="Agent must be specified.")
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
    
    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionObjects() {
        return collectionObjects;
    }

    public void setCollectionObjects(Collection<Collectionobject> collectionObjects) {
        this.collectionObjects = collectionObjects;
    }

 
    public Date getAppraisalDate() {
        return appraisalDate;
    }

    public void setAppraisalDate(Date appraisalDate) {
        this.appraisalDate = appraisalDate;
    }
 
    @Override
    public String getEntityName() {
        return "appraisal";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appraisalId != null ? appraisalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appraisal)) {
            return false;
        }
        Appraisal other = (Appraisal) object;
        if ((this.appraisalId == null && other.appraisalId != null) || (this.appraisalId != null && !this.appraisalId.equals(other.appraisalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appraisal[ appraisalID=" + appraisalId + " ]";
    }
 
    
}

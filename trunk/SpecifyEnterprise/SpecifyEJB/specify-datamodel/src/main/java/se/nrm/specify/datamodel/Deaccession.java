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
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
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
@Table(name = "deaccession")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deaccession.findAll", query = "SELECT d FROM Deaccession d"),
    @NamedQuery(name = "Deaccession.findByDeaccessionId", query = "SELECT d FROM Deaccession d WHERE d.deaccessionId = :deaccessionId"),
    @NamedQuery(name = "Deaccession.findByTimestampCreated", query = "SELECT d FROM Deaccession d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Deaccession.findByTimestampModified", query = "SELECT d FROM Deaccession d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Deaccession.findByVersion", query = "SELECT d FROM Deaccession d WHERE d.version = :version"),
    @NamedQuery(name = "Deaccession.findByDeaccessionDate", query = "SELECT d FROM Deaccession d WHERE d.deaccessionDate = :deaccessionDate"),
    @NamedQuery(name = "Deaccession.findByDeaccessionNumber", query = "SELECT d FROM Deaccession d WHERE d.deaccessionNumber = :deaccessionNumber"),
    @NamedQuery(name = "Deaccession.findByNumber1", query = "SELECT d FROM Deaccession d WHERE d.number1 = :number1"),
    @NamedQuery(name = "Deaccession.findByNumber2", query = "SELECT d FROM Deaccession d WHERE d.number2 = :number2"),
    @NamedQuery(name = "Deaccession.findByType", query = "SELECT d FROM Deaccession d WHERE d.type = :type"),
    @NamedQuery(name = "Deaccession.findByYesNo1", query = "SELECT d FROM Deaccession d WHERE d.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Deaccession.findByYesNo2", query = "SELECT d FROM Deaccession d WHERE d.yesNo2 = :yesNo2")})
public class Deaccession extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DeaccessionID")
    private Integer deaccessionId;
    
    @Size(max = 50)
    @Column(name = "DeaccessionNumber")
    private String deaccessionNumber;
    
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
    
    @Size(max = 64)
    @Column(name = "Type")
    private String type;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Column(name = "DeaccessionDate")
    @Temporal(TemporalType.DATE)
    private Date deaccessionDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaccession")
    private Collection<Deaccessionagent> deaccessionAgents;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne
    private Accession accession;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deaccession")
    private Collection<Deaccessionpreparation> deaccessionPreparations;

    public Deaccession() {
    }

    public Deaccession(Integer deaccessionId) {
        this.deaccessionId = deaccessionId;
    }

    public Deaccession(Integer deaccessionId, Date timestampCreated) {
        super(timestampCreated);
        this.deaccessionId = deaccessionId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (deaccessionId != null) ? deaccessionId.toString() : "0";
    }

    public String getDeaccessionNumber() {
        return deaccessionNumber;
    }

    public void setDeaccessionNumber(String deaccessionNumber) {
        this.deaccessionNumber = deaccessionNumber;
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
    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getDeaccessionId() {
        return deaccessionId;
    }

    public void setDeaccessionId(Integer deaccessionId) {
        this.deaccessionId = deaccessionId;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Collection<Deaccessionagent> getDeaccessionAgents() {
        return deaccessionAgents;
    }

    public void setDeaccessionAgents(Collection<Deaccessionagent> deaccessionAgents) {
        this.deaccessionAgents = deaccessionAgents;
    }

    public Collection<Deaccessionpreparation> getDeaccessionPreparations() {
        return deaccessionPreparations;
    }

    public void setDeaccessionPreparations(Collection<Deaccessionpreparation> deaccessionPreparations) {
        this.deaccessionPreparations = deaccessionPreparations;
    }
 
    public Date getDeaccessionDate() {
        return deaccessionDate;
    }

    public void setDeaccessionDate(Date deaccessionDate) {
        this.deaccessionDate = deaccessionDate;
    }
 
    
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Accession) {
            this.accession  = (Accession)parent;   
        }
    }
    
    
    @Override
    public String getEntityName() {
        return "deaccession";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deaccessionId != null ? deaccessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deaccession)) {
            return false;
        }
        Deaccession other = (Deaccession) object;
        if ((this.deaccessionId == null && other.deaccessionId != null) || (this.deaccessionId != null && !this.deaccessionId.equals(other.deaccessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Deaccession[ deaccessionID=" + deaccessionId + " ]";
    }

 
}

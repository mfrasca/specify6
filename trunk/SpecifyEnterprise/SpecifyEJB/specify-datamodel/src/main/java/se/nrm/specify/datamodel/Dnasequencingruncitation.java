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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "dnasequencingruncitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnasequencingruncitation.findAll", query = "SELECT d FROM Dnasequencingruncitation d"),
    @NamedQuery(name = "Dnasequencingruncitation.findByDNASequencingRunCitationID", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.dnaSequencingRunCitationId = :dNASequencingRunCitationID"),
    @NamedQuery(name = "Dnasequencingruncitation.findByTimestampCreated", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequencingruncitation.findByTimestampModified", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Dnasequencingruncitation.findByVersion", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.version = :version"),
    @NamedQuery(name = "Dnasequencingruncitation.findByNumber1", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.number1 = :number1"),
    @NamedQuery(name = "Dnasequencingruncitation.findByNumber2", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.number2 = :number2"),
    @NamedQuery(name = "Dnasequencingruncitation.findByYesNo1", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Dnasequencingruncitation.findByYesNo2", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.yesNo2 = :yesNo2")})
public class Dnasequencingruncitation extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DNASequencingRunCitationID")
    private Integer dnaSequencingRunCitationId; 
    
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
    
    @JoinColumn(name = "DNASequencingRunID", referencedColumnName = "DNASequencingRunID")
    @ManyToOne(optional = false)
    private Dnasequencingrun sequencingRun;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWork;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Dnasequencingruncitation() {
    }

    public Dnasequencingruncitation(Integer dnaSequencingRunCitationId) {
        this.dnaSequencingRunCitationId = dnaSequencingRunCitationId;
    }

    public Dnasequencingruncitation(Integer dnaSequencingRunCitationId, Date timestampCreated) {
        super(timestampCreated);
        this.dnaSequencingRunCitationId = dnaSequencingRunCitationId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (dnaSequencingRunCitationId != null) ? dnaSequencingRunCitationId.toString() : "0";
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getDnaSequencingRunCitationId() {
        return dnaSequencingRunCitationId;
    }

    public void setDnaSequencingRunCitationId(Integer dnaSequencingRunCitationId) {
        this.dnaSequencingRunCitationId = dnaSequencingRunCitationId;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @NotNull(message="ReferenceWork must be specified.")
    public Referencework getReferenceWork() {
        return referenceWork;
    }

    public void setReferenceWork(Referencework referenceWork) {
        this.referenceWork = referenceWork;
    }

    @NotNull(message="SequencingRun must be specified.")
    public Dnasequencingrun getSequencingRun() {
        return sequencingRun;
    }

    public void setSequencingRun(Dnasequencingrun sequencingRun) {
        this.sequencingRun = sequencingRun;
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

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnaSequencingRunCitationId != null ? dnaSequencingRunCitationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequencingruncitation)) {
            return false;
        }
        Dnasequencingruncitation other = (Dnasequencingruncitation) object;
        if ((this.dnaSequencingRunCitationId == null && other.dnaSequencingRunCitationId != null) || (this.dnaSequencingRunCitationId != null && !this.dnaSequencingRunCitationId.equals(other.dnaSequencingRunCitationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnasequencingruncitation[ dnaSequencingRunCitationId=" + dnaSequencingRunCitationId + " ]";
    }
 
}

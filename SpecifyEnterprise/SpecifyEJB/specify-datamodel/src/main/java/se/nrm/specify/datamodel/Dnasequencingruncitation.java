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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Dnasequencingruncitation.findByDNASequencingRunCitationID", query = "SELECT d FROM Dnasequencingruncitation d WHERE d.dNASequencingRunCitationID = :dNASequencingRunCitationID"),
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
    private Integer dNASequencingRunCitationID; 
    
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
    private Dnasequencingrun dNASequencingRunID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWorkID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Dnasequencingruncitation() {
    }

    public Dnasequencingruncitation(Integer dNASequencingRunCitationID) {
        this.dNASequencingRunCitationID = dNASequencingRunCitationID;
    }

    public Dnasequencingruncitation(Integer dNASequencingRunCitationID, Date timestampCreated) {
        super(timestampCreated);
        this.dNASequencingRunCitationID = dNASequencingRunCitationID; 
    }

    public Integer getDNASequencingRunCitationID() {
        return dNASequencingRunCitationID;
    }

    public void setDNASequencingRunCitationID(Integer dNASequencingRunCitationID) {
        this.dNASequencingRunCitationID = dNASequencingRunCitationID;
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

    public Dnasequencingrun getDNASequencingRunID() {
        return dNASequencingRunID;
    }

    public void setDNASequencingRunID(Dnasequencingrun dNASequencingRunID) {
        this.dNASequencingRunID = dNASequencingRunID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Referencework getReferenceWorkID() {
        return referenceWorkID;
    }

    public void setReferenceWorkID(Referencework referenceWorkID) {
        this.referenceWorkID = referenceWorkID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dNASequencingRunCitationID != null ? dNASequencingRunCitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequencingruncitation)) {
            return false;
        }
        Dnasequencingruncitation other = (Dnasequencingruncitation) object;
        if ((this.dNASequencingRunCitationID == null && other.dNASequencingRunCitationID != null) || (this.dNASequencingRunCitationID != null && !this.dNASequencingRunCitationID.equals(other.dNASequencingRunCitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnasequencingruncitation[ dNASequencingRunCitationID=" + dNASequencingRunCitationID + " ]";
    }
    
}

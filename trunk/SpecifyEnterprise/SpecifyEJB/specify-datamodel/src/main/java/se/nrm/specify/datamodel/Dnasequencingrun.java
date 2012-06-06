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
@Table(name = "dnasequencingrun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnasequencingrun.findAll", query = "SELECT d FROM Dnasequencingrun d"),
    @NamedQuery(name = "Dnasequencingrun.findByDNASequencingRunID", query = "SELECT d FROM Dnasequencingrun d WHERE d.dnaSequencingRunId = :dNASequencingRunID"),
    @NamedQuery(name = "Dnasequencingrun.findByTimestampCreated", query = "SELECT d FROM Dnasequencingrun d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequencingrun.findByTimestampModified", query = "SELECT d FROM Dnasequencingrun d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Dnasequencingrun.findByVersion", query = "SELECT d FROM Dnasequencingrun d WHERE d.version = :version"),
    @NamedQuery(name = "Dnasequencingrun.findByCollectionMemberID", query = "SELECT d FROM Dnasequencingrun d WHERE d.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Dnasequencingrun.findByNumber1", query = "SELECT d FROM Dnasequencingrun d WHERE d.number1 = :number1"),
    @NamedQuery(name = "Dnasequencingrun.findByNumber2", query = "SELECT d FROM Dnasequencingrun d WHERE d.number2 = :number2"),
    @NamedQuery(name = "Dnasequencingrun.findByNumber3", query = "SELECT d FROM Dnasequencingrun d WHERE d.number3 = :number3"),
    @NamedQuery(name = "Dnasequencingrun.findByOrdinal", query = "SELECT d FROM Dnasequencingrun d WHERE d.ordinal = :ordinal"),
    @NamedQuery(name = "Dnasequencingrun.findByPCRCocktailPrimer", query = "SELECT d FROM Dnasequencingrun d WHERE d.pcrCocktailPrimer = :pCRCocktailPrimer"),
    @NamedQuery(name = "Dnasequencingrun.findByPCRForwardPrimerCode", query = "SELECT d FROM Dnasequencingrun d WHERE d.pcrForwardPrimerCode = :pCRForwardPrimerCode"),
    @NamedQuery(name = "Dnasequencingrun.findByPCRPrimerName", query = "SELECT d FROM Dnasequencingrun d WHERE d.pcrPrimerName = :pCRPrimerName"),
    @NamedQuery(name = "Dnasequencingrun.findByPCRPrimerSequence53", query = "SELECT d FROM Dnasequencingrun d WHERE d.pcrPrimerSequence5_3 = :pCRPrimerSequence53"),
    @NamedQuery(name = "Dnasequencingrun.findByPCRReversePrimerCode", query = "SELECT d FROM Dnasequencingrun d WHERE d.pcrReversePrimerCode = :pCRReversePrimerCode"),
    @NamedQuery(name = "Dnasequencingrun.findByReadDirection", query = "SELECT d FROM Dnasequencingrun d WHERE d.readDirection = :readDirection"),
    @NamedQuery(name = "Dnasequencingrun.findByRunDate", query = "SELECT d FROM Dnasequencingrun d WHERE d.runDate = :runDate"),
    @NamedQuery(name = "Dnasequencingrun.findByScoreFileName", query = "SELECT d FROM Dnasequencingrun d WHERE d.scoreFileName = :scoreFileName"),
    @NamedQuery(name = "Dnasequencingrun.findBySequenceCocktailPrimer", query = "SELECT d FROM Dnasequencingrun d WHERE d.sequenceCocktailPrimer = :sequenceCocktailPrimer"),
    @NamedQuery(name = "Dnasequencingrun.findBySequencePrimerCode", query = "SELECT d FROM Dnasequencingrun d WHERE d.sequencePrimerCode = :sequencePrimerCode"),
    @NamedQuery(name = "Dnasequencingrun.findBySequencePrimerName", query = "SELECT d FROM Dnasequencingrun d WHERE d.sequencePrimerName = :sequencePrimerName"),
    @NamedQuery(name = "Dnasequencingrun.findBySequencePrimerSequence53", query = "SELECT d FROM Dnasequencingrun d WHERE d.sequencePrimerSequence5_3 = :sequencePrimerSequence53"),
    @NamedQuery(name = "Dnasequencingrun.findByText1", query = "SELECT d FROM Dnasequencingrun d WHERE d.text1 = :text1"),
    @NamedQuery(name = "Dnasequencingrun.findByText2", query = "SELECT d FROM Dnasequencingrun d WHERE d.text2 = :text2"),
    @NamedQuery(name = "Dnasequencingrun.findByText3", query = "SELECT d FROM Dnasequencingrun d WHERE d.text3 = :text3"),
    @NamedQuery(name = "Dnasequencingrun.findByTraceFileName", query = "SELECT d FROM Dnasequencingrun d WHERE d.traceFileName = :traceFileName"),
    @NamedQuery(name = "Dnasequencingrun.findByYesNo1", query = "SELECT d FROM Dnasequencingrun d WHERE d.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Dnasequencingrun.findByYesNo2", query = "SELECT d FROM Dnasequencingrun d WHERE d.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Dnasequencingrun.findByYesNo3", query = "SELECT d FROM Dnasequencingrun d WHERE d.yesNo3 = :yesNo3")})
public class Dnasequencingrun extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DNASequencingRunID")
    private Integer dnaSequencingRunId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Column(name = "Number3")
    private Float number3;
    
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Column(name = "PCRCocktailPrimer")
    private Boolean pcrCocktailPrimer;
    
    @Size(max = 32)
    @Column(name = "PCRForwardPrimerCode")
    private String pcrForwardPrimerCode;
    
    @Size(max = 32)
    @Column(name = "PCRPrimerName")
    private String pcrPrimerName;
    
    @Size(max = 64)
    @Column(name = "PCRPrimerSequence5_3")
    private String pcrPrimerSequence5_3;
    
    @Size(max = 32) 
    @Column(name = "PCRReversePrimerCode")
    private String pcrReversePrimerCode;
    
    @Size(max = 16)
    @Column(name = "ReadDirection")
    private String readDirection;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "RunDate")
    @Temporal(TemporalType.DATE)
    private Date runDate;
    
    @Size(max = 32)
    @Column(name = "ScoreFileName")
    private String scoreFileName;
    
    @Column(name = "SequenceCocktailPrimer")
    private Boolean sequenceCocktailPrimer;
    
    @Size(max = 32)
    @Column(name = "SequencePrimerCode")
    private String sequencePrimerCode;
    
    @Size(max = 32)
    @Column(name = "SequencePrimerName")
    private String sequencePrimerName;
    
    @Size(max = 64)
    @Column(name = "SequencePrimerSequence5_3")
    private String sequencePrimerSequence5_3;
    
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 64)
    @Column(name = "Text3")
    private String text3;
    
    @Size(max = 32)
    @Column(name = "TraceFileName")
    private String traceFileName;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Column(name = "YesNo3")
    private Boolean yesNo3;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dnaSequencingRun")
    private Collection<Dnasequenceattachment> attachments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sequencingRun")
    private Collection<Dnasequencingruncitation> citations;
    
    @JoinColumn(name = "RunByAgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent runByAgent;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DNASequenceID", referencedColumnName = "DnaSequenceID")
    @ManyToOne(optional = false)
    private Dnasequence dnaSequence;
    
    @JoinColumn(name = "PreparedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent preparedByAgent;

    public Dnasequencingrun() {
    }

    public Dnasequencingrun(Integer dnaSequencingRunId) {
        this.dnaSequencingRunId = dnaSequencingRunId;
    }

    public Dnasequencingrun(Integer dnaSequencingRunId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.dnaSequencingRunId = dnaSequencingRunId; 
        this.collectionMemberId = collectionMemberId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

 
    public Integer getDnaSequencingRunId() {
        return dnaSequencingRunId;
    }

    public void setDnaSequencingRunId(Integer dnaSequencingRunId) {
        this.dnaSequencingRunId = dnaSequencingRunId;
    }

    public Boolean getPcrCocktailPrimer() {
        return pcrCocktailPrimer;
    }

    public void setPcrCocktailPrimer(Boolean pcrCocktailPrimer) {
        this.pcrCocktailPrimer = pcrCocktailPrimer;
    }

    public String getPcrForwardPrimerCode() {
        return pcrForwardPrimerCode;
    }

    public void setPcrForwardPrimerCode(String pcrForwardPrimerCode) {
        this.pcrForwardPrimerCode = pcrForwardPrimerCode;
    }

    public String getPcrPrimerName() {
        return pcrPrimerName;
    }

    public void setPcrPrimerName(String pcrPrimerName) {
        this.pcrPrimerName = pcrPrimerName;
    }

    public String getPcrPrimerSequence5_3() {
        return pcrPrimerSequence5_3;
    }

    public void setPcrPrimerSequence5_3(String pcrPrimerSequence5_3) {
        this.pcrPrimerSequence5_3 = pcrPrimerSequence5_3;
    }

    public String getPcrReversePrimerCode() {
        return pcrReversePrimerCode;
    }

    public void setPcrReversePrimerCode(String pcrReversePrimerCode) {
        this.pcrReversePrimerCode = pcrReversePrimerCode;
    }

    public String getSequencePrimerSequence5_3() {
        return sequencePrimerSequence5_3;
    }

    public void setSequencePrimerSequence5_3(String sequencePrimerSequence5_3) {
        this.sequencePrimerSequence5_3 = sequencePrimerSequence5_3;
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

    public Float getNumber3() {
        return number3;
    }

    public void setNumber3(Float number3) {
        this.number3 = number3;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    
 

    public String getReadDirection() {
        return readDirection;
    }

    public void setReadDirection(String readDirection) {
        this.readDirection = readDirection;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public String getScoreFileName() {
        return scoreFileName;
    }

    public void setScoreFileName(String scoreFileName) {
        this.scoreFileName = scoreFileName;
    }

    public Boolean getSequenceCocktailPrimer() {
        return sequenceCocktailPrimer;
    }

    public void setSequenceCocktailPrimer(Boolean sequenceCocktailPrimer) {
        this.sequenceCocktailPrimer = sequenceCocktailPrimer;
    }

    public String getSequencePrimerCode() {
        return sequencePrimerCode;
    }

    public void setSequencePrimerCode(String sequencePrimerCode) {
        this.sequencePrimerCode = sequencePrimerCode;
    }

    public String getSequencePrimerName() {
        return sequencePrimerName;
    }

    public void setSequencePrimerName(String sequencePrimerName) {
        this.sequencePrimerName = sequencePrimerName;
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

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getTraceFileName() {
        return traceFileName;
    }

    public void setTraceFileName(String traceFileName) {
        this.traceFileName = traceFileName;
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

    @XmlTransient
    public Collection<Dnasequenceattachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Dnasequenceattachment> attachments) {
        this.attachments = attachments;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getCitations() {
        return citations;
    }

    public void setCitations(Collection<Dnasequencingruncitation> citations) {
        this.citations = citations;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Dnasequence getDnaSequence() {
        return dnaSequence;
    }

    public void setDnaSequence(Dnasequence dnaSequence) {
        this.dnaSequence = dnaSequence;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Agent getPreparedByAgent() {
        return preparedByAgent;
    }

    public void setPreparedByAgent(Agent preparedByAgent) {
        this.preparedByAgent = preparedByAgent;
    }

    public Agent getRunByAgent() {
        return runByAgent;
    }

    public void setRunByAgent(Agent runByAgent) {
        this.runByAgent = runByAgent;
    }

    
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnaSequencingRunId != null ? dnaSequencingRunId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequencingrun)) {
            return false;
        }
        Dnasequencingrun other = (Dnasequencingrun) object;
        if ((this.dnaSequencingRunId == null && other.dnaSequencingRunId != null) || (this.dnaSequencingRunId != null && !this.dnaSequencingRunId.equals(other.dnaSequencingRunId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnasequencingrun[ dNASequencingRunID=" + dnaSequencingRunId + " ]";
    }
    
}

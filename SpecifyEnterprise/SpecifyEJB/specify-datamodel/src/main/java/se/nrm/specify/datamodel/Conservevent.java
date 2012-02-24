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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "conservevent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conservevent.findAll", query = "SELECT c FROM Conservevent c"),
    @NamedQuery(name = "Conservevent.findByConservEventID", query = "SELECT c FROM Conservevent c WHERE c.conservEventID = :conservEventID"),
    @NamedQuery(name = "Conservevent.findByTimestampCreated", query = "SELECT c FROM Conservevent c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Conservevent.findByTimestampModified", query = "SELECT c FROM Conservevent c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Conservevent.findByVersion", query = "SELECT c FROM Conservevent c WHERE c.version = :version"),
    @NamedQuery(name = "Conservevent.findByCompletedDate", query = "SELECT c FROM Conservevent c WHERE c.completedDate = :completedDate"),
    @NamedQuery(name = "Conservevent.findByCuratorApprovalDate", query = "SELECT c FROM Conservevent c WHERE c.curatorApprovalDate = :curatorApprovalDate"),
    @NamedQuery(name = "Conservevent.findByExamDate", query = "SELECT c FROM Conservevent c WHERE c.examDate = :examDate"),
    @NamedQuery(name = "Conservevent.findByText1", query = "SELECT c FROM Conservevent c WHERE c.text1 = :text1"),
    @NamedQuery(name = "Conservevent.findByText2", query = "SELECT c FROM Conservevent c WHERE c.text2 = :text2"),
    @NamedQuery(name = "Conservevent.findByNumber1", query = "SELECT c FROM Conservevent c WHERE c.number1 = :number1"),
    @NamedQuery(name = "Conservevent.findByNumber2", query = "SELECT c FROM Conservevent c WHERE c.number2 = :number2"),
    @NamedQuery(name = "Conservevent.findByYesNo1", query = "SELECT c FROM Conservevent c WHERE c.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Conservevent.findByYesNo2", query = "SELECT c FROM Conservevent c WHERE c.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Conservevent.findByTreatmentCompDate", query = "SELECT c FROM Conservevent c WHERE c.treatmentCompDate = :treatmentCompDate")})
public class Conservevent extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ConservEventID")
    private Integer conservEventID;
      
    @Lob
    @Size(max = 65535)
    @Column(name = "AdvTestingExam")
    private String advTestingExam;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "AdvTestingExamResults")
    private String advTestingExamResults;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "CompletedComments")
    private String completedComments;
    
    @Column(name = "CompletedDate")
    @Temporal(TemporalType.DATE)
    private Date completedDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "ConditionReport")
    private String conditionReport;
    
    @Column(name = "CuratorApprovalDate")
    @Temporal(TemporalType.DATE)
    private Date curatorApprovalDate;
    
    @Column(name = "ExamDate")
    @Temporal(TemporalType.DATE)
    private Date examDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "PhotoDocs")
    private String photoDocs;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 64)
    @Column(name = "Text2")
    private String text2;
    
    @Column(name = "Number1")
    private Integer number1;
    
    @Column(name = "Number2")
    private Integer number2;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Column(name = "TreatmentCompDate")
    @Temporal(TemporalType.DATE)
    private Date treatmentCompDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "TreatmentReport")
    private String treatmentReport;
    
    @JoinColumn(name = "ConservDescriptionID", referencedColumnName = "ConservDescriptionID")
    @ManyToOne(optional = false)
    private Conservdescription conservDescriptionID;
    
    @JoinColumn(name = "CuratorID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent curatorID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "TreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent treatedByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "ExaminedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent examinedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conservEventID")
    private Collection<Conserveventattachment> conserveventattachmentCollection;

    public Conservevent() {
    }

    public Conservevent(Integer conservEventID) {
        this.conservEventID = conservEventID;
    }

    public Conservevent(Integer conservEventID, Date timestampCreated) {
        super(timestampCreated);
        this.conservEventID = conservEventID; 
    }

    public Integer getConservEventID() {
        return conservEventID;
    }

    public void setConservEventID(Integer conservEventID) {
        this.conservEventID = conservEventID;
    } 
    
    public String getAdvTestingExam() {
        return advTestingExam;
    }

    public void setAdvTestingExam(String advTestingExam) {
        this.advTestingExam = advTestingExam;
    }

    public String getAdvTestingExamResults() {
        return advTestingExamResults;
    }

    public void setAdvTestingExamResults(String advTestingExamResults) {
        this.advTestingExamResults = advTestingExamResults;
    }

    public String getCompletedComments() {
        return completedComments;
    }

    public void setCompletedComments(String completedComments) {
        this.completedComments = completedComments;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String getConditionReport() {
        return conditionReport;
    }

    public void setConditionReport(String conditionReport) {
        this.conditionReport = conditionReport;
    }

    public Date getCuratorApprovalDate() {
        return curatorApprovalDate;
    }

    public void setCuratorApprovalDate(Date curatorApprovalDate) {
        this.curatorApprovalDate = curatorApprovalDate;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public String getPhotoDocs() {
        return photoDocs;
    }

    public void setPhotoDocs(String photoDocs) {
        this.photoDocs = photoDocs;
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

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
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

    public Date getTreatmentCompDate() {
        return treatmentCompDate;
    }

    public void setTreatmentCompDate(Date treatmentCompDate) {
        this.treatmentCompDate = treatmentCompDate;
    }

    public String getTreatmentReport() {
        return treatmentReport;
    }

    public void setTreatmentReport(String treatmentReport) {
        this.treatmentReport = treatmentReport;
    }

    public Conservdescription getConservDescriptionID() {
        return conservDescriptionID;
    }

    public void setConservDescriptionID(Conservdescription conservDescriptionID) {
        this.conservDescriptionID = conservDescriptionID;
    }

    public Agent getCuratorID() {
        return curatorID;
    }

    public void setCuratorID(Agent curatorID) {
        this.curatorID = curatorID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Agent getTreatedByAgentID() {
        return treatedByAgentID;
    }

    public void setTreatedByAgentID(Agent treatedByAgentID) {
        this.treatedByAgentID = treatedByAgentID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Agent getExaminedByAgentID() {
        return examinedByAgentID;
    }

    public void setExaminedByAgentID(Agent examinedByAgentID) {
        this.examinedByAgentID = examinedByAgentID;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConserveventattachmentCollection() {
        return conserveventattachmentCollection;
    }

    public void setConserveventattachmentCollection(Collection<Conserveventattachment> conserveventattachmentCollection) {
        this.conserveventattachmentCollection = conserveventattachmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conservEventID != null ? conservEventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conservevent)) {
            return false;
        }
        Conservevent other = (Conservevent) object;
        if ((this.conservEventID == null && other.conservEventID != null) || (this.conservEventID != null && !this.conservEventID.equals(other.conservEventID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conservevent[ conservEventID=" + conservEventID + " ]";
    }
    
}
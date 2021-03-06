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
@Table(name = "dnasequence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnasequence.findAll", query = "SELECT d FROM Dnasequence d"),
    @NamedQuery(name = "Dnasequence.findByDnaSequenceId", query = "SELECT d FROM Dnasequence d WHERE d.dnaSequenceId = :dnaSequenceId"),
    @NamedQuery(name = "Dnasequence.findByTimestampCreated", query = "SELECT d FROM Dnasequence d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequence.findByTimestampModified", query = "SELECT d FROM Dnasequence d WHERE d.timestampModified = :timestampModified"),
//    @NamedQuery(name = "Dnasequence.findByVersion", query = "SELECT d FROM Dnasequence d WHERE d.version = :version"), 
    @NamedQuery(name = "Dnasequence.findByCollectionMemberId", query = "SELECT d FROM Dnasequence d WHERE d.collectionMemberId = :collectionMemberId"),
    @NamedQuery(name = "Dnasequence.findByAmbiguousResidues", query = "SELECT d FROM Dnasequence d WHERE d.ambiguousResidues = :ambiguousResidues"),
    @NamedQuery(name = "Dnasequence.findByBOLDBarcodeID", query = "SELECT d FROM Dnasequence d WHERE d.boldBarcodeId = :bOLDBarcodeID"),
    @NamedQuery(name = "Dnasequence.findByBOLDLastUpdateDate", query = "SELECT d FROM Dnasequence d WHERE d.boldLastUpdateDate = :bOLDLastUpdateDate"),
    @NamedQuery(name = "Dnasequence.findByBOLDSampleID", query = "SELECT d FROM Dnasequence d WHERE d.boldSampleId = :bOLDSampleID"),
    @NamedQuery(name = "Dnasequence.findByBOLDTranslationMatrix", query = "SELECT d FROM Dnasequence d WHERE d.boldTranslationMatrix = :bOLDTranslationMatrix"),
    @NamedQuery(name = "Dnasequence.findByCompA", query = "SELECT d FROM Dnasequence d WHERE d.compA = :compA"),
    @NamedQuery(name = "Dnasequence.findByCompC", query = "SELECT d FROM Dnasequence d WHERE d.compC = :compC"),
    @NamedQuery(name = "Dnasequence.findByCompG", query = "SELECT d FROM Dnasequence d WHERE d.compG = :compG"),
    @NamedQuery(name = "Dnasequence.findByCompT", query = "SELECT d FROM Dnasequence d WHERE d.compT = :compT"),
    @NamedQuery(name = "Dnasequence.findByGenBankAccessionNumber", query = "SELECT d FROM Dnasequence d WHERE d.genbankAccessionNumber = :genBankAccessionNumber"),
    @NamedQuery(name = "Dnasequence.findByMoleculeType", query = "SELECT d FROM Dnasequence d WHERE d.moleculeType = :moleculeType"),
    @NamedQuery(name = "Dnasequence.findByNumber1", query = "SELECT d FROM Dnasequence d WHERE d.number1 = :number1"),
    @NamedQuery(name = "Dnasequence.findByNumber2", query = "SELECT d FROM Dnasequence d WHERE d.number2 = :number2"),
    @NamedQuery(name = "Dnasequence.findByNumber3", query = "SELECT d FROM Dnasequence d WHERE d.number3 = :number3"),
    @NamedQuery(name = "Dnasequence.findByTargetMarker", query = "SELECT d FROM Dnasequence d WHERE d.targetMarker = :targetMarker"),
    @NamedQuery(name = "Dnasequence.findByNumber3", query = "SELECT d FROM Dnasequence d WHERE d.number3 = :number3"),
    @NamedQuery(name = "Dnasequence.findByTargetMarkerAndCatalogNumber", query = "SELECT d FROM Dnasequence d , Determination dt WHERE dt.collectionObject = d.collectionObject AND dt.isCurrent=true AND d.targetMarker = :targetMarker AND d.collectionObject.catalogNumber IN :catalogNumber"),
    @NamedQuery(name = "Dnasequence.findByText1", query = "SELECT d FROM Dnasequence d WHERE d.text1 = :text1"),
    @NamedQuery(name = "Dnasequence.findByText2", query = "SELECT d FROM Dnasequence d WHERE d.text2 = :text2"),
    @NamedQuery(name = "Dnasequence.findByText3", query = "SELECT d FROM Dnasequence d WHERE d.text3 = :text3"),
    @NamedQuery(name = "Dnasequence.findByTotalResidues", query = "SELECT d FROM Dnasequence d WHERE d.totalResidues = :totalResidues"),
    @NamedQuery(name = "Dnasequence.findByYesNo1", query = "SELECT d FROM Dnasequence d WHERE d.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Dnasequence.findByYesNo2", query = "SELECT d FROM Dnasequence d WHERE d.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Dnasequence.findByYesNo3", query = "SELECT d FROM Dnasequence d WHERE d.yesNo3 = :yesNo3")})
public class Dnasequence extends BaseEntity {
 
     
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DnaSequenceID")
    private Integer dnaSequenceId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Column(name = "AmbiguousResidues")
    private Integer ambiguousResidues;
    
    @Column(name = "BOLDLastUpdateDate")
    @Temporal(TemporalType.DATE)
    private Date boldLastUpdateDate;
    
    @Size(max = 32)
    @Column(name = "BOLDBarcodeID")
    private String boldBarcodeId;
    
    @Size(max = 32)
    @Column(name = "BOLDSampleID")
    private String boldSampleId;
    
    @Size(max = 64)
    @Column(name = "BOLDTranslationMatrix")
    private String boldTranslationMatrix;
    
    @Column(name = "CompA")
    private Integer compA;
    
    @Column(name = "CompC")
    private Integer compC;
    
    @Column(name = "CompG")
    private Integer compG;
    
    @Column(name = "compT")
    private Integer compT;
    
    @Size(max = 32)
    @Column(name = "GenBankAccessionNumber")
    private String genbankAccessionNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "GeneSequence")
    private String geneSequence;
    
    @Size(max = 32)
    @Column(name = "MoleculeType")
    private String moleculeType;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Column(name = "Number3")
    private Float number3;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "TargetMarker")
    private String targetMarker;
    
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 64)
    @Column(name = "Text3")
    private String text3;
    
    @Column(name = "TotalResidues")
    private Integer totalResidues;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Column(name = "YesNo3")
    private Boolean yesNo3;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent sequencer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dnaSequence")
    private Collection<Dnasequencingrun> dnaSequencingRuns;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dnasequence")
    private Collection<Dnasequenceattachment> dnasequenceattachments;

    public Dnasequence() {
    }

    public Dnasequence(Integer dnaSequenceId) {
        this.dnaSequenceId = dnaSequenceId;
    }

    public Dnasequence(Integer dnaSequenceId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.dnaSequenceId = dnaSequenceId;
        this.collectionMemberId = collectionMemberId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (dnaSequenceId != null) ? dnaSequenceId.toString() : "0";
    }
    public String getBoldBarcodeId() {
        return boldBarcodeId;
    }

    public void setBoldBarcodeId(String boldBarcodeId) {
        this.boldBarcodeId = boldBarcodeId;
    }

    public String getBoldSampleId() {
        return boldSampleId;
    }

    public void setBoldSampleId(String boldSampleId) {
        this.boldSampleId = boldSampleId;
    }

    public String getBoldTranslationMatrix() {
        return boldTranslationMatrix;
    }

    public void setBoldTranslationMatrix(String boldTranslationMatrix) {
        this.boldTranslationMatrix = boldTranslationMatrix;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Integer getDnaSequenceId() {
        return dnaSequenceId;
    }

    public void setDnaSequenceId(Integer dnaSequenceId) {
        this.dnaSequenceId = dnaSequenceId;
    }

    public String getGenbankAccessionNumber() {
        return genbankAccessionNumber;
    }

    public void setGenbankAccessionNumber(String genbankAccessionNumber) {
        this.genbankAccessionNumber = genbankAccessionNumber;
    }

    public Date getBoldLastUpdateDate() {
        return boldLastUpdateDate;
    }

    public void setBoldLastUpdateDate(Date boldLastUpdateDate) {
        this.boldLastUpdateDate = boldLastUpdateDate;
    }

    public Collection<Dnasequenceattachment> getDnasequenceattachments() {
        return dnasequenceattachments;
    }

    public void setDnasequenceattachments(Collection<Dnasequenceattachment> dnasequenceattachments) {
        this.dnasequenceattachments = dnasequenceattachments;
    }
    
    

    public Integer getAmbiguousResidues() {
        return ambiguousResidues;
    }

    public void setAmbiguousResidues(Integer ambiguousResidues) {
        this.ambiguousResidues = ambiguousResidues;
    }

    public Integer getCompA() {
        return compA;
    }

    public void setCompA(Integer compA) {
        this.compA = compA;
    }

    public Integer getCompC() {
        return compC;
    }

    public void setCompC(Integer compC) {
        this.compC = compC;
    }

    public Integer getCompG() {
        return compG;
    }

    public void setCompG(Integer compG) {
        this.compG = compG;
    }

    public Integer getCompT() {
        return compT;
    }

    public void setCompT(Integer compT) {
        this.compT = compT;
    }

    public String getGeneSequence() {
        return geneSequence;
    }

    public void setGeneSequence(String geneSequence) {
        this.geneSequence = geneSequence;
    }

    public String getMoleculeType() {
        return moleculeType;
    }

    public void setMoleculeType(String moleculeType) {
        this.moleculeType = moleculeType;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTargetMarker() {
        return targetMarker;
    }

    public void setTargetMarker(String targetMarker) {
        this.targetMarker = targetMarker;
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

    public Integer getTotalResidues() {
        return totalResidues;
    }

    public void setTotalResidues(Integer totalResidues) {
        this.totalResidues = totalResidues;
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

    @XmlTransient
    public Collection<Dnasequencingrun> getDnaSequencingRuns() {
        return dnaSequencingRuns;
    }

    public void setDnaSequencingRuns(Collection<Dnasequencingrun> dnaSequencingRuns) {
        this.dnaSequencingRuns = dnaSequencingRuns;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Agent getSequencer() {
        return sequencer;
    }

    public void setSequencer(Agent sequencer) {
        this.sequencer = sequencer;
    }

    @Override
    public String getEntityName() {
        return "dnaSequence";
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnaSequenceId != null ? dnaSequenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequence)) {
            return false;
        }
        Dnasequence other = (Dnasequence) object;
        if ((this.dnaSequenceId == null && other.dnaSequenceId != null) || (this.dnaSequenceId != null && !this.dnaSequenceId.equals(other.dnaSequenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnasequence[ dnaSequenceId=" + dnaSequenceId + " ]";
    }
}

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
@Table(name = "dnasequence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnasequence.findAll", query = "SELECT d FROM Dnasequence d"),
    @NamedQuery(name = "Dnasequence.findByDnaSequenceID", query = "SELECT d FROM Dnasequence d WHERE d.dnaSequenceID = :dnaSequenceID"),
    @NamedQuery(name = "Dnasequence.findByTimestampCreated", query = "SELECT d FROM Dnasequence d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequence.findByTimestampModified", query = "SELECT d FROM Dnasequence d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Dnasequence.findByVersion", query = "SELECT d FROM Dnasequence d WHERE d.version = :version"), 
    @NamedQuery(name = "Dnasequence.findByCollectionMemberID", query = "SELECT d FROM Dnasequence d WHERE d.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Dnasequence.findByAmbiguousResidues", query = "SELECT d FROM Dnasequence d WHERE d.ambiguousResidues = :ambiguousResidues"),
    @NamedQuery(name = "Dnasequence.findByBOLDBarcodeID", query = "SELECT d FROM Dnasequence d WHERE d.bOLDBarcodeID = :bOLDBarcodeID"),
    @NamedQuery(name = "Dnasequence.findByBOLDLastUpdateDate", query = "SELECT d FROM Dnasequence d WHERE d.bOLDLastUpdateDate = :bOLDLastUpdateDate"),
    @NamedQuery(name = "Dnasequence.findByBOLDSampleID", query = "SELECT d FROM Dnasequence d WHERE d.bOLDSampleID = :bOLDSampleID"),
    @NamedQuery(name = "Dnasequence.findByBOLDTranslationMatrix", query = "SELECT d FROM Dnasequence d WHERE d.bOLDTranslationMatrix = :bOLDTranslationMatrix"),
    @NamedQuery(name = "Dnasequence.findByCompA", query = "SELECT d FROM Dnasequence d WHERE d.compA = :compA"),
    @NamedQuery(name = "Dnasequence.findByCompC", query = "SELECT d FROM Dnasequence d WHERE d.compC = :compC"),
    @NamedQuery(name = "Dnasequence.findByCompG", query = "SELECT d FROM Dnasequence d WHERE d.compG = :compG"),
    @NamedQuery(name = "Dnasequence.findByCompT", query = "SELECT d FROM Dnasequence d WHERE d.compT = :compT"),
    @NamedQuery(name = "Dnasequence.findByGenBankAccessionNumber", query = "SELECT d FROM Dnasequence d WHERE d.genBankAccessionNumber = :genBankAccessionNumber"),
    @NamedQuery(name = "Dnasequence.findByMoleculeType", query = "SELECT d FROM Dnasequence d WHERE d.moleculeType = :moleculeType"),
    @NamedQuery(name = "Dnasequence.findByNumber1", query = "SELECT d FROM Dnasequence d WHERE d.number1 = :number1"),
    @NamedQuery(name = "Dnasequence.findByNumber2", query = "SELECT d FROM Dnasequence d WHERE d.number2 = :number2"),
    @NamedQuery(name = "Dnasequence.findByNumber3", query = "SELECT d FROM Dnasequence d WHERE d.number3 = :number3"),
    @NamedQuery(name = "Dnasequence.findByTargetMarker", query = "SELECT d FROM Dnasequence d WHERE d.targetMarker = :targetMarker"),
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
    private Integer dnaSequenceID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Column(name = "AmbiguousResidues")
    private Integer ambiguousResidues;
    
    @Size(max = 32)
    @Column(name = "BOLDBarcodeID")
    private String bOLDBarcodeID;
    
    @Column(name = "BOLDLastUpdateDate")
    @Temporal(TemporalType.DATE)
    private Date bOLDLastUpdateDate;
    
    @Size(max = 32)
    @Column(name = "BOLDSampleID")
    private String bOLDSampleID;
    
    @Size(max = 64)
    @Column(name = "BOLDTranslationMatrix")
    private String bOLDTranslationMatrix;
    
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
    private String genBankAccessionNumber;
    
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
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent agentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dNASequenceID")
    private Collection<Dnasequencingrun> dnasequencingrunCollection;

    public Dnasequence() {
    }

    public Dnasequence(Integer dnaSequenceID) {
        this.dnaSequenceID = dnaSequenceID; 
    }

    public Dnasequence(Integer dnaSequenceID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.dnaSequenceID = dnaSequenceID; 
        this.collectionMemberID = collectionMemberID; 
    }

    public Integer getDnaSequenceID() {
        return dnaSequenceID;
    }

    public void setDnaSequenceID(Integer dnaSequenceID) {
        this.dnaSequenceID = dnaSequenceID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getAmbiguousResidues() {
        return ambiguousResidues;
    }

    public void setAmbiguousResidues(Integer ambiguousResidues) {
        this.ambiguousResidues = ambiguousResidues;
    }

    public String getBOLDBarcodeID() {
        return bOLDBarcodeID;
    }

    public void setBOLDBarcodeID(String bOLDBarcodeID) {
        this.bOLDBarcodeID = bOLDBarcodeID;
    }

    public Date getBOLDLastUpdateDate() {
        return bOLDLastUpdateDate;
    }

    public void setBOLDLastUpdateDate(Date bOLDLastUpdateDate) {
        this.bOLDLastUpdateDate = bOLDLastUpdateDate;
    }

    public String getBOLDSampleID() {
        return bOLDSampleID;
    }

    public void setBOLDSampleID(String bOLDSampleID) {
        this.bOLDSampleID = bOLDSampleID;
    }

    public String getBOLDTranslationMatrix() {
        return bOLDTranslationMatrix;
    }

    public void setBOLDTranslationMatrix(String bOLDTranslationMatrix) {
        this.bOLDTranslationMatrix = bOLDTranslationMatrix;
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

    public String getGenBankAccessionNumber() {
        return genBankAccessionNumber;
    }

    public void setGenBankAccessionNumber(String genBankAccessionNumber) {
        this.genBankAccessionNumber = genBankAccessionNumber;
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

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Collectionobject getCollectionObjectID() {
        return collectionObjectID;
    }

    public void setCollectionObjectID(Collectionobject collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingrunCollection() {
        return dnasequencingrunCollection;
    }

    public void setDnasequencingrunCollection(Collection<Dnasequencingrun> dnasequencingrunCollection) {
        this.dnasequencingrunCollection = dnasequencingrunCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnaSequenceID != null ? dnaSequenceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequence)) {
            return false;
        }
        Dnasequence other = (Dnasequence) object;
        if ((this.dnaSequenceID == null && other.dnaSequenceID != null) || (this.dnaSequenceID != null && !this.dnaSequenceID.equals(other.dnaSequenceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnasequence[ dnaSequenceID=" + dnaSequenceID + " ]";
    }
    
}

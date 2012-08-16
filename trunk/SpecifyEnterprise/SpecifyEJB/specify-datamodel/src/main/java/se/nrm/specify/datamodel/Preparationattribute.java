package se.nrm.specify.datamodel;
 
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
@Table(name = "preparationattribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preparationattribute.findAll", query = "SELECT p FROM Preparationattribute p"),
    @NamedQuery(name = "Preparationattribute.findByPreparationAttributeID", query = "SELECT p FROM Preparationattribute p WHERE p.preparationAttributeId = :preparationAttributeID"),
    @NamedQuery(name = "Preparationattribute.findByTimestampCreated", query = "SELECT p FROM Preparationattribute p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Preparationattribute.findByTimestampModified", query = "SELECT p FROM Preparationattribute p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Preparationattribute.findByVersion", query = "SELECT p FROM Preparationattribute p WHERE p.version = :version"),
    @NamedQuery(name = "Preparationattribute.findByCollectionMemberID", query = "SELECT p FROM Preparationattribute p WHERE p.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Preparationattribute.findByAttrDate", query = "SELECT p FROM Preparationattribute p WHERE p.attrDate = :attrDate"),
    @NamedQuery(name = "Preparationattribute.findByNumber1", query = "SELECT p FROM Preparationattribute p WHERE p.number1 = :number1"),
    @NamedQuery(name = "Preparationattribute.findByNumber2", query = "SELECT p FROM Preparationattribute p WHERE p.number2 = :number2"),
    @NamedQuery(name = "Preparationattribute.findByNumber3", query = "SELECT p FROM Preparationattribute p WHERE p.number3 = :number3"),
    @NamedQuery(name = "Preparationattribute.findByNumber4", query = "SELECT p FROM Preparationattribute p WHERE p.number4 = :number4"),
    @NamedQuery(name = "Preparationattribute.findByNumber5", query = "SELECT p FROM Preparationattribute p WHERE p.number5 = :number5"),
    @NamedQuery(name = "Preparationattribute.findByNumber6", query = "SELECT p FROM Preparationattribute p WHERE p.number6 = :number6"),
    @NamedQuery(name = "Preparationattribute.findByNumber7", query = "SELECT p FROM Preparationattribute p WHERE p.number7 = :number7"),
    @NamedQuery(name = "Preparationattribute.findByNumber8", query = "SELECT p FROM Preparationattribute p WHERE p.number8 = :number8"),
    @NamedQuery(name = "Preparationattribute.findByNumber9", query = "SELECT p FROM Preparationattribute p WHERE p.number9 = :number9"),
    @NamedQuery(name = "Preparationattribute.findByText11", query = "SELECT p FROM Preparationattribute p WHERE p.text11 = :text11"),
    @NamedQuery(name = "Preparationattribute.findByText12", query = "SELECT p FROM Preparationattribute p WHERE p.text12 = :text12"),
    @NamedQuery(name = "Preparationattribute.findByText13", query = "SELECT p FROM Preparationattribute p WHERE p.text13 = :text13"),
    @NamedQuery(name = "Preparationattribute.findByText14", query = "SELECT p FROM Preparationattribute p WHERE p.text14 = :text14"),
    @NamedQuery(name = "Preparationattribute.findByText15", query = "SELECT p FROM Preparationattribute p WHERE p.text15 = :text15"),
    @NamedQuery(name = "Preparationattribute.findByText16", query = "SELECT p FROM Preparationattribute p WHERE p.text16 = :text16"),
    @NamedQuery(name = "Preparationattribute.findByText17", query = "SELECT p FROM Preparationattribute p WHERE p.text17 = :text17"),
    @NamedQuery(name = "Preparationattribute.findByText18", query = "SELECT p FROM Preparationattribute p WHERE p.text18 = :text18"),
    @NamedQuery(name = "Preparationattribute.findByText19", query = "SELECT p FROM Preparationattribute p WHERE p.text19 = :text19"),
    @NamedQuery(name = "Preparationattribute.findByText20", query = "SELECT p FROM Preparationattribute p WHERE p.text20 = :text20"),
    @NamedQuery(name = "Preparationattribute.findByText21", query = "SELECT p FROM Preparationattribute p WHERE p.text21 = :text21"),
    @NamedQuery(name = "Preparationattribute.findByText22", query = "SELECT p FROM Preparationattribute p WHERE p.text22 = :text22"),
    @NamedQuery(name = "Preparationattribute.findByText23", query = "SELECT p FROM Preparationattribute p WHERE p.text23 = :text23"),
    @NamedQuery(name = "Preparationattribute.findByText24", query = "SELECT p FROM Preparationattribute p WHERE p.text24 = :text24"),
    @NamedQuery(name = "Preparationattribute.findByText25", query = "SELECT p FROM Preparationattribute p WHERE p.text25 = :text25"),
    @NamedQuery(name = "Preparationattribute.findByText26", query = "SELECT p FROM Preparationattribute p WHERE p.text26 = :text26"),
    @NamedQuery(name = "Preparationattribute.findByText3", query = "SELECT p FROM Preparationattribute p WHERE p.text3 = :text3"),
    @NamedQuery(name = "Preparationattribute.findByText4", query = "SELECT p FROM Preparationattribute p WHERE p.text4 = :text4"),
    @NamedQuery(name = "Preparationattribute.findByText5", query = "SELECT p FROM Preparationattribute p WHERE p.text5 = :text5"),
    @NamedQuery(name = "Preparationattribute.findByText6", query = "SELECT p FROM Preparationattribute p WHERE p.text6 = :text6"),
    @NamedQuery(name = "Preparationattribute.findByText7", query = "SELECT p FROM Preparationattribute p WHERE p.text7 = :text7"),
    @NamedQuery(name = "Preparationattribute.findByText8", query = "SELECT p FROM Preparationattribute p WHERE p.text8 = :text8"),
    @NamedQuery(name = "Preparationattribute.findByText9", query = "SELECT p FROM Preparationattribute p WHERE p.text9 = :text9"),
    @NamedQuery(name = "Preparationattribute.findByYesNo1", query = "SELECT p FROM Preparationattribute p WHERE p.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Preparationattribute.findByYesNo2", query = "SELECT p FROM Preparationattribute p WHERE p.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Preparationattribute.findByYesNo3", query = "SELECT p FROM Preparationattribute p WHERE p.yesNo3 = :yesNo3"),
    @NamedQuery(name = "Preparationattribute.findByYesNo4", query = "SELECT p FROM Preparationattribute p WHERE p.yesNo4 = :yesNo4")})
public class Preparationattribute extends BaseEntity {
 
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PreparationAttributeID")
    private Integer preparationAttributeId;
     
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
    
    @Column(name = "Number4")
    private Integer number4;
    
    @Column(name = "Number5")
    private Integer number5;
    
    @Column(name = "Number6")
    private Integer number6;
    
    @Column(name = "Number7")
    private Integer number7;
    
    @Column(name = "Number8")
    private Integer number8;
    
    @Column(name = "Number9")
    private Short number9;
    
    @Column(name = "AttrDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attrDate;
    
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
    @Column(name = "Text10")
    private String text10;
    
    @Size(max = 50)
    @Column(name = "Text11")
    private String text11;
    
    @Size(max = 50)
    @Column(name = "Text12")
    private String text12;
    
    @Size(max = 50)
    @Column(name = "Text13")
    private String text13;
    
    @Size(max = 50)
    @Column(name = "Text14")
    private String text14;
    
    @Size(max = 50)
    @Column(name = "Text15")
    private String text15;
    
    @Size(max = 50)
    @Column(name = "Text16")
    private String text16;
    
    @Size(max = 50)
    @Column(name = "Text17")
    private String text17;
    
    @Size(max = 50)
    @Column(name = "Text18")
    private String text18;
    
    @Size(max = 50)
    @Column(name = "Text19")
    private String text19;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 50)
    @Column(name = "Text20")
    private String text20;
    
    @Size(max = 50)
    @Column(name = "Text21")
    private String text21;
    
    @Size(max = 50)
    @Column(name = "Text22")
    private String text22;
    
    @Size(max = 50)
    @Column(name = "Text23")
    private String text23;
    
    @Size(max = 50)
    @Column(name = "Text24")
    private String text24;
    
    @Size(max = 50)
    @Column(name = "Text25")
    private String text25;
    
    @Size(max = 50)
    @Column(name = "Text26")
    private String text26;
    
    @Size(max = 50)
    @Column(name = "Text3")
    private String text3;
    
    @Size(max = 50)
    @Column(name = "Text4")
    private String text4;
    
    @Size(max = 50)
    @Column(name = "Text5")
    private String text5;
    
    @Size(max = 50)
    @Column(name = "Text6")
    private String text6;
    
    @Size(max = 50)
    @Column(name = "Text7")
    private String text7;
    
    @Size(max = 50)
    @Column(name = "Text8")
    private String text8;
    
    @Size(max = 50)
    @Column(name = "Text9")
    private String text9;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Column(name = "YesNo3")
    private Boolean yesNo3;
    
    @Column(name = "YesNo4")
    private Boolean yesNo4;
    
    @OneToMany(mappedBy = "preparationAttribute")
    private Collection<Preparation> preparations;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Preparationattribute() {
    }

    public Preparationattribute(Integer preparationAttributeId) {
        this.preparationAttributeId = preparationAttributeId;
    }

    public Preparationattribute(Integer preparationAttributeId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.preparationAttributeId = preparationAttributeId; 
        this.collectionMemberId = collectionMemberId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (preparationAttributeId != null) ? preparationAttributeId.toString() : "0";
    }
    
    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getPreparationAttributeId() {
        return preparationAttributeId;
    }

    public void setPreparationAttributeId(Integer preparationAttributeId) {
        this.preparationAttributeId = preparationAttributeId;
    }

    @XmlTransient
    public Collection<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(Collection<Preparation> preparations) {
        this.preparations = preparations;
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

    public Integer getNumber4() {
        return number4;
    }

    public void setNumber4(Integer number4) {
        this.number4 = number4;
    }

    public Integer getNumber5() {
        return number5;
    }

    public void setNumber5(Integer number5) {
        this.number5 = number5;
    }

    public Integer getNumber6() {
        return number6;
    }

    public void setNumber6(Integer number6) {
        this.number6 = number6;
    }

    public Integer getNumber7() {
        return number7;
    }

    public void setNumber7(Integer number7) {
        this.number7 = number7;
    }

    public Integer getNumber8() {
        return number8;
    }

    public void setNumber8(Integer number8) {
        this.number8 = number8;
    }

    public Short getNumber9() {
        return number9;
    }

    public void setNumber9(Short number9) {
        this.number9 = number9;
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

    public String getText10() {
        return text10;
    }

    public void setText10(String text10) {
        this.text10 = text10;
    }

    public String getText11() {
        return text11;
    }

    public void setText11(String text11) {
        this.text11 = text11;
    }

    public String getText12() {
        return text12;
    }

    public void setText12(String text12) {
        this.text12 = text12;
    }

    public String getText13() {
        return text13;
    }

    public void setText13(String text13) {
        this.text13 = text13;
    }

    public String getText14() {
        return text14;
    }

    public void setText14(String text14) {
        this.text14 = text14;
    }

    public String getText15() {
        return text15;
    }

    public void setText15(String text15) {
        this.text15 = text15;
    }

    public String getText16() {
        return text16;
    }

    public void setText16(String text16) {
        this.text16 = text16;
    }

    public String getText17() {
        return text17;
    }

    public void setText17(String text17) {
        this.text17 = text17;
    }

    public String getText18() {
        return text18;
    }

    public void setText18(String text18) {
        this.text18 = text18;
    }

    public String getText19() {
        return text19;
    }

    public void setText19(String text19) {
        this.text19 = text19;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText20() {
        return text20;
    }

    public void setText20(String text20) {
        this.text20 = text20;
    }

    public String getText21() {
        return text21;
    }

    public void setText21(String text21) {
        this.text21 = text21;
    }

    public String getText22() {
        return text22;
    }

    public void setText22(String text22) {
        this.text22 = text22;
    }

    public String getText23() {
        return text23;
    }

    public void setText23(String text23) {
        this.text23 = text23;
    }

    public String getText24() {
        return text24;
    }

    public void setText24(String text24) {
        this.text24 = text24;
    }

    public String getText25() {
        return text25;
    }

    public void setText25(String text25) {
        this.text25 = text25;
    }

    public String getText26() {
        return text26;
    }

    public void setText26(String text26) {
        this.text26 = text26;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public String getText6() {
        return text6;
    }

    public void setText6(String text6) {
        this.text6 = text6;
    }

    public String getText7() {
        return text7;
    }

    public void setText7(String text7) {
        this.text7 = text7;
    }

    public String getText8() {
        return text8;
    }

    public void setText8(String text8) {
        this.text8 = text8;
    }

    public String getText9() {
        return text9;
    }

    public void setText9(String text9) {
        this.text9 = text9;
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

    public Boolean getYesNo4() {
        return yesNo4;
    }

    public void setYesNo4(Boolean yesNo4) {
        this.yesNo4 = yesNo4;
    }
    
    
    
    public Date getAttrDate() {
        return attrDate;
    }

    public void setAttrDate(Date attrDate) {
        this.attrDate = attrDate;
    }
    
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preparationAttributeId != null ? preparationAttributeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preparationattribute)) {
            return false;
        }
        Preparationattribute other = (Preparationattribute) object;
        if ((this.preparationAttributeId == null && other.preparationAttributeId != null) || (this.preparationAttributeId != null && !this.preparationAttributeId.equals(other.preparationAttributeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preparationattribute[ preparationAttributeID=" + preparationAttributeId + " ]";
    }
 
}

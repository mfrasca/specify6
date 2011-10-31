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
@Table(name = "commonnametxcitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commonnametxcitation.findAll", query = "SELECT c FROM Commonnametxcitation c"),
    @NamedQuery(name = "Commonnametxcitation.findByCommonNameTxCitationID", query = "SELECT c FROM Commonnametxcitation c WHERE c.commonNameTxCitationID = :commonNameTxCitationID"),
    @NamedQuery(name = "Commonnametxcitation.findByTimestampCreated", query = "SELECT c FROM Commonnametxcitation c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Commonnametxcitation.findByTimestampModified", query = "SELECT c FROM Commonnametxcitation c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Commonnametxcitation.findByVersion", query = "SELECT c FROM Commonnametxcitation c WHERE c.version = :version"),
    @NamedQuery(name = "Commonnametxcitation.findByNumber1", query = "SELECT c FROM Commonnametxcitation c WHERE c.number1 = :number1"),
    @NamedQuery(name = "Commonnametxcitation.findByNumber2", query = "SELECT c FROM Commonnametxcitation c WHERE c.number2 = :number2"),
    @NamedQuery(name = "Commonnametxcitation.findByYesNo1", query = "SELECT c FROM Commonnametxcitation c WHERE c.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Commonnametxcitation.findByYesNo2", query = "SELECT c FROM Commonnametxcitation c WHERE c.yesNo2 = :yesNo2")})
public class Commonnametxcitation extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CommonNameTxCitationID")
    private Integer commonNameTxCitationID;
     
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
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWorkID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "CommonNameTxID", referencedColumnName = "CommonNameTxID")
    @ManyToOne(optional = false)
    private Commonnametx commonNameTxID;
     
    public Commonnametxcitation() {
    }

    public Commonnametxcitation(Integer commonNameTxCitationID) {
        this.commonNameTxCitationID = commonNameTxCitationID;
    }

    public Commonnametxcitation(Integer commonNameTxCitationID, Date timestampCreated) {
        super(timestampCreated);
        this.commonNameTxCitationID = commonNameTxCitationID; 
    }

    public Integer getCommonNameTxCitationID() {
        return commonNameTxCitationID;
    }

    public void setCommonNameTxCitationID(Integer commonNameTxCitationID) {
        this.commonNameTxCitationID = commonNameTxCitationID;
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

    public Commonnametx getCommonNameTxID() {
        return commonNameTxID;
    }

    public void setCommonNameTxID(Commonnametx commonNameTxID) {
        this.commonNameTxID = commonNameTxID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commonNameTxCitationID != null ? commonNameTxCitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commonnametxcitation)) {
            return false;
        }
        Commonnametxcitation other = (Commonnametxcitation) object;
        if ((this.commonNameTxCitationID == null && other.commonNameTxCitationID != null) || (this.commonNameTxCitationID != null && !this.commonNameTxCitationID.equals(other.commonNameTxCitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Commonnametxcitation[ commonNameTxCitationID=" + commonNameTxCitationID + " ]";
    }
    
}

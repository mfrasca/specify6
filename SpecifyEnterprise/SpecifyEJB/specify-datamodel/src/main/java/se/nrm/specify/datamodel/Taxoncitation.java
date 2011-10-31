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
@Table(name = "taxoncitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxoncitation.findAll", query = "SELECT t FROM Taxoncitation t"),
    @NamedQuery(name = "Taxoncitation.findByTaxonCitationID", query = "SELECT t FROM Taxoncitation t WHERE t.taxonCitationID = :taxonCitationID"),
    @NamedQuery(name = "Taxoncitation.findByTimestampCreated", query = "SELECT t FROM Taxoncitation t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxoncitation.findByTimestampModified", query = "SELECT t FROM Taxoncitation t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxoncitation.findByVersion", query = "SELECT t FROM Taxoncitation t WHERE t.version = :version"),
    @NamedQuery(name = "Taxoncitation.findByNumber1", query = "SELECT t FROM Taxoncitation t WHERE t.number1 = :number1"),
    @NamedQuery(name = "Taxoncitation.findByNumber2", query = "SELECT t FROM Taxoncitation t WHERE t.number2 = :number2"),
    @NamedQuery(name = "Taxoncitation.findByYesNo1", query = "SELECT t FROM Taxoncitation t WHERE t.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Taxoncitation.findByYesNo2", query = "SELECT t FROM Taxoncitation t WHERE t.yesNo2 = :yesNo2")})
public class Taxoncitation extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TaxonCitationID")
    private Integer taxonCitationID;
     
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
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @ManyToOne(optional = false)
    private Taxon taxonID;

    public Taxoncitation() {
    }

    public Taxoncitation(Integer taxonCitationID) {
        this.taxonCitationID = taxonCitationID;
    }

    public Taxoncitation(Integer taxonCitationID, Date timestampCreated) {
        super(timestampCreated);
        this.taxonCitationID = taxonCitationID; 
    }

    public Integer getTaxonCitationID() {
        return taxonCitationID;
    }

    public void setTaxonCitationID(Integer taxonCitationID) {
        this.taxonCitationID = taxonCitationID;
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

    public Taxon getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Taxon taxonID) {
        this.taxonID = taxonID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonCitationID != null ? taxonCitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxoncitation)) {
            return false;
        }
        Taxoncitation other = (Taxoncitation) object;
        if ((this.taxonCitationID == null && other.taxonCitationID != null) || (this.taxonCitationID != null && !this.taxonCitationID.equals(other.taxonCitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxoncitation[ taxonCitationID=" + taxonCitationID + " ]";
    }
    
}

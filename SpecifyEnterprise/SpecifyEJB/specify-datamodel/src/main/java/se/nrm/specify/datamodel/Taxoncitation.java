package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "taxoncitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxoncitation.findAll", query = "SELECT t FROM Taxoncitation t"),
    @NamedQuery(name = "Taxoncitation.findByTaxonCitationId", query = "SELECT t FROM Taxoncitation t WHERE t.taxonCitationId = :taxonCitationId"),
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
    private Integer taxonCitationId;
     
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
    private Agent createdByAgent;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @NotNull
    @ManyToOne(optional = false)
    private Referencework referenceWork;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @NotNull
    @ManyToOne(optional = false)
    private Taxon taxon;

    public Taxoncitation() {
    }

    public Taxoncitation(Integer taxonCitationId) {
        this.taxonCitationId = taxonCitationId;
    }

    public Taxoncitation(Integer taxonCitationId, Date timestampCreated) {
        super(timestampCreated);
        this.taxonCitationId = taxonCitationId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (taxonCitationId != null) ? taxonCitationId.toString() : "0";
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @NotNull(message="Reference must be specified.")
    public Referencework getReferenceWork() {
        return referenceWork;
    }

    public void setReferenceWork(Referencework referenceWork) {
        this.referenceWork = referenceWork;
    }

    @XmlTransient
    @NotNull(message="Taxon must be specified.")
    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

    public Integer getTaxonCitationId() {
        return taxonCitationId;
    }

    public void setTaxonCitationId(Integer taxonCitationId) {
        this.taxonCitationId = taxonCitationId;
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
 
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Taxon) {
            this.taxon = (Taxon)parent;   
        }
    }
    
    @Override
    public String getEntityName() {
        return "taxonCitation";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonCitationId != null ? taxonCitationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxoncitation)) {
            return false;
        }
        Taxoncitation other = (Taxoncitation) object;
        if ((this.taxonCitationId == null && other.taxonCitationId != null) || (this.taxonCitationId != null && !this.taxonCitationId.equals(other.taxonCitationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxoncitation[ taxonCitationID=" + taxonCitationId + " ]";
    }
  
}

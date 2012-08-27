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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "deaccessionpreparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deaccessionpreparation.findAll", query = "SELECT d FROM Deaccessionpreparation d"),
    @NamedQuery(name = "Deaccessionpreparation.findByDeaccessionPreparationId", query = "SELECT d FROM Deaccessionpreparation d WHERE d.deaccessionPreparationId = :deaccessionPreparationId"),
    @NamedQuery(name = "Deaccessionpreparation.findByTimestampCreated", query = "SELECT d FROM Deaccessionpreparation d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Deaccessionpreparation.findByTimestampModified", query = "SELECT d FROM Deaccessionpreparation d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Deaccessionpreparation.findByVersion", query = "SELECT d FROM Deaccessionpreparation d WHERE d.version = :version"),
    @NamedQuery(name = "Deaccessionpreparation.findByQuantity", query = "SELECT d FROM Deaccessionpreparation d WHERE d.quantity = :quantity")})
public class Deaccessionpreparation extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DeaccessionPreparationID")
    private Integer deaccessionPreparationId;
      
    @Column(name = "Quantity")
    private Short quantity;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "deaccessionPreparation")
    private Collection<Loanreturnpreparation> loanreturnpreparations;
    
    @JoinColumn(name = "DeaccessionID", referencedColumnName = "DeaccessionID")
    @NotNull
    @ManyToOne(optional = false)
    private Deaccession deaccession;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne
    private Preparation preparation;

    public Deaccessionpreparation() {
    }

    public Deaccessionpreparation(Integer deaccessionPreparationId) {
        this.deaccessionPreparationId = deaccessionPreparationId;
    }

    public Deaccessionpreparation(Integer deaccessionPreparationId, Date timestampCreated) {
        super(timestampCreated);
        this.deaccessionPreparationId = deaccessionPreparationId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (deaccessionPreparationId != null) ? deaccessionPreparationId.toString() : "0";
    }
    
    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Deaccession must be specified.")
    public Deaccession getDeaccession() {
        return deaccession;
    }

    public void setDeaccession(Deaccession deaccession) {
        this.deaccession = deaccession;
    }

    public Integer getDeaccessionPreparationId() {
        return deaccessionPreparationId;
    }

    public void setDeaccessionPreparationId(Integer deaccessionPreparationId) {
        this.deaccessionPreparationId = deaccessionPreparationId;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

 
 
    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparations() {
        return loanreturnpreparations;
    }

    public void setLoanreturnpreparations(Collection<Loanreturnpreparation> loanreturnpreparations) {
        this.loanreturnpreparations = loanreturnpreparations;
    }

    @Override
    public String getEntityName() {
        return "deaccessionPreparation";
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deaccessionPreparationId != null ? deaccessionPreparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deaccessionpreparation)) {
            return false;
        }
        Deaccessionpreparation other = (Deaccessionpreparation) object;
        if ((this.deaccessionPreparationId == null && other.deaccessionPreparationId != null) || (this.deaccessionPreparationId != null && !this.deaccessionPreparationId.equals(other.deaccessionPreparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Deaccessionpreparation[ deaccessionPreparationID=" + deaccessionPreparationId + " ]";
    }

    
}

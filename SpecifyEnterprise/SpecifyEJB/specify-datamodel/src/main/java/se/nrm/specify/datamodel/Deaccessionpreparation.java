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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Deaccessionpreparation.findByDeaccessionPreparationID", query = "SELECT d FROM Deaccessionpreparation d WHERE d.deaccessionPreparationID = :deaccessionPreparationID"),
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
    private Integer deaccessionPreparationID;
      
    @Column(name = "Quantity")
    private Short quantity;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "deaccessionPreparationID")
    private Collection<Loanreturnpreparation> loanreturnpreparationCollection;
    
    @JoinColumn(name = "DeaccessionID", referencedColumnName = "DeaccessionID")
    @ManyToOne(optional = false)
    private Deaccession deaccessionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne
    private Preparation preparationID;

    public Deaccessionpreparation() {
    }

    public Deaccessionpreparation(Integer deaccessionPreparationID) {
        this.deaccessionPreparationID = deaccessionPreparationID;
    }

    public Deaccessionpreparation(Integer deaccessionPreparationID, Date timestampCreated) {
        super(timestampCreated);
        this.deaccessionPreparationID = deaccessionPreparationID; 
    }

    public Integer getDeaccessionPreparationID() {
        return deaccessionPreparationID;
    }

    public void setDeaccessionPreparationID(Integer deaccessionPreparationID) {
        this.deaccessionPreparationID = deaccessionPreparationID;
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
    public Collection<Loanreturnpreparation> getLoanreturnpreparationCollection() {
        return loanreturnpreparationCollection;
    }

    public void setLoanreturnpreparationCollection(Collection<Loanreturnpreparation> loanreturnpreparationCollection) {
        this.loanreturnpreparationCollection = loanreturnpreparationCollection;
    }

    public Deaccession getDeaccessionID() {
        return deaccessionID;
    }

    public void setDeaccessionID(Deaccession deaccessionID) {
        this.deaccessionID = deaccessionID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Preparation getPreparationID() {
        return preparationID;
    }

    public void setPreparationID(Preparation preparationID) {
        this.preparationID = preparationID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deaccessionPreparationID != null ? deaccessionPreparationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deaccessionpreparation)) {
            return false;
        }
        Deaccessionpreparation other = (Deaccessionpreparation) object;
        if ((this.deaccessionPreparationID == null && other.deaccessionPreparationID != null) || (this.deaccessionPreparationID != null && !this.deaccessionPreparationID.equals(other.deaccessionPreparationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Deaccessionpreparation[ deaccessionPreparationID=" + deaccessionPreparationID + " ]";
    }
    
}

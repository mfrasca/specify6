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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "loanreturnpreparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loanreturnpreparation.findAll", query = "SELECT l FROM Loanreturnpreparation l"),
    @NamedQuery(name = "Loanreturnpreparation.findByLoanReturnPreparationID", query = "SELECT l FROM Loanreturnpreparation l WHERE l.loanReturnPreparationId = :loanReturnPreparationID"),
    @NamedQuery(name = "Loanreturnpreparation.findByTimestampCreated", query = "SELECT l FROM Loanreturnpreparation l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Loanreturnpreparation.findByTimestampModified", query = "SELECT l FROM Loanreturnpreparation l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Loanreturnpreparation.findByVersion", query = "SELECT l FROM Loanreturnpreparation l WHERE l.version = :version"),
    @NamedQuery(name = "Loanreturnpreparation.findByQuantityResolved", query = "SELECT l FROM Loanreturnpreparation l WHERE l.quantityResolved = :quantityResolved"),
    @NamedQuery(name = "Loanreturnpreparation.findByQuantityReturned", query = "SELECT l FROM Loanreturnpreparation l WHERE l.quantityReturned = :quantityReturned"),
    @NamedQuery(name = "Loanreturnpreparation.findByReturnedDate", query = "SELECT l FROM Loanreturnpreparation l WHERE l.returnedDate = :returnedDate")})
public class Loanreturnpreparation extends BaseEntity {
 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LoanReturnPreparationID")
    private Integer loanReturnPreparationId;
     
    @Column(name = "QuantityResolved")
    private Integer quantityResolved;
    
    @Column(name = "QuantityReturned")
    private Integer quantityReturned;
    
    @Column(name = "ReturnedDate")
    @Temporal(TemporalType.DATE)
    private Date returnedDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "DeaccessionPreparationID", referencedColumnName = "DeaccessionPreparationID")
    @ManyToOne
    private Deaccessionpreparation deaccessionPreparation;
    
    @JoinColumn(name = "LoanPreparationID", referencedColumnName = "LoanPreparationID")
    @ManyToOne(optional = false)
    private Loanpreparation loanPreparation;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline discipline;
    
    @JoinColumn(name = "ReceivedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent receivedBy;

    public Loanreturnpreparation() {
    }

    public Loanreturnpreparation(Integer loanReturnPreparationId) {
        this.loanReturnPreparationId = loanReturnPreparationId;
    }

    public Loanreturnpreparation(Integer loanReturnPreparationId, Date timestampCreated) {
        super(timestampCreated);
        this.loanReturnPreparationId = loanReturnPreparationId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (loanReturnPreparationId != null) ? loanReturnPreparationId.toString() : "0";
    }
 
    public Integer getQuantityResolved() {
        return quantityResolved;
    }

    public void setQuantityResolved(Integer quantityResolved) {
        this.quantityResolved = quantityResolved;
    }

    public Integer getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Integer quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Deaccessionpreparation getDeaccessionPreparation() {
        return deaccessionPreparation;
    }

    public void setDeaccessionPreparation(Deaccessionpreparation deaccessionPreparation) {
        this.deaccessionPreparation = deaccessionPreparation;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @NotNull(message="LoanPreparation must be specified.")
    public Loanpreparation getLoanPreparation() {
        return loanPreparation;
    }

    public void setLoanPreparation(Loanpreparation loanPreparation) {
        this.loanPreparation = loanPreparation;
    }

    public Integer getLoanReturnPreparationId() {
        return loanReturnPreparationId;
    }

    public void setLoanReturnPreparationId(Integer loanReturnPreparationId) {
        this.loanReturnPreparationId = loanReturnPreparationId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Agent getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Agent receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanReturnPreparationId != null ? loanReturnPreparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanreturnpreparation)) {
            return false;
        }
        Loanreturnpreparation other = (Loanreturnpreparation) object;
        if ((this.loanReturnPreparationId == null && other.loanReturnPreparationId != null) || (this.loanReturnPreparationId != null && !this.loanReturnPreparationId.equals(other.loanReturnPreparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanreturnpreparation[ loanReturnPreparationID=" + loanReturnPreparationId + " ]";
    } 
}

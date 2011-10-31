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
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Loanreturnpreparation.findByLoanReturnPreparationID", query = "SELECT l FROM Loanreturnpreparation l WHERE l.loanReturnPreparationID = :loanReturnPreparationID"),
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
    private Integer loanReturnPreparationID;
     
    @Column(name = "QuantityResolved")
    private Integer quantityResolved;
    
    @Column(name = "QuantityReturned")
    private Integer quantityReturned;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "ReturnedDate")
    @Temporal(TemporalType.DATE)
    private Date returnedDate;
    
    @JoinColumn(name = "DeaccessionPreparationID", referencedColumnName = "DeaccessionPreparationID")
    @ManyToOne
    private Deaccessionpreparation deaccessionPreparationID;
    
    @JoinColumn(name = "LoanPreparationID", referencedColumnName = "LoanPreparationID")
    @ManyToOne(optional = false)
    private Loanpreparation loanPreparationID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline disciplineID;
    
    @JoinColumn(name = "ReceivedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent receivedByID;

    public Loanreturnpreparation() {
    }

    public Loanreturnpreparation(Integer loanReturnPreparationID) {
        this.loanReturnPreparationID = loanReturnPreparationID;
    }

    public Loanreturnpreparation(Integer loanReturnPreparationID, Date timestampCreated) {
        super(timestampCreated);
        this.loanReturnPreparationID = loanReturnPreparationID; 
    }

    public Integer getLoanReturnPreparationID() {
        return loanReturnPreparationID;
    }

    public void setLoanReturnPreparationID(Integer loanReturnPreparationID) {
        this.loanReturnPreparationID = loanReturnPreparationID;
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

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Deaccessionpreparation getDeaccessionPreparationID() {
        return deaccessionPreparationID;
    }

    public void setDeaccessionPreparationID(Deaccessionpreparation deaccessionPreparationID) {
        this.deaccessionPreparationID = deaccessionPreparationID;
    }

    public Loanpreparation getLoanPreparationID() {
        return loanPreparationID;
    }

    public void setLoanPreparationID(Loanpreparation loanPreparationID) {
        this.loanPreparationID = loanPreparationID;
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

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    public Agent getReceivedByID() {
        return receivedByID;
    }

    public void setReceivedByID(Agent receivedByID) {
        this.receivedByID = receivedByID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanReturnPreparationID != null ? loanReturnPreparationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanreturnpreparation)) {
            return false;
        }
        Loanreturnpreparation other = (Loanreturnpreparation) object;
        if ((this.loanReturnPreparationID == null && other.loanReturnPreparationID != null) || (this.loanReturnPreparationID != null && !this.loanReturnPreparationID.equals(other.loanReturnPreparationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanreturnpreparation[ loanReturnPreparationID=" + loanReturnPreparationID + " ]";
    }
    
}

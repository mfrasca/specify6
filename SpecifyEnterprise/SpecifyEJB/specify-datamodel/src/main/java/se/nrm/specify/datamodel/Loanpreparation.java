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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "loanpreparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loanpreparation.findAll", query = "SELECT l FROM Loanpreparation l"),
    @NamedQuery(name = "Loanpreparation.findByLoanPreparationID", query = "SELECT l FROM Loanpreparation l WHERE l.loanPreparationId = :loanPreparationID"),
    @NamedQuery(name = "Loanpreparation.findByTimestampCreated", query = "SELECT l FROM Loanpreparation l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Loanpreparation.findByTimestampModified", query = "SELECT l FROM Loanpreparation l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Loanpreparation.findByVersion", query = "SELECT l FROM Loanpreparation l WHERE l.version = :version"),
    @NamedQuery(name = "Loanpreparation.findByIsResolved", query = "SELECT l FROM Loanpreparation l WHERE l.isResolved = :isResolved"),
    @NamedQuery(name = "Loanpreparation.findByQuantity", query = "SELECT l FROM Loanpreparation l WHERE l.quantity = :quantity"),
    @NamedQuery(name = "Loanpreparation.findByQuantityResolved", query = "SELECT l FROM Loanpreparation l WHERE l.quantityResolved = :quantityResolved"),
    @NamedQuery(name = "Loanpreparation.findByQuantityReturned", query = "SELECT l FROM Loanpreparation l WHERE l.quantityReturned = :quantityReturned")})
public class Loanpreparation extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LoanPreparationID")
    private Integer loanPreparationId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "DescriptionOfMaterial")
    private String descriptionOfMaterial;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "InComments")
    private String inComments;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsResolved")
    private boolean isResolved;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "OutComments")
    private String outComments;
    
    @Column(name = "Quantity")
    private Integer quantity;
    
    @Column(name = "QuantityResolved")
    private Integer quantityResolved;
    
    @Column(name = "QuantityReturned")
    private Integer quantityReturned;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "ReceivedComments")
    private String receivedComments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanPreparation")
    private Collection<Loanreturnpreparation> loanreturnpreparations;
    
    @JoinColumn(name = "LoanID", referencedColumnName = "LoanID")
    @ManyToOne(optional = false)
    private Loan loan;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline discipline;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne
    private Preparation preparation;

    public Loanpreparation() {
    }

    public Loanpreparation(Integer loanPreparationId) {
        this.loanPreparationId = loanPreparationId;
    }

    public Loanpreparation(Integer loanPreparationId, Date timestampCreated, boolean isResolved) {
        super(timestampCreated);
        this.loanPreparationId = loanPreparationId; 
        this.isResolved = isResolved;
    }
 
    public String getDescriptionOfMaterial() {
        return descriptionOfMaterial;
    }

    public void setDescriptionOfMaterial(String descriptionOfMaterial) {
        this.descriptionOfMaterial = descriptionOfMaterial;
    }

    public String getInComments() {
        return inComments;
    }

    public void setInComments(String inComments) {
        this.inComments = inComments;
    }

    public boolean getIsResolved() {
        return isResolved;
    }

    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public String getOutComments() {
        return outComments;
    }

    public void setOutComments(String outComments) {
        this.outComments = outComments;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getReceivedComments() {
        return receivedComments;
    }

    public void setReceivedComments(String receivedComments) {
        this.receivedComments = receivedComments;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparations() {
        return loanreturnpreparations;
    }

    public void setLoanreturnpreparations(Collection<Loanreturnpreparation> loanreturnpreparations) {
        this.loanreturnpreparations = loanreturnpreparations;
    }

 

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Integer getLoanPreparationId() {
        return loanPreparationId;
    }

    public void setLoanPreparationId(Integer loanPreparationId) {
        this.loanPreparationId = loanPreparationId;
    }

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

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanPreparationId != null ? loanPreparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanpreparation)) {
            return false;
        }
        Loanpreparation other = (Loanpreparation) object;
        if ((this.loanPreparationId == null && other.loanPreparationId != null) || (this.loanPreparationId != null && !this.loanPreparationId.equals(other.loanPreparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanpreparation[ loanPreparationID=" + loanPreparationId + " ]";
    }
    
}

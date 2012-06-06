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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "loanagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loanagent.findAll", query = "SELECT l FROM Loanagent l"),
    @NamedQuery(name = "Loanagent.findByLoanAgentID", query = "SELECT l FROM Loanagent l WHERE l.loanAgentId = :loanAgentID"),
    @NamedQuery(name = "Loanagent.findByTimestampCreated", query = "SELECT l FROM Loanagent l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Loanagent.findByTimestampModified", query = "SELECT l FROM Loanagent l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Loanagent.findByVersion", query = "SELECT l FROM Loanagent l WHERE l.version = :version"),
    @NamedQuery(name = "Loanagent.findByRole", query = "SELECT l FROM Loanagent l WHERE l.role = :role")})
public class Loanagent extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LoanAgentID")
    private Integer loanAgentId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Role")
    private String role;
    
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
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agent;

    public Loanagent() {
    }

    public Loanagent(Integer loanAgentId) {
        this.loanAgentId = loanAgentId;
    }

    public Loanagent(Integer loanAgentId, Date timestampCreated, String role) {
        super(timestampCreated);
        this.loanAgentId = loanAgentId; 
        this.role = role;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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

    public Integer getLoanAgentId() {
        return loanAgentId;
    }

    public void setLoanAgentId(Integer loanAgentId) {
        this.loanAgentId = loanAgentId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 
 
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanAgentId != null ? loanAgentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanagent)) {
            return false;
        }
        Loanagent other = (Loanagent) object;
        if ((this.loanAgentId == null && other.loanAgentId != null) || (this.loanAgentId != null && !this.loanAgentId.equals(other.loanAgentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanagent[ loanAgentID=" + loanAgentId + " ]";
    }
    
}

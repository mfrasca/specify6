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
    @NamedQuery(name = "Loanagent.findByLoanAgentID", query = "SELECT l FROM Loanagent l WHERE l.loanAgentID = :loanAgentID"),
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
    private Integer loanAgentID;
     
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
    private Loan loanID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline disciplineID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Loanagent() {
    }

    public Loanagent(Integer loanAgentID) {
        this.loanAgentID = loanAgentID;
    }

    public Loanagent(Integer loanAgentID, Date timestampCreated, String role) {
        super(timestampCreated);
        this.loanAgentID = loanAgentID; 
        this.role = role;
    }

    public Integer getLoanAgentID() {
        return loanAgentID;
    }

    public void setLoanAgentID(Integer loanAgentID) {
        this.loanAgentID = loanAgentID;
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

    public Loan getLoanID() {
        return loanID;
    }

    public void setLoanID(Loan loanID) {
        this.loanID = loanID;
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

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanAgentID != null ? loanAgentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanagent)) {
            return false;
        }
        Loanagent other = (Loanagent) object;
        if ((this.loanAgentID == null && other.loanAgentID != null) || (this.loanAgentID != null && !this.loanAgentID.equals(other.loanAgentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanagent[ loanAgentID=" + loanAgentID + " ]";
    }
    
}

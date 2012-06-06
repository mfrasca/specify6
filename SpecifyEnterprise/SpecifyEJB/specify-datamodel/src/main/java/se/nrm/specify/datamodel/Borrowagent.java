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
@Table(name = "borrowagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrowagent.findAll", query = "SELECT b FROM Borrowagent b"),
    @NamedQuery(name = "Borrowagent.findByBorrowAgentID", query = "SELECT b FROM Borrowagent b WHERE b.borrowAgentId = :borrowAgentID"),
    @NamedQuery(name = "Borrowagent.findByTimestampCreated", query = "SELECT b FROM Borrowagent b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrowagent.findByTimestampModified", query = "SELECT b FROM Borrowagent b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrowagent.findByVersion", query = "SELECT b FROM Borrowagent b WHERE b.version = :version"),
    @NamedQuery(name = "Borrowagent.findByCollectionMemberID", query = "SELECT b FROM Borrowagent b WHERE b.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Borrowagent.findByRole", query = "SELECT b FROM Borrowagent b WHERE b.role = :role")})
public class Borrowagent extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "BorrowAgentID")
    private Integer borrowAgentId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "Role")
    private String role;
    
    @JoinColumn(name = "BorrowID", referencedColumnName = "BorrowID")
    @ManyToOne(optional = false)
    private Borrow borrow;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agent;

    public Borrowagent() {
    }

    public Borrowagent(Integer borrowAgentId) {
        this.borrowAgentId = borrowAgentId;
    }

    public Borrowagent(Integer borrowAgentId, Date timestampCreated, int collectionMemberId, String role) {
        super(timestampCreated);
        this.borrowAgentId = borrowAgentId; 
        this.collectionMemberId = collectionMemberId;
        this.role = role;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

    public Integer getBorrowAgentId() {
        return borrowAgentId;
    }

    public void setBorrowAgentId(Integer borrowAgentId) {
        this.borrowAgentId = borrowAgentId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
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
        hash += (borrowAgentId != null ? borrowAgentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrowagent)) {
            return false;
        }
        Borrowagent other = (Borrowagent) object;
        if ((this.borrowAgentId == null && other.borrowAgentId != null) || (this.borrowAgentId != null && !this.borrowAgentId.equals(other.borrowAgentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Borrowagent[ borrowAgentId=" + borrowAgentId + " ]";
    }
    
}

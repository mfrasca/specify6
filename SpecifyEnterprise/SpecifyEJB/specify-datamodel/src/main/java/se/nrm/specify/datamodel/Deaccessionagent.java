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
@Table(name = "deaccessionagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deaccessionagent.findAll", query = "SELECT d FROM Deaccessionagent d"),
    @NamedQuery(name = "Deaccessionagent.findByDeaccessionAgentID", query = "SELECT d FROM Deaccessionagent d WHERE d.deaccessionAgentId = :deaccessionAgentID"),
    @NamedQuery(name = "Deaccessionagent.findByTimestampCreated", query = "SELECT d FROM Deaccessionagent d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Deaccessionagent.findByTimestampModified", query = "SELECT d FROM Deaccessionagent d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Deaccessionagent.findByVersion", query = "SELECT d FROM Deaccessionagent d WHERE d.version = :version"),
    @NamedQuery(name = "Deaccessionagent.findByRole", query = "SELECT d FROM Deaccessionagent d WHERE d.role = :role")})
public class Deaccessionagent extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DeaccessionAgentID")
    private Integer deaccessionAgentId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Role")
    private String role;
    
    @JoinColumn(name = "DeaccessionID", referencedColumnName = "DeaccessionID")
    @ManyToOne(optional = false)
    private Deaccession deaccession;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agent;
    
    public Deaccessionagent() {
    }

    public Deaccessionagent(Integer deaccessionAgentId) {
        this.deaccessionAgentId = deaccessionAgentId;
    }

    public Deaccessionagent(Integer deaccessionAgentId, Date timestampCreated, String role) {
        this.deaccessionAgentId = deaccessionAgentId;
        this.timestampCreated = timestampCreated;  
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

    public Deaccession getDeaccession() {
        return deaccession;
    }

    public void setDeaccession(Deaccession deaccession) {
        this.deaccession = deaccession;
    }

    public Integer getDeaccessionAgentId() {
        return deaccessionAgentId;
    }

    public void setDeaccessionAgentId(Integer deaccessionAgentId) {
        this.deaccessionAgentId = deaccessionAgentId;
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
        hash += (deaccessionAgentId != null ? deaccessionAgentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deaccessionagent)) {
            return false;
        }
        Deaccessionagent other = (Deaccessionagent) object;
        if ((this.deaccessionAgentId == null && other.deaccessionAgentId != null) || (this.deaccessionAgentId != null && !this.deaccessionAgentId.equals(other.deaccessionAgentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Deaccessionagent[ deaccessionAgentId=" + deaccessionAgentId + " ]";
    }
    
}

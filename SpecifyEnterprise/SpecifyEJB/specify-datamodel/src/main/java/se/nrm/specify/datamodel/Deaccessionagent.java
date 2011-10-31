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
    @NamedQuery(name = "Deaccessionagent.findByDeaccessionAgentID", query = "SELECT d FROM Deaccessionagent d WHERE d.deaccessionAgentID = :deaccessionAgentID"),
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
    private Integer deaccessionAgentID;
     
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
    private Deaccession deaccessionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;
    
    public Deaccessionagent() {
    }

    public Deaccessionagent(Integer deaccessionAgentID) {
        this.deaccessionAgentID = deaccessionAgentID;
    }

    public Deaccessionagent(Integer deaccessionAgentID, Date timestampCreated, String role) {
        this.deaccessionAgentID = deaccessionAgentID;
        this.timestampCreated = timestampCreated;  
        this.role = role;
    }

    public Integer getDeaccessionAgentID() {
        return deaccessionAgentID;
    }

    public void setDeaccessionAgentID(Integer deaccessionAgentID) {
        this.deaccessionAgentID = deaccessionAgentID;
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

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deaccessionAgentID != null ? deaccessionAgentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deaccessionagent)) {
            return false;
        }
        Deaccessionagent other = (Deaccessionagent) object;
        if ((this.deaccessionAgentID == null && other.deaccessionAgentID != null) || (this.deaccessionAgentID != null && !this.deaccessionAgentID.equals(other.deaccessionAgentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Deaccessionagent[ deaccessionAgentID=" + deaccessionAgentID + " ]";
    }
    
}

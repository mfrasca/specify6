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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "agentgeography")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agentgeography.findAll", query = "SELECT a FROM Agentgeography a"),
    @NamedQuery(name = "Agentgeography.findByAgentGeographyID", query = "SELECT a FROM Agentgeography a WHERE a.agentGeographyId = :agentGeographyID"),
    @NamedQuery(name = "Agentgeography.findByTimestampCreated", query = "SELECT a FROM Agentgeography a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Agentgeography.findByTimestampModified", query = "SELECT a FROM Agentgeography a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Agentgeography.findByVersion", query = "SELECT a FROM Agentgeography a WHERE a.version = :version"),
    @NamedQuery(name = "Agentgeography.findByRole", query = "SELECT a FROM Agentgeography a WHERE a.role = :role")})
public class Agentgeography extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AgentGeographyID")
    private Integer agentGeographyId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "Role")
    private String role;
    
    @JoinColumn(name = "GeographyID", referencedColumnName = "GeographyID")
    @ManyToOne(optional = false)
    private Geography geography;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agent;

    public Agentgeography() {
    }

    public Agentgeography(Integer agentGeographyId) {
        this.agentGeographyId = agentGeographyId;
    }

    public Agentgeography(Integer agentGeographyId, Date timestampCreated) {
        super(timestampCreated);
        this.agentGeographyId = agentGeographyId; 
    }

    public Integer getAgentGeographyId() {
        return agentGeographyId;
    }

    public void setAgentGeographyId(Integer agentGeographyId) {
        this.agentGeographyId = agentGeographyId;
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

    public Geography getGeography() {
        return geography;
    }

    public void setGeography(Geography geography) {
        this.geography = geography;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }
 
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentGeographyId != null ? agentGeographyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agentgeography)) {
            return false;
        }
        Agentgeography other = (Agentgeography) object;
        if ((this.agentGeographyId == null && other.agentGeographyId != null) || (this.agentGeographyId != null && !this.agentGeographyId.equals(other.agentGeographyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agentgeography[ agentGeographyId=" + agentGeographyId + " ]";
    }
    
}

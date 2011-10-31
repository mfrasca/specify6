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
@Table(name = "giftagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Giftagent.findAll", query = "SELECT g FROM Giftagent g"),
    @NamedQuery(name = "Giftagent.findByGiftAgentID", query = "SELECT g FROM Giftagent g WHERE g.giftAgentID = :giftAgentID"),
    @NamedQuery(name = "Giftagent.findByTimestampCreated", query = "SELECT g FROM Giftagent g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Giftagent.findByTimestampModified", query = "SELECT g FROM Giftagent g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Giftagent.findByVersion", query = "SELECT g FROM Giftagent g WHERE g.version = :version"),
    @NamedQuery(name = "Giftagent.findByRole", query = "SELECT g FROM Giftagent g WHERE g.role = :role")})
public class Giftagent extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GiftAgentID")
    private Integer giftAgentID;
      
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Role")
    private String role;
    
    @JoinColumn(name = "GiftID", referencedColumnName = "GiftID")
    @ManyToOne(optional = false)
    private Gift giftID;
    
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

    public Giftagent() {
    }

    public Giftagent(Integer giftAgentID) {
        this.giftAgentID = giftAgentID;
    }

    public Giftagent(Integer giftAgentID, Date timestampCreated, String role) {
        super(timestampCreated);
        this.giftAgentID = giftAgentID; 
        this.role = role;
    }

    public Integer getGiftAgentID() {
        return giftAgentID;
    }

    public void setGiftAgentID(Integer giftAgentID) {
        this.giftAgentID = giftAgentID;
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

    public Gift getGiftID() {
        return giftID;
    }

    public void setGiftID(Gift giftID) {
        this.giftID = giftID;
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
        hash += (giftAgentID != null ? giftAgentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giftagent)) {
            return false;
        }
        Giftagent other = (Giftagent) object;
        if ((this.giftAgentID == null && other.giftAgentID != null) || (this.giftAgentID != null && !this.giftAgentID.equals(other.giftAgentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Giftagent[ giftAgentID=" + giftAgentID + " ]";
    }
    
}

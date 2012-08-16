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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Giftagent.findByGiftAgentID", query = "SELECT g FROM Giftagent g WHERE g.giftAgentId = :giftAgentID"),
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
    private Integer giftAgentId;
      
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
    private Gift gift;
    
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

    public Giftagent() {
    }

    public Giftagent(Integer giftAgentId) {
        this.giftAgentId = giftAgentId;
    }

    public Giftagent(Integer giftAgentId, Date timestampCreated, String role) {
        super(timestampCreated);
        this.giftAgentId = giftAgentId; 
        this.role = role;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (giftAgentId != null) ? giftAgentId.toString() : "0";
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

    @NotNull(message="Agent must be specified.")
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

    @NotNull(message="Gift must be specified.")
    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Integer getGiftAgentId() {
        return giftAgentId;
    }

    public void setGiftAgentId(Integer giftAgentId) {
        this.giftAgentId = giftAgentId;
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
        hash += (giftAgentId != null ? giftAgentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giftagent)) {
            return false;
        }
        Giftagent other = (Giftagent) object;
        if ((this.giftAgentId == null && other.giftAgentId != null) || (this.giftAgentId != null && !this.giftAgentId.equals(other.giftAgentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Giftagent[ giftAgentID=" + giftAgentId + " ]";
    }
 
}

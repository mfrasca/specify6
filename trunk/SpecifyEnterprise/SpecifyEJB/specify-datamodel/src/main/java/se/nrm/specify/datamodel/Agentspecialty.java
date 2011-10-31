package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "agentspecialty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agentspecialty.findAll", query = "SELECT a FROM Agentspecialty a"),
    @NamedQuery(name = "Agentspecialty.findByAgentSpecialtyID", query = "SELECT a FROM Agentspecialty a WHERE a.agentSpecialtyID = :agentSpecialtyID"),
    @NamedQuery(name = "Agentspecialty.findByTimestampCreated", query = "SELECT a FROM Agentspecialty a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Agentspecialty.findByTimestampModified", query = "SELECT a FROM Agentspecialty a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Agentspecialty.findByVersion", query = "SELECT a FROM Agentspecialty a WHERE a.version = :version"),
    @NamedQuery(name = "Agentspecialty.findByOrderNumber", query = "SELECT a FROM Agentspecialty a WHERE a.orderNumber = :orderNumber"),
    @NamedQuery(name = "Agentspecialty.findBySpecialtyName", query = "SELECT a FROM Agentspecialty a WHERE a.specialtyName = :specialtyName")})
public class Agentspecialty extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AgentSpecialtyID")
    private Integer agentSpecialtyID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderNumber")
    private int orderNumber;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "SpecialtyName")
    private String specialtyName;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Agentspecialty() {
    }

    public Agentspecialty(Integer agentSpecialtyID) {
        this.agentSpecialtyID = agentSpecialtyID;
    }

    public Agentspecialty(Integer agentSpecialtyID, Date timestampCreated, int orderNumber, String specialtyName) {
        super(timestampCreated);
        this.agentSpecialtyID = agentSpecialtyID; 
        this.orderNumber = orderNumber;
        this.specialtyName = specialtyName;
    }

    public Integer getAgentSpecialtyID() {
        return agentSpecialtyID;
    }

    public void setAgentSpecialtyID(Integer agentSpecialtyID) {
        this.agentSpecialtyID = agentSpecialtyID;
    } 
    
    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
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
        hash += (agentSpecialtyID != null ? agentSpecialtyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agentspecialty)) {
            return false;
        }
        Agentspecialty other = (Agentspecialty) object;
        if ((this.agentSpecialtyID == null && other.agentSpecialtyID != null) || (this.agentSpecialtyID != null && !this.agentSpecialtyID.equals(other.agentSpecialtyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Agentspecialty[ agentSpecialtyID=" + agentSpecialtyID + " ]";
    }
    
}

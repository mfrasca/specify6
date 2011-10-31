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
@Table(name = "collector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collector.findAll", query = "SELECT c FROM Collector c"),
    @NamedQuery(name = "Collector.findByCollectorID", query = "SELECT c FROM Collector c WHERE c.collectorID = :collectorID"),
    @NamedQuery(name = "Collector.findByCollectingEventID", query = "SELECT c FROM Collector c WHERE c.collectingEventID in :collectingEventID"),
    @NamedQuery(name = "Collector.findByTimestampCreated", query = "SELECT c FROM Collector c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collector.findByTimestampModified", query = "SELECT c FROM Collector c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collector.findByVersion", query = "SELECT c FROM Collector c WHERE c.version = :version"),
    @NamedQuery(name = "Collector.findByIsPrimary", query = "SELECT c FROM Collector c WHERE c.isPrimary = :isPrimary"),
    @NamedQuery(name = "Collector.findByOrderNumber", query = "SELECT c FROM Collector c WHERE c.orderNumber = :orderNumber")})
public class Collector extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectorID")
    private Integer collectorID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPrimary")
    private boolean isPrimary;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderNumber")
    private int orderNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "CollectingEventID", referencedColumnName = "CollectingEventID")
    @ManyToOne(optional = false)
    private Collectingevent collectingEventID;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Division divisionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Collector() {
    }

    public Collector(Integer collectorID) {
        this.collectorID = collectorID;
    }

    public Collector(Integer collectorID, Date timestampCreated, boolean isPrimary, int orderNumber) {
        super(timestampCreated);
        this.collectorID = collectorID; 
        this.isPrimary = isPrimary;
        this.orderNumber = orderNumber;
    }

    public Integer getCollectorID() {
        return collectorID;
    }

    public void setCollectorID(Integer collectorID) {
        this.collectorID = collectorID;
    }
 
    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Collectingevent getCollectingEventID() {
        return collectingEventID;
    }

    public void setCollectingEventID(Collectingevent collectingEventID) {
        this.collectingEventID = collectingEventID;
    }

    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
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
        hash += (collectorID != null ? collectorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collector)) {
            return false;
        }
        Collector other = (Collector) object;
        if ((this.collectorID == null && other.collectorID != null) || (this.collectorID != null && !this.collectorID.equals(other.collectorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collector[ collectorID=" + collectorID + " ]";
    }
    
}

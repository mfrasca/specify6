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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collector")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collector.findAll", query = "SELECT c FROM Collector c"),
    @NamedQuery(name = "Collector.findByCollectorID", query = "SELECT c FROM Collector c WHERE c.collectorId = :collectorID"),
    @NamedQuery(name = "Collector.findByCollectingEventID", query = "SELECT c FROM Collector c WHERE c.collectingEvent in :collectingEventID"),
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
    private Integer collectorId;
     
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
    private Collectingevent collectingEvent;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Division division;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agent;

    public Collector() {
    }

    public Collector(Integer collectorId) {
        this.collectorId = collectorId;
    }

    public Collector(Integer collectorId, Date timestampCreated, boolean isPrimary, int orderNumber) {
        super(timestampCreated);
        this.collectorId = collectorId; 
        this.isPrimary = isPrimary;
        this.orderNumber = orderNumber;
    }

    public Integer getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Integer collectorId) {
        this.collectorId = collectorId;
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @XmlTransient
    public Collectingevent getCollectingEvent() {
        return collectingEvent;
    }

    public void setCollectingEvent(Collectingevent collectingEvent) {
        this.collectingEvent = collectingEvent;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
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
        hash += (collectorId != null ? collectorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collector)) {
            return false;
        }
        Collector other = (Collector) object;
        if ((this.collectorId == null && other.collectorId != null) || (this.collectorId != null && !this.collectorId.equals(other.collectorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collector[ collectorID=" + collectorId + " ]";
    }
    
}

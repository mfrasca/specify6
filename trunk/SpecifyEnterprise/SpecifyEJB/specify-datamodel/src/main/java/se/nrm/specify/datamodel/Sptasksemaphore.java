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
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "sptasksemaphore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sptasksemaphore.findAll", query = "SELECT s FROM Sptasksemaphore s"),
    @NamedQuery(name = "Sptasksemaphore.findByTaskSemaphoreId", query = "SELECT s FROM Sptasksemaphore s WHERE s.spTaskSemaphoreId = :taskSemaphoreId"),
    @NamedQuery(name = "Sptasksemaphore.findByTimestampCreated", query = "SELECT s FROM Sptasksemaphore s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Sptasksemaphore.findByTimestampModified", query = "SELECT s FROM Sptasksemaphore s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Sptasksemaphore.findByVersion", query = "SELECT s FROM Sptasksemaphore s WHERE s.version = :version"),
    @NamedQuery(name = "Sptasksemaphore.findByContext", query = "SELECT s FROM Sptasksemaphore s WHERE s.context = :context"),
    @NamedQuery(name = "Sptasksemaphore.findByIsLocked", query = "SELECT s FROM Sptasksemaphore s WHERE s.isLocked = :isLocked"),
    @NamedQuery(name = "Sptasksemaphore.findByLockedTime", query = "SELECT s FROM Sptasksemaphore s WHERE s.lockedTime = :lockedTime"),
    @NamedQuery(name = "Sptasksemaphore.findByMachineName", query = "SELECT s FROM Sptasksemaphore s WHERE s.machineName = :machineName"),
    @NamedQuery(name = "Sptasksemaphore.findByScope", query = "SELECT s FROM Sptasksemaphore s WHERE s.scope = :scope"),
    @NamedQuery(name = "Sptasksemaphore.findByTaskName", query = "SELECT s FROM Sptasksemaphore s WHERE s.taskName = :taskName"),
    @NamedQuery(name = "Sptasksemaphore.findByUsageCount", query = "SELECT s FROM Sptasksemaphore s WHERE s.usageCount = :usageCount")})
public class Sptasksemaphore extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TaskSemaphoreID")
    private Integer spTaskSemaphoreId;
     
    @Size(max = 32)
    @Column(name = "Context")
    private String context;
    
    @Column(name = "IsLocked")
    private Boolean isLocked;
    
    @Size(max = 64)
    @Column(name = "MachineName")
    private String machineName;
    
    @Column(name = "Scope")
    private Short scope;
    
    @Size(max = 32)
    @Column(name = "TaskName")
    private String taskName;
    
    @Column(name = "UsageCount")
    private Integer usageCount;
    
    @Column(name = "LockedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedTime;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Collection collection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "OwnerID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser owner;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline discipline;

    public Sptasksemaphore() {
    }

    public Sptasksemaphore(Integer spTaskSemaphoreId) {
        this.spTaskSemaphoreId = spTaskSemaphoreId;
    }

    public Sptasksemaphore(Integer spTaskSemaphoreId, Date timestampCreated) {
        super(timestampCreated);
        this.spTaskSemaphoreId = spTaskSemaphoreId; 
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spTaskSemaphoreId != null) ? spTaskSemaphoreId.toString() : "0";
    }
    
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public Short getScope() {
        return scope;
    }

    public void setScope(Short scope) {
        this.scope = scope;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
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

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Specifyuser getOwner() {
        return owner;
    }

    public void setOwner(Specifyuser owner) {
        this.owner = owner;
    }

    public Integer getSpTaskSemaphoreId() {
        return spTaskSemaphoreId;
    }

    public void setSpTaskSemaphoreId(Integer spTaskSemaphoreId) {
        this.spTaskSemaphoreId = spTaskSemaphoreId;
    }
 
    public Date getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Date lockedTime) {
        this.lockedTime = lockedTime;
    }
    
    @Override
    public String getEntityName() {
        return "spTaskSemaphore";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spTaskSemaphoreId != null ? spTaskSemaphoreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sptasksemaphore)) {
            return false;
        }
        Sptasksemaphore other = (Sptasksemaphore) object;
        if ((this.spTaskSemaphoreId == null && other.spTaskSemaphoreId != null) || (this.spTaskSemaphoreId != null && !this.spTaskSemaphoreId.equals(other.spTaskSemaphoreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sptasksemaphore[ taskSemaphoreID=" + spTaskSemaphoreId + " ]";
    }
   
}

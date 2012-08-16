package se.nrm.specify.datamodel;
 
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.NotNull; 
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spauditlog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spauditlog.findAll", query = "SELECT s FROM Spauditlog s"),
    @NamedQuery(name = "Spauditlog.findBySpAuditLogID", query = "SELECT s FROM Spauditlog s WHERE s.spAuditLogId = :spAuditLogID"),
    @NamedQuery(name = "Spauditlog.findByTimestampCreated", query = "SELECT s FROM Spauditlog s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spauditlog.findByTimestampModified", query = "SELECT s FROM Spauditlog s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spauditlog.findByVersion", query = "SELECT s FROM Spauditlog s WHERE s.version = :version"),
    @NamedQuery(name = "Spauditlog.findByAction", query = "SELECT s FROM Spauditlog s WHERE s.action = :action"), 
    @NamedQuery(name = "Spauditlog.findByRecordId", query = "SELECT s FROM Spauditlog s WHERE s.recordId = :recordId"),
    @NamedQuery(name = "Spauditlog.findByTableNum", query = "SELECT s FROM Spauditlog s WHERE s.tableNum = :tableNum"),
    @NamedQuery(name = "Spauditlog.findByParentRecordId", query = "SELECT s FROM Spauditlog s WHERE s.parentRecordId = :parentRecordId"),
    @NamedQuery(name = "Spauditlog.findByParentTableNum", query = "SELECT s FROM Spauditlog s WHERE s.parentTableNum = :parentTableNum"),
    @NamedQuery(name = "Spauditlog.findByRecordVersion", query = "SELECT s FROM Spauditlog s WHERE s.recordVersion = :recordVersion")})
public class Spauditlog extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpAuditLogID")
    private Integer spAuditLogId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "Action")
    private int action;
      
    @Column(name = "RecordId")
    private Integer recordId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TableNum")
    private int tableNum;
    
    @Column(name = "ParentRecordId")
    private Integer parentRecordId;
    
    @Column(name = "ParentTableNum")
    private Short parentTableNum;
    
    @Column(name = "RecordVersion")
    private Integer recordVersion;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "spAuditLog")
    private Collection<Spauditlogfield> fields;

    public Spauditlog() {
    }

    public Spauditlog(Integer spAuditLogId) {
        this.spAuditLogId = spAuditLogId;
    }

    public Spauditlog(Integer spAuditLogId, Date timestampCreated, int action, int tableNum) {
        super(timestampCreated);
        this.spAuditLogId = spAuditLogId; 
        this.action = action; 
        this.tableNum = tableNum;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spAuditLogId != null) ? spAuditLogId.toString() : "0";
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Spauditlogfield> getFields() {
        return fields;
    }

    public void setFields(Collection<Spauditlogfield> fields) {
        this.fields = fields;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getSpAuditLogId() {
        return spAuditLogId;
    }

    public void setSpAuditLogId(Integer spAuditLogId) {
        this.spAuditLogId = spAuditLogId;
    }
 
    
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
  
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public Integer getParentRecordId() {
        return parentRecordId;
    }

    public void setParentRecordId(Integer parentRecordId) {
        this.parentRecordId = parentRecordId;
    }

    public Short getParentTableNum() {
        return parentTableNum;
    }

    public void setParentTableNum(Short parentTableNum) {
        this.parentTableNum = parentTableNum;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAuditLogId != null ? spAuditLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spauditlog)) {
            return false;
        }
        Spauditlog other = (Spauditlog) object;
        if ((this.spAuditLogId == null && other.spAuditLogId != null) || (this.spAuditLogId != null && !this.spAuditLogId.equals(other.spAuditLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spauditlog[ spAuditLogID=" + spAuditLogId + " ]";
    }
 
    
}

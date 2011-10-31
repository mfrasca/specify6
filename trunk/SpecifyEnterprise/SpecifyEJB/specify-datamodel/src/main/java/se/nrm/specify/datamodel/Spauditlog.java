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
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Spauditlog.findBySpAuditLogID", query = "SELECT s FROM Spauditlog s WHERE s.spAuditLogID = :spAuditLogID"),
    @NamedQuery(name = "Spauditlog.findByTimestampCreated", query = "SELECT s FROM Spauditlog s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spauditlog.findByTimestampModified", query = "SELECT s FROM Spauditlog s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spauditlog.findByVersion", query = "SELECT s FROM Spauditlog s WHERE s.version = :version"),
    @NamedQuery(name = "Spauditlog.findByAction", query = "SELECT s FROM Spauditlog s WHERE s.action = :action"),
    @NamedQuery(name = "Spauditlog.findByDescription", query = "SELECT s FROM Spauditlog s WHERE s.description = :description"),
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
    private Integer spAuditLogID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "Action")
    private int action;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Description")
    private String description;
    
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
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "spAuditLogID")
    private Collection<Spauditlogfield> spauditlogfieldCollection;

    public Spauditlog() {
    }

    public Spauditlog(Integer spAuditLogID) {
        this.spAuditLogID = spAuditLogID;
    }

    public Spauditlog(Integer spAuditLogID, Date timestampCreated, int action, String description, int tableNum) {
        super(timestampCreated);
        this.spAuditLogID = spAuditLogID; 
        this.action = action;
        this.description = description;
        this.tableNum = tableNum;
    }

    public Integer getSpAuditLogID() {
        return spAuditLogID;
    }

    public void setSpAuditLogID(Integer spAuditLogID) {
        this.spAuditLogID = spAuditLogID;
    } 
    
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @XmlTransient
    public Collection<Spauditlogfield> getSpauditlogfieldCollection() {
        return spauditlogfieldCollection;
    }

    public void setSpauditlogfieldCollection(Collection<Spauditlogfield> spauditlogfieldCollection) {
        this.spauditlogfieldCollection = spauditlogfieldCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAuditLogID != null ? spAuditLogID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spauditlog)) {
            return false;
        }
        Spauditlog other = (Spauditlog) object;
        if ((this.spAuditLogID == null && other.spAuditLogID != null) || (this.spAuditLogID != null && !this.spAuditLogID.equals(other.spAuditLogID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spauditlog[ spAuditLogID=" + spAuditLogID + " ]";
    }
    
}

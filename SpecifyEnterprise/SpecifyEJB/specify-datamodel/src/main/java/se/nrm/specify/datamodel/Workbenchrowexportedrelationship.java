package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "workbenchrowexportedrelationship")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchrowexportedrelationship.findAll", query = "SELECT w FROM Workbenchrowexportedrelationship w"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByWorkbenchRowExportedRelationshipID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.workbenchRowExportedRelationshipID = :workbenchRowExportedRelationshipID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByTimestampCreated", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByTimestampModified", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.timestampModified = :timestampModified"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByVersion", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.version = :version"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByRecordID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.recordID = :recordID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByRelationshipName", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.relationshipName = :relationshipName"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findBySequence", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.sequence = :sequence"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByTableName", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.tableName = :tableName"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByModifiedByAgentID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.modifiedByAgentID = :modifiedByAgentID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByWorkbenchRowID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.workbenchRowID = :workbenchRowID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByCreatedByAgentID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.createdByAgentID = :createdByAgentID")})
public class Workbenchrowexportedrelationship extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchRowExportedRelationshipID")
    private Integer workbenchRowExportedRelationshipID;
     
    @Column(name = "RecordID")
    private Integer recordID;
    
    @Size(max = 120)
    @Column(name = "RelationshipName")
    private String relationshipName;
    
    @Column(name = "Sequence")
    private Integer sequence;
    
    @Size(max = 120)
    @Column(name = "TableName")
    private String tableName;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "WorkbenchRowID")
    private int workbenchRowID;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentID;

    public Workbenchrowexportedrelationship() {
    }

    public Workbenchrowexportedrelationship(Integer workbenchRowExportedRelationshipID) {
        this.workbenchRowExportedRelationshipID = workbenchRowExportedRelationshipID;
    }

    public Workbenchrowexportedrelationship(Integer workbenchRowExportedRelationshipID, Date timestampCreated, int workbenchRowID) {
        super(timestampCreated);
        this.workbenchRowExportedRelationshipID = workbenchRowExportedRelationshipID; 
        this.workbenchRowID = workbenchRowID;
    }

    public Integer getWorkbenchRowExportedRelationshipID() {
        return workbenchRowExportedRelationshipID;
    }

    public void setWorkbenchRowExportedRelationshipID(Integer workbenchRowExportedRelationshipID) {
        this.workbenchRowExportedRelationshipID = workbenchRowExportedRelationshipID;
    }
 
    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Integer modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public int getWorkbenchRowID() {
        return workbenchRowID;
    }

    public void setWorkbenchRowID(int workbenchRowID) {
        this.workbenchRowID = workbenchRowID;
    }

    public Integer getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Integer createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchRowExportedRelationshipID != null ? workbenchRowExportedRelationshipID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchrowexportedrelationship)) {
            return false;
        }
        Workbenchrowexportedrelationship other = (Workbenchrowexportedrelationship) object;
        if ((this.workbenchRowExportedRelationshipID == null && other.workbenchRowExportedRelationshipID != null) || (this.workbenchRowExportedRelationshipID != null && !this.workbenchRowExportedRelationshipID.equals(other.workbenchRowExportedRelationshipID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workbenchrowexportedrelationship[ workbenchRowExportedRelationshipID=" + workbenchRowExportedRelationshipID + " ]";
    }
    
}

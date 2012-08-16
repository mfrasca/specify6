package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
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
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByWorkbenchRowExportedRelationshipID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.workbenchRowExportedRelationshipId = :workbenchRowExportedRelationshipID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByTimestampCreated", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByTimestampModified", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.timestampModified = :timestampModified"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByVersion", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.version = :version"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByRecordID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.recordId = :recordID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByRelationshipName", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.relationshipName = :relationshipName"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findBySequence", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.sequence = :sequence"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByTableName", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.tableName = :tableName"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByModifiedByAgentID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.modifiedByAgent = :modifiedByAgentID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByWorkbenchRowID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.workbenchRow = :workbenchRowID"),
    @NamedQuery(name = "Workbenchrowexportedrelationship.findByCreatedByAgentID", query = "SELECT w FROM Workbenchrowexportedrelationship w WHERE w.createdByAgent = :createdByAgentID")})
public class Workbenchrowexportedrelationship extends BaseEntity {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchRowExportedRelationshipID")
    private Integer workbenchRowExportedRelationshipId;
     
    @Column(name = "RecordID")
    private Integer recordId;
    
    @Size(max = 120)
    @Column(name = "RelationshipName")
    private String relationshipName;
    
    @Column(name = "Sequence")
    private Integer sequence;
    
    @Size(max = 120)
    @Column(name = "TableName")
    private String tableName;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgent;
    
    @Basic(optional = false) 
    @Column(name = "WorkbenchRowID")
    private int workbenchRow;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgent;

    public Workbenchrowexportedrelationship() {
    }

    public Workbenchrowexportedrelationship(Integer workbenchRowExportedRelationshipId) {
        this.workbenchRowExportedRelationshipId = workbenchRowExportedRelationshipId;
    }

    public Workbenchrowexportedrelationship(Integer workbenchRowExportedRelationshipId, Date timestampCreated, int workbenchRow) {
        super(timestampCreated);
        this.workbenchRowExportedRelationshipId = workbenchRowExportedRelationshipId; 
        this.workbenchRow = workbenchRow;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (workbenchRowExportedRelationshipId != null) ? workbenchRowExportedRelationshipId.toString() : "0";
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
 
    public Integer getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Integer createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
     
    public Integer getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Integer modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @NotNull(message="WorkbenchRow must be specified.")
    public int getWorkbenchRow() {
        return workbenchRow;
    }

    public void setWorkbenchRow(int workbenchRow) {
        this.workbenchRow = workbenchRow;
    }

    public Integer getWorkbenchRowExportedRelationshipId() {
        return workbenchRowExportedRelationshipId;
    }

    public void setWorkbenchRowExportedRelationshipId(Integer workbenchRowExportedRelationshipId) {
        this.workbenchRowExportedRelationshipId = workbenchRowExportedRelationshipId;
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchRowExportedRelationshipId != null ? workbenchRowExportedRelationshipId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchrowexportedrelationship)) {
            return false;
        }
        Workbenchrowexportedrelationship other = (Workbenchrowexportedrelationship) object;
        if ((this.workbenchRowExportedRelationshipId == null && other.workbenchRowExportedRelationshipId != null) || (this.workbenchRowExportedRelationshipId != null && !this.workbenchRowExportedRelationshipId.equals(other.workbenchRowExportedRelationshipId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workbenchrowexportedrelationship[ workbenchRowExportedRelationshipID=" + workbenchRowExportedRelationshipId + " ]";
    }
 
}

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
@Table(name = "spfieldvaluedefault")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spfieldvaluedefault.findAll", query = "SELECT s FROM Spfieldvaluedefault s"),
    @NamedQuery(name = "Spfieldvaluedefault.findBySpFieldValueDefaultID", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.spFieldValueDefaultID = :spFieldValueDefaultID"),
    @NamedQuery(name = "Spfieldvaluedefault.findByTimestampCreated", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spfieldvaluedefault.findByTimestampModified", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spfieldvaluedefault.findByVersion", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.version = :version"),
    @NamedQuery(name = "Spfieldvaluedefault.findByCollectionMemberID", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Spfieldvaluedefault.findByFieldName", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.fieldName = :fieldName"),
    @NamedQuery(name = "Spfieldvaluedefault.findByIdValue", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.idValue = :idValue"),
    @NamedQuery(name = "Spfieldvaluedefault.findByStrValue", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.strValue = :strValue"),
    @NamedQuery(name = "Spfieldvaluedefault.findByTableName", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.tableName = :tableName")})
public class Spfieldvaluedefault extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpFieldValueDefaultID")
    private Integer spFieldValueDefaultID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Size(max = 32)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Column(name = "IdValue")
    private Integer idValue;
    
    @Size(max = 64)
    @Column(name = "StrValue")
    private String strValue;
    
    @Size(max = 32)
    @Column(name = "TableName")
    private String tableName;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Spfieldvaluedefault() {
    }

    public Spfieldvaluedefault(Integer spFieldValueDefaultID) {
        this.spFieldValueDefaultID = spFieldValueDefaultID;
    }

    public Spfieldvaluedefault(Integer spFieldValueDefaultID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.spFieldValueDefaultID = spFieldValueDefaultID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getSpFieldValueDefaultID() {
        return spFieldValueDefaultID;
    }

    public void setSpFieldValueDefaultID(Integer spFieldValueDefaultID) {
        this.spFieldValueDefaultID = spFieldValueDefaultID;
    } 

    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getIdValue() {
        return idValue;
    }

    public void setIdValue(Integer idValue) {
        this.idValue = idValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spFieldValueDefaultID != null ? spFieldValueDefaultID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spfieldvaluedefault)) {
            return false;
        }
        Spfieldvaluedefault other = (Spfieldvaluedefault) object;
        if ((this.spFieldValueDefaultID == null && other.spFieldValueDefaultID != null) || (this.spFieldValueDefaultID != null && !this.spFieldValueDefaultID.equals(other.spFieldValueDefaultID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spfieldvaluedefault[ spFieldValueDefaultID=" + spFieldValueDefaultID + " ]";
    }
    
}

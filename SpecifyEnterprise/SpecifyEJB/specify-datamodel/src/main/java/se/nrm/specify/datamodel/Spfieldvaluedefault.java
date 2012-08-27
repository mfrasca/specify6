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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Spfieldvaluedefault.findBySpFieldValueDefaultId", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.spFieldValueDefaultId = :spFieldValueDefaultId"),
    @NamedQuery(name = "Spfieldvaluedefault.findByTimestampCreated", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spfieldvaluedefault.findByTimestampModified", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spfieldvaluedefault.findByVersion", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.version = :version"),
    @NamedQuery(name = "Spfieldvaluedefault.findByCollectionMemberID", query = "SELECT s FROM Spfieldvaluedefault s WHERE s.collectionMemberId = :collectionMemberID"),
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
    private Integer spFieldValueDefaultId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
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
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Spfieldvaluedefault() {
    }

    public Spfieldvaluedefault(Integer spFieldValueDefaultId) {
        this.spFieldValueDefaultId = spFieldValueDefaultId;
    }

    public Spfieldvaluedefault(Integer spFieldValueDefaultId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.spFieldValueDefaultId = spFieldValueDefaultId; 
        this.collectionMemberId = collectionMemberId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spFieldValueDefaultId != null) ? spFieldValueDefaultId.toString() : "0";
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getSpFieldValueDefaultId() {
        return spFieldValueDefaultId;
    }

    public void setSpFieldValueDefaultId(Integer spFieldValueDefaultId) {
        this.spFieldValueDefaultId = spFieldValueDefaultId;
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

    @Override
    public String getEntityName() {
        return "spFieldValueDefault";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spFieldValueDefaultId != null ? spFieldValueDefaultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spfieldvaluedefault)) {
            return false;
        }
        Spfieldvaluedefault other = (Spfieldvaluedefault) object;
        if ((this.spFieldValueDefaultId == null && other.spFieldValueDefaultId != null) || (this.spFieldValueDefaultId != null && !this.spFieldValueDefaultId.equals(other.spFieldValueDefaultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spfieldvaluedefault[ spFieldValueDefaultID=" + spFieldValueDefaultId + " ]";
    }
 
}

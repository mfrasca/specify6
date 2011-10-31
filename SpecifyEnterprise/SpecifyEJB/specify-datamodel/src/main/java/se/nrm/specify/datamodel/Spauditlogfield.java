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
@Table(name = "spauditlogfield")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spauditlogfield.findAll", query = "SELECT s FROM Spauditlogfield s"),
    @NamedQuery(name = "Spauditlogfield.findBySpAuditLogFieldID", query = "SELECT s FROM Spauditlogfield s WHERE s.spAuditLogFieldID = :spAuditLogFieldID"),
    @NamedQuery(name = "Spauditlogfield.findByTimestampCreated", query = "SELECT s FROM Spauditlogfield s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spauditlogfield.findByTimestampModified", query = "SELECT s FROM Spauditlogfield s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spauditlogfield.findByVersion", query = "SELECT s FROM Spauditlogfield s WHERE s.version = :version"),
    @NamedQuery(name = "Spauditlogfield.findByFieldName", query = "SELECT s FROM Spauditlogfield s WHERE s.fieldName = :fieldName"),
    @NamedQuery(name = "Spauditlogfield.findByNewValue", query = "SELECT s FROM Spauditlogfield s WHERE s.newValue = :newValue"),
    @NamedQuery(name = "Spauditlogfield.findByOldValue", query = "SELECT s FROM Spauditlogfield s WHERE s.oldValue = :oldValue")})
public class Spauditlogfield extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpAuditLogFieldID")
    private Integer spAuditLogFieldID;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "NewValue")
    private String newValue;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "OldValue")
    private String oldValue;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "SpAuditLogID", referencedColumnName = "SpAuditLogID")
    @ManyToOne
    private Spauditlog spAuditLogID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Spauditlogfield() {
    }

    public Spauditlogfield(Integer spAuditLogFieldID) {
        this.spAuditLogFieldID = spAuditLogFieldID;
    }

    public Spauditlogfield(Integer spAuditLogFieldID, Date timestampCreated, String fieldName, String newValue, String oldValue) {
        super(timestampCreated);
        this.spAuditLogFieldID = spAuditLogFieldID; 
        this.fieldName = fieldName;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    public Integer getSpAuditLogFieldID() {
        return spAuditLogFieldID;
    }

    public void setSpAuditLogFieldID(Integer spAuditLogFieldID) {
        this.spAuditLogFieldID = spAuditLogFieldID;
    }
 
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Spauditlog getSpAuditLogID() {
        return spAuditLogID;
    }

    public void setSpAuditLogID(Spauditlog spAuditLogID) {
        this.spAuditLogID = spAuditLogID;
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
        hash += (spAuditLogFieldID != null ? spAuditLogFieldID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spauditlogfield)) {
            return false;
        }
        Spauditlogfield other = (Spauditlogfield) object;
        if ((this.spAuditLogFieldID == null && other.spAuditLogFieldID != null) || (this.spAuditLogFieldID != null && !this.spAuditLogFieldID.equals(other.spAuditLogFieldID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spauditlogfield[ spAuditLogFieldID=" + spAuditLogFieldID + " ]";
    }
    
}

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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spversion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spversion.findAll", query = "SELECT s FROM Spversion s"),
    @NamedQuery(name = "Spversion.findBySpVersionID", query = "SELECT s FROM Spversion s WHERE s.spVersionID = :spVersionID"),
    @NamedQuery(name = "Spversion.findByTimestampCreated", query = "SELECT s FROM Spversion s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spversion.findByTimestampModified", query = "SELECT s FROM Spversion s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spversion.findByVersion", query = "SELECT s FROM Spversion s WHERE s.version = :version"),
    @NamedQuery(name = "Spversion.findByAppName", query = "SELECT s FROM Spversion s WHERE s.appName = :appName"),
    @NamedQuery(name = "Spversion.findByAppVersion", query = "SELECT s FROM Spversion s WHERE s.appVersion = :appVersion"),
    @NamedQuery(name = "Spversion.findBySchemaVersion", query = "SELECT s FROM Spversion s WHERE s.schemaVersion = :schemaVersion")})
public class Spversion extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpVersionID")
    private Integer spVersionID;
     
    @Size(max = 32)
    @Column(name = "AppName")
    private String appName;
    
    @Size(max = 16)
    @Column(name = "AppVersion")
    private String appVersion;
    
    @Size(max = 16)
    @Column(name = "SchemaVersion")
    private String schemaVersion;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Spversion() {
    }

    public Spversion(Integer spVersionID) {
        this.spVersionID = spVersionID;
    }

    public Spversion(Integer spVersionID, Date timestampCreated) {
        super(timestampCreated);
        this.spVersionID = spVersionID; 
    }

    public Integer getSpVersionID() {
        return spVersionID;
    }

    public void setSpVersionID(Integer spVersionID) {
        this.spVersionID = spVersionID;
    }
 
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
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
        hash += (spVersionID != null ? spVersionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spversion)) {
            return false;
        }
        Spversion other = (Spversion) object;
        if ((this.spVersionID == null && other.spVersionID != null) || (this.spVersionID != null && !this.spVersionID.equals(other.spVersionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spversion[ spVersionID=" + spVersionID + " ]";
    }
    
}

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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spappresourcedata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spappresourcedata.findAll", query = "SELECT s FROM Spappresourcedata s"),
    @NamedQuery(name = "Spappresourcedata.findBySpAppResourceDataID", query = "SELECT s FROM Spappresourcedata s WHERE s.spAppResourceDataId = :spAppResourceDataID"),
    @NamedQuery(name = "Spappresourcedata.findByTimestampCreated", query = "SELECT s FROM Spappresourcedata s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spappresourcedata.findByTimestampModified", query = "SELECT s FROM Spappresourcedata s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spappresourcedata.findByVersion", query = "SELECT s FROM Spappresourcedata s WHERE s.version = :version")})
public class Spappresourcedata extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpAppResourceDataID")
    private Integer spAppResourceDataId;
    
    @Lob
    @Column(name = "data")
    private byte[] data;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "SpAppResourceID", referencedColumnName = "SpAppResourceID")
    @ManyToOne
    private Spappresource spAppResource;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpViewSetObjID", referencedColumnName = "SpViewSetObjID")
    @ManyToOne
    private Spviewsetobj spViewSetObj;

    public Spappresourcedata() {
    }

    public Spappresourcedata(Integer spAppResourceDataId) {
        this.spAppResourceDataId = spAppResourceDataId;
    }

    public Spappresourcedata(Integer spAppResourceDataId, Date timestampCreated) {
        super(timestampCreated);
        this.spAppResourceDataId = spAppResourceDataId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spAppResourceDataId != null) ? spAppResourceDataId.toString() : "0";
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

    public Spappresource getSpAppResource() {
        return spAppResource;
    }

    public void setSpAppResource(Spappresource spAppResource) {
        this.spAppResource = spAppResource;
    }

    public Integer getSpAppResourceDataId() {
        return spAppResourceDataId;
    }

    public void setSpAppResourceDataId(Integer spAppResourceDataId) {
        this.spAppResourceDataId = spAppResourceDataId;
    }

    public Spviewsetobj getSpViewSetObj() {
        return spViewSetObj;
    }

    public void setSpViewSetObj(Spviewsetobj spViewSetObj) {
        this.spViewSetObj = spViewSetObj;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAppResourceDataId != null ? spAppResourceDataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spappresourcedata)) {
            return false;
        }
        Spappresourcedata other = (Spappresourcedata) object;
        if ((this.spAppResourceDataId == null && other.spAppResourceDataId != null) || (this.spAppResourceDataId != null && !this.spAppResourceDataId.equals(other.spAppResourceDataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spappresourcedata[ spAppResourceDataID=" + spAppResourceDataId + " ]";
    } 
}

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
//import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Spappresourcedata.findBySpAppResourceDataID", query = "SELECT s FROM Spappresourcedata s WHERE s.spAppResourceDataID = :spAppResourceDataID"),
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
    private Integer spAppResourceDataID;
     
    @Lob
    @Column(name = "data")
    private byte[] data;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "SpAppResourceID", referencedColumnName = "SpAppResourceID")
    @ManyToOne
    private Spappresource spAppResourceID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpViewSetObjID", referencedColumnName = "SpViewSetObjID")
    @ManyToOne
    private Spviewsetobj spViewSetObjID;

    public Spappresourcedata() {
    }

    public Spappresourcedata(Integer spAppResourceDataID) {
        this.spAppResourceDataID = spAppResourceDataID;
    }

    public Spappresourcedata(Integer spAppResourceDataID, Date timestampCreated) {
        super(timestampCreated);
        this.spAppResourceDataID = spAppResourceDataID; 
    }

    public Integer getSpAppResourceDataID() {
        return spAppResourceDataID;
    }

    public void setSpAppResourceDataID(Integer spAppResourceDataID) {
        this.spAppResourceDataID = spAppResourceDataID;
    }
 
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Spappresource getSpAppResourceID() {
        return spAppResourceID;
    }

    public void setSpAppResourceID(Spappresource spAppResourceID) {
        this.spAppResourceID = spAppResourceID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Spviewsetobj getSpViewSetObjID() {
        return spViewSetObjID;
    }

    public void setSpViewSetObjID(Spviewsetobj spViewSetObjID) {
        this.spViewSetObjID = spViewSetObjID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAppResourceDataID != null ? spAppResourceDataID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spappresourcedata)) {
            return false;
        }
        Spappresourcedata other = (Spappresourcedata) object;
        if ((this.spAppResourceDataID == null && other.spAppResourceDataID != null) || (this.spAppResourceDataID != null && !this.spAppResourceDataID.equals(other.spAppResourceDataID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spappresourcedata[ spAppResourceDataID=" + spAppResourceDataID + " ]";
    }
    
}

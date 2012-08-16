package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table; 
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "latlonpolygon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Latlonpolygon.findAll", query = "SELECT l FROM Latlonpolygon l"),
    @NamedQuery(name = "Latlonpolygon.findByLatLonPolygonID", query = "SELECT l FROM Latlonpolygon l WHERE l.latLonPolygonId = :latLonPolygonID"),
    @NamedQuery(name = "Latlonpolygon.findByTimestampCreated", query = "SELECT l FROM Latlonpolygon l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Latlonpolygon.findByTimestampModified", query = "SELECT l FROM Latlonpolygon l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Latlonpolygon.findByVersion", query = "SELECT l FROM Latlonpolygon l WHERE l.version = :version"),
    @NamedQuery(name = "Latlonpolygon.findByIsPolyline", query = "SELECT l FROM Latlonpolygon l WHERE l.isPolyline = :isPolyline"),
    @NamedQuery(name = "Latlonpolygon.findByName", query = "SELECT l FROM Latlonpolygon l WHERE l.name = :name"),
    @NamedQuery(name = "Latlonpolygon.findBySpVisualQueryID", query = "SELECT l FROM Latlonpolygon l WHERE l.visualQuery = :spVisualQueryID"),
    @NamedQuery(name = "Latlonpolygon.findByLocalityID", query = "SELECT l FROM Latlonpolygon l WHERE l.locality = :localityID"),
    @NamedQuery(name = "Latlonpolygon.findByCreatedByAgentID", query = "SELECT l FROM Latlonpolygon l WHERE l.createdByAgent = :createdByAgentID"),
    @NamedQuery(name = "Latlonpolygon.findByModifiedByAgentID", query = "SELECT l FROM Latlonpolygon l WHERE l.modifiedByAgent = :modifiedByAgentID")})
public class Latlonpolygon extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LatLonPolygonID")
    private Integer latLonPolygonId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPolyline")
    private boolean isPolyline;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "SpVisualQueryID")
    private Integer visualQuery;
    
    @Column(name = "LocalityID") 
    private Integer locality;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgent;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgent;
     
    public Latlonpolygon() {
    }

    public Latlonpolygon(Integer latLonPolygonId) {
        this.latLonPolygonId = latLonPolygonId;
    }

    public Latlonpolygon(Integer latLonPolygonId, Date timestampCreated, boolean isPolyline, String name) {
        super(timestampCreated);
        this.latLonPolygonId = latLonPolygonId; 
        this.isPolyline = isPolyline;
        this.name = name;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (latLonPolygonId != null) ? latLonPolygonId.toString() : "0";
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPolyline() {
        return isPolyline;
    }

    public void setIsPolyline(boolean isPolyline) {
        this.isPolyline = isPolyline;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Integer createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getLatLonPolygonId() {
        return latLonPolygonId;
    }

    public void setLatLonPolygonId(Integer latLonPolygonId) {
        this.latLonPolygonId = latLonPolygonId;
    }

    public Integer getLocality() {
        return locality;
    }

    public void setLocality(Integer locality) {
        this.locality = locality;
    }

    public Integer getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Integer modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getVisualQuery() {
        return visualQuery;
    }

    public void setVisualQuery(Integer visualQuery) {
        this.visualQuery = visualQuery;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (latLonPolygonId != null ? latLonPolygonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Latlonpolygon)) {
            return false;
        }
        Latlonpolygon other = (Latlonpolygon) object;
        if ((this.latLonPolygonId == null && other.latLonPolygonId != null) || (this.latLonPolygonId != null && !this.latLonPolygonId.equals(other.latLonPolygonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Latlonpolygon[ latLonPolygonID=" + latLonPolygonId + " ]";
    }
 
    
}

package se.nrm.specify.datamodel;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "latlonpolygonpnt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Latlonpolygonpnt.findAll", query = "SELECT l FROM Latlonpolygonpnt l"),
    @NamedQuery(name = "Latlonpolygonpnt.findByLatLonPolygonPntID", query = "SELECT l FROM Latlonpolygonpnt l WHERE l.latLonPolygonPntID = :latLonPolygonPntID"),
    @NamedQuery(name = "Latlonpolygonpnt.findByElevation", query = "SELECT l FROM Latlonpolygonpnt l WHERE l.elevation = :elevation"),
    @NamedQuery(name = "Latlonpolygonpnt.findByLatitude", query = "SELECT l FROM Latlonpolygonpnt l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "Latlonpolygonpnt.findByLongitude", query = "SELECT l FROM Latlonpolygonpnt l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "Latlonpolygonpnt.findByOrdinal", query = "SELECT l FROM Latlonpolygonpnt l WHERE l.ordinal = :ordinal"),
    @NamedQuery(name = "Latlonpolygonpnt.findByLatLonPolygonID", query = "SELECT l FROM Latlonpolygonpnt l WHERE l.latLonPolygonID = :latLonPolygonID")})
public class Latlonpolygonpnt implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LatLonPolygonPntID")
    private Integer latLonPolygonPntID;
    
    @Column(name = "Elevation")
    private Integer elevation;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Latitude")
    private BigDecimal latitude;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Longitude")
    private BigDecimal longitude;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ordinal")
    private int ordinal;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "LatLonPolygonID")
    private int latLonPolygonID;

    public Latlonpolygonpnt() {
    }

    public Latlonpolygonpnt(Integer latLonPolygonPntID) {
        this.latLonPolygonPntID = latLonPolygonPntID;
    }

    public Latlonpolygonpnt(Integer latLonPolygonPntID, BigDecimal latitude, BigDecimal longitude, int ordinal, int latLonPolygonID) {
        this.latLonPolygonPntID = latLonPolygonPntID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordinal = ordinal;
        this.latLonPolygonID = latLonPolygonID;
    }

    public Integer getLatLonPolygonPntID() {
        return latLonPolygonPntID;
    }

    public void setLatLonPolygonPntID(Integer latLonPolygonPntID) {
        this.latLonPolygonPntID = latLonPolygonPntID;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getLatLonPolygonID() {
        return latLonPolygonID;
    }

    public void setLatLonPolygonID(int latLonPolygonID) {
        this.latLonPolygonID = latLonPolygonID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (latLonPolygonPntID != null ? latLonPolygonPntID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Latlonpolygonpnt)) {
            return false;
        }
        Latlonpolygonpnt other = (Latlonpolygonpnt) object;
        if ((this.latLonPolygonPntID == null && other.latLonPolygonPntID != null) || (this.latLonPolygonPntID != null && !this.latLonPolygonPntID.equals(other.latLonPolygonPntID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Latlonpolygonpnt[ latLonPolygonPntID=" + latLonPolygonPntID + " ]";
    }
    
}

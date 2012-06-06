package se.nrm.specify.datamodel;
 
import java.math.BigDecimal;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "geocoorddetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geocoorddetail.findAll", query = "SELECT g FROM Geocoorddetail g"),
    @NamedQuery(name = "Geocoorddetail.findByGeoCoordDetailID", query = "SELECT g FROM Geocoorddetail g WHERE g.geoCoordDetailId = :geoCoordDetailID"),
    @NamedQuery(name = "Geocoorddetail.findByTimestampCreated", query = "SELECT g FROM Geocoorddetail g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geocoorddetail.findByTimestampModified", query = "SELECT g FROM Geocoorddetail g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geocoorddetail.findByVersion", query = "SELECT g FROM Geocoorddetail g WHERE g.version = :version"),
    @NamedQuery(name = "Geocoorddetail.findByGeoRefAccuracyUnits", query = "SELECT g FROM Geocoorddetail g WHERE g.geoRefAccuracyUnits = :geoRefAccuracyUnits"),
    @NamedQuery(name = "Geocoorddetail.findByGeoRefDetDate", query = "SELECT g FROM Geocoorddetail g WHERE g.geoRefDetDate = :geoRefDetDate"),
    @NamedQuery(name = "Geocoorddetail.findByGeoRefDetRef", query = "SELECT g FROM Geocoorddetail g WHERE g.geoRefDetRef = :geoRefDetRef"),
    @NamedQuery(name = "Geocoorddetail.findByGeoRefVerificationStatus", query = "SELECT g FROM Geocoorddetail g WHERE g.geoRefVerificationStatus = :geoRefVerificationStatus"),
    @NamedQuery(name = "Geocoorddetail.findByMaxUncertaintyEst", query = "SELECT g FROM Geocoorddetail g WHERE g.maxUncertaintyEst = :maxUncertaintyEst"),
    @NamedQuery(name = "Geocoorddetail.findByMaxUncertaintyEstUnit", query = "SELECT g FROM Geocoorddetail g WHERE g.maxUncertaintyEstUnit = :maxUncertaintyEstUnit"),
    @NamedQuery(name = "Geocoorddetail.findByNamedPlaceExtent", query = "SELECT g FROM Geocoorddetail g WHERE g.namedPlaceExtent = :namedPlaceExtent"),
    @NamedQuery(name = "Geocoorddetail.findByNoGeoRefBecause", query = "SELECT g FROM Geocoorddetail g WHERE g.noGeoRefBecause = :noGeoRefBecause"),
    @NamedQuery(name = "Geocoorddetail.findByOriginalCoordSystem", query = "SELECT g FROM Geocoorddetail g WHERE g.originalCoordSystem = :originalCoordSystem"),
    @NamedQuery(name = "Geocoorddetail.findByProtocol", query = "SELECT g FROM Geocoorddetail g WHERE g.protocol = :protocol"),
    @NamedQuery(name = "Geocoorddetail.findBySource", query = "SELECT g FROM Geocoorddetail g WHERE g.source = :source"),
    @NamedQuery(name = "Geocoorddetail.findByValidation", query = "SELECT g FROM Geocoorddetail g WHERE g.validation = :validation")})
public class Geocoorddetail extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GeoCoordDetailID")
    private Integer geoCoordDetailId;
      
    @Size(max = 20)
    @Column(name = "GeoRefAccuracyUnits")
    private String geoRefAccuracyUnits;
    
    @Column(name = "GeoRefDetDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date geoRefDetDate;
    
    @Size(max = 100)
    @Column(name = "GeoRefDetRef")
    private String geoRefDetRef;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "GeoRefRemarks")
    private String geoRefRemarks;
    
    @Size(max = 50)
    @Column(name = "GeoRefVerificationStatus")
    private String geoRefVerificationStatus;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MaxUncertaintyEst")
    private BigDecimal maxUncertaintyEst;
    
    @Size(max = 8)
    @Column(name = "MaxUncertaintyEstUnit")
    private String maxUncertaintyEstUnit;
    
    @Column(name = "NamedPlaceExtent")
    private BigDecimal namedPlaceExtent;
    
    @Size(max = 100)
    @Column(name = "NoGeoRefBecause")
    private String noGeoRefBecause;
    
    @Size(max = 32)
    @Column(name = "OriginalCoordSystem")
    private String originalCoordSystem;
    
    @Size(max = 64)
    @Column(name = "Protocol")
    private String protocol;
    
    @Size(max = 64)
    @Column(name = "Source")
    private String source;
    
    @Size(max = 64)
    @Column(name = "Validation")
    private String validation;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne
    private Locality locality;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent geoRefDetBy;

    public Geocoorddetail() {
    }

    public Geocoorddetail(Integer geoCoordDetailId) {
        this.geoCoordDetailId = geoCoordDetailId;
    }

    public Geocoorddetail(Integer geoCoordDetailId, Date timestampCreated) {
        super(timestampCreated);
        this.geoCoordDetailId = geoCoordDetailId; 
    }

 

    public String getGeoRefAccuracyUnits() {
        return geoRefAccuracyUnits;
    }

    public void setGeoRefAccuracyUnits(String geoRefAccuracyUnits) {
        this.geoRefAccuracyUnits = geoRefAccuracyUnits;
    }

    public Date getGeoRefDetDate() {
        return geoRefDetDate;
    }

    public void setGeoRefDetDate(Date geoRefDetDate) {
        this.geoRefDetDate = geoRefDetDate;
    }

    public String getGeoRefDetRef() {
        return geoRefDetRef;
    }

    public void setGeoRefDetRef(String geoRefDetRef) {
        this.geoRefDetRef = geoRefDetRef;
    }

    public String getGeoRefRemarks() {
        return geoRefRemarks;
    }

    public void setGeoRefRemarks(String geoRefRemarks) {
        this.geoRefRemarks = geoRefRemarks;
    }

    public String getGeoRefVerificationStatus() {
        return geoRefVerificationStatus;
    }

    public void setGeoRefVerificationStatus(String geoRefVerificationStatus) {
        this.geoRefVerificationStatus = geoRefVerificationStatus;
    }

    public BigDecimal getMaxUncertaintyEst() {
        return maxUncertaintyEst;
    }

    public void setMaxUncertaintyEst(BigDecimal maxUncertaintyEst) {
        this.maxUncertaintyEst = maxUncertaintyEst;
    }

    public String getMaxUncertaintyEstUnit() {
        return maxUncertaintyEstUnit;
    }

    public void setMaxUncertaintyEstUnit(String maxUncertaintyEstUnit) {
        this.maxUncertaintyEstUnit = maxUncertaintyEstUnit;
    }

    public BigDecimal getNamedPlaceExtent() {
        return namedPlaceExtent;
    }

    public void setNamedPlaceExtent(BigDecimal namedPlaceExtent) {
        this.namedPlaceExtent = namedPlaceExtent;
    }

    public String getNoGeoRefBecause() {
        return noGeoRefBecause;
    }

    public void setNoGeoRefBecause(String noGeoRefBecause) {
        this.noGeoRefBecause = noGeoRefBecause;
    }

    public String getOriginalCoordSystem() {
        return originalCoordSystem;
    }

    public void setOriginalCoordSystem(String originalCoordSystem) {
        this.originalCoordSystem = originalCoordSystem;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getGeoCoordDetailId() {
        return geoCoordDetailId;
    }

    public void setGeoCoordDetailId(Integer geoCoordDetailId) {
        this.geoCoordDetailId = geoCoordDetailId;
    }

    public Agent getGeoRefDetBy() {
        return geoRefDetBy;
    }

    public void setGeoRefDetBy(Agent geoRefDetBy) {
        this.geoRefDetBy = geoRefDetBy;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geoCoordDetailId != null ? geoCoordDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geocoorddetail)) {
            return false;
        }
        Geocoorddetail other = (Geocoorddetail) object;
        if ((this.geoCoordDetailId == null && other.geoCoordDetailId != null) || (this.geoCoordDetailId != null && !this.geoCoordDetailId.equals(other.geoCoordDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geocoorddetail[ geoCoordDetailID=" + geoCoordDetailId + " ]";
    }
    
}

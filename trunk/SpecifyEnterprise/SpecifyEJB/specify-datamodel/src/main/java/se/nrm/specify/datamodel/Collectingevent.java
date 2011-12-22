package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectingevent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectingevent.findAll", query = "SELECT c FROM Collectingevent c"),
    @NamedQuery(name = "Collectingevent.findByCollectingEventID", query = "SELECT c FROM Collectingevent c WHERE c.collectingEventID = :collectingEventID"), 
    @NamedQuery(name = "Collectingevent.findByTimestampCreated", query = "SELECT c FROM Collectingevent c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectingevent.findByTimestampModified", query = "SELECT c FROM Collectingevent c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectingevent.findByVersion", query = "SELECT c FROM Collectingevent c WHERE c.version = :version"),
    @NamedQuery(name = "Collectingevent.findByEndDate", query = "SELECT c FROM Collectingevent c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "Collectingevent.findByEndDatePrecision", query = "SELECT c FROM Collectingevent c WHERE c.endDatePrecision = :endDatePrecision"),
    @NamedQuery(name = "Collectingevent.findByEndDateVerbatim", query = "SELECT c FROM Collectingevent c WHERE c.endDateVerbatim = :endDateVerbatim"),
    @NamedQuery(name = "Collectingevent.findByEndTime", query = "SELECT c FROM Collectingevent c WHERE c.endTime = :endTime"),
    @NamedQuery(name = "Collectingevent.findByLocality", query = "SELECT c FROM Collectingevent c WHERE c.localityID.localityName like :localityName"),
    @NamedQuery(name = "Collectingevent.findByMethod", query = "SELECT c FROM Collectingevent c WHERE c.method = :method"),
    @NamedQuery(name = "Collectingevent.findByStartDate", query = "SELECT c FROM Collectingevent c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "Collectingevent.findByStartDatePrecision", query = "SELECT c FROM Collectingevent c WHERE c.startDatePrecision = :startDatePrecision"),
    @NamedQuery(name = "Collectingevent.findByStartDateVerbatim", query = "SELECT c FROM Collectingevent c WHERE c.startDateVerbatim = :startDateVerbatim"),
    @NamedQuery(name = "Collectingevent.findByStartTime", query = "SELECT c FROM Collectingevent c WHERE c.startTime = :startTime"),
    @NamedQuery(name = "Collectingevent.findByStationFieldNumber", query = "SELECT c FROM Collectingevent c WHERE c.stationFieldNumber = :stationFieldNumber"),
    @NamedQuery(name = "Collectingevent.findByVerbatimDate", query = "SELECT c FROM Collectingevent c WHERE c.verbatimDate = :verbatimDate"),
    @NamedQuery(name = "Collectingevent.findByVisibility", query = "SELECT c FROM Collectingevent c WHERE c.visibility = :visibility")})
public class Collectingevent extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectingEventID")
    private Integer collectingEventID;
     
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "EndDatePrecision")
    private Short endDatePrecision;
    
    @Size(max = 50)
    @Column(name = "EndDateVerbatim")
    private String endDateVerbatim;
    
    @Column(name = "EndTime")
    private Short endTime;
    
    @Size(max = 50)
    @Column(name = "Method")
    private String method;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name = "StartDatePrecision")
    private Short startDatePrecision;
    
    @Size(max = 50)
    @Column(name = "StartDateVerbatim")
    private String startDateVerbatim;
    
    @Column(name = "StartTime")
    private Short startTime;
    
    @Size(max = 50)
    @Column(name = "StationFieldNumber")
    private String stationFieldNumber;
    
    @Size(max = 50)
    @Column(name = "VerbatimDate")
    private String verbatimDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "VerbatimLocality")
    private String verbatimLocality;
    
    @Column(name = "Visibility")
    private Short visibility;
     
    @OneToOne(mappedBy = "collectingEventID")
    private Collector collectorID;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectingEventID")
    private Collection<Collectingeventattr> collectingeventattrCollection;
    
    @JoinColumn(name = "CollectingEventAttributeID", referencedColumnName = "CollectingEventAttributeID")
    @ManyToOne
    private Collectingeventattribute collectingEventAttributeID;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne
    private Locality localityID;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetByID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectingTripID", referencedColumnName = "CollectingTripID")
    @ManyToOne
    private Collectingtrip collectingTripID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;
    
    @OneToMany(mappedBy = "collectingEventID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectingEventID")
    private Collection<Collector> collectorCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectingEventID")
    private Collection<Collectingeventattachment> collectingeventattachmentCollection;

    public Collectingevent() {
    }

    public Collectingevent(Integer collectingEventID) {
        this.collectingEventID = collectingEventID;
    }

    public Collectingevent(Integer collectingEventID, Date timestampCreated) {
        super(timestampCreated);
        this.collectingEventID = collectingEventID; 
    }

    public Integer getCollectingEventID() {
        return collectingEventID;
    }

    public void setCollectingEventID(Integer collectingEventID) {
        this.collectingEventID = collectingEventID;
    } 
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getEndDatePrecision() {
        return endDatePrecision;
    }

    public void setEndDatePrecision(Short endDatePrecision) {
        this.endDatePrecision = endDatePrecision;
    }

    public String getEndDateVerbatim() {
        return endDateVerbatim;
    }

    public void setEndDateVerbatim(String endDateVerbatim) {
        this.endDateVerbatim = endDateVerbatim;
    }

    public Short getEndTime() {
        return endTime;
    }

    public void setEndTime(Short endTime) {
        this.endTime = endTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Short getStartDatePrecision() {
        return startDatePrecision;
    }

    public void setStartDatePrecision(Short startDatePrecision) {
        this.startDatePrecision = startDatePrecision;
    }

    public String getStartDateVerbatim() {
        return startDateVerbatim;
    }

    public void setStartDateVerbatim(String startDateVerbatim) {
        this.startDateVerbatim = startDateVerbatim;
    }

    public Short getStartTime() {
        return startTime;
    }

    public void setStartTime(Short startTime) {
        this.startTime = startTime;
    }

    public String getStationFieldNumber() {
        return stationFieldNumber;
    }

    public void setStationFieldNumber(String stationFieldNumber) {
        this.stationFieldNumber = stationFieldNumber;
    }

    public String getVerbatimDate() {
        return verbatimDate;
    }

    public void setVerbatimDate(String verbatimDate) {
        this.verbatimDate = verbatimDate;
    }

    public String getVerbatimLocality() {
        return verbatimLocality;
    }

    public void setVerbatimLocality(String verbatimLocality) {
        this.verbatimLocality = verbatimLocality;
    }

    public Short getVisibility() {
        return visibility;
    }

    public void setVisibility(Short visibility) {
        this.visibility = visibility;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingeventattrCollection() {
        return collectingeventattrCollection;
    }

    public void setCollectingeventattrCollection(Collection<Collectingeventattr> collectingeventattrCollection) {
        this.collectingeventattrCollection = collectingeventattrCollection;
    }

    public Collectingeventattribute getCollectingEventAttributeID() {
        return collectingEventAttributeID;
    }

    public void setCollectingEventAttributeID(Collectingeventattribute collectingEventAttributeID) {
        this.collectingEventAttributeID = collectingEventAttributeID;
    }

    public Locality getLocalityID() {
        return localityID;
    }

    public void setLocalityID(Locality localityID) {
        this.localityID = localityID;
    }

    public Specifyuser getVisibilitySetByID() {
        return visibilitySetByID;
    }

    public void setVisibilitySetByID(Specifyuser visibilitySetByID) {
        this.visibilitySetByID = visibilitySetByID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Collectingtrip getCollectingTripID() {
        return collectingTripID;
    }

    public void setCollectingTripID(Collectingtrip collectingTripID) {
        this.collectingTripID = collectingTripID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }
    
    @XmlTransient
    public Collector getCollectorID() {
        return collectorID;
    }

    public void setCollectorID(Collector collectorID) {
        this.collectorID = collectorID;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    @XmlTransient
    public Collection<Collector> getCollectorCollection() {
        return collectorCollection;
    }

    public void setCollectorCollection(Collection<Collector> collectorCollection) {
        this.collectorCollection = collectorCollection;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingeventattachmentCollection() {
        return collectingeventattachmentCollection;
    }

    public void setCollectingeventattachmentCollection(Collection<Collectingeventattachment> collectingeventattachmentCollection) {
        this.collectingeventattachmentCollection = collectingeventattachmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectingEventID != null ? collectingEventID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectingevent)) {
            return false;
        }
        Collectingevent other = (Collectingevent) object;
        if ((this.collectingEventID == null && other.collectingEventID != null) || (this.collectingEventID != null && !this.collectingEventID.equals(other.collectingEventID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectingevent[ collectingEventID=" + collectingEventID + " ]";
    }
    
}

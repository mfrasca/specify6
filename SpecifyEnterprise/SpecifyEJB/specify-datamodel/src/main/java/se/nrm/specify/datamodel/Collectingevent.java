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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
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
    @NamedQuery(name = "Collectingevent.findByCollectingEventId", query = "SELECT c FROM Collectingevent c WHERE c.collectingEventId = :collectingEventId"), 
    @NamedQuery(name = "Collectingevent.findByTimestampCreated", query = "SELECT c FROM Collectingevent c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectingevent.findByTimestampModified", query = "SELECT c FROM Collectingevent c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectingevent.findByVersion", query = "SELECT c FROM Collectingevent c WHERE c.version = :version"),
    @NamedQuery(name = "Collectingevent.findByEndDate", query = "SELECT c FROM Collectingevent c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "Collectingevent.findByEndDatePrecision", query = "SELECT c FROM Collectingevent c WHERE c.endDatePrecision = :endDatePrecision"),
    @NamedQuery(name = "Collectingevent.findByEndDateVerbatim", query = "SELECT c FROM Collectingevent c WHERE c.endDateVerbatim = :endDateVerbatim"),
    @NamedQuery(name = "Collectingevent.findByEndTime", query = "SELECT c FROM Collectingevent c WHERE c.endTime = :endTime"),
    @NamedQuery(name = "Collectingevent.findByLocality", query = "SELECT c FROM Collectingevent c WHERE c.locality.localityName like :localityName"),
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
    private Integer collectingEventId;
    
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
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Column(name = "SGRStatus")
    private Short sGRStatus;
    
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
     
    @OneToOne(mappedBy = "collectingEvent", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private Collector collector;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectingEvent")
    private Collection<Collectingeventattr> collectingEventAttrs;
    
    @JoinColumn(name = "CollectingEventAttributeID", referencedColumnName = "CollectingEventAttributeID")
    @ManyToOne
    private Collectingeventattribute collectingEventAttribute;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne
    private Locality locality;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetBy;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectingTripID", referencedColumnName = "CollectingTripID")
    @ManyToOne
    private Collectingtrip collectingTrip;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Discipline discipline;
    
    @OneToMany(mappedBy = "collectingEvent", cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Collection<Collectionobject> collectionObjects;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectingEvent")
    private Collection<Collector> collectors;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectingEvent")
    private Collection<Collectingeventattachment> collectingEventAttachments;

    public Collectingevent() {
    }

    public Collectingevent(Integer collectingEventId) {
        this.collectingEventId = collectingEventId;
    }

    public Collectingevent(Integer collectingEventId, Date timestampCreated) {
        super(timestampCreated);
        this.collectingEventId = collectingEventId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectingEventId != null) ? collectingEventId.toString() : "0";
    }
    
    public Integer getCollectingEventId() {
        return collectingEventId;
    }

    public void setCollectingEventId(Integer collectingEventId) {
        this.collectingEventId = collectingEventId;
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
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Short getSGRStatus() {
        return sGRStatus;
    }

    public void setSGRStatus(Short sGRStatus) {
        this.sGRStatus = sGRStatus;
    }
 
    public Collection<Collectingeventattachment> getCollectingEventAttachments() {
        return collectingEventAttachments;
    }

    public void setCollectingEventAttachments(Collection<Collectingeventattachment> collectingEventAttachments) {
        this.collectingEventAttachments = collectingEventAttachments;
    }

    public Collectingeventattribute getCollectingEventAttribute() {
        return collectingEventAttribute;
    }

    public void setCollectingEventAttribute(Collectingeventattribute collectingEventAttribute) {
        this.collectingEventAttribute = collectingEventAttribute;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingEventAttrs() {
        return collectingEventAttrs;
    }

    public void setCollectingEventAttrs(Collection<Collectingeventattr> collectingEventAttrs) {
        this.collectingEventAttrs = collectingEventAttrs;
    }



    public Collectingtrip getCollectingTrip() {
        return collectingTrip;
    }

    public void setCollectingTrip(Collectingtrip collectingTrip) {
        this.collectingTrip = collectingTrip;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionObjects() {
        return collectionObjects;
    }

    public void setCollectionObjects(Collection<Collectionobject> collectionObjects) {
        this.collectionObjects = collectionObjects;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }
 
    @XmlTransient
    public Collection<Collector> getCollectors() {
        return collectors;
    }

    public void setCollectors(Collection<Collector> collectors) {
        this.collectors = collectors;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
 
    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlIDREF
    public Specifyuser getVisibilitySetBy() { 
        return visibilitySetBy;
    }

    public void setVisibilitySetBy(Specifyuser visibilitySetBy) {
        this.visibilitySetBy = visibilitySetBy;
    }
 
    @Override
    public String getEntityName() {
        return "collectingEvent";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectingEventId != null ? collectingEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectingevent)) {
            return false;
        }
        Collectingevent other = (Collectingevent) object;
        if ((this.collectingEventId == null && other.collectingEventId != null) || (this.collectingEventId != null && !this.collectingEventId.equals(other.collectingEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectingevent[ collectingEventId=" + collectingEventId + " ]";
    } 
}

package se.nrm.specify.datamodel;
 
import java.util.Collection;
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
import javax.persistence.OneToMany;
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
@Table(name = "collectingtrip")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectingtrip.findAll", query = "SELECT c FROM Collectingtrip c"),
    @NamedQuery(name = "Collectingtrip.findByCollectingTripID", query = "SELECT c FROM Collectingtrip c WHERE c.collectingTripID = :collectingTripID"),
    @NamedQuery(name = "Collectingtrip.findByTimestampCreated", query = "SELECT c FROM Collectingtrip c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectingtrip.findByTimestampModified", query = "SELECT c FROM Collectingtrip c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectingtrip.findByVersion", query = "SELECT c FROM Collectingtrip c WHERE c.version = :version"),
    @NamedQuery(name = "Collectingtrip.findByCollectingTripName", query = "SELECT c FROM Collectingtrip c WHERE c.collectingTripName = :collectingTripName"),
    @NamedQuery(name = "Collectingtrip.findByEndDate", query = "SELECT c FROM Collectingtrip c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "Collectingtrip.findByEndDateVerbatim", query = "SELECT c FROM Collectingtrip c WHERE c.endDateVerbatim = :endDateVerbatim"),
    @NamedQuery(name = "Collectingtrip.findByEndTime", query = "SELECT c FROM Collectingtrip c WHERE c.endTime = :endTime"),
    @NamedQuery(name = "Collectingtrip.findBySponsor", query = "SELECT c FROM Collectingtrip c WHERE c.sponsor = :sponsor"),
    @NamedQuery(name = "Collectingtrip.findByStartDate", query = "SELECT c FROM Collectingtrip c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "Collectingtrip.findByStartDateVerbatim", query = "SELECT c FROM Collectingtrip c WHERE c.startDateVerbatim = :startDateVerbatim"),
    @NamedQuery(name = "Collectingtrip.findByStartTime", query = "SELECT c FROM Collectingtrip c WHERE c.startTime = :startTime")})
public class Collectingtrip extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectingTripID")
    private Integer collectingTripID;
     
    @Size(max = 64)
    @Column(name = "CollectingTripName")
    private String collectingTripName;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Size(max = 50)
    @Column(name = "EndDateVerbatim")
    private String endDateVerbatim;
    
    @Column(name = "EndTime")
    private Short endTime;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "Sponsor")
    private String sponsor;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @Size(max = 50)
    @Column(name = "StartDateVerbatim")
    private String startDateVerbatim;
    
    @Column(name = "StartTime")
    private Short startTime;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;
    
    @OneToMany(mappedBy = "collectingTripID")
    private Collection<Collectingevent> collectingeventCollection;

    public Collectingtrip() {
    }

    public Collectingtrip(Integer collectingTripID) {
        this.collectingTripID = collectingTripID;
    }

    public Collectingtrip(Integer collectingTripID, Date timestampCreated) {
        super(timestampCreated);
        this.collectingTripID = collectingTripID; 
    }

    public Integer getCollectingTripID() {
        return collectingTripID;
    }

    public void setCollectingTripID(Integer collectingTripID) {
        this.collectingTripID = collectingTripID;
    } 

    public String getCollectingTripName() {
        return collectingTripName;
    }

    public void setCollectingTripName(String collectingTripName) {
        this.collectingTripName = collectingTripName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingeventCollection() {
        return collectingeventCollection;
    }

    public void setCollectingeventCollection(Collection<Collectingevent> collectingeventCollection) {
        this.collectingeventCollection = collectingeventCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectingTripID != null ? collectingTripID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectingtrip)) {
            return false;
        }
        Collectingtrip other = (Collectingtrip) object;
        if ((this.collectingTripID == null && other.collectingTripID != null) || (this.collectingTripID != null && !this.collectingTripID.equals(other.collectingTripID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectingtrip[ collectingTripID=" + collectingTripID + " ]";
    }
    
}

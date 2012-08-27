package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.*; 
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
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
@Table(name = "treatmentevent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treatmentevent.findAll", query = "SELECT t FROM Treatmentevent t"),
    @NamedQuery(name = "Treatmentevent.findByTreatmentEventId", query = "SELECT t FROM Treatmentevent t WHERE t.treatmentEventId = :treatmentEventId"),
    @NamedQuery(name = "Treatmentevent.findByTimestampCreated", query = "SELECT t FROM Treatmentevent t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Treatmentevent.findByTimestampModified", query = "SELECT t FROM Treatmentevent t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Treatmentevent.findByVersion", query = "SELECT t FROM Treatmentevent t WHERE t.version = :version"),
    @NamedQuery(name = "Treatmentevent.findByDateBoxed", query = "SELECT t FROM Treatmentevent t WHERE t.dateBoxed = :dateBoxed"),
    @NamedQuery(name = "Treatmentevent.findByDateCleaned", query = "SELECT t FROM Treatmentevent t WHERE t.dateCleaned = :dateCleaned"),
    @NamedQuery(name = "Treatmentevent.findByDateCompleted", query = "SELECT t FROM Treatmentevent t WHERE t.dateCompleted = :dateCompleted"),
    @NamedQuery(name = "Treatmentevent.findByDateReceived", query = "SELECT t FROM Treatmentevent t WHERE t.dateReceived = :dateReceived"),
    @NamedQuery(name = "Treatmentevent.findByDateToIsolation", query = "SELECT t FROM Treatmentevent t WHERE t.dateToIsolation = :dateToIsolation"),
    @NamedQuery(name = "Treatmentevent.findByDateTreatmentEnded", query = "SELECT t FROM Treatmentevent t WHERE t.dateTreatmentEnded = :dateTreatmentEnded"),
    @NamedQuery(name = "Treatmentevent.findByDateTreatmentStarted", query = "SELECT t FROM Treatmentevent t WHERE t.dateTreatmentStarted = :dateTreatmentStarted"),
    @NamedQuery(name = "Treatmentevent.findByFieldNumber", query = "SELECT t FROM Treatmentevent t WHERE t.fieldNumber = :fieldNumber"),
    @NamedQuery(name = "Treatmentevent.findByStorage", query = "SELECT t FROM Treatmentevent t WHERE t.location = :storage"),
    @NamedQuery(name = "Treatmentevent.findByTreatmentNumber", query = "SELECT t FROM Treatmentevent t WHERE t.treatmentNumber = :treatmentNumber"),
    @NamedQuery(name = "Treatmentevent.findByType", query = "SELECT t FROM Treatmentevent t WHERE t.type = :type")})
public class Treatmentevent extends BaseEntity {  
   
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TreatmentEventID")
    private Integer treatmentEventId;
    
    @Size(max = 50)
    @Column(name = "FieldNumber")
    private String fieldNumber;
    
    @Size(max = 64)
    @Column(name = "Storage")
    private String location;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "TreatmentNumber")
    private String treatmentNumber;
    
    @Size(max = 32)
    @Column(name = "Type")
    private String type;
    
    
    @Column(name =     "DateBoxed")
    @Temporal(TemporalType.DATE)
    private Date dateBoxed;
    
    @Column(name =     "DateCleaned")
    @Temporal(TemporalType.DATE)
    private Date dateCleaned;
    
    @Column(name =     "DateCompleted")
    @Temporal(TemporalType.DATE)
    private Date dateCompleted;
    
    @Column(name =     "DateReceived")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    
    @Column(name =     "DateToIsolation")
    @Temporal(TemporalType.DATE)
    private Date dateToIsolation;
    
    @Column(name =     "DateTreatmentEnded")
    @Temporal(TemporalType.DATE)
    private Date dateTreatmentEnded;
    
    @Column(name = "DateTreatmentStarted")
    @Temporal(TemporalType.DATE)
    private Date dateTreatmentStarted;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Division division;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @XmlTransient
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne
    private Accession accession;

    public Treatmentevent() {
    }

    public Treatmentevent(Integer treatmentEventId) {
        this.treatmentEventId = treatmentEventId;
    }

    public Treatmentevent(Integer treatmentEventId, Date timestampCreated) {
        super(timestampCreated);
        this.treatmentEventId = treatmentEventId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (treatmentEventId != null) ? treatmentEventId.toString() : "0";
    }

    public String getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }
 
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTreatmentNumber() {
        return treatmentNumber;
    }

    public void setTreatmentNumber(String treatmentNumber) {
        this.treatmentNumber = treatmentNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlIDREF
    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    @XmlTransient
    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public Date getDateBoxed() {
        return dateBoxed;
    }

    public void setDateBoxed(Date dateBoxed) {
        this.dateBoxed = dateBoxed;
    }

    public Date getDateCleaned() {
        return dateCleaned;
    }

    public void setDateCleaned(Date dateCleaned) {
        this.dateCleaned = dateCleaned;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getDateToIsolation() {
        return dateToIsolation;
    }

    public void setDateToIsolation(Date dateToIsolation) {
        this.dateToIsolation = dateToIsolation;
    }

    public Date getDateTreatmentEnded() {
        return dateTreatmentEnded;
    }

    public void setDateTreatmentEnded(Date dateTreatmentEnded) {
        this.dateTreatmentEnded = dateTreatmentEnded;
    }

    public Date getDateTreatmentStarted() {
        return dateTreatmentStarted;
    }

    public void setDateTreatmentStarted(Date dateTreatmentStarted) {
        this.dateTreatmentStarted = dateTreatmentStarted;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getTreatmentEventId() {
        return treatmentEventId;
    }

    public void setTreatmentEventId(Integer treatmentEventId) {
        this.treatmentEventId = treatmentEventId;
    }
    
    @Override
    public String getEntityName() {
        return "treatmentEvent";
    }

    
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Collectionobject) {
            this.collectionObject = (Collectionobject)parent;   
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treatmentEventId != null ? treatmentEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treatmentevent)) {
            return false;
        }
        Treatmentevent other = (Treatmentevent) object;
        if ((this.treatmentEventId == null && other.treatmentEventId != null) || (this.treatmentEventId != null && !this.treatmentEventId.equals(other.treatmentEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Treatmentevent[ treatmentEventID=" + treatmentEventId + " ]";
    }
 
    
    
}

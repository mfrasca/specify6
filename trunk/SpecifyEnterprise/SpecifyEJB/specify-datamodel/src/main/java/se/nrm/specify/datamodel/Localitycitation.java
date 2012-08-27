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
@Table(name = "localitycitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localitycitation.findAll", query = "SELECT l FROM Localitycitation l"),
    @NamedQuery(name = "Localitycitation.findByLocalityCitationId", query = "SELECT l FROM Localitycitation l WHERE l.localityCitationId = :localityCitationId"),
    @NamedQuery(name = "Localitycitation.findByTimestampCreated", query = "SELECT l FROM Localitycitation l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Localitycitation.findByTimestampModified", query = "SELECT l FROM Localitycitation l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Localitycitation.findByVersion", query = "SELECT l FROM Localitycitation l WHERE l.version = :version")})
public class Localitycitation extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LocalityCitationID")
    private Integer localityCitationId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @NotNull
    @ManyToOne(optional = false)
    private Locality locality;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @NotNull
    @ManyToOne(optional = false)
    private Referencework referenceWork;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline discipline;

    public Localitycitation() {
    }

    public Localitycitation(Integer localityCitationId) {
        this.localityCitationId = localityCitationId;
    }

    public Localitycitation(Integer localityCitationId, Date timestampCreated) {
        super(timestampCreated);
        this.localityCitationId = localityCitationId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (localityCitationId != null) ? localityCitationId.toString() : "0";
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @NotNull(message="Locality must be specified.")
    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Integer getLocalityCitationId() {
        return localityCitationId;
    }

    public void setLocalityCitationId(Integer localityCitationId) {
        this.localityCitationId = localityCitationId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @NotNull(message="Reference must be specified.")
    public Referencework getReferenceWork() {
        return referenceWork;
    }

    public void setReferenceWork(Referencework referenceWork) {
        this.referenceWork = referenceWork;
    }
 
 
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String getEntityName() {
        return "localityCitation";
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (localityCitationId != null ? localityCitationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localitycitation)) {
            return false;
        }
        Localitycitation other = (Localitycitation) object;
        if ((this.localityCitationId == null && other.localityCitationId != null) || (this.localityCitationId != null && !this.localityCitationId.equals(other.localityCitationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localitycitation[ localityCitationID=" + localityCitationId + " ]";
    }
 
    
}

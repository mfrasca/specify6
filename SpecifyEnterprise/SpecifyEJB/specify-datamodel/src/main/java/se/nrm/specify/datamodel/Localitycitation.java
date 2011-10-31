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
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Localitycitation.findByLocalityCitationID", query = "SELECT l FROM Localitycitation l WHERE l.localityCitationID = :localityCitationID"),
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
    private Integer localityCitationID;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne(optional = false)
    private Locality localityID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWorkID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline disciplineID;

    public Localitycitation() {
    }

    public Localitycitation(Integer localityCitationID) {
        this.localityCitationID = localityCitationID;
    }

    public Localitycitation(Integer localityCitationID, Date timestampCreated) {
        super(timestampCreated);
        this.localityCitationID = localityCitationID; 
    }

    public Integer getLocalityCitationID() {
        return localityCitationID;
    }

    public void setLocalityCitationID(Integer localityCitationID) {
        this.localityCitationID = localityCitationID;
    }
 
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Locality getLocalityID() {
        return localityID;
    }

    public void setLocalityID(Locality localityID) {
        this.localityID = localityID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Referencework getReferenceWorkID() {
        return referenceWorkID;
    }

    public void setReferenceWorkID(Referencework referenceWorkID) {
        this.referenceWorkID = referenceWorkID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (localityCitationID != null ? localityCitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localitycitation)) {
            return false;
        }
        Localitycitation other = (Localitycitation) object;
        if ((this.localityCitationID == null && other.localityCitationID != null) || (this.localityCitationID != null && !this.localityCitationID.equals(other.localityCitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localitycitation[ localityCitationID=" + localityCitationID + " ]";
    }
    
}

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "determinationcitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Determinationcitation.findAll", query = "SELECT d FROM Determinationcitation d"),
    @NamedQuery(name = "Determinationcitation.findByDeterminationCitationID", query = "SELECT d FROM Determinationcitation d WHERE d.determinationCitationID = :determinationCitationID"),
    @NamedQuery(name = "Determinationcitation.findByTimestampCreated", query = "SELECT d FROM Determinationcitation d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Determinationcitation.findByTimestampModified", query = "SELECT d FROM Determinationcitation d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Determinationcitation.findByVersion", query = "SELECT d FROM Determinationcitation d WHERE d.version = :version"),
    @NamedQuery(name = "Determinationcitation.findByCollectionMemberID", query = "SELECT d FROM Determinationcitation d WHERE d.collectionMemberID = :collectionMemberID")})
public class Determinationcitation extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DeterminationCitationID")
    private Integer determinationCitationID;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWorkID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DeterminationID", referencedColumnName = "DeterminationID")
    @ManyToOne(optional = false)
    private Determination determinationID;

    public Determinationcitation() {
    }

    public Determinationcitation(Integer determinationCitationID) {
        this.determinationCitationID = determinationCitationID;
    }

    public Determinationcitation(Integer determinationCitationID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.determinationCitationID = determinationCitationID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getDeterminationCitationID() {
        return determinationCitationID;
    }

    public void setDeterminationCitationID(Integer determinationCitationID) {
        this.determinationCitationID = determinationCitationID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Determination getDeterminationID() {
        return determinationID;
    }

    public void setDeterminationID(Determination determinationID) {
        this.determinationID = determinationID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (determinationCitationID != null ? determinationCitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Determinationcitation)) {
            return false;
        }
        Determinationcitation other = (Determinationcitation) object;
        if ((this.determinationCitationID == null && other.determinationCitationID != null) || (this.determinationCitationID != null && !this.determinationCitationID.equals(other.determinationCitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Determinationcitation[ determinationCitationID=" + determinationCitationID + " ]";
    }
    
}

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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
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
    @NamedQuery(name = "Determinationcitation.findByDeterminationCitationID", query = "SELECT d FROM Determinationcitation d WHERE d.determinationCitationId = :determinationCitationID"),
    @NamedQuery(name = "Determinationcitation.findByTimestampCreated", query = "SELECT d FROM Determinationcitation d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Determinationcitation.findByTimestampModified", query = "SELECT d FROM Determinationcitation d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Determinationcitation.findByVersion", query = "SELECT d FROM Determinationcitation d WHERE d.version = :version"),
    @NamedQuery(name = "Determinationcitation.findByCollectionMemberID", query = "SELECT d FROM Determinationcitation d WHERE d.collectionMemberId = :collectionMemberID")})
public class Determinationcitation extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DeterminationCitationID")
    private Integer determinationCitationId;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWork;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DeterminationID", referencedColumnName = "DeterminationID")
    @ManyToOne(optional = false)
    private Determination determination;

    public Determinationcitation() {
    }

    public Determinationcitation(Integer determinationCitationId) {
        this.determinationCitationId = determinationCitationId;
    }

    public Determinationcitation(Integer determinationCitationId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.determinationCitationId = determinationCitationId; 
        this.collectionMemberId = collectionMemberId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (determinationCitationId != null) ? determinationCitationId.toString() : "0";
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Determination must be specified.")
    public Determination getDetermination() {
        return determination;
    }

    public void setDetermination(Determination determination) {
        this.determination = determination;
    }

    public Integer getDeterminationCitationId() {
        return determinationCitationId;
    }

    public void setDeterminationCitationId(Integer determinationCitationId) {
        this.determinationCitationId = determinationCitationId;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @NotNull(message="Referencework must be specified.")
    public Referencework getReferenceWork() {
        return referenceWork;
    }

    public void setReferenceWork(Referencework referenceWork) {
        this.referenceWork = referenceWork;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (determinationCitationId != null ? determinationCitationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Determinationcitation)) {
            return false;
        }
        Determinationcitation other = (Determinationcitation) object;
        if ((this.determinationCitationId == null && other.determinationCitationId != null) || (this.determinationCitationId != null && !this.determinationCitationId.equals(other.determinationCitationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Determinationcitation[ determinationCitationId=" + determinationCitationId + " ]";
    }
 
    
}

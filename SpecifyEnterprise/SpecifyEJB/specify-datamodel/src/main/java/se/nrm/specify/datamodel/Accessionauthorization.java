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
@Table(name = "accessionauthorization")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accessionauthorization.findAll", query = "SELECT a FROM Accessionauthorization a"),
    @NamedQuery(name = "Accessionauthorization.findByAccessionAuthorizationID", query = "SELECT a FROM Accessionauthorization a WHERE a.accessionAuthorizationID = :accessionAuthorizationID"),
    @NamedQuery(name = "Accessionauthorization.findByTimestampCreated", query = "SELECT a FROM Accessionauthorization a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Accessionauthorization.findByTimestampModified", query = "SELECT a FROM Accessionauthorization a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Accessionauthorization.findByVersion", query = "SELECT a FROM Accessionauthorization a WHERE a.version = :version")})
public class Accessionauthorization extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AccessionAuthorizationID")
    private Integer accessionAuthorizationID;
      
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "PermitID", referencedColumnName = "PermitID")
    @ManyToOne(optional = false)
    private Permit permitID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "RepositoryAgreementID", referencedColumnName = "RepositoryAgreementID")
    @ManyToOne
    private Repositoryagreement repositoryAgreementID;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne
    private Accession accessionID;

    public Accessionauthorization() {
    }

    public Accessionauthorization(Integer accessionAuthorizationID) {
        this.accessionAuthorizationID = accessionAuthorizationID;
    }

    public Accessionauthorization(Integer accessionAuthorizationID, Date timestampCreated) {
        super(timestampCreated);
        this.accessionAuthorizationID = accessionAuthorizationID; 
    }

    public Integer getAccessionAuthorizationID() {
        return accessionAuthorizationID;
    }

    public void setAccessionAuthorizationID(Integer accessionAuthorizationID) {
        this.accessionAuthorizationID = accessionAuthorizationID;
    } 
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Permit getPermitID() {
        return permitID;
    }

    public void setPermitID(Permit permitID) {
        this.permitID = permitID;
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

    public Repositoryagreement getRepositoryAgreementID() {
        return repositoryAgreementID;
    }

    public void setRepositoryAgreementID(Repositoryagreement repositoryAgreementID) {
        this.repositoryAgreementID = repositoryAgreementID;
    }

    public Accession getAccessionID() {
        return accessionID;
    }

    public void setAccessionID(Accession accessionID) {
        this.accessionID = accessionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionAuthorizationID != null ? accessionAuthorizationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessionauthorization)) {
            return false;
        }
        Accessionauthorization other = (Accessionauthorization) object;
        if ((this.accessionAuthorizationID == null && other.accessionAuthorizationID != null) || (this.accessionAuthorizationID != null && !this.accessionAuthorizationID.equals(other.accessionAuthorizationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accessionauthorization[ accessionAuthorizationID=" + accessionAuthorizationID + " ]";
    }
    
}

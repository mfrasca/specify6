package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.*;
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
    @NamedQuery(name = "Accessionauthorization.findByAccessionAuthorizationID", query = "SELECT a FROM Accessionauthorization a WHERE a.accessionAuthorizationId = :accessionAuthorizationID"),
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
    private Integer accessionAuthorizationId;
      
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "PermitID", referencedColumnName = "PermitID")
    @ManyToOne(optional = false)
    private Permit permit;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "RepositoryAgreementID", referencedColumnName = "RepositoryAgreementID")
    @ManyToOne
    private Repositoryagreement repositoryAgreement;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne
    private Accession accession;

    public Accessionauthorization() {
    }

    public Accessionauthorization(Integer accessionAuthorizationId) {
        this.accessionAuthorizationId = accessionAuthorizationId;
    }

    public Accessionauthorization(Integer accessionAuthorizationId, Date timestampCreated) {
        super(timestampCreated);
        this.accessionAuthorizationId = accessionAuthorizationId; 
    }

    public Integer getAccessionAuthorizationId() {
        return accessionAuthorizationId;
    }

    public void setAccessionAuthorizationId(Integer accessionAuthorizationId) {
        this.accessionAuthorizationId = accessionAuthorizationId;
    }

  
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Permit getPermit() {
        return permit;
    }

    public void setPermit(Permit permit) {
        this.permit = permit;
    }

    public Repositoryagreement getRepositoryAgreement() {
        return repositoryAgreement;
    }

    public void setRepositoryAgreement(Repositoryagreement repositoryAgreement) {
        this.repositoryAgreement = repositoryAgreement;
    }

    
     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionAuthorizationId != null ? accessionAuthorizationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessionauthorization)) {
            return false;
        }
        Accessionauthorization other = (Accessionauthorization) object;
        if ((this.accessionAuthorizationId == null && other.accessionAuthorizationId != null) || (this.accessionAuthorizationId != null && !this.accessionAuthorizationId.equals(other.accessionAuthorizationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accessionauthorization[ accessionAuthorizationID=" + accessionAuthorizationId + " ]";
    }
    
}

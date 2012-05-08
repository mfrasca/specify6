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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "accessionagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accessionagent.findAll", query = "SELECT a FROM Accessionagent a"),
    @NamedQuery(name = "Accessionagent.findByAccessionAgentID", query = "SELECT a FROM Accessionagent a WHERE a.accessionAgentID = :accessionAgentID"),
    @NamedQuery(name = "Accessionagent.findByTimestampCreated", query = "SELECT a FROM Accessionagent a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Accessionagent.findByTimestampModified", query = "SELECT a FROM Accessionagent a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Accessionagent.findByVersion", query = "SELECT a FROM Accessionagent a WHERE a.version = :version"),
    @NamedQuery(name = "Accessionagent.findByRole", query = "SELECT a FROM Accessionagent a WHERE a.role = :role")})
public class Accessionagent extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AccessionAgentID")
    private Integer accessionAgentID;
      
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull(message="Role must be specified.")
    @Size(min = 1, max = 50)
    @Column(name = "Role")
    private String role;
    
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
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Accessionagent() {
    }

    public Accessionagent(Integer accessionAgentID) {
        this.accessionAgentID = accessionAgentID;
    }

    public Accessionagent(Integer accessionAgentID, Date timestampCreated, String role) {
        super(timestampCreated);
        this.accessionAgentID = accessionAgentID;  
        this.role = role;
    }

    public Integer getAccessionAgentID() {
        return accessionAgentID;
    }

    public void setAccessionAgentID(Integer accessionAgentID) {
        this.accessionAgentID = accessionAgentID;
    } 
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @XmlTransient
    public Accession getAccessionID() {
        return accessionID;
    }

    public void setAccessionID(Accession accessionID) {
        this.accessionID = accessionID;
    }

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionAgentID != null ? accessionAgentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessionagent)) {
            return false;
        }
        Accessionagent other = (Accessionagent) object;
        if ((this.accessionAgentID == null && other.accessionAgentID != null) || (this.accessionAgentID != null && !this.accessionAgentID.equals(other.accessionAgentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accessionagent[ accessionAgentID=" + accessionAgentID + " ]";
    }
    
}

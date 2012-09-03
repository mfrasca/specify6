package se.nrm.specify.datamodel;

import com.sun.xml.bind.CycleRecoverable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID; 
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
    @NamedQuery(name = "Accessionagent.findByAccessionAgentId", query = "SELECT a FROM Accessionagent a WHERE a.accessionAgentId = :accessionAgentId"),
    @NamedQuery(name = "Accessionagent.findByTimestampCreated", query = "SELECT a FROM Accessionagent a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Accessionagent.findByTimestampModified", query = "SELECT a FROM Accessionagent a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Accessionagent.findByVersion", query = "SELECT a FROM Accessionagent a WHERE a.version = :version"),
    @NamedQuery(name = "Accessionagent.findByRole", query = "SELECT a FROM Accessionagent a WHERE a.role = :role")})
public class Accessionagent extends BaseEntity implements CycleRecoverable {
 

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AccessionAgentID")
    private Integer accessionAgentId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 50)
    @NotNull
    @Column(name = "Role")
    private String role;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "RepositoryAgreementID", referencedColumnName = "RepositoryAgreementID")
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    private Repositoryagreement repositoryAgreement;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST})
    private Accession accession;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @NotNull
    @ManyToOne(optional = false)
    private Agent agent;

    public Accessionagent() {
    }

    public Accessionagent(Integer accessionAgentId) {
        this.accessionAgentId = accessionAgentId;
    }

    public Accessionagent(Integer accessionAgentId, Date timestampCreated, String role) {
        super(timestampCreated);
        this.accessionAgentId = accessionAgentId;
        this.role = role;
    }

 

    @Override
    public Accessionagent onCycleDetected(Context context) {
        // Context provides access to the Marshaller being used:
        System.out.println("JAXB Marshaller is: " + context.getMarshaller() + " -- " + this.getClass().getSimpleName());

        return new Accessionagent(accessionAgentId);
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (accessionAgentId != null) ? accessionAgentId.toString() : "0";
    }
 
    @XmlTransient
    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    public Integer getAccessionAgentId() {
        return accessionAgentId;
    }

    public void setAccessionAgentId(Integer accessionAgentId) {
        this.accessionAgentId = accessionAgentId;
    }

    @NotNull(message="Agent must be specified.")
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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

    @XmlTransient
    public Repositoryagreement getRepositoryAgreement() {
        return repositoryAgreement;
    }

    public void setRepositoryAgreement(Repositoryagreement repositoryAgreement) {
        this.repositoryAgreement = repositoryAgreement;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @NotNull(message = "Role must be specified.")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
 
     

    @Override
    public String getEntityName() {
        return "accessionAgent";
    }
 
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        if (parent instanceof Accession) {
            this.accession = (Accession) parent;
        } else if (parent instanceof Repositoryagreement) {
            this.repositoryAgreement = (Repositoryagreement) parent;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionAgentId != null ? accessionAgentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessionagent)) {
            return false;
        }
        Accessionagent other = (Accessionagent) object;
        if ((this.accessionAgentId == null && other.accessionAgentId != null) || (this.accessionAgentId != null && !this.accessionAgentId.equals(other.accessionAgentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accessionagent[ accessionAgentId=" + accessionAgentId + " ]";
    }
 
}

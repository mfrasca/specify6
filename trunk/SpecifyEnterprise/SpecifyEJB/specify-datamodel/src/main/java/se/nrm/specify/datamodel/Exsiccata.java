package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "exsiccata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exsiccata.findAll", query = "SELECT e FROM Exsiccata e"),
    @NamedQuery(name = "Exsiccata.findByExsiccataId", query = "SELECT e FROM Exsiccata e WHERE e.exsiccataId = :exsiccataId"),
    @NamedQuery(name = "Exsiccata.findByTimestampCreated", query = "SELECT e FROM Exsiccata e WHERE e.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Exsiccata.findByTimestampModified", query = "SELECT e FROM Exsiccata e WHERE e.timestampModified = :timestampModified"),
    @NamedQuery(name = "Exsiccata.findByVersion", query = "SELECT e FROM Exsiccata e WHERE e.version = :version"),
    @NamedQuery(name = "Exsiccata.findByTitle", query = "SELECT e FROM Exsiccata e WHERE e.title = :title")})
public class Exsiccata extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ExsiccataID")
    private Integer exsiccataId;
     
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Title")
    private String title;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exsiccata")
    private Collection<Exsiccataitem> exsiccataItems;

    public Exsiccata() {
    }

    public Exsiccata(Integer exsiccataId) {
        this.exsiccataId = exsiccataId;
    }

    public Exsiccata(Integer exsiccataId, Date timestampCreated, String title) {
        super(timestampCreated);
        this.exsiccataId = exsiccataId; 
        this.title = title;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (exsiccataId != null) ? exsiccataId.toString() : "0";
    }
 
    @NotNull(message="Title must be specified.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getExsiccataId() {
        return exsiccataId;
    }

    public void setExsiccataId(Integer exsiccataId) {
        this.exsiccataId = exsiccataId;
    }

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

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataItems() {
        return exsiccataItems;
    }

    public void setExsiccataItems(Collection<Exsiccataitem> exsiccataItems) {
        this.exsiccataItems = exsiccataItems;
    }

    @Override
    public String getEntityName() {
        return "exsiccata";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exsiccataId != null ? exsiccataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exsiccata)) {
            return false;
        }
        Exsiccata other = (Exsiccata) object;
        if ((this.exsiccataId == null && other.exsiccataId != null) || (this.exsiccataId != null && !this.exsiccataId.equals(other.exsiccataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exsiccata[ exsiccataID=" + exsiccataId + " ]";
    }
 
}

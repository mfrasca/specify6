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
    @NamedQuery(name = "Exsiccata.findByExsiccataID", query = "SELECT e FROM Exsiccata e WHERE e.exsiccataID = :exsiccataID"),
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
    private Integer exsiccataID;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Title")
    private String title;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ReferenceWorkID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne(optional = false)
    private Referencework referenceWorkID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exsiccataID")
    private Collection<Exsiccataitem> exsiccataitemCollection;

    public Exsiccata() {
    }

    public Exsiccata(Integer exsiccataID) {
        this.exsiccataID = exsiccataID;
    }

    public Exsiccata(Integer exsiccataID, Date timestampCreated, String title) {
        super(timestampCreated);
        this.exsiccataID = exsiccataID; 
        this.title = title;
    }

    public Integer getExsiccataID() {
        return exsiccataID;
    }

    public void setExsiccataID(Integer exsiccataID) {
        this.exsiccataID = exsiccataID;
    }
 
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataitemCollection() {
        return exsiccataitemCollection;
    }

    public void setExsiccataitemCollection(Collection<Exsiccataitem> exsiccataitemCollection) {
        this.exsiccataitemCollection = exsiccataitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exsiccataID != null ? exsiccataID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exsiccata)) {
            return false;
        }
        Exsiccata other = (Exsiccata) object;
        if ((this.exsiccataID == null && other.exsiccataID != null) || (this.exsiccataID != null && !this.exsiccataID.equals(other.exsiccataID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exsiccata[ exsiccataID=" + exsiccataID + " ]";
    }
    
}

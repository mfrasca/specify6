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
import javax.persistence.Lob;
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
@Table(name = "lithostrattreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lithostrattreedef.findAll", query = "SELECT l FROM Lithostrattreedef l"),
    @NamedQuery(name = "Lithostrattreedef.findByLithoStratTreeDefID", query = "SELECT l FROM Lithostrattreedef l WHERE l.lithoStratTreeDefID = :lithoStratTreeDefID"),
    @NamedQuery(name = "Lithostrattreedef.findByTimestampCreated", query = "SELECT l FROM Lithostrattreedef l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Lithostrattreedef.findByTimestampModified", query = "SELECT l FROM Lithostrattreedef l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Lithostrattreedef.findByVersion", query = "SELECT l FROM Lithostrattreedef l WHERE l.version = :version"),
    @NamedQuery(name = "Lithostrattreedef.findByFullNameDirection", query = "SELECT l FROM Lithostrattreedef l WHERE l.fullNameDirection = :fullNameDirection"),
    @NamedQuery(name = "Lithostrattreedef.findByName", query = "SELECT l FROM Lithostrattreedef l WHERE l.name = :name")})
public class Lithostrattreedef extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LithoStratTreeDefID")
    private Integer lithoStratTreeDefID;
     
    @Column(name = "FullNameDirection")
    private Integer fullNameDirection;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lithoStratTreeDefID")
    private Collection<Lithostrattreedefitem> lithostrattreedefitemCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lithoStratTreeDefID")
    private Collection<Lithostrat> lithostratCollection;
    
    @OneToMany(mappedBy = "lithoStratTreeDefID")
    private Collection<Discipline> disciplineCollection;
     
    public Lithostrattreedef() {
    }

    public Lithostrattreedef(Integer lithoStratTreeDefID) {
        this.lithoStratTreeDefID = lithoStratTreeDefID;
    }

    public Lithostrattreedef(Integer lithoStratTreeDefID, Date timestampCreated, String name) {
        super(timestampCreated);
        this.lithoStratTreeDefID = lithoStratTreeDefID; 
        this.name = name;
    }

    public Integer getLithoStratTreeDefID() {
        return lithoStratTreeDefID;
    }

    public void setLithoStratTreeDefID(Integer lithoStratTreeDefID) {
        this.lithoStratTreeDefID = lithoStratTreeDefID;
    }
 
    public Integer getFullNameDirection() {
        return fullNameDirection;
    }

    public void setFullNameDirection(Integer fullNameDirection) {
        this.fullNameDirection = fullNameDirection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<Lithostrattreedefitem> getLithostrattreedefitemCollection() {
        return lithostrattreedefitemCollection;
    }

    public void setLithostrattreedefitemCollection(Collection<Lithostrattreedefitem> lithostrattreedefitemCollection) {
        this.lithostrattreedefitemCollection = lithostrattreedefitemCollection;
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

    @XmlTransient
    public Collection<Lithostrat> getLithostratCollection() {
        return lithostratCollection;
    }

    public void setLithostratCollection(Collection<Lithostrat> lithostratCollection) {
        this.lithostratCollection = lithostratCollection;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplineCollection() {
        return disciplineCollection;
    }

    public void setDisciplineCollection(Collection<Discipline> disciplineCollection) {
        this.disciplineCollection = disciplineCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lithoStratTreeDefID != null ? lithoStratTreeDefID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lithostrattreedef)) {
            return false;
        }
        Lithostrattreedef other = (Lithostrattreedef) object;
        if ((this.lithoStratTreeDefID == null && other.lithoStratTreeDefID != null) || (this.lithoStratTreeDefID != null && !this.lithoStratTreeDefID.equals(other.lithoStratTreeDefID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lithostrattreedef[ lithoStratTreeDefID=" + lithoStratTreeDefID + " ]";
    }
    
}

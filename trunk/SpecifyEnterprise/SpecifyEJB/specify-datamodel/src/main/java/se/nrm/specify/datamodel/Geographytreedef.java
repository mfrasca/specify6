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
@Table(name = "geographytreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geographytreedef.findAll", query = "SELECT g FROM Geographytreedef g"),
    @NamedQuery(name = "Geographytreedef.findByGeographyTreeDefID", query = "SELECT g FROM Geographytreedef g WHERE g.geographyTreeDefID = :geographyTreeDefID"),
    @NamedQuery(name = "Geographytreedef.findByTimestampCreated", query = "SELECT g FROM Geographytreedef g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geographytreedef.findByTimestampModified", query = "SELECT g FROM Geographytreedef g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geographytreedef.findByVersion", query = "SELECT g FROM Geographytreedef g WHERE g.version = :version"),
    @NamedQuery(name = "Geographytreedef.findByFullNameDirection", query = "SELECT g FROM Geographytreedef g WHERE g.fullNameDirection = :fullNameDirection"),
    @NamedQuery(name = "Geographytreedef.findByName", query = "SELECT g FROM Geographytreedef g WHERE g.name = :name")})
public class Geographytreedef extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GeographyTreeDefID")
    private Integer geographyTreeDefID;
     
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
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geographyTreeDefID")
    private Collection<Geographytreedefitem> geographytreedefitemCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geographyTreeDefID")
    private Collection<Geography> geographyCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geographyTreeDefID")
    private Collection<Discipline> disciplineCollection;

    public Geographytreedef() {
    }

    public Geographytreedef(Integer geographyTreeDefID) {
        this.geographyTreeDefID = geographyTreeDefID;
    }

    public Geographytreedef(Integer geographyTreeDefID, Date timestampCreated, String name) {
        super(timestampCreated);
        this.geographyTreeDefID = geographyTreeDefID; 
        this.name = name;
    }

    public Integer getGeographyTreeDefID() {
        return geographyTreeDefID;
    }

    public void setGeographyTreeDefID(Integer geographyTreeDefID) {
        this.geographyTreeDefID = geographyTreeDefID;
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
    public Collection<Geographytreedefitem> getGeographytreedefitemCollection() {
        return geographytreedefitemCollection;
    }

    public void setGeographytreedefitemCollection(Collection<Geographytreedefitem> geographytreedefitemCollection) {
        this.geographytreedefitemCollection = geographytreedefitemCollection;
    }

    @XmlTransient
    public Collection<Geography> getGeographyCollection() {
        return geographyCollection;
    }

    public void setGeographyCollection(Collection<Geography> geographyCollection) {
        this.geographyCollection = geographyCollection;
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
        hash += (geographyTreeDefID != null ? geographyTreeDefID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geographytreedef)) {
            return false;
        }
        Geographytreedef other = (Geographytreedef) object;
        if ((this.geographyTreeDefID == null && other.geographyTreeDefID != null) || (this.geographyTreeDefID != null && !this.geographyTreeDefID.equals(other.geographyTreeDefID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geographytreedef[ geographyTreeDefID=" + geographyTreeDefID + " ]";
    }
    
}

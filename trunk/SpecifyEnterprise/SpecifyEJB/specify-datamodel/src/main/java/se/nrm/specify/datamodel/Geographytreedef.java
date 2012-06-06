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
    @NamedQuery(name = "Geographytreedef.findByGeographyTreeDefID", query = "SELECT g FROM Geographytreedef g WHERE g.geographyTreeDefId = :geographyTreeDefID"),
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
    private Integer geographyTreeDefId;
     
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
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treeDef")
    private Collection<Geographytreedefitem> treeDefItems;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Geography> treeEntries;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geographyTreeDef")
    private Collection<Discipline> disciplines;

    public Geographytreedef() {
    }

    public Geographytreedef(Integer geographyTreeDefId) {
        this.geographyTreeDefId = geographyTreeDefId;
    }

    public Geographytreedef(Integer geographyTreeDefId, Date timestampCreated, String name) {
        super(timestampCreated);
        this.geographyTreeDefId = geographyTreeDefId; 
        this.name = name;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getGeographyTreeDefId() {
        return geographyTreeDefId;
    }

    public void setGeographyTreeDefId(Integer geographyTreeDefId) {
        this.geographyTreeDefId = geographyTreeDefId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
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
    public Collection<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Collection<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @XmlTransient
    public Collection<Geographytreedefitem> getTreeDefItems() {
        return treeDefItems;
    }

    public void setTreeDefItems(Collection<Geographytreedefitem> treeDefItems) {
        this.treeDefItems = treeDefItems;
    }

    @XmlTransient
    public Collection<Geography> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Geography> treeEntries) {
        this.treeEntries = treeEntries;
    }

   
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geographyTreeDefId != null ? geographyTreeDefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geographytreedef)) {
            return false;
        }
        Geographytreedef other = (Geographytreedef) object;
        if ((this.geographyTreeDefId == null && other.geographyTreeDefId != null) || (this.geographyTreeDefId != null && !this.geographyTreeDefId.equals(other.geographyTreeDefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geographytreedef[ geographyTreeDefID=" + geographyTreeDefId + " ]";
    }
    
}

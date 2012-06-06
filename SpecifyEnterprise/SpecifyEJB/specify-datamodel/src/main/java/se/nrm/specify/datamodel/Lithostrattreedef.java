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
    @NamedQuery(name = "Lithostrattreedef.findByLithoStratTreeDefID", query = "SELECT l FROM Lithostrattreedef l WHERE l.lithoStratTreeDefId = :lithoStratTreeDefID"),
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
    private Integer lithoStratTreeDefId;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treeDef")
    private Collection<Lithostrattreedefitem> treeDefItems;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Lithostrat> treeEntries;
    
    @OneToMany(mappedBy = "lithoStratTreeDef")
    private Collection<Discipline> disciplines;
     
    public Lithostrattreedef() {
    }

    public Lithostrattreedef(Integer lithoStratTreeDefId) {
        this.lithoStratTreeDefId = lithoStratTreeDefId;
    }

    public Lithostrattreedef(Integer lithoStratTreeDefId, Date timestampCreated, String name) {
        super(timestampCreated);
        this.lithoStratTreeDefId = lithoStratTreeDefId; 
        this.name = name;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getLithoStratTreeDefId() {
        return lithoStratTreeDefId;
    }

    public void setLithoStratTreeDefId(Integer lithoStratTreeDefId) {
        this.lithoStratTreeDefId = lithoStratTreeDefId;
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
    public Collection<Lithostrattreedefitem> getTreeDefItems() {
        return treeDefItems;
    }

    public void setTreeDefItems(Collection<Lithostrattreedefitem> treeDefItems) {
        this.treeDefItems = treeDefItems;
    }

    @XmlTransient
    public Collection<Lithostrat> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Lithostrat> treeEntries) {
        this.treeEntries = treeEntries;
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lithoStratTreeDefId != null ? lithoStratTreeDefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lithostrattreedef)) {
            return false;
        }
        Lithostrattreedef other = (Lithostrattreedef) object;
        if ((this.lithoStratTreeDefId == null && other.lithoStratTreeDefId != null) || (this.lithoStratTreeDefId != null && !this.lithoStratTreeDefId.equals(other.lithoStratTreeDefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lithostrattreedef[ lithoStratTreeDefID=" + lithoStratTreeDefId + " ]";
    }
    
}

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
import javax.persistence.Table;  ;
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
@Table(name = "geologictimeperiodtreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geologictimeperiodtreedef.findAll", query = "SELECT g FROM Geologictimeperiodtreedef g"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByGeologicTimePeriodTreeDefId", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.geologicTimePeriodTreeDefId = :geologicTimePeriodTreeDefId"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByTimestampCreated", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByTimestampModified", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByVersion", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.version = :version"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByFullNameDirection", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.fullNameDirection = :fullNameDirection"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByName", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.name = :name")})
public class Geologictimeperiodtreedef extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "GeologicTimePeriodTreeDefID")
    private Integer geologicTimePeriodTreeDefId; 
    
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
    private Collection<Geologictimeperiodtreedefitem> treeDefItems;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Geologictimeperiod> treeEntries;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geologicTimePeriodTreeDef")
    private Collection<Discipline> disciplines;

    public Geologictimeperiodtreedef() {
    }

    public Geologictimeperiodtreedef(Integer geologicTimePeriodTreeDefId) {
        this.geologicTimePeriodTreeDefId = geologicTimePeriodTreeDefId;
    }

    public Geologictimeperiodtreedef(Integer geologicTimePeriodTreeDefId, Date timestampCreated, String name) {
        super(timestampCreated);
        this.geologicTimePeriodTreeDefId = geologicTimePeriodTreeDefId; 
        this.name = name;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (geologicTimePeriodTreeDefId != null) ? geologicTimePeriodTreeDefId.toString() : "0";
    }
    
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getGeologicTimePeriodTreeDefId() {
        return geologicTimePeriodTreeDefId;
    }

    public void setGeologicTimePeriodTreeDefId(Integer geologicTimePeriodTreeDefId) {
        this.geologicTimePeriodTreeDefId = geologicTimePeriodTreeDefId;
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

    @NotNull(message="Name must be specified.")
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
    public Collection<Geologictimeperiodtreedefitem> getTreeDefItems() {
        return treeDefItems;
    } 
    
    public void setTreeDefItems(Collection<Geologictimeperiodtreedefitem> treeDefItems) {
        this.treeDefItems = treeDefItems;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Geologictimeperiod> treeEntries) {
        this.treeEntries = treeEntries;
    }

    @Override
    public String getEntityName() {
        return "geologicTimePeriodTreeDef";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geologicTimePeriodTreeDefId != null ? geologicTimePeriodTreeDefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geologictimeperiodtreedef)) {
            return false;
        }
        Geologictimeperiodtreedef other = (Geologictimeperiodtreedef) object;
        if ((this.geologicTimePeriodTreeDefId == null && other.geologicTimePeriodTreeDefId != null) || (this.geologicTimePeriodTreeDefId != null && !this.geologicTimePeriodTreeDefId.equals(other.geologicTimePeriodTreeDefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geologictimeperiodtreedef[ geologicTimePeriodTreeDefID=" + geologicTimePeriodTreeDefId + " ]";
    } 
}

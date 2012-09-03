package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
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
@Table(name = "taxontreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxontreedef.findAll", query = "SELECT t FROM Taxontreedef t"),
    @NamedQuery(name = "Taxontreedef.findByTaxonTreeDefId", query = "SELECT t FROM Taxontreedef t WHERE t.taxonTreeDefId = :taxonTreeDefId"),
    @NamedQuery(name = "Taxontreedef.findByTimestampCreated", query = "SELECT t FROM Taxontreedef t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxontreedef.findByTimestampModified", query = "SELECT t FROM Taxontreedef t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxontreedef.findByVersion", query = "SELECT t FROM Taxontreedef t WHERE t.version = :version"),
    @NamedQuery(name = "Taxontreedef.findByFullNameDirection", query = "SELECT t FROM Taxontreedef t WHERE t.fullNameDirection = :fullNameDirection"),
    @NamedQuery(name = "Taxontreedef.findByName", query = "SELECT t FROM Taxontreedef t WHERE t.name = :name"),
    @NamedQuery(name = "Taxontreedef.findByRemarks", query = "SELECT t FROM Taxontreedef t WHERE t.remarks = :remarks")})
public class Taxontreedef extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TaxonTreeDefID")
    private Integer taxonTreeDefId;
     
    @Column(name = "FullNameDirection")
    private Integer fullNameDirection;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 255)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Taxon> treeEntries;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "treeDef")
    private Collection<Taxontreedefitem> treeDefItems;
    
    @OneToMany(mappedBy = "taxonTreeDef")
    private Collection<Discipline> discipline;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Taxontreedef() {
    }

    public Taxontreedef(Integer taxonTreeDefId) {
        this.taxonTreeDefId = taxonTreeDefId;
    }

    public Taxontreedef(Integer taxonTreeDefId, Date timestampCreated, String name) {
        super(timestampCreated);
        this.taxonTreeDefId = taxonTreeDefId; 
        this.name = name;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (taxonTreeDefId != null) ? taxonTreeDefId.toString() : "0";
    }
 
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Discipline> getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Collection<Discipline> discipline) {
        this.discipline = discipline;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getTaxonTreeDefId() {
        return taxonTreeDefId;
    }

    public void setTaxonTreeDefId(Integer taxonTreeDefId) {
        this.taxonTreeDefId = taxonTreeDefId;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTreeDefItems() {
        return treeDefItems;
    }

    public void setTreeDefItems(Collection<Taxontreedefitem> treeDefItems) {
        this.treeDefItems = treeDefItems;
    }

    @XmlTransient
    public Collection<Taxon> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Taxon> treeEntries) {
        this.treeEntries = treeEntries;
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

   
    @Override
    public String getEntityName() {
        return "taxonTreeDef";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonTreeDefId != null ? taxonTreeDefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxontreedef)) {
            return false;
        }
        Taxontreedef other = (Taxontreedef) object;
        if ((this.taxonTreeDefId == null && other.taxonTreeDefId != null) || (this.taxonTreeDefId != null && !this.taxonTreeDefId.equals(other.taxonTreeDefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxontreedef[ taxonTreeDefID=" + taxonTreeDefId + " ]";
    }
 
}

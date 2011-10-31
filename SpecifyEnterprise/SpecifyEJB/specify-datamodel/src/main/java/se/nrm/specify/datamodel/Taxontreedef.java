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
@Table(name = "taxontreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxontreedef.findAll", query = "SELECT t FROM Taxontreedef t"),
    @NamedQuery(name = "Taxontreedef.findByTaxonTreeDefID", query = "SELECT t FROM Taxontreedef t WHERE t.taxonTreeDefID = :taxonTreeDefID"),
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
    private Integer taxonTreeDefID;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonTreeDefID")
    private Collection<Taxon> taxonCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonTreeDefID")
    private Collection<Taxontreedefitem> taxontreedefitemCollection;
    
    @OneToMany(mappedBy = "taxonTreeDefID")
    private Collection<Discipline> disciplineCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Taxontreedef() {
    }

    public Taxontreedef(Integer taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
    }

    public Taxontreedef(Integer taxonTreeDefID, Date timestampCreated, String name) {
        super(timestampCreated);
        this.taxonTreeDefID = taxonTreeDefID; 
        this.name = name;
    }

    public Integer getTaxonTreeDefID() {
        return taxonTreeDefID;
    }

    public void setTaxonTreeDefID(Integer taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
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
    public Collection<Taxon> getTaxonCollection() {
        return taxonCollection;
    }

    public void setTaxonCollection(Collection<Taxon> taxonCollection) {
        this.taxonCollection = taxonCollection;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTaxontreedefitemCollection() {
        return taxontreedefitemCollection;
    }

    public void setTaxontreedefitemCollection(Collection<Taxontreedefitem> taxontreedefitemCollection) {
        this.taxontreedefitemCollection = taxontreedefitemCollection;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplineCollection() {
        return disciplineCollection;
    }

    public void setDisciplineCollection(Collection<Discipline> disciplineCollection) {
        this.disciplineCollection = disciplineCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonTreeDefID != null ? taxonTreeDefID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxontreedef)) {
            return false;
        }
        Taxontreedef other = (Taxontreedef) object;
        if ((this.taxonTreeDefID == null && other.taxonTreeDefID != null) || (this.taxonTreeDefID != null && !this.taxonTreeDefID.equals(other.taxonTreeDefID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxontreedef[ taxonTreeDefID=" + taxonTreeDefID + " ]";
    }
    
}

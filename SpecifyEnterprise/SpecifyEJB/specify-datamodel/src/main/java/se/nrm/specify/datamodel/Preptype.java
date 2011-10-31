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
@Table(name = "preptype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preptype.findAll", query = "SELECT p FROM Preptype p"),
    @NamedQuery(name = "Preptype.findByPrepTypeID", query = "SELECT p FROM Preptype p WHERE p.prepTypeID = :prepTypeID"),
    @NamedQuery(name = "Preptype.findByTimestampCreated", query = "SELECT p FROM Preptype p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Preptype.findByTimestampModified", query = "SELECT p FROM Preptype p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Preptype.findByVersion", query = "SELECT p FROM Preptype p WHERE p.version = :version"),
    @NamedQuery(name = "Preptype.findByIsLoanable", query = "SELECT p FROM Preptype p WHERE p.isLoanable = :isLoanable"),
    @NamedQuery(name = "Preptype.findByName", query = "SELECT p FROM Preptype p WHERE p.name = :name")})
public class Preptype extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PrepTypeID")
    private Integer prepTypeID;
       
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsLoanable")
    private boolean isLoanable;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private se.nrm.specify.datamodel.Collection collectionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prepTypeID")
    private Collection<Preparation> preparationCollection;
    
    @OneToMany(mappedBy = "prepTypeID")
    private Collection<Attributedef> attributedefCollection;

    public Preptype() {
    }

    public Preptype(Integer prepTypeID) {
        this.prepTypeID = prepTypeID;
    }

    public Preptype(Integer prepTypeID, Date timestampCreated, boolean isLoanable, String name) {
        super(timestampCreated);
        this.prepTypeID = prepTypeID; 
        this.isLoanable = isLoanable;
        this.name = name;
    }

    public Integer getPrepTypeID() {
        return prepTypeID;
    }

    public void setPrepTypeID(Integer prepTypeID) {
        this.prepTypeID = prepTypeID;
    }
 
    public boolean getIsLoanable() {
        return isLoanable;
    }

    public void setIsLoanable(boolean isLoanable) {
        this.isLoanable = isLoanable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public se.nrm.specify.datamodel.Collection getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(se.nrm.specify.datamodel.Collection collectionID) {
        this.collectionID = collectionID;
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
    public Collection<Preparation> getPreparationCollection() {
        return preparationCollection;
    }

    public void setPreparationCollection(Collection<Preparation> preparationCollection) {
        this.preparationCollection = preparationCollection;
    }

    @XmlTransient
    public Collection<Attributedef> getAttributedefCollection() {
        return attributedefCollection;
    }

    public void setAttributedefCollection(Collection<Attributedef> attributedefCollection) {
        this.attributedefCollection = attributedefCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prepTypeID != null ? prepTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preptype)) {
            return false;
        }
        Preptype other = (Preptype) object;
        if ((this.prepTypeID == null && other.prepTypeID != null) || (this.prepTypeID != null && !this.prepTypeID.equals(other.prepTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preptype[ prepTypeID=" + prepTypeID + " ]";
    }
    
}

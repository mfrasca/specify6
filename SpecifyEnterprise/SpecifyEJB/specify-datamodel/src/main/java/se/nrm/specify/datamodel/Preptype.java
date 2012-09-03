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
@Table(name = "preptype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preptype.findAll", query = "SELECT p FROM Preptype p"),
    @NamedQuery(name = "Preptype.findByPrepTypeId", query = "SELECT p FROM Preptype p WHERE p.prepTypeId = :prepTypeId"),
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
    private Integer prepTypeId;
       
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
    @NotNull
    @ManyToOne(optional = false)
    private se.nrm.specify.datamodel.Collection collection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prepType")
    private Collection<Preparation> preparations;
    
    @OneToMany(mappedBy = "prepType")
    private Collection<Attributedef> attributeDefs;

    public Preptype() {
    }

    public Preptype(Integer prepTypeId) {
        this.prepTypeId = prepTypeId;
    }

    public Preptype(Integer prepTypeId, Date timestampCreated, boolean isLoanable, String name) {
        super(timestampCreated);
        this.prepTypeId = prepTypeId; 
        this.isLoanable = isLoanable;
        this.name = name;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (prepTypeId != null) ? prepTypeId.toString() : "0";
    }
 
    @NotNull(message="Collection must be specified.")
    public se.nrm.specify.datamodel.Collection getCollection() {
        return collection;
    }

    public void setCollection(se.nrm.specify.datamodel.Collection collection) {
        this.collection = collection;
    }
 
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getPrepTypeId() {
        return prepTypeId;
    }

    public void setPrepTypeId(Integer prepTypeId) {
        this.prepTypeId = prepTypeId;
    }
 
 
    public boolean getIsLoanable() {
        return isLoanable;
    }

    public void setIsLoanable(boolean isLoanable) {
        this.isLoanable = isLoanable;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public Collection<Attributedef> getAttributeDefs() {
        return attributeDefs;
    }

    public void setAttributeDefs(Collection<Attributedef> attributeDefs) {
        this.attributeDefs = attributeDefs;
    }

    @XmlTransient
    public Collection<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(Collection<Preparation> preparations) {
        this.preparations = preparations;
    }

    
    @Override
    public String getEntityName() {
        return "prepType";
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prepTypeId != null ? prepTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preptype)) {
            return false;
        }
        Preptype other = (Preptype) object;
        if ((this.prepTypeId == null && other.prepTypeId != null) || (this.prepTypeId != null && !this.prepTypeId.equals(other.prepTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preptype[ prepTypeID=" + prepTypeId + " ]";
    }
 
}

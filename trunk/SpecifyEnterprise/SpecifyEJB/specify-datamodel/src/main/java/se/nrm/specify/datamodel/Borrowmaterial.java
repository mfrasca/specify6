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
import javax.persistence.Transient;
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
@Table(name = "borrowmaterial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrowmaterial.findAll", query = "SELECT b FROM Borrowmaterial b"),
    @NamedQuery(name = "Borrowmaterial.findByBorrowMaterialId", query = "SELECT b FROM Borrowmaterial b WHERE b.borrowMaterialId = :borrowMaterialId"),
    @NamedQuery(name = "Borrowmaterial.findByTimestampCreated", query = "SELECT b FROM Borrowmaterial b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrowmaterial.findByTimestampModified", query = "SELECT b FROM Borrowmaterial b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrowmaterial.findByVersion", query = "SELECT b FROM Borrowmaterial b WHERE b.version = :version"),
    @NamedQuery(name = "Borrowmaterial.findByCollectionMemberID", query = "SELECT b FROM Borrowmaterial b WHERE b.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Borrowmaterial.findByDescription", query = "SELECT b FROM Borrowmaterial b WHERE b.description = :description"),
    @NamedQuery(name = "Borrowmaterial.findByMaterialNumber", query = "SELECT b FROM Borrowmaterial b WHERE b.materialNumber = :materialNumber"),
    @NamedQuery(name = "Borrowmaterial.findByQuantity", query = "SELECT b FROM Borrowmaterial b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "Borrowmaterial.findByQuantityResolved", query = "SELECT b FROM Borrowmaterial b WHERE b.quantityResolved = :quantityResolved"),
    @NamedQuery(name = "Borrowmaterial.findByQuantityReturned", query = "SELECT b FROM Borrowmaterial b WHERE b.quantityReturned = :quantityReturned")})
public class Borrowmaterial extends BaseEntity {
 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "BorrowMaterialID")
    private Integer borrowMaterialId;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Size(max = 50)
    @Column(name = "Description")
    private String description;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "InComments")
    private String inComments;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MaterialNumber")
    private String materialNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "OutComments")
    private String outComments;
    
    @Column(name = "Quantity")
    private Short quantity;
    
    @Column(name = "QuantityResolved")
    private Short quantityResolved;
    
    @Column(name = "QuantityReturned")
    private Short quantityReturned;
    
    @JoinColumn(name = "BorrowID", referencedColumnName = "BorrowID")
    @NotNull
    @ManyToOne(optional = false)
    private Borrow borrow;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borrowMaterial")
    private Collection<Borrowreturnmaterial> borrowReturnMaterials;

    public Borrowmaterial() {
    }

    public Borrowmaterial(Integer borrowMaterialId) {
        this.borrowMaterialId = borrowMaterialId;
    }

    public Borrowmaterial(Integer borrowMaterialId, Date timestampCreated, int collectionMemberId, String materialNumber) {
        super(timestampCreated);
        this.borrowMaterialId = borrowMaterialId; 
        this.collectionMemberId = collectionMemberId;
        this.materialNumber = materialNumber;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (borrowMaterialId != null) ? borrowMaterialId.toString() : "0";
    }

    public Integer getBorrowMaterialId() {
        return borrowMaterialId;
    }

    public void setBorrowMaterialId(Integer borrowMaterialId) {
        this.borrowMaterialId = borrowMaterialId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

 

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInComments() {
        return inComments;
    }

    public void setInComments(String inComments) {
        this.inComments = inComments;
    }

    @NotNull(message="MaterialNumber must be specified.")
    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getOutComments() {
        return outComments;
    }

    public void setOutComments(String outComments) {
        this.outComments = outComments;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Short getQuantityResolved() {
        return quantityResolved;
    }

    public void setQuantityResolved(Short quantityResolved) {
        this.quantityResolved = quantityResolved;
    }

    public Short getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Short quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    @NotNull(message="Borrow must be specified.")
    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
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

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowReturnMaterials() {
        return borrowReturnMaterials;
    }

    public void setBorrowReturnMaterials(Collection<Borrowreturnmaterial> borrowReturnMaterials) {
        this.borrowReturnMaterials = borrowReturnMaterials;
    }
    
    @Override
    public String getEntityName() {
        return "borrowMaterial";
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (borrowMaterialId != null ? borrowMaterialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrowmaterial)) {
            return false;
        }
        Borrowmaterial other = (Borrowmaterial) object;
        if ((this.borrowMaterialId == null && other.borrowMaterialId != null) || (this.borrowMaterialId != null && !this.borrowMaterialId.equals(other.borrowMaterialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Borrowmaterial[ borrowMaterialId=" + borrowMaterialId + " ]";
    }
 
}

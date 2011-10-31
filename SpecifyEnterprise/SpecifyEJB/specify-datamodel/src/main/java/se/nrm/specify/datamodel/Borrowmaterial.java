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
@Table(name = "borrowmaterial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrowmaterial.findAll", query = "SELECT b FROM Borrowmaterial b"),
    @NamedQuery(name = "Borrowmaterial.findByBorrowMaterialID", query = "SELECT b FROM Borrowmaterial b WHERE b.borrowMaterialID = :borrowMaterialID"),
    @NamedQuery(name = "Borrowmaterial.findByTimestampCreated", query = "SELECT b FROM Borrowmaterial b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrowmaterial.findByTimestampModified", query = "SELECT b FROM Borrowmaterial b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrowmaterial.findByVersion", query = "SELECT b FROM Borrowmaterial b WHERE b.version = :version"),
    @NamedQuery(name = "Borrowmaterial.findByCollectionMemberID", query = "SELECT b FROM Borrowmaterial b WHERE b.collectionMemberID = :collectionMemberID"),
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
    private Integer borrowMaterialID;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
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
    @ManyToOne(optional = false)
    private Borrow borrowID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borrowMaterialID")
    private Collection<Borrowreturnmaterial> borrowreturnmaterialCollection;

    public Borrowmaterial() {
    }

    public Borrowmaterial(Integer borrowMaterialID) {
        this.borrowMaterialID = borrowMaterialID;
    }

    public Borrowmaterial(Integer borrowMaterialID, Date timestampCreated, int collectionMemberID, String materialNumber) {
        super(timestampCreated);
        this.borrowMaterialID = borrowMaterialID; 
        this.collectionMemberID = collectionMemberID;
        this.materialNumber = materialNumber;
    }

    public Integer getBorrowMaterialID() {
        return borrowMaterialID;
    }

    public void setBorrowMaterialID(Integer borrowMaterialID) {
        this.borrowMaterialID = borrowMaterialID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
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

    public Borrow getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(Borrow borrowID) {
        this.borrowID = borrowID;
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
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterialCollection() {
        return borrowreturnmaterialCollection;
    }

    public void setBorrowreturnmaterialCollection(Collection<Borrowreturnmaterial> borrowreturnmaterialCollection) {
        this.borrowreturnmaterialCollection = borrowreturnmaterialCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (borrowMaterialID != null ? borrowMaterialID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrowmaterial)) {
            return false;
        }
        Borrowmaterial other = (Borrowmaterial) object;
        if ((this.borrowMaterialID == null && other.borrowMaterialID != null) || (this.borrowMaterialID != null && !this.borrowMaterialID.equals(other.borrowMaterialID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Borrowmaterial[ borrowMaterialID=" + borrowMaterialID + " ]";
    }
    
}

package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "borrowreturnmaterial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrowreturnmaterial.findAll", query = "SELECT b FROM Borrowreturnmaterial b"),
    @NamedQuery(name = "Borrowreturnmaterial.findByBorrowReturnMaterialID", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.borrowReturnMaterialID = :borrowReturnMaterialID"),
    @NamedQuery(name = "Borrowreturnmaterial.findByTimestampCreated", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrowreturnmaterial.findByTimestampModified", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrowreturnmaterial.findByVersion", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.version = :version"),
    @NamedQuery(name = "Borrowreturnmaterial.findByCollectionMemberID", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Borrowreturnmaterial.findByQuantity", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "Borrowreturnmaterial.findByReturnedDate", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.returnedDate = :returnedDate")})
public class Borrowreturnmaterial extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "BorrowReturnMaterialID")
    private Integer borrowReturnMaterialID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Column(name = "Quantity")
    private Short quantity;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "ReturnedDate")
    @Temporal(TemporalType.DATE)
    private Date returnedDate;
    
    @JoinColumn(name = "ReturnedByID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent returnedByID;
    
    @JoinColumn(name = "BorrowMaterialID", referencedColumnName = "BorrowMaterialID")
    @ManyToOne(optional = false)
    private Borrowmaterial borrowMaterialID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Borrowreturnmaterial() {
    }

    public Borrowreturnmaterial(Integer borrowReturnMaterialID) {
        this.borrowReturnMaterialID = borrowReturnMaterialID;
    }

    public Borrowreturnmaterial(Integer borrowReturnMaterialID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.borrowReturnMaterialID = borrowReturnMaterialID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getBorrowReturnMaterialID() {
        return borrowReturnMaterialID;
    }

    public void setBorrowReturnMaterialID(Integer borrowReturnMaterialID) {
        this.borrowReturnMaterialID = borrowReturnMaterialID;
    }
  
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Agent getReturnedByID() {
        return returnedByID;
    }

    public void setReturnedByID(Agent returnedByID) {
        this.returnedByID = returnedByID;
    }

    public Borrowmaterial getBorrowMaterialID() {
        return borrowMaterialID;
    }

    public void setBorrowMaterialID(Borrowmaterial borrowMaterialID) {
        this.borrowMaterialID = borrowMaterialID;
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
        hash += (borrowReturnMaterialID != null ? borrowReturnMaterialID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrowreturnmaterial)) {
            return false;
        }
        Borrowreturnmaterial other = (Borrowreturnmaterial) object;
        if ((this.borrowReturnMaterialID == null && other.borrowReturnMaterialID != null) || (this.borrowReturnMaterialID != null && !this.borrowReturnMaterialID.equals(other.borrowReturnMaterialID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Borrowreturnmaterial[ borrowReturnMaterialID=" + borrowReturnMaterialID + " ]";
    }
    
}

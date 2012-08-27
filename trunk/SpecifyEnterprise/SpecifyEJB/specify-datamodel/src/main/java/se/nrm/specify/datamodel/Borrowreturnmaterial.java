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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Borrowreturnmaterial.findByBorrowReturnMaterialId", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.borrowReturnMaterialId = :borrowReturnMaterialId"),
    @NamedQuery(name = "Borrowreturnmaterial.findByTimestampCreated", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrowreturnmaterial.findByTimestampModified", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrowreturnmaterial.findByVersion", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.version = :version"),
    @NamedQuery(name = "Borrowreturnmaterial.findByCollectionMemberID", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Borrowreturnmaterial.findByQuantity", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "Borrowreturnmaterial.findByReturnedDate", query = "SELECT b FROM Borrowreturnmaterial b WHERE b.returnedDate = :returnedDate")})
public class Borrowreturnmaterial extends BaseEntity {
 

    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "BorrowReturnMaterialID")
    private Integer borrowReturnMaterialId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
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
    private Agent agent;
    
    @JoinColumn(name = "BorrowMaterialID", referencedColumnName = "BorrowMaterialID")
    @NotNull
    @ManyToOne(optional = false)
    private Borrowmaterial borrowMaterial;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Borrowreturnmaterial() {
    }

    public Borrowreturnmaterial(Integer borrowReturnMaterialId) {
        this.borrowReturnMaterialId = borrowReturnMaterialId;
    }

    public Borrowreturnmaterial(Integer borrowReturnMaterialId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.borrowReturnMaterialId = borrowReturnMaterialId; 
        this.collectionMemberId = collectionMemberId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (borrowReturnMaterialId != null) ? borrowReturnMaterialId.toString() : "0";
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @NotNull(message="BorrowMaterial must be specified.")
    public Borrowmaterial getBorrowMaterial() {
        return borrowMaterial;
    }

    public void setBorrowMaterial(Borrowmaterial borrowMaterial) {
        this.borrowMaterial = borrowMaterial;
    }

    public Integer getBorrowReturnMaterialId() {
        return borrowReturnMaterialId;
    }

    public void setBorrowReturnMaterialId(Integer borrowReturnMaterialId) {
        this.borrowReturnMaterialId = borrowReturnMaterialId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
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
 
    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
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
 
    @Override
    public String getEntityName() {
        return "borrowReturnMaterial";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (borrowReturnMaterialId != null ? borrowReturnMaterialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrowreturnmaterial)) {
            return false;
        }
        Borrowreturnmaterial other = (Borrowreturnmaterial) object;
        if ((this.borrowReturnMaterialId == null && other.borrowReturnMaterialId != null) || (this.borrowReturnMaterialId != null && !this.borrowReturnMaterialId.equals(other.borrowReturnMaterialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Borrowreturnmaterial[ borrowReturnMaterialId=" + borrowReturnMaterialId + " ]";
    } 
  
}

package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table; 
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "exsiccataitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exsiccataitem.findAll", query = "SELECT e FROM Exsiccataitem e"),
    @NamedQuery(name = "Exsiccataitem.findByExsiccataItemID", query = "SELECT e FROM Exsiccataitem e WHERE e.exsiccataItemID = :exsiccataItemID"),
    @NamedQuery(name = "Exsiccataitem.findByTimestampCreated", query = "SELECT e FROM Exsiccataitem e WHERE e.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Exsiccataitem.findByTimestampModified", query = "SELECT e FROM Exsiccataitem e WHERE e.timestampModified = :timestampModified"),
    @NamedQuery(name = "Exsiccataitem.findByVersion", query = "SELECT e FROM Exsiccataitem e WHERE e.version = :version"),
    @NamedQuery(name = "Exsiccataitem.findByFascicle", query = "SELECT e FROM Exsiccataitem e WHERE e.fascicle = :fascicle"),
    @NamedQuery(name = "Exsiccataitem.findByNumber", query = "SELECT e FROM Exsiccataitem e WHERE e.number = :number")})
public class Exsiccataitem extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ExsiccataItemID")
    private Integer exsiccataItemID;
      
    @Size(max = 16)
    @Column(name = "Fascicle")
    private String fascicle;
    
    @Size(max = 16)
    @Column(name = "Number")
    private String number;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "ExsiccataID", referencedColumnName = "ExsiccataID")
    @ManyToOne(optional = false)
    private Exsiccata exsiccataID;

    public Exsiccataitem() {
    }

    public Exsiccataitem(Integer exsiccataItemID) {
        this.exsiccataItemID = exsiccataItemID;
    }

    public Exsiccataitem(Integer exsiccataItemID, Date timestampCreated) {
        super(timestampCreated);
        this.exsiccataItemID = exsiccataItemID; 
    }

    public Integer getExsiccataItemID() {
        return exsiccataItemID;
    }

    public void setExsiccataItemID(Integer exsiccataItemID) {
        this.exsiccataItemID = exsiccataItemID;
    }
 
    public String getFascicle() {
        return fascicle;
    }

    public void setFascicle(String fascicle) {
        this.fascicle = fascicle;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Collectionobject getCollectionObjectID() {
        return collectionObjectID;
    }

    public void setCollectionObjectID(Collectionobject collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Exsiccata getExsiccataID() {
        return exsiccataID;
    }

    public void setExsiccataID(Exsiccata exsiccataID) {
        this.exsiccataID = exsiccataID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exsiccataItemID != null ? exsiccataItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exsiccataitem)) {
            return false;
        }
        Exsiccataitem other = (Exsiccataitem) object;
        if ((this.exsiccataItemID == null && other.exsiccataItemID != null) || (this.exsiccataItemID != null && !this.exsiccataItemID.equals(other.exsiccataItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exsiccataitem[ exsiccataItemID=" + exsiccataItemID + " ]";
    }
    
}

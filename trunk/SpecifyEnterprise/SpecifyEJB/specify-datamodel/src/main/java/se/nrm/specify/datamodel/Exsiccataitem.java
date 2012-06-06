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
    @NamedQuery(name = "Exsiccataitem.findByExsiccataItemID", query = "SELECT e FROM Exsiccataitem e WHERE e.exsiccataItemId = :exsiccataItemID"),
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
    private Integer exsiccataItemId;
      
    @Size(max = 16)
    @Column(name = "Fascicle")
    private String fascicle;
    
    @Size(max = 16)
    @Column(name = "Number")
    private String number;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "ExsiccataID", referencedColumnName = "ExsiccataID")
    @ManyToOne(optional = false)
    private Exsiccata exsiccata;

    public Exsiccataitem() {
    }

    public Exsiccataitem(Integer exsiccataItemId) {
        this.exsiccataItemId = exsiccataItemId;
    }

    public Exsiccataitem(Integer exsiccataItemId, Date timestampCreated) {
        super(timestampCreated);
        this.exsiccataItemId = exsiccataItemId; 
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

    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Exsiccata getExsiccata() {
        return exsiccata;
    }

    public void setExsiccata(Exsiccata exsiccata) {
        this.exsiccata = exsiccata;
    }

    public Integer getExsiccataItemId() {
        return exsiccataItemId;
    }

    public void setExsiccataItemId(Integer exsiccataItemId) {
        this.exsiccataItemId = exsiccataItemId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exsiccataItemId != null ? exsiccataItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exsiccataitem)) {
            return false;
        }
        Exsiccataitem other = (Exsiccataitem) object;
        if ((this.exsiccataItemId == null && other.exsiccataItemId != null) || (this.exsiccataItemId != null && !this.exsiccataItemId.equals(other.exsiccataItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exsiccataitem[ exsiccataItemID=" + exsiccataItemId + " ]";
    }
    
}

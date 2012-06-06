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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "picklistitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Picklistitem.findAll", query = "SELECT p FROM Picklistitem p"),
    @NamedQuery(name = "Picklistitem.findByPickListItemID", query = "SELECT p FROM Picklistitem p WHERE p.pickListItemId = :pickListItemID"),
    @NamedQuery(name = "Picklistitem.findByTimestampCreated", query = "SELECT p FROM Picklistitem p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Picklistitem.findByTimestampModified", query = "SELECT p FROM Picklistitem p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Picklistitem.findByVersion", query = "SELECT p FROM Picklistitem p WHERE p.version = :version"),
    @NamedQuery(name = "Picklistitem.findByOrdinal", query = "SELECT p FROM Picklistitem p WHERE p.ordinal = :ordinal"),
    @NamedQuery(name = "Picklistitem.findByTitle", query = "SELECT p FROM Picklistitem p WHERE p.title = :title"),
    @NamedQuery(name = "Picklistitem.findByValue", query = "SELECT p FROM Picklistitem p WHERE p.value = :value")})
public class Picklistitem extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PickListItemID")
    private Integer pickListItemId;
     
    @Column(name = "Ordinal") 
    private Integer ordinal;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Title")
    private String title;
    
    @Size(max = 64)
    @Column(name = "Value")
    private String value;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "PickListID", referencedColumnName = "PickListID")
    @ManyToOne(optional = false)
    private Picklist pickList;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Picklistitem() {
    }

    public Picklistitem(Integer pickListItemId) {
        this.pickListItemId = pickListItemId;
    }

    public Picklistitem(Integer pickListItemId, Date timestampCreated, String title) {
        super(timestampCreated);
        this.pickListItemId = pickListItemId; 
        this.title = title;
    }

     
    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Picklist getPickList() {
        return pickList;
    }

    public void setPickList(Picklist pickList) {
        this.pickList = pickList;
    }

    public Integer getPickListItemId() {
        return pickListItemId;
    }

    public void setPickListItemId(Integer pickListItemId) {
        this.pickListItemId = pickListItemId;
    }

     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pickListItemId != null ? pickListItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Picklistitem)) {
            return false;
        }
        Picklistitem other = (Picklistitem) object;
        if ((this.pickListItemId == null && other.pickListItemId != null) || (this.pickListItemId != null && !this.pickListItemId.equals(other.pickListItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Picklistitem[ pickListItemID=" + pickListItemId + " ]";
    }
    
}

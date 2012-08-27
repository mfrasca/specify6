package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;  
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
@Table(name = "spvisualquery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spvisualquery.findAll", query = "SELECT s FROM Spvisualquery s"),
    @NamedQuery(name = "Spvisualquery.findBySpVisualQueryId", query = "SELECT s FROM Spvisualquery s WHERE s.spVisualQueryId = :spVisualQueryId"),
    @NamedQuery(name = "Spvisualquery.findByTimestampCreated", query = "SELECT s FROM Spvisualquery s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spvisualquery.findByTimestampModified", query = "SELECT s FROM Spvisualquery s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spvisualquery.findByVersion", query = "SELECT s FROM Spvisualquery s WHERE s.version = :version"),
    @NamedQuery(name = "Spvisualquery.findByName", query = "SELECT s FROM Spvisualquery s WHERE s.name = :name"),
    @NamedQuery(name = "Spvisualquery.findByCreatedByAgentID", query = "SELECT s FROM Spvisualquery s WHERE s.createdByAgent = :createdByAgentID"),
    @NamedQuery(name = "Spvisualquery.findBySpecifyUserID", query = "SELECT s FROM Spvisualquery s WHERE s.specifyUser = :specifyUserID"),
    @NamedQuery(name = "Spvisualquery.findByModifiedByAgentID", query = "SELECT s FROM Spvisualquery s WHERE s.modifiedByAgent = :modifiedByAgentID")})
public class Spvisualquery extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpVisualQueryID")
    private Integer spVisualQueryId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgent;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SpecifyUserID")
    private int specifyUser;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgent;

    public Spvisualquery() {
    }

    public Spvisualquery(Integer spVisualQueryId) {
        this.spVisualQueryId = spVisualQueryId;
    }

    public Spvisualquery(Integer spVisualQueryId, Date timestampCreated, String name, int specifyUser) {
        super(timestampCreated);
        this.spVisualQueryId = spVisualQueryId; 
        this.name = name;
        this.specifyUser = specifyUser;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spVisualQueryId != null) ? spVisualQueryId.toString() : "0";
    }

    public Integer getSpVisualQueryId() {
        return spVisualQueryId;
    }

    public void setSpVisualQueryId(Integer spVisualQueryId) {
        this.spVisualQueryId = spVisualQueryId;
    }
 
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Integer createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Integer modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public int getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(int specifyUser) {
        this.specifyUser = specifyUser;
    }

    @Override
    public String getEntityName() {
        return "spVisualQuery";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spVisualQueryId != null ? spVisualQueryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spvisualquery)) {
            return false;
        }
        Spvisualquery other = (Spvisualquery) object;
        if ((this.spVisualQueryId == null && other.spVisualQueryId != null) || (this.spVisualQueryId != null && !this.spVisualQueryId.equals(other.spVisualQueryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spvisualquery[ spVisualQueryID=" + spVisualQueryId + " ]";
    }
 
}

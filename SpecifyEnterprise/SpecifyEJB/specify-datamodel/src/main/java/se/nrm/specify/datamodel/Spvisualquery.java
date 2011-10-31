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
    @NamedQuery(name = "Spvisualquery.findBySpVisualQueryID", query = "SELECT s FROM Spvisualquery s WHERE s.spVisualQueryID = :spVisualQueryID"),
    @NamedQuery(name = "Spvisualquery.findByTimestampCreated", query = "SELECT s FROM Spvisualquery s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spvisualquery.findByTimestampModified", query = "SELECT s FROM Spvisualquery s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spvisualquery.findByVersion", query = "SELECT s FROM Spvisualquery s WHERE s.version = :version"),
    @NamedQuery(name = "Spvisualquery.findByName", query = "SELECT s FROM Spvisualquery s WHERE s.name = :name"),
    @NamedQuery(name = "Spvisualquery.findByCreatedByAgentID", query = "SELECT s FROM Spvisualquery s WHERE s.createdByAgentID = :createdByAgentID"),
    @NamedQuery(name = "Spvisualquery.findBySpecifyUserID", query = "SELECT s FROM Spvisualquery s WHERE s.specifyUserID = :specifyUserID"),
    @NamedQuery(name = "Spvisualquery.findByModifiedByAgentID", query = "SELECT s FROM Spvisualquery s WHERE s.modifiedByAgentID = :modifiedByAgentID")})
public class Spvisualquery extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpVisualQueryID")
    private Integer spVisualQueryID;
     
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
    private Integer createdByAgentID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SpecifyUserID")
    private int specifyUserID;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentID;

    public Spvisualquery() {
    }

    public Spvisualquery(Integer spVisualQueryID) {
        this.spVisualQueryID = spVisualQueryID;
    }

    public Spvisualquery(Integer spVisualQueryID, Date timestampCreated, String name, int specifyUserID) {
        super(timestampCreated);
        this.spVisualQueryID = spVisualQueryID; 
        this.name = name;
        this.specifyUserID = specifyUserID;
    }

    public Integer getSpVisualQueryID() {
        return spVisualQueryID;
    }

    public void setSpVisualQueryID(Integer spVisualQueryID) {
        this.spVisualQueryID = spVisualQueryID;
    } 
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Integer createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public int getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(int specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    public Integer getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Integer modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spVisualQueryID != null ? spVisualQueryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spvisualquery)) {
            return false;
        }
        Spvisualquery other = (Spvisualquery) object;
        if ((this.spVisualQueryID == null && other.spVisualQueryID != null) || (this.spVisualQueryID != null && !this.spVisualQueryID.equals(other.spVisualQueryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spvisualquery[ spVisualQueryID=" + spVisualQueryID + " ]";
    }
    
}

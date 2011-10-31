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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "otheridentifier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Otheridentifier.findAll", query = "SELECT o FROM Otheridentifier o"),
    @NamedQuery(name = "Otheridentifier.findByOtherIdentifierID", query = "SELECT o FROM Otheridentifier o WHERE o.otherIdentifierID = :otherIdentifierID"),
    @NamedQuery(name = "Otheridentifier.findByTimestampCreated", query = "SELECT o FROM Otheridentifier o WHERE o.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Otheridentifier.findByTimestampModified", query = "SELECT o FROM Otheridentifier o WHERE o.timestampModified = :timestampModified"),
    @NamedQuery(name = "Otheridentifier.findByVersion", query = "SELECT o FROM Otheridentifier o WHERE o.version = :version"),
    @NamedQuery(name = "Otheridentifier.findByCollectionMemberID", query = "SELECT o FROM Otheridentifier o WHERE o.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Otheridentifier.findByIdentifier", query = "SELECT o FROM Otheridentifier o WHERE o.identifier = :identifier"),
    @NamedQuery(name = "Otheridentifier.findByInstitution", query = "SELECT o FROM Otheridentifier o WHERE o.institution = :institution")})
public class Otheridentifier extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "OtherIdentifierID")
    private Integer otherIdentifierID;
   
    @Basic(optional = false) 
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Identifier")
    private String identifier;
    
    @Size(max = 64)
    @Column(name = "Institution")
    private String institution;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Otheridentifier() {
    }

    public Otheridentifier(Integer otherIdentifierID) {
        this.otherIdentifierID = otherIdentifierID;
    }

    public Otheridentifier(Integer otherIdentifierID, Date timestampCreated, int collectionMemberID, String identifier) {
        super(timestampCreated);
        this.otherIdentifierID = otherIdentifierID; 
        this.collectionMemberID = collectionMemberID;
        this.identifier = identifier;
    }

    public Integer getOtherIdentifierID() {
        return otherIdentifierID;
    }

    public void setOtherIdentifierID(Integer otherIdentifierID) {
        this.otherIdentifierID = otherIdentifierID;
    } 
    
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otherIdentifierID != null ? otherIdentifierID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otheridentifier)) {
            return false;
        }
        Otheridentifier other = (Otheridentifier) object;
        if ((this.otherIdentifierID == null && other.otherIdentifierID != null) || (this.otherIdentifierID != null && !this.otherIdentifierID.equals(other.otherIdentifierID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Otheridentifier[ otherIdentifierID=" + otherIdentifierID + " ]";
    }
    
}

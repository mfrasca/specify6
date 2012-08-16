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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Otheridentifier.findByOtherIdentifierID", query = "SELECT o FROM Otheridentifier o WHERE o.otherIdentifierId = :otherIdentifierID"),
    @NamedQuery(name = "Otheridentifier.findByTimestampCreated", query = "SELECT o FROM Otheridentifier o WHERE o.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Otheridentifier.findByTimestampModified", query = "SELECT o FROM Otheridentifier o WHERE o.timestampModified = :timestampModified"),
    @NamedQuery(name = "Otheridentifier.findByVersion", query = "SELECT o FROM Otheridentifier o WHERE o.version = :version"),
    @NamedQuery(name = "Otheridentifier.findByCollectionMemberID", query = "SELECT o FROM Otheridentifier o WHERE o.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Otheridentifier.findByIdentifier", query = "SELECT o FROM Otheridentifier o WHERE o.identifier = :identifier"),
    @NamedQuery(name = "Otheridentifier.findByInstitution", query = "SELECT o FROM Otheridentifier o WHERE o.institution = :institution")})
public class Otheridentifier extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "OtherIdentifierID")
    private Integer otherIdentifierId;
   
    @Basic(optional = false) 
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Basic(optional = false) 
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
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Otheridentifier() {
    }

    public Otheridentifier(Integer otherIdentifierId) {
        this.otherIdentifierId = otherIdentifierId;
    }

    public Otheridentifier(Integer otherIdentifierId, Date timestampCreated, int collectionMemberId, String identifier) {
        super(timestampCreated);
        this.otherIdentifierId = otherIdentifierId; 
        this.collectionMemberId = collectionMemberId;
        this.identifier = identifier;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (otherIdentifierId != null) ? otherIdentifierId.toString() : "0";
    }
    
    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    @NotNull(message="Collectionobject must be specified.")
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

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getOtherIdentifierId() {
        return otherIdentifierId;
    }

    public void setOtherIdentifierId(Integer otherIdentifierId) {
        this.otherIdentifierId = otherIdentifierId;
    }

    
    @NotNull(message="Identifier must be specified.")
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

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otherIdentifierId != null ? otherIdentifierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otheridentifier)) {
            return false;
        }
        Otheridentifier other = (Otheridentifier) object;
        if ((this.otherIdentifierId == null && other.otherIdentifierId != null) || (this.otherIdentifierId != null && !this.otherIdentifierId.equals(other.otherIdentifierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Otheridentifier[ otherIdentifierID=" + otherIdentifierId + " ]";
    }
 
    
}

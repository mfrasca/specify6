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
@Table(name = "collectionobjectattr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectattr.findAll", query = "SELECT c FROM Collectionobjectattr c"),
    @NamedQuery(name = "Collectionobjectattr.findByAttrID", query = "SELECT c FROM Collectionobjectattr c WHERE c.attrID = :attrID"),
    @NamedQuery(name = "Collectionobjectattr.findByTimestampCreated", query = "SELECT c FROM Collectionobjectattr c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobjectattr.findByTimestampModified", query = "SELECT c FROM Collectionobjectattr c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobjectattr.findByVersion", query = "SELECT c FROM Collectionobjectattr c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobjectattr.findByCollectionMemberID", query = "SELECT c FROM Collectionobjectattr c WHERE c.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Collectionobjectattr.findByDoubleValue", query = "SELECT c FROM Collectionobjectattr c WHERE c.doubleValue = :doubleValue"),
    @NamedQuery(name = "Collectionobjectattr.findByStrValue", query = "SELECT c FROM Collectionobjectattr c WHERE c.strValue = :strValue")})
public class Collectionobjectattr extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttrID")
    private Integer attrID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DoubleValue")
    private Double doubleValue;
    
    @Size(max = 255)
    @Column(name = "StrValue")
    private String strValue;
    
    @JoinColumn(name = "AttributeDefID", referencedColumnName = "AttributeDefID")
    @ManyToOne(optional = false)
    private Attributedef attributeDefID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne(optional = false)
    private Collectionobject collectionObjectID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Collectionobjectattr() {
    }

    public Collectionobjectattr(Integer attrID) {
        this.attrID = attrID;
    }

    public Collectionobjectattr(Integer attrID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.attrID = attrID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getAttrID() {
        return attrID;
    }

    public void setAttrID(Integer attrID) {
        this.attrID = attrID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public Attributedef getAttributeDefID() {
        return attributeDefID;
    }

    public void setAttributeDefID(Attributedef attributeDefID) {
        this.attributeDefID = attributeDefID;
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
        hash += (attrID != null ? attrID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectattr)) {
            return false;
        }
        Collectionobjectattr other = (Collectionobjectattr) object;
        if ((this.attrID == null && other.attrID != null) || (this.attrID != null && !this.attrID.equals(other.attrID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobjectattr[ attrID=" + attrID + " ]";
    }
    
}

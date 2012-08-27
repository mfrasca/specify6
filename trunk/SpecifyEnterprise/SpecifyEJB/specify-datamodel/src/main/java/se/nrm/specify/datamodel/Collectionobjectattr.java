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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "collectionobjectattr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobjectattr.findAll", query = "SELECT c FROM Collectionobjectattr c"),
    @NamedQuery(name = "Collectionobjectattr.findByAttrId", query = "SELECT c FROM Collectionobjectattr c WHERE c.attrId = :attrId"),
    @NamedQuery(name = "Collectionobjectattr.findByTimestampCreated", query = "SELECT c FROM Collectionobjectattr c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobjectattr.findByTimestampModified", query = "SELECT c FROM Collectionobjectattr c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobjectattr.findByVersion", query = "SELECT c FROM Collectionobjectattr c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobjectattr.findByCollectionMemberId", query = "SELECT c FROM Collectionobjectattr c WHERE c.collectionMemberId = :collectionMemberId"),
    @NamedQuery(name = "Collectionobjectattr.findByDoubleValue", query = "SELECT c FROM Collectionobjectattr c WHERE c.dblValue = :doubleValue"),
    @NamedQuery(name = "Collectionobjectattr.findByStrValue", query = "SELECT c FROM Collectionobjectattr c WHERE c.strValue = :strValue")})
public class Collectionobjectattr extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttrID")
    private Integer attrId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "DoubleValue")
    private Double dblValue;
    
    @Size(max = 255)
    @Column(name = "StrValue")
    private String strValue;
    
    @JoinColumn(name = "AttributeDefID", referencedColumnName = "AttributeDefID")
    @NotNull
    @ManyToOne(optional = false)
    private Attributedef definition;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @NotNull
    @ManyToOne(optional = false)
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Collectionobjectattr() {
    }

    public Collectionobjectattr(Integer attrId) {
        this.attrId = attrId;
    }

    public Collectionobjectattr(Integer attrId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.attrId = attrId; 
        this.collectionMemberId = collectionMemberId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (attrId != null) ? attrId.toString() : "0";
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Double getDblValue() {
        return dblValue;
    }

    public void setDblValue(Double dblValue) {
        this.dblValue = dblValue;
    }

 

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    @XmlTransient
    @NotNull(message="Collectionobject must be specified.")
    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Difinition must be specified.")
    public Attributedef getDefinition() {
        return definition;
    }

    public void setDefinition(Attributedef definition) {
        this.definition = definition;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Collectionobject) {
            this.collectionObject = (Collectionobject)parent;   
        }
    }
    
    @Override
    public String getEntityName() {
        return "collectionObjectAttr";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrId != null ? attrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobjectattr)) {
            return false;
        }
        Collectionobjectattr other = (Collectionobjectattr) object;
        if ((this.attrId == null && other.attrId != null) || (this.attrId != null && !this.attrId.equals(other.attrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobjectattr[ attrID=" + attrId + " ]";
    }
 
}

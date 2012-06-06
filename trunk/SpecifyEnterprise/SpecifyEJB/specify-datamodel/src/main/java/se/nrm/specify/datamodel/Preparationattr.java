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
@Table(name = "preparationattr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preparationattr.findAll", query = "SELECT p FROM Preparationattr p"),
    @NamedQuery(name = "Preparationattr.findByAttrID", query = "SELECT p FROM Preparationattr p WHERE p.attrId = :attrID"),
    @NamedQuery(name = "Preparationattr.findByTimestampCreated", query = "SELECT p FROM Preparationattr p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Preparationattr.findByTimestampModified", query = "SELECT p FROM Preparationattr p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Preparationattr.findByVersion", query = "SELECT p FROM Preparationattr p WHERE p.version = :version"),
    @NamedQuery(name = "Preparationattr.findByCollectionMemberID", query = "SELECT p FROM Preparationattr p WHERE p.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Preparationattr.findByDoubleValue", query = "SELECT p FROM Preparationattr p WHERE p.dblValue = :doubleValue"),
    @NamedQuery(name = "Preparationattr.findByStrValue", query = "SELECT p FROM Preparationattr p WHERE p.strValue = :strValue")})
public class Preparationattr extends BaseEntity {  
    
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
    @ManyToOne(optional = false)
    private Attributedef definition;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne  
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "PreparationId", referencedColumnName = "PreparationID")
    @ManyToOne(optional = false)
    private Preparation preparation;

    public Preparationattr() {
    }

    public Preparationattr(Integer attrId) {
        this.attrId = attrId;
    }

    public Preparationattr(Integer attrId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.attrId = attrId; 
        this.collectionMemberId = collectionMemberId;
    }

    
    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
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

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Double getDblValue() {
        return dblValue;
    }

    public void setDblValue(Double dblValue) {
        this.dblValue = dblValue;
    }

    public Attributedef getDefinition() {
        return definition;
    }

    public void setDefinition(Attributedef definition) {
        this.definition = definition;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
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
        if (!(object instanceof Preparationattr)) {
            return false;
        }
        Preparationattr other = (Preparationattr) object;
        if ((this.attrId == null && other.attrId != null) || (this.attrId != null && !this.attrId.equals(other.attrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preparationattr[ attrID=" + attrId + " ]";
    }
    
}

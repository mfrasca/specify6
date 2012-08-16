package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "attributedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attributedef.findAll", query = "SELECT a FROM Attributedef a"),
    @NamedQuery(name = "Attributedef.findByAttributeDefID", query = "SELECT a FROM Attributedef a WHERE a.attributeDefId = :attributeDefID"),
    @NamedQuery(name = "Attributedef.findByTimestampCreated", query = "SELECT a FROM Attributedef a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Attributedef.findByTimestampModified", query = "SELECT a FROM Attributedef a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Attributedef.findByVersion", query = "SELECT a FROM Attributedef a WHERE a.version = :version"),
    @NamedQuery(name = "Attributedef.findByDataType", query = "SELECT a FROM Attributedef a WHERE a.dataType = :dataType"),
    @NamedQuery(name = "Attributedef.findByFieldName", query = "SELECT a FROM Attributedef a WHERE a.fieldName = :fieldName"),
    @NamedQuery(name = "Attributedef.findByTableType", query = "SELECT a FROM Attributedef a WHERE a.tableType = :tableType")})
public class Attributedef extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttributeDefID")
    private Integer attributeDefId;
     
    @Column(name = "DataType")
    private Short dataType;
    
    @Size(max = 32)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Column(name = "TableType")
    private Short tableType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Collectingeventattr> collectingEventAttrs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Collectionobjectattr> collectionObjectAttrs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definition")
    private Collection<Preparationattr> preparationAttrs;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "PrepTypeID", referencedColumnName = "PrepTypeID")
    @ManyToOne
    private Preptype prepType;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline discipline;

    public Attributedef() {
    }

    public Attributedef(Integer attributeDefId) {
        this.attributeDefId = attributeDefId;
    }

    public Attributedef(Integer attributeDefId, Date timestampCreated) {
        super(timestampCreated);
        this.attributeDefId = attributeDefId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (attributeDefId != null) ? attributeDefId.toString() : "0";
    }

    public Integer getAttributeDefId() {
        return attributeDefId;
    }

    public void setAttributeDefId(Integer attributeDefId) {
        this.attributeDefId = attributeDefId;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingEventAttrs() {
        return collectingEventAttrs;
    }

    public void setCollectingEventAttrs(Collection<Collectingeventattr> collectingEventAttrs) {
        this.collectingEventAttrs = collectingEventAttrs;
    }

    @XmlTransient
    public Collection<Collectionobjectattr> getCollectionObjectAttrs() {
        return collectionObjectAttrs;
    }

    public void setCollectionObjectAttrs(Collection<Collectionobjectattr> collectionObjectAttrs) {
        this.collectionObjectAttrs = collectionObjectAttrs;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Preptype getPrepType() {
        return prepType;
    }

    public void setPrepType(Preptype prepType) {
        this.prepType = prepType;
    }

    @XmlTransient
    public Collection<Preparationattr> getPreparationAttrs() {
        return preparationAttrs;
    }

    public void setPreparationAttrs(Collection<Preparationattr> preparationAttrs) {
        this.preparationAttrs = preparationAttrs;
    }

 
    
    public Short getDataType() {
        return dataType;
    }

    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Short getTableType() {
        return tableType;
    }

    public void setTableType(Short tableType) {
        this.tableType = tableType;
    }

 
    

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributeDefId != null ? attributeDefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attributedef)) {
            return false;
        }
        Attributedef other = (Attributedef) object;
        if ((this.attributeDefId == null && other.attributeDefId != null) || (this.attributeDefId != null && !this.attributeDefId.equals(other.attributeDefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attributedef[ attributeDefId=" + attributeDefId + " ]";
    }
 
}

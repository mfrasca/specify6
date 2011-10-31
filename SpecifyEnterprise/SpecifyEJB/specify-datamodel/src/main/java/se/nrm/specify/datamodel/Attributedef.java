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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Attributedef.findByAttributeDefID", query = "SELECT a FROM Attributedef a WHERE a.attributeDefID = :attributeDefID"),
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
    private Integer attributeDefID;
     
    @Column(name = "DataType")
    private Short dataType;
    
    @Size(max = 32)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Column(name = "TableType")
    private Short tableType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attributeDefID")
    private Collection<Collectingeventattr> collectingeventattrCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attributeDefID")
    private Collection<Collectionobjectattr> collectionobjectattrCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attributeDefID")
    private Collection<Preparationattr> preparationattrCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "PrepTypeID", referencedColumnName = "PrepTypeID")
    @ManyToOne
    private Preptype prepTypeID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;

    public Attributedef() {
    }

    public Attributedef(Integer attributeDefID) {
        this.attributeDefID = attributeDefID;
    }

    public Attributedef(Integer attributeDefID, Date timestampCreated) {
        super(timestampCreated);
        this.attributeDefID = attributeDefID; 
    }

    public Integer getAttributeDefID() {
        return attributeDefID;
    }

    public void setAttributeDefID(Integer attributeDefID) {
        this.attributeDefID = attributeDefID;
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

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingeventattrCollection() {
        return collectingeventattrCollection;
    }

    public void setCollectingeventattrCollection(Collection<Collectingeventattr> collectingeventattrCollection) {
        this.collectingeventattrCollection = collectingeventattrCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectattr> getCollectionobjectattrCollection() {
        return collectionobjectattrCollection;
    }

    public void setCollectionobjectattrCollection(Collection<Collectionobjectattr> collectionobjectattrCollection) {
        this.collectionobjectattrCollection = collectionobjectattrCollection;
    }

    @XmlTransient
    public Collection<Preparationattr> getPreparationattrCollection() {
        return preparationattrCollection;
    }

    public void setPreparationattrCollection(Collection<Preparationattr> preparationattrCollection) {
        this.preparationattrCollection = preparationattrCollection;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Preptype getPrepTypeID() {
        return prepTypeID;
    }

    public void setPrepTypeID(Preptype prepTypeID) {
        this.prepTypeID = prepTypeID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributeDefID != null ? attributeDefID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attributedef)) {
            return false;
        }
        Attributedef other = (Attributedef) object;
        if ((this.attributeDefID == null && other.attributeDefID != null) || (this.attributeDefID != null && !this.attributeDefID.equals(other.attributeDefID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attributedef[ attributeDefID=" + attributeDefID + " ]";
    }
    
}

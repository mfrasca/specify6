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
@Table(name = "spexportschemaitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spexportschemaitem.findAll", query = "SELECT s FROM Spexportschemaitem s"),
    @NamedQuery(name = "Spexportschemaitem.findBySpExportSchemaItemID", query = "SELECT s FROM Spexportschemaitem s WHERE s.spExportSchemaItemID = :spExportSchemaItemID"),
    @NamedQuery(name = "Spexportschemaitem.findByTimestampCreated", query = "SELECT s FROM Spexportschemaitem s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spexportschemaitem.findByTimestampModified", query = "SELECT s FROM Spexportschemaitem s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spexportschemaitem.findByVersion", query = "SELECT s FROM Spexportschemaitem s WHERE s.version = :version"),
    @NamedQuery(name = "Spexportschemaitem.findByDataType", query = "SELECT s FROM Spexportschemaitem s WHERE s.dataType = :dataType"),
    @NamedQuery(name = "Spexportschemaitem.findByDescription", query = "SELECT s FROM Spexportschemaitem s WHERE s.description = :description"),
    @NamedQuery(name = "Spexportschemaitem.findByFieldName", query = "SELECT s FROM Spexportschemaitem s WHERE s.fieldName = :fieldName"),
    @NamedQuery(name = "Spexportschemaitem.findByFormatter", query = "SELECT s FROM Spexportschemaitem s WHERE s.formatter = :formatter")})
public class Spexportschemaitem extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpExportSchemaItemID")
    private Integer spExportSchemaItemID;
     
    @Size(max = 32)
    @Column(name = "DataType")
    private String dataType;
    
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 64)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Size(max = 32)
    @Column(name = "Formatter")
    private String formatter;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exportSchemaItemID")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "SpLocaleContainerItemID", referencedColumnName = "SpLocaleContainerItemID")
    @ManyToOne
    private Splocalecontaineritem spLocaleContainerItemID;
    
    @JoinColumn(name = "SpExportSchemaID", referencedColumnName = "SpExportSchemaID")
    @ManyToOne(optional = false)
    private Spexportschema spExportSchemaID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Spexportschemaitem() {
    }

    public Spexportschemaitem(Integer spExportSchemaItemID) {
        this.spExportSchemaItemID = spExportSchemaItemID;
    }

    public Spexportschemaitem(Integer spExportSchemaItemID, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaItemID = spExportSchemaItemID; 
    }

    public Integer getSpExportSchemaItemID() {
        return spExportSchemaItemID;
    }

    public void setSpExportSchemaItemID(Integer spExportSchemaItemID) {
        this.spExportSchemaItemID = spExportSchemaItemID;
    }
 
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappingCollection() {
        return spexportschemaitemmappingCollection;
    }

    public void setSpexportschemaitemmappingCollection(Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection) {
        this.spexportschemaitemmappingCollection = spexportschemaitemmappingCollection;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Splocalecontaineritem getSpLocaleContainerItemID() {
        return spLocaleContainerItemID;
    }

    public void setSpLocaleContainerItemID(Splocalecontaineritem spLocaleContainerItemID) {
        this.spLocaleContainerItemID = spLocaleContainerItemID;
    }

    public Spexportschema getSpExportSchemaID() {
        return spExportSchemaID;
    }

    public void setSpExportSchemaID(Spexportschema spExportSchemaID) {
        this.spExportSchemaID = spExportSchemaID;
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
        hash += (spExportSchemaItemID != null ? spExportSchemaItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschemaitem)) {
            return false;
        }
        Spexportschemaitem other = (Spexportschemaitem) object;
        if ((this.spExportSchemaItemID == null && other.spExportSchemaItemID != null) || (this.spExportSchemaItemID != null && !this.spExportSchemaItemID.equals(other.spExportSchemaItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschemaitem[ spExportSchemaItemID=" + spExportSchemaItemID + " ]";
    }
    
}

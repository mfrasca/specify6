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
    @NamedQuery(name = "Spexportschemaitem.findBySpExportSchemaItemID", query = "SELECT s FROM Spexportschemaitem s WHERE s.spExportSchemaItemId = :spExportSchemaItemID"),
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
    private Integer spExportSchemaItemId;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exportSchemaItem")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappings;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "SpLocaleContainerItemID", referencedColumnName = "SpLocaleContainerItemID")
    @ManyToOne
    private Splocalecontaineritem spLocaleContainerItem;
    
    @JoinColumn(name = "SpExportSchemaID", referencedColumnName = "SpExportSchemaID")
    @ManyToOne(optional = false)
    private Spexportschema spExportSchema;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Spexportschemaitem() {
    }

    public Spexportschemaitem(Integer spExportSchemaItemId) {
        this.spExportSchemaItemId = spExportSchemaItemId;
    }

    public Spexportschemaitem(Integer spExportSchemaItemId, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaItemId = spExportSchemaItemId; 
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

    public Spexportschema getSpExportSchema() {
        return spExportSchema;
    }

    public void setSpExportSchema(Spexportschema spExportSchema) {
        this.spExportSchema = spExportSchema;
    }

    public Integer getSpExportSchemaItemId() {
        return spExportSchemaItemId;
    }

    public void setSpExportSchemaItemId(Integer spExportSchemaItemId) {
        this.spExportSchemaItemId = spExportSchemaItemId;
    }

    public Splocalecontaineritem getSpLocaleContainerItem() {
        return spLocaleContainerItem;
    }

    public void setSpLocaleContainerItem(Splocalecontaineritem spLocaleContainerItem) {
        this.spLocaleContainerItem = spLocaleContainerItem;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappings() {
        return spexportschemaitemmappings;
    }

    public void setSpexportschemaitemmappings(Collection<Spexportschemaitemmapping> spexportschemaitemmappings) {
        this.spexportschemaitemmappings = spexportschemaitemmappings;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaItemId != null ? spExportSchemaItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschemaitem)) {
            return false;
        }
        Spexportschemaitem other = (Spexportschemaitem) object;
        if ((this.spExportSchemaItemId == null && other.spExportSchemaItemId != null) || (this.spExportSchemaItemId != null && !this.spExportSchemaItemId.equals(other.spExportSchemaItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschemaitem[ spExportSchemaItemID=" + spExportSchemaItemId + " ]";
    }
    
}

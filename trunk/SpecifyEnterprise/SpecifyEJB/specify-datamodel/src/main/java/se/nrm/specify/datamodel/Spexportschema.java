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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spexportschema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spexportschema.findAll", query = "SELECT s FROM Spexportschema s"),
    @NamedQuery(name = "Spexportschema.findBySpExportSchemaId", query = "SELECT s FROM Spexportschema s WHERE s.spExportSchemaId = :spExportSchemaId"),
    @NamedQuery(name = "Spexportschema.findByTimestampCreated", query = "SELECT s FROM Spexportschema s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spexportschema.findByTimestampModified", query = "SELECT s FROM Spexportschema s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spexportschema.findByVersion", query = "SELECT s FROM Spexportschema s WHERE s.version = :version"),
    @NamedQuery(name = "Spexportschema.findByDescription", query = "SELECT s FROM Spexportschema s WHERE s.description = :description"),
    @NamedQuery(name = "Spexportschema.findBySchemaName", query = "SELECT s FROM Spexportschema s WHERE s.schemaName = :schemaName"),
    @NamedQuery(name = "Spexportschema.findBySchemaVersion", query = "SELECT s FROM Spexportschema s WHERE s.schemaVersion = :schemaVersion")})
public class Spexportschema extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpExportSchemaID")
    private Integer spExportSchemaId;
     
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 80)
    @Column(name = "SchemaName")
    private String schemaName;
    
    @Size(max = 80)
    @Column(name = "SchemaVersion")
    private String schemaVersion;
    
    @JoinTable(name = "sp_schema_mapping", joinColumns = {
        @JoinColumn(name = "SpExportSchemaID", referencedColumnName = "SpExportSchemaID")}, inverseJoinColumns = {
        @JoinColumn(name = "SpExportSchemaMappingID", referencedColumnName = "SpExportSchemaMappingID")})
    @ManyToMany
    private Collection<Spexportschemamapping> spExportSchemaMappings;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Discipline discipline
            ;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spExportSchema")
    private Collection<Spexportschemaitem> spExportSchemaItems;

    public Spexportschema() {
    }

    public Spexportschema(Integer spExportSchemaId) {
        this.spExportSchemaId = spExportSchemaId;
    }

    public Spexportschema(Integer spExportSchemaId, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaId = spExportSchemaId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spExportSchemaId != null) ? spExportSchemaId.toString() : "0";
    }
    
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

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getSpExportSchemaId() {
        return spExportSchemaId;
    }

    public void setSpExportSchemaId(Integer spExportSchemaId) {
        this.spExportSchemaId = spExportSchemaId;
    }

    @XmlTransient
    public Collection<Spexportschemaitem> getSpExportSchemaItems() {
        return spExportSchemaItems;
    }

    public void setSpExportSchemaItems(Collection<Spexportschemaitem> spExportSchemaItems) {
        this.spExportSchemaItems = spExportSchemaItems;
    }

    @XmlTransient
    public Collection<Spexportschemamapping> getSpExportSchemaMappings() {
        return spExportSchemaMappings;
    }

    public void setSpExportSchemaMappings(Collection<Spexportschemamapping> spExportSchemaMappings) {
        this.spExportSchemaMappings = spExportSchemaMappings;
    }
 

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }
 

    @Override
    public String getEntityName() {
        return "spExportSchema";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaId != null ? spExportSchemaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschema)) {
            return false;
        }
        Spexportschema other = (Spexportschema) object;
        if ((this.spExportSchemaId == null && other.spExportSchemaId != null) || (this.spExportSchemaId != null && !this.spExportSchemaId.equals(other.spExportSchemaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschema[ spExportSchemaID=" + spExportSchemaId + " ]";
    }
 
}

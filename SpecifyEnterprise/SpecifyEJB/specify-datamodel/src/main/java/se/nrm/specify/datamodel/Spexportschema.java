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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Spexportschema.findBySpExportSchemaID", query = "SELECT s FROM Spexportschema s WHERE s.spExportSchemaID = :spExportSchemaID"),
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
    private Integer spExportSchemaID;
     
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
    private Collection<Spexportschemamapping> spexportschemamappingCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spExportSchemaID")
    private Collection<Spexportschemaitem> spexportschemaitemCollection;

    public Spexportschema() {
    }

    public Spexportschema(Integer spExportSchemaID) {
        this.spExportSchemaID = spExportSchemaID;
    }

    public Spexportschema(Integer spExportSchemaID, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaID = spExportSchemaID; 
    }

    public Integer getSpExportSchemaID() {
        return spExportSchemaID;
    }

    public void setSpExportSchemaID(Integer spExportSchemaID) {
        this.spExportSchemaID = spExportSchemaID;
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

    @XmlTransient
    public Collection<Spexportschemamapping> getSpexportschemamappingCollection() {
        return spexportschemamappingCollection;
    }

    public void setSpexportschemamappingCollection(Collection<Spexportschemamapping> spexportschemamappingCollection) {
        this.spexportschemamappingCollection = spexportschemamappingCollection;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
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

    @XmlTransient
    public Collection<Spexportschemaitem> getSpexportschemaitemCollection() {
        return spexportschemaitemCollection;
    }

    public void setSpexportschemaitemCollection(Collection<Spexportschemaitem> spexportschemaitemCollection) {
        this.spexportschemaitemCollection = spexportschemaitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaID != null ? spExportSchemaID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschema)) {
            return false;
        }
        Spexportschema other = (Spexportschema) object;
        if ((this.spExportSchemaID == null && other.spExportSchemaID != null) || (this.spExportSchemaID != null && !this.spExportSchemaID.equals(other.spExportSchemaID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschema[ spExportSchemaID=" + spExportSchemaID + " ]";
    }
    
}

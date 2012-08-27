package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 
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
@Table(name = "spexportschemamapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spexportschemamapping.findAll", query = "SELECT s FROM Spexportschemamapping s"),
    @NamedQuery(name = "Spexportschemamapping.findBySpExportSchemaMappingId", query = "SELECT s FROM Spexportschemamapping s WHERE s.spExportSchemaMappingId = :spExportSchemaMappingId"),
    @NamedQuery(name = "Spexportschemamapping.findByTimestampCreated", query = "SELECT s FROM Spexportschemamapping s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spexportschemamapping.findByTimestampModified", query = "SELECT s FROM Spexportschemamapping s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spexportschemamapping.findByVersion", query = "SELECT s FROM Spexportschemamapping s WHERE s.version = :version"),
    @NamedQuery(name = "Spexportschemamapping.findByDescription", query = "SELECT s FROM Spexportschemamapping s WHERE s.description = :description"),
    @NamedQuery(name = "Spexportschemamapping.findByMappingName", query = "SELECT s FROM Spexportschemamapping s WHERE s.mappingName = :mappingName"),
    @NamedQuery(name = "Spexportschemamapping.findByTimeStampExported", query = "SELECT s FROM Spexportschemamapping s WHERE s.timestampExported = :timeStampExported"),
    @NamedQuery(name = "Spexportschemamapping.findByCollectionMemberId", query = "SELECT s FROM Spexportschemamapping s WHERE s.collectionMemberId = :collectionMemberId")})
public class Spexportschemamapping extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpExportSchemaMappingID")
    private Integer spExportSchemaMappingId;
     
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 50)
    @Column(name = "MappingName")
    private String mappingName;
    
    @Column(name = "CollectionMemberID")
    private Integer collectionMemberId;
    
    @Column(name = "TimeStampExported")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampExported;
    
    @ManyToMany(mappedBy = "spExportSchemaMappings")
    private Collection<Spexportschema> spExportSchemas;
    
    @OneToMany(mappedBy = "exportSchemaMapping")
    private Collection<Spexportschemaitemmapping> mappings;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Spexportschemamapping() {
    }

    public Spexportschemamapping(Integer spExportSchemaMappingId) {
        this.spExportSchemaMappingId = spExportSchemaMappingId;
    }

    public Spexportschemamapping(Integer spExportSchemaMappingId, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaMappingId = spExportSchemaMappingId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spExportSchemaMappingId  != null) ? spExportSchemaMappingId.toString() : "0";
    }
  
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public Integer getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(Integer collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getMappings() {
        return mappings;
    }

    public void setMappings(Collection<Spexportschemaitemmapping> mappings) {
        this.mappings = mappings;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getSpExportSchemaMappingId() {
        return spExportSchemaMappingId;
    }

    public void setSpExportSchemaMappingId(Integer spExportSchemaMappingId) {
        this.spExportSchemaMappingId = spExportSchemaMappingId;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpExportSchemas() {
        return spExportSchemas;
    }

    public void setSpExportSchemas(Collection<Spexportschema> spExportSchemas) {
        this.spExportSchemas = spExportSchemas;
    }

    public Date getTimestampExported() {
        return timestampExported;
    }

    public void setTimestampExported(Date timestampExported) {
        this.timestampExported = timestampExported;
    }
  
    @Override
    public String getEntityName() {
        return "spExportSchemaMapping";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaMappingId != null ? spExportSchemaMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschemamapping)) {
            return false;
        }
        Spexportschemamapping other = (Spexportschemamapping) object;
        if ((this.spExportSchemaMappingId == null && other.spExportSchemaMappingId != null) || (this.spExportSchemaMappingId != null && !this.spExportSchemaMappingId.equals(other.spExportSchemaMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschemamapping[ spExportSchemaMappingID=" + spExportSchemaMappingId + " ]";
    } 
}

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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Spexportschemamapping.findBySpExportSchemaMappingID", query = "SELECT s FROM Spexportschemamapping s WHERE s.spExportSchemaMappingID = :spExportSchemaMappingID"),
    @NamedQuery(name = "Spexportschemamapping.findByTimestampCreated", query = "SELECT s FROM Spexportschemamapping s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spexportschemamapping.findByTimestampModified", query = "SELECT s FROM Spexportschemamapping s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spexportschemamapping.findByVersion", query = "SELECT s FROM Spexportschemamapping s WHERE s.version = :version"),
    @NamedQuery(name = "Spexportschemamapping.findByDescription", query = "SELECT s FROM Spexportschemamapping s WHERE s.description = :description"),
    @NamedQuery(name = "Spexportschemamapping.findByMappingName", query = "SELECT s FROM Spexportschemamapping s WHERE s.mappingName = :mappingName"),
    @NamedQuery(name = "Spexportschemamapping.findByTimeStampExported", query = "SELECT s FROM Spexportschemamapping s WHERE s.timeStampExported = :timeStampExported"),
    @NamedQuery(name = "Spexportschemamapping.findByCollectionMemberID", query = "SELECT s FROM Spexportschemamapping s WHERE s.collectionMemberID = :collectionMemberID")})
public class Spexportschemamapping extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpExportSchemaMappingID")
    private Integer spExportSchemaMappingID;
     
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 50)
    @Column(name = "MappingName")
    private String mappingName;
    
    @Column(name = "TimeStampExported")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStampExported;
    
    @Column(name = "CollectionMemberID")
    private Integer collectionMemberID;
    
    @ManyToMany(mappedBy = "spexportschemamappingCollection")
    private Collection<Spexportschema> spexportschemaCollection;
    
    @OneToMany(mappedBy = "spExportSchemaMappingID")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Spexportschemamapping() {
    }

    public Spexportschemamapping(Integer spExportSchemaMappingID) {
        this.spExportSchemaMappingID = spExportSchemaMappingID;
    }

    public Spexportschemamapping(Integer spExportSchemaMappingID, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaMappingID = spExportSchemaMappingID; 
    }

    public Integer getSpExportSchemaMappingID() {
        return spExportSchemaMappingID;
    }

    public void setSpExportSchemaMappingID(Integer spExportSchemaMappingID) {
        this.spExportSchemaMappingID = spExportSchemaMappingID;
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

    public Date getTimeStampExported() {
        return timeStampExported;
    }

    public void setTimeStampExported(Date timeStampExported) {
        this.timeStampExported = timeStampExported;
    }

    public Integer getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(Integer collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpexportschemaCollection() {
        return spexportschemaCollection;
    }

    public void setSpexportschemaCollection(Collection<Spexportschema> spexportschemaCollection) {
        this.spexportschemaCollection = spexportschemaCollection;
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

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaMappingID != null ? spExportSchemaMappingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschemamapping)) {
            return false;
        }
        Spexportschemamapping other = (Spexportschemamapping) object;
        if ((this.spExportSchemaMappingID == null && other.spExportSchemaMappingID != null) || (this.spExportSchemaMappingID != null && !this.spExportSchemaMappingID.equals(other.spExportSchemaMappingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschemamapping[ spExportSchemaMappingID=" + spExportSchemaMappingID + " ]";
    }
    
}

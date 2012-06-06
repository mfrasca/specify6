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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spexportschemaitemmapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spexportschemaitemmapping.findAll", query = "SELECT s FROM Spexportschemaitemmapping s"),
    @NamedQuery(name = "Spexportschemaitemmapping.findBySpExportSchemaItemMappingID", query = "SELECT s FROM Spexportschemaitemmapping s WHERE s.spExportSchemaItemMappingId = :spExportSchemaItemMappingID"),
    @NamedQuery(name = "Spexportschemaitemmapping.findByTimestampCreated", query = "SELECT s FROM Spexportschemaitemmapping s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spexportschemaitemmapping.findByTimestampModified", query = "SELECT s FROM Spexportschemaitemmapping s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spexportschemaitemmapping.findByVersion", query = "SELECT s FROM Spexportschemaitemmapping s WHERE s.version = :version"),
    @NamedQuery(name = "Spexportschemaitemmapping.findByRemarks", query = "SELECT s FROM Spexportschemaitemmapping s WHERE s.remarks = :remarks")})
public class Spexportschemaitemmapping extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpExportSchemaItemMappingID")
    private Integer spExportSchemaItemMappingId;
      
    @Size(max = 255)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "SpExportSchemaMappingID", referencedColumnName = "SpExportSchemaMappingID")
    @ManyToOne
    private Spexportschemamapping exportSchemaMapping;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @XmlTransient
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @XmlTransient
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpQueryFieldID", referencedColumnName = "SpQueryFieldID")
    @ManyToOne
    @XmlTransient
    private Spqueryfield queryField;
    
    @JoinColumn(name = "ExportSchemaItemID", referencedColumnName = "SpExportSchemaItemID")
    @ManyToOne(optional = false)
    private Spexportschemaitem exportSchemaItem;

    public Spexportschemaitemmapping() {
    }

    public Spexportschemaitemmapping(Integer spExportSchemaItemMappingId) {
        this.spExportSchemaItemMappingId = spExportSchemaItemMappingId;
    }

    public Spexportschemaitemmapping(Integer spExportSchemaItemMappingId, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaItemMappingId = spExportSchemaItemMappingId; 
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Spexportschemaitem getExportSchemaItem() {
        return exportSchemaItem;
    }

    public void setExportSchemaItem(Spexportschemaitem exportSchemaItem) {
        this.exportSchemaItem = exportSchemaItem;
    }

    public Spexportschemamapping getExportSchemaMapping() {
        return exportSchemaMapping;
    }

    public void setExportSchemaMapping(Spexportschemamapping exportSchemaMapping) {
        this.exportSchemaMapping = exportSchemaMapping;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Spqueryfield getQueryField() {
        return queryField;
    }

    public void setQueryField(Spqueryfield queryField) {
        this.queryField = queryField;
    }

    public Integer getSpExportSchemaItemMappingId() {
        return spExportSchemaItemMappingId;
    }

    public void setSpExportSchemaItemMappingId(Integer spExportSchemaItemMappingId) {
        this.spExportSchemaItemMappingId = spExportSchemaItemMappingId;
    }
 
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaItemMappingId != null ? spExportSchemaItemMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschemaitemmapping)) {
            return false;
        }
        Spexportschemaitemmapping other = (Spexportschemaitemmapping) object;
        if ((this.spExportSchemaItemMappingId == null && other.spExportSchemaItemMappingId != null) || (this.spExportSchemaItemMappingId != null && !this.spExportSchemaItemMappingId.equals(other.spExportSchemaItemMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschemaitemmapping[ spExportSchemaItemMappingID=" + spExportSchemaItemMappingId + " ]";
    }
    
}

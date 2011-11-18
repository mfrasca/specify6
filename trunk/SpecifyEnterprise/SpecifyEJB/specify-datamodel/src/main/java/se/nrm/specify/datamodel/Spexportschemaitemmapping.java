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
    @NamedQuery(name = "Spexportschemaitemmapping.findBySpExportSchemaItemMappingID", query = "SELECT s FROM Spexportschemaitemmapping s WHERE s.spExportSchemaItemMappingID = :spExportSchemaItemMappingID"),
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
    private Integer spExportSchemaItemMappingID;
      
    @Size(max = 255)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "SpExportSchemaMappingID", referencedColumnName = "SpExportSchemaMappingID")
    @ManyToOne
    private Spexportschemamapping spExportSchemaMappingID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @XmlTransient
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    @XmlTransient
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpQueryFieldID", referencedColumnName = "SpQueryFieldID")
    @ManyToOne
    @XmlTransient
    private Spqueryfield spQueryFieldID;
    
    @JoinColumn(name = "ExportSchemaItemID", referencedColumnName = "SpExportSchemaItemID")
    @ManyToOne(optional = false)
    private Spexportschemaitem exportSchemaItemID;

    public Spexportschemaitemmapping() {
    }

    public Spexportschemaitemmapping(Integer spExportSchemaItemMappingID) {
        this.spExportSchemaItemMappingID = spExportSchemaItemMappingID;
    }

    public Spexportschemaitemmapping(Integer spExportSchemaItemMappingID, Date timestampCreated) {
        super(timestampCreated);
        this.spExportSchemaItemMappingID = spExportSchemaItemMappingID; 
    }

    public Integer getSpExportSchemaItemMappingID() {
        return spExportSchemaItemMappingID;
    }

    public void setSpExportSchemaItemMappingID(Integer spExportSchemaItemMappingID) {
        this.spExportSchemaItemMappingID = spExportSchemaItemMappingID;
    }
 

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Spexportschemamapping getSpExportSchemaMappingID() {
        return spExportSchemaMappingID;
    }

    public void setSpExportSchemaMappingID(Spexportschemamapping spExportSchemaMappingID) {
        this.spExportSchemaMappingID = spExportSchemaMappingID;
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

    public Spqueryfield getSpQueryFieldID() {
        return spQueryFieldID;
    }

    public void setSpQueryFieldID(Spqueryfield spQueryFieldID) {
        this.spQueryFieldID = spQueryFieldID;
    }

    public Spexportschemaitem getExportSchemaItemID() {
        return exportSchemaItemID;
    }

    public void setExportSchemaItemID(Spexportschemaitem exportSchemaItemID) {
        this.exportSchemaItemID = exportSchemaItemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spExportSchemaItemMappingID != null ? spExportSchemaItemMappingID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spexportschemaitemmapping)) {
            return false;
        }
        Spexportschemaitemmapping other = (Spexportschemaitemmapping) object;
        if ((this.spExportSchemaItemMappingID == null && other.spExportSchemaItemMappingID != null) || (this.spExportSchemaItemMappingID != null && !this.spExportSchemaItemMappingID.equals(other.spExportSchemaItemMappingID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spexportschemaitemmapping[ spExportSchemaItemMappingID=" + spExportSchemaItemMappingID + " ]";
    }
    
}

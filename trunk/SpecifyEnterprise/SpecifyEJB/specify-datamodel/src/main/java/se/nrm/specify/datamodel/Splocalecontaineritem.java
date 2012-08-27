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
@Table(name = "splocalecontaineritem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Splocalecontaineritem.findAll", query = "SELECT s FROM Splocalecontaineritem s"),
    @NamedQuery(name = "Splocalecontaineritem.findBySpLocaleContainerItemId", query = "SELECT s FROM Splocalecontaineritem s WHERE s.spLocaleContainerItemId = :spLocaleContainerItemId"),
    @NamedQuery(name = "Splocalecontaineritem.findByTimestampCreated", query = "SELECT s FROM Splocalecontaineritem s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Splocalecontaineritem.findByTimestampModified", query = "SELECT s FROM Splocalecontaineritem s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Splocalecontaineritem.findByVersion", query = "SELECT s FROM Splocalecontaineritem s WHERE s.version = :version"),
    @NamedQuery(name = "Splocalecontaineritem.findByFormat", query = "SELECT s FROM Splocalecontaineritem s WHERE s.format = :format"),
    @NamedQuery(name = "Splocalecontaineritem.findByIsHidden", query = "SELECT s FROM Splocalecontaineritem s WHERE s.isHidden = :isHidden"),
    @NamedQuery(name = "Splocalecontaineritem.findByIsSystem", query = "SELECT s FROM Splocalecontaineritem s WHERE s.isSystem = :isSystem"),
    @NamedQuery(name = "Splocalecontaineritem.findByIsUIFormatter", query = "SELECT s FROM Splocalecontaineritem s WHERE s.isUIFormatter = :isUIFormatter"),
    @NamedQuery(name = "Splocalecontaineritem.findByName", query = "SELECT s FROM Splocalecontaineritem s WHERE s.name = :name"),
    @NamedQuery(name = "Splocalecontaineritem.findByPickListName", query = "SELECT s FROM Splocalecontaineritem s WHERE s.pickListName = :pickListName"),
    @NamedQuery(name = "Splocalecontaineritem.findByType", query = "SELECT s FROM Splocalecontaineritem s WHERE s.type = :type"),
    @NamedQuery(name = "Splocalecontaineritem.findByIsRequired", query = "SELECT s FROM Splocalecontaineritem s WHERE s.isRequired = :isRequired"),
    @NamedQuery(name = "Splocalecontaineritem.findByWebLinkName", query = "SELECT s FROM Splocalecontaineritem s WHERE s.webLinkName = :webLinkName")})
public class Splocalecontaineritem extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpLocaleContainerItemID")
    private Integer spLocaleContainerItemId;
     
    @Size(max = 64)
    @Column(name = "Format")
    private String format;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsHidden")
    private boolean isHidden;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsSystem")
    private boolean isSystem;
    
    @Column(name = "IsUIFormatter")
    private Boolean isUIFormatter;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 64)
    @Column(name = "PickListName")
    private String pickListName;
    
    @Size(max = 32)
    @Column(name = "Type")
    private String type;
    
    @Column(name = "IsRequired")
    private Boolean isRequired;
    
    @Size(max = 32)
    @Column(name = "WebLinkName")
    private String webLinkName;
    
    @OneToMany(mappedBy = "itemName")
    private Collection<Splocaleitemstr> names;
    
    @OneToMany(mappedBy = "itemDesc")
    private Collection<Splocaleitemstr> descs;
    
    @JoinColumn(name = "SpLocaleContainerID", referencedColumnName = "SpLocaleContainerID")
    @NotNull
    @ManyToOne(optional = false)
    private Splocalecontainer container;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "spLocaleContainerItem")
    private Collection<Spexportschemaitem> spExportSchemaItems;

    public Splocalecontaineritem() {
    }

    public Splocalecontaineritem(Integer spLocaleContainerItemId) {
        this.spLocaleContainerItemId = spLocaleContainerItemId;
    }

    public Splocalecontaineritem(Integer spLocaleContainerItemId, Date timestampCreated, boolean isHidden, boolean isSystem, String name) {
        super(timestampCreated);
        this.spLocaleContainerItemId = spLocaleContainerItemId; 
        this.isHidden = isHidden;
        this.isSystem = isSystem;
        this.name = name;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spLocaleContainerItemId != null) ? spLocaleContainerItemId.toString() : "0";
    }
 
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Boolean getIsUIFormatter() {
        return isUIFormatter;
    }

    public void setIsUIFormatter(Boolean isUIFormatter) {
        this.isUIFormatter = isUIFormatter;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickListName() {
        return pickListName;
    }

    public void setPickListName(String pickListName) {
        this.pickListName = pickListName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getWebLinkName() {
        return webLinkName;
    }

    public void setWebLinkName(String webLinkName) {
        this.webLinkName = webLinkName;
    }

    @NotNull(message="Container must be specified.")
    public Splocalecontainer getContainer() {
        return container;
    }

    public void setContainer(Splocalecontainer container) {
        this.container = container;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getDescs() {
        return descs;
    }

    public void setDescs(Collection<Splocaleitemstr> descs) {
        this.descs = descs;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getNames() {
        return names;
    }

    public void setNames(Collection<Splocaleitemstr> names) {
        this.names = names;
    }

    @XmlTransient
    public Collection<Spexportschemaitem> getSpExportSchemaItems() {
        return spExportSchemaItems;
    }

    public void setSpExportSchemaItems(Collection<Spexportschemaitem> spExportSchemaItems) {
        this.spExportSchemaItems = spExportSchemaItems;
    }

    public Integer getSpLocaleContainerItemId() {
        return spLocaleContainerItemId;
    }

    public void setSpLocaleContainerItemId(Integer spLocaleContainerItemId) {
        this.spLocaleContainerItemId = spLocaleContainerItemId;
    }

    @Override
    public String getEntityName() {
        return "spLocaleContainerItem";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spLocaleContainerItemId != null ? spLocaleContainerItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Splocalecontaineritem)) {
            return false;
        }
        Splocalecontaineritem other = (Splocalecontaineritem) object;
        if ((this.spLocaleContainerItemId == null && other.spLocaleContainerItemId != null) || (this.spLocaleContainerItemId != null && !this.spLocaleContainerItemId.equals(other.spLocaleContainerItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Splocalecontaineritem[ spLocaleContainerItemID=" + spLocaleContainerItemId + " ]";
    }
 
    
}

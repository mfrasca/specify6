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
    @NamedQuery(name = "Splocalecontaineritem.findBySpLocaleContainerItemID", query = "SELECT s FROM Splocalecontaineritem s WHERE s.spLocaleContainerItemID = :spLocaleContainerItemID"),
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
    private Integer spLocaleContainerItemID;
     
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
    
    @OneToMany(mappedBy = "spLocaleContainerItemNameID")
    private Collection<Splocaleitemstr> splocaleitemstrCollection;
    
    @OneToMany(mappedBy = "spLocaleContainerItemDescID")
    private Collection<Splocaleitemstr> splocaleitemstrCollection1;
    
    @JoinColumn(name = "SpLocaleContainerID", referencedColumnName = "SpLocaleContainerID")
    @ManyToOne(optional = false)
    private Splocalecontainer spLocaleContainerID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "spLocaleContainerItemID")
    private Collection<Spexportschemaitem> spexportschemaitemCollection;

    public Splocalecontaineritem() {
    }

    public Splocalecontaineritem(Integer spLocaleContainerItemID) {
        this.spLocaleContainerItemID = spLocaleContainerItemID;
    }

    public Splocalecontaineritem(Integer spLocaleContainerItemID, Date timestampCreated, boolean isHidden, boolean isSystem, String name) {
        super(timestampCreated);
        this.spLocaleContainerItemID = spLocaleContainerItemID; 
        this.isHidden = isHidden;
        this.isSystem = isSystem;
        this.name = name;
    }

    public Integer getSpLocaleContainerItemID() {
        return spLocaleContainerItemID;
    }

    public void setSpLocaleContainerItemID(Integer spLocaleContainerItemID) {
        this.spLocaleContainerItemID = spLocaleContainerItemID;
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

    @XmlTransient
    public Collection<Splocaleitemstr> getSplocaleitemstrCollection() {
        return splocaleitemstrCollection;
    }

    public void setSplocaleitemstrCollection(Collection<Splocaleitemstr> splocaleitemstrCollection) {
        this.splocaleitemstrCollection = splocaleitemstrCollection;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getSplocaleitemstrCollection1() {
        return splocaleitemstrCollection1;
    }

    public void setSplocaleitemstrCollection1(Collection<Splocaleitemstr> splocaleitemstrCollection1) {
        this.splocaleitemstrCollection1 = splocaleitemstrCollection1;
    }

    public Splocalecontainer getSpLocaleContainerID() {
        return spLocaleContainerID;
    }

    public void setSpLocaleContainerID(Splocalecontainer spLocaleContainerID) {
        this.spLocaleContainerID = spLocaleContainerID;
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
        hash += (spLocaleContainerItemID != null ? spLocaleContainerItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Splocalecontaineritem)) {
            return false;
        }
        Splocalecontaineritem other = (Splocalecontaineritem) object;
        if ((this.spLocaleContainerItemID == null && other.spLocaleContainerItemID != null) || (this.spLocaleContainerItemID != null && !this.spLocaleContainerItemID.equals(other.spLocaleContainerItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Splocalecontaineritem[ spLocaleContainerItemID=" + spLocaleContainerItemID + " ]";
    }
    
}

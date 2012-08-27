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
@Table(name = "splocalecontainer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Splocalecontainer.findAll", query = "SELECT s FROM Splocalecontainer s"),
    @NamedQuery(name = "Splocalecontainer.findBySpLocaleContainerId", query = "SELECT s FROM Splocalecontainer s WHERE s.spLocaleContainerId = :spLocaleContainerId"),
    @NamedQuery(name = "Splocalecontainer.findByTimestampCreated", query = "SELECT s FROM Splocalecontainer s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Splocalecontainer.findByTimestampModified", query = "SELECT s FROM Splocalecontainer s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Splocalecontainer.findByVersion", query = "SELECT s FROM Splocalecontainer s WHERE s.version = :version"),
    @NamedQuery(name = "Splocalecontainer.findByFormat", query = "SELECT s FROM Splocalecontainer s WHERE s.format = :format"),
    @NamedQuery(name = "Splocalecontainer.findByIsHidden", query = "SELECT s FROM Splocalecontainer s WHERE s.isHidden = :isHidden"),
    @NamedQuery(name = "Splocalecontainer.findByIsSystem", query = "SELECT s FROM Splocalecontainer s WHERE s.isSystem = :isSystem"),
    @NamedQuery(name = "Splocalecontainer.findByIsUIFormatter", query = "SELECT s FROM Splocalecontainer s WHERE s.isUIFormatter = :isUIFormatter"),
    @NamedQuery(name = "Splocalecontainer.findByName", query = "SELECT s FROM Splocalecontainer s WHERE s.name = :name"),
    @NamedQuery(name = "Splocalecontainer.findByPickListName", query = "SELECT s FROM Splocalecontainer s WHERE s.pickListName = :pickListName"),
    @NamedQuery(name = "Splocalecontainer.findByType", query = "SELECT s FROM Splocalecontainer s WHERE s.type = :type"),
    @NamedQuery(name = "Splocalecontainer.findByAggregator", query = "SELECT s FROM Splocalecontainer s WHERE s.aggregator = :aggregator"),
    @NamedQuery(name = "Splocalecontainer.findByDefaultUI", query = "SELECT s FROM Splocalecontainer s WHERE s.defaultUI = :defaultUI"),
    @NamedQuery(name = "Splocalecontainer.findBySchemaType", query = "SELECT s FROM Splocalecontainer s WHERE s.schemaType = :schemaType")})
public class Splocalecontainer extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpLocaleContainerID")
    private Integer spLocaleContainerId;
     
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
    
    @Size(max = 64)
    @Column(name = "Aggregator")
    private String aggregator;
    
    @Size(max = 64)
    @Column(name = "DefaultUI")
    private String defaultUI;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SchemaType")
    private short schemaType;
    
    @OneToMany(mappedBy = "containerName")
    private Collection<Splocaleitemstr> names;
    
    @OneToMany(mappedBy = "containerDesc")
    private Collection<Splocaleitemstr> descs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "container")
    private Collection<Splocalecontaineritem> items;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Discipline discipline;

    public Splocalecontainer() {
    }

    public Splocalecontainer(Integer spLocaleContainerId) {
        this.spLocaleContainerId = spLocaleContainerId;
    }

    public Splocalecontainer(Integer spLocaleContainerId, Date timestampCreated, boolean isHidden, boolean isSystem, String name, short schemaType) {
        super(timestampCreated);
        this.spLocaleContainerId = spLocaleContainerId;  
        this.isHidden = isHidden;
        this.isSystem = isSystem;
        this.name = name;
        this.schemaType = schemaType;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spLocaleContainerId != null) ? spLocaleContainerId.toString() : "0";
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

    public String getAggregator() {
        return aggregator;
    }

    public void setAggregator(String aggregator) {
        this.aggregator = aggregator;
    }

    public String getDefaultUI() {
        return defaultUI;
    }

    public void setDefaultUI(String defaultUI) {
        this.defaultUI = defaultUI;
    }

    public short getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(short schemaType) {
        this.schemaType = schemaType;
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

    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @XmlTransient
    public Collection<Splocalecontaineritem> getItems() {
        return items;
    }

    public void setItems(Collection<Splocalecontaineritem> items) {
        this.items = items;
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

    public Integer getSpLocaleContainerId() {
        return spLocaleContainerId;
    }

    public void setSpLocaleContainerId(Integer spLocaleContainerId) {
        this.spLocaleContainerId = spLocaleContainerId;
    }

    @Override
    public String getEntityName() {
        return "spLocaleContainer";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spLocaleContainerId != null ? spLocaleContainerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Splocalecontainer)) {
            return false;
        }
        Splocalecontainer other = (Splocalecontainer) object;
        if ((this.spLocaleContainerId == null && other.spLocaleContainerId != null) || (this.spLocaleContainerId != null && !this.spLocaleContainerId.equals(other.spLocaleContainerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Splocalecontainer[ spLocaleContainerID=" + spLocaleContainerId + " ]";
    }

 
}

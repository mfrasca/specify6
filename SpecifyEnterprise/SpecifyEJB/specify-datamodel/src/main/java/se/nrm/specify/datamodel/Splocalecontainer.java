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
    @NamedQuery(name = "Splocalecontainer.findBySpLocaleContainerID", query = "SELECT s FROM Splocalecontainer s WHERE s.spLocaleContainerID = :spLocaleContainerID"),
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
    private Integer spLocaleContainerID;
     
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
    
    @OneToMany(mappedBy = "spLocaleContainerNameID")
    private Collection<Splocaleitemstr> splocaleitemstrCollection;
    
    @OneToMany(mappedBy = "spLocaleContainerDescID")
    private Collection<Splocaleitemstr> splocaleitemstrCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spLocaleContainerID")
    private Collection<Splocalecontaineritem> splocalecontaineritemCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;

    public Splocalecontainer() {
    }

    public Splocalecontainer(Integer spLocaleContainerID) {
        this.spLocaleContainerID = spLocaleContainerID;
    }

    public Splocalecontainer(Integer spLocaleContainerID, Date timestampCreated, boolean isHidden, boolean isSystem, String name, short schemaType) {
        super(timestampCreated);
        this.spLocaleContainerID = spLocaleContainerID;  
        this.isHidden = isHidden;
        this.isSystem = isSystem;
        this.name = name;
        this.schemaType = schemaType;
    }

    public Integer getSpLocaleContainerID() {
        return spLocaleContainerID;
    }

    public void setSpLocaleContainerID(Integer spLocaleContainerID) {
        this.spLocaleContainerID = spLocaleContainerID;
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

    @XmlTransient
    public Collection<Splocalecontaineritem> getSplocalecontaineritemCollection() {
        return splocalecontaineritemCollection;
    }

    public void setSplocalecontaineritemCollection(Collection<Splocalecontaineritem> splocalecontaineritemCollection) {
        this.splocalecontaineritemCollection = splocalecontaineritemCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spLocaleContainerID != null ? spLocaleContainerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Splocalecontainer)) {
            return false;
        }
        Splocalecontainer other = (Splocalecontainer) object;
        if ((this.spLocaleContainerID == null && other.spLocaleContainerID != null) || (this.spLocaleContainerID != null && !this.spLocaleContainerID.equals(other.spLocaleContainerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Splocalecontainer[ spLocaleContainerID=" + spLocaleContainerID + " ]";
    }
    
}

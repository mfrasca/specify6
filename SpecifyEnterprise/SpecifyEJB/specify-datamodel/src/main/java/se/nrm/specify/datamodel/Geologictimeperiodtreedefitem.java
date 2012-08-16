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
import javax.persistence.Lob;
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
@Table(name = "geologictimeperiodtreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findAll", query = "SELECT g FROM Geologictimeperiodtreedefitem g"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByGeologicTimePeriodTreeDefItemID", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.geologicTimePeriodTreeDefItemId = :geologicTimePeriodTreeDefItemID"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTimestampCreated", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTimestampModified", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByVersion", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.version = :version"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByFullNameSeparator", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByIsEnforced", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.isEnforced = :isEnforced"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByIsInFullName", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.isInFullName = :isInFullName"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByName", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.name = :name"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByRankID", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.rankId = :rankID"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTextAfter", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.textAfter = :textAfter"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTextBefore", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.textBefore = :textBefore"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTitle", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.title = :title")})
public class Geologictimeperiodtreedefitem extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GeologicTimePeriodTreeDefItemID")
    private Integer geologicTimePeriodTreeDefItemId;
    
    @Size(max = 32)
    @Column(name = "FullNameSeparator")
    private String fullNameSeparator;
    
    @Column(name = "IsEnforced")
    private Boolean isEnforced;
    
    @Column(name = "IsInFullName")
    private Boolean isInFullName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "TextAfter")
    private String textAfter;
    
    @Size(max = 64)
    @Column(name = "TextBefore")
    private String textBefore;
    
    @Size(max = 64)
    @Column(name = "Title")
    private String title;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedef treeDef;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Geologictimeperiodtreedefitem> children;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "GeologicTimePeriodTreeDefItemID")
    @ManyToOne
    private Geologictimeperiodtreedefitem parent;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definitionItem")
    private Collection<Geologictimeperiod> treeEntries;

    public Geologictimeperiodtreedefitem() {
    }

    public Geologictimeperiodtreedefitem(Integer geologicTimePeriodTreeDefItemId) {
        this.geologicTimePeriodTreeDefItemId = geologicTimePeriodTreeDefItemId;
    }

    public Geologictimeperiodtreedefitem(Integer geologicTimePeriodTreeDefItemId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.geologicTimePeriodTreeDefItemId = geologicTimePeriodTreeDefItemId; 
        this.name = name;
        this.rankId = rankId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (geologicTimePeriodTreeDefItemId != null) ? geologicTimePeriodTreeDefItemId.toString() : "0";
    }

    public Integer getGeologicTimePeriodTreeDefItemId() {
        return geologicTimePeriodTreeDefItemId;
    }

    public void setGeologicTimePeriodTreeDefItemId(Integer geologicTimePeriodTreeDefItemId) {
        this.geologicTimePeriodTreeDefItemId = geologicTimePeriodTreeDefItemId;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

 
 
    public String getFullNameSeparator() {
        return fullNameSeparator;
    }

    public void setFullNameSeparator(String fullNameSeparator) {
        this.fullNameSeparator = fullNameSeparator;
    }

    public Boolean getIsEnforced() {
        return isEnforced;
    }

    public void setIsEnforced(Boolean isEnforced) {
        this.isEnforced = isEnforced;
    }

    public Boolean getIsInFullName() {
        return isInFullName;
    }

    public void setIsInFullName(Boolean isInFullName) {
        this.isInFullName = isInFullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

 

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public void setTextAfter(String textAfter) {
        this.textAfter = textAfter;
    }

    public String getTextBefore() {
        return textBefore;
    }

    public void setTextBefore(String textBefore) {
        this.textBefore = textBefore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getChildren() {
        return children;
    }

    public void setChildren(Collection<Geologictimeperiodtreedefitem> children) {
        this.children = children;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Geologictimeperiodtreedefitem getParent() {
        return parent;
    }

    public void setParent(Geologictimeperiodtreedefitem parent) {
        this.parent = parent;
    }

    @NotNull(message="TreeDef must be specified.")
    public Geologictimeperiodtreedef getTreeDef() {
        return treeDef;
    }

    public void setTreeDef(Geologictimeperiodtreedef treeDef) {
        this.treeDef = treeDef;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Geologictimeperiod> treeEntries) {
        this.treeEntries = treeEntries;
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geologicTimePeriodTreeDefItemId != null ? geologicTimePeriodTreeDefItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geologictimeperiodtreedefitem)) {
            return false;
        }
        Geologictimeperiodtreedefitem other = (Geologictimeperiodtreedefitem) object;
        if ((this.geologicTimePeriodTreeDefItemId == null && other.geologicTimePeriodTreeDefItemId != null) || (this.geologicTimePeriodTreeDefItemId != null && !this.geologicTimePeriodTreeDefItemId.equals(other.geologicTimePeriodTreeDefItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geologictimeperiodtreedefitem[ geologicTimePeriodTreeDefItemID=" + geologicTimePeriodTreeDefItemId + " ]";
    }
 
    
}

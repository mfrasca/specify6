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
@Table(name = "lithostrattreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lithostrattreedefitem.findAll", query = "SELECT l FROM Lithostrattreedefitem l"),
    @NamedQuery(name = "Lithostrattreedefitem.findByLithoStratTreeDefItemID", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.lithoStratTreeDefItemId = :lithoStratTreeDefItemID"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTimestampCreated", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTimestampModified", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Lithostrattreedefitem.findByVersion", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.version = :version"),
    @NamedQuery(name = "Lithostrattreedefitem.findByFullNameSeparator", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Lithostrattreedefitem.findByIsEnforced", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.isEnforced = :isEnforced"),
    @NamedQuery(name = "Lithostrattreedefitem.findByIsInFullName", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.isInFullName = :isInFullName"),
    @NamedQuery(name = "Lithostrattreedefitem.findByName", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.name = :name"),
    @NamedQuery(name = "Lithostrattreedefitem.findByRankID", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.rankId = :rankID"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTextAfter", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.textAfter = :textAfter"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTextBefore", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.textBefore = :textBefore"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTitle", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.title = :title")})
public class Lithostrattreedefitem extends BaseEntity {
     
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LithoStratTreeDefItemID")
    private Integer lithoStratTreeDefItemId;
     
    @Size(max = 32)
    @Column(name = "FullNameSeparator")
    private String fullNameSeparator;
    
    @Column(name = "IsEnforced")
    private Boolean isEnforced;
    
    @Column(name = "IsInFullName")
    private Boolean isInFullName;
    
    @Basic(optional = false) 
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
    
    @OneToMany(mappedBy = "parent")
    private Collection<Lithostrattreedefitem> children;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "LithoStratTreeDefItemID")
    @ManyToOne
    private Lithostrattreedefitem parent;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "LithoStratTreeDefID", referencedColumnName = "LithoStratTreeDefID")
    @ManyToOne(optional = false)
    private Lithostrattreedef treeDef;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definitionItem")
    private Collection<Lithostrat> treeEntries;

    public Lithostrattreedefitem() {
    }

    public Lithostrattreedefitem(Integer lithoStratTreeDefItemId) {
        this.lithoStratTreeDefItemId = lithoStratTreeDefItemId;
    }

    public Lithostrattreedefitem(Integer lithoStratTreeDefItemId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.lithoStratTreeDefItemId = lithoStratTreeDefItemId; 
        this.name = name;
        this.rankId = rankId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (lithoStratTreeDefItemId != null) ? lithoStratTreeDefItemId.toString() : "0";
    }

    public Integer getLithoStratTreeDefItemId() {
        return lithoStratTreeDefItemId;
    }

    public void setLithoStratTreeDefItemId(Integer lithoStratTreeDefItemId) {
        this.lithoStratTreeDefItemId = lithoStratTreeDefItemId;
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

    @NotNull(message="Name must be specified.")
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
    public Collection<Lithostrattreedefitem> getChildren() {
        return children;
    }

    public void setChildren(Collection<Lithostrattreedefitem> children) {
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

    public Lithostrattreedefitem getParent() {
        return parent;
    }

    public void setParent(Lithostrattreedefitem parent) {
        this.parent = parent;
    }

    @NotNull(message="TreeDef must be specified.")
    public Lithostrattreedef getTreeDef() {
        return treeDef;
    }

    public void setTreeDef(Lithostrattreedef treeDef) {
        this.treeDef = treeDef;
    }

    @XmlTransient
    public Collection<Lithostrat> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Lithostrat> treeEntries) {
        this.treeEntries = treeEntries;
    }

    

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lithoStratTreeDefItemId != null ? lithoStratTreeDefItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lithostrattreedefitem)) {
            return false;
        }
        Lithostrattreedefitem other = (Lithostrattreedefitem) object;
        if ((this.lithoStratTreeDefItemId == null && other.lithoStratTreeDefItemId != null) || (this.lithoStratTreeDefItemId != null && !this.lithoStratTreeDefItemId.equals(other.lithoStratTreeDefItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lithostrattreedefitem[ lithoStratTreeDefItemID=" + lithoStratTreeDefItemId + " ]";
    }
}

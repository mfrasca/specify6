package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity bean mapping to taxontreedefitem table
 * 
 * @author idali
 */
@Entity
@Table(name = "taxontreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxontreedefitem.findAll", query = "SELECT t FROM Taxontreedefitem t"),
    @NamedQuery(name = "Taxontreedefitem.findByTaxonTreeDefItemID", query = "SELECT t FROM Taxontreedefitem t WHERE t.taxonTreeDefItemId = :taxonTreeDefItemID"),
    @NamedQuery(name = "Taxontreedefitem.findByTimestampCreated", query = "SELECT t FROM Taxontreedefitem t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxontreedefitem.findByTimestampModified", query = "SELECT t FROM Taxontreedefitem t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxontreedefitem.findByVersion", query = "SELECT t FROM Taxontreedefitem t WHERE t.version = :version"),
    @NamedQuery(name = "Taxontreedefitem.findByFormatToken", query = "SELECT t FROM Taxontreedefitem t WHERE t.formatToken = :formatToken"),
    @NamedQuery(name = "Taxontreedefitem.findByFullNameSeparator", query = "SELECT t FROM Taxontreedefitem t WHERE t.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Taxontreedefitem.findByIsEnforced", query = "SELECT t FROM Taxontreedefitem t WHERE t.isEnforced = :isEnforced"),
    @NamedQuery(name = "Taxontreedefitem.findByIsInFullName", query = "SELECT t FROM Taxontreedefitem t WHERE t.isInFullName = :isInFullName"),
    @NamedQuery(name = "Taxontreedefitem.findByName", query = "SELECT t FROM Taxontreedefitem t WHERE t.name = :name"),
    @NamedQuery(name = "Taxontreedefitem.findByRankID", query = "SELECT t FROM Taxontreedefitem t WHERE t.rankId = :rankID"),
    @NamedQuery(name = "Taxontreedefitem.findByTextAfter", query = "SELECT t FROM Taxontreedefitem t WHERE t.textAfter = :textAfter"),
    @NamedQuery(name = "Taxontreedefitem.findByTextBefore", query = "SELECT t FROM Taxontreedefitem t WHERE t.textBefore = :textBefore"),
    @NamedQuery(name = "Taxontreedefitem.findByTitle", query = "SELECT t FROM Taxontreedefitem t WHERE t.title = :title")})
public class Taxontreedefitem extends BaseEntity {
     
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TaxonTreeDefItemID")
    private Integer taxonTreeDefItemId;
     
    @Size(max = 32)
    @Column(name = "FormatToken")
    private String formatToken;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definitionItem")
    private Collection<Taxon> treeEntries;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID")
    @ManyToOne(optional = false)
    private Taxontreedef treeDef;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Taxontreedefitem> children;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "TaxonTreeDefItemID")
    @ManyToOne
    private Taxontreedefitem parent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Taxontreedefitem() {
    }

    public Taxontreedefitem(Integer taxonTreeDefItemId) {
        this.taxonTreeDefItemId = taxonTreeDefItemId;
    }

    public Taxontreedefitem(Integer taxonTreeDefItemId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.taxonTreeDefItemId = taxonTreeDefItemId; 
        this.name = name;
        this.rankId = rankId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (taxonTreeDefItemId != null) ? taxonTreeDefItemId.toString() : "0";
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getChildren() {
        return children;
    }

    public void setChildren(Collection<Taxontreedefitem> children) {
        this.children = children;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Taxontreedefitem getParent() {
        return parent;
    }

    public void setParent(Taxontreedefitem parent) {
        this.parent = parent;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }

    public Integer getTaxonTreeDefItemId() {
        return taxonTreeDefItemId;
    }

    public void setTaxonTreeDefItemId(Integer taxonTreeDefItemId) {
        this.taxonTreeDefItemId = taxonTreeDefItemId;
    }

    @NotNull(message="TreeDef must be specified.")
    public Taxontreedef getTreeDef() {
        return treeDef;
    }

    public void setTreeDef(Taxontreedef treeDef) {
        this.treeDef = treeDef;
    }

    @XmlTransient
    public Collection<Taxon> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Taxon> treeEntries) {
        this.treeEntries = treeEntries;
    }
 
 
    public String getFormatToken() {
        return formatToken;
    }

    public void setFormatToken(String formatToken) {
        this.formatToken = formatToken;
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
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonTreeDefItemId != null ? taxonTreeDefItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxontreedefitem)) {
            return false;
        }
        Taxontreedefitem other = (Taxontreedefitem) object;
        if ((this.taxonTreeDefItemId == null && other.taxonTreeDefItemId != null) || (this.taxonTreeDefItemId != null && !this.taxonTreeDefItemId.equals(other.taxonTreeDefItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxontreedefitem[ taxonTreeDefItemID=" + taxonTreeDefItemId + " ]";
    } 
}

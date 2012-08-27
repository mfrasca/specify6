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
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "geographytreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geographytreedefitem.findAll", query = "SELECT g FROM Geographytreedefitem g"),
    @NamedQuery(name = "Geographytreedefitem.findByGeographyTreeDefItemId", query = "SELECT g FROM Geographytreedefitem g WHERE g.geographyTreeDefItemId = :geographyTreeDefItemId"),
    @NamedQuery(name = "Geographytreedefitem.findByTimestampCreated", query = "SELECT g FROM Geographytreedefitem g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geographytreedefitem.findByTimestampModified", query = "SELECT g FROM Geographytreedefitem g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geographytreedefitem.findByVersion", query = "SELECT g FROM Geographytreedefitem g WHERE g.version = :version"),
    @NamedQuery(name = "Geographytreedefitem.findByFullNameSeparator", query = "SELECT g FROM Geographytreedefitem g WHERE g.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Geographytreedefitem.findByIsEnforced", query = "SELECT g FROM Geographytreedefitem g WHERE g.isEnforced = :isEnforced"),
    @NamedQuery(name = "Geographytreedefitem.findByIsInFullName", query = "SELECT g FROM Geographytreedefitem g WHERE g.isInFullName = :isInFullName"),
    @NamedQuery(name = "Geographytreedefitem.findByName", query = "SELECT g FROM Geographytreedefitem g WHERE g.name = :name"),
    @NamedQuery(name = "Geographytreedefitem.findByRankID", query = "SELECT g FROM Geographytreedefitem g WHERE g.rankId = :rankID"),
    @NamedQuery(name = "Geographytreedefitem.findByTextAfter", query = "SELECT g FROM Geographytreedefitem g WHERE g.textAfter = :textAfter"),
    @NamedQuery(name = "Geographytreedefitem.findByTextBefore", query = "SELECT g FROM Geographytreedefitem g WHERE g.textBefore = :textBefore"),
    @NamedQuery(name = "Geographytreedefitem.findByTitle", query = "SELECT g FROM Geographytreedefitem g WHERE g.title = :title")})
public class Geographytreedefitem extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GeographyTreeDefItemID")
    private Integer geographyTreeDefItemId;
    
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
    
    @NotNull
    @Basic(optional = false) 
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
    
    @JoinColumn(name = "GeographyTreeDefID", referencedColumnName = "GeographyTreeDefID")
    @NotNull
    @ManyToOne(optional = false)
    private Geographytreedef treeDef;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Geographytreedefitem> children;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "GeographyTreeDefItemID")
    @ManyToOne
    private Geographytreedefitem parent;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definitionItem")
    private Collection<Geography> treeEntries;

    public Geographytreedefitem() {
    }

    public Geographytreedefitem(Integer geographyTreeDefItemId) {
        this.geographyTreeDefItemId = geographyTreeDefItemId;
    }

    public Geographytreedefitem(Integer geographyTreeDefItemId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.geographyTreeDefItemId = geographyTreeDefItemId;
        this.name = name;
        this.rankId = rankId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (geographyTreeDefItemId != null) ? geographyTreeDefItemId.toString() : "0";
    }

    public Integer getGeographyTreeDefItemId() {
        return geographyTreeDefItemId;
    }

    public void setGeographyTreeDefItemId(Integer geographyTreeDefItemId) {
        this.geographyTreeDefItemId = geographyTreeDefItemId;
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
    public Collection<Geographytreedefitem> getChildren() {
        return children;
    }

    public void setChildren(Collection<Geographytreedefitem> children) {
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

    @XmlIDREF
    public Geographytreedefitem getParent() {
        return parent;
    }

    public void setParent(Geographytreedefitem parent) {
        this.parent = parent;
    }

    @NotNull(message="TreeDef must be specified.")
    public Geographytreedef getTreeDef() {
        return treeDef;
    }

    public void setTreeDef(Geographytreedef treeDef) {
        this.treeDef = treeDef;
    }

    @XmlTransient
    public Collection<Geography> getTreeEntries() {
        return treeEntries;
    }

    public void setTreeEntries(Collection<Geography> treeEntries) {
        this.treeEntries = treeEntries;
    }

    
    @Override
    public String getEntityName() {
        return "geographyTreeDefItem";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geographyTreeDefItemId != null ? geographyTreeDefItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geographytreedefitem)) {
            return false;
        }
        Geographytreedefitem other = (Geographytreedefitem) object;
        if ((this.geographyTreeDefItemId == null && other.geographyTreeDefItemId != null) || (this.geographyTreeDefItemId != null && !this.geographyTreeDefItemId.equals(other.geographyTreeDefItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geographytreedefitem[ geographyTreeDefItemID=" + geographyTreeDefItemId + " ]";
    }
 
}

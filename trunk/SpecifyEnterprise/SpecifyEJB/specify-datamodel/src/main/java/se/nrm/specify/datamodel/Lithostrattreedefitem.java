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
    @NamedQuery(name = "Lithostrattreedefitem.findByLithoStratTreeDefItemID", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.lithoStratTreeDefItemID = :lithoStratTreeDefItemID"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTimestampCreated", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Lithostrattreedefitem.findByTimestampModified", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Lithostrattreedefitem.findByVersion", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.version = :version"),
    @NamedQuery(name = "Lithostrattreedefitem.findByFullNameSeparator", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Lithostrattreedefitem.findByIsEnforced", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.isEnforced = :isEnforced"),
    @NamedQuery(name = "Lithostrattreedefitem.findByIsInFullName", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.isInFullName = :isInFullName"),
    @NamedQuery(name = "Lithostrattreedefitem.findByName", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.name = :name"),
    @NamedQuery(name = "Lithostrattreedefitem.findByRankID", query = "SELECT l FROM Lithostrattreedefitem l WHERE l.rankID = :rankID"),
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
    private Integer lithoStratTreeDefItemID;
     
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
    private int rankID;
    
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
    
    @OneToMany(mappedBy = "parentItemID")
    private Collection<Lithostrattreedefitem> lithostrattreedefitemCollection;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "LithoStratTreeDefItemID")
    @ManyToOne
    private Lithostrattreedefitem parentItemID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "LithoStratTreeDefID", referencedColumnName = "LithoStratTreeDefID")
    @ManyToOne(optional = false)
    private Lithostrattreedef lithoStratTreeDefID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lithoStratTreeDefItemID")
    private Collection<Lithostrat> lithostratCollection;

    public Lithostrattreedefitem() {
    }

    public Lithostrattreedefitem(Integer lithoStratTreeDefItemID) {
        this.lithoStratTreeDefItemID = lithoStratTreeDefItemID;
    }

    public Lithostrattreedefitem(Integer lithoStratTreeDefItemID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.lithoStratTreeDefItemID = lithoStratTreeDefItemID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getLithoStratTreeDefItemID() {
        return lithoStratTreeDefItemID;
    }

    public void setLithoStratTreeDefItemID(Integer lithoStratTreeDefItemID) {
        this.lithoStratTreeDefItemID = lithoStratTreeDefItemID;
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

    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
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
    public Collection<Lithostrattreedefitem> getLithostrattreedefitemCollection() {
        return lithostrattreedefitemCollection;
    }

    public void setLithostrattreedefitemCollection(Collection<Lithostrattreedefitem> lithostrattreedefitemCollection) {
        this.lithostrattreedefitemCollection = lithostrattreedefitemCollection;
    }

    public Lithostrattreedefitem getParentItemID() {
        return parentItemID;
    }

    public void setParentItemID(Lithostrattreedefitem parentItemID) {
        this.parentItemID = parentItemID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Lithostrattreedef getLithoStratTreeDefID() {
        return lithoStratTreeDefID;
    }

    public void setLithoStratTreeDefID(Lithostrattreedef lithoStratTreeDefID) {
        this.lithoStratTreeDefID = lithoStratTreeDefID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @XmlTransient
    public Collection<Lithostrat> getLithostratCollection() {
        return lithostratCollection;
    }

    public void setLithostratCollection(Collection<Lithostrat> lithostratCollection) {
        this.lithostratCollection = lithostratCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lithoStratTreeDefItemID != null ? lithoStratTreeDefItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lithostrattreedefitem)) {
            return false;
        }
        Lithostrattreedefitem other = (Lithostrattreedefitem) object;
        if ((this.lithoStratTreeDefItemID == null && other.lithoStratTreeDefItemID != null) || (this.lithoStratTreeDefItemID != null && !this.lithoStratTreeDefItemID.equals(other.lithoStratTreeDefItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lithostrattreedefitem[ lithoStratTreeDefItemID=" + lithoStratTreeDefItemID + " ]";
    }
    
}

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
 * Entity bean mapping to taxontreedefitem table
 * 
 * @author idali
 */
@Entity
@Table(name = "taxontreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxontreedefitem.findAll", query = "SELECT t FROM Taxontreedefitem t"),
    @NamedQuery(name = "Taxontreedefitem.findByTaxonTreeDefItemID", query = "SELECT t FROM Taxontreedefitem t WHERE t.taxonTreeDefItemID = :taxonTreeDefItemID"),
    @NamedQuery(name = "Taxontreedefitem.findByTimestampCreated", query = "SELECT t FROM Taxontreedefitem t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxontreedefitem.findByTimestampModified", query = "SELECT t FROM Taxontreedefitem t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxontreedefitem.findByVersion", query = "SELECT t FROM Taxontreedefitem t WHERE t.version = :version"),
    @NamedQuery(name = "Taxontreedefitem.findByFormatToken", query = "SELECT t FROM Taxontreedefitem t WHERE t.formatToken = :formatToken"),
    @NamedQuery(name = "Taxontreedefitem.findByFullNameSeparator", query = "SELECT t FROM Taxontreedefitem t WHERE t.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Taxontreedefitem.findByIsEnforced", query = "SELECT t FROM Taxontreedefitem t WHERE t.isEnforced = :isEnforced"),
    @NamedQuery(name = "Taxontreedefitem.findByIsInFullName", query = "SELECT t FROM Taxontreedefitem t WHERE t.isInFullName = :isInFullName"),
    @NamedQuery(name = "Taxontreedefitem.findByName", query = "SELECT t FROM Taxontreedefitem t WHERE t.name = :name"),
    @NamedQuery(name = "Taxontreedefitem.findByRankID", query = "SELECT t FROM Taxontreedefitem t WHERE t.rankID = :rankID"),
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
    private Integer taxonTreeDefItemID;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonTreeDefItemID")
    private Collection<Taxon> taxonCollection;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID")
    @ManyToOne(optional = false)
    private Taxontreedef taxonTreeDefID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @OneToMany(mappedBy = "parentItemID")
    private Collection<Taxontreedefitem> taxontreedefitemCollection;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "TaxonTreeDefItemID")
    @ManyToOne
    private Taxontreedefitem parentItemID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Taxontreedefitem() {
    }

    public Taxontreedefitem(Integer taxonTreeDefItemID) {
        this.taxonTreeDefItemID = taxonTreeDefItemID;
    }

    public Taxontreedefitem(Integer taxonTreeDefItemID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.taxonTreeDefItemID = taxonTreeDefItemID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getTaxonTreeDefItemID() {
        return taxonTreeDefItemID;
    }

    public void setTaxonTreeDefItemID(Integer taxonTreeDefItemID) {
        this.taxonTreeDefItemID = taxonTreeDefItemID;
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
    public Collection<Taxon> getTaxonCollection() {
        return taxonCollection;
    }

    public void setTaxonCollection(Collection<Taxon> taxonCollection) {
        this.taxonCollection = taxonCollection;
    }

    public Taxontreedef getTaxonTreeDefID() {
        return taxonTreeDefID;
    }

    public void setTaxonTreeDefID(Taxontreedef taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTaxontreedefitemCollection() {
        return taxontreedefitemCollection;
    }

    public void setTaxontreedefitemCollection(Collection<Taxontreedefitem> taxontreedefitemCollection) {
        this.taxontreedefitemCollection = taxontreedefitemCollection;
    }

    public Taxontreedefitem getParentItemID() {
        return parentItemID;
    }

    public void setParentItemID(Taxontreedefitem parentItemID) {
        this.parentItemID = parentItemID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonTreeDefItemID != null ? taxonTreeDefItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxontreedefitem)) {
            return false;
        }
        Taxontreedefitem other = (Taxontreedefitem) object;
        if ((this.taxonTreeDefItemID == null && other.taxonTreeDefItemID != null) || (this.taxonTreeDefItemID != null && !this.taxonTreeDefItemID.equals(other.taxonTreeDefItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxontreedefitem[ taxonTreeDefItemID=" + taxonTreeDefItemID + " ]";
    }
    
}

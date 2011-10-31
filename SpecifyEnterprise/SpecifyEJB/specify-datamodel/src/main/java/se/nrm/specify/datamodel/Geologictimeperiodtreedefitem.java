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
@Table(name = "geologictimeperiodtreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findAll", query = "SELECT g FROM Geologictimeperiodtreedefitem g"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByGeologicTimePeriodTreeDefItemID", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.geologicTimePeriodTreeDefItemID = :geologicTimePeriodTreeDefItemID"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTimestampCreated", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByTimestampModified", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByVersion", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.version = :version"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByFullNameSeparator", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByIsEnforced", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.isEnforced = :isEnforced"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByIsInFullName", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.isInFullName = :isInFullName"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByName", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.name = :name"),
    @NamedQuery(name = "Geologictimeperiodtreedefitem.findByRankID", query = "SELECT g FROM Geologictimeperiodtreedefitem g WHERE g.rankID = :rankID"),
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
    private Integer geologicTimePeriodTreeDefItemID;
    
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
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedef geologicTimePeriodTreeDefID;
    
    @OneToMany(mappedBy = "parentItemID")
    private Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection;
    
    @JoinColumn(name = "ParentItemID", referencedColumnName = "GeologicTimePeriodTreeDefItemID")
    @ManyToOne
    private Geologictimeperiodtreedefitem parentItemID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geologicTimePeriodTreeDefItemID")
    private Collection<Geologictimeperiod> geologictimeperiodCollection;

    public Geologictimeperiodtreedefitem() {
    }

    public Geologictimeperiodtreedefitem(Integer geologicTimePeriodTreeDefItemID) {
        this.geologicTimePeriodTreeDefItemID = geologicTimePeriodTreeDefItemID;
    }

    public Geologictimeperiodtreedefitem(Integer geologicTimePeriodTreeDefItemID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.geologicTimePeriodTreeDefItemID = geologicTimePeriodTreeDefItemID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getGeologicTimePeriodTreeDefItemID() {
        return geologicTimePeriodTreeDefItemID;
    }

    public void setGeologicTimePeriodTreeDefItemID(Integer geologicTimePeriodTreeDefItemID) {
        this.geologicTimePeriodTreeDefItemID = geologicTimePeriodTreeDefItemID;
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

    public Geologictimeperiodtreedef getGeologicTimePeriodTreeDefID() {
        return geologicTimePeriodTreeDefID;
    }

    public void setGeologicTimePeriodTreeDefID(Geologictimeperiodtreedef geologicTimePeriodTreeDefID) {
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getGeologictimeperiodtreedefitemCollection() {
        return geologictimeperiodtreedefitemCollection;
    }

    public void setGeologictimeperiodtreedefitemCollection(Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection) {
        this.geologictimeperiodtreedefitemCollection = geologictimeperiodtreedefitemCollection;
    }

    public Geologictimeperiodtreedefitem getParentItemID() {
        return parentItemID;
    }

    public void setParentItemID(Geologictimeperiodtreedefitem parentItemID) {
        this.parentItemID = parentItemID;
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
    public Collection<Geologictimeperiod> getGeologictimeperiodCollection() {
        return geologictimeperiodCollection;
    }

    public void setGeologictimeperiodCollection(Collection<Geologictimeperiod> geologictimeperiodCollection) {
        this.geologictimeperiodCollection = geologictimeperiodCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geologicTimePeriodTreeDefItemID != null ? geologicTimePeriodTreeDefItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geologictimeperiodtreedefitem)) {
            return false;
        }
        Geologictimeperiodtreedefitem other = (Geologictimeperiodtreedefitem) object;
        if ((this.geologicTimePeriodTreeDefItemID == null && other.geologicTimePeriodTreeDefItemID != null) || (this.geologicTimePeriodTreeDefItemID != null && !this.geologicTimePeriodTreeDefItemID.equals(other.geologicTimePeriodTreeDefItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geologictimeperiodtreedefitem[ geologicTimePeriodTreeDefItemID=" + geologicTimePeriodTreeDefItemID + " ]";
    }
    
}

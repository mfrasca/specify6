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
@Table(name = "geographytreedefitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geographytreedefitem.findAll", query = "SELECT g FROM Geographytreedefitem g"),
    @NamedQuery(name = "Geographytreedefitem.findByGeographyTreeDefItemID", query = "SELECT g FROM Geographytreedefitem g WHERE g.geographyTreeDefItemID = :geographyTreeDefItemID"),
    @NamedQuery(name = "Geographytreedefitem.findByTimestampCreated", query = "SELECT g FROM Geographytreedefitem g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geographytreedefitem.findByTimestampModified", query = "SELECT g FROM Geographytreedefitem g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geographytreedefitem.findByVersion", query = "SELECT g FROM Geographytreedefitem g WHERE g.version = :version"),
    @NamedQuery(name = "Geographytreedefitem.findByFullNameSeparator", query = "SELECT g FROM Geographytreedefitem g WHERE g.fullNameSeparator = :fullNameSeparator"),
    @NamedQuery(name = "Geographytreedefitem.findByIsEnforced", query = "SELECT g FROM Geographytreedefitem g WHERE g.isEnforced = :isEnforced"),
    @NamedQuery(name = "Geographytreedefitem.findByIsInFullName", query = "SELECT g FROM Geographytreedefitem g WHERE g.isInFullName = :isInFullName"),
    @NamedQuery(name = "Geographytreedefitem.findByName", query = "SELECT g FROM Geographytreedefitem g WHERE g.name = :name"),
    @NamedQuery(name = "Geographytreedefitem.findByRankID", query = "SELECT g FROM Geographytreedefitem g WHERE g.rankID = :rankID"),
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
    private Integer geographyTreeDefItemID;
    @Size(max = 32)
    @Column(name = "FullNameSeparator")
    private String fullNameSeparator;
    @Column(name = "IsEnforced")
    private Boolean isEnforced;
    @Column(name = "IsInFullName")
    private Boolean isInFullName;
    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    @NotNull
    @Basic(optional = false) 
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
    @JoinColumn(name = "GeographyTreeDefID", referencedColumnName = "GeographyTreeDefID")
    @ManyToOne(optional = false)
    private Geographytreedef geographyTreeDefID;
    @OneToMany(mappedBy = "parentItemID")
    private Collection<Geographytreedefitem> geographytreedefitemCollection;
    @JoinColumn(name = "ParentItemID", referencedColumnName = "GeographyTreeDefItemID")
    @ManyToOne
    private Geographytreedefitem parentItemID;
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geographyTreeDefItemID")
    private Collection<Geography> geographyCollection;

    public Geographytreedefitem() {
    }

    public Geographytreedefitem(Integer geographyTreeDefItemID) {
        this.geographyTreeDefItemID = geographyTreeDefItemID;
    }

    public Geographytreedefitem(Integer geographyTreeDefItemID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.geographyTreeDefItemID = geographyTreeDefItemID;
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getGeographyTreeDefItemID() {
        return geographyTreeDefItemID;
    }

    public void setGeographyTreeDefItemID(Integer geographyTreeDefItemID) {
        this.geographyTreeDefItemID = geographyTreeDefItemID;
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

    public Geographytreedef getGeographyTreeDefID() {
        return geographyTreeDefID;
    }

    public void setGeographyTreeDefID(Geographytreedef geographyTreeDefID) {
        this.geographyTreeDefID = geographyTreeDefID;
    }

    @XmlTransient
    public Collection<Geographytreedefitem> getGeographytreedefitemCollection() {
        return geographytreedefitemCollection;
    }

    public void setGeographytreedefitemCollection(Collection<Geographytreedefitem> geographytreedefitemCollection) {
        this.geographytreedefitemCollection = geographytreedefitemCollection;
    }

    public Geographytreedefitem getParentItemID() {
        return parentItemID;
    }

    public void setParentItemID(Geographytreedefitem parentItemID) {
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
    public Collection<Geography> getGeographyCollection() {
        return geographyCollection;
    }

    public void setGeographyCollection(Collection<Geography> geographyCollection) {
        this.geographyCollection = geographyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geographyTreeDefItemID != null ? geographyTreeDefItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geographytreedefitem)) {
            return false;
        }
        Geographytreedefitem other = (Geographytreedefitem) object;
        if ((this.geographyTreeDefItemID == null && other.geographyTreeDefItemID != null) || (this.geographyTreeDefItemID != null && !this.geographyTreeDefItemID.equals(other.geographyTreeDefItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geographytreedefitem[ geographyTreeDefItemID=" + geographyTreeDefItemID + " ]";
    }
}

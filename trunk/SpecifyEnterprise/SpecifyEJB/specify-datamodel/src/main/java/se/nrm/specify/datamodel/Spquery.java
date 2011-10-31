package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "spquery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spquery.findAll", query = "SELECT s FROM Spquery s"),
    @NamedQuery(name = "Spquery.findBySpQueryID", query = "SELECT s FROM Spquery s WHERE s.spQueryID = :spQueryID"),
    @NamedQuery(name = "Spquery.findByTimestampCreated", query = "SELECT s FROM Spquery s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spquery.findByTimestampModified", query = "SELECT s FROM Spquery s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spquery.findByVersion", query = "SELECT s FROM Spquery s WHERE s.version = :version"),
    @NamedQuery(name = "Spquery.findByContextName", query = "SELECT s FROM Spquery s WHERE s.contextName = :contextName"),
    @NamedQuery(name = "Spquery.findByContextTableId", query = "SELECT s FROM Spquery s WHERE s.contextTableId = :contextTableId"),
    @NamedQuery(name = "Spquery.findByCountOnly", query = "SELECT s FROM Spquery s WHERE s.countOnly = :countOnly"),
    @NamedQuery(name = "Spquery.findByIsFavorite", query = "SELECT s FROM Spquery s WHERE s.isFavorite = :isFavorite"),
    @NamedQuery(name = "Spquery.findByName", query = "SELECT s FROM Spquery s WHERE s.name = :name"),
    @NamedQuery(name = "Spquery.findByOrdinal", query = "SELECT s FROM Spquery s WHERE s.ordinal = :ordinal"),
    @NamedQuery(name = "Spquery.findBySearchSynonymy", query = "SELECT s FROM Spquery s WHERE s.searchSynonymy = :searchSynonymy"),
    @NamedQuery(name = "Spquery.findBySelectDistinct", query = "SELECT s FROM Spquery s WHERE s.selectDistinct = :selectDistinct"),
    @NamedQuery(name = "Spquery.findBySqlStr", query = "SELECT s FROM Spquery s WHERE s.sqlStr = :sqlStr")})
public class Spquery extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "SpQueryID")
    private Integer spQueryID;
     
    @Basic(optional = false)
//    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ContextName")
    private String contextName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ContextTableId")
    private short contextTableId;
    
    @Column(name = "CountOnly")
    private Boolean countOnly;
    
    @Column(name = "IsFavorite")
    private Boolean isFavorite;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "Ordinal")
    private Short ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "SearchSynonymy")
    private Boolean searchSynonymy;
    
    @Column(name = "SelectDistinct")
    private Boolean selectDistinct;
    
    @Size(max = 64)
    @Column(name = "SqlStr")
    private String sqlStr;
    
    @OneToMany(mappedBy = "spQueryID")
    private Collection<Spqueryfield> spqueryfieldCollection;
    
    @OneToMany(mappedBy = "spQueryID")
    private Collection<Spreport> spreportCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne(optional = false)
    private Specifyuser specifyUserID;

    public Spquery() {
    }

    public Spquery(Integer spQueryID) {
        this.spQueryID = spQueryID;
    }

    public Spquery(Integer spQueryID, Date timestampCreated, String contextName, short contextTableId, String name) {
        super(timestampCreated);
        this.spQueryID = spQueryID; 
        this.contextName = contextName;
        this.contextTableId = contextTableId;
        this.name = name;
    }

    public Integer getSpQueryID() {
        return spQueryID;
    }

    public void setSpQueryID(Integer spQueryID) {
        this.spQueryID = spQueryID;
    } 

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public short getContextTableId() {
        return contextTableId;
    }

    public void setContextTableId(short contextTableId) {
        this.contextTableId = contextTableId;
    }

    public Boolean getCountOnly() {
        return countOnly;
    }

    public void setCountOnly(Boolean countOnly) {
        this.countOnly = countOnly;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Short ordinal) {
        this.ordinal = ordinal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getSearchSynonymy() {
        return searchSynonymy;
    }

    public void setSearchSynonymy(Boolean searchSynonymy) {
        this.searchSynonymy = searchSynonymy;
    }

    public Boolean getSelectDistinct() {
        return selectDistinct;
    }

    public void setSelectDistinct(Boolean selectDistinct) {
        this.selectDistinct = selectDistinct;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    @XmlTransient
    public Collection<Spqueryfield> getSpqueryfieldCollection() {
        return spqueryfieldCollection;
    }

    public void setSpqueryfieldCollection(Collection<Spqueryfield> spqueryfieldCollection) {
        this.spqueryfieldCollection = spqueryfieldCollection;
    }

    @XmlTransient
    public Collection<Spreport> getSpreportCollection() {
        return spreportCollection;
    }

    public void setSpreportCollection(Collection<Spreport> spreportCollection) {
        this.spreportCollection = spreportCollection;
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

    public Specifyuser getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(Specifyuser specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spQueryID != null ? spQueryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spquery)) {
            return false;
        }
        Spquery other = (Spquery) object;
        if ((this.spQueryID == null && other.spQueryID != null) || (this.spQueryID != null && !this.spQueryID.equals(other.spQueryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spquery[ spQueryID=" + spQueryID + " ]";
    }
    
}

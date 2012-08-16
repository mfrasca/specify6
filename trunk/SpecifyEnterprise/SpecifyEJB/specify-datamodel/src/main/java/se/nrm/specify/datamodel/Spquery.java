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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "spquery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spquery.findAll", query = "SELECT s FROM Spquery s"),
    @NamedQuery(name = "Spquery.findBySpQueryID", query = "SELECT s FROM Spquery s WHERE s.spQueryId = :spQueryID"),
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
//    @NotNull
    @Column(name = "SpQueryID")
    private Integer spQueryId;
     
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
    
    @OneToMany(mappedBy = "query")
    private Collection<Spqueryfield> fields;
    
    @OneToMany(mappedBy = "query")
    private Collection<Spreport> reports;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne(optional = false)
    private Specifyuser specifyUser;

    public Spquery() {
    }

    public Spquery(Integer spQueryId) {
        this.spQueryId = spQueryId;
    }

    public Spquery(Integer spQueryId, Date timestampCreated, String contextName, short contextTableId, String name) {
        super(timestampCreated);
        this.spQueryId = spQueryId; 
        this.contextName = contextName;
        this.contextTableId = contextTableId;
        this.name = name;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spQueryId != null) ? spQueryId.toString() : "0";
    }
    
    @NotNull(message="ContextName must be specified.")
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

    @NotNull(message="Name must be specified.")
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

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Spqueryfield> getFields() {
        return fields;
    }

    public void setFields(Collection<Spqueryfield> fields) {
        this.fields = fields;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Spreport> getReports() {
        return reports;
    }

    public void setReports(Collection<Spreport> reports) {
        this.reports = reports;
    }

    public Integer getSpQueryId() {
        return spQueryId;
    }

    public void setSpQueryId(Integer spQueryId) {
        this.spQueryId = spQueryId;
    }

    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spQueryId != null ? spQueryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spquery)) {
            return false;
        }
        Spquery other = (Spquery) object;
        if ((this.spQueryId == null && other.spQueryId != null) || (this.spQueryId != null && !this.spQueryId.equals(other.spQueryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spquery[ spQueryID=" + spQueryId + " ]";
    }
 
}

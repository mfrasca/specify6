package se.nrm.specify.datamodel;
 
import java.math.BigDecimal;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "geography")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geography.findAll", query = "SELECT g FROM Geography g"),
    @NamedQuery(name = "Geography.findByGeographyID", query = "SELECT g FROM Geography g WHERE g.geographyID = :geographyID"),
    @NamedQuery(name = "Geography.findByGeographytreedefitemId", query = "SELECT g FROM Geography g WHERE g.geographyTreeDefItemID = :geographyTreeDefItemID"),
    @NamedQuery(name = "Geography.findByTimestampCreated", query = "SELECT g FROM Geography g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geography.findByTimestampModified", query = "SELECT g FROM Geography g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geography.findByVersion", query = "SELECT g FROM Geography g WHERE g.version = :version"),
    @NamedQuery(name = "Geography.findByAbbrev", query = "SELECT g FROM Geography g WHERE g.abbrev = :abbrev"),
    @NamedQuery(name = "Geography.findByCentroidLat", query = "SELECT g FROM Geography g WHERE g.centroidLat = :centroidLat"),
    @NamedQuery(name = "Geography.findByCentroidLon", query = "SELECT g FROM Geography g WHERE g.centroidLon = :centroidLon"),
    @NamedQuery(name = "Geography.findByCommonName", query = "SELECT g FROM Geography g WHERE g.commonName = :commonName"),
    @NamedQuery(name = "Geography.findByFullName", query = "SELECT g FROM Geography g WHERE g.fullName = :fullName"),
    @NamedQuery(name = "Geography.findByGeographyCode", query = "SELECT g FROM Geography g WHERE g.geographyCode = :geographyCode"),
    @NamedQuery(name = "Geography.findByGuid", query = "SELECT g FROM Geography g WHERE g.guid = :guid"),
    @NamedQuery(name = "Geography.findByHighestChildNodeNumber", query = "SELECT g FROM Geography g WHERE g.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Geography.findByIsAccepted", query = "SELECT g FROM Geography g WHERE g.isAccepted = :isAccepted"),
    @NamedQuery(name = "Geography.findByIsCurrent", query = "SELECT g FROM Geography g WHERE g.isCurrent = :isCurrent"),
    @NamedQuery(name = "Geography.findByName", query = "SELECT g FROM Geography g WHERE g.name = :name"),
    @NamedQuery(name = "Geography.findByNodeNumber", query = "SELECT g FROM Geography g WHERE g.nodeNumber = :nodeNumber"),
    @NamedQuery(name = "Geography.findByNumber1", query = "SELECT g FROM Geography g WHERE g.number1 = :number1"),
    @NamedQuery(name = "Geography.findByNumber2", query = "SELECT g FROM Geography g WHERE g.number2 = :number2"),
    @NamedQuery(name = "Geography.findByRankID", query = "SELECT g FROM Geography g WHERE g.rankID = :rankID"),
    @NamedQuery(name = "Geography.findByText1", query = "SELECT g FROM Geography g WHERE g.text1 = :text1"),
    @NamedQuery(name = "Geography.findByText2", query = "SELECT g FROM Geography g WHERE g.text2 = :text2"),
    @NamedQuery(name = "Geography.findByTimestampVersion", query = "SELECT g FROM Geography g WHERE g.timestampVersion = :timestampVersion")})
public class Geography extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GeographyID")
    private Integer geographyID;
     
    @Size(max = 16)
    @Column(name = "Abbrev")
    private String abbrev;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CentroidLat")
    private BigDecimal centroidLat;
    
    @Column(name = "CentroidLon")
    private BigDecimal centroidLon;
    
    @Size(max = 128)
    @Column(name = "CommonName")
    private String commonName;
    
    @Size(max = 255)
    @Column(name = "FullName")
    private String fullName;
    
    @Size(max = 8)
    @Column(name = "GeographyCode")
    private String geographyCode;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "GML")
    private String gml;
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Column(name = "HighestChildNodeNumber")
    private Integer highestChildNodeNumber;
    
    @Column(name = "IsAccepted")
    private Boolean isAccepted;
    
    @Column(name = "IsCurrent")
    private Boolean isCurrent;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "NodeNumber")
    private Integer nodeNumber;
    
    @Column(name = "Number1")
    private Integer number1;
    
    @Column(name = "Number2")
    private Integer number2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    private String text2;
    
    @Column(name = "TimestampVersion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampVersion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geographyID")
    private Collection<Agentgeography> agentgeographyCollection;
    
    @OneToMany(mappedBy = "acceptedID")
    private Collection<Geography> geographyCollection;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "GeographyID")
    @ManyToOne
    private Geography acceptedID;
    
    @JoinColumn(name = "GeographyTreeDefItemID", referencedColumnName = "GeographyTreeDefItemID")
    @ManyToOne(optional = false)
    private Geographytreedefitem geographyTreeDefItemID;
    
    @JoinColumn(name = "GeographyTreeDefID", referencedColumnName = "GeographyTreeDefID")
    @ManyToOne(optional = false)
    private Geographytreedef geographyTreeDefID;
    
    @OneToMany(mappedBy = "parentID")
    private Collection<Geography> geographyCollection1;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "GeographyID")
    @ManyToOne
    private Geography parentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "geographyID")
    private Collection<Locality> localityCollection;

    public Geography() {
    }

    public Geography(Integer geographyID) {
        this.geographyID = geographyID;
    }

    public Geography(Integer geographyID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.geographyID = geographyID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getGeographyID() {
        return geographyID;
    }

    public void setGeographyID(Integer geographyID) {
        this.geographyID = geographyID;
    } 
    
    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public BigDecimal getCentroidLat() {
        return centroidLat;
    }

    public void setCentroidLat(BigDecimal centroidLat) {
        this.centroidLat = centroidLat;
    }

    public BigDecimal getCentroidLon() {
        return centroidLon;
    }

    public void setCentroidLon(BigDecimal centroidLon) {
        this.centroidLon = centroidLon;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGeographyCode() {
        return geographyCode;
    }

    public void setGeographyCode(String geographyCode) {
        this.geographyCode = geographyCode;
    }

    public String getGml() {
        return gml;
    }

    public void setGml(String gml) {
        this.gml = gml;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getHighestChildNodeNumber() {
        return highestChildNodeNumber;
    }

    public void setHighestChildNodeNumber(Integer highestChildNodeNumber) {
        this.highestChildNodeNumber = highestChildNodeNumber;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
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

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Date getTimestampVersion() {
        return timestampVersion;
    }

    public void setTimestampVersion(Date timestampVersion) {
        this.timestampVersion = timestampVersion;
    }

    @XmlTransient
    public Collection<Agentgeography> getAgentgeographyCollection() {
        return agentgeographyCollection;
    }

    public void setAgentgeographyCollection(Collection<Agentgeography> agentgeographyCollection) {
        this.agentgeographyCollection = agentgeographyCollection;
    }

    @XmlTransient
    public Collection<Geography> getGeographyCollection() {
        return geographyCollection;
    }

    public void setGeographyCollection(Collection<Geography> geographyCollection) {
        this.geographyCollection = geographyCollection;
    }

    public Geography getAcceptedID() {
        return acceptedID;
    }

    public void setAcceptedID(Geography acceptedID) {
        this.acceptedID = acceptedID;
    }

    public Geographytreedefitem getGeographyTreeDefItemID() {
        return geographyTreeDefItemID;
    }

    public void setGeographyTreeDefItemID(Geographytreedefitem geographyTreeDefItemID) {
        this.geographyTreeDefItemID = geographyTreeDefItemID;
    }

    public Geographytreedef getGeographyTreeDefID() {
        return geographyTreeDefID;
    }

    public void setGeographyTreeDefID(Geographytreedef geographyTreeDefID) {
        this.geographyTreeDefID = geographyTreeDefID;
    }

    @XmlTransient
    public Collection<Geography> getGeographyCollection1() {
        return geographyCollection1;
    }

    public void setGeographyCollection1(Collection<Geography> geographyCollection1) {
        this.geographyCollection1 = geographyCollection1;
    }

    public Geography getParentID() {
        return parentID;
    }

    public void setParentID(Geography parentID) {
        this.parentID = parentID;
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
    public Collection<Locality> getLocalityCollection() {
        return localityCollection;
    }

    public void setLocalityCollection(Collection<Locality> localityCollection) {
        this.localityCollection = localityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geographyID != null ? geographyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geography)) {
            return false;
        }
        Geography other = (Geography) object;
        if ((this.geographyID == null && other.geographyID != null) || (this.geographyID != null && !this.geographyID.equals(other.geographyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geography[ geographyID=" + geographyID + " ]";
    }
    
}

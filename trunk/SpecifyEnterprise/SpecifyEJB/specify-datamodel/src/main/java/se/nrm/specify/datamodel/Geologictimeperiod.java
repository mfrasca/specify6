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
@Table(name = "geologictimeperiod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geologictimeperiod.findAll", query = "SELECT g FROM Geologictimeperiod g"),
    @NamedQuery(name = "Geologictimeperiod.findByGeologicTimePeriodID", query = "SELECT g FROM Geologictimeperiod g WHERE g.geologicTimePeriodID = :geologicTimePeriodID"),
    @NamedQuery(name = "Geologictimeperiod.findByTimestampCreated", query = "SELECT g FROM Geologictimeperiod g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geologictimeperiod.findByTimestampModified", query = "SELECT g FROM Geologictimeperiod g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geologictimeperiod.findByVersion", query = "SELECT g FROM Geologictimeperiod g WHERE g.version = :version"),
    @NamedQuery(name = "Geologictimeperiod.findByEndPeriod", query = "SELECT g FROM Geologictimeperiod g WHERE g.endPeriod = :endPeriod"),
    @NamedQuery(name = "Geologictimeperiod.findByEndUncertainty", query = "SELECT g FROM Geologictimeperiod g WHERE g.endUncertainty = :endUncertainty"),
    @NamedQuery(name = "Geologictimeperiod.findByText1", query = "SELECT g FROM Geologictimeperiod g WHERE g.text1 = :text1"),
    @NamedQuery(name = "Geologictimeperiod.findByText2", query = "SELECT g FROM Geologictimeperiod g WHERE g.text2 = :text2"),
    @NamedQuery(name = "Geologictimeperiod.findByFullName", query = "SELECT g FROM Geologictimeperiod g WHERE g.fullName = :fullName"),
    @NamedQuery(name = "Geologictimeperiod.findByGuid", query = "SELECT g FROM Geologictimeperiod g WHERE g.guid = :guid"),
    @NamedQuery(name = "Geologictimeperiod.findByHighestChildNodeNumber", query = "SELECT g FROM Geologictimeperiod g WHERE g.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Geologictimeperiod.findByIsAccepted", query = "SELECT g FROM Geologictimeperiod g WHERE g.isAccepted = :isAccepted"),
    @NamedQuery(name = "Geologictimeperiod.findByIsBioStrat", query = "SELECT g FROM Geologictimeperiod g WHERE g.isBioStrat = :isBioStrat"),
    @NamedQuery(name = "Geologictimeperiod.findByName", query = "SELECT g FROM Geologictimeperiod g WHERE g.name = :name"),
    @NamedQuery(name = "Geologictimeperiod.findByNodeNumber", query = "SELECT g FROM Geologictimeperiod g WHERE g.nodeNumber = :nodeNumber"),
    @NamedQuery(name = "Geologictimeperiod.findByRankID", query = "SELECT g FROM Geologictimeperiod g WHERE g.rankID = :rankID"),
    @NamedQuery(name = "Geologictimeperiod.findByStandard", query = "SELECT g FROM Geologictimeperiod g WHERE g.standard = :standard"),
    @NamedQuery(name = "Geologictimeperiod.findByStartPeriod", query = "SELECT g FROM Geologictimeperiod g WHERE g.startPeriod = :startPeriod"),
    @NamedQuery(name = "Geologictimeperiod.findByStartUncertainty", query = "SELECT g FROM Geologictimeperiod g WHERE g.startUncertainty = :startUncertainty")})
public class Geologictimeperiod extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GeologicTimePeriodID")
    private Integer geologicTimePeriodID; 
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EndPeriod")
    private Float endPeriod;
    
    @Column(name = "EndUncertainty")
    private Float endUncertainty;
    
    @Size(max = 64)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 64)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 255)
    @Column(name = "FullName")
    private String fullName;
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Column(name = "HighestChildNodeNumber")
    private Integer highestChildNodeNumber;
    
    @Column(name = "IsAccepted")
    private Boolean isAccepted;
    
    @Column(name = "IsBioStrat")
    private Boolean isBioStrat;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "NodeNumber")
    private Integer nodeNumber;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "Standard")
    private String standard;
    
    @Column(name = "StartPeriod")
    private Float startPeriod;
    
    @Column(name = "StartUncertainty")
    private Float startUncertainty;
    
    @OneToMany(mappedBy = "parentID")
    private Collection<Geologictimeperiod> geologictimeperiodCollection;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod parentID;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefItemID", referencedColumnName = "GeologicTimePeriodTreeDefItemID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedefitem geologicTimePeriodTreeDefItemID;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedef geologicTimePeriodTreeDefID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "acceptedID")
    private Collection<Geologictimeperiod> geologictimeperiodCollection1;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod acceptedID;
    
    @OneToMany(mappedBy = "bioStratID")
    private Collection<Paleocontext> paleocontextCollection;
    
    @OneToMany(mappedBy = "chronosStratID") 
    private Collection<Paleocontext> paleocontextCollection1;
    
    @OneToMany(mappedBy = "chronosStratEndID")
    private Collection<Paleocontext> paleocontextCollection2;

    public Geologictimeperiod() {
    }

    public Geologictimeperiod(Integer geologicTimePeriodID) {
        this.geologicTimePeriodID = geologicTimePeriodID;
    }

    public Geologictimeperiod(Integer geologicTimePeriodID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.geologicTimePeriodID = geologicTimePeriodID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getGeologicTimePeriodID() {
        return geologicTimePeriodID;
    }

    public void setGeologicTimePeriodID(Integer geologicTimePeriodID) {
        this.geologicTimePeriodID = geologicTimePeriodID;
    } 

    public Float getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Float endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Float getEndUncertainty() {
        return endUncertainty;
    }

    public void setEndUncertainty(Float endUncertainty) {
        this.endUncertainty = endUncertainty;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Boolean getIsBioStrat() {
        return isBioStrat;
    }

    public void setIsBioStrat(Boolean isBioStrat) {
        this.isBioStrat = isBioStrat;
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

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Float getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Float startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Float getStartUncertainty() {
        return startUncertainty;
    }

    public void setStartUncertainty(Float startUncertainty) {
        this.startUncertainty = startUncertainty;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getGeologictimeperiodCollection() {
        return geologictimeperiodCollection;
    }

    public void setGeologictimeperiodCollection(Collection<Geologictimeperiod> geologictimeperiodCollection) {
        this.geologictimeperiodCollection = geologictimeperiodCollection;
    }

    public Geologictimeperiod getParentID() {
        return parentID;
    }

    public void setParentID(Geologictimeperiod parentID) {
        this.parentID = parentID;
    }

    public Geologictimeperiodtreedefitem getGeologicTimePeriodTreeDefItemID() {
        return geologicTimePeriodTreeDefItemID;
    }

    public void setGeologicTimePeriodTreeDefItemID(Geologictimeperiodtreedefitem geologicTimePeriodTreeDefItemID) {
        this.geologicTimePeriodTreeDefItemID = geologicTimePeriodTreeDefItemID;
    }

    public Geologictimeperiodtreedef getGeologicTimePeriodTreeDefID() {
        return geologicTimePeriodTreeDefID;
    }

    public void setGeologicTimePeriodTreeDefID(Geologictimeperiodtreedef geologicTimePeriodTreeDefID) {
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID;
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
    public Collection<Geologictimeperiod> getGeologictimeperiodCollection1() {
        return geologictimeperiodCollection1;
    }

    public void setGeologictimeperiodCollection1(Collection<Geologictimeperiod> geologictimeperiodCollection1) {
        this.geologictimeperiodCollection1 = geologictimeperiodCollection1;
    }

    public Geologictimeperiod getAcceptedID() {
        return acceptedID;
    }

    public void setAcceptedID(Geologictimeperiod acceptedID) {
        this.acceptedID = acceptedID;
    }

    @XmlTransient
    public Collection<Paleocontext> getPaleocontextCollection() {
        return paleocontextCollection;
    }

    public void setPaleocontextCollection(Collection<Paleocontext> paleocontextCollection) {
        this.paleocontextCollection = paleocontextCollection;
    }

    @XmlTransient
    public Collection<Paleocontext> getPaleocontextCollection1() {
        return paleocontextCollection1;
    }

    public void setPaleocontextCollection1(Collection<Paleocontext> paleocontextCollection1) {
        this.paleocontextCollection1 = paleocontextCollection1;
    }

    @XmlTransient
    public Collection<Paleocontext> getPaleocontextCollection2() {
        return paleocontextCollection2;
    }

    public void setPaleocontextCollection2(Collection<Paleocontext> paleocontextCollection2) {
        this.paleocontextCollection2 = paleocontextCollection2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geologicTimePeriodID != null ? geologicTimePeriodID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geologictimeperiod)) {
            return false;
        }
        Geologictimeperiod other = (Geologictimeperiod) object;
        if ((this.geologicTimePeriodID == null && other.geologicTimePeriodID != null) || (this.geologicTimePeriodID != null && !this.geologicTimePeriodID.equals(other.geologicTimePeriodID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geologictimeperiod[ geologicTimePeriodID=" + geologicTimePeriodID + " ]";
    }
    
}

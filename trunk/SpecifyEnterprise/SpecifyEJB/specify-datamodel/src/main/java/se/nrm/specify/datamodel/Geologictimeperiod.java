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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Geologictimeperiod.findByGeologicTimePeriodID", query = "SELECT g FROM Geologictimeperiod g WHERE g.geologicTimePeriodId = :geologicTimePeriodID"),
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
    @NamedQuery(name = "Geologictimeperiod.findByRankID", query = "SELECT g FROM Geologictimeperiod g WHERE g.rankId = :rankID"),
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
    private Integer geologicTimePeriodId; 
    
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
    private int rankId;
    
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
    
    @OneToMany(mappedBy = "parent")
    private Collection<Geologictimeperiod> children;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod parent;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefItemID", referencedColumnName = "GeologicTimePeriodTreeDefItemID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedefitem definitionItem;
    
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", referencedColumnName = "GeologicTimePeriodTreeDefID")
    @ManyToOne(optional = false)
    private Geologictimeperiodtreedef definition;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "acceptedGeologicTimePeriod")
    private Collection<Geologictimeperiod> acceptedChildren;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod acceptedGeologicTimePeriod;
    
    @OneToMany(mappedBy = "bioStrat")
    private Collection<Paleocontext> bioStratsPaleoContext;
    
    @OneToMany(mappedBy = "chronosStrat") 
    private Collection<Paleocontext> chronosStratsPaleoContext;
    
    @OneToMany(mappedBy = "chronosStratEnd")
    private Collection<Paleocontext> chronosStratsEndPaleoContext;

    public Geologictimeperiod() {
    }

    public Geologictimeperiod(Integer geologicTimePeriodId) {
        this.geologicTimePeriodId = geologicTimePeriodId;
    }

    public Geologictimeperiod(Integer geologicTimePeriodId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.geologicTimePeriodId = geologicTimePeriodId; 
        this.name = name;
        this.rankId = rankId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (geologicTimePeriodId != null) ? geologicTimePeriodId.toString() : "0";
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

    public Collection<Geologictimeperiod> getAcceptedChildren() {
        return acceptedChildren;
    }

    public void setAcceptedChildren(Collection<Geologictimeperiod> acceptedChildren) {
        this.acceptedChildren = acceptedChildren;
    }

    public Geologictimeperiod getAcceptedGeologicTimePeriod() {
        return acceptedGeologicTimePeriod;
    }

    public void setAcceptedGeologicTimePeriod(Geologictimeperiod acceptedGeologicTimePeriod) {
        this.acceptedGeologicTimePeriod = acceptedGeologicTimePeriod;
    }

    @XmlTransient
    public Collection<Paleocontext> getBioStratsPaleoContext() {
        return bioStratsPaleoContext;
    }

    public void setBioStratsPaleoContext(Collection<Paleocontext> bioStratsPaleoContext) {
        this.bioStratsPaleoContext = bioStratsPaleoContext;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getChildren() {
        return children;
    }

    public void setChildren(Collection<Geologictimeperiod> children) {
        this.children = children;
    }

    @XmlTransient
    public Collection<Paleocontext> getChronosStratsEndPaleoContext() {
        return chronosStratsEndPaleoContext;
    }

    public void setChronosStratsEndPaleoContext(Collection<Paleocontext> chronosStratsEndPaleoContext) {
        this.chronosStratsEndPaleoContext = chronosStratsEndPaleoContext;
    }

    @XmlTransient
    public Collection<Paleocontext> getChronosStratsPaleoContext() {
        return chronosStratsPaleoContext;
    }

    public void setChronosStratsPaleoContext(Collection<Paleocontext> chronosStratsPaleoContext) {
        this.chronosStratsPaleoContext = chronosStratsPaleoContext;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Definition must be specified.")
    public Geologictimeperiodtreedef getDefinition() {
        return definition;
    }

    public void setDefinition(Geologictimeperiodtreedef definition) {
        this.definition = definition;
    }

    @NotNull(message="DefinitionItem must be specified.")
    public Geologictimeperiodtreedefitem getDefinitionItem() {
        return definitionItem;
    }

    public void setDefinitionItem(Geologictimeperiodtreedefitem definitionItem) {
        this.definitionItem = definitionItem;
    }

    public Integer getGeologicTimePeriodId() {
        return geologicTimePeriodId;
    }

    public void setGeologicTimePeriodId(Integer geologicTimePeriodId) {
        this.geologicTimePeriodId = geologicTimePeriodId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Geologictimeperiod getParent() {
        return parent;
    }

    public void setParent(Geologictimeperiod parent) {
        this.parent = parent;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }
 
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geologicTimePeriodId != null ? geologicTimePeriodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geologictimeperiod)) {
            return false;
        }
        Geologictimeperiod other = (Geologictimeperiod) object;
        if ((this.geologicTimePeriodId == null && other.geologicTimePeriodId != null) || (this.geologicTimePeriodId != null && !this.geologicTimePeriodId.equals(other.geologicTimePeriodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geologictimeperiod[ geologicTimePeriodID=" + geologicTimePeriodId + " ]";
    }
 
}

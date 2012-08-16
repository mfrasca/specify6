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
@Table(name = "lithostrat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lithostrat.findAll", query = "SELECT l FROM Lithostrat l"),
    @NamedQuery(name = "Lithostrat.findByLithoStratID", query = "SELECT l FROM Lithostrat l WHERE l.lithoStratId = :lithoStratID"),
    @NamedQuery(name = "Lithostrat.findByTimestampCreated", query = "SELECT l FROM Lithostrat l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Lithostrat.findByTimestampModified", query = "SELECT l FROM Lithostrat l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Lithostrat.findByVersion", query = "SELECT l FROM Lithostrat l WHERE l.version = :version"),
    @NamedQuery(name = "Lithostrat.findByFullName", query = "SELECT l FROM Lithostrat l WHERE l.fullName = :fullName"),
    @NamedQuery(name = "Lithostrat.findByGuid", query = "SELECT l FROM Lithostrat l WHERE l.guid = :guid"),
    @NamedQuery(name = "Lithostrat.findByHighestChildNodeNumber", query = "SELECT l FROM Lithostrat l WHERE l.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Lithostrat.findByIsAccepted", query = "SELECT l FROM Lithostrat l WHERE l.isAccepted = :isAccepted"),
    @NamedQuery(name = "Lithostrat.findByName", query = "SELECT l FROM Lithostrat l WHERE l.name = :name"),
    @NamedQuery(name = "Lithostrat.findByNodeNumber", query = "SELECT l FROM Lithostrat l WHERE l.nodeNumber = :nodeNumber"),
    @NamedQuery(name = "Lithostrat.findByNumber1", query = "SELECT l FROM Lithostrat l WHERE l.number1 = :number1"),
    @NamedQuery(name = "Lithostrat.findByNumber2", query = "SELECT l FROM Lithostrat l WHERE l.number2 = :number2"),
    @NamedQuery(name = "Lithostrat.findByRankID", query = "SELECT l FROM Lithostrat l WHERE l.rankId = :rankID"),
    @NamedQuery(name = "Lithostrat.findByYesNo1", query = "SELECT l FROM Lithostrat l WHERE l.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Lithostrat.findByYesNo2", query = "SELECT l FROM Lithostrat l WHERE l.yesNo2 = :yesNo2")})
public class Lithostrat extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LithoStratID")
    private Integer lithoStratId;
     
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
    
    @Basic(optional = false) 
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "NodeNumber")
    private Integer nodeNumber;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Double number1;
    
    @Column(name = "Number2")
    private Double number2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2") 
    private String text2;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @JoinColumn(name = "LithoStratTreeDefItemID", referencedColumnName = "LithoStratTreeDefItemID")
    @ManyToOne(optional = false)
    private Lithostrattreedefitem definitionItem;
    
    @OneToMany(mappedBy = "parent") 
    private Collection<Lithostrat> children;
    
    
    @JoinColumn(name = "ParentID", referencedColumnName = "LithoStratID")
    @ManyToOne
    private Lithostrat parent;
    
    @OneToMany(mappedBy = "acceptedLithoStrat")
    private Collection<Lithostrat> acceptedChildren;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "LithoStratID")
    @ManyToOne
    private Lithostrat acceptedLithoStrat;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "LithoStratTreeDefID", referencedColumnName = "LithoStratTreeDefID")
    @ManyToOne(optional = false)
    private Lithostrattreedef definition;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "lithoStrat")
    private Collection<Paleocontext> paleoContexts;

    public Lithostrat() {
    }

    public Lithostrat(Integer lithoStratId) {
        this.lithoStratId = lithoStratId;
    }

    public Lithostrat(Integer lithoStratId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.lithoStratId = lithoStratId; 
        this.name = name;
        this.rankId = rankId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (lithoStratId != null) ? lithoStratId.toString() : "0";
    }
    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
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

    @NotNull(message="Name must be specified.")
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

    public Double getNumber1() {
        return number1;
    }

    public void setNumber1(Double number1) {
        this.number1 = number1;
    }

    public Double getNumber2() {
        return number2;
    }

    public void setNumber2(Double number2) {
        this.number2 = number2;
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

    public Boolean getYesNo1() {
        return yesNo1;
    }

    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    public Boolean getYesNo2() {
        return yesNo2;
    }

    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    @XmlTransient
    public Collection<Lithostrat> getAcceptedChildren() {
        return acceptedChildren;
    }

    public void setAcceptedChildren(Collection<Lithostrat> acceptedChildren) {
        this.acceptedChildren = acceptedChildren;
    }

    public Lithostrat getAcceptedLithoStrat() {
        return acceptedLithoStrat;
    }

    public void setAcceptedLithoStrat(Lithostrat acceptedLithoStrat) {
        this.acceptedLithoStrat = acceptedLithoStrat;
    }

    @XmlTransient
    public Collection<Lithostrat> getChildren() {
        return children;
    }

    public void setChildren(Collection<Lithostrat> children) {
        this.children = children;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Definition must be specified.")
    public Lithostrattreedef getDefinition() {
        return definition;
    }

    public void setDefinition(Lithostrattreedef definition) {
        this.definition = definition;
    }

    @NotNull(message="DefinitionItem must be specified.")
    public Lithostrattreedefitem getDefinitionItem() {
        return definitionItem;
    }

    public void setDefinitionItem(Lithostrattreedefitem definitionItem) {
        this.definitionItem = definitionItem;
    }

    public Integer getLithoStratId() {
        return lithoStratId;
    }

    public void setLithoStratId(Integer lithoStratId) {
        this.lithoStratId = lithoStratId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Lithostrat getParent() {
        return parent;
    }

    public void setParent(Lithostrat parent) {
        this.parent = parent;
    }

    @XmlTransient
    public Collection<Paleocontext> getPaleoContexts() {
        return paleoContexts;
    }

    public void setPaleoContexts(Collection<Paleocontext> paleoContexts) {
        this.paleoContexts = paleoContexts;
    }

   
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lithoStratId != null ? lithoStratId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lithostrat)) {
            return false;
        }
        Lithostrat other = (Lithostrat) object;
        if ((this.lithoStratId == null && other.lithoStratId != null) || (this.lithoStratId != null && !this.lithoStratId.equals(other.lithoStratId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lithostrat[ lithoStratID=" + lithoStratId + " ]";
    }
 
    
}

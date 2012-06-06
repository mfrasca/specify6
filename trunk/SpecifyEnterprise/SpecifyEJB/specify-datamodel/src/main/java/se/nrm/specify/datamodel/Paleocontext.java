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
@Table(name = "paleocontext")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paleocontext.findAll", query = "SELECT p FROM Paleocontext p"),
    @NamedQuery(name = "Paleocontext.findByPaleoContextID", query = "SELECT p FROM Paleocontext p WHERE p.paleoContextId = :paleoContextID"),
    @NamedQuery(name = "Paleocontext.findByTimestampCreated", query = "SELECT p FROM Paleocontext p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Paleocontext.findByTimestampModified", query = "SELECT p FROM Paleocontext p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Paleocontext.findByVersion", query = "SELECT p FROM Paleocontext p WHERE p.version = :version"),
    @NamedQuery(name = "Paleocontext.findByCollectionMemberID", query = "SELECT p FROM Paleocontext p WHERE p.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Paleocontext.findByBottomDistance", query = "SELECT p FROM Paleocontext p WHERE p.bottomDistance = :bottomDistance"),
    @NamedQuery(name = "Paleocontext.findByDirection", query = "SELECT p FROM Paleocontext p WHERE p.direction = :direction"),
    @NamedQuery(name = "Paleocontext.findByDistanceUnits", query = "SELECT p FROM Paleocontext p WHERE p.distanceUnits = :distanceUnits"),
    @NamedQuery(name = "Paleocontext.findByPositionState", query = "SELECT p FROM Paleocontext p WHERE p.positionState = :positionState"),
    @NamedQuery(name = "Paleocontext.findByText1", query = "SELECT p FROM Paleocontext p WHERE p.text1 = :text1"),
    @NamedQuery(name = "Paleocontext.findByText2", query = "SELECT p FROM Paleocontext p WHERE p.text2 = :text2"),
    @NamedQuery(name = "Paleocontext.findByTopDistance", query = "SELECT p FROM Paleocontext p WHERE p.topDistance = :topDistance"),
    @NamedQuery(name = "Paleocontext.findByYesNo1", query = "SELECT p FROM Paleocontext p WHERE p.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Paleocontext.findByYesNo2", query = "SELECT p FROM Paleocontext p WHERE p.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Paleocontext.findByRemarks", query = "SELECT p FROM Paleocontext p WHERE p.remarks = :remarks")})
public class Paleocontext extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "PaleoContextID")
    private Integer paleoContextId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BottomDistance")
    private Float bottomDistance;
    
    @Size(max = 32)
    @Column(name = "Direction")
    private String direction;
    
    @Size(max = 16)
    @Column(name = "DistanceUnits")
    private String distanceUnits;
    
    @Size(max = 32)
    @Column(name = "PositionState")
    private String positionState;
    
    @Size(max = 64)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 64)
    @Column(name = "Text2")
    private String text2;
    
    @Column(name = "TopDistance")
    private Float topDistance;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Size(max = 60)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "paleoContext")
    private Collection<Collectionobject> collectionObjects;
    
    @JoinColumn(name = "LithoStratID", referencedColumnName = "LithoStratID")
    @ManyToOne
    private Lithostrat lithoStrat;
    
    @JoinColumn(name = "BioStratID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod bioStrat;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "ChronosStratID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod chronosStrat;
    
    @JoinColumn(name = "ChronosStratEndID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod chronosStratEnd;

    public Paleocontext() {
    }

    public Paleocontext(Integer paleoContextId) {
        this.paleoContextId = paleoContextId;
    }

    public Paleocontext(Integer paleoContextId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.paleoContextId = paleoContextId; 
        this.collectionMemberId = collectionMemberId;
    }

     

    public Float getBottomDistance() {
        return bottomDistance;
    }

    public void setBottomDistance(Float bottomDistance) {
        this.bottomDistance = bottomDistance;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistanceUnits() {
        return distanceUnits;
    }

    public void setDistanceUnits(String distanceUnits) {
        this.distanceUnits = distanceUnits;
    }

    public String getPositionState() {
        return positionState;
    }

    public void setPositionState(String positionState) {
        this.positionState = positionState;
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

    public Float getTopDistance() {
        return topDistance;
    }

    public void setTopDistance(Float topDistance) {
        this.topDistance = topDistance;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionObjects() {
        return collectionObjects;
    }

    public void setCollectionObjects(Collection<Collectionobject> collectionObjects) {
        this.collectionObjects = collectionObjects;
    }

 

    public Geologictimeperiod getBioStrat() {
        return bioStrat;
    }

    public void setBioStrat(Geologictimeperiod bioStrat) {
        this.bioStrat = bioStrat;
    }

    public Geologictimeperiod getChronosStrat() {
        return chronosStrat;
    }

    public void setChronosStrat(Geologictimeperiod chronosStrat) {
        this.chronosStrat = chronosStrat;
    }

    public Geologictimeperiod getChronosStratEnd() {
        return chronosStratEnd;
    }

    public void setChronosStratEnd(Geologictimeperiod chronosStratEnd) {
        this.chronosStratEnd = chronosStratEnd;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Lithostrat getLithoStrat() {
        return lithoStrat;
    }

    public void setLithoStrat(Lithostrat lithoStrat) {
        this.lithoStrat = lithoStrat;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getPaleoContextId() {
        return paleoContextId;
    }

    public void setPaleoContextId(Integer paleoContextId) {
        this.paleoContextId = paleoContextId;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paleoContextId != null ? paleoContextId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paleocontext)) {
            return false;
        }
        Paleocontext other = (Paleocontext) object;
        if ((this.paleoContextId == null && other.paleoContextId != null) || (this.paleoContextId != null && !this.paleoContextId.equals(other.paleoContextId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paleocontext[ paleoContextID=" + paleoContextId + " ]";
    }
    
}

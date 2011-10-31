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
    @NamedQuery(name = "Paleocontext.findByPaleoContextID", query = "SELECT p FROM Paleocontext p WHERE p.paleoContextID = :paleoContextID"),
    @NamedQuery(name = "Paleocontext.findByTimestampCreated", query = "SELECT p FROM Paleocontext p WHERE p.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Paleocontext.findByTimestampModified", query = "SELECT p FROM Paleocontext p WHERE p.timestampModified = :timestampModified"),
    @NamedQuery(name = "Paleocontext.findByVersion", query = "SELECT p FROM Paleocontext p WHERE p.version = :version"),
    @NamedQuery(name = "Paleocontext.findByCollectionMemberID", query = "SELECT p FROM Paleocontext p WHERE p.collectionMemberID = :collectionMemberID"),
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
    private Integer paleoContextID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
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
    
    @OneToMany(mappedBy = "paleoContextID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @JoinColumn(name = "LithoStratID", referencedColumnName = "LithoStratID")
    @ManyToOne
    private Lithostrat lithoStratID;
    
    @JoinColumn(name = "BioStratID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod bioStratID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "ChronosStratID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod chronosStratID;
    
    @JoinColumn(name = "ChronosStratEndID", referencedColumnName = "GeologicTimePeriodID")
    @ManyToOne
    private Geologictimeperiod chronosStratEndID;

    public Paleocontext() {
    }

    public Paleocontext(Integer paleoContextID) {
        this.paleoContextID = paleoContextID;
    }

    public Paleocontext(Integer paleoContextID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.paleoContextID = paleoContextID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getPaleoContextID() {
        return paleoContextID;
    }

    public void setPaleoContextID(Integer paleoContextID) {
        this.paleoContextID = paleoContextID;
    } 

    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
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
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    public Lithostrat getLithoStratID() {
        return lithoStratID;
    }

    public void setLithoStratID(Lithostrat lithoStratID) {
        this.lithoStratID = lithoStratID;
    }

    public Geologictimeperiod getBioStratID() {
        return bioStratID;
    }

    public void setBioStratID(Geologictimeperiod bioStratID) {
        this.bioStratID = bioStratID;
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

    public Geologictimeperiod getChronosStratID() {
        return chronosStratID;
    }

    public void setChronosStratID(Geologictimeperiod chronosStratID) {
        this.chronosStratID = chronosStratID;
    }

    public Geologictimeperiod getChronosStratEndID() {
        return chronosStratEndID;
    }

    public void setChronosStratEndID(Geologictimeperiod chronosStratEndID) {
        this.chronosStratEndID = chronosStratEndID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paleoContextID != null ? paleoContextID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paleocontext)) {
            return false;
        }
        Paleocontext other = (Paleocontext) object;
        if ((this.paleoContextID == null && other.paleoContextID != null) || (this.paleoContextID != null && !this.paleoContextID.equals(other.paleoContextID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paleocontext[ paleoContextID=" + paleoContextID + " ]";
    }
    
}

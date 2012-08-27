package se.nrm.specify.datamodel;
 
import java.math.BigDecimal;
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
import javax.persistence.Table; 
//import javax.validation.constraints.NotNull; 
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "localitydetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localitydetail.findAll", query = "SELECT l FROM Localitydetail l"),
    @NamedQuery(name = "Localitydetail.findByLocalityDetailId", query = "SELECT l FROM Localitydetail l WHERE l.localityDetailId = :localityDetailId"),
    @NamedQuery(name = "Localitydetail.findByTimestampCreated", query = "SELECT l FROM Localitydetail l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Localitydetail.findByTimestampModified", query = "SELECT l FROM Localitydetail l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Localitydetail.findByVersion", query = "SELECT l FROM Localitydetail l WHERE l.version = :version"),
    @NamedQuery(name = "Localitydetail.findByBaseMeridian", query = "SELECT l FROM Localitydetail l WHERE l.baseMeridian = :baseMeridian"),
    @NamedQuery(name = "Localitydetail.findByDrainage", query = "SELECT l FROM Localitydetail l WHERE l.drainage = :drainage"),
    @NamedQuery(name = "Localitydetail.findByStartDepth", query = "SELECT l FROM Localitydetail l WHERE l.startDepth = :startDepth"),
    @NamedQuery(name = "Localitydetail.findByStartDepthUnit", query = "SELECT l FROM Localitydetail l WHERE l.startDepthUnit = :startDepthUnit"),
    @NamedQuery(name = "Localitydetail.findByStartDepthVerbatim", query = "SELECT l FROM Localitydetail l WHERE l.startDepthVerbatim = :startDepthVerbatim"),
    @NamedQuery(name = "Localitydetail.findByEndDepth", query = "SELECT l FROM Localitydetail l WHERE l.endDepth = :endDepth"),
    @NamedQuery(name = "Localitydetail.findByEndDepthUnit", query = "SELECT l FROM Localitydetail l WHERE l.endDepthUnit = :endDepthUnit"),
    @NamedQuery(name = "Localitydetail.findByEndDepthVerbatim", query = "SELECT l FROM Localitydetail l WHERE l.endDepthVerbatim = :endDepthVerbatim"),
    @NamedQuery(name = "Localitydetail.findByHucCode", query = "SELECT l FROM Localitydetail l WHERE l.hucCode = :hucCode"),
    @NamedQuery(name = "Localitydetail.findByIsland", query = "SELECT l FROM Localitydetail l WHERE l.island = :island"),
    @NamedQuery(name = "Localitydetail.findByIslandGroup", query = "SELECT l FROM Localitydetail l WHERE l.islandGroup = :islandGroup"),
    @NamedQuery(name = "Localitydetail.findByNationalParkName", query = "SELECT l FROM Localitydetail l WHERE l.nationalParkName = :nationalParkName"),
    @NamedQuery(name = "Localitydetail.findByNumber1", query = "SELECT l FROM Localitydetail l WHERE l.number1 = :number1"),
    @NamedQuery(name = "Localitydetail.findByNumber2", query = "SELECT l FROM Localitydetail l WHERE l.number2 = :number2"),
    @NamedQuery(name = "Localitydetail.findByRangeDesc", query = "SELECT l FROM Localitydetail l WHERE l.rangeDesc = :rangeDesc"),
    @NamedQuery(name = "Localitydetail.findByRangeDirection", query = "SELECT l FROM Localitydetail l WHERE l.rangeDirection = :rangeDirection"),
    @NamedQuery(name = "Localitydetail.findBySection", query = "SELECT l FROM Localitydetail l WHERE l.section = :section"),
    @NamedQuery(name = "Localitydetail.findBySectionPart", query = "SELECT l FROM Localitydetail l WHERE l.sectionPart = :sectionPart"),
    @NamedQuery(name = "Localitydetail.findByTownship", query = "SELECT l FROM Localitydetail l WHERE l.township = :township"),
    @NamedQuery(name = "Localitydetail.findByTownshipDirection", query = "SELECT l FROM Localitydetail l WHERE l.townshipDirection = :townshipDirection"),
    @NamedQuery(name = "Localitydetail.findByUtmDatum", query = "SELECT l FROM Localitydetail l WHERE l.utmDatum = :utmDatum"),
    @NamedQuery(name = "Localitydetail.findByUtmEasting", query = "SELECT l FROM Localitydetail l WHERE l.utmEasting = :utmEasting"),
    @NamedQuery(name = "Localitydetail.findByUtmFalseEasting", query = "SELECT l FROM Localitydetail l WHERE l.utmFalseEasting = :utmFalseEasting"),
    @NamedQuery(name = "Localitydetail.findByUtmFalseNorthing", query = "SELECT l FROM Localitydetail l WHERE l.utmFalseNorthing = :utmFalseNorthing"),
    @NamedQuery(name = "Localitydetail.findByUtmNorthing", query = "SELECT l FROM Localitydetail l WHERE l.utmNorthing = :utmNorthing"),
    @NamedQuery(name = "Localitydetail.findByUtmOrigLatitude", query = "SELECT l FROM Localitydetail l WHERE l.utmOrigLatitude = :utmOrigLatitude"),
    @NamedQuery(name = "Localitydetail.findByUtmOrigLongitude", query = "SELECT l FROM Localitydetail l WHERE l.utmOrigLongitude = :utmOrigLongitude"),
    @NamedQuery(name = "Localitydetail.findByUtmScale", query = "SELECT l FROM Localitydetail l WHERE l.utmScale = :utmScale"),
    @NamedQuery(name = "Localitydetail.findByUtmZone", query = "SELECT l FROM Localitydetail l WHERE l.utmZone = :utmZone"),
    @NamedQuery(name = "Localitydetail.findByWaterBody", query = "SELECT l FROM Localitydetail l WHERE l.waterBody = :waterBody"),
    @NamedQuery(name = "Localitydetail.findByYesNo1", query = "SELECT l FROM Localitydetail l WHERE l.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Localitydetail.findByYesNo2", query = "SELECT l FROM Localitydetail l WHERE l.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Localitydetail.findByMgrsZone", query = "SELECT l FROM Localitydetail l WHERE l.mgrsZone = :mgrsZone")})
public class Localitydetail extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LocalityDetailID")
    private Integer localityDetailId;
     
    @Size(max = 50)
    @Column(name = "BaseMeridian")
    private String baseMeridian;
    
    @Size(max = 64) 
    @Column(name = "Drainage")
    private String drainage;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "StartDepth")
    private Double startDepth;
    
    @Column(name = "StartDepthUnit")
    private Short startDepthUnit;
    
    @Size(max = 32)
    @Column(name = "StartDepthVerbatim")
    private String startDepthVerbatim;
    
    @Column(name = "EndDepth")
    private Double endDepth;
    
    @Column(name = "EndDepthUnit")
    private Short endDepthUnit;
    
    @Size(max = 32)
    @Column(name = "EndDepthVerbatim")
    private String endDepthVerbatim;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "GML")
    private String gml;
    
    @Size(max = 16)
    @Column(name = "HucCode")
    private String hucCode;
    
    
    @Size(max = 64)
    @Column(name = "Island")
    private String island;
    @Size(max = 64)
    @Column(name = "IslandGroup")
    private String islandGroup;
    
    @Size(max = 64)
    @Column(name = "NationalParkName")
    private String nationalParkName;
    
    @Column(name = "Number1")
    private Double number1;
    
    @Column(name = "Number2")
    private Double number2;
    
    @Size(max = 50)
    @Column(name = "RangeDesc")
    private String rangeDesc;
    
    @Size(max = 50)
    @Column(name = "RangeDirection")
    private String rangeDirection;
    
    @Size(max = 50)
    @Column(name = "Section")
    private String section;
    
    @Size(max = 50)
    @Column(name = "SectionPart")
    private String sectionPart;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 50)
    @Column(name = "Township")
    private String township;
    
    @Size(max = 50)
    @Column(name = "TownshipDirection")
    private String townshipDirection;
    
    @Size(max = 255)
    @Column(name = "UtmDatum")
    private String utmDatum;
    
    @Column(name = "UtmEasting")
    private BigDecimal utmEasting;
    
    @Column(name = "UtmFalseEasting")
    private Integer utmFalseEasting;
    
    @Column(name = "UtmFalseNorthing")
    private Integer utmFalseNorthing;
    
    @Column(name = "UtmNorthing")
    private BigDecimal utmNorthing;
    
    @Column(name = "UtmOrigLatitude")
    private BigDecimal utmOrigLatitude;
    
    @Column(name = "UtmOrigLongitude")
    private BigDecimal utmOrigLongitude;
    
    @Column(name = "UtmScale")
    private BigDecimal utmScale;
    
    @Column(name = "UtmZone")
    private Short utmZone;
    
    @Size(max = 64)
    @Column(name = "WaterBody")
    private String waterBody;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Size(max = 4)
    @Column(name = "MgrsZone")
    private String mgrsZone;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @ManyToOne
    private Locality locality;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Localitydetail() {
    }

    public Localitydetail(Integer localityDetailId) {
        this.localityDetailId = localityDetailId;
    }

    public Localitydetail(Integer localityDetailId, Date timestampCreated) {
        super(timestampCreated);
        this.localityDetailId = localityDetailId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (localityDetailId != null) ? localityDetailId.toString() : "0";
    }
 
    public String getBaseMeridian() {
        return baseMeridian;
    }

    public void setBaseMeridian(String baseMeridian) {
        this.baseMeridian = baseMeridian;
    }

    public String getDrainage() {
        return drainage;
    }

    public void setDrainage(String drainage) {
        this.drainage = drainage;
    }

    public Double getStartDepth() {
        return startDepth;
    }

    public void setStartDepth(Double startDepth) {
        this.startDepth = startDepth;
    }

    public Short getStartDepthUnit() {
        return startDepthUnit;
    }

    public void setStartDepthUnit(Short startDepthUnit) {
        this.startDepthUnit = startDepthUnit;
    }

    public String getStartDepthVerbatim() {
        return startDepthVerbatim;
    }

    public void setStartDepthVerbatim(String startDepthVerbatim) {
        this.startDepthVerbatim = startDepthVerbatim;
    }

    public Double getEndDepth() {
        return endDepth;
    }

    public void setEndDepth(Double endDepth) {
        this.endDepth = endDepth;
    }

    public Short getEndDepthUnit() {
        return endDepthUnit;
    }

    public void setEndDepthUnit(Short endDepthUnit) {
        this.endDepthUnit = endDepthUnit;
    }

    public String getEndDepthVerbatim() {
        return endDepthVerbatim;
    }

    public void setEndDepthVerbatim(String endDepthVerbatim) {
        this.endDepthVerbatim = endDepthVerbatim;
    }

    public String getGml() {
        return gml;
    }

    public void setGml(String gml) {
        this.gml = gml;
    }

    public String getHucCode() {
        return hucCode;
    }

    public void setHucCode(String hucCode) {
        this.hucCode = hucCode;
    }

    public String getIsland() {
        return island;
    }

    public void setIsland(String island) {
        this.island = island;
    }

    public String getIslandGroup() {
        return islandGroup;
    }

    public void setIslandGroup(String islandGroup) {
        this.islandGroup = islandGroup;
    }

    public String getNationalParkName() {
        return nationalParkName;
    }

    public void setNationalParkName(String nationalParkName) {
        this.nationalParkName = nationalParkName;
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

    public String getRangeDesc() {
        return rangeDesc;
    }

    public void setRangeDesc(String rangeDesc) {
        this.rangeDesc = rangeDesc;
    }

    public String getRangeDirection() {
        return rangeDirection;
    }

    public void setRangeDirection(String rangeDirection) {
        this.rangeDirection = rangeDirection;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionPart() {
        return sectionPart;
    }

    public void setSectionPart(String sectionPart) {
        this.sectionPart = sectionPart;
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

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getTownshipDirection() {
        return townshipDirection;
    }

    public void setTownshipDirection(String townshipDirection) {
        this.townshipDirection = townshipDirection;
    }

    public String getUtmDatum() {
        return utmDatum;
    }

    public void setUtmDatum(String utmDatum) {
        this.utmDatum = utmDatum;
    }

    public BigDecimal getUtmEasting() {
        return utmEasting;
    }

    public void setUtmEasting(BigDecimal utmEasting) {
        this.utmEasting = utmEasting;
    }

    public Integer getUtmFalseEasting() {
        return utmFalseEasting;
    }

    public void setUtmFalseEasting(Integer utmFalseEasting) {
        this.utmFalseEasting = utmFalseEasting;
    }

    public Integer getUtmFalseNorthing() {
        return utmFalseNorthing;
    }

    public void setUtmFalseNorthing(Integer utmFalseNorthing) {
        this.utmFalseNorthing = utmFalseNorthing;
    }

    public BigDecimal getUtmNorthing() {
        return utmNorthing;
    }

    public void setUtmNorthing(BigDecimal utmNorthing) {
        this.utmNorthing = utmNorthing;
    }

    public BigDecimal getUtmOrigLatitude() {
        return utmOrigLatitude;
    }

    public void setUtmOrigLatitude(BigDecimal utmOrigLatitude) {
        this.utmOrigLatitude = utmOrigLatitude;
    }

    public BigDecimal getUtmOrigLongitude() {
        return utmOrigLongitude;
    }

    public void setUtmOrigLongitude(BigDecimal utmOrigLongitude) {
        this.utmOrigLongitude = utmOrigLongitude;
    }

    public BigDecimal getUtmScale() {
        return utmScale;
    }

    public void setUtmScale(BigDecimal utmScale) {
        this.utmScale = utmScale;
    }

    public Short getUtmZone() {
        return utmZone;
    }

    public void setUtmZone(Short utmZone) {
        this.utmZone = utmZone;
    }

    public String getWaterBody() {
        return waterBody;
    }

    public void setWaterBody(String waterBody) {
        this.waterBody = waterBody;
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

    public String getMgrsZone() {
        return mgrsZone;
    }

    public void setMgrsZone(String mgrsZone) {
        this.mgrsZone = mgrsZone;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Integer getLocalityDetailId() {
        return localityDetailId;
    }

    public void setLocalityDetailId(Integer localityDetailId) {
        this.localityDetailId = localityDetailId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @Override
    public String getEntityName() {
        return "localityDetail";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (localityDetailId != null ? localityDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localitydetail)) {
            return false;
        }
        Localitydetail other = (Localitydetail) object;
        if ((this.localityDetailId == null && other.localityDetailId != null) || (this.localityDetailId != null && !this.localityDetailId.equals(other.localityDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localitydetail[ localityDetailID=" + localityDetailId + " ]";
    }
 
}

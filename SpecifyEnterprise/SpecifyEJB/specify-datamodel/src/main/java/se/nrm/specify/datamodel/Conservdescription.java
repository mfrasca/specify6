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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "conservdescription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conservdescription.findAll", query = "SELECT c FROM Conservdescription c"),
    @NamedQuery(name = "Conservdescription.findByConservDescriptionID", query = "SELECT c FROM Conservdescription c WHERE c.conservDescriptionId = :conservDescriptionID"),
    @NamedQuery(name = "Conservdescription.findByTimestampCreated", query = "SELECT c FROM Conservdescription c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Conservdescription.findByTimestampModified", query = "SELECT c FROM Conservdescription c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Conservdescription.findByVersion", query = "SELECT c FROM Conservdescription c WHERE c.version = :version"),
    @NamedQuery(name = "Conservdescription.findByHeight", query = "SELECT c FROM Conservdescription c WHERE c.height = :height"),
    @NamedQuery(name = "Conservdescription.findByObjLength", query = "SELECT c FROM Conservdescription c WHERE c.objLength = :objLength"),
    @NamedQuery(name = "Conservdescription.findByShortDesc", query = "SELECT c FROM Conservdescription c WHERE c.shortDesc = :shortDesc"),
    @NamedQuery(name = "Conservdescription.findByUnits", query = "SELECT c FROM Conservdescription c WHERE c.units = :units"),
    @NamedQuery(name = "Conservdescription.findByWidth", query = "SELECT c FROM Conservdescription c WHERE c.width = :width")})
public class Conservdescription extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ConservDescriptionID")
    private Integer conservDescriptionId;
      
    @Lob
    @Size(max = 65535)
    @Column(name = "BackgroundInfo")
    private String backgroundInfo;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Composition")
    private String composition;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "DisplayRecommendations")
    private String displayRecommendations;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Height")
    private Float height;
    @Lob
    @Size(max = 65535)
    @Column(name = "LightRecommendations")
    private String lightRecommendations;
    
    @Column(name = "ObjLength")
    private Float objLength;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "OtherRecommendations")
    private String otherRecommendations;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 128)
    @Column(name = "ShortDesc")
    private String shortDesc;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Source")
    private String source;
    
    @Size(max = 16)
    @Column(name = "Units")
    private String units;
    
    @Column(name = "Width")
    private Float width;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conservDescription")
    private Collection<Conservevent> events;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Division division;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")
    @ManyToOne
    private Collectionobject collectionObject;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conservDescription")
    private Collection<Conservdescriptionattachment> conservDescriptionAttachments;

    public Conservdescription() {
    }

    public Conservdescription(Integer conservDescriptionId) {
        this.conservDescriptionId = conservDescriptionId;
    }

    public Conservdescription(Integer conservDescriptionId, Date timestampCreated) {
        super(timestampCreated);
        this.conservDescriptionId = conservDescriptionId; 
    }
 
 
    public String getBackgroundInfo() {
        return backgroundInfo;
    }

    public void setBackgroundInfo(String backgroundInfo) {
        this.backgroundInfo = backgroundInfo;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayRecommendations() {
        return displayRecommendations;
    }

    public void setDisplayRecommendations(String displayRecommendations) {
        this.displayRecommendations = displayRecommendations;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getLightRecommendations() {
        return lightRecommendations;
    }

    public void setLightRecommendations(String lightRecommendations) {
        this.lightRecommendations = lightRecommendations;
    }

    public Float getObjLength() {
        return objLength;
    }

    public void setObjLength(Float objLength) {
        this.objLength = objLength;
    }

    public String getOtherRecommendations() {
        return otherRecommendations;
    }

    public void setOtherRecommendations(String otherRecommendations) {
        this.otherRecommendations = otherRecommendations;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

 

    public Collectionobject getCollectionObject() {
        return collectionObject;
    }

    public void setCollectionObject(Collectionobject collectionObject) {
        this.collectionObject = collectionObject;
    }

    public Integer getConservDescriptionId() {
        return conservDescriptionId;
    }

    public void setConservDescriptionId(Integer conservDescriptionId) {
        this.conservDescriptionId = conservDescriptionId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservDescriptionAttachments() {
        return conservDescriptionAttachments;
    }

    public void setConservDescriptionAttachments(Collection<Conservdescriptionattachment> conservDescriptionAttachments) {
        this.conservDescriptionAttachments = conservDescriptionAttachments;
    }

    @XmlTransient
    public Collection<Conservevent> getEvents() {
        return events;
    }

    public void setEvents(Collection<Conservevent> events) {
        this.events = events;
    }

   

    

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conservDescriptionId != null ? conservDescriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conservdescription)) {
            return false;
        }
        Conservdescription other = (Conservdescription) object;
        if ((this.conservDescriptionId == null && other.conservDescriptionId != null) || (this.conservDescriptionId != null && !this.conservDescriptionId.equals(other.conservDescriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Conservdescription[ conservDescriptionId=" + conservDescriptionId + " ]";
    }
    
}

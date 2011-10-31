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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
 * 
 * Entity bean mapping to collectionobject table
 */
@Entity
@Table(name = "collectionobject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Collectionobject.findAll", query = "SELECT c FROM Collectionobject c"),
    @NamedQuery(name = "Collectionobject.findByCollectionObjectID", query = "SELECT c FROM Collectionobject c WHERE c.collectionObjectID = :collectionObjectID"),
    @NamedQuery(name = "Collectionobject.findByTimestampCreated", query = "SELECT c FROM Collectionobject c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobject.findByTimestampModified", query = "SELECT c FROM Collectionobject c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobject.findByVersion", query = "SELECT c FROM Collectionobject c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobject.findByCollectionMemberID", query = "SELECT c FROM Collectionobject c WHERE c.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Collectionobject.findByAltCatalogNumber", query = "SELECT c FROM Collectionobject c WHERE c.altCatalogNumber = :altCatalogNumber"),
    @NamedQuery(name = "Collectionobject.findByAvailability", query = "SELECT c FROM Collectionobject c WHERE c.availability = :availability"),
    @NamedQuery(name = "Collectionobject.findByCatalogNumber", query = "SELECT c FROM Collectionobject c WHERE c.catalogNumber = :catalogNumber"),
    @NamedQuery(name = "Collectionobject.findByCatalogedDate", query = "SELECT c FROM Collectionobject c WHERE c.catalogedDate = :catalogedDate"),
    @NamedQuery(name = "Collectionobject.findByCatalogedDatePrecision", query = "SELECT c FROM Collectionobject c WHERE c.catalogedDatePrecision = :catalogedDatePrecision"),
    @NamedQuery(name = "Collectionobject.findByCatalogedDateVerbatim", query = "SELECT c FROM Collectionobject c WHERE c.catalogedDateVerbatim = :catalogedDateVerbatim"),
    @NamedQuery(name = "Collectionobject.findByCountAmt", query = "SELECT c FROM Collectionobject c WHERE c.countAmt = :countAmt"),
    @NamedQuery(name = "Collectionobject.findByDeaccessioned", query = "SELECT c FROM Collectionobject c WHERE c.deaccessioned = :deaccessioned"),
    @NamedQuery(name = "Collectionobject.findByDescription", query = "SELECT c FROM Collectionobject c WHERE c.description = :description"),
    @NamedQuery(name = "Collectionobject.findByFieldNumber", query = "SELECT c FROM Collectionobject c WHERE c.fieldNumber = :fieldNumber"),
    @NamedQuery(name = "Collectionobject.findByGuid", query = "SELECT c FROM Collectionobject c WHERE c.guid = :guid"),
    @NamedQuery(name = "Collectionobject.findByInventoryDate", query = "SELECT c FROM Collectionobject c WHERE c.inventoryDate = :inventoryDate"),
    @NamedQuery(name = "Collectionobject.findByModifier", query = "SELECT c FROM Collectionobject c WHERE c.modifier = :modifier"),
    @NamedQuery(name = "Collectionobject.findByName", query = "SELECT c FROM Collectionobject c WHERE c.name = :name"),
    @NamedQuery(name = "Collectionobject.findByNotifications", query = "SELECT c FROM Collectionobject c WHERE c.notifications = :notifications"),
    @NamedQuery(name = "Collectionobject.findByNumber1", query = "SELECT c FROM Collectionobject c WHERE c.number1 = :number1"),
    @NamedQuery(name = "Collectionobject.findByNumber2", query = "SELECT c FROM Collectionobject c WHERE c.number2 = :number2"),
    @NamedQuery(name = "Collectionobject.findByObjectCondition", query = "SELECT c FROM Collectionobject c WHERE c.objectCondition = :objectCondition"),
    @NamedQuery(name = "Collectionobject.findByProjectNumber", query = "SELECT c FROM Collectionobject c WHERE c.projectNumber = :projectNumber"),
    @NamedQuery(name = "Collectionobject.findByRestrictions", query = "SELECT c FROM Collectionobject c WHERE c.restrictions = :restrictions"),
    @NamedQuery(name = "Collectionobject.findByTotalValue", query = "SELECT c FROM Collectionobject c WHERE c.totalValue = :totalValue"),
    @NamedQuery(name = "Collectionobject.findByVisibility", query = "SELECT c FROM Collectionobject c WHERE c.visibility = :visibility"),
    @NamedQuery(name = "Collectionobject.findByYesNo1", query = "SELECT c FROM Collectionobject c WHERE c.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Collectionobject.findByYesNo2", query = "SELECT c FROM Collectionobject c WHERE c.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Collectionobject.findByYesNo3", query = "SELECT c FROM Collectionobject c WHERE c.yesNo3 = :yesNo3"),
    @NamedQuery(name = "Collectionobject.findByYesNo4", query = "SELECT c FROM Collectionobject c WHERE c.yesNo4 = :yesNo4"),
    @NamedQuery(name = "Collectionobject.findByYesNo5", query = "SELECT c FROM Collectionobject c WHERE c.yesNo5 = :yesNo5"),
    @NamedQuery(name = "Collectionobject.findByYesNo6", query = "SELECT c FROM Collectionobject c WHERE c.yesNo6 = :yesNo6")})
public class Collectionobject extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionObjectID")
    private Integer collectionObjectID;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Size(max = 32)
    @Column(name = "AltCatalogNumber")
    private String altCatalogNumber;
    
    @Size(max = 32)
    @Column(name = "Availability")
    private String availability;
    
    @Size(max = 32)
    @Column(name = "CatalogNumber")
    private String catalogNumber;
    
    @Column(name = "CatalogedDate")
    @Temporal(TemporalType.DATE)
    private Date catalogedDate;
    
    @Column(name = "CatalogedDatePrecision")
    private Short catalogedDatePrecision;
    
    @Size(max = 32)
    @Column(name = "CatalogedDateVerbatim")
    private String catalogedDateVerbatim;
    
    @Column(name = "CountAmt")
    private Integer countAmt;
    
    @Column(name = "Deaccessioned")
    private Boolean deaccessioned;
    
    @Size(max = 255)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 50)
    @Column(name = "FieldNumber")
    private String fieldNumber;
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Column(name = "InventoryDate")
    @Temporal(TemporalType.DATE)
    private Date inventoryDate;
    
    @Size(max = 50)
    @Column(name = "Modifier")
    private String modifier;
    
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 32)
    @Column(name = "Notifications")
    private String notifications;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Size(max = 64)
    @Column(name = "ObjectCondition")
    private String objectCondition;
    
    @Size(max = 64)
    @Column(name = "ProjectNumber")
    private String projectNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Restrictions")
    private String restrictions;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Column(name = "TotalValue")
    private BigDecimal totalValue;
    
    @Column(name = "Visibility")
    private Short visibility;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @Column(name = "YesNo3")
    private Boolean yesNo3;
    
    @Column(name = "YesNo4")
    private Boolean yesNo4;
    
    @Column(name = "YesNo5")
    private Boolean yesNo5;
    
    @Column(name = "YesNo6")
    private Boolean yesNo6;
    
    @JoinTable(name = "project_colobj", joinColumns = {
        @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")}, inverseJoinColumns = {
        @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")})
    @ManyToMany
    private Collection<Project> projectCollection;
    
    @OneToMany(mappedBy = "collectionObjectID")
    private Collection<Treatmentevent> treatmenteventCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Preparation> preparationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Otheridentifier> otheridentifierCollection;
    
    @OneToMany(mappedBy = "collectionObjectID")
    private Collection<Conservdescription> conservdescriptionCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rightSideCollectionID")
    private Collection<Collectionrelationship> collectionrelationshipCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leftSideCollectionID")
    private Collection<Collectionrelationship> collectionrelationshipCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Collectionobjectattr> collectionobjectattrCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Determination> determinationCollection;
    
    @OneToMany(mappedBy = "collectionObjectID")
    private Collection<Dnasequence> dnasequenceCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Collectionobjectattachment> collectionobjectattachmentCollection;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne
    private Accession accessionID;
    
    @JoinColumn(name = "CatalogerID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent catalogerID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "FieldNotebookPageID", referencedColumnName = "FieldNotebookPageID")
    @ManyToOne
    private Fieldnotebookpage fieldNotebookPageID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetByID;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private se.nrm.specify.datamodel.Collection collectionID;
    
    @JoinColumn(name = "PaleoContextID", referencedColumnName = "PaleoContextID")
    @ManyToOne
    private Paleocontext paleoContextID;
    
    @JoinColumn(name = "CollectionObjectAttributeID", referencedColumnName = "CollectionObjectAttributeID")
    @ManyToOne
    private Collectionobjectattribute collectionObjectAttributeID;
    
    @JoinColumn(name = "ContainerOwnerID", referencedColumnName = "ContainerID")
    @ManyToOne
    private Container containerOwnerID;
    
    @JoinColumn(name = "AppraisalID", referencedColumnName = "AppraisalID")
    @ManyToOne
    private Appraisal appraisalID;
    
    @JoinColumn(name = "CollectingEventID", referencedColumnName = "CollectingEventID")
    @ManyToOne
    private Collectingevent collectingEventID;
    
    @JoinColumn(name = "ContainerID", referencedColumnName = "ContainerID")
    @ManyToOne
    private Container containerID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Collectionobjectcitation> collectionobjectcitationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObjectID")
    private Collection<Exsiccataitem> exsiccataitemCollection;

    public Collectionobject() {
    }

    public Collectionobject(Integer collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
    }

    public Collectionobject(Integer collectionObjectID, Date timestampCreated, int collectionMemberID) {
        super(timestampCreated);
        this.collectionObjectID = collectionObjectID; 
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getCollectionObjectID() {
        return collectionObjectID;
    }

    public void setCollectionObjectID(Integer collectionObjectID) {
        this.collectionObjectID = collectionObjectID;
    }
 
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public String getAltCatalogNumber() {
        return altCatalogNumber;
    }

    public void setAltCatalogNumber(String altCatalogNumber) {
        this.altCatalogNumber = altCatalogNumber;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public Date getCatalogedDate() {
        return catalogedDate;
    }

    public void setCatalogedDate(Date catalogedDate) {
        this.catalogedDate = catalogedDate;
    }

    public Short getCatalogedDatePrecision() {
        return catalogedDatePrecision;
    }

    public void setCatalogedDatePrecision(Short catalogedDatePrecision) {
        this.catalogedDatePrecision = catalogedDatePrecision;
    }

    public String getCatalogedDateVerbatim() {
        return catalogedDateVerbatim;
    }

    public void setCatalogedDateVerbatim(String catalogedDateVerbatim) {
        this.catalogedDateVerbatim = catalogedDateVerbatim;
    }

    public Integer getCountAmt() {
        return countAmt;
    }

    public void setCountAmt(Integer countAmt) {
        this.countAmt = countAmt;
    }

    public Boolean getDeaccessioned() {
        return deaccessioned;
    }

    public void setDeaccessioned(Boolean deaccessioned) {
        this.deaccessioned = deaccessioned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public Float getNumber1() {
        return number1;
    }

    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    public Float getNumber2() {
        return number2;
    }

    public void setNumber2(Float number2) {
        this.number2 = number2;
    }

    public String getObjectCondition() {
        return objectCondition;
    }

    public void setObjectCondition(String objectCondition) {
        this.objectCondition = objectCondition;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
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

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public Short getVisibility() {
        return visibility;
    }

    public void setVisibility(Short visibility) {
        this.visibility = visibility;
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

    public Boolean getYesNo3() {
        return yesNo3;
    }

    public void setYesNo3(Boolean yesNo3) {
        this.yesNo3 = yesNo3;
    }

    public Boolean getYesNo4() {
        return yesNo4;
    }

    public void setYesNo4(Boolean yesNo4) {
        this.yesNo4 = yesNo4;
    }

    public Boolean getYesNo5() {
        return yesNo5;
    }

    public void setYesNo5(Boolean yesNo5) {
        this.yesNo5 = yesNo5;
    }

    public Boolean getYesNo6() {
        return yesNo6;
    }

    public void setYesNo6(Boolean yesNo6) {
        this.yesNo6 = yesNo6;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmenteventCollection() {
        return treatmenteventCollection;
    }

    public void setTreatmenteventCollection(Collection<Treatmentevent> treatmenteventCollection) {
        this.treatmenteventCollection = treatmenteventCollection;
    }

    @XmlTransient
    public Collection<Preparation> getPreparationCollection() {
        return preparationCollection;
    }

    public void setPreparationCollection(Collection<Preparation> preparationCollection) {
        this.preparationCollection = preparationCollection;
    }

    @XmlTransient
    public Collection<Otheridentifier> getOtheridentifierCollection() {
        return otheridentifierCollection;
    }

    public void setOtheridentifierCollection(Collection<Otheridentifier> otheridentifierCollection) {
        this.otheridentifierCollection = otheridentifierCollection;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptionCollection() {
        return conservdescriptionCollection;
    }

    public void setConservdescriptionCollection(Collection<Conservdescription> conservdescriptionCollection) {
        this.conservdescriptionCollection = conservdescriptionCollection;
    }

    @XmlTransient
    public Collection<Collectionrelationship> getCollectionrelationshipCollection() {
        return collectionrelationshipCollection;
    }

    public void setCollectionrelationshipCollection(Collection<Collectionrelationship> collectionrelationshipCollection) {
        this.collectionrelationshipCollection = collectionrelationshipCollection;
    }

    @XmlTransient
    public Collection<Collectionrelationship> getCollectionrelationshipCollection1() {
        return collectionrelationshipCollection1;
    }

    public void setCollectionrelationshipCollection1(Collection<Collectionrelationship> collectionrelationshipCollection1) {
        this.collectionrelationshipCollection1 = collectionrelationshipCollection1;
    }

    @XmlTransient
    public Collection<Collectionobjectattr> getCollectionobjectattrCollection() {
        return collectionobjectattrCollection;
    }

    public void setCollectionobjectattrCollection(Collection<Collectionobjectattr> collectionobjectattrCollection) {
        this.collectionobjectattrCollection = collectionobjectattrCollection;
    }

    @XmlTransient
    public Collection<Determination> getDeterminationCollection() {
        return determinationCollection;
    }

    public void setDeterminationCollection(Collection<Determination> determinationCollection) {
        this.determinationCollection = determinationCollection;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequenceCollection() {
        return dnasequenceCollection;
    }

    public void setDnasequenceCollection(Collection<Dnasequence> dnasequenceCollection) {
        this.dnasequenceCollection = dnasequenceCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionobjectattachmentCollection() {
        return collectionobjectattachmentCollection;
    }

    public void setCollectionobjectattachmentCollection(Collection<Collectionobjectattachment> collectionobjectattachmentCollection) {
        this.collectionobjectattachmentCollection = collectionobjectattachmentCollection;
    }

    public Accession getAccessionID() {
        return accessionID;
    }

    public void setAccessionID(Accession accessionID) {
        this.accessionID = accessionID;
    }

    public Agent getCatalogerID() {
        return catalogerID;
    }

    public void setCatalogerID(Agent catalogerID) {
        this.catalogerID = catalogerID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Fieldnotebookpage getFieldNotebookPageID() {
        return fieldNotebookPageID;
    }

    public void setFieldNotebookPageID(Fieldnotebookpage fieldNotebookPageID) {
        this.fieldNotebookPageID = fieldNotebookPageID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Specifyuser getVisibilitySetByID() {
        return visibilitySetByID;
    }

    public void setVisibilitySetByID(Specifyuser visibilitySetByID) {
        this.visibilitySetByID = visibilitySetByID;
    }

    public se.nrm.specify.datamodel.Collection getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(se.nrm.specify.datamodel.Collection collectionID) {
        this.collectionID = collectionID;
    }

    public Paleocontext getPaleoContextID() {
        return paleoContextID;
    }

    public void setPaleoContextID(Paleocontext paleoContextID) {
        this.paleoContextID = paleoContextID;
    }

    public Collectionobjectattribute getCollectionObjectAttributeID() {
        return collectionObjectAttributeID;
    }

    public void setCollectionObjectAttributeID(Collectionobjectattribute collectionObjectAttributeID) {
        this.collectionObjectAttributeID = collectionObjectAttributeID;
    }

    public Container getContainerOwnerID() {
        return containerOwnerID;
    }

    public void setContainerOwnerID(Container containerOwnerID) {
        this.containerOwnerID = containerOwnerID;
    }

    public Appraisal getAppraisalID() {
        return appraisalID;
    }

    public void setAppraisalID(Appraisal appraisalID) {
        this.appraisalID = appraisalID;
    }

    public Collectingevent getCollectingEventID() {
        return collectingEventID;
    }

    public void setCollectingEventID(Collectingevent collectingEventID) {
        this.collectingEventID = collectingEventID;
    }

    public Container getContainerID() {
        return containerID;
    }

    public void setContainerID(Container containerID) {
        this.containerID = containerID;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionobjectcitationCollection() {
        return collectionobjectcitationCollection;
    }

    public void setCollectionobjectcitationCollection(Collection<Collectionobjectcitation> collectionobjectcitationCollection) {
        this.collectionobjectcitationCollection = collectionobjectcitationCollection;
    }

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataitemCollection() {
        return exsiccataitemCollection;
    }

    public void setExsiccataitemCollection(Collection<Exsiccataitem> exsiccataitemCollection) {
        this.exsiccataitemCollection = exsiccataitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionObjectID != null ? collectionObjectID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobject)) {
            return false;
        }
        Collectionobject other = (Collectionobject) object;
        if ((this.collectionObjectID == null && other.collectionObjectID != null) || (this.collectionObjectID != null && !this.collectionObjectID.equals(other.collectionObjectID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Collectionobject[ collectionObjectID=" + collectionObjectID + " ]";
    }
    
}

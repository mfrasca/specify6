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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID; 
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
    @NamedQuery(name = "Collectionobject.findByCollectionObjectId", query = "SELECT c FROM Collectionobject c WHERE c.collectionObjectId = :collectionObjectId"),
    @NamedQuery(name = "Collectionobject.findByTimestampCreated", query = "SELECT c FROM Collectionobject c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Collectionobject.findByTimestampModified", query = "SELECT c FROM Collectionobject c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Collectionobject.findByVersion", query = "SELECT c FROM Collectionobject c WHERE c.version = :version"),
    @NamedQuery(name = "Collectionobject.findByCollectionMemberId", query = "SELECT c FROM Collectionobject c WHERE c.collectionMemberId = :collectionMemberId"),
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
    @NamedQuery(name = "Collectionobject.findLastRecordByCollectionCode", query = "select c from Collectionobject c where c.collection.code = :code order by c.collectionObjectId desc"),
    @NamedQuery(name = "Collectionobject.findByCollectingEventIDAndYesNo2", query = "SELECT c FROM Collectionobject c WHERE c.collectingEvent = :collectingEventID and c.yesNo2 IS NULL"),  
    @NamedQuery(name = "Collectionobject.findByYesNo1", query = "SELECT c FROM Collectionobject c WHERE c.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Collectionobject.findByYesNo2", query = "SELECT c FROM Collectionobject c WHERE c.yesNo2 = :yesNo2"),
    @NamedQuery(name = "Collectionobject.findByYesNo3", query = "SELECT c FROM Collectionobject c WHERE c.yesNo3 = :yesNo3"),
    @NamedQuery(name = "Collectionobject.findByYesNo4", query = "SELECT c FROM Collectionobject c WHERE c.yesNo4 = :yesNo4"),
    @NamedQuery(name = "Collectionobject.findByYesNo5", query = "SELECT c FROM Collectionobject c WHERE c.yesNo5 = :yesNo5"),
    @NamedQuery(name = "Collectionobject.findByYesNo6", query = "SELECT c FROM Collectionobject c WHERE c.yesNo6 = :yesNo6")})
public class Collectionobject extends BaseEntity {
  
//implements CycleRecoverable { 
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CollectionObjectID")
    private Integer collectionObjectId;
      
    @NotNull
    @Basic(optional = false)  
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Size(max = 32)
    @Column(name = "AltCatalogNumber")
    private String altCatalogNumber;
    
    @Size(max = 32)
    @Column(name = "Availability")
    private String availability;
    
    @Size(max = 32)
    @Column(name = "CatalogNumber")  
    private String catalogNumber;
    
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
    
    @Column(name = "CatalogedDate")
    @Temporal(TemporalType.DATE)
    private Date catalogedDate;
    
    @Column(name = "InventoryDate")
    @Temporal(TemporalType.DATE)
    private Date inventoryDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "OCR")
    private String ocr;
    
    @Column(name = "SGRStatus")
    private Short sGRStatus;
    
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
    
    @XmlTransient
    @JoinTable(name = "project_colobj", joinColumns = {
        @JoinColumn(name = "CollectionObjectID", referencedColumnName = "CollectionObjectID")}, inverseJoinColumns = {
        @JoinColumn(name = "ProjectID", referencedColumnName = "ProjectID")})
    @ManyToMany
    private Collection<Project> projects;
      
    @OneToMany(mappedBy = "collectionObject")
    private Collection<Treatmentevent> treatmentEvents;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObject")
    private Collection<Preparation> preparations;
     
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObject")
    private Collection<Otheridentifier> otherIdentifiers;
    
    @OneToMany(mappedBy = "collectionObject")
    private Collection<Conservdescription> conservDescriptions;
    
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rightSide")
    private Collection<Collectionrelationship> rightSideRels;
    
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leftSide")
    private Collection<Collectionrelationship> leftSideRels;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObject")
    private Collection<Collectionobjectattr> collectionObjectAttrs;
    
    
    @OneToMany(mappedBy = "collectionObject", cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH}, orphanRemoval=true)
    private Collection<Determination> determinations;
    
    @XmlTransient
    @OneToMany(mappedBy = "collectionObject")
    private Collection<Dnasequence> dnaSequences;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObject")
    private Collection<Collectionobjectattachment> collectionObjectAttachments;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    private Accession accession;
    
    @JoinColumn(name = "CatalogerID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent cataloger;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "FieldNotebookPageID", referencedColumnName = "FieldNotebookPageID")
    @ManyToOne
    private Fieldnotebookpage fieldNotebookPage;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetBy;
     
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private se.nrm.specify.datamodel.Collection collection;
    
    @JoinColumn(name = "PaleoContextID", referencedColumnName = "PaleoContextID")
    @ManyToOne
    private Paleocontext paleoContext;
    
    @JoinColumn(name = "CollectionObjectAttributeID", referencedColumnName = "CollectionObjectAttributeID")
    @ManyToOne
    private Collectionobjectattribute collectionObjectAttribute;
    
    @JoinColumn(name = "ContainerOwnerID", referencedColumnName = "ContainerID")
    @ManyToOne
    private Container containerOwner;
    
    @JoinColumn(name = "AppraisalID", referencedColumnName = "AppraisalID")
    @ManyToOne
    private Appraisal appraisal;
    
    @JoinColumn(name = "CollectingEventID", referencedColumnName = "CollectingEventID")
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    private Collectingevent collectingEvent;
    
    @JoinColumn(name = "ContainerID", referencedColumnName = "ContainerID")
    @ManyToOne
    private Container container;
     
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObject")
    private Collection<Collectionobjectcitation> collectionObjectCitations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionObject")
    private Collection<Exsiccataitem> exsiccataItems;
    
    

    public Collectionobject() {
        super();
    }

    public Collectionobject(Integer collectionObjectId) {
        this.collectionObjectId = collectionObjectId;
    }

    public Collectionobject(Integer collectionObjectId, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.collectionObjectId = collectionObjectId; 
        this.collectionMemberId = collectionMemberId;
    }
 

 
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (collectionObjectId != null) ? collectionObjectId.toString() : "0";
    }

    @NotNull(message="CollectionMemberId must be specified.")
    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Integer getCollectionObjectId() {
        return collectionObjectId;
    }

    public void setCollectionObjectId(Integer collectionObjectId) {
        this.collectionObjectId = collectionObjectId;
    }

    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
    }

    public Appraisal getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Appraisal appraisal) {
        this.appraisal = appraisal;
    }

    public Agent getCataloger() {
        return cataloger;
    }

    public void setCataloger(Agent cataloger) {
        this.cataloger = cataloger;
    }

    public Collectingevent getCollectingEvent() {
        return collectingEvent;
    }

    public void setCollectingEvent(Collectingevent collectingEvent) {
        this.collectingEvent = collectingEvent;
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
    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

 

    public Paleocontext getPaleoContext() {
        return paleoContext;
    }

    public void setPaleoContext(Paleocontext paleoContext) {
        this.paleoContext = paleoContext;
    }
   
    public Collection<Treatmentevent> getTreatmentEvents() {
        return treatmentEvents;
    }

    public void setTreatmentEvents(Collection<Treatmentevent> treatmentEvents) {
        this.treatmentEvents = treatmentEvents;
    }

    public Specifyuser getVisibilitySetBy() {
        return visibilitySetBy;
    }

    public void setVisibilitySetBy(Specifyuser visibilitySetBy) {
        this.visibilitySetBy = visibilitySetBy;
    }
 

    @XmlTransient
    public Collection<Collectionrelationship> getLeftSideRels() {
        return leftSideRels;
    }

    public void setLeftSideRels(Collection<Collectionrelationship> leftSideRels) {
        this.leftSideRels = leftSideRels;
    }
 
    public Collection<Otheridentifier> getOtherIdentifiers() {
        return otherIdentifiers;
    }

    public void setOtherIdentifiers(Collection<Otheridentifier> otherIdentifiers) {
        this.otherIdentifiers = otherIdentifiers;
    }

    @XmlTransient
    public Collection<Collectionrelationship> getRightSideRels() {
        return rightSideRels;
    }

    public void setRightSideRels(Collection<Collectionrelationship> rightSideRels) {
        this.rightSideRels = rightSideRels;
    }
 
 
    @XmlElementWrapper(name="preparations")
    @XmlElement(name="preparation") 
    public Collection<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(Collection<Preparation> preparations) {
        this.preparations = preparations;
    }
 

    @XmlElementWrapper(name="determinations")
    @XmlElement(name="determination") 
    public Collection<Determination> getDeterminations() {
        return determinations;
    }

    public void setDeterminations(Collection<Determination> determinations) {
        this.determinations = determinations;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnaSequences() {
        return dnaSequences;
    }

    public void setDnaSequences(Collection<Dnasequence> dnaSequences) {
        this.dnaSequences = dnaSequences;
    }

 

 

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Container getContainerOwner() {
        return containerOwner;
    }

    public void setContainerOwner(Container containerOwner) {
        this.containerOwner = containerOwner;
    }
  
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Fieldnotebookpage getFieldNotebookPage() {
        return fieldNotebookPage;
    }

    public void setFieldNotebookPage(Fieldnotebookpage fieldNotebookPage) {
        this.fieldNotebookPage = fieldNotebookPage;
    }

    @NotNull(message="Collection must be specified.")
    public se.nrm.specify.datamodel.Collection getCollection() {
        return collection;
    }

    public void setCollection(se.nrm.specify.datamodel.Collection collection) {
        this.collection = collection;
    }
 
    public Collection<Collectionobjectattachment> getCollectionObjectAttachments() {
        return collectionObjectAttachments;
    }

    public void setCollectionObjectAttachments(Collection<Collectionobjectattachment> collectionObjectAttachments) {
        this.collectionObjectAttachments = collectionObjectAttachments;
    }
 
    public Collectionobjectattribute getCollectionObjectAttribute() {
        return collectionObjectAttribute;
    }

    public void setCollectionObjectAttribute(Collectionobjectattribute collectionObjectAttribute) {
        this.collectionObjectAttribute = collectionObjectAttribute;
    }

   
    public Collection<Collectionobjectattr> getCollectionObjectAttrs() {
        return collectionObjectAttrs;
    }
 
    public void setCollectionObjectAttrs(Collection<Collectionobjectattr> collectionObjectAttrs) {
        this.collectionObjectAttrs = collectionObjectAttrs;
    }
 
    public Collection<Collectionobjectcitation> getCollectionObjectCitations() {
        return collectionObjectCitations;
    }

    public void setCollectionObjectCitations(Collection<Collectionobjectcitation> collectionObjectCitations) {
        this.collectionObjectCitations = collectionObjectCitations;
    }
 
    public Collection<Conservdescription> getConservDescriptions() {
        return conservDescriptions;
    }

    public void setConservDescriptions(Collection<Conservdescription> conservDescriptions) {
        this.conservDescriptions = conservDescriptions;
    }
 
    public Collection<Exsiccataitem> getExsiccataItems() {
        return exsiccataItems;
    }

    public void setExsiccataItems(Collection<Exsiccataitem> exsiccataItems) {
        this.exsiccataItems = exsiccataItems;
    }

    public Date getCatalogedDate() {
        return catalogedDate;
    }

    public void setCatalogedDate(Date catalogedDate) {
        this.catalogedDate = catalogedDate;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getOcr() {
        return ocr;
    }

    public void setOcr(String ocr) {
        this.ocr = ocr;
    }

 

    public Short getsGRStatus() {
        return sGRStatus;
    }

    public void setsGRStatus(Short sGRStatus) {
        this.sGRStatus = sGRStatus;
    }
     
    @Override
    public String getEntityName() {
        return "collectionObject";
    }
    
    
  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collectionObjectId != null ? collectionObjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Collectionobject)) {
            return false;
        }
        Collectionobject other = (Collectionobject) object;
        if ((this.collectionObjectId == null && other.collectionObjectId != null) || (this.collectionObjectId != null && !this.collectionObjectId.equals(other.collectionObjectId))) {
            return false;
        }
        return true;
    }
  
    
    @Override
    public String toString() {
        return "Collectionobject[ collectionObjectId=" + collectionObjectId + " ]";
    }
 
   
}

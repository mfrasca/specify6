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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "agent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agent.findAll", query = "SELECT a FROM Agent a"),
    @NamedQuery(name = "Agent.findByAgentID", query = "SELECT a FROM Agent a WHERE a.agentID = :agentID"),
    @NamedQuery(name = "Agent.findByTimestampCreated", query = "SELECT a FROM Agent a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Agent.findByTimestampModified", query = "SELECT a FROM Agent a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Agent.findByVersion", query = "SELECT a FROM Agent a WHERE a.version = :version"),
    @NamedQuery(name = "Agent.findByAbbreviation", query = "SELECT a FROM Agent a WHERE a.abbreviation = :abbreviation"),
    @NamedQuery(name = "Agent.findByAgentType", query = "SELECT a FROM Agent a WHERE a.agentType = :agentType"),
    @NamedQuery(name = "Agent.findByDateOfBirth", query = "SELECT a FROM Agent a WHERE a.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "Agent.findByDateOfBirthPrecision", query = "SELECT a FROM Agent a WHERE a.dateOfBirthPrecision = :dateOfBirthPrecision"),
    @NamedQuery(name = "Agent.findByDateOfDeath", query = "SELECT a FROM Agent a WHERE a.dateOfDeath = :dateOfDeath"),
    @NamedQuery(name = "Agent.findByDateOfDeathPrecision", query = "SELECT a FROM Agent a WHERE a.dateOfDeathPrecision = :dateOfDeathPrecision"),
    @NamedQuery(name = "Agent.findByEmail", query = "SELECT a FROM Agent a WHERE a.email = :email"),
    @NamedQuery(name = "Agent.findByFirstName", query = "SELECT a FROM Agent a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Agent.findByGuid", query = "SELECT a FROM Agent a WHERE a.guid = :guid"),
    @NamedQuery(name = "Agent.findByInitials", query = "SELECT a FROM Agent a WHERE a.initials = :initials"),
    @NamedQuery(name = "Agent.findByInterests", query = "SELECT a FROM Agent a WHERE a.interests = :interests"),
    @NamedQuery(name = "Agent.findByJobTitle", query = "SELECT a FROM Agent a WHERE a.jobTitle = :jobTitle"),
    @NamedQuery(name = "Agent.findByLastName", query = "SELECT a FROM Agent a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Agent.findByMiddleInitial", query = "SELECT a FROM Agent a WHERE a.middleInitial = :middleInitial"),
    @NamedQuery(name = "Agent.findByTitle", query = "SELECT a FROM Agent a WHERE a.title = :title"),
    @NamedQuery(name = "Agent.findBySpecifyuserid", query = "SELECT a FROM Agent a WHERE a.specifyUserID.specifyUserID = :specifyUserID"),
    @NamedQuery(name = "Agent.findByDateType", query = "SELECT a FROM Agent a WHERE a.dateType = :dateType")})
public class Agent extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AgentID")
    private Integer agentID;
      
    @Size(max = 50)
    @Column(name = "Abbreviation")
    private String abbreviation;
    
    @NotNull
    @Basic(optional = false) 
    @Column(name = "AgentType")
    private short agentType;
    
    @Column(name = "DateOfBirth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Column(name = "DateOfBirthPrecision")
    private Short dateOfBirthPrecision;
    
    @Column(name = "DateOfDeath")
    @Temporal(TemporalType.DATE)
    private Date dateOfDeath;
    
    @Column(name = "DateOfDeathPrecision")
    private Short dateOfDeathPrecision;
    
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    
    @Size(max = 50)
    @Column(name = "FirstName")
    private String firstName;
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Size(max = 8)
    @Column(name = "Initials")
    private String initials;
    
    @Size(max = 255)
    @Column(name = "Interests")
    private String interests;
    
    @Size(max = 50)
    @Column(name = "JobTitle")
    private String jobTitle;
    
    @Size(max = 128)
    @Column(name = "LastName")
    private String lastName;
    
    @Size(max = 50)
    @Column(name = "MiddleInitial")
    private String middleInitial;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 50)
    @Column(name = "Title")
    private String title;
    
    @Column(name = "DateType")
    private Short dateType;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "URL")
    private String url;
    
    @OneToMany(mappedBy = "createdByAgentID", cascade= CascadeType.ALL)
    private Collection<Localitynamealias> localitynamealiasCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID", cascade= CascadeType.ALL)
    private Collection<Localitynamealias> localitynamealiasCollection1;
    
    @OneToMany(mappedBy = "shipperID")
    private Collection<Shipment> shipmentCollection;
    
    @OneToMany(mappedBy = "shippedToID")
    private Collection<Shipment> shipmentCollection1;
    
    @OneToMany(mappedBy = "shippedByID")
    private Collection<Shipment> shipmentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Shipment> shipmentCollection3;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Shipment> shipmentCollection4;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Loanagent> loanagentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Loanagent> loanagentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Loanagent> loanagentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Preptype> preptypeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Preptype> preptypeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Agentgeography> agentgeographyCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Agentgeography> agentgeographyCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")  
    private Collection<Agentgeography> agentgeographyCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Deaccessionagent> deaccessionagentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Deaccessionagent> deaccessionagentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Deaccessionagent> deaccessionagentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Loanattachment> loanattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Loanattachment> loanattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Treatmentevent> treatmenteventCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Treatmentevent> treatmenteventCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Workbench> workbenchCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Workbench> workbenchCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Attachmentmetadata> attachmentmetadataCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Attachmentmetadata> attachmentmetadataCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Taxon> taxonCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Taxon> taxonCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectingeventattr> collectingeventattrCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectingeventattr> collectingeventattrCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Splocaleitemstr> splocaleitemstrCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Splocaleitemstr> splocaleitemstrCollection1;
    
    @OneToMany(mappedBy = "preparedByAgentID")
    private Collection<Preparation> preparationCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Preparation> preparationCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Preparation> preparationCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Workbenchtemplate> workbenchtemplateCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Workbenchtemplate> workbenchtemplateCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Sptasksemaphore> sptasksemaphoreCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Sptasksemaphore> sptasksemaphoreCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Picklistitem> picklistitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Picklistitem> picklistitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Loan> loanCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Loan> loanCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectingtrip> collectingtripCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectingtrip> collectingtripCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Project> projectCollection;
    
    @OneToMany(mappedBy = "projectAgentID")
    private Collection<Project> projectCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Project> projectCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Agentattachment> agentattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Agentattachment> agentattachmentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Agentattachment> agentattachmentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Preparationattribute> preparationattributeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Preparationattribute> preparationattributeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Container> containerCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Container> containerCollection1;
    
    @OneToMany(mappedBy = "curatorID")
    private Collection<Conservevent> conserveventCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Conservevent> conserveventCollection1;
    
    @OneToMany(mappedBy = "treatedByAgentID")
    private Collection<Conservevent> conserveventCollection2;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Conservevent> conserveventCollection3;
    
    @OneToMany(mappedBy = "examinedByAgentID")
    private Collection<Conservevent> conserveventCollection4;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Lithostrattreedefitem> lithostrattreedefitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Lithostrattreedefitem> lithostrattreedefitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Lithostrattreedef> lithostrattreedefCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Lithostrattreedef> lithostrattreedefCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID") 
    private Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Repositoryagreementattachment> repositoryagreementattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Repositoryagreementattachment> repositoryagreementattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Permitattachment> permitattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Permitattachment> permitattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Dnasequenceattachment> dnasequenceattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Dnasequenceattachment> dnasequenceattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Appraisal> appraisalCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Appraisal> appraisalCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Appraisal> appraisalCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Picklist> picklistCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Picklist> picklistCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spappresourcedata> spappresourcedataCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spappresourcedata> spappresourcedataCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receivedFromOrganizationID")
    private Collection<Exchangein> exchangeinCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Exchangein> exchangeinCollection1;
     
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Exchangein> exchangeinCollection2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogedByID")
    private Collection<Exchangein> exchangeinCollection3;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Gift> giftCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Gift> giftCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Agentvariant> agentvariantCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Agentvariant> agentvariantCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Agentvariant> agentvariantCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spauditlog> spauditlogCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spauditlog> spauditlogCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID") 
    private Collection<Otheridentifier> otheridentifierCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Otheridentifier> otheridentifierCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Splocalecontaineritem> splocalecontaineritemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Splocalecontaineritem> splocalecontaineritemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Recordset> recordsetCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Recordset> recordsetCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection1;
    
    @OneToMany(mappedBy = "agentID")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Giftagent> giftagentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Giftagent> giftagentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Giftagent> giftagentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<se.nrm.specify.datamodel.Collection> collectionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<se.nrm.specify.datamodel.Collection> collectionCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spversion> spversionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spversion> spversionCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Conservdescription> conservdescriptionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Conservdescription> conservdescriptionCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Addressofrecord> addressofrecordCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Addressofrecord> addressofrecordCollection1;
    
    @OneToMany(mappedBy = "agentID", cascade= CascadeType.ALL)
    private Collection<Addressofrecord> addressofrecordCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
//    @XmlElement
    private Collection<Division> divisionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
//    @XmlElement
    private Collection<Division> divisionCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Storagetreedefitem> storagetreedefitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Storagetreedefitem> storagetreedefitemCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sentToOrganizationID")
    private Collection<Exchangeout> exchangeoutCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Exchangeout> exchangeoutCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Exchangeout> exchangeoutCollection2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogedByID")
    private Collection<Exchangeout> exchangeoutCollection3;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spprincipal> spprincipalCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spprincipal> spprincipalCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Autonumberingscheme> autonumberingschemeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Autonumberingscheme> autonumberingschemeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Determinationcitation> determinationcitationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Determinationcitation> determinationcitationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Specifyuser> specifyuserCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Specifyuser> specifyuserCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Preparationattachment> preparationattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Preparationattachment> preparationattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Localitydetail> localitydetailCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Localitydetail> localitydetailCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectionrelationship> collectionrelationshipCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionrelationship> collectionrelationshipCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectionobjectattr> collectionobjectattrCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionobjectattr> collectionobjectattrCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectingeventattribute> collectingeventattributeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectingeventattribute> collectingeventattributeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spviewsetobj> spviewsetobjCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spviewsetobj> spviewsetobjCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spfieldvaluedefault> spfieldvaluedefaultCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spfieldvaluedefault> spfieldvaluedefaultCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Commonnametx> commonnametxCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Commonnametx> commonnametxCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Borrowmaterial> borrowmaterialCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Borrowmaterial> borrowmaterialCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Accessionagent> accessionagentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Accessionagent> accessionagentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Accessionagent> accessionagentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spappresource> spappresourceCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spappresource> spappresourceCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geologictimeperiod> geologictimeperiodCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geologictimeperiod> geologictimeperiodCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Repositoryagreement> repositoryagreementCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Repositoryagreement> repositoryagreementCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Repositoryagreement> repositoryagreementCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spqueryfield> spqueryfieldCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID",  cascade= CascadeType.ALL)
    private Collection<Spqueryfield> spqueryfieldCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spreport> spreportCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spreport> spreportCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Referencework> referenceworkCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Referencework> referenceworkCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Localityattachment> localityattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Localityattachment> localityattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Accession> accessionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Accession> accessionCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupID")
    private Collection<Groupperson> grouppersonCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Groupperson> grouppersonCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Groupperson> grouppersonCollection2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "memberID")
    private Collection<Groupperson> grouppersonCollection3;
    
    @OneToMany(mappedBy = "returnedByID")
    private Collection<Borrowreturnmaterial> borrowreturnmaterialCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Borrowreturnmaterial> borrowreturnmaterialCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Borrowreturnmaterial> borrowreturnmaterialCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Preparationattr> preparationattrCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Preparationattr> preparationattrCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Datatype> datatypeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Datatype> datatypeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Storagetreedef> storagetreedefCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Storagetreedef> storagetreedefCollection1;
    
    @OneToMany(mappedBy = "issuedToID")
    private Collection<Permit> permitCollection;
    
    @OneToMany(mappedBy = "issuedByID")
    private Collection<Permit> permitCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Permit> permitCollection2;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Permit> permitCollection3;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Commonnametxcitation> commonnametxcitationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Commonnametxcitation> commonnametxcitationCollection1;
    
    @OneToMany(mappedBy = "determinerID")
    private Collection<Determination> determinationCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Determination> determinationCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Determination> determinationCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spexportschema> spexportschemaCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spexportschema> spexportschemaCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectingevent> collectingeventCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectingevent> collectingeventCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectionreltype> collectionreltypeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionreltype> collectionreltypeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Dnasequence> dnasequenceCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Dnasequence> dnasequenceCollection1;
    
    @OneToMany(mappedBy = "agentID")
    private Collection<Dnasequence> dnasequenceCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spauditlogfield> spauditlogfieldCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spauditlogfield> spauditlogfieldCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geographytreedef> geographytreedefCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geographytreedef> geographytreedefCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID") 
    private Collection<Collectionobjectattachment> collectionobjectattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionobjectattachment> collectionobjectattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Taxoncitation> taxoncitationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID") 
    private Collection<Taxoncitation> taxoncitationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Dnasequencingruncitation> dnasequencingruncitationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Dnasequencingruncitation> dnasequencingruncitationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Inforequest> inforequestCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Inforequest> inforequestCollection1;
    
    @OneToMany(mappedBy = "agentID")
    private Collection<Inforequest> inforequestCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Lithostrat> lithostratCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Lithostrat> lithostratCollection1;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne 
    private Division divisionID;
    
    @JoinColumn(name = "InstitutionTCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Institution institutionTCID;
    
    @JoinColumn(name = "InstitutionCCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Institution institutionCCID;
    
    @OneToMany(mappedBy = "parentOrganizationID")
    private Collection<Agent> agentCollection;
    
    @JoinColumn(name = "ParentOrganizationID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent parentOrganizationID;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Agent> agentCollection1;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Agent> agentCollection2;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser specifyUserID;
    
    @JoinColumn(name = "CollectionTCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection collectionTCID;
    
    @JoinColumn(name = "CollectionCCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection collectionCCID;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Borrow> borrowCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Borrow> borrowCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spexportschemaitem> spexportschemaitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spexportschemaitem> spexportschemaitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Attributedef> attributedefCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Attributedef> attributedefCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Exsiccata> exsiccataCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Exsiccata> exsiccataCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Taxontreedefitem> taxontreedefitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Taxontreedefitem> taxontreedefitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Taxonattachment> taxonattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Taxonattachment> taxonattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Loanreturnpreparation> loanreturnpreparationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Loanreturnpreparation> loanreturnpreparationCollection1;
    
    @OneToMany(mappedBy = "receivedByID")
    private Collection<Loanreturnpreparation> loanreturnpreparationCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spexportschemamapping> spexportschemamappingCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spexportschemamapping> spexportschemamappingCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID") 
    private Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Giftpreparation> giftpreparationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Giftpreparation> giftpreparationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geographytreedefitem> geographytreedefitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geographytreedefitem> geographytreedefitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Splocalecontainer> splocalecontainerCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Splocalecontainer> splocalecontainerCollection1;
    
    @OneToMany(mappedBy = "catalogerID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionobject> collectionobjectCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectionobject> collectionobjectCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Borrowagent> borrowagentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Borrowagent> borrowagentCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Borrowagent> borrowagentCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Conserveventattachment> conserveventattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Conserveventattachment> conserveventattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectionobjectcitation> collectionobjectcitationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionobjectcitation> collectionobjectcitationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Deaccession> deaccessionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Deaccession> deaccessionCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Journal> journalCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Journal> journalCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Fieldnotebookpage> fieldnotebookpageCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Fieldnotebookpage> fieldnotebookpageCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Author> authorCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Author> authorCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Author> authorCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collector> collectorCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collector> collectorCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Collector> collectorCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geography> geographyCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geography> geographyCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Accessionattachment> accessionattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Accessionattachment> accessionattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Exsiccataitem> exsiccataitemCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Exsiccataitem> exsiccataitemCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Fieldnotebook> fieldnotebookCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Fieldnotebook> fieldnotebookCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")
    private Collection<Fieldnotebook> fieldnotebookCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Discipline> disciplineCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Discipline> disciplineCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Institution> institutionCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Institution> institutionCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Conservdescriptionattachment> conservdescriptionattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Conservdescriptionattachment> conservdescriptionattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Geocoorddetail> geocoorddetailCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Geocoorddetail> geocoorddetailCollection1;
    
    @OneToMany(mappedBy = "agentID")
    private Collection<Geocoorddetail> geocoorddetailCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Deaccessionpreparation> deaccessionpreparationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Deaccessionpreparation> deaccessionpreparationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectingeventattachment> collectingeventattachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectingeventattachment> collectingeventattachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Accessionauthorization> accessionauthorizationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Accessionauthorization> accessionauthorizationCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Attachment> attachmentCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Attachment> attachmentCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spappresourcedir> spappresourcedirCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spappresourcedir> spappresourcedirCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Spquery> spqueryCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Spquery> spqueryCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Collectionobjectattribute> collectionobjectattributeCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Collectionobjectattribute> collectionobjectattributeCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Attachmenttag> attachmenttagCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Attachmenttag> attachmenttagCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Paleocontext> paleocontextCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Paleocontext> paleocontextCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Loanpreparation> loanpreparationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Loanpreparation> loanpreparationCollection1;
    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modifiedByAgentID")
//    @XmlElement
    private Collection<Address> addressCollection2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByAgentID")
//    @XmlElement 
    private Collection<Address> addressCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID")  
    private Collection<Address> addressCollection;  // change addressCollection2 to addressCollection to match global.views.xml
     
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Locality> localityCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Locality> localityCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Agentspecialty> agentspecialtyCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Agentspecialty> agentspecialtyCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentID") 
    private Collection<Agentspecialty> agentspecialtyCollection2;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Taxontreedef> taxontreedefCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Taxontreedef> taxontreedefCollection1;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Localitycitation> localitycitationCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Localitycitation> localitycitationCollection1;
    
    @OneToMany(mappedBy = "runByAgentID")
    private Collection<Dnasequencingrun> dnasequencingrunCollection;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Dnasequencingrun> dnasequencingrunCollection1;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Dnasequencingrun> dnasequencingrunCollection2;
    
    @OneToMany(mappedBy = "preparedByAgentID")
    private Collection<Dnasequencingrun> dnasequencingrunCollection3;
    
    @OneToMany(mappedBy = "createdByAgentID")
    private Collection<Storage> storageCollection;
    
    @OneToMany(mappedBy = "modifiedByAgentID")
    private Collection<Storage> storageCollection1;
    

    public Agent() {
    }

    public Agent(Integer agentID) {
        this.agentID = agentID;
    }

    public Agent(Integer agentID, Date timestampCreated, short agentType) {
        super(timestampCreated);
        this.agentID = agentID; 
        this.agentType = agentType;
    }

    public Integer getAgentID() {
        return agentID;
    }

    public void setAgentID(Integer agentID) {
        this.agentID = agentID;
    } 
    
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public short getAgentType() {
        return agentType;
    }

    public void setAgentType(short agentType) {
        this.agentType = agentType;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Short getDateOfBirthPrecision() {
        return dateOfBirthPrecision;
    }

    public void setDateOfBirthPrecision(Short dateOfBirthPrecision) {
        this.dateOfBirthPrecision = dateOfBirthPrecision;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Short getDateOfDeathPrecision() {
        return dateOfDeathPrecision;
    }

    public void setDateOfDeathPrecision(Short dateOfDeathPrecision) {
        this.dateOfDeathPrecision = dateOfDeathPrecision;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getDateType() {
        return dateType;
    }

    public void setDateType(Short dateType) {
        this.dateType = dateType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public Collection<Localitynamealias> getLocalitynamealiasCollection() {
        return localitynamealiasCollection;
    }

    public void setLocalitynamealiasCollection(Collection<Localitynamealias> localitynamealiasCollection) {
        this.localitynamealiasCollection = localitynamealiasCollection;
    }

    @XmlTransient
    public Collection<Localitynamealias> getLocalitynamealiasCollection1() {
        return localitynamealiasCollection1;
    }

    public void setLocalitynamealiasCollection1(Collection<Localitynamealias> localitynamealiasCollection1) {
        this.localitynamealiasCollection1 = localitynamealiasCollection1;
    }

    @XmlTransient
    public Collection<Shipment> getShipmentCollection() {
        return shipmentCollection;
    }

    public void setShipmentCollection(Collection<Shipment> shipmentCollection) {
        this.shipmentCollection = shipmentCollection;
    }

    @XmlTransient
    public Collection<Shipment> getShipmentCollection1() {
        return shipmentCollection1;
    }

    public void setShipmentCollection1(Collection<Shipment> shipmentCollection1) {
        this.shipmentCollection1 = shipmentCollection1;
    }

    @XmlTransient
    public Collection<Shipment> getShipmentCollection2() {
        return shipmentCollection2;
    }

    public void setShipmentCollection2(Collection<Shipment> shipmentCollection2) {
        this.shipmentCollection2 = shipmentCollection2;
    }

    @XmlTransient
    public Collection<Shipment> getShipmentCollection3() {
        return shipmentCollection3;
    }

    public void setShipmentCollection3(Collection<Shipment> shipmentCollection3) {
        this.shipmentCollection3 = shipmentCollection3;
    }

    @XmlTransient
    public Collection<Shipment> getShipmentCollection4() {
        return shipmentCollection4;
    }

    public void setShipmentCollection4(Collection<Shipment> shipmentCollection4) {
        this.shipmentCollection4 = shipmentCollection4;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagentCollection() {
        return loanagentCollection;
    }

    public void setLoanagentCollection(Collection<Loanagent> loanagentCollection) {
        this.loanagentCollection = loanagentCollection;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagentCollection1() {
        return loanagentCollection1;
    }

    public void setLoanagentCollection1(Collection<Loanagent> loanagentCollection1) {
        this.loanagentCollection1 = loanagentCollection1;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagentCollection2() {
        return loanagentCollection2;
    }

    public void setLoanagentCollection2(Collection<Loanagent> loanagentCollection2) {
        this.loanagentCollection2 = loanagentCollection2;
    }

    @XmlTransient
    public Collection<Preptype> getPreptypeCollection() {
        return preptypeCollection;
    }

    public void setPreptypeCollection(Collection<Preptype> preptypeCollection) {
        this.preptypeCollection = preptypeCollection;
    }

    @XmlTransient
    public Collection<Preptype> getPreptypeCollection1() {
        return preptypeCollection1;
    }

    public void setPreptypeCollection1(Collection<Preptype> preptypeCollection1) {
        this.preptypeCollection1 = preptypeCollection1;
    }

    @XmlTransient
    public Collection<Agentgeography> getAgentgeographyCollection() {
        return agentgeographyCollection;
    }

    public void setAgentgeographyCollection(Collection<Agentgeography> agentgeographyCollection) {
        this.agentgeographyCollection = agentgeographyCollection;
    }

    @XmlTransient
    public Collection<Agentgeography> getAgentgeographyCollection1() {
        return agentgeographyCollection1;
    }

    public void setAgentgeographyCollection1(Collection<Agentgeography> agentgeographyCollection1) {
        this.agentgeographyCollection1 = agentgeographyCollection1;
    }

    @XmlTransient
    public Collection<Agentgeography> getAgentgeographyCollection2() {
        return agentgeographyCollection2;
    }

    public void setAgentgeographyCollection2(Collection<Agentgeography> agentgeographyCollection2) {
        this.agentgeographyCollection2 = agentgeographyCollection2;
    }

    @XmlTransient
    public Collection<Deaccessionagent> getDeaccessionagentCollection() {
        return deaccessionagentCollection;
    }

    public void setDeaccessionagentCollection(Collection<Deaccessionagent> deaccessionagentCollection) {
        this.deaccessionagentCollection = deaccessionagentCollection;
    }

    @XmlTransient
    public Collection<Deaccessionagent> getDeaccessionagentCollection1() {
        return deaccessionagentCollection1;
    }

    public void setDeaccessionagentCollection1(Collection<Deaccessionagent> deaccessionagentCollection1) {
        this.deaccessionagentCollection1 = deaccessionagentCollection1;
    }

    @XmlTransient
    public Collection<Deaccessionagent> getDeaccessionagentCollection2() {
        return deaccessionagentCollection2;
    }

    public void setDeaccessionagentCollection2(Collection<Deaccessionagent> deaccessionagentCollection2) {
        this.deaccessionagentCollection2 = deaccessionagentCollection2;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanattachmentCollection() {
        return loanattachmentCollection;
    }

    public void setLoanattachmentCollection(Collection<Loanattachment> loanattachmentCollection) {
        this.loanattachmentCollection = loanattachmentCollection;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanattachmentCollection1() {
        return loanattachmentCollection1;
    }

    public void setLoanattachmentCollection1(Collection<Loanattachment> loanattachmentCollection1) {
        this.loanattachmentCollection1 = loanattachmentCollection1;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmenteventCollection() {
        return treatmenteventCollection;
    }

    public void setTreatmenteventCollection(Collection<Treatmentevent> treatmenteventCollection) {
        this.treatmenteventCollection = treatmenteventCollection;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmenteventCollection1() {
        return treatmenteventCollection1;
    }

    public void setTreatmenteventCollection1(Collection<Treatmentevent> treatmenteventCollection1) {
        this.treatmenteventCollection1 = treatmenteventCollection1;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchCollection() {
        return workbenchCollection;
    }

    public void setWorkbenchCollection(Collection<Workbench> workbenchCollection) {
        this.workbenchCollection = workbenchCollection;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchCollection1() {
        return workbenchCollection1;
    }

    public void setWorkbenchCollection1(Collection<Workbench> workbenchCollection1) {
        this.workbenchCollection1 = workbenchCollection1;
    }

    @XmlTransient
    public Collection<Attachmentmetadata> getAttachmentmetadataCollection() {
        return attachmentmetadataCollection;
    }

    public void setAttachmentmetadataCollection(Collection<Attachmentmetadata> attachmentmetadataCollection) {
        this.attachmentmetadataCollection = attachmentmetadataCollection;
    }

    @XmlTransient
    public Collection<Attachmentmetadata> getAttachmentmetadataCollection1() {
        return attachmentmetadataCollection1;
    }

    public void setAttachmentmetadataCollection1(Collection<Attachmentmetadata> attachmentmetadataCollection1) {
        this.attachmentmetadataCollection1 = attachmentmetadataCollection1;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection() {
        return taxonCollection;
    }

    public void setTaxonCollection(Collection<Taxon> taxonCollection) {
        this.taxonCollection = taxonCollection;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection1() {
        return taxonCollection1;
    }

    public void setTaxonCollection1(Collection<Taxon> taxonCollection1) {
        this.taxonCollection1 = taxonCollection1;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingeventattrCollection() {
        return collectingeventattrCollection;
    }

    public void setCollectingeventattrCollection(Collection<Collectingeventattr> collectingeventattrCollection) {
        this.collectingeventattrCollection = collectingeventattrCollection;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingeventattrCollection1() {
        return collectingeventattrCollection1;
    }

    public void setCollectingeventattrCollection1(Collection<Collectingeventattr> collectingeventattrCollection1) {
        this.collectingeventattrCollection1 = collectingeventattrCollection1;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getSplocaleitemstrCollection() {
        return splocaleitemstrCollection;
    }

    public void setSplocaleitemstrCollection(Collection<Splocaleitemstr> splocaleitemstrCollection) {
        this.splocaleitemstrCollection = splocaleitemstrCollection;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getSplocaleitemstrCollection1() {
        return splocaleitemstrCollection1;
    }

    public void setSplocaleitemstrCollection1(Collection<Splocaleitemstr> splocaleitemstrCollection1) {
        this.splocaleitemstrCollection1 = splocaleitemstrCollection1;
    }

    @XmlTransient
    public Collection<Preparation> getPreparationCollection() {
        return preparationCollection;
    }

    public void setPreparationCollection(Collection<Preparation> preparationCollection) {
        this.preparationCollection = preparationCollection;
    }

    @XmlTransient
    public Collection<Preparation> getPreparationCollection1() {
        return preparationCollection1;
    }

    public void setPreparationCollection1(Collection<Preparation> preparationCollection1) {
        this.preparationCollection1 = preparationCollection1;
    }

    @XmlTransient
    public Collection<Preparation> getPreparationCollection2() {
        return preparationCollection2;
    }

    public void setPreparationCollection2(Collection<Preparation> preparationCollection2) {
        this.preparationCollection2 = preparationCollection2;
    }

    @XmlTransient
    public Collection<Workbenchtemplate> getWorkbenchtemplateCollection() {
        return workbenchtemplateCollection;
    }

    public void setWorkbenchtemplateCollection(Collection<Workbenchtemplate> workbenchtemplateCollection) {
        this.workbenchtemplateCollection = workbenchtemplateCollection;
    }

    @XmlTransient
    public Collection<Workbenchtemplate> getWorkbenchtemplateCollection1() {
        return workbenchtemplateCollection1;
    }

    public void setWorkbenchtemplateCollection1(Collection<Workbenchtemplate> workbenchtemplateCollection1) {
        this.workbenchtemplateCollection1 = workbenchtemplateCollection1;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphoreCollection() {
        return sptasksemaphoreCollection;
    }

    public void setSptasksemaphoreCollection(Collection<Sptasksemaphore> sptasksemaphoreCollection) {
        this.sptasksemaphoreCollection = sptasksemaphoreCollection;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphoreCollection1() {
        return sptasksemaphoreCollection1;
    }

    public void setSptasksemaphoreCollection1(Collection<Sptasksemaphore> sptasksemaphoreCollection1) {
        this.sptasksemaphoreCollection1 = sptasksemaphoreCollection1;
    }

    @XmlTransient
    public Collection<Picklistitem> getPicklistitemCollection() {
        return picklistitemCollection;
    }

    public void setPicklistitemCollection(Collection<Picklistitem> picklistitemCollection) {
        this.picklistitemCollection = picklistitemCollection;
    }

    @XmlTransient
    public Collection<Picklistitem> getPicklistitemCollection1() {
        return picklistitemCollection1;
    }

    public void setPicklistitemCollection1(Collection<Picklistitem> picklistitemCollection1) {
        this.picklistitemCollection1 = picklistitemCollection1;
    }

    @XmlTransient
    public Collection<Loan> getLoanCollection() {
        return loanCollection;
    }

    public void setLoanCollection(Collection<Loan> loanCollection) {
        this.loanCollection = loanCollection;
    }

    @XmlTransient
    public Collection<Loan> getLoanCollection1() {
        return loanCollection1;
    }

    public void setLoanCollection1(Collection<Loan> loanCollection1) {
        this.loanCollection1 = loanCollection1;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getGeologictimeperiodtreedefitemCollection() {
        return geologictimeperiodtreedefitemCollection;
    }

    public void setGeologictimeperiodtreedefitemCollection(Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection) {
        this.geologictimeperiodtreedefitemCollection = geologictimeperiodtreedefitemCollection;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getGeologictimeperiodtreedefitemCollection1() {
        return geologictimeperiodtreedefitemCollection1;
    }

    public void setGeologictimeperiodtreedefitemCollection1(Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection1) {
        this.geologictimeperiodtreedefitemCollection1 = geologictimeperiodtreedefitemCollection1;
    }

    @XmlTransient
    public Collection<Collectingtrip> getCollectingtripCollection() {
        return collectingtripCollection;
    }

    public void setCollectingtripCollection(Collection<Collectingtrip> collectingtripCollection) {
        this.collectingtripCollection = collectingtripCollection;
    }

    @XmlTransient
    public Collection<Collectingtrip> getCollectingtripCollection1() {
        return collectingtripCollection1;
    }

    public void setCollectingtripCollection1(Collection<Collectingtrip> collectingtripCollection1) {
        this.collectingtripCollection1 = collectingtripCollection1;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection1() {
        return projectCollection1;
    }

    public void setProjectCollection1(Collection<Project> projectCollection1) {
        this.projectCollection1 = projectCollection1;
    }

    @XmlTransient
    public Collection<Project> getProjectCollection2() {
        return projectCollection2;
    }

    public void setProjectCollection2(Collection<Project> projectCollection2) {
        this.projectCollection2 = projectCollection2;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentattachmentCollection() {
        return agentattachmentCollection;
    }

    public void setAgentattachmentCollection(Collection<Agentattachment> agentattachmentCollection) {
        this.agentattachmentCollection = agentattachmentCollection;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentattachmentCollection1() {
        return agentattachmentCollection1;
    }

    public void setAgentattachmentCollection1(Collection<Agentattachment> agentattachmentCollection1) {
        this.agentattachmentCollection1 = agentattachmentCollection1;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentattachmentCollection2() {
        return agentattachmentCollection2;
    }

    public void setAgentattachmentCollection2(Collection<Agentattachment> agentattachmentCollection2) {
        this.agentattachmentCollection2 = agentattachmentCollection2;
    }

    @XmlTransient
    public Collection<Preparationattribute> getPreparationattributeCollection() {
        return preparationattributeCollection;
    }

    public void setPreparationattributeCollection(Collection<Preparationattribute> preparationattributeCollection) {
        this.preparationattributeCollection = preparationattributeCollection;
    }

    @XmlTransient
    public Collection<Preparationattribute> getPreparationattributeCollection1() {
        return preparationattributeCollection1;
    }

    public void setPreparationattributeCollection1(Collection<Preparationattribute> preparationattributeCollection1) {
        this.preparationattributeCollection1 = preparationattributeCollection1;
    }

    @XmlTransient
    public Collection<Container> getContainerCollection() {
        return containerCollection;
    }

    public void setContainerCollection(Collection<Container> containerCollection) {
        this.containerCollection = containerCollection;
    }

    @XmlTransient
    public Collection<Container> getContainerCollection1() {
        return containerCollection1;
    }

    public void setContainerCollection1(Collection<Container> containerCollection1) {
        this.containerCollection1 = containerCollection1;
    }

    @XmlTransient
    public Collection<Conservevent> getConserveventCollection() {
        return conserveventCollection;
    }

    public void setConserveventCollection(Collection<Conservevent> conserveventCollection) {
        this.conserveventCollection = conserveventCollection;
    }

    @XmlTransient
    public Collection<Conservevent> getConserveventCollection1() {
        return conserveventCollection1;
    }

    public void setConserveventCollection1(Collection<Conservevent> conserveventCollection1) {
        this.conserveventCollection1 = conserveventCollection1;
    }

    @XmlTransient
    public Collection<Conservevent> getConserveventCollection2() {
        return conserveventCollection2;
    }

    public void setConserveventCollection2(Collection<Conservevent> conserveventCollection2) {
        this.conserveventCollection2 = conserveventCollection2;
    }

    @XmlTransient
    public Collection<Conservevent> getConserveventCollection3() {
        return conserveventCollection3;
    }

    public void setConserveventCollection3(Collection<Conservevent> conserveventCollection3) {
        this.conserveventCollection3 = conserveventCollection3;
    }

    @XmlTransient
    public Collection<Conservevent> getConserveventCollection4() {
        return conserveventCollection4;
    }

    public void setConserveventCollection4(Collection<Conservevent> conserveventCollection4) {
        this.conserveventCollection4 = conserveventCollection4;
    }

    @XmlTransient
    public Collection<Lithostrattreedefitem> getLithostrattreedefitemCollection() {
        return lithostrattreedefitemCollection;
    }

    public void setLithostrattreedefitemCollection(Collection<Lithostrattreedefitem> lithostrattreedefitemCollection) {
        this.lithostrattreedefitemCollection = lithostrattreedefitemCollection;
    }

    @XmlTransient
    public Collection<Lithostrattreedefitem> getLithostrattreedefitemCollection1() {
        return lithostrattreedefitemCollection1;
    }

    public void setLithostrattreedefitemCollection1(Collection<Lithostrattreedefitem> lithostrattreedefitemCollection1) {
        this.lithostrattreedefitemCollection1 = lithostrattreedefitemCollection1;
    }

    @XmlTransient
    public Collection<Lithostrattreedef> getLithostrattreedefCollection() {
        return lithostrattreedefCollection;
    }

    public void setLithostrattreedefCollection(Collection<Lithostrattreedef> lithostrattreedefCollection) {
        this.lithostrattreedefCollection = lithostrattreedefCollection;
    }

    @XmlTransient
    public Collection<Lithostrattreedef> getLithostrattreedefCollection1() {
        return lithostrattreedefCollection1;
    }

    public void setLithostrattreedefCollection1(Collection<Lithostrattreedef> lithostrattreedefCollection1) {
        this.lithostrattreedefCollection1 = lithostrattreedefCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachmentCollection() {
        return fieldnotebookpagesetattachmentCollection;
    }

    public void setFieldnotebookpagesetattachmentCollection(Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection) {
        this.fieldnotebookpagesetattachmentCollection = fieldnotebookpagesetattachmentCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachmentCollection1() {
        return fieldnotebookpagesetattachmentCollection1;
    }

    public void setFieldnotebookpagesetattachmentCollection1(Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection1) {
        this.fieldnotebookpagesetattachmentCollection1 = fieldnotebookpagesetattachmentCollection1;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryagreementattachmentCollection() {
        return repositoryagreementattachmentCollection;
    }

    public void setRepositoryagreementattachmentCollection(Collection<Repositoryagreementattachment> repositoryagreementattachmentCollection) {
        this.repositoryagreementattachmentCollection = repositoryagreementattachmentCollection;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryagreementattachmentCollection1() {
        return repositoryagreementattachmentCollection1;
    }

    public void setRepositoryagreementattachmentCollection1(Collection<Repositoryagreementattachment> repositoryagreementattachmentCollection1) {
        this.repositoryagreementattachmentCollection1 = repositoryagreementattachmentCollection1;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitattachmentCollection() {
        return permitattachmentCollection;
    }

    public void setPermitattachmentCollection(Collection<Permitattachment> permitattachmentCollection) {
        this.permitattachmentCollection = permitattachmentCollection;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitattachmentCollection1() {
        return permitattachmentCollection1;
    }

    public void setPermitattachmentCollection1(Collection<Permitattachment> permitattachmentCollection1) {
        this.permitattachmentCollection1 = permitattachmentCollection1;
    }

    @XmlTransient
    public Collection<Dnasequenceattachment> getDnasequenceattachmentCollection() {
        return dnasequenceattachmentCollection;
    }

    public void setDnasequenceattachmentCollection(Collection<Dnasequenceattachment> dnasequenceattachmentCollection) {
        this.dnasequenceattachmentCollection = dnasequenceattachmentCollection;
    }

    @XmlTransient
    public Collection<Dnasequenceattachment> getDnasequenceattachmentCollection1() {
        return dnasequenceattachmentCollection1;
    }

    public void setDnasequenceattachmentCollection1(Collection<Dnasequenceattachment> dnasequenceattachmentCollection1) {
        this.dnasequenceattachmentCollection1 = dnasequenceattachmentCollection1;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisalCollection() {
        return appraisalCollection;
    }

    public void setAppraisalCollection(Collection<Appraisal> appraisalCollection) {
        this.appraisalCollection = appraisalCollection;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisalCollection1() {
        return appraisalCollection1;
    }

    public void setAppraisalCollection1(Collection<Appraisal> appraisalCollection1) {
        this.appraisalCollection1 = appraisalCollection1;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisalCollection2() {
        return appraisalCollection2;
    }

    public void setAppraisalCollection2(Collection<Appraisal> appraisalCollection2) {
        this.appraisalCollection2 = appraisalCollection2;
    }

    @XmlTransient
    public Collection<Picklist> getPicklistCollection() {
        return picklistCollection;
    }

    public void setPicklistCollection(Collection<Picklist> picklistCollection) {
        this.picklistCollection = picklistCollection;
    }

    @XmlTransient
    public Collection<Picklist> getPicklistCollection1() {
        return picklistCollection1;
    }

    public void setPicklistCollection1(Collection<Picklist> picklistCollection1) {
        this.picklistCollection1 = picklistCollection1;
    }

    @XmlTransient
    public Collection<Spappresourcedata> getSpappresourcedataCollection() {
        return spappresourcedataCollection;
    }

    public void setSpappresourcedataCollection(Collection<Spappresourcedata> spappresourcedataCollection) {
        this.spappresourcedataCollection = spappresourcedataCollection;
    }

    @XmlTransient
    public Collection<Spappresourcedata> getSpappresourcedataCollection1() {
        return spappresourcedataCollection1;
    }

    public void setSpappresourcedataCollection1(Collection<Spappresourcedata> spappresourcedataCollection1) {
        this.spappresourcedataCollection1 = spappresourcedataCollection1;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeinCollection() {
        return exchangeinCollection;
    }

    public void setExchangeinCollection(Collection<Exchangein> exchangeinCollection) {
        this.exchangeinCollection = exchangeinCollection;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeinCollection1() {
        return exchangeinCollection1;
    }

    public void setExchangeinCollection1(Collection<Exchangein> exchangeinCollection1) {
        this.exchangeinCollection1 = exchangeinCollection1;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeinCollection2() {
        return exchangeinCollection2;
    }

    public void setExchangeinCollection2(Collection<Exchangein> exchangeinCollection2) {
        this.exchangeinCollection2 = exchangeinCollection2;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeinCollection3() {
        return exchangeinCollection3;
    }

    public void setExchangeinCollection3(Collection<Exchangein> exchangeinCollection3) {
        this.exchangeinCollection3 = exchangeinCollection3;
    }

    @XmlTransient
    public Collection<Gift> getGiftCollection() {
        return giftCollection;
    }

    public void setGiftCollection(Collection<Gift> giftCollection) {
        this.giftCollection = giftCollection;
    }

    @XmlTransient
    public Collection<Gift> getGiftCollection1() {
        return giftCollection1;
    }

    public void setGiftCollection1(Collection<Gift> giftCollection1) {
        this.giftCollection1 = giftCollection1;
    }

    @XmlTransient
    public Collection<Agentvariant> getAgentvariantCollection() {
        return agentvariantCollection;
    }

    public void setAgentvariantCollection(Collection<Agentvariant> agentvariantCollection) {
        this.agentvariantCollection = agentvariantCollection;
    }

    @XmlTransient
    public Collection<Agentvariant> getAgentvariantCollection1() {
        return agentvariantCollection1;
    }

    public void setAgentvariantCollection1(Collection<Agentvariant> agentvariantCollection1) {
        this.agentvariantCollection1 = agentvariantCollection1;
    }

    @XmlTransient
    public Collection<Agentvariant> getAgentvariantCollection2() {
        return agentvariantCollection2;
    }

    public void setAgentvariantCollection2(Collection<Agentvariant> agentvariantCollection2) {
        this.agentvariantCollection2 = agentvariantCollection2;
    }

    @XmlTransient
    public Collection<Spauditlog> getSpauditlogCollection() {
        return spauditlogCollection;
    }

    public void setSpauditlogCollection(Collection<Spauditlog> spauditlogCollection) {
        this.spauditlogCollection = spauditlogCollection;
    }

    @XmlTransient
    public Collection<Spauditlog> getSpauditlogCollection1() {
        return spauditlogCollection1;
    }

    public void setSpauditlogCollection1(Collection<Spauditlog> spauditlogCollection1) {
        this.spauditlogCollection1 = spauditlogCollection1;
    }

    @XmlTransient
    public Collection<Otheridentifier> getOtheridentifierCollection() {
        return otheridentifierCollection;
    }

    public void setOtheridentifierCollection(Collection<Otheridentifier> otheridentifierCollection) {
        this.otheridentifierCollection = otheridentifierCollection;
    }

    @XmlTransient
    public Collection<Otheridentifier> getOtheridentifierCollection1() {
        return otheridentifierCollection1;
    }

    public void setOtheridentifierCollection1(Collection<Otheridentifier> otheridentifierCollection1) {
        this.otheridentifierCollection1 = otheridentifierCollection1;
    }

    @XmlTransient
    public Collection<Splocalecontaineritem> getSplocalecontaineritemCollection() {
        return splocalecontaineritemCollection;
    }

    public void setSplocalecontaineritemCollection(Collection<Splocalecontaineritem> splocalecontaineritemCollection) {
        this.splocalecontaineritemCollection = splocalecontaineritemCollection;
    }

    @XmlTransient
    public Collection<Splocalecontaineritem> getSplocalecontaineritemCollection1() {
        return splocalecontaineritemCollection1;
    }

    public void setSplocalecontaineritemCollection1(Collection<Splocalecontaineritem> splocalecontaineritemCollection1) {
        this.splocalecontaineritemCollection1 = splocalecontaineritemCollection1;
    }

    @XmlTransient
    public Collection<Recordset> getRecordsetCollection() {
        return recordsetCollection;
    }

    public void setRecordsetCollection(Collection<Recordset> recordsetCollection) {
        this.recordsetCollection = recordsetCollection;
    }

    @XmlTransient
    public Collection<Recordset> getRecordsetCollection1() {
        return recordsetCollection1;
    }

    public void setRecordsetCollection1(Collection<Recordset> recordsetCollection1) {
        this.recordsetCollection1 = recordsetCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesetCollection() {
        return fieldnotebookpagesetCollection;
    }

    public void setFieldnotebookpagesetCollection(Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection) {
        this.fieldnotebookpagesetCollection = fieldnotebookpagesetCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesetCollection1() {
        return fieldnotebookpagesetCollection1;
    }

    public void setFieldnotebookpagesetCollection1(Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection1) {
        this.fieldnotebookpagesetCollection1 = fieldnotebookpagesetCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesetCollection2() {
        return fieldnotebookpagesetCollection2;
    }

    public void setFieldnotebookpagesetCollection2(Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection2) {
        this.fieldnotebookpagesetCollection2 = fieldnotebookpagesetCollection2;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagentCollection() {
        return giftagentCollection;
    }

    public void setGiftagentCollection(Collection<Giftagent> giftagentCollection) {
        this.giftagentCollection = giftagentCollection;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagentCollection1() {
        return giftagentCollection1;
    }

    public void setGiftagentCollection1(Collection<Giftagent> giftagentCollection1) {
        this.giftagentCollection1 = giftagentCollection1;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagentCollection2() {
        return giftagentCollection2;
    }

    public void setGiftagentCollection2(Collection<Giftagent> giftagentCollection2) {
        this.giftagentCollection2 = giftagentCollection2;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollectionCollection() {
        return collectionCollection;
    }

    public void setCollectionCollection(Collection<se.nrm.specify.datamodel.Collection> collectionCollection) {
        this.collectionCollection = collectionCollection;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollectionCollection1() {
        return collectionCollection1;
    }

    public void setCollectionCollection1(Collection<se.nrm.specify.datamodel.Collection> collectionCollection1) {
        this.collectionCollection1 = collectionCollection1;
    }

    @XmlTransient
    public Collection<Spversion> getSpversionCollection() {
        return spversionCollection;
    }

    public void setSpversionCollection(Collection<Spversion> spversionCollection) {
        this.spversionCollection = spversionCollection;
    }

    @XmlTransient
    public Collection<Spversion> getSpversionCollection1() {
        return spversionCollection1;
    }

    public void setSpversionCollection1(Collection<Spversion> spversionCollection1) {
        this.spversionCollection1 = spversionCollection1;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedef> getGeologictimeperiodtreedefCollection() {
        return geologictimeperiodtreedefCollection;
    }

    public void setGeologictimeperiodtreedefCollection(Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefCollection) {
        this.geologictimeperiodtreedefCollection = geologictimeperiodtreedefCollection;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedef> getGeologictimeperiodtreedefCollection1() {
        return geologictimeperiodtreedefCollection1;
    }

    public void setGeologictimeperiodtreedefCollection1(Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefCollection1) {
        this.geologictimeperiodtreedefCollection1 = geologictimeperiodtreedefCollection1;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptionCollection() {
        return conservdescriptionCollection;
    }

    public void setConservdescriptionCollection(Collection<Conservdescription> conservdescriptionCollection) {
        this.conservdescriptionCollection = conservdescriptionCollection;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptionCollection1() {
        return conservdescriptionCollection1;
    }

    public void setConservdescriptionCollection1(Collection<Conservdescription> conservdescriptionCollection1) {
        this.conservdescriptionCollection1 = conservdescriptionCollection1;
    }

    @XmlTransient
    public Collection<Addressofrecord> getAddressofrecordCollection() {
        return addressofrecordCollection;
    }

    public void setAddressofrecordCollection(Collection<Addressofrecord> addressofrecordCollection) {
        this.addressofrecordCollection = addressofrecordCollection;
    }

    @XmlTransient
    public Collection<Addressofrecord> getAddressofrecordCollection1() {
        return addressofrecordCollection1;
    }

    public void setAddressofrecordCollection1(Collection<Addressofrecord> addressofrecordCollection1) {
        this.addressofrecordCollection1 = addressofrecordCollection1;
    }

    @XmlTransient
    public Collection<Addressofrecord> getAddressofrecordCollection2() {
        return addressofrecordCollection2;
    }

    public void setAddressofrecordCollection2(Collection<Addressofrecord> addressofrecordCollection2) {
        this.addressofrecordCollection2 = addressofrecordCollection2;
    }

    @XmlTransient
    public Collection<Division> getDivisionCollection() {
        return divisionCollection;
    }

    public void setDivisionCollection(Collection<Division> divisionCollection) {
        this.divisionCollection = divisionCollection;
    }

    @XmlTransient
    public Collection<Division> getDivisionCollection1() {
        return divisionCollection1;
    }

    public void setDivisionCollection1(Collection<Division> divisionCollection1) {
        this.divisionCollection1 = divisionCollection1;
    }

    @XmlTransient
    public Collection<Storagetreedefitem> getStoragetreedefitemCollection() {
        return storagetreedefitemCollection;
    }

    public void setStoragetreedefitemCollection(Collection<Storagetreedefitem> storagetreedefitemCollection) {
        this.storagetreedefitemCollection = storagetreedefitemCollection;
    }

    @XmlTransient
    public Collection<Storagetreedefitem> getStoragetreedefitemCollection1() {
        return storagetreedefitemCollection1;
    }

    public void setStoragetreedefitemCollection1(Collection<Storagetreedefitem> storagetreedefitemCollection1) {
        this.storagetreedefitemCollection1 = storagetreedefitemCollection1;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeoutCollection() {
        return exchangeoutCollection;
    }

    public void setExchangeoutCollection(Collection<Exchangeout> exchangeoutCollection) {
        this.exchangeoutCollection = exchangeoutCollection;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeoutCollection1() {
        return exchangeoutCollection1;
    }

    public void setExchangeoutCollection1(Collection<Exchangeout> exchangeoutCollection1) {
        this.exchangeoutCollection1 = exchangeoutCollection1;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeoutCollection2() {
        return exchangeoutCollection2;
    }

    public void setExchangeoutCollection2(Collection<Exchangeout> exchangeoutCollection2) {
        this.exchangeoutCollection2 = exchangeoutCollection2;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeoutCollection3() {
        return exchangeoutCollection3;
    }

    public void setExchangeoutCollection3(Collection<Exchangeout> exchangeoutCollection3) {
        this.exchangeoutCollection3 = exchangeoutCollection3;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpprincipalCollection() {
        return spprincipalCollection;
    }

    public void setSpprincipalCollection(Collection<Spprincipal> spprincipalCollection) {
        this.spprincipalCollection = spprincipalCollection;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpprincipalCollection1() {
        return spprincipalCollection1;
    }

    public void setSpprincipalCollection1(Collection<Spprincipal> spprincipalCollection1) {
        this.spprincipalCollection1 = spprincipalCollection1;
    }

    @XmlTransient
    public Collection<Autonumberingscheme> getAutonumberingschemeCollection() {
        return autonumberingschemeCollection;
    }

    public void setAutonumberingschemeCollection(Collection<Autonumberingscheme> autonumberingschemeCollection) {
        this.autonumberingschemeCollection = autonumberingschemeCollection;
    }

    @XmlTransient
    public Collection<Autonumberingscheme> getAutonumberingschemeCollection1() {
        return autonumberingschemeCollection1;
    }

    public void setAutonumberingschemeCollection1(Collection<Autonumberingscheme> autonumberingschemeCollection1) {
        this.autonumberingschemeCollection1 = autonumberingschemeCollection1;
    }

    @XmlTransient
    public Collection<Determinationcitation> getDeterminationcitationCollection() {
        return determinationcitationCollection;
    }

    public void setDeterminationcitationCollection(Collection<Determinationcitation> determinationcitationCollection) {
        this.determinationcitationCollection = determinationcitationCollection;
    }

    @XmlTransient
    public Collection<Determinationcitation> getDeterminationcitationCollection1() {
        return determinationcitationCollection1;
    }

    public void setDeterminationcitationCollection1(Collection<Determinationcitation> determinationcitationCollection1) {
        this.determinationcitationCollection1 = determinationcitationCollection1;
    }

    @XmlTransient
    public Collection<Specifyuser> getSpecifyuserCollection() {
        return specifyuserCollection;
    }

    public void setSpecifyuserCollection(Collection<Specifyuser> specifyuserCollection) {
        this.specifyuserCollection = specifyuserCollection;
    }

    @XmlTransient
    public Collection<Specifyuser> getSpecifyuserCollection1() {
        return specifyuserCollection1;
    }

    public void setSpecifyuserCollection1(Collection<Specifyuser> specifyuserCollection1) {
        this.specifyuserCollection1 = specifyuserCollection1;
    }

    @XmlTransient
    public Collection<Preparationattachment> getPreparationattachmentCollection() {
        return preparationattachmentCollection;
    }

    public void setPreparationattachmentCollection(Collection<Preparationattachment> preparationattachmentCollection) {
        this.preparationattachmentCollection = preparationattachmentCollection;
    }

    @XmlTransient
    public Collection<Preparationattachment> getPreparationattachmentCollection1() {
        return preparationattachmentCollection1;
    }

    public void setPreparationattachmentCollection1(Collection<Preparationattachment> preparationattachmentCollection1) {
        this.preparationattachmentCollection1 = preparationattachmentCollection1;
    }

    @XmlTransient
    public Collection<Localitydetail> getLocalitydetailCollection() {
        return localitydetailCollection;
    }

    public void setLocalitydetailCollection(Collection<Localitydetail> localitydetailCollection) {
        this.localitydetailCollection = localitydetailCollection;
    }

    @XmlTransient
    public Collection<Localitydetail> getLocalitydetailCollection1() {
        return localitydetailCollection1;
    }

    public void setLocalitydetailCollection1(Collection<Localitydetail> localitydetailCollection1) {
        this.localitydetailCollection1 = localitydetailCollection1;
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
    public Collection<Collectionobjectattr> getCollectionobjectattrCollection1() {
        return collectionobjectattrCollection1;
    }

    public void setCollectionobjectattrCollection1(Collection<Collectionobjectattr> collectionobjectattrCollection1) {
        this.collectionobjectattrCollection1 = collectionobjectattrCollection1;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributeCollection() {
        return collectingeventattributeCollection;
    }

    public void setCollectingeventattributeCollection(Collection<Collectingeventattribute> collectingeventattributeCollection) {
        this.collectingeventattributeCollection = collectingeventattributeCollection;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributeCollection1() {
        return collectingeventattributeCollection1;
    }

    public void setCollectingeventattributeCollection1(Collection<Collectingeventattribute> collectingeventattributeCollection1) {
        this.collectingeventattributeCollection1 = collectingeventattributeCollection1;
    }

    @XmlTransient
    public Collection<Spviewsetobj> getSpviewsetobjCollection() {
        return spviewsetobjCollection;
    }

    public void setSpviewsetobjCollection(Collection<Spviewsetobj> spviewsetobjCollection) {
        this.spviewsetobjCollection = spviewsetobjCollection;
    }

    @XmlTransient
    public Collection<Spviewsetobj> getSpviewsetobjCollection1() {
        return spviewsetobjCollection1;
    }

    public void setSpviewsetobjCollection1(Collection<Spviewsetobj> spviewsetobjCollection1) {
        this.spviewsetobjCollection1 = spviewsetobjCollection1;
    }

    @XmlTransient
    public Collection<Spfieldvaluedefault> getSpfieldvaluedefaultCollection() {
        return spfieldvaluedefaultCollection;
    }

    public void setSpfieldvaluedefaultCollection(Collection<Spfieldvaluedefault> spfieldvaluedefaultCollection) {
        this.spfieldvaluedefaultCollection = spfieldvaluedefaultCollection;
    }

    @XmlTransient
    public Collection<Spfieldvaluedefault> getSpfieldvaluedefaultCollection1() {
        return spfieldvaluedefaultCollection1;
    }

    public void setSpfieldvaluedefaultCollection1(Collection<Spfieldvaluedefault> spfieldvaluedefaultCollection1) {
        this.spfieldvaluedefaultCollection1 = spfieldvaluedefaultCollection1;
    }

    @XmlTransient
    public Collection<Commonnametx> getCommonnametxCollection() {
        return commonnametxCollection;
    }

    public void setCommonnametxCollection(Collection<Commonnametx> commonnametxCollection) {
        this.commonnametxCollection = commonnametxCollection;
    }

    @XmlTransient
    public Collection<Commonnametx> getCommonnametxCollection1() {
        return commonnametxCollection1;
    }

    public void setCommonnametxCollection1(Collection<Commonnametx> commonnametxCollection1) {
        this.commonnametxCollection1 = commonnametxCollection1;
    }

    @XmlTransient
    public Collection<Borrowmaterial> getBorrowmaterialCollection() {
        return borrowmaterialCollection;
    }

    public void setBorrowmaterialCollection(Collection<Borrowmaterial> borrowmaterialCollection) {
        this.borrowmaterialCollection = borrowmaterialCollection;
    }

    @XmlTransient
    public Collection<Borrowmaterial> getBorrowmaterialCollection1() {
        return borrowmaterialCollection1;
    }

    public void setBorrowmaterialCollection1(Collection<Borrowmaterial> borrowmaterialCollection1) {
        this.borrowmaterialCollection1 = borrowmaterialCollection1;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagentCollection() {
        return accessionagentCollection;
    }

    public void setAccessionagentCollection(Collection<Accessionagent> accessionagentCollection) {
        this.accessionagentCollection = accessionagentCollection;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagentCollection1() {
        return accessionagentCollection1;
    }

    public void setAccessionagentCollection1(Collection<Accessionagent> accessionagentCollection1) {
        this.accessionagentCollection1 = accessionagentCollection1;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagentCollection2() {
        return accessionagentCollection2;
    }

    public void setAccessionagentCollection2(Collection<Accessionagent> accessionagentCollection2) {
        this.accessionagentCollection2 = accessionagentCollection2;
    }

    @XmlTransient
    public Collection<Spappresource> getSpappresourceCollection() {
        return spappresourceCollection;
    }

    public void setSpappresourceCollection(Collection<Spappresource> spappresourceCollection) {
        this.spappresourceCollection = spappresourceCollection;
    }

    @XmlTransient
    public Collection<Spappresource> getSpappresourceCollection1() {
        return spappresourceCollection1;
    }

    public void setSpappresourceCollection1(Collection<Spappresource> spappresourceCollection1) {
        this.spappresourceCollection1 = spappresourceCollection1;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getGeologictimeperiodCollection() {
        return geologictimeperiodCollection;
    }

    public void setGeologictimeperiodCollection(Collection<Geologictimeperiod> geologictimeperiodCollection) {
        this.geologictimeperiodCollection = geologictimeperiodCollection;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getGeologictimeperiodCollection1() {
        return geologictimeperiodCollection1;
    }

    public void setGeologictimeperiodCollection1(Collection<Geologictimeperiod> geologictimeperiodCollection1) {
        this.geologictimeperiodCollection1 = geologictimeperiodCollection1;
    }

    @XmlTransient
    public Collection<Workbenchtemplatemappingitem> getWorkbenchtemplatemappingitemCollection() {
        return workbenchtemplatemappingitemCollection;
    }

    public void setWorkbenchtemplatemappingitemCollection(Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitemCollection) {
        this.workbenchtemplatemappingitemCollection = workbenchtemplatemappingitemCollection;
    }

    @XmlTransient
    public Collection<Workbenchtemplatemappingitem> getWorkbenchtemplatemappingitemCollection1() {
        return workbenchtemplatemappingitemCollection1;
    }

    public void setWorkbenchtemplatemappingitemCollection1(Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitemCollection1) {
        this.workbenchtemplatemappingitemCollection1 = workbenchtemplatemappingitemCollection1;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreementCollection() {
        return repositoryagreementCollection;
    }

    public void setRepositoryagreementCollection(Collection<Repositoryagreement> repositoryagreementCollection) {
        this.repositoryagreementCollection = repositoryagreementCollection;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreementCollection1() {
        return repositoryagreementCollection1;
    }

    public void setRepositoryagreementCollection1(Collection<Repositoryagreement> repositoryagreementCollection1) {
        this.repositoryagreementCollection1 = repositoryagreementCollection1;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreementCollection2() {
        return repositoryagreementCollection2;
    }

    public void setRepositoryagreementCollection2(Collection<Repositoryagreement> repositoryagreementCollection2) {
        this.repositoryagreementCollection2 = repositoryagreementCollection2;
    }

    @XmlTransient
    public Collection<Spqueryfield> getSpqueryfieldCollection() {
        return spqueryfieldCollection;
    }

    public void setSpqueryfieldCollection(Collection<Spqueryfield> spqueryfieldCollection) {
        this.spqueryfieldCollection = spqueryfieldCollection;
    }

    @XmlTransient
    public Collection<Spqueryfield> getSpqueryfieldCollection1() {
        return spqueryfieldCollection1;
    }

    public void setSpqueryfieldCollection1(Collection<Spqueryfield> spqueryfieldCollection1) {
        this.spqueryfieldCollection1 = spqueryfieldCollection1;
    }

    @XmlTransient
    public Collection<Spreport> getSpreportCollection() {
        return spreportCollection;
    }

    public void setSpreportCollection(Collection<Spreport> spreportCollection) {
        this.spreportCollection = spreportCollection;
    }

    @XmlTransient
    public Collection<Spreport> getSpreportCollection1() {
        return spreportCollection1;
    }

    public void setSpreportCollection1(Collection<Spreport> spreportCollection1) {
        this.spreportCollection1 = spreportCollection1;
    }

    @XmlTransient
    public Collection<Referencework> getReferenceworkCollection() {
        return referenceworkCollection;
    }

    public void setReferenceworkCollection(Collection<Referencework> referenceworkCollection) {
        this.referenceworkCollection = referenceworkCollection;
    }

    @XmlTransient
    public Collection<Referencework> getReferenceworkCollection1() {
        return referenceworkCollection1;
    }

    public void setReferenceworkCollection1(Collection<Referencework> referenceworkCollection1) {
        this.referenceworkCollection1 = referenceworkCollection1;
    }

    @XmlTransient
    public Collection<Localityattachment> getLocalityattachmentCollection() {
        return localityattachmentCollection;
    }

    public void setLocalityattachmentCollection(Collection<Localityattachment> localityattachmentCollection) {
        this.localityattachmentCollection = localityattachmentCollection;
    }

    @XmlTransient
    public Collection<Localityattachment> getLocalityattachmentCollection1() {
        return localityattachmentCollection1;
    }

    public void setLocalityattachmentCollection1(Collection<Localityattachment> localityattachmentCollection1) {
        this.localityattachmentCollection1 = localityattachmentCollection1;
    }

    @XmlTransient
    public Collection<Accession> getAccessionCollection() {
        return accessionCollection;
    }

    public void setAccessionCollection(Collection<Accession> accessionCollection) {
        this.accessionCollection = accessionCollection;
    }

    @XmlTransient
    public Collection<Accession> getAccessionCollection1() {
        return accessionCollection1;
    }

    public void setAccessionCollection1(Collection<Accession> accessionCollection1) {
        this.accessionCollection1 = accessionCollection1;
    }

    @XmlTransient
    public Collection<Groupperson> getGrouppersonCollection() {
        return grouppersonCollection;
    }

    public void setGrouppersonCollection(Collection<Groupperson> grouppersonCollection) {
        this.grouppersonCollection = grouppersonCollection;
    }

    @XmlTransient
    public Collection<Groupperson> getGrouppersonCollection1() {
        return grouppersonCollection1;
    }

    public void setGrouppersonCollection1(Collection<Groupperson> grouppersonCollection1) {
        this.grouppersonCollection1 = grouppersonCollection1;
    }

    @XmlTransient
    public Collection<Groupperson> getGrouppersonCollection2() {
        return grouppersonCollection2;
    }

    public void setGrouppersonCollection2(Collection<Groupperson> grouppersonCollection2) {
        this.grouppersonCollection2 = grouppersonCollection2;
    }

    @XmlTransient
    public Collection<Groupperson> getGrouppersonCollection3() {
        return grouppersonCollection3;
    }

    public void setGrouppersonCollection3(Collection<Groupperson> grouppersonCollection3) {
        this.grouppersonCollection3 = grouppersonCollection3;
    }

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterialCollection() {
        return borrowreturnmaterialCollection;
    }

    public void setBorrowreturnmaterialCollection(Collection<Borrowreturnmaterial> borrowreturnmaterialCollection) {
        this.borrowreturnmaterialCollection = borrowreturnmaterialCollection;
    }

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterialCollection1() {
        return borrowreturnmaterialCollection1;
    }

    public void setBorrowreturnmaterialCollection1(Collection<Borrowreturnmaterial> borrowreturnmaterialCollection1) {
        this.borrowreturnmaterialCollection1 = borrowreturnmaterialCollection1;
    }

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterialCollection2() {
        return borrowreturnmaterialCollection2;
    }

    public void setBorrowreturnmaterialCollection2(Collection<Borrowreturnmaterial> borrowreturnmaterialCollection2) {
        this.borrowreturnmaterialCollection2 = borrowreturnmaterialCollection2;
    }

    @XmlTransient
    public Collection<Preparationattr> getPreparationattrCollection() {
        return preparationattrCollection;
    }

    public void setPreparationattrCollection(Collection<Preparationattr> preparationattrCollection) {
        this.preparationattrCollection = preparationattrCollection;
    }

    @XmlTransient
    public Collection<Preparationattr> getPreparationattrCollection1() {
        return preparationattrCollection1;
    }

    public void setPreparationattrCollection1(Collection<Preparationattr> preparationattrCollection1) {
        this.preparationattrCollection1 = preparationattrCollection1;
    }

    @XmlTransient
    public Collection<Datatype> getDatatypeCollection() {
        return datatypeCollection;
    }

    public void setDatatypeCollection(Collection<Datatype> datatypeCollection) {
        this.datatypeCollection = datatypeCollection;
    }

    @XmlTransient
    public Collection<Datatype> getDatatypeCollection1() {
        return datatypeCollection1;
    }

    public void setDatatypeCollection1(Collection<Datatype> datatypeCollection1) {
        this.datatypeCollection1 = datatypeCollection1;
    }

    @XmlTransient
    public Collection<Storagetreedef> getStoragetreedefCollection() {
        return storagetreedefCollection;
    }

    public void setStoragetreedefCollection(Collection<Storagetreedef> storagetreedefCollection) {
        this.storagetreedefCollection = storagetreedefCollection;
    }

    @XmlTransient
    public Collection<Storagetreedef> getStoragetreedefCollection1() {
        return storagetreedefCollection1;
    }

    public void setStoragetreedefCollection1(Collection<Storagetreedef> storagetreedefCollection1) {
        this.storagetreedefCollection1 = storagetreedefCollection1;
    }

    @XmlTransient
    public Collection<Permit> getPermitCollection() {
        return permitCollection;
    }

    public void setPermitCollection(Collection<Permit> permitCollection) {
        this.permitCollection = permitCollection;
    }

    @XmlTransient
    public Collection<Permit> getPermitCollection1() {
        return permitCollection1;
    }

    public void setPermitCollection1(Collection<Permit> permitCollection1) {
        this.permitCollection1 = permitCollection1;
    }

    @XmlTransient
    public Collection<Permit> getPermitCollection2() {
        return permitCollection2;
    }

    public void setPermitCollection2(Collection<Permit> permitCollection2) {
        this.permitCollection2 = permitCollection2;
    }

    @XmlTransient
    public Collection<Permit> getPermitCollection3() {
        return permitCollection3;
    }

    public void setPermitCollection3(Collection<Permit> permitCollection3) {
        this.permitCollection3 = permitCollection3;
    }

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitationCollection() {
        return commonnametxcitationCollection;
    }

    public void setCommonnametxcitationCollection(Collection<Commonnametxcitation> commonnametxcitationCollection) {
        this.commonnametxcitationCollection = commonnametxcitationCollection;
    }

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitationCollection1() {
        return commonnametxcitationCollection1;
    }

    public void setCommonnametxcitationCollection1(Collection<Commonnametxcitation> commonnametxcitationCollection1) {
        this.commonnametxcitationCollection1 = commonnametxcitationCollection1;
    }

    @XmlTransient
    public Collection<Determination> getDeterminationCollection() {
        return determinationCollection;
    }

    public void setDeterminationCollection(Collection<Determination> determinationCollection) {
        this.determinationCollection = determinationCollection;
    }

    @XmlTransient
    public Collection<Determination> getDeterminationCollection1() {
        return determinationCollection1;
    }

    public void setDeterminationCollection1(Collection<Determination> determinationCollection1) {
        this.determinationCollection1 = determinationCollection1;
    }

    @XmlTransient
    public Collection<Determination> getDeterminationCollection2() {
        return determinationCollection2;
    }

    public void setDeterminationCollection2(Collection<Determination> determinationCollection2) {
        this.determinationCollection2 = determinationCollection2;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpexportschemaCollection() {
        return spexportschemaCollection;
    }

    public void setSpexportschemaCollection(Collection<Spexportschema> spexportschemaCollection) {
        this.spexportschemaCollection = spexportschemaCollection;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpexportschemaCollection1() {
        return spexportschemaCollection1;
    }

    public void setSpexportschemaCollection1(Collection<Spexportschema> spexportschemaCollection1) {
        this.spexportschemaCollection1 = spexportschemaCollection1;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingeventCollection() {
        return collectingeventCollection;
    }

    public void setCollectingeventCollection(Collection<Collectingevent> collectingeventCollection) {
        this.collectingeventCollection = collectingeventCollection;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingeventCollection1() {
        return collectingeventCollection1;
    }

    public void setCollectingeventCollection1(Collection<Collectingevent> collectingeventCollection1) {
        this.collectingeventCollection1 = collectingeventCollection1;
    }

    @XmlTransient
    public Collection<Collectionreltype> getCollectionreltypeCollection() {
        return collectionreltypeCollection;
    }

    public void setCollectionreltypeCollection(Collection<Collectionreltype> collectionreltypeCollection) {
        this.collectionreltypeCollection = collectionreltypeCollection;
    }

    @XmlTransient
    public Collection<Collectionreltype> getCollectionreltypeCollection1() {
        return collectionreltypeCollection1;
    }

    public void setCollectionreltypeCollection1(Collection<Collectionreltype> collectionreltypeCollection1) {
        this.collectionreltypeCollection1 = collectionreltypeCollection1;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappingCollection() {
        return spexportschemaitemmappingCollection;
    }

    public void setSpexportschemaitemmappingCollection(Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection) {
        this.spexportschemaitemmappingCollection = spexportschemaitemmappingCollection;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappingCollection1() {
        return spexportschemaitemmappingCollection1;
    }

    public void setSpexportschemaitemmappingCollection1(Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection1) {
        this.spexportschemaitemmappingCollection1 = spexportschemaitemmappingCollection1;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequenceCollection() {
        return dnasequenceCollection;
    }

    public void setDnasequenceCollection(Collection<Dnasequence> dnasequenceCollection) {
        this.dnasequenceCollection = dnasequenceCollection;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequenceCollection1() {
        return dnasequenceCollection1;
    }

    public void setDnasequenceCollection1(Collection<Dnasequence> dnasequenceCollection1) {
        this.dnasequenceCollection1 = dnasequenceCollection1;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequenceCollection2() {
        return dnasequenceCollection2;
    }

    public void setDnasequenceCollection2(Collection<Dnasequence> dnasequenceCollection2) {
        this.dnasequenceCollection2 = dnasequenceCollection2;
    }

    @XmlTransient
    public Collection<Spauditlogfield> getSpauditlogfieldCollection() {
        return spauditlogfieldCollection;
    }

    public void setSpauditlogfieldCollection(Collection<Spauditlogfield> spauditlogfieldCollection) {
        this.spauditlogfieldCollection = spauditlogfieldCollection;
    }

    @XmlTransient
    public Collection<Spauditlogfield> getSpauditlogfieldCollection1() {
        return spauditlogfieldCollection1;
    }

    public void setSpauditlogfieldCollection1(Collection<Spauditlogfield> spauditlogfieldCollection1) {
        this.spauditlogfieldCollection1 = spauditlogfieldCollection1;
    }

    @XmlTransient
    public Collection<Geographytreedef> getGeographytreedefCollection() {
        return geographytreedefCollection;
    }

    public void setGeographytreedefCollection(Collection<Geographytreedef> geographytreedefCollection) {
        this.geographytreedefCollection = geographytreedefCollection;
    }

    @XmlTransient
    public Collection<Geographytreedef> getGeographytreedefCollection1() {
        return geographytreedefCollection1;
    }

    public void setGeographytreedefCollection1(Collection<Geographytreedef> geographytreedefCollection1) {
        this.geographytreedefCollection1 = geographytreedefCollection1;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionobjectattachmentCollection() {
        return collectionobjectattachmentCollection;
    }

    public void setCollectionobjectattachmentCollection(Collection<Collectionobjectattachment> collectionobjectattachmentCollection) {
        this.collectionobjectattachmentCollection = collectionobjectattachmentCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionobjectattachmentCollection1() {
        return collectionobjectattachmentCollection1;
    }

    public void setCollectionobjectattachmentCollection1(Collection<Collectionobjectattachment> collectionobjectattachmentCollection1) {
        this.collectionobjectattachmentCollection1 = collectionobjectattachmentCollection1;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxoncitationCollection() {
        return taxoncitationCollection;
    }

    public void setTaxoncitationCollection(Collection<Taxoncitation> taxoncitationCollection) {
        this.taxoncitationCollection = taxoncitationCollection;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxoncitationCollection1() {
        return taxoncitationCollection1;
    }

    public void setTaxoncitationCollection1(Collection<Taxoncitation> taxoncitationCollection1) {
        this.taxoncitationCollection1 = taxoncitationCollection1;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getDnasequencingruncitationCollection() {
        return dnasequencingruncitationCollection;
    }

    public void setDnasequencingruncitationCollection(Collection<Dnasequencingruncitation> dnasequencingruncitationCollection) {
        this.dnasequencingruncitationCollection = dnasequencingruncitationCollection;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getDnasequencingruncitationCollection1() {
        return dnasequencingruncitationCollection1;
    }

    public void setDnasequencingruncitationCollection1(Collection<Dnasequencingruncitation> dnasequencingruncitationCollection1) {
        this.dnasequencingruncitationCollection1 = dnasequencingruncitationCollection1;
    }

    @XmlTransient
    public Collection<Inforequest> getInforequestCollection() {
        return inforequestCollection;
    }

    public void setInforequestCollection(Collection<Inforequest> inforequestCollection) {
        this.inforequestCollection = inforequestCollection;
    }

    @XmlTransient
    public Collection<Inforequest> getInforequestCollection1() {
        return inforequestCollection1;
    }

    public void setInforequestCollection1(Collection<Inforequest> inforequestCollection1) {
        this.inforequestCollection1 = inforequestCollection1;
    }

    @XmlTransient
    public Collection<Inforequest> getInforequestCollection2() {
        return inforequestCollection2;
    }

    public void setInforequestCollection2(Collection<Inforequest> inforequestCollection2) {
        this.inforequestCollection2 = inforequestCollection2;
    }

    @XmlTransient
    public Collection<Lithostrat> getLithostratCollection() {
        return lithostratCollection;
    }

    public void setLithostratCollection(Collection<Lithostrat> lithostratCollection) {
        this.lithostratCollection = lithostratCollection;
    }

    @XmlTransient
    public Collection<Lithostrat> getLithostratCollection1() {
        return lithostratCollection1;
    }

    public void setLithostratCollection1(Collection<Lithostrat> lithostratCollection1) {
        this.lithostratCollection1 = lithostratCollection1;
    }

    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
    }

    public Institution getInstitutionTCID() {
        return institutionTCID;
    }

    public void setInstitutionTCID(Institution institutionTCID) {
        this.institutionTCID = institutionTCID;
    }

    public Institution getInstitutionCCID() {
        return institutionCCID;
    }

    public void setInstitutionCCID(Institution institutionCCID) {
        this.institutionCCID = institutionCCID;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection() {
        return agentCollection;
    }

    public void setAgentCollection(Collection<Agent> agentCollection) {
        this.agentCollection = agentCollection;
    }

    @XmlTransient
    public Agent getParentOrganizationID() {
        return parentOrganizationID;
    }

    public void setParentOrganizationID(Agent parentOrganizationID) {
        this.parentOrganizationID = parentOrganizationID;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection1() {
        return agentCollection1;
    }

    public void setAgentCollection1(Collection<Agent> agentCollection1) {
        this.agentCollection1 = agentCollection1;
    }

    @XmlTransient
    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection2() {
        return agentCollection2;
    }

    public void setAgentCollection2(Collection<Agent> agentCollection2) {
        this.agentCollection2 = agentCollection2;
    }

    @XmlTransient
    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Specifyuser getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(Specifyuser specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    public se.nrm.specify.datamodel.Collection getCollectionTCID() {
        return collectionTCID;
    }

    public void setCollectionTCID(se.nrm.specify.datamodel.Collection collectionTCID) {
        this.collectionTCID = collectionTCID;
    }

    public se.nrm.specify.datamodel.Collection getCollectionCCID() {
        return collectionCCID;
    }

    public void setCollectionCCID(se.nrm.specify.datamodel.Collection collectionCCID) {
        this.collectionCCID = collectionCCID;
    }

    @XmlTransient
    public Collection<Borrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<Borrow> borrowCollection) {
        this.borrowCollection = borrowCollection;
    }

    @XmlTransient
    public Collection<Borrow> getBorrowCollection1() {
        return borrowCollection1;
    }

    public void setBorrowCollection1(Collection<Borrow> borrowCollection1) {
        this.borrowCollection1 = borrowCollection1;
    }

    @XmlTransient
    public Collection<Spexportschemaitem> getSpexportschemaitemCollection() {
        return spexportschemaitemCollection;
    }

    public void setSpexportschemaitemCollection(Collection<Spexportschemaitem> spexportschemaitemCollection) {
        this.spexportschemaitemCollection = spexportschemaitemCollection;
    }

    @XmlTransient
    public Collection<Spexportschemaitem> getSpexportschemaitemCollection1() {
        return spexportschemaitemCollection1;
    }

    public void setSpexportschemaitemCollection1(Collection<Spexportschemaitem> spexportschemaitemCollection1) {
        this.spexportschemaitemCollection1 = spexportschemaitemCollection1;
    }

    @XmlTransient
    public Collection<Attributedef> getAttributedefCollection() {
        return attributedefCollection;
    }

    public void setAttributedefCollection(Collection<Attributedef> attributedefCollection) {
        this.attributedefCollection = attributedefCollection;
    }

    @XmlTransient
    public Collection<Attributedef> getAttributedefCollection1() {
        return attributedefCollection1;
    }

    public void setAttributedefCollection1(Collection<Attributedef> attributedefCollection1) {
        this.attributedefCollection1 = attributedefCollection1;
    }

    @XmlTransient
    public Collection<Exsiccata> getExsiccataCollection() {
        return exsiccataCollection;
    }

    public void setExsiccataCollection(Collection<Exsiccata> exsiccataCollection) {
        this.exsiccataCollection = exsiccataCollection;
    }

    @XmlTransient
    public Collection<Exsiccata> getExsiccataCollection1() {
        return exsiccataCollection1;
    }

    public void setExsiccataCollection1(Collection<Exsiccata> exsiccataCollection1) {
        this.exsiccataCollection1 = exsiccataCollection1;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTaxontreedefitemCollection() {
        return taxontreedefitemCollection;
    }

    public void setTaxontreedefitemCollection(Collection<Taxontreedefitem> taxontreedefitemCollection) {
        this.taxontreedefitemCollection = taxontreedefitemCollection;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTaxontreedefitemCollection1() {
        return taxontreedefitemCollection1;
    }

    public void setTaxontreedefitemCollection1(Collection<Taxontreedefitem> taxontreedefitemCollection1) {
        this.taxontreedefitemCollection1 = taxontreedefitemCollection1;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonattachmentCollection() {
        return taxonattachmentCollection;
    }

    public void setTaxonattachmentCollection(Collection<Taxonattachment> taxonattachmentCollection) {
        this.taxonattachmentCollection = taxonattachmentCollection;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonattachmentCollection1() {
        return taxonattachmentCollection1;
    }

    public void setTaxonattachmentCollection1(Collection<Taxonattachment> taxonattachmentCollection1) {
        this.taxonattachmentCollection1 = taxonattachmentCollection1;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparationCollection() {
        return loanreturnpreparationCollection;
    }

    public void setLoanreturnpreparationCollection(Collection<Loanreturnpreparation> loanreturnpreparationCollection) {
        this.loanreturnpreparationCollection = loanreturnpreparationCollection;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparationCollection1() {
        return loanreturnpreparationCollection1;
    }

    public void setLoanreturnpreparationCollection1(Collection<Loanreturnpreparation> loanreturnpreparationCollection1) {
        this.loanreturnpreparationCollection1 = loanreturnpreparationCollection1;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparationCollection2() {
        return loanreturnpreparationCollection2;
    }

    public void setLoanreturnpreparationCollection2(Collection<Loanreturnpreparation> loanreturnpreparationCollection2) {
        this.loanreturnpreparationCollection2 = loanreturnpreparationCollection2;
    }

    @XmlTransient
    public Collection<Spexportschemamapping> getSpexportschemamappingCollection() {
        return spexportschemamappingCollection;
    }

    public void setSpexportschemamappingCollection(Collection<Spexportschemamapping> spexportschemamappingCollection) {
        this.spexportschemamappingCollection = spexportschemamappingCollection;
    }

    @XmlTransient
    public Collection<Spexportschemamapping> getSpexportschemamappingCollection1() {
        return spexportschemamappingCollection1;
    }

    public void setSpexportschemamappingCollection1(Collection<Spexportschemamapping> spexportschemamappingCollection1) {
        this.spexportschemamappingCollection1 = spexportschemamappingCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldnotebookpageattachmentCollection() {
        return fieldnotebookpageattachmentCollection;
    }

    public void setFieldnotebookpageattachmentCollection(Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection) {
        this.fieldnotebookpageattachmentCollection = fieldnotebookpageattachmentCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldnotebookpageattachmentCollection1() {
        return fieldnotebookpageattachmentCollection1;
    }

    public void setFieldnotebookpageattachmentCollection1(Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection1) {
        this.fieldnotebookpageattachmentCollection1 = fieldnotebookpageattachmentCollection1;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftpreparationCollection() {
        return giftpreparationCollection;
    }

    public void setGiftpreparationCollection(Collection<Giftpreparation> giftpreparationCollection) {
        this.giftpreparationCollection = giftpreparationCollection;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftpreparationCollection1() {
        return giftpreparationCollection1;
    }

    public void setGiftpreparationCollection1(Collection<Giftpreparation> giftpreparationCollection1) {
        this.giftpreparationCollection1 = giftpreparationCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldnotebookattachmentCollection() {
        return fieldnotebookattachmentCollection;
    }

    public void setFieldnotebookattachmentCollection(Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection) {
        this.fieldnotebookattachmentCollection = fieldnotebookattachmentCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldnotebookattachmentCollection1() {
        return fieldnotebookattachmentCollection1;
    }

    public void setFieldnotebookattachmentCollection1(Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection1) {
        this.fieldnotebookattachmentCollection1 = fieldnotebookattachmentCollection1;
    }

    @XmlTransient
    public Collection<Geographytreedefitem> getGeographytreedefitemCollection() {
        return geographytreedefitemCollection;
    }

    public void setGeographytreedefitemCollection(Collection<Geographytreedefitem> geographytreedefitemCollection) {
        this.geographytreedefitemCollection = geographytreedefitemCollection;
    }

    @XmlTransient
    public Collection<Geographytreedefitem> getGeographytreedefitemCollection1() {
        return geographytreedefitemCollection1;
    }

    public void setGeographytreedefitemCollection1(Collection<Geographytreedefitem> geographytreedefitemCollection1) {
        this.geographytreedefitemCollection1 = geographytreedefitemCollection1;
    }

    @XmlTransient
    public Collection<Splocalecontainer> getSplocalecontainerCollection() {
        return splocalecontainerCollection;
    }

    public void setSplocalecontainerCollection(Collection<Splocalecontainer> splocalecontainerCollection) {
        this.splocalecontainerCollection = splocalecontainerCollection;
    }

    @XmlTransient
    public Collection<Splocalecontainer> getSplocalecontainerCollection1() {
        return splocalecontainerCollection1;
    }

    public void setSplocalecontainerCollection1(Collection<Splocalecontainer> splocalecontainerCollection1) {
        this.splocalecontainerCollection1 = splocalecontainerCollection1;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection1() {
        return collectionobjectCollection1;
    }

    public void setCollectionobjectCollection1(Collection<Collectionobject> collectionobjectCollection1) {
        this.collectionobjectCollection1 = collectionobjectCollection1;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection2() {
        return collectionobjectCollection2;
    }

    public void setCollectionobjectCollection2(Collection<Collectionobject> collectionobjectCollection2) {
        this.collectionobjectCollection2 = collectionobjectCollection2;
    }

    @XmlTransient
    public Collection<Borrowagent> getBorrowagentCollection() {
        return borrowagentCollection;
    }

    public void setBorrowagentCollection(Collection<Borrowagent> borrowagentCollection) {
        this.borrowagentCollection = borrowagentCollection;
    }

    @XmlTransient
    public Collection<Borrowagent> getBorrowagentCollection1() {
        return borrowagentCollection1;
    }

    public void setBorrowagentCollection1(Collection<Borrowagent> borrowagentCollection1) {
        this.borrowagentCollection1 = borrowagentCollection1;
    }

    @XmlTransient
    public Collection<Borrowagent> getBorrowagentCollection2() {
        return borrowagentCollection2;
    }

    public void setBorrowagentCollection2(Collection<Borrowagent> borrowagentCollection2) {
        this.borrowagentCollection2 = borrowagentCollection2;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConserveventattachmentCollection() {
        return conserveventattachmentCollection;
    }

    public void setConserveventattachmentCollection(Collection<Conserveventattachment> conserveventattachmentCollection) {
        this.conserveventattachmentCollection = conserveventattachmentCollection;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConserveventattachmentCollection1() {
        return conserveventattachmentCollection1;
    }

    public void setConserveventattachmentCollection1(Collection<Conserveventattachment> conserveventattachmentCollection1) {
        this.conserveventattachmentCollection1 = conserveventattachmentCollection1;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionobjectcitationCollection() {
        return collectionobjectcitationCollection;
    }

    public void setCollectionobjectcitationCollection(Collection<Collectionobjectcitation> collectionobjectcitationCollection) {
        this.collectionobjectcitationCollection = collectionobjectcitationCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionobjectcitationCollection1() {
        return collectionobjectcitationCollection1;
    }

    public void setCollectionobjectcitationCollection1(Collection<Collectionobjectcitation> collectionobjectcitationCollection1) {
        this.collectionobjectcitationCollection1 = collectionobjectcitationCollection1;
    }

    @XmlTransient
    public Collection<Deaccession> getDeaccessionCollection() {
        return deaccessionCollection;
    }

    public void setDeaccessionCollection(Collection<Deaccession> deaccessionCollection) {
        this.deaccessionCollection = deaccessionCollection;
    }

    @XmlTransient
    public Collection<Deaccession> getDeaccessionCollection1() {
        return deaccessionCollection1;
    }

    public void setDeaccessionCollection1(Collection<Deaccession> deaccessionCollection1) {
        this.deaccessionCollection1 = deaccessionCollection1;
    }

    @XmlTransient
    public Collection<Journal> getJournalCollection() {
        return journalCollection;
    }

    public void setJournalCollection(Collection<Journal> journalCollection) {
        this.journalCollection = journalCollection;
    }

    @XmlTransient
    public Collection<Journal> getJournalCollection1() {
        return journalCollection1;
    }

    public void setJournalCollection1(Collection<Journal> journalCollection1) {
        this.journalCollection1 = journalCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpageCollection() {
        return fieldnotebookpageCollection;
    }

    public void setFieldnotebookpageCollection(Collection<Fieldnotebookpage> fieldnotebookpageCollection) {
        this.fieldnotebookpageCollection = fieldnotebookpageCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpageCollection1() {
        return fieldnotebookpageCollection1;
    }

    public void setFieldnotebookpageCollection1(Collection<Fieldnotebookpage> fieldnotebookpageCollection1) {
        this.fieldnotebookpageCollection1 = fieldnotebookpageCollection1;
    }

    @XmlTransient
    public Collection<Author> getAuthorCollection() {
        return authorCollection;
    }

    public void setAuthorCollection(Collection<Author> authorCollection) {
        this.authorCollection = authorCollection;
    }

    @XmlTransient
    public Collection<Author> getAuthorCollection1() {
        return authorCollection1;
    }

    public void setAuthorCollection1(Collection<Author> authorCollection1) {
        this.authorCollection1 = authorCollection1;
    }

    @XmlTransient
    public Collection<Author> getAuthorCollection2() {
        return authorCollection2;
    }

    public void setAuthorCollection2(Collection<Author> authorCollection2) {
        this.authorCollection2 = authorCollection2;
    }

    @XmlTransient
    public Collection<Collector> getCollectorCollection() {
        return collectorCollection;
    }

    public void setCollectorCollection(Collection<Collector> collectorCollection) {
        this.collectorCollection = collectorCollection;
    }

    @XmlTransient
    public Collection<Collector> getCollectorCollection1() {
        return collectorCollection1;
    }

    public void setCollectorCollection1(Collection<Collector> collectorCollection1) {
        this.collectorCollection1 = collectorCollection1;
    }

    @XmlTransient
    public Collection<Collector> getCollectorCollection2() {
        return collectorCollection2;
    }

    public void setCollectorCollection2(Collection<Collector> collectorCollection2) {
        this.collectorCollection2 = collectorCollection2;
    }

    @XmlTransient
    public Collection<Geography> getGeographyCollection() {
        return geographyCollection;
    }

    public void setGeographyCollection(Collection<Geography> geographyCollection) {
        this.geographyCollection = geographyCollection;
    }

    @XmlTransient
    public Collection<Geography> getGeographyCollection1() {
        return geographyCollection1;
    }

    public void setGeographyCollection1(Collection<Geography> geographyCollection1) {
        this.geographyCollection1 = geographyCollection1;
    }

    @XmlTransient
    public Collection<Accessionattachment> getAccessionattachmentCollection() {
        return accessionattachmentCollection;
    }

    public void setAccessionattachmentCollection(Collection<Accessionattachment> accessionattachmentCollection) {
        this.accessionattachmentCollection = accessionattachmentCollection;
    }

    @XmlTransient
    public Collection<Accessionattachment> getAccessionattachmentCollection1() {
        return accessionattachmentCollection1;
    }

    public void setAccessionattachmentCollection1(Collection<Accessionattachment> accessionattachmentCollection1) {
        this.accessionattachmentCollection1 = accessionattachmentCollection1;
    }

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataitemCollection() {
        return exsiccataitemCollection;
    }

    public void setExsiccataitemCollection(Collection<Exsiccataitem> exsiccataitemCollection) {
        this.exsiccataitemCollection = exsiccataitemCollection;
    }

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataitemCollection1() {
        return exsiccataitemCollection1;
    }

    public void setExsiccataitemCollection1(Collection<Exsiccataitem> exsiccataitemCollection1) {
        this.exsiccataitemCollection1 = exsiccataitemCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebookCollection() {
        return fieldnotebookCollection;
    }

    public void setFieldnotebookCollection(Collection<Fieldnotebook> fieldnotebookCollection) {
        this.fieldnotebookCollection = fieldnotebookCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebookCollection1() {
        return fieldnotebookCollection1;
    }

    public void setFieldnotebookCollection1(Collection<Fieldnotebook> fieldnotebookCollection1) {
        this.fieldnotebookCollection1 = fieldnotebookCollection1;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebookCollection2() {
        return fieldnotebookCollection2;
    }

    public void setFieldnotebookCollection2(Collection<Fieldnotebook> fieldnotebookCollection2) {
        this.fieldnotebookCollection2 = fieldnotebookCollection2;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplineCollection() {
        return disciplineCollection;
    }

    public void setDisciplineCollection(Collection<Discipline> disciplineCollection) {
        this.disciplineCollection = disciplineCollection;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplineCollection1() {
        return disciplineCollection1;
    }

    public void setDisciplineCollection1(Collection<Discipline> disciplineCollection1) {
        this.disciplineCollection1 = disciplineCollection1;
    }

    @XmlTransient
    public Collection<Institution> getInstitutionCollection() {
        return institutionCollection;
    }

    public void setInstitutionCollection(Collection<Institution> institutionCollection) {
        this.institutionCollection = institutionCollection;
    }

    @XmlTransient
    public Collection<Institution> getInstitutionCollection1() {
        return institutionCollection1;
    }

    public void setInstitutionCollection1(Collection<Institution> institutionCollection1) {
        this.institutionCollection1 = institutionCollection1;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservdescriptionattachmentCollection() {
        return conservdescriptionattachmentCollection;
    }

    public void setConservdescriptionattachmentCollection(Collection<Conservdescriptionattachment> conservdescriptionattachmentCollection) {
        this.conservdescriptionattachmentCollection = conservdescriptionattachmentCollection;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservdescriptionattachmentCollection1() {
        return conservdescriptionattachmentCollection1;
    }

    public void setConservdescriptionattachmentCollection1(Collection<Conservdescriptionattachment> conservdescriptionattachmentCollection1) {
        this.conservdescriptionattachmentCollection1 = conservdescriptionattachmentCollection1;
    }

    @XmlTransient
    public Collection<Geocoorddetail> getGeocoorddetailCollection() {
        return geocoorddetailCollection;
    }

    public void setGeocoorddetailCollection(Collection<Geocoorddetail> geocoorddetailCollection) {
        this.geocoorddetailCollection = geocoorddetailCollection;
    }

    @XmlTransient
    public Collection<Geocoorddetail> getGeocoorddetailCollection1() {
        return geocoorddetailCollection1;
    }

    public void setGeocoorddetailCollection1(Collection<Geocoorddetail> geocoorddetailCollection1) {
        this.geocoorddetailCollection1 = geocoorddetailCollection1;
    }

    @XmlTransient
    public Collection<Geocoorddetail> getGeocoorddetailCollection2() {
        return geocoorddetailCollection2;
    }

    public void setGeocoorddetailCollection2(Collection<Geocoorddetail> geocoorddetailCollection2) {
        this.geocoorddetailCollection2 = geocoorddetailCollection2;
    }

    @XmlTransient
    public Collection<Deaccessionpreparation> getDeaccessionpreparationCollection() {
        return deaccessionpreparationCollection;
    }

    public void setDeaccessionpreparationCollection(Collection<Deaccessionpreparation> deaccessionpreparationCollection) {
        this.deaccessionpreparationCollection = deaccessionpreparationCollection;
    }

    @XmlTransient
    public Collection<Deaccessionpreparation> getDeaccessionpreparationCollection1() {
        return deaccessionpreparationCollection1;
    }

    public void setDeaccessionpreparationCollection1(Collection<Deaccessionpreparation> deaccessionpreparationCollection1) {
        this.deaccessionpreparationCollection1 = deaccessionpreparationCollection1;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingeventattachmentCollection() {
        return collectingeventattachmentCollection;
    }

    public void setCollectingeventattachmentCollection(Collection<Collectingeventattachment> collectingeventattachmentCollection) {
        this.collectingeventattachmentCollection = collectingeventattachmentCollection;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingeventattachmentCollection1() {
        return collectingeventattachmentCollection1;
    }

    public void setCollectingeventattachmentCollection1(Collection<Collectingeventattachment> collectingeventattachmentCollection1) {
        this.collectingeventattachmentCollection1 = collectingeventattachmentCollection1;
    }

    @XmlTransient
    public Collection<Accessionauthorization> getAccessionauthorizationCollection() {
        return accessionauthorizationCollection;
    }

    public void setAccessionauthorizationCollection(Collection<Accessionauthorization> accessionauthorizationCollection) {
        this.accessionauthorizationCollection = accessionauthorizationCollection;
    }

    @XmlTransient
    public Collection<Accessionauthorization> getAccessionauthorizationCollection1() {
        return accessionauthorizationCollection1;
    }

    public void setAccessionauthorizationCollection1(Collection<Accessionauthorization> accessionauthorizationCollection1) {
        this.accessionauthorizationCollection1 = accessionauthorizationCollection1;
    }

    @XmlTransient
    public Collection<Attachment> getAttachmentCollection() {
        return attachmentCollection;
    }

    public void setAttachmentCollection(Collection<Attachment> attachmentCollection) {
        this.attachmentCollection = attachmentCollection;
    }

    @XmlTransient
    public Collection<Attachment> getAttachmentCollection1() {
        return attachmentCollection1;
    }

    public void setAttachmentCollection1(Collection<Attachment> attachmentCollection1) {
        this.attachmentCollection1 = attachmentCollection1;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirCollection() {
        return spappresourcedirCollection;
    }

    public void setSpappresourcedirCollection(Collection<Spappresourcedir> spappresourcedirCollection) {
        this.spappresourcedirCollection = spappresourcedirCollection;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirCollection1() {
        return spappresourcedirCollection1;
    }

    public void setSpappresourcedirCollection1(Collection<Spappresourcedir> spappresourcedirCollection1) {
        this.spappresourcedirCollection1 = spappresourcedirCollection1;
    }

    @XmlTransient
    public Collection<Spquery> getSpqueryCollection() {
        return spqueryCollection;
    }

    public void setSpqueryCollection(Collection<Spquery> spqueryCollection) {
        this.spqueryCollection = spqueryCollection;
    }

    @XmlTransient
    public Collection<Spquery> getSpqueryCollection1() {
        return spqueryCollection1;
    }

    public void setSpqueryCollection1(Collection<Spquery> spqueryCollection1) {
        this.spqueryCollection1 = spqueryCollection1;
    }

    @XmlTransient
    public Collection<Collectionobjectattribute> getCollectionobjectattributeCollection() {
        return collectionobjectattributeCollection;
    }

    public void setCollectionobjectattributeCollection(Collection<Collectionobjectattribute> collectionobjectattributeCollection) {
        this.collectionobjectattributeCollection = collectionobjectattributeCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectattribute> getCollectionobjectattributeCollection1() {
        return collectionobjectattributeCollection1;
    }

    public void setCollectionobjectattributeCollection1(Collection<Collectionobjectattribute> collectionobjectattributeCollection1) {
        this.collectionobjectattributeCollection1 = collectionobjectattributeCollection1;
    }

    @XmlTransient
    public Collection<Attachmenttag> getAttachmenttagCollection() {
        return attachmenttagCollection;
    }

    public void setAttachmenttagCollection(Collection<Attachmenttag> attachmenttagCollection) {
        this.attachmenttagCollection = attachmenttagCollection;
    }

    @XmlTransient
    public Collection<Attachmenttag> getAttachmenttagCollection1() {
        return attachmenttagCollection1;
    }

    public void setAttachmenttagCollection1(Collection<Attachmenttag> attachmenttagCollection1) {
        this.attachmenttagCollection1 = attachmenttagCollection1;
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
    public Collection<Loanpreparation> getLoanpreparationCollection() {
        return loanpreparationCollection;
    }

    public void setLoanpreparationCollection(Collection<Loanpreparation> loanpreparationCollection) {
        this.loanpreparationCollection = loanpreparationCollection;
    }

    @XmlTransient
    public Collection<Loanpreparation> getLoanpreparationCollection1() {
        return loanpreparationCollection1;
    }

    public void setLoanpreparationCollection1(Collection<Loanpreparation> loanpreparationCollection1) {
        this.loanpreparationCollection1 = loanpreparationCollection1;
    }

//    @XmlTransient
    public Collection<Address> getAddressCollection() {
        return addressCollection;
    }

    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
    }

//    @XmlTransient
    public Collection<Address> getAddressCollection1() {
        return addressCollection1;
    }

    public void setAddressCollection1(Collection<Address> addressCollection1) {
        this.addressCollection1 = addressCollection1;
    }

//    @XmlTransient
    public Collection<Address> getAddressCollection2() {
        return addressCollection2;
    }

    public void setAddressCollection2(Collection<Address> addressCollection2) {
        this.addressCollection2 = addressCollection2;
    }

    @XmlTransient
    public Collection<Locality> getLocalityCollection() {
        return localityCollection;
    }

    public void setLocalityCollection(Collection<Locality> localityCollection) {
        this.localityCollection = localityCollection;
    }

    @XmlTransient
    public Collection<Locality> getLocalityCollection1() {
        return localityCollection1;
    }

    public void setLocalityCollection1(Collection<Locality> localityCollection1) {
        this.localityCollection1 = localityCollection1;
    }

    @XmlTransient
    public Collection<Agentspecialty> getAgentspecialtyCollection() {
        return agentspecialtyCollection;
    }

    public void setAgentspecialtyCollection(Collection<Agentspecialty> agentspecialtyCollection) {
        this.agentspecialtyCollection = agentspecialtyCollection;
    }

    @XmlTransient
    public Collection<Agentspecialty> getAgentspecialtyCollection1() {
        return agentspecialtyCollection1;
    }

    public void setAgentspecialtyCollection1(Collection<Agentspecialty> agentspecialtyCollection1) {
        this.agentspecialtyCollection1 = agentspecialtyCollection1;
    }

    @XmlTransient
    public Collection<Agentspecialty> getAgentspecialtyCollection2() {
        return agentspecialtyCollection2;
    }

    public void setAgentspecialtyCollection2(Collection<Agentspecialty> agentspecialtyCollection2) {
        this.agentspecialtyCollection2 = agentspecialtyCollection2;
    }

    @XmlTransient
    public Collection<Taxontreedef> getTaxontreedefCollection() {
        return taxontreedefCollection;
    }

    public void setTaxontreedefCollection(Collection<Taxontreedef> taxontreedefCollection) {
        this.taxontreedefCollection = taxontreedefCollection;
    }

    @XmlTransient
    public Collection<Taxontreedef> getTaxontreedefCollection1() {
        return taxontreedefCollection1;
    }

    public void setTaxontreedefCollection1(Collection<Taxontreedef> taxontreedefCollection1) {
        this.taxontreedefCollection1 = taxontreedefCollection1;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitationCollection() {
        return localitycitationCollection;
    }

    public void setLocalitycitationCollection(Collection<Localitycitation> localitycitationCollection) {
        this.localitycitationCollection = localitycitationCollection;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitationCollection1() {
        return localitycitationCollection1;
    }

    public void setLocalitycitationCollection1(Collection<Localitycitation> localitycitationCollection1) {
        this.localitycitationCollection1 = localitycitationCollection1;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingrunCollection() {
        return dnasequencingrunCollection;
    }

    public void setDnasequencingrunCollection(Collection<Dnasequencingrun> dnasequencingrunCollection) {
        this.dnasequencingrunCollection = dnasequencingrunCollection;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingrunCollection1() {
        return dnasequencingrunCollection1;
    }

    public void setDnasequencingrunCollection1(Collection<Dnasequencingrun> dnasequencingrunCollection1) {
        this.dnasequencingrunCollection1 = dnasequencingrunCollection1;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingrunCollection2() {
        return dnasequencingrunCollection2;
    }

    public void setDnasequencingrunCollection2(Collection<Dnasequencingrun> dnasequencingrunCollection2) {
        this.dnasequencingrunCollection2 = dnasequencingrunCollection2;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingrunCollection3() {
        return dnasequencingrunCollection3;
    }

    public void setDnasequencingrunCollection3(Collection<Dnasequencingrun> dnasequencingrunCollection3) {
        this.dnasequencingrunCollection3 = dnasequencingrunCollection3;
    }

    @XmlTransient
    public Collection<Storage> getStorageCollection() {
        return storageCollection;
    }

    public void setStorageCollection(Collection<Storage> storageCollection) {
        this.storageCollection = storageCollection;
    }

    @XmlTransient
    public Collection<Storage> getStorageCollection1() {
        return storageCollection1;
    }

    public void setStorageCollection1(Collection<Storage> storageCollection1) {
        this.storageCollection1 = storageCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentID != null ? agentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        if ((this.agentID == null && other.agentID != null) || (this.agentID != null && !this.agentID.equals(other.agentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Agent[ agentID=" + agentID + " ]";
    }
    
}

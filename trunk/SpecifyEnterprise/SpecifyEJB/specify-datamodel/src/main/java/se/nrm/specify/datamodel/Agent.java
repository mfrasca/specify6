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
import javax.xml.bind.annotation.XmlElement;
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
    @NamedQuery(name = "Agent.findByAgentID", query = "SELECT a FROM Agent a WHERE a.agentId = :agentID"),
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
    @NamedQuery(name = "Agent.findBySpecifyuserid", query = "SELECT a FROM Agent a WHERE a.specifyUser.specifyUserId = :specifyUserID"),
    @NamedQuery(name = "Agent.findByDateType", query = "SELECT a FROM Agent a WHERE a.dateType = :dateType")})
public class Agent extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AgentID")
    private Integer agentId;
      
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
    
    @OneToMany(mappedBy = "createdByAgent", cascade= CascadeType.ALL)
    private Collection<Localitynamealias> localitynamealiases;
    
    @OneToMany(mappedBy = "modifiedByAgent", cascade= CascadeType.ALL)
    private Collection<Localitynamealias> localitynamealiases1;
    
    @OneToMany(mappedBy = "shipper")
    private Collection<Shipment> shipments;
    
    @OneToMany(mappedBy = "shippedTo")
    private Collection<Shipment> shipments1;
    
    @OneToMany(mappedBy = "shippedBy")
    private Collection<Shipment> shipments2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Shipment> shipments3;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Shipment> shipments4;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Loanagent> loanagents;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Loanagent> loanagents1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Loanagent> loanagents2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Preptype> preptypes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Preptype> preptypes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Agentgeography> agentGeographies1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Agentgeography> agentGeographies2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")  
    private Collection<Agentgeography> agentGeographies;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Deaccessionagent> deaccessionagents;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Deaccessionagent> deaccessionagents1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Deaccessionagent> deaccessionagents2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Loanattachment> loanattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Loanattachment> loanattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Treatmentevent> treatmentevents3;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Treatmentevent> treatmentevents4;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Workbench> workbenchs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Workbench> workbenchs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Attachmentmetadata> attachmentmetadata;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Attachmentmetadata> attachmentmetadata1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Taxon> taxons;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Taxon> taxons1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectingeventattr> collectingeventattrs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectingeventattr> collectingeventattrs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Splocaleitemstr> splocaleitemstrs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Splocaleitemstr> splocaleitemstrs1;
    
    @OneToMany(mappedBy = "preparedByAgent")
    private Collection<Preparation> preparations;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Preparation> preparations1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Preparation> preparations2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Workbenchtemplate> workbenchtemplates;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Workbenchtemplate> workbenchtemplates1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Sptasksemaphore> sptasksemaphores;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Sptasksemaphore> sptasksemaphores1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Picklistitem> picklistitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Picklistitem> picklistitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Loan> loans;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Loan> loans1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectingtrip> collectingtrips;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectingtrip> collectingtrips1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Project> projects;
    
    @OneToMany(mappedBy = "agent")
    private Collection<Project> projects1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Project> projects2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Agentattachment> agentAttachments1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Agentattachment> agentAttachments2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Agentattachment> agentAttachments;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Preparationattribute> preparationattributes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Preparationattribute> preparationattributes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Container> containers;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Container> containers1;
    
    @OneToMany(mappedBy = "curator")
    private Collection<Conservevent> conservevents;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Conservevent> conservevents1;
    
    @OneToMany(mappedBy = "treatedByAgent")
    private Collection<Conservevent> conservevents2;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Conservevent> conservevents3;
    
    @OneToMany(mappedBy = "examinedByAgent")
    private Collection<Conservevent> conservevents4;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Lithostrattreedefitem> lithostrattreedefitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Lithostrattreedefitem> lithostrattreedefitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Lithostrattreedef> lithostrattreedefs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Lithostrattreedef> lithostrattreedefs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent") 
    private Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Repositoryagreementattachment> repositoryagreementattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Repositoryagreementattachment> repositoryagreementattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Permitattachment> permitattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Permitattachment> permitattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Dnasequenceattachment> dnasequenceattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Dnasequenceattachment> dnasequenceattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Appraisal> appraisals;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Appraisal> appraisals1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Appraisal> appraisals2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Picklist> picklists;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Picklist> picklists1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spappresourcedata> spappresourcedata;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spappresourcedata> spappresourcedata1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentReceivedFrom")
    private Collection<Exchangein> exchangeins;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Exchangein> exchangeins1;
     
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Exchangein> exchangeins2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentCatalogedBy")
    private Collection<Exchangein> exchangeins3;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Gift> gifts;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Gift> gifts1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Agentvariant> agentvariants;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Agentvariant> agentvariants1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Agentvariant> variants;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spauditlog> spauditlogs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spauditlog> spauditlogs1;
    
    @OneToMany(mappedBy = "createdByAgent") 
    private Collection<Otheridentifier> otheridentifiers;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Otheridentifier> otheridentifiers1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Splocalecontaineritem> splocalecontaineritems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Splocalecontaineritem> splocalecontaineritems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Recordset> recordsets;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Recordset> recordsets1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesets;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesets1;
    
    @OneToMany(mappedBy = "sourceAgent")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesets2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Giftagent> giftagents;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Giftagent> giftagents1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Giftagent> giftagents2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<se.nrm.specify.datamodel.Collection> collections;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<se.nrm.specify.datamodel.Collection> collections1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spversion> spversions;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spversion> spversions1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Conservdescription> conservdescriptions;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Conservdescription> conservdescriptions1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Addressofrecord> addressofrecords;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Addressofrecord> addressofrecords1;
    
    @OneToMany(mappedBy = "agent", cascade= CascadeType.ALL)
    private Collection<Addressofrecord> addressofrecords2;
    
    @OneToMany(mappedBy = "createdByAgent") 
    private Collection<Division> divisions;
    
    @OneToMany(mappedBy = "modifiedByAgent")
//    @XmlElement
    private Collection<Division> divisions1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Storagetreedefitem> storagetreedefitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Storagetreedefitem> storagetreedefitems1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentSentTo")
    private Collection<Exchangeout> exchangeouts;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Exchangeout> exchangeouts1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Exchangeout> exchangeouts2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agentCatalogedBy")
    private Collection<Exchangeout> exchangeouts3;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spprincipal> spprincipals;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spprincipal> spprincipals1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Autonumberingscheme> autonumberingschemes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Autonumberingscheme> autonumberingschemes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Determinationcitation> determinationcitations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Determinationcitation> determinationcitations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Specifyuser> specifyusers;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Specifyuser> specifyusers1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Preparationattachment> preparationattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Preparationattachment> preparationattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Localitydetail> localitydetails;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Localitydetail> localitydetails1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectionrelationship> collectionrelationships;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionrelationship> collectionrelationships1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectionobjectattr> collectionobjectattrs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionobjectattr> collectionobjectattrs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectingeventattribute> collectingeventattributes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectingeventattribute> collectingeventattributes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spviewsetobj> spviewsetobjs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spviewsetobj> spviewsetobjs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spfieldvaluedefault> spfieldvaluedefaults;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spfieldvaluedefault> spfieldvaluedefaults1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Commonnametx> commonnametxs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Commonnametx> commonnametxs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Borrowmaterial> borrowmaterials;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Borrowmaterial> borrowmaterials1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Accessionagent> accessionagents;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Accessionagent> accessionagents1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Accessionagent> accessionagents2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spappresource> spappresources;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spappresource> spappresources1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geologictimeperiod> geologictimeperiods;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geologictimeperiod> geologictimeperiods1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Repositoryagreement> repositoryagreements;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Repositoryagreement> repositoryagreements1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "originator")
    private Collection<Repositoryagreement> repositoryagreements2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spqueryfield> spqueryfields;
    
    @OneToMany(mappedBy = "modifiedByAgent",  cascade= CascadeType.ALL)
    private Collection<Spqueryfield> spqueryfields1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spreport> spreports;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spreport> spreports1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Referencework> referenceworks;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Referencework> referenceworks1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Localityattachment> localityattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Localityattachment> localityattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Accession> accessions;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Accession> accessions1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Collection<Groupperson> groups;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Groupperson> groups1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Groupperson> groups2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private Collection<Groupperson> members;
    
    @OneToMany(mappedBy = "agent")
    private Collection<Borrowreturnmaterial> borrowreturnmaterials;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Borrowreturnmaterial> borrowreturnmaterials1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Borrowreturnmaterial> borrowreturnmaterials2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Preparationattr> preparationattrs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Preparationattr> preparationattrs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Datatype> datatypes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Datatype> datatypes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Storagetreedef> storagetreedefs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Storagetreedef> storagetreedefs1;
    
    @OneToMany(mappedBy = "issuedTo")
    private Collection<Permit> permits;
    
    @OneToMany(mappedBy = "issuedBy")
    private Collection<Permit> permits1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Permit> permits2;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Permit> permits3;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Commonnametxcitation> commonnametxcitations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Commonnametxcitation> commonnametxcitations1;
    
    @OneToMany(mappedBy = "determiner")
    private Collection<Determination> determinations;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Determination> determinations1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Determination> determinations2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spexportschema> spexportschemas;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spexportschema> spexportschemas1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectingevent> collectingevents;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectingevent> collectingevents1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectionreltype> collectionreltypes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionreltype> collectionreltypes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappings;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappings1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Dnasequence> dnasequences;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Dnasequence> dnasequences1;
    
    @OneToMany(mappedBy = "sequencer")
    private Collection<Dnasequence> dnasequences2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spauditlogfield> spauditlogfields;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spauditlogfield> spauditlogfields1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geographytreedef> geographytreedefs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geographytreedef> geographytreedefs1;
    
    @OneToMany(mappedBy = "createdByAgent") 
    private Collection<Collectionobjectattachment> collectionobjectattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionobjectattachment> collectionobjectattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Taxoncitation> taxoncitations;
    
    @OneToMany(mappedBy = "modifiedByAgent") 
    private Collection<Taxoncitation> taxoncitations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Dnasequencingruncitation> dnasequencingruncitations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Dnasequencingruncitation> dnasequencingruncitations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Inforequest> inforequests;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Inforequest> inforequests1;
    
    @OneToMany(mappedBy = "agent")
    private Collection<Inforequest> inforequests2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Lithostrat> lithostrats;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Lithostrat> lithostrats1;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne 
    private Division division;
    
    @JoinColumn(name = "InstitutionTCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Institution instTechContact;
    
    @JoinColumn(name = "InstitutionCCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Institution instContentContact;
    
    @OneToMany(mappedBy = "organization")
    private Collection<Agent> orgMembers;
    
    @JoinColumn(name = "ParentOrganizationID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent organization;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Agent> agents1;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Agent> agents2;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser specifyUser;
    
    @JoinColumn(name = "CollectionTCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection collTechContact;
    
    @JoinColumn(name = "CollectionCCID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection collContentContact;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Borrow> borrows;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Borrow> borrows1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spexportschemaitem> spexportschemaitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spexportschemaitem> spexportschemaitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Attributedef> attributedefs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Attributedef> attributedefs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Exsiccata> exsiccatas;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Exsiccata> exsiccatas1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Taxontreedefitem> taxontreedefitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Taxontreedefitem> taxontreedefitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Taxonattachment> taxonattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Taxonattachment> taxonattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Loanreturnpreparation> loanreturnpreparations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Loanreturnpreparation> loanreturnpreparations1;
    
    @OneToMany(mappedBy = "receivedBy")
    private Collection<Loanreturnpreparation> loanreturnpreparations2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spexportschemamapping> spexportschemamappings;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spexportschemamapping> spexportschemamappings1;
    
    @OneToMany(mappedBy = "createdByAgent") 
    private Collection<Fieldnotebookpageattachment> fieldnotebookpageattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Fieldnotebookpageattachment> fieldnotebookpageattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Giftpreparation> giftpreparations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Giftpreparation> giftpreparations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Fieldnotebookattachment> fieldnotebookattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Fieldnotebookattachment> fieldnotebookattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geographytreedefitem> geographytreedefitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geographytreedefitem> geographytreedefitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Splocalecontainer> splocalecontainers;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Splocalecontainer> splocalecontainers1;
    
    @OneToMany(mappedBy = "cataloger")
    private Collection<Collectionobject> collectionobjects;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionobject> collectionobjects1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectionobject> collectionobjects2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Borrowagent> borrowagents;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Borrowagent> borrowagents1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Borrowagent> borrowagents2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Conserveventattachment> conserveventattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Conserveventattachment> conserveventattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectionobjectcitation> collectionobjectcitations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionobjectcitation> collectionobjectcitations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Deaccession> deaccessions;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Deaccession> deaccessions1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Journal> journals;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Journal> journals1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Fieldnotebookpage> fieldnotebookpages;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Fieldnotebookpage> fieldnotebookpages1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Author> authors;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Author> authors1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Author> authors2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collector> collectors1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collector> collectors2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Collector> collectors;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geography> geographys;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geography> geographys1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Accessionattachment> accessionattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Accessionattachment> accessionattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Exsiccataitem> exsiccataitems;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Exsiccataitem> exsiccataitems1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Fieldnotebook> fieldnotebooks;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Fieldnotebook> fieldnotebooks1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerAgent")
    private Collection<Fieldnotebook> fieldnotebooks2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Discipline> disciplines;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Discipline> disciplines1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Institution> institutions;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Institution> institutions1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Conservdescriptionattachment> conservdescriptionattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Conservdescriptionattachment> conservdescriptionattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Geocoorddetail> geocoorddetails;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Geocoorddetail> geocoorddetails1;
    
    @OneToMany(mappedBy = "geoRefDetBy")
    private Collection<Geocoorddetail> geocoorddetails2;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Deaccessionpreparation> deaccessionpreparations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Deaccessionpreparation> deaccessionpreparations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectingeventattachment> collectingeventattachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectingeventattachment> collectingeventattachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Accessionauthorization> accessionauthorizations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Accessionauthorization> accessionauthorizations1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Attachment> attachments;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Attachment> attachments1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spappresourcedir> spappresourcedirs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spappresourcedir> spappresourcedirs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Spquery> spquerys;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Spquery> spquerys1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Collectionobjectattribute> collectionobjectattributes;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Collectionobjectattribute> collectionobjectattributes1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Attachmenttag> attachmenttags;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Attachmenttag> attachmenttagsn1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Paleocontext> paleocontexts;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Paleocontext> paleocontexts1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Loanpreparation> loanpreparations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Loanpreparation> loanpreparations1;
    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modifiedByAgent") 
    private Collection<Address> addresses2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByAgent") 
    private Collection<Address> addresses1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")  
    private Collection<Address> addresses;   
     
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Locality> localities1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Locality> localities;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Agentspecialty> agentSpecialties2;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Agentspecialty> agentspecialties1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent") 
    private Collection<Agentspecialty> agentSpecialties;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Taxontreedef> taxontreedefs;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Taxontreedef> taxontreedefs1;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Localitycitation> localitycitations;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Localitycitation> localitycitations1;
    
    @OneToMany(mappedBy = "runByAgent")
    private Collection<Dnasequencingrun> dnasequencingruns;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Dnasequencingrun> dnasequencingruns1;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Dnasequencingrun> dnasequencingruns2;
    
    @OneToMany(mappedBy = "preparedByAgent")
    private Collection<Dnasequencingrun> dnasequencingruns3;
    
    @OneToMany(mappedBy = "createdByAgent")
    private Collection<Storage> storages;
    
    @OneToMany(mappedBy = "modifiedByAgent")
    private Collection<Storage> storages1;
    

    public Agent() {
    }

    public Agent(Integer agentId) {
        this.agentId = agentId;
    }

    public Agent(Integer agentId, Date timestampCreated, short agentType) {
        super(timestampCreated);
        this.agentId = agentId; 
        this.agentType = agentType;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
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
    public Collection<Agentgeography> getAgentGeographies() {
        return agentGeographies;
    }

    public void setAgentGeographies(Collection<Agentgeography> agentGeographies) {
        this.agentGeographies = agentGeographies;
    }

  
 

    @XmlTransient
    public Collection<Agentattachment> getAgentAttachments() {
        return agentAttachments;
    }

    public void setAgentAttachments(Collection<Agentattachment> agentAttachments) {
        this.agentAttachments = agentAttachments;
    }

  

    @XmlTransient
    public Collection<Agentvariant> getVariants() {
        return variants;
    }

    public void setVariants(Collection<Agentvariant> variants) {
        this.variants = variants;
    }

   
  

   

    @XmlTransient
    public Collection<Groupperson> getGroups() {
        return groups;
    }

    public void setGroups(Collection<Groupperson> groups) {
        this.groups = groups;
    }
  
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Institution getInstContentContact() {
        return instContentContact;
    }

    public void setInstContentContact(Institution instContentContact) {
        this.instContentContact = instContentContact;
    }

    public Institution getInstTechContact() {
        return instTechContact;
    }

    public void setInstTechContact(Institution instTechContact) {
        this.instTechContact = instTechContact;
    }

    @XmlTransient
    public Collection<Agent> getOrgMembers() {
        return orgMembers;
    }

    public void setOrgMembers(Collection<Agent> orgMembers) {
        this.orgMembers = orgMembers;
    }

 

 

    @XmlTransient
    public Agent getOrganization() {  
        return organization;
    }

    public void setOrganization(Agent organization) {
        this.organization = organization;
    }

 
 
    @XmlTransient
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

 

 

    public se.nrm.specify.datamodel.Collection getCollContentContact() {
        return collContentContact;
    }

    public void setCollContentContact(se.nrm.specify.datamodel.Collection collContentContact) {
        this.collContentContact = collContentContact;
    }

    public se.nrm.specify.datamodel.Collection getCollTechContact() {
        return collTechContact;
    }

    public void setCollTechContact(se.nrm.specify.datamodel.Collection collTechContact) {
        this.collTechContact = collTechContact;
    }

   
    @XmlTransient
    public Collection<Collector> getCollectors() {
        return collectors;
    }

    public void setCollectors(Collection<Collector> collectors) {
        this.collectors = collectors;
    }

    @XmlElement
    public Collection<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Collection<Address> addresses) {
        this.addresses = addresses;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagents() {
        return accessionagents;
    }

    public void setAccessionagents(Collection<Accessionagent> accessionagents) {
        this.accessionagents = accessionagents;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagents1() {
        return accessionagents1;
    }

    public void setAccessionagents1(Collection<Accessionagent> accessionagents1) {
        this.accessionagents1 = accessionagents1;
    }

    @XmlTransient
    public Collection<Accessionagent> getAccessionagents2() {
        return accessionagents2;
    }

    public void setAccessionagents2(Collection<Accessionagent> accessionagents2) {
        this.accessionagents2 = accessionagents2;
    }

    @XmlTransient
    public Collection<Accessionattachment> getAccessionattachments() {
        return accessionattachments;
    }

    public void setAccessionattachments(Collection<Accessionattachment> accessionattachments) {
        this.accessionattachments = accessionattachments;
    }

    @XmlTransient
    public Collection<Accessionattachment> getAccessionattachments1() {
        return accessionattachments1;
    }

    public void setAccessionattachments1(Collection<Accessionattachment> accessionattachments1) {
        this.accessionattachments1 = accessionattachments1;
    }

    @XmlTransient
    public Collection<Accessionauthorization> getAccessionauthorizations() {
        return accessionauthorizations;
    }

    public void setAccessionauthorizations(Collection<Accessionauthorization> accessionauthorizations) {
        this.accessionauthorizations = accessionauthorizations;
    }

    @XmlTransient
    public Collection<Accessionauthorization> getAccessionauthorizations1() {
        return accessionauthorizations1;
    }

    public void setAccessionauthorizations1(Collection<Accessionauthorization> accessionauthorizations1) {
        this.accessionauthorizations1 = accessionauthorizations1;
    }

    @XmlTransient
    public Collection<Accession> getAccessions() {
        return accessions;
    }

    public void setAccessions(Collection<Accession> accessions) {
        this.accessions = accessions;
    }

    @XmlTransient
    public Collection<Accession> getAccessions1() {
        return accessions1;
    }

    public void setAccessions1(Collection<Accession> accessions1) {
        this.accessions1 = accessions1;
    }

    
    @XmlTransient
    public Collection<Address> getAddresses1() {
        return addresses1;
    }

    public void setAddresses1(Collection<Address> addresses1) {
        this.addresses1 = addresses1;
    }

    @XmlTransient
    public Collection<Address> getAddresses2() {
        return addresses2;
    }

    public void setAddresses2(Collection<Address> addresses2) {
        this.addresses2 = addresses2;
    }

    @XmlTransient
    public Collection<Addressofrecord> getAddressofrecords() {
        return addressofrecords;
    }

    public void setAddressofrecords(Collection<Addressofrecord> addressofrecords) {
        this.addressofrecords = addressofrecords;
    }

    @XmlTransient
    public Collection<Addressofrecord> getAddressofrecords1() {
        return addressofrecords1;
    }

    public void setAddressofrecords1(Collection<Addressofrecord> addressofrecords1) {
        this.addressofrecords1 = addressofrecords1;
    }

    @XmlTransient
    public Collection<Addressofrecord> getAddressofrecords2() {
        return addressofrecords2;
    }

    public void setAddressofrecords2(Collection<Addressofrecord> addressofrecords2) {
        this.addressofrecords2 = addressofrecords2;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentAttachments1() {
        return agentAttachments1;
    }

    public void setAgentAttachments1(Collection<Agentattachment> agentAttachments1) {
        this.agentAttachments1 = agentAttachments1;
    }

    @XmlTransient
    public Collection<Agentattachment> getAgentAttachments2() {
        return agentAttachments2;
    }

    public void setAgentAttachments2(Collection<Agentattachment> agentAttachments2) {
        this.agentAttachments2 = agentAttachments2;
    }

    @XmlTransient
    public Collection<Agentgeography> getAgentGeographies1() {
        return agentGeographies1;
    }

    public void setAgentGeographies1(Collection<Agentgeography> agentGeographies1) {
        this.agentGeographies1 = agentGeographies1;
    }

    @XmlTransient
    public Collection<Agentgeography> getAgentGeographies2() {
        return agentGeographies2;
    }

    public void setAgentGeographies2(Collection<Agentgeography> agentGeographies2) {
        this.agentGeographies2 = agentGeographies2;
    }

    @XmlTransient
    public Collection<Agentspecialty> getAgentSpecialties() {
        return agentSpecialties;
    }

    public void setAgentSpecialties(Collection<Agentspecialty> agentSpecialties) {
        this.agentSpecialties = agentSpecialties;
    }

    @XmlTransient
    public Collection<Agentspecialty> getAgentSpecialties2() {
        return agentSpecialties2;
    }

    public void setAgentSpecialties2(Collection<Agentspecialty> agentSpecialties2) {
        this.agentSpecialties2 = agentSpecialties2;
    }

    @XmlTransient
    public Collection<Agent> getAgents1() {
        return agents1;
    }

    public void setAgents1(Collection<Agent> agents1) {
        this.agents1 = agents1;
    }

    @XmlTransient
    public Collection<Agent> getAgents2() {
        return agents2;
    }

    public void setAgents2(Collection<Agent> agents2) {
        this.agents2 = agents2;
    }

    @XmlTransient
    public Collection<Agentspecialty> getAgentspecialties1() {
        return agentspecialties1;
    }

    public void setAgentspecialties1(Collection<Agentspecialty> agentspecialties1) {
        this.agentspecialties1 = agentspecialties1;
    }

    @XmlTransient
    public Collection<Agentvariant> getAgentvariants() {
        return agentvariants;
    }

    public void setAgentvariants(Collection<Agentvariant> agentvariants) {
        this.agentvariants = agentvariants;
    }

    @XmlTransient
    public Collection<Agentvariant> getAgentvariants1() {
        return agentvariants1;
    }

    public void setAgentvariants1(Collection<Agentvariant> agentvariants1) {
        this.agentvariants1 = agentvariants1;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisals() {
        return appraisals;
    }

    public void setAppraisals(Collection<Appraisal> appraisals) {
        this.appraisals = appraisals;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisals1() {
        return appraisals1;
    }

    public void setAppraisals1(Collection<Appraisal> appraisals1) {
        this.appraisals1 = appraisals1;
    }

    @XmlTransient
    public Collection<Appraisal> getAppraisals2() {
        return appraisals2;
    }

    public void setAppraisals2(Collection<Appraisal> appraisals2) {
        this.appraisals2 = appraisals2;
    }

    @XmlTransient
    public Collection<Attachmentmetadata> getAttachmentmetadata() {
        return attachmentmetadata;
    }

    public void setAttachmentmetadata(Collection<Attachmentmetadata> attachmentmetadata) {
        this.attachmentmetadata = attachmentmetadata;
    }

    @XmlTransient
    public Collection<Attachmentmetadata> getAttachmentmetadata1() {
        return attachmentmetadata1;
    }

    public void setAttachmentmetadata1(Collection<Attachmentmetadata> attachmentmetadata1) {
        this.attachmentmetadata1 = attachmentmetadata1;
    }

    @XmlTransient
    public Collection<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Attachment> attachments) {
        this.attachments = attachments;
    }

    @XmlTransient
    public Collection<Attachment> getAttachments1() {
        return attachments1;
    }

    public void setAttachments1(Collection<Attachment> attachments1) {
        this.attachments1 = attachments1;
    }

    @XmlTransient
    public Collection<Attachmenttag> getAttachmenttags() {
        return attachmenttags;
    }

    public void setAttachmenttags(Collection<Attachmenttag> attachmenttags) {
        this.attachmenttags = attachmenttags;
    }

    @XmlTransient
    public Collection<Attachmenttag> getAttachmenttagsn1() {
        return attachmenttagsn1;
    }

    public void setAttachmenttagsn1(Collection<Attachmenttag> attachmenttagsn1) {
        this.attachmenttagsn1 = attachmenttagsn1;
    }

    @XmlTransient
    public Collection<Attributedef> getAttributedefs() {
        return attributedefs;
    }

    public void setAttributedefs(Collection<Attributedef> attributedefs) {
        this.attributedefs = attributedefs;
    }

    @XmlTransient
    public Collection<Attributedef> getAttributedefs1() {
        return attributedefs1;
    }

    public void setAttributedefs1(Collection<Attributedef> attributedefs1) {
        this.attributedefs1 = attributedefs1;
    }

    @XmlTransient
    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    @XmlTransient
    public Collection<Author> getAuthors1() {
        return authors1;
    }

    public void setAuthors1(Collection<Author> authors1) {
        this.authors1 = authors1;
    }

    @XmlTransient
    public Collection<Author> getAuthors2() {
        return authors2;
    }

    public void setAuthors2(Collection<Author> authors2) {
        this.authors2 = authors2;
    }

    @XmlTransient
    public Collection<Autonumberingscheme> getAutonumberingschemes() {
        return autonumberingschemes;
    }

    public void setAutonumberingschemes(Collection<Autonumberingscheme> autonumberingschemes) {
        this.autonumberingschemes = autonumberingschemes;
    }

    @XmlTransient
    public Collection<Autonumberingscheme> getAutonumberingschemes1() {
        return autonumberingschemes1;
    }

    public void setAutonumberingschemes1(Collection<Autonumberingscheme> autonumberingschemes1) {
        this.autonumberingschemes1 = autonumberingschemes1;
    }

    @XmlTransient
    public Collection<Borrowagent> getBorrowagents() {
        return borrowagents;
    }

    public void setBorrowagents(Collection<Borrowagent> borrowagents) {
        this.borrowagents = borrowagents;
    }

    @XmlTransient
    public Collection<Borrowagent> getBorrowagents1() {
        return borrowagents1;
    }

    public void setBorrowagents1(Collection<Borrowagent> borrowagents1) {
        this.borrowagents1 = borrowagents1;
    }

    @XmlTransient
    public Collection<Borrowagent> getBorrowagents2() {
        return borrowagents2;
    }

    public void setBorrowagents2(Collection<Borrowagent> borrowagents2) {
        this.borrowagents2 = borrowagents2;
    }

    @XmlTransient
    public Collection<Borrowmaterial> getBorrowmaterials() {
        return borrowmaterials;
    }

    public void setBorrowmaterials(Collection<Borrowmaterial> borrowmaterials) {
        this.borrowmaterials = borrowmaterials;
    }

    @XmlTransient
    public Collection<Borrowmaterial> getBorrowmaterials1() {
        return borrowmaterials1;
    }

    public void setBorrowmaterials1(Collection<Borrowmaterial> borrowmaterials1) {
        this.borrowmaterials1 = borrowmaterials1;
    }

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterials() {
        return borrowreturnmaterials;
    }

    public void setBorrowreturnmaterials(Collection<Borrowreturnmaterial> borrowreturnmaterials) {
        this.borrowreturnmaterials = borrowreturnmaterials;
    }

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterials1() {
        return borrowreturnmaterials1;
    }

    public void setBorrowreturnmaterials1(Collection<Borrowreturnmaterial> borrowreturnmaterials1) {
        this.borrowreturnmaterials1 = borrowreturnmaterials1;
    }

    @XmlTransient
    public Collection<Borrowreturnmaterial> getBorrowreturnmaterials2() {
        return borrowreturnmaterials2;
    }

    public void setBorrowreturnmaterials2(Collection<Borrowreturnmaterial> borrowreturnmaterials2) {
        this.borrowreturnmaterials2 = borrowreturnmaterials2;
    }

    @XmlTransient
    public Collection<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Collection<Borrow> borrows) {
        this.borrows = borrows;
    }

    @XmlTransient
    public Collection<Borrow> getBorrows1() {
        return borrows1;
    }

    public void setBorrows1(Collection<Borrow> borrows1) {
        this.borrows1 = borrows1;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingeventattachments() {
        return collectingeventattachments;
    }

    public void setCollectingeventattachments(Collection<Collectingeventattachment> collectingeventattachments) {
        this.collectingeventattachments = collectingeventattachments;
    }

    @XmlTransient
    public Collection<Collectingeventattachment> getCollectingeventattachments1() {
        return collectingeventattachments1;
    }

    public void setCollectingeventattachments1(Collection<Collectingeventattachment> collectingeventattachments1) {
        this.collectingeventattachments1 = collectingeventattachments1;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributes() {
        return collectingeventattributes;
    }

    public void setCollectingeventattributes(Collection<Collectingeventattribute> collectingeventattributes) {
        this.collectingeventattributes = collectingeventattributes;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributes1() {
        return collectingeventattributes1;
    }

    public void setCollectingeventattributes1(Collection<Collectingeventattribute> collectingeventattributes1) {
        this.collectingeventattributes1 = collectingeventattributes1;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingeventattrs() {
        return collectingeventattrs;
    }

    public void setCollectingeventattrs(Collection<Collectingeventattr> collectingeventattrs) {
        this.collectingeventattrs = collectingeventattrs;
    }

    @XmlTransient
    public Collection<Collectingeventattr> getCollectingeventattrs1() {
        return collectingeventattrs1;
    }

    public void setCollectingeventattrs1(Collection<Collectingeventattr> collectingeventattrs1) {
        this.collectingeventattrs1 = collectingeventattrs1;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingevents() {
        return collectingevents;
    }

    public void setCollectingevents(Collection<Collectingevent> collectingevents) {
        this.collectingevents = collectingevents;
    }

    @XmlTransient
    public Collection<Collectingevent> getCollectingevents1() {
        return collectingevents1;
    }

    public void setCollectingevents1(Collection<Collectingevent> collectingevents1) {
        this.collectingevents1 = collectingevents1;
    }

    @XmlTransient
    public Collection<Collectingtrip> getCollectingtrips() {
        return collectingtrips;
    }

    public void setCollectingtrips(Collection<Collectingtrip> collectingtrips) {
        this.collectingtrips = collectingtrips;
    }

    @XmlTransient
    public Collection<Collectingtrip> getCollectingtrips1() {
        return collectingtrips1;
    }

    public void setCollectingtrips1(Collection<Collectingtrip> collectingtrips1) {
        this.collectingtrips1 = collectingtrips1;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionobjectattachments() {
        return collectionobjectattachments;
    }

    public void setCollectionobjectattachments(Collection<Collectionobjectattachment> collectionobjectattachments) {
        this.collectionobjectattachments = collectionobjectattachments;
    }

    @XmlTransient
    public Collection<Collectionobjectattachment> getCollectionobjectattachments1() {
        return collectionobjectattachments1;
    }

    public void setCollectionobjectattachments1(Collection<Collectionobjectattachment> collectionobjectattachments1) {
        this.collectionobjectattachments1 = collectionobjectattachments1;
    }

    @XmlTransient
    public Collection<Collectionobjectattribute> getCollectionobjectattributes() {
        return collectionobjectattributes;
    }

    public void setCollectionobjectattributes(Collection<Collectionobjectattribute> collectionobjectattributes) {
        this.collectionobjectattributes = collectionobjectattributes;
    }

    @XmlTransient
    public Collection<Collectionobjectattribute> getCollectionobjectattributes1() {
        return collectionobjectattributes1;
    }

    public void setCollectionobjectattributes1(Collection<Collectionobjectattribute> collectionobjectattributes1) {
        this.collectionobjectattributes1 = collectionobjectattributes1;
    }

    @XmlTransient
    public Collection<Collectionobjectattr> getCollectionobjectattrs() {
        return collectionobjectattrs;
    }

    public void setCollectionobjectattrs(Collection<Collectionobjectattr> collectionobjectattrs) {
        this.collectionobjectattrs = collectionobjectattrs;
    }

    @XmlTransient
    public Collection<Collectionobjectattr> getCollectionobjectattrs1() {
        return collectionobjectattrs1;
    }

    public void setCollectionobjectattrs1(Collection<Collectionobjectattr> collectionobjectattrs1) {
        this.collectionobjectattrs1 = collectionobjectattrs1;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionobjectcitations() {
        return collectionobjectcitations;
    }

    public void setCollectionobjectcitations(Collection<Collectionobjectcitation> collectionobjectcitations) {
        this.collectionobjectcitations = collectionobjectcitations;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionobjectcitations1() {
        return collectionobjectcitations1;
    }

    public void setCollectionobjectcitations1(Collection<Collectionobjectcitation> collectionobjectcitations1) {
        this.collectionobjectcitations1 = collectionobjectcitations1;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjects() {
        return collectionobjects;
    }

    public void setCollectionobjects(Collection<Collectionobject> collectionobjects) {
        this.collectionobjects = collectionobjects;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjects1() {
        return collectionobjects1;
    }

    public void setCollectionobjects1(Collection<Collectionobject> collectionobjects1) {
        this.collectionobjects1 = collectionobjects1;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjects2() {
        return collectionobjects2;
    }

    public void setCollectionobjects2(Collection<Collectionobject> collectionobjects2) {
        this.collectionobjects2 = collectionobjects2;
    }

    @XmlTransient
    public Collection<Collectionrelationship> getCollectionrelationships() {
        return collectionrelationships;
    }

    public void setCollectionrelationships(Collection<Collectionrelationship> collectionrelationships) {
        this.collectionrelationships = collectionrelationships;
    }

    @XmlTransient
    public Collection<Collectionrelationship> getCollectionrelationships1() {
        return collectionrelationships1;
    }

    public void setCollectionrelationships1(Collection<Collectionrelationship> collectionrelationships1) {
        this.collectionrelationships1 = collectionrelationships1;
    }

    @XmlTransient
    public Collection<Collectionreltype> getCollectionreltypes() {
        return collectionreltypes;
    }

    public void setCollectionreltypes(Collection<Collectionreltype> collectionreltypes) {
        this.collectionreltypes = collectionreltypes;
    }

    @XmlTransient
    public Collection<Collectionreltype> getCollectionreltypes1() {
        return collectionreltypes1;
    }

    public void setCollectionreltypes1(Collection<Collectionreltype> collectionreltypes1) {
        this.collectionreltypes1 = collectionreltypes1;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollections() {
        return collections;
    }

    public void setCollections(Collection<se.nrm.specify.datamodel.Collection> collections) {
        this.collections = collections;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollections1() {
        return collections1;
    }

    public void setCollections1(Collection<se.nrm.specify.datamodel.Collection> collections1) {
        this.collections1 = collections1;
    }

    @XmlTransient
    public Collection<Collector> getCollectors1() {
        return collectors1;
    }

    public void setCollectors1(Collection<Collector> collectors1) {
        this.collectors1 = collectors1;
    }

    @XmlTransient
    public Collection<Collector> getCollectors2() {
        return collectors2;
    }

    public void setCollectors2(Collection<Collector> collectors2) {
        this.collectors2 = collectors2;
    }

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitations() {
        return commonnametxcitations;
    }

    public void setCommonnametxcitations(Collection<Commonnametxcitation> commonnametxcitations) {
        this.commonnametxcitations = commonnametxcitations;
    }

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitations1() {
        return commonnametxcitations1;
    }

    public void setCommonnametxcitations1(Collection<Commonnametxcitation> commonnametxcitations1) {
        this.commonnametxcitations1 = commonnametxcitations1;
    }

    @XmlTransient
    public Collection<Commonnametx> getCommonnametxs() {
        return commonnametxs;
    }

    public void setCommonnametxs(Collection<Commonnametx> commonnametxs) {
        this.commonnametxs = commonnametxs;
    }

    @XmlTransient
    public Collection<Commonnametx> getCommonnametxs1() {
        return commonnametxs1;
    }

    public void setCommonnametxs1(Collection<Commonnametx> commonnametxs1) {
        this.commonnametxs1 = commonnametxs1;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservdescriptionattachments() {
        return conservdescriptionattachments;
    }

    public void setConservdescriptionattachments(Collection<Conservdescriptionattachment> conservdescriptionattachments) {
        this.conservdescriptionattachments = conservdescriptionattachments;
    }

    @XmlTransient
    public Collection<Conservdescriptionattachment> getConservdescriptionattachments1() {
        return conservdescriptionattachments1;
    }

    public void setConservdescriptionattachments1(Collection<Conservdescriptionattachment> conservdescriptionattachments1) {
        this.conservdescriptionattachments1 = conservdescriptionattachments1;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptions() {
        return conservdescriptions;
    }

    public void setConservdescriptions(Collection<Conservdescription> conservdescriptions) {
        this.conservdescriptions = conservdescriptions;
    }

    @XmlTransient
    public Collection<Conservdescription> getConservdescriptions1() {
        return conservdescriptions1;
    }

    public void setConservdescriptions1(Collection<Conservdescription> conservdescriptions1) {
        this.conservdescriptions1 = conservdescriptions1;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConserveventattachments() {
        return conserveventattachments;
    }

    public void setConserveventattachments(Collection<Conserveventattachment> conserveventattachments) {
        this.conserveventattachments = conserveventattachments;
    }

    @XmlTransient
    public Collection<Conserveventattachment> getConserveventattachments1() {
        return conserveventattachments1;
    }

    public void setConserveventattachments1(Collection<Conserveventattachment> conserveventattachments1) {
        this.conserveventattachments1 = conserveventattachments1;
    }

    @XmlTransient
    public Collection<Conservevent> getConservevents() {
        return conservevents;
    }

    public void setConservevents(Collection<Conservevent> conservevents) {
        this.conservevents = conservevents;
    }

    @XmlTransient
    public Collection<Conservevent> getConservevents1() {
        return conservevents1;
    }

    public void setConservevents1(Collection<Conservevent> conservevents1) {
        this.conservevents1 = conservevents1;
    }

    @XmlTransient
    public Collection<Conservevent> getConservevents2() {
        return conservevents2;
    }

    public void setConservevents2(Collection<Conservevent> conservevents2) {
        this.conservevents2 = conservevents2;
    }

    @XmlTransient
    public Collection<Conservevent> getConservevents3() {
        return conservevents3;
    }

    public void setConservevents3(Collection<Conservevent> conservevents3) {
        this.conservevents3 = conservevents3;
    }

    @XmlTransient
    public Collection<Conservevent> getConservevents4() {
        return conservevents4;
    }

    public void setConservevents4(Collection<Conservevent> conservevents4) {
        this.conservevents4 = conservevents4;
    }

    @XmlTransient
    public Collection<Container> getContainers() {
        return containers;
    }

    public void setContainers(Collection<Container> containers) {
        this.containers = containers;
    }

    @XmlTransient
    public Collection<Container> getContainers1() {
        return containers1;
    }

    public void setContainers1(Collection<Container> containers1) {
        this.containers1 = containers1;
    }

    @XmlTransient
    public Collection<Datatype> getDatatypes() {
        return datatypes;
    }

    public void setDatatypes(Collection<Datatype> datatypes) {
        this.datatypes = datatypes;
    }

    @XmlTransient
    public Collection<Datatype> getDatatypes1() {
        return datatypes1;
    }

    public void setDatatypes1(Collection<Datatype> datatypes1) {
        this.datatypes1 = datatypes1;
    }

    @XmlTransient
    public Collection<Deaccessionagent> getDeaccessionagents() {
        return deaccessionagents;
    }

    public void setDeaccessionagents(Collection<Deaccessionagent> deaccessionagents) {
        this.deaccessionagents = deaccessionagents;
    }

    @XmlTransient
    public Collection<Deaccessionagent> getDeaccessionagents1() {
        return deaccessionagents1;
    }

    public void setDeaccessionagents1(Collection<Deaccessionagent> deaccessionagents1) {
        this.deaccessionagents1 = deaccessionagents1;
    }

    @XmlTransient
    public Collection<Deaccessionagent> getDeaccessionagents2() {
        return deaccessionagents2;
    }

    public void setDeaccessionagents2(Collection<Deaccessionagent> deaccessionagents2) {
        this.deaccessionagents2 = deaccessionagents2;
    }

    @XmlTransient
    public Collection<Deaccessionpreparation> getDeaccessionpreparations() {
        return deaccessionpreparations;
    }

    public void setDeaccessionpreparations(Collection<Deaccessionpreparation> deaccessionpreparations) {
        this.deaccessionpreparations = deaccessionpreparations;
    }

    @XmlTransient
    public Collection<Deaccessionpreparation> getDeaccessionpreparations1() {
        return deaccessionpreparations1;
    }

    public void setDeaccessionpreparations1(Collection<Deaccessionpreparation> deaccessionpreparations1) {
        this.deaccessionpreparations1 = deaccessionpreparations1;
    }

    @XmlTransient
    public Collection<Deaccession> getDeaccessions() {
        return deaccessions;
    }

    public void setDeaccessions(Collection<Deaccession> deaccessions) {
        this.deaccessions = deaccessions;
    }

    @XmlTransient
    public Collection<Deaccession> getDeaccessions1() {
        return deaccessions1;
    }

    public void setDeaccessions1(Collection<Deaccession> deaccessions1) {
        this.deaccessions1 = deaccessions1;
    }

    @XmlTransient
    public Collection<Determinationcitation> getDeterminationcitations() {
        return determinationcitations;
    }

    public void setDeterminationcitations(Collection<Determinationcitation> determinationcitations) {
        this.determinationcitations = determinationcitations;
    }

    @XmlTransient
    public Collection<Determinationcitation> getDeterminationcitations1() {
        return determinationcitations1;
    }

    public void setDeterminationcitations1(Collection<Determinationcitation> determinationcitations1) {
        this.determinationcitations1 = determinationcitations1;
    }

    @XmlTransient
    public Collection<Determination> getDeterminations() {
        return determinations;
    }

    public void setDeterminations(Collection<Determination> determinations) {
        this.determinations = determinations;
    }

    @XmlTransient
    public Collection<Determination> getDeterminations1() {
        return determinations1;
    }

    public void setDeterminations1(Collection<Determination> determinations1) {
        this.determinations1 = determinations1;
    }

    @XmlTransient
    public Collection<Determination> getDeterminations2() {
        return determinations2;
    }

    public void setDeterminations2(Collection<Determination> determinations2) {
        this.determinations2 = determinations2;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Collection<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplines1() {
        return disciplines1;
    }

    public void setDisciplines1(Collection<Discipline> disciplines1) {
        this.disciplines1 = disciplines1;
    }

    @XmlTransient
    public Collection<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(Collection<Division> divisions) {
        this.divisions = divisions;
    }

    @XmlTransient
    public Collection<Division> getDivisions1() {
        return divisions1;
    }

    public void setDivisions1(Collection<Division> divisions1) {
        this.divisions1 = divisions1;
    }

    @XmlTransient
    public Collection<Dnasequenceattachment> getDnasequenceattachments() {
        return dnasequenceattachments;
    }

    public void setDnasequenceattachments(Collection<Dnasequenceattachment> dnasequenceattachments) {
        this.dnasequenceattachments = dnasequenceattachments;
    }

    @XmlTransient
    public Collection<Dnasequenceattachment> getDnasequenceattachments1() {
        return dnasequenceattachments1;
    }

    public void setDnasequenceattachments1(Collection<Dnasequenceattachment> dnasequenceattachments1) {
        this.dnasequenceattachments1 = dnasequenceattachments1;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequences() {
        return dnasequences;
    }

    public void setDnasequences(Collection<Dnasequence> dnasequences) {
        this.dnasequences = dnasequences;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequences1() {
        return dnasequences1;
    }

    public void setDnasequences1(Collection<Dnasequence> dnasequences1) {
        this.dnasequences1 = dnasequences1;
    }

    @XmlTransient
    public Collection<Dnasequence> getDnasequences2() {
        return dnasequences2;
    }

    public void setDnasequences2(Collection<Dnasequence> dnasequences2) {
        this.dnasequences2 = dnasequences2;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getDnasequencingruncitations() {
        return dnasequencingruncitations;
    }

    public void setDnasequencingruncitations(Collection<Dnasequencingruncitation> dnasequencingruncitations) {
        this.dnasequencingruncitations = dnasequencingruncitations;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getDnasequencingruncitations1() {
        return dnasequencingruncitations1;
    }

    public void setDnasequencingruncitations1(Collection<Dnasequencingruncitation> dnasequencingruncitations1) {
        this.dnasequencingruncitations1 = dnasequencingruncitations1;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingruns() {
        return dnasequencingruns;
    }

    public void setDnasequencingruns(Collection<Dnasequencingrun> dnasequencingruns) {
        this.dnasequencingruns = dnasequencingruns;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingruns1() {
        return dnasequencingruns1;
    }

    public void setDnasequencingruns1(Collection<Dnasequencingrun> dnasequencingruns1) {
        this.dnasequencingruns1 = dnasequencingruns1;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingruns2() {
        return dnasequencingruns2;
    }

    public void setDnasequencingruns2(Collection<Dnasequencingrun> dnasequencingruns2) {
        this.dnasequencingruns2 = dnasequencingruns2;
    }

    @XmlTransient
    public Collection<Dnasequencingrun> getDnasequencingruns3() {
        return dnasequencingruns3;
    }

    public void setDnasequencingruns3(Collection<Dnasequencingrun> dnasequencingruns3) {
        this.dnasequencingruns3 = dnasequencingruns3;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeins() {
        return exchangeins;
    }

    public void setExchangeins(Collection<Exchangein> exchangeins) {
        this.exchangeins = exchangeins;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeins1() {
        return exchangeins1;
    }

    public void setExchangeins1(Collection<Exchangein> exchangeins1) {
        this.exchangeins1 = exchangeins1;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeins2() {
        return exchangeins2;
    }

    public void setExchangeins2(Collection<Exchangein> exchangeins2) {
        this.exchangeins2 = exchangeins2;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeins3() {
        return exchangeins3;
    }

    public void setExchangeins3(Collection<Exchangein> exchangeins3) {
        this.exchangeins3 = exchangeins3;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeouts() {
        return exchangeouts;
    }

    public void setExchangeouts(Collection<Exchangeout> exchangeouts) {
        this.exchangeouts = exchangeouts;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeouts1() {
        return exchangeouts1;
    }

    public void setExchangeouts1(Collection<Exchangeout> exchangeouts1) {
        this.exchangeouts1 = exchangeouts1;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeouts2() {
        return exchangeouts2;
    }

    public void setExchangeouts2(Collection<Exchangeout> exchangeouts2) {
        this.exchangeouts2 = exchangeouts2;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeouts3() {
        return exchangeouts3;
    }

    public void setExchangeouts3(Collection<Exchangeout> exchangeouts3) {
        this.exchangeouts3 = exchangeouts3;
    }

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataitems() {
        return exsiccataitems;
    }

    public void setExsiccataitems(Collection<Exsiccataitem> exsiccataitems) {
        this.exsiccataitems = exsiccataitems;
    }

    @XmlTransient
    public Collection<Exsiccataitem> getExsiccataitems1() {
        return exsiccataitems1;
    }

    public void setExsiccataitems1(Collection<Exsiccataitem> exsiccataitems1) {
        this.exsiccataitems1 = exsiccataitems1;
    }

    @XmlTransient
    public Collection<Exsiccata> getExsiccatas() {
        return exsiccatas;
    }

    public void setExsiccatas(Collection<Exsiccata> exsiccatas) {
        this.exsiccatas = exsiccatas;
    }

    @XmlTransient
    public Collection<Exsiccata> getExsiccatas1() {
        return exsiccatas1;
    }

    public void setExsiccatas1(Collection<Exsiccata> exsiccatas1) {
        this.exsiccatas1 = exsiccatas1;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldnotebookattachments() {
        return fieldnotebookattachments;
    }

    public void setFieldnotebookattachments(Collection<Fieldnotebookattachment> fieldnotebookattachments) {
        this.fieldnotebookattachments = fieldnotebookattachments;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldnotebookattachments1() {
        return fieldnotebookattachments1;
    }

    public void setFieldnotebookattachments1(Collection<Fieldnotebookattachment> fieldnotebookattachments1) {
        this.fieldnotebookattachments1 = fieldnotebookattachments1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldnotebookpageattachments() {
        return fieldnotebookpageattachments;
    }

    public void setFieldnotebookpageattachments(Collection<Fieldnotebookpageattachment> fieldnotebookpageattachments) {
        this.fieldnotebookpageattachments = fieldnotebookpageattachments;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldnotebookpageattachments1() {
        return fieldnotebookpageattachments1;
    }

    public void setFieldnotebookpageattachments1(Collection<Fieldnotebookpageattachment> fieldnotebookpageattachments1) {
        this.fieldnotebookpageattachments1 = fieldnotebookpageattachments1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpages() {
        return fieldnotebookpages;
    }

    public void setFieldnotebookpages(Collection<Fieldnotebookpage> fieldnotebookpages) {
        this.fieldnotebookpages = fieldnotebookpages;
    }

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpages1() {
        return fieldnotebookpages1;
    }

    public void setFieldnotebookpages1(Collection<Fieldnotebookpage> fieldnotebookpages1) {
        this.fieldnotebookpages1 = fieldnotebookpages1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachments() {
        return fieldnotebookpagesetattachments;
    }

    public void setFieldnotebookpagesetattachments(Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachments) {
        this.fieldnotebookpagesetattachments = fieldnotebookpagesetattachments;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachments1() {
        return fieldnotebookpagesetattachments1;
    }

    public void setFieldnotebookpagesetattachments1(Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachments1) {
        this.fieldnotebookpagesetattachments1 = fieldnotebookpagesetattachments1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesets() {
        return fieldnotebookpagesets;
    }

    public void setFieldnotebookpagesets(Collection<Fieldnotebookpageset> fieldnotebookpagesets) {
        this.fieldnotebookpagesets = fieldnotebookpagesets;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesets1() {
        return fieldnotebookpagesets1;
    }

    public void setFieldnotebookpagesets1(Collection<Fieldnotebookpageset> fieldnotebookpagesets1) {
        this.fieldnotebookpagesets1 = fieldnotebookpagesets1;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesets2() {
        return fieldnotebookpagesets2;
    }

    public void setFieldnotebookpagesets2(Collection<Fieldnotebookpageset> fieldnotebookpagesets2) {
        this.fieldnotebookpagesets2 = fieldnotebookpagesets2;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebooks() {
        return fieldnotebooks;
    }

    public void setFieldnotebooks(Collection<Fieldnotebook> fieldnotebooks) {
        this.fieldnotebooks = fieldnotebooks;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebooks1() {
        return fieldnotebooks1;
    }

    public void setFieldnotebooks1(Collection<Fieldnotebook> fieldnotebooks1) {
        this.fieldnotebooks1 = fieldnotebooks1;
    }

    @XmlTransient
    public Collection<Fieldnotebook> getFieldnotebooks2() {
        return fieldnotebooks2;
    }

    public void setFieldnotebooks2(Collection<Fieldnotebook> fieldnotebooks2) {
        this.fieldnotebooks2 = fieldnotebooks2;
    }

    @XmlTransient
    public Collection<Geocoorddetail> getGeocoorddetails() {
        return geocoorddetails;
    }

    public void setGeocoorddetails(Collection<Geocoorddetail> geocoorddetails) {
        this.geocoorddetails = geocoorddetails;
    }

    @XmlTransient
    public Collection<Geocoorddetail> getGeocoorddetails1() {
        return geocoorddetails1;
    }

    public void setGeocoorddetails1(Collection<Geocoorddetail> geocoorddetails1) {
        this.geocoorddetails1 = geocoorddetails1;
    }

    @XmlTransient
    public Collection<Geocoorddetail> getGeocoorddetails2() {
        return geocoorddetails2;
    }

    public void setGeocoorddetails2(Collection<Geocoorddetail> geocoorddetails2) {
        this.geocoorddetails2 = geocoorddetails2;
    }

    @XmlTransient
    public Collection<Geography> getGeographys() {
        return geographys;
    }

    public void setGeographys(Collection<Geography> geographys) {
        this.geographys = geographys;
    }

    @XmlTransient
    public Collection<Geography> getGeographys1() {
        return geographys1;
    }

    public void setGeographys1(Collection<Geography> geographys1) {
        this.geographys1 = geographys1;
    }

    @XmlTransient
    public Collection<Geographytreedefitem> getGeographytreedefitems() {
        return geographytreedefitems;
    }

    public void setGeographytreedefitems(Collection<Geographytreedefitem> geographytreedefitems) {
        this.geographytreedefitems = geographytreedefitems;
    }

    @XmlTransient
    public Collection<Geographytreedefitem> getGeographytreedefitems1() {
        return geographytreedefitems1;
    }

    public void setGeographytreedefitems1(Collection<Geographytreedefitem> geographytreedefitems1) {
        this.geographytreedefitems1 = geographytreedefitems1;
    }

    @XmlTransient
    public Collection<Geographytreedef> getGeographytreedefs() {
        return geographytreedefs;
    }

    public void setGeographytreedefs(Collection<Geographytreedef> geographytreedefs) {
        this.geographytreedefs = geographytreedefs;
    }

    @XmlTransient
    public Collection<Geographytreedef> getGeographytreedefs1() {
        return geographytreedefs1;
    }

    public void setGeographytreedefs1(Collection<Geographytreedef> geographytreedefs1) {
        this.geographytreedefs1 = geographytreedefs1;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getGeologictimeperiods() {
        return geologictimeperiods;
    }

    public void setGeologictimeperiods(Collection<Geologictimeperiod> geologictimeperiods) {
        this.geologictimeperiods = geologictimeperiods;
    }

    @XmlTransient
    public Collection<Geologictimeperiod> getGeologictimeperiods1() {
        return geologictimeperiods1;
    }

    public void setGeologictimeperiods1(Collection<Geologictimeperiod> geologictimeperiods1) {
        this.geologictimeperiods1 = geologictimeperiods1;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getGeologictimeperiodtreedefitems() {
        return geologictimeperiodtreedefitems;
    }

    public void setGeologictimeperiodtreedefitems(Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitems) {
        this.geologictimeperiodtreedefitems = geologictimeperiodtreedefitems;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getGeologictimeperiodtreedefitems1() {
        return geologictimeperiodtreedefitems1;
    }

    public void setGeologictimeperiodtreedefitems1(Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitems1) {
        this.geologictimeperiodtreedefitems1 = geologictimeperiodtreedefitems1;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedef> getGeologictimeperiodtreedefs() {
        return geologictimeperiodtreedefs;
    }

    public void setGeologictimeperiodtreedefs(Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefs) {
        this.geologictimeperiodtreedefs = geologictimeperiodtreedefs;
    }

    @XmlTransient
    public Collection<Geologictimeperiodtreedef> getGeologictimeperiodtreedefs1() {
        return geologictimeperiodtreedefs1;
    }

    public void setGeologictimeperiodtreedefs1(Collection<Geologictimeperiodtreedef> geologictimeperiodtreedefs1) {
        this.geologictimeperiodtreedefs1 = geologictimeperiodtreedefs1;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagents() {
        return giftagents;
    }

    public void setGiftagents(Collection<Giftagent> giftagents) {
        this.giftagents = giftagents;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagents1() {
        return giftagents1;
    }

    public void setGiftagents1(Collection<Giftagent> giftagents1) {
        this.giftagents1 = giftagents1;
    }

    @XmlTransient
    public Collection<Giftagent> getGiftagents2() {
        return giftagents2;
    }

    public void setGiftagents2(Collection<Giftagent> giftagents2) {
        this.giftagents2 = giftagents2;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftpreparations() {
        return giftpreparations;
    }

    public void setGiftpreparations(Collection<Giftpreparation> giftpreparations) {
        this.giftpreparations = giftpreparations;
    }

    @XmlTransient
    public Collection<Giftpreparation> getGiftpreparations1() {
        return giftpreparations1;
    }

    public void setGiftpreparations1(Collection<Giftpreparation> giftpreparations1) {
        this.giftpreparations1 = giftpreparations1;
    }

    @XmlTransient
    public Collection<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Collection<Gift> gifts) {
        this.gifts = gifts;
    }

    @XmlTransient
    public Collection<Gift> getGifts1() {
        return gifts1;
    }

    public void setGifts1(Collection<Gift> gifts1) {
        this.gifts1 = gifts1;
    }

    @XmlTransient
    public Collection<Groupperson> getGroups1() {
        return groups1;
    }

    public void setGroups1(Collection<Groupperson> groups1) {
        this.groups1 = groups1;
    }

    @XmlTransient
    public Collection<Groupperson> getGroups2() {
        return groups2;
    }

    public void setGroups2(Collection<Groupperson> groups2) {
        this.groups2 = groups2;
    }

    @XmlTransient
    public Collection<Inforequest> getInforequests() {
        return inforequests;
    }

    public void setInforequests(Collection<Inforequest> inforequests) {
        this.inforequests = inforequests;
    }

    @XmlTransient
    public Collection<Inforequest> getInforequests1() {
        return inforequests1;
    }

    public void setInforequests1(Collection<Inforequest> inforequests1) {
        this.inforequests1 = inforequests1;
    }

    @XmlTransient
    public Collection<Inforequest> getInforequests2() {
        return inforequests2;
    }

    public void setInforequests2(Collection<Inforequest> inforequests2) {
        this.inforequests2 = inforequests2;
    }

    @XmlTransient
    public Collection<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Collection<Institution> institutions) {
        this.institutions = institutions;
    }

    @XmlTransient
    public Collection<Institution> getInstitutions1() {
        return institutions1;
    }

    public void setInstitutions1(Collection<Institution> institutions1) {
        this.institutions1 = institutions1;
    }

    @XmlTransient
    public Collection<Journal> getJournals() {
        return journals;
    }

    public void setJournals(Collection<Journal> journals) {
        this.journals = journals;
    }

    @XmlTransient
    public Collection<Journal> getJournals1() {
        return journals1;
    }

    public void setJournals1(Collection<Journal> journals1) {
        this.journals1 = journals1;
    }

    @XmlTransient
    public Collection<Lithostrat> getLithostrats() {
        return lithostrats;
    }

    public void setLithostrats(Collection<Lithostrat> lithostrats) {
        this.lithostrats = lithostrats;
    }

    @XmlTransient
    public Collection<Lithostrat> getLithostrats1() {
        return lithostrats1;
    }

    public void setLithostrats1(Collection<Lithostrat> lithostrats1) {
        this.lithostrats1 = lithostrats1;
    }

    @XmlTransient
    public Collection<Lithostrattreedefitem> getLithostrattreedefitems() {
        return lithostrattreedefitems;
    }

    public void setLithostrattreedefitems(Collection<Lithostrattreedefitem> lithostrattreedefitems) {
        this.lithostrattreedefitems = lithostrattreedefitems;
    }

    @XmlTransient
    public Collection<Lithostrattreedefitem> getLithostrattreedefitems1() {
        return lithostrattreedefitems1;
    }

    public void setLithostrattreedefitems1(Collection<Lithostrattreedefitem> lithostrattreedefitems1) {
        this.lithostrattreedefitems1 = lithostrattreedefitems1;
    }

    @XmlTransient
    public Collection<Lithostrattreedef> getLithostrattreedefs() {
        return lithostrattreedefs;
    }

    public void setLithostrattreedefs(Collection<Lithostrattreedef> lithostrattreedefs) {
        this.lithostrattreedefs = lithostrattreedefs;
    }

    @XmlTransient
    public Collection<Lithostrattreedef> getLithostrattreedefs1() {
        return lithostrattreedefs1;
    }

    public void setLithostrattreedefs1(Collection<Lithostrattreedef> lithostrattreedefs1) {
        this.lithostrattreedefs1 = lithostrattreedefs1;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagents() {
        return loanagents;
    }

    public void setLoanagents(Collection<Loanagent> loanagents) {
        this.loanagents = loanagents;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagents1() {
        return loanagents1;
    }

    public void setLoanagents1(Collection<Loanagent> loanagents1) {
        this.loanagents1 = loanagents1;
    }

    @XmlTransient
    public Collection<Loanagent> getLoanagents2() {
        return loanagents2;
    }

    public void setLoanagents2(Collection<Loanagent> loanagents2) {
        this.loanagents2 = loanagents2;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanattachments() {
        return loanattachments;
    }

    public void setLoanattachments(Collection<Loanattachment> loanattachments) {
        this.loanattachments = loanattachments;
    }

    @XmlTransient
    public Collection<Loanattachment> getLoanattachments1() {
        return loanattachments1;
    }

    public void setLoanattachments1(Collection<Loanattachment> loanattachments1) {
        this.loanattachments1 = loanattachments1;
    }

    @XmlTransient
    public Collection<Loanpreparation> getLoanpreparations() {
        return loanpreparations;
    }

    public void setLoanpreparations(Collection<Loanpreparation> loanpreparations) {
        this.loanpreparations = loanpreparations;
    }

    @XmlTransient
    public Collection<Loanpreparation> getLoanpreparations1() {
        return loanpreparations1;
    }

    public void setLoanpreparations1(Collection<Loanpreparation> loanpreparations1) {
        this.loanpreparations1 = loanpreparations1;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparations() {
        return loanreturnpreparations;
    }

    public void setLoanreturnpreparations(Collection<Loanreturnpreparation> loanreturnpreparations) {
        this.loanreturnpreparations = loanreturnpreparations;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparations1() {
        return loanreturnpreparations1;
    }

    public void setLoanreturnpreparations1(Collection<Loanreturnpreparation> loanreturnpreparations1) {
        this.loanreturnpreparations1 = loanreturnpreparations1;
    }

    @XmlTransient
    public Collection<Loanreturnpreparation> getLoanreturnpreparations2() {
        return loanreturnpreparations2;
    }

    public void setLoanreturnpreparations2(Collection<Loanreturnpreparation> loanreturnpreparations2) {
        this.loanreturnpreparations2 = loanreturnpreparations2;
    }

    @XmlTransient
    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    @XmlTransient
    public Collection<Loan> getLoans1() {
        return loans1;
    }

    public void setLoans1(Collection<Loan> loans1) {
        this.loans1 = loans1;
    }

    @XmlTransient
    public Collection<Locality> getLocalities() {
        return localities;
    }

    public void setLocalities(Collection<Locality> localities) {
        this.localities = localities;
    }

    @XmlTransient
    public Collection<Locality> getLocalities1() {
        return localities1;
    }

    public void setLocalities1(Collection<Locality> localities1) {
        this.localities1 = localities1;
    }

    @XmlTransient
    public Collection<Localityattachment> getLocalityattachments() {
        return localityattachments;
    }

    public void setLocalityattachments(Collection<Localityattachment> localityattachments) {
        this.localityattachments = localityattachments;
    }

    @XmlTransient
    public Collection<Localityattachment> getLocalityattachments1() {
        return localityattachments1;
    }

    public void setLocalityattachments1(Collection<Localityattachment> localityattachments1) {
        this.localityattachments1 = localityattachments1;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitations() {
        return localitycitations;
    }

    public void setLocalitycitations(Collection<Localitycitation> localitycitations) {
        this.localitycitations = localitycitations;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitations1() {
        return localitycitations1;
    }

    public void setLocalitycitations1(Collection<Localitycitation> localitycitations1) {
        this.localitycitations1 = localitycitations1;
    }

    @XmlTransient
    public Collection<Localitydetail> getLocalitydetails() {
        return localitydetails;
    }

    public void setLocalitydetails(Collection<Localitydetail> localitydetails) {
        this.localitydetails = localitydetails;
    }

    @XmlTransient
    public Collection<Localitydetail> getLocalitydetails1() {
        return localitydetails1;
    }

    public void setLocalitydetails1(Collection<Localitydetail> localitydetails1) {
        this.localitydetails1 = localitydetails1;
    }

    @XmlTransient
    public Collection<Localitynamealias> getLocalitynamealiases() {
        return localitynamealiases;
    }

    public void setLocalitynamealiases(Collection<Localitynamealias> localitynamealiases) {
        this.localitynamealiases = localitynamealiases;
    }

    @XmlTransient
    public Collection<Localitynamealias> getLocalitynamealiases1() {
        return localitynamealiases1;
    }

    public void setLocalitynamealiases1(Collection<Localitynamealias> localitynamealiases1) {
        this.localitynamealiases1 = localitynamealiases1;
    }

    @XmlTransient
    public Collection<Groupperson> getMembers() {
        return members;
    }

    public void setMembers(Collection<Groupperson> members) {
        this.members = members;
    }

    @XmlTransient
    public Collection<Otheridentifier> getOtheridentifiers() {
        return otheridentifiers;
    }

    public void setOtheridentifiers(Collection<Otheridentifier> otheridentifiers) {
        this.otheridentifiers = otheridentifiers;
    }

    @XmlTransient
    public Collection<Otheridentifier> getOtheridentifiers1() {
        return otheridentifiers1;
    }

    public void setOtheridentifiers1(Collection<Otheridentifier> otheridentifiers1) {
        this.otheridentifiers1 = otheridentifiers1;
    }

    @XmlTransient
    public Collection<Paleocontext> getPaleocontexts() {
        return paleocontexts;
    }

    public void setPaleocontexts(Collection<Paleocontext> paleocontexts) {
        this.paleocontexts = paleocontexts;
    }

    @XmlTransient
    public Collection<Paleocontext> getPaleocontexts1() {
        return paleocontexts1;
    }

    public void setPaleocontexts1(Collection<Paleocontext> paleocontexts1) {
        this.paleocontexts1 = paleocontexts1;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitattachments() {
        return permitattachments;
    }

    public void setPermitattachments(Collection<Permitattachment> permitattachments) {
        this.permitattachments = permitattachments;
    }

    @XmlTransient
    public Collection<Permitattachment> getPermitattachments1() {
        return permitattachments1;
    }

    public void setPermitattachments1(Collection<Permitattachment> permitattachments1) {
        this.permitattachments1 = permitattachments1;
    }

    @XmlTransient
    public Collection<Permit> getPermits() {
        return permits;
    }

    public void setPermits(Collection<Permit> permits) {
        this.permits = permits;
    }

    @XmlTransient
    public Collection<Permit> getPermits1() {
        return permits1;
    }

    public void setPermits1(Collection<Permit> permits1) {
        this.permits1 = permits1;
    }

    @XmlTransient
    public Collection<Permit> getPermits2() {
        return permits2;
    }

    public void setPermits2(Collection<Permit> permits2) {
        this.permits2 = permits2;
    }

    @XmlTransient
    public Collection<Permit> getPermits3() {
        return permits3;
    }

    public void setPermits3(Collection<Permit> permits3) {
        this.permits3 = permits3;
    }

    @XmlTransient
    public Collection<Picklistitem> getPicklistitems() {
        return picklistitems;
    }

    public void setPicklistitems(Collection<Picklistitem> picklistitems) {
        this.picklistitems = picklistitems;
    }

    @XmlTransient
    public Collection<Picklistitem> getPicklistitems1() {
        return picklistitems1;
    }

    public void setPicklistitems1(Collection<Picklistitem> picklistitems1) {
        this.picklistitems1 = picklistitems1;
    }

    @XmlTransient
    public Collection<Picklist> getPicklists() {
        return picklists;
    }

    public void setPicklists(Collection<Picklist> picklists) {
        this.picklists = picklists;
    }

    @XmlTransient
    public Collection<Picklist> getPicklists1() {
        return picklists1;
    }

    public void setPicklists1(Collection<Picklist> picklists1) {
        this.picklists1 = picklists1;
    }

    @XmlTransient
    public Collection<Preparationattachment> getPreparationattachments() {
        return preparationattachments;
    }

    public void setPreparationattachments(Collection<Preparationattachment> preparationattachments) {
        this.preparationattachments = preparationattachments;
    }

    @XmlTransient
    public Collection<Preparationattachment> getPreparationattachments1() {
        return preparationattachments1;
    }

    public void setPreparationattachments1(Collection<Preparationattachment> preparationattachments1) {
        this.preparationattachments1 = preparationattachments1;
    }

    @XmlTransient
    public Collection<Preparationattribute> getPreparationattributes() {
        return preparationattributes;
    }

    public void setPreparationattributes(Collection<Preparationattribute> preparationattributes) {
        this.preparationattributes = preparationattributes;
    }

    @XmlTransient
    public Collection<Preparationattribute> getPreparationattributes1() {
        return preparationattributes1;
    }

    public void setPreparationattributes1(Collection<Preparationattribute> preparationattributes1) {
        this.preparationattributes1 = preparationattributes1;
    }

    @XmlTransient
    public Collection<Preparationattr> getPreparationattrs() {
        return preparationattrs;
    }

    public void setPreparationattrs(Collection<Preparationattr> preparationattrs) {
        this.preparationattrs = preparationattrs;
    }

    @XmlTransient
    public Collection<Preparationattr> getPreparationattrs1() {
        return preparationattrs1;
    }

    public void setPreparationattrs1(Collection<Preparationattr> preparationattrs1) {
        this.preparationattrs1 = preparationattrs1;
    }

    @XmlTransient
    public Collection<Preparation> getPreparations() {
        return preparations;
    }

    public void setPreparations(Collection<Preparation> preparations) {
        this.preparations = preparations;
    }

    @XmlTransient
    public Collection<Preparation> getPreparations1() {
        return preparations1;
    }

    public void setPreparations1(Collection<Preparation> preparations1) {
        this.preparations1 = preparations1;
    }

    @XmlTransient
    public Collection<Preparation> getPreparations2() {
        return preparations2;
    }

    public void setPreparations2(Collection<Preparation> preparations2) {
        this.preparations2 = preparations2;
    }

    @XmlTransient
    public Collection<Preptype> getPreptypes() {
        return preptypes;
    }

    public void setPreptypes(Collection<Preptype> preptypes) {
        this.preptypes = preptypes;
    }

    @XmlTransient
    public Collection<Preptype> getPreptypes1() {
        return preptypes1;
    }

    public void setPreptypes1(Collection<Preptype> preptypes1) {
        this.preptypes1 = preptypes1;
    }

    @XmlTransient
    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    
    @XmlTransient
    public Collection<Project> getProjects1() {
        return projects1;
    }

    public void setProjects1(Collection<Project> projects1) {
        this.projects1 = projects1;
    }

    @XmlTransient
    public Collection<Project> getProjects2() {
        return projects2;
    }

    public void setProjects2(Collection<Project> projects2) {
        this.projects2 = projects2;
    }

    @XmlTransient
    public Collection<Recordset> getRecordsets() {
        return recordsets;
    }

    public void setRecordsets(Collection<Recordset> recordsets) {
        this.recordsets = recordsets;
    }

    @XmlTransient
    public Collection<Recordset> getRecordsets1() {
        return recordsets1;
    }

    public void setRecordsets1(Collection<Recordset> recordsets1) {
        this.recordsets1 = recordsets1;
    }

    @XmlTransient
    public Collection<Referencework> getReferenceworks() {
        return referenceworks;
    }

    public void setReferenceworks(Collection<Referencework> referenceworks) {
        this.referenceworks = referenceworks;
    }

    @XmlTransient
    public Collection<Referencework> getReferenceworks1() {
        return referenceworks1;
    }

    public void setReferenceworks1(Collection<Referencework> referenceworks1) {
        this.referenceworks1 = referenceworks1;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryagreementattachments() {
        return repositoryagreementattachments;
    }

    public void setRepositoryagreementattachments(Collection<Repositoryagreementattachment> repositoryagreementattachments) {
        this.repositoryagreementattachments = repositoryagreementattachments;
    }

    @XmlTransient
    public Collection<Repositoryagreementattachment> getRepositoryagreementattachments1() {
        return repositoryagreementattachments1;
    }

    public void setRepositoryagreementattachments1(Collection<Repositoryagreementattachment> repositoryagreementattachments1) {
        this.repositoryagreementattachments1 = repositoryagreementattachments1;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreements() {
        return repositoryagreements;
    }

    public void setRepositoryagreements(Collection<Repositoryagreement> repositoryagreements) {
        this.repositoryagreements = repositoryagreements;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreements1() {
        return repositoryagreements1;
    }

    public void setRepositoryagreements1(Collection<Repositoryagreement> repositoryagreements1) {
        this.repositoryagreements1 = repositoryagreements1;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreements2() {
        return repositoryagreements2;
    }

    public void setRepositoryagreements2(Collection<Repositoryagreement> repositoryagreements2) {
        this.repositoryagreements2 = repositoryagreements2;
    }

    @XmlTransient
    public Collection<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(Collection<Shipment> shipments) {
        this.shipments = shipments;
    }

    @XmlTransient
    public Collection<Shipment> getShipments1() {
        return shipments1;
    }

    public void setShipments1(Collection<Shipment> shipments1) {
        this.shipments1 = shipments1;
    }

    @XmlTransient
    public Collection<Shipment> getShipments2() {
        return shipments2;
    }

    public void setShipments2(Collection<Shipment> shipments2) {
        this.shipments2 = shipments2;
    }

    @XmlTransient
    public Collection<Shipment> getShipments3() {
        return shipments3;
    }

    public void setShipments3(Collection<Shipment> shipments3) {
        this.shipments3 = shipments3;
    }

    @XmlTransient
    public Collection<Shipment> getShipments4() {
        return shipments4;
    }

    public void setShipments4(Collection<Shipment> shipments4) {
        this.shipments4 = shipments4;
    }

    @XmlTransient
    public Collection<Spappresourcedata> getSpappresourcedata() {
        return spappresourcedata;
    }

    public void setSpappresourcedata(Collection<Spappresourcedata> spappresourcedata) {
        this.spappresourcedata = spappresourcedata;
    }

    @XmlTransient
    public Collection<Spappresourcedata> getSpappresourcedata1() {
        return spappresourcedata1;
    }

    public void setSpappresourcedata1(Collection<Spappresourcedata> spappresourcedata1) {
        this.spappresourcedata1 = spappresourcedata1;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirs() {
        return spappresourcedirs;
    }

    public void setSpappresourcedirs(Collection<Spappresourcedir> spappresourcedirs) {
        this.spappresourcedirs = spappresourcedirs;
    }

    @XmlTransient
    public Collection<Spappresourcedir> getSpappresourcedirs1() {
        return spappresourcedirs1;
    }

    public void setSpappresourcedirs1(Collection<Spappresourcedir> spappresourcedirs1) {
        this.spappresourcedirs1 = spappresourcedirs1;
    }

    @XmlTransient
    public Collection<Spappresource> getSpappresources() {
        return spappresources;
    }

    public void setSpappresources(Collection<Spappresource> spappresources) {
        this.spappresources = spappresources;
    }

    @XmlTransient
    public Collection<Spappresource> getSpappresources1() {
        return spappresources1;
    }

    public void setSpappresources1(Collection<Spappresource> spappresources1) {
        this.spappresources1 = spappresources1;
    }

    @XmlTransient
    public Collection<Spauditlogfield> getSpauditlogfields() {
        return spauditlogfields;
    }

    public void setSpauditlogfields(Collection<Spauditlogfield> spauditlogfields) {
        this.spauditlogfields = spauditlogfields;
    }

    @XmlTransient
    public Collection<Spauditlogfield> getSpauditlogfields1() {
        return spauditlogfields1;
    }

    public void setSpauditlogfields1(Collection<Spauditlogfield> spauditlogfields1) {
        this.spauditlogfields1 = spauditlogfields1;
    }

    @XmlTransient
    public Collection<Spauditlog> getSpauditlogs() {
        return spauditlogs;
    }

    public void setSpauditlogs(Collection<Spauditlog> spauditlogs) {
        this.spauditlogs = spauditlogs;
    }

    @XmlTransient
    public Collection<Spauditlog> getSpauditlogs1() {
        return spauditlogs1;
    }

    public void setSpauditlogs1(Collection<Spauditlog> spauditlogs1) {
        this.spauditlogs1 = spauditlogs1;
    }

    @XmlTransient
    public Collection<Specifyuser> getSpecifyusers() {
        return specifyusers;
    }

    public void setSpecifyusers(Collection<Specifyuser> specifyusers) {
        this.specifyusers = specifyusers;
    }

    @XmlTransient
    public Collection<Specifyuser> getSpecifyusers1() {
        return specifyusers1;
    }

    public void setSpecifyusers1(Collection<Specifyuser> specifyusers1) {
        this.specifyusers1 = specifyusers1;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappings() {
        return spexportschemaitemmappings;
    }

    public void setSpexportschemaitemmappings(Collection<Spexportschemaitemmapping> spexportschemaitemmappings) {
        this.spexportschemaitemmappings = spexportschemaitemmappings;
    }

    @XmlTransient
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappings1() {
        return spexportschemaitemmappings1;
    }

    public void setSpexportschemaitemmappings1(Collection<Spexportschemaitemmapping> spexportschemaitemmappings1) {
        this.spexportschemaitemmappings1 = spexportschemaitemmappings1;
    }

    @XmlTransient
    public Collection<Spexportschemaitem> getSpexportschemaitems() {
        return spexportschemaitems;
    }

    public void setSpexportschemaitems(Collection<Spexportschemaitem> spexportschemaitems) {
        this.spexportschemaitems = spexportschemaitems;
    }

    @XmlTransient
    public Collection<Spexportschemaitem> getSpexportschemaitems1() {
        return spexportschemaitems1;
    }

    public void setSpexportschemaitems1(Collection<Spexportschemaitem> spexportschemaitems1) {
        this.spexportschemaitems1 = spexportschemaitems1;
    }

    @XmlTransient
    public Collection<Spexportschemamapping> getSpexportschemamappings() {
        return spexportschemamappings;
    }

    public void setSpexportschemamappings(Collection<Spexportschemamapping> spexportschemamappings) {
        this.spexportschemamappings = spexportschemamappings;
    }

    @XmlTransient
    public Collection<Spexportschemamapping> getSpexportschemamappings1() {
        return spexportschemamappings1;
    }

    public void setSpexportschemamappings1(Collection<Spexportschemamapping> spexportschemamappings1) {
        this.spexportschemamappings1 = spexportschemamappings1;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpexportschemas() {
        return spexportschemas;
    }

    public void setSpexportschemas(Collection<Spexportschema> spexportschemas) {
        this.spexportschemas = spexportschemas;
    }

    @XmlTransient
    public Collection<Spexportschema> getSpexportschemas1() {
        return spexportschemas1;
    }

    public void setSpexportschemas1(Collection<Spexportschema> spexportschemas1) {
        this.spexportschemas1 = spexportschemas1;
    }

    @XmlTransient
    public Collection<Spfieldvaluedefault> getSpfieldvaluedefaults() {
        return spfieldvaluedefaults;
    }

    public void setSpfieldvaluedefaults(Collection<Spfieldvaluedefault> spfieldvaluedefaults) {
        this.spfieldvaluedefaults = spfieldvaluedefaults;
    }

    @XmlTransient
    public Collection<Spfieldvaluedefault> getSpfieldvaluedefaults1() {
        return spfieldvaluedefaults1;
    }

    public void setSpfieldvaluedefaults1(Collection<Spfieldvaluedefault> spfieldvaluedefaults1) {
        this.spfieldvaluedefaults1 = spfieldvaluedefaults1;
    }

    @XmlTransient
    public Collection<Splocalecontaineritem> getSplocalecontaineritems() {
        return splocalecontaineritems;
    }

    public void setSplocalecontaineritems(Collection<Splocalecontaineritem> splocalecontaineritems) {
        this.splocalecontaineritems = splocalecontaineritems;
    }

    @XmlTransient
    public Collection<Splocalecontaineritem> getSplocalecontaineritems1() {
        return splocalecontaineritems1;
    }

    public void setSplocalecontaineritems1(Collection<Splocalecontaineritem> splocalecontaineritems1) {
        this.splocalecontaineritems1 = splocalecontaineritems1;
    }

    @XmlTransient
    public Collection<Splocalecontainer> getSplocalecontainers() {
        return splocalecontainers;
    }

    public void setSplocalecontainers(Collection<Splocalecontainer> splocalecontainers) {
        this.splocalecontainers = splocalecontainers;
    }

    @XmlTransient
    public Collection<Splocalecontainer> getSplocalecontainers1() {
        return splocalecontainers1;
    }

    public void setSplocalecontainers1(Collection<Splocalecontainer> splocalecontainers1) {
        this.splocalecontainers1 = splocalecontainers1;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getSplocaleitemstrs() {
        return splocaleitemstrs;
    }

    public void setSplocaleitemstrs(Collection<Splocaleitemstr> splocaleitemstrs) {
        this.splocaleitemstrs = splocaleitemstrs;
    }

    @XmlTransient
    public Collection<Splocaleitemstr> getSplocaleitemstrs1() {
        return splocaleitemstrs1;
    }

    public void setSplocaleitemstrs1(Collection<Splocaleitemstr> splocaleitemstrs1) {
        this.splocaleitemstrs1 = splocaleitemstrs1;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpprincipals() {
        return spprincipals;
    }

    public void setSpprincipals(Collection<Spprincipal> spprincipals) {
        this.spprincipals = spprincipals;
    }

    @XmlTransient
    public Collection<Spprincipal> getSpprincipals1() {
        return spprincipals1;
    }

    public void setSpprincipals1(Collection<Spprincipal> spprincipals1) {
        this.spprincipals1 = spprincipals1;
    }

    @XmlTransient
    public Collection<Spqueryfield> getSpqueryfields() {
        return spqueryfields;
    }

    public void setSpqueryfields(Collection<Spqueryfield> spqueryfields) {
        this.spqueryfields = spqueryfields;
    }

    @XmlTransient
    public Collection<Spqueryfield> getSpqueryfields1() {
        return spqueryfields1;
    }

    public void setSpqueryfields1(Collection<Spqueryfield> spqueryfields1) {
        this.spqueryfields1 = spqueryfields1;
    }

    @XmlTransient
    public Collection<Spquery> getSpquerys() {
        return spquerys;
    }

    public void setSpquerys(Collection<Spquery> spquerys) {
        this.spquerys = spquerys;
    }

    @XmlTransient
    public Collection<Spquery> getSpquerys1() {
        return spquerys1;
    }

    public void setSpquerys1(Collection<Spquery> spquerys1) {
        this.spquerys1 = spquerys1;
    }

    @XmlTransient
    public Collection<Spreport> getSpreports() {
        return spreports;
    }

    public void setSpreports(Collection<Spreport> spreports) {
        this.spreports = spreports;
    }

    @XmlTransient
    public Collection<Spreport> getSpreports1() {
        return spreports1;
    }

    public void setSpreports1(Collection<Spreport> spreports1) {
        this.spreports1 = spreports1;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphores() {
        return sptasksemaphores;
    }

    public void setSptasksemaphores(Collection<Sptasksemaphore> sptasksemaphores) {
        this.sptasksemaphores = sptasksemaphores;
    }

    @XmlTransient
    public Collection<Sptasksemaphore> getSptasksemaphores1() {
        return sptasksemaphores1;
    }

    public void setSptasksemaphores1(Collection<Sptasksemaphore> sptasksemaphores1) {
        this.sptasksemaphores1 = sptasksemaphores1;
    }

    @XmlTransient
    public Collection<Spversion> getSpversions() {
        return spversions;
    }

    public void setSpversions(Collection<Spversion> spversions) {
        this.spversions = spversions;
    }

    @XmlTransient
    public Collection<Spversion> getSpversions1() {
        return spversions1;
    }

    public void setSpversions1(Collection<Spversion> spversions1) {
        this.spversions1 = spversions1;
    }

    @XmlTransient
    public Collection<Spviewsetobj> getSpviewsetobjs() {
        return spviewsetobjs;
    }

    public void setSpviewsetobjs(Collection<Spviewsetobj> spviewsetobjs) {
        this.spviewsetobjs = spviewsetobjs;
    }

    @XmlTransient
    public Collection<Spviewsetobj> getSpviewsetobjs1() {
        return spviewsetobjs1;
    }

    public void setSpviewsetobjs1(Collection<Spviewsetobj> spviewsetobjs1) {
        this.spviewsetobjs1 = spviewsetobjs1;
    }

    @XmlTransient
    public Collection<Storage> getStorages() {
        return storages;
    }

    public void setStorages(Collection<Storage> storages) {
        this.storages = storages;
    }

    @XmlTransient
    public Collection<Storage> getStorages1() {
        return storages1;
    }

    public void setStorages1(Collection<Storage> storages1) {
        this.storages1 = storages1;
    }

    @XmlTransient
    public Collection<Storagetreedefitem> getStoragetreedefitems() {
        return storagetreedefitems;
    }

    public void setStoragetreedefitems(Collection<Storagetreedefitem> storagetreedefitems) {
        this.storagetreedefitems = storagetreedefitems;
    }

    @XmlTransient
    public Collection<Storagetreedefitem> getStoragetreedefitems1() {
        return storagetreedefitems1;
    }

    public void setStoragetreedefitems1(Collection<Storagetreedefitem> storagetreedefitems1) {
        this.storagetreedefitems1 = storagetreedefitems1;
    }

    @XmlTransient
    public Collection<Storagetreedef> getStoragetreedefs() {
        return storagetreedefs;
    }

    public void setStoragetreedefs(Collection<Storagetreedef> storagetreedefs) {
        this.storagetreedefs = storagetreedefs;
    }

    @XmlTransient
    public Collection<Storagetreedef> getStoragetreedefs1() {
        return storagetreedefs1;
    }

    public void setStoragetreedefs1(Collection<Storagetreedef> storagetreedefs1) {
        this.storagetreedefs1 = storagetreedefs1;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonattachments() {
        return taxonattachments;
    }

    public void setTaxonattachments(Collection<Taxonattachment> taxonattachments) {
        this.taxonattachments = taxonattachments;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonattachments1() {
        return taxonattachments1;
    }

    public void setTaxonattachments1(Collection<Taxonattachment> taxonattachments1) {
        this.taxonattachments1 = taxonattachments1;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxoncitations() {
        return taxoncitations;
    }

    public void setTaxoncitations(Collection<Taxoncitation> taxoncitations) {
        this.taxoncitations = taxoncitations;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxoncitations1() {
        return taxoncitations1;
    }

    public void setTaxoncitations1(Collection<Taxoncitation> taxoncitations1) {
        this.taxoncitations1 = taxoncitations1;
    }

    @XmlTransient
    public Collection<Taxon> getTaxons() {
        return taxons;
    }

    public void setTaxons(Collection<Taxon> taxons) {
        this.taxons = taxons;
    }

    @XmlTransient
    public Collection<Taxon> getTaxons1() {
        return taxons1;
    }

    public void setTaxons1(Collection<Taxon> taxons1) {
        this.taxons1 = taxons1;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTaxontreedefitems() {
        return taxontreedefitems;
    }

    public void setTaxontreedefitems(Collection<Taxontreedefitem> taxontreedefitems) {
        this.taxontreedefitems = taxontreedefitems;
    }

    @XmlTransient
    public Collection<Taxontreedefitem> getTaxontreedefitems1() {
        return taxontreedefitems1;
    }

    public void setTaxontreedefitems1(Collection<Taxontreedefitem> taxontreedefitems1) {
        this.taxontreedefitems1 = taxontreedefitems1;
    }

    @XmlTransient
    public Collection<Taxontreedef> getTaxontreedefs() {
        return taxontreedefs;
    }

    public void setTaxontreedefs(Collection<Taxontreedef> taxontreedefs) {
        this.taxontreedefs = taxontreedefs;
    }

    @XmlTransient
    public Collection<Taxontreedef> getTaxontreedefs1() {
        return taxontreedefs1;
    }

    public void setTaxontreedefs1(Collection<Taxontreedef> taxontreedefs1) {
        this.taxontreedefs1 = taxontreedefs1;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmentevents3() {
        return treatmentevents3;
    }

    public void setTreatmentevents3(Collection<Treatmentevent> treatmentevents3) {
        this.treatmentevents3 = treatmentevents3;
    }

    @XmlTransient
    public Collection<Treatmentevent> getTreatmentevents4() {
        return treatmentevents4;
    }

    public void setTreatmentevents4(Collection<Treatmentevent> treatmentevents4) {
        this.treatmentevents4 = treatmentevents4;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchs() {
        return workbenchs;
    }

    public void setWorkbenchs(Collection<Workbench> workbenchs) {
        this.workbenchs = workbenchs;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenchs1() {
        return workbenchs1;
    }

    public void setWorkbenchs1(Collection<Workbench> workbenchs1) {
        this.workbenchs1 = workbenchs1;
    }

    @XmlTransient
    public Collection<Workbenchtemplatemappingitem> getWorkbenchtemplatemappingitems() {
        return workbenchtemplatemappingitems;
    }

    public void setWorkbenchtemplatemappingitems(Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitems) {
        this.workbenchtemplatemappingitems = workbenchtemplatemappingitems;
    }

    @XmlTransient
    public Collection<Workbenchtemplatemappingitem> getWorkbenchtemplatemappingitems1() {
        return workbenchtemplatemappingitems1;
    }

    public void setWorkbenchtemplatemappingitems1(Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitems1) {
        this.workbenchtemplatemappingitems1 = workbenchtemplatemappingitems1;
    }

    @XmlTransient
    public Collection<Workbenchtemplate> getWorkbenchtemplates() {
        return workbenchtemplates;
    }

    public void setWorkbenchtemplates(Collection<Workbenchtemplate> workbenchtemplates) {
        this.workbenchtemplates = workbenchtemplates;
    }

    @XmlTransient
    public Collection<Workbenchtemplate> getWorkbenchtemplates1() {
        return workbenchtemplates1;
    }

    public void setWorkbenchtemplates1(Collection<Workbenchtemplate> workbenchtemplates1) {
        this.workbenchtemplates1 = workbenchtemplates1;
    }

  
   

   
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentId != null ? agentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        if ((this.agentId == null && other.agentId != null) || (this.agentId != null && !this.agentId.equals(other.agentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agent[ agentId=" + agentId + " ]";
    }
    
}

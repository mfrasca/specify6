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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "referencework")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Referencework.findAll", query = "SELECT r FROM Referencework r"),
    @NamedQuery(name = "Referencework.findByReferenceWorkID", query = "SELECT r FROM Referencework r WHERE r.referenceWorkId = :referenceWorkID"),
    @NamedQuery(name = "Referencework.findByTimestampCreated", query = "SELECT r FROM Referencework r WHERE r.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Referencework.findByTimestampModified", query = "SELECT r FROM Referencework r WHERE r.timestampModified = :timestampModified"),
    @NamedQuery(name = "Referencework.findByVersion", query = "SELECT r FROM Referencework r WHERE r.version = :version"),
    @NamedQuery(name = "Referencework.findByGuid", query = "SELECT r FROM Referencework r WHERE r.guid = :guid"),
    @NamedQuery(name = "Referencework.findByIsPublished", query = "SELECT r FROM Referencework r WHERE r.isPublished = :isPublished"),
    @NamedQuery(name = "Referencework.findByIsbn", query = "SELECT r FROM Referencework r WHERE r.isbn = :isbn"),
    @NamedQuery(name = "Referencework.findByLibraryNumber", query = "SELECT r FROM Referencework r WHERE r.libraryNumber = :libraryNumber"),
    @NamedQuery(name = "Referencework.findByNumber1", query = "SELECT r FROM Referencework r WHERE r.number1 = :number1"),
    @NamedQuery(name = "Referencework.findByNumber2", query = "SELECT r FROM Referencework r WHERE r.number2 = :number2"),
    @NamedQuery(name = "Referencework.findByPages", query = "SELECT r FROM Referencework r WHERE r.pages = :pages"),
    @NamedQuery(name = "Referencework.findByPlaceOfPublication", query = "SELECT r FROM Referencework r WHERE r.placeOfPublication = :placeOfPublication"),
    @NamedQuery(name = "Referencework.findByPublisher", query = "SELECT r FROM Referencework r WHERE r.publisher = :publisher"),
    @NamedQuery(name = "Referencework.findByReferenceWorkType", query = "SELECT r FROM Referencework r WHERE r.referenceWorkType = :referenceWorkType"),
    @NamedQuery(name = "Referencework.findByTitle", query = "SELECT r FROM Referencework r WHERE r.title = :title"),
    @NamedQuery(name = "Referencework.findByVolume", query = "SELECT r FROM Referencework r WHERE r.volume = :volume"),
    @NamedQuery(name = "Referencework.findByWorkDate", query = "SELECT r FROM Referencework r WHERE r.workDate = :workDate"),
    @NamedQuery(name = "Referencework.findByYesNo1", query = "SELECT r FROM Referencework r WHERE r.yesNo1 = :yesNo1"),
    @NamedQuery(name = "Referencework.findByYesNo2", query = "SELECT r FROM Referencework r WHERE r.yesNo2 = :yesNo2")})
public class Referencework extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ReferenceWorkID")
    private Integer referenceWorkId;
     
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Column(name = "IsPublished")
    private Boolean isPublished;
    
    @Size(max = 16)
    @Column(name = "ISBN")
    private String isbn;
    
    @Size(max = 50)
    @Column(name = "LibraryNumber")
    private String libraryNumber;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Number1")
    private Float number1;
    
    @Column(name = "Number2")
    private Float number2;
    
    @Size(max = 50)
    @Column(name = "Pages")
    private String pages;
    
    @Size(max = 50)
    @Column(name = "PlaceOfPublication")
    private String placeOfPublication;
    
    @Size(max = 50)
    @Column(name = "Publisher")
    private String publisher;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReferenceWorkType")
    private short referenceWorkType;
    
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
    
    @Size(max = 255)
    @Column(name = "Title")
    private String title;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "URL")
    private String url;
    
    @Size(max = 25)
    @Column(name = "Volume")
    private String volume;
    
    @Size(max = 25)
    @Column(name = "WorkDate")
    private String workDate;
    
    @Column(name = "YesNo1")
    private Boolean yesNo1;
    
    @Column(name = "YesNo2")
    private Boolean yesNo2;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Determinationcitation> determinationCitations;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "JournalID", referencedColumnName = "JournalID")
    @ManyToOne
    private Journal journal;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "containedRFParent")
    private Collection<Referencework> containedReferenceWorks;
    
    @JoinColumn(name = "ContainedRFParentID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne
    private Referencework containedRFParent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Commonnametxcitation> commonnametxcitations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Taxoncitation> taxonCitations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Dnasequencingruncitation> dnasequencingruncitations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Exsiccata> exsiccatae;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Collectionobjectcitation> collectionObjectCitations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Author> authors;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWork")
    private Collection<Localitycitation> localityCitations;

    public Referencework() {
    }

    public Referencework(Integer referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }

    public Referencework(Integer referenceWorkId, Date timestampCreated, short referenceWorkType) {
        super(timestampCreated);
        this.referenceWorkId = referenceWorkId; 
        this.referenceWorkType = referenceWorkType;
    }

 
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    public void setLibraryNumber(String libraryNumber) {
        this.libraryNumber = libraryNumber;
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

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public short getReferenceWorkType() {
        return referenceWorkType;
    }

    public void setReferenceWorkType(short referenceWorkType) {
        this.referenceWorkType = referenceWorkType;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
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
    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionObjectCitations() {
        return collectionObjectCitations;
    }

    public void setCollectionObjectCitations(Collection<Collectionobjectcitation> collectionObjectCitations) {
        this.collectionObjectCitations = collectionObjectCitations;
    }

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitations() {
        return commonnametxcitations;
    }

    public void setCommonnametxcitations(Collection<Commonnametxcitation> commonnametxcitations) {
        this.commonnametxcitations = commonnametxcitations;
    }

    public Referencework getContainedRFParent() {
        return containedRFParent;
    }

    public void setContainedRFParent(Referencework containedRFParent) {
        this.containedRFParent = containedRFParent;
    }

    @XmlTransient
    public Collection<Referencework> getContainedReferenceWorks() {
        return containedReferenceWorks;
    }

    public void setContainedReferenceWorks(Collection<Referencework> containedReferenceWorks) {
        this.containedReferenceWorks = containedReferenceWorks;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    public Collection<Determinationcitation> getDeterminationCitations() {
        return determinationCitations;
    }

    public void setDeterminationCitations(Collection<Determinationcitation> determinationCitations) {
        this.determinationCitations = determinationCitations;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getDnasequencingruncitations() {
        return dnasequencingruncitations;
    }

    public void setDnasequencingruncitations(Collection<Dnasequencingruncitation> dnasequencingruncitations) {
        this.dnasequencingruncitations = dnasequencingruncitations;
    }

    @XmlTransient
    public Collection<Exsiccata> getExsiccatae() {
        return exsiccatae;
    }

    public void setExsiccatae(Collection<Exsiccata> exsiccatae) {
        this.exsiccatae = exsiccatae;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalityCitations() {
        return localityCitations;
    }

    public void setLocalityCitations(Collection<Localitycitation> localityCitations) {
        this.localityCitations = localityCitations;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getReferenceWorkId() {
        return referenceWorkId;
    }

    public void setReferenceWorkId(Integer referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxonCitations() {
        return taxonCitations;
    }

    public void setTaxonCitations(Collection<Taxoncitation> taxonCitations) {
        this.taxonCitations = taxonCitations;
    }
 

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenceWorkId != null ? referenceWorkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Referencework)) {
            return false;
        }
        Referencework other = (Referencework) object;
        if ((this.referenceWorkId == null && other.referenceWorkId != null) || (this.referenceWorkId != null && !this.referenceWorkId.equals(other.referenceWorkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Referencework[ referenceWorkID=" + referenceWorkId + " ]";
    }
    
}

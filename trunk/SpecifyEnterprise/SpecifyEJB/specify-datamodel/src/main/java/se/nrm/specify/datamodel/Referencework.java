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
    @NamedQuery(name = "Referencework.findByReferenceWorkID", query = "SELECT r FROM Referencework r WHERE r.referenceWorkID = :referenceWorkID"),
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
    private Integer referenceWorkID;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Determinationcitation> determinationcitationCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "JournalID", referencedColumnName = "JournalID")
    @ManyToOne
    private Journal journalID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "containedRFParentID")
    private Collection<Referencework> referenceworkCollection;
    
    @JoinColumn(name = "ContainedRFParentID", referencedColumnName = "ReferenceWorkID")
    @ManyToOne
    private Referencework containedRFParentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Commonnametxcitation> commonnametxcitationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Taxoncitation> taxoncitationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Dnasequencingruncitation> dnasequencingruncitationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Exsiccata> exsiccataCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Collectionobjectcitation> collectionobjectcitationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Author> authorCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceWorkID")
    private Collection<Localitycitation> localitycitationCollection;

    public Referencework() {
    }

    public Referencework(Integer referenceWorkID) {
        this.referenceWorkID = referenceWorkID;
    }

    public Referencework(Integer referenceWorkID, Date timestampCreated, short referenceWorkType) {
        super(timestampCreated);
        this.referenceWorkID = referenceWorkID; 
        this.referenceWorkType = referenceWorkType;
    }

    public Integer getReferenceWorkID() {
        return referenceWorkID;
    }

    public void setReferenceWorkID(Integer referenceWorkID) {
        this.referenceWorkID = referenceWorkID;
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
    public Collection<Determinationcitation> getDeterminationcitationCollection() {
        return determinationcitationCollection;
    }

    public void setDeterminationcitationCollection(Collection<Determinationcitation> determinationcitationCollection) {
        this.determinationcitationCollection = determinationcitationCollection;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Journal getJournalID() {
        return journalID;
    }

    public void setJournalID(Journal journalID) {
        this.journalID = journalID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @XmlTransient
    public Collection<Referencework> getReferenceworkCollection() {
        return referenceworkCollection;
    }

    public void setReferenceworkCollection(Collection<Referencework> referenceworkCollection) {
        this.referenceworkCollection = referenceworkCollection;
    }

    public Referencework getContainedRFParentID() {
        return containedRFParentID;
    }

    public void setContainedRFParentID(Referencework containedRFParentID) {
        this.containedRFParentID = containedRFParentID;
    }

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitationCollection() {
        return commonnametxcitationCollection;
    }

    public void setCommonnametxcitationCollection(Collection<Commonnametxcitation> commonnametxcitationCollection) {
        this.commonnametxcitationCollection = commonnametxcitationCollection;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxoncitationCollection() {
        return taxoncitationCollection;
    }

    public void setTaxoncitationCollection(Collection<Taxoncitation> taxoncitationCollection) {
        this.taxoncitationCollection = taxoncitationCollection;
    }

    @XmlTransient
    public Collection<Dnasequencingruncitation> getDnasequencingruncitationCollection() {
        return dnasequencingruncitationCollection;
    }

    public void setDnasequencingruncitationCollection(Collection<Dnasequencingruncitation> dnasequencingruncitationCollection) {
        this.dnasequencingruncitationCollection = dnasequencingruncitationCollection;
    }

    @XmlTransient
    public Collection<Exsiccata> getExsiccataCollection() {
        return exsiccataCollection;
    }

    public void setExsiccataCollection(Collection<Exsiccata> exsiccataCollection) {
        this.exsiccataCollection = exsiccataCollection;
    }

    @XmlTransient
    public Collection<Collectionobjectcitation> getCollectionobjectcitationCollection() {
        return collectionobjectcitationCollection;
    }

    public void setCollectionobjectcitationCollection(Collection<Collectionobjectcitation> collectionobjectcitationCollection) {
        this.collectionobjectcitationCollection = collectionobjectcitationCollection;
    }

    @XmlTransient
    public Collection<Author> getAuthorCollection() {
        return authorCollection;
    }

    public void setAuthorCollection(Collection<Author> authorCollection) {
        this.authorCollection = authorCollection;
    }

    @XmlTransient
    public Collection<Localitycitation> getLocalitycitationCollection() {
        return localitycitationCollection;
    }

    public void setLocalitycitationCollection(Collection<Localitycitation> localitycitationCollection) {
        this.localitycitationCollection = localitycitationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenceWorkID != null ? referenceWorkID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Referencework)) {
            return false;
        }
        Referencework other = (Referencework) object;
        if ((this.referenceWorkID == null && other.referenceWorkID != null) || (this.referenceWorkID != null && !this.referenceWorkID.equals(other.referenceWorkID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Referencework[ referenceWorkID=" + referenceWorkID + " ]";
    }
    
}

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "fieldnotebookpage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fieldnotebookpage.findAll", query = "SELECT f FROM Fieldnotebookpage f"),
    @NamedQuery(name = "Fieldnotebookpage.findByFieldNotebookPageID", query = "SELECT f FROM Fieldnotebookpage f WHERE f.fieldNotebookPageId = :fieldNotebookPageID"),
    @NamedQuery(name = "Fieldnotebookpage.findByTimestampCreated", query = "SELECT f FROM Fieldnotebookpage f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebookpage.findByTimestampModified", query = "SELECT f FROM Fieldnotebookpage f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebookpage.findByVersion", query = "SELECT f FROM Fieldnotebookpage f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebookpage.findByDescription", query = "SELECT f FROM Fieldnotebookpage f WHERE f.description = :description"),
    @NamedQuery(name = "Fieldnotebookpage.findByPageNumber", query = "SELECT f FROM Fieldnotebookpage f WHERE f.pageNumber = :pageNumber"),
    @NamedQuery(name = "Fieldnotebookpage.findByScanDate", query = "SELECT f FROM Fieldnotebookpage f WHERE f.scanDate = :scanDate")})
public class Fieldnotebookpage extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookPageID")
    private Integer fieldNotebookPageId;
     
    @Size(max = 128)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 32)
    @Column(name = "PageNumber")
    private String pageNumber;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ScanDate")
    @Temporal(TemporalType.DATE)
    private Date scanDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldNotebookPage")
    private Collection<Fieldnotebookpageattachment> attachments;
    
    @OneToMany(mappedBy = "fieldNotebookPage")
    private Collection<Collectionobject> collectionObjects;
    
    @JoinColumn(name = "FieldNotebookPageSetID", referencedColumnName = "FieldNotebookPageSetID")
    @ManyToOne
    private Fieldnotebookpageset pageSet;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline discipline;

    public Fieldnotebookpage() {
    }

    public Fieldnotebookpage(Integer fieldNotebookPageId) {
        this.fieldNotebookPageId = fieldNotebookPageId;
    }

    public Fieldnotebookpage(Integer fieldNotebookPageId, Date timestampCreated, Date scanDate) {
        super(timestampCreated);
        this.fieldNotebookPageId = fieldNotebookPageId; 
        this.scanDate = scanDate;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (fieldNotebookPageId != null) ? fieldNotebookPageId.toString() : "0";
    }
    
    public Integer getFieldNotebookPageId() {
        return fieldNotebookPageId;
    }

    public void setFieldNotebookPageId(Integer fieldNotebookPageId) {
        this.fieldNotebookPageId = fieldNotebookPageId;
    }

 
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
 
    public Collection<Fieldnotebookpageattachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Fieldnotebookpageattachment> attachments) {
        this.attachments = attachments;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionObjects() {
        return collectionObjects;
    }

    public void setCollectionObjects(Collection<Collectionobject> collectionObjects) {
        this.collectionObjects = collectionObjects;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Fieldnotebookpageset getPageSet() {
        return pageSet;
    }

    public void setPageSet(Fieldnotebookpageset pageSet) {
        this.pageSet = pageSet;
    }

    @NotNull(message="Scandate must be specified.")
    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }
 

    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Fieldnotebookpageset) {
            this.pageSet = (Fieldnotebookpageset)parent;   
        } else if(parent instanceof Discipline) {
            this.discipline = (Discipline)parent;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldNotebookPageId != null ? fieldNotebookPageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookpage)) {
            return false;
        }
        Fieldnotebookpage other = (Fieldnotebookpage) object;
        if ((this.fieldNotebookPageId == null && other.fieldNotebookPageId != null) || (this.fieldNotebookPageId != null && !this.fieldNotebookPageId.equals(other.fieldNotebookPageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookpage[ fieldNotebookPageID=" + fieldNotebookPageId + " ]";
    }
 
 
}

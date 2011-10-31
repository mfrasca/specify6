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
    @NamedQuery(name = "Fieldnotebookpage.findByFieldNotebookPageID", query = "SELECT f FROM Fieldnotebookpage f WHERE f.fieldNotebookPageID = :fieldNotebookPageID"),
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
    private Integer fieldNotebookPageID;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldNotebookPageID")
    private Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection;
    
    @OneToMany(mappedBy = "fieldNotebookPageID")
    private Collection<Collectionobject> collectionobjectCollection;
    
    @JoinColumn(name = "FieldNotebookPageSetID", referencedColumnName = "FieldNotebookPageSetID")
    @ManyToOne
    private Fieldnotebookpageset fieldNotebookPageSetID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;

    public Fieldnotebookpage() {
    }

    public Fieldnotebookpage(Integer fieldNotebookPageID) {
        this.fieldNotebookPageID = fieldNotebookPageID;
    }

    public Fieldnotebookpage(Integer fieldNotebookPageID, Date timestampCreated, Date scanDate) {
        super(timestampCreated);
        this.fieldNotebookPageID = fieldNotebookPageID; 
        this.scanDate = scanDate;
    }

    public Integer getFieldNotebookPageID() {
        return fieldNotebookPageID;
    }

    public void setFieldNotebookPageID(Integer fieldNotebookPageID) {
        this.fieldNotebookPageID = fieldNotebookPageID;
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

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageattachment> getFieldnotebookpageattachmentCollection() {
        return fieldnotebookpageattachmentCollection;
    }

    public void setFieldnotebookpageattachmentCollection(Collection<Fieldnotebookpageattachment> fieldnotebookpageattachmentCollection) {
        this.fieldnotebookpageattachmentCollection = fieldnotebookpageattachmentCollection;
    }

    @XmlTransient
    public Collection<Collectionobject> getCollectionobjectCollection() {
        return collectionobjectCollection;
    }

    public void setCollectionobjectCollection(Collection<Collectionobject> collectionobjectCollection) {
        this.collectionobjectCollection = collectionobjectCollection;
    }

    public Fieldnotebookpageset getFieldNotebookPageSetID() {
        return fieldNotebookPageSetID;
    }

    public void setFieldNotebookPageSetID(Fieldnotebookpageset fieldNotebookPageSetID) {
        this.fieldNotebookPageSetID = fieldNotebookPageSetID;
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

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldNotebookPageID != null ? fieldNotebookPageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookpage)) {
            return false;
        }
        Fieldnotebookpage other = (Fieldnotebookpage) object;
        if ((this.fieldNotebookPageID == null && other.fieldNotebookPageID != null) || (this.fieldNotebookPageID != null && !this.fieldNotebookPageID.equals(other.fieldNotebookPageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookpage[ fieldNotebookPageID=" + fieldNotebookPageID + " ]";
    }
    
}

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
import javax.validation.constraints.Size;
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
@Table(name = "fieldnotebook")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fieldnotebook.findAll", query = "SELECT f FROM Fieldnotebook f"),
    @NamedQuery(name = "Fieldnotebook.findByFieldNotebookId", query = "SELECT f FROM Fieldnotebook f WHERE f.fieldNotebookId = :fieldNotebookId"),
    @NamedQuery(name = "Fieldnotebook.findByTimestampCreated", query = "SELECT f FROM Fieldnotebook f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebook.findByTimestampModified", query = "SELECT f FROM Fieldnotebook f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebook.findByVersion", query = "SELECT f FROM Fieldnotebook f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebook.findByEndDate", query = "SELECT f FROM Fieldnotebook f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "Fieldnotebook.findByStorage", query = "SELECT f FROM Fieldnotebook f WHERE f.location = :storage"),
    @NamedQuery(name = "Fieldnotebook.findByName", query = "SELECT f FROM Fieldnotebook f WHERE f.name = :name"),
    @NamedQuery(name = "Fieldnotebook.findByStartDate", query = "SELECT f FROM Fieldnotebook f WHERE f.startDate = :startDate")})
public class Fieldnotebook extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookID")
    private Integer fieldNotebookId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 64)
    @Column(name = "Storage")
    private String location;
    
    @Size(max = 32)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @OneToMany(mappedBy = "fieldNotebook")
    private Collection<Fieldnotebookpageset> pageSets;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldNotebook")
    private Collection<Fieldnotebookattachment> attachments;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private se.nrm.specify.datamodel.Collection collection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Discipline discipline;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @NotNull
    @ManyToOne(optional = false)
    private Agent ownerAgent;

    public Fieldnotebook() {
    }

    public Fieldnotebook(Integer fieldNotebookId) {
        this.fieldNotebookId = fieldNotebookId;
    }

    public Fieldnotebook(Integer fieldNotebookId, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookId = fieldNotebookId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (fieldNotebookId != null) ? fieldNotebookId.toString() : "0";
    }
    
    @NotNull(message="Collection must be specified.")
    public se.nrm.specify.datamodel.Collection getCollection() {
        return collection;
    }

    public void setCollection(se.nrm.specify.datamodel.Collection collection) {
        this.collection = collection;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Integer getFieldNotebookId() {
        return fieldNotebookId;
    }

    public void setFieldNotebookId(Integer fieldNotebookId) {
        this.fieldNotebookId = fieldNotebookId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }
 
    
    
    @NotNull(message="Discipline must be specified.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
    public Collection<Fieldnotebookattachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Fieldnotebookattachment> attachments) {
        this.attachments = attachments;
    }
 
    public Collection<Fieldnotebookpageset> getPageSets() {
        return pageSets;
    }

    public void setPageSets(Collection<Fieldnotebookpageset> pageSets) {
        this.pageSets = pageSets;
    }

    @NotNull(message="OwnerAgent must be specified.")
    public Agent getOwnerAgent() {
        return ownerAgent;
    }

    public void setOwnerAgent(Agent ownerAgent) {
        this.ownerAgent = ownerAgent;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
  

    @Override
    public String getEntityName() {
        return "fieldNoteBook";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldNotebookId != null ? fieldNotebookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebook)) {
            return false;
        }
        Fieldnotebook other = (Fieldnotebook) object;
        if ((this.fieldNotebookId == null && other.fieldNotebookId != null) || (this.fieldNotebookId != null && !this.fieldNotebookId.equals(other.fieldNotebookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebook[ fieldNotebookID=" + fieldNotebookId + " ]";
    }
 
}

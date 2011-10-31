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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Fieldnotebook.findByFieldNotebookID", query = "SELECT f FROM Fieldnotebook f WHERE f.fieldNotebookID = :fieldNotebookID"),
    @NamedQuery(name = "Fieldnotebook.findByTimestampCreated", query = "SELECT f FROM Fieldnotebook f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebook.findByTimestampModified", query = "SELECT f FROM Fieldnotebook f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebook.findByVersion", query = "SELECT f FROM Fieldnotebook f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebook.findByEndDate", query = "SELECT f FROM Fieldnotebook f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "Fieldnotebook.findByStorage", query = "SELECT f FROM Fieldnotebook f WHERE f.storage = :storage"),
    @NamedQuery(name = "Fieldnotebook.findByName", query = "SELECT f FROM Fieldnotebook f WHERE f.name = :name"),
    @NamedQuery(name = "Fieldnotebook.findByStartDate", query = "SELECT f FROM Fieldnotebook f WHERE f.startDate = :startDate")})
public class Fieldnotebook extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookID")
    private Integer fieldNotebookID;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Size(max = 64)
    @Column(name = "Storage")
    private String storage;
    
    @Size(max = 32)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @OneToMany(mappedBy = "fieldNotebookID")
    private Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldNotebookID")
    private Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private se.nrm.specify.datamodel.Collection collectionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline disciplineID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Fieldnotebook() {
    }

    public Fieldnotebook(Integer fieldNotebookID) {
        this.fieldNotebookID = fieldNotebookID;
    }

    public Fieldnotebook(Integer fieldNotebookID, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookID = fieldNotebookID; 
    }

    public Integer getFieldNotebookID() {
        return fieldNotebookID;
    }

    public void setFieldNotebookID(Integer fieldNotebookID) {
        this.fieldNotebookID = fieldNotebookID;
    }
 
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public Collection<Fieldnotebookpageset> getFieldnotebookpagesetCollection() {
        return fieldnotebookpagesetCollection;
    }

    public void setFieldnotebookpagesetCollection(Collection<Fieldnotebookpageset> fieldnotebookpagesetCollection) {
        this.fieldnotebookpagesetCollection = fieldnotebookpagesetCollection;
    }

    @XmlTransient
    public Collection<Fieldnotebookattachment> getFieldnotebookattachmentCollection() {
        return fieldnotebookattachmentCollection;
    }

    public void setFieldnotebookattachmentCollection(Collection<Fieldnotebookattachment> fieldnotebookattachmentCollection) {
        this.fieldnotebookattachmentCollection = fieldnotebookattachmentCollection;
    }

    public se.nrm.specify.datamodel.Collection getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(se.nrm.specify.datamodel.Collection collectionID) {
        this.collectionID = collectionID;
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

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldNotebookID != null ? fieldNotebookID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebook)) {
            return false;
        }
        Fieldnotebook other = (Fieldnotebook) object;
        if ((this.fieldNotebookID == null && other.fieldNotebookID != null) || (this.fieldNotebookID != null && !this.fieldNotebookID.equals(other.fieldNotebookID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebook[ fieldNotebookID=" + fieldNotebookID + " ]";
    }
    
}

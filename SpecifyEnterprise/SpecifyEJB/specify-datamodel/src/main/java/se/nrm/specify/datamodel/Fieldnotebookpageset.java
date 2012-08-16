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
@Table(name = "fieldnotebookpageset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fieldnotebookpageset.findAll", query = "SELECT f FROM Fieldnotebookpageset f"),
    @NamedQuery(name = "Fieldnotebookpageset.findByFieldNotebookPageSetID", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.fieldNotebookPageSetId = :fieldNotebookPageSetID"),
    @NamedQuery(name = "Fieldnotebookpageset.findByTimestampCreated", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebookpageset.findByTimestampModified", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebookpageset.findByVersion", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebookpageset.findByDescription", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.description = :description"),
    @NamedQuery(name = "Fieldnotebookpageset.findByEndDate", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.endDate = :endDate"),
    @NamedQuery(name = "Fieldnotebookpageset.findByMethod", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.method = :method"),
    @NamedQuery(name = "Fieldnotebookpageset.findByOrderNumber", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.orderNumber = :orderNumber"),
    @NamedQuery(name = "Fieldnotebookpageset.findByStartDate", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.startDate = :startDate")})
public class Fieldnotebookpageset extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookPageSetID")
    private Integer fieldNotebookPageSetId;
     
    @Size(max = 128)
    @Column(name = "Description")
    private String description;
    
    @Size(max = 64)
    @Column(name = "Method")
    private String method;
    
    @Column(name = "OrderNumber")
    private Short orderNumber;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldNotebookPageSet")
    private Collection<Fieldnotebookpagesetattachment> attachments;
    
    @JoinColumn(name = "FieldNotebookID", referencedColumnName = "FieldNotebookID")
    @ManyToOne
    private Fieldnotebook fieldNotebook;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne(optional = false)
    private Discipline discipline;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent sourceAgent;
    
    @OneToMany(mappedBy = "pageSet")
    private Collection<Fieldnotebookpage> pages;

    public Fieldnotebookpageset() {
    }

    public Fieldnotebookpageset(Integer fieldNotebookPageSetId) {
        this.fieldNotebookPageSetId = fieldNotebookPageSetId;
    }

    public Fieldnotebookpageset(Integer fieldNotebookPageSetId, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookPageSetId = fieldNotebookPageSetId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (fieldNotebookPageSetId != null) ? fieldNotebookPageSetId.toString() : "0";
    }

    public Integer getFieldNotebookPageSetId() {
        return fieldNotebookPageSetId;
    }

    public void setFieldNotebookPageSetId(Integer fieldNotebookPageSetId) {
        this.fieldNotebookPageSetId = fieldNotebookPageSetId;
    }

 

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Short getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Short orderNumber) {
        this.orderNumber = orderNumber;
    }
 
    public Collection<Fieldnotebookpagesetattachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Fieldnotebookpagesetattachment> attachments) {
        this.attachments = attachments;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Discipline must be specified.")
    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    @XmlTransient
    public Fieldnotebook getFieldNotebook() {
        return fieldNotebook;
    }

    public void setFieldNotebook(Fieldnotebook fieldNotebook) {
        this.fieldNotebook = fieldNotebook;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }
 
    public Collection<Fieldnotebookpage> getPages() {
        return pages;
    }

    public void setPages(Collection<Fieldnotebookpage> pages) {
        this.pages = pages;
    }

    public Agent getSourceAgent() {
        return sourceAgent;
    }

    public void setSourceAgent(Agent sourceAgent) {
        this.sourceAgent = sourceAgent;
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
 
    
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Fieldnotebook) {
            this.fieldNotebook = (Fieldnotebook)parent;   
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldNotebookPageSetId != null ? fieldNotebookPageSetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookpageset)) {
            return false;
        }
        Fieldnotebookpageset other = (Fieldnotebookpageset) object;
        if ((this.fieldNotebookPageSetId == null && other.fieldNotebookPageSetId != null) || (this.fieldNotebookPageSetId != null && !this.fieldNotebookPageSetId.equals(other.fieldNotebookPageSetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookpageset[ fieldNotebookPageSetID=" + fieldNotebookPageSetId + " ]";
    }
 
}

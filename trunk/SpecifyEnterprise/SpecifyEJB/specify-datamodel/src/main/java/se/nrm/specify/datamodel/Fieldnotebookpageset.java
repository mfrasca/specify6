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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Fieldnotebookpageset.findByFieldNotebookPageSetID", query = "SELECT f FROM Fieldnotebookpageset f WHERE f.fieldNotebookPageSetID = :fieldNotebookPageSetID"),
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
    private Integer fieldNotebookPageSetID;
     
    @Size(max = 128)
    @Column(name = "Description")
    private String description;
    
    @Column(name = "EndDate") 
    @Temporal(TemporalType.DATE)
    private Date endDate;
    
    @Size(max = 64)
    @Column(name = "Method")
    private String method;
    
    @Column(name = "OrderNumber")
    private Short orderNumber;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldNotebookPageSetID")
    private Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection;
    
    @JoinColumn(name = "FieldNotebookID", referencedColumnName = "FieldNotebookID")
    @ManyToOne
    private Fieldnotebook fieldNotebookID;
    
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
    @ManyToOne
    private Agent agentID;
    
    @OneToMany(mappedBy = "fieldNotebookPageSetID")
    private Collection<Fieldnotebookpage> fieldnotebookpageCollection;

    public Fieldnotebookpageset() {
    }

    public Fieldnotebookpageset(Integer fieldNotebookPageSetID) {
        this.fieldNotebookPageSetID = fieldNotebookPageSetID;
    }

    public Fieldnotebookpageset(Integer fieldNotebookPageSetID, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookPageSetID = fieldNotebookPageSetID; 
    }

    public Integer getFieldNotebookPageSetID() {
        return fieldNotebookPageSetID;
    }

    public void setFieldNotebookPageSetID(Integer fieldNotebookPageSetID) {
        this.fieldNotebookPageSetID = fieldNotebookPageSetID;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public Collection<Fieldnotebookpagesetattachment> getFieldnotebookpagesetattachmentCollection() {
        return fieldnotebookpagesetattachmentCollection;
    }

    public void setFieldnotebookpagesetattachmentCollection(Collection<Fieldnotebookpagesetattachment> fieldnotebookpagesetattachmentCollection) {
        this.fieldnotebookpagesetattachmentCollection = fieldnotebookpagesetattachmentCollection;
    }

    public Fieldnotebook getFieldNotebookID() {
        return fieldNotebookID;
    }

    public void setFieldNotebookID(Fieldnotebook fieldNotebookID) {
        this.fieldNotebookID = fieldNotebookID;
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

    @XmlTransient
    public Collection<Fieldnotebookpage> getFieldnotebookpageCollection() {
        return fieldnotebookpageCollection;
    }

    public void setFieldnotebookpageCollection(Collection<Fieldnotebookpage> fieldnotebookpageCollection) {
        this.fieldnotebookpageCollection = fieldnotebookpageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fieldNotebookPageSetID != null ? fieldNotebookPageSetID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookpageset)) {
            return false;
        }
        Fieldnotebookpageset other = (Fieldnotebookpageset) object;
        if ((this.fieldNotebookPageSetID == null && other.fieldNotebookPageSetID != null) || (this.fieldNotebookPageSetID != null && !this.fieldNotebookPageSetID.equals(other.fieldNotebookPageSetID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookpageset[ fieldNotebookPageSetID=" + fieldNotebookPageSetID + " ]";
    }
    
}

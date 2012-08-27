package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;  
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spreport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spreport.findAll", query = "SELECT s FROM Spreport s"),
    @NamedQuery(name = "Spreport.findBySpReportId", query = "SELECT s FROM Spreport s WHERE s.spReportId = :spReportId"),
    @NamedQuery(name = "Spreport.findByTimestampCreated", query = "SELECT s FROM Spreport s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spreport.findByTimestampModified", query = "SELECT s FROM Spreport s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spreport.findByVersion", query = "SELECT s FROM Spreport s WHERE s.version = :version"),
    @NamedQuery(name = "Spreport.findByName", query = "SELECT s FROM Spreport s WHERE s.name = :name"),
    @NamedQuery(name = "Spreport.findByRepeatCount", query = "SELECT s FROM Spreport s WHERE s.repeatCount = :repeatCount"),
    @NamedQuery(name = "Spreport.findByRepeatField", query = "SELECT s FROM Spreport s WHERE s.repeatField = :repeatField")})
public class Spreport extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpReportId")
    private Integer spReportId;
     
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "RepeatCount")
    private Integer repeatCount;
    
    @Size(max = 255)
    @Column(name = "RepeatField")
    private String repeatField;
    
    @JoinColumn(name = "SpQueryID", referencedColumnName = "SpQueryID")
    @ManyToOne
    private Spquery query;
    
    @JoinColumn(name = "WorkbenchTemplateID", referencedColumnName = "WorkbenchTemplateID")
    @ManyToOne
    private Workbenchtemplate workbenchTemplate;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @NotNull
    @ManyToOne(optional = false)
    private Specifyuser specifyUser;
    
    @JoinColumn(name = "AppResourceID", referencedColumnName = "SpAppResourceID")
    @NotNull
    @ManyToOne(optional = false)
    private Spappresource appResource;

    public Spreport() {
    }

    public Spreport(Integer spReportId) {
        this.spReportId = spReportId;
    }

    public Spreport(Integer spReportId, Date timestampCreated, String name) {
        super(timestampCreated);
        this.spReportId = spReportId; 
        this.name = name;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spReportId != null) ? spReportId.toString() : "0";
    }
    
    public Integer getSpReportId() {
        return spReportId;
    }

    public void setSpReportId(Integer spReportId) {
        this.spReportId = spReportId;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getRepeatField() {
        return repeatField;
    }

    public void setRepeatField(String repeatField) {
        this.repeatField = repeatField;
    }

    @NotNull(message="AppResource must be specified.")
    public Spappresource getAppResource() {
        return appResource;
    }

    public void setAppResource(Spappresource appResource) {
        this.appResource = appResource;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Spquery getQuery() {
        return query;
    }

    public void setQuery(Spquery query) {
        this.query = query;
    }

    @NotNull(message="SpecifyUser must be specified.")
    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

    public Workbenchtemplate getWorkbenchTemplate() {
        return workbenchTemplate;
    }

    public void setWorkbenchTemplate(Workbenchtemplate workbenchTemplate) {
        this.workbenchTemplate = workbenchTemplate;
    }

    @Override
    public String getEntityName() {
        return "spReport";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spReportId != null ? spReportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spreport)) {
            return false;
        }
        Spreport other = (Spreport) object;
        if ((this.spReportId == null && other.spReportId != null) || (this.spReportId != null && !this.spReportId.equals(other.spReportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spreport[ spReportId=" + spReportId + " ]";
    }
 
}

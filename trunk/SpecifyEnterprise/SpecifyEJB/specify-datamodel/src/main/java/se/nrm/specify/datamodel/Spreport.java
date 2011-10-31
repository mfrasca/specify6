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
    private Spquery spQueryID;
    
    @JoinColumn(name = "WorkbenchTemplateID", referencedColumnName = "WorkbenchTemplateID")
    @ManyToOne
    private Workbenchtemplate workbenchTemplateID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne(optional = false)
    private Specifyuser specifyUserID;
    
    @JoinColumn(name = "AppResourceID", referencedColumnName = "SpAppResourceID")
    @ManyToOne(optional = false)
    private Spappresource appResourceID;

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

    public Spquery getSpQueryID() {
        return spQueryID;
    }

    public void setSpQueryID(Spquery spQueryID) {
        this.spQueryID = spQueryID;
    }

    public Workbenchtemplate getWorkbenchTemplateID() {
        return workbenchTemplateID;
    }

    public void setWorkbenchTemplateID(Workbenchtemplate workbenchTemplateID) {
        this.workbenchTemplateID = workbenchTemplateID;
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

    public Specifyuser getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(Specifyuser specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    public Spappresource getAppResourceID() {
        return appResourceID;
    }

    public void setAppResourceID(Spappresource appResourceID) {
        this.appResourceID = appResourceID;
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

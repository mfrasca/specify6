package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
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
@Table(name = "workbench")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbench.findAll", query = "SELECT w FROM Workbench w"),
    @NamedQuery(name = "Workbench.findByWorkbenchId", query = "SELECT w FROM Workbench w WHERE w.workbenchId = :workbenchId"),
    @NamedQuery(name = "Workbench.findByTimestampCreated", query = "SELECT w FROM Workbench w WHERE w.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Workbench.findByTimestampModified", query = "SELECT w FROM Workbench w WHERE w.timestampModified = :timestampModified"),
    @NamedQuery(name = "Workbench.findByVersion", query = "SELECT w FROM Workbench w WHERE w.version = :version"),
    @NamedQuery(name = "Workbench.findByAllPermissionLevel", query = "SELECT w FROM Workbench w WHERE w.allPermissionLevel = :allPermissionLevel"),
    @NamedQuery(name = "Workbench.findByTableID", query = "SELECT w FROM Workbench w WHERE w.dbTableId = :tableID"),
    @NamedQuery(name = "Workbench.findByExportInstitutionName", query = "SELECT w FROM Workbench w WHERE w.exportInstitutionName = :exportInstitutionName"),
    @NamedQuery(name = "Workbench.findByFormId", query = "SELECT w FROM Workbench w WHERE w.formId = :formId"),
    @NamedQuery(name = "Workbench.findByGroupPermissionLevel", query = "SELECT w FROM Workbench w WHERE w.groupPermissionLevel = :groupPermissionLevel"),
    @NamedQuery(name = "Workbench.findByLockedByUserName", query = "SELECT w FROM Workbench w WHERE w.lockedByUserName = :lockedByUserName"),
    @NamedQuery(name = "Workbench.findByName", query = "SELECT w FROM Workbench w WHERE w.name = :name"),
    @NamedQuery(name = "Workbench.findByOwnerPermissionLevel", query = "SELECT w FROM Workbench w WHERE w.ownerPermissionLevel = :ownerPermissionLevel"),
    @NamedQuery(name = "Workbench.findBySrcFilePath", query = "SELECT w FROM Workbench w WHERE w.srcFilePath = :srcFilePath"),
    @NamedQuery(name = "Workbench.findByExportedFromTableName", query = "SELECT w FROM Workbench w WHERE w.exportedFromTableName = :exportedFromTableName")})
public class Workbench extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchID")
    private Integer workbenchId;
     
    @Column(name = "AllPermissionLevel")
    private Integer allPermissionLevel;
    
    @Column(name = "TableID")
    private Integer dbTableId;
    
    @Size(max = 128)
    @Column(name = "ExportInstitutionName")
    private String exportInstitutionName;
    
    @Column(name = "FormId")
    private Integer formId;
    
    @Column(name = "GroupPermissionLevel")
    private Integer groupPermissionLevel;
    
    @Size(max = 64)
    @Column(name = "LockedByUserName")
    private String lockedByUserName;
    
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "OwnerPermissionLevel")
    private Integer ownerPermissionLevel;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 255)
    @Column(name = "SrcFilePath")
    private String srcFilePath;
    
    @Size(max = 128)
    @Column(name = "ExportedFromTableName")
    private String exportedFromTableName;
    
    @JoinColumn(name = "SpPrincipalID", referencedColumnName = "SpPrincipalID")
    @ManyToOne
    private Spprincipal group;
    
    @JoinColumn(name = "WorkbenchTemplateID", referencedColumnName = "WorkbenchTemplateID")
    @NotNull
    @ManyToOne(optional = false)
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbench")
    private Collection<Workbenchrow> workbenchRows;

    public Workbench() {
    }

    public Workbench(Integer workbenchId) {
        this.workbenchId = workbenchId;
    }

    public Workbench(Integer workbenchId, Date timestampCreated) {
        super(timestampCreated);
        this.workbenchId = workbenchId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (workbenchId != null) ? workbenchId.toString() : "0";
    }
 
    public Integer getAllPermissionLevel() {
        return allPermissionLevel;
    }

    public void setAllPermissionLevel(Integer allPermissionLevel) {
        this.allPermissionLevel = allPermissionLevel;
    }
 
    public String getExportInstitutionName() {
        return exportInstitutionName;
    }

    public void setExportInstitutionName(String exportInstitutionName) {
        this.exportInstitutionName = exportInstitutionName;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getGroupPermissionLevel() {
        return groupPermissionLevel;
    }

    public void setGroupPermissionLevel(Integer groupPermissionLevel) {
        this.groupPermissionLevel = groupPermissionLevel;
    }

    public String getLockedByUserName() {
        return lockedByUserName;
    }

    public void setLockedByUserName(String lockedByUserName) {
        this.lockedByUserName = lockedByUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerPermissionLevel() {
        return ownerPermissionLevel;
    }

    public void setOwnerPermissionLevel(Integer ownerPermissionLevel) {
        this.ownerPermissionLevel = ownerPermissionLevel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public String getExportedFromTableName() {
        return exportedFromTableName;
    }

    public void setExportedFromTableName(String exportedFromTableName) {
        this.exportedFromTableName = exportedFromTableName;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getDbTableId() {
        return dbTableId;
    }

    public void setDbTableId(Integer dbTableId) {
        this.dbTableId = dbTableId;
    }

    public Spprincipal getGroup() {
        return group;
    }

    public void setGroup(Spprincipal group) {
        this.group = group;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @NotNull(message="SpecifyUser must be specified.")
    @XmlIDREF
    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

    public Integer getWorkbenchId() {
        return workbenchId;
    }

    public void setWorkbenchId(Integer workbenchId) {
        this.workbenchId = workbenchId;
    }

    @XmlTransient
    public Collection<Workbenchrow> getWorkbenchRows() {
        return workbenchRows;
    }

    public void setWorkbenchRows(Collection<Workbenchrow> workbenchRows) {
        this.workbenchRows = workbenchRows;
    }

    @NotNull(message="Workbenchtemplate must be specified.")
    public Workbenchtemplate getWorkbenchTemplate() {
        return workbenchTemplate;
    }

    public void setWorkbenchTemplate(Workbenchtemplate workbenchTemplate) {
        this.workbenchTemplate = workbenchTemplate;
    }
 

    @Override
    public String getEntityName() {
        return "workbench";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchId != null ? workbenchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbench)) {
            return false;
        }
        Workbench other = (Workbench) object;
        if ((this.workbenchId == null && other.workbenchId != null) || (this.workbenchId != null && !this.workbenchId.equals(other.workbenchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workbench[ workbenchID=" + workbenchId + " ]";
    }

   
    
}

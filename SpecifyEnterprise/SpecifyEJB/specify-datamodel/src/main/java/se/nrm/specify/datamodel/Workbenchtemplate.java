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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "workbenchtemplate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchtemplate.findAll", query = "SELECT w FROM Workbenchtemplate w"),
    @NamedQuery(name = "Workbenchtemplate.findByWorkbenchTemplateId", query = "SELECT w FROM Workbenchtemplate w WHERE w.workbenchTemplateId = :workbenchTemplateId"),
    @NamedQuery(name = "Workbenchtemplate.findByTimestampCreated", query = "SELECT w FROM Workbenchtemplate w WHERE w.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Workbenchtemplate.findByTimestampModified", query = "SELECT w FROM Workbenchtemplate w WHERE w.timestampModified = :timestampModified"),
    @NamedQuery(name = "Workbenchtemplate.findByVersion", query = "SELECT w FROM Workbenchtemplate w WHERE w.version = :version"),
    @NamedQuery(name = "Workbenchtemplate.findByName", query = "SELECT w FROM Workbenchtemplate w WHERE w.name = :name"),
    @NamedQuery(name = "Workbenchtemplate.findBySrcFilePath", query = "SELECT w FROM Workbenchtemplate w WHERE w.srcFilePath = :srcFilePath")})
public class Workbenchtemplate extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchTemplateID")
    private Integer workbenchTemplateId;
     
    @Size(max = 64)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 255)
    @Column(name = "SrcFilePath")
    private String srcFilePath;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchTemplate")
    private Collection<Workbench> workbenches;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchTemplate")
    private Collection<Workbenchtemplatemappingitem> workbenchTemplateMappingItems;
    
    @OneToMany(mappedBy = "workbenchTemplate")
    private Collection<Spreport> spreports;

    public Workbenchtemplate() {
    }

    public Workbenchtemplate(Integer workbenchTemplateId) {
        this.workbenchTemplateId = workbenchTemplateId;
    }

    public Workbenchtemplate(Integer workbenchTemplateId, Date timestampCreated) {
        super(timestampCreated);
        this.workbenchTemplateId = workbenchTemplateId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (workbenchTemplateId != null) ? workbenchTemplateId.toString() : "0";
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

    
    @NotNull(message="SpecifyUser must be specified.")
    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

    @XmlTransient
    public Collection<Spreport> getSpreports() {
        return spreports;
    }

    public void setSpreports(Collection<Spreport> spreports) {
        this.spreports = spreports;
    }

  

    public Integer getWorkbenchTemplateId() {
        return workbenchTemplateId;
    }

    public void setWorkbenchTemplateId(Integer workbenchTemplateId) {
        this.workbenchTemplateId = workbenchTemplateId;
    }

    @XmlTransient
    public Collection<Workbenchtemplatemappingitem> getWorkbenchTemplateMappingItems() {
        return workbenchTemplateMappingItems;
    }

    public void setWorkbenchTemplateMappingItems(Collection<Workbenchtemplatemappingitem> workbenchTemplateMappingItems) {
        this.workbenchTemplateMappingItems = workbenchTemplateMappingItems;
    }

    @XmlTransient
    public Collection<Workbench> getWorkbenches() {
        return workbenches;
    }

    public void setWorkbenches(Collection<Workbench> workbenches) {
        this.workbenches = workbenches;
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

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

   

    @Override
    public String getEntityName() {
        return "workbenchTemplate";
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchTemplateId != null ? workbenchTemplateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchtemplate)) {
            return false;
        }
        Workbenchtemplate other = (Workbenchtemplate) object;
        if ((this.workbenchTemplateId == null && other.workbenchTemplateId != null) || (this.workbenchTemplateId != null && !this.workbenchTemplateId.equals(other.workbenchTemplateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workbenchtemplate[ workbenchTemplateID=" + workbenchTemplateId + " ]";
    }
 
}

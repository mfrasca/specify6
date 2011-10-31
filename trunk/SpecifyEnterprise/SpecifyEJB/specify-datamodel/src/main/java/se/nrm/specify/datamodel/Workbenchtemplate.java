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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Workbenchtemplate.findByWorkbenchTemplateID", query = "SELECT w FROM Workbenchtemplate w WHERE w.workbenchTemplateID = :workbenchTemplateID"),
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
    private Integer workbenchTemplateID;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchTemplateID")
    private Collection<Workbench> workbenchCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne(optional = false)
    private Specifyuser specifyUserID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchTemplateID")
    private Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitemCollection;
    
    @OneToMany(mappedBy = "workbenchTemplateID")
    private Collection<Spreport> spreportCollection;

    public Workbenchtemplate() {
    }

    public Workbenchtemplate(Integer workbenchTemplateID) {
        this.workbenchTemplateID = workbenchTemplateID;
    }

    public Workbenchtemplate(Integer workbenchTemplateID, Date timestampCreated) {
        super(timestampCreated);
        this.workbenchTemplateID = workbenchTemplateID; 
    }

    public Integer getWorkbenchTemplateID() {
        return workbenchTemplateID;
    }

    public void setWorkbenchTemplateID(Integer workbenchTemplateID) {
        this.workbenchTemplateID = workbenchTemplateID;
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

    @XmlTransient
    public Collection<Workbench> getWorkbenchCollection() {
        return workbenchCollection;
    }

    public void setWorkbenchCollection(Collection<Workbench> workbenchCollection) {
        this.workbenchCollection = workbenchCollection;
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

    @XmlTransient
    public Collection<Workbenchtemplatemappingitem> getWorkbenchtemplatemappingitemCollection() {
        return workbenchtemplatemappingitemCollection;
    }

    public void setWorkbenchtemplatemappingitemCollection(Collection<Workbenchtemplatemappingitem> workbenchtemplatemappingitemCollection) {
        this.workbenchtemplatemappingitemCollection = workbenchtemplatemappingitemCollection;
    }

    @XmlTransient
    public Collection<Spreport> getSpreportCollection() {
        return spreportCollection;
    }

    public void setSpreportCollection(Collection<Spreport> spreportCollection) {
        this.spreportCollection = spreportCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchTemplateID != null ? workbenchTemplateID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchtemplate)) {
            return false;
        }
        Workbenchtemplate other = (Workbenchtemplate) object;
        if ((this.workbenchTemplateID == null && other.workbenchTemplateID != null) || (this.workbenchTemplateID != null && !this.workbenchTemplateID.equals(other.workbenchTemplateID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workbenchtemplate[ workbenchTemplateID=" + workbenchTemplateID + " ]";
    }
    
}

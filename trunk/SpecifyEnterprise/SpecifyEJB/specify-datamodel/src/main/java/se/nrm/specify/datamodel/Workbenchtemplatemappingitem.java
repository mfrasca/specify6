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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "workbenchtemplatemappingitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchtemplatemappingitem.findAll", query = "SELECT w FROM Workbenchtemplatemappingitem w"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByWorkbenchTemplateMappingItemID", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.workbenchTemplateMappingItemID = :workbenchTemplateMappingItemID"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByTimestampCreated", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByTimestampModified", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.timestampModified = :timestampModified"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByVersion", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.version = :version"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByXCoord", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.xCoord = :xCoord"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByYCoord", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.yCoord = :yCoord"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByCaption", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.caption = :caption"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByCarryForward", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.carryForward = :carryForward"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByDataFieldLength", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.dataFieldLength = :dataFieldLength"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByFieldName", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.fieldName = :fieldName"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByFieldType", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.fieldType = :fieldType"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByImportedColName", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.importedColName = :importedColName"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByIsExportableToContent", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.isExportableToContent = :isExportableToContent"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByIsIncludedInTitle", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.isIncludedInTitle = :isIncludedInTitle"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByIsRequired", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.isRequired = :isRequired"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByMetaData", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.metaData = :metaData"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByDataColumnIndex", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.dataColumnIndex = :dataColumnIndex"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByTableId", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.tableId = :tableId"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByTableName", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.tableName = :tableName"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByViewOrder", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.viewOrder = :viewOrder"),
    @NamedQuery(name = "Workbenchtemplatemappingitem.findByIsEditable", query = "SELECT w FROM Workbenchtemplatemappingitem w WHERE w.isEditable = :isEditable")})
public class Workbenchtemplatemappingitem extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchTemplateMappingItemID")
    private Integer workbenchTemplateMappingItemID;
 
    @Column(name = "XCoord")
    private Short xCoord;
    
    @Column(name = "YCoord")
    private Short yCoord;
    
    @Size(max = 64)
    @Column(name = "Caption")
    private String caption;
    
    @Column(name = "CarryForward")
    private Boolean carryForward;
    
    @Column(name = "DataFieldLength")
    private Short dataFieldLength;
    
    @Size(max = 255)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Column(name = "FieldType")
    private Short fieldType;
    
    @Size(max = 255)
    @Column(name = "ImportedColName")
    private String importedColName;
    
    @Column(name = "IsExportableToContent")
    private Boolean isExportableToContent;
    
    @Column(name = "IsIncludedInTitle")
    private Boolean isIncludedInTitle;
    
    @Column(name = "IsRequired")
    private Boolean isRequired;
    
    @Size(max = 128)
    @Column(name = "MetaData")
    private String metaData;
    
    @Column(name = "DataColumnIndex")
    private Short dataColumnIndex;
    
    @Column(name = "TableId")
    private Integer tableId;
    
    @Size(max = 64)
    @Column(name = "TableName")
    private String tableName;
    
    @Column(name = "ViewOrder")
    private Short viewOrder;
    
    @Column(name = "IsEditable")
    private Boolean isEditable;
    
    @JoinColumn(name = "WorkbenchTemplateID", referencedColumnName = "WorkbenchTemplateID")
    @ManyToOne(optional = false)
    private Workbenchtemplate workbenchTemplateID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchTemplateMappingItemID")
    private Collection<Workbenchdataitem> workbenchdataitemCollection;

    public Workbenchtemplatemappingitem() {
    }

    public Workbenchtemplatemappingitem(Integer workbenchTemplateMappingItemID) {
        this.workbenchTemplateMappingItemID = workbenchTemplateMappingItemID;
    }

    public Workbenchtemplatemappingitem(Integer workbenchTemplateMappingItemID, Date timestampCreated) {
        super(timestampCreated);
        this.workbenchTemplateMappingItemID = workbenchTemplateMappingItemID; 
    }

    public Integer getWorkbenchTemplateMappingItemID() {
        return workbenchTemplateMappingItemID;
    }

    public void setWorkbenchTemplateMappingItemID(Integer workbenchTemplateMappingItemID) {
        this.workbenchTemplateMappingItemID = workbenchTemplateMappingItemID;
    }
 
    public Short getXCoord() {
        return xCoord;
    }

    public void setXCoord(Short xCoord) {
        this.xCoord = xCoord;
    }

    public Short getYCoord() {
        return yCoord;
    }

    public void setYCoord(Short yCoord) {
        this.yCoord = yCoord;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Boolean getCarryForward() {
        return carryForward;
    }

    public void setCarryForward(Boolean carryForward) {
        this.carryForward = carryForward;
    }

    public Short getDataFieldLength() {
        return dataFieldLength;
    }

    public void setDataFieldLength(Short dataFieldLength) {
        this.dataFieldLength = dataFieldLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Short getFieldType() {
        return fieldType;
    }

    public void setFieldType(Short fieldType) {
        this.fieldType = fieldType;
    }

    public String getImportedColName() {
        return importedColName;
    }

    public void setImportedColName(String importedColName) {
        this.importedColName = importedColName;
    }

    public Boolean getIsExportableToContent() {
        return isExportableToContent;
    }

    public void setIsExportableToContent(Boolean isExportableToContent) {
        this.isExportableToContent = isExportableToContent;
    }

    public Boolean getIsIncludedInTitle() {
        return isIncludedInTitle;
    }

    public void setIsIncludedInTitle(Boolean isIncludedInTitle) {
        this.isIncludedInTitle = isIncludedInTitle;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Short getDataColumnIndex() {
        return dataColumnIndex;
    }

    public void setDataColumnIndex(Short dataColumnIndex) {
        this.dataColumnIndex = dataColumnIndex;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Short getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(Short viewOrder) {
        this.viewOrder = viewOrder;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
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

    @XmlTransient
    public Collection<Workbenchdataitem> getWorkbenchdataitemCollection() {
        return workbenchdataitemCollection;
    }

    public void setWorkbenchdataitemCollection(Collection<Workbenchdataitem> workbenchdataitemCollection) {
        this.workbenchdataitemCollection = workbenchdataitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchTemplateMappingItemID != null ? workbenchTemplateMappingItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchtemplatemappingitem)) {
            return false;
        }
        Workbenchtemplatemappingitem other = (Workbenchtemplatemappingitem) object;
        if ((this.workbenchTemplateMappingItemID == null && other.workbenchTemplateMappingItemID != null) || (this.workbenchTemplateMappingItemID != null && !this.workbenchTemplateMappingItemID.equals(other.workbenchTemplateMappingItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workbenchtemplatemappingitem[ workbenchTemplateMappingItemID=" + workbenchTemplateMappingItemID + " ]";
    }
    
}

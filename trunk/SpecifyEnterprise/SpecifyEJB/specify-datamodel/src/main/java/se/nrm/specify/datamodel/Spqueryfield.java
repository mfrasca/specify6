package se.nrm.specify.datamodel;
 
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "spqueryfield")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spqueryfield.findAll", query = "SELECT s FROM Spqueryfield s"),
    @NamedQuery(name = "Spqueryfield.findBySpQueryFieldID", query = "SELECT s FROM Spqueryfield s WHERE s.spQueryFieldID = :spQueryFieldID"),
    @NamedQuery(name = "Spqueryfield.findByTimestampCreated", query = "SELECT s FROM Spqueryfield s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spqueryfield.findByTimestampModified", query = "SELECT s FROM Spqueryfield s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spqueryfield.findByVersion", query = "SELECT s FROM Spqueryfield s WHERE s.version = :version"),
    @NamedQuery(name = "Spqueryfield.findByAlwaysFilter", query = "SELECT s FROM Spqueryfield s WHERE s.alwaysFilter = :alwaysFilter"),
    @NamedQuery(name = "Spqueryfield.findByColumnAlias", query = "SELECT s FROM Spqueryfield s WHERE s.columnAlias = :columnAlias"),
    @NamedQuery(name = "Spqueryfield.findByContextTableIdent", query = "SELECT s FROM Spqueryfield s WHERE s.contextTableIdent = :contextTableIdent"),
    @NamedQuery(name = "Spqueryfield.findByEndValue", query = "SELECT s FROM Spqueryfield s WHERE s.endValue = :endValue"),
    @NamedQuery(name = "Spqueryfield.findByFieldName", query = "SELECT s FROM Spqueryfield s WHERE s.fieldName = :fieldName"),
    @NamedQuery(name = "Spqueryfield.findByFormatName", query = "SELECT s FROM Spqueryfield s WHERE s.formatName = :formatName"),
    @NamedQuery(name = "Spqueryfield.findByIsDisplay", query = "SELECT s FROM Spqueryfield s WHERE s.isDisplay = :isDisplay"),
    @NamedQuery(name = "Spqueryfield.findByIsNot", query = "SELECT s FROM Spqueryfield s WHERE s.isNot = :isNot"),
    @NamedQuery(name = "Spqueryfield.findByIsPrompt", query = "SELECT s FROM Spqueryfield s WHERE s.isPrompt = :isPrompt"),
    @NamedQuery(name = "Spqueryfield.findByIsRelFld", query = "SELECT s FROM Spqueryfield s WHERE s.isRelFld = :isRelFld"),
    @NamedQuery(name = "Spqueryfield.findByOperEnd", query = "SELECT s FROM Spqueryfield s WHERE s.operEnd = :operEnd"),
    @NamedQuery(name = "Spqueryfield.findByOperStart", query = "SELECT s FROM Spqueryfield s WHERE s.operStart = :operStart"),
    @NamedQuery(name = "Spqueryfield.findByPosition", query = "SELECT s FROM Spqueryfield s WHERE s.position = :position"),
    @NamedQuery(name = "Spqueryfield.findBySortType", query = "SELECT s FROM Spqueryfield s WHERE s.sortType = :sortType"),
    @NamedQuery(name = "Spqueryfield.findByStartValue", query = "SELECT s FROM Spqueryfield s WHERE s.startValue = :startValue"),
    @NamedQuery(name = "Spqueryfield.findByAllowNulls", query = "SELECT s FROM Spqueryfield s WHERE s.allowNulls = :allowNulls")})
public class Spqueryfield extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpQueryFieldID")
    private Integer spQueryFieldID;
     
    @Column(name = "AlwaysFilter")
    private Boolean alwaysFilter;
    
    @Size(max = 64)
    @Column(name = "ColumnAlias")
    private String columnAlias;
    
    @Column(name = "ContextTableIdent")
    private Integer contextTableIdent;
    
    @Size(max = 64)
    @Column(name = "EndValue")
    private String endValue;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FieldName")
    private String fieldName;
    
    @Size(max = 64)
    @Column(name = "FormatName")
    private String formatName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsDisplay")
    private boolean isDisplay;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsNot")
    private boolean isNot;
    
    @Column(name = "IsPrompt")
    private Boolean isPrompt;
    
    @Column(name = "IsRelFld")
    private Boolean isRelFld;
    
    @Column(name = "OperEnd")
    private Short operEnd;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "OperStart")
    private short operStart;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Position")
    private short position;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SortType")
    private short sortType;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "StartValue")
    private String startValue;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "StringId")
    private String stringId;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "TableList")
    private String tableList;
    
    @Column(name = "AllowNulls")
    private Boolean allowNulls;
    
    @JoinColumn(name = "SpQueryID", referencedColumnName = "SpQueryID")
    @ManyToOne
    private Spquery spQueryID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "spQueryFieldID")
    private Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection;

    public Spqueryfield() {
    }

    public Spqueryfield(Integer spQueryFieldID) {
        this.spQueryFieldID = spQueryFieldID;
    }

    public Spqueryfield(Integer spQueryFieldID, Date timestampCreated, String fieldName, boolean isDisplay, boolean isNot, short operStart, short position, short sortType, String startValue, String stringId, String tableList) {
        super(timestampCreated);
        this.spQueryFieldID = spQueryFieldID; 
        this.fieldName = fieldName;
        this.isDisplay = isDisplay;
        this.isNot = isNot;
        this.operStart = operStart;
        this.position = position;
        this.sortType = sortType;
        this.startValue = startValue;
        this.stringId = stringId;
        this.tableList = tableList;
    }

    public Integer getSpQueryFieldID() {
        return spQueryFieldID;
    }

    public void setSpQueryFieldID(Integer spQueryFieldID) {
        this.spQueryFieldID = spQueryFieldID;
    }
 
    public Boolean getAlwaysFilter() {
        return alwaysFilter;
    }

    public void setAlwaysFilter(Boolean alwaysFilter) {
        this.alwaysFilter = alwaysFilter;
    }

    public String getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

    public Integer getContextTableIdent() {
        return contextTableIdent;
    }

    public void setContextTableIdent(Integer contextTableIdent) {
        this.contextTableIdent = contextTableIdent;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public boolean getIsNot() {
        return isNot;
    }

    public void setIsNot(boolean isNot) {
        this.isNot = isNot;
    }

    public Boolean getIsPrompt() {
        return isPrompt;
    }

    public void setIsPrompt(Boolean isPrompt) {
        this.isPrompt = isPrompt;
    }

    public Boolean getIsRelFld() {
        return isRelFld;
    }

    public void setIsRelFld(Boolean isRelFld) {
        this.isRelFld = isRelFld;
    }

    public Short getOperEnd() {
        return operEnd;
    }

    public void setOperEnd(Short operEnd) {
        this.operEnd = operEnd;
    }

    public short getOperStart() {
        return operStart;
    }

    public void setOperStart(short operStart) {
        this.operStart = operStart;
    }

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    public short getSortType() {
        return sortType;
    }

    public void setSortType(short sortType) {
        this.sortType = sortType;
    }

    public String getStartValue() {
        return startValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getTableList() {
        return tableList;
    }

    public void setTableList(String tableList) {
        this.tableList = tableList;
    }

    public Boolean getAllowNulls() {
        return allowNulls;
    }

    public void setAllowNulls(Boolean allowNulls) {
        this.allowNulls = allowNulls;
    }

    public Spquery getSpQueryID() {
        return spQueryID;
    }

    public void setSpQueryID(Spquery spQueryID) {
        this.spQueryID = spQueryID;
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
    public Collection<Spexportschemaitemmapping> getSpexportschemaitemmappingCollection() {
        return spexportschemaitemmappingCollection;
    }

    public void setSpexportschemaitemmappingCollection(Collection<Spexportschemaitemmapping> spexportschemaitemmappingCollection) {
        this.spexportschemaitemmappingCollection = spexportschemaitemmappingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spQueryFieldID != null ? spQueryFieldID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spqueryfield)) {
            return false;
        }
        Spqueryfield other = (Spqueryfield) object;
        if ((this.spQueryFieldID == null && other.spQueryFieldID != null) || (this.spQueryFieldID != null && !this.spQueryFieldID.equals(other.spQueryFieldID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spqueryfield[ spQueryFieldID=" + spQueryFieldID + " ]";
    }
    
}

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "autonumberingscheme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autonumberingscheme.findAll", query = "SELECT a FROM Autonumberingscheme a"),
    @NamedQuery(name = "Autonumberingscheme.findByAutoNumberingSchemeID", query = "SELECT a FROM Autonumberingscheme a WHERE a.autoNumberingSchemeID = :autoNumberingSchemeID"),
    @NamedQuery(name = "Autonumberingscheme.findByTimestampCreated", query = "SELECT a FROM Autonumberingscheme a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Autonumberingscheme.findByTimestampModified", query = "SELECT a FROM Autonumberingscheme a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Autonumberingscheme.findByVersion", query = "SELECT a FROM Autonumberingscheme a WHERE a.version = :version"),
    @NamedQuery(name = "Autonumberingscheme.findByFormatName", query = "SELECT a FROM Autonumberingscheme a WHERE a.formatName = :formatName"),
    @NamedQuery(name = "Autonumberingscheme.findByIsNumericOnly", query = "SELECT a FROM Autonumberingscheme a WHERE a.isNumericOnly = :isNumericOnly"),
    @NamedQuery(name = "Autonumberingscheme.findBySchemeClassName", query = "SELECT a FROM Autonumberingscheme a WHERE a.schemeClassName = :schemeClassName"),
    @NamedQuery(name = "Autonumberingscheme.findBySchemeName", query = "SELECT a FROM Autonumberingscheme a WHERE a.schemeName = :schemeName"),
    @NamedQuery(name = "Autonumberingscheme.findByTableNumber", query = "SELECT a FROM Autonumberingscheme a WHERE a.tableNumber = :tableNumber")})
public class Autonumberingscheme extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AutoNumberingSchemeID")
    private Integer autoNumberingSchemeID;
     
    @Size(max = 64)
    @Column(name = "FormatName")
    private String formatName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsNumericOnly")
    private boolean isNumericOnly;
    
    @Size(max = 64)
    @Column(name = "SchemeClassName")
    private String schemeClassName;
    
    @Size(max = 64)
    @Column(name = "SchemeName")
    private String schemeName;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TableNumber")
    private int tableNumber;
    
    @ManyToMany(mappedBy = "autonumberingschemeCollection")
    private Collection<se.nrm.specify.datamodel.Collection> collectionCollection;
    
    @ManyToMany(mappedBy = "autonumberingschemeCollection") 
    private Collection<Discipline> disciplineCollection;
    
    @ManyToMany(mappedBy = "autonumberingschemeCollection")
    private Collection<Division> divisionCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Autonumberingscheme() {
    }

    public Autonumberingscheme(Integer autoNumberingSchemeID) {
        this.autoNumberingSchemeID = autoNumberingSchemeID;
    }

    public Autonumberingscheme(Integer autoNumberingSchemeID, Date timestampCreated, boolean isNumericOnly, int tableNumber) {
        super(timestampCreated);
        this.autoNumberingSchemeID = autoNumberingSchemeID; 
        this.isNumericOnly = isNumericOnly;
        this.tableNumber = tableNumber;
    }

    public Integer getAutoNumberingSchemeID() {
        return autoNumberingSchemeID;
    }

    public void setAutoNumberingSchemeID(Integer autoNumberingSchemeID) {
        this.autoNumberingSchemeID = autoNumberingSchemeID;
    }
 
    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public boolean getIsNumericOnly() {
        return isNumericOnly;
    }

    public void setIsNumericOnly(boolean isNumericOnly) {
        this.isNumericOnly = isNumericOnly;
    }

    public String getSchemeClassName() {
        return schemeClassName;
    }

    public void setSchemeClassName(String schemeClassName) {
        this.schemeClassName = schemeClassName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @XmlTransient
    public Collection<se.nrm.specify.datamodel.Collection> getCollectionCollection() {
        return collectionCollection;
    }

    public void setCollectionCollection(Collection<se.nrm.specify.datamodel.Collection> collectionCollection) {
        this.collectionCollection = collectionCollection;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplineCollection() {
        return disciplineCollection;
    }

    public void setDisciplineCollection(Collection<Discipline> disciplineCollection) {
        this.disciplineCollection = disciplineCollection;
    }

    @XmlTransient
    public Collection<Division> getDivisionCollection() {
        return divisionCollection;
    }

    public void setDivisionCollection(Collection<Division> divisionCollection) {
        this.divisionCollection = divisionCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (autoNumberingSchemeID != null ? autoNumberingSchemeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autonumberingscheme)) {
            return false;
        }
        Autonumberingscheme other = (Autonumberingscheme) object;
        if ((this.autoNumberingSchemeID == null && other.autoNumberingSchemeID != null) || (this.autoNumberingSchemeID != null && !this.autoNumberingSchemeID.equals(other.autoNumberingSchemeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Autonumberingscheme[ autoNumberingSchemeID=" + autoNumberingSchemeID + " ]";
    }
    
}

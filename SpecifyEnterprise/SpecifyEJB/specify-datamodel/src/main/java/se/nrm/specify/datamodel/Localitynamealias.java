package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "localitynamealias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Localitynamealias.findAll", query = "SELECT l FROM Localitynamealias l"),
    @NamedQuery(name = "Localitynamealias.findByLocalityNameAliasId", query = "SELECT l FROM Localitynamealias l WHERE l.localityNameAliasId = :localityNameAliasId"),
    @NamedQuery(name = "Localitynamealias.findByTimestampCreated", query = "SELECT l FROM Localitynamealias l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Localitynamealias.findByTimestampModified", query = "SELECT l FROM Localitynamealias l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Localitynamealias.findByVersion", query = "SELECT l FROM Localitynamealias l WHERE l.version = :version"),
    @NamedQuery(name = "Localitynamealias.findByName", query = "SELECT l FROM Localitynamealias l WHERE l.name = :name"),
    @NamedQuery(name = "Localitynamealias.findBySource", query = "SELECT l FROM Localitynamealias l WHERE l.source = :source")})
public class Localitynamealias extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LocalityNameAliasID")
    private Integer localityNameAliasId;
     
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Source")
    private String source;
    
    @JoinColumn(name = "LocalityID", referencedColumnName = "LocalityID")
    @NotNull
    @ManyToOne(optional = false)
    private Locality locality;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @NotNull
    @ManyToOne(optional = false)
    private Discipline discipline;

    public Localitynamealias() {
    }

    public Localitynamealias(Integer localityNameAliasId) {
        this.localityNameAliasId = localityNameAliasId;
    }

    public Localitynamealias(Integer localityNameAliasId, Date timestampCreated, String name, String source) {
        super(timestampCreated);
        this.localityNameAliasId = localityNameAliasId; 
        this.name = name;
        this.source = source;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (localityNameAliasId != null) ? localityNameAliasId.toString() : "0";
    }
    
    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

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

    @NotNull(message="Locality must be specified.")
    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Integer getLocalityNameAliasId() {
        return localityNameAliasId;
    }

    public void setLocalityNameAliasId(Integer localityNameAliasId) {
        this.localityNameAliasId = localityNameAliasId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @Override
    public String getEntityName() {
        return "localityNameAlias";
    }
  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (localityNameAliasId != null ? localityNameAliasId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Localitynamealias)) {
            return false;
        }
        Localitynamealias other = (Localitynamealias) object;
        if ((this.localityNameAliasId == null && other.localityNameAliasId != null) || (this.localityNameAliasId != null && !this.localityNameAliasId.equals(other.localityNameAliasId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localitynamealias[ localityNameAliasID=" + localityNameAliasId + " ]";
    }
 
}

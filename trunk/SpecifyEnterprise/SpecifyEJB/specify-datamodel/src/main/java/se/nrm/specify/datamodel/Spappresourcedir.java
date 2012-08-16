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
@Table(name = "spappresourcedir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spappresourcedir.findAll", query = "SELECT s FROM Spappresourcedir s"),
    @NamedQuery(name = "Spappresourcedir.findBySpAppResourceDirID", query = "SELECT s FROM Spappresourcedir s WHERE s.spAppResourceDirId = :spAppResourceDirID"),
    @NamedQuery(name = "Spappresourcedir.findByTimestampCreated", query = "SELECT s FROM Spappresourcedir s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Spappresourcedir.findByTimestampModified", query = "SELECT s FROM Spappresourcedir s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Spappresourcedir.findByVersion", query = "SELECT s FROM Spappresourcedir s WHERE s.version = :version"),
    @NamedQuery(name = "Spappresourcedir.findByDisciplineType", query = "SELECT s FROM Spappresourcedir s WHERE s.disciplineType = :disciplineType"),
    @NamedQuery(name = "Spappresourcedir.findByIsPersonal", query = "SELECT s FROM Spappresourcedir s WHERE s.isPersonal = :isPersonal"),
    @NamedQuery(name = "Spappresourcedir.findByUserType", query = "SELECT s FROM Spappresourcedir s WHERE s.userType = :userType")})
public class Spappresourcedir extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpAppResourceDirID")
    private Integer spAppResourceDirId;
     
    @Size(max = 64)
    @Column(name = "DisciplineType")
    private String disciplineType;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPersonal")
    private boolean isPersonal;
    
    @Size(max = 64)
    @Column(name = "UserType")
    private String userType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spAppResourceDir")
    private Collection<Spviewsetobj> spPersistedViewSets;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spAppResourceDir")
    private Collection<Spappresource> spPersistedAppResources;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection collection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline discipline;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser specifyUser;

    public Spappresourcedir() {
    }

    public Spappresourcedir(Integer spAppResourceDirId) {
        this.spAppResourceDirId = spAppResourceDirId;
    }

    public Spappresourcedir(Integer spAppResourceDirId, Date timestampCreated, boolean isPersonal) {
        super(timestampCreated);
        this.spAppResourceDirId = spAppResourceDirId; 
        this.isPersonal = isPersonal;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spAppResourceDirId != null) ? spAppResourceDirId.toString() : "0";
    }
    
    public String getDisciplineType() {
        return disciplineType;
    }

    public void setDisciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
    }

    public boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public se.nrm.specify.datamodel.Collection getCollection() {
        return collection;
    }

    public void setCollection(se.nrm.specify.datamodel.Collection collection) {
        this.collection = collection;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getSpAppResourceDirId() {
        return spAppResourceDirId;
    }

    public void setSpAppResourceDirId(Integer spAppResourceDirId) {
        this.spAppResourceDirId = spAppResourceDirId;
    }

    @XmlTransient
    public Collection<Spappresource> getSpPersistedAppResources() {
        return spPersistedAppResources;
    }

    public void setSpPersistedAppResources(Collection<Spappresource> spPersistedAppResources) {
        this.spPersistedAppResources = spPersistedAppResources;
    }

    @XmlTransient
    public Collection<Spviewsetobj> getSpPersistedViewSets() {
        return spPersistedViewSets;
    }

    public void setSpPersistedViewSets(Collection<Spviewsetobj> spPersistedViewSets) {
        this.spPersistedViewSets = spPersistedViewSets;
    }

    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }

   
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAppResourceDirId != null ? spAppResourceDirId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spappresourcedir)) {
            return false;
        }
        Spappresourcedir other = (Spappresourcedir) object;
        if ((this.spAppResourceDirId == null && other.spAppResourceDirId != null) || (this.spAppResourceDirId != null && !this.spAppResourceDirId.equals(other.spAppResourceDirId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spappresourcedir[ spAppResourceDirID=" + spAppResourceDirId + " ]";
    }
 
}

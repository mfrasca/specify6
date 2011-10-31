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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Spappresourcedir.findBySpAppResourceDirID", query = "SELECT s FROM Spappresourcedir s WHERE s.spAppResourceDirID = :spAppResourceDirID"),
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
    private Integer spAppResourceDirID;
     
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spAppResourceDirID")
    private Collection<Spviewsetobj> spviewsetobjCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "spAppResourceDirID")
    private Collection<Spappresource> spappresourceCollection;
    
    @JoinColumn(name = "CollectionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private se.nrm.specify.datamodel.Collection collectionID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline disciplineID;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser specifyUserID;

    public Spappresourcedir() {
    }

    public Spappresourcedir(Integer spAppResourceDirID) {
        this.spAppResourceDirID = spAppResourceDirID;
    }

    public Spappresourcedir(Integer spAppResourceDirID, Date timestampCreated, boolean isPersonal) {
        super(timestampCreated);
        this.spAppResourceDirID = spAppResourceDirID; 
        this.isPersonal = isPersonal;
    }

    public Integer getSpAppResourceDirID() {
        return spAppResourceDirID;
    }

    public void setSpAppResourceDirID(Integer spAppResourceDirID) {
        this.spAppResourceDirID = spAppResourceDirID;
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

    @XmlTransient
    public Collection<Spviewsetobj> getSpviewsetobjCollection() {
        return spviewsetobjCollection;
    }

    public void setSpviewsetobjCollection(Collection<Spviewsetobj> spviewsetobjCollection) {
        this.spviewsetobjCollection = spviewsetobjCollection;
    }

    @XmlTransient
    public Collection<Spappresource> getSpappresourceCollection() {
        return spappresourceCollection;
    }

    public void setSpappresourceCollection(Collection<Spappresource> spappresourceCollection) {
        this.spappresourceCollection = spappresourceCollection;
    }

    public se.nrm.specify.datamodel.Collection getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(se.nrm.specify.datamodel.Collection collectionID) {
        this.collectionID = collectionID;
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

    public Discipline getDisciplineID() {
        return disciplineID;
    }

    public void setDisciplineID(Discipline disciplineID) {
        this.disciplineID = disciplineID;
    }

    public Specifyuser getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(Specifyuser specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spAppResourceDirID != null ? spAppResourceDirID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spappresourcedir)) {
            return false;
        }
        Spappresourcedir other = (Spappresourcedir) object;
        if ((this.spAppResourceDirID == null && other.spAppResourceDirID != null) || (this.spAppResourceDirID != null && !this.spAppResourceDirID.equals(other.spAppResourceDirID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Spappresourcedir[ spAppResourceDirID=" + spAppResourceDirID + " ]";
    }
    
}

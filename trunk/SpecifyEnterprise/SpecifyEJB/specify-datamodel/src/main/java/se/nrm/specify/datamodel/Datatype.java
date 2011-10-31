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
@Table(name = "datatype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datatype.findAll", query = "SELECT d FROM Datatype d"),
    @NamedQuery(name = "Datatype.findByDataTypeID", query = "SELECT d FROM Datatype d WHERE d.dataTypeID = :dataTypeID"),
    @NamedQuery(name = "Datatype.findByTimestampCreated", query = "SELECT d FROM Datatype d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Datatype.findByTimestampModified", query = "SELECT d FROM Datatype d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Datatype.findByVersion", query = "SELECT d FROM Datatype d WHERE d.version = :version"),
    @NamedQuery(name = "Datatype.findByName", query = "SELECT d FROM Datatype d WHERE d.name = :name")})
public class Datatype extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DataTypeID")
    private Integer dataTypeID;
      
    @Size(max = 50)
    @Column(name = "Name")
    private String name;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataTypeID")
    private Collection<Discipline> disciplineCollection;

    public Datatype() {
    }

    public Datatype(Integer dataTypeID) {
        this.dataTypeID = dataTypeID;
    }

    public Datatype(Integer dataTypeID, Date timestampCreated) {
        super();
        this.dataTypeID = dataTypeID;
        setTimestampCreated(timestampCreated); 
    }

    public Integer getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID(Integer dataTypeID) {
        this.dataTypeID = dataTypeID;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Collection<Discipline> getDisciplineCollection() {
        return disciplineCollection;
    }

    public void setDisciplineCollection(Collection<Discipline> disciplineCollection) {
        this.disciplineCollection = disciplineCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataTypeID != null ? dataTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datatype)) {
            return false;
        }
        Datatype other = (Datatype) object;
        if ((this.dataTypeID == null && other.dataTypeID != null) || (this.dataTypeID != null && !this.dataTypeID.equals(other.dataTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datatype[ dataTypeID=" + dataTypeID + " ]";
    }
    
}

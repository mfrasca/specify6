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
@Table(name = "datatype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datatype.findAll", query = "SELECT d FROM Datatype d"),
    @NamedQuery(name = "Datatype.findByDataTypeId", query = "SELECT d FROM Datatype d WHERE d.dataTypeId = :dataTypeId"),
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
    private Integer dataTypeId;
      
    @Size(max = 50)
    @Column(name = "Name")
    private String name;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataType")
    private Collection<Discipline> disciplines;

    public Datatype() {
    }

    public Datatype(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Datatype(Integer dataTypeId, Date timestampCreated) {
        super();
        this.dataTypeId = dataTypeId;
        setTimestampCreated(timestampCreated); 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (dataTypeId != null) ? dataTypeId.toString() : "0";
    }
     
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }
 
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Collection<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
 
    @Override
    public String getEntityName() {
        return "dataType";
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataTypeId != null ? dataTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datatype)) {
            return false;
        }
        Datatype other = (Datatype) object;
        if ((this.dataTypeId == null && other.dataTypeId != null) || (this.dataTypeId != null && !this.dataTypeId.equals(other.dataTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datatype[ dataTypeId=" + dataTypeId + " ]";
    }
 
}

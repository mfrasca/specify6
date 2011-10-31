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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "geologictimeperiodtreedef")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Geologictimeperiodtreedef.findAll", query = "SELECT g FROM Geologictimeperiodtreedef g"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByGeologicTimePeriodTreeDefID", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.geologicTimePeriodTreeDefID = :geologicTimePeriodTreeDefID"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByTimestampCreated", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByTimestampModified", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByVersion", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.version = :version"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByFullNameDirection", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.fullNameDirection = :fullNameDirection"),
    @NamedQuery(name = "Geologictimeperiodtreedef.findByName", query = "SELECT g FROM Geologictimeperiodtreedef g WHERE g.name = :name")})
public class Geologictimeperiodtreedef extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "GeologicTimePeriodTreeDefID")
    private Integer geologicTimePeriodTreeDefID; 
    
    @Column(name = "FullNameDirection")
    private Integer fullNameDirection;
    
    @Basic(optional = false)
//    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geologicTimePeriodTreeDefID")
    private Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geologicTimePeriodTreeDefID")
    private Collection<Geologictimeperiod> geologictimeperiodCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "geologicTimePeriodTreeDefID")
    private Collection<Discipline> disciplineCollection;

    public Geologictimeperiodtreedef() {
    }

    public Geologictimeperiodtreedef(Integer geologicTimePeriodTreeDefID) {
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID;
    }

    public Geologictimeperiodtreedef(Integer geologicTimePeriodTreeDefID, Date timestampCreated, String name) {
        super(timestampCreated);
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID; 
        this.name = name;
    }

    public Integer getGeologicTimePeriodTreeDefID() {
        return geologicTimePeriodTreeDefID;
    }

    public void setGeologicTimePeriodTreeDefID(Integer geologicTimePeriodTreeDefID) {
        this.geologicTimePeriodTreeDefID = geologicTimePeriodTreeDefID;
    }
 
    public Integer getFullNameDirection() {
        return fullNameDirection;
    }

    public void setFullNameDirection(Integer fullNameDirection) {
        this.fullNameDirection = fullNameDirection;
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

    @XmlTransient
    public Collection<Geologictimeperiodtreedefitem> getGeologictimeperiodtreedefitemCollection() {
        return geologictimeperiodtreedefitemCollection;
    }

    public void setGeologictimeperiodtreedefitemCollection(Collection<Geologictimeperiodtreedefitem> geologictimeperiodtreedefitemCollection) {
        this.geologictimeperiodtreedefitemCollection = geologictimeperiodtreedefitemCollection;
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
    public Collection<Geologictimeperiod> getGeologictimeperiodCollection() {
        return geologictimeperiodCollection;
    }

    public void setGeologictimeperiodCollection(Collection<Geologictimeperiod> geologictimeperiodCollection) {
        this.geologictimeperiodCollection = geologictimeperiodCollection;
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
        hash += (geologicTimePeriodTreeDefID != null ? geologicTimePeriodTreeDefID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Geologictimeperiodtreedef)) {
            return false;
        }
        Geologictimeperiodtreedef other = (Geologictimeperiodtreedef) object;
        if ((this.geologicTimePeriodTreeDefID == null && other.geologicTimePeriodTreeDefID != null) || (this.geologicTimePeriodTreeDefID != null && !this.geologicTimePeriodTreeDefID.equals(other.geologicTimePeriodTreeDefID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geologictimeperiodtreedef[ geologicTimePeriodTreeDefID=" + geologicTimePeriodTreeDefID + " ]";
    }
    
}

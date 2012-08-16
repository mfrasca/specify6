package se.nrm.specify.datamodel;
 
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
import javax.persistence.Table;  
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "groupperson")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupperson.findAll", query = "SELECT g FROM Groupperson g"),
    @NamedQuery(name = "Groupperson.findByGroupPersonID", query = "SELECT g FROM Groupperson g WHERE g.groupPersonId = :groupPersonID"),
    @NamedQuery(name = "Groupperson.findByTimestampCreated", query = "SELECT g FROM Groupperson g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Groupperson.findByTimestampModified", query = "SELECT g FROM Groupperson g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Groupperson.findByVersion", query = "SELECT g FROM Groupperson g WHERE g.version = :version"),
    @NamedQuery(name = "Groupperson.findByOrderNumber", query = "SELECT g FROM Groupperson g WHERE g.orderNumber = :orderNumber")})
public class Groupperson extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GroupPersonID")
    private Integer groupPersonId;
      
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderNumber")
    private short orderNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "DivisionID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Division division;
    
    @JoinColumn(name = "GroupID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent group;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "MemberID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent member;

    public Groupperson() {
    }

    public Groupperson(Integer groupPersonId) {
        this.groupPersonId = groupPersonId;
    }

    public Groupperson(Integer groupPersonId, Date timestampCreated, short orderNumber) {
        super(timestampCreated);
        this.groupPersonId = groupPersonId; 
        this.orderNumber = orderNumber;
    }
 
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (groupPersonId != null) ? groupPersonId.toString() : "0";
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @NotNull(message="Group must be specified.")
    @XmlIDREF
    public Agent getGroup() {
        return group;
    }

    public void setGroup(Agent group) {
        this.group = group;
    }

    public Integer getGroupPersonId() {
        return groupPersonId;
    }

    public void setGroupPersonId(Integer groupPersonId) {
        this.groupPersonId = groupPersonId;
    }

    @NotNull(message="Member must be specified.")
    public Agent getMember() {
        return member;
    }

    public void setMember(Agent member) {
        this.member = member;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 
 
    public short getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(short orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupPersonId != null ? groupPersonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupperson)) {
            return false;
        }
        Groupperson other = (Groupperson) object;
        if ((this.groupPersonId == null && other.groupPersonId != null) || (this.groupPersonId != null && !this.groupPersonId.equals(other.groupPersonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Groupperson[ groupPersonID=" + groupPersonId + " ]";
    } 
}

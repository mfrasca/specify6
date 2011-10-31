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
    @NamedQuery(name = "Groupperson.findByGroupPersonID", query = "SELECT g FROM Groupperson g WHERE g.groupPersonID = :groupPersonID"),
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
    private Integer groupPersonID;
      
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
    private Division divisionID;
    
    @JoinColumn(name = "GroupID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent groupID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "MemberID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent memberID;

    public Groupperson() {
    }

    public Groupperson(Integer groupPersonID) {
        this.groupPersonID = groupPersonID;
    }

    public Groupperson(Integer groupPersonID, Date timestampCreated, short orderNumber) {
        super(timestampCreated);
        this.groupPersonID = groupPersonID; 
        this.orderNumber = orderNumber;
    }

    public Integer getGroupPersonID() {
        return groupPersonID;
    }

    public void setGroupPersonID(Integer groupPersonID) {
        this.groupPersonID = groupPersonID;
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

    public Division getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Division divisionID) {
        this.divisionID = divisionID;
    }

    public Agent getGroupID() {
        return groupID;
    }

    public void setGroupID(Agent groupID) {
        this.groupID = groupID;
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

    public Agent getMemberID() {
        return memberID;
    }

    public void setMemberID(Agent memberID) {
        this.memberID = memberID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupPersonID != null ? groupPersonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupperson)) {
            return false;
        }
        Groupperson other = (Groupperson) object;
        if ((this.groupPersonID == null && other.groupPersonID != null) || (this.groupPersonID != null && !this.groupPersonID.equals(other.groupPersonID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Groupperson[ groupPersonID=" + groupPersonID + " ]";
    }
    
}

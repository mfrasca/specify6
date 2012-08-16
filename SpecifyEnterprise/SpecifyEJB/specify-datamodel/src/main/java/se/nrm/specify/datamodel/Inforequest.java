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
@Table(name = "inforequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inforequest.findAll", query = "SELECT i FROM Inforequest i"),
    @NamedQuery(name = "Inforequest.findByInfoRequestID", query = "SELECT i FROM Inforequest i WHERE i.infoRequestID = :infoRequestID"),
    @NamedQuery(name = "Inforequest.findByTimestampCreated", query = "SELECT i FROM Inforequest i WHERE i.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Inforequest.findByTimestampModified", query = "SELECT i FROM Inforequest i WHERE i.timestampModified = :timestampModified"),
    @NamedQuery(name = "Inforequest.findByVersion", query = "SELECT i FROM Inforequest i WHERE i.version = :version"),
    @NamedQuery(name = "Inforequest.findByCollectionMemberID", query = "SELECT i FROM Inforequest i WHERE i.collectionMemberId = :collectionMemberID"),
    @NamedQuery(name = "Inforequest.findByEmail", query = "SELECT i FROM Inforequest i WHERE i.email = :email"),
    @NamedQuery(name = "Inforequest.findByFirstname", query = "SELECT i FROM Inforequest i WHERE i.firstName = :firstname"),
    @NamedQuery(name = "Inforequest.findByInfoReqNumber", query = "SELECT i FROM Inforequest i WHERE i.infoReqNumber = :infoReqNumber"),
    @NamedQuery(name = "Inforequest.findByInstitution", query = "SELECT i FROM Inforequest i WHERE i.institution = :institution"),
    @NamedQuery(name = "Inforequest.findByLastname", query = "SELECT i FROM Inforequest i WHERE i.lastName = :lastname"),
    @NamedQuery(name = "Inforequest.findByReplyDate", query = "SELECT i FROM Inforequest i WHERE i.replyDate = :replyDate"),
    @NamedQuery(name = "Inforequest.findByRequestDate", query = "SELECT i FROM Inforequest i WHERE i.requestDate = :requestDate")})
public class Inforequest extends BaseEntity {
  
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "InfoRequestID")
    private Integer infoRequestID;
  
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    
    @Size(max = 50)
    @Column(name = "Firstname")
    private String firstName;
    
    @Size(max = 32)
    @Column(name = "InfoReqNumber")
    private String infoReqNumber;
    
    @Size(max = 127)
    @Column(name = "Institution")
    private String institution;
    
    @Size(max = 50)
    @Column(name = "Lastname")
    private String lastName;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "ReplyDate")
    @Temporal(TemporalType.DATE)
    private Date replyDate;
    
    @Column(name = "RequestDate")
    @Temporal(TemporalType.DATE)
    private Date requestDate;
    
    @OneToMany(mappedBy = "infoRequest")
    private Collection<Recordset> recordSets;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent agent;

    public Inforequest() {
    }

    public Inforequest(Integer infoRequestID) {
        this.infoRequestID = infoRequestID;
    }

    public Inforequest(Integer infoRequestID, Date timestampCreated, int collectionMemberId) {
        super(timestampCreated);
        this.infoRequestID = infoRequestID; 
        this.collectionMemberId = collectionMemberId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (infoRequestID != null) ? infoRequestID.toString() : "0";
    }
    public Integer getInfoRequestID() {
        return infoRequestID;
    }

    public void setInfoRequestID(Integer infoRequestID) {
        this.infoRequestID = infoRequestID;
    } 
 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 

    public String getInfoReqNumber() {
        return infoReqNumber;
    }

    public void setInfoReqNumber(String infoReqNumber) {
        this.infoReqNumber = infoReqNumber;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @XmlTransient
    public Collection<Recordset> getRecordSets() {
        return recordSets;
    }

    public void setRecordSets(Collection<Recordset> recordSets) {
        this.recordSets = recordSets;
    }

 

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getCollectionMemberId() {
        return collectionMemberId;
    }

    public void setCollectionMemberId(int collectionMemberId) {
        this.collectionMemberId = collectionMemberId;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (infoRequestID != null ? infoRequestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inforequest)) {
            return false;
        }
        Inforequest other = (Inforequest) object;
        if ((this.infoRequestID == null && other.infoRequestID != null) || (this.infoRequestID != null && !this.infoRequestID.equals(other.infoRequestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inforequest[ infoRequestID=" + infoRequestID + " ]";
    } 
}

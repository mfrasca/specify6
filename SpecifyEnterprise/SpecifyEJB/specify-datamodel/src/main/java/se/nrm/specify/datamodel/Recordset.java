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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "recordset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recordset.findAll", query = "SELECT r FROM Recordset r"),
    @NamedQuery(name = "Recordset.findByRecordSetId", query = "SELECT r FROM Recordset r WHERE r.recordSetId = :recordSetId"),
    @NamedQuery(name = "Recordset.findByTimestampCreated", query = "SELECT r FROM Recordset r WHERE r.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Recordset.findByTimestampModified", query = "SELECT r FROM Recordset r WHERE r.timestampModified = :timestampModified"),
    @NamedQuery(name = "Recordset.findByVersion", query = "SELECT r FROM Recordset r WHERE r.version = :version"),
    @NamedQuery(name = "Recordset.findByCollectionMemberId", query = "SELECT r FROM Recordset r WHERE r.collectionMemberId = :collectionMemberId"),
    @NamedQuery(name = "Recordset.findByAllPermissionLevel", query = "SELECT r FROM Recordset r WHERE r.allPermissionLevel = :allPermissionLevel"),
    @NamedQuery(name = "Recordset.findByTableID", query = "SELECT r FROM Recordset r WHERE r.dbTableId = :tableID"),
    @NamedQuery(name = "Recordset.findByGroupPermissionLevel", query = "SELECT r FROM Recordset r WHERE r.groupPermissionLevel = :groupPermissionLevel"),
    @NamedQuery(name = "Recordset.findByName", query = "SELECT r FROM Recordset r WHERE r.name = :name"),
    @NamedQuery(name = "Recordset.findByOwnerPermissionLevel", query = "SELECT r FROM Recordset r WHERE r.ownerPermissionLevel = :ownerPermissionLevel"),
    @NamedQuery(name = "Recordset.findByType", query = "SELECT r FROM Recordset r WHERE r.type = :type")})
public class Recordset extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "RecordSetID")
    private Integer recordSetId;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberId;
    
    @Column(name = "AllPermissionLevel")
    private Integer allPermissionLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TableID")
    private int dbTableId;
    
    @Column(name = "GroupPermissionLevel")
    private Integer groupPermissionLevel;
    
    @Basic(optional = false) 
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Column(name = "OwnerPermissionLevel")
    private Integer ownerPermissionLevel;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "Type")
    private short type;
    
    @JoinColumn(name = "SpPrincipalID", referencedColumnName = "SpPrincipalID")
    @ManyToOne
    private Spprincipal group;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @NotNull
    @ManyToOne(optional = false)
    private Specifyuser specifyUser;
    
    @JoinColumn(name = "InfoRequestID", referencedColumnName = "InfoRequestID")
    @ManyToOne
    private Inforequest infoRequest;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordSet")
    private Collection<Recordsetitem> recordSetItems;

    public Recordset() {
    }

    public Recordset(Integer recordSetId) {
        this.recordSetId = recordSetId;
    }

    public Recordset(Integer recordSetId, Date timestampCreated, int collectionMemberId, int tableID, String name, short type) {
        super(timestampCreated);
        this.recordSetId = recordSetId; 
        this.collectionMemberId = collectionMemberId;
        this.dbTableId = tableID;
        this.name = name;
        this.type = type;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (recordSetId != null) ? recordSetId.toString() : "0";
    }
   
    public Integer getAllPermissionLevel() {
        return allPermissionLevel;
    }

    public void setAllPermissionLevel(Integer allPermissionLevel) {
        this.allPermissionLevel = allPermissionLevel;
    }

    

    public Integer getGroupPermissionLevel() {
        return groupPermissionLevel;
    }

    public void setGroupPermissionLevel(Integer groupPermissionLevel) {
        this.groupPermissionLevel = groupPermissionLevel;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerPermissionLevel() {
        return ownerPermissionLevel;
    }

    public void setOwnerPermissionLevel(Integer ownerPermissionLevel) {
        this.ownerPermissionLevel = ownerPermissionLevel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
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

    public int getDbTableId() {
        return dbTableId;
    }

    public void setDbTableId(int dbTableId) {
        this.dbTableId = dbTableId;
    }

    public Spprincipal getGroup() {
        return group;
    }

    public void setGroup(Spprincipal group) {
        this.group = group;
    }

    public Inforequest getInfoRequest() {
        return infoRequest;
    }

    public void setInfoRequest(Inforequest infoRequest) {
        this.infoRequest = infoRequest;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getRecordSetId() {
        return recordSetId;
    }

    public void setRecordSetId(Integer recordSetId) {
        this.recordSetId = recordSetId;
    }

    @XmlTransient
    public Collection<Recordsetitem> getRecordSetItems() {
        return recordSetItems;
    }

    public void setRecordSetItems(Collection<Recordsetitem> recordSetItems) {
        this.recordSetItems = recordSetItems;
    }

    @NotNull(message="SpecifyUser must be specified.")
    public Specifyuser getSpecifyUser() {
        return specifyUser;
    }

    public void setSpecifyUser(Specifyuser specifyUser) {
        this.specifyUser = specifyUser;
    }
    
    @Override
    public String getEntityName() {
        return "recordSet";
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordSetId != null ? recordSetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recordset)) {
            return false;
        }
        Recordset other = (Recordset) object;
        if ((this.recordSetId == null && other.recordSetId != null) || (this.recordSetId != null && !this.recordSetId.equals(other.recordSetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recordset[ recordSetID=" + recordSetId + " ]";
    }

 
}

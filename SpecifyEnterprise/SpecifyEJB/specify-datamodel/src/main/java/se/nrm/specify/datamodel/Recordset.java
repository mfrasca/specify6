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
@Table(name = "recordset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recordset.findAll", query = "SELECT r FROM Recordset r"),
    @NamedQuery(name = "Recordset.findByRecordSetID", query = "SELECT r FROM Recordset r WHERE r.recordSetID = :recordSetID"),
    @NamedQuery(name = "Recordset.findByTimestampCreated", query = "SELECT r FROM Recordset r WHERE r.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Recordset.findByTimestampModified", query = "SELECT r FROM Recordset r WHERE r.timestampModified = :timestampModified"),
    @NamedQuery(name = "Recordset.findByVersion", query = "SELECT r FROM Recordset r WHERE r.version = :version"),
    @NamedQuery(name = "Recordset.findByCollectionMemberID", query = "SELECT r FROM Recordset r WHERE r.collectionMemberID = :collectionMemberID"),
    @NamedQuery(name = "Recordset.findByAllPermissionLevel", query = "SELECT r FROM Recordset r WHERE r.allPermissionLevel = :allPermissionLevel"),
    @NamedQuery(name = "Recordset.findByTableID", query = "SELECT r FROM Recordset r WHERE r.tableID = :tableID"),
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
    private Integer recordSetID;
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectionMemberID")
    private int collectionMemberID;
    
    @Column(name = "AllPermissionLevel")
    private Integer allPermissionLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TableID")
    private int tableID;
    
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
    private Spprincipal spPrincipalID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "SpecifyUserID", referencedColumnName = "SpecifyUserID")
    @ManyToOne(optional = false)
    private Specifyuser specifyUserID;
    
    @JoinColumn(name = "InfoRequestID", referencedColumnName = "InfoRequestID")
    @ManyToOne
    private Inforequest infoRequestID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordSetID")
    private Collection<Recordsetitem> recordsetitemCollection;

    public Recordset() {
    }

    public Recordset(Integer recordSetID) {
        this.recordSetID = recordSetID;
    }

    public Recordset(Integer recordSetID, Date timestampCreated, int collectionMemberID, int tableID, String name, short type) {
        super(timestampCreated);
        this.recordSetID = recordSetID; 
        this.collectionMemberID = collectionMemberID;
        this.tableID = tableID;
        this.name = name;
        this.type = type;
    }

    public Integer getRecordSetID() {
        return recordSetID;
    }

    public void setRecordSetID(Integer recordSetID) {
        this.recordSetID = recordSetID;
    } 
    
    public int getCollectionMemberID() {
        return collectionMemberID;
    }

    public void setCollectionMemberID(int collectionMemberID) {
        this.collectionMemberID = collectionMemberID;
    }

    public Integer getAllPermissionLevel() {
        return allPermissionLevel;
    }

    public void setAllPermissionLevel(Integer allPermissionLevel) {
        this.allPermissionLevel = allPermissionLevel;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public Integer getGroupPermissionLevel() {
        return groupPermissionLevel;
    }

    public void setGroupPermissionLevel(Integer groupPermissionLevel) {
        this.groupPermissionLevel = groupPermissionLevel;
    }

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

    public Spprincipal getSpPrincipalID() {
        return spPrincipalID;
    }

    public void setSpPrincipalID(Spprincipal spPrincipalID) {
        this.spPrincipalID = spPrincipalID;
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

    public Specifyuser getSpecifyUserID() {
        return specifyUserID;
    }

    public void setSpecifyUserID(Specifyuser specifyUserID) {
        this.specifyUserID = specifyUserID;
    }

    public Inforequest getInfoRequestID() {
        return infoRequestID;
    }

    public void setInfoRequestID(Inforequest infoRequestID) {
        this.infoRequestID = infoRequestID;
    }

    @XmlTransient
    public Collection<Recordsetitem> getRecordsetitemCollection() {
        return recordsetitemCollection;
    }

    public void setRecordsetitemCollection(Collection<Recordsetitem> recordsetitemCollection) {
        this.recordsetitemCollection = recordsetitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordSetID != null ? recordSetID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recordset)) {
            return false;
        }
        Recordset other = (Recordset) object;
        if ((this.recordSetID == null && other.recordSetID != null) || (this.recordSetID != null && !this.recordSetID.equals(other.recordSetID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recordset[ recordSetID=" + recordSetID + " ]";
    }
    
}

package se.nrm.specify.datamodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "borrowattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borrowattachment.findAll", query = "SELECT b FROM Borrowattachment b"),
    @NamedQuery(name = "Borrowattachment.findByBorrowAttachmentID", query = "SELECT b FROM Borrowattachment b WHERE b.borrowAttachmentId = :borrowAttachmentID"),
    @NamedQuery(name = "Borrowattachment.findByTimestampCreated", query = "SELECT b FROM Borrowattachment b WHERE b.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Borrowattachment.findByTimestampModified", query = "SELECT b FROM Borrowattachment b WHERE b.timestampModified = :timestampModified"),
    @NamedQuery(name = "Borrowattachment.findByVersion", query = "SELECT b FROM Borrowattachment b WHERE b.version = :version"),
    @NamedQuery(name = "Borrowattachment.findByOrdinal", query = "SELECT b FROM Borrowattachment b WHERE b.ordinal = :ordinal"),
    @NamedQuery(name = "Borrowattachment.findByModifiedByAgentID", query = "SELECT b FROM Borrowattachment b WHERE b.modifiedByAgentId = :modifiedByAgentID"),
    @NamedQuery(name = "Borrowattachment.findByCreatedByAgentID", query = "SELECT b FROM Borrowattachment b WHERE b.createdByAgentId = :createdByAgentID"),
    @NamedQuery(name = "Borrowattachment.findByBorrowID", query = "SELECT b FROM Borrowattachment b WHERE b.borrowId = :borrowID"),
    @NamedQuery(name = "Borrowattachment.findByAttachmentID", query = "SELECT b FROM Borrowattachment b WHERE b.attachmentId = :attachmentID")})
public class Borrowattachment extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "BorrowAttachmentID")
    private Integer borrowAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentId;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "BorrowID")
    private int borrowId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AttachmentID")
    private int attachmentId;

    public Borrowattachment() {
    }

    public Borrowattachment(Integer borrowAttachmentId) {
        this.borrowAttachmentId = borrowAttachmentId;
    }

    public Borrowattachment(Integer borrowAttachmentId, Date timestampCreated, int borrowId, int attachmentId) {
        this.borrowAttachmentId = borrowAttachmentId;
        this.timestampCreated = timestampCreated;
        this.borrowId = borrowId;
        this.attachmentId = attachmentId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (borrowAttachmentId != null) ? borrowAttachmentId.toString() : "0";
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getBorrowAttachmentId() {
        return borrowAttachmentId;
    }

    public void setBorrowAttachmentId(Integer borrowAttachmentId) {
        this.borrowAttachmentId = borrowAttachmentId;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public Integer getCreatedByAgentId() {
        return createdByAgentId;
    }

    public void setCreatedByAgentId(Integer createdByAgentId) {
        this.createdByAgentId = createdByAgentId;
    }

    public Integer getModifiedByAgentId() {
        return modifiedByAgentId;
    }

    public void setModifiedByAgentId(Integer modifiedByAgentId) {
        this.modifiedByAgentId = modifiedByAgentId;
    }
     

    
    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
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
        hash += (borrowAttachmentId != null ? borrowAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borrowattachment)) {
            return false;
        }
        Borrowattachment other = (Borrowattachment) object;
        if ((this.borrowAttachmentId == null && other.borrowAttachmentId != null) || (this.borrowAttachmentId != null && !this.borrowAttachmentId.equals(other.borrowAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Borrowattachment[ borrowAttachmentID=" + borrowAttachmentId + " ]";
    }
    
}

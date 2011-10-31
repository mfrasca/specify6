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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "accessionattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accessionattachment.findAll", query = "SELECT a FROM Accessionattachment a"),
    @NamedQuery(name = "Accessionattachment.findByAccessionAttachmentID", query = "SELECT a FROM Accessionattachment a WHERE a.accessionAttachmentID = :accessionAttachmentID"),
    @NamedQuery(name = "Accessionattachment.findByTimestampCreated", query = "SELECT a FROM Accessionattachment a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Accessionattachment.findByTimestampModified", query = "SELECT a FROM Accessionattachment a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Accessionattachment.findByVersion", query = "SELECT a FROM Accessionattachment a WHERE a.version = :version"),
    @NamedQuery(name = "Accessionattachment.findByOrdinal", query = "SELECT a FROM Accessionattachment a WHERE a.ordinal = :ordinal")})
public class Accessionattachment extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AccessionAttachmentID")
    private Integer accessionAttachmentID;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @ManyToOne(optional = false)
    private Accession accessionID;

    public Accessionattachment() {
    }

    public Accessionattachment(Integer accessionAttachmentID) {
        this.accessionAttachmentID = accessionAttachmentID;
    }

    public Accessionattachment(Integer accessionAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.accessionAttachmentID = accessionAttachmentID; 
    }

    public Integer getAccessionAttachmentID() {
        return accessionAttachmentID;
    }

    public void setAccessionAttachmentID(Integer accessionAttachmentID) {
        this.accessionAttachmentID = accessionAttachmentID;
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

    public Attachment getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Attachment attachmentID) {
        this.attachmentID = attachmentID;
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

    public Accession getAccessionID() {
        return accessionID;
    }

    public void setAccessionID(Accession accessionID) {
        this.accessionID = accessionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionAttachmentID != null ? accessionAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessionattachment)) {
            return false;
        }
        Accessionattachment other = (Accessionattachment) object;
        if ((this.accessionAttachmentID == null && other.accessionAttachmentID != null) || (this.accessionAttachmentID != null && !this.accessionAttachmentID.equals(other.accessionAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accessionattachment[ accessionAttachmentID=" + accessionAttachmentID + " ]";
    }
    
}

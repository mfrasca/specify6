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
@Table(name = "dnasequenceattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnasequenceattachment.findAll", query = "SELECT d FROM Dnasequenceattachment d"),
    @NamedQuery(name = "Dnasequenceattachment.findByDnaSequencingRunAttachmentId", query = "SELECT d FROM Dnasequenceattachment d WHERE d.dnaSequencingRunAttachmentId = :dnaSequencingRunAttachmentId"),
    @NamedQuery(name = "Dnasequenceattachment.findByTimestampCreated", query = "SELECT d FROM Dnasequenceattachment d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequenceattachment.findByTimestampModified", query = "SELECT d FROM Dnasequenceattachment d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Dnasequenceattachment.findByVersion", query = "SELECT d FROM Dnasequenceattachment d WHERE d.version = :version"),
    @NamedQuery(name = "Dnasequenceattachment.findByOrdinal", query = "SELECT d FROM Dnasequenceattachment d WHERE d.ordinal = :ordinal")})
public class Dnasequenceattachment extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "DnaSequencingRunAttachmentId")
    private Integer dnaSequencingRunAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "DnaSequencingRunID", referencedColumnName = "DNASequencingRunID")
    @ManyToOne(optional = false)
    private Dnasequencingrun dnaSequencingRunID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Dnasequenceattachment() {
    }

    public Dnasequenceattachment(Integer dnaSequencingRunAttachmentId) {
        this.dnaSequencingRunAttachmentId = dnaSequencingRunAttachmentId;
    }

    public Dnasequenceattachment(Integer dnaSequencingRunAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.dnaSequencingRunAttachmentId = dnaSequencingRunAttachmentId; 
    }

    public Integer getDnaSequencingRunAttachmentId() {
        return dnaSequencingRunAttachmentId;
    }

    public void setDnaSequencingRunAttachmentId(Integer dnaSequencingRunAttachmentId) {
        this.dnaSequencingRunAttachmentId = dnaSequencingRunAttachmentId;
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

    public Dnasequencingrun getDnaSequencingRunID() {
        return dnaSequencingRunID;
    }

    public void setDnaSequencingRunID(Dnasequencingrun dnaSequencingRunID) {
        this.dnaSequencingRunID = dnaSequencingRunID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnaSequencingRunAttachmentId != null ? dnaSequencingRunAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequenceattachment)) {
            return false;
        }
        Dnasequenceattachment other = (Dnasequenceattachment) object;
        if ((this.dnaSequencingRunAttachmentId == null && other.dnaSequencingRunAttachmentId != null) || (this.dnaSequencingRunAttachmentId != null && !this.dnaSequencingRunAttachmentId.equals(other.dnaSequencingRunAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnasequenceattachment[ dnaSequencingRunAttachmentId=" + dnaSequencingRunAttachmentId + " ]";
    }
    
}

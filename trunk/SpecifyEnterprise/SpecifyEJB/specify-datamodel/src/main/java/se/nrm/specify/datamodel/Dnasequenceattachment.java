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
    @NamedQuery(name = "Dnasequenceattachment.findByDnaSequencingAttachmentId", query = "SELECT d FROM Dnasequenceattachment d WHERE d.dnaSequenceAttachmentId = :dnaSequenceAttachmentId"),
    @NamedQuery(name = "Dnasequenceattachment.findByTimestampCreated", query = "SELECT d FROM Dnasequenceattachment d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequenceattachment.findByTimestampModified", query = "SELECT d FROM Dnasequenceattachment d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Dnasequenceattachment.findByVersion", query = "SELECT d FROM Dnasequenceattachment d WHERE d.version = :version"),
    @NamedQuery(name = "Dnasequenceattachment.findByOrdinal", query = "SELECT d FROM Dnasequenceattachment d WHERE d.ordinal = :ordinal")})
public class Dnasequenceattachment extends BaseEntity {
      
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "DnaSequenceAttachmentId")
    private Integer dnaSequenceAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @NotNull
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "DnaSequenceID", referencedColumnName = "DnaSequenceID")
    @NotNull
    @ManyToOne(optional = false)
    private Dnasequence dnasequence;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
 
    public Dnasequenceattachment() {
    }

    public Dnasequenceattachment(Integer dnaSequenceAttachmentId) {
        this.dnaSequenceAttachmentId = dnaSequenceAttachmentId;
    }

    public Dnasequenceattachment(Integer dnaSequenceAttachmentId, Date timestampCreated) {
        this.dnaSequenceAttachmentId = dnaSequenceAttachmentId;
        this.timestampCreated = timestampCreated;
    }
 
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (dnaSequenceAttachmentId != null) ? dnaSequenceAttachmentId.toString() : "0";
    }

    public Integer getDnaSequenceAttachmentId() {
        return dnaSequenceAttachmentId;
    }

    public void setDnaSequenceAttachmentId(Integer dnaSequenceAttachmentId) {
        this.dnaSequenceAttachmentId = dnaSequenceAttachmentId;
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

    @NotNull(message="Attachment must be specified.")
    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }
 

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

 
 
    @NotNull(message="Dnasequence must be specified.")
    public Dnasequence getDnasequence() {
        return dnasequence;
    }

    public void setDnasequence(Dnasequence dnasequence) {
        this.dnasequence = dnasequence;
    }
    
    @Override
    public String getEntityName() {
        return "dnaSequenceAttachment";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnaSequenceAttachmentId != null ? dnaSequenceAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnasequenceattachment)) {
            return false;
        }
        Dnasequenceattachment other = (Dnasequenceattachment) object;
        if ((this.dnaSequenceAttachmentId == null && other.dnaSequenceAttachmentId != null) || (this.dnaSequenceAttachmentId != null && !this.dnaSequenceAttachmentId.equals(other.dnaSequenceAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Dnasequenceattachment[ dnaSequenceAttachmentId=" + dnaSequenceAttachmentId + " ]";
    }
    
}

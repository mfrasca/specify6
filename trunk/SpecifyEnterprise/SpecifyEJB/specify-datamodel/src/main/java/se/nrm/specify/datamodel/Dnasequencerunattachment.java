package se.nrm.specify.datamodel;

import java.io.Serializable;
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
@Table(name = "dnasequencerunattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnasequencerunattachment.findAll", query = "SELECT d FROM Dnasequencerunattachment d"),
    @NamedQuery(name = "Dnasequencerunattachment.findByDnaSequencingRunAttachmentId", query = "SELECT d FROM Dnasequencerunattachment d WHERE d.dnaSequencingRunAttachmentId = :dnaSequencingRunAttachmentId"),
    @NamedQuery(name = "Dnasequencerunattachment.findByTimestampCreated", query = "SELECT d FROM Dnasequencerunattachment d WHERE d.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Dnasequencerunattachment.findByTimestampModified", query = "SELECT d FROM Dnasequencerunattachment d WHERE d.timestampModified = :timestampModified"),
    @NamedQuery(name = "Dnasequencerunattachment.findByVersion", query = "SELECT d FROM Dnasequencerunattachment d WHERE d.version = :version"),
    @NamedQuery(name = "Dnasequencerunattachment.findByOrdinal", query = "SELECT d FROM Dnasequencerunattachment d WHERE d.ordinal = :ordinal")})
public class Dnasequencerunattachment extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
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
    private Attachment attachment;
    
    @JoinColumn(name = "DnaSequencingRunID", referencedColumnName = "DNASequencingRunID")
    @ManyToOne(optional = false)
    private Dnasequencingrun dnasequencingrun;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Dnasequencerunattachment() {
    }

    public Dnasequencerunattachment(Integer dnaSequencingRunAttachmentId) {
        this.dnaSequencingRunAttachmentId = dnaSequencingRunAttachmentId;
    }

    public Dnasequencerunattachment(Integer dnaSequencingRunAttachmentId, Date timestampCreated) {
        this.dnaSequencingRunAttachmentId = dnaSequencingRunAttachmentId;
        this.timestampCreated = timestampCreated;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (dnaSequencingRunAttachmentId != null) ? dnaSequencingRunAttachmentId.toString() : "0";
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

    @NotNull(message="Attachment must be specified.")
    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @NotNull(message="Dnasequencerun must be specified.")
    public Dnasequencingrun getDnasequencingrun() {
        return dnasequencingrun;
    }

    public void setDnasequencingrun(Dnasequencingrun dnasequencingrun) {
        this.dnasequencingrun = dnasequencingrun;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
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
        if (!(object instanceof Dnasequencerunattachment)) {
            return false;
        }
        Dnasequencerunattachment other = (Dnasequencerunattachment) object;
        if ((this.dnaSequencingRunAttachmentId == null && other.dnaSequencingRunAttachmentId != null) || (this.dnaSequencingRunAttachmentId != null && !this.dnaSequencingRunAttachmentId.equals(other.dnaSequencingRunAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Dnasequencerunattachment[ dnaSequencingRunAttachmentId=" + dnaSequencingRunAttachmentId + " ]";
    }
    
}

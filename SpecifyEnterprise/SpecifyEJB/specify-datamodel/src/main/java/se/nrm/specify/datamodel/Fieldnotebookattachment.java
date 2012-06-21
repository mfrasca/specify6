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
@Table(name = "fieldnotebookattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fieldnotebookattachment.findAll", query = "SELECT f FROM Fieldnotebookattachment f"),
    @NamedQuery(name = "Fieldnotebookattachment.findByFieldNotebookAttachmentId", query = "SELECT f FROM Fieldnotebookattachment f WHERE f.fieldNotebookAttachmentId = :fieldNotebookAttachmentId"),
    @NamedQuery(name = "Fieldnotebookattachment.findByTimestampCreated", query = "SELECT f FROM Fieldnotebookattachment f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebookattachment.findByTimestampModified", query = "SELECT f FROM Fieldnotebookattachment f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebookattachment.findByVersion", query = "SELECT f FROM Fieldnotebookattachment f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebookattachment.findByOrdinal", query = "SELECT f FROM Fieldnotebookattachment f WHERE f.ordinal = :ordinal")})
public class Fieldnotebookattachment extends BaseEntity {  
     
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookAttachmentId")
    private Integer fieldNotebookAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "FieldNotebookID", referencedColumnName = "FieldNotebookID")
    @ManyToOne(optional = false)
    private Fieldnotebook fieldNotebook;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Fieldnotebookattachment() {
    }

    public Fieldnotebookattachment(Integer fieldNotebookAttachmentId) {
        this.fieldNotebookAttachmentId = fieldNotebookAttachmentId;
    }

    public Fieldnotebookattachment(Integer fieldNotebookAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookAttachmentId = fieldNotebookAttachmentId; 
    }

    public Integer getFieldNotebookAttachmentId() {
        return fieldNotebookAttachmentId;
    }

    public void setFieldNotebookAttachmentId(Integer fieldNotebookAttachmentId) {
        this.fieldNotebookAttachmentId = fieldNotebookAttachmentId;
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

    public Fieldnotebook getFieldNotebook() {
        return fieldNotebook;
    }

    public void setFieldNotebook(Fieldnotebook fieldNotebook) {
        this.fieldNotebook = fieldNotebook;
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
        hash += (fieldNotebookAttachmentId != null ? fieldNotebookAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookattachment)) {
            return false;
        }
        Fieldnotebookattachment other = (Fieldnotebookattachment) object;
        if ((this.fieldNotebookAttachmentId == null && other.fieldNotebookAttachmentId != null) || (this.fieldNotebookAttachmentId != null && !this.fieldNotebookAttachmentId.equals(other.fieldNotebookAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookattachment[ fieldNotebookAttachmentId=" + fieldNotebookAttachmentId + " ]";
    }
    
}
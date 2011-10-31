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
@Table(name = "fieldnotebookpagesetattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fieldnotebookpagesetattachment.findAll", query = "SELECT f FROM Fieldnotebookpagesetattachment f"),
    @NamedQuery(name = "Fieldnotebookpagesetattachment.findByFieldNotebookPageSetAttachmentId", query = "SELECT f FROM Fieldnotebookpagesetattachment f WHERE f.fieldNotebookPageSetAttachmentId = :fieldNotebookPageSetAttachmentId"),
    @NamedQuery(name = "Fieldnotebookpagesetattachment.findByTimestampCreated", query = "SELECT f FROM Fieldnotebookpagesetattachment f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebookpagesetattachment.findByTimestampModified", query = "SELECT f FROM Fieldnotebookpagesetattachment f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebookpagesetattachment.findByVersion", query = "SELECT f FROM Fieldnotebookpagesetattachment f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebookpagesetattachment.findByOrdinal", query = "SELECT f FROM Fieldnotebookpagesetattachment f WHERE f.ordinal = :ordinal")})
public class Fieldnotebookpagesetattachment extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookPageSetAttachmentId")
    private Integer fieldNotebookPageSetAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "FieldNotebookPageSetID", referencedColumnName = "FieldNotebookPageSetID")
    @ManyToOne(optional = false)
    private Fieldnotebookpageset fieldNotebookPageSetID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Fieldnotebookpagesetattachment() {
    }

    public Fieldnotebookpagesetattachment(Integer fieldNotebookPageSetAttachmentId) {
        this.fieldNotebookPageSetAttachmentId = fieldNotebookPageSetAttachmentId;
    }

    public Fieldnotebookpagesetattachment(Integer fieldNotebookPageSetAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookPageSetAttachmentId = fieldNotebookPageSetAttachmentId; 
    }

    public Integer getFieldNotebookPageSetAttachmentId() {
        return fieldNotebookPageSetAttachmentId;
    }

    public void setFieldNotebookPageSetAttachmentId(Integer fieldNotebookPageSetAttachmentId) {
        this.fieldNotebookPageSetAttachmentId = fieldNotebookPageSetAttachmentId;
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

    public Fieldnotebookpageset getFieldNotebookPageSetID() {
        return fieldNotebookPageSetID;
    }

    public void setFieldNotebookPageSetID(Fieldnotebookpageset fieldNotebookPageSetID) {
        this.fieldNotebookPageSetID = fieldNotebookPageSetID;
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
        hash += (fieldNotebookPageSetAttachmentId != null ? fieldNotebookPageSetAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookpagesetattachment)) {
            return false;
        }
        Fieldnotebookpagesetattachment other = (Fieldnotebookpagesetattachment) object;
        if ((this.fieldNotebookPageSetAttachmentId == null && other.fieldNotebookPageSetAttachmentId != null) || (this.fieldNotebookPageSetAttachmentId != null && !this.fieldNotebookPageSetAttachmentId.equals(other.fieldNotebookPageSetAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookpagesetattachment[ fieldNotebookPageSetAttachmentId=" + fieldNotebookPageSetAttachmentId + " ]";
    }
    
}

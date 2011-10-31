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
@Table(name = "fieldnotebookpageattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fieldnotebookpageattachment.findAll", query = "SELECT f FROM Fieldnotebookpageattachment f"),
    @NamedQuery(name = "Fieldnotebookpageattachment.findByFieldNotebookPageAttachmentId", query = "SELECT f FROM Fieldnotebookpageattachment f WHERE f.fieldNotebookPageAttachmentId = :fieldNotebookPageAttachmentId"),
    @NamedQuery(name = "Fieldnotebookpageattachment.findByTimestampCreated", query = "SELECT f FROM Fieldnotebookpageattachment f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fieldnotebookpageattachment.findByTimestampModified", query = "SELECT f FROM Fieldnotebookpageattachment f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fieldnotebookpageattachment.findByVersion", query = "SELECT f FROM Fieldnotebookpageattachment f WHERE f.version = :version"),
    @NamedQuery(name = "Fieldnotebookpageattachment.findByOrdinal", query = "SELECT f FROM Fieldnotebookpageattachment f WHERE f.ordinal = :ordinal")})
public class Fieldnotebookpageattachment extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "FieldNotebookPageAttachmentId")
    private Integer fieldNotebookPageAttachmentId;
     
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
    
    @JoinColumn(name = "FieldNotebookPageID", referencedColumnName = "FieldNotebookPageID")
    @ManyToOne(optional = false)
    private Fieldnotebookpage fieldNotebookPageID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Fieldnotebookpageattachment() {
    }

    public Fieldnotebookpageattachment(Integer fieldNotebookPageAttachmentId) {
        this.fieldNotebookPageAttachmentId = fieldNotebookPageAttachmentId;
    }

    public Fieldnotebookpageattachment(Integer fieldNotebookPageAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookPageAttachmentId = fieldNotebookPageAttachmentId; 
    }

    public Integer getFieldNotebookPageAttachmentId() {
        return fieldNotebookPageAttachmentId;
    }

    public void setFieldNotebookPageAttachmentId(Integer fieldNotebookPageAttachmentId) {
        this.fieldNotebookPageAttachmentId = fieldNotebookPageAttachmentId;
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

    public Fieldnotebookpage getFieldNotebookPageID() {
        return fieldNotebookPageID;
    }

    public void setFieldNotebookPageID(Fieldnotebookpage fieldNotebookPageID) {
        this.fieldNotebookPageID = fieldNotebookPageID;
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
        hash += (fieldNotebookPageAttachmentId != null ? fieldNotebookPageAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fieldnotebookpageattachment)) {
            return false;
        }
        Fieldnotebookpageattachment other = (Fieldnotebookpageattachment) object;
        if ((this.fieldNotebookPageAttachmentId == null && other.fieldNotebookPageAttachmentId != null) || (this.fieldNotebookPageAttachmentId != null && !this.fieldNotebookPageAttachmentId.equals(other.fieldNotebookPageAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fieldnotebookpageattachment[ fieldNotebookPageAttachmentId=" + fieldNotebookPageAttachmentId + " ]";
    }
    
}

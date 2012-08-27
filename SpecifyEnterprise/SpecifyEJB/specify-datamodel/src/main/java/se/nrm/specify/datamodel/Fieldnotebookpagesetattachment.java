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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NotNull
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "FieldNotebookPageSetID", referencedColumnName = "FieldNotebookPageSetID")
    @NotNull
    @ManyToOne(optional = false)
    private Fieldnotebookpageset fieldNotebookPageSet;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Fieldnotebookpagesetattachment() {
    }

    public Fieldnotebookpagesetattachment(Integer fieldNotebookPageSetAttachmentId) {
        this.fieldNotebookPageSetAttachmentId = fieldNotebookPageSetAttachmentId;
    }

    public Fieldnotebookpagesetattachment(Integer fieldNotebookPageSetAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.fieldNotebookPageSetAttachmentId = fieldNotebookPageSetAttachmentId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (fieldNotebookPageSetAttachmentId != null) ? fieldNotebookPageSetAttachmentId.toString() : "0";
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

    @NotNull(message="Attachment must be specified.")
    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlTransient
    @NotNull(message="FieldNotebookPageSet must be specified.")
    public Fieldnotebookpageset getFieldNotebookPageSet() {
        return fieldNotebookPageSet;
    }

    public void setFieldNotebookPageSet(Fieldnotebookpageset fieldNotebookPageSet) {
        this.fieldNotebookPageSet = fieldNotebookPageSet;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

     /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Fieldnotebookpageset) {
            this.fieldNotebookPageSet = (Fieldnotebookpageset)parent;   
        }
    }
    
    @Override
    public String getEntityName() {
        return "fieldNotebookPageSetAttachment";
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

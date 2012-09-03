package se.nrm.specify.datamodel;

import java.util.Date;
import javax.persistence.*; 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "accessionattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accessionattachment.findAll", query = "SELECT a FROM Accessionattachment a"),
    @NamedQuery(name = "Accessionattachment.findByAccessionAttachmentId", query = "SELECT a FROM Accessionattachment a WHERE a.accessionAttachmentId = :accessionAttachmentId"),
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
    private Integer accessionAttachmentId;
    
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
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AccessionID", referencedColumnName = "AccessionID")
    @NotNull
    @ManyToOne(optional = false)
    private Accession accession;

    public Accessionattachment() {
    }

    public Accessionattachment(Integer accessionAttachmentId) {
        this.accessionAttachmentId = accessionAttachmentId;
    }

    public Accessionattachment(Integer accessionAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.accessionAttachmentId = accessionAttachmentId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (accessionAttachmentId != null) ? accessionAttachmentId.toString() : "0";
    }
    
    public Integer getAccessionAttachmentId() {
        return accessionAttachmentId;
    }

    public void setAccessionAttachmentId(Integer accessionAttachmentId) {
        this.accessionAttachmentId = accessionAttachmentId;
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

    @XmlTransient
    @NotNull(message="Accession must be specified.")
    public Accession getAccession() {
        return accession;
    }

    public void setAccession(Accession accession) {
        this.accession = accession;
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
 
    @Override
    public String getEntityName() {
        return "accessionAttachment";
    }
    
    
    /**
     * Parent pointer
     * 
     * @param u
     * @param parent 
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {  
        if(parent instanceof Accession) {
            this.accession = (Accession)parent;   
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessionAttachmentId != null ? accessionAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accessionattachment)) {
            return false;
        }
        Accessionattachment other = (Accessionattachment) object;
        if ((this.accessionAttachmentId == null && other.accessionAttachmentId != null) || (this.accessionAttachmentId != null && !this.accessionAttachmentId.equals(other.accessionAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Accessionattachment[ accessionAttachmentId=" + accessionAttachmentId + " ]";
    }
 
}

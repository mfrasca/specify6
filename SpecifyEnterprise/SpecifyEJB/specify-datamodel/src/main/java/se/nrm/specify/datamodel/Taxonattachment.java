package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "taxonattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxonattachment.findAll", query = "SELECT t FROM Taxonattachment t"),
    @NamedQuery(name = "Taxonattachment.findByTaxonAttachmentID", query = "SELECT t FROM Taxonattachment t WHERE t.taxonAttachmentId = :taxonAttachmentID"),
    @NamedQuery(name = "Taxonattachment.findByTimestampCreated", query = "SELECT t FROM Taxonattachment t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxonattachment.findByTimestampModified", query = "SELECT t FROM Taxonattachment t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxonattachment.findByVersion", query = "SELECT t FROM Taxonattachment t WHERE t.version = :version"),
    @NamedQuery(name = "Taxonattachment.findByOrdinal", query = "SELECT t FROM Taxonattachment t WHERE t.ordinal = :ordinal")})
public class Taxonattachment extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TaxonAttachmentID")
    private Integer taxonAttachmentId;
     
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @ManyToOne(optional = false)
    private Taxon taxon;

    public Taxonattachment() {
    }

    public Taxonattachment(Integer taxonAttachmentId) {
        this.taxonAttachmentId = taxonAttachmentId;
    }

    public Taxonattachment(Integer taxonAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.taxonAttachmentId = taxonAttachmentId; 
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

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

    public Integer getTaxonAttachmentId() {
        return taxonAttachmentId;
    }

    public void setTaxonAttachmentId(Integer taxonAttachmentId) {
        this.taxonAttachmentId = taxonAttachmentId;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonAttachmentId != null ? taxonAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxonattachment)) {
            return false;
        }
        Taxonattachment other = (Taxonattachment) object;
        if ((this.taxonAttachmentId == null && other.taxonAttachmentId != null) || (this.taxonAttachmentId != null && !this.taxonAttachmentId.equals(other.taxonAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxonattachment[ taxonAttachmentID=" + taxonAttachmentId + " ]";
    }
    
}

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
@Table(name = "taxonattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxonattachment.findAll", query = "SELECT t FROM Taxonattachment t"),
    @NamedQuery(name = "Taxonattachment.findByTaxonAttachmentID", query = "SELECT t FROM Taxonattachment t WHERE t.taxonAttachmentID = :taxonAttachmentID"),
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
    private Integer taxonAttachmentID;
     
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
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @ManyToOne(optional = false)
    private Taxon taxonID;

    public Taxonattachment() {
    }

    public Taxonattachment(Integer taxonAttachmentID) {
        this.taxonAttachmentID = taxonAttachmentID;
    }

    public Taxonattachment(Integer taxonAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.taxonAttachmentID = taxonAttachmentID; 
    }

    public Integer getTaxonAttachmentID() {
        return taxonAttachmentID;
    }

    public void setTaxonAttachmentID(Integer taxonAttachmentID) {
        this.taxonAttachmentID = taxonAttachmentID;
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

    public Taxon getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Taxon taxonID) {
        this.taxonID = taxonID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonAttachmentID != null ? taxonAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxonattachment)) {
            return false;
        }
        Taxonattachment other = (Taxonattachment) object;
        if ((this.taxonAttachmentID == null && other.taxonAttachmentID != null) || (this.taxonAttachmentID != null && !this.taxonAttachmentID.equals(other.taxonAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxonattachment[ taxonAttachmentID=" + taxonAttachmentID + " ]";
    }
    
}

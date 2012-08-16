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
@Table(name = "loanattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loanattachment.findAll", query = "SELECT l FROM Loanattachment l"),
    @NamedQuery(name = "Loanattachment.findByLoanAttachmentID", query = "SELECT l FROM Loanattachment l WHERE l.loanAttachmentId = :loanAttachmentID"),
    @NamedQuery(name = "Loanattachment.findByTimestampCreated", query = "SELECT l FROM Loanattachment l WHERE l.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Loanattachment.findByTimestampModified", query = "SELECT l FROM Loanattachment l WHERE l.timestampModified = :timestampModified"),
    @NamedQuery(name = "Loanattachment.findByVersion", query = "SELECT l FROM Loanattachment l WHERE l.version = :version"),
    @NamedQuery(name = "Loanattachment.findByOrdinal", query = "SELECT l FROM Loanattachment l WHERE l.ordinal = :ordinal")})
public class Loanattachment extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "LoanAttachmentID")
    private Integer loanAttachmentId;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachment;
    
    @JoinColumn(name = "LoanID", referencedColumnName = "LoanID")
    @ManyToOne(optional = false)
    private Loan loan;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Loanattachment() {
    }

    public Loanattachment(Integer loanAttachmentId) {
        this.loanAttachmentId = loanAttachmentId;
    }

    public Loanattachment(Integer loanAttachmentId, Date timestampCreated) {
        super(timestampCreated);
        this.loanAttachmentId = loanAttachmentId; 
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (loanAttachmentId != null) ? loanAttachmentId.toString() : "0";
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

    @NotNull(message="Loan must be specified.")
    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Integer getLoanAttachmentId() {
        return loanAttachmentId;
    }

    public void setLoanAttachmentId(Integer loanAttachmentId) {
        this.loanAttachmentId = loanAttachmentId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
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

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loanAttachmentId != null ? loanAttachmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanattachment)) {
            return false;
        }
        Loanattachment other = (Loanattachment) object;
        if ((this.loanAttachmentId == null && other.loanAttachmentId != null) || (this.loanAttachmentId != null && !this.loanAttachmentId.equals(other.loanAttachmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanattachment[ loanAttachmentID=" + loanAttachmentId + " ]";
    } 
}

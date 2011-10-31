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
@Table(name = "loanattachment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loanattachment.findAll", query = "SELECT l FROM Loanattachment l"),
    @NamedQuery(name = "Loanattachment.findByLoanAttachmentID", query = "SELECT l FROM Loanattachment l WHERE l.loanAttachmentID = :loanAttachmentID"),
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
    private Integer loanAttachmentID;
      
    @Column(name = "Ordinal")
    private Integer ordinal;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @JoinColumn(name = "AttachmentID", referencedColumnName = "AttachmentID")
    @ManyToOne(optional = false)
    private Attachment attachmentID;
    
    @JoinColumn(name = "LoanID", referencedColumnName = "LoanID")
    @ManyToOne(optional = false)
    private Loan loanID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;

    public Loanattachment() {
    }

    public Loanattachment(Integer loanAttachmentID) {
        this.loanAttachmentID = loanAttachmentID;
    }

    public Loanattachment(Integer loanAttachmentID, Date timestampCreated) {
        super(timestampCreated);
        this.loanAttachmentID = loanAttachmentID; 
    }

    public Integer getLoanAttachmentID() {
        return loanAttachmentID;
    }

    public void setLoanAttachmentID(Integer loanAttachmentID) {
        this.loanAttachmentID = loanAttachmentID;
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

    public Loan getLoanID() {
        return loanID;
    }

    public void setLoanID(Loan loanID) {
        this.loanID = loanID;
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
        hash += (loanAttachmentID != null ? loanAttachmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanattachment)) {
            return false;
        }
        Loanattachment other = (Loanattachment) object;
        if ((this.loanAttachmentID == null && other.loanAttachmentID != null) || (this.loanAttachmentID != null && !this.loanAttachmentID.equals(other.loanAttachmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Loanattachment[ loanAttachmentID=" + loanAttachmentID + " ]";
    }
    
}

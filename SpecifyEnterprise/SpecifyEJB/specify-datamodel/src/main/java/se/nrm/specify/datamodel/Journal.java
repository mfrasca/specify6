package se.nrm.specify.datamodel;
 
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;  
import javax.validation.constraints.Size;
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
@Table(name = "journal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Journal.findAll", query = "SELECT j FROM Journal j"),
    @NamedQuery(name = "Journal.findByJournalId", query = "SELECT j FROM Journal j WHERE j.journalId = :journalId"),
    @NamedQuery(name = "Journal.findByTimestampCreated", query = "SELECT j FROM Journal j WHERE j.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Journal.findByTimestampModified", query = "SELECT j FROM Journal j WHERE j.timestampModified = :timestampModified"),
    @NamedQuery(name = "Journal.findByVersion", query = "SELECT j FROM Journal j WHERE j.version = :version"),
    @NamedQuery(name = "Journal.findByGuid", query = "SELECT j FROM Journal j WHERE j.guid = :guid"),
    @NamedQuery(name = "Journal.findByIssn", query = "SELECT j FROM Journal j WHERE j.issn = :issn"),
    @NamedQuery(name = "Journal.findByJournalAbbreviation", query = "SELECT j FROM Journal j WHERE j.journalAbbreviation = :journalAbbreviation"),
    @NamedQuery(name = "Journal.findByJournalName", query = "SELECT j FROM Journal j WHERE j.journalName = :journalName"),
    @NamedQuery(name = "Journal.findByText1", query = "SELECT j FROM Journal j WHERE j.text1 = :text1")})
public class Journal extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "JournalID")
    private Integer journalId; 
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Size(max = 16)
    @Column(name = "ISSN")
    private String issn;
    
    @Size(max = 50)
    @Column(name = "JournalAbbreviation")
    private String journalAbbreviation;
    
    @Size(max = 255)
    @Column(name = "JournalName")
    private String journalName;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @OneToMany(mappedBy = "journal")
    private Collection<Referencework> referenceWorks;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Journal() {
    }

    public Journal(Integer journalId) {
        this.journalId = journalId;
    }

    public Journal(Integer journalId, Date timestampCreated) {
        super(timestampCreated);
        this.journalId = journalId; 
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (journalId != null) ? journalId.toString() : "0";
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Integer getJournalId() {
        return journalId;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlTransient
    public Collection<Referencework> getReferenceWorks() {
        return referenceWorks;
    }

    public void setReferenceWorks(Collection<Referencework> referenceWorks) {
        this.referenceWorks = referenceWorks;
    }

     
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getJournalAbbreviation() {
        return journalAbbreviation;
    }

    public void setJournalAbbreviation(String journalAbbreviation) {
        this.journalAbbreviation = journalAbbreviation;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }
 

    @Override
    public String getEntityName() {
        return "journal";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (journalId != null ? journalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Journal)) {
            return false;
        }
        Journal other = (Journal) object;
        if ((this.journalId == null && other.journalId != null) || (this.journalId != null && !this.journalId.equals(other.journalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Journal[ journalID=" + journalId + " ]";
    }
 
}

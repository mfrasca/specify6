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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "addressofrecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addressofrecord.findAll", query = "SELECT a FROM Addressofrecord a"),
    @NamedQuery(name = "Addressofrecord.findByAddressOfRecordID", query = "SELECT a FROM Addressofrecord a WHERE a.addressOfRecordID = :addressOfRecordID"),
    @NamedQuery(name = "Addressofrecord.findByTimestampCreated", query = "SELECT a FROM Addressofrecord a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Addressofrecord.findByTimestampModified", query = "SELECT a FROM Addressofrecord a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Addressofrecord.findByVersion", query = "SELECT a FROM Addressofrecord a WHERE a.version = :version"),
    @NamedQuery(name = "Addressofrecord.findByAddress", query = "SELECT a FROM Addressofrecord a WHERE a.address = :address"),
    @NamedQuery(name = "Addressofrecord.findByAddress2", query = "SELECT a FROM Addressofrecord a WHERE a.address2 = :address2"),
    @NamedQuery(name = "Addressofrecord.findByCity", query = "SELECT a FROM Addressofrecord a WHERE a.city = :city"),
    @NamedQuery(name = "Addressofrecord.findByCountry", query = "SELECT a FROM Addressofrecord a WHERE a.country = :country"),
    @NamedQuery(name = "Addressofrecord.findByPostalCode", query = "SELECT a FROM Addressofrecord a WHERE a.postalCode = :postalCode"),
    @NamedQuery(name = "Addressofrecord.findByState", query = "SELECT a FROM Addressofrecord a WHERE a.state = :state")})
public class Addressofrecord extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AddressOfRecordID")
    private Integer addressOfRecordID;
 
    @Size(max = 255)
    @Column(name = "Address")
    private String address;
    
    @Size(max = 255)
    @Column(name = "Address2")
    private String address2;
    
    @Size(max = 64)
    @Column(name = "City")
    private String city;
    
    @Size(max = 64)
    @Column(name = "Country")
    private String country;
    
    @Size(max = 32)
    @Column(name = "PostalCode")
    private String postalCode;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "State")
    private String state;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Loan> loanCollection;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Exchangein> exchangeinCollection;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Gift> giftCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent agentID;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Exchangeout> exchangeoutCollection;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Repositoryagreement> repositoryagreementCollection;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Accession> accessionCollection;
    
    @OneToMany(mappedBy = "addressOfRecordID")
    private Collection<Borrow> borrowCollection;

    public Addressofrecord() {
    }

    public Addressofrecord(Integer addressOfRecordID) {
        this.addressOfRecordID = addressOfRecordID;
    }

    public Addressofrecord(Integer addressOfRecordID, Date timestampCreated) {
        super(timestampCreated);
        this.addressOfRecordID = addressOfRecordID; 
    }

    public Integer getAddressOfRecordID() {
        return addressOfRecordID;
    }

    public void setAddressOfRecordID(Integer addressOfRecordID) {
        this.addressOfRecordID = addressOfRecordID;
    } 
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlTransient
    public Collection<Loan> getLoanCollection() {
        return loanCollection;
    }

    public void setLoanCollection(Collection<Loan> loanCollection) {
        this.loanCollection = loanCollection;
    }

    @XmlTransient
    public Collection<Exchangein> getExchangeinCollection() {
        return exchangeinCollection;
    }

    public void setExchangeinCollection(Collection<Exchangein> exchangeinCollection) {
        this.exchangeinCollection = exchangeinCollection;
    }

    @XmlTransient
    public Collection<Gift> getGiftCollection() {
        return giftCollection;
    }

    public void setGiftCollection(Collection<Gift> giftCollection) {
        this.giftCollection = giftCollection;
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

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @XmlTransient
    public Collection<Exchangeout> getExchangeoutCollection() {
        return exchangeoutCollection;
    }

    public void setExchangeoutCollection(Collection<Exchangeout> exchangeoutCollection) {
        this.exchangeoutCollection = exchangeoutCollection;
    }

    @XmlTransient
    public Collection<Repositoryagreement> getRepositoryagreementCollection() {
        return repositoryagreementCollection;
    }

    public void setRepositoryagreementCollection(Collection<Repositoryagreement> repositoryagreementCollection) {
        this.repositoryagreementCollection = repositoryagreementCollection;
    }

    @XmlTransient
    public Collection<Accession> getAccessionCollection() {
        return accessionCollection;
    }

    public void setAccessionCollection(Collection<Accession> accessionCollection) {
        this.accessionCollection = accessionCollection;
    }

    @XmlTransient
    public Collection<Borrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<Borrow> borrowCollection) {
        this.borrowCollection = borrowCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressOfRecordID != null ? addressOfRecordID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addressofrecord)) {
            return false;
        }
        Addressofrecord other = (Addressofrecord) object;
        if ((this.addressOfRecordID == null && other.addressOfRecordID != null) || (this.addressOfRecordID != null && !this.addressOfRecordID.equals(other.addressOfRecordID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Addressofrecord[ addressOfRecordID=" + addressOfRecordID + " ]";
    }
    
}

package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "addressofrecord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addressofrecord.findAll", query = "SELECT a FROM Addressofrecord a"),
    @NamedQuery(name = "Addressofrecord.findByAddressOfRecordID", query = "SELECT a FROM Addressofrecord a WHERE a.addressOfRecordId = :addressOfRecordID"),
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
    private Integer addressOfRecordId;
 
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
    
    @OneToMany(mappedBy = "addressOfRecord", cascade= CascadeType.ALL)
    private Collection<Loan> loans;
    
    @OneToMany(mappedBy = "addressOfRecord", cascade= CascadeType.ALL)
    private Collection<Exchangein> exchangeIns;
    
    @OneToMany(mappedBy = "addressOfRecord", cascade= CascadeType.ALL)
    private Collection<Gift> gifts;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent agent;
    
    @OneToMany(mappedBy = "addressOfRecord")
    private Collection<Exchangeout> exchangeOuts;
    
    @OneToMany(mappedBy = "addressOfRecord")
    private Collection<Repositoryagreement> repositoryAgreements;
    
    @OneToMany(mappedBy = "addressOfRecord")
    private Collection<Accession> accessions;
    
    @OneToMany(mappedBy = "addressOfRecord")
    private Collection<Borrow> borrows;

    public Addressofrecord() {
    }

    public Addressofrecord(Integer addressOfRecordId) {
        this.addressOfRecordId = addressOfRecordId;
    }

    public Addressofrecord(Integer addressOfRecordId, Date timestampCreated) {
        super(timestampCreated);
        this.addressOfRecordId = addressOfRecordId; 
    }

    public Integer getAddressOfRecordId() {
        return addressOfRecordId;
    }

    public void setAddressOfRecordId(Integer addressOfRecordId) {
        this.addressOfRecordId = addressOfRecordId;
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

 

    public Collection<Accession> getAccessions() {
        return accessions;
    }

    public void setAccessions(Collection<Accession> accessions) {
        this.accessions = accessions;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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

    public Collection<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Collection<Borrow> borrows) {
        this.borrows = borrows;
    }

    public Collection<Exchangein> getExchangeIns() {
        return exchangeIns;
    }

    public void setExchangeIns(Collection<Exchangein> exchangeIns) {
        this.exchangeIns = exchangeIns;
    }

    public Collection<Exchangeout> getExchangeOuts() {
        return exchangeOuts;
    }

    public void setExchangeOuts(Collection<Exchangeout> exchangeOuts) {
        this.exchangeOuts = exchangeOuts;
    }

    public Collection<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Collection<Gift> gifts) {
        this.gifts = gifts;
    }

    public Collection<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans = loans;
    }

    public Collection<Repositoryagreement> getRepositoryAgreements() {
        return repositoryAgreements;
    }

    public void setRepositoryAgreements(Collection<Repositoryagreement> repositoryAgreements) {
        this.repositoryAgreements = repositoryAgreements;
    }

    
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressOfRecordId != null ? addressOfRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addressofrecord)) {
            return false;
        }
        Addressofrecord other = (Addressofrecord) object;
        if ((this.addressOfRecordId == null && other.addressOfRecordId != null) || (this.addressOfRecordId != null && !this.addressOfRecordId.equals(other.addressOfRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Addressofrecord[ addressOfRecordId=" + addressOfRecordId + " ]";
    }
    
}

package se.nrm.specify.datamodel;
 
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size; 
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient; 
 


/**
 *
 * @author idali
 */
@Entity
@Table(name = "address")
@XmlRootElement 
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findByAddressID", query = "SELECT a FROM Address a WHERE a.addressID = :addressID"),
    @NamedQuery(name = "Address.findByTimestampCreated", query = "SELECT a FROM Address a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Address.findByTimestampModified", query = "SELECT a FROM Address a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Address.findByVersion", query = "SELECT a FROM Address a WHERE a.version = :version"),
    @NamedQuery(name = "Address.findByAddress", query = "SELECT a FROM Address a WHERE a.address = :address"),
    @NamedQuery(name = "Address.findByAddress2", query = "SELECT a FROM Address a WHERE a.address2 = :address2"),
    @NamedQuery(name = "Address.findByAddress3", query = "SELECT a FROM Address a WHERE a.address3 = :address3"),
    @NamedQuery(name = "Address.findByAddress4", query = "SELECT a FROM Address a WHERE a.address4 = :address4"),
    @NamedQuery(name = "Address.findByAddress5", query = "SELECT a FROM Address a WHERE a.address5 = :address5"),
    @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city"),
    @NamedQuery(name = "Address.findByCountry", query = "SELECT a FROM Address a WHERE a.country = :country"),
    @NamedQuery(name = "Address.findByEndDate", query = "SELECT a FROM Address a WHERE a.endDate = :endDate"),
    @NamedQuery(name = "Address.findByFax", query = "SELECT a FROM Address a WHERE a.fax = :fax"),
    @NamedQuery(name = "Address.findByIsCurrent", query = "SELECT a FROM Address a WHERE a.isCurrent = :isCurrent"),
    @NamedQuery(name = "Address.findByIsPrimary", query = "SELECT a FROM Address a WHERE a.isPrimary = :isPrimary"),
    @NamedQuery(name = "Address.findByIsShipping", query = "SELECT a FROM Address a WHERE a.isShipping = :isShipping"),
    @NamedQuery(name = "Address.findByOrdinal", query = "SELECT a FROM Address a WHERE a.ordinal = :ordinal"),
    @NamedQuery(name = "Address.findByPhone1", query = "SELECT a FROM Address a WHERE a.phone1 = :phone1"),
    @NamedQuery(name = "Address.findByPhone2", query = "SELECT a FROM Address a WHERE a.phone2 = :phone2"),
    @NamedQuery(name = "Address.findByPositionHeld", query = "SELECT a FROM Address a WHERE a.positionHeld = :positionHeld"),
    @NamedQuery(name = "Address.findByPostalCode", query = "SELECT a FROM Address a WHERE a.postalCode = :postalCode"),
    @NamedQuery(name = "Address.findByRoomOrBuilding", query = "SELECT a FROM Address a WHERE a.roomOrBuilding = :roomOrBuilding"),
    @NamedQuery(name = "Address.findByStartDate", query = "SELECT a FROM Address a WHERE a.startDate = :startDate"),
    @NamedQuery(name = "Address.findByState", query = "SELECT a FROM Address a WHERE a.state = :state"),
    @NamedQuery(name = "Address.findByTypeOfAddr", query = "SELECT a FROM Address a WHERE a.typeOfAddr = :typeOfAddr")})
public class Address extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AddressID") 
    private Integer addressID;
      
    @Size(max = 255)
    @Column(name = "Address") 
    private String address;
    
    @Size(max = 255)
    @Column(name = "Address2") 
    private String address2;
    
    @Size(max = 64)
    @Column(name = "Address3") 
    private String address3;
    
    @Size(max = 64)
    @Column(name = "Address4") 
    private String address4;
    
    @Size(max = 64)
    @Column(name = "Address5") 
    private String address5;
    
    @Size(max = 64)
    @Column(name = "City") 
    private String city;
    
    @Size(max = 64)
    @Column(name = "Country") 
    private String country;
    
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE) 
    private Date endDate;
    
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Fax") 
    private String fax;
    
    @Column(name = "IsCurrent") 
    private Boolean isCurrent;
    
    @Column(name = "IsPrimary") 
    private Boolean isPrimary;
    
    @Column(name = "IsShipping") 
    private Boolean isShipping;
    
    @Column(name = "Ordinal") 
    private Integer ordinal;
    
    @Size(max = 50)
    @Column(name = "Phone1") 
    private String phone1;
    
    @Size(max = 50)
    @Column(name = "Phone2")
    private String phone2;
    
    @Size(max = 32)
    @Column(name = "PositionHeld") 
    private String positionHeld;
    
    @Size(max = 32)
    @Column(name = "PostalCode") 
    private String postalCode;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks") 
    private String remarks;
    
    @Size(max = 50)
    @Column(name = "RoomOrBuilding") 
    private String roomOrBuilding;
    
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)  
    private Date startDate;
    
    @Size(max = 64)
    @Column(name = "State") 
    private String state;
    
    @Size(max = 32)
    @Column(name = "TypeOfAddr") 
    private String typeOfAddr;
    
    @OneToMany(mappedBy = "addressID", cascade = CascadeType.ALL)
//    @XmlElement 
    private Collection<Division> divisionCollection;
    
    @OneToMany(mappedBy = "addressID")
//    @XmlElement 
    private Collection<Institution> institutionCollection;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
//    @XmlInverseReference(mappedBy = "addressCollection") 
    @XmlTransient 
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
//    @XmlInverseReference(mappedBy = "addressCollection1") 
    @XmlTransient 
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne 
    private Agent agentID;

    public Address() {
        super();
    }

    public Address(Integer addressID) {
        super();
        this.addressID = addressID;
    }

    public Address(Integer addressID, Date timestampCreated) { 
        super(timestampCreated);
        this.addressID = addressID; 
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
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

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean getIsShipping() {
        return isShipping;
    }

    public void setIsShipping(Boolean isShipping) {
        this.isShipping = isShipping;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPositionHeld() {
        return positionHeld;
    }

    public void setPositionHeld(String positionHeld) {
        this.positionHeld = positionHeld;
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

    public String getRoomOrBuilding() {
        return roomOrBuilding;
    }

    public void setRoomOrBuilding(String roomOrBuilding) {
        this.roomOrBuilding = roomOrBuilding;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypeOfAddr() {
        return typeOfAddr;
    }

    public void setTypeOfAddr(String typeOfAddr) {
        this.typeOfAddr = typeOfAddr;
    }

    @XmlTransient
    public Collection<Division> getDivisionCollection() {
        return divisionCollection;
    }

    public void setDivisionCollection(Collection<Division> divisionCollection) {
        this.divisionCollection = divisionCollection;
    }

    @XmlTransient
    public Collection<Institution> getInstitutionCollection() {
        return institutionCollection;
    }

    public void setInstitutionCollection(Collection<Institution> institutionCollection) {
        this.institutionCollection = institutionCollection;
    }

    @XmlTransient
    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlTransient
    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }
 
    @XmlTransient
    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressID != null ? addressID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressID == null && other.addressID != null) || (this.addressID != null && !this.addressID.equals(other.addressID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address[ addressID=" + addressID + " ], version = " + version + " address = " + address + ", " + city + ", " + country;
    } 
}

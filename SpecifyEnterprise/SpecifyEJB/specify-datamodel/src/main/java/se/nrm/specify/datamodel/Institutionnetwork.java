/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "institutionnetwork")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institutionnetwork.findAll", query = "SELECT i FROM Institutionnetwork i"),
    @NamedQuery(name = "Institutionnetwork.findByInstitutionNetworkId", query = "SELECT i FROM Institutionnetwork i WHERE i.institutionNetworkId = :institutionNetworkId"),
    @NamedQuery(name = "Institutionnetwork.findByTimestampCreated", query = "SELECT i FROM Institutionnetwork i WHERE i.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Institutionnetwork.findByTimestampModified", query = "SELECT i FROM Institutionnetwork i WHERE i.timestampModified = :timestampModified"),
    @NamedQuery(name = "Institutionnetwork.findByVersion", query = "SELECT i FROM Institutionnetwork i WHERE i.version = :version"),
    @NamedQuery(name = "Institutionnetwork.findByAltName", query = "SELECT i FROM Institutionnetwork i WHERE i.altName = :altName"),
    @NamedQuery(name = "Institutionnetwork.findByCode", query = "SELECT i FROM Institutionnetwork i WHERE i.code = :code"),
    @NamedQuery(name = "Institutionnetwork.findByIconURI", query = "SELECT i FROM Institutionnetwork i WHERE i.iconURI = :iconURI"),
    @NamedQuery(name = "Institutionnetwork.findByName", query = "SELECT i FROM Institutionnetwork i WHERE i.name = :name"),
    @NamedQuery(name = "Institutionnetwork.findByUri", query = "SELECT i FROM Institutionnetwork i WHERE i.uri = :uri"),
    @NamedQuery(name = "Institutionnetwork.findByAddressID", query = "SELECT i FROM Institutionnetwork i WHERE i.addressId = :addressID"),
    @NamedQuery(name = "Institutionnetwork.findByModifiedByAgentID", query = "SELECT i FROM Institutionnetwork i WHERE i.modifiedByAgentId = :modifiedByAgentID"),
    @NamedQuery(name = "Institutionnetwork.findByCreatedByAgentID", query = "SELECT i FROM Institutionnetwork i WHERE i.createdByAgentId = :createdByAgentID")})
public class Institutionnetwork extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "InstitutionNetworkID")
    private Integer institutionNetworkId;
 
    @Size(max = 128)
    @Column(name = "AltName")
    private String altName;
    
    @Size(max = 64)
    @Column(name = "Code")
    private String code;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Copyright")
    private String copyright;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Disclaimer")
    private String disclaimer;
    
    @Size(max = 255)
    @Column(name = "IconURI")
    private String iconURI;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Ipr")
    private String ipr;
    @Lob
    @Size(max = 65535)
    @Column(name = "License")
    private String license;
    
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "TermsOfUse")
    private String termsOfUse;
    
    @Size(max = 255)
    @Column(name = "Uri")
    private String uri;
    
    @Column(name = "AddressID")
    private Integer addressId;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentId;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentId;

    public Institutionnetwork() {
    }

    public Institutionnetwork(Integer institutionNetworkId) {
        this.institutionNetworkId = institutionNetworkId;
    }

    public Institutionnetwork(Integer institutionNetworkId, Date timestampCreated) {
        this.institutionNetworkId = institutionNetworkId;
        this.timestampCreated = timestampCreated;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (institutionNetworkId != null) ? institutionNetworkId.toString() : "0";
    }
    
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getCreatedByAgentId() {
        return createdByAgentId;
    }

    public void setCreatedByAgentId(Integer createdByAgentId) {
        this.createdByAgentId = createdByAgentId;
    }

    public Integer getInstitutionNetworkId() {
        return institutionNetworkId;
    }

    public void setInstitutionNetworkId(Integer institutionNetworkId) {
        this.institutionNetworkId = institutionNetworkId;
    }

    public Integer getModifiedByAgentId() {
        return modifiedByAgentId;
    }

    public void setModifiedByAgentId(Integer modifiedByAgentId) {
        this.modifiedByAgentId = modifiedByAgentId;
    }

   
 

    public String getAltName() {
        return altName;
    }

    public void setAltName(String altName) {
        this.altName = altName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
    }

    public String getIpr() {
        return ipr;
    }

    public void setIpr(String ipr) {
        this.ipr = ipr;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getEntityName() {
        return "institutionNetwork";
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (institutionNetworkId != null ? institutionNetworkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institutionnetwork)) {
            return false;
        }
        Institutionnetwork other = (Institutionnetwork) object;
        if ((this.institutionNetworkId == null && other.institutionNetworkId != null) || (this.institutionNetworkId != null && !this.institutionNetworkId.equals(other.institutionNetworkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Institutionnetwork[ institutionNetworkID=" + institutionNetworkId + " ]";
    }
    
}

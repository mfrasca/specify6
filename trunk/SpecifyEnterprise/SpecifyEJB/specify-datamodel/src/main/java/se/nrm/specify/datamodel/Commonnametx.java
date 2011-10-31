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
@Table(name = "commonnametx")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commonnametx.findAll", query = "SELECT c FROM Commonnametx c"),
    @NamedQuery(name = "Commonnametx.findByCommonNameTxID", query = "SELECT c FROM Commonnametx c WHERE c.commonNameTxID = :commonNameTxID"),
    @NamedQuery(name = "Commonnametx.findByTimestampCreated", query = "SELECT c FROM Commonnametx c WHERE c.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Commonnametx.findByTimestampModified", query = "SELECT c FROM Commonnametx c WHERE c.timestampModified = :timestampModified"),
    @NamedQuery(name = "Commonnametx.findByVersion", query = "SELECT c FROM Commonnametx c WHERE c.version = :version"),
    @NamedQuery(name = "Commonnametx.findByAuthor", query = "SELECT c FROM Commonnametx c WHERE c.author = :author"),
    @NamedQuery(name = "Commonnametx.findByCountry", query = "SELECT c FROM Commonnametx c WHERE c.country = :country"),
    @NamedQuery(name = "Commonnametx.findByLanguage", query = "SELECT c FROM Commonnametx c WHERE c.language = :language"),
    @NamedQuery(name = "Commonnametx.findByName", query = "SELECT c FROM Commonnametx c WHERE c.name = :name"),
    @NamedQuery(name = "Commonnametx.findByVariant", query = "SELECT c FROM Commonnametx c WHERE c.variant = :variant")})
public class Commonnametx extends BaseEntity { 
        
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "CommonNameTxID")
    private Integer commonNameTxID;
     
    @Size(max = 128)
    @Column(name = "Author")
    private String author;
    
    @Size(max = 2)
    @Column(name = "Country")
    private String country;
    
    @Size(max = 2)
    @Column(name = "Language")
    private String language;
    
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 2)
    @Column(name = "Variant")
    private String variant;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "TaxonID", referencedColumnName = "TaxonID")
    @ManyToOne(optional = false)
    private Taxon taxonID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commonNameTxID")
    private Collection<Commonnametxcitation> commonnametxcitationCollection;

    public Commonnametx() {
    }

    public Commonnametx(Integer commonNameTxID) {
        this.commonNameTxID = commonNameTxID;
    }

    public Commonnametx(Integer commonNameTxID, Date timestampCreated) {
        this.commonNameTxID = commonNameTxID;
//        this.timestampCreated = timestampCreated;
        setTimestampCreated(timestampCreated);
    }

    public Integer getCommonNameTxID() {
        return commonNameTxID;
    }

    public void setCommonNameTxID(Integer commonNameTxID) {
        this.commonNameTxID = commonNameTxID;
    } 

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
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

    @XmlTransient
    public Collection<Commonnametxcitation> getCommonnametxcitationCollection() {
        return commonnametxcitationCollection;
    }

    public void setCommonnametxcitationCollection(Collection<Commonnametxcitation> commonnametxcitationCollection) {
        this.commonnametxcitationCollection = commonnametxcitationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commonNameTxID != null ? commonNameTxID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commonnametx)) {
            return false;
        }
        Commonnametx other = (Commonnametx) object;
        if ((this.commonNameTxID == null && other.commonNameTxID != null) || (this.commonNameTxID != null && !this.commonNameTxID.equals(other.commonNameTxID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Commonnametx[ commonNameTxID=" + commonNameTxID + " ]";
    }
    
}

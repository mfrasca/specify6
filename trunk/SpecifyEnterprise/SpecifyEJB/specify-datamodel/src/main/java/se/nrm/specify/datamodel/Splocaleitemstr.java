package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table; 
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "splocaleitemstr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Splocaleitemstr.findAll", query = "SELECT s FROM Splocaleitemstr s"),
    @NamedQuery(name = "Splocaleitemstr.findBySpLocaleItemStrID", query = "SELECT s FROM Splocaleitemstr s WHERE s.spLocaleItemStrId = :spLocaleItemStrID"),
    @NamedQuery(name = "Splocaleitemstr.findByTimestampCreated", query = "SELECT s FROM Splocaleitemstr s WHERE s.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Splocaleitemstr.findByTimestampModified", query = "SELECT s FROM Splocaleitemstr s WHERE s.timestampModified = :timestampModified"),
    @NamedQuery(name = "Splocaleitemstr.findByVersion", query = "SELECT s FROM Splocaleitemstr s WHERE s.version = :version"),
    @NamedQuery(name = "Splocaleitemstr.findByCountry", query = "SELECT s FROM Splocaleitemstr s WHERE s.country = :country"),
    @NamedQuery(name = "Splocaleitemstr.findByLanguage", query = "SELECT s FROM Splocaleitemstr s WHERE s.language = :language"),
    @NamedQuery(name = "Splocaleitemstr.findByText", query = "SELECT s FROM Splocaleitemstr s WHERE s.text = :text"),
    @NamedQuery(name = "Splocaleitemstr.findByVariant", query = "SELECT s FROM Splocaleitemstr s WHERE s.variant = :variant")})
public class Splocaleitemstr extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "SpLocaleItemStrID")
    private Integer spLocaleItemStrId;
     
    @Size(max = 2)
    @Column(name = "Country")
    private String country;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 2)
    @Column(name = "Language")
    private String language;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 255)
    @Column(name = "Text")
    private String text;
    
    @Size(max = 2)
    @Column(name = "Variant")
    private String variant;
    
    @JoinColumn(name = "SpLocaleContainerItemNameID", referencedColumnName = "SpLocaleContainerItemID")
    @ManyToOne
    private Splocalecontaineritem itemName;
    
    @JoinColumn(name = "SpLocaleContainerItemDescID", referencedColumnName = "SpLocaleContainerItemID")
    @ManyToOne
    private Splocalecontaineritem itemDesc;
    
    @JoinColumn(name = "SpLocaleContainerNameID", referencedColumnName = "SpLocaleContainerID")
    @ManyToOne
    private Splocalecontainer containerName;
    
    @JoinColumn(name = "SpLocaleContainerDescID", referencedColumnName = "SpLocaleContainerID")
    @ManyToOne
    private Splocalecontainer containerDesc;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;

    public Splocaleitemstr() {
    }

    public Splocaleitemstr(Integer spLocaleItemStrId) {
        this.spLocaleItemStrId = spLocaleItemStrId;
    }

    public Splocaleitemstr(Integer spLocaleItemStrId, Date timestampCreated, String language, String text) {
        super(timestampCreated);
        this.spLocaleItemStrId = spLocaleItemStrId; 
        this.language = language;
        this.text = text;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (spLocaleItemStrId != null) ? spLocaleItemStrId.toString() : "0";
    }

    public String getCountry() {
        return country;
    }

    
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    

    @NotNull(message="Language must be specified.")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @NotNull(message="Text must be specified.")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Splocalecontainer getContainerDesc() {
        return containerDesc;
    }

    public void setContainerDesc(Splocalecontainer containerDesc) {
        this.containerDesc = containerDesc;
    }

    public Splocalecontainer getContainerName() {
        return containerName;
    }

    public void setContainerName(Splocalecontainer containerName) {
        this.containerName = containerName;
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Splocalecontaineritem getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(Splocalecontaineritem itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Splocalecontaineritem getItemName() {
        return itemName;
    }

    public void setItemName(Splocalecontaineritem itemName) {
        this.itemName = itemName;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getSpLocaleItemStrId() {
        return spLocaleItemStrId;
    }

    public void setSpLocaleItemStrId(Integer spLocaleItemStrId) {
        this.spLocaleItemStrId = spLocaleItemStrId;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spLocaleItemStrId != null ? spLocaleItemStrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Splocaleitemstr)) {
            return false;
        }
        Splocaleitemstr other = (Splocaleitemstr) object;
        if ((this.spLocaleItemStrId == null && other.spLocaleItemStrId != null) || (this.spLocaleItemStrId != null && !this.spLocaleItemStrId.equals(other.spLocaleItemStrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Splocaleitemstr[ spLocaleItemStrID=" + spLocaleItemStrId + " ]";
    }
 
}

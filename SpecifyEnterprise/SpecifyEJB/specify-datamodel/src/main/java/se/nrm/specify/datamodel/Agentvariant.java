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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "agentvariant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agentvariant.findAll", query = "SELECT a FROM Agentvariant a"),
    @NamedQuery(name = "Agentvariant.findByAgentVariantID", query = "SELECT a FROM Agentvariant a WHERE a.agentVariantID = :agentVariantID"),
    @NamedQuery(name = "Agentvariant.findByTimestampCreated", query = "SELECT a FROM Agentvariant a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Agentvariant.findByTimestampModified", query = "SELECT a FROM Agentvariant a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Agentvariant.findByVersion", query = "SELECT a FROM Agentvariant a WHERE a.version = :version"),
    @NamedQuery(name = "Agentvariant.findByCountry", query = "SELECT a FROM Agentvariant a WHERE a.country = :country"),
    @NamedQuery(name = "Agentvariant.findByLanguage", query = "SELECT a FROM Agentvariant a WHERE a.language = :language"),
    @NamedQuery(name = "Agentvariant.findByName", query = "SELECT a FROM Agentvariant a WHERE a.name = :name"),
    @NamedQuery(name = "Agentvariant.findByVarType", query = "SELECT a FROM Agentvariant a WHERE a.varType = :varType"),
    @NamedQuery(name = "Agentvariant.findByVariant", query = "SELECT a FROM Agentvariant a WHERE a.variant = :variant")})
public class Agentvariant extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AgentVariantID")
    private Integer agentVariantID;
     
    @Size(max = 2)
    @Column(name = "Country")
    private String country;
    
    @Size(max = 2)
    @Column(name = "Language")
    private String language;
    
    @Size(max = 255)
    @Column(name = "Name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VarType")
    private short varType;
    
    @Size(max = 2)
    @Column(name = "Variant")
    private String variant;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @JoinColumn(name = "AgentID", referencedColumnName = "AgentID")
    @ManyToOne(optional = false)
    private Agent agentID;

    public Agentvariant() {
    }

    public Agentvariant(Integer agentVariantID) {
        this.agentVariantID = agentVariantID;
    }

    public Agentvariant(Integer agentVariantID, Date timestampCreated, short varType) {
        this.agentVariantID = agentVariantID; 
        this.varType = varType;
    }

    public Integer getAgentVariantID() {
        return agentVariantID;
    }

    public void setAgentVariantID(Integer agentVariantID) {
        this.agentVariantID = agentVariantID;
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

    public short getVarType() {
        return varType;
    }

    public void setVarType(short varType) {
        this.varType = varType;
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

    public Agent getAgentID() {
        return agentID;
    }

    public void setAgentID(Agent agentID) {
        this.agentID = agentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentVariantID != null ? agentVariantID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agentvariant)) {
            return false;
        }
        Agentvariant other = (Agentvariant) object;
        if ((this.agentVariantID == null && other.agentVariantID != null) || (this.agentVariantID != null && !this.agentVariantID.equals(other.agentVariantID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agentvariant[ agentVariantID=" + agentVariantID + " ]";
    }
    
}

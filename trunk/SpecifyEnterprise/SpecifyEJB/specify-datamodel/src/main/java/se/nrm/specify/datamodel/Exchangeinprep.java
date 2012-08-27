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
@Table(name = "exchangeinprep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exchangeinprep.findAll", query = "SELECT e FROM Exchangeinprep e"),
    @NamedQuery(name = "Exchangeinprep.findByExchangeInPrepId", query = "SELECT e FROM Exchangeinprep e WHERE e.exchangeInPrepId = :exchangeInPrepId"),
    @NamedQuery(name = "Exchangeinprep.findByTimestampCreated", query = "SELECT e FROM Exchangeinprep e WHERE e.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Exchangeinprep.findByTimestampModified", query = "SELECT e FROM Exchangeinprep e WHERE e.timestampModified = :timestampModified"),
    @NamedQuery(name = "Exchangeinprep.findByVersion", query = "SELECT e FROM Exchangeinprep e WHERE e.version = :version"),
    @NamedQuery(name = "Exchangeinprep.findByDescriptionOfMaterial", query = "SELECT e FROM Exchangeinprep e WHERE e.descriptionOfMaterial = :descriptionOfMaterial"),
    @NamedQuery(name = "Exchangeinprep.findByNumber1", query = "SELECT e FROM Exchangeinprep e WHERE e.number1 = :number1"),
    @NamedQuery(name = "Exchangeinprep.findByQuantity", query = "SELECT e FROM Exchangeinprep e WHERE e.quantity = :quantity"),
    @NamedQuery(name = "Exchangeinprep.findByPreparation", query = "SELECT e FROM Exchangeinprep e WHERE e.preparation = :preparation"),
    @NamedQuery(name = "Exchangeinprep.findByModifiedByAgent", query = "SELECT e FROM Exchangeinprep e WHERE e.modifiedByAgent = :modifiedByAgent"),
    @NamedQuery(name = "Exchangeinprep.findByDisciplineID", query = "SELECT e FROM Exchangeinprep e WHERE e.discipline = :disciplineID"),
    @NamedQuery(name = "Exchangeinprep.findByExchangeInID", query = "SELECT e FROM Exchangeinprep e WHERE e.exchangeIn = :exchangeInID"),
    @NamedQuery(name = "Exchangeinprep.findByCreatedByAgent", query = "SELECT e FROM Exchangeinprep e WHERE e.createdByAgent = :createdByAgent")})
public class Exchangeinprep extends BaseEntity {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ExchangeInPrepID")
    private Integer exchangeInPrepId;
     
    @Lob
    @Size(max = 65535)
    @Column(name = "Comments")
    private String comments;
    
    @Size(max = 255)
    @Column(name = "DescriptionOfMaterial")
    private String descriptionOfMaterial;
    
    @Column(name = "Number1")
    private Integer number1;
    
    @Column(name = "Quantity")
    private Integer quantity;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text1")
    private String text1;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Text2")
    private String text2;
    
    @Column(name = "PreparationID")
    private Integer preparation;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgent;
    
    @Basic(optional = false) 
    @NotNull
    @Column(name = "DisciplineID")
    private int discipline;
    
    @Column(name = "ExchangeInID")
    private Integer exchangeIn;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgent;

    public Exchangeinprep() {
    }

    public Exchangeinprep(Integer exchangeInPrepId) {
        this.exchangeInPrepId = exchangeInPrepId;
    }

    public Exchangeinprep(Integer exchangeInPrepId, Date timestampCreated, int discipline) {
        super(timestampCreated);
        this.exchangeInPrepId = exchangeInPrepId; 
        this.discipline = discipline;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (exchangeInPrepId != null) ? exchangeInPrepId.toString() : "0";
    }
    
    public Integer getExchangeInPrepId() {
        return exchangeInPrepId;
    }

    public void setExchangeInPrepId(Integer exchangeInPrepId) {
        this.exchangeInPrepId = exchangeInPrepId;
    }
  
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDescriptionOfMaterial() {
        return descriptionOfMaterial;
    }

    public void setDescriptionOfMaterial(String descriptionOfMaterial) {
        this.descriptionOfMaterial = descriptionOfMaterial;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Integer getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Integer createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @NotNull(message="Discipline must be specified.")
    public int getDiscipline() {
        return discipline;
    }

    public void setDiscipline(int discipline) {
        this.discipline = discipline;
    }

    public Integer getExchangeIn() {
        return exchangeIn;
    }

    public void setExchangeIn(Integer exchangeIn) {
        this.exchangeIn = exchangeIn;
    }

    public Integer getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Integer modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Integer getPreparation() {
        return preparation;
    }

    public void setPreparation(Integer preparation) {
        this.preparation = preparation;
    }
    
    @Override
    public String getEntityName() {
        return "exchangeInPrep";
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exchangeInPrepId != null ? exchangeInPrepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exchangeinprep)) {
            return false;
        }
        Exchangeinprep other = (Exchangeinprep) object;
        if ((this.exchangeInPrepId == null && other.exchangeInPrepId != null) || (this.exchangeInPrepId != null && !this.exchangeInPrepId.equals(other.exchangeInPrepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exchangeinprep[ exchangeInPrepID=" + exchangeInPrepId + " ]";
    }
 
}

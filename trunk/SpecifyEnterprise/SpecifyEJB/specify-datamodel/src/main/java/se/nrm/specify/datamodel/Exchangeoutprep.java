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
@Table(name = "exchangeoutprep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exchangeoutprep.findAll", query = "SELECT e FROM Exchangeoutprep e"),
    @NamedQuery(name = "Exchangeoutprep.findByExchangeOutPrepID", query = "SELECT e FROM Exchangeoutprep e WHERE e.exchangeOutPrepId = :exchangeOutPrepID"),
    @NamedQuery(name = "Exchangeoutprep.findByTimestampCreated", query = "SELECT e FROM Exchangeoutprep e WHERE e.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Exchangeoutprep.findByTimestampModified", query = "SELECT e FROM Exchangeoutprep e WHERE e.timestampModified = :timestampModified"),
    @NamedQuery(name = "Exchangeoutprep.findByVersion", query = "SELECT e FROM Exchangeoutprep e WHERE e.version = :version"),
    @NamedQuery(name = "Exchangeoutprep.findByDescriptionOfMaterial", query = "SELECT e FROM Exchangeoutprep e WHERE e.descriptionOfMaterial = :descriptionOfMaterial"),
    @NamedQuery(name = "Exchangeoutprep.findByNumber1", query = "SELECT e FROM Exchangeoutprep e WHERE e.number1 = :number1"),
    @NamedQuery(name = "Exchangeoutprep.findByQuantity", query = "SELECT e FROM Exchangeoutprep e WHERE e.quantity = :quantity"),
    @NamedQuery(name = "Exchangeoutprep.findByPreparationID", query = "SELECT e FROM Exchangeoutprep e WHERE e.preparation = :preparationID"),
    @NamedQuery(name = "Exchangeoutprep.findByCreatedByAgentID", query = "SELECT e FROM Exchangeoutprep e WHERE e.createdByAgent = :createdByAgentID"),
    @NamedQuery(name = "Exchangeoutprep.findByExchangeOutID", query = "SELECT e FROM Exchangeoutprep e WHERE e.exchangeOut = :exchangeOutID"),
    @NamedQuery(name = "Exchangeoutprep.findByModifiedByAgentID", query = "SELECT e FROM Exchangeoutprep e WHERE e.modifiedByAgent = :modifiedByAgentID"),
    @NamedQuery(name = "Exchangeoutprep.findByDisciplineID", query = "SELECT e FROM Exchangeoutprep e WHERE e.discipline = :disciplineID")})
public class Exchangeoutprep extends BaseEntity {
   
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "ExchangeOutPrepID")
    private Integer exchangeOutPrepId;
      
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
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgent;
    
    @Column(name = "ExchangeOutID")
    private Integer exchangeOut;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgent;
    
    @Basic(optional = false) 
    @Column(name = "DisciplineID")
    private int discipline;

    public Exchangeoutprep() {
    }

    public Exchangeoutprep(Integer exchangeOutPrepId) {
        this.exchangeOutPrepId = exchangeOutPrepId;
    }

    public Exchangeoutprep(Integer exchangeOutPrepId, Date timestampCreated, int discipline) {
        super(timestampCreated);
        this.exchangeOutPrepId = exchangeOutPrepId; 
        this.discipline = discipline;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (exchangeOutPrepId != null) ? exchangeOutPrepId.toString() : "0";
    }
    
    public Integer getExchangeOutPrepId() {
        return exchangeOutPrepId;
    }

    public void setExchangeOutPrepId(Integer exchangeOutPrepId) {
        this.exchangeOutPrepId = exchangeOutPrepId;
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

    public Integer getExchangeOut() {
        return exchangeOut;
    }

    public void setExchangeOut(Integer exchangeOut) {
        this.exchangeOut = exchangeOut;
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
    public int hashCode() {
        int hash = 0;
        hash += (exchangeOutPrepId != null ? exchangeOutPrepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exchangeoutprep)) {
            return false;
        }
        Exchangeoutprep other = (Exchangeoutprep) object;
        if ((this.exchangeOutPrepId == null && other.exchangeOutPrepId != null) || (this.exchangeOutPrepId != null && !this.exchangeOutPrepId.equals(other.exchangeOutPrepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Exchangeoutprep[ exchangeOutPrepID=" + exchangeOutPrepId + " ]";
    }
 
}

package se.nrm.specify.datamodel;
 
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
import javax.persistence.Table; 
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "giftpreparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Giftpreparation.findAll", query = "SELECT g FROM Giftpreparation g"),
    @NamedQuery(name = "Giftpreparation.findByGiftPreparationID", query = "SELECT g FROM Giftpreparation g WHERE g.giftPreparationId = :giftPreparationID"),
    @NamedQuery(name = "Giftpreparation.findByTimestampCreated", query = "SELECT g FROM Giftpreparation g WHERE g.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Giftpreparation.findByTimestampModified", query = "SELECT g FROM Giftpreparation g WHERE g.timestampModified = :timestampModified"),
    @NamedQuery(name = "Giftpreparation.findByVersion", query = "SELECT g FROM Giftpreparation g WHERE g.version = :version"),
    @NamedQuery(name = "Giftpreparation.findByDescriptionOfMaterial", query = "SELECT g FROM Giftpreparation g WHERE g.descriptionOfMaterial = :descriptionOfMaterial"),
    @NamedQuery(name = "Giftpreparation.findByQuantity", query = "SELECT g FROM Giftpreparation g WHERE g.quantity = :quantity")})
public class Giftpreparation extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "GiftPreparationID")
    private Integer giftPreparationId; 
    
    @Size(max = 255)
    @Column(name = "DescriptionOfMaterial")
    private String descriptionOfMaterial;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "InComments")
    private String inComments;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "OutComments")
    private String outComments;
    
    @Column(name = "Quantity")
    private Integer quantity;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "ReceivedComments")
    private String receivedComments;
    
    @JoinColumn(name = "GiftID", referencedColumnName = "GiftID")
    @ManyToOne
    private Gift gift;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @JoinColumn(name = "DisciplineID", referencedColumnName = "UserGroupScopeId")
    @ManyToOne
    private Discipline discipline;
    
    @JoinColumn(name = "PreparationID", referencedColumnName = "PreparationID")
    @ManyToOne
    private Preparation preparation;

    public Giftpreparation() {
    }

    public Giftpreparation(Integer giftPreparationId) {
        this.giftPreparationId = giftPreparationId;
    }

    public Giftpreparation(Integer giftPreparationId, Date timestampCreated) {
        super(timestampCreated);
        this.giftPreparationId = giftPreparationId; 
    }

    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Integer getGiftPreparationId() {
        return giftPreparationId;
    }

    public void setGiftPreparationId(Integer giftPreparationId) {
        this.giftPreparationId = giftPreparationId;
    }

    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

 
    public String getDescriptionOfMaterial() {
        return descriptionOfMaterial;
    }

    public void setDescriptionOfMaterial(String descriptionOfMaterial) {
        this.descriptionOfMaterial = descriptionOfMaterial;
    }

    public String getInComments() {
        return inComments;
    }

    public void setInComments(String inComments) {
        this.inComments = inComments;
    }

    public String getOutComments() {
        return outComments;
    }

    public void setOutComments(String outComments) {
        this.outComments = outComments;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReceivedComments() {
        return receivedComments;
    }

    public void setReceivedComments(String receivedComments) {
        this.receivedComments = receivedComments;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (giftPreparationId != null ? giftPreparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giftpreparation)) {
            return false;
        }
        Giftpreparation other = (Giftpreparation) object;
        if ((this.giftPreparationId == null && other.giftPreparationId != null) || (this.giftPreparationId != null && !this.giftPreparationId.equals(other.giftPreparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Giftpreparation[ giftPreparationID=" + giftPreparationId + " ]";
    }
    
}

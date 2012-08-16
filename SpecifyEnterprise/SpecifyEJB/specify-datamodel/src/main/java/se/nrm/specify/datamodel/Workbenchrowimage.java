package se.nrm.specify.datamodel;

import java.io.Serializable;
import javax.persistence.*;
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
@Table(name = "workbenchrowimage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchrowimage.findAll", query = "SELECT w FROM Workbenchrowimage w"),
    @NamedQuery(name = "Workbenchrowimage.findByWorkbenchRowImageID", query = "SELECT w FROM Workbenchrowimage w WHERE w.workbenchRowImageId = :workbenchRowImageID"),
    @NamedQuery(name = "Workbenchrowimage.findByAttachToTableName", query = "SELECT w FROM Workbenchrowimage w WHERE w.attachToTableName = :attachToTableName"),
    @NamedQuery(name = "Workbenchrowimage.findByCardImageFullPath", query = "SELECT w FROM Workbenchrowimage w WHERE w.cardImageFullPath = :cardImageFullPath"),
    @NamedQuery(name = "Workbenchrowimage.findByImageOrder", query = "SELECT w FROM Workbenchrowimage w WHERE w.imageOrder = :imageOrder")})
public class Workbenchrowimage implements Serializable, SpecifyBean {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchRowImageID")
    private Integer workbenchRowImageId;
    
    @Size(max = 64)
    @Column(name = "AttachToTableName")
    private String attachToTableName;
    
    @Size(max = 255)
    @Column(name = "CardImageFullPath")
    private String cardImageFullPath;
    
    @Column(name = "ImageOrder")
    private Integer imageOrder;
    
    @JoinColumn(name = "WorkbenchRowID", referencedColumnName = "WorkbenchRowID")
    @ManyToOne(optional = false)
    private Workbenchrow workbenchRow;
    
    @Lob
    @Column(name = "CardImageData")
    private byte[] cardImageData;
    

    public Workbenchrowimage() {
    }

    public Workbenchrowimage(Integer workbenchRowImageId) {
        this.workbenchRowImageId = workbenchRowImageId;
    }
    
    @XmlID
    @XmlAttribute(name = "id") 
    public String getIdentityString() {
        return (workbenchRowImageId != null) ? workbenchRowImageId.toString() : "0";
    }

    @NotNull(message="WorkbenchRow must be specified.")
    public Workbenchrow getWorkbenchRow() {
        return workbenchRow;
    }

    public void setWorkbenchRow(Workbenchrow workbenchRow) {
        this.workbenchRow = workbenchRow;
    }

    public Integer getWorkbenchRowImageId() {
        return workbenchRowImageId;
    }

    public void setWorkbenchRowImageId(Integer workbenchRowImageId) {
        this.workbenchRowImageId = workbenchRowImageId;
    }
 

    public String getAttachToTableName() {
        return attachToTableName;
    }

    public void setAttachToTableName(String attachToTableName) {
        this.attachToTableName = attachToTableName;
    }

    public String getCardImageFullPath() {
        return cardImageFullPath;
    }

    public void setCardImageFullPath(String cardImageFullPath) {
        this.cardImageFullPath = cardImageFullPath;
    }

    public Integer getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(Integer imageOrder) {
        this.imageOrder = imageOrder;
    }
    
    public byte[] getCardImageData() {
        return cardImageData;
    }

    public void setCardImageData(byte[] cardImageData) {
        this.cardImageData = cardImageData;
    }

 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchRowImageId != null ? workbenchRowImageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchrowimage)) {
            return false;
        }
        Workbenchrowimage other = (Workbenchrowimage) object;
        if ((this.workbenchRowImageId == null && other.workbenchRowImageId != null) || (this.workbenchRowImageId != null && !this.workbenchRowImageId.equals(other.workbenchRowImageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Workbenchrowimage[ workbenchRowImageID=" + workbenchRowImageId + " ]";
    } 
    
}

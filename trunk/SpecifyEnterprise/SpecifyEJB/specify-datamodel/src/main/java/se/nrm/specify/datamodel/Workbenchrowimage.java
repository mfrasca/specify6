package se.nrm.specify.datamodel;

import java.io.Serializable;
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
@Table(name = "workbenchrowimage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchrowimage.findAll", query = "SELECT w FROM Workbenchrowimage w"),
    @NamedQuery(name = "Workbenchrowimage.findByWorkbenchRowImageID", query = "SELECT w FROM Workbenchrowimage w WHERE w.workbenchRowImageID = :workbenchRowImageID"),
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
    private Integer workbenchRowImageID;
    
    @Size(max = 64)
    @Column(name = "AttachToTableName")
    private String attachToTableName;
    
    @Lob
    @Column(name = "CardImageData")
    private byte[] cardImageData;
    
    @Size(max = 255)
    @Column(name = "CardImageFullPath")
    private String cardImageFullPath;
    
    @Column(name = "ImageOrder")
    private Integer imageOrder;
    
    @JoinColumn(name = "WorkbenchRowID", referencedColumnName = "WorkbenchRowID")
    @ManyToOne(optional = false)
    private Workbenchrow workbenchRowID;

    public Workbenchrowimage() {
    }

    public Workbenchrowimage(Integer workbenchRowImageID) {
        this.workbenchRowImageID = workbenchRowImageID;
    }

    public Integer getWorkbenchRowImageID() {
        return workbenchRowImageID;
    }

    public void setWorkbenchRowImageID(Integer workbenchRowImageID) {
        this.workbenchRowImageID = workbenchRowImageID;
    }

    public String getAttachToTableName() {
        return attachToTableName;
    }

    public void setAttachToTableName(String attachToTableName) {
        this.attachToTableName = attachToTableName;
    }

    public byte[] getCardImageData() {
        return cardImageData;
    }

    public void setCardImageData(byte[] cardImageData) {
        this.cardImageData = cardImageData;
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

    public Workbenchrow getWorkbenchRowID() {
        return workbenchRowID;
    }

    public void setWorkbenchRowID(Workbenchrow workbenchRowID) {
        this.workbenchRowID = workbenchRowID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchRowImageID != null ? workbenchRowImageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchrowimage)) {
            return false;
        }
        Workbenchrowimage other = (Workbenchrowimage) object;
        if ((this.workbenchRowImageID == null && other.workbenchRowImageID != null) || (this.workbenchRowImageID != null && !this.workbenchRowImageID.equals(other.workbenchRowImageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Workbenchrowimage[ workbenchRowImageID=" + workbenchRowImageID + " ]";
    }
    
}

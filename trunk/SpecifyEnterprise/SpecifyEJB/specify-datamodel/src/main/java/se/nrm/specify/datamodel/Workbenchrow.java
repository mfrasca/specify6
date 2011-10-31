package se.nrm.specify.datamodel;

import java.io.Serializable;
import java.util.Collection;
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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "workbenchrow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchrow.findAll", query = "SELECT w FROM Workbenchrow w"),
    @NamedQuery(name = "Workbenchrow.findByWorkbenchRowID", query = "SELECT w FROM Workbenchrow w WHERE w.workbenchRowID = :workbenchRowID"),
    @NamedQuery(name = "Workbenchrow.findByCardImageFullPath", query = "SELECT w FROM Workbenchrow w WHERE w.cardImageFullPath = :cardImageFullPath"),
    @NamedQuery(name = "Workbenchrow.findByLat1Text", query = "SELECT w FROM Workbenchrow w WHERE w.lat1Text = :lat1Text"),
    @NamedQuery(name = "Workbenchrow.findByLat2Text", query = "SELECT w FROM Workbenchrow w WHERE w.lat2Text = :lat2Text"),
    @NamedQuery(name = "Workbenchrow.findByLong1Text", query = "SELECT w FROM Workbenchrow w WHERE w.long1Text = :long1Text"),
    @NamedQuery(name = "Workbenchrow.findByLong2Text", query = "SELECT w FROM Workbenchrow w WHERE w.long2Text = :long2Text"),
    @NamedQuery(name = "Workbenchrow.findByRowNumber", query = "SELECT w FROM Workbenchrow w WHERE w.rowNumber = :rowNumber"),
    @NamedQuery(name = "Workbenchrow.findByUploadStatus", query = "SELECT w FROM Workbenchrow w WHERE w.uploadStatus = :uploadStatus"),
    @NamedQuery(name = "Workbenchrow.findByRecordID", query = "SELECT w FROM Workbenchrow w WHERE w.recordID = :recordID")})
public class Workbenchrow implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchRowID")
    private Integer workbenchRowID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "BioGeomancerResults")
    private String bioGeomancerResults;
    
    @Lob
    @Column(name = "CardImageData")
    private byte[] cardImageData;
    
    @Size(max = 255)
    @Column(name = "CardImageFullPath")
    private String cardImageFullPath;
    
    @Size(max = 50)
    @Column(name = "Lat1Text")
    private String lat1Text;
    
    @Size(max = 50)
    @Column(name = "Lat2Text")
    private String lat2Text;
    
    @Size(max = 50)
    @Column(name = "Long1Text")
    private String long1Text;
    
    @Size(max = 50)
    @Column(name = "Long2Text")
    private String long2Text;
    
    @Column(name = "RowNumber")
    private Short rowNumber;
    
    @Column(name = "UploadStatus")
    private Short uploadStatus;
    
    @Column(name = "RecordID")
    private Integer recordID;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchRowID")
    private Collection<Workbenchrowimage> workbenchrowimageCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workbenchRowID")
    private Collection<Workbenchdataitem> workbenchdataitemCollection;
    
    @JoinColumn(name = "WorkbenchID", referencedColumnName = "WorkbenchID")
    @ManyToOne(optional = false)
    private Workbench workbenchID;

    public Workbenchrow() {
    }

    public Workbenchrow(Integer workbenchRowID) {
        this.workbenchRowID = workbenchRowID;
    }

    public Integer getWorkbenchRowID() {
        return workbenchRowID;
    }

    public void setWorkbenchRowID(Integer workbenchRowID) {
        this.workbenchRowID = workbenchRowID;
    }

    public String getBioGeomancerResults() {
        return bioGeomancerResults;
    }

    public void setBioGeomancerResults(String bioGeomancerResults) {
        this.bioGeomancerResults = bioGeomancerResults;
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

    public String getLat1Text() {
        return lat1Text;
    }

    public void setLat1Text(String lat1Text) {
        this.lat1Text = lat1Text;
    }

    public String getLat2Text() {
        return lat2Text;
    }

    public void setLat2Text(String lat2Text) {
        this.lat2Text = lat2Text;
    }

    public String getLong1Text() {
        return long1Text;
    }

    public void setLong1Text(String long1Text) {
        this.long1Text = long1Text;
    }

    public String getLong2Text() {
        return long2Text;
    }

    public void setLong2Text(String long2Text) {
        this.long2Text = long2Text;
    }

    public Short getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Short rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Short getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Short uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public Integer getRecordID() {
        return recordID;
    }

    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    @XmlTransient
    public Collection<Workbenchrowimage> getWorkbenchrowimageCollection() {
        return workbenchrowimageCollection;
    }

    public void setWorkbenchrowimageCollection(Collection<Workbenchrowimage> workbenchrowimageCollection) {
        this.workbenchrowimageCollection = workbenchrowimageCollection;
    }

    @XmlTransient
    public Collection<Workbenchdataitem> getWorkbenchdataitemCollection() {
        return workbenchdataitemCollection;
    }

    public void setWorkbenchdataitemCollection(Collection<Workbenchdataitem> workbenchdataitemCollection) {
        this.workbenchdataitemCollection = workbenchdataitemCollection;
    }

    public Workbench getWorkbenchID() {
        return workbenchID;
    }

    public void setWorkbenchID(Workbench workbenchID) {
        this.workbenchID = workbenchID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchRowID != null ? workbenchRowID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchrow)) {
            return false;
        }
        Workbenchrow other = (Workbenchrow) object;
        if ((this.workbenchRowID == null && other.workbenchRowID != null) || (this.workbenchRowID != null && !this.workbenchRowID.equals(other.workbenchRowID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Workbenchrow[ workbenchRowID=" + workbenchRowID + " ]";
    }
    
}

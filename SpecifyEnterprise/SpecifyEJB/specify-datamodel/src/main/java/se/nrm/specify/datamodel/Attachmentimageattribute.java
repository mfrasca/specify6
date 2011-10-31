package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "attachmentimageattribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachmentimageattribute.findAll", query = "SELECT a FROM Attachmentimageattribute a"),
    @NamedQuery(name = "Attachmentimageattribute.findByAttachmentImageAttributeID", query = "SELECT a FROM Attachmentimageattribute a WHERE a.attachmentImageAttributeID = :attachmentImageAttributeID"),
    @NamedQuery(name = "Attachmentimageattribute.findByTimestampCreated", query = "SELECT a FROM Attachmentimageattribute a WHERE a.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Attachmentimageattribute.findByTimestampModified", query = "SELECT a FROM Attachmentimageattribute a WHERE a.timestampModified = :timestampModified"),
    @NamedQuery(name = "Attachmentimageattribute.findByVersion", query = "SELECT a FROM Attachmentimageattribute a WHERE a.version = :version"),
    @NamedQuery(name = "Attachmentimageattribute.findByCreativeCommons", query = "SELECT a FROM Attachmentimageattribute a WHERE a.creativeCommons = :creativeCommons"),
    @NamedQuery(name = "Attachmentimageattribute.findByHeight", query = "SELECT a FROM Attachmentimageattribute a WHERE a.height = :height"),
    @NamedQuery(name = "Attachmentimageattribute.findByMagnification", query = "SELECT a FROM Attachmentimageattribute a WHERE a.magnification = :magnification"),
    @NamedQuery(name = "Attachmentimageattribute.findByMBImageID", query = "SELECT a FROM Attachmentimageattribute a WHERE a.mBImageID = :mBImageID"),
    @NamedQuery(name = "Attachmentimageattribute.findByResolution", query = "SELECT a FROM Attachmentimageattribute a WHERE a.resolution = :resolution"),
    @NamedQuery(name = "Attachmentimageattribute.findByTimestampLastSend", query = "SELECT a FROM Attachmentimageattribute a WHERE a.timestampLastSend = :timestampLastSend"),
    @NamedQuery(name = "Attachmentimageattribute.findByTimestampLastUpdateCheck", query = "SELECT a FROM Attachmentimageattribute a WHERE a.timestampLastUpdateCheck = :timestampLastUpdateCheck"),
    @NamedQuery(name = "Attachmentimageattribute.findByWidth", query = "SELECT a FROM Attachmentimageattribute a WHERE a.width = :width"),
    @NamedQuery(name = "Attachmentimageattribute.findByCreatedByAgentID", query = "SELECT a FROM Attachmentimageattribute a WHERE a.createdByAgentID = :createdByAgentID"),
    @NamedQuery(name = "Attachmentimageattribute.findByModifiedByAgentID", query = "SELECT a FROM Attachmentimageattribute a WHERE a.modifiedByAgentID = :modifiedByAgentID"),
    @NamedQuery(name = "Attachmentimageattribute.findByMorphBankViewID", query = "SELECT a FROM Attachmentimageattribute a WHERE a.morphBankViewID = :morphBankViewID")})
public class Attachmentimageattribute extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "AttachmentImageAttributeID")
    private Integer attachmentImageAttributeID;
     
    @Size(max = 128)
    @Column(name = "CreativeCommons")
    private String creativeCommons;
    
    @Column(name = "Height")
    private Integer height;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Magnification")
    private Double magnification;
    
    @Column(name = "MBImageID")
    private Integer mBImageID;
    
    @Column(name = "Resolution")
    private Double resolution;
    
    @Column(name = "TimestampLastSend")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampLastSend;
    
    @Column(name = "TimestampLastUpdateCheck")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampLastUpdateCheck;
    
    @Column(name = "Width")
    private Integer width;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentID;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentID;
    
    @Column(name = "MorphBankViewID")
    private Integer morphBankViewID;

    public Attachmentimageattribute() {
    }

    public Attachmentimageattribute(Integer attachmentImageAttributeID) {
        this.attachmentImageAttributeID = attachmentImageAttributeID;
    }

    public Attachmentimageattribute(Integer attachmentImageAttributeID, Date timestampCreated) {
        super(timestampCreated);
        this.attachmentImageAttributeID = attachmentImageAttributeID;  
    }

    public Integer getAttachmentImageAttributeID() {
        return attachmentImageAttributeID;
    }

    public void setAttachmentImageAttributeID(Integer attachmentImageAttributeID) {
        this.attachmentImageAttributeID = attachmentImageAttributeID;
    }
 
    public String getCreativeCommons() {
        return creativeCommons;
    }

    public void setCreativeCommons(String creativeCommons) {
        this.creativeCommons = creativeCommons;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getMagnification() {
        return magnification;
    }

    public void setMagnification(Double magnification) {
        this.magnification = magnification;
    }

    public Integer getMBImageID() {
        return mBImageID;
    }

    public void setMBImageID(Integer mBImageID) {
        this.mBImageID = mBImageID;
    }

    public Double getResolution() {
        return resolution;
    }

    public void setResolution(Double resolution) {
        this.resolution = resolution;
    }

    public Date getTimestampLastSend() {
        return timestampLastSend;
    }

    public void setTimestampLastSend(Date timestampLastSend) {
        this.timestampLastSend = timestampLastSend;
    }

    public Date getTimestampLastUpdateCheck() {
        return timestampLastUpdateCheck;
    }

    public void setTimestampLastUpdateCheck(Date timestampLastUpdateCheck) {
        this.timestampLastUpdateCheck = timestampLastUpdateCheck;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Integer createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    public Integer getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Integer modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    public Integer getMorphBankViewID() {
        return morphBankViewID;
    }

    public void setMorphBankViewID(Integer morphBankViewID) {
        this.morphBankViewID = morphBankViewID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attachmentImageAttributeID != null ? attachmentImageAttributeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachmentimageattribute)) {
            return false;
        }
        Attachmentimageattribute other = (Attachmentimageattribute) object;
        if ((this.attachmentImageAttributeID == null && other.attachmentImageAttributeID != null) || (this.attachmentImageAttributeID != null && !this.attachmentImageAttributeID.equals(other.attachmentImageAttributeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attachmentimageattribute[ attachmentImageAttributeID=" + attachmentImageAttributeID + " ]";
    }
    
}

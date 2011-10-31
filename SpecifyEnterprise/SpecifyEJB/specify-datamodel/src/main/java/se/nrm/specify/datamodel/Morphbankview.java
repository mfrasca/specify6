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
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "morphbankview")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Morphbankview.findAll", query = "SELECT m FROM Morphbankview m"),
    @NamedQuery(name = "Morphbankview.findByMorphBankViewID", query = "SELECT m FROM Morphbankview m WHERE m.morphBankViewID = :morphBankViewID"),
    @NamedQuery(name = "Morphbankview.findByTimestampCreated", query = "SELECT m FROM Morphbankview m WHERE m.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Morphbankview.findByTimestampModified", query = "SELECT m FROM Morphbankview m WHERE m.timestampModified = :timestampModified"),
    @NamedQuery(name = "Morphbankview.findByVersion", query = "SELECT m FROM Morphbankview m WHERE m.version = :version"),
    @NamedQuery(name = "Morphbankview.findByDevelopmentState", query = "SELECT m FROM Morphbankview m WHERE m.developmentState = :developmentState"),
    @NamedQuery(name = "Morphbankview.findByForm", query = "SELECT m FROM Morphbankview m WHERE m.form = :form"),
    @NamedQuery(name = "Morphbankview.findByImagingPreparationTechnique", query = "SELECT m FROM Morphbankview m WHERE m.imagingPreparationTechnique = :imagingPreparationTechnique"),
    @NamedQuery(name = "Morphbankview.findByImagingTechnique", query = "SELECT m FROM Morphbankview m WHERE m.imagingTechnique = :imagingTechnique"),
    @NamedQuery(name = "Morphbankview.findByMorphBankExternalViewID", query = "SELECT m FROM Morphbankview m WHERE m.morphBankExternalViewID = :morphBankExternalViewID"),
    @NamedQuery(name = "Morphbankview.findBySex", query = "SELECT m FROM Morphbankview m WHERE m.sex = :sex"),
    @NamedQuery(name = "Morphbankview.findBySpecimenPart", query = "SELECT m FROM Morphbankview m WHERE m.specimenPart = :specimenPart"),
    @NamedQuery(name = "Morphbankview.findByViewAngle", query = "SELECT m FROM Morphbankview m WHERE m.viewAngle = :viewAngle"),
    @NamedQuery(name = "Morphbankview.findByViewName", query = "SELECT m FROM Morphbankview m WHERE m.viewName = :viewName"),
    @NamedQuery(name = "Morphbankview.findByCreatedByAgentID", query = "SELECT m FROM Morphbankview m WHERE m.createdByAgentID = :createdByAgentID"),
    @NamedQuery(name = "Morphbankview.findByModifiedByAgentID", query = "SELECT m FROM Morphbankview m WHERE m.modifiedByAgentID = :modifiedByAgentID")})
public class Morphbankview extends BaseEntity { 
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "MorphBankViewID")
    private Integer morphBankViewID;
     
    @Size(max = 128)
    @Column(name = "DevelopmentState")
    private String developmentState;
    
    @Size(max = 128)
    @Column(name = "Form")
    private String form;
    
    @Size(max = 128)
    @Column(name = "ImagingPreparationTechnique")
    private String imagingPreparationTechnique;
    
    @Size(max = 128)
    @Column(name = "ImagingTechnique")
    private String imagingTechnique;
    
    @Column(name = "MorphBankExternalViewID")
    private Integer morphBankExternalViewID;
    
    @Size(max = 32)
    @Column(name = "Sex")
    private String sex;
    
    @Size(max = 128)
    @Column(name = "SpecimenPart")
    private String specimenPart;
    
    @Size(max = 128)
    @Column(name = "ViewAngle")
    private String viewAngle;
    
    @Size(max = 128)
    @Column(name = "ViewName")
    private String viewName;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentID;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentID;

    public Morphbankview() {
    }

    public Morphbankview(Integer morphBankViewID) {
        this.morphBankViewID = morphBankViewID;
    }

    public Morphbankview(Integer morphBankViewID, Date timestampCreated) {
        super(timestampCreated);
        this.morphBankViewID = morphBankViewID; 
    }

    public Integer getMorphBankViewID() {
        return morphBankViewID;
    }

    public void setMorphBankViewID(Integer morphBankViewID) {
        this.morphBankViewID = morphBankViewID;
    } 

    public String getDevelopmentState() {
        return developmentState;
    }

    public void setDevelopmentState(String developmentState) {
        this.developmentState = developmentState;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getImagingPreparationTechnique() {
        return imagingPreparationTechnique;
    }

    public void setImagingPreparationTechnique(String imagingPreparationTechnique) {
        this.imagingPreparationTechnique = imagingPreparationTechnique;
    }

    public String getImagingTechnique() {
        return imagingTechnique;
    }

    public void setImagingTechnique(String imagingTechnique) {
        this.imagingTechnique = imagingTechnique;
    }

    public Integer getMorphBankExternalViewID() {
        return morphBankExternalViewID;
    }

    public void setMorphBankExternalViewID(Integer morphBankExternalViewID) {
        this.morphBankExternalViewID = morphBankExternalViewID;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecimenPart() {
        return specimenPart;
    }

    public void setSpecimenPart(String specimenPart) {
        this.specimenPart = specimenPart;
    }

    public String getViewAngle() {
        return viewAngle;
    }

    public void setViewAngle(String viewAngle) {
        this.viewAngle = viewAngle;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (morphBankViewID != null ? morphBankViewID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Morphbankview)) {
            return false;
        }
        Morphbankview other = (Morphbankview) object;
        if ((this.morphBankViewID == null && other.morphBankViewID != null) || (this.morphBankViewID != null && !this.morphBankViewID.equals(other.morphBankViewID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Morphbankview[ morphBankViewID=" + morphBankViewID + " ]";
    }
    
}

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
@Table(name = "workbenchdataitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchdataitem.findAll", query = "SELECT w FROM Workbenchdataitem w"),
    @NamedQuery(name = "Workbenchdataitem.findByWorkbenchDataItemID", query = "SELECT w FROM Workbenchdataitem w WHERE w.workbenchDataItemId = :workbenchDataItemID"),
    @NamedQuery(name = "Workbenchdataitem.findByRowNumber", query = "SELECT w FROM Workbenchdataitem w WHERE w.rowNumber = :rowNumber"),
    @NamedQuery(name = "Workbenchdataitem.findByValidationStatus", query = "SELECT w FROM Workbenchdataitem w WHERE w.validationStatus = :validationStatus")})
public class Workbenchdataitem implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchDataItemID")
    private Integer workbenchDataItemId;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "CellData")
    private String cellData;
    
    @Column(name = "RowNumber")
    private Short rowNumber;
    
    @Column(name = "ValidationStatus")
    private Short validationStatus;
    
    @JoinColumn(name = "WorkbenchRowID", referencedColumnName = "WorkbenchRowID")
    @ManyToOne(optional = false)
    private Workbenchrow workbenchRow;
    
    @JoinColumn(name = "WorkbenchTemplateMappingItemID", referencedColumnName = "WorkbenchTemplateMappingItemID")
    @ManyToOne(optional = false)
    private Workbenchtemplatemappingitem workbenchTemplateMappingItem;

    public Workbenchdataitem() {
    }

    public Workbenchdataitem(Integer workbenchDataItemId) {
        this.workbenchDataItemId = workbenchDataItemId;
    }
 
    @XmlID
    @XmlAttribute(name = "id") 
    public String getIdentityString() {
        return (workbenchDataItemId!= null) ? workbenchDataItemId.toString() : "0";
    }

    public String getCellData() {
        return cellData;
    }

    public void setCellData(String cellData) {
        this.cellData = cellData;
    }

    public Short getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Short rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Short getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(Short validationStatus) {
        this.validationStatus = validationStatus;
    }

    public Integer getWorkbenchDataItemId() {
        return workbenchDataItemId;
    }

    public void setWorkbenchDataItemId(Integer workbenchDataItemId) {
        this.workbenchDataItemId = workbenchDataItemId;
    }

    @NotNull(message="WorkbenchRow must be specified.")
    public Workbenchrow getWorkbenchRow() {
        return workbenchRow;
    }

    public void setWorkbenchRow(Workbenchrow workbenchRow) {
        this.workbenchRow = workbenchRow;
    }

    @NotNull(message="WorkbenchTemplateMappingItem must be specified.")
    public Workbenchtemplatemappingitem getWorkbenchTemplateMappingItem() {
        return workbenchTemplateMappingItem;
    }

    public void setWorkbenchTemplateMappingItem(Workbenchtemplatemappingitem workbenchTemplateMappingItem) {
        this.workbenchTemplateMappingItem = workbenchTemplateMappingItem;
    }

 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchDataItemId != null ? workbenchDataItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchdataitem)) {
            return false;
        }
        Workbenchdataitem other = (Workbenchdataitem) object;
        if ((this.workbenchDataItemId == null && other.workbenchDataItemId != null) || (this.workbenchDataItemId != null && !this.workbenchDataItemId.equals(other.workbenchDataItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Workbenchdataitem[ workbenchDataItemID=" + workbenchDataItemId + " ]";
    }
    
}

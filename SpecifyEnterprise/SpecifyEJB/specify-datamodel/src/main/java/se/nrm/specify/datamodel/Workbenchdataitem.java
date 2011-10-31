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
@Table(name = "workbenchdataitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workbenchdataitem.findAll", query = "SELECT w FROM Workbenchdataitem w"),
    @NamedQuery(name = "Workbenchdataitem.findByWorkbenchDataItemID", query = "SELECT w FROM Workbenchdataitem w WHERE w.workbenchDataItemID = :workbenchDataItemID"),
    @NamedQuery(name = "Workbenchdataitem.findByRowNumber", query = "SELECT w FROM Workbenchdataitem w WHERE w.rowNumber = :rowNumber"),
    @NamedQuery(name = "Workbenchdataitem.findByValidationStatus", query = "SELECT w FROM Workbenchdataitem w WHERE w.validationStatus = :validationStatus")})
public class Workbenchdataitem implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "WorkbenchDataItemID")
    private Integer workbenchDataItemID;
    
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
    private Workbenchrow workbenchRowID;
    
    @JoinColumn(name = "WorkbenchTemplateMappingItemID", referencedColumnName = "WorkbenchTemplateMappingItemID")
    @ManyToOne(optional = false)
    private Workbenchtemplatemappingitem workbenchTemplateMappingItemID;

    public Workbenchdataitem() {
    }

    public Workbenchdataitem(Integer workbenchDataItemID) {
        this.workbenchDataItemID = workbenchDataItemID;
    }

    public Integer getWorkbenchDataItemID() {
        return workbenchDataItemID;
    }

    public void setWorkbenchDataItemID(Integer workbenchDataItemID) {
        this.workbenchDataItemID = workbenchDataItemID;
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

    public Workbenchrow getWorkbenchRowID() {
        return workbenchRowID;
    }

    public void setWorkbenchRowID(Workbenchrow workbenchRowID) {
        this.workbenchRowID = workbenchRowID;
    }

    public Workbenchtemplatemappingitem getWorkbenchTemplateMappingItemID() {
        return workbenchTemplateMappingItemID;
    }

    public void setWorkbenchTemplateMappingItemID(Workbenchtemplatemappingitem workbenchTemplateMappingItemID) {
        this.workbenchTemplateMappingItemID = workbenchTemplateMappingItemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workbenchDataItemID != null ? workbenchDataItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workbenchdataitem)) {
            return false;
        }
        Workbenchdataitem other = (Workbenchdataitem) object;
        if ((this.workbenchDataItemID == null && other.workbenchDataItemID != null) || (this.workbenchDataItemID != null && !this.workbenchDataItemID.equals(other.workbenchDataItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Workbenchdataitem[ workbenchDataItemID=" + workbenchDataItemID + " ]";
    }
    
}

package se.nrm.specify.datamodel;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "recordsetitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recordsetitem.findAll", query = "SELECT r FROM Recordsetitem r"),
    @NamedQuery(name = "Recordsetitem.findByRecordSetItemID", query = "SELECT r FROM Recordsetitem r WHERE r.recordSetItemID = :recordSetItemID"),
    @NamedQuery(name = "Recordsetitem.findByRecordId", query = "SELECT r FROM Recordsetitem r WHERE r.recordId = :recordId")})
public class Recordsetitem implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "RecordSetItemID")
    private Integer recordSetItemID;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecordId")
    private int recordId;
    
    @JoinColumn(name = "RecordSetID", referencedColumnName = "RecordSetID")
    @ManyToOne(optional = false)
    private Recordset recordSetID;

    public Recordsetitem() {
    }

    public Recordsetitem(Integer recordSetItemID) {
        this.recordSetItemID = recordSetItemID;
    }

    public Recordsetitem(Integer recordSetItemID, int recordId) {
        this.recordSetItemID = recordSetItemID;
        this.recordId = recordId;
    }

    public Integer getRecordSetItemID() {
        return recordSetItemID;
    }

    public void setRecordSetItemID(Integer recordSetItemID) {
        this.recordSetItemID = recordSetItemID;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public Recordset getRecordSetID() {
        return recordSetID;
    }

    public void setRecordSetID(Recordset recordSetID) {
        this.recordSetID = recordSetID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordSetItemID != null ? recordSetItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recordsetitem)) {
            return false;
        }
        Recordsetitem other = (Recordsetitem) object;
        if ((this.recordSetItemID == null && other.recordSetItemID != null) || (this.recordSetItemID != null && !this.recordSetItemID.equals(other.recordSetItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Recordsetitem[ recordSetItemID=" + recordSetItemID + " ]";
    }
    
}

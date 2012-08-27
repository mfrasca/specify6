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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
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
    @NamedQuery(name = "Recordsetitem.findByRecordSetItemId", query = "SELECT r FROM Recordsetitem r WHERE r.recordSetItemId = :recordSetItemId"),
    @NamedQuery(name = "Recordsetitem.findByRecordId", query = "SELECT r FROM Recordsetitem r WHERE r.recordId = :recordId")})
public class Recordsetitem implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "RecordSetItemID")
    private Integer recordSetItemId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RecordId")
    private int recordId;
    
    @JoinColumn(name = "RecordSetID", referencedColumnName = "RecordSetID")
    @NotNull
    @ManyToOne(optional = false)
    private Recordset recordSet;

    public Recordsetitem() {
    }

    public Recordsetitem(Integer recordSetItemId) {
        this.recordSetItemId = recordSetItemId;
    }

    public Recordsetitem(Integer recordSetItemId, int recordId) {
        this.recordSetItemId = recordSetItemId;
        this.recordId = recordId;
    }
    
    @XmlID
    @XmlAttribute(name = "id") 
    public String getIdentityString() {
        return (recordSetItemId != null) ? recordSetItemId.toString() : "0";
    }
 
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @NotNull(message="Recordset must be specified.")
    public Recordset getRecordSet() {
        return recordSet;
    }

    public void setRecordSet(Recordset recordSet) {
        this.recordSet = recordSet;
    }

    public Integer getRecordSetItemId() {
        return recordSetItemId;
    }

    public void setRecordSetItemId(Integer recordSetItemId) {
        this.recordSetItemId = recordSetItemId;
    }
 
    @Override
    public String getEntityName() {
        return "recordSetItem";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordSetItemId != null ? recordSetItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recordsetitem)) {
            return false;
        }
        Recordsetitem other = (Recordsetitem) object;
        if ((this.recordSetItemId == null && other.recordSetItemId != null) || (this.recordSetItemId != null && !this.recordSetItemId.equals(other.recordSetItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Recordsetitem[ recordSetItemID=" + recordSetItemId + " ]";
    }
    
}

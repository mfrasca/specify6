/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.specify.datamodel;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "sgrbatchmatchresultset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgrbatchmatchresultset.findAll", query = "SELECT s FROM Sgrbatchmatchresultset s"),
    @NamedQuery(name = "Sgrbatchmatchresultset.findById", query = "SELECT s FROM Sgrbatchmatchresultset s WHERE s.id = :id"),
    @NamedQuery(name = "Sgrbatchmatchresultset.findByInsertTime", query = "SELECT s FROM Sgrbatchmatchresultset s WHERE s.insertTime = :insertTime"),
    @NamedQuery(name = "Sgrbatchmatchresultset.findByName", query = "SELECT s FROM Sgrbatchmatchresultset s WHERE s.name = :name"),
    @NamedQuery(name = "Sgrbatchmatchresultset.findByRecordSetID", query = "SELECT s FROM Sgrbatchmatchresultset s WHERE s.recordSetID = :recordSetID"),
    @NamedQuery(name = "Sgrbatchmatchresultset.findByDbTableId", query = "SELECT s FROM Sgrbatchmatchresultset s WHERE s.dbTableId = :dbTableId")})
public class Sgrbatchmatchresultset implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "insertTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTime;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    private String name;
    
    @Column(name = "recordSetID")
    private BigInteger recordSetID;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "query")
    private String query;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "dbTableId")
    private Integer dbTableId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sgrbatchmatchresultset")
    private Collection<Sgrbatchmatchresultitem> sgrbatchmatchresultitems;
    
    @JoinColumn(name = "matchConfigurationId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sgrmatchconfiguration sgrmatchconfiguration;

    public Sgrbatchmatchresultset() {
    }

    public Sgrbatchmatchresultset(Long id) {
        this.id = id;
    }

    public Sgrbatchmatchresultset(Long id, Date insertTime, String name, String query, String remarks) {
        this.id = id;
        this.insertTime = insertTime;
        this.name = name;
        this.query = query;
        this.remarks = remarks;
    }
    
    @XmlID
    @XmlAttribute(name = "id") 
    public String getIdentityString() {
        return (id != null) ? id.toString() : "0";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message="InsertTime must be specified.")
    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @NotNull(message="Name must be specified.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getRecordSetID() {
        return recordSetID;
    }

    public void setRecordSetID(BigInteger recordSetID) {
        this.recordSetID = recordSetID;
    }

    @NotNull(message="Query must be specified.")
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @NotNull(message="Remarks must be specified.")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDbTableId() {
        return dbTableId;
    }

    public void setDbTableId(Integer dbTableId) {
        this.dbTableId = dbTableId;
    }

    @XmlTransient
    public Collection<Sgrbatchmatchresultitem> getSgrbatchmatchresultitems() {
        return sgrbatchmatchresultitems;
    }

    public void setSgrbatchmatchresultitems(Collection<Sgrbatchmatchresultitem> sgrbatchmatchresultitems) {
        this.sgrbatchmatchresultitems = sgrbatchmatchresultitems;
    }

 
    @NotNull(message="Sqrmatchconfiguration must be specified.")
    public Sgrmatchconfiguration getSgrmatchconfiguration() {
        return sgrmatchconfiguration;
    }

    public void setSgrmatchconfiguration(Sgrmatchconfiguration sgrmatchconfiguration) {
        this.sgrmatchconfiguration = sgrmatchconfiguration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sgrbatchmatchresultset)) {
            return false;
        }
        Sgrbatchmatchresultset other = (Sgrbatchmatchresultset) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Sgrbatchmatchresultset[ id=" + id + " ]";
    }
    
}

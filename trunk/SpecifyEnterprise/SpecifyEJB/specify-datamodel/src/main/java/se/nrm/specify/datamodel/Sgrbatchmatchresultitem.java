/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author idali
 */
@Entity
@Table(name = "sgrbatchmatchresultitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sgrbatchmatchresultitem.findAll", query = "SELECT s FROM Sgrbatchmatchresultitem s"),
    @NamedQuery(name = "Sgrbatchmatchresultitem.findById", query = "SELECT s FROM Sgrbatchmatchresultitem s WHERE s.id = :id"),
    @NamedQuery(name = "Sgrbatchmatchresultitem.findByMatchedId", query = "SELECT s FROM Sgrbatchmatchresultitem s WHERE s.matchedId = :matchedId"),
    @NamedQuery(name = "Sgrbatchmatchresultitem.findByMaxScore", query = "SELECT s FROM Sgrbatchmatchresultitem s WHERE s.maxScore = :maxScore"),
    @NamedQuery(name = "Sgrbatchmatchresultitem.findByQTime", query = "SELECT s FROM Sgrbatchmatchresultitem s WHERE s.qTime = :qTime")})
public class Sgrbatchmatchresultitem implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false) 
    @Size(min = 1, max = 128)
    @Column(name = "matchedId")
    private String matchedId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxScore")
    private float maxScore;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "qTime")
    private int qTime;
    
    @JoinColumn(name = "batchMatchResultSetId", referencedColumnName = "id")
    @NotNull
    @ManyToOne(optional = false)
    private Sgrbatchmatchresultset sgrbatchmatchresultset;

    public Sgrbatchmatchresultitem() {
    }

    public Sgrbatchmatchresultitem(Long id) {
        this.id = id;
    }
 

    public Sgrbatchmatchresultitem(Long id, String matchedId, float maxScore, int qTime) {
        this.id = id;
        this.matchedId = matchedId;
        this.maxScore = maxScore;
        this.qTime = qTime;
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

    @NotNull(message="MatchedId must be specified.")
    public String getMatchedId() {
        return matchedId;
    }

    public void setMatchedId(String matchedId) {
        this.matchedId = matchedId;
    }

    public float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    public int getQTime() {
        return qTime;
    }

    public void setQTime(int qTime) {
        this.qTime = qTime;
    }

    @NotNull(message="Sgrbatchmatchresultset must be specified.")
    public Sgrbatchmatchresultset getSgrbatchmatchresultset() {
        return sgrbatchmatchresultset;
    }

    public void setSgrbatchmatchresultset(Sgrbatchmatchresultset sgrbatchmatchresultset) {
        this.sgrbatchmatchresultset = sgrbatchmatchresultset;
    }

    @Override
    public String getEntityName() {
        return "sgrbatchmatchresultitem";
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
        if (!(object instanceof Sgrbatchmatchresultitem)) {
            return false;
        }
        Sgrbatchmatchresultitem other = (Sgrbatchmatchresultitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Sgrbatchmatchresultitem[ id=" + id + " ]";
    }
    
}

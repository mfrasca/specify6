/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.specify.datamodel;
 
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "fundingagent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fundingagent.findAll", query = "SELECT f FROM Fundingagent f"),
    @NamedQuery(name = "Fundingagent.findByFundingAgentID", query = "SELECT f FROM Fundingagent f WHERE f.fundingAgentId = :fundingAgentID"),
    @NamedQuery(name = "Fundingagent.findByTimestampCreated", query = "SELECT f FROM Fundingagent f WHERE f.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Fundingagent.findByTimestampModified", query = "SELECT f FROM Fundingagent f WHERE f.timestampModified = :timestampModified"),
    @NamedQuery(name = "Fundingagent.findByVersion", query = "SELECT f FROM Fundingagent f WHERE f.version = :version"),
    @NamedQuery(name = "Fundingagent.findByIsPrimary", query = "SELECT f FROM Fundingagent f WHERE f.isPrimary = :isPrimary"),
    @NamedQuery(name = "Fundingagent.findByOrderNumber", query = "SELECT f FROM Fundingagent f WHERE f.orderNumber = :orderNumber"),
    @NamedQuery(name = "Fundingagent.findByType", query = "SELECT f FROM Fundingagent f WHERE f.type = :type"),
    @NamedQuery(name = "Fundingagent.findByDivisionID", query = "SELECT f FROM Fundingagent f WHERE f.divisionId = :divisionID"),
    @NamedQuery(name = "Fundingagent.findByCreatedByAgentID", query = "SELECT f FROM Fundingagent f WHERE f.createdByAgentId = :createdByAgentID"),
    @NamedQuery(name = "Fundingagent.findByModifiedByAgentID", query = "SELECT f FROM Fundingagent f WHERE f.modifiedByAgentId = :modifiedByAgentID"),
    @NamedQuery(name = "Fundingagent.findByCollectingTripID", query = "SELECT f FROM Fundingagent f WHERE f.collectingTripId = :collectingTripID"),
    @NamedQuery(name = "Fundingagent.findByAgentID", query = "SELECT f FROM Fundingagent f WHERE f.agentId = :agentID")})
public class Fundingagent extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "FundingAgentID")
    private Integer fundingAgentId;

    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IsPrimary")
    private boolean isPrimary;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderNumber")
    private int orderNumber;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 32)
    @Column(name = "Type")
    private String type;
    
    @Column(name = "DivisionID")
    private Integer divisionId;
    
    @Column(name = "CreatedByAgentID")
    private Integer createdByAgentId;
    
    @Column(name = "ModifiedByAgentID")
    private Integer modifiedByAgentId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CollectingTripID")
    private int collectingTripId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AgentID")
    private int agentId;

    public Fundingagent() {
    }

    public Fundingagent(Integer fundingAgentId) {
        this.fundingAgentId = fundingAgentId;
    }

    public Fundingagent(Integer fundingAgentId, Date timestampCreated, boolean isPrimary, int orderNumber, int collectingTripId, int agentId) {
        this.fundingAgentId = fundingAgentId;
        this.timestampCreated = timestampCreated;
        this.isPrimary = isPrimary;
        this.orderNumber = orderNumber;
        this.collectingTripId = collectingTripId;
        this.agentId = agentId;
    }

    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (fundingAgentId != null) ? fundingAgentId.toString() : "0";
    }
    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getCollectingTripId() {
        return collectingTripId;
    }

    public void setCollectingTripId(int collectingTripId) {
        this.collectingTripId = collectingTripId;
    }

    public Integer getCreatedByAgentId() {
        return createdByAgentId;
    }

    public void setCreatedByAgentId(Integer createdByAgentId) {
        this.createdByAgentId = createdByAgentId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getFundingAgentId() {
        return fundingAgentId;
    }

    public void setFundingAgentId(Integer fundingAgentId) {
        this.fundingAgentId = fundingAgentId;
    }

    public Integer getModifiedByAgentId() {
        return modifiedByAgentId;
    }

    public void setModifiedByAgentId(Integer modifiedByAgentId) {
        this.modifiedByAgentId = modifiedByAgentId;
    }
    
    
    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fundingAgentId != null ? fundingAgentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fundingagent)) {
            return false;
        }
        Fundingagent other = (Fundingagent) object;
        if ((this.fundingAgentId == null && other.fundingAgentId != null) || (this.fundingAgentId != null && !this.fundingAgentId.equals(other.fundingAgentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Fundingagent[ fundingAgentID=" + fundingAgentId + " ]";
    }
    
}

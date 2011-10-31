package se.nrm.specify.datamodel;
 
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author idali
 * 
 * Entity bean mapping to table taxon
 */
@Entity
@Table(name = "taxon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taxon.findAll", query = "SELECT t FROM Taxon t"),
    @NamedQuery(name = "Taxon.findByTaxonID", query = "SELECT t FROM Taxon t WHERE t.taxonID = :taxonID"),
    @NamedQuery(name = "Taxon.findByTimestampCreated", query = "SELECT t FROM Taxon t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxon.findByTimestampModified", query = "SELECT t FROM Taxon t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxon.findByVersion", query = "SELECT t FROM Taxon t WHERE t.version = :version"),
    @NamedQuery(name = "Taxon.findByAuthor", query = "SELECT t FROM Taxon t WHERE t.author = :author"),
    @NamedQuery(name = "Taxon.findByCitesStatus", query = "SELECT t FROM Taxon t WHERE t.citesStatus = :citesStatus"),
    @NamedQuery(name = "Taxon.findByCOLStatus", query = "SELECT t FROM Taxon t WHERE t.cOLStatus = :cOLStatus"),
    @NamedQuery(name = "Taxon.findByCommonName", query = "SELECT t FROM Taxon t WHERE t.commonName = :commonName"),
    @NamedQuery(name = "Taxon.findByCultivarName", query = "SELECT t FROM Taxon t WHERE t.cultivarName = :cultivarName"),
    @NamedQuery(name = "Taxon.findByEnvironmentalProtectionStatus", query = "SELECT t FROM Taxon t WHERE t.environmentalProtectionStatus = :environmentalProtectionStatus"),
    @NamedQuery(name = "Taxon.findByEsaStatus", query = "SELECT t FROM Taxon t WHERE t.esaStatus = :esaStatus"),
    @NamedQuery(name = "Taxon.findByFullName", query = "SELECT t FROM Taxon t WHERE t.fullName = :fullName"),
    @NamedQuery(name = "Taxon.findByGroupNumber", query = "SELECT t FROM Taxon t WHERE t.groupNumber = :groupNumber"),
    @NamedQuery(name = "Taxon.findByGuid", query = "SELECT t FROM Taxon t WHERE t.guid = :guid"),
    @NamedQuery(name = "Taxon.findByHighestChildNodeNumber", query = "SELECT t FROM Taxon t WHERE t.highestChildNodeNumber = :highestChildNodeNumber"),
    @NamedQuery(name = "Taxon.findByIsAccepted", query = "SELECT t FROM Taxon t WHERE t.isAccepted = :isAccepted"),
    @NamedQuery(name = "Taxon.findByIsHybrid", query = "SELECT t FROM Taxon t WHERE t.isHybrid = :isHybrid"),
    @NamedQuery(name = "Taxon.findByIsisNumber", query = "SELECT t FROM Taxon t WHERE t.isisNumber = :isisNumber"),
    @NamedQuery(name = "Taxon.findByLabelFormat", query = "SELECT t FROM Taxon t WHERE t.labelFormat = :labelFormat"),
    @NamedQuery(name = "Taxon.findByName", query = "SELECT t FROM Taxon t WHERE t.name = :name"),
    @NamedQuery(name = "Taxon.findByNcbiTaxonNumber", query = "SELECT t FROM Taxon t WHERE t.ncbiTaxonNumber = :ncbiTaxonNumber"),
    @NamedQuery(name = "Taxon.findByNodeNumber", query = "SELECT t FROM Taxon t WHERE t.nodeNumber = :nodeNumber"),
    @NamedQuery(name = "Taxon.findByNumber1", query = "SELECT t FROM Taxon t WHERE t.number1 = :number1"),
    @NamedQuery(name = "Taxon.findByNumber2", query = "SELECT t FROM Taxon t WHERE t.number2 = :number2"),
    @NamedQuery(name = "Taxon.findByRankID", query = "SELECT t FROM Taxon t WHERE t.rankID = :rankID"),
    @NamedQuery(name = "Taxon.findBySource", query = "SELECT t FROM Taxon t WHERE t.source = :source"),
    @NamedQuery(name = "Taxon.findByTaxonomicSerialNumber", query = "SELECT t FROM Taxon t WHERE t.taxonomicSerialNumber = :taxonomicSerialNumber"),
    @NamedQuery(name = "Taxon.findByText1", query = "SELECT t FROM Taxon t WHERE t.text1 = :text1"),
    @NamedQuery(name = "Taxon.findByText2", query = "SELECT t FROM Taxon t WHERE t.text2 = :text2"),
    @NamedQuery(name = "Taxon.findByUnitInd1", query = "SELECT t FROM Taxon t WHERE t.unitInd1 = :unitInd1"),
    @NamedQuery(name = "Taxon.findByUnitInd2", query = "SELECT t FROM Taxon t WHERE t.unitInd2 = :unitInd2"),
    @NamedQuery(name = "Taxon.findByUnitInd3", query = "SELECT t FROM Taxon t WHERE t.unitInd3 = :unitInd3"),
    @NamedQuery(name = "Taxon.findByUnitInd4", query = "SELECT t FROM Taxon t WHERE t.unitInd4 = :unitInd4"),
    @NamedQuery(name = "Taxon.findByUnitName1", query = "SELECT t FROM Taxon t WHERE t.unitName1 = :unitName1"),
    @NamedQuery(name = "Taxon.findByUnitName2", query = "SELECT t FROM Taxon t WHERE t.unitName2 = :unitName2"),
    @NamedQuery(name = "Taxon.findByUnitName3", query = "SELECT t FROM Taxon t WHERE t.unitName3 = :unitName3"),
    @NamedQuery(name = "Taxon.findByUnitName4", query = "SELECT t FROM Taxon t WHERE t.unitName4 = :unitName4"),
    @NamedQuery(name = "Taxon.findByUsfwsCode", query = "SELECT t FROM Taxon t WHERE t.usfwsCode = :usfwsCode"),
    @NamedQuery(name = "Taxon.findByVisibility", query = "SELECT t FROM Taxon t WHERE t.visibility = :visibility")})
public class Taxon extends BaseEntity {  
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TaxonID")
    private Integer taxonID;
     
    @Size(max = 128)
    @Column(name = "Author")
    private String author;
    
    @Size(max = 32)
    @Column(name = "CitesStatus")
    private String citesStatus;
    
    @Size(max = 32)
    @Column(name = "COLStatus")
    private String cOLStatus;
    
    @Size(max = 128)
    @Column(name = "CommonName")
    private String commonName;
    
    @Size(max = 32)
    @Column(name = "CultivarName")
    private String cultivarName;
    
    @Size(max = 64)
    @Column(name = "EnvironmentalProtectionStatus")
    private String environmentalProtectionStatus;
    
    @Size(max = 64)
    @Column(name = "EsaStatus")
    private String esaStatus;
    
    @Size(max = 255)
    @Column(name = "FullName")
    private String fullName;
    
    @Size(max = 20)
    @Column(name = "GroupNumber")
    private String groupNumber;
    
    @Size(max = 128)
    @Column(name = "GUID")
    private String guid;
    
    @Column(name = "HighestChildNodeNumber")
    private Integer highestChildNodeNumber;
    
    @Column(name = "IsAccepted")
    private Boolean isAccepted;
    
    @Column(name = "IsHybrid")
    private Boolean isHybrid;
    
    @Size(max = 16)
    @Column(name = "IsisNumber")
    private String isisNumber;
    
    @Size(max = 64)
    @Column(name = "LabelFormat")
    private String labelFormat;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "Name")
    private String name;
    
    @Size(max = 8)
    @Column(name = "NcbiTaxonNumber")
    private String ncbiTaxonNumber;
    
    @Column(name = "NodeNumber")
    private Integer nodeNumber;
    
    @Column(name = "Number1")
    private Integer number1;
    
    @Column(name = "Number2")
    private Integer number2;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RankID")
    private int rankID;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "Remarks")
    private String remarks;
    
    @Size(max = 64)
    @Column(name = "Source")
    private String source;
    
    @Size(max = 50)
    @Column(name = "TaxonomicSerialNumber")
    private String taxonomicSerialNumber;
    
    @Size(max = 32)
    @Column(name = "Text1")
    private String text1;
    
    @Size(max = 32)
    @Column(name = "Text2")
    private String text2;
    
    @Size(max = 50)
    @Column(name = "UnitInd1")
    private String unitInd1;
    
    @Size(max = 50)
    @Column(name = "UnitInd2")
    private String unitInd2;
    
    @Size(max = 50)
    @Column(name = "UnitInd3")
    private String unitInd3;
    
    @Size(max = 50)
    @Column(name = "UnitInd4")
    private String unitInd4;
    
    @Size(max = 50)
    @Column(name = "UnitName1")
    private String unitName1;
    
    @Size(max = 50)
    @Column(name = "UnitName2")
    private String unitName2;
    
    @Size(max = 50)
    @Column(name = "UnitName3")
    private String unitName3;
    
    @Size(max = 50)
    @Column(name = "UnitName4")
    private String unitName4;
    
    @Size(max = 16)
    @Column(name = "UsfwsCode")
    private String usfwsCode;
    
    @Column(name = "Visibility")
    private Short visibility;
    
    @JoinColumn(name = "TaxonTreeDefID", referencedColumnName = "TaxonTreeDefID") 
    @ManyToOne(optional = false)
    private Taxontreedef taxonTreeDefID;
    
    @OneToMany(mappedBy = "parentID") 
    private Collection<Taxon> taxonCollection;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon parentID;
    
    @JoinColumn(name = "TaxonTreeDefItemID", referencedColumnName = "TaxonTreeDefItemID")
    @ManyToOne(optional = false)
    private Taxontreedefitem taxonTreeDefItemID;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetByID;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgentID;
    
    @OneToMany(mappedBy = "hybridParent2ID")
    private Collection<Taxon> taxonCollection1;
    
    @JoinColumn(name = "HybridParent2ID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon hybridParent2ID;
    
    @OneToMany(mappedBy = "hybridParent1ID")
    private Collection<Taxon> taxonCollection2;
    
    @JoinColumn(name = "HybridParent1ID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon hybridParent1ID;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgentID;
    
    @OneToMany(mappedBy = "acceptedID")
    private Collection<Taxon> taxonCollection3;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon acceptedID;
    
    @OneToMany(mappedBy = "hostTaxonID")
    private Collection<Collectingeventattribute> collectingeventattributeCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonID") 
    private Collection<Commonnametx> commonnametxCollection;
    
    @OneToMany(mappedBy = "preferredTaxonID")
    private Collection<Determination> determinationCollection;
    
    @OneToMany(mappedBy = "taxonID")
    private Collection<Determination> determinationCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonID")
    private Collection<Taxoncitation> taxoncitationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonID")
    private Collection<Taxonattachment> taxonattachmentCollection;

    public Taxon() {
    }

    public Taxon(Integer taxonID) {
        this.taxonID = taxonID;
    }

    public Taxon(Integer taxonID, Date timestampCreated, String name, int rankID) {
        super(timestampCreated);
        this.taxonID = taxonID; 
        this.name = name;
        this.rankID = rankID;
    }

    public Integer getTaxonID() {
        return taxonID;
    }

    public void setTaxonID(Integer taxonID) {
        this.taxonID = taxonID;
    }
 
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCitesStatus() {
        return citesStatus;
    }

    public void setCitesStatus(String citesStatus) {
        this.citesStatus = citesStatus;
    }

    public String getCOLStatus() {
        return cOLStatus;
    }

    public void setCOLStatus(String cOLStatus) {
        this.cOLStatus = cOLStatus;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getCultivarName() {
        return cultivarName;
    }

    public void setCultivarName(String cultivarName) {
        this.cultivarName = cultivarName;
    }

    public String getEnvironmentalProtectionStatus() {
        return environmentalProtectionStatus;
    }

    public void setEnvironmentalProtectionStatus(String environmentalProtectionStatus) {
        this.environmentalProtectionStatus = environmentalProtectionStatus;
    }

    public String getEsaStatus() {
        return esaStatus;
    }

    public void setEsaStatus(String esaStatus) {
        this.esaStatus = esaStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getHighestChildNodeNumber() {
        return highestChildNodeNumber;
    }

    public void setHighestChildNodeNumber(Integer highestChildNodeNumber) {
        this.highestChildNodeNumber = highestChildNodeNumber;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Boolean getIsHybrid() {
        return isHybrid;
    }

    public void setIsHybrid(Boolean isHybrid) {
        this.isHybrid = isHybrid;
    }

    public String getIsisNumber() {
        return isisNumber;
    }

    public void setIsisNumber(String isisNumber) {
        this.isisNumber = isisNumber;
    }

    public String getLabelFormat() {
        return labelFormat;
    }

    public void setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNcbiTaxonNumber() {
        return ncbiTaxonNumber;
    }

    public void setNcbiTaxonNumber(String ncbiTaxonNumber) {
        this.ncbiTaxonNumber = ncbiTaxonNumber;
    }

    public Integer getNodeNumber() {
        return nodeNumber;
    }

    public void setNodeNumber(Integer nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTaxonomicSerialNumber() {
        return taxonomicSerialNumber;
    }

    public void setTaxonomicSerialNumber(String taxonomicSerialNumber) {
        this.taxonomicSerialNumber = taxonomicSerialNumber;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getUnitInd1() {
        return unitInd1;
    }

    public void setUnitInd1(String unitInd1) {
        this.unitInd1 = unitInd1;
    }

    public String getUnitInd2() {
        return unitInd2;
    }

    public void setUnitInd2(String unitInd2) {
        this.unitInd2 = unitInd2;
    }

    public String getUnitInd3() {
        return unitInd3;
    }

    public void setUnitInd3(String unitInd3) {
        this.unitInd3 = unitInd3;
    }

    public String getUnitInd4() {
        return unitInd4;
    }

    public void setUnitInd4(String unitInd4) {
        this.unitInd4 = unitInd4;
    }

    public String getUnitName1() {
        return unitName1;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    public String getUnitName2() {
        return unitName2;
    }

    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    public String getUnitName3() {
        return unitName3;
    }

    public void setUnitName3(String unitName3) {
        this.unitName3 = unitName3;
    }

    public String getUnitName4() {
        return unitName4;
    }

    public void setUnitName4(String unitName4) {
        this.unitName4 = unitName4;
    }

    public String getUsfwsCode() {
        return usfwsCode;
    }

    public void setUsfwsCode(String usfwsCode) {
        this.usfwsCode = usfwsCode;
    }

    public Short getVisibility() {
        return visibility;
    }

    public void setVisibility(Short visibility) {
        this.visibility = visibility;
    }

    public Taxontreedef getTaxonTreeDefID() {
        return taxonTreeDefID;
    }

    public void setTaxonTreeDefID(Taxontreedef taxonTreeDefID) {
        this.taxonTreeDefID = taxonTreeDefID;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection() {
        return taxonCollection;
    }

    public void setTaxonCollection(Collection<Taxon> taxonCollection) {
        this.taxonCollection = taxonCollection;
    }

    public Taxon getParentID() {
        return parentID;
    }

    public void setParentID(Taxon parentID) {
        this.parentID = parentID;
    }

    public Taxontreedefitem getTaxonTreeDefItemID() {
        return taxonTreeDefItemID;
    }

    public void setTaxonTreeDefItemID(Taxontreedefitem taxonTreeDefItemID) {
        this.taxonTreeDefItemID = taxonTreeDefItemID;
    }

    public Specifyuser getVisibilitySetByID() {
        return visibilitySetByID;
    }

    public void setVisibilitySetByID(Specifyuser visibilitySetByID) {
        this.visibilitySetByID = visibilitySetByID;
    }

    public Agent getCreatedByAgentID() {
        return createdByAgentID;
    }

    public void setCreatedByAgentID(Agent createdByAgentID) {
        this.createdByAgentID = createdByAgentID;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection1() {
        return taxonCollection1;
    }

    public void setTaxonCollection1(Collection<Taxon> taxonCollection1) {
        this.taxonCollection1 = taxonCollection1;
    }

    public Taxon getHybridParent2ID() {
        return hybridParent2ID;
    }

    public void setHybridParent2ID(Taxon hybridParent2ID) {
        this.hybridParent2ID = hybridParent2ID;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection2() {
        return taxonCollection2;
    }

    public void setTaxonCollection2(Collection<Taxon> taxonCollection2) {
        this.taxonCollection2 = taxonCollection2;
    }

    public Taxon getHybridParent1ID() {
        return hybridParent1ID;
    }

    public void setHybridParent1ID(Taxon hybridParent1ID) {
        this.hybridParent1ID = hybridParent1ID;
    }

    public Agent getModifiedByAgentID() {
        return modifiedByAgentID;
    }

    public void setModifiedByAgentID(Agent modifiedByAgentID) {
        this.modifiedByAgentID = modifiedByAgentID;
    }

    @XmlTransient
    public Collection<Taxon> getTaxonCollection3() {
        return taxonCollection3;
    }

    public void setTaxonCollection3(Collection<Taxon> taxonCollection3) {
        this.taxonCollection3 = taxonCollection3;
    }

    public Taxon getAcceptedID() {
        return acceptedID;
    }

    public void setAcceptedID(Taxon acceptedID) {
        this.acceptedID = acceptedID;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingeventattributeCollection() {
        return collectingeventattributeCollection;
    }

    public void setCollectingeventattributeCollection(Collection<Collectingeventattribute> collectingeventattributeCollection) {
        this.collectingeventattributeCollection = collectingeventattributeCollection;
    }

    @XmlTransient
    public Collection<Commonnametx> getCommonnametxCollection() {
        return commonnametxCollection;
    }

    public void setCommonnametxCollection(Collection<Commonnametx> commonnametxCollection) {
        this.commonnametxCollection = commonnametxCollection;
    }

    @XmlTransient
    public Collection<Determination> getDeterminationCollection() {
        return determinationCollection;
    }

    public void setDeterminationCollection(Collection<Determination> determinationCollection) {
        this.determinationCollection = determinationCollection;
    }

    @XmlTransient
    public Collection<Determination> getDeterminationCollection1() {
        return determinationCollection1;
    }

    public void setDeterminationCollection1(Collection<Determination> determinationCollection1) {
        this.determinationCollection1 = determinationCollection1;
    }

    @XmlTransient
    public Collection<Taxoncitation> getTaxoncitationCollection() {
        return taxoncitationCollection;
    }

    public void setTaxoncitationCollection(Collection<Taxoncitation> taxoncitationCollection) {
        this.taxoncitationCollection = taxoncitationCollection;
    }

    @XmlTransient
    public Collection<Taxonattachment> getTaxonattachmentCollection() {
        return taxonattachmentCollection;
    }

    public void setTaxonattachmentCollection(Collection<Taxonattachment> taxonattachmentCollection) {
        this.taxonattachmentCollection = taxonattachmentCollection;
    }
     
    
    @XmlTransient
    public int getNumDetermination() {
        return determinationCollection1.size();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonID != null ? taxonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxon)) {
            return false;
        }
        Taxon other = (Taxon) object;
        if ((this.taxonID == null && other.taxonID != null) || (this.taxonID != null && !this.taxonID.equals(other.taxonID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxon[ taxonID=" + taxonID + " ]";
    }
    
}

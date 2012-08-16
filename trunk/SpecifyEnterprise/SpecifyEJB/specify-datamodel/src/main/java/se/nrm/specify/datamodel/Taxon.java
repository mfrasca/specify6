package se.nrm.specify.datamodel;

import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Index;

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
    @NamedQuery(name = "Taxon.findByTaxonID", query = "SELECT t FROM Taxon t WHERE t.taxonId = :taxonID"),
    @NamedQuery(name = "Taxon.findByTimestampCreated", query = "SELECT t FROM Taxon t WHERE t.timestampCreated = :timestampCreated"),
    @NamedQuery(name = "Taxon.findByTimestampModified", query = "SELECT t FROM Taxon t WHERE t.timestampModified = :timestampModified"),
    @NamedQuery(name = "Taxon.findByVersion", query = "SELECT t FROM Taxon t WHERE t.version = :version"),
    @NamedQuery(name = "Taxon.findByAuthor", query = "SELECT t FROM Taxon t WHERE t.author = :author"),
    @NamedQuery(name = "Taxon.findByCitesStatus", query = "SELECT t FROM Taxon t WHERE t.citesStatus = :citesStatus"),
    @NamedQuery(name = "Taxon.findByCOLStatus", query = "SELECT t FROM Taxon t WHERE t.colStatus = :cOLStatus"),
    @NamedQuery(name = "Taxon.findByCommonName", query = "SELECT t FROM Taxon t WHERE t.commonName = :commonName"),
    @NamedQuery(name = "Taxon.findByCommonNameWithWildCard", query = "SELECT t FROM Taxon t WHERE t.commonName like :commonName"),
    @NamedQuery(name = "Taxon.findByCultivarName", query = "SELECT t FROM Taxon t WHERE t.cultivarName = :cultivarName"),
    @NamedQuery(name = "Taxon.findByEnvironmentalProtectionStatus", query = "SELECT t FROM Taxon t WHERE t.environmentalProtectionStatus = :environmentalProtectionStatus"),
    @NamedQuery(name = "Taxon.findByEsaStatus", query = "SELECT t FROM Taxon t WHERE t.esaStatus = :esaStatus"),
    @NamedQuery(name = "Taxon.findByFullName", query = "SELECT t FROM Taxon t WHERE t.fullName = :fullName"),
    @NamedQuery(name = "Taxon.findByFullNameWithWildCard", query = "SELECT t FROM Taxon t WHERE t.fullName like :fullName"),
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
    @NamedQuery(name = "Taxon.findByRankID", query = "SELECT t FROM Taxon t WHERE t.rankId = :rankID"),
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
    private Integer taxonId;
    
    @Size(max = 128)
    @Column(name = "Author")
    private String author;
    
    @Size(max = 32)
    @Column(name = "CitesStatus")
    private String citesStatus;
    
    @Size(max = 32)
    @Column(name = "COLStatus")
    private String colStatus;
    
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
    
    @Index
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
    private int rankId;
    
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
    private Taxontreedef definition;
    
    @OneToMany(mappedBy = "parent")
    private Collection<Taxon> children;
    
    @JoinColumn(name = "ParentID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon parent;
    
    @JoinColumn(name = "TaxonTreeDefItemID", referencedColumnName = "TaxonTreeDefItemID")
    @ManyToOne(optional = false)
    private Taxontreedefitem definitionItem;
    
    @JoinColumn(name = "VisibilitySetByID", referencedColumnName = "SpecifyUserID")
    @ManyToOne
    private Specifyuser visibilitySetBy;
    
    @JoinColumn(name = "CreatedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent createdByAgent;
    
    @OneToMany(mappedBy = "hybridParent2")
    private Collection<Taxon> hybridChildren2;
    
    @JoinColumn(name = "HybridParent2ID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon hybridParent2;
    
    @OneToMany(mappedBy = "hybridParent1")
    private Collection<Taxon> hybridChildren1;
    
    @JoinColumn(name = "HybridParent1ID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon hybridParent1;
    
    @JoinColumn(name = "ModifiedByAgentID", referencedColumnName = "AgentID")
    @ManyToOne
    private Agent modifiedByAgent;
    
    @OneToMany(mappedBy = "acceptedTaxon")
    private Collection<Taxon> acceptedChildren;
    
    @JoinColumn(name = "AcceptedID", referencedColumnName = "TaxonID")
    @ManyToOne
    private Taxon acceptedTaxon;
    
    @OneToMany(mappedBy = "hostTaxon")
    private Collection<Collectingeventattribute> collectingEventAttributes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxon")
    private Collection<Commonnametx> commonNames;
    
    
    @OneToMany(mappedBy = "preferredTaxon")
    private Collection<Determination> determinations1;
    
    @OneToMany(mappedBy = "taxon")
    private Collection<Determination> determinations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxon")
    private Collection<Taxoncitation> taxonCitations;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxon")
    private Collection<Taxonattachment> taxonAttachments;
    
 

    public Taxon() {
    }

    public Taxon(Integer taxonId) {
        this.taxonId = taxonId;
    }

    public Taxon(Integer taxonId, Date timestampCreated, String name, int rankId) {
        super(timestampCreated);
        this.taxonId = taxonId;
        this.name = name;
        this.rankId = rankId;
    }
    
    @XmlID
    @XmlAttribute(name = "id")
    @Override
    public String getIdentityString() {
        return (taxonId != null) ? taxonId.toString() : "0";
    }
    public Taxon(Integer taxonId, String fullName) {
        this.taxonId = taxonId;
        this.fullName = fullName;
    }

    @XmlIDREF
    public Collection<Taxon> getAcceptedChildren() {
        return acceptedChildren;
    }

    public void setAcceptedChildren(Collection<Taxon> acceptedChildren) {
        this.acceptedChildren = acceptedChildren;
    }

    @XmlIDREF
    public Taxon getAcceptedTaxon() {
        return acceptedTaxon;
    }

    public void setAcceptedTaxon(Taxon acceptedTaxon) {
        this.acceptedTaxon = acceptedTaxon;
    }

    @XmlElementWrapper(name="children")
    @XmlElement(name="child") 
    @XmlIDREF
    public Collection<Taxon> getChildren() {
        return children;
    }

    public void setChildren(Collection<Taxon> children) {
        this.children = children;
    }

    public String getColStatus() {
        return colStatus;
    }

    public void setColStatus(String colStatus) {
        this.colStatus = colStatus;
    }

    @XmlTransient
    public Collection<Collectingeventattribute> getCollectingEventAttributes() {
        return collectingEventAttributes;
    }

    public void setCollectingEventAttributes(Collection<Collectingeventattribute> collectingEventAttributes) {
        this.collectingEventAttributes = collectingEventAttributes;
    }
 
    public Collection<Commonnametx> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(Collection<Commonnametx> commonNames) {
        this.commonNames = commonNames;
    }

    @XmlIDREF
    public Agent getCreatedByAgent() {
        return createdByAgent;
    }

    public void setCreatedByAgent(Agent createdByAgent) {
        this.createdByAgent = createdByAgent;
    }

    @XmlIDREF
    @NotNull(message="Definition must be specified.")
    public Taxontreedef getDefinition() {
        return definition;
    }

    public void setDefinition(Taxontreedef definition) {
        this.definition = definition;
    }

    @XmlIDREF
    @NotNull(message="DefinitionItem must be specified.")
    public Taxontreedefitem getDefinitionItem() {
        return definitionItem;
    }

    public void setDefinitionItem(Taxontreedefitem definitionItem) {
        this.definitionItem = definitionItem;
    }

    @XmlTransient
    public Collection<Determination> getDeterminations1() {
        return determinations1;
    }

    public void setDeterminations1(Collection<Determination> determinations1) {
        this.determinations1 = determinations1;
    }

    @XmlTransient
    public Collection<Determination> getDeterminations() {
        return determinations;
    }

    public void setDeterminations(Collection<Determination> determinations) {
        this.determinations = determinations;
    }

    @XmlTransient
    public Collection<Taxon> getHybridChildren1() {
        return hybridChildren1;
    }

    public void setHybridChildren1(Collection<Taxon> hybridChildren1) {
        this.hybridChildren1 = hybridChildren1;
    }

    @XmlTransient
    public Collection<Taxon> getHybridChildren2() {
        return hybridChildren2;
    }

    public void setHybridChildren2(Collection<Taxon> hybridChildren2) {
        this.hybridChildren2 = hybridChildren2;
    }

    @XmlIDREF
    public Taxon getHybridParent1() {
        return hybridParent1;
    }

    public void setHybridParent1(Taxon hybridParent1) {
        this.hybridParent1 = hybridParent1;
    }

    @XmlIDREF
    public Taxon getHybridParent2() {
        return hybridParent2;
    }

    public void setHybridParent2(Taxon hybridParent2) {
        this.hybridParent2 = hybridParent2;
    }

    @XmlIDREF
    public Agent getModifiedByAgent() {
        return modifiedByAgent;
    }

    public void setModifiedByAgent(Agent modifiedByAgent) {
        this.modifiedByAgent = modifiedByAgent;
    }

    @XmlIDREF
    public Taxon getParent() {
        return parent;
    }

    public void setParent(Taxon parent) {
        this.parent = parent;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }
 
    public Collection<Taxonattachment> getTaxonAttachments() {
        return taxonAttachments;
    }

    public void setTaxonAttachments(Collection<Taxonattachment> taxonAttachments) {
        this.taxonAttachments = taxonAttachments;
    }
  
    public Collection<Taxoncitation> getTaxonCitations() {
        return taxonCitations;
    }

    public void setTaxonCitations(Collection<Taxoncitation> taxonCitations) {
        this.taxonCitations = taxonCitations;
    }

    public Integer getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(Integer taxonId) {
        this.taxonId = taxonId;
    }

    public Specifyuser getVisibilitySetBy() {
        return visibilitySetBy;
    }

    public void setVisibilitySetBy(Specifyuser visibilitySetBy) {
        this.visibilitySetBy = visibilitySetBy;
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

    @NotNull(message="Name must be specified.")
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

    
    @XmlTransient
    public int getNumDetermination() {
        return determinations.size();
    }

    public Taxon getTaxonFamily() {

        if (rankId > 140) {
            if (definitionItem != null && definitionItem.getName().equals("Family")) {
                return this;
            } else {
                Taxon newParent = parent;

                if (parent != null) {
                    Taxontreedefitem item = newParent.getDefinitionItem();

                    while (item != null && !item.getName().equals("Family")) {
                        newParent = newParent.getParent();
                        item = newParent.getDefinitionItem();
                    }
                }

                return newParent;
            }
        }
        return new Taxon();
    }
     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxonId != null ? taxonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxon)) {
            return false;
        }
        Taxon other = (Taxon) object;
        if ((this.taxonId == null && other.taxonId != null) || (this.taxonId != null && !this.taxonId.equals(other.taxonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxon[ taxonId=" + taxonId + " ]";
    }
 
}

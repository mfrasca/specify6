/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.specify.datamodel;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "countryinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countryinfo.findAll", query = "SELECT c FROM Countryinfo c"),
    @NamedQuery(name = "Countryinfo.findByName", query = "SELECT c FROM Countryinfo c WHERE c.name = :name"),
    @NamedQuery(name = "Countryinfo.findByIsoAlpha2", query = "SELECT c FROM Countryinfo c WHERE c.isoAlpha2 = :isoAlpha2"),
    @NamedQuery(name = "Countryinfo.findByIsoAlpha3", query = "SELECT c FROM Countryinfo c WHERE c.isoAlpha3 = :isoAlpha3"),
    @NamedQuery(name = "Countryinfo.findByIsoNumeric", query = "SELECT c FROM Countryinfo c WHERE c.isoNumeric = :isoNumeric"),
    @NamedQuery(name = "Countryinfo.findByFipsCode", query = "SELECT c FROM Countryinfo c WHERE c.fipsCode = :fipsCode"),
    @NamedQuery(name = "Countryinfo.findByCapital", query = "SELECT c FROM Countryinfo c WHERE c.capital = :capital"),
    @NamedQuery(name = "Countryinfo.findByAreainsqkm", query = "SELECT c FROM Countryinfo c WHERE c.areainsqkm = :areainsqkm"),
    @NamedQuery(name = "Countryinfo.findByPopulation", query = "SELECT c FROM Countryinfo c WHERE c.population = :population"),
    @NamedQuery(name = "Countryinfo.findByContinent", query = "SELECT c FROM Countryinfo c WHERE c.continent = :continent"),
    @NamedQuery(name = "Countryinfo.findByLanguages", query = "SELECT c FROM Countryinfo c WHERE c.languages = :languages"),
    @NamedQuery(name = "Countryinfo.findByCurrency", query = "SELECT c FROM Countryinfo c WHERE c.currency = :currency"),
    @NamedQuery(name = "Countryinfo.findByGeonameId", query = "SELECT c FROM Countryinfo c WHERE c.geonameId = :geonameId")})
public class Countryinfo implements Serializable, SpecifyBean {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "name")
    private String name;
    
    @Size(max = 2)
    @Column(name = "iso_alpha2")
    private String isoAlpha2;
    
    @Size(max = 3)
    @Column(name = "iso_alpha3")
    private String isoAlpha3;
    
    @Column(name = "iso_numeric")
    private Integer isoNumeric;
    
    @Size(max = 3)
    @Column(name = "fips_code")
    private String fipsCode;
    
    @Size(max = 200)
    @Column(name = "capital")
    private String capital;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "areainsqkm")
    private Double areainsqkm;
    
    @Column(name = "population")
    private Integer population;
    
    @Size(max = 2)
    @Column(name = "continent")
    private String continent;
    
    @Size(max = 200)
    @Column(name = "languages")
    private String languages;
    
    @Size(max = 3)
    @Column(name = "currency")
    private String currency;
    
    @Column(name = "geonameId")
    private Integer geonameId;

    public Countryinfo() {
    }

    public Countryinfo(String name) {
        this.name = name;
    }
    
    @XmlID
    @XmlAttribute(name = "id") 
    public String getIdentityString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoAlpha2() {
        return isoAlpha2;
    }

    public void setIsoAlpha2(String isoAlpha2) {
        this.isoAlpha2 = isoAlpha2;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public Integer getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(Integer isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Double getAreainsqkm() {
        return areainsqkm;
    }

    public void setAreainsqkm(Double areainsqkm) {
        this.areainsqkm = areainsqkm;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Countryinfo)) {
            return false;
        }
        Countryinfo other = (Countryinfo) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.specify.datamodel.Countryinfo[ name=" + name + " ]";
    }
    
}

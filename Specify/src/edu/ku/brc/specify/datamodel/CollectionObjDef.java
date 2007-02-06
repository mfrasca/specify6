/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.specify.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.HashSet;
import java.util.Set;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@Table(name="collectionobjdef")
public class CollectionObjDef extends DataModelObjBase implements java.io.Serializable 
{

    protected static CollectionObjDef currentCollectionObjDef = null;
    
    // Fields

     protected Long collectionObjDefId;
     protected String name;
     protected String discipline;
     protected DataType dataType;
     protected Set<CatalogSeries> catalogSeries;
     protected SpecifyUser specifyUser;
     protected Set<AttributeDef> attributeDefs;
     protected GeographyTreeDef geographyTreeDef;
     protected GeologicTimePeriodTreeDef geologicTimePeriodTreeDef;
     protected LocationTreeDef locationTreeDef;
     protected TaxonTreeDef taxonTreeDef;
     protected Set<Locality> localities;
     protected Set<AppResourceDefault> appResourceDefaults;
     private Set<CollectionObject> collectionObjects;
     private Set<UserPermission> userPermissions;
     

    // Constructors

    /** default constructor */
    public CollectionObjDef() {
        //
    }

    /** constructor with id */
    public CollectionObjDef(Long collectionObjDefId) {
        this.collectionObjDefId = collectionObjDefId;
    }

    public static CollectionObjDef getCurrentCollectionObjDef()
    {
        return currentCollectionObjDef;
    }

    public static void setCurrentCollectionObjDef(CollectionObjDef currentCollectionObjDef)
    {
        CollectionObjDef.currentCollectionObjDef = currentCollectionObjDef;
    }

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        collectionObjDefId = null;
        name = null;
        discipline = null;
        dataType = null;
        userPermissions = null;
        catalogSeries = new HashSet<CatalogSeries>();
        specifyUser = null;
        attributeDefs = new HashSet<AttributeDef>();
        geographyTreeDef = null;
        geologicTimePeriodTreeDef = null;
        locationTreeDef = null;
        taxonTreeDef = null;
        localities = new HashSet<Locality>();
        collectionObjects = new HashSet<CollectionObject>();
        appResourceDefaults = new HashSet<AppResourceDefault>();
    }
    // End Initializer

    // Property accessors

    /**
     *
     */
    @Id
    @GeneratedValue
    @Column(name="CollectionObjDefID", unique=false, nullable=false, insertable=true, updatable=true)
    public Long getCollectionObjDefId() {
        return this.collectionObjDefId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Long getId()
    {
        return this.collectionObjDefId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return CollectionObjDef.class;
    }

    public void setCollectionObjDefId(Long collectionObjDefId) {
        this.collectionObjDefId = collectionObjDefId;
    }

    /**
     *
     */
    @Column(name="Name", unique=false, nullable=true, insertable=true, updatable=true, length=64)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
    *
    */
    @Column(name="Discipline", unique=false, nullable=true, insertable=true, updatable=true, length=64)
    public String getDiscipline()
    {
        return discipline;
    }

    public void setDiscipline(String discipline)
    {
        this.discipline = discipline;
    }

    /**
     *
     */
    @ManyToOne
    @Cascade( {CascadeType.SAVE_UPDATE} )
    @JoinColumn(name="DataTypeID", unique=false, nullable=false, insertable=true, updatable=true)
    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     *
     */
    @ManyToMany(cascade={}, fetch=FetchType.LAZY, mappedBy="collectionObjDefItems")
    @Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN} )
    public Set<CatalogSeries> getCatalogSeries() {
        return this.catalogSeries;
    }

    public void setCatalogSeries(Set<CatalogSeries> catalogSeries) {
        this.catalogSeries = catalogSeries;
    }

    /**
     *
     */
    @ManyToOne
    @Cascade( {CascadeType.SAVE_UPDATE} )
    @JoinColumn(name="SpecifyUserID", unique=false, nullable=false, insertable=true, updatable=true)
    public SpecifyUser getSpecifyUser() {
        return this.specifyUser;
    }

    public void setSpecifyUser(SpecifyUser specifyUser) {
        this.specifyUser = specifyUser;
    }

    /**
     *
     */
    @OneToMany(cascade={}, fetch=FetchType.LAZY, mappedBy="collectionObjDef")
    @Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN} )
    public Set<AttributeDef> getAttributeDefs() {
        return this.attributeDefs;
    }

    public void setAttributeDefs(Set<AttributeDef> attributeDefs) {
        this.attributeDefs = attributeDefs;
    }

    /**
     *
     */
    @ManyToOne
    @Cascade( {CascadeType.SAVE_UPDATE} )
    @JoinColumn(name="GeographyTreeDefID", unique=false, nullable=false, insertable=true, updatable=true)
    public GeographyTreeDef getGeographyTreeDef() {
        return this.geographyTreeDef;
    }

    public void setGeographyTreeDef(GeographyTreeDef geographyTreeDef) {
        this.geographyTreeDef = geographyTreeDef;
    }

    /**
     *
     */
    @ManyToOne
    @Cascade( {CascadeType.SAVE_UPDATE} )
    @JoinColumn(name="GeologicTimePeriodTreeDefID", unique=false, nullable=false, insertable=true, updatable=true)
    public GeologicTimePeriodTreeDef getGeologicTimePeriodTreeDef() {
        return this.geologicTimePeriodTreeDef;
    }

    public void setGeologicTimePeriodTreeDef(GeologicTimePeriodTreeDef geologicTimePeriodTreeDef) {
        this.geologicTimePeriodTreeDef = geologicTimePeriodTreeDef;
    }

    /**
     *
     */
    @ManyToOne
    @Cascade( {CascadeType.SAVE_UPDATE} )
    @JoinColumn(name="LocationTreeDefID", unique=false, nullable=false, insertable=true, updatable=true)
    public LocationTreeDef getLocationTreeDef() {
        return this.locationTreeDef;
    }

    public void setLocationTreeDef(LocationTreeDef locationTreeDef) {
        this.locationTreeDef = locationTreeDef;
    }

    /**
     *      * @hibernate.one-to-one
     */
    @OneToOne
    @JoinColumn(name="TaxonTreeDefID")
    public TaxonTreeDef getTaxonTreeDef() {
        return this.taxonTreeDef;
    }

    public void setTaxonTreeDef(TaxonTreeDef taxonTreeDef) {
        this.taxonTreeDef = taxonTreeDef;
    }

    /**
     *
     */
    @ManyToMany(cascade={}, fetch=FetchType.LAZY, mappedBy="collectionObjDefs")
    @Cascade( {CascadeType.SAVE_UPDATE} )
    public Set<Locality> getLocalities() {
        return this.localities;
    }

    public void setLocalities(Set<Locality> localities) {
        this.localities = localities;
    } 

    @OneToMany(cascade={}, fetch=FetchType.LAZY, mappedBy="collectionObjDef")
    public Set<AppResourceDefault> getAppResourceDefaults()
    {
        return appResourceDefaults;
    }

    public void setAppResourceDefaults(Set<AppResourceDefault> appResourceDefaults)
    {
        this.appResourceDefaults = appResourceDefaults;
    }
    @OneToMany(cascade={}, fetch=FetchType.LAZY, mappedBy="collectionObjDef")
    @Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN} )
    public Set<UserPermission> getUserPermissions() 
    {
        return this.userPermissions;
    }
    
    public void setUserPermissions(Set<UserPermission> userPermissions) 
    {
        this.userPermissions = userPermissions;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(128);

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("name").append("='").append(getName()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }




    // Add Methods

    public void addCatalogSeries(final CatalogSeries catalogSeriesArg)
    {
        this.catalogSeries.add(catalogSeriesArg);
        catalogSeriesArg.getCollectionObjDefItems().add(this);
    }

    public void addAttributeDefs(final AttributeDef attributeDef)
    {
        this.attributeDefs.add(attributeDef);
        attributeDef.setCollectionObjDef(this);
    }

    public void addLocalities(final Locality localitiesArg)
    {
        this.localities.add(localitiesArg);
        localitiesArg.getCollectionObjDefs().add(this);
    }
    
    public void addCollectionObject(final CollectionObject collectionObject)
    {
        this.collectionObjects.add(collectionObject);
        collectionObject.setCollectionObjDef(this);
    }
    public void addUserPermission(final UserPermission userPermission)
    {
        this.userPermissions.add(userPermission);
        userPermission.setCollectionObjDef(this);
    }
    // Done Add Methods

    // Delete Methods

    public void removeCatalogSeries(final CatalogSeries catalogSeriesArg)
    {
        this.catalogSeries.remove(catalogSeriesArg);
        catalogSeriesArg.getCollectionObjDefItems().remove(this);
    }

    public void removeAttributeDefs(final AttributeDef attributeDef)
    {
        this.attributeDefs.remove(attributeDef);
        attributeDef.setCollectionObjDef(null);
    }
    public void removeCollectionObject(final CollectionObject collectionObject)
    {
        this.collectionObjects.remove(collectionObject);
        collectionObject.setCollectionObjDef(null);
    }
    
    public void removeLocalities(final Locality localitiesArg)
    {
        this.localities.remove(localitiesArg);
        localitiesArg.getCollectionObjDefs().remove(this);
    }
    public void removeUserPermission(final UserPermission userPermission)
    {
        this.userPermissions.remove(userPermission);
        userPermission.setCollectionObjDef(null);
    }
    // Delete Add Methods
    
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getIdentityTitle()
     */
    @Override
    @Transient
    public String getIdentityTitle()
    {
        if (name != null) return name;
        return super.getIdentityTitle();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public Integer getTableId()
    {
        return 26;
    }

    /**
     * @return the collectionObjects
     */
    @OneToMany(cascade={}, fetch=FetchType.LAZY, mappedBy="collectionObjDef")
    @Cascade( {CascadeType.ALL, CascadeType.DELETE_ORPHAN} )
    public Set<CollectionObject> getCollectionObjects()
    {
        return this.collectionObjects;
    }

    /**
     * @param collectionObjects the collectionObjects to set
     */
    public void setCollectionObjects(Set<CollectionObject> collectionObjects)
    {
        this.collectionObjects = collectionObjects;
    }

}

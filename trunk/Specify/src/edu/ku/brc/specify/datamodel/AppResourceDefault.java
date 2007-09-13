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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**

 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "appresourcedefault")
public class AppResourceDefault extends DataModelObjBase implements java.io.Serializable
{

    // Fields

     protected Integer          appResourceDefaultId;
     protected Collection       collection;
     protected CollectionType   collectionType;
     protected SpecifyUser      specifyUser;
     protected Set<AppResource> persistedAppResources;
     protected Set<ViewSetObj>  persistedViewSets;
     protected String           userType;
     protected String           disciplineType;
     
     // Transient Data Member
     protected Set<AppResource> appResources = null;
     protected Set<ViewSetObj>  viewSets;

    // Constructors

    /** default constructor */
    public AppResourceDefault() {
        //
    }

    /** constructor with id */
    public AppResourceDefault(Integer appResourceDefaultId) {
        this.appResourceDefaultId = appResourceDefaultId;
    }

    // Initializer
    @Override
    public void initialize()
    {
        super.init();
        appResourceDefaultId  = null;
        collection            = null;
        collectionType      = null;
        specifyUser           = null;
        
        persistedAppResources = new HashSet<AppResource>();
        persistedViewSets     = new HashSet<ViewSetObj>();

        userType              = null;
        disciplineType        = null;
        
        appResources          = null;//new HashSet<AppResource>();
        viewSets              = new HashSet<ViewSetObj>();

    }
    // End Initializer


    // Property accessors

    /**
     *
     */
    @Id
    @GeneratedValue
    @Column(name = "AppResourceDefaultID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getAppResourceDefaultId() {
        return this.appResourceDefaultId;
    }

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.appResourceDefaultId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return AppResourceDefault.class;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#isIndexable()
     */
    @Transient
    @Override
    public boolean isIndexable()
    {
        return false;
    }
    
    public void setAppResourceDefaultId(Integer appResourceDefaultId) {
        this.appResourceDefaultId = appResourceDefaultId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.AppResourceDefaultIFace#getCollection()
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CollectionID", unique = false, nullable = true, insertable = true, updatable = true)
    public Collection getCollection() {
        return this.collection;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.AppResourceDefaultIFace#setCollection(edu.ku.brc.specify.datamodel.Collection)
     */
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.AppResourceDefaultIFace#getCollectionType()
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CollectionTypeID", unique = false, nullable = true, insertable = true, updatable = true)
    public CollectionType getCollectionType() {
        return this.collectionType;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.AppResourceDefaultIFace#setCollectionType(edu.ku.brc.specify.datamodel.CollectionType)
     */
    public void setCollectionType(CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.AppResourceDefaultIFace#getSpecifyUser()
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SpecifyUserID", unique = false, nullable = true, insertable = true, updatable = true)
    public SpecifyUser getSpecifyUser() {
        return this.specifyUser;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.AppResourceDefaultIFace#setSpecifyUser(edu.ku.brc.specify.datamodel.SpecifyUser)
     */
    public void setSpecifyUser(SpecifyUser specifyUser) {
        this.specifyUser = specifyUser;
    }


    @ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "appResourceDefaults")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    public Set<AppResource> getPersistedAppResources() {
        return this.persistedAppResources;
    }


    public void setPersistedAppResources(Set<AppResource> persistedAppResources) {
        this.persistedAppResources = persistedAppResources;
    }


    @ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "appResourceDefaults")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK })
    public Set<ViewSetObj> getPersistedViewSets() {
        return this.persistedViewSets;
    }


    public void setPersistedViewSets(Set<ViewSetObj> persistedViewSets) {
        this.persistedViewSets = persistedViewSets;
    }

    @Column(name = "DisciplineType", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getDisciplineType()
    {
        return disciplineType;
    }

    public void setDisciplineType(String disciplineType)
    {
        this.disciplineType = disciplineType;
    }

    @Column(name = "UserType", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }
    
    @Transient
    public Set<AppResource> getAppResources()
    {
        if (appResources == null)
        {
            appResources = new HashSet<AppResource>();
            appResources.addAll(getPersistedAppResources());
        }
        return appResources;
    }

    public void setAppResources(Set<AppResource> appResources)
    {
        this.appResources = appResources;
    }

    @Transient
    public Set<ViewSetObj> getViewSets()
    {
        if (viewSets == null)
        {
            viewSets = new HashSet<ViewSetObj>();
            viewSets.addAll(getPersistedViewSets());
        }
        return viewSets;
    }

    public void setViewSets(Set<ViewSetObj> viewSets)
    {
        this.viewSets = viewSets;
    }

    @Transient
    public String getVerboseUniqueIdentifer()
    {
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(""+(collection != null ? collection.getCollectionName() : ""));
        strBuf.append(" "+(specifyUser != null ? specifyUser.getName() : ""));
        strBuf.append(" "+(collectionType != null ? collectionType.getName() : ""));
        strBuf.append(" "+(disciplineType != null ? disciplineType : ""));
        strBuf.append(" "+(userType != null ? userType : ""));
        return strBuf.toString(); 
    }

    @Transient
    public String getUniqueIdentifer()
    {
        return appResourceDefaultId == null ? getVerboseUniqueIdentifer() : appResourceDefaultId.toString();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
    @Override
    @Transient
    public int getTableId()
    {
        return getClassTableId();
    }
    
    /**
     * @return the Table ID for the class.
     */
    public static int getClassTableId()
    {
        return 85;
    }

}

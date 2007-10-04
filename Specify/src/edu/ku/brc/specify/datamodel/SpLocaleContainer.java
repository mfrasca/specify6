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
/**
 * 
 */
package edu.ku.brc.specify.datamodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

import edu.ku.brc.specify.tools.schemalocale.LocalizableContainerIFace;
import edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace;
import edu.ku.brc.specify.tools.schemalocale.LocalizableStrIFace;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Sep 25, 2007
 *
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "splocalecontainer")
@org.hibernate.annotations.Table(appliesTo="splocalecontainer", indexes =
    {   @Index (name="SpLocaleContainerNameIDX", columnNames={"Name"})
    })
public class SpLocaleContainer extends SpLocaleBase implements LocalizableContainerIFace
{
    private static final Logger log = Logger.getLogger(SpLocaleContainer.class);
            
    protected Integer                    spLocaleContainerId;
    protected Boolean                    isHidden;
    protected Set<SpLocaleContainerItem> items;
    
    protected Set<SpLocaleItemStr>       names;
    protected Set<SpLocaleItemStr>       descs;
    
    protected CollectionType             collectionType;
    
    // Transient
    protected Vector<LocalizableItemIFace> containerItems = null;
    
    /**
     * 
     */
    public SpLocaleContainer()
    {
        // no op
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#initialize()
     */
    @Override
    public void initialize()
    {
        super.initialize();
        
        spLocaleContainerId = null;
        isHidden            = false;
        items               = new HashSet<SpLocaleContainerItem>();
        
        names = new HashSet<SpLocaleItemStr>();
        descs = new HashSet<SpLocaleItemStr>();
        
        containerItems = null;
    }
    
    /**
     * @return the localeContainerId
     */
    @Id
    @GeneratedValue
    @Column(name = "SpLocaleContainerID", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getSpLocaleContainerId()
    {
        return spLocaleContainerId;
    }

    /**
     * @param localeContainerId the localeContainerId to set
     */
    public void setSpLocaleContainerId(Integer spLocaleContainerId)
    {
        this.spLocaleContainerId = spLocaleContainerId;
    }

    /**
     * @return the isHidden
     */
    @Column(name = "IsHidden", unique = false, nullable = false, insertable = true, updatable = true)
    public Boolean getIsHidden()
    {
        return isHidden;
    }

    /**
     * @param isHidden the isHidden to set
     */
    public void setIsHidden(Boolean isHidden)
    {
        this.isHidden = isHidden;
    }

    /**
     * @return the items
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "container")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<SpLocaleContainerItem> getItems()
    {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Set<SpLocaleContainerItem> items)
    {
        this.items = items;
    }
    

    /**
     * @return the descs
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "containerDesc")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<SpLocaleItemStr> getDescs()
    {
        return descs;
    }

    /**
     * @param descs the descs to set
     */
    public void setDescs(Set<SpLocaleItemStr> descs)
    {
        this.descs = descs;
    }

    /**
     * @return the names
     */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "containerName")
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<SpLocaleItemStr> getNames()
    {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(Set<SpLocaleItemStr> names)
    {
        this.names = names;
    }

    /**
     * @return the collectionType
     */
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CollectionTypeID", unique = false, nullable = false, insertable = true, updatable = true)
    public CollectionType getCollectionType()
    {
        return collectionType;
    }

    /**
     * @param collectionType the collectionType to set
     */
    public void setCollectionType(CollectionType collectionType)
    {
        this.collectionType = collectionType;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getDataClass()
     */
    @Override
    @Transient
    public Class<?> getDataClass()
    {
        return SpLocaleContainer.class;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.DataModelObjBase#getId()
     */
    @Override
    @Transient
    public Integer getId()
    {
        return spLocaleContainerId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getTableId()
     */
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
        return 503;
    }
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SpLocaleContainer obj)
    {
        return name.compareTo(obj.name);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return name;
    }

    /**
     * @param item
     * @param srcLocale
     * @param dstLocale
     */
    public static void copyLocaleXXX(LocalizableItemIFace item, final Locale srcLocale, final Locale dstLocale)
    {/*
        SpLocaleItemStr srcName = null;
        for (SpLocaleItemStr nm : item.getNames())
        {
            if (nm.isLocale(srcLocale))
            {
                srcName = nm;
                break;
            }
        }
        
        if (srcName != null)
        {
            SpLocaleItemStr name = new SpLocaleItemStr(srcName.getText(), dstLocale);
            item.getNames().add(name);
        }

        SpLocaleItemStr srcDesc = null;
        for (SpLocaleItemStr d : item.getDescs())
        {
            if (d.isLocale(srcLocale))
            {
                srcDesc = d;
                break;
            }
        }
        
        if (srcDesc != null)
        {
            SpLocaleItemStr desc = new SpLocaleItemStr(srcDesc.getText(), dstLocale);
            item.getDescs().add(desc);
        }  
        */     
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableContainerIFace#getContainerItems()
     */
    @Transient
    public Collection<LocalizableItemIFace> getContainerItems()
    {
        if (containerItems == null)
        {
            containerItems = new Vector<LocalizableItemIFace>(items);
        }
        return containerItems;
    }
    
    /**
     * 
     */
    public void clearCollectionItemsList()
    {
        containerItems = null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableContainerIFace#removeItem(edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace)
     */
    public void removeItem(LocalizableItemIFace item)
    {
        if (item != null)
        {
            if (item instanceof SpLocaleContainerItem)
            {
                SpLocaleContainerItem it = (SpLocaleContainerItem)item;
                items.remove(it);
                if (containerItems != null)
                {
                    containerItems.remove(it);
                }
            } else
            {
                log.error("Trying to remove an item that isn't of class SpLocaleContainerItem");
            }
        }        
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(LocalizableContainerIFace o)
    {
        return name.compareTo(o.getName());
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableContainerIFace#addItem(edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace)
     */
    public void addItem(LocalizableItemIFace item)
    {
        if (item != null && item instanceof SpLocaleContainerItem)
        {
            SpLocaleContainerItem it = (SpLocaleContainerItem)item;
            items.add(it);
            it.setContainer(this);
            if (containerItems != null)
            {
                containerItems.add(it);
            }
        } else
        {
            log.error("LocalizableItemIFace was null or not of Class SpLocaleContainerItem");
        }        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableContainerIFace#getItemByName(java.lang.String)
     */
    public LocalizableItemIFace getItemByName(String nameArg)
    {
        for (SpLocaleContainerItem item : items)
        {
            if (nameArg.equals(item.getName()))
            {
                return item;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace#fillDescs(java.util.List)
     */
    public void fillDescs(List<LocalizableStrIFace> descsArg)
    {
        descsArg.clear();
        descsArg.addAll(descs);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace#fillNames(java.util.List)
     */
    public void fillNames(List<LocalizableStrIFace> namesArg)
    {
        namesArg.clear();
        namesArg.addAll(names);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace#addDesc(edu.ku.brc.specify.tools.schemalocale.LocalizableStrIFace)
     */
    public void addDesc(LocalizableStrIFace str)
    {
        if (str != null && str instanceof SpLocaleItemStr)
        {
            descs.add((SpLocaleItemStr)str);
        } else
        {
            log.error("LocalizableStrIFace was null or not of Class SpLocaleItemStr");
        }
        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace#addName(edu.ku.brc.specify.tools.schemalocale.LocalizableStrIFace)
     */
    public void addName(LocalizableStrIFace str)
    {
        if (str != null && str instanceof SpLocaleItemStr)
        {
            names.add((SpLocaleItemStr)str);
        } else
        {
            log.error("LocalizableStrIFace was null or not of Class SpLocaleItemStr");
        }
        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace#removeDesc(edu.ku.brc.specify.tools.schemalocale.LocalizableStrIFace)
     */
    public void removeDesc(LocalizableStrIFace str)
    {
        if (str != null && str instanceof SpLocaleItemStr)
        {
            descs.remove(str);
        } else
        {
            log.error("LocalizableStrIFace was null or not of Class SpLocaleItemStr");
        }
        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tools.schemalocale.LocalizableItemIFace#removeName(edu.ku.brc.specify.tools.schemalocale.LocalizableStrIFace)
     */
    public void removeName(LocalizableStrIFace str)
    {
        if (str != null && str instanceof SpLocaleItemStr)
        {
            names.add((SpLocaleItemStr)str);
        } else
        {
            log.error("LocalizableStrIFace was null or not of Class SpLocaleItemStr");
        }
    }
    
    
}

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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "lithostrattreedef")
public class LithoStratTreeDef extends DataModelObjBase implements java.io.Serializable, TreeDefIface<LithoStrat, LithoStratTreeDef, LithoStratTreeDefItem>
{
	protected Integer                    lithoStratTreeDefId;
    protected String                     name;
    protected String                     remarks;
    protected Integer                    fullNameDirection;
    protected Set<CollectionType>        collectionTypes;
    protected Set<LithoStrat>            treeEntries;
    protected Set<LithoStratTreeDefItem> treeDefItems;

	// Constructors

	/** default constructor */
	public LithoStratTreeDef()
	{
		// do nothing
	}

	/** constructor with id */
	public LithoStratTreeDef(Integer geologicTimePeriodTreeDefId)
	{
		this.lithoStratTreeDefId = geologicTimePeriodTreeDefId;
	}

	// Initializer
    @Override
    public void initialize()
	{
        super.init();
		lithoStratTreeDefId = null;
		name = null;
		remarks = null;
        fullNameDirection = null;
		collectionTypes = new HashSet<CollectionType>();
		treeEntries = new HashSet<LithoStrat>();
		treeDefItems = new HashSet<LithoStratTreeDefItem>();
	}

	// End Initializer

	// Property accessors

	/**
	 * 
	 */
    @Id
    @GeneratedValue
    @Column(name = "LithoStratTreeDefID", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getLithoStratTreeDefId()
	{
		return this.lithoStratTreeDefId;
	}

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.lithoStratTreeDefId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return LithoStratTreeDef.class;
    }

	public void setLithoStratTreeDefId(Integer geologicTimePeriodTreeDefId)
	{
		this.lithoStratTreeDefId = geologicTimePeriodTreeDefId;
	}

	/**
	 * 
	 */
    @Column(name = "Name", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 */
    @Lob
    @Column(name = "Remarks", length = 4096)
	public String getRemarks()
	{
		return this.remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

    @Column(name = "FullNameDirection", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getFullNameDirection()
    {
        return fullNameDirection;
    }

    public void setFullNameDirection(Integer fullNameDirection)
    {
        this.fullNameDirection = fullNameDirection;
    }

    /**
	 * 
	 */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "lithoStratTreeDef")
	public Set<CollectionType> getCollectionTypes()
	{
		return this.collectionTypes;
	}

	public void setCollectionTypes(Set<CollectionType> collectionTypes)
	{
		this.collectionTypes = collectionTypes;
	}
    
    public void addCollectionType( CollectionType ct )
    {
        this.collectionTypes.add(ct);
        ct.setLithoStratTreeDef(this);
    }
    
    public void removeCollectionType( CollectionType ct )
    {
        this.collectionTypes.remove(ct);
        ct.setLithoStratTreeDef(null);
    }


	/**
	 * 
	 */
    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "definition")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.LOCK, CascadeType.MERGE })
	public Set<LithoStrat> getTreeEntries()
	{
		return this.treeEntries;
	}

	public void setTreeEntries(Set<LithoStrat> treeEntries)
	{
		this.treeEntries = treeEntries;
	}

	/**
	 * 
	 */
    @OneToMany(cascade = {}, fetch = FetchType.EAGER, mappedBy = "treeDef")
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.LOCK })
	public Set<LithoStratTreeDefItem> getTreeDefItems()
	{
		return this.treeDefItems;
	}

	public void setTreeDefItems(Set<LithoStratTreeDefItem> treeDefItems)
	{
		this.treeDefItems = treeDefItems;
	}

	//
	// Methods added to implement TreeDefinitionIface
	//

    @Transient
	public Integer getTreeDefId()
	{
		return getLithoStratTreeDefId();
	}

	public void setTreeDefId(Integer id)
	{
		setLithoStratTreeDefId(id);
	}

	public void addTreeEntry(LithoStrat taxon)
	{
		treeEntries.add(taxon);
		taxon.setDefinition(this);
	}

	public void removeTreeEntry(LithoStrat taxon)
	{
		treeEntries.remove(taxon);
		taxon.setDefinition(null);
	}

	public void addTreeDefItem(LithoStratTreeDefItem item)
	{
		treeDefItems.add(item);
		item.setTreeDef(this);
	}

	public void removeTreeDefItem(LithoStratTreeDefItem item)
	{
		treeDefItems.remove(item);
		item.setTreeDef(null);
	}

	// methods to "complete" the implementation of TreeDefinitionIface
    @Transient
	public Class<LithoStrat> getNodeClass()
	{
		return LithoStrat.class;
	}
	
	/**
	 * Returns the <code>TreeDefinitionItemIface</code> object associated with the called
	 * <code>TreeDefinitionIface</code> object and having the given rank.
	 * 
	 * @param rank the rank of the returned def item
	 * @return the definition item
	 */
    public LithoStratTreeDefItem getDefItemByRank(Integer rank)
	{
		for( LithoStratTreeDefItem item: treeDefItems )
		{
			if( item.getRankId().equals(rank) )
			{
				return item;
			}
		}
		return null;
	}
	
//	/**
//	 * Returns the <code>TreeDefinitionItemIface</code> object associated with the called
//	 * <code>TreeDefinitionIface</code> object and having the given name.
//	 * 
//	 * @param name the name of the returned def item
//	 * @return the definition item
//	 */
//	public LithoStratTreeDefItem getDefItemByName(String defItemName)
//	{
//		for( LithoStratTreeDefItem item: treeDefItems )
//		{
//			if( item.getName().equals(defItemName) )
//			{
//				return item;
//			}
//		}
//		return null;
//	}
	
	/**
	 * Determines if the child node can be reparented to newParent while not
	 * violating any of the business rules.  Currently, the only rule on
	 * reparenting is that the new parent must be of rank equal to or less than
	 * the next higher enforced rank in the child's tree definition.
	 * 
	 * @param child the node to be reparented
	 * @param newParent the prospective new parent node
	 * 
	 * @return <code>true</code> if the action will not violate any reparenting rules, false otherwise
	 */
	public boolean canChildBeReparentedToNode( LithoStrat child, LithoStrat newParent )
	{
		if( newParent.getRankId().intValue() >= child.getRankId().intValue() )
		{
			// a node cannot have a parent that is a peer or of lower rank (larger rank id)
			return false;
		}
		
		Integer nextEnforcedRank = getRankOfNextHighestEnforcedLevel(child);
		if( nextEnforcedRank == null )
		{
			// no higher ranks are being enforced
			// the node can be reparented all the way up to the root
			return true;
		}
		
		if( nextEnforcedRank.intValue() <= newParent.getRankId().intValue() )
		{
			// the next enforced rank is equal to or above the new parent rank
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the next highest rank in the tree that is enforced by the
	 * tree definition.
	 * 
	 * @param node the node to find the next highest enforced rank for
	 * @return the next highest rank
	 */
	public Integer getRankOfNextHighestEnforcedLevel( LithoStrat node )
	{
		LithoStratTreeDefItem defItem = node.getDefinitionItem();
		while( defItem.getParent() != null )
		{
			defItem = defItem.getParent();
			if( defItem.getIsEnforced() != null && defItem.getIsEnforced().booleanValue() == true )
			{
				return defItem.getRankId();
			}
		}
		
		return null;
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
        return 47;
    }

    @Override
    public String toString()
    {
        return getIdentityTitle();
    }
}

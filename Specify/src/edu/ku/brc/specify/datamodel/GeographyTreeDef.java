package edu.ku.brc.specify.datamodel;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class GeographyTreeDef implements java.io.Serializable, TreeDefIface<Geography, GeographyTreeDef, GeographyTreeDefItem>
{
	protected Integer				taxonTreeDefId;
	protected String				name;
	protected String				remarks;
	protected Set<CollectionObjDef>		collObjDefs;
	protected Set<Geography>			treeEntries;
	protected Set<GeographyTreeDefItem>	treeDefItems;

	// Constructors

	/** default constructor */
	public GeographyTreeDef()
	{
		// do nothing
	}

	/** constructor with id */
	public GeographyTreeDef(Integer taxonTreeDefId)
	{
		this.taxonTreeDefId = taxonTreeDefId;
	}

	// Initializer
	public void initialize()
	{
		taxonTreeDefId = null;
		name = null;
		remarks = null;
		collObjDefs = new HashSet<CollectionObjDef>();
		treeEntries = new HashSet<Geography>();
		treeDefItems = new HashSet<GeographyTreeDefItem>();
	}

	// End Initializer

	// Property accessors

	/**
	 * 
	 */
	public Integer getGeographyTreeDefId()
	{
		return this.taxonTreeDefId;
	}

	public void setGeographyTreeDefId(Integer taxonTreeDefId)
	{
		this.taxonTreeDefId = taxonTreeDefId;
	}

	/**
	 * 
	 */
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
	public String getRemarks()
	{
		return this.remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	/**
	 * 
	 */
	public Set<CollectionObjDef> getCollObjDefs()
	{
		return this.collObjDefs;
	}

	public void setCollObjDefs(Set<CollectionObjDef> collObjDefs)
	{
		this.collObjDefs = collObjDefs;
	}

	/**
	 * 
	 */
	public Set<Geography> getTreeEntries()
	{
		return this.treeEntries;
	}

	public void setTreeEntries(Set<Geography> treeEntries)
	{
		this.treeEntries = treeEntries;
	}

	/**
	 * 
	 */
	public Set<GeographyTreeDefItem> getTreeDefItems()
	{
		return this.treeDefItems;
	}

	public void setTreeDefItems(Set<GeographyTreeDefItem> treeDefItems)
	{
		this.treeDefItems = treeDefItems;
	}

	//
	// Methods added to implement TreeDefinitionIface
	//

	public Integer getTreeDefId()
	{
		return getGeographyTreeDefId();
	}

	public void setTreeDefId(Integer id)
	{
		setGeographyTreeDefId(id);
	}

	public void addTreeEntry(Geography taxon)
	{
		treeEntries.add(taxon);
		taxon.setDefinition(this);
	}

	public void removeTreeEntry(Geography taxon)
	{
		treeEntries.remove(taxon);
		taxon.setDefinition(null);
	}

	public void addTreeDefItem(GeographyTreeDefItem item)
	{
		treeDefItems.add(item);
		item.setTreeDef(this);
	}

	public void removeTreeDefItem(GeographyTreeDefItem item)
	{
		treeDefItems.remove(item);
		item.setTreeDef(null);
	}

	// methods to "complete" the implementation of TreeDefinitionIface
	public Class<Geography> getNodeClass()
	{
		return Geography.class;
	}
	
	/**
	 * Returns the <code>TreeDefinitionItemIface</code> object associated with the called
	 * <code>TreeDefinitionIface</code> object and having the given rank.
	 * 
	 * @param rank the rank of the returned def item
	 * @return the definition item
	 */
    public GeographyTreeDefItem getDefItemByRank(Integer rank)
	{
		for( GeographyTreeDefItem item: treeDefItems )
		{
			if( item.getRankId().equals(rank) )
			{
				return item;
			}
		}
		return null;
	}
	
	/**
	 * Returns the <code>TreeDefinitionItemIface</code> object associated with the called
	 * <code>TreeDefinitionIface</code> object and having the given name.
	 * 
	 * @param name the name of the returned def item
	 * @return the definition item
	 */
	public GeographyTreeDefItem getDefItemByName(String defItemName)
	{
		for( GeographyTreeDefItem item: treeDefItems )
		{
			if( item.getName().equals(defItemName) )
			{
				return item;
			}
		}
		return null;
	}
	
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
	public boolean canChildBeReparentedToNode( Geography child, Geography newParent )
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
	public Integer getRankOfNextHighestEnforcedLevel( Geography node )
	{
		GeographyTreeDefItem defItem = node.getDefinitionItem();
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
}
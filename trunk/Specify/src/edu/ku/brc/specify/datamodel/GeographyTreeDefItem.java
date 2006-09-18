package edu.ku.brc.specify.datamodel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class GeographyTreeDefItem implements Serializable, TreeDefItemIface<Geography,GeographyTreeDef,GeographyTreeDefItem>
{

	// Fields    

	protected Long			    	taxonTreeDefItemId;
	protected String				name;
	protected String				remarks;
	protected Integer				rankId;
	protected Boolean				isEnforced;
	protected Boolean				isInFullName;
	protected GeographyTreeDef			treeDef;
	protected GeographyTreeDefItem		parent;
	protected Set<Geography>			treeEntries;
	protected Set<GeographyTreeDefItem>	children;

	// Constructors

	/** default constructor */
	public GeographyTreeDefItem()
	{
		// do nothing
	}

	/** constructor with id */
	public GeographyTreeDefItem(Long taxonTreeDefItemId)
	{
		this.taxonTreeDefItemId = taxonTreeDefItemId;
	}

	// Initializer
	public void initialize()
	{
		taxonTreeDefItemId = null;
		name = null;
		remarks = null;
		rankId = null;
		isEnforced = null;
		isInFullName = null;
		treeDef = null;
		treeEntries = new HashSet<Geography>();
		parent = null;
		children = new HashSet<GeographyTreeDefItem>();
	}

	// End Initializer

	// Property accessors

	/**
	 * 
	 */
	public Long getGeographyTreeDefItemId()
	{
		return this.taxonTreeDefItemId;
	}

	public void setGeographyTreeDefItemId(Long taxonTreeDefItemId)
	{
		this.taxonTreeDefItemId = taxonTreeDefItemId;
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
	public Integer getRankId()
	{
		return this.rankId;
	}

	public void setRankId(Integer rankId)
	{
		this.rankId = rankId;
	}

	/**
	 * 
	 */
	public Boolean getIsEnforced()
	{
		return this.isEnforced;
	}

	public void setIsEnforced(Boolean isEnforced)
	{
		this.isEnforced = isEnforced;
	}

	public Boolean getIsInFullName()
	{
		return isInFullName;
	}

	public void setIsInFullName(Boolean isInFullName)
	{
		this.isInFullName = isInFullName;
	}

	/**
	 * 
	 */
	public GeographyTreeDef getTreeDef()
	{
		return this.treeDef;
	}

	public void setTreeDef(GeographyTreeDef treeDef)
	{
		this.treeDef = treeDef;
	}

	/**
	 * 
	 */
	public GeographyTreeDefItem getParent()
	{
		return this.parent;
	}

	public void setParent(GeographyTreeDefItem parent)
	{
		this.parent = parent;
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
	public Set<GeographyTreeDefItem> getChildren()
	{
		return this.children;
	}

	public void setChildren(Set<GeographyTreeDefItem> children)
	{
		this.children = children;
	}

	// Code added to implement TreeDefinitionItemIface

	public Long getTreeDefItemId()
	{
		return getGeographyTreeDefItemId();
	}

	public void setTreeDefItemId(Long id)
	{
		setGeographyTreeDefItemId(id);
	}

	public void setChild(GeographyTreeDefItem child)
	{
		if( child==null )
		{
			children = new HashSet<GeographyTreeDefItem>();
			return;
		}

		children = new HashSet<GeographyTreeDefItem>();
		children.add(child);
	}
	
	public GeographyTreeDefItem getChild()
	{
		if(children.isEmpty())
		{
			return null;
		}
		return children.iterator().next();
	}

	public void addTreeEntry(Geography entry)
	{
		treeEntries.add(entry);
		entry.setDefinitionItem(this);
	}

	public void removeTreeEntry(Geography entry)
	{
		treeEntries.remove(entry);
		entry.setDefinitionItem(null);
	}

	public void removeChild(GeographyTreeDefItem child)
	{
		children.remove(child);
		child.setParent(null);
	}
	
	public boolean canBeDeleted()
	{
		if(treeEntries.isEmpty())
		{
			return true;
		}
		return false;
	}
}

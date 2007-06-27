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

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import edu.ku.brc.specify.treeutils.GeologicTimePeriodComparator;

@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "geologictimeperiod")
public class GeologicTimePeriod extends DataModelObjBase implements java.io.Serializable, Treeable<GeologicTimePeriod,GeologicTimePeriodTreeDef,GeologicTimePeriodTreeDefItem>{

    /**
     * A <code>Logger</code> object used for all log messages eminating from
     * this class.
     */
    protected static final Logger log = Logger.getLogger(Geography.class);

	protected Long						    geologicTimePeriodId;
	protected Integer						rankId;
	protected String						name;
	protected String						fullName;
	protected String						remarks;
	protected Integer						nodeNumber;
	protected Integer						highestChildNodeNumber;
	protected String						standard;
	protected Float							startPeriod;
	protected Float							startUncertainty;
	protected Float							endPeriod;
	protected Float							endUncertainty;
	private GeologicTimePeriodTreeDef		definition;
	private GeologicTimePeriodTreeDefItem	definitionItem;
	private GeologicTimePeriod				parent;
	protected Set<GeologicTimePeriod>		children;
	protected Set<Stratigraphy>				stratigraphies;

	// Constructors

	/** default constructor */
	public GeologicTimePeriod()
	{
		// do nothing
	}

	/** constructor with id */
	public GeologicTimePeriod(Long geologicTimePeriodId)
	{
		this.geologicTimePeriodId = geologicTimePeriodId;
	}

	// Initializer
	@Override
    public void initialize()
	{
        super.init();
		geologicTimePeriodId = null;
		rankId = null;
		name = null;
		remarks = null;
		nodeNumber = null;
		highestChildNodeNumber = null;
		standard = null;
		startPeriod = null;
		startUncertainty = null;
		endPeriod = null;
		endUncertainty = null;
		definition = null;
		definitionItem = null;
		parent = null;
		children = new HashSet<GeologicTimePeriod>();
		stratigraphies = new HashSet<Stratigraphy>();
	}

	// End Initializer

	// Property accessors

	/**
	 * 
	 */
    @Id
    @GeneratedValue
    @Column(name = "GeologicTimePeriodID", unique = false, nullable = false, insertable = true, updatable = true)
	public Long getGeologicTimePeriodId()
	{
		return this.geologicTimePeriodId;
	}

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Long getId()
    {
        return this.geologicTimePeriodId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return GeologicTimePeriod.class;
    }

	public void setGeologicTimePeriodId(Long geologicTimePeriodId)
	{
		this.geologicTimePeriodId = geologicTimePeriodId;
	}

	/**
	 * 
	 */
    @Column(name = "RankID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
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
	 * @return the fullName
	 */
    @Column(name = "FullName", unique = false, nullable = true, insertable = true, updatable = true)
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * 
	 */
    @Column(name = "NodeNumber", unique = false, nullable = true, insertable = true, updatable = false, length = 10)
	public Integer getNodeNumber()
	{
		return this.nodeNumber;
	}

	public void setNodeNumber(Integer nodeNumber)
	{
		this.nodeNumber = nodeNumber;
	}

	/**
	 * 
	 */
    @Column(name = "HighestChildNodeNumber", unique = false, nullable = true, insertable = true, updatable = false, length = 10)
	public Integer getHighestChildNodeNumber()
	{
		return this.highestChildNodeNumber;
	}

	public void setHighestChildNodeNumber(Integer highestChildNodeNumber)
	{
		this.highestChildNodeNumber = highestChildNodeNumber;
	}

	/**
	 * 
	 */
    @Column(name = "Standard", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public String getStandard()
	{
		return this.standard;
	}

	public void setStandard(String standard)
	{
		this.standard = standard;
	}

    @Column(name = "EndPeriod", unique = false, nullable = true, insertable = true, updatable = true)
	public Float getEndPeriod()
	{
		return endPeriod;
	}

	public void setEndPeriod(Float end)
	{
		this.endPeriod = end;
	}

    @Column(name = "EndUncertainty", unique = false, nullable = true, insertable = true, updatable = true)
	public Float getEndUncertainty()
	{
		return endUncertainty;
	}

	public void setEndUncertainty(Float endUncertainty)
	{
		this.endUncertainty = endUncertainty;
	}

    @Column(name = "StartPeriod", unique = false, nullable = true, insertable = true, updatable = true)
	public Float getStartPeriod()
	{
		return startPeriod;
	}

	public void setStartPeriod(Float start)
	{
		this.startPeriod = start;
	}

    @Column(name = "StartUncertainty", unique = false, nullable = true, insertable = true, updatable = true)
	public Float getStartUncertainty()
	{
		return startUncertainty;
	}

	public void setStartUncertainty(Float startUncertainty)
	{
		this.startUncertainty = startUncertainty;
	}

	/**
	 * 
	 */
    @Lob
    @Column(name="Remarks", unique=false, nullable=true, updatable=true, insertable=true)
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
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @Cascade( { CascadeType.LOCK })
    @JoinColumn(name = "GeologicTimePeriodTreeDefID", unique = false, nullable = false, insertable = true, updatable = true)
	public GeologicTimePeriodTreeDef getDefinition()
	{
		return this.definition;
	}

	public void setDefinition(GeologicTimePeriodTreeDef definition)
	{
		this.definition = definition;
	}

	/**
	 * 
	 */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.LOCK })
    @JoinColumn(name = "GeologicTimePeriodTreeDefItemID", unique = false, nullable = false, insertable = true, updatable = true)
	public GeologicTimePeriodTreeDefItem getDefinitionItem()
	{
		return this.definitionItem;
	}

	public void setDefinitionItem(GeologicTimePeriodTreeDefItem definitionItem)
	{
        this.definitionItem = definitionItem;
        if (definitionItem!=null && definitionItem.getRankId()!=null)
        {
            this.rankId = this.definitionItem.getRankId();
        }
	}

	/**
	 * 
	 */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.LOCK })
    @JoinColumn(name = "ParentID", unique = false, nullable = true, insertable = true, updatable = true)
	public GeologicTimePeriod getParent()
	{
		return this.parent;
	}

	public void setParent(GeologicTimePeriod parent)
	{
		this.parent = parent;
	}

	/**
	 * 
	 */
    @OneToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<GeologicTimePeriod> getChildren()
	{
		return this.children;
	}

	public void setChildren(Set<GeologicTimePeriod> children)
	{
		this.children = children;
	}

    @OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "geologicTimePeriod")
	public Set<Stratigraphy> getStratigraphies()
	{
		return stratigraphies;
	}

	public void setStratigraphies(Set<Stratigraphy> stratigraphies)
	{
		this.stratigraphies = stratigraphies;
	}

	/* Code added in order to implement Treeable */

    @Transient
	public Long getTreeId()
	{
		return getGeologicTimePeriodId();
	}

	public void setTreeId(Long id)
	{
		setGeologicTimePeriodId(id);
	}

	public void addChild(GeologicTimePeriod child)
	{
		GeologicTimePeriod oldParent = child.getParent();
		if( oldParent!=null )
		{
			oldParent.removeChild(child);
		}

		children.add(child);
		child.setParent(this);
	}

	public void removeChild(GeologicTimePeriod child)
	{
		children.remove(child);
		child.setParent(null);
	}

	public void addStratigraphy(Stratigraphy strat)
	{
		GeologicTimePeriod oldGTP = strat.getGeologicTimePeriod();
		if( oldGTP!=null )
		{
			oldGTP.removeStratigraphy(strat);
		}

		stratigraphies.add(strat);
		strat.setGeologicTimePeriod(this);
	}

	public void removeStratigraphy(Stratigraphy strat)
	{
		stratigraphies.remove(strat);
		strat.setGeologicTimePeriod(null);
	}

    @Override
    public String toString()
    {
        return (fullName!=null) ? fullName : super.toString();
    }

	// methods to complete implementation of AbstractTreeable

    @Transient
    public int getFullNameDirection()
    {
        return definition.getFullNameDirection();
    }

    @Transient
    public String getFullNameSeparator()
    {
        return definitionItem.getFullNameSeparator();
    }

	/**
	 * Generates the 'full name' of a node using the <code>IsInFullName</code> field from the tree
	 * definition items and following the parent pointer until we hit the root node.  Also used
	 * in the process is a "direction indicator" for the tree determining whether the name
	 * should start with the higher nodes and work down to the given node or vice versa.
	 * 
	 * @param node the node to get the full name for
	 * @return the full name
	 */
    public String fixFullName()
    {
        Vector<GeologicTimePeriod> parts = new Vector<GeologicTimePeriod>();
        parts.add(this);
        GeologicTimePeriod node = getParent();
        while( node != null )
        {
            Boolean include = node.getDefinitionItem().getIsInFullName();
            if( include != null && include.booleanValue() == true )
            {
                parts.add(node);
            }
            
            node = node.getParent();
        }
        int direction = getFullNameDirection();
        
        StringBuilder fullNameBuilder = new StringBuilder(parts.size() * 10);
        
        switch( direction )
        {
            case FORWARD:
            {
                for( int j = parts.size()-1; j > -1; --j )
                {
                    GeologicTimePeriod part = parts.get(j);
                    String before = part.getDefinitionItem().getTextBefore();
                    String after = part.getDefinitionItem().getTextAfter();

                    if (before!=null)
                    {
                        fullNameBuilder.append(part.getDefinitionItem().getTextBefore());
                    }
                    fullNameBuilder.append(part.getName());
                    if (after!=null)
                    {
                        fullNameBuilder.append(part.getDefinitionItem().getTextAfter());
                    }
                    if(j!=parts.size()-1)
                    {
                        fullNameBuilder.append(parts.get(j).getFullNameSeparator());
                    }
                }
                break;
            }
            case REVERSE:
            {
                for( int j = 0; j < parts.size(); ++j )
                {
                    GeologicTimePeriod part = parts.get(j);
                    String before = part.getDefinitionItem().getTextBefore();
                    String after = part.getDefinitionItem().getTextAfter();

                    if (before!=null)
                    {
                        fullNameBuilder.append(part.getDefinitionItem().getTextBefore());
                    }
                    fullNameBuilder.append(part.getName());
                    if (after!=null)
                    {
                        fullNameBuilder.append(part.getDefinitionItem().getTextAfter());
                    }
                    if(j!=parts.size()-1)
                    {
                        fullNameBuilder.append(parts.get(j).getFullNameSeparator());
                    }
                }
                break;
            }
            default:
            {
                log.error("Invalid tree walk direction (for creating fullname field) found in tree definition");
                return null;
            }
        }
        
        return fullNameBuilder.toString().trim();
    }
	
	/**
	 * Returns the number of proper descendants for node.
	 * 
	 * @param node the node to count descendants for
	 * @return the number of proper descendants
	 */
    @Transient
	public int getDescendantCount()
	{
		int totalDescendants = 0;
		for( GeologicTimePeriod child: getChildren() )
		{
			totalDescendants += 1 + child.getDescendantCount();
		}
		return totalDescendants;
	}
	
	/**
	 * Determines if children are allowed for the given node.
	 * 
	 * @param item the node to examine
	 * @return <code>true</code> if children are allowed as defined by the node's tree definition, false otherwise
	 */
	public boolean childrenAllowed()
	{
		if( definitionItem == null || definitionItem.getChild() == null )
		{
			return false;
		}
		return true;
	}

	/**
	 * Returns a <code>List</code> of all descendants of the called <code>node</code>.
	 * 
	 * @return all descendants of <code>node</code>
	 */
    @Transient
	public List<GeologicTimePeriod> getAllDescendants()
	{
		Vector<GeologicTimePeriod> descendants = new Vector<GeologicTimePeriod>();
		for( GeologicTimePeriod child: getChildren() )
		{
			descendants.add(child);
			descendants.addAll(child.getAllDescendants());
		}
		return descendants;
	}
	
    @Transient
	public List<GeologicTimePeriod> getAllAncestors()
	{
		Vector<GeologicTimePeriod> ancestors = new Vector<GeologicTimePeriod>();
		GeologicTimePeriod parentNode = parent;
		while(parentNode != null)
		{
			ancestors.add(0,parentNode);
			parentNode = parentNode.getParent();
		}
		
		return ancestors;
	}

	/**
	 * Fixes the fullname for the given node and all of its descendants.
	 */
	public void fixFullNameForAllDescendants()
	{
        setFullName(fixFullName());
		for( GeologicTimePeriod child: getChildren() )
		{
			child.fixFullNameForAllDescendants();
		}
	}
	
	/**
	 * Updates the created and modified timestamps to now.  Also
	 * updates the <code>lastEditedBy</code> field to the current
	 * value of the <code>user.name</code> system property.
	 */
	public void setTimestampsToNow()
	{
		Date now = new Date();
		setTimestampCreated(now);
		setTimestampModified(now);

		//TODO: fix this somehow
		String user = System.getProperty("user.name");
		setLastEditedBy(user);
	}
	
	/**
	 * Updates the modified timestamp to now.  Also updates the
	 * <code>lastEditedBy</code> field to the current value
	 * of the <code>user.name</code> system property.
	 */
	public void updateModifiedTimeAndUser()
	{
		Date now = new Date();
		setTimestampModified(now);
		
		//TODO: fix this somehow
		String user = System.getProperty("user.name");
		setLastEditedBy(user);
	}

	public boolean isDescendantOf(GeologicTimePeriod node)
	{
		if( node==null )
		{
			throw new NullPointerException();
		}
		
		GeologicTimePeriod i = getParent();
		while( i != null )
		{
			if( i.getId() == getId() )
			{
				return true;
			}
			
			i = i.getParent();
		}
		return false;
	}
	
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.Treeable#getComparator()
     */
    @Transient
	public Comparator<? super GeologicTimePeriod> getComparator()
	{
		return new GeologicTimePeriodComparator();
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
        return 46;
    }

}

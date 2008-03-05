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
import org.hibernate.annotations.Index;

import edu.ku.brc.specify.treeutils.TreeOrderSiblingComparator;


@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "lithostrat")
@org.hibernate.annotations.Table(appliesTo="lithostrat", indexes =
    {   @Index (name="LithoNameIDX", columnNames={"Name"}),
        @Index (name="LithoFullNameIDX", columnNames={"FullName"}),
        @Index (name="LithoGuidIDX", columnNames={"GUID"})
    })
public class LithoStrat extends DataModelObjBase implements java.io.Serializable, Treeable<LithoStrat,LithoStratTreeDef,LithoStratTreeDefItem>
{

    /**
     * A <code>Logger</code> object used for all log messages eminating from
     * this class.
     */
    protected static final Logger log = Logger.getLogger(LithoStrat.class);

	protected Integer					    lithoStratId;
	protected Integer						rankId;
	protected String						name;
	protected String						fullName;
	protected String						remarks;
	protected String                        guid;
	protected Integer						nodeNumber;
	protected Integer						highestChildNodeNumber;
	private LithoStratTreeDef		        definition;
	private LithoStratTreeDefItem	        definitionItem;
	private LithoStrat				        parent;
	protected Set<LithoStrat>		        children;
    
	protected Set<PaleoContext>	            paleoContexts;

    // for synonym support
    protected Boolean               isAccepted;
    protected LithoStrat            acceptedLithoStrat;
    protected Set<LithoStrat>       acceptedChildren;


	// Constructors

	/** default constructor */
	public LithoStrat()
	{
		// do nothing
	}

	/** constructor with id */
	public LithoStrat(Integer lithoStratId)
	{
		this.lithoStratId = lithoStratId;
	}

	// Initializer
	@Override
    public void initialize()
	{
        super.init();
		lithoStratId = null;
		rankId = null;
		name = null;
        fullName = null;
		remarks = null;
		guid = null;
		nodeNumber = null;
		highestChildNodeNumber = null;
		definition = null;
		definitionItem = null;
		parent = null;
		children = new HashSet<LithoStrat>();
        paleoContexts = new HashSet<PaleoContext>();
	}

	// End Initializer

	// Property accessors

	/**
	 * 
	 */
    @Id
    @GeneratedValue
    @Column(name = "LithoStratID", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getLithoStratId()
	{
		return this.lithoStratId;
	}

    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    @Override
    public Integer getId()
    {
        return this.lithoStratId;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.FormDataObjIFace#getDataClass()
     */
    @Transient
    @Override
    public Class<?> getDataClass()
    {
        return LithoStrat.class;
    }

	public void setLithoStratId(Integer lithoStratId)
	{
		this.lithoStratId = lithoStratId;
	}

	/**
	 * 
	 */
    @Column(name = "RankID", nullable=false)
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
    @Column(name = "Name", nullable=false, length = 64)
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
    @Column(name = "FullName", length = 255)
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
    @Column(name = "GUID", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
	
	/**
	 * 
	 */
    @Column(name = "NodeNumber", unique = false, nullable = true, insertable = true, updatable = true)
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
    @Column(name = "HighestChildNodeNumber", unique = false, nullable = true, insertable = true, updatable = true)
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
    
    @Column(name="IsAccepted", unique=false, nullable=true, insertable=true, updatable=true)
    public Boolean getIsAccepted()
    {
        return this.isAccepted;
    }

    public void setIsAccepted(Boolean accepted)
    {
        this.isAccepted = accepted;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "acceptedLithoStrat")
    @Cascade( {CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.LOCK} )
    public Set<LithoStrat> getAcceptedChildren()
    {
        return this.acceptedChildren;
    }

    public void setAcceptedChildren(Set<LithoStrat> acceptedChildren)
    {
        this.acceptedChildren = acceptedChildren;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "AcceptedID")
    public LithoStrat getAcceptedLithoStrat()
    {
        return this.acceptedLithoStrat;
    }

    public void setAcceptedLithoStrat(LithoStrat acceptedLithoStrat)
    {
        this.acceptedLithoStrat = acceptedLithoStrat;
    }
    
    @Transient
    public LithoStrat getAcceptedParent()
    {
        return getAcceptedLithoStrat();
    }
    
    public void setAcceptedParent(LithoStrat acceptedParent)
    {
        setAcceptedLithoStrat(acceptedParent);
    }

	/**
	 * 
	 */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "LithoStratTreeDefID", unique = false, nullable = false, insertable = true, updatable = true)
	public LithoStratTreeDef getDefinition()
	{
		return this.definition;
	}

	public void setDefinition(LithoStratTreeDef definition)
	{
		this.definition = definition;
	}

	/**
	 * 
	 */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "LithoStratTreeDefItemID", unique = false, nullable = false, insertable = true, updatable = true)
	public LithoStratTreeDefItem getDefinitionItem()
	{
		return this.definitionItem;
	}

	public void setDefinitionItem(LithoStratTreeDefItem definitionItem)
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
    @JoinColumn(name = "ParentID", unique = false, nullable = true, insertable = true, updatable = true)
	public LithoStrat getParent()
	{
		return this.parent;
	}

	public void setParent(LithoStrat parent)
	{
		this.parent = parent;
	}

    @OneToMany(cascade = {javax.persistence.CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<LithoStrat> getChildren()
	{
		return this.children;
	}

	public void setChildren(Set<LithoStrat> children)
	{
		this.children = children;
	}

    /**
     * @return the paleoContexts
     */
    @OneToMany(mappedBy = "lithoStrat")
    @Cascade( {CascadeType.ALL} )
    public Set<PaleoContext> getPaleoContexts()
    {
        return paleoContexts;
    }

    /**
     * @param paleoContexts the paleoContexts to set
     */
    public void setPaleoContexts(Set<PaleoContext> paleoContexts)
    {
        this.paleoContexts = paleoContexts;
    }

	/* Code added in order to implement Treeable */

    @Transient
	public Integer getTreeId()
	{
		return getLithoStratId();
	}

	public void setTreeId(Integer id)
	{
		setLithoStratId(id);
	}

	public void addChild(LithoStrat child)
	{
		LithoStrat oldParent = child.getParent();
		if( oldParent!=null )
		{
			oldParent.removeChild(child);
		}

		children.add(child);
		child.setParent(this);
	}

	public void removeChild(LithoStrat child)
	{
		children.remove(child);
		child.setParent(null);
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
        Vector<LithoStrat> parts = new Vector<LithoStrat>();
        parts.add(this);
        LithoStrat node = getParent();
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
                    LithoStrat part = parts.get(j);
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
                    LithoStrat part = parts.get(j);
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
		for( LithoStrat child: getChildren() )
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
	public List<LithoStrat> getAllDescendants()
	{
		Vector<LithoStrat> descendants = new Vector<LithoStrat>();
		for( LithoStrat child: getChildren() )
		{
			descendants.add(child);
			descendants.addAll(child.getAllDescendants());
		}
		return descendants;
	}
	
    @Transient
	public List<LithoStrat> getAllAncestors()
	{
		Vector<LithoStrat> ancestors = new Vector<LithoStrat>();
		LithoStrat parentNode = parent;
		while(parentNode != null)
		{
			ancestors.add(0,parentNode);
			parentNode = parentNode.getParent();
		}
		
		return ancestors;
	}

	public boolean isDescendantOf(LithoStrat node)
	{
		if( node==null )
		{
			throw new NullPointerException();
		}
		
		LithoStrat i = getParent();
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
    
    @Transient
    public Comparator<? super LithoStrat> getComparator()
    {
        return new TreeOrderSiblingComparator();
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
        return 100;
    }

}

package edu.ku.brc.specify.datamodel;

import java.util.Date;
import java.util.List;
import java.util.Set;

import edu.ku.brc.util.Nameable;
import edu.ku.brc.util.Rankable;

/**
 * Describes any class where a collection of its objects can be modeled as
 * a tree.  Each instance of the implementing class represents a single node
 * in a tree.  Database tables can contain multiple trees simultaneously as
 * long as each node in a given tree has a common identifier, the tree definition.
 * Each node in the tree must have a unique ID, which is the primary key of
 * the corresponding database table.  Each node must also be numbered (the
 * node number) using a depth-first traversal of the tree.  The highest child
 * node number field contains the largest node number in the tree that is a
 * descendant of the given node.  The rank id represents the node's depth in
 * the tree.  Possible depths are defined in the tree definition.
 * 
 * A few of the methods defined in this interface are expected, at times,
 * to throw IllegalArgumentException.  This occurs when a setter
 * method is called on a Treeable object, but the passed in argument
 * represents a tree definition or tree definition item of another type of 
 * Treeable object.  For example, if setTreeDef(TreeDefinitionIface)
 * is called on a Taxon object, but the argument given is an instance of
 * GeographyTreeDef, an IllegalArgumentException will be thrown.
 *
 * @code_status Unknown (auto-generated)
 * 
 * @author jstewart
 */
public interface Treeable extends Rankable, Nameable
{
	public void initialize();
	
	/**
	 * @return the ID (primary key) of this node
	 */
	public Integer getTreeId();
	
	/**
	 * Sets the ID of this node
	 * 
	 * @param id the new ID value
	 */
	public void setTreeId(Integer id);
	
	/**
	 * Returns the parent node object.  If called on the root node of
	 * the tree, returns null.
	 * 
	 * @return the parent node object
	 */
	public Treeable getParentNode();
	
	/**
	 * Re-parents the node by setting its parent to <code>node</code>.
	 * 
	 * @param node the new parent
	 */
	public void setParentNode(Treeable node);
	
	public Set<Treeable> getChildNodes();
	
	public void setChildNodes( Set<Treeable> children );
	
	public void addChild( Treeable child );

	public void removeChild( Treeable child );
	/**
	 * @return the node number as determined by a depth-first traversal of the containing tree
	 */
	public Integer getNodeNumber();
	
	/**
	 * Sets the depth-first traversal node number of this object
	 * 
	 * @param nodeNumber
	 */
	public void setNodeNumber(Integer nodeNumber);
	
	/**
	 * @return the node number of the descdendant having the largest node number
	 */
	public Integer getHighestChildNodeNumber();
	
	/**
	 * @param nodeNumber the node number of the descdendant having the largest node number
	 */
	public void setHighestChildNodeNumber(Integer nodeNumber);
		
	/**
	 * @return the remarks of this node
	 */
	public String getRemarks();
	
	/**
	 * @param name the new remarks of the node
	 */
	public void setRemarks(String remarks);
	
	/**
	 * @return the series ID of the tree containing this node
	 */
	public TreeDefinitionIface getTreeDef();
	
	/**
	 * @param id the new series ID of the tree that this node is contained in
	 * 
	 * @throws IllegalArgumentException if treeDef isn't an object of the correct type to represent this Treeable's tree definition item
	 */
	public void setTreeDef(TreeDefinitionIface treeDef);
	
	/**
	 * @return the TreeDefinitionItemIface object representing this Treeable's location in the tree
	 */
	public TreeDefinitionItemIface getDefItem();
	
	/**
	 * @param defItem the new TreeDefinitionItemIface object representing this Treeable's location in the tree
	 * 
	 * @throws IllegalArgumentException if defItem isn't an object of the correct type to represent this Treeable's tree definition
	 */
	public void setDefItem(TreeDefinitionItemIface defItem);

	public String getFullName();
	public void setFullName(String fullName);
	
	public Date getTimestampCreated();
	public void setTimestampCreated(Date created);
	
	public Date getTimestampModified();
	public void setTimestampModified(Date modified);
	
	public String getLastEditedBy();
	public void setLastEditedBy(String user);
	
	public int getFullNameDirection();
	public String getFullNameSeparator();
	
	public int getDescendantCount();
	public boolean childrenAllowed();
	public boolean canBeDeleted();
	public List<Treeable> getAllDescendants();
	public void fixFullNameForAllDescendants();
	
	public void setTimestampsToNow();
	public void updateModifiedTimeAndUser();
	public boolean isDescendantOf(Treeable node);
}

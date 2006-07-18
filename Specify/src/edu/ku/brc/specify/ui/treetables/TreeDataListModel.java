package edu.ku.brc.specify.ui.treetables;

import java.awt.FontMetrics;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.AbstractListModel;

import org.apache.log4j.Logger;

import edu.ku.brc.specify.datamodel.TreeDefinitionIface;
import edu.ku.brc.specify.datamodel.TreeDefinitionItemIface;
import edu.ku.brc.specify.datamodel.Treeable;
import edu.ku.brc.specify.dbsupport.HibernateUtil;
import edu.ku.brc.specify.treeutils.TreeFactory;
import edu.ku.brc.specify.treeutils.TreeTableUtils;
import edu.ku.brc.util.Pair;

/**
 * @author jstewart
 *
 */
@SuppressWarnings("serial")
public class TreeDataListModel extends AbstractListModel
{
	protected Vector<Treeable> visibleNodes;
	protected Hashtable<Treeable,Boolean> childrenWereShowing;
	protected TreeDefinitionIface treeDef;
    private static final Logger log = Logger.getLogger(TreeDataListModel.class);
    protected Treeable root;
    protected Treeable visibleRoot;
    protected Comparator<Treeable> comparator;

	public TreeDataListModel( Treeable root )
	{
		visibleNodes = new Vector<Treeable>();
		childrenWereShowing = new Hashtable<Treeable, Boolean>();
		this.root = root;
		this.visibleRoot = root;
		comparator = TreeFactory.getAppropriateComparator(root);
		
		treeDef = root.getTreeDef();
		
		makeNodeVisible(root);
		showChildren(root);
	}
	
	public Treeable getRoot()
	{
		return root;
	}

	public Treeable getVisibleRoot()
	{
		return visibleRoot;
	}
	
	public TreeDefinitionIface getTreeDef()
	{
		return this.treeDef;
	}
	
	public boolean allChildrenAreVisible(Treeable t)
	{
		if( t == null )
		{
			return false;
		}
		
		for(Treeable child: t.getChildNodes())
		{
			if(!visibleNodes.contains(child))
			{
				return false;
			}
		}
		return true;
	}
	
	protected boolean childrenWereShowing(Treeable t)
	{
		Boolean showing = childrenWereShowing.get(t);
		return (showing != null) ? showing : false;
	}
	
	public void setChildrenVisible( Treeable t, boolean visible )
	{
		// if the node is currently invisible, change the status of the children nodes
		// for when the node becomes visible in the future
		if( !visibleNodes.contains(t) )
		{
			childrenWereShowing.put(t, visible);
			return;
		}
		
		if(visible)
		{
			showChildren(t);
		}
		else
		{
			hideChildren(t);
		}
	}
	
	public void showDescendants( Treeable t )
	{
		showChildren(t);
		for( Treeable child: t.getChildNodes() )
		{
			showDescendants(child);
		}
	}
	
	protected void showChildren(Treeable t)
	{
		if( allChildrenAreVisible(t) )
		{
			return;
		}
		
		for( Treeable child: t.getChildNodes() )
		{
			setNodeVisible(child,true);
		}
	}
	
	protected void hideChildren(Treeable t)
	{
		if( !allChildrenAreVisible(t) )
		{
			return;
		}
		
		for( Treeable child: t.getChildNodes() )
		{
			setNodeVisible(child, false);
		}
	}
	
	protected void setNodeVisible( Treeable t, boolean visible )
	{
		if(visible)
		{
			if( visibleNodes.contains(t) )
			{
				// already visible
				return;
			}

			int origSize = visibleNodes.size();
			int addedIndex = makeNodeVisible(t);
			if( addedIndex == -1 )
			{
				log.error("Code error: Unexpected behavior");
				return;
			}
			int sizeChange = visibleNodes.size() - origSize;
			fireIntervalAdded(this, addedIndex, addedIndex+sizeChange-1);
		}
		else
		{
			if( !visibleNodes.contains(t) )
			{
				// already invisible
				return;
			}

			int startIndex = visibleNodes.indexOf(t);
			int origSize = visibleNodes.size();
			makeNodeInvisible(t);
			fireIntervalRemoved(this, startIndex, startIndex+(origSize-visibleNodes.size()-1));
		}
	}
	
	/**
	 * @param t the node to insert
	 * @return the new index of t in the visible node collection
	 * @throws RuntimeException if the parent of t isn't currently visible
	 */
	protected int makeNodeVisible( Treeable t )
	{
		// if this is the first node to ever be made visible...
		if( visibleNodes.isEmpty() )
		{
			visibleNodes.add(t);
			if( childrenWereShowing(t) )
			{
				showChildren(t);
			}

			return 0;
		}
		
		Treeable parent = t.getParentNode();

		// if the parent node isn't currently visible, throw an Exception
		int indexOfParent = visibleNodes.indexOf(parent);
		if( indexOfParent == -1 )
		{
			throw new RuntimeException("Parent of argument must already be visible");
		}
		
		// if the parent node is the current last node...
		if( indexOfParent == visibleNodes.size()-1 )
		{
			visibleNodes.add(t);
			if( childrenWereShowing(t) )
			{
				showChildren(t);
			}

			return visibleNodes.size()-1;
		}

		// else...
		// parent is visible and not the last node
		
		int currentIndex = indexOfParent+1;
		Treeable node = visibleNodes.get(currentIndex);
		while( true )
		{
			// if we've moved past the last descendant of 'parent',
			// insert the new node ('t') as the last descendant of 'parent'
			if( !TreeTableUtils.nodeIsDescendantOfNode(node, parent) )
			{
				// we've ventured out of our parent's descendant set
				// we should be added just before 'node'
				visibleNodes.insertElementAt(t, currentIndex);
				if( childrenWereShowing(t) )
				{
					showChildren(t);
				}
				return currentIndex;
			}
			else if( (node.getParentNode() == parent) && (comparator.compare(t, node) < 0) )
			{
				//else if 'node' is a direct child of 'parent' and is after 't' according to the comparator,
				// the new node ('t') should be inserted before 'node'
				visibleNodes.insertElementAt(t, currentIndex);
				if( childrenWereShowing(t) )
				{
					showChildren(t);
				}
				return currentIndex;
			}
			else if( currentIndex == visibleNodes.size()-1 )
			{
				// if there are no more nodes after this one, insert at the end
				visibleNodes.add(t);
				if( childrenWereShowing(t) )
				{
					showChildren(t);
				}
				return visibleNodes.size()-1;
			}
			
			++currentIndex;
			node = visibleNodes.get(currentIndex);
		}
	}
	
	protected void makeNodeInvisible( Treeable t )
	{
		if( !visibleNodes.contains(t) )
		{
			return;
		}
		
		if( allChildrenAreVisible(t) )
		{
			childrenWereShowing.put(t, true);
			
			for( Treeable child: t.getChildNodes() )
			{
				makeNodeInvisible(child);
			}
		}
		else
		{
			childrenWereShowing.put(t, false);
		}
		visibleNodes.remove(t);
	}
	
	protected void removeNode( Treeable node )
	{
		if( node == root )
		{
			throw new IllegalArgumentException("Cannot remove root node");
		}
		
		// basic algorithm here...
		// 1. hide the children of the parent node
		// 2. remove the child node
		// 3. reshow the children IF they were previously visible
		
		Treeable parent = node.getParentNode();
		boolean prevVisible = allChildrenAreVisible(parent);
		hideChildren(parent);
		parent.removeChild(node);
		if( prevVisible )
		{
			showChildren(parent);
		}
	}
	
	protected void insertNode( Treeable node, Treeable parent )
	{
		// basic algorithm here...
		// 1. hide children of new parent
		// 2. insert new child
		// 3. show children of new parent
		
		hideChildren(parent);
		parent.addChild(node);
		showChildren(parent);
	}
	
	public SortedSet<Integer> getVisibleRanks()
	{
		TreeSet<Integer> usedRanks = new TreeSet<Integer>();
		for( Treeable node: visibleNodes )
		{
			usedRanks.add(node.getRankId());
		}
		return usedRanks;
	}
	
	public int getVisibleNodeCountForRank(Integer rankId)
	{
		if( rankId == null )
		{
			return 0;
		}
		
		int count = 0;
		for( Treeable node: visibleNodes )
		{
			if( node.getRankId().equals(rankId) )
			{
				++count;
			}
		}
		return count;
	}
	
	public Integer getLongestNamePixelLengthByRank( Integer rank, FontMetrics fm, boolean considerRankName )
	{
		return getLongestNameAndPixelLengthByRank(rank, fm, considerRankName).second;
	}
	
	public String getLongestNameByRank( Integer rank, FontMetrics fm, boolean considerRankName )
	{
		return getLongestNameAndPixelLengthByRank(rank, fm, considerRankName).first;
	}
	
	protected Pair<String,Integer> getLongestNameAndPixelLengthByRank( Integer rank, FontMetrics fm, boolean considerRankName )
	{
		int nodeCount = getVisibleNodeCountForRank(rank);
		if( nodeCount == 0 )
		{
			return null;
		}
		
		TreeDefinitionItemIface defItem = TreeTableUtils.getDefItemByRank(treeDef, rank);

		// start with the rank name being the longest item
		// this way, it's length gets factored in
		// which is useful when displays show column headers
		String longestName = "";
		if( considerRankName == true )
		{
			longestName = defItem.getName();
		}
		int nodesFound = 0;
		for( Treeable node: visibleNodes )
		{
			if( node.getRankId().equals(rank) )
			{
				if( fm.stringWidth(node.getName()) > fm.stringWidth(longestName) )
				{
					longestName = node.getName();
				}
				nodesFound++;
				if( nodesFound >= nodeCount )
				{
					return new Pair<String,Integer>(longestName,fm.stringWidth(longestName));
				}
			}
		}
		return new Pair<String,Integer>(longestName,fm.stringWidth(longestName));
	}
	
	public Object getElementAt(int arg0)
	{
		int index = 0;
		for( Treeable t: visibleNodes )
		{
			if( index == arg0 )
			{
				return t;
			}
			++index;
		}
		
		throw new ArrayIndexOutOfBoundsException();
	}
	
	public boolean parentHasChildrenAfterNode( Treeable parent, Treeable child )
	{
		if( !visibleNodes.contains(child) )
		{
			throw new IllegalArgumentException("Must provide a child node that is visible");
		}
		
		if( child.getParentNode() != parent )
		{
			throw new IllegalArgumentException("Given nodes are not a parent/child pair");
		}
		
		int indexOfChild = visibleNodes.indexOf(child);
		for( int i = indexOfChild+1; i < visibleNodes.size(); ++i )
		{
			Treeable node = visibleNodes.get(i);
			if( node.getParentNode() == parent )
			{
				return true;
			}
		}
		
		return false;
	}

	public int getSize()
	{
		return visibleNodes.size();
	}
	
	public boolean reparent( Treeable node, Treeable newParent )
	{
		if( node.getParentNode() == newParent )
		{
			return false;
		}
		
		this.removeNode(node);
		this.insertNode(node, newParent);
		TreeTableUtils.fixFullNames(node);
		return true;
	}

	public void addChild( Treeable child, Treeable parent )
	{
		HibernateUtil.attach(parent, HibernateUtil.getCurrentSession());
		parent.addChild(child);
		HibernateUtil.closeSession();
		showChildren(parent);
	}

	public void nodeValuesChanged(Treeable node)
	{
		if( visibleNodes.contains(node) )
		{
			int index = visibleNodes.indexOf(node);
			fireContentsChanged(this, index, index);
		}
	}

	public void setVisibleRoot(Treeable node)
	{
		setNodeVisible(visibleRoot,false);
		visibleRoot = node;
		makeNodeVisible(visibleRoot);
		showChildren(visibleRoot);
	}
}

package edu.ku.brc.specify.ui.treetables;

import java.util.Iterator;
import java.util.Set;

import edu.ku.brc.util.Pair;

/**
 * @author rod
 * (Original author JDS)
 *
 * @code_status Beta
 *
 * Jan 23, 2008
 *
 */
public class TreeNode
{
    protected String   name;
    protected String   fullName;
    protected boolean  hasChildren;
    protected int      id;
    protected int      parentId;
    protected Class<?> dataObjClass;
    protected int      rank;
    protected int      parentRank;
    protected int      nodeNumber;
    protected int      highestChildNodeNumber;
    
    /** This field will hold a number representing the number of associated records for this node.
     *  For example, if this field represents a Taxon record, the <code>associatedRecordCount</code> would
     *  be the number of CollectionObject records that refer to this Taxon.  For TreeNodes representing
     *  Geography records, this number might represent the number of CollectingEvents that were in this
     *  geographical area.
     */
    protected int     associatedRecordCount;
    protected int     associatedRecordCount2;
    protected Integer acceptedParentId;
    protected String  acceptedParentFullName;
    protected Set<Pair<Integer,String>> synonymIdsAndNames;
    
    public TreeNode(final String name, 
                    final String fullName, 
                    final int id, 
                    final int parentId, 
                    final int rank, 
                    final int parentRank, 
                    final boolean hasChildren, 
                    final Integer acceptedParentId, 
                    final String acceptedParentFullName, 
                    final Set<Pair<Integer,String>> synonymIdsAndNames)
    {
        super();
        this.name                   = name;
        this.fullName               = fullName;
        this.id                     = id;
        this.parentId               = parentId;
        this.rank                   = rank;
        this.parentRank             = parentRank;
        this.hasChildren            = hasChildren;
        this.acceptedParentId       = acceptedParentId;
        this.acceptedParentFullName = acceptedParentFullName;
        this.synonymIdsAndNames     = synonymIdsAndNames;
        this.associatedRecordCount  = 0;
        this.associatedRecordCount2 = 0;
    }

    public Class<?> getDataObjClass()
    {
        return dataObjClass;
    }

    public void setDataObjClass(final Class<?> dataObjClass)
    {
        this.dataObjClass = dataObjClass;
    }

    public boolean isHasChildren()
    {
        return hasChildren;
    }

    public void setHasChildren(final boolean hasChildren)
    {
        this.hasChildren = hasChildren;
    }

    public int getId()
    {
        return id;
    }

    public void setId(final int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(final String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return the parent node's ID, or, if this node has no parent, this node's ID
     */
    public int getParentId()
    {
        return parentId;
    }

    public void setParentId(final int parentId)
    {
        this.parentId = parentId;
    }

    /**
     * @return the parent node's rank, or -1 if this node doesn't have a parent
     */
    public int getParentRank()
    {
        return parentRank;
    }

    public void setParentRank(final int parentRank)
    {
        this.parentRank = parentRank;
    }

    public int getRank()
    {
        return rank;
    }

    public void setRank(final int rank)
    {
        this.rank = rank;
    }

    public int getAssociatedRecordCount()
    {
        return associatedRecordCount;
    }

    public void setAssociatedRecordCount(final int associatedRecordCount)
    {
        this.associatedRecordCount = associatedRecordCount;
    }

    /**
     * @return the associatedRecordCount2
     */
    public int getAssociatedRecordCount2()
    {
        return associatedRecordCount2;
    }

    /**
     * @param associatedRecordCount2 the associatedRecordCount2 to set
     */
    public void setAssociatedRecordCount2(final int associatedRecordCount2)
    {
        this.associatedRecordCount2 = associatedRecordCount2;
    }

    public Integer getAcceptedParentId()
    {
        return acceptedParentId;
    }

    public String getAcceptedParentFullName()
    {
        return acceptedParentFullName;
    }

    public void setAcceptedParentFullName(final String acceptedParentFullName)
    {
        this.acceptedParentFullName = acceptedParentFullName;
    }

    public void setAcceptedParentId(final Integer acceptedParentId)
    {
        this.acceptedParentId = acceptedParentId;
    }

    public Set<Pair<Integer, String>> getSynonymIdsAndNames()
    {
        return synonymIdsAndNames;
    }
    
    public void removeSynonym(final Integer synonymNodeId)
    {
        Iterator<Pair<Integer,String>> iter = synonymIdsAndNames.iterator();
        while( iter.hasNext() )
        {
            if (iter.next().first.equals(synonymNodeId))
            {
                iter.remove();
                return;
            }
        }
    }

    public void setSynonymIdsAndNames(Set<Pair<Integer, String>> synonymIdsAndNames)
    {
        this.synonymIdsAndNames = synonymIdsAndNames;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String memoryLocation = "00000000" + Integer.toHexString(hashCode());
        memoryLocation = memoryLocation.substring(memoryLocation.length() - 8);
        return getClass().getSimpleName() + "@0x" + memoryLocation + ": " + name + ", " + id + ", " + rank + ", " + parentId + ", " + parentRank;
    }
}

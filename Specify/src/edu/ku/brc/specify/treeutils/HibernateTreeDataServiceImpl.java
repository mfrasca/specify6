/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.treeutils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.dbsupport.HibernateUtil;
import edu.ku.brc.dbsupport.DataProviderSessionIFace.QueryIFace;
import edu.ku.brc.specify.datamodel.DataModelObjBase;
import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.datamodel.TreeDefItemIface;
import edu.ku.brc.specify.datamodel.Treeable;
import edu.ku.brc.specify.dbsupport.HibernateDataProviderSession;
import edu.ku.brc.specify.ui.treetables.TreeNode;
import edu.ku.brc.ui.forms.BusinessRulesIFace;
import edu.ku.brc.util.Pair;

/**
 * An implementation of @see {@link TreeDataService} that uses Hibernate
 * capabilities.
 *
 * @code_status Beta
 * @author jstewart
 */
public class HibernateTreeDataServiceImpl <T extends Treeable<T,D,I>,
											D extends TreeDefIface<T,D,I>,
											I extends TreeDefItemIface<T,D,I>>
											implements TreeDataService<T,D,I>
{
    /**
     * A <code>Logger</code> object used for all log messages eminating from
     * this class.
     */
    protected static final Logger log = Logger.getLogger(HibernateTreeDataServiceImpl.class);

    /** An {@link Interceptor} that logs all objects loaded by Hibernate. */
    //public static HibernateLoadLogger loadLogger = new HibernateLoadLogger();
    
	/**
	 * Constructor.
	 */
	public HibernateTreeDataServiceImpl()
	{
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.treeutils.TreeDataService#findByName(edu.ku.brc.specify.datamodel.TreeDefIface, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
    public synchronized List<T> findByName(D treeDef, String name)
    {
        Vector<T> results = new Vector<T>();
        Class<T> nodeClass = treeDef.getNodeClass();
        
        Session session = getNewSession(treeDef);
        Query q = session.createQuery("FROM "+nodeClass.getSimpleName()+" as node WHERE node.name LIKE :name");
        q.setParameter("name",name);
        for( Object o: q.list() )
        {
            T t = (T)o;
            
            // force loading on all ancestors
            T parent = t.getParent();
            while(parent!=null)
            {
                parent = parent.getParent();
            }
            
            results.add(t);
        }
        
        Collections.sort(results,new TreePathComparator<T,D,I>(true));
        session.close();
        return results;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#getChildNodes(edu.ku.brc.specify.datamodel.Treeable)
     */
    public synchronized Set<T> getChildNodes(T parent)
    {
        if (Hibernate.isInitialized(parent.getChildren()))
        {
            return parent.getChildren();
        }
        
        Session session = getNewSession(parent);
        Set<T> children = parent.getChildren();
        // to force Set loading
        int childCount = children.size();
        log.debug("getChildNodes( " + parent + " ): " + childCount + " child(ren) of " + parent.getName() + " loaded");
        for (T child: children)
        {
            log.debug("\t" + nodeDebugInfo(child));
        }
        session.close();
        return children;
    }
    
    @SuppressWarnings("unchecked")
    public List<TreeNode> getChildTreeNodes(T parent)
    {
        Session session = getNewSession(parent);
        String childQueryString = TreeFactory.getChildQueryString(parent);
        Query getNodeInfoList = session.createQuery(childQueryString);
        getNodeInfoList.setParameter("PARENT", parent);
        List list = getNodeInfoList.list();
        List<Object[]> nodeInfoList = list;
        session.close();
        
        Vector<TreeNode> treeNodes = new Vector<TreeNode>();
        for (Object[] nodeInfo: nodeInfoList)
        {
            treeNodes.add(createNode(nodeInfo,parent));
        }
        return treeNodes;
    }
    
    /**
     * Creates a {@link TreeNode} from the passed in {@link Object} array.  The array
     * must contain the following objects, in order: 
     * <ol start="0">
     * <li>{@link Integer} id
     * <li>{@link String} name
     * <li>{@link String} fullName
     * <li>{@link Integer} nodeNumber
     * <li>{@link Integer} highestChildNodeNumber
     * <li>{@link Integer} rankId
     * <li>{@link Integer} acceptedParent.rankId
     * <li>{@link String} acceptedParent.fullName
     * </ol>
     * 
     * (The acceptedParent fields will commonly be <code>null</code>.)
     * 
     * @param nodeInfo an object array containing the node info
     * @param parent the parent record
     * @return a {@link TreeNode} object
     */
    @SuppressWarnings("unchecked")
    private TreeNode createNode(Object[] nodeInfo, T parent)
    {
        Integer id                     = (Integer) nodeInfo[0];
        String  nodeName               = (String)  nodeInfo[1];
        String  fullName               = (String)  nodeInfo[2];
        Integer nodeNum                = (Integer) nodeInfo[3];
        Integer highChild              = (Integer) nodeInfo[4];
        int     rank                   = (Integer) nodeInfo[5];
        Integer acceptedParentId       = (Integer) nodeInfo[6];
        String  acceptedParentFullName = (String)  nodeInfo[7];
        
        int descCount = 0;
        if (highChild != null && nodeNum != null)
        {
            descCount = highChild - nodeNum;
        }

        int parentId;
        int parentRank;
        
        T parentRecord = parent;
        if (parentRecord == null)
        {
            parentId = id;
            parentRank = -1;
        }
        else
        {
            parentId = parentRecord.getTreeId();
            parentRank = parentRecord.getRankId();
        }
        
        Set<Pair<Integer,String>> synIdsAndNames = getSynonymIdsAndNames(parent.getClass(), id);
        
        TreeNode node = new TreeNode(nodeName,fullName,id,parentId,rank,parentRank, (descCount != 0), acceptedParentId, acceptedParentFullName, synIdsAndNames);
        return node;
    }

    
	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.treeutils.TreeDataService#getRootNode(edu.ku.brc.specify.datamodel.TreeDefIface)
	 */
	@SuppressWarnings("unchecked")
	public synchronized T getRootNode(D treeDef)
	{
		T root = null;
		
        Session session = getNewSession(treeDef);
        
        for( I defItem: treeDef.getTreeDefItems() )
        {
            if (defItem.getRankId()==0)
            {
                root = defItem.getTreeEntries().iterator().next();
            }
        }
        
        session.close();
        if (root!=null)
        {
            log.debug("Root node: " + nodeDebugInfo(root));
        }
        else
        {
            log.debug("No root node");
        }
		return root;
	}

    @SuppressWarnings("unchecked")
    public synchronized T getNodeById(Class<?> clazz, int id)
    {
        log.debug("getNodeById( " + clazz.getSimpleName() + ", " + id + " )");
        Session session = getNewSession();
        T record = (T)session.load(clazz, id);
        session.close();
        return record;
    }
    
	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.treeutils.TreeDataService#getAllTreeDefs(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<D> getAllTreeDefs(Class<D> treeDefClass)
	{
        Session session = getNewSession();

        Query q = session.createQuery("FROM " + treeDefClass.getSimpleName());
        List<?> results = q.list();
        
		Vector<D> defs = new Vector<D>(results.size());
		for( Object o: results )
		{
			D def = (D)o;
            
            // force loading of all related def items
            def.getTreeDefItems().size();
            
			defs.add(def);
		}
        
        session.close();
		return defs;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.treeutils.TreeDataService#getTreeDef(java.lang.Class, int)
	 */
	@SuppressWarnings("unchecked")
	public synchronized D getTreeDef(Class<D> defClass, int defId)
	{
        Session session = getNewSession();
		String className = defClass.getSimpleName();
		String idFieldName = className.toLowerCase().substring(0,1) + className.substring(1) + "Id";
		Query query = session.createQuery("FROM " + className + " WHERE " + idFieldName + "=:defId");
		query.setParameter("defId",defId);
		D def = (D)query.uniqueResult();

        // force loading of all related def items
        // (they should load anyway as long as FetchType.EAGER is set on getTreeDefItems())
        def.getTreeDefItems().size();
        
        session.close();
		return def;
	}

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#getDescendantCount(edu.ku.brc.specify.datamodel.Treeable)
     */
    public synchronized int getDescendantCount(T node)
    {
        if (node == null)
        {
            return 0;
        }
        
        Session session = getNewSession(node);
        //log.debug("refreshing " + nodeDebugInfo(node));
        
        session.refresh(node);
        Integer nodeNum = node.getNodeNumber();
        Integer highChild = node.getHighestChildNodeNumber();
        
        int descCnt = 0;
        if (nodeNum!=null && highChild!=null)
        {
            descCnt = highChild-nodeNum;
        }
        else
        {
        	descCnt = node.getDescendantCount();
        }
        
        session.close();
        return descCnt;
   }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#deleteTreeNode(edu.ku.brc.specify.datamodel.Treeable)
     */
    @SuppressWarnings("null")
    public synchronized boolean deleteTreeNode(T node)
    {
        Session session = getNewSession(node);

        // refresh the node data so we have correct information for the following calculation
        session.refresh(node);
        T parent = node.getParent();
        if (parent!=null)
        {
            session.refresh(parent);
        }

        Transaction tx = session.beginTransaction();
        
        // detach from the parent node
        if (parent!=null)
        {
            parent.removeChild(node);
            node.setParent(null);
        }
        
        // let Hibernate delete the subtree
        DataProviderSessionIFace sessionWrapper = new HibernateDataProviderSession(session);
        
        BusinessRulesIFace busRulesObj = DBTableIdMgr.getInstance().getBusinessRule(node);
        if (busRulesObj != null)
        {
            // TODO: Have to rework this to provide a non-null DataProviderSessionIFace thingy
            busRulesObj.beforeDelete(node, sessionWrapper);
        }
        session.delete(node);
        
        if (busRulesObj != null)
        {
            try
            {
                if (!busRulesObj.beforeDeleteCommit(node, sessionWrapper))
                {
                    tx.rollback();
                    return false;
                }
            }
            catch(Exception e)
            {
                tx.rollback();
                return false;
            }
        }
        boolean retVal = commitTransaction(session, tx);
        
        if (busRulesObj != null && retVal)
        {
            busRulesObj.afterDeleteCommit(node);
        }
        return retVal;
    }
    
    public List<String> nodesSkippingOverLevel(int levelSkippedRank, D treeDef)
    {
        Session session = getNewSession();
        Class<T> nodeClass = treeDef.getNodeClass();
        
        Query nodeSkippingLevelQuery = session.createQuery("select n.fullName from " + nodeClass.getName() + " n where rankId>:rankID AND parent.rankId<:rankID AND definition=:treeDef");
        nodeSkippingLevelQuery.setParameter("rankID", levelSkippedRank);
//        nodeSkippingLevelQuery.setParameter("rankID2", levelSkippedRank);
        nodeSkippingLevelQuery.setParameter("treeDef", treeDef);
        List<?> results = nodeSkippingLevelQuery.list();
        Vector<String> nodeNames = new Vector<String>(results.size());
        for (Object o: results)
        {
            nodeNames.add((String)o);
        }
        session.close();
        return nodeNames;
    }
    
    public List<String> nodeNamesAtLevel(int rankID, D treeDef)
    {
        Session session = getNewSession();
        Class<T> nodeClass = treeDef.getNodeClass();
        
        Query nodeNamesQuery = session.createQuery("select n.fullName from " + nodeClass.getName() + " n where rankId=:rankID AND definition=:treeDef");
        nodeNamesQuery.setParameter("rankID", rankID);
        nodeNamesQuery.setParameter("treeDef", treeDef);
        List<?> results = nodeNamesQuery.list();
        Vector<String> nodeNames = new Vector<String>(results.size());
        for (Object o: results)
        {
            nodeNames.add((String)o);
        }
        session.close();
        return nodeNames;
    }
    
    public int countNodesAtLevel(int rankID, D treeDef)
    {
        Session session = getNewSession();
        Class<T> nodeClass = treeDef.getNodeClass();
        
        Query nodeCountQuery = session.createQuery("select count(n) from " + nodeClass.getName() + " n where rankID=:rankID AND definition=:treeDef");
        nodeCountQuery.setParameter("rankID", rankID);
        nodeCountQuery.setParameter("treeDef", treeDef);
        Integer count = (Integer)nodeCountQuery.uniqueResult();
        session.close();
        return count;
    }
    
    @SuppressWarnings({ "unchecked", "null" })
    public synchronized boolean updateNodeNumbersAfterNodeAddition(T newNode, DataProviderSessionIFace session) throws Exception
    {
        // update the nodeNumber and highestChildNodeNumber fields for all effected nodes
        boolean doNodeNumberUpdate = true;
        
        T parent = newNode.getParent();
        Integer parentNN = null;
        if (parent == null)
        {
            doNodeNumberUpdate = false;
        }
        else
        {
            parentNN = parent.getNodeNumber();
            if (parentNN==null)
            {
                doNodeNumberUpdate = false;
            }
        }
        
        if (!doNodeNumberUpdate)
        {
            return true;
        }
        // else, node number update needed
        
        T mergedParent = session.merge(parent);
        session.refresh(mergedParent);

        String className = mergedParent.getClass().getName();
        TreeDefIface<T,D,I> def = mergedParent.getDefinition();

        String updateNodeNumbersQueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber+1 WHERE nodeNumber>:parentNN AND definition=:def";
        QueryIFace fixNodeNumQuery = session.createQuery(updateNodeNumbersQueryStr);
        fixNodeNumQuery.setParameter("parentNN", parentNN);
        fixNodeNumQuery.setParameter("def", def);
        fixNodeNumQuery.executeUpdate();

        String updateHighChildQueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber+1 WHERE highestChildNodeNumber>=:parentNN AND definition=:def";
        QueryIFace fixHighChildQuery = session.createQuery(updateHighChildQueryStr);
        fixHighChildQuery.setParameter("parentNN", parentNN);
        fixHighChildQuery.setParameter("def", def);
        fixHighChildQuery.executeUpdate();

        // now set the initial values of the nodeNumber and highestChildNodeNumber fields for the new node
        int newChildNN = parentNN+1;
        String setChildNNQueryStr = "UPDATE " + className + " SET nodeNumber=:newChildNN WHERE nodeNumber IS NULL AND parentID=:parentID";
        QueryIFace setChildNNQuery = session.createQuery(setChildNNQueryStr);
        setChildNNQuery.setParameter("newChildNN", newChildNN);
        setChildNNQuery.setParameter("parentID", parent.getTreeId());
        setChildNNQuery.executeUpdate();

        String setChildHCQueryStr = "UPDATE " + className + " SET highestChildNodeNumber=:newChildNN WHERE highestChildNodeNumber IS NULL AND parentID=:parentID";
        QueryIFace setChildHCQuery = session.createQuery(setChildHCQueryStr);
        setChildHCQuery.setParameter("newChildNN", newChildNN);
        setChildHCQuery.setParameter("parentID", parent.getTreeId());
        setChildHCQuery.executeUpdate();

        return true;
    }
    
    @SuppressWarnings("null")
    public boolean updateNodeNumbersAfterNodeDeletion(T deletedNode, DataProviderSessionIFace session) throws Exception
    {
        boolean success = true;
        
        // save the original nodeNumber and highestChildNodeNumber values
        Integer delNodeNN = deletedNode.getNodeNumber();
        Integer delNodeHC = deletedNode.getHighestChildNodeNumber();

        // update the nodeNumber and highestChildNodeNumber fields for all effected nodes
        boolean doNodeNumberUpdate = true;
        if (delNodeNN==null || delNodeHC==null)
        {
            doNodeNumberUpdate = false;
        }

        if (doNodeNumberUpdate)
        {
            int nodesDeleted = delNodeHC-delNodeNN+1;

            String className = deletedNode.getClass().getName();
            TreeDefIface<T,D,I> def = deletedNode.getDefinition();

            String updateNodeNumbersQueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber-:nodesDeleted WHERE nodeNumber>=:delNodeNN AND definition=:def";
            QueryIFace fixNodeNumQuery = session.createQuery(updateNodeNumbersQueryStr);
            fixNodeNumQuery.setParameter("nodesDeleted", nodesDeleted);
            fixNodeNumQuery.setParameter("delNodeNN", delNodeNN);
            fixNodeNumQuery.setParameter("def", def);
            fixNodeNumQuery.executeUpdate();

            String updateHighChildQueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber-:nodesDeleted WHERE highestChildNodeNumber>=:delNodeHC AND definition=:def";
            QueryIFace fixHighChildQuery = session.createQuery(updateHighChildQueryStr);
            fixHighChildQuery.setParameter("nodesDeleted", nodesDeleted);
            fixHighChildQuery.setParameter("delNodeHC", delNodeHC);
            fixHighChildQuery.setParameter("def", def);
            fixHighChildQuery.executeUpdate();
        }

        return success;
    }
    
    @SuppressWarnings("unchecked")
    public synchronized boolean moveTreeNode(T node, T newParent)
    {
        log.debug("Moving ["+nodeDebugInfo(node)+"] to ["+nodeDebugInfo(newParent)+"]");
        
        if (node==null || newParent==null)
        {
            throw new NullPointerException("'node' and 'newParent' must both be non-null");
        }
        
        if( node.getParent() == newParent )
        {
            return false;
        }
        
        T oldParent = node.getParent();
        if (oldParent==null)
        {
            throw new NullPointerException("'node' must already have a parent");
        }

        Session session = getNewSession();
        T mergedNode      = (T)mergeIntoSession(session, node);
        T mergedNewParent = (T)mergeIntoSession(session, newParent);
        T mergedOldParent = (T)mergeIntoSession(session, oldParent);
        Transaction tx = session.beginTransaction();
        
        log.debug("refreshing " + nodeDebugInfo(mergedNode));
        session.refresh(mergedNode);
        log.debug("refreshing " + nodeDebugInfo(mergedNewParent));
        session.refresh(mergedNewParent);
        
        // fix up the parent/child pointers for the effected nodes
        // oldParent cannot be null at this point
        mergedOldParent.removeChild(mergedNode);
        mergedNewParent.addChild(mergedNode);
        mergedNode.setParent(mergedNewParent);
        
        BusinessRulesIFace busRules = DBTableIdMgr.getInstance().getBusinessRule(mergedNode);
        HibernateDataProviderSession sessionWrapper = new HibernateDataProviderSession(session);
        
        if (busRules != null)
        {
            busRules.beforeSave(mergedNode, sessionWrapper);
        }
        session.saveOrUpdate(mergedNode);
        
        // fix all the node numbers for effected nodes
        // X will represent moving subtree's root node
        // Y will represent new parent of moving subtree
        
        // get the root node
        T rootNode = mergedNewParent;
        while (rootNode.getParent() != null)
        {
            rootNode = rootNode.getParent();
        }
        
        int rootHC = rootNode.getHighestChildNodeNumber();
        int xNN = mergedNode.getNodeNumber();
        int xHC = mergedNode.getHighestChildNodeNumber();
        int yNN = mergedNewParent.getNodeNumber();
        D def = mergedNode.getDefinition();
        String className = mergedNode.getClass().getName();
        int numMoving = xHC-xNN+1;
        
        // the HQL update statements that need to happen now are dependant on the 'direction' of the move
        boolean downwardMove = true;
        if (xNN>yNN)
        {
            downwardMove = false;
        }
        
        if (downwardMove)
        {
            // change node numbers for the moving nodes to high values in order to temporarily 'move them out of the tree'
            String step1QueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber+:rootHC, highestChildNodeNumber=highestChildNodeNumber+:rootHC WHERE nodeNumber>=:xNN AND nodeNumber<=:xHC AND definition=:def";
            Query step1Query = session.createQuery(step1QueryStr);
            step1Query.setParameter("def", def);
            step1Query.setParameter("xNN", xNN);
            step1Query.setParameter("xHC", xHC);
            step1Query.setParameter("rootHC", rootHC);
            step1Query.executeUpdate();

            String step2QueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber-:numMoving WHERE nodeNumber>:xHC AND nodeNumber<=:yNN AND definition=:def";
            Query step2Query = session.createQuery(step2QueryStr);
            step2Query.setParameter("def", def);
            step2Query.setParameter("xHC", xHC);
            step2Query.setParameter("yNN", yNN);
            step2Query.setParameter("numMoving", numMoving);
            step2Query.executeUpdate();

            String step3QueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber-:numMoving WHERE highestChildNodeNumber>=:xHC AND highestChildNodeNumber<:yNN AND definition=:def";
            Query step3Query = session.createQuery(step3QueryStr);
            step3Query.setParameter("def", def);
            step3Query.setParameter("xHC", xHC);
            step3Query.setParameter("yNN", yNN);
            step3Query.setParameter("numMoving", numMoving);
            step3Query.executeUpdate();

            String step4QueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber-nodeNumber WHERE nodeNumber>:rootHC AND definition=:def";
            Query step4Query = session.createQuery(step4QueryStr);
            step4Query.setParameter("def", def);
            step4Query.setParameter("rootHC", rootHC);
            step4Query.executeUpdate();

            String step5QueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber + :yNN - :xHC - :rootHC WHERE nodeNumber>:rootHC AND definition=:def";
            Query step5Query = session.createQuery(step5QueryStr);
            step5Query.setParameter("def", def);
            step5Query.setParameter("xHC", xHC);
            step5Query.setParameter("yNN", yNN);
            step5Query.setParameter("rootHC", rootHC);
            step5Query.executeUpdate();

            String step6QueryStr = "UPDATE " + className + " SET highestChildNodeNumber=nodeNumber+highestChildNodeNumber WHERE nodeNumber >:yNN-:numMoving AND nodeNumber<=:yNN AND definition=:def";
            Query step6Query = session.createQuery(step6QueryStr);
            step6Query.setParameter("def", def);
            step6Query.setParameter("yNN", yNN);
            step6Query.setParameter("numMoving", numMoving);
            step6Query.executeUpdate();
        }
        else
        {
            // change node numbers for the moving nodes to high values in order to temporarily 'move them out of the tree'
            String step1QueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber+:rootHC, highestChildNodeNumber=highestChildNodeNumber+:rootHC WHERE nodeNumber>=:xNN AND nodeNumber<=:xHC AND definition=:def";
            Query step1Query = session.createQuery(step1QueryStr);
            step1Query.setParameter("def", def);
            step1Query.setParameter("xNN", xNN);
            step1Query.setParameter("xHC", xHC);
            step1Query.setParameter("rootHC", rootHC);
            step1Query.executeUpdate();
            
            String step2QueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber+:numMoving WHERE nodeNumber>:yNN AND nodeNumber<:xNN AND definition=:def";
            Query step2Query = session.createQuery(step2QueryStr);
            step2Query.setParameter("def", def);
            step2Query.setParameter("xNN", xNN);
            step2Query.setParameter("yNN", yNN);
            step2Query.setParameter("numMoving", numMoving);
            step2Query.executeUpdate();

            String step3QueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber+:numMoving WHERE highestChildNodeNumber>=:yNN AND highestChildNodeNumber<:xHC AND definition=:def";
            Query step3Query = session.createQuery(step3QueryStr);
            step3Query.setParameter("def", def);
            step3Query.setParameter("yNN", yNN);
            step3Query.setParameter("xHC", xHC);
            step3Query.setParameter("numMoving", numMoving);
            step3Query.executeUpdate();

            String step4QueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber-nodeNumber WHERE nodeNumber>:rootHC AND definition=:def";
            Query step4Query = session.createQuery(step4QueryStr);
            step4Query.setParameter("def", def);
            step4Query.setParameter("rootHC", rootHC);
            step4Query.executeUpdate();

            String step5QueryStr = "UPDATE " + className + " SET nodeNumber=nodeNumber+1+:yNN-:xNN-:rootHC WHERE nodeNumber>:rootHC AND definition=:def";
            Query step5Query = session.createQuery(step5QueryStr);
            step5Query.setParameter("def", def);
            step5Query.setParameter("xNN", xNN);
            step5Query.setParameter("yNN", yNN);
            step5Query.setParameter("rootHC", rootHC);
            step5Query.executeUpdate();

            String step6QueryStr = "UPDATE " + className + " SET highestChildNodeNumber=highestChildNodeNumber+nodeNumber WHERE nodeNumber>:yNN AND nodeNumber<=:yNN+:numMoving AND definition=:def";
            Query step6Query = session.createQuery(step6QueryStr);
            step6Query.setParameter("def", def);
            step6Query.setParameter("yNN", yNN);
            step6Query.setParameter("numMoving", numMoving);
            step6Query.executeUpdate();
        }

        if (busRules != null)
        {
            try
            {
                boolean retVal = busRules.beforeSaveCommit(node, sessionWrapper);
                if (retVal == false)
                {
                    tx.rollback();
                    return false;
                }
            }
            catch (Exception e)
            {
                tx.rollback();
                return false;
            }
        }
        boolean success = commitTransaction(session, tx);
        if (busRules != null)
        {
            success &= busRules.afterSaveCommit(node);
        }
        
        return success;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#createNodeLink(edu.ku.brc.specify.datamodel.Treeable, edu.ku.brc.specify.datamodel.Treeable)
     */
    @SuppressWarnings("unchecked")
    public String synonymize(T source, T destination)
    {
        Session session = getNewSession(source);
        T mergedDest = (T)mergeIntoSession(session, destination);
        Transaction tx = session.beginTransaction();
        String statusMsg = TreeHelper.createNodeRelationship(source,mergedDest);
        if (!commitTransaction(session, tx))
        {
            statusMsg = "Failed to create node relationship.";
        }
        return statusMsg;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#getSynonyms(edu.ku.brc.specify.datamodel.Treeable)
     */
    public Set<T> getSynonyms(T node)
    {
        Session session = getNewSession(node);
        Set<T> synonyms = node.getAcceptedChildren();
        synonyms.size();
        session.close();
        return synonyms;
    }
    
    public Set<T> getSynonyms(Class<? extends Treeable<?,?,?>> clazz, Integer id)
    {
        T node = getNodeById(clazz, id);
        return getSynonyms(node);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#getSynonymIdsAndNames(java.lang.Class, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public Set<Pair<Integer,String>> getSynonymIdsAndNames(Class<?> clazz, Integer nodeId)
    {
        Set<Pair<Integer,String>> idsAndNames = new HashSet<Pair<Integer,String>>();
        Session session = getNewSession();
        String queryString = TreeFactory.getSynonymQueryString(clazz);
        Query q = session.createQuery(queryString);
        q.setParameter("NODEID", nodeId);
        List<Object[]> results = q.list();
        for (Object[] idAndName: results)
        {
            Integer id = (Integer)idAndName[0];
            String name = (String)idAndName[1];
            
            idsAndNames.add(new Pair<Integer,String>(id,name));
        }
        session.close();
        return idsAndNames;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#initializeRelatedObjects(edu.ku.brc.specify.datamodel.Treeable)
     */
    public synchronized void initializeRelatedObjects(T node)
    {
        Session session = getNewSession(node);
        TreeHelper.initializeRelatedObjects(node);
        session.close();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.treeutils.TreeDataService#refresh(java.lang.Object[])
     */
    public synchronized void refresh(Object ... objects)
    {
        Session session = getNewSession(objects);
        for (Object o: objects)
        {
            session.refresh(o);
        }
        session.close();
    }
    
    /**
     * Creates a new Hibernate session and associates the given objects with it.
     * 
     * @param objects the objects to associate with the new session
     * @return the newly created session
     */
    private Session getNewSession(Object... objects)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        for (Object o: objects)
        {
            if (o!=null)
            {
                // make sure not to attempt locking an unsaved object
                DataModelObjBase dmob = (DataModelObjBase)o;
                if (dmob.getId() != null)
                {
                    session.lock(o, LockMode.NONE);
                }
            }
        }
        return session;
    }
    
    private Object mergeIntoSession(Session session, Object object)
    {
        Object newObject = session.merge(object);
        return newObject;
    }
    
    /**
     * Commits the given Hibernate transaction on the given session.  A rollback
     * is performed, and false is returned, on failure.
     * 
     * @param session the session
     * @param tx the transaction
     * @return true on success
     */
    private boolean commitTransaction(Session session, Transaction tx)
    {
        boolean result = true;
        try
        {
            tx.commit();
        }
        catch (Exception ex)
        {
            result = false;
            log.error("Error while committing transaction to DB",ex);
            tx.rollback();
        }
        finally
        {
            if (session.isOpen())
            {
                session.close();
            }
        }
        return result;
    }
    
    /**
     * Returns a string representation of the given Object.  If the object does not
     * implement {@link Treeable}, then {@link Object#toString()} is called.  If the object
     * does implement {@link Treeable}, the node ID, name, and hashcode are used to create
     * a string representation.  This method is for debugging purposes only.
     * 
     * @param o any object
     * @return a string representation of the node
     */
    private String nodeDebugInfo(Object o)
    {
        if (o instanceof Treeable)
        {
            Treeable<?,?,?> t = (Treeable<?,?,?>)o;
            return t.getTreeId() + " " + t.getName() + " 0x" + Integer.toHexString(t.hashCode());
        }
        return o.toString();
    }

//    /**
//     * This class is an extension of {@link EmptyInterceptor} that logs all
//     * objects loaded by Hibernate.  This class is only intended for use in
//     * debugging.
//     * 
//     * @author jstewart
//     * @code_status Complete.
//     */
//    public static class HibernateLoadLogger extends EmptyInterceptor
//    {
//        @Override
//        public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
//        {
//            String className = entity.getClass().getSimpleName();
//            log.debug("loaded " + className + " (" + id + ") at 0x" + entity.hashCode());
//            return false;
//        }
//    }
}
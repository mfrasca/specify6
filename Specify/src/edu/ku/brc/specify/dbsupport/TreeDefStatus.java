/**
 * 
 */
package edu.ku.brc.specify.dbsupport;

import edu.ku.brc.specify.datamodel.TreeDefIface;
import edu.ku.brc.specify.dbsupport.TaskSemaphoreMgr.USER_ACTION;
import edu.ku.brc.ui.UIRegistry;

/**
 * @author timo
 *
 */
public class TreeDefStatus
{
    protected static String nodeNumbersInvalid = "BadNodes";
    protected static String numberingNodes = "UpdateNodes";
    protected final TreeDefIface<?,?,?> treeDef;
    protected boolean doNodeNumberUpdates;
    protected boolean uploadInProgress;
	
	public TreeDefStatus(final TreeDefIface<?,?,?> treeDef)
	{
		this.treeDef = treeDef;
		
		//XXX need to check locks to determine stats
		//these are the defaults
		this.doNodeNumberUpdates = true;
		this.uploadInProgress = false;
	}
	/**
	 * @return the nodeNumbersAreUpToDate
	 */
	public synchronized boolean isNodeNumbersAreUpToDate() 
	{
		return !TaskSemaphoreMgr.isLocked(getNodeNumberUptoDateLockTitle(), getNodeNumberUptoDateLockName(), TaskSemaphoreMgr.SCOPE.Discipline);
	}
	/**
	 * @param nodeNumbersAreUpToDate the nodeNumbersAreUpToDate to set
	 * 
	 * return true if the set was accomplished.
	 */
	public synchronized boolean setNodeNumbersAreUpToDate(
			boolean nodeNumbersAreUpToDate) 
	{
		boolean current = isNodeNumbersAreUpToDate();
		if (current != nodeNumbersAreUpToDate)
		{
			if (nodeNumbersAreUpToDate == false)
			{
				//let anybody set them out of date.
				return TaskSemaphoreMgr.lock(getNodeNumberUptoDateLockTitle(), getNodeNumberUptoDateLockName(), null,
						TaskSemaphoreMgr.SCOPE.Discipline, true) == TaskSemaphoreMgr.USER_ACTION.OK;
			}
			else
			{
				return TaskSemaphoreMgr.unlock(getNodeNumberUptoDateLockTitle(),
						getNodeNumberUptoDateLockName(),
						TaskSemaphoreMgr.SCOPE.Discipline);
			}
		}
		return true;
	}
	/**
	 * @return the isRenumberingNodes
	 */
	public synchronized boolean isRenumberingNodes() 
	{
		return TaskSemaphoreMgr.isLocked(getNodeNumberingLockTitle(), getNodeNumberingLockName(), TaskSemaphoreMgr.SCOPE.Discipline);
	}
	/**
	 * @param isRenumberingNode the isRenumberingNodes to set
	 * 
	 * return true if the set was accomplished.
	 */
	public synchronized boolean setRenumberingNodes(boolean isRenumberingNodes) 
	{
		boolean current = isRenumberingNodes();
		if (current != isRenumberingNodes)
		{
			if (isRenumberingNodes == true)
			{
				return TaskSemaphoreMgr.lock(getNodeNumberingLockTitle(), getNodeNumberingLockName(), null,
						TaskSemaphoreMgr.SCOPE.Discipline, true) == TaskSemaphoreMgr.USER_ACTION.OK;
			}
			else
			{
				return TaskSemaphoreMgr.unlock(getNodeNumberingLockTitle(),
					getNodeNumberingLockName(),
					TaskSemaphoreMgr.SCOPE.Discipline);
			}
		}
		return true;
	}
	/**
	 * @return the doNodeNumberUpdates
	 */
	public synchronized boolean isDoNodeNumberUpdates() 
	{
		return doNodeNumberUpdates;
	}
	/**
	 * @param doNodeNumberUpdates the doNodeNumberUpdates to set
	 */
	public synchronized void setDoNodeNumberUpdates(boolean doNodeNumberUpdates) 
	{
		this.doNodeNumberUpdates = doNodeNumberUpdates;
	}
	/**
	 * @return the uploadInProgress
	 */
	public synchronized boolean isUploadInProgress() 
	{
		return uploadInProgress;
	}
	/**
	 * @param uploadInProgress the uploadInProgress to set
	 */
	public synchronized void setUploadInProgress(boolean uploadInProgress) 
	{
		this.uploadInProgress = uploadInProgress;
	}
    /**
     * @return title for tree lock.
     */
    protected String getTreeLockTitle()
    {
        return treeDef.getClass().getSimpleName();
    }
    /**
     * @return title for tree lock.
     */
    protected String getNodeNumberingLockTitle()
    {
        return String.format(UIRegistry.getResourceString("BaseTreeDef.numberingNodes"), treeDef.getNodeClass().getSimpleName());
    }
       
    /**
     * @return title for nodenumberuptodate lock.
     */
    protected String getNodeNumberUptoDateLockTitle()
    {
        return String.format(UIRegistry.getResourceString("BaseTreeDef.nodeNumbersInvalid"), treeDef.getNodeClass().getSimpleName());
    }
    
    /**
     * @return  name for nodenumbering lock.
     */
    protected String getNodeNumberingLockName()
    {
        return numberingNodes + treeDef.getNodeClass().getSimpleName();
    }
    
    /**
     * @return name for nodenumberuptodate lock.
     */
    protected String getNodeNumberUptoDateLockName()
    {
        return nodeNumbersInvalid + treeDef.getNodeClass().getSimpleName();
    }

    /**
     * @return name for tree lock.
     */
    protected String getTreeLockName()
    {
        return treeDef.getClass().getSimpleName();
    }
    
    /**
     * @return true if lock succeeds, else return false.
     */
    public boolean lockTree(TaskSemaphoreMgrCallerIFace lockCallback)
    {
		TaskSemaphoreMgr.USER_ACTION action = TaskSemaphoreMgr.lock(getTreeLockTitle(), 
				getTreeLockName(), null, TaskSemaphoreMgr.SCOPE.Discipline, false, lockCallback);
		if (action == USER_ACTION.Override)
		{
			if (TaskSemaphoreMgr.unlock(getTreeLockTitle(), getTreeLockName(), TaskSemaphoreMgr.SCOPE.Discipline))
			{
				action = TaskSemaphoreMgr.lock(getTreeLockTitle(), 
						getTreeLockName(), null, TaskSemaphoreMgr.SCOPE.Discipline, false);
			}
			else
			{
				return false;
			}
		}
    	return action == USER_ACTION.OK;
    }
    
    /**
     * @return true if unlock succeeds, else return false.
     */
    public boolean unlockTree()
    {
		return TaskSemaphoreMgr.unlock(getTreeLockTitle(), getTreeLockName(), 
				TaskSemaphoreMgr.SCOPE.Discipline);
    }
}

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
/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane;

import java.beans.PropertyChangeEvent;
import java.util.Vector;

import edu.ku.brc.af.core.expresssearch.SearchTableConfig;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.specify.tasks.ExpressSearchTask;
import edu.ku.brc.specify.ui.db.ResultSetTableModel;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.util.Pair;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Apr 29, 2008
 *
 */
public class SearchInfoTableResults extends ResultSetTableModel
{
    protected SIQueryForIdResults info;
    protected Vector<Pair<SearchTableConfig, String>> reasonList = null;
    
    
    /**
     * Constructor.
     * @param parentERTP parent
     * @param results results
     */
    public SearchInfoTableResults(final ESResultsTablePanelIFace parentERTP,
                                  final SIQueryForIdResults results)
    {
        super(parentERTP, results);
        
        reasonList = results.getReasonList();
    }
    
    protected void startDataAquisition(final boolean doSequentially)
    {
        SwingWorker workerThread = new SwingWorker()
        {
            @Override
            public Object construct()
            {
                try
                {
                    Thread.sleep(50);
                } catch (Exception ex)
                {
                    edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                    edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(SearchInfoTableResults.class, ex);
                    
                }
                return null;
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public void finished()
            {
                if (propertyListener != null)
                {
                    propertyListener.propertyChange(new PropertyChangeEvent(this, "rowCount", null, reasonList.size()));
                }
                
                if (parentERTP != null)
                {
                    CommandAction cmdAction = new CommandAction(ExpressSearchTask.EXPRESSSEARCH, "SearchComplete", null);
                    cmdAction.setProperty("QueryForIdResultsIFace", results);
                    cmdAction.setProperty("ESResultsTablePanelIFace", parentERTP);
                    CommandDispatcher.dispatch(cmdAction);
                }
            }
        };
        workerThread.start();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.db.ResultSetTableModel#getCacheValueAt(int, int)
     */
    @Override
    public Object getCacheValueAt(int row, int column)
    {
        return getValueAt(row, column);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.db.ResultSetTableModel#getRowCount()
     */
    @Override
    public int getRowCount()
    {
        return reasonList.size();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.db.ResultSetTableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int row, int column)
    {
        Pair<SearchTableConfig, String> pair = reasonList.get(row);
        SearchTableConfig stc = pair.first;
        
        return column == 0 ? stc.getTitle() : pair.second;
    }
    
}

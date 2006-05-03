package edu.ku.brc.specify.stats;

import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.forms.layout.CellConstraints;

import edu.ku.brc.specify.dbsupport.SQLExecutionListener;
import edu.ku.brc.specify.dbsupport.SQLExecutionProcessor;

/**
 *
 * Class to create an entire group from a single query.
 * Groups are typically made up of individual StatItems where each statistic requires it's
 * own query and the usually just the right hand side comes from the query, although the
 * description part can come from the query also. With this class you describe whcih columns in the resultset
 * that the description and value (left and right) comes from.
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class StatGroupTableFromQuery extends StatGroupTable implements SQLExecutionListener
{
    // Static Data Members
    private static Log log = LogFactory.getLog(StatGroupTableFromQuery.class);

    // Data Members
    protected SQLExecutionProcessor sqle;

    protected int    descCol;
    protected int    valCol;
    protected String linkStr = null;
    protected int    colId   = -1;
    protected String noResultsMsg;

    /**
     * Constructor that describes where we get everything from
     * @param name the name or title
     * @param sql the SQL statement to be executed
     * @param descCol the column where the description comes form
     * @param valCol the column where the value comes from
     */
    public StatGroupTableFromQuery(final String name,
                                   final String[] columnNames,
                                   final String sql,
                                   final int    descCol,
                                   final int    valCol,
                                   final String noResultsMsg)
    {
        super(name, columnNames);

        this.descCol = descCol;
        this.valCol  = valCol;
        this.noResultsMsg = noResultsMsg;

        sqle = new SQLExecutionProcessor(this, sql);
        sqle.start();
    }

    /**
     * Constructor that describes where we get everything from
     * @param name the name or title
     * @param sql the SQL statement to be executed
     * @param descCol the column where the description comes form
     * @param valCol the column where the value comes from
     * @param useSeparator use non-border separator titles
     */
    public StatGroupTableFromQuery(final String   name,
                                   final String[] columnNames,
                                   final String   sql,
                                   final int      descCol,
                                   final int      valCol,
                                   boolean        useSeparator,
                                   final String   noResultsMsg)
    {
        super(name, columnNames, useSeparator, 100); // this is an arbitrary number only to tell it to make scrollbars

        this.descCol = descCol;
        this.valCol  = valCol;
        this.noResultsMsg = noResultsMsg;

        sqle = new SQLExecutionProcessor(this, sql);
        sqle.start();
    }

    /**
     * Sets info need to make links
     * @param linkStr the name of the static link
     * @param colId the column of the id which is used to build the link
     */
    public void setLinkInfo(final String linkStr, final int colId)
    {
        this.linkStr = linkStr;
        this.colId   = colId;
    }
    
    /**
     * Removes the table and adds the None Available message
     */
    protected void addNoneAvailableMsg(final String msg)
    {
        JLabel label = new JLabel(noResultsMsg != null ? noResultsMsg : getResourceString("NoneAvail"));
        if (useSeparator)
        {
            builder.getPanel().remove(scrollPane != null ? scrollPane : table);
            builder.add(label, new CellConstraints().xy(1,2));
            
        } else
        {
            remove(scrollPane != null ? scrollPane : table);
            add(label, BorderLayout.CENTER);
         }
    }

    //-----------------------------------------------------
    //-- SQLExecutionListener
    //-----------------------------------------------------


    /* (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.SQLExecutionListener#exectionDone(edu.ku.brc.specify.dbsupport.SQLExecutionProcessor, java.sql.ResultSet)
     */
    public synchronized void exectionDone(final SQLExecutionProcessor processor, final java.sql.ResultSet resultSet)
    {

        List<Object> data = new Vector<Object>();
        try
        {
            if (resultSet.first())
            {
                StringBuffer rowsDef = new StringBuffer();
                do
                {
                    data.add(resultSet.getObject(descCol));
                    data.add(resultSet.getObject(valCol));
                    data.add(colId > 0 ? resultSet.getObject(colId) : null);
                    if (rowsDef.length() > 0)
                    {
                        rowsDef.append(",15dlu,");
                    }
                    rowsDef.append("top:p");

                } while (resultSet.next());

                for (int i=0;i<data.size();i++)
                {
                    String desc     = data.get(i++).toString();
                    String val      = data.get(i++).toString();
                    Object colIdObj = data.get(i);
                    String colId    = colIdObj != null ? colIdObj.toString() : null;
                    StatDataItem statItem = new StatDataItem(desc, linkStr == null || colId == null ? null : (linkStr+",id="+colId), false);
                    statItem.setValStr(val);
                    model.addDataItem(statItem);
                }
                data.clear();
            } else
            {
                addNoneAvailableMsg(noResultsMsg);
            }
            model.fireNewData();
            
        } catch (Exception ex)
        {   
            log.error(ex);
            ex.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.SQLExecutionListener#executionError(edu.ku.brc.specify.dbsupport.SQLExecutionProcessor, java.lang.Exception)
     */
    public synchronized void executionError(final SQLExecutionProcessor processor, final Exception ex)
    {
        addNoneAvailableMsg(getResourceString("GetStatsError"));

    }


}

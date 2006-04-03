package edu.ku.brc.specify.ui.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.datamodel.RecordSetItem;

@SuppressWarnings("serial")
public class ResultSetTableModelDM extends ResultSetTableModel
{
    // Static Data Members
    private static Log log = LogFactory.getLog(ResultSetTableModelDM.class);
   
    private int[] displayIndexes    = null;
    private int[] displayColIndexes = null;
    
    /**
     * Construct with a ResultSet
     * @param resultSet the recordset
     */
    public ResultSetTableModelDM(ResultSet resultSet)
    {
        super(resultSet);
        
    }
    
    /**
     * Returns the number of columns
     * @return Number of columns
     */
    public int getColumnCount()
    {
        try
        {
            return metaData == null ? 0 : (displayColIndexes != null ? displayColIndexes.length : metaData.getColumnCount());
        } catch (SQLException ex)
        {
            log.error("In getColumnCount", ex);
        }
        return 0;
    }

    /**
     * Returns the Class object for a column
     * @param column the column in question
     * @return the Class of the column
     */
    public Class getColumnClass(int column)
    {
        return classNames.size() == 0 ? String.class : 
            (displayColIndexes != null ? (Class)classNames.elementAt(displayColIndexes[column]) : 
                (Class)classNames.elementAt(column));
    }

    /**
     * Get the column name
     * @param column the column of the cell to be gotten
     */
    public String getColumnName(int column)
    {
        if (metaData == null)
        {
            return "N/A";
        }
        
        try
        {
            return displayColIndexes != null ? metaData.getColumnName(displayColIndexes[column]+1) : 
                                               metaData.getColumnName(column+1);          
        } catch (SQLException ex)
        {
            return "N/A";
        }
    }
    
    /**
     * Returns the number of rows
     * @return Number of rows
     */
    public int getRowCount()
    {
      return displayIndexes != null ? displayIndexes.length : numRows;
    }
    
    /**
     * Gets the value of the row col
     * @param row the row of the cell to be gotten
     * @param column the column of the cell to be gotten
     */
    public Object getValueAt(int row, int column)
    {
        
        if (resultSet == null) return null;
        
        try
        {
            if (displayIndexes != null)
            {
                if (!resultSet.absolute(displayIndexes[row]+1))
                {
                    log.error("Error doing resultSet.absolute("+row+")");
                    return null;
                }
               
            } else
            {
                row++;
            
                if (row == 1)
                {
                    if (!resultSet.first())
                    {
                        log.error("Error doing resultSet.first");
                        return null;
                    }
                    currentRow = 1;
                } else
                {
                    if (currentRow+1 == row)
                    {
                        if (!resultSet.next())
                        {
                            log.error("Error doing resultSet.next");
                            return null;
                        }
                        currentRow++;
                    } else
                    {
                        if (!resultSet.absolute(row))
                        {
                            log.error("Error doing resultSet.absolute("+row+")");
                            return null;
                        }
                        currentRow = row;
                    }
                }
                
            }
            
            return displayColIndexes != null ? resultSet.getObject(displayColIndexes[column]+1) : resultSet.getObject(column+1);
            
        } catch (SQLException ex)
        {
            log.error("getValueAt", ex);
            ex.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Initializes the display index data structure
     *
     */
    public void initializeDisplayIndexes()
    {
        displayIndexes    = null;
        //displayColIndexes = null;
    }
 
    /**
     * Sets the display indexes to display only a portion of the recordset
     * @param indexes the array of indexes
     */
    public void addDisplayIndexes(int[] indexes)
    {
        if (indexes != null)
        {
            displayIndexes = indexes;
            this.fireTableDataChanged();
        }
    }
   
    /**
     * Sets the display indexes to display only a portion of the recordset
     * @param indexes the array of indexes
     */
    public void addDisplayColIndexes(int[] indexes)
    {
        if (indexes != null)
        {
            displayColIndexes = indexes;
            this.fireTableDataChanged();
        }
    }
   
    /**
     * Returns a RecordSet object from the table
     * @param rows the selected rows
     * @param column the col that contains the ID
     * @param returnAll indicates whether all the records should be returned if nothing was selected
     * @return Returns a RecordSet object from the table
     */
    public RecordSet getRecordSet(final int[] rows, final int column, final boolean returnAll)
    {
        try
        {
            RecordSet rs = new RecordSet();
            
            Set<RecordSetItem> items = new HashSet<RecordSetItem>();
            rs.setItems(items);
            
            if (rows == null || rows.length == 0)
            {
                /*if (displayIndexes != null)
                {
                    for (int i=0;i<displayIndexes.length;i++)
                    {
                        if (resultSet.absolute(displayIndexes[i]+1))
                        {
                            RecordSetItem rsi = new RecordSetItem();
                            rsi.setRecordId(resultSet.getObject(column+1).toString());
                            items.add(rsi);
                        }
                    }
                } else
                {*/
                
                if (returnAll)
                {
                    if (!resultSet.first())
                    {
                        log.error("Error doing resultSet.first");
                        return null;
                    }                   
                    do
                    {                   
                        RecordSetItem rsi = new RecordSetItem();
                        rsi.setRecordId(resultSet.getObject(column+1).toString());
                        items.add(rsi);
                    } while (resultSet.next());
                }    

                //}
        
            } else
            {
                for (int i=0;i<rows.length;i++)
                {
                    int rowInx = displayIndexes != null ? displayIndexes[rows[i]] : rows[i];
                    if (resultSet.absolute(rowInx+1))
                    {
                        RecordSetItem rsi = new RecordSetItem();
                        rsi.setRecordId(resultSet.getObject(column+1).toString());
                        items.add(rsi);
                    }
                }
            }
            return rs;

        } catch (Exception ex)
        {
            log.error(ex);
        }
        return null;
    }
    

}

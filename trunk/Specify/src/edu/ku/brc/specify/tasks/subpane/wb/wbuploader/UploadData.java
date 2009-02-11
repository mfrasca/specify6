/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.wb.wbuploader;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.specify.datamodel.WorkbenchRow;

/**
 * @author timo
 *
 * A simple pairing of workbench upload mappings with the data in the workbench being uploaded.
 */
public class UploadData
{
    /**
     * Mappings for the columns in the workbench being uploaded.
     */
    Vector<UploadMappingDef> mappings;
    /**
     * The rows of the workbench being uploaded.
     */
    Vector<WorkbenchRow>     wbRows;

    /**
     * @param m
     * @return mapping m
     */
    public UploadMappingDef getMapping(int m)
    {
        return mappings.get(m);
    }

    /**
     * @param r
     * @return WorkbenchRow r
     */
    public WorkbenchRow getWbRow(int r)
    {
        return wbRows.get(r);
    }

    /**
     * @param row
     * @param col
     * @return Workbench data for row and col
     */
    public String get(int row, int col)
    {
        return wbRows.get(row).getData(col);
    }

    /**
     * @param row
     * @return true if all cells in row are blank
     */
    public boolean isEmptyRow(int row)
    {
    	if (row < 0 || row >= getRows())
    	{
    		return true;
    	}
    	for (int col = 0; col < getCols(); col++)
    	{
    		String val = get(row, col);
    		if (!StringUtils.isBlank(val))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    /**
     * @return the number rows
     */
    public int getRows()
    {
        return wbRows.size();
    }

    /**
     * @return number of columns mapped
     */
    public int getCols()
    {
        return mappings.size();
    }

    /**
     * @param mappings
     * @param wbRows
     */
    public UploadData(Vector<UploadMappingDef> mappings, Vector<WorkbenchRow> wbRows)
    {
        this.mappings = mappings;
        this.wbRows = wbRows;
    }
    
    /**
     * @param freshRows - possibly a new collection of rows.
     */
    public void refresh(Vector<WorkbenchRow> freshRows)
    {
        this.wbRows = freshRows;
    }
}

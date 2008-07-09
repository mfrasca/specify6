/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.wb;

import edu.ku.brc.specify.datamodel.WorkbenchDataItem;


/**
 * @author timbo
 *
 * @code_status Alpha
 *
 */
public class DataImportTruncation
{
    protected int    row;
    protected short    col;
    protected int len;
    protected String colHeader;
    protected String originalValue;

    /**
     * @return the originalValue
     */
    public String getOriginalValue()
    {
        return originalValue;
    }

    /**
     * @return the row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @return the col
     */
    public short getCol()
    {
        return col;
    }

    /**
     * @return the colHeader
     */
    public String getColHeader()
    {
        return colHeader;
    }

    /**
     * @return portion of original value that was not imported
     */
    public String getExcluded()
    {
        return this.originalValue.substring(len-1);
    }
    
    public DataImportTruncation(final int row, final short col, final int len, final String colHeader, final String originalValue)
    {
        super();
        this.row = row;
        this.col = col;
        this.len = len;
        this.colHeader = colHeader;
        this.originalValue = originalValue;
     }

}

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

import java.io.IOException;
import java.util.List;

import com.csvreader.CsvWriter;

import edu.ku.brc.specify.datamodel.WorkbenchRow;

/**
 * @author timbo
 *
 * @code_status Alpha
 *
 */
public class CSVExport implements DataExport
{
    ConfigureCSV config;
    
    public CSVExport(final ConfigureExternalData config)
    {
       this.config = (ConfigureCSV)config; 
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.DataExport#getConfig()
     */
    public ConfigureExternalData getConfig()
    {
        return this.config;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.DataExport#setConfig(edu.ku.brc.specify.tasks.subpane.wb.ConfigureExternalData)
     */
    public void setConfig(ConfigureExternalData config)
    {
        this.config = (ConfigureCSV)config; 
    }

    protected void writeHeaders(CsvWriter csv) throws IOException
    {
        try
        {
            csv.writeRecord(config.getHeaders(), true);
        } catch (IOException e)
        {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.ku.brc.specify.tasks.subpane.wb.DataExport#writeData(java.util.List)
     */
    public void writeData(List<?> data) throws Exception
    {
        String[] record;
        CsvWriter writer = new CsvWriter(config.getFileName());
        if (config.getFirstRowHasHeaders() && !config.getAppendData())
        {
            writeHeaders(writer);
        }
        for (int r = 0; r < data.size(); r++)
        {
            WorkbenchRow row = (WorkbenchRow) data.get(r);
            record = new String[row.getItems().size()];
            for (int c = 0; c < row.getItems().size(); c++)
            {
                record[c] = row.getData(c);
            }
            try
            {
                writer.writeRecord(record);
            } catch (IOException e)
            {
                throw(e);
            }
        }
        writer.flush();
    }

}

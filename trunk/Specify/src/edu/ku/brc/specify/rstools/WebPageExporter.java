/**
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */

package edu.ku.brc.specify.rstools;

import java.util.List;
import java.util.Properties;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.datamodel.Taxon;

/**
 * An implementation of {@link RecordSetToolsIFace} that handles {@link List}s and
 * {@link RecordSet}s of {@link Taxon} objects.  The resulting output is an HTML
 * page displaying detailed information about the {@link Taxon} objects.
 * 
 * @author jstewart
 * @code_status Alpha
 */
public class WebPageExporter implements RecordSetToolsIFace
{
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#exportRecordSet(edu.ku.brc.specify.datamodel.RecordSet)
     */
    public void processRecordSet(RecordSet data, Properties reqParams) throws Exception
    {
        int taxonTableId = DBTableIdMgr.getInstance().getIdByClassName(Taxon.class.getName());
        int dataTableId = data.getDbTableId();

        if (dataTableId == taxonTableId)
        {
            throw new Exception("Not yet implemented");
        }
        // else
        
        throw new RuntimeException("Unsupported data type");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getDescription()
     */
    public String getDescription()
    {
        return "Creates web pages to represent the holdings of a collection in a taxonomy tree-based layout.";
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getHandledClasses()
     */
    public Class<?>[] getHandledClasses()
    {
        return new Class<?>[] {Taxon.class};
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getIconName()
     */
    public String getIconName()
    {
        return "WebPage";
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getName()
     */
    public String getName()
    {
        return "Web Page";
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#exportList(java.util.List, java.util.Properties)
     */
    public void processDataList(List<?> data, Properties reqParams) throws Exception
    {
        throw new Exception("Not yet implemented");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporterIFace#isVisible()
     */
    public boolean isVisible()
    {
        return false;
    }
    
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetToolsIFace#getTableIds()
     */
    public Integer[] getTableIds()
    {
        return null;
    }
}

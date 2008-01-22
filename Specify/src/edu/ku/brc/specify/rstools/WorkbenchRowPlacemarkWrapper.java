package edu.ku.brc.specify.rstools;

import java.net.URL;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.specify.datamodel.WorkbenchDataItem;
import edu.ku.brc.specify.datamodel.WorkbenchRow;
import edu.ku.brc.specify.datamodel.WorkbenchTemplateMappingItem;
import edu.ku.brc.util.GeoRefConverter;
import edu.ku.brc.util.Pair;
import edu.ku.brc.util.GeoRefConverter.GeoRefFormat;

/**
 * This class implements the {@link GoogleEarthPlacemarkIFace} interface
 * using the data in a {@link WorkbenchRow} object.
 * 
 * @author jstewart
 * @code_status Beta
 */
public class WorkbenchRowPlacemarkWrapper implements GoogleEarthPlacemarkIFace
{
    /** The {@link WorkbenchRow} containing the actual data. */
    protected WorkbenchRow wbRow;
    
    /** A text label used to identify this placemark. */
    protected String label;
    
    /** The {@link WorkbenchTemplateMappingItem}s describing the row. */
    protected Hashtable<Short, WorkbenchTemplateMappingItem> mappings = null;
    
    /** The data items contained in the row. */
    protected Vector<WorkbenchDataItem> dataList = null;
    
    protected List<WorkbenchTemplateMappingItem>               visibleColumns;
    protected Hashtable<WorkbenchTemplateMappingItem, Boolean> visibleMap = new Hashtable<WorkbenchTemplateMappingItem, Boolean>();

    /**
     * Constructor.
     * 
     * @param row the {@link WorkbenchRow} to represent as a {@link GoogleEarthPlacemarkIFace} object
     * @param label the text label of the resulting placemark
     * @param visibleColumns the columns to use for the HTML
     */
    public WorkbenchRowPlacemarkWrapper(final WorkbenchRow row, 
                                        final String label,
                                        final List<WorkbenchTemplateMappingItem> visibleColumns)
    {
        this.wbRow = row;
        this.label = label;
        this.visibleColumns = visibleColumns;
        
        for (WorkbenchTemplateMappingItem wbtmi : visibleColumns)
        {
            visibleMap.put(wbtmi, true);
        }
    }
    
    /**
     * Initializes all data needed by the {@link GoogleEarthPlacemarkIFace} methods.
     */
    protected void initExportData()
    {
        if (mappings == null)
        {
            mappings = new Hashtable<Short, WorkbenchTemplateMappingItem>();
            for (WorkbenchTemplateMappingItem wbtmi : wbRow.getWorkbench().getWorkbenchTemplate().getWorkbenchTemplateMappingItems())
            {
                mappings.put(wbtmi.getViewOrder(), wbtmi);
            }
        }
        
        if (dataList == null)
        {
            dataList = new Vector<WorkbenchDataItem>(wbRow.getWorkbenchDataItems());
            Collections.sort(dataList);
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.GoogleEarthPlacemarkIFace#cleanup()
     */
    public void cleanup()
    {
        mappings.clear();
        mappings = null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.GoogleEarthPlacemarkIFace#getHtmlContent()
     */
    public String getHtmlContent()
    {
        // XXX In the Future this should call a delegate
        // so it isn't hard code in the class
        
        initExportData();
        
        StringBuilder sb = new StringBuilder("<table>");
        for (WorkbenchDataItem wbdi : dataList)
        {
            WorkbenchTemplateMappingItem wbtmi = mappings.get(wbdi.getColumnNumber());
            if (visibleMap.get(wbtmi) != null && wbtmi.getIsExportableToContent())
            {
                sb.append("<tr><td align=\"right\">");
                sb.append(wbtmi.getTitle());
                sb.append(":</td><td align=\"left\">");
                sb.append(wbdi.getCellData());
                sb.append("</td></tr>\n");
            }
        } 
        sb.append("</table>\n");
        
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.GoogleEarthPlacemarkIFace#getLatLon()
     */
    public Pair<Double, Double> getLatLon()
    {
        GeoRefConverter converter = new GeoRefConverter();
        
        initExportData();
        
        Double latitude  = null;
        Double longitude = null;
        
        for (WorkbenchDataItem wbdi : dataList)
        {
            WorkbenchTemplateMappingItem wbtmi = mappings.get(wbdi.getColumnNumber());
            if (wbtmi.getFieldName().equals("latitude1"))
            {
                String valStr = wbdi.getCellData();
                if (StringUtils.isNotEmpty(valStr))
                {
                    String latStr;
                    try
                    {
                        latStr = converter.convert(valStr, GeoRefFormat.D_PLUS_MINUS.name());
                        latitude = Double.parseDouble(latStr);
                    }
                    catch (Exception e)
                    {
                        latitude = null;
                    }
                }
            } else if (wbtmi.getFieldName().equals("longitude1"))
            {
                String valStr = wbdi.getCellData();
                if (StringUtils.isNotEmpty(valStr))
                {
                    String lonStr;
                    try
                    {
                        lonStr = converter.convert(valStr, GeoRefFormat.D_PLUS_MINUS.name());
                        longitude = Double.parseDouble(lonStr);
                    }
                    catch (Exception e)
                    {
                        longitude = null;
                    }
                }
            }
        }
        
        if (latitude != null && longitude != null)
        {
            return new Pair<Double, Double>(latitude, longitude);
        }
        
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.GoogleEarthPlacemarkIFace#getTitle()
     */
    public String getTitle()
    {
        initExportData();
        return label;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.rstools.GoogleEarthPlacemarkIFace#getIconURL()
     */
    public URL getIconURL()
    {
        // TODO Auto-generated method stub
        return null;
    }

}

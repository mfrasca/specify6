/* Copyright (C) 2009, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.specify.rstools;

import java.util.List;
import java.util.Properties;

import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.services.biogeomancer.GeoCoordDataIFace;
import edu.ku.brc.services.biogeomancer.GeoCoordGeoLocateProvider;
import edu.ku.brc.services.biogeomancer.GeoCoordProviderListenerIFace;
import edu.ku.brc.ui.UIRegistry;

/**
 * Implements the RecordSetToolsIFace for GeoReferenceing with Geolocate.
 * 
 * @author rod
 *
 * @code_status Complete
 *
 * Jan 15, 2008
 *
 */
public class GeoLocateRecordSetProcessor extends GeoRefRecordSetProcessorBase implements GeoCoordProviderListenerIFace
{
    /**
     * Constructor.
     */
    public GeoLocateRecordSetProcessor()
    {
        
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.rstools.GeoRefRecordSetProcessorBase#getGeoRefProviderName()
     */
    @Override
    public String getGeoRefProviderName()
    {
        return "GEOLocate";
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.rstools.GeoRefRecordSetProcessorBase#processDataList(java.util.List, java.util.Properties)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void processDataList(final List<?> items, 
                                final Properties requestParams) throws Exception
    {
        Object listenerObj = requestParams.get("listener");
        GeoCoordProviderListenerIFace listener = listenerObj != null && listenerObj instanceof GeoCoordProviderListenerIFace ? 
                (GeoCoordProviderListenerIFace)listenerObj : null;
        
        GeoCoordGeoLocateProvider geoCoordGLProvider = new GeoCoordGeoLocateProvider();
        geoCoordGLProvider.processGeoRefData((List<GeoCoordDataIFace>)items, listener, "");
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.rstools.GeoRefRecordSetProcessorBase#processRecordSet(edu.ku.brc.dbsupport.RecordSetIFace, java.util.Properties)
     */
    public void processRecordSet(final RecordSetIFace recordSet, 
                                 final Properties requestParams) throws Exception
    {
        processRecordSet(recordSet, requestParams, new GeoCoordGeoLocateProvider());
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporterIFace#getIconName()
     */
    public String getIconName()
    {
        return "GEOLocate";
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporterIFace#getName()
     */
    public String getName()
    {
        return "GEOLocate"; 
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.GeoRefRecordSetProcessorBase#getDescription()
     */
    @Override
    public String getDescription()
    {
        return UIRegistry.getResourceString("GEOLOCATE_DESC");
    }
    
}

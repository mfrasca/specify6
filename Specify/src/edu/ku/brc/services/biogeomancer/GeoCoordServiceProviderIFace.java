/* Copyright (C) 2013, University of Kansas Center for Research
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
package edu.ku.brc.services.biogeomancer;

import java.util.List;

/**
 * An interface that provides Geo-Referencing capability.
 * 
 * @author rod
 *
 * @code_status Alpha
 *
 * Jan 14, 2008
 *
 */
public interface GeoCoordServiceProviderIFace
{
    /**
     * Use the BioGeomancer web service to lookup georeferences foe the given items.
     * @param items the list of items to be GeoRef'ed
     * @param listener a listener for progress
     * @param helpContext provides a help context for any UI that may be presented durng the GeoRef process
     */
    public abstract void processGeoRefData(List<GeoCoordDataIFace> items, 
                                           GeoCoordProviderListenerIFace listener,
                                           String helpContext);
    
}

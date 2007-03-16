/**
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */

package edu.ku.brc.specify.exporters;

import edu.ku.brc.util.Pair;

/**
 * This interface defines the minimum requirements for an object that is to
 * be displayed in Google Earth as a placemark.
 * 
 * @author jstewart
 * @code_status Alpha
 */
public interface GoogleEarthPlacemarkIFace
{
    /**
     * Returns the title of the placemark.
     * 
     * @return the placemark title
     */
    public String getTitle();
    
    /**
     * Returns the HTML content to be displayed in the placemark popup bubble.
     * 
     * @return the HTML content
     */
    public String getHtmlContent();
    
    /**
     * Returns the geolocation of the placemark (latitude, longitude).
     * 
     * @return the lat and lon
     */
    public Pair<Double,Double> getLatLon();
}

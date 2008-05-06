
package edu.ku.brc.services.geolocate.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SnapPointToNearestFoundWaterBody element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="SnapPointToNearestFoundWaterBody">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="LocalityDescription" type="{http://www.museum.tulane.edu/webservices/}LocalityDescription"/>
 *           &lt;element name="WGS84Coordinate" type="{http://www.museum.tulane.edu/webservices/}GeographicPoint"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { //$NON-NLS-1$
    "localityDescription", //$NON-NLS-1$
    "wgs84Coordinate" //$NON-NLS-1$
})
@XmlRootElement(name = "SnapPointToNearestFoundWaterBody") //$NON-NLS-1$
public class SnapPointToNearestFoundWaterBody {

    @XmlElement(name = "LocalityDescription", namespace = "http://www.museum.tulane.edu/webservices/", required = true) //$NON-NLS-1$ //$NON-NLS-2$
    protected LocalityDescription localityDescription;
    @XmlElement(name = "WGS84Coordinate", namespace = "http://www.museum.tulane.edu/webservices/", required = true) //$NON-NLS-1$ //$NON-NLS-2$
    protected GeographicPoint wgs84Coordinate;

    /**
     * Gets the value of the localityDescription property.
     * 
     * @return
     *     possible object is
     *     {@link LocalityDescription }
     *     
     */
    public LocalityDescription getLocalityDescription() {
        return localityDescription;
    }

    /**
     * Sets the value of the localityDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalityDescription }
     *     
     */
    public void setLocalityDescription(LocalityDescription value) {
        this.localityDescription = value;
    }

    /**
     * Gets the value of the wgs84Coordinate property.
     * 
     * @return
     *     possible object is
     *     {@link GeographicPoint }
     *     
     */
    public GeographicPoint getWGS84Coordinate() {
        return wgs84Coordinate;
    }

    /**
     * Sets the value of the wgs84Coordinate property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeographicPoint }
     *     
     */
    public void setWGS84Coordinate(GeographicPoint value) {
        this.wgs84Coordinate = value;
    }

}

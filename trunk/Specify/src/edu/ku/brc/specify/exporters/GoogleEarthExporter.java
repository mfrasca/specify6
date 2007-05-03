/**
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.exporters;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.dbsupport.RecordSetLoader;
import edu.ku.brc.specify.tasks.services.KeyholeMarkupGenerator;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.Pair;
import edu.ku.brc.util.services.GenericKmlGenerator;

/**
 * @author jstewart
 * @code_status Alpha
 */
public class GoogleEarthExporter implements RecordSetExporter
{
    /** Logger for all log messages emitted from this class. */
    private static final Logger log = Logger.getLogger(GoogleEarthExporter.class);
            
	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.tasks.RecordSetExporter#exportRecordSet(edu.ku.brc.specify.datamodel.RecordSet)
	 */
	public void exportRecordSet(RecordSet data, Properties reqParams)
    {
        log.info("Exporting RecordSet");
        int collObjTableId = DBTableIdMgr.getInstance().getIdByClassName(CollectionObject.class.getName());
        int collEvtTableId = DBTableIdMgr.getInstance().getIdByClassName(CollectingEvent.class.getName());
        int dataTableId = data.getDbTableId();

        if (dataTableId == collObjTableId)
        {
            exportCollectionObjectRecordSet(data);
        }
        else if (dataTableId == collEvtTableId)
        {
            exportCollectingEventRecordSet(data);
        }
        else
        {
            throw new RuntimeException("Unsupported data type");
        }
	}
    
    @SuppressWarnings("unchecked")
    public void exportList(List<?> data, Properties reqParams) throws Exception
    {
        log.info("Exporting data list");
        if (data==null || data.size()==0)
        {
            log.warn("Empty or null data list given to GoogleEarthExporter");
            return;
        }
        
        if (data.get(0) instanceof GoogleEarthPlacemarkIFace)
        {
            try
            {
                File tmpFile = File.createTempFile("sp6export", ".kmz");
                tmpFile.deleteOnExit();
                log.info("Writing KML output to " + tmpFile.getAbsolutePath());

                List<GoogleEarthPlacemarkIFace> mappedPlacemarks = exportPlacemarkList((List<GoogleEarthPlacemarkIFace>)data, reqParams, tmpFile);
                if (mappedPlacemarks.size() != data.size())
                {
                    Component parentFrame = UIRegistry.get(UIRegistry.TOPFRAME);
                    String notAllMappedMsg = getResourceString("NOT_ALL_MAPPED");
                    String title = getResourceString("Warning");
                    JOptionPane.showMessageDialog(parentFrame, notAllMappedMsg, title, JOptionPane.WARNING_MESSAGE);
                }
                
                try
                {
                	openExternalViewer(tmpFile);
                }
                catch (Exception e)
                {
                	log.warn("Failed to start external viewer (e.g. Google Earth) for KML file", e);
                    Component parentFrame = UIRegistry.get(UIRegistry.TOPFRAME);
                    String errorMessage = getResourceString("GOOGLE_EARTH_ERROR");
                    String title = getResourceString("Error");
                    JOptionPane.showMessageDialog(parentFrame, errorMessage, title, JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Exception e)
            {
                log.error("Exception caught while creating KML output or opening Google Earth", e);
            }
        }
    }
    
    protected void exportCollectionObjectRecordSet(@SuppressWarnings("unused") RecordSet data)
    {
        log.info("Exporting a RecordSet of CollectionObjects");
        JFrame topFrame = (JFrame)UIRegistry.get(UIRegistry.TOPFRAME);
        Icon icon = IconManager.getIcon(getIconName());
        JOptionPane.showMessageDialog(topFrame, "Not yet implemented", getName() + " data export", JOptionPane.ERROR_MESSAGE, icon);
    }
    
    protected void exportCollectingEventRecordSet(RecordSet data)
    {
        KeyholeMarkupGenerator kmlGen = new KeyholeMarkupGenerator();
        List<Object> records = RecordSetLoader.loadRecordSet(data);
        for (Object o: records)
        {
            CollectingEvent ce = (CollectingEvent)o;
            kmlGen.addCollectingEvent(ce, null);
        }
        File tmpFile;
        try
        {
            tmpFile = File.createTempFile("sp6export", ".kml");
            kmlGen.outputToFile(tmpFile.getAbsolutePath());
            AttachmentUtils.openFile(tmpFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Generates a KML file representing the given list of placemarks.
     * 
     * @param placemarks a List of placemarks to be mapped
     * @param reqParams request parameters
     * @param outputFile the file to put the KML into
     * @return a List of placemarks that were mapped (does not include any passed in placemarks that couldn't be mapped for some reason)
     * @throws IOException an error occurred while writing the KML to the file
     */
    protected List<GoogleEarthPlacemarkIFace> exportPlacemarkList(List<GoogleEarthPlacemarkIFace> placemarks, Properties reqParams, File outputFile) throws IOException
    {
        List<GoogleEarthPlacemarkIFace> mappedPlacemarks = new Vector<GoogleEarthPlacemarkIFace>();
        
        GenericKmlGenerator kmlGenerator = new GenericKmlGenerator();
        
        //kmlGenerator.setBalloonStyleBgColor("AA94590B");
        //kmlGenerator.setBalloonStyleTextColor("FFFFFFFF");
        //kmlGenerator.setBalloonStyleText("<b><font size=\"+3\"><center>$[name]</center></font></b><br/><hr><br/>$[description]<br/><br/><center><b><a style=\"color: white\" href=\"http://www.specifysoftware.org/\">http://www.specifysoftware.org</a></b></center>");

        // see if an icon URL was specified
        Object iconURL = reqParams.getProperty("iconURL");
        if (iconURL!=null && iconURL instanceof String)
        {
            kmlGenerator.setPlacemarkIconURL((String)iconURL);
        }
        else
        {
            kmlGenerator.setPlacemarkIconURL("files/specify32.png");
        }
        
        for (GoogleEarthPlacemarkIFace pm: placemarks)
        {
            String name = pm.getTitle();
            Pair<Double,Double> geoRef = pm.getLatLon();
            if (geoRef != null)
            {
                mappedPlacemarks.add(pm);
                String htmlDesc = pm.getHtmlContent();
                kmlGenerator.addPlacemark(geoRef, name, htmlDesc);
            }
            else
            {
                log.warn("Placemark returned a null geocoordinate");
            }
        }

        kmlGenerator.generateKML(outputFile);
        
        // now we have the KML in outputFile
        // we need to create a KMZ (zip file containing doc.kml and other files)
        
        // get a copy of the icon file
        URL icon = IconManager.getImagePath("specify32.png");
        File iconFile = File.createTempFile("sp6-export-icon-", ".png");
        FileUtils.copyURLToFile(icon, iconFile);
        
        // create a buffer for reading the files
        byte[] buf = new byte[1024];
        int len;
        
        // create the KMZ file
        File outputKMZ = File.createTempFile("sp6-export-", ".kmz");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputKMZ));
        
        // add the doc.kml file to the ZIP
        FileInputStream in = new FileInputStream(outputFile);
        // add ZIP entry to output stream
        out.putNextEntry(new ZipEntry("doc.kml"));
        // copy the bytes
        while ((len = in.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        // complete the entry
        out.closeEntry();
        in.close();
        
        // add a "files" directory to the KMZ file
        ZipEntry filesDir = new ZipEntry("files/");
        out.putNextEntry(filesDir);
        out.closeEntry();
        
        // add the specify32.png file to the ZIP (in the "files" directory)
        in = new FileInputStream(iconFile);
        // add ZIP entry to output stream
        out.putNextEntry(new ZipEntry("files/specify32.png"));
        // copy the bytes
        while ((len = in.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        // complete the entry
        out.closeEntry();
        in.close();

        // complete the ZIP file
        out.close();
        
        // now put the KMZ file where the KML output was
        FileUtils.copyFile(outputKMZ, outputFile);
        
        iconFile.delete();
        outputKMZ.delete();
        
        return mappedPlacemarks;
    }
    
    protected void openExternalViewer(File f) throws Exception
    {
        AttachmentUtils.openFile(f);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.RecordSetExporter#getHandledClasses()
     */
    public Class<?>[] getHandledClasses()
    {
        return new Class<?>[] {CollectionObject.class, CollectingEvent.class, GoogleEarthPlacemarkIFace.class};
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.RecordSetExporter#getName()
     */
    public String getName()
    {
        return getResourceString("GoogleEarth");
    }

    public String getIconName()
    {
        return "GoogleEarth";
    }
    
    public String getDescription()
    {
        return getResourceString("GoogleEarth_Description");
    }
}

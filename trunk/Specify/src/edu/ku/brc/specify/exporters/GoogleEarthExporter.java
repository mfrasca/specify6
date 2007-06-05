/**
 * Copyright (C) 2007  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.exporters;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.dbsupport.RecordSetLoader;
import edu.ku.brc.specify.tasks.services.KeyholeMarkupGenerator;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.util.AttachmentUtils;
import edu.ku.brc.util.Pair;
import edu.ku.brc.util.services.GenericKmlGenerator;

/**
 * An implementation of {@link RecordSetExporter} that produces KML files
 * and opens Google Earth as an external viewer.
 * 
 * @author jstewart
 * @code_status Complete
 */
public class GoogleEarthExporter implements RecordSetExporter
{
    /** Logger for all log messages emitted from this class. */
    private static final Logger log = Logger.getLogger(GoogleEarthExporter.class);
    
    /** The filename of the placemark icon file used if the caller doesn't specify one.  This file must be
     * successfully located using {@link IconManager#getImagePath(String). */
    private static final String DEFAULT_ICON_FILE = "specify32White.png";
            
	/* (non-Javadoc)
	 * @see edu.ku.brc.specify.tasks.RecordSetExporter#exportRecordSet(edu.ku.brc.specify.datamodel.RecordSet)
	 */
	public void exportRecordSet(RecordSet data, Properties reqParams)
    {
        log.info("Exporting RecordSet");
        int collEvtTableId = DBTableIdMgr.getInstance().getIdByClassName(CollectingEvent.class.getName());
        int dataTableId = data.getDbTableId();

        if (dataTableId == collEvtTableId)
        {
            exportCollectingEventRecordSet(data);
        }
        else
        {
            throw new RuntimeException("Unsupported data type");
        }
	}
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#exportList(java.util.List, java.util.Properties)
     */
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
                    UIRegistry.getStatusBar().setErrorMessage(String.format(getResourceString("NOT_ALL_MAPPED"), new Object[] {(data.size() - mappedPlacemarks.size()), data.size()}));
                }
                
                try
                {
                	openExternalViewer(tmpFile);
                }
                catch (Exception e)
                {
                	log.warn("Failed to open external viewer (e.g. Google Earth) for KML file", e);
                    String errorMessage = getResourceString("GOOGLE_EARTH_ERROR");
                    UIRegistry.getStatusBar().setErrorMessage(errorMessage,e);
                }
            }
            catch (Exception e)
            {
                log.error("Exception caught while creating KML output or opening Google Earth", e);
                String errorMessage = getResourceString("KML_EXPORT_ERROR");
                UIRegistry.getStatusBar().setErrorMessage(errorMessage,e);
            }
        }
    }
    
    /**
     * Creates a KML file containing the data found in a {@link RecordSet} of {@link CollectingEvent}s and
     * opens the KML file using the default system viewer (most likely Google Earth).
     * 
     * @param data a {@link RecordSet} of {@link CollectingEvent}s
     */
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
            openExternalViewer(tmpFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Generates a KMZ file representing the given list of placemarks.
     * 
     * @param placemarks a List of placemarks to be mapped
     * @param requestParams request parameters
     * @param outputFile the file to put the KML into
     * @return a List of placemarks that were mapped (does not include any passed in placemarks that couldn't be mapped for some reason)
     * @throws IOException an error occurred while writing the KML to the file
     */
    protected List<GoogleEarthPlacemarkIFace> exportPlacemarkList(List<GoogleEarthPlacemarkIFace> placemarks, Properties requestParams, File outputFile) throws IOException
    {
        List<GoogleEarthPlacemarkIFace> mappedPlacemarks = new Vector<GoogleEarthPlacemarkIFace>();
        
        GenericKmlGenerator kmlGenerator = new GenericKmlGenerator();
        
        // setup all of the general style stuff
        kmlGenerator.setBalloonStyleBgColor("AAB36600");
        String red   = "FF";
        String green = "FF";
        String blue  = "FF";
        String textColor = red + green + blue;
        kmlGenerator.setBalloonStyleTextColor("FF" + blue + green + red);
        kmlGenerator.setBalloonStyleText(
                "<b><font size=\"+3\"><center>$[name]</center></font></b>"
                + "<br/><hr><br/>"
                + "$[description]<br/><br/>"
                + "<center><b><a style=\"color: #" + textColor + "\" href=\"http://www.specifysoftware.org/\">http://www.specifysoftware.org</a></b></center>"
                );

        // get a copy of the default icon file (we (attempt to) package this with all KMZ files)
        URL defaultIconURL = IconManager.getImagePath(DEFAULT_ICON_FILE);
        File defaultIconFile = null;
        if (defaultIconURL != null)
        {
            defaultIconFile = File.createTempFile("sp6-export-icon-", ".png");
            FileUtils.copyURLToFile(defaultIconURL, defaultIconFile);
        }
        // else...
        // the default icon file was not found, we simply have to leave it out of the KMZ file

        // see if an icon URL was specified as a parameter
        Object iconURLparam = requestParams.getProperty("iconURL");
        if (iconURLparam != null && iconURLparam instanceof String)
        {
            kmlGenerator.setPlacemarkIconURL((String)iconURLparam);
        }
        else if (defaultIconFile != null)
        {
            // the caller didn't specify an icon URL on the web, so try to use the default placemark icon
            kmlGenerator.setPlacemarkIconURL("files/specify32.png");
        }
        else
        {
            // the default icon file was not found, so...
            // this will force Google Earth to use the default placemark icon that it provides
            kmlGenerator.setPlacemarkIconURL(null);
        }
        
        // add all of the placemarks to the KML generator
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

        // generate the KML
        kmlGenerator.generateKML(outputFile);
        
        // now we have the KML in outputFile
        // we need to create a KMZ (zip file containing doc.kml and other files)
        
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
        
        if (defaultIconFile != null)
        {
            // add the specify32.png file (default icon file) to the ZIP (in the "files" directory)
            in = new FileInputStream(defaultIconFile);
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
        }

        // complete the ZIP file
        out.close();
        
        // now put the KMZ file where the KML output was
        FileUtils.copyFile(outputKMZ, outputFile);
        
        if (defaultIconFile != null)
        {
            defaultIconFile.delete();
        }
        outputKMZ.delete();
        
        return mappedPlacemarks;
    }
    
    /**
     * Opens the system default viewer for the given file.
     * 
     * @param f
     * @throws Exception
     */
    protected void openExternalViewer(File f) throws Exception
    {
        AttachmentUtils.openFile(f);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.RecordSetExporter#getHandledClasses()
     */
    public Class<?>[] getHandledClasses()
    {
        return new Class<?>[] {CollectingEvent.class, GoogleEarthPlacemarkIFace.class};
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.RecordSetExporter#getName()
     */
    public String getName()
    {
        return getResourceString("GoogleEarth");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getIconName()
     */
    public String getIconName()
    {
        return "GoogleEarth";
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getDescription()
     */
    public String getDescription()
    {
        return getResourceString("GoogleEarth_Description");
    }
}

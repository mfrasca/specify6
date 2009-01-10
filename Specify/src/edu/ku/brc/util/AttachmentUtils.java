/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.util;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;

import edu.ku.brc.specify.datamodel.Attachment;
import edu.ku.brc.specify.datamodel.ObjectAttachmentIFace;
import edu.ku.brc.util.thumbnails.Thumbnailer;

/**
 *
 * @code_status Alpha
 * @author jstewart
 */
public class AttachmentUtils
{
    protected static AttachmentManagerIface attachMgr;
    protected static Thumbnailer thumbnailer;
    
    public static void setAttachmentManager(AttachmentManagerIface mgr)
    {
        attachMgr = mgr;
    }
    
    public static void setThumbnailer(Thumbnailer thumber)
    {
        thumbnailer = thumber;
    }
    
    public static AttachmentManagerIface getAttachmentManager()
    {
        return attachMgr;
    }
    
    public static Thumbnailer getThumbnailer()
    {
        return thumbnailer;
    }
    
    public static ActionListener getAttachmentDisplayer()
    {
        ActionListener displayer = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Object source = e.getSource();
                if (!(source instanceof Attachment) && !(source instanceof ObjectAttachmentIFace))
                {
                    throw new IllegalArgumentException("Passed object must be an Attachment or ObjectAttachmentIFace");
                }
                
                
                Attachment attachment = (source instanceof Attachment) ? (Attachment)source : ((ObjectAttachmentIFace<?>)source).getAttachment();
                File original = null;
                if (attachment.getId()!=null)
                {
                    original = attachMgr.getOriginal(attachment);
                }
                else
                {
                    String origFile = attachment.getOrigFilename();
                    original = new File(origFile);
                }

                try
                {
                    Desktop.getDesktop().open(original);
                }
                catch (IOException e1)
                {
                    edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                    edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(AttachmentUtils.class, e1);
                    e1.printStackTrace();
                }
            }
        };
        
        return displayer;
    }
    
    public static String getMimeType(String filename)
    {
        if (filename==null)
        {
            return null;
        }
        
        MimetypesFileTypeMap mimeMap = (MimetypesFileTypeMap)FileTypeMap.getDefaultFileTypeMap();
        mimeMap.addMimeTypes("image/png    png");
        mimeMap.addMimeTypes("application/vnd.google-earth.kml+xml kml");

        return mimeMap.getContentType(filename);
    }
    
    public static void main(String[] args)
    {
        String[] filenames = {"hello.txt","a.bmp","b.pdf","hello.gif","blha.tiff","c.jpg",null,"blah.kml"};
    
        for (String name: filenames)
        {
            System.out.println(AttachmentUtils.getMimeType(name));
        }
    }
    
    public static void openFile(File f) throws Exception
    {
        Desktop.getDesktop().open(f);
    }
    
    public static void openURI(URI uri) throws Exception
    {
        Desktop.getDesktop().browse(uri);
    }
}

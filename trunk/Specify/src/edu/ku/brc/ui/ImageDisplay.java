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
package edu.ku.brc.ui;

import static edu.ku.brc.ui.UIRegistry.getResourceString;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class is responsible for displaying an image.The ImageDisplay will resize the image approriately to fit within its space
 * and it will keep the image proportional.
 * 
 * @code_status Beta
 * @author rods, jstewart
 *
 */
@SuppressWarnings("serial")
public class ImageDisplay extends JPanel implements GetSetValueIFace
{
    // Error Code
    public static final int kImageOK       = 0;
    public static final int kError         = 1;
    public static final int kHttpError     = 2;
    public static final int kInterruptedError = 3;
    public static final int kIOError       = 4;
    public static final int kURLError      = 5;
    
    private byte[]         bytes           = null;
    
	protected Image        image           = null;
	protected String       url;
	protected boolean      isEditMode      = true;
	protected JButton      editBtn;
	protected ImageGetter  getter          = null;
	protected String       noImageStr      = getResourceString("noimage");
	protected String       loadingImageStr = getResourceString("loadingimage");
	protected boolean      isNoImage       = true;
    protected JFileChooser chooser;
    protected boolean      doShowText      = true;
    
    protected ChangeListener changeListener = null;
    private   int            status         = kImageOK;
    private   ArrayList<File> fileCache = new ArrayList<File>();


	/**
	 * Constructor.
	 * @param imgWidth the desired image width
	 * @param imgHeight the desired image height
	 * @param isEditMode whether it is in browse mode or edit mode
	 * @param hasBorder whether it has a border
	 */
	public ImageDisplay(final int imgWidth, final int imgHeight, boolean isEditMode, boolean hasBorder)
	{
		super(new BorderLayout());
		
		setBorder(hasBorder ? new EtchedBorder(EtchedBorder.LOWERED) : BorderFactory.createEmptyBorder());

		setPreferredSize(new Dimension(imgWidth, imgHeight));

		this.isEditMode = isEditMode;
		createUI();
		
		setDoubleBuffered(true);
	}

	/**
	 * Constructor with ImageIcon.
	 * @param imgIcon the icon to be displayed
	 * @param isEditMode whether it is in browse mode or edit mode
	 * @param hasBorder whether it has a border
	 */
    public ImageDisplay(final ImageIcon imgIcon, boolean isEditMode, boolean hasBorder)
    {
        this(imgIcon.getIconWidth(), imgIcon.getIconHeight(), isEditMode, hasBorder);
        setImage(imgIcon.getImage());
    }

    /**
     * @param imgIcon
     * @param isEditMode
     * @param hasBorder
     */
    public ImageDisplay(final Image imgIcon, boolean isEditMode, boolean hasBorder)
    {
        this(imgIcon.getWidth(null), imgIcon.getHeight(null), isEditMode, hasBorder);
        setImage(imgIcon);
    }

    /**
	 *
	 */
	protected void createUI()
	{
		this.setLayout(new FormLayout("f:p:g" + (isEditMode ? ",2px,p" : ""),"p"));
		CellConstraints cc = new CellConstraints();

		if (isEditMode)
		{
			ImageIcon icon = IconManager.getImage("EditIcon",
					IconManager.IconSize.NonStd);

			editBtn = new JButton(icon);
			editBtn.setMargin(new Insets(1, 1, 1, 1));
			editBtn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					selectNewImage();
				}
			});
			add(editBtn, cc.xy(3, 1));
		}
	}

	/**
     * @return the changeListener
     */
    public ChangeListener getChangeListener()
    {
        return changeListener;
    }
    
    /**
     * @return
     */
    public boolean isInError()
    {
        return status != kImageOK;
    }

    /**
     * @param changeListener the changeListener to set
     */
    public void setChangeListener(ChangeListener changeListener)
    {
        this.changeListener = changeListener;
    }

    protected void selectNewImage()
	{
        String oldURL = this.url;
		synchronized(this)
        {
           if (chooser==null)
           {
            //      XXX Need to add a filter for just images
               chooser = new JFileChooser();
           }
        }

		int returnVal = chooser.showOpenDialog(UIRegistry.getTopWindow());
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = new File(chooser.getSelectedFile().getAbsolutePath());
			try
            {
                setImage(ImageIO.read(file));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            url = file.getAbsolutePath();
			repaint();
		}
        
        firePropertyChange("imageURL", oldURL, url);
	}

	/**
	 * @param newImage
	 */
    public synchronized void setImage(final Image newImage)
    {
        if (newImage != null && 
            newImage.getWidth(null) > 0 &&
            newImage.getHeight(null) > 0)
        {
            image = newImage;
            setNoImage(false);
        } else
        {
            image = null;
            setNoImage(true);
        }
        repaint();

        doLayout();
    }
    
    /**
     * @param newImageIcon
     */
    public synchronized void setImage(final ImageIcon newImageIcon)
    { 
        if (newImageIcon != null)
        {
            setImage(newImageIcon.getImage());
        } else
        {
            setImage((Image)null);
        }
    }
    
    /**
     * 
     */
    private void notifyChangeListener()
    {
        if (changeListener != null)
        {
            changeListener.stateChanged(new ChangeEvent(this));
        }
    }

	/**
	 *
	 */
	public void loadImage()
	{
	    status = kImageOK;
		if (isNotEmpty(url))
		{
			setNoImage(false); // means it is loading it
			repaint();

			if (getter != null)
			{
			    getter.setManualStop(true);
				getter.stop();
				//getter.setManualStop(false);
			}

			if (url.startsWith("file"))
			{
				try
				{
					File f = new File(new URI(url));
					Image img = ImageIO.read(f);
					setImage(img);
					repaint();
					notifyChangeListener();
					return;
					
				} catch (Exception e)
				{
				    status = kURLError;
				    edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
				    edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(ImageDisplay.class, e);
					e.printStackTrace();
				}
			}
			getter = new ImageGetter(url);
			getter.start();

		} else
		{
		    status = kURLError;
			setNoImage(true);
			setImage((Image)null);
			notifyChangeListener();
		}
		repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		boolean doScale = true;

		int w = getWidth();
		int h = getHeight();

		if (image != null && (!isNoImage && status == kImageOK))
		{
			int imgW = image.getWidth(null);
			int imgH = image.getHeight(null);

			if (doScale && (imgW > w || imgH > h))
			{
				double scaleW = 1.0;
				double scaleH = 1.0;
				double scale  = 1.0;

				if (imgW > w)
				{
					scaleW = (double) w / imgW;
				}
				if (imgH > h)
				{
					scaleH = (double) h / imgH;
				}
				scale = Math.min(scaleW, scaleH);

				imgW = (int) (imgW * scale);
				imgH = (int) (imgH * scale);
			}

			int x = 0;
			int y = 0;

			if (imgW < w)
			{
				x = (w - imgW) / 2;
			}
			if (imgH < h)
			{
				y = (h - imgH) / 2;
			}
			g.drawImage(image, x, y, imgW, imgH, null);
			
		} else if (doShowText)
		{
			String label = this.isNoImage ? noImageStr : loadingImageStr;
			FontMetrics fm = g.getFontMetrics();
			g.drawString(label, (w - fm.stringWidth(label)) / 2, (h - fm.getAscent()) / 2);
		}
	}

	public void setNoImage(boolean isNoImage)
	{
		this.isNoImage = isNoImage;
		repaint();
	}

	//--------------------------------------------------------------
	//-- GetSetValueIFace
	//--------------------------------------------------------------

	/* (non-Javadoc)
	 * @see edu.ku.brc.ui.GetSetValueIFace#setValue(java.lang.Object, java.lang.String)
	 */
	public void setValue(Object value, String defaultValue)
	{
		if (value instanceof String)
		{
			url = (String) value;
			loadImage();
		}
	}

	/* (non-Javadoc)
	 * @see edu.ku.brc.ui.GetSetValueIFace#getValue()
	 */
	public Object getValue()
	{
		return url;
	}

	/**
     * @return the doShowText
     */
    public boolean isDoShowText()
    {
        return doShowText;
    }
    
    /**
     * @param doShowText the doShowText to set
     */
    public void setDoShowText(boolean doShowText)
    {
        this.doShowText = doShowText;
    }

    //--------------------------------------------------------------
	//-- Inner Class JPanel for displaying an image
	//--------------------------------------------------------------
	public class ImageGetter implements Runnable
	{

		private Thread thread = null;
		private String urlStr;
		private boolean isManualStop = false;
		
		/**
		 * @param urlStr
		 */
		public ImageGetter(String urlStr)
		{
			this.urlStr = urlStr;
		}

		/**
         * @return the status
         */
        public int getStatus()
        {
            return status;
        }

        //----------------------------------------------------------------
		//-- Runnable Interface
		//----------------------------------------------------------------
		public void start()
		{
		    //System.out.println("Calling Start");
			if (thread == null)
			{
				thread = new Thread(this);
				thread.start();
			}
		}

		/**
         * @param isManualStop the isManualStop to set
         */
        public void setManualStop(boolean isManualStop)
        {
            this.isManualStop = isManualStop;
        }

        /**
		 *
		 *
		 */
		public synchronized void stop()
		{
		    //System.out.println("Calling Stop: "+isManualStop);
			if (thread != null)
			{
				thread.interrupt();
			}
			
			if (!isManualStop)
			{
			    status = kInterruptedError;
			    notifyChangeListener();
			}
			
			thread = null;
			notifyAll();
		}

		/**
		 *
		 */
		public void run()
		{
			try
			{
				setNoImage(false); // means it is loading it

			    File localFile = getFileFromWeb(urlStr);
				if (localFile != null)
				{
				    localFile.deleteOnExit();
				    
				    setImage(ImageIO.read(localFile));
				    status = kImageOK;
				    //System.out.println("Got Image");
				    
				    if (fileCache.size() > 2)
				    {
				        File f = fileCache.get(0);
				        fileCache.remove(0);
				        f.delete();
				    }
				    fileCache.add(localFile);
				    
				} else
				{
				    status = kIOError;
				    //System.out.println("Image Error1");
				}

				getter = null;

            } catch (MalformedURLException e)
            {
                status = kURLError;
                e.printStackTrace();
                //System.out.println("Image Error2");
                
            } catch (Exception e)
            {
                status = kError;
			    e.printStackTrace();
			    //System.out.println("Image Error3");
			}
            notifyChangeListener();
		}
	}
	
    /**
     * @param urlStr
     * @param tmpFile
     * @return
     */
    private boolean fillFileFromWeb(final String urlStr, 
                                    final File tmpFile)
    {
        if (bytes == null)
        {
            bytes = new byte[100*1024];
        }
        
        try
        {
            URL urlObj = new URL(urlStr);
            InputStream inpStream = urlObj.openStream();
            if (inpStream != null)
            {
                BufferedInputStream  in  = new BufferedInputStream(inpStream);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
                
                do
                {
                    int numBytes = in.read(bytes);
                    if (numBytes == -1)
                    {
                        break;
                    }
                    bos.write(bytes, 0, numBytes);
                    
                } while(true);
                in.close();
                bos.close();
            
                return true;
            }
            
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * @param fileName
     * @param mimeType
     * @param isThumb
     * @return
     */
    private synchronized File getFileFromWeb(final String urlStr)
    {
        try
        {
            File tmpFile = File.createTempFile("sp6", ".img", null);
            return fillFileFromWeb(urlStr, tmpFile) ? tmpFile : null;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

}
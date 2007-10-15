/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.datamodel;

import java.lang.ref.WeakReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.ImageIcon;

/**
 * A data class to hold images corresponding to WorkbenchRows.
 * 
 * Note: this class has a natural ordering that is inconsistent with equals (as described be {@link Comparable#compareTo(Object)}.
 *
 * @author jstewart
 * @code_status Alpha
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert=true, dynamicUpdate=true)
@Table(name = "workbenchrowimage")
@org.hibernate.annotations.Proxy(lazy = false)
public class WorkbenchRowImage implements java.io.Serializable, Comparable<WorkbenchRowImage>
{
    protected Integer         workbenchRowImageId;
    protected Integer      imageOrder;
    protected byte[]       cardImageData;
    protected String       cardImageFullPath;
    protected WorkbenchRow workbenchRow;
    protected WeakReference<ImageIcon> fullSizeImageWR = null;
    protected ImageIcon thumbnail = null;
    
    /**
     * Constructor (for JPA compliance).
     */
    public WorkbenchRowImage()
    {
        //
    }
    
    public void initialize()
    {
        workbenchRowImageId = null;
        imageOrder          = null;
        cardImageData       = null;
        cardImageFullPath   = null;
        workbenchRow        = null;
    }

    @Id
    @GeneratedValue
    @Column(name = "WorkbenchRowImageID", nullable = false)
    public Integer getWorkbenchRowImageId()
    {
        return workbenchRowImageId;
    }

    public void setWorkbenchRowImageId(Integer workbenchRowImageId)
    {
        this.workbenchRowImageId = workbenchRowImageId;
    }
    
    /**
     * Generic Getter for the ID Property.
     * @returns ID Property.
     */
    @Transient
    public Integer getId()
    {
        return this.workbenchRowImageId;
    }
    
    @Lob
    @Column(name = "CardImageData", length=16000000)
    public byte[] getCardImageData()
    {
        return cardImageData;
    }

    public void setCardImageData(byte[] cardImageData)
    {
        this.cardImageData = cardImageData;
    }

    @Column(name="CardImageFullPath", length=255)
    public String getCardImageFullPath()
    {
        return cardImageFullPath;
    }

    public void setCardImageFullPath(String cardImageFullPath)
    {
        this.cardImageFullPath = cardImageFullPath;
    }

    @Column(name = "ImageOrder")
    public Integer getImageOrder()
    {
        return imageOrder;
    }

    public void setImageOrder(Integer imageOrder)
    {
        this.imageOrder = imageOrder;
    }

    @ManyToOne
    @JoinColumn(name = "WorkbenchRowID", nullable = false)
    public WorkbenchRow getWorkbenchRow()
    {
        return workbenchRow;
    }

    public void setWorkbenchRow(WorkbenchRow workbenchRow)
    {
        this.workbenchRow = workbenchRow;
    }
    
    @Override
    public String toString()
    {
        return imageOrder + ": " + cardImageFullPath;
    }
    
    ////////////////////////////////////
    // Helper methods
    ////////////////////////////////////
    
    @Transient
    public ImageIcon getImage()
    {
        if (cardImageData != null)
        {
            return new ImageIcon(cardImageData);
        }
        
        return null;
    }
    
    @Transient
    public ImageIcon getFullSizeImage()
    {
        if (cardImageData != null && cardImageFullPath != null && cardImageFullPath.length()>0)
        {
            ImageIcon fullSizeImage = null;
            
            // try to get the image from the WeakReference
            if (fullSizeImageWR != null)
            {
                fullSizeImage = fullSizeImageWR.get();
            }
            
            // if the image is still null, reload the WeakReference
            if (fullSizeImage == null)
            {
                ImageIcon iconImage = new ImageIcon(cardImageFullPath);
                fullSizeImageWR = new WeakReference<ImageIcon>(iconImage);
            }
            
            return fullSizeImageWR.get();
        }
        return null;
    }
    
    @Transient
    public ImageIcon getThumbnail()
    {
        return thumbnail;
    }
    
    public void setThumbnail(ImageIcon thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(WorkbenchRowImage o)
    {
        if (o == null)
        {
            return 1;
        }
        
        return this.getImageOrder().compareTo(o.getImageOrder());
    }
}
/* Filename:    $RCSfile: NavBoxLayoutManager.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2005/10/19 19:59:54 $
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.specify.core;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.Vector;
import java.util.List;

/**
 * The layout manager for laying out NavBoxes in a vertical fashion.
 * 
 * @author rods
 *
 */
public class NavBoxLayoutManager implements LayoutManager
{
    
    private Vector<Component> comps         = new Vector<Component>();
    private Dimension         preferredSize = new Dimension();
    private int               borderPadding = 2;
    private int               ySeparation   = 5;

    /**
     * Contructs a layout manager for layting out NavBoxes. It lays out all the NavBoxes vertically 
     * and uses the 'ySeparator' as the spacing in between the boxes. It uses borderPadding as a 'margin'
     * aroound all the boxes
     * @param borderPadding the margin around the boxes
     * @param ySeparation the vertical separation inbetween the boxes.
     */
    public NavBoxLayoutManager(final int borderPadding, final int ySeparation)
    {
        this.borderPadding = borderPadding;
        this.ySeparation   = ySeparation;
    }
    
    /* (non-Javadoc)
     * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
     */
    public void addLayoutComponent(String arg0, Component arg1)
    {
        if (arg1 == null)
        {
            throw new NullPointerException("Null component in addLayoutComponent");
        }
        comps.addElement(arg1);

    }

    /* (non-Javadoc)
     * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
     */
    public void removeLayoutComponent(Component arg0)
    {
        if (arg0 == null)
        {
            throw new NullPointerException("Null component in removeLayoutComponent");
        }
      comps.removeElement(arg0);

    }

    /* (non-Javadoc)
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    public Dimension preferredLayoutSize(Container arg0)
    {
        return new Dimension(preferredSize);
    }

    /* (non-Javadoc)
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    public Dimension minimumLayoutSize(Container arg0)
    {
         return new Dimension(preferredSize);
    }

    /* (non-Javadoc)
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    public void layoutContainer(Container arg0)
    {
        Dimension parentSize =  ((NavBoxMgr)arg0).getSize();
        parentSize.width  -= 2 * borderPadding;
        parentSize.height -= 2 * borderPadding;
        
        int x = borderPadding;
        int y = borderPadding;
        
        for (Component comp : comps)
        {
            Dimension size = comp.getPreferredSize();
            comp.setBounds(x, y, parentSize.width, size.height);
            y += size.height + ySeparation;
        }

    }
    
    /**
     * Calculates the preferred size of the contain. It lays out all the NavBoxes vertically 
     * and uses the 'ySeparator' as the spacing in between the boxes. It uses borderPadding as a 'margin'
     * aroound all the boxes
     *
     */
    protected void calcPreferredSize()
    {
        preferredSize.setSize(borderPadding*2, borderPadding);
        
        for (Component comp : comps)
        {
            Dimension size = comp.getPreferredSize();
            preferredSize.width = Math.max(preferredSize.width, size.width + (2 * borderPadding));
            preferredSize.height += size.height + ySeparation;
        }
        preferredSize.height -= ySeparation;
    }
    
    /**
     * Return the list of all the components that have been added to the alyout manager
     * @return the list of all the components that have been added to the alyout manager
     */
    public List<Component> getComponentList()
    {
        return comps;
    }
    
    /*
     * Remove all the componets that have been added to the layout manager
     */
    public void removeAll()
    {
        comps.clear();
    }

}

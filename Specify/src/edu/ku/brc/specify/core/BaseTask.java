/* Filename:    $RCSfile: BaseTask.java,v $
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

import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import edu.ku.brc.specify.plugins.MenuItemDesc;
import edu.ku.brc.specify.plugins.TaskPluginable;
import edu.ku.brc.specify.plugins.ToolBarItemDesc;
import edu.ku.brc.specify.ui.IconManager;
import edu.ku.brc.specify.ui.SubPaneIFace;
import edu.ku.brc.specify.ui.ToolBarDropDownBtn;
import edu.ku.brc.specify.ui.UICacheManager;


public abstract class BaseTask implements Taskable, TaskPluginable
{
    // Data Members
    protected final String        name;
    
    protected Vector<NavBoxIFace> navBoxes = new Vector<NavBoxIFace>(); 
    protected Icon                icon     = null;
    
    public BaseTask(final String name)
    {
        this.name = name;
    }
    
     /**
     * Helper
     * @param catName
     * @param imageName
     * @param hint
     * @return
     */
    protected ToolBarDropDownBtn createToolbarButton(final String catName,
                                                     final String imageName,
                                                     final String hint)
    {
        String name = getResourceString(catName);
        
        ImageIcon icon32 = IconManager.getInstance().register(name, imageName, IconManager.IconSize.Std32);
        ImageIcon icon24 = IconManager.getInstance().getIcon(name, IconManager.IconSize.Std24);
        ImageIcon icon16 = IconManager.getInstance().getIcon(name, IconManager.IconSize.Std16);
        
        icon = icon16;
        
        ToolBarDropDownBtn btn = new ToolBarDropDownBtn(name, icon24, JButton.BOTTOM);
        btn.setStatusBarHintText(getResourceString(hint));
        
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                requestContext();
            }
        });
        return btn;
        
    }
    
    public abstract SubPaneIFace getStarterPane();

    /**
     * 
     *
     */
    protected void requestContext()
    {
        ContextMgr.getInstance().requestContext(this);
        
        UICacheManager.getInstance().getSubPaneMgr().addPane(getStarterPane());
    }
    
    //-------------------------------------------------------
    // Taskable
    //-------------------------------------------------------
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#getNavBoxes()
     */
    public java.util.List<NavBoxIFace> getNavBoxes()
    {
        return navBoxes;
    }
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#getIcon()
     */
    public Icon getIcon()
    {
        return icon;
    }
    
    //-------------------------------------------------------
    // Plugin Interface
    //-------------------------------------------------------
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getToolBarItems()
     */
    public String getName()
    {
        return name;
    }
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getToolBarItems()
     */
    public abstract List<ToolBarItemDesc> getToolBarItems();
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getMenuItems()
     */
    public abstract List<MenuItemDesc> getMenuItems();

}

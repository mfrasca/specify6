/* Filename:    $RCSfile: DataEntryTask.java,v $
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

import java.awt.event.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.JButton;

import edu.ku.brc.specify.core.subpane.*;
import edu.ku.brc.specify.plugins.MenuItemDesc;
import edu.ku.brc.specify.plugins.TaskPluginable;
import edu.ku.brc.specify.plugins.ToolBarItemDesc;
import edu.ku.brc.specify.ui.IconManager;
import edu.ku.brc.specify.ui.SubPaneIFace;
import edu.ku.brc.specify.ui.ToolBarDropDownBtn;
import edu.ku.brc.specify.ui.UICacheManager;
import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;


public class DataEntryTask extends BaseTask
{
    /**
     * 
     *
     */
    public DataEntryTask()
    {
        super(UICacheManager.getResourceString("Data_Entry"));
        
        // Temporary
        NavBox navBox = new NavBox(getResourceString("Actions"));
        navBox.add(NavBox.createBtn(getResourceString("Series_Processing"), name, IconManager.IconSize.Std16, new DataEntryAction("")));
        navBoxes.addElement(navBox);
        
        navBox = new NavBox(getResourceString("Create"));
        navBox.add(NavBox.createBtn(name, name, IconManager.IconSize.Std16));
        navBoxes.addElement(navBox);
        
    }
    
    /**
     * 
     * @param sqlStr
     */
    public void openForm(final String formName)
    {
        DataEntryPane formPane = new DataEntryPane(name, this);
        UICacheManager.getInstance().getSubPaneMgr().addPane(formPane);

    }
    
    /**
     * @return the initial pane
     */
    public SubPaneIFace getStarterPane()
    {
        return new SimpleDescPane(name, this, "This is the Data Entry Pane");
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
        return "Data_Entry"; // XXX Localize
    }
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getToolBarItems()
     */
    public List<ToolBarItemDesc> getToolBarItems()
    {
        Vector<ToolBarItemDesc> list = new Vector<ToolBarItemDesc>();
        
        ToolBarDropDownBtn btn = createToolbarButton("Data_Entry",   "dataentry.gif",    "dataentry_hint");
       
        list.add(new ToolBarItemDesc(btn.getCompleteComp()));
        
        return list;
    }
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getMenuItems()
     */
    public List<MenuItemDesc> getMenuItems()
    {
        Vector<MenuItemDesc> list = new Vector<MenuItemDesc>();
        return list;
        
    }
    
    //--------------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------------
    
    /**
     * 
     * @author rods
     *
     */
    class DataEntryAction implements ActionListener 
    {
        private String formName;
        
        public DataEntryAction(final String formName)
        {
            this.formName = formName;
        }
        public void actionPerformed(ActionEvent e) 
        {
            openForm(formName);
        }
    }


}

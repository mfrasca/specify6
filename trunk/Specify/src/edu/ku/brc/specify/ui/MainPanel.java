/* Filename:    $RCSfile: MainPanel.java,v $
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
package edu.ku.brc.specify.ui;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import edu.ku.brc.specify.core.NavBoxMgr;

public class MainPanel extends JSplitPane
{

    private NavBoxMgr     navBoxMgr;
    private SubPaneMgr    subPaneMgr;
    
    /**
     * Default Constructor
     *
     */
    public MainPanel()
    {
        super(JSplitPane.HORIZONTAL_SPLIT);
        
        navBoxMgr  =  NavBoxMgr.getInstance();
        subPaneMgr = new SubPaneMgr();
        UICacheManager.getInstance().setSubPaneMgr(subPaneMgr);
        
        this.setLeftComponent(navBoxMgr);
        this.setRightComponent(subPaneMgr);
        
        setTabPlacement(JTabbedPane.BOTTOM);  // PREF
        this.setDividerLocation(175);         // PREF
    }
    
    /**
     * Enables the tab place to be set externally (like from prefs)
     * @param placement the placement of the tabs Top, Bottom, Left or Right (SwingConstants)
     */
    public void setTabPlacement(int placement)
    {
        subPaneMgr.setTabPlacement(placement);
    }
    
    /**
     * Adds a panel to the tab control and then the panel is asked to regiester all of it's Command tabs
     * @param comp the component being added
     * @return the same panel
     */
    public JComponent addSubPanel(SubPaneIFace comp)
    {
        subPaneMgr.addPane(comp);
        
        navBoxMgr.invalidate();
        subPaneMgr.invalidate();

        return comp.getUIComponent();
    }
    
    /**
     * Removes a Panel from the Tab control, the Panel is then asked to un register it's Command Tabs (boxes)
     * @param comp
     */
    public void removeSubPanel(SubPaneIFace comp)
    {
        subPaneMgr.removePane(comp);
    }
    
    public void showPane(String name)
    {
        subPaneMgr.showPane(name);
    }
    
    
    
    
}

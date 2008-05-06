/* This library is free software; you can redistribute it and/or
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
package edu.ku.brc.af.core;

import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;


/**
 * The MainPanel contains a splitter that divdes the window into two parts, the NavBox area and the TabbedPane area.<br><br>
 * The NavBox area is managed by the NavBoxMgr and the Sub pane area is managed by the SubPaneMgr.
 *
 * @code_status Complete
 * 
 * @author rods
 *
 */
@SuppressWarnings("serial") //$NON-NLS-1$
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
        //setBackground(Color.WHITE);
        
        setOneTouchExpandable(true);
        
        navBoxMgr  = NavBoxMgr.getInstance();
        subPaneMgr = SubPaneMgr.getInstance();
        
        this.setLeftComponent(navBoxMgr);
        this.setRightComponent(subPaneMgr);
        
        setTabPlacement(SwingConstants.BOTTOM);  // PREF
        this.setDividerLocation(0);         // PREF
        this.setLastDividerLocation(175);
        navBoxMgr.setSplitPane(this);
    }
    
    /**
     * Enables the tab place to be set externally (like from prefs).
     * @param placement the placement of the tabs Top, Bottom, Left or Right (SwingConstants)
     */
    public void setTabPlacement(int placement)
    {
        subPaneMgr.setTabPlacement(placement);
    }
    
    /**
     * Adds a panel to the tab control and then the panel is asked to regiester all of it's Command tabs.
     * @param subpanel the component being added
     * @return the same subpanel
     */
    public JComponent addSubPanel(final SubPaneIFace subpanel)
    {
        subPaneMgr.addPane(subpanel);
        
        navBoxMgr.invalidate();
        subPaneMgr.invalidate();

        return subpanel.getUIComponent();
    }
    
    /**
     * Removes a Panel from the Tab control, the Panel is then asked to un register it's Command Tabs (boxes).
     * @param subpanel the subpanel to be removed
     */
    public void removeSubPanel(final SubPaneIFace subpanel)
    {
        subPaneMgr.removePane(subpanel);
    }
    
    /**
     * Show subpane by name
     * @param name the name of the subpane
     */
    public void showPane(final String name)
    {
        subPaneMgr.showPane(name);
    }
    
    
    
    
}

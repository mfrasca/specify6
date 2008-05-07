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
package edu.ku.brc.af.tasks;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.ku.brc.af.core.MenuItemDesc;
import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;
import edu.ku.brc.af.core.ToolBarItemDesc;
import edu.ku.brc.af.tasks.subpane.StatsPane;
import edu.ku.brc.ui.GraphicsUtils;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.ToolBarDropDownBtn;

/**
 * This task reads an xml definition describing the content and the layout of statistics for the
 * startup page of the application.
 
 * @code_status Complete
 **
 * @author rods
 *
 */
/**
 * @author rods
 *
 * @code_status Alpha
 *
 */
public class StartUpTask extends BaseTask
{
    public static final String STARTUP = "Startup"; //$NON-NLS-1$

    protected Vector<ToolBarDropDownBtn> tbList     = new Vector<ToolBarDropDownBtn>();
    protected SubPaneIFace               blankPanel = null;

    // XXX Demo Only
    StatsPane statPane;


    /**
     * Default Constructor
     *
     */
    public StartUpTask()
    {
        super(STARTUP, getResourceString(STARTUP));
        icon = IconManager.getImage(STARTUP, IconManager.IconSize.Std16);
    }
    
    /**
     * @return
     */
    public static JPanel createSplashPanel()
    {
        Image img = GraphicsUtils.getScaledImage(IconManager.getIcon("SpecifySplash"), 300, 500, true);
        JPanel splashPanel = new JPanel(new BorderLayout());
        splashPanel.add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
        return splashPanel;
    }

    /**
     * Creates the StartUP Statistics pane and removes the blank pane.
     */
    public void createStartUpStatPanel()
    {
        StatsPane pane = new StatsPane(title, this, "StartUpPanel", true, null, createSplashPanel()); //$NON-NLS-1$
        SubPaneMgr.getInstance().removePane(blankPanel);
        SubPaneMgr.getInstance().addPane(pane);
        blankPanel = null;
    }

    /**
     * Returns the blank SubPane or null.
     * @return the blank SubPane or null
     */
    public SubPaneIFace getBlankPane()
    {
        return blankPanel;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.BaseTask#getStarterPane()
     */
    @Override
    public SubPaneIFace getStarterPane()
    {
        if (starterPane == null)
        {
            starterPane = new StatsPane(title, this, "StartUpPanel", true, null, createSplashPanel()); //$NON-NLS-1$
        }
        return starterPane;
    }

    //-------------------------------------------------------
    // BaseTask (Taskable Interface)
    //-------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#getToolBarItems()
     */
    @Override
    public List<ToolBarItemDesc> getToolBarItems()
    {
        return new Vector<ToolBarItemDesc>();

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#getMenuItems()
     */
    @Override
    public List<MenuItemDesc> getMenuItems()
    {
        return new Vector<MenuItemDesc>();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.tasks.BaseTask#getTaskClass()
     */
    @Override
    public Class<? extends StartUpTask> getTaskClass()
    {
        return this.getClass();
    }

}

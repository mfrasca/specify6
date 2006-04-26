/* Filename:    $RCSfile: ReportsTask.java,v $
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
package edu.ku.brc.specify.tasks;

import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import edu.ku.brc.specify.core.NavBox;
import edu.ku.brc.specify.helpers.XMLHelper;
import edu.ku.brc.specify.plugins.MenuItemDesc;
import edu.ku.brc.specify.plugins.ToolBarItemDesc;
import edu.ku.brc.specify.stats.ChartPanel;
import edu.ku.brc.specify.stats.StatsMgr;
import edu.ku.brc.specify.tasks.subpane.SimpleDescPane;
import edu.ku.brc.specify.tasks.subpane.StatsPane;
import edu.ku.brc.specify.ui.IconManager;
import edu.ku.brc.specify.ui.SubPaneIFace;
import edu.ku.brc.specify.ui.ToolBarDropDownBtn;
/**
 * The StatsTask is responsible gettng and displaying all various idfferent kinds of stats
 * 
 * @author rods
 *
 */
public class StatsTask extends BaseTask
{
    // Static Data Members
    public static final String STATISTICS = "Statistics";
    
    private static Log log = LogFactory.getLog(StatsTask.class);
    
    protected static final String DISPLAY   = "display";
    protected static final String BAR_CHART = "bar chart";
    protected static final String PIE_CHART = "pie chart";
    protected static final String TABLE     = "table";
    protected static final String FORM      = "form";
    
    
    // Data Members
     protected Element panelDOM;

    /**
     * Creates a Statistics Tasks
     *
     */
    public StatsTask()
    {
        super(STATISTICS, getResourceString(STATISTICS));
        
        try
        {
            panelDOM = XMLHelper.readDOMFromConfigDir("statistics_panel.xml");   // contains a description of the NavBoxes
            
        } catch (Exception ex)
        {
            log.error(ex);
        }

        // Process the NavBox Panel and create all the commands
        // XXX This needs to be made generic so everyone can use it
        // 
        List boxes = panelDOM.selectNodes("/boxes/box");
        for ( Iterator iter = boxes.iterator(); iter.hasNext(); ) 
        {
            Element box = (Element) iter.next();
            NavBox navBox = new NavBox(box.attributeValue("title"));
            
            List items = box.selectNodes("item");
            for ( Iterator iter2 = items.iterator(); iter2.hasNext(); ) 
            {
                Element item = (Element) iter2.next();
                String boxName  = item.attributeValue("name");
                String boxTitle = item.attributeValue("title");
                String type     = item.attributeValue("type");
                ActionListener action = null;
                if (type.toLowerCase().equals(PIE_CHART))
                {
                    type = "Pie_Chart";
                    action = new DisplayAction(boxName);
                    
                } else if (type.toLowerCase().equals(BAR_CHART))
                {
                    type = "Bar_Chart";
                    action = new DisplayAction(boxName);
                }
                
                navBox.add(NavBox.createBtn(boxTitle, type, IconManager.IconSize.Std16, action));
           } 
           navBoxes.addElement(navBox);
        }    
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    public SubPaneIFace getStarterPane()
    {
        return new StatsPane(name, this, "stats_summary_panel.xml", false, null);
    }
    
    /**
     * Looks up statName and creates the appropriate SubPane 
     * @param statName the name of the stat to be displayed
     */
    public void createStatPane(final String statName)
    {
        // Create stat pane return a non-null panel for charts and null for non-charts
        // Of coarse, it could pass back nul if a chart was missed named
        // but error would be shown inside the StatsMgr for that case
        JPanel panel = StatsMgr.createStatPane(statName);
        if (panel != null)
        {
            SimpleDescPane pane = new SimpleDescPane(name, this, panel);
            addSubPaneToMgr(pane);
        }

        

    }

    
    //-------------------------------------------------------
    // Plugin Interface
    //-------------------------------------------------------
    
    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.TaskPluginable#getToolBarItems()
     */
    public List<ToolBarItemDesc> getToolBarItems()
    {
        Vector<ToolBarItemDesc> list = new Vector<ToolBarItemDesc>();
        ToolBarDropDownBtn      btn  = createToolbarButton(name, "stats.gif", "stats_hint");      

        
        list.add(new ToolBarItemDesc(btn));
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
    class DisplayAction implements ActionListener 
    {
        private String   statName;
        
        public DisplayAction(final String statName)
        {
            this.statName = statName;
        }
        
        public void actionPerformed(ActionEvent e) 
        {
            createStatPane(statName);
        }
    }
   

}

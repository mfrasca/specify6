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
package edu.ku.brc.specify.core;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ku.brc.specify.helpers.*;
import edu.ku.brc.specify.ui.*;
import edu.ku.brc.specify.dbsupport.*;
import edu.ku.brc.specify.ui.ToolBarDropDownBtn;
import edu.ku.brc.specify.ui.UICacheManager;
import edu.ku.brc.specify.core.subpane.*;
import edu.ku.brc.specify.dbsupport.QueryResultsGetter;
import edu.ku.brc.specify.plugins.MenuItemDesc;
import edu.ku.brc.specify.plugins.TaskPluginable;
import edu.ku.brc.specify.plugins.ToolBarItemDesc;
import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;

/**
 * The StatsTask is responsible gettng and displaying all various idfferent kinds of stats
 * 
 * @author rods
 *
 */
public class StatsTask extends BaseTask
{
    // Static Data Members
    private static Log log = LogFactory.getLog(StatsTask.class);
    
    // Data Members
    org.dom4j.Element statDOM;

    /**
     * Creates a Statistics Tasks
     *
     */
    public StatsTask()
    {
        super(getResourceString("Statistics"));
        
        org.dom4j.Element panelDOM = null;
        
        try
        {
            statDOM  = XMLHelper.readDOMFromConfigDir("statistics.xml");         // Describes each Statistic, its SQL and how it is to be displayed
            panelDOM = XMLHelper.readDOMFromConfigDir("statistics_panel.xml");   // contains a description of the NavBoxes
            
        } catch (Exception ex)
        {
            log.error(ex);
        }

        String pieChartStr = getResourceString("Pie_Chart");
        String barChartStr = getResourceString("Bar_Chart");
        
        // Process the NavBox Panel and create all the commands
        // XXX This needs to be made generic so everyone can use it
        // 
        List boxes = panelDOM.selectNodes("/boxes/box");
        for ( Iterator iter = boxes.iterator(); iter.hasNext(); ) 
        {
            org.dom4j.Element box = (org.dom4j.Element) iter.next();
            NavBox navBox = new NavBox(box.attributeValue("name"));
            
            List items = box.selectNodes("item");
            for ( Iterator iter2 = items.iterator(); iter2.hasNext(); ) 
            {
                org.dom4j.Element item = (org.dom4j.Element) iter2.next();
                String boxName = item.attributeValue("name");
                String type    = item.attributeValue("type");
                ActionListener action = null;
                if (type.equals(pieChartStr))
                {
                    type = pieChartStr;
                    action = new PieChartAction(this, boxName);
                    
                } else if (type.equals(barChartStr))
                {
                    type = barChartStr;
                    action = new BarChartAction(this, boxName);
                }
                
                navBox.add(NavBox.createBtn(boxName, type, IconManager.IconSize.Std16, action));
           } 
           navBoxes.addElement(navBox);
        }    
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.core.BaseTask#getStarterPane()
     */
    public SubPaneIFace getStarterPane()
    {
        return new StatsPane(name, this);
    }
    
    /**
     * Reads the Chart INfo from the DOM and sets it into a Chartable object. The info are string used to decorate the chart,
     * like title, X Axis Title, Y Axis Title etc.
     * @param element the 'chartinfo' element ot be processed
     * @param name the name of the child element that needs to be looked up to get its value
     * @return returns the string value for the info element or an empty string
     */
    protected String getChartInfo(final org.dom4j.Element element, final String name)
    {
        org.dom4j.Element el = (org.dom4j.Element)element.selectSingleNode("chartinfo/"+name);
        if (el != null)
        {
            return el.getText();
        }
        return "";
    }
    
    /**
     * Fills the ChartPane with any extra desxcription information
     * @param chartPane the chart pane to be augmented
     */
    protected void fillWithChartInfo(final org.dom4j.Element element, final ChartPane chartPane)
    {
        chartPane.setTitle(getChartInfo(element, "title"));
        chartPane.setXAxis(getChartInfo(element, "xaxis"));
        chartPane.setYAxis(getChartInfo(element, "yaxis"));
        String vert = getChartInfo(element, "vertical");
        if (vert != null && vert.length() > 0)
        {
            chartPane.setVertical(vert.toLowerCase().equals("true"));
        }
    }

    /**
     * Helper method for adding row/col desc and values to a container
     * @param qrc the QueryResultsContainer that will be added to
     * @param descRow the textual description's row position
     * @param descCol the textual description's column position
     * @param valueRow the value's row position
     * @param valueCol the value's column position
     */
    public void add(final QueryResultsContainer qrc, final int descRow, final int descCol, final int valueRow, final int valueCol)
    {
        qrc.add(new QueryResultsDataObj(descRow, descCol));
        qrc.add(new QueryResultsDataObj(valueRow, valueCol));
    }

    /**
     * Creates a chart from an XML definition. The query may be defined in the XML for it could be a custom query
     * that comes from an instance of a class.
     * @param actionName the action name (this is the name of the statistic to look up in the XML)
     * @param qrProcessable the processor to take care of the results
     * @param subPane the sub pane to be added to the UI
     * @param listener the listener who nneds to know when the query is done and all the results are available
     */
    public void createChart(final String                  actionName, 
                            final QueryResultsProcessable qrProcessable,
                            final BaseSubPane             subPane, 
                            final QueryResultsListener    listener)
    {
        org.dom4j.Element element = (org.dom4j.Element)statDOM.selectSingleNode("/statistics/stat[@name='"+actionName+"']");
        if (element != null)
        {
            // FIll the chart with extra descirption and decoration info
            if (subPane instanceof ChartPane)
            {
                fillWithChartInfo(element, (ChartPane)subPane);
            }
            
            // It better have some SQL
            org.dom4j.Element sqlElement = (org.dom4j.Element)element.selectSingleNode("sql");
            if (sqlElement == null)
            {
                throw new RuntimeException("sql element is null!");
            }

            // The SQL can be of two types "text" or "builtin"
            // Text is just a text string of SQL that will be executed
            // Builtin is a class that will provide SQL
            String sqlType = sqlElement.attributeValue("type");
            if (sqlType.equals("text"))
            {
                
                QueryResultsContainer    container   = new QueryResultsContainer();
                QueryResultsHandlerIFace singlePairs = new PairsSingleQueryResultsHandler();
                qrProcessable.setHandler(singlePairs);
                
                container.setSql(sqlElement.getText().trim());
                
                String displayType = element.attributeValue("display");               
                List parts = element.selectNodes(displayType.equals("Pie Chart") ? "slice" : "bar");
                for ( Iterator iter = parts.iterator(); iter.hasNext(); ) {
                    org.dom4j.Element slice = (org.dom4j.Element) iter.next();
                    int descRow  = Integer.parseInt(slice.valueOf( "desc/@row" ));
                    int descCol  = Integer.parseInt(slice.valueOf( "desc/@col" ));
                    int valueRow = Integer.parseInt(slice.valueOf( "value/@row" ));
                    int valueCol = Integer.parseInt(slice.valueOf( "value/@col" ));
                    add(container, descRow, descCol, valueRow, valueCol);
                }
                singlePairs.init(listener, container);
                singlePairs.startUp();
               
            } else if (sqlType.equals("builtin"))
            {
                try
                {
                    CustomQuery                  customQuery   = CustomQueryFactory.createCustomQuery(sqlElement.attributeValue("className"));
                    PairsMultipleQueryResultsHandler multiplePairs = new PairsMultipleQueryResultsHandler();
                    qrProcessable.setHandler(multiplePairs);
                    
                    multiplePairs.init(listener, customQuery.getQueryDefinition());
                    multiplePairs.startUp();
                    
                } catch (ClassNotFoundException ex)
                {
                    log.error(ex); // XXX what should we do here?
                } catch (IllegalAccessException ex)
                {
                    log.error(ex); // XXX what should we do here?
                } catch (InstantiationException ex)
                {
                    log.error(ex); // XXX what should we do here?
                }
                
            } else
            {
                throw new RuntimeException("unrecognizable type for sql element["+sqlType+"]");
            }
            
            UICacheManager.getInstance().getSubPaneMgr().addPane(subPane);
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
    class BarChartAction implements ActionListener 
    {
        private String   actionName;
        private Taskable taskable;
        
        public BarChartAction(final Taskable taskable, final String actionName)
        {
            this.taskable = taskable;
            this.actionName = actionName;
        }
        public void actionPerformed(ActionEvent e) 
        {
            BarChartPane barChart    = new BarChartPane("Bar Chart", taskable);
            createChart(actionName, barChart, barChart, barChart);
        }
    }
   
    /**
     * 
     * @author rods
     *
     */
    class PieChartAction implements ActionListener 
    {
        private String   actionName;
        private Taskable taskable;
        
        public PieChartAction(final Taskable taskable, final String actionName)
        {
            this.taskable = taskable;
            this.actionName = actionName;
        }
        public void actionPerformed(ActionEvent e) 
        {
            PieChartPane pieChart = new PieChartPane("Pie Chart", taskable);
            createChart(actionName, pieChart, pieChart, pieChart);
        }
    }
   

}

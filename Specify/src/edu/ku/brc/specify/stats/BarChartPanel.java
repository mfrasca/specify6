/* Filename:    $RCSfile: BarChartPanel.java,v $
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

package edu.ku.brc.specify.stats;

import static edu.ku.brc.specify.helpers.UIHelper.getInt;
import static edu.ku.brc.specify.helpers.UIHelper.getString;
import static edu.ku.brc.specify.ui.UICacheManager.getResourceString;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.specify.dbsupport.QueryResultsContainer;
import edu.ku.brc.specify.dbsupport.QueryResultsHandlerIFace;
import edu.ku.brc.specify.dbsupport.QueryResultsListener;
import edu.ku.brc.specify.dbsupport.QueryResultsProcessable;
import edu.ku.brc.specify.ui.IconManager;


/**
 * Creates a pane that can listener for Query Results and then create a Bar Chart
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class BarChartPanel extends ChartPanel implements QueryResultsListener, QueryResultsProcessable
{
    // Static Data Members
    //private static Log log = LogFactory.getLog(BarChartPanel.class);

    // Data Members
    private QueryResultsHandlerIFace   handler = null;
    private org.jfree.chart.ChartPanel chart   = null;
    private Dimension                  maxChartSize = null;

    /**
     * Creates a BarChart pane with a name and a reference to the taskable that started it
     * @param name the name of the BarChart
     * @param task the starting task
     */
    public BarChartPanel()
    {
        setBorder(null);
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.core.Taskable#getIcon()
     */
    public Icon getIcon()
    {
        return IconManager.getIcon("Bar_Chart", IconManager.IconSize.Std16);
    }

    //--------------------------------------
    // QueryResultsProcessable
    //--------------------------------------

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.QueryResultsProcessable#setHandler()
     */
    public void setHandler(final QueryResultsHandlerIFace handler)
    {
        this.handler = handler;
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.QueryResultsProcessable#getHandler()
     */
    public QueryResultsHandlerIFace getHandler()
    {
        return handler;
    }

    //--------------------------------------
    // QueryResultsListener
    //--------------------------------------

    /**
     * Helper method for methods below
     */
    protected void addCompletedComp(JComponent comp)
    {
        removeAll(); // remove progress bar
        add(comp, BorderLayout.CENTER);
        /*
        CellConstraints cc      = new CellConstraints();
        //PanelBuilder builder    = new PanelBuilder(new FormLayout("F:P:G", "F:P:G"), this);
        PanelBuilder builder    = new PanelBuilder(new FormLayout("p", "p"), this);
        builder.add(comp, cc.xy(1,1));
*/
        if (handler != null)
        {
            handler.cleanUp();
            handler = null;
        }

        doLayout();
        repaint();
    }

    /*
     *  (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.QueryResultsListener#allResultsBack()
     */
    public synchronized void allResultsBack()
    {
        // create a dataset...
        String cat = "";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        java.util.List<Object> list = handler.getDataObjects();
        for (int i=0;i<list.size();i++)
        {
            Object descObj = list.get(i++);
            Object valObj  = list.get(i);
            dataset.addValue(getInt(valObj), getString(descObj), cat);
        }
        list.clear();

        // create the chart...
        JFreeChart jgChart = ChartFactory.createBarChart3D(
                title,      // chart title
                xAxisTitle, // domain axis label
                yAxisTitle, // range axis label
                dataset,    // data
                isVertical ? PlotOrientation.VERTICAL : PlotOrientation.HORIZONTAL,
                true,       // include legend
                true,       // tooltips?
                false       // URLs?
            );
        // create and display a frame...
        chart = new org.jfree.chart.ChartPanel(jgChart, true, true, true, true, true);
        chart.setBackground(Color.WHITE);
        if (maxChartSize != null)
        {
            chart.setMaximumSize(maxChartSize);
            chart.setPreferredSize(maxChartSize);
        }
        addCompletedComp(chart);

    }
    
    public void setMaxChartSize(int width, int height)
    {
        if (maxChartSize == null)
        {
            maxChartSize = new Dimension();
        } 
        maxChartSize.setSize(width, height-5);

        if (chart != null)
        {
            chart.setMaximumSize(maxChartSize);
            chart.setPreferredSize(maxChartSize);
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.dbsupport.QueryResultsListener#resultsInError(edu.ku.brc.specify.dbsupport.QueryResultsContainer)
     */
    public void resultsInError(final QueryResultsContainer qrc)
    {
        //JOptionPane.showMessageDialog(this, getResourceString("ERROR_CREATNG_BARCHART"), getResourceString("Error"), JOptionPane.ERROR_MESSAGE); // XXX LOCALIZE

        addCompletedComp(new JLabel(getResourceString("ERROR_CREATNG_BARCHART"), JLabel.CENTER));
    }

    public void setBounds(Rectangle r)
    {
        setBounds(r.x, r.y, r.width, r.height);
        if (chart != null)
        {
            setMaxChartSize(r.width, r.height);
        }
        System.out.println(r);
    }

    public void setBounds(int x, int y, int width, int height)
    {
        //System.out.print(x+" "+y+" "+width+" "+height);
        //System.out.println("  "+maxChartSize.width+" "+maxChartSize.height);

        super.setBounds(x, y, width, height);
        if (chart != null)
        {
            setMaxChartSize(width, height);
        }
    }
}

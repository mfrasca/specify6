/* Filename:    $RCSfile: ChartPanel.java,v $
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

import javax.swing.JPanel;

/**
 * This base class implements the Chartable interface
 * that enables derived classes to easily accept and have access to information needed to decorate or describe the chart.
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ChartPanel extends JPanel implements Chartable
{
    // Static Data Members
    //private static Log log = LogFactory.getLog(ChartPanel.class);

    // Data Members
    protected String  title       = "";
    protected String  xAxisTitle  = "";
    protected String  yAxisTitle  = "";
    protected boolean isVertical  = true;

    public ChartPanel()
    {
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.Chartable#setTitle(java.lang.String)
     */
    public void setTitle(final String title)
    {
        this.title = title;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.Chartable#setXAxis(java.lang.String)
     */
    public void setXAxis(final String title)
    {
        xAxisTitle = title;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.Chartable#setYAxis(java.lang.String)
     */
    public void setYAxis(final String title)
    {
       yAxisTitle = title;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.Chartable#setVertical(boolean)
     */
    public void setVertical(boolean isVertical)
    {
        this.isVertical = isVertical;
    }

}

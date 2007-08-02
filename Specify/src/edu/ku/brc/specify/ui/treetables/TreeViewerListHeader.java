/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.ui.treetables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.apache.log4j.Logger;

import edu.ku.brc.ui.GraphicsUtils;
import edu.ku.brc.util.Pair;

/**
 * A {@link JLabel} for displaying the names of columns of lists displaying data in
 * {@link TreeDataListModel}s.
 *
 * @code_status Unknown (auto-generated)
 *
 * @author jstewart
 */
@SuppressWarnings("serial")
public class TreeViewerListHeader extends JPanel implements ListDataListener
{
    /** Logger for all messages emitted. */
    private static final Logger log = Logger.getLogger(TreeViewerListHeader.class);

    /** The associated JList. */
	protected JList list;
	/** The underlying data model for the list. */
	protected TreeViewerListModel model;
	/** The cell renderer for the list. */
	protected TreeViewerNodeRenderer cellRenderer;
	/** The label's text color. */
	protected Color textColor;
	
	protected Color bgs[];
    
    protected HashMap<Integer, String> rankToNameMap = new HashMap<Integer, String>();
    
    protected boolean resizingColumns = false;
    protected Integer expandingRank;
    protected int mostRecentX = -1;
    protected int minColWidth = 50;
	
	/**
	 * Creates a header appropriate for labelling the columns of the given
	 * list (which represents the given data model).
	 *
	 * @param list the list
	 * @param tdlm the list's underlying data model
	 */
	public TreeViewerListHeader(JList list, TreeViewerListModel tvlm, TreeViewerNodeRenderer listCellRenderer, Map<Integer, String> rankToNameMap)
	{
		this.list = list;
		this.model = tvlm;
		this.cellRenderer = listCellRenderer;
		
		bgs = listCellRenderer.getBackgroundColors();
		
		model.addListDataListener(this);
		
		if( list.getFont() != null )
		{
			this.setFont(list.getFont());
		}
		this.setTextColor(list.getForeground());
		this.setBackground(list.getBackground());
        
        this.rankToNameMap.putAll(rankToNameMap);
        
        MouseAdapter mouseAdapter = new MouseAdapter()
        {
            //@Override
            public void mousePressed(MouseEvent e)
            {
                pressed(e);
            }

            //@Override
            public void mouseDragged(MouseEvent e)
            {
                dragged(e);
            }

            //@Override
            public void mouseReleased(MouseEvent e)
            {
                released(e);
            }

            //@Override
            public void mouseMoved(MouseEvent e)
            {
                hover(e);
            }
        };
        
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener((MouseMotionListener)mouseAdapter);
	}
    
    protected void pressed(MouseEvent e)
    {
        // if press is on a boundary between ranks
        // set resizingColumns to true
        
        int[] ranksBeingChanged = cellRenderer.getRanksSurroundingPoint(e.getX());
        
        // this action expands the column to the left of the click, so we have to at least have that column
        if (ranksBeingChanged == null || ranksBeingChanged[0] == -1)
        {
            return;
        }
        
        // else
        
        expandingRank = ranksBeingChanged[0];
        mostRecentX = e.getX();
        log.debug("Setting 'mostRecentX' to " + mostRecentX);
        resizingColumns = true;
        
        log.debug("Starting a column resize action for rank " + expandingRank);
    }
    
    protected void hover(MouseEvent e)
    {
        int[] ranksBeingChanged = cellRenderer.getRanksSurroundingPoint(e.getX());
        if (ranksBeingChanged == null || ranksBeingChanged[0] == -1)
        {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            return;
        }
        // else
        this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
    }
    
    protected void dragged(MouseEvent e)
    {
        if (resizingColumns)
        {
            int x = e.getX();
            int change = x - mostRecentX;
            
            log.debug("Resizing tree viewer columns");
            
            // change the column that should expand/contrat
            int currentColWidth = cellRenderer.getColumnWidth(expandingRank);
            if (currentColWidth + change < minColWidth)
            {
                change = currentColWidth - minColWidth;
            }
            
            log.debug("Changing width of column for rank " + expandingRank + " by " + change + " pixels");
            
            // ignore changes that have no effect
            if (change == 0)
            {
                log.debug("Ignoring change of 0 pixels");
                return;
            }
            
            cellRenderer.changeColumnWidth(expandingRank, change);

            list.invalidate();
            list.repaint();
            this.repaint();
            
            this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            mostRecentX = x;
            log.debug("Changing 'mostRecentX' to " + mostRecentX);
        }
        else
        {
            System.out.println("ignoring drag event");
        }
    }
    
    protected void released(@SuppressWarnings("unused") MouseEvent e)
    {
        if (resizingColumns)
        {
            log.debug("Ending a column resize action");
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            resizingColumns = false;
        }
    }

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.getBackground());
        
        int w = this.getWidth();
        int h = this.getHeight();
        g2.fillRect(0,0,w,h);

        int i = 0;
        for( Integer rank: model.getVisibleRanks() )
		{
            String rankName = rankToNameMap.get(rank);

			// draw column background color
			Pair<Integer,Integer> colBounds = cellRenderer.getColumnBoundsForRank(rank);
			g.setColor(bgs[i%2]);
            g.fillRect(colBounds.first,0,colBounds.second-colBounds.first,this.getHeight());
			++i;

			// draw text
			Pair<Integer,Integer> textBounds = cellRenderer.getTextBoundsForRank(rank); 
			g.setColor(textColor);
            String clippedName = GraphicsUtils.clipString(g.getFontMetrics(), rankName, textBounds.second-textBounds.first);
			g.drawString(clippedName,textBounds.first,getHeight()/2);
		}
        
        g2.setColor(this.getBackground());
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(0,h,w,h);
	}

	/**
	 * Returns the text color.
	 *
	 * @see #setTextColor(Color)
	 * @return the text color
	 */
	public Color getTextColor()
	{
		return textColor;
	}

	/**
	 * Sets the text color.
	 *
	 * @see #getTextColor()
	 * @param textColor the text color
	 */
	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	/**
	 * Returns the width of the header.
	 *
	 * @see javax.swing.Icon#getIconWidth()
	 * @return the width of the header
	 */
	@Override
	public int getWidth()
	{
		return list.getWidth();
	}

	/**
	 * Returns the height of the header.
	 *
	 * @see javax.swing.Icon#getIconHeight()
	 * @return the height of the header
	 */
	@Override
	public int getHeight()
	{
		return list.getGraphics().getFontMetrics().getHeight()+20;
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(getWidth(),getHeight());
	}

	/**
	 * Repaints the header.
	 *
	 * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
	 * @param e the triggering list data event
	 */
	public void contentsChanged(ListDataEvent e)
	{
		repaint();
	}

	/**
	 * Repaints the header.
	 *
	 * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
	 * @param e the triggering list data event
	 */
	public void intervalAdded(ListDataEvent e)
	{
		repaint();
	}

	/**
	 * Repaints the header.
	 *
	 * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
	 * @param e the triggering list data event
	 */
	public void intervalRemoved(ListDataEvent e)
	{
		repaint();
	}
}

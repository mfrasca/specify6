package edu.ku.brc.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputAdapter;

import edu.ku.brc.af.core.SubPaneIFace;
import edu.ku.brc.af.core.SubPaneMgr;

import org.apache.log4j.Logger;

/**
 * Adds a close "X" in the bottom right of the TabbedPane for closing tabs and adds a Close btn to each tab.
 *
 * @code_status Complete
 * 
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ExtendedTabbedPane extends JTabbedPane
{
    private static final Logger log = Logger.getLogger(ExtendedTabbedPane.class);
    
    protected static final int CLOSER_SIZE = 5;
    
    protected Rectangle closerRect = new Rectangle();
    protected boolean   isOver     = false;
    protected ExtendedTabbedPane itself;
    
    /**
     * Constructor.
     */
    public ExtendedTabbedPane()
    {
        super();
        init();
    }

    /**
     * Constructor.
     * @param tabPlacement tabLayoutPolicy
     */
    public ExtendedTabbedPane(int tabPlacement)
    {
        super(tabPlacement);
        init();
    }

    /**
     * Constructor.
     * @param tabPlacement tabPlacement
     * @param tabLayoutPolicy tabLayoutPolicy
     */
    public ExtendedTabbedPane(int tabPlacement, int tabLayoutPolicy)
    {
        super(tabPlacement, tabLayoutPolicy);
        init();
    }
    
    /**
     * Hooks up listeners for painting the hover state of the close "X".
     */
    protected void init()
    {
        itself = this;
        
        MouseInputAdapter mouseInputAdapter = new MouseInputAdapter() {
            @Override
            public void mouseExited(MouseEvent e) 
            {
                if (!closerRect.contains(e.getPoint()))
                {
                    isOver = false;
                    repaint();
                    //UIRegistry.displayStatusBarText("");
                }
            }
            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (closerRect.contains(e.getPoint()))
                {
                    isOver = true;
                    repaint();
                    //UIRegistry.displayStatusBarText(itself.getToolTipText());
                    
                } else if (isOver)
                {
                    isOver = false;
                    repaint();
                }
                
            }
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                if (closerRect.contains(e.getPoint()))
                {
                    closeCurrent();
                }
                
            }
          };
        addMouseListener(mouseInputAdapter);
        addMouseMotionListener(mouseInputAdapter);
        
    }
    
    /**
     * Adds a Close Btn to the Tab.
     * @param title the title of the tab
     * @param icon the icon for the tab (can be null)
     * @param comp the component tab
     * @param index the index of the tab to be fixed
     */
    protected void adjustTab(final String title, final Icon icon, Component comp, final int index)
    {
        /*
         * XXX iReport
         * NOTE: un-commenting the block below causes iReport to fail while loading.
         * (Somehow related to lookandfeel settings. This note may no longer apply when/if
         * the current code to handle iReport lookandfeel vs. Specify lookandfeel conflicts is changed.)
         */
        
        /*
        
        final JLabel closeBtn = new JLabel(IconManager.getIcon("Close"));
        closeBtn.setBorder(null);
        
        class TabMouseAdapter extends MouseAdapter
        {
            protected Component tabComp;
            
            public TabMouseAdapter(final Component tabComp) 
            {
                this.tabComp = tabComp;
            }
            @Override
            public void mouseClicked(MouseEvent e)
            {
                SubPaneIFace subPane = (SubPaneIFace)tabComp;
                SubPaneMgr.getInstance().showPane(subPane);
                SubPaneMgr.getInstance().closeCurrent();
            }
            @Override
            public void mouseEntered(MouseEvent e)
            {
                closeBtn.setIcon(IconManager.getIcon("CloseHover"));
                closeBtn.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                closeBtn.setIcon(IconManager.getIcon("Close"));
                closeBtn.repaint();
            }
        }

        closeBtn.setOpaque(false);
        closeBtn.addMouseListener(new TabMouseAdapter(comp));
          
        // XXX Java 6.0
        
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.add(new JLabel(title, icon, SwingConstants.RIGHT), BorderLayout.WEST);
        tabPanel.add(new JLabel(" "), BorderLayout.CENTER);
        tabPanel.add(closeBtn, BorderLayout.EAST);
        
        setTabComponentAt(index, tabPanel);
        */
        
        
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JTabbedPane#addTab(java.lang.String, java.awt.Component)
     */
    @Override
    public void addTab(String title, Component component)
    {
        super.addTab(title, component);
        
        adjustTab(title, null, component, getTabCount()-1);
    }

    /* (non-Javadoc)
     * @see javax.swing.JTabbedPane#addTab(java.lang.String, javax.swing.Icon, java.awt.Component, java.lang.String)
     */
    @Override
    public void addTab(String title, Icon icon, Component component, String tip)
    {
        super.addTab(title, icon, component, tip);
        
        adjustTab(title, icon, component, getTabCount()-1);
    }

    /* (non-Javadoc)
     * @see javax.swing.JTabbedPane#addTab(java.lang.String, javax.swing.Icon, java.awt.Component)
     */
    @Override
    public void addTab(String title, Icon icon, Component component)
    {
        super.addTab(title, icon, component);
        
        adjustTab(title, icon, component, getTabCount()-1);
    }

    /* (non-Javadoc)
     * @see javax.swing.JTabbedPane#insertTab(java.lang.String, javax.swing.Icon, java.awt.Component, java.lang.String, int)
     */
    @Override
    public void insertTab(String title, Icon icon, Component component, String tip, int index)
    {
        super.insertTab(title, icon, component, tip, index);
        
        adjustTab(title, icon, component, index);
    }

    /**
     * Clsoes the current tab.
     */
    protected void closeCurrent()
    {
        this.remove(this.getSelectedComponent());
    }
    
    /**
     *  Draws the close "X" 
     * @param g f
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    protected void drawCloser(final Graphics g, final int x, final int y, final int w, final int h)
    {
        closerRect.setBounds(x, y, w, h);
        
        g.drawLine(x, y, x+w, y+h);
        g.drawLine(x+w, y, x, y+h);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        try
        {
            super.paintComponent(g);
            
        } catch (java.lang.ArrayIndexOutOfBoundsException ex)
        {
            log.error(ex);
        }
        
        if (this.getTabCount() > 0)
        {
            Dimension s = getSize();
            s.width  -= CLOSER_SIZE + 1;
            s.height -= CLOSER_SIZE + 1;
            
            Color color = getBackground();
            
            int x =  s.width -5;
            int y =  s.height-5;
            if (isOver)
            {
                g.setColor(Color.RED);
                drawCloser(g, x, y, CLOSER_SIZE, CLOSER_SIZE);
                
            } else
            {
                g.setColor(color.darker());
                drawCloser(g, x, y, CLOSER_SIZE, CLOSER_SIZE);                
            }
        } else
        {
            closerRect.setBounds(0,0,0,0);
        }
    }
}

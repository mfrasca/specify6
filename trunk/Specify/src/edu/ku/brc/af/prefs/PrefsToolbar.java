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
package edu.ku.brc.af.prefs;

import static edu.ku.brc.helpers.XMLHelper.getAttr;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import edu.ku.brc.af.core.NavBoxButton;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.ToolbarLayoutManager;

/**
 * This class simply reads all the prefs and constructs a toolbar with the various icons.
 *
 * @code_status Complete
 *
 * @author rods
 *
 */
@SuppressWarnings("serial") //$NON-NLS-1$
public class PrefsToolbar extends JPanel
{
    private static final Logger log = Logger.getLogger(PrefsToolbar.class);
    
    public static final String NAME        = "name"; //$NON-NLS-1$
    public static final String TITLE       = "title"; //$NON-NLS-1$
    public static final String PANEL_CLASS = "panelClass"; //$NON-NLS-1$
    public static final String ICON_PATH   = "iconPath"; //$NON-NLS-1$

    protected PreferencesDlg prefsDlg;
    protected int           iconSize = 24;  // XXX PREF (Possible???)
    protected int           numPrefs = 0;
     /**
     * Constructor with the main panel so the icon know how to show their pane.
     *
     * @param prefsDlg the main pane that houses all the preference panes
     */
    public PrefsToolbar(final PreferencesDlg prefsDlg)
    {
        super(new ToolbarLayoutManager(2, 8));

        this.prefsDlg = prefsDlg;

        init();
    }
    
    public int getNumPrefs()
    {
        return numPrefs;
    }

    /**
     * Initializes the toolbar with all the icon from all the diffrent groups or sections.
     */
    protected void init()
    {
        try
        {
            Element root = XMLHelper.readDOMFromConfigDir("prefs_init.xml"); //$NON-NLS-1$
            if (root == null)
            {
                return; // XXX FIXME
            }

            List<?> sections = root.selectNodes("/prefs/section"); //$NON-NLS-1$
            numPrefs = sections.size();
            for ( Iterator<?> iter = sections.iterator(); iter.hasNext(); )
            {
                org.dom4j.Element section = (org.dom4j.Element)iter.next();

                String title = getAttr(section, "title", null); //$NON-NLS-1$
                if (StringUtils.isNotEmpty(title))
                {
                    if (!getAttr(section, "isApp", false)) //$NON-NLS-1$
                    {
                        loadSectionPrefs(section);
                    }
                }
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
            // XXX FIXME
        }
    }

    /**
     * Loads a Section or grouping of Prefs.
     * @param parentPref the parent pref which is the groups or section
     */
    protected void loadSectionPrefs(final Element sectionElement)
    {
        NavBoxButton.setVertGap(2);
        
        //List<NavBoxButton> btns = new Vector<NavBoxButton>();
        //int totalWidth = 0;
        try
        {
            List<?> prefs = sectionElement.selectNodes("pref"); //$NON-NLS-1$
            //numPrefs = prefs.size();
            for ( Iterator<?> iterPrefs = prefs.iterator(); iterPrefs.hasNext(); )
            {
                org.dom4j.Element pref = (org.dom4j.Element)iterPrefs.next();

                String prefTitle   = pref.attributeValue("title"); //$NON-NLS-1$
                String iconPath    = pref.attributeValue("icon"); //$NON-NLS-1$
                String panelClass  = pref.attributeValue("panelClass"); //$NON-NLS-1$
                String viewSetName = pref.attributeValue("viewsetname"); //$NON-NLS-1$
                String viewName    = pref.attributeValue("viewname"); //$NON-NLS-1$
                String hContext    = pref.attributeValue("help"); //$NON-NLS-1$

                if (StringUtils.isNotEmpty(prefTitle) && 
                    StringUtils.isNotEmpty(iconPath) && 
                    StringUtils.isNotEmpty(panelClass))
                {
                    ImageIcon icon;
                    if (iconPath.startsWith("http") || iconPath.startsWith("file")) //$NON-NLS-1$ //$NON-NLS-2$
                    {
                        icon = new ImageIcon(new URL(iconPath));
                    } else
                    {
                        icon = IconManager.getImage(iconPath);
                    }
                    
                    if (icon != null)
                    {
                        if (icon.getIconWidth() > iconSize || icon.getIconHeight() > iconSize)
                        {
                            icon = new ImageIcon(icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                        }
                    }
                    if (icon == null)
                    {
                        log.error("Icon was created - path["+iconPath+"]"); //$NON-NLS-1$ //$NON-NLS-2$
                    }

                    NavBoxButton btn = new NavBoxButton(getResourceString(prefTitle), icon);
                    btn.setOpaque(false);
                    btn.setVerticalLayout(true);
                    
                    //btns.add(btn);
                    //totalWidth += btn.getPreferredSize().getWidth();
                    
                    try
                    {
                        Class<?>  panelClassObj = Class.forName(panelClass);
                        Component comp          = (Component)panelClassObj.newInstance();
                        if (panelClassObj == GenericPrefsPanel.class)
                        {
                            if (StringUtils.isNotEmpty(viewSetName) && StringUtils.isNotEmpty(viewName))
                            {
                                GenericPrefsPanel genericPrefsPanel = (GenericPrefsPanel)comp;
                                genericPrefsPanel.setHelpContext(hContext);
                                genericPrefsPanel.createForm(viewSetName, viewName);
                                
                            } else
                            {
                                log.error("ViewSetName["+viewSetName+"] or ViewName["+viewName+"] is empty!"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                            }
                        }
                        prefsDlg.addPanel(prefTitle, comp);

                        add(btn.getUIComponent());

                    } catch (Exception ex)
                    {
                        log.error(ex); // XXX FIXME
                        ex.printStackTrace();
                    }
                    btn.addActionListener(new ShowAction(prefTitle));
                }
            }
            
            /*int aveWidth = totalWidth / btns.size();
            for (NavBoxButton nbb : btns)
            {
                Dimension size = nbb.getPreferredSize();
                if (size.width < aveWidth)
                {
                    size.width = aveWidth;
                }
                nbb.setPreferredSize(size);
                nbb.setSize(size);
            } */               

        } catch (Exception ex)
        {
            
            throw new RuntimeException(ex);
        } finally
        {
            NavBoxButton.setVertGap(0);

        }
        
    }

    /**
     * Show a panel by name.
     * @param panelName the name of the panel to be shown
     */
    protected void showPanel(final String panelName)
    {
        prefsDlg.showPanel(panelName);
    }


    //--------------------------------------------------------------
    // Inner Classes
    //--------------------------------------------------------------

    /**
     *
     * Command for showing a pref pane
     *
     */
    class ShowAction implements ActionListener
    {
        private String panelName;

        public ShowAction(final String panelName)
        {
            this.panelName = panelName;
        }
        public void actionPerformed(ActionEvent e)
        {
            showPanel(panelName);
        }
    }

}

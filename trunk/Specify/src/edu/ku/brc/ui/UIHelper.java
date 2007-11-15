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
package edu.ku.brc.ui;

import static edu.ku.brc.ui.UIRegistry.getResourceString;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.core.UsageTracker;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.exceptions.ConfigurationException;
import edu.ku.brc.helpers.MenuItemPropertyChangeListener;
import edu.ku.brc.specify.conversion.CustomDBConverter;
import edu.ku.brc.specify.conversion.CustomDBConverterDlg;
import edu.ku.brc.specify.conversion.CustomDBConverterListener;
import edu.ku.brc.ui.ViewBasedDialogFactoryIFace.FRAME_TYPE;
import edu.ku.brc.ui.db.DatabaseLoginDlg;
import edu.ku.brc.ui.db.DatabaseLoginListener;
import edu.ku.brc.ui.db.DatabaseLoginPanel;
import edu.ku.brc.ui.db.ViewBasedDisplayIFace;
import edu.ku.brc.ui.dnd.GhostDataAggregatable;
import edu.ku.brc.ui.forms.DataObjectGettable;
import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.forms.FormHelper;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.persist.AltViewIFace;
import edu.ku.brc.ui.forms.persist.FormCellIFace;

/**
 * A Helper class that has a very wide array of misc methods for helping out. (Is that meaningless or what?)
 *
 * @code_status Code Freeze
 *
 * @author rods
 *
 */
public final class UIHelper
{
    public enum OSTYPE {Unknown, Windows, MacOSX, Linux}
    
    // Static Data Members
    protected static final Logger   log      = Logger.getLogger(UIHelper.class);
    protected static Calendar       calendar = new GregorianCalendar();
    protected static OSTYPE         oSType;

    protected static Object[]       values           = new Object[2];
    protected static DateWrapper    scrDateFormat    = null;

    protected static Border          emptyBorder     = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    protected static Border          focusBorder     = new LineBorder(Color.GRAY, 1, true);
    protected static RenderingHints  txtRenderingHints;
    
    static {

        String osStr = System.getProperty("os.name");
        if (osStr.startsWith("Mac OS X"))
        {
            oSType   = OSTYPE.MacOSX;

        } else if (osStr.indexOf("Windows") != -1)
        {
            oSType   = OSTYPE.Windows;

        } else if (osStr.indexOf("Linux") != -1)
        {
            oSType   = OSTYPE.Linux;

        } else
        {
            oSType   = OSTYPE.Unknown;
        }
        txtRenderingHints = createTextRenderingHints();
    }
    
    /**
     * @return whether the OS is Mac.
     */
    public static boolean isMacOS()
    {
        return oSType == OSTYPE.MacOSX;
    }
    
    /**
     * @return whether the OS is Mac.
     */
    public static boolean isWindows()
    {
        return oSType == OSTYPE.Windows;
    }
    
    /**
     * @return whether the OS is Mac.
     */
    public static boolean isLinux()
    {
        return oSType == OSTYPE.Linux;
    }
    
    /**
     * Windows background selection color for JTables is too dark so use a lighter blue.
     */
    public static void adjustUIDefaults()
    {
        if (isWindows())
        {
            UIManager.put("Table.selectionBackground", new ColorUIResource(161, 175, 191));
        }
    }
    
    /**
     * Center and make the window visible
     * @param window the window to center
     */
    public static void centerAndShow(java.awt.Window window)
    {
        centerWindow(window);

        window.setVisible(true);
    }

    /**
     * Center and make the window visible
     * @param window the window to center
     */
    public static void centerWindow(java.awt.Window window)
    {
        JFrame topFrame = (JFrame)UIRegistry.getTopWindow();
        Insets screenInsets = null;
        Rectangle screenRect = null;
        
        // if there is a registered TOPFRAME, and it's not the same as the window being passed in...
        if (topFrame != null && topFrame != window)
        {
            screenRect = topFrame.getBounds();
            screenInsets = topFrame.getInsets();
        }
        else
        {
            screenRect = window.getGraphicsConfiguration().getBounds();
            screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(window.getGraphicsConfiguration());
        }
        
        // Make sure we don't place the demo off the screen.
        int centerWidth = screenRect.width < window.getSize().width ? screenRect.x : screenRect.x
            + screenRect.width / 2 - window.getSize().width / 2;
        int centerHeight = screenRect.height < window.getSize().height ? screenRect.y : screenRect.y
            + screenRect.height / 2 - window.getSize().height / 2;

        centerHeight = centerHeight < screenInsets.top ? screenInsets.top : centerHeight;

        window.setLocation(centerWidth, centerHeight);
    }

    /**
     * Center and make the window visible
     * @param window the window to center
     */
    public static void positionAndShow(java.awt.Window window)
    {
        if (System.getProperty("os.name").equals("Mac OS X"))
        {
            window.setLocation(0, 0);
            window.setVisible(true);

        } else
        {
            centerAndShow(window);
        }

    }

    
    /**
     * @param frame to be positioned
     * 
     * positions frame on screen relative to position of TOPFRAME.
     * Sets frame.alwaysOnTop to true if TOPFRAME is maximized.
     */
    public static void positionFrameRelativeToTopFrame(final JFrame frame)
    {
        // not sure of safest surest way to get main window???
        JFrame topFrame = (JFrame) UIRegistry.getTopWindow();

        // for now this just sets the top of frame to the top of topFrame
        // if there is room on the left side of topFrame, frame is set so it's right edge is next to topFrame's left edge.
        // otherwise, if frame will fit, frame's left edge is aligned with topFrame's right edge.
        // If it won't fit then frame's right edge is aligned with right of edge of screen.
        if (topFrame != null)
        {
            int x = 0;
            int y = topFrame.getY();
            Rectangle screenRect = topFrame.getGraphicsConfiguration().getBounds();
            if (topFrame.getX() >= frame.getWidth())
            {
                x = topFrame.getX() - frame.getWidth();
            }
            else if (screenRect.width - topFrame.getX() - topFrame.getWidth() >= frame.getWidth())
            {
                x = topFrame.getWidth();
            }
            else
            {
                x = screenRect.width - frame.getWidth();
            }
            frame.setBounds(x, y, frame.getWidth(), frame.getHeight());
            
            frame.setAlwaysOnTop(topFrame.getExtendedState() == Frame.MAXIMIZED_BOTH || topFrame.getExtendedState() == Frame.MAXIMIZED_VERT || topFrame.getExtendedState() == Frame.MAXIMIZED_HORIZ);
        }
    }
   
    /**
     * Returns a JGoodies column or row definition string that has 'length' number of duplicated formats
     * @param def the col/row def to be duplicated
     * @param length the number of duplications
     * @return Returns a JGoodies column or row definition string that has 'length' number of duplicated formats
     */
    public static String createDuplicateJGoodiesDef(final String def, final String separator, final int length)
    {
        StringBuilder strBuf = new StringBuilder(64);
        for (int i=0;i<length;i++)
        {
            if (strBuf.length() > 0)
            {
                strBuf.append(',');
            }
            strBuf.append(def);

            if (i < (length-1))
            {
                strBuf.append(',');
                strBuf.append(separator);
            }
        }
        return strBuf.toString();
    }

    /**
     * Converts an integer time in the form of YYYYMMDD to the proper Date
     * @param iDate the int to be converted
     * @return the date object
     */
    public static Date convertIntToDate(final int iDate)
    {
        if (iDate == 0)
        {
            return null;
        }
        calendar.clear();

        int year  = iDate / 10000;
        if (year > 1800)
        {
            int tmp   = (iDate - (year * 10000));
            int month = tmp / 100;
            int day   = (tmp - (month * 100));

            calendar.set(year, month-1, day);
        } else
        {
            calendar.setTimeInMillis(0);
        }

        return calendar.getTime();
    }

    /**
     * Converts a Date to an Integer formated as YYYYMMDD
     * @param date the date to be converted
     * @return the date object
     */
    public static int convertDateToInt(final Date date)
    {
        calendar.setTimeInMillis(date.getTime());

        return (calendar.get(Calendar.YEAR) * 10000) + ((calendar.get(Calendar.MONTH)+1) * 100) + calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Converts Object to a Float
     * @param valObj the object in question
     * @return a float
     */
    public static float getFloat(Object valObj)
    {
        float value = 0.0f;
        if (valObj != null)
        {
            if (valObj instanceof Integer)
            {
                value = ((Integer)valObj).floatValue();
            } else if (valObj instanceof Long)
            {
                value = ((Long)valObj).floatValue();
            } else if (valObj instanceof Float)
            {
                value = ((Float)valObj).floatValue();
            } else if (valObj instanceof Double)
            {
                value = ((Double)valObj).floatValue();
            } else
            {
                log.error("getFloat - Class type is "+valObj.getClass().getName());
            }
        } else
        {
            log.error("getFloat - Result Object is null for["+valObj+"]");
        }
        return value;
    }

    public static double getDouble(Object valObj)
    {
        double value = 0.0;
        if (valObj != null)
        {
            if (valObj instanceof Integer)
            {
                value = ((Integer)valObj).doubleValue();
            } else if (valObj instanceof Long)
            {
                value = ((Long)valObj).doubleValue();
            } else if (valObj instanceof Float)
            {
                value = ((Float)valObj).doubleValue();
            } else if (valObj instanceof Double)
            {
                value = ((Double)valObj).doubleValue();
            } else
            {
                log.error("getDouble - Class type is "+valObj.getClass().getName());
            }
        } else
        {
            log.error("getDouble - Result Object is null for["+valObj+"]");
        }
        return value;
    }

    public static int getInt(Object valObj)
    {
        int value = 0;
        if (valObj != null)
        {
            if (valObj instanceof Integer)
            {
                value = ((Integer)valObj).intValue();
            } else if (valObj instanceof Long)
            {
                value = ((Long)valObj).intValue();
            } else if (valObj instanceof Float)
            {
                value = ((Float)valObj).intValue();
            } else if (valObj instanceof Double)
            {
                value = ((Double)valObj).intValue();
            } else
            {
                log.error("getInt - Class type is "+valObj.getClass().getName());
            }
        } else
        {
            log.error("getInt - Result Object is null for["+valObj+"]");
        }
        return value;
    }

    public static String getString(Object valObj)
    {
        if (valObj != null)
        {
            if (valObj instanceof String)
            {
                return (String)valObj;
            }
            // else
            log.error("getString - Class type is "+valObj.getClass().getName()+" should be String");
        } else
        {
            log.error("getString - Result Object is null for["+valObj+"] in getString");
        }
        return "";
   }
    
    /**
     * Converts a String to the class that is passed in.
     * @param dataStr the data string to be converted
     * @param cls the class that the string is to be converted t
     * @return the data object
     */
    public static <T> Object convertDataFromString(final String dataStr, final Class<T> cls)
    {
        log.debug("Trying to convertDataFromString dataStr [" + dataStr + "] of class[" + cls + "]");
        if (cls == Integer.class)
        {
            return Integer.parseInt(dataStr);
            
        } else if (cls == Float.class)
        {
            return Float.parseFloat(dataStr);
            
        } else if (cls == Double.class)
        {
            return Double.parseDouble(dataStr);
            
        } else if (cls == Long.class)
        {
            return Long.parseLong(dataStr);
            
        } else if (cls == Short.class)
        {
            return Short.parseShort(dataStr);
            
        } else if (cls == Byte.class)
        {
            return Byte.parseByte(dataStr);
            
        } else if (cls == Calendar.class)
        {
            return getCalendar(dataStr, scrDateFormat);
            
        } else if (cls == Date.class)
        {
            return getDate(dataStr, scrDateFormat);
            
        } else if (cls == Timestamp.class)
        {
            return getDate(dataStr, scrDateFormat);
            
        } else
        {
            throw new RuntimeException("Unsupported type for conversion["+cls.getSimpleName()+"]");
        }
    }
    
    /**
     * Returns a Date object from a string
     * @param dateStr the string to convert
     * @param dateWrapper the formatter to use
     * @return null or a Calendar Object
     */
    public static Date getDate(final String dateStr, final DateWrapper dateWrapper)
    {
        Calendar cal = getCalendar(dateStr, dateWrapper);
        if (cal != null)
        {
            return cal.getTime();
        }
        return null;
    }

    /**
     * Returns a Date object from a string
     * @param dateStr the string to convert
     * @param dateWrapper the formatter to use
     * @return null or a Calendar Object
     */
    public static Calendar getCalendar(final String dateStr, final DateWrapper dateWrapper)
    {
        if (StringUtils.isNotEmpty(dateStr))
        {
            try
            {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateWrapper.getSimpleDateFormat().parse(dateStr));
                return cal;

            } catch (ParseException ex)
            {
                log.error("Date is in error for parsing["+dateStr+"]");
            }
        }
        return null;
    }

    public static boolean getBoolean(Object valObj)
    {
        if (valObj != null)
        {
            if (valObj instanceof String)
            {
                String valStr = ((String)valObj).toLowerCase();
                if (valStr.equals("true"))
                {
                    return true;
                } else if (valStr.equals("false"))
                {
                    return false;
                } else
                {
                    log.error("getBoolean - value is not 'true' or 'false'");
                }
            } else
            {
                log.error("getBoolean - Class type is "+valObj.getClass().getName()+" should be String");
            }
        } else
        {
            log.error("getBoolean - Result Object is null for["+valObj+"]");
        }
        return false;
   }


     /**
      * Helper method for returning data if it is of a particular Class.
      * Meaning is this data implementing an interface or is it derived from some other class.
     * @param data the generic data
     * @param classObj the class in questions
     * @return the data if it is derived from or implements, can it be cast to
     */
    public static Object getDataForClass(final Object data, Class<?> classObj)
    {
        // Short circut if all they are interested in is the generic "Object"
        if (classObj == Object.class)
        {
            return data;
        }

        // Check to see if it supports the aggrgation interface
        if (data instanceof GhostDataAggregatable)
        {
            Object newData = ((GhostDataAggregatable)data).getDataForClass(classObj);
            if (newData != null)
            {
                return newData;
            }
        }

        Vector<Class<?>> classes = new Vector<Class<?>>();

        // First Check interfaces
        Class<?>[] theInterfaces = data.getClass().getInterfaces();
        for (Class<?> co : theInterfaces)
        {
            classes.add(co);
        }

        if (classes.contains(classObj))
        {
            return data;
        }
        classes.clear();

        // Now Check super classes
        Class<?> superclass = data.getClass().getSuperclass();
        while (superclass != null)
        {
            classes.addElement(superclass);
            superclass = superclass.getSuperclass();
        }

        // Wow, it doesn't support anything
        return null;
    }

    /**
     * Returns the type of the current OS.
     * @return the type of the current OS
     */
    public static OSTYPE getOSType()
    {
        return oSType;
    }

    //-----------------------------------------------------------------------------------------
    // Menu Helpers
    //-----------------------------------------------------------------------------------------

    /**
     * Creates a JMenu.
     * @param resKey the resource key for localization
     * @param virtualKeyCode the virtual key code i.e. KeyEvent.VK_N
     * @param mneu thee mneumonic
     * @return the JMenuItem
     */
    protected static JMenuItem createMenu(final String resKey, final int virtualKeyCode, final String mneu)
    {
        JMenuItem jmi = new JMenuItem(getResourceString(resKey));
        if (oSType != OSTYPE.MacOSX)
        {
            jmi.setMnemonic(mneu.charAt(0));
        }
        jmi.setAccelerator(KeyStroke.getKeyStroke(virtualKeyCode, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        return jmi;
    }

    /**
     * @param app
     * @param isPlatformSpecific
     * @param includeCutCopyPaste
     * @return the menubar
     */
    public static JMenuBar getBasicMenuBar(final boolean includeCutCopyPaste)
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = createMenu(menuBar, getResourceString("FileMenu"), getResourceString("FileMneu"));

        if (oSType != OSTYPE.MacOSX)
        {
            fileMenu.addSeparator();
            fileMenu.add(createMenu(getResourceString("ExitMenu"), getResourceString("ExitAccl").charAt(0), getResourceString("ExitMneu")));
        } else
        {
            //new MacOSAppHandler((AppIFace)app);
        }

        if (includeCutCopyPaste)
        {
            JMenu editMenu = createMenu(menuBar, getResourceString("EditMenu"), getResourceString("EditMneu"));
            editMenu.add(createMenu(getResourceString("CutMenu"), getResourceString("CutAccl").charAt(0), getResourceString("CutMneu")));
            editMenu.add(createMenu(getResourceString("CopyMenu"), KeyEvent.VK_C, getResourceString("CopyMneu")));
            editMenu.add(createMenu(getResourceString("PasteMenu"), KeyEvent.VK_V, getResourceString("PasteMneu")));
        }



        return menuBar;
    }

    /**
     * Creates a JMenuItem.
     * @param label the label of the menu item
     * @param mnemonic the mnemonic
     * @param accessibleDescription the accessible Description
     * @param enabled enabled
     * @param action the aciton
     * @return menu item
     */
    public static JMenuItem createMenuItem(final String         label,
                                           final String         mnemonic,
                                           final String         accessibleDescription,
                                           final boolean        enabled,
                                           final ActionListener al)
    {
        JMenuItem mi = new JMenuItem(label);
        if (isNotEmpty(mnemonic))
        {
            mi.setMnemonic(mnemonic.charAt(0));
        }
        if (isNotEmpty(accessibleDescription))
        {
            mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
        }
        mi.addActionListener(al);
        mi.setEnabled(enabled);
        return mi;
    }
    
    /**
     * Creates a JMenuItem.
     * @param menu parent menu
     * @param label the label of the menu item
     * @param mnemonic the mnemonic
     * @param accessibleDescription the accessible Description
     * @param enabled enabled
     * @param action the aciton
     * @return menu item
     */
    public static JMenuItem createMenuItem(final JMenu          menu,
                                           final String         label,
                                           final String         mnemonic,
                                           final String         accessibleDescription,
                                           final boolean        enabled,
                                           final ActionListener al)
    {
        JMenuItem mi = createMenuItem(label, mnemonic, accessibleDescription, enabled, al);
        if (menu != null)
        {
            menu.add(mi);
        }
        return mi;
    }
    
    /**
     * Creates a JMenuItem.
     * @param menu parent menu
     * @param label the label of the menu item
     * @param mnemonic the mnemonic
     * @param accessibleDescription the accessible Description
     * @param enabled enabled
     * @param action the aciton
     * @return menu item
     */
    public static JMenuItem createMenuItem(final JPopupMenu     popupMenu,
                                           final String         label,
                                           final String         mnemonic,
                                           final String         accessibleDescription,
                                           final boolean        enabled,
                                           final ActionListener al)
    {
        JMenuItem mi = createMenuItem(label, mnemonic, accessibleDescription, enabled, al);
        if (popupMenu != null)
        {
            popupMenu.add(mi);
        }
        return mi;
    }
    
    /**
     * Creates a JMenuItem.
     * @param menu parent menu
     * @param label the label of the menu item
     * @param mnemonic the mnemonic
     * @param accessibleDescription the accessible Description
     * @param enabled enabled
     * @param action the aciton
     * @return menu item
     */
    public static JMenuItem createMenuItemWithAction(final JMenu   menu,
                                                     final String  label,
                                                     final String  mnemonic,
                                                     final String  accessibleDescription,
                                                     final boolean enabled,
                                                     final Action  action)
    {
        JMenuItem mi = new JMenuItem(label);
        if (menu != null)
        {
            menu.add(mi);
        }
        if (isNotEmpty(mnemonic))
        {
            mi.setMnemonic(mnemonic.charAt(0));
        }
        if (isNotEmpty(accessibleDescription))
        {
            mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
        }
        if (action != null)
        {
            mi.addActionListener(action);
            action.addPropertyChangeListener(new MenuItemPropertyChangeListener(mi));
            action.setEnabled(enabled);
        }

        return mi;
    }

    /**
     * Creates a JCheckBoxMenuItem.
     * @param menu parent menu
     * @param label the label of the menu item
     * @param mnemonic the mnemonic
     * @param accessibleDescription the accessible Description
     * @param enabled enabled
     * @param action the aciton
     * @return menu item
     */
    public static JCheckBoxMenuItem createCheckBoxMenuItem(final JMenu          menu,
                                                           final String         label,
                                                           final String         mnemonic,
                                                           final String         accessibleDescription,
                                                           final boolean        enabled,
                                                           final AbstractAction action)
    {
        JCheckBoxMenuItem mi = new JCheckBoxMenuItem(getResourceString(label));
        if (menu != null)
        {
            menu.add(mi);
        }
        if (isNotEmpty(mnemonic))
        {
            mi.setMnemonic(getResourceString(mnemonic).charAt(0));
        }
        if (isNotEmpty(accessibleDescription))
        {
            mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
        }
        if (action != null)
        {
            mi.addActionListener(action);
            action.addPropertyChangeListener(new MenuItemPropertyChangeListener(mi));
            action.setEnabled(enabled);
        }

        return mi;
    }

    /**
     * Creates a JRadioButtonMenuItem.
     * @param menu parent menu
     * @param label the label of the menu item
     * @param mnemonic the mnemonic
     * @param accessibleDescription the accessible Description
     * @param enabled enabled
     * @param action the aciton
     * @return menu item
     */
    public static JRadioButtonMenuItem createRadioButtonMenuItem(final JMenu          menu,
                                                              final String         label,
                                                              final String         mnemonic,
                                                              final String         accessibleDescription,
                                                              final boolean        enabled,
                                                              final AbstractAction action)
    {
        JRadioButtonMenuItem mi = new JRadioButtonMenuItem(getResourceString(label));
        if (menu != null)
        {
            menu.add(mi);
        }
        if (isNotEmpty(mnemonic))
        {
            mi.setMnemonic(getResourceString(mnemonic).charAt(0));
        }
        if (isNotEmpty(accessibleDescription))
        {
            mi.getAccessibleContext().setAccessibleDescription(accessibleDescription);
        }
        if (action != null)
        {
            mi.addActionListener(action);
            action.addPropertyChangeListener(new MenuItemPropertyChangeListener(mi));
            action.setEnabled(enabled);
        }

        return mi;
    }

    /**
     * Create a menu.
     * @param menuBar the menubar
     * @param labelKey the label key to be localized
     * @param mneuKey the mneu key to be localized
     * @return returns a menu
     */
    public static JMenu createMenu(final JMenuBar menuBar, final String labelKey, final String mneuKey)
    {
        JMenu menu = null;
        try
        {
            menu = menuBar.add(new JMenu(getResourceString(labelKey)));
            if (oSType != OSTYPE.MacOSX)
            {
                menu.setMnemonic(getResourceString(mneuKey).charAt(0));
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
            log.error("Couldn't create menu for " + labelKey + "  " + mneuKey);
        }
        return menu;
    }

    //-----------------------------------------------------------------------------------------
    // DataObject and Data Access Helpers
    //-----------------------------------------------------------------------------------------


    /**
     * Return an array of values given a FormCell definition. Note: The returned array is owned by the utility and
     * may be longer than the number of fields defined in the CellForm object. Any additional "slots" in the array that are used
     * are set to null;
     * @param fieldNames the array of field name to be filled ( the array is really the path to the object)
     * @param dataObj the dataObj from which to get the data from
     * @param getter the DataObjectGettable to use to get the data
     * @return an array of values at least as long as the fielName list, but may be longer
     */
    public static Object[] getFieldValues(final String[] fieldNames,
                                          final Object dataObj,
                                          final DataObjectGettable getter)
    {
        if (scrDateFormat == null)
        {
            scrDateFormat = AppPrefsCache.getDateWrapper("ui", "formatting", "scrdateformat");
        }

        if (fieldNames.length > values.length)
        {
            values = new Object[fieldNames.length];
        } else
        {
            for (int i=fieldNames.length;i<values.length;i++)
            {
                values[i] = null;
            }
        }

        boolean  allFieldsNull = true;

        int cnt = 0;
        for (String fldName : fieldNames)
        {
            Object dataValue;
            if (getter.usesDotNotation())
            {
                int inx = fldName.indexOf(".");
                if (inx > -1)
                {
                    StringTokenizer st = new StringTokenizer(fldName, ".");
                    Object data = dataObj;
                    while (data != null && st.hasMoreTokens())
                    {
                        data = getter.getFieldValue(data, st.nextToken());
                    }
                    dataValue = data;
                } else
                {
                    dataValue = getter.getFieldValue(dataObj, fldName);
                }
            } else
            {
                dataValue = getter.getFieldValue(dataObj, fldName);
            }

            if (dataValue instanceof java.util.Date)
            {
                dataValue = scrDateFormat.format((java.util.Date)dataValue);

            } else if (dataValue instanceof java.util.Calendar)
            {
                dataValue = scrDateFormat.format(((java.util.Calendar)dataValue).getTime());
            }

            if (allFieldsNull && dataValue != null)
            {
                allFieldsNull = false;
            }
            values[cnt++] = dataValue;
        }

         return allFieldsNull ? null : values;
    }

    /**
     * Return an array of values given a FormCell definition. Note: The returned array is owned by the utility and
     * may be longer than the number of fields defined in the CellForm object. Any additional "slots" in the array that are used
     * are set to null;
     * @param formCell the definition of the field to get
     * @param dataObj the dataObj from which to get the data from
     * @param getter the DataObjectGettable to use to get the data
     * @return an array of values at least as long as the fielName list, but may be longer
     */
    public static Object[] getFieldValues(final FormCellIFace formCell, final Object dataObj, final DataObjectGettable getter)
    {
        String[] fieldNames = formCell.getFieldNames();
        if( fieldNames != null && fieldNames.length != 0 )
        {
            return getFieldValues(fieldNames, dataObj, getter);
        }
        // else
        return null;
    }


    /**
     * Returns a single String value that formats all the value in the array per the format mask
     * @param valuesArg the array of values
     * @param format the format mask
     * @return a string with the formatted values
     */
    public static Object getFormattedValue(final Object[] valuesArg,
                                           final String format)
    {
        if (valuesArg == null)
        {
            return "";
        }

        try
        {
            Formatter formatter = new Formatter();
            formatter.format(format, valuesArg);
            return formatter.toString();

        } catch (java.util.IllegalFormatConversionException ex)
        {
            return valuesArg[0] != null ? valuesArg[0].toString() : "";
        }

    }

    //-------------------------------------------------------
    //-- Helpers for creating Lists and Maps
    //-------------------------------------------------------
    /**
     * @return a string map
     */
    public static Map<String, String> createMap()
    {
        return new Hashtable<String, String>();
    }

    /**
     * @return string list
     */
    public static List<String> createList()
    {
        return new ArrayList<String>();
    }

    //-------------------------------------------------------
    //-- Helpers for logging into the database
    //-------------------------------------------------------

    /**
     * Constructs the full connection string for JDBC
     * @param dbProtocol the protocol
     * @param dbServer the server name machine or IP address
     * @param dbName the name of the database
     * @return the full JDBC connection string
     */
    public static String constructJDBCConnectionString(final String dbProtocol,
                                                       final String dbServer,
                                                       final String dbName)
    {
        StringBuilder strBuf = new StringBuilder(64);
        strBuf.append("jdbc:");
        strBuf.append(dbProtocol);

        if (isNotEmpty(dbServer))
        {
            strBuf.append("://");
            strBuf.append(dbServer);
            strBuf.append("/");
            strBuf.append(dbName);

        } else
        {
            strBuf.append(":");
            strBuf.append(dbName);
        }
        return strBuf.toString();
    }

    /**
     * Tries to login using the supplied params
     * @param dbDriver the driver (a class name)
     * @param dbDialect the Hibernate Dialect class name
     * @param connectionStr the full JDBC connection string that includes the database name
     * @param dbName the name of the database
     * @param dbUsername the user name
     * @param dbPassword the password
     * @return true if logged in, false if not
     */
    public static boolean tryLogin(final String dbDriver,
                                   final String dbDialect,
                                   final String dbName,
                                   final String connectionStr,
                                   final String dbUsername,
                                   final String dbPassword)
    {
        FormHelper.setCurrentUserEditStr("");

        DBConnection dbConn = DBConnection.getInstance();

        dbConn.setDriver(dbDriver);
        dbConn.setDialect(dbDialect);
        dbConn.setDatabaseName(dbName);
        dbConn.setConnectionStr(connectionStr);
        dbConn.setUsernamePassword(dbUsername, dbPassword);

        Connection connection = dbConn.createConnection();
        if (connection != null)
        {
            try
            {
                connection.close();
                
                // This is used to fill who editted the object
                FormHelper.setCurrentUserEditStr(dbUsername);
                return true;
                
            } catch (SQLException ex)
            {
                // do nothing
            }

        }
        // else
        return false;
    }

    /**
     * Tries to do the login, if doAutoLogin is set to true it will try without displaying a dialog
     * and if the login fails then it will display the dialog
     * @param doAutoLogin whether to try to utomatically log the user in
     * @param doAutoClose hwther it should automatically close the window when it is logged in successfully
     * @param useDialog use a Dialog or a Frame
     * @param listener a listener for when it is logged in or fails
     */
    public static DatabaseLoginPanel doLogin(final boolean doAutoLogin,
                                             final boolean doAutoClose,
                                             final boolean useDialog,
                                             final DatabaseLoginListener listener)
    {
        boolean doAutoLoginNow = doAutoLogin && AppPreferences.getLocalPrefs().getBoolean("login.autologin", false);

        if (useDialog)
        {
            JDialog.setDefaultLookAndFeelDecorated(false); 
            DatabaseLoginDlg dlg = new DatabaseLoginDlg((Frame)UIRegistry.getTopWindow(), listener);
            dlg.setAlwaysOnTop(true);
            JDialog.setDefaultLookAndFeelDecorated(true); 
            dlg.setDoAutoLogin(doAutoLoginNow);
            dlg.setDoAutoClose(doAutoClose);
            UIHelper.centerAndShow(dlg);

            return dlg.getDatabaseLoginPanel();

        }
        // else
        class DBListener implements DatabaseLoginListener
        {
            protected JFrame                frame;
            protected DatabaseLoginListener frameDBListener;
            protected boolean               doAutoCloseOfListener;

            public DBListener(JFrame frame, DatabaseLoginListener frameDBListener, boolean doAutoCloseOfListener)
            {
                this.frame                 = frame;
                this.frameDBListener       = frameDBListener;
                this.doAutoCloseOfListener = doAutoCloseOfListener;
            }
            
            public void loggedIn(final String databaseName, final String userName)
            {
                if (doAutoCloseOfListener)
                {
                    frame.setVisible(false);
                }
                frameDBListener.loggedIn(databaseName, userName);
            }

            public void cancelled()
            {
                frame.setVisible(false);
                frameDBListener.cancelled();
            }
        }
        JFrame.setDefaultLookAndFeelDecorated(false);

        JFrame frame = new JFrame(getResourceString("logintitle"));
        DatabaseLoginPanel panel = new DatabaseLoginPanel(new DBListener(frame, listener, doAutoClose), false);
        frame.setAlwaysOnTop(true);
        panel.setAutoClose(doAutoClose);
        panel.setWindow(frame);
        frame.setContentPane(panel);
        frame.setIconImage(IconManager.getIcon("AppIcon", IconManager.IconSize.Std16).getImage());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();

        if (doAutoLoginNow)
        {
            panel.doLogin();
        }
        UIHelper.centerAndShow(frame);

        return panel;
    }
    
    /**
     * Tries to do the login, if doAutoLogin is set to true it will try without displaying a dialog
     * and if the login fails then it will display the dialog
     * @param doAutoLogin whether to try to utomatically log the user in
     * @param doAutoClose hwther it should automatically close the window when it is logged in successfully
     * @param useDialog use a Dialog or a Frame
     * @param listener a listener for when it is logged in or fails
     */
    public static CustomDBConverterDlg doSpecifyConvert()
    {
        log.debug("doSpecifyConvert");
        CustomDBConverter converter = new CustomDBConverter();
        converter.setUpSystemProperties();
        converter.setUpPreferrences();
        final CustomDBConverterListener listener = converter;
        JDialog.setDefaultLookAndFeelDecorated(false);
        CustomDBConverterDlg dlg = new CustomDBConverterDlg((Frame)UIRegistry.getTopWindow(), listener);
        JDialog.setDefaultLookAndFeelDecorated(true);
        UIHelper.centerAndShow(dlg);
        return dlg;
    }
    /**
     * Creates an UnhandledException dialog.
     * @param message the string
     */
    /*public static void showUnhandledException(final String message)
    {
        UnhandledExceptionDialog dlg = instance.new UnhandledExceptionDialog(message);
        dlg.setVisible(true);
        throw new RuntimeException(message);
    }*/
    
    /**
     * There are certain exceptions that are coming from the JVM or toolkits that we have
     * no control over. This will mask them out.
     * 
     * @param e the thrown exception
     * @return whether the exception dialog should be displayed
     */
    protected static boolean isExceptionOKToThrow(Throwable e)
    {
        if (e instanceof java.lang.ArrayIndexOutOfBoundsException)
        {
            if (e.getMessage().indexOf("apple.awt.CWindow.displayChanged") > 0)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Creates and attaches the UnhandledException handler for piping them to the dialog
     */
    public static void attachUnhandledException()
    {
        log.debug("attachUnhandledException "+Thread.currentThread().getName()+ " "+Thread.currentThread().hashCode());
        
        Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler()
        {
            public void uncaughtException(Thread t, Throwable e)
            {
                if (isExceptionOKToThrow(e))
                {
                    UIHelper.showUnhandledException(e);
                }
                UsageTracker.incrUsageCount("UncaughtException");
                e.printStackTrace();
            }
        });
        
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
        {
            public void uncaughtException(Thread t, Throwable e)
            {
                if (isExceptionOKToThrow(e))
                {
                    UIHelper.showUnhandledException(e);
                }
                UsageTracker.incrUsageCount("UncaughtException");
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates an UnhandledException dialog.
     * @param throwable the throwable
     */
    public static void showUnhandledException(final Throwable throwable)
    {              
        log.debug("showUnhandledException "+Thread.currentThread().getName()+ " "+Thread.currentThread().hashCode());
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                UnhandledExceptionDialog dlg = new UnhandledExceptionDialog(throwable);
                dlg.setVisible(true);

                attachUnhandledException();
            }
        });
        
    }

    /**
     * Creates an UnhandledException dialog.
     * @param ex the exception
     */
    /*public static void showUnhandledException(final Exception ex)
    {
        UnhandledExceptionDialog dlg = instance.new UnhandledExceptionDialog(ex);
        dlg.setVisible(true);
        
        log.error(ex);
        ex.printStackTrace();
        
        throw new RuntimeException(ex);
    }*/
    
    
    //-------------------------------------------------------------------------
    // Inner Classes
    //-------------------------------------------------------------------------
    
    /**
     * Walks parents until it gets to a Window
     * @param comp the current component whiching for its parent
     * @return the parent frame
     */
    public static Window getWindow(final Component comp)
    {
        Component parent = comp.getParent();
        do
        {
            if (parent instanceof Window)
            {
                return (Window)parent;
            }
            parent = parent.getParent();
        } while (parent != null);
        
        return null;
    }
    
    /**
     * Walks parents until it gets to a frame
     * @param comp the current component whiching for its parent
     * @return the parent frame
     */
    public static Frame getFrame(final Component comp)
    {
        Window window = UIHelper.getWindow(comp);
        return window instanceof Frame ? (Frame)window : (Frame)UIRegistry.getTopWindow();
    }
    
    /**
     * Helper to create an icon button.
     * @param iconName the name of the icon
     * @param toolTip the text of the tool tip
     * @param size the size of the icon
     * @param focusable whether the button can take focus
     * @return a icon btn
     */
    public static JButton createButton(final String iconName, final String toolTip, IconManager.IconSize size, final boolean focusable)
    {
        JButton btn = new JButton(IconManager.getIcon(iconName, size));
        btn.setToolTipText(toolTip);
        btn.setFocusable(focusable);
        btn.setMargin(new Insets(1,1,1,1));
        btn.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        return btn;
    }
    
    /**
     * Creates a dialog for editting or viewing a data object.
     * @param altView the current AaltView
     * @param mainComp the mainComp that this is being launched from
     * @param dataObj the data object for the dialog (cannot be NULL)
     * @param isEditMode whether it is in edit mode
     * @param isNewObject whether it is a new object
     * @return the dialog
     */
    public static  ViewBasedDisplayIFace createDataObjectDialog(@SuppressWarnings("unused") 
                                                                final AltViewIFace     altView, 
                                                                final JComponent       mainComp, 
                                                                final FormDataObjIFace dataObj, 
                                                                final boolean          isEditMode,
                                                                final boolean          isNewObject)
    {
        DBTableInfo setTI = DBTableIdMgr.getInstance().getByClassName(dataObj.getClass().getName());
        String defFormName = setTI.getEditObjDialog();

        if (StringUtils.isNotEmpty(defFormName))
        {
            int     opts = (isNewObject ? MultiView.IS_NEW_OBJECT : MultiView.NO_OPTIONS) | MultiView.HIDE_SAVE_BTN;
            String  title   = (isNewObject && isEditMode) ? getResourceString("Edit") : dataObj.getIdentityTitle();
            ViewBasedDisplayIFace dialog = UIRegistry.getViewbasedFactory().createDisplay(UIHelper.getWindow(mainComp),
                                                                        defFormName,
                                                                        title,
                                                                        getResourceString(isEditMode ? "Accept" : "Close"),
                                                                        isEditMode,
                                                                        opts,
                                                                        FRAME_TYPE.DIALOG);
            return dialog;
        }
        // else
        log.error("The Default Form Name is empty for Object type ["+dataObj.getClass().getName()+"]");
        
        return null;
    }
    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param al the action listener
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final String               toolTipTextKey, 
                                        final ActionListener       al)
    {
        return createIconBtn(iconName, null, toolTipTextKey, false, al);
    }
    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param al the action listener
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final IconManager.IconSize size,
                                        final String               toolTipTextKey, 
                                        final ActionListener       al)
    {
        return createIconBtn(iconName, size, toolTipTextKey, false, al);
    }
    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param al the action listener
     * @param withEmptyBorder set an empyt border
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final IconManager.IconSize size,
                                        final String               toolTipTextKey, 
                                        final boolean              withEmptyBorder,
                                        final ActionListener       al)
    {
        JButton btn = new JButton(size != null ? IconManager.getIcon(iconName, size) : IconManager.getIcon(iconName));
        btn.setOpaque(false);
        
        if (!withEmptyBorder)
        {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {
                        ((JButton)e.getSource()).setBorder(focusBorder);
                    }
                    super.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {               
                        ((JButton)e.getSource()).setBorder(emptyBorder);
                    }
                    super.mouseExited(e);
                }
                
            });
            btn.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {
                        ((JButton)e.getSource()).setBorder(focusBorder);
                    }
                }
                public void focusLost(FocusEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {               
                        ((JButton)e.getSource()).setBorder(emptyBorder);
                    }
                }
                
            });
            btn.setBorder(emptyBorder);
        }
        if (StringUtils.isNotEmpty(toolTipTextKey))
        {
            btn.setToolTipText(getResourceString(toolTipTextKey));
        }
        if (al != null)
        {
            btn.addActionListener(al);
        }
        btn.setEnabled(false);
        btn.setMargin(new Insets(0,0,0,0));
        return btn;
    }

    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param al the action listener
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final String               toolTipTextKey,
                                        final Action               action)
    {
        return createIconBtn(iconName, null, toolTipTextKey, false, action);
    }
    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param withEmptyBorder set an empyt border
     * @param al the action listener
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final String               toolTipTextKey,
                                        final boolean              withEmptyBorder,
                                        final Action               action)
    {
        return createIconBtn(iconName, null, toolTipTextKey, withEmptyBorder, action);
    }
    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param al the action listener
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final IconManager.IconSize size,
                                        final String               toolTipTextKey,
                                        final Action               action)
    {
        return createIconBtn(iconName, size, toolTipTextKey, false, action);
    }
    
    /**
     * Creates an icon button with tooltip and action listener.
     * @param iconName the name of the icon (use default size)
     * @param toolTipTextKey the tooltip text resource bundle key
     * @param al the action listener
     * @param withEmptyBorder set an empyt border
     * @return the JButton icon button
     */
    public static JButton createIconBtn(final String               iconName, 
                                        final IconManager.IconSize size,
                                        final String               toolTipTextKey, 
                                        final boolean              withEmptyBorder,
                                        final Action               action)
    {
        JButton btn = new JButton(action)
        {
            @Override
            public void setEnabled(boolean enabled)
            {
                super.setEnabled(enabled);
                setBorder(emptyBorder);
            }
        };
        
        btn.setOpaque(false);
        if (!withEmptyBorder)
        {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {
                        ((JButton)e.getSource()).setBorder(focusBorder);
                    }
                    super.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {               
                        ((JButton)e.getSource()).setBorder(emptyBorder);
                    }
                    super.mouseExited(e);
                }
                
            });
            btn.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {
                        ((JButton)e.getSource()).setBorder(focusBorder);
                    }
                }
                public void focusLost(FocusEvent e)
                {
                    if (((JButton)e.getSource()).isEnabled())
                    {               
                        ((JButton)e.getSource()).setBorder(emptyBorder);
                    }
                }
                
            });
            btn.setBorder(emptyBorder);
        }
        btn.setIcon(size != null ? IconManager.getIcon(iconName, size) : IconManager.getIcon(iconName));
        btn.setText(null);
        if (StringUtils.isNotEmpty(toolTipTextKey))
        {
            btn.setToolTipText(getResourceString(toolTipTextKey));
        }
        btn.setEnabled(false);
       
        btn.setFocusable(true);
        btn.setMargin(new Insets(0,0,0,0));
        return btn;
    }
    
    /**
     * Calculates and sets the each column to it preferred size.  NOTE: This
     * method also sets the table height to 10 rows.
     * 
     * @param table the table to fix up
     */
    public static void calcColumnWidths(JTable table)
    {
        JTableHeader header = table.getTableHeader();

        TableCellRenderer defaultHeaderRenderer = null;

        if (header != null)
        {
            defaultHeaderRenderer = header.getDefaultRenderer();
        }

        TableColumnModel columns = table.getColumnModel();
        TableModel data = table.getModel();

        int margin = columns.getColumnMargin(); // only JDK1.3

        int rowCount = data.getRowCount();

        int totalWidth = 0;

        for (int i = columns.getColumnCount() - 1; i >= 0; --i)
        {
            TableColumn column = columns.getColumn(i);

            int columnIndex = column.getModelIndex();

            int width = -1;

            TableCellRenderer h = column.getHeaderRenderer();

            if (h == null)
                h = defaultHeaderRenderer;

            if (h != null) // Not explicitly impossible
            {
                Component c = h.getTableCellRendererComponent
                       (table, column.getHeaderValue(),
                        false, false, -1, i);

                width = c.getPreferredSize().width;
            }

            for (int row = rowCount - 1; row >= 0; --row)
            {
                TableCellRenderer r = table.getCellRenderer(row, i);

                Component c = r.getTableCellRendererComponent
                   (table,
                    data.getValueAt(row, columnIndex),
                    false, false, row, i);

                    width = Math.max(width, c.getPreferredSize().width+10); // adding an arbitray 10 pixels to make it look nicer
            }

            if (width >= 0)
            {
                column.setPreferredWidth(width + margin); // <1.3: without margin
            }
            else
            {
                // ???
            }

            totalWidth += column.getPreferredWidth();
        }

        // If you like; This does not make sense for two many columns!
        Dimension size = table.getPreferredScrollableViewportSize();
        //if (totalWidth > size.width)
        {
            size.height = Math.min(size.height, table.getRowHeight()*10);
            size.width  = totalWidth;
            table.setPreferredScrollableViewportSize(size);
        }
    }

    /**
     * Parses a string for ";" colon separated name/value pairs.  In order to allow '=' in the value portion
     * of the string, the first '=' is assumed to separate the name and value.  All other occurances of '='
     * are left untouched.
     * 
     * @param namedValuePairs a string of named/value pairs
     * @return a properties object with the named value pairs or null if the string it null or empty
     */
    public static Properties parseProperties(final String nameValuePairs)
    {
        if (isNotEmpty(nameValuePairs))
        {
            Properties props = new Properties();
            
            for (String pair : StringUtils.split(nameValuePairs, ";"))
            {
                int firstEqualsSign = pair.indexOf('=');

                // the the '=' isn't present or is the last character, ERROR
                if (firstEqualsSign == -1 || firstEqualsSign == pair.length()-1)
                {
                    log.error("Initialize string["+nameValuePairs+"] must be a set of name/value pairs separated by ';'.");
                }
                else
                {
                    String name = pair.substring(0, firstEqualsSign);
                    String value = pair.substring(firstEqualsSign+1);
                    props.put(name, value);
                }
            }
            return props.size() > 0 ? props : null;
        }
        return null;
    }
    
    /**
     * Returns a Properties object as ";" separated string of name/value pairs.
     * @param props the properties object
     * @return the string representing it
     */
    public String createNameValuePairs(final Properties props)
    {
        StringBuilder str = new StringBuilder();
        for (Object key : props.keySet())
        {
            if (str.length() > 0)
            {
                str.append(';');
            }
            str.append(key.toString());
            str.append('=');
            str.append(props.get(key).toString());
        }
        return str.toString();
    }
    
    /**
     * Get Property as int, if it is empty then it passes back the default value.
     * @param properties the properties
     * @param nameStr the name of the property
     * @param defVal the default value
     * @return
     */
    public static int getProperty(final Properties properties, final String nameStr, final int defVal)
    {
        if (properties != null)
        {
            String str = properties.getProperty(nameStr);
            if (StringUtils.isNotEmpty(str))
            {
                return Integer.parseInt(str);
            }
        } else
        {
            return defVal;
        }
        return -1;
    }

    /**
     * Get Property as boolean, if it is empty then it passes back the default value.
     * @param properties the properties
     * @param nameStr the name of the property
     * @param defVal the default value
     * @return
     */
    public static boolean getProperty(final Properties properties, final String nameStr, final boolean defVal)
    {
        if (properties != null)
        {
            String str = properties.getProperty(nameStr);
            if (StringUtils.isNotEmpty(str))
            {
                return str.equalsIgnoreCase("true");
            }
        } else
        {
            return defVal;
        }
        return false;
    }
    
    /**
     * Takes a string and separates the 'names' inside by the capitial letters.
     * @param nameToFix the name to fix
     * @return the new name with spaces in it
     */
    public static String makeNamePretty(final String nameToFix)
    {
        StringBuilder s = new StringBuilder();
        for (int i=0;i<nameToFix.length();i++)
        {
            if (i == 0) 
            {
                s.append(Character.toUpperCase(nameToFix.charAt(i)));
            } else
            {
                char c = nameToFix.charAt(i);
                if (Character.isUpperCase(c))
                {
                    s.append(' ');
                }
                s.append(c);
            }
        }
        return s.toString();  
    }
    
    
    /**
     * Strips directories off the end of a path.
     * @param path the path to be stripped
     * @param numToStrip the number to strip
     * @return the stripped directory path
     */
    public static String stripSubDirs(final String path, final int numToStrip)
    {
        String databasePath = path;
        
        for (int i=0;i<numToStrip;i++)
        {
            int endInx = databasePath.lastIndexOf("/");
            if (endInx > -1)
            {
            	databasePath = databasePath.substring(0, endInx);
            } else 
            {
            	endInx = databasePath.lastIndexOf("\\");
                if (endInx > -1)
                {
                	databasePath = databasePath.substring(0, endInx);

	            } else
	            {
	            	log.error("Couldn'f find / in ["+databasePath+"]");
	            }
            }
        }
        return databasePath;
    }
    
    
    /**
     * Creates rendering hints for Text.
     */
    public static RenderingHints createTextRenderingHints() 
    {
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                                                           RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        Object value = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
        try {
            Field declaredField = RenderingHints.class.getDeclaredField("VALUE_TEXT_ANTIALIAS_LCD_HRGB");
            value = declaredField.get(null);
            
        } catch (Exception e)
        {
            // do nothing
        }
        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, value);
        return renderingHints;
    }
    
    /**
     * Removes all the focus listeners for a component.
     * @param comp the comp
     */
    public static void removeFocusListeners(final Component comp)
    {
        for (FocusListener l : comp.getFocusListeners())
        {
            comp.removeFocusListener(l);
        }
    }

    /**
     * Removes the ListSelection Listeners.
     * @param comp the comp
     */
    public static void removeListSelectionListeners(final JList comp)
    {
        for (ListSelectionListener l : comp.getListSelectionListeners())
        {
            comp.removeListSelectionListener(l);
        }
    }
    
    /**
     * Removes all the focus listeners for a component.
     * @param comp the comp
     */
    public static void removeKeyListeners(final Component comp)
    {
        for (KeyListener l : comp.getKeyListeners())
        {
            comp.removeKeyListener(l);
        }
    }

    
    /**
     * Removes the Mouse Listeners.
     * @param c component
     */
    public static void removeMouseListeners(final Component c)
    {
        for (MouseListener l : c.getMouseListeners())
        {
            c.removeMouseListener(l);
        }
        for (MouseMotionListener l : c.getMouseMotionListeners())
        {
            c.removeMouseMotionListener(l);
        }
    }
    

    /**
     * Parse comma separated r,g,b string
     * @param rgb the string with comma separated color values
     * @return the Color object
     */
    public static Color parseRGB(final String rgb)
    {
        StringTokenizer st = new StringTokenizer(rgb, ",");
        if (st.countTokens() == 3)
        {
            String r = st.nextToken().trim();
            String g = st.nextToken().trim();
            String b = st.nextToken().trim();
            return new Color(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b));
        }
        throw new ConfigurationException("R,G,B value is bad ["+rgb+"]");
    }
    
    /**
     * @param addAL
     * @param addTT
     * @param removeAL
     * @param removeTT
     * @param editAL
     * @param editTT
     * @return
     */
    public static JPanel createAddRemoveEditBtnBar(final ActionListener addAL, 
                                            final String         addTT,
                                            final ActionListener removeAL, 
                                            final String         removeTT,
                                            final ActionListener editAL,
                                            final String         editTT)
    {
        int numBtns = (addAL != null ? 1 : 0) + (removeAL != null ? 1 : 0) + (editAL != null ? 1 : 0);
        PanelBuilder pb = new PanelBuilder(new FormLayout("f:p:g,"+ createDuplicateJGoodiesDef("p", "2px", numBtns), "p"));
        CellConstraints cc = new CellConstraints();
        int x = 2;
        if (addAL != null)
        {
            pb.add(createIconBtn("PlusSign", addTT, addAL), cc.xy(x,1));
            x += 2;
        }
        if (removeAL != null)
        {
            pb.add(createIconBtn("MinusSign", removeTT, removeAL), cc.xy(x,1));
            x += 2;
        }
        if (editAL != null)
        {
            pb.add(createIconBtn("EditIcon", editTT, editAL), cc.xy(x,1));
            x += 2;
        }
        return pb.getPanel();
    }
}

/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */

package edu.ku.brc.specify.plugins.latlon;

import static edu.ku.brc.ui.UIHelper.createComboBox;
import static edu.ku.brc.ui.UIHelper.createLabel;
import static edu.ku.brc.ui.UIHelper.setControlSize;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.ui.forms.ViewFactory;
import edu.ku.brc.ui.forms.formatters.NumberMinMaxFormatter;
import edu.ku.brc.ui.forms.validation.DataChangeListener;
import edu.ku.brc.ui.forms.validation.DataChangeNotifier;
import edu.ku.brc.ui.forms.validation.UIValidatable;
import edu.ku.brc.ui.forms.validation.ValFormattedTextFieldSingle;
import edu.ku.brc.ui.forms.validation.UIValidatable.ErrorType;
import edu.ku.brc.util.LatLonConverter;

/**
 * 
 * 
 * @author rods
 *
 * @code_status Beta
 *
 * Created Date: Jan 8, 2007
 *
 */
public class DDDDPanel extends JPanel implements LatLonUIIFace, DataChangeListener, ItemListener
{
    protected static final   String[] NORTH_SOUTH = {"N", "S"};
    protected static final   String[] EAST_WEST   = {"E", "W"};
    
    protected static String[] northSouth  = null;
    protected static String[] eastWest    = null;
          
    protected BigDecimal     minusOne      = new BigDecimal("-1.0");

    protected boolean        isViewMode = false;
    protected ValFormattedTextFieldSingle latitudeDD; 
    protected ValFormattedTextFieldSingle longitudeDD;
    
    protected BigDecimal     latitude;
    protected BigDecimal     longitude;
    
    protected JComboBox      latitudeDir;
    protected JComboBox      longitudeDir;
    protected JTextField     latitudeDirTxt;
    protected JTextField     longitudeDirTxt;
    protected JLabel         latLabel;
    protected JLabel         lonLabel;
    
    protected boolean        hasChanged = false;
    protected boolean        isRequired = false;
    protected ChangeListener changeListener = null;
    
    protected Vector<ValFormattedTextFieldSingle> textFields  = new Vector<ValFormattedTextFieldSingle>();
    protected Vector<DataChangeNotifier>          dcNotifiers = new Vector<DataChangeNotifier>();

    // For helper methods
    protected StringBuilder sb = new StringBuilder();
    protected enum ValState {Empty, Valid, Error}

    /**
     * Constructor. 
     */
    public DDDDPanel()
    {
        if (northSouth == null)
        {
            northSouth = new String[] {getResourceString(NORTH_SOUTH[0]), getResourceString(NORTH_SOUTH[1])};
        }

        if (eastWest == null)
        {
            eastWest = new String[] {getResourceString(EAST_WEST[0]), getResourceString(EAST_WEST[1])};
        }
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    public void setEnabled(final boolean enabled)
    {
        super.setEnabled(enabled);
        
        if (latitudeDir != null)
        {
            latitudeDir.setEnabled(enabled);
            longitudeDir.setEnabled(enabled);
        }
        if (latitudeDirTxt != null)
        {
            latitudeDirTxt.setEnabled(enabled);
            longitudeDirTxt.setEnabled(enabled);
        }
        
        for (ValFormattedTextFieldSingle vtfs : textFields)
        {
            vtfs.setEnabled(enabled);
        }
        
        latLabel.setEnabled(enabled);
        lonLabel.setEnabled(enabled);
    }
    
    /**
     * Creates and initializes the UI.
     */
    public void init()
    {
        createUI("p, 2px, p, 2px, p", 10, 10, 5, false);
    }
    
    /**
     * Creates the UI for the panel.
     * @param colDef the JGoodies column definition
     * @param latCols the number of columns for the latitude control
     * @param lonCols the number of columns for the longitude control
     * @param cbxIndex the column index of the combobox
     * @return return the builder
     */
    protected PanelBuilder createUI(final String colDef, 
                                    final int latCols,
                                    final int lonCols,
                                    final int cbxIndex,
                                    final boolean asDDIntegers)
    {
        latitudeDD    = asDDIntegers ? createTextField(Integer.class, latCols, 0, 90) : createTextField(Double.class, latCols, 0.0, 90.0);
        longitudeDD   = asDDIntegers? createTextField(Integer.class, lonCols, 0, 180) : createTextField(Double.class, lonCols, 0.0, 180.0);

        PanelBuilder    builder    = new PanelBuilder(new FormLayout(colDef, "p, 1px, p, c:p:g"), this);
        CellConstraints cc         = new CellConstraints();
        
        latitudeDir  = createDirComboxbox(true);
        longitudeDir = createDirComboxbox(false);
        
        JComponent latDir;
        JComponent lonDir;
        if (isViewMode)
        {
            latitudeDirTxt  = new JTextField(2);
            longitudeDirTxt = new JTextField(2);
            
            setControlSize(latitudeDirTxt);
            setControlSize(longitudeDirTxt);

            ViewFactory.changeTextFieldUIForDisplay(latitudeDirTxt, false);
            ViewFactory.changeTextFieldUIForDisplay(longitudeDirTxt, false);
            latDir = latitudeDirTxt;
            lonDir = longitudeDirTxt;   
            
        } else
        {
            latDir = latitudeDir;
            lonDir = longitudeDir;
        }

        builder.add(latLabel = createLabel("Latitude:", SwingConstants.RIGHT), cc.xy(1, 1)); // I18N
        builder.add(latitudeDD, cc.xy(3, 1));
        builder.add(latDir, cc.xy(cbxIndex, 1));
        
        builder.add(lonLabel = createLabel("Longitude:", SwingConstants.RIGHT), cc.xy(1, 3)); // I18N
        builder.add(longitudeDD, cc.xy(3, 3));
        builder.add(lonDir, cc.xy(cbxIndex, 3));
     
        return builder;
    }
    
    /**
     * Creates a combox for selecting direction.
     * @param forNorthSouth true N,S, false E,W
     * @return cbx
     */
    public JComboBox createDirComboxbox(final boolean forNorthSouth)
    {
        JComboBox cbx =  createComboBox(forNorthSouth ? northSouth : eastWest );
        cbx.addItemListener(this);
        return cbx;
    }
    
    /**
     * set the data into the U.
     */
    protected void setDataIntoUI()
    {

        if (latitude != null)
        {
            latitudeDir.setSelectedIndex(latitude.doubleValue() >= 0 ? 0 : 1);
            latitudeDD.setText(LatLonConverter.format(latitude.abs()));
            
            if (latitudeDirTxt != null)
            {
                latitudeDirTxt.setText(latitudeDir.getSelectedItem().toString());
            }
        } else
        {
            latitudeDD.setText("");
            if (latitudeDirTxt != null)
            {
                latitudeDirTxt.setText("");
            }
        }
        
        if (longitude != null)
        {
            longitudeDir.setSelectedIndex(longitude.doubleValue() >= 0 ? 0 : 1);
            longitudeDD.setText(LatLonConverter.format(longitude.abs()));
            if (longitudeDirTxt != null)
            {
                longitudeDirTxt.setText(longitudeDir.getSelectedItem().toString());
            }
        } else
        {
            longitudeDD.setText("");
            if (latitudeDirTxt != null)
            {
                longitudeDirTxt.setText("");
            }    
        }
 
    }
    
    /**
     * @param txtFields
     * @return
     */
    protected String getStringFromFields(final ValFormattedTextFieldSingle... txtFields)
    {
        sb.setLength(0);
        
        for (ValFormattedTextFieldSingle tf : txtFields)
        {
            String str = StringUtils.deleteWhitespace(tf.getText());
            if (StringUtils.isEmpty(str))
            {
                str = "0";
            }
            if (sb.length() > 0) sb.append(' ');
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * @param txtFields
     * @return
     */
    protected ValState evalState(final ValFormattedTextFieldSingle... txtFields)
    {
        for (ValFormattedTextFieldSingle tf : txtFields)
        {
            tf.setState(UIValidatable.ErrorType.Valid);
            tf.repaint();
        }
        ValState                    state   = ValState.Empty;
        boolean                     isEmpty = false;
        ValFormattedTextFieldSingle prevTF  = null;
        for (ValFormattedTextFieldSingle tf : txtFields)
        {
            String str = StringUtils.deleteWhitespace(tf.getText());
            if (StringUtils.isEmpty(str))
            {
                isEmpty = true;
                
            } else if (tf.getState() == UIValidatable.ErrorType.Valid && tf.getText().length() > 0)
            {
                if (isEmpty)
                {
                    if (prevTF != null)
                    {
                        prevTF.setState(UIValidatable.ErrorType.Error);
                        prevTF.repaint();
                    }
                    return ValState.Error;
                }
                state =  ValState.Valid;
                
            } else
            {
                return ValState.Error;
            }
            prevTF = tf;
        }
        return state;
    }

    /**
     * Helper method for setting the data into the UI.
     * @param doLatitude true does latitude, false does longitude
     */
    protected void getDataFromUI(final boolean doLatitude)
    {
        if (doLatitude)
        {
            String str = latitudeDD.getText();
            if (StringUtils.isNotEmpty(StringUtils.deleteWhitespace(str)))
            {
                latitude = LatLonConverter.convertDDDDToDDDD(str, NORTH_SOUTH[latitudeDir.getSelectedIndex()]);
            }
        } else
        {
            String str = longitudeDD.getText();
            if (StringUtils.isNotEmpty(StringUtils.deleteWhitespace(str)))
            {
                longitude = LatLonConverter.convertDDDDToDDDD(str, EAST_WEST[longitudeDir.getSelectedIndex()]);
            }
        }
    }
    
    /**
     * Helper to create a validated text field and hook up a datachange listener.
     * @param columns the number of columns
     * @return the textfield
     */
    protected ValFormattedTextFieldSingle createTextField(final Class<?> dataCls,
                                                          final int      columns,
                                                          final Number   minValue,
                                                          final Number   maxValue)
    {
        NumberMinMaxFormatter       fmt       = new NumberMinMaxFormatter(dataCls, columns, minValue, maxValue);
        ValFormattedTextFieldSingle textField = new ValFormattedTextFieldSingle(fmt, false, false, false);
        
        /*textField.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                ((JTextField)e.getSource()).selectAll();
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                ((ValFormattedTextFieldSingle)e.getSource()).setNew(false);
                doDataChanged();
                repaint();
            }
        });*/
        
        if (isViewMode)
        {
            ViewFactory.changeTextFieldUIForDisplay(textField, false);

        } else
        {
            textField.setRequired(isRequired);
            
            DataChangeNotifier dcn = new DataChangeNotifier(null, textField, null);
            dcn.addDataChangeListener(this);
            dcNotifiers.add(dcn);
    
            textField.getDocument().addDocumentListener(dcn);
        }
        
        textFields.add(textField);

        return textField;
    }
    
    /**
     * Notify the changeListeners.
     */
    protected void doDataChangeNotify()
    {
        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run()
            {
                hasChanged = true;
                if (changeListener != null)
                {
                    changeListener.stateChanged(null);
                }
            }
        });
    }
    
    /**
     *  Helper to notify data has changed.
     */
    protected void doDataChanged()
    {
        doDataChangeNotify();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#setViewMode(boolean)
     */
    public void setViewMode(boolean isViewModeArg)
    {
        this.isViewMode = isViewModeArg;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#getDataFromUI()
     */
    public void getDataFromUI()
    {
        getDataFromUI(true);
        getDataFromUI(false);
    }
    
    /* (non-Javadoc)
     * @see LatLonUIIFace#set(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
     */
    public void set(final BigDecimal latitude1, 
                    final BigDecimal longitude1)
    {
        this.latitude  = latitude1;
        this.longitude = longitude1;
        
        setDataIntoUI();
    }
    
    /* (non-Javadoc)
     * @see LatLonUIIFace#getLatitude()
     */
    public BigDecimal getLatitude()
    {
        getDataFromUI(true);
        return latitude;
    }
    
    /* (non-Javadoc)
     * @see LatLonUIIFace#getLongitude()
     */
    public BigDecimal getLongitude()
    {
        getDataFromUI(false);
        return longitude;
    }
    
    /* (non-Javadoc)
     * @see LatLonUIIFace#getLatitude()
     */
    public String getLatitudeDir()
    {
        getDataFromUI(true);
        return latitude.doubleValue() >= 0 ? northSouth[0] : northSouth[1];
    }
    
    /* (non-Javadoc)
     * @see LatLonUIIFace#getLongitude()
     */
    public String getLongitudeDir()
    {
        getDataFromUI(false);
        return longitude.doubleValue() >= 0 ? eastWest[0] : eastWest[1];
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#hasChanged()
     */
    public boolean hasChanged()
    {
        return hasChanged;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#setHasChanged(boolean)
     */
    public void setHasChanged(boolean hasChanged)
    {
        this.hasChanged = hasChanged;
        
        for (DataChangeNotifier dcn : dcNotifiers)
        {
            dcn.setDataChanged(hasChanged);
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#setIsRequired(boolean)
     */
    public void setIsRequired(boolean isRequired)
    {
        this.isRequired = isRequired;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#cleanUp()
     */
    public void cleanUp()
    {
        for (DataChangeNotifier dcn : dcNotifiers)
        {
            dcn.cleanUp();
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#setChangeListener(javax.swing.event.ChangeListener)
     */
    public void setChangeListener(final ChangeListener changeListener)
    {
        this.changeListener = changeListener;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#validateState()
     */
    public ErrorType validateState()
    {
        return validateStateTexFields();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.plugins.latlon.LatLonUIIFace#validateState()
     */
    protected ErrorType validateStateTexFields()
    {
        UIValidatable.ErrorType valState = UIValidatable.ErrorType.Valid;
        for (ValFormattedTextFieldSingle vtf : textFields)
        {
            UIValidatable.ErrorType errType = vtf.validateState();
            if (errType.ordinal() > valState.ordinal())
            {
                valState = errType;
            }
        }
        return valState;
    }
    
    //--------------------------------------------------------
    // DataChangedListener Interface
    //--------------------------------------------------------
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.DataChangeListener#dataChanged(java.lang.String, java.awt.Component, edu.ku.brc.ui.forms.validation.DataChangeNotifier)
     */
    public void dataChanged(String name, Component comp, DataChangeNotifier dcn)
    {
        doDataChanged();

    }
    
    //--------------------------------------------------------
    // ItemListener Interface
    //--------------------------------------------------------
    
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent e)
    {
        doDataChanged();
    }
}

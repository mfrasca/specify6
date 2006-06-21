/* Filename:    $RCSfile: ViewFactory.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2005/10/12 16:52:27 $
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
package edu.ku.brc.specify.ui.forms;

import static edu.ku.brc.specify.ui.validation.UIValidator.parseValidationType;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.split;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.specify.helpers.BrowserLauncher;
import edu.ku.brc.specify.prefs.PrefsCache;
import edu.ku.brc.specify.ui.BrowseBtnPanel;
import edu.ku.brc.specify.ui.ColorChooser;
import edu.ku.brc.specify.ui.ColorWrapper;
import edu.ku.brc.specify.ui.CommandAction;
import edu.ku.brc.specify.ui.CommandActionWrapper;
import edu.ku.brc.specify.ui.GetSetValueIFace;
import edu.ku.brc.specify.ui.ImageDisplay;
import edu.ku.brc.specify.ui.UIPluginable;
import edu.ku.brc.specify.ui.db.PickListDBAdapter;
import edu.ku.brc.specify.ui.db.TextFieldWithInfo;
import edu.ku.brc.specify.ui.forms.persist.AltView;
import edu.ku.brc.specify.ui.forms.persist.FormCell;
import edu.ku.brc.specify.ui.forms.persist.FormCellCommand;
import edu.ku.brc.specify.ui.forms.persist.FormCellField;
import edu.ku.brc.specify.ui.forms.persist.FormCellLabel;
import edu.ku.brc.specify.ui.forms.persist.FormCellPanel;
import edu.ku.brc.specify.ui.forms.persist.FormCellSeparator;
import edu.ku.brc.specify.ui.forms.persist.FormCellSubView;
import edu.ku.brc.specify.ui.forms.persist.FormRow;
import edu.ku.brc.specify.ui.forms.persist.FormViewDef;
import edu.ku.brc.specify.ui.forms.persist.View;
import edu.ku.brc.specify.ui.forms.persist.ViewDef;
import edu.ku.brc.specify.ui.validation.ComboBoxFromQueryFactory;
import edu.ku.brc.specify.ui.validation.DataChangeNotifier;
import edu.ku.brc.specify.ui.validation.FormValidator;
import edu.ku.brc.specify.ui.validation.UIValidator;
import edu.ku.brc.specify.ui.validation.ValComboBox;
import edu.ku.brc.specify.ui.validation.ValComboBoxFromQuery;
import edu.ku.brc.specify.ui.validation.ValFormattedTextField;
import edu.ku.brc.specify.ui.validation.ValListBox;
import edu.ku.brc.specify.ui.validation.ValPasswordField;
import edu.ku.brc.specify.ui.validation.ValTextArea;
import edu.ku.brc.specify.ui.validation.ValTextField;
import edu.ku.brc.specify.ui.validation.ValidatedJPanel;

/**
 * Creates FormViewObj object that implment the Viewable interface.
 *
 * @author rods
 *
 */
public class ViewFactory
{
    // Statics
    private static final Logger log = Logger.getLogger(ViewFactory.class);
    private static final ViewFactory  instance = new ViewFactory();

    // Data Members
    protected static SimpleDateFormat scrDateFormat  = null;
    protected static ColorWrapper     viewFieldColor = null;

    protected MultiView               rootMultiView  = null; // transient - is valid only during a build process

    /**
     * Constructor
     */
    protected ViewFactory()
    {
    }

    /**
     *
     * @return the singleton for the ViewMgr
     */
    public static ViewFactory getInstance()
    {
        return instance;
    }

    /**
     * CReates a panel with the "..." icon
     * @param comp the component to put into the panel
     */
    public JPanel createIconPanel(JComponent comp)
    {
        JPanel  panel = new JPanel(new BorderLayout());
        JButton btn   = new JButton("...");
        panel.add(btn, BorderLayout.WEST);
        panel.add(comp, BorderLayout.EAST);
        return panel;
    }

    /**
     * Returns the data object for this field in the "main" data object
     * @param dataObj the main data object
     * @param fieldName the field name to be gotten
     * @return return the field data frm the POJO
     */
    public Object getFieldValue(Object dataObj, String fieldName)
    {
        Object value = null;
        if (dataObj != null)
        {
            try
            {
                Method getter = PropertyUtils.getReadMethod(PropertyUtils.getPropertyDescriptor(dataObj, fieldName));
                value = getter.invoke(dataObj, (Object[])null);

            } catch (Exception ex)
            {
                // XXX FIXME
                ex.printStackTrace();
            }
        }
        return value == null ? "" : value;
    }

    /**
     * Returns a ViewDef Obj with the form UI built
     * @param view the view definition
     * @param altView which AltView to build
     * @param parentView the MultiViw that this view/form will be parented to
     * @param createRecordSetController indicates that a RecordSet Contoller should be created
     * @param createViewSwitcher can be used to make sure that the multiview switc her is not created
     * @return a Viewable Obj with the form UI built
     */
    public Viewable buildViewable(final View      view, 
                                  final AltView   altView, 
                                  final MultiView parentView, 
                                  final boolean   createRecordSetController,
                                  final boolean   createViewSwitcher)
    {
        if (scrDateFormat == null)
        {
            scrDateFormat = PrefsCache.getSimpleDateFormat("ui", "formatting", "scrdateformat");
        }
        if (viewFieldColor == null)
        {
            viewFieldColor = PrefsCache.getColorWrapper("ui", "formatting", "viewfieldcolor");
        }

        ViewDef viewDef = altView.getViewDef();

        if (viewDef == null) return null;

        this.rootMultiView =  parentView;

        if (viewDef.getType() == ViewDef.ViewType.form)
        {
            Viewable viewable = buildFormViewable(view, altView, parentView, createRecordSetController, createViewSwitcher);
            this.rootMultiView =  null;
            return viewable;

        } else if (viewDef.getType() == FormViewDef.ViewType.table)
        {
            this.rootMultiView =  null;
            return null;

        } else if (viewDef.getType() == FormViewDef.ViewType.field)
        {
            this.rootMultiView =  null;
            return null;

        } else
        {
            this.rootMultiView =  null;
            throw new RuntimeException("Form Type not covered by builder ["+viewDef.getType()+"]");
        }

    }

    /**
     * Creates a ValTextField
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return a ValTextField
     */
    protected JTextField createTextField(final FormValidator validator,
                                         final FormCellField cellField)
    {
        String validationRule = cellField.getValidationRule();

        JTextField txtField;
        if (validator != null && (cellField.isRequired() || isNotEmpty(validationRule) || cellField.isChangeListenerOnly()))
        {

            String            pickListName      = cellField.getPickListName();
            PickListDBAdapter pickListDBAdapter = null;
            if (isNotEmpty(pickListName))
            {
                pickListDBAdapter = new PickListDBAdapter(pickListName, false);
            }

            ValTextField textField = new ValTextField(cellField.getCols(), pickListDBAdapter);
            textField.setRequired(cellField.isRequired());

            validator.hookupTextField((JTextField)textField,
                                        cellField.getId(),
                                        cellField.isRequired(),
                                        parseValidationType(cellField.getValidationType()),
                                        cellField.getValidationRule(),
                                        cellField.isChangeListenerOnly());

            txtField = textField;
            textField.setEditable(!cellField.isReadOnly());

        } else
        {
            txtField = new JTextField(cellField.getCols());
        }

        return txtField;
    }

    /**
     * Creates a ValPasswordField
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return a ValPasswordField
     */
    public JTextField createPasswordField(final FormValidator validator,
                                          final FormCellField cellField)
    {
        String validationRule = cellField.getValidationRule();
        JTextField txt;

        if (validator != null && (cellField.isRequired() || isNotEmpty(validationRule) || cellField.isChangeListenerOnly()))
        {
            ValPasswordField textField = new ValPasswordField(cellField.getCols());
            textField.setRequired(cellField.isRequired());
            textField.setEncrypted(cellField.isEncrypted());

            validator.hookupTextField((JTextField)textField,
                                        cellField.getId(),
                                        cellField.isRequired(),
                                        parseValidationType(cellField.getValidationType()),
                                        validationRule,
                                        cellField.isChangeListenerOnly());

           txt = textField;

        } else
        {
            txt = new ValPasswordField(cellField.getCols());
        }
        return txt;
    }


    /**
     * Creates a ValFormattedTextField
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return ValFormattedTextField
     */
    protected JTextField createFormattedTextField(final FormValidator validator,
                                                  final FormCellField cellField)
    {
        log.debug(cellField.getName()+"  "+cellField.getUIFieldFormatter());
        ValFormattedTextField textField;// = new ValFormattedTextField(cellField.getUIFieldFormatter());

        // Because it is formatted we ALWAYS validate it when there is a validator
        if (validator != null)
        {
            // deliberately ignore "cellField.isChangeListenerOnly()"
            // pass in false instead

            textField = new ValFormattedTextField(cellField.getUIFieldFormatter());
            textField.setRequired(cellField.isRequired());

            validator.hookupTextField((JTextField)textField,
                                      cellField.getId(),
                                      cellField.isRequired(),
                                      UIValidator.Type.Changed,  cellField.getValidationRule(), false);
                                      //UIValidator.Type.Changed,  cellField.getName()+".isInError() == false", false);


        } else
        {
            textField = new ValFormattedTextField(cellField.getUIFieldFormatter());
        }

        textField.setEditable(!cellField.isReadOnly());

        return textField;
    }

    /**
     * Creates a ValTextArea
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return ValTextArea
     */
    protected JTextArea createTextArea(final FormValidator validator,
                                       final FormCellField cellField)
    {
        ValTextArea textArea = new ValTextArea("", cellField.getRows(), cellField.getCols());
        if (validator != null)
        {
            DataChangeNotifier dcn = validator.hookupComponent(textArea, cellField.getId(), false, UIValidator.Type.Focus, null, true);
            textArea.addFocusListener(dcn);
        }
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setEditable(!cellField.isReadOnly());

        return textArea;
    }


    /**
     * Creates a ValListBox
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return ValListBox
     */
    protected JList createList(final FormValidator validator,
                               final FormCellField cellField)
    {
        int numRows = 15;
        String[] initArray = null;

        String initStr = cellField.getInitialize();
        if (isNotEmpty(initStr))
        {
            String[] initSections = split(initStr, ";");
            if (initSections[0].indexOf("rows=") > -1)
            {
                String[] nameValPair = split(initStr, "=");
                if (nameValPair.length == 2 && nameValPair[0].equals("rows"))
                {
                    numRows = Integer.parseInt(nameValPair[1]);
                }
                if (initSections.length == 2)
                {
                    initArray = split(initStr, ",");
                    for (int i=0;i<initArray.length;i++)
                    {
                        initArray[i] = initArray[i].trim();
                    }
                }
            } else
            {
                initArray = split(initSections[0], ",");
            }
        }

        ValListBox valList = initArray == null ? new ValListBox() : new ValListBox(initArray);
        if (validator != null && (cellField.isRequired() || isNotEmpty(cellField.getValidationRule())))
        {
            DataChangeNotifier dcn = validator.hookupComponent(valList, cellField.getId(), cellField.isRequired(), parseValidationType(cellField.getValidationType()), cellField.getValidationRule(), false);
            valList.getModel().addListDataListener(dcn);
            valList.addFocusListener(dcn);
        }
        valList.setRequired(cellField.isRequired());
        valList.setVisibleRowCount(numRows);
        return valList;
    }

    /**
     * Creates a ValComboBoxFromQuery
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return ValComboBoxFromQuery
     */
    protected ValComboBoxFromQuery createQueryComboBox(final FormValidator validator,
                                                       final FormCellField cellField)
    {
        //String cbxName = cellField.getInitialize();
        String cbxName = cellField.getProperty("name");
        if (isNotEmpty(cbxName))
        {
            ValComboBoxFromQuery cbx = ComboBoxFromQueryFactory.createValComboBoxFromQuery(cbxName);
            cbx.setRequired(cellField.isRequired());
            if (validator != null && (cellField.isRequired() || isNotEmpty(cellField.getValidationRule())))
            {
                DataChangeNotifier dcn = validator.hookupComponent(cbx, cellField.getId(), cellField.isRequired(), parseValidationType(cellField.getValidationType()), cellField.getValidationRule(), false);
                cbx.getComboBox().getModel().addListDataListener(dcn);
                
                if (dcn.getValidationType() == UIValidator.Type.Focus) // returns None when no Validator
                {
                    cbx.addFocusListener(dcn);
                }
            }
            return cbx;

        } else
        {
            throw new RuntimeException("CBX Name for ValComboBoxFromQuery ["+cbxName+"] is empty!");
        }
    }

    /**
     * Creates a ValComboBox
     * @param validator a validator to hook the control up to (may be null)
     * @param cellField the definition of the cell for this control
     * @return ValComboBox
     */
    protected ValComboBox createComboBox(final FormValidator validator,
                                         final FormCellField cellField)
    {

        String[] initArray = split(cellField.getInitialize(), ",");
        for (int i=0;i<initArray.length;i++)
        {
            initArray[i] = initArray[i].trim();
        }

        String      pickListName = cellField.getPickListName();
        ValComboBox cbx          = null;
        if (isNotEmpty(pickListName))
        {
            cbx = new ValComboBox(new PickListDBAdapter(pickListName, false)); // false means don't auto-create picklist
            
        } else
        {
            cbx = initArray == null ? new ValComboBox() : new ValComboBox(initArray);
        }
        cbx.setRequired(cellField.isRequired());

        if (validator != null && (cellField.isRequired() || isNotEmpty(cellField.getValidationRule())))
        {
            DataChangeNotifier dcn = validator.hookupComponent(cbx, cellField.getId(), cellField.isRequired(), parseValidationType(cellField.getValidationType()), cellField.getValidationRule(), false);
            cbx.getModel().addListDataListener(dcn);
            
            if (dcn.getValidationType() == UIValidator.Type.Focus) // returns None when no Validator
            {
                cbx.addFocusListener(dcn);
            }
        }

        return cbx;
    }
    
    /**
     * Makes adjusts to the border and the colors to make it "flat" for diaply mode
     * @param textField the text field to be flattened
     */
    protected void changeTextFieldUIForDisplay(final JTextField textField)
    {
        Insets insets = textField.getBorder().getBorderInsets(textField);
        textField.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.bottom));
        textField.setForeground(Color.BLACK);
        textField.setEditable(false);
        if (viewFieldColor != null)
        {
            textField.setBackground(viewFieldColor.getColor());
        }
    }

    /**
     * @param parent MultiView parent
     * @param formViewDef the FormViewDef (Viewdef)
     * @param validator optional validator
     * @param formViewObj the FormViewObj this row belongs to
     * @param mode the creation mode
     * @param builder the current JGoodies builder
     * @param labelsForHash the has table for label
     * @param cc CellConstraints
     * @param currDataObj the current data object
     * @param formRows the list of rows to be processed
     */
    protected void processRows(final MultiView       parent,
                               final FormViewDef     formViewDef,
                               final FormValidator   validator,
                               final FormViewObj     formViewObj,
                               final AltView.CreationMode mode,
                               final PanelBuilder    builder,
                               final Hashtable<String, JLabel> labelsForHash,
                               final CellConstraints cc,
                               final Object          currDataObj,
                               final List<FormRow>   formRows)
    {
        int rowInx    = 1;
        int curMaxRow = 1;

        for (FormRow row : formRows)
        {
            int colInx = 1;

            if (rowInx < curMaxRow)
            {
                //rowInx = curMaxRow;
            }
            for (FormCell cell : row.getCells())
            {
                JComponent compToAdd = null;
                JComponent compToReg = null;

                int        colspan   = cell.getColspan();
                int        rowspan   = cell.getRowspan();

                boolean    addToValidator = true;
                boolean    addControl     = true;

                if (cell.getType() == FormCell.CellType.label)
                {
                    FormCellLabel cellLabel = (FormCellLabel)cell;

                    String lblStr = cellLabel.getLabel();
                     JLabel        lbl       = new JLabel(isNotEmpty(lblStr) ? lblStr + ":" : "  ", JLabel.RIGHT);
                    //lbl.setFont(boldLabelFont);
                    labelsForHash.put(cellLabel.getLabelFor(), lbl);

                    compToAdd      =  lbl;
                    addToValidator = false;
                    addControl     = false;
                    formViewObj.addLabel(cellLabel, lbl);


                } else if (cell.getType() == FormCell.CellType.field)
                {
                    FormCellField cellField = (FormCellField)cell;

                    String uiType = cellField.getUiType();

                    if (isEmpty(uiType))
                    {
                        uiType = "text";
                    }


                    if (mode == AltView.CreationMode.View)
                    {
                        uiType = cellField.getDspUIType();
                    }

                    if (uiType.equals("text"))
                    {
                        compToAdd = createTextField(validator, cellField);
                        addToValidator = validator == null; // might already added to validator

                    } else if (uiType.equals("formattedtext"))
                    {
                        compToAdd = createFormattedTextField(validator, cellField);
                        addToValidator = validator == null; // might already added to validator


                    } else if (uiType.equals("label"))
                    {
                        compToAdd = new JLabel("", JLabel.LEFT);

                    } else if (uiType.equals("dsptextfield"))
                    {
                        JTextField text = new JTextField(cellField.getCols());
                        changeTextFieldUIForDisplay(text);
                        compToAdd = text;


                    } else if (uiType.equals("textfieldinfo"))
                    {
                        TextFieldWithInfo textFieldInfo;
                        String            txtName = cellField.getProperty("name");
                        if (isNotEmpty(txtName))
                        {
                            textFieldInfo = ComboBoxFromQueryFactory.getTextFieldWithInfo(txtName);
                            textFieldInfo.setMultiView(parent);
                            textFieldInfo.setFrameTitle(cellField.getProperty("title"));
                            
                        } else
                        {
                            throw new RuntimeException("textfieldinfo Name for textFieldWithInfo ["+txtName+"] is empty!");
                        }

                        JTextField text = textFieldInfo.getTextField();
                        text.setColumns(cellField.getCols());

                        
                        changeTextFieldUIForDisplay(text);
                        
                        compToAdd = textFieldInfo;


                    } else if (uiType.equals("image"))
                    {
                        
                        int w = 150;
                        int h = 150;
                        String sizeDefStr = cellField.getProperty("size");
                        if (isNotEmpty(sizeDefStr))
                        {
                            String[] wh = StringUtils.split(sizeDefStr, ",");
                            if (wh.length == 2)
                            {
                                try
                                {
                                    w = Integer.parseInt(wh[0]);
                                    h = Integer.parseInt(wh[1]);

                                } catch (Exception ex)
                                {
                                    log.error("size prop for Image is incorrect ["+sizeDefStr+"]");
                                }
                            }

                        }
                        
                        boolean imageInEdit = mode == AltView.CreationMode.Edit;
                        String editModeStr = cellField.getProperty("edit");
                        if (isNotEmpty(editModeStr))
                        {
                            imageInEdit = editModeStr.toLowerCase().equals("true");
                        }

                        ImageDisplay imgDisp = new ImageDisplay(w, h, imageInEdit);
                        compToAdd = imgDisp;

                        addToValidator = false;

                    } else if (uiType.equals("url"))
                    {
                        compToAdd = new BrowserLauncherBtn(cellField.getProperty("title"));
                        addToValidator = false;

                    } else if (uiType.equals("combobox"))
                    {
                        compToAdd = createComboBox(validator, cellField);
                        addToValidator = validator == null; // might already added to validator


                    } else if (uiType.equals("checkbox"))
                    {
                        JCheckBox checkbox = new JCheckBox(cellField.getLabel());
                        if (validator != null)
                        {
                            DataChangeNotifier dcn = validator.createDataChangeNotifer(cellField.getId(), checkbox, null);
                            checkbox.addActionListener(dcn);
                        }

                        compToAdd = checkbox;

                    } else if (uiType.equals("password"))
                    {

                        compToAdd      = createPasswordField(validator, cellField);
                        addToValidator = validator == null; // might already added to validator



                    } else if (uiType.equals("dsptextarea"))
                    {
                        JTextArea ta = new JTextArea(cellField.getRows(), cellField.getCols());
                        //ta.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
                        Insets insets = ta.getBorder().getBorderInsets(ta);
                        ta.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.bottom));
                        ta.setForeground(Color.BLACK);
                        ta.setEditable(false);
                        ta.setBackground(viewFieldColor.getColor());

                        JScrollPane scrollPane = new JScrollPane(ta);
                        insets = scrollPane.getBorder().getBorderInsets(scrollPane);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPane.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.bottom));

                        compToAdd = scrollPane;

                    } else if (uiType.equals("textarea"))
                    {
                        JTextArea ta = createTextArea(validator, cellField);
                        JScrollPane scrollPane = new JScrollPane(ta);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                        addToValidator = validator == null; // might already added to validator
                        compToReg = ta;
                        compToAdd = scrollPane;

                    } else if (uiType.equals("browse"))
                    {
                        BrowseBtnPanel bbp = new BrowseBtnPanel(createTextField(validator, cellField));
                        compToAdd = bbp;

                    } else if (uiType.equals("querycbx"))
                    {
                        ValComboBoxFromQuery cbx = createQueryComboBox(validator, cellField);
                        cbx.setMultiView(parent);
                        cbx.setFrameTitle(cellField.getProperty("title"));
                        
                        compToAdd = cbx;
                        addToValidator = validator == null; // might already added to validator

                    } else if (uiType.equals("list"))
                    {
                        JList list = createList(validator, cellField);

                        JScrollPane scrollPane = new JScrollPane(list);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                        addToValidator = validator == null;
                        compToReg = list;
                        compToAdd = scrollPane;

                    } else if (uiType.equals("colorchooser"))
                    {
                        ColorChooser colorChooser = new ColorChooser(Color.BLACK);
                        if (validator != null)
                        {
                            DataChangeNotifier dcn = validator.createDataChangeNotifer(cellField.getName(), colorChooser, null);
                            colorChooser.addPropertyChangeListener("setValue", dcn);
                        }
                        compToAdd = colorChooser;

                    } else if (uiType.equals("button"))
                    {
                        JButton btn = new JButton(cellField.getProperty("title"));
                        compToAdd = btn;
                        
                    } else if (uiType.equals("progress"))
                    {
                        JProgressBar progressBar = new JProgressBar(0, 100);
                        compToAdd = progressBar;
                        
                    } else if (uiType.equals("plugin"))
                    {
                        String classNameStr = cellField.getProperty("class");
                        if (StringUtils.isEmpty(classNameStr))
                        {
                            throw new RuntimeException("Creating plugin and the class property was missing.");
                        }
                        
                        try
                        {
                            Class  classObj = Class.forName(classNameStr);
                            Object uiObj    = classObj.newInstance();
                            
                            if (!(uiObj instanceof GetSetValueIFace))
                            {
                                throw new RuntimeException("Plugin of class["+classNameStr+"] doesn't implement the GetSetValueIFace!");
                            }
                            
                            if (uiObj instanceof UIPluginable)
                            {
                                ((UIPluginable)uiObj).initialize(cellField.getProperties());
                            }
                            
                            if (uiObj instanceof JComponent)
                            {
                                compToAdd = (JComponent)uiObj;
                                
                            } else
                            {
                                throw new RuntimeException("A UIPlugin MUST be derived from a JComponent!");
                            }
                            
                        } catch (Exception ex)
                        {
                           log.error(ex);
                           throw new RuntimeException(ex);
                        }
                        
                    } else
                    {
                        throw new RuntimeException("Don't recognize uitype=["+uiType+"]");
                    }

                } else if (cell.getType() == FormCell.CellType.command)
                {
                    FormCellCommand cellCmd = (FormCellCommand)cell;
                    JButton btn  = new JButton(cellCmd.getLabel());
                    if (cellCmd.getCommandType().length() > 0)
                    {
                        btn.addActionListener(new CommandActionWrapper(new CommandAction(cellCmd.getCommandType(), cellCmd.getAction(), "")));
                    }
                    addToValidator = false;
                    compToAdd = btn;

                } else if (cell.getType() == FormCell.CellType.separator)
                {
                    compToAdd = null;
                    Component sep = builder.addSeparator(((FormCellSeparator)cell).getLabel(), cc.xyw(colInx, rowInx, cell.getColspan()));
                    if (cell.getName().length() > 0)
                    {
                        formViewObj.addControl(cell, sep);
                    }
                    curMaxRow = rowInx;
                    colInx += 2;

                } else if (cell.getType() == FormCell.CellType.subview)
                {
                    FormCellSubView cellSubView = (FormCellSubView)cell;

                    String subViewName = cellSubView.getViewName();

                    View subView = ViewMgr.getView(cellSubView.getViewSetName(), subViewName);
                    if (subView != null)
                    {
                        if (parent != null)
                        {
                            MultiView multiView = new MultiView(parent, subView, parent.getCreateWithMode(), !cellSubView.isSingleValueFromSet(), true);
                            parent.addChild(multiView);
                            
                            
                            builder.add(multiView, cc.xywh(colInx, rowInx, cellSubView.getColspan(), 1, "fill,fill"));
                            //String classDesc = cellSubView.getClassDesc();
                            //if (cell.isIgnoreSetGet() || (classDesc != null && classDesc.length() > 0))
                            //{
                                formViewObj.addSubView(cell, multiView);
                                
                            //}
                            curMaxRow = rowInx;
                        } else
                        {
                            log.error("buildFormView - parent is NULL for subview ["+subViewName+"]");
                        }

                    } else
                    {
                        log.error("buildFormView - Could find subview's with ViewSet["+cellSubView.getViewSetName()+"] ViewName["+subViewName+"]");
                    }
                    compToAdd = null;
                    colInx += 2;

                } else if (cell.getType() == FormCell.CellType.panel)
                {
                    FormCellPanel cellPanel = (FormCellPanel)cell;
                    String panelType = cellPanel.getPanelType();

                    if (isEmpty(panelType))
                    {
                        DefaultFormBuilder panelBuilder = new DefaultFormBuilder(new FormLayout(cellPanel.getColDef(), cellPanel.getRowDef()));

                        //Color[] colors = new Color[] {Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA};
                        //panelBuilder.getPanel().setBackground(colors[cnt % colors.length]);
                        //cnt++;

                        processRows(parent, formViewDef, validator, formViewObj, mode, panelBuilder, labelsForHash, new CellConstraints(), currDataObj, cellPanel.getRows());

                        compToAdd = panelBuilder.getPanel();

                    } else if (panelType.equalsIgnoreCase("buttonbar"))
                    {

                        JButton[] btns = processRows(formViewDef, validator, formViewObj, cellPanel.getRows());
                        compToAdd      = com.jgoodies.forms.factories.ButtonBarFactory.buildCenteredBar(btns);
                   }

                    addControl     = false;
                    addToValidator = false;

                }

                if (compToAdd != null)
                {
                    //System.out.println(colInx+"  "+rowInx+"  "+colspan+"  "+rowspan+"  "+compToAdd.getClass().toString());
                    builder.add(compToAdd, cc.xywh(colInx, rowInx, colspan, rowspan));

                    curMaxRow = Math.max(curMaxRow, rowspan+rowInx);

                    if (addControl)
                    {
                        formViewObj.addControl(cell, compToReg == null ? compToAdd : compToReg);
                    }

                    if (validator != null && addToValidator)
                    {

                        validator.addUIComp(cell.getName(), compToReg == null ? compToAdd : compToReg);
                    }
                    colInx += colspan + 1;
                 }

            }
            rowInx += 2;
        }


    }

    /**
     * @param formViewDef formViewDef
     * @param validator validator
     * @param formViewObj formViewObj
     * @param formRows formRows
     * @return JButton[]
     */
    protected JButton[] processRows(final FormViewDef    formViewDef,
                                    final FormValidator  validator,
                                    final FormViewObj    formViewObj,
                                    final List<FormRow>  formRows)
    {
        List<JButton> btns = new ArrayList<JButton>();

        for (FormRow row : formRows)
        {
            for (FormCell cell : row.getCells())
            {
                if (cell.getType() == FormCell.CellType.command)
                {
                    FormCellCommand cellCmd = (FormCellCommand)cell;
                    JButton btn  = new JButton(cellCmd.getLabel());
                    if (cellCmd.getCommandType().length() > 0)
                    {
                        btn.addActionListener(new CommandActionWrapper(new CommandAction(cellCmd.getCommandType(), cellCmd.getAction(), "")));
                    }
                    formViewObj.addControl(cell, btn);
                    btns.add(btn);
                }
            }
        }

        JButton[] btnsArray = new JButton[btns.size()];
        int i = 0;
        for (JButton b : btns)
        {
            btnsArray[i++] = b;
        }
        btns.clear();
        return btnsArray;

    }



    /**
     * Creates a Form
     * @param view view the view definition
     * @param altView the altView to use (if null, then it uses the default ViewDef)
     * @param parentView the MultiView parent (this may be null)
     * @param createRecordSetController indicates that a RecordSet Contoller should be created
     * @param createViewSwitcher can be used to make sure that the multiview switc her is not created
     * @return the form
     */
    public FormViewObj buildFormViewable(final View        view,
                                         final AltView     altView,
                                         final MultiView   parentView,
                                         final boolean     createRecordSetController,
                                         final boolean     createViewSwitcher)
    {
        try
        {
            FormViewDef formViewDef = (FormViewDef)altView.getViewDef();

            Hashtable<String, JLabel> labelsForHash = new Hashtable<String, JLabel>();
            
            ValidatedJPanel validatedPanel = null;
            FormValidator   validator      = null;
            if (altView.isValidated())
            {
                validatedPanel = new ValidatedJPanel();
                validator      = validatedPanel.getFormValidator();
                validator.setDataChangeNotification(true);
            }

            FormViewObj formViewObj = new FormViewObj(view, altView, parentView, validator, createRecordSetController, createViewSwitcher);

            Object currDataObj = formViewObj.getCurrentDataObj();

            // Figure columns
            FormLayout      formLayout = new FormLayout(formViewDef.getColumnDef(), formViewDef.getRowDef());
            PanelBuilder    builder    = new PanelBuilder(formLayout);
            CellConstraints cc         = new CellConstraints();

            processRows(parentView, formViewDef, validator, formViewObj, altView.getMode(), builder, labelsForHash, cc, currDataObj, formViewDef.getRows());


            if (parentView == null)
            {
                builder.getPanel().setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
            }

            if (altView.isValidated())
            {
                validatedPanel.addPanel(builder.getPanel());

                // Here we add all the components whether they are used or not
                // XXX possible optimization is to only load the ones being used (although I am not sure how we will know that)
                Map<String, Component> mapping = formViewObj.getControlMapping();
                for (String id : mapping.keySet())
                {
                    validatedPanel.addValidationComp(id, mapping.get(id));
                }
                Map<String, String> enableRules = formViewDef.getEnableRules();

                // Load up validation Rules
                FormValidator fv = validatedPanel.getFormValidator();
                formViewObj.setValidator(fv);
                
                fv.setName(formViewDef.getName()); // For Debugging

                for (String name : enableRules.keySet())
                {
                    fv.addEnableRule(name, enableRules.get(name));
                }

                // Load up labels and associate them with there component
                for (String idFor : labelsForHash.keySet())
                {
                    fv.addUILabel(idFor, labelsForHash.get(idFor));
                }

                formViewObj.setFormComp(validatedPanel);
                
            } else
            {
                formViewObj.setFormComp(builder.getPanel());
            }

            return formViewObj;

        } catch (Exception e)
        {
            System.err.println("buildPanel - Outer Name["+altView.getName()+"]");
            e.printStackTrace();
        }
        return null;
    }
    
    protected boolean isRecordSetNeeded(final MultiView multiView)
    {
        if (multiView != null)
        {
            //multiView.getViewDef();
        }
        return false;
    }
    

    /**
     * Creates a FormViewObj
     * @param multiView the parent multiView
     * @param view the definition of the form view to be created
     * @param altName the name of the altView to be used (can be null - then it defaults to the default AltView)
     * @param data the data to be set into the form
     * @return a new FormViewObj
     */
    public static FormViewObj createFormView(final MultiView multiView, 
                                             final View view, 
                                             final String altName, 
                                             final Object data)
    {
        return createFormView(multiView, view, altName, data, false, true);
    }
    

    /**
     * Creates a FormViewObj
     * @param multiView the parent multiView
     * @param view the definition of the form view to be created
     * @param altName the name of the altView to be used (can be null - then it defaults to the default AltView)
     * @param data the data to be set into the form
     * @param createViewSwitcher can be used to make sure that the multiview switcher is not created
     * @return a new FormViewObj
     */
    public static FormViewObj createFormView(final MultiView multiView, 
                                             final View view, 
                                             final String altName, 
                                             final Object data,
                                             final boolean createRecordSetContoller,
                                             final boolean createViewSwitcher)
    {
        if (scrDateFormat == null)
        {
            scrDateFormat = PrefsCache.getSimpleDateFormat("ui", "formatting", "scrdateformat");
        }
        if (viewFieldColor == null)
        {
            viewFieldColor = PrefsCache.getColorWrapper("ui", "formatting", "viewfieldcolor");
        }

        AltView altView = view.getAltView(altName);

        if (altView != null && altView.getViewDef().getType() == ViewDef.ViewType.form)
        {
            if (altView.getViewDef().getType() == ViewDef.ViewType.form)
            {
                FormViewObj form = (FormViewObj)instance.buildViewable(view, altView, multiView, createRecordSetContoller, createViewSwitcher);
                if (form != null)
                {
                    if (data != null)
                    {
                        form.setDataObj(data);
                    }
                } else
                {
                    throw new RuntimeException("Form could be created! ["+view.getName()+"]["+altView.getName()+"]");
                }
                return form;
            }
        }

        return null;
    }

    //-----------------------------------------------------------
    // Inner Class
    //-----------------------------------------------------------
    
    class BrowserLauncherBtn extends JButton implements GetSetValueIFace
    {
        protected String url     = null;
        protected Object dataObj = null;
        protected BrowserLauncherAction action = null;
        
        public BrowserLauncherBtn(final String text)
        {
            super(text);
            setEnabled(false);
        }
        
        /* (non-Javadoc)
         * @see edu.ku.brc.specify.ui.GetSetValueIFace#setValue(java.lang.Object, java.lang.String)
         */
        public void setValue(Object value, String defaultValue)
        {
            if (value != null)
            {
                url = value.toString();
                dataObj = value;
                
                if (action != null)
                {
                    this.removeActionListener(action);
                }
                
                if (StringUtils.isNotEmpty(url) && url.startsWith("http"))
                {
                    action = new BrowserLauncherAction(url);
                    addActionListener(action);
                    setEnabled(true);
                } else
                {
                    setEnabled(false);
                }
            } else
            {
                setEnabled(false);
            }
        }
        
        /* (non-Javadoc)
         * @see edu.ku.brc.specify.ui.GetSetValueIFace#getValue()
         */
        public Object getValue()
        {
            return dataObj;
        }
    }
    
    class BrowserLauncherAction implements ActionListener
    {
        protected String url;
        
        public BrowserLauncherAction(final String url)
        {
            this.url = url;
        }
        
        public void actionPerformed(ActionEvent ae)
        {
            BrowserLauncher.openURL(url);
        }
    };
}

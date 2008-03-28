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

package edu.ku.brc.ui.forms.validation;

import static edu.ku.brc.ui.UIHelper.setControlSize;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.ui.ColorWrapper;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.forms.ViewFactory;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterField;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterMgr;

/**
 * A Single JTextField that provides for "formatted" input. The format "mask" is define in XML
 * via the UIFieldFormatterMgr class. This is idea for text fields that have a standard size and a specific format (i.e. Dates)
 * The mask enables the "fields" and separators to be specifically defined.
 *
 * Note: This has a single text Field and is usually only used for Dates.
 *
 * @code_status Beta
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ValFormattedTextFieldSingle extends JTextField implements UIValidatable,
                                                                       GetSetValueIFace,
                                                                       DocumentListener, 
                                                                       UIRegistry.UndoableTextIFace,
                                                                       AutoNumberableIFace
{
    private static final Logger log  = Logger.getLogger(ValFormattedTextFieldSingle.class);

    protected static ColorWrapper     valtextcolor       = null;
    protected static ColorWrapper     requiredfieldcolor = null;  

    protected UIValidatable.ErrorType     valState       = UIValidatable.ErrorType.Valid;
    protected boolean                     isRequired     = false;
    protected boolean                     isChanged      = false;
    protected boolean                     isNew          = false;
    protected boolean                     isViewOnly     = false;
    protected Color                       bgColor        = null;
    protected boolean                     needsUpdating  = false;

    protected int                         requiredLength = 0;
    protected boolean                     doSetText      = false; 

    protected JFormattedDoc               document;
    protected String                      defaultValue   = null;

    protected UIFieldFormatterIFace       formatter;
    protected List<UIFieldFormatterField> fields         = null;
    
    protected Object                      origValue      = null;
    protected UndoManager                 undoManager    = null;

    //---
    protected String bgStr     = null;
    protected Point  pnt       = null;
    protected Color  textColor = new Color(0,0,0,64);
    protected Insets inner;
    
    protected JComponent[] comps;

    /**
     * Constructor
     * @param dataObjFormatterName the formatters name
     */
    protected ValFormattedTextFieldSingle()
    {
        // do nothing
    }

    /**
     * Constructor
     * @param dataObjFormatterName the formatters name
     */
    public ValFormattedTextFieldSingle(final UIFieldFormatterIFace formatter, final boolean isViewOnly)
    {
      super();
      
      init(formatter, isViewOnly);

    }

    /**
     * Constructor
     * @param formatterName the formatters name
     */
    public ValFormattedTextFieldSingle(final String formatterName, final boolean isViewOnly)
    {
        super();

        init(UIFieldFormatterMgr.getFormatter(formatterName), isViewOnly);

    }
    
    /**
     * @param formatter
     * @param isViewOnly
     */
    protected void init(final UIFieldFormatterIFace formatter, final boolean isViewOnly)
    {
        setControlSize(this);

        this.isViewOnly = isViewOnly;
        
        initColors();
        
        inner = getInsets();
        
        setFormatterInternal(formatter);
        
        addMouseListener(new MouseAdapter() {

            /* (non-Javadoc)
             * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
             */
            @Override
            public void mousePressed(MouseEvent e)
            {
                //System.err.println(e);
                super.mousePressed(e);
            }
            
        });
        
        addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                ((JTextField)e.getSource()).selectAll();
                //System.err.println(e);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                isNew = false;
                validateState();
                repaint();

            }
        });

        if (!isViewOnly) // NOTE: This is checking the method argument NOT the class member!
        {
            if (!formatter.isUserInputNeeded())
            {
                ViewFactory.changeTextFieldUIForDisplay(this, false);
                //bgStr = "";
                
            } else
            {
                super.setEnabled(true);
            }
        }
    }
    
    /**
     * Sets the formatter.
     * @param dataObjFormatterName the formatter to use
     */
    protected void setFormatterInternal(final UIFieldFormatterIFace formatterArg)
    {
        if (formatterArg != null && formatter != formatterArg)
        {
            int oldReqLen = requiredLength;
            
            formatter = formatterArg;
            
            fields = formatter.getFields();
    
            requiredLength = formatter.getLength();
            bgStr = formatter.toPattern();
    
            if (requiredLength > oldReqLen) // don't let the field shrink
            {
                this.setColumns(Math.min(10, requiredLength));
            }
    
            document = new JFormattedDoc(this, formatter, requiredLength);
            setDocument(document);
            document.addDocumentListener(this);
        }
    }
    
    /**
     * Sets the formatter and reset the current value into the new format.
     * @param dataObjFormatterName the formatter to use
     */
    public void setFormatter(final UIFieldFormatterIFace formatter)
    {
        Object currentValue = isChanged ? getValue() : origValue;
        
        setFormatterInternal(formatter);
        
        setValue(currentValue, "");
    }


    /**
     * Initializes the control.
     */
    public void initColors()
    {
        bgColor = getBackground();
        if (valtextcolor == null || requiredfieldcolor == null)
        {
            valtextcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "valtextcolor");
            requiredfieldcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "requiredfieldcolor");
        }
    }


    /**
     * Helper method for validation scripting to see if the text field is empty
     * @return whether the text field is empty or not
     */
    public boolean isNotEmpty()
    {
        return getText().length() > 0;
    }

    /* (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        String text = getText();

        if (!isViewOnly && needsUpdating && isEnabled() && text != null && text.length() < bgStr.length() )
        {
            FontMetrics fm   = g.getFontMetrics();
            int          w   = fm.stringWidth(text);
            pnt = new Point(inner.left+w, inner.top + fm.getAscent());

            g.setColor(textColor);
            g.drawString(bgStr.substring(text.length(), bgStr.length()), pnt.x, pnt.y);
        }


        if (!isNew && valState == UIValidatable.ErrorType.Error && isEnabled())
        {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Dimension dim = getSize();
            g.setColor(valtextcolor.getColor());
            g.drawRect(1, 1, dim.width-2, dim.height-2);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled)
    {
        //if (!isViewOnly)
        //{
            boolean isNeeded = formatter.isUserInputNeeded();
            if (enabled && isNeeded)
            {
                super.setEnabled(isNeeded);
                
            } else
            {
                super.setEnabled(enabled);
            }
    
            setBackground(isRequired && isEnabled() ? requiredfieldcolor.getColor() : bgColor);
        //}
    }

    /* (non-Javadoc)
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    protected void setText(final String text, final boolean notify)
    {
        document.setIgnoreNotify(!notify);
        if (StringUtils.isEmpty(text))
        {
            bgStr = formatter.toPattern();
        }
        doSetText = true;
        super.setText(text);
        doSetText = false;
        document.setIgnoreNotify(notify);
        repaint();
    }

    /* (non-Javadoc)
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    @Override
    public void setText(final String text)
    {
        setText(text, false);
    }
    
    /**
     * Returns true if the no validation errors, false if there are
     * @return true if the no validation errors, false if there are
     */
    public boolean isOK()
    {
        return valState == UIValidatable.ErrorType.Valid;
    }


    /**
     * @param isViewOnly the isViewOnly to set
     */
    public void setViewOnly(boolean isViewOnly)
    {
        this.isViewOnly = isViewOnly;
    }
    
    //--------------------------------------------------
    //-- AutoNumberableIFace Interface
    //--------------------------------------------------
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.AutoNumberableIFace#updateAutoNumbers()
     */
    public void updateAutoNumbers()
    {
        if (needsUpdating && formatter.getAutoNumber() != null && !isViewOnly)
        {
            String nextNum = formatter.getNextNumber(getText());
            if (StringUtils.isNotEmpty(nextNum))
            {
                try
                {
                    setValue(nextNum, nextNum);
                    bgStr = "";
                    needsUpdating = false;
                    
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
    

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.AutoNumberableIFace#isFormatterAutoNumber()
     */
    public boolean isFormatterAutoNumber()
    {
        return formatter != null && formatter.getAutoNumber() != null;
    }
    
    //--------------------------------------------------
    //-- UIValidatable Interface
    //--------------------------------------------------


    /* (non-Javadoc)
     * @see edu.kui.brc.ui.validation.UIValidatable#valState()
     */
    public boolean isInError()
    {
        return valState != UIValidatable.ErrorType.Valid;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#getState()
     */
    public ErrorType getState()
    {
        return valState;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setState(edu.ku.brc.ui.forms.validation.UIValidatable.ErrorType)
     */
    public void setState(ErrorType state)
    {
        this.valState = state;
    }

    /* (non-Javadoc)
     * @see edu.kui.brc.ui.validation.UIValidatable#isRequired()
     */
    public boolean isRequired()
    {
        return isRequired && !isViewOnly;
    }

    /* (non-Javadoc)
     * @see edu.kui.brc.ui.validation.UIValidatable#setRequired(boolean)
     */
    public void setRequired(boolean isRequired)
    {
        if (!isViewOnly)
        {
            setBackground(isRequired && isEnabled() ? requiredfieldcolor.getColor() : bgColor);
            this.isRequired = isRequired;
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#isChanged()
     */
    public boolean isChanged()
    {
        return isChanged;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setChanged(boolean)
     */
    public void setChanged(boolean isChanged)
    {
        this.isChanged = isChanged;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#setAsNew(boolean)
     */
    public void setAsNew(boolean isNew)
    {
        // Now that we now we know it is a new Object then set the default value
        if (isNew && origValue == null)
        {
            setText(StringUtils.isNotEmpty(defaultValue) ? defaultValue : "");
        }
        this.isNew = isRequired || !formatter.isUserInputNeeded() ? isNew : false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#reset()
     */
    public void reset()
    {
        origValue = null;
        setText( StringUtils.isNotEmpty(defaultValue) ? defaultValue : "");
        validateState();
        repaint();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#getValidatableUIComp()
     */
    public Component getValidatableUIComp()
    {
        return this;
    }

    /* (non-Javadoc)
     * @see java.awt.Component#validate()
     */
    public UIValidatable.ErrorType validateState()
    {
        if (isViewOnly)
        {
            valState = UIValidatable.ErrorType.Valid;
            
        } else if (formatter != null && formatter.isUserInputNeeded())
        {
            String data = getText();
            if (StringUtils.isEmpty(data))
            {
                valState = isRequired ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;
    
            } else if (formatter.isNumeric())
            {
                valState = validateNumeric(data);
                
            } else
            {
                valState = data.length() != requiredLength ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
            }
        } else
        {
            valState = UIValidatable.ErrorType.Valid;
        }
        //System.out.println("#### validateState "+ getText()+"  "+data.length() +"  "+ requiredLength+"  "+valState);
        return valState;
    }
    
    
    /**
     * @param value
     * @return
     */
    protected UIValidatable.ErrorType validateNumeric(final String value)
    {
        Class<?> cls = formatter.getDataClass();
        try
        {
            if (cls == Long.class)
            {
                Long val  = Long.parseLong(value);
                return val > Long.MAX_VALUE || val < -(Long.MAX_VALUE) ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
                
            } else if (cls == Integer.class)
            {
                Integer val  = Integer.parseInt(value);
                return val > Integer.MAX_VALUE || val < -(Integer.MAX_VALUE) ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
    
            } else if (cls == Short.class)
            {
                Short val  = Short.parseShort(value);
                return val > Short.MAX_VALUE || val < -(Short.MAX_VALUE) ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
    
            } else if (cls == Byte.class)
            {
                Byte val  = Byte.parseByte(value);
                return val > Byte.MAX_VALUE || val < -(Byte.MAX_VALUE) ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
    
            } else if (cls == Double.class)
            {
                Double val  = Double.parseDouble(value);
                return val > Double.MAX_VALUE || val < -(Double.MAX_VALUE) ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
    
            } else if (cls == Float.class)
            {
                Float val  = Float.parseFloat(value);
                return val.floatValue() > Float.MAX_VALUE || val < -(Float.MAX_VALUE) ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
    
            } else if (cls == BigDecimal.class)
            {
                // XXX This needs to be redone
                Double val  = Double.parseDouble(value);
                return val > Double.MAX_VALUE || val < Double.MIN_VALUE ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
    
            } else
            {
                throw new RuntimeException("Missing case for numeric class ["+cls.getName()+"]");        
            }
        } catch (Exception ex)
        {
            
        }
        return UIValidatable.ErrorType.Error;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#cleanUp()
     */
    public void cleanUp()
    {
        document  = null;
        formatter = null;
        fields    = null;
    }

    //--------------------------------------------------------
    // UIRegistry.UndoableTextIFace
    //--------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.UIRegistry.UndoableTextIFace#getUndoManager()
     */
    public UndoManager getUndoManager()
    {
        if (undoManager == null)
        {
            undoManager = new UndoManager();
            UIRegistry.getInstance().hookUpUndoableEditListener(this);
        }
        return undoManager;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.UIRegistry.UndoableTextIFace#getTextComponent()
     */
    public JTextComponent getTextComponent()
    {
        return this;
    }

    //--------------------------------------------------------
    // GetSetValueIFace
    //--------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#setValue(java.lang.Object, java.lang.String)
     */
    public void setValue(Object value, String defaultValue)
    {
        this.defaultValue = defaultValue;

        String data;

        if (value != null)
        {
            if (value instanceof String)
            {
                data = (String)value;

            } else if (value instanceof Date)
            {
                data = formatter.getDateWrapper().format((Date)value);
                
            } else if (value instanceof Calendar)
            {
                data = formatter.getDateWrapper().format(((Calendar)value).getTime());
                
            } else
            {
                data = value.toString();
            }
        } else
        {
            // rods - 3/15/08 This was commented out because of AutoNumbering
            // and it was just set to " (empty). But for Dates we need to use the default
            // so I tested checking for "isDate" and it worked now let's try when
            // it isn't an AutoNumber.
            data = StringUtils.isNotEmpty(defaultValue) && formatter.getAutoNumber() == null ? defaultValue : "";
            needsUpdating = true;
        }
        
        if (origValue == null)
        {
            origValue = value;
        }
        
        if (formatter.isInBoundFormatter())
        {
            setText((String)formatter.formatInBound(data));
            needsUpdating = StringUtils.isEmpty(data) && formatter.getAutoNumber() != null && formatter.isIncrementer();
            
        } else
        {
            setText(data);
        }
        
        if (undoManager != null)
        {
            undoManager.discardAllEdits();
        }

        validateState();

        repaint();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#getValue()
     */
    public Object getValue()
    {
        if (formatter.isDate())
        {
            String value = getText();
            if (StringUtils.isNotEmpty(value))
            {
                try
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(formatter.getDateWrapper().getSimpleDateFormat().parse(value));
                    return cal;

                } catch (ParseException ex)
                {
                    log.error("Date is in error for parsing["+value+"]");
                }
            }
            return null;

        }
        // else
        String val = getText();
        if (formatter.isOutBoundFormatter())
        {
            if (StringUtils.isNotEmpty(val))
            {
                return formatter.formatOutBound(getText());
            }
        }
        return val;

    }

    //--------------------------------------------------------
    // DocumentListener
    //--------------------------------------------------------


    public void changedUpdate(DocumentEvent e)
    {
        isChanged = true;
    }

    public void insertUpdate(DocumentEvent e)
    {
        isChanged = true;
    }

    public void removeUpdate(DocumentEvent e)
    {
        isChanged = true;
    }


    //-------------------------------------------------
    // JFormattedDoc
    //-------------------------------------------------

    public class JFormattedDoc extends ValPlainTextDocument
    {
        protected int                     limit;
        protected ValFormattedTextFieldSingle   textField;
        protected UIFieldFormatterIFace   docFormatter;
        protected UIFieldFormatterField[] docFields;

        /**
         * CReate a special formatted document
         * @param textField the textfield the document is associated with
         * @param formatter the formatter
         * @param limit the lengthof the format
         */
        public JFormattedDoc(ValFormattedTextFieldSingle textField, UIFieldFormatterIFace formatter, int limit)
        {
            super();
            this.textField    = textField;
            this.docFormatter = formatter;
            this.limit        = limit;
            this.docFields    = new UIFieldFormatterField[limit];
            int inx = 0;
            for (UIFieldFormatterField f : docFormatter.getFields())
            {
                for (int i=0;i<f.getSize();i++)
                {
                    docFields[inx++] = f;
                }
            }
        }

        /**
         * Check to see if the input was correct (doesn't check against the separator)
         * @param field the field info
         * @param str the str to be checked
         * @return true char matches the type of input, false it is in error
         */
        protected boolean isCharOK(final UIFieldFormatterField field, final String str)
        {
            if (field.getType() == UIFieldFormatterField.FieldType.alpha && !StringUtils.isAlpha(str))
            {
                return false;

            } else if (field.getType() == UIFieldFormatterField.FieldType.alphanumeric && !StringUtils.isAlphanumeric(str))
            {
                return false;

            } else if (field.getType() == UIFieldFormatterField.FieldType.numeric)
            {
                // we really need to check to make sure this is a Double, Float or BigDecimal
                String s = StringUtils.remove(str, '.');
                s = StringUtils.remove(str, '.');
                return s.length() == 0 || StringUtils.isNumericSpace(s);

            }
            return true;
        }

        /**
         * Checks to see if the incoming string maps correctly to the format and ll the chars match the appropriate type
         * @param str the string
         * @return true - ok, false there was an error
         */
        protected boolean okToInsertText(final String str)
        {
            int len = Math.min(str.length(), limit);
            for (int i=0;i<len;i++)
            {
                char c = str.charAt(i);
                if (docFields[i].getType() == UIFieldFormatterField.FieldType.separator)
                {
                    if (c != docFields[i].getValue().charAt(0))
                    {
                        return false;
                    }
                }
                String s = "";
                s += c;
                if (!isCharOK(docFields[i], s))
                {
                    return false;
                }
            }
            return true;
        }

        /* (non-Javadoc)
         * @see javax.swing.text.Document#remove(int, int)
         */
        @Override
        public void remove(int offset, int len)
        {
            UIFieldFormatterField field = docFields[offset];
            
            // We can't let them try to delete separator's or incrementers
            if (!doSetText && len < limit && (field.isIncrementer() || field.isByYear() || docFields[offset].getType() == UIFieldFormatterField.FieldType.separator))
            {
                int pos = getCaretPosition();
                setCaretPosition(pos - field.getSize());
                return;
            }
            
            try
            {
                int l = this.getLength()-offset;
                super.remove(offset, l);
                    
                validateState();

            } catch (BadLocationException ex)
            {
                throw new RuntimeException(ex);
            }
        }

        /* (non-Javadoc)
         * @see javax.swing.text.Document#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
         */
        @Override
        public void insertString(final int offset, final String strArg, final AttributeSet attr) throws BadLocationException
        {
            String str = strArg;
            if (str == null)
            {
                return;
            }

            if (str.length() > 1)
            {
                if (!doSetText)
                {
                    int len = Math.min(str.length(), limit);
                    if (len < limit && docFields[len-1].isIncrementer())
                    {
                        return;
                    }
                }
                
                if (okToInsertText(str))
                {
                    // This way truncates incoming values
                    //int newLen = Math.min(str.length(), limit);
                    //valState = offset + newLen < requiredLength;
                    //super.insertString(offset, str.substring(0, newLen), attr);

                    //valState = offset + str.length() < requiredLength ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
                    super.insertString(offset, str, attr);

                } else
                {
                    //valState = UIValidatable.ErrorType.Error;
                    //getToolkit().beep();
                }
                validateState();
                //System.out.println("******* "+(valState));
                return;
            }

            int len = getLength() + str.length();
            if (len <= limit)
            {
                UIFieldFormatterField field =  docFields[offset];
                if (!isCharOK(field, str))
                {
                    //getToolkit().beep();
                    //valState = UIValidatable.ErrorType.Error;
                    //System.out.println("******* "+(valState));
                    validateState();
                    return;
                }

                if (field.getType() == UIFieldFormatterField.FieldType.separator)
                {
                    if (str.charAt(0) != field.getValue().charAt(0))
                    {
                        if (!isCharOK(docFields[offset + 1], str))
                        {
                            //valState = UIValidatable.ErrorType.Error;
                            //getToolkit().beep();
                            validateState();
                            return;
                        }
                        str = field.getValue() + str;
                    }
                } else
                {
                    //int offsetinx = offset;
                    if (docFields[offset].isIncrementer())
                    {
                        /*str   = docFields[offset].getValue();
                        field = docFields[offset + docFields[offset].getValue().length()];
                        if (!isCharOK(field, str))
                        {
                            //getToolkit().beep();
                            //valState = UIValidatable.ErrorType.Error;
                            //System.out.println("******* "+(valState));
                            validateState();
                            return;
                        }*/                      
                    }
                }
                //valState = offset + str.length() < requiredLength ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;

                super.insertString(offset, str, attr);
                
                String text = textField.getText();
                if (text != null && text.length() < limit)
                {
                    int inx =  text.length();
                    field   = docFields[inx];
                    if (field != null && (field.getType() == UIFieldFormatterField.FieldType.separator || field.isIncrementer()))
                    {
                        StringBuilder sb = new StringBuilder(text.substring(offset + str.length()));
                        while (field != null && (field.getType() == UIFieldFormatterField.FieldType.separator || field.isIncrementer()))
                        {
                            if (field.getType() == UIFieldFormatterField.FieldType.separator)
                            {
                                sb.append(field.getValue());
                                inx++;
                                
                            } else
                            {
                                for (int i=0;i<field.getSize();i++)
                                {
                                    sb.append("#");
                                }
                                inx += field.getSize();
                            }
                            
                            if (inx < limit)
                            {
                                field = docFields[inx];
                            } else
                            {
                                field = null;
                            }
                        }
                        insertString(offset + str.length(), sb.toString(), attr);
                    }
                }
                
            } else
            {
                //valState = UIValidatable.ErrorType.Error;
            }

            validateState();
        }
    }
}

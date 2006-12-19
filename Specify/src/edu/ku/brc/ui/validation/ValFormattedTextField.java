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

package edu.ku.brc.ui.validation;

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
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.ui.ColorWrapper;
import edu.ku.brc.ui.DateWrapper;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.ui.forms.UIFieldFormatterMgr;

/**
 * A JTextField (wrapped inside a JPanel) that provides for "formatted" input. The format "mask" is define in XML
 * via the UIFieldFormatterMgr class. This is idea for text fields that have a standard size and a specific format (i.e. Dates)
 * The mask enables the "fields" and separators to be specifically defined.

 * @code_status Beta
 **
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ValFormattedTextField extends JTextField implements UIValidatable,
                                                                 GetSetValueIFace,
                                                                 DocumentListener
{
    private static final Logger log  = Logger.getLogger(ValFormattedTextField.class);

    protected static ColorWrapper     valtextcolor       = null;
    protected static ColorWrapper     requiredfieldcolor = null;
    protected static DateWrapper      scrDateFormat      = AppPrefsCache.getDateWrapper("ui", "formatting", "scrdateformat");                

    protected UIValidatable.ErrorType valState  = UIValidatable.ErrorType.Valid;
    protected boolean                 isRequired = false;
    protected boolean                 isChanged  = false;
    protected boolean                 isNew      = false;
    protected Color                   bgColor    = null;

    protected int                     requiredLength   = 0;
    protected Object[]                formatObj  = null;

    protected JFormattedDoc           document;
    protected String                  defaultValue = null;

    protected UIFieldFormatterMgr.Formatter            formatter;
    protected List<UIFieldFormatterMgr.FormatterField> fields = null;

    //---
    protected String bgStr     = null;
    protected Point  pnt       = null;
    protected Color  textColor = new Color(0,0,0,64);
    protected Insets inner;

    /**
     * Constructor
     * @param formatterName the formatters name
     */
    protected ValFormattedTextField()
    {
        // do nothing
    }

    /**
     * Constructor
     * @param formatterName the formatters name
     */
    public ValFormattedTextField(final String formatterName)
    {
        super();

        init();

        formatter = UIFieldFormatterMgr.getFormatter(formatterName);
        fields = formatter.getFields();

        StringBuilder strBuf = new StringBuilder(32);
        for (UIFieldFormatterMgr.FormatterField field : fields)
        {
            requiredLength += field.getSize();
            strBuf.append(field.getValue());
        }
        bgStr = strBuf.toString();
        inner = getInsets();

        this.setColumns(requiredLength);

        document = new JFormattedDoc(this, formatter, requiredLength);
        setDocument(document);
        document.addDocumentListener(this);
        
        addFocusListener(new FocusAdapter()
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
                        isNew = false;
                        validateState();
                        repaint();

                    }
                });

    }


    /**
     * Inits the control
     */
    public void init()
    {
        bgColor = getBackground();
        if (valtextcolor == null || requiredfieldcolor == null)
        {
            valtextcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "valtextcolor");
            requiredfieldcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "requiredfieldcolor");
        }
    }


    /**
     * Helper method for validation sripting to see if the text field is empty
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

        if (text != null && text.length() < bgStr.length())
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
        super.setEnabled(enabled);

        //setBackground(isRequired && isEnabled() ? requiredfieldcolor.getColor() : bgColor);
    }

    /* (non-Javadoc)
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    @Override
    public void setText(String text)
    {
        document.setIgnoreNotify(true);
        super.setText(text);
        document.setIgnoreNotify(false);
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
     * Sets the text and notifies the validator of the change. This is used manually callit directly.
     * @param text the new text (must already be formatted).
     */
    public void setTextWithNotify(String text)
    {
        super.setText(text);
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
     * @see edu.ku.brc.ui.validation.UIValidatable#getState()
     */
    public ErrorType getState()
    {
        return valState;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#setState(edu.ku.brc.ui.validation.UIValidatable.ErrorType)
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
        return isRequired;
    }

    /* (non-Javadoc)
     * @see edu.kui.brc.ui.validation.UIValidatable#setRequired(boolean)
     */
    public void setRequired(boolean isRequired)
    {
        setBackground(isRequired && isEnabled() ? requiredfieldcolor.getColor() : bgColor);
        this.isRequired = isRequired;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#isChanged()
     */
    public boolean isChanged()
    {
        return isChanged;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#setChanged(boolean)
     */
    public void setChanged(boolean isChanged)
    {
        this.isChanged = isChanged;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#setAsNew(boolean)
     */
    public void setAsNew(boolean isNew)
    {
        this.isNew = isRequired ? isNew : false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#reset()
     */
    public void reset()
    {
        setText( StringUtils.isNotEmpty(defaultValue) ? defaultValue : "");
        validateState();
        repaint();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#getValidatableUIComp()
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
        String data = getText();
        if (StringUtils.isEmpty(data))
        {
            valState = isRequired ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;

        } else
        {
            valState = data.length() != requiredLength ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
        }
        //System.out.println("#### validateState "+ getText()+"  "+data.length() +"  "+ requiredLength+"  "+valState);

        return valState;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.validation.UIValidatable#cleanUp()
     */
    public void cleanUp()
    {
        document  = null;
        formatter = null;
        fields    = null;
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
                data = scrDateFormat.format((Date)value);
                
            } else if (value instanceof Calendar)
            {
                data = scrDateFormat.format(((Calendar)value).getTime());
                
            } else
            {
                data = value.toString();
            }
        } else
        {

            data = StringUtils.isNotEmpty(defaultValue) ? defaultValue : "";
        }

        setText(data);

        validateState();

        repaint();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#getValue()
     */
    public Object getValue()
    {
        if (formatter.getName().equals("Date"))
        {
            String value = getText();
            if (StringUtils.isNotEmpty(value))
            {
                try
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(scrDateFormat.getSimpleDateFormat().parse(value));
                    return cal;

                } catch (ParseException ex)
                {
                    log.error("Date is in error for parsing["+value+"]");
                }
            }
            return null;

        }
        // else
        return getText();

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
    // AppPrefsChangeListener
    //-------------------------------------------------

    public class JFormattedDoc extends ValPlainTextDocument
    {
        protected int limit;
        protected ValFormattedTextField textField;
        protected UIFieldFormatterMgr.Formatter docFormatter;
        protected UIFieldFormatterMgr.FormatterField[] docFields;

        /**
         * CReate a special formatted document
         * @param textField the textfield the document is associated with
         * @param formatter the formatter
         * @param limit the lengthof the format
         */
        public JFormattedDoc(ValFormattedTextField textField, UIFieldFormatterMgr.Formatter formatter, int limit)
        {
            super();
            this.textField    = textField;
            this.docFormatter = formatter;
            this.limit        = limit;
            docFields = new UIFieldFormatterMgr.FormatterField[limit];
            int inx = 0;
            for (UIFieldFormatterMgr.FormatterField f : docFormatter.getFields())
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
         * @returntrue char matches the type of input, false it is in error
         */
        protected boolean isCharOK(final UIFieldFormatterMgr.FormatterField field, final String str)
        {
            if (field.getType() == UIFieldFormatterMgr.FieldType.alpha && !StringUtils.isAlpha(str))
            {
                return false;

            } else if (field.getType() == UIFieldFormatterMgr.FieldType.alphanumeric && !StringUtils.isAlphanumeric(str))
            {
                return false;

            } else if (field.getType() == UIFieldFormatterMgr.FieldType.numeric && !StringUtils.isNumeric(str))
            {
                return false;

            }
            return true;
        }

        /**
         * Checks to see if the icoming string maps correctly to the format and ll the chars match the appropriate type
         * @param str the string
         * @return true - ok, false there was an error
         */
        protected boolean okToInsertText(final String str)
        {
            int len = Math.min(str.length(), limit);
            for (int i=0;i<len;i++)
            {
                char c = str.charAt(i);
                if (docFields[i].getType() == UIFieldFormatterMgr.FieldType.separator)
                {
                    if (c != docFields[i].getValue().charAt(0))
                    {
                        return false;
                    }
                }
                String s = "";
                c += c;
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
            try
            {
                super.remove(offset, len);
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
                    getToolkit().beep();
                }
                validateState();
                //System.out.println("******* "+(valState));
                return;
            }

            int len = getLength() + str.length();
            if (len <= limit)
            {
                UIFieldFormatterMgr.FormatterField field =  docFields[offset];
                if (!isCharOK(field, str))
                {
                    getToolkit().beep();
                    //valState = UIValidatable.ErrorType.Error;
                    //System.out.println("******* "+(valState));
                    validateState();
                    return;
                }

                if (field.getType() == UIFieldFormatterMgr.FieldType.separator)
                {
                    if (str.charAt(0) != field.getValue().charAt(0))
                    {
                        if (!isCharOK(docFields[offset + 1], str))
                        {
                            //valState = UIValidatable.ErrorType.Error;
                            getToolkit().beep();
                            validateState();
                            return;
                        }
                        str = field.getValue() + str;
                    }
                }
                //valState = offset + str.length() < requiredLength ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;

                super.insertString(offset, str, attr);
            } else
            {
                //valState = UIValidatable.ErrorType.Error;
            }

            validateState();
        }
    }
}

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

import static edu.ku.brc.ui.UIHelper.createLabel;
import static edu.ku.brc.ui.UIHelper.setControlSize;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.prefs.AppPrefsCache;
import edu.ku.brc.ui.ColorWrapper;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.forms.ViewFactory;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterField;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.ui.forms.formatters.UIFieldFormatterMgr;

/**
 * A Multiple JTextFields (wrapped inside a JPanel) that provides for "formatted" input. The format "mask" is define in XML
 * via the UIFieldFormatterMgr class. This is idea for text fields that have a standard size and a specific format (i.e. Dates)
 * The mask enables the "fields" and separators to be specifically defined.
 * 
 * NOTE: This impl has multiple Text Field, one for each part of the format.
 * 
 * @code_status Beta
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ValFormattedTextField extends JPanel implements UIValidatable,
                                                             GetSetValueIFace,
                                                             DocumentListener,
                                                             AutoNumberableIFace
{
    //private static final Logger log  = Logger.getLogger(ValFormattedTextField.class);

    protected static ColorWrapper     valtextcolor       = null;
    protected static ColorWrapper     requiredfieldcolor = null;
    protected static ColorWrapper     viewFieldColor     = null;

    protected UIValidatable.ErrorType     valState       = UIValidatable.ErrorType.Valid;
    protected boolean                     isRequired     = false;
    protected boolean                     isChanged      = false;
    protected boolean                     isNew          = false;
    protected boolean                     isViewOnly     = false;
    protected Color                       bgColor        = null;
    
    protected JTextField                  viewtextField  = null;

    protected int                         requiredLength = 0;
    protected boolean                     shouldIgnoreNotifyDoc = true;
    protected boolean                     needsUpdating  = false;
    
    protected String                      currCachedValue = null;

    protected List<JFormattedDoc>         documents      = new Vector<JFormattedDoc>();
    protected String                      defaultValue   = null;
    protected DocumentListener            documentListener = null;

    protected UIFieldFormatterIFace       formatter;
    protected List<UIFieldFormatterField> fields         = null;
    
    protected Object                      origValue      = null;
    protected ChangeListener              changeListener = null;

    //---
    protected Color  textColor = new Color(0,0,0,64);
    
    protected JComponent[] comps = null;

    /**
     * Constructor
     * @param dataObjFormatterName the formatters name
     */
    protected ValFormattedTextField()
    {
        super();
        setOpaque(false);
    }

    /**
     * Constructor.
     * @param formatter
     * @param isViewOnly
     * @param isAllEditable
     */
    public ValFormattedTextField(final UIFieldFormatterIFace formatter, 
                                 final boolean isViewOnly, 
                                 final boolean isAllEditable)
    {
        this();
        
        this.isViewOnly = isViewOnly;
        
        init(formatter, isAllEditable);
    }

    /**
     * Constructor.
     * @param formatter the formatters
     */
    public ValFormattedTextField(final UIFieldFormatterIFace formatter, final boolean isViewOnly)
    {
        this(formatter, isViewOnly, false);
    }

    /**
     * Constructor.
     * @param formatter the formatters
     */
    public ValFormattedTextField(final String formatterName, final boolean isViewOnly)
    {
        this(formatterName, isViewOnly, false);
    }

    /**
     * Constructor
     * @param formatterName the formatters name
     */
    public ValFormattedTextField(final String formatterName, final boolean isViewOnly, final boolean isAllEditable)
    {
        super();

        this.isViewOnly = isViewOnly;
        
        init(UIFieldFormatterMgr.getFormatter(formatterName), isAllEditable);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#requestFocus()
     */
    @Override
    public void requestFocus()
    {
        if (comps != null && comps[0] != null)
        {
            comps[0].requestFocus();
            //comps[0].requestFocusInWindow();
        }
    }

    /**
     * 
     */
    protected void init(final UIFieldFormatterIFace formatterArg, final boolean isAllEditable)
    {
        setFormatterInternal(formatterArg);
        
        createUI();
        
        if (valtextcolor == null || requiredfieldcolor == null ||viewFieldColor == null)
        {
            valtextcolor       = AppPrefsCache.getColorWrapper("ui", "formatting", "valtextcolor");
            requiredfieldcolor = AppPrefsCache.getColorWrapper("ui", "formatting", "requiredfieldcolor");
            viewFieldColor     = AppPrefsCache.getColorWrapper("ui", "formatting", "viewfieldcolor");
        }
        
        //if (isViewOnly || !formatter.isUserInputNeeded())
        //{
        //    setBackground(viewFieldColor.getColor());
        //}
        
        if (!isViewOnly && comps != null && !isAllEditable)
        {
            int inx = 0;
            for (UIFieldFormatterField field : fields)
            {
                if (field.isIncrementer())
                {
                    if (comps[inx] instanceof JTextField)
                    {
                        ViewFactory.changeTextFieldUIForDisplay(((JTextField)comps[inx]), false);
                    }
                }
                inx++;
            }
        }
    }
    
    /**
     * @return the text field
     */
    public JTextField getTextField()
    {
        return viewtextField;
    }
    
    /**
     * @return the comps
     */
    public JComponent[] getTextComps()
    {
        return comps;
    }

    /**
     * Creates the various UI Components for the formatter.
     */
    protected void createUI()
    {
        CellConstraints cc = new CellConstraints();
        
        if (isViewOnly || !formatter.isUserInputNeeded())
        {
            viewtextField = new JTextField();
            setControlSize(viewtextField);
            
            JFormattedDoc document = new JFormattedDoc(viewtextField, formatter, formatter.getFields().get(0));
            viewtextField.setDocument(document);
            document.addDocumentListener(this);
            documents.add(document);
            
            ViewFactory.changeTextFieldUIForDisplay(viewtextField, false);
            PanelBuilder    builder = new PanelBuilder(new FormLayout("1px,P,1px", "1px,P,1px"), this);
            builder.add(viewtextField, cc.xy(2, 2));
            bgColor = viewtextField.getBackground();

        } else
        {
            Font font = new Font("Courier", Font.PLAIN, 12);
            
            BufferedImage bi = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics(font);
            g.dispose();
            
            JTextField txt = new JTextField();
            Insets ins = txt.getBorder().getBorderInsets(txt);
            int baseWidth = ins.left + ins.right;
            
            bgColor = txt.getBackground();
    
            
            StringBuilder sb = new StringBuilder("1px");
            int i = 0;
            for (UIFieldFormatterField f : fields)
            {
                sb.append(",");
                if (f.getType() == UIFieldFormatterField.FieldType.separator)
                {
                    sb.append('p');
                } else
                {
                    sb.append(((fm.getMaxAdvance() * f.getSize()) + baseWidth) + "px");
                }
                i++;
            }
            sb.append(",1px");
            
            PanelBuilder    builder = new PanelBuilder(new FormLayout(sb.toString(), "1px,P,1px"), this);
            
            comps = new JComponent[fields.size()];
            int inx = 0;
            for (UIFieldFormatterField f : fields)
            {
                JComponent comp = null;
                if (f.getType() == UIFieldFormatterField.FieldType.separator)
                {
                    comp = createLabel(f.getValue());

                } else
                {
                    JTextField tf = new BGTextField(f.getSize(), isViewOnly ? "" : f.getValue());
                    JFormattedDoc document = new JFormattedDoc(tf, formatter, f);
                    tf.setDocument(document);
                    document.addDocumentListener(this);
                    documents.add(document);

                    tf.addFocusListener(new FocusAdapter()
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
                    comp = tf;
                    comp.setFont(font);
                    
                }
                setControlSize(comp);
                builder.add(comp, cc.xy(inx+2, 2));
                comps[inx] = comp;
                inx++;
            }
        }
    }
    
    /**
     * @param changeListener the changeListener to set
     */
    public void setChangeListener(ChangeListener changeListener)
    {
        this.changeListener = changeListener;
    }

    /**
     * Sets the formatter.
     * @param dataObjFormatterName the formatter to use
     */
    protected void setFormatterInternal(final UIFieldFormatterIFace formatterArg)
    {
        if (formatter != formatterArg && formatterArg != null)
        {
            formatter = formatterArg;
            
            fields = formatter.getFields();
    
            requiredLength = formatter.getLength();
        }
    }
    
    /**
     * Sets the BG color of all the text fields.
     * @param color
     */
    protected void setBGColor(final Color color)
    {
        if (viewtextField != null)
        {
            viewtextField.setBackground(color);
            
        } else if (comps != null)
        {
            for (JComponent comp : comps)
            {
                if (comp instanceof JTextField)
                {
                    ((JTextField)comp).setBackground(color);
                } 
            }
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
     * Helper method for validation scripting to see if the text field is empty
     * @return whether the text field is empty or not
     */
    public boolean isNotEmpty()
    {
        return getText().length() > 0;
    }
    
    /**
     * @return
     */
    public String getText()
    {
        if (viewtextField != null)
        {
            return viewtextField.getText();
        }
        
        //if (currCachedValue == null)
        //{
            StringBuilder sb = new StringBuilder();
            int inx = 0;
            for (JComponent c : comps)
            {
                String val = null;
                if (c instanceof JLabel)
                {
                    val = ((JLabel)c).getText();
                } else
                {
                    val = ((JTextField)c).getText();
                }
                
                sb.append(val);
                
                if (StringUtils.isEmpty(val))
                {
                    return null;
                }
                inx++;
            }
            currCachedValue = sb.toString();
        //}
        return currCachedValue;
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled)
    {
        boolean isEnabled = enabled;
        if (!isViewOnly)
        {
            boolean isNeeded = formatter.isUserInputNeeded();
            if (enabled && isNeeded)
            {
                isEnabled = isNeeded;
                
            } else
            {
                isEnabled = enabled;
            }
    
            setBGColor(isRequired && isEnabled ? requiredfieldcolor.getColor() : bgColor);
        }
        
        super.setEnabled(isEnabled);
        
        if (viewtextField != null)
        {
            viewtextField.setEnabled(isEnabled);
            
        } else if (comps != null)
        {
            for (JComponent comp : comps)
            {
                if (comp instanceof JTextField)
                {
                    ((JTextField)comp).setEnabled(isEnabled);
                } 
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.text.JTextComponent#setText(java.lang.String)
     */
    public void setText(final String text, final boolean notify)
    {
        if (viewtextField != null)
        {
            viewtextField.setText(text);
            return;
        }
        
        shouldIgnoreNotifyDoc = !notify;
        
        boolean isTextEmpty = StringUtils.isEmpty(text);
        
        int txtLen = text.length();
        //int len    = formatter.getLength();
        int inx    = 0;
        int pos    = 0;
        for (UIFieldFormatterField field : fields)
        {
            String val;
            if (isTextEmpty)
            {
                /*if (field.isEntryField())
                {
                    if (field.getType() == UIFieldFormatterField.FieldType.year)
                    {
                        val = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                    } else
                    {
                        val = "";
                    }
                } else
                {
                    val = field.getValue();
                }*/
                val = "";
            } else
            {
                if (pos < txtLen)
                {
                    val = text.substring(pos, Math.min(pos+field.getSize(), txtLen));
                } else
                {
                    val = "";
                }
            }
            
            if (comps[inx] instanceof JLabel)
            {
                if (!val.equals(field.getValue()))
                {
                    valState = UIValidatable.ErrorType.Error;
                }
            } else
            {
                ((JTextField)comps[inx]).setText(val);
            }
            pos += field.getSize();
            inx++;
        }
        
        shouldIgnoreNotifyDoc = false;
        
        repaint();
    }
    
    /* (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        if (!isViewOnly && !isNew && valState == UIValidatable.ErrorType.Error && isEnabled())
        {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Dimension dim = getSize();
            g.setColor(valtextcolor.getColor());
            g.drawRect(1, 1, dim.width-2, dim.height-2);
        }
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
    public void setText(final String text)
    {
        setText(text, true);
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
        if (needsUpdating)
        {
            String nextNum = formatter.getNextNumber(getText());
            if (StringUtils.isNotEmpty(nextNum))
            {
                try
                {
                    setValue(nextNum, nextNum);
                    
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            needsUpdating = false;
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
        this.isRequired = isRequired;
        setBGColor(isRequired && isEnabled() ? requiredfieldcolor.getColor() : bgColor);
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
        this.isNew = isRequired ? isNew : false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#reset()
     */
    public void reset()
    {
        origValue = null;
        if (StringUtils.isNotEmpty(defaultValue))
        {
            setText(defaultValue);
        }
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
        UIValidatable.ErrorType oldState = valState;
        
        if (isViewOnly)
        {
            valState = UIValidatable.ErrorType.Valid;
            
        } else if (formatter != null && formatter.isUserInputNeeded())
        {
            
            String data = getText();
            if (StringUtils.isEmpty(data))
            {
                valState = isRequired ? UIValidatable.ErrorType.Incomplete : UIValidatable.ErrorType.Valid;
    
            } else
            {
                valState = data.length() != requiredLength ? UIValidatable.ErrorType.Error : UIValidatable.ErrorType.Valid;
            }
        } else
        {
            valState = UIValidatable.ErrorType.Valid;
        }
        
        if (oldState != valState)
        {
            repaint();
        }
        //System.err.println("#### validateState "+ getText()+"  "+ requiredLength+"  "+valState);
       
        return valState;
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.validation.UIValidatable#cleanUp()
     */
    public void cleanUp()
    {
        UIHelper.removeFocusListeners(this);
        UIHelper.removeKeyListeners(this);

        for (JFormattedDoc document : documents)
        {
            for (DocumentListener l : document.getDocumentListeners())
            {
                document.removeDocumentListener(l);
            }
        }
        documents.clear();
        documents = null;
        formatter = null;
        fields    = null;
        UIHelper.removeFocusListeners(this);
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

            data = StringUtils.isNotEmpty(defaultValue) ? defaultValue : "";
        }
        
        if (origValue == null)
        {
            origValue = value;
        }
        
        String fmtVal;
        if (formatter != null)
        {
            if (formatter.isInBoundFormatter())
            {
                needsUpdating = StringUtils.isEmpty(data) && formatter.getAutoNumber() != null && formatter.isIncrementer();
                
                fmtVal = (String)formatter.formatInBound(data);
                
            } else 
            {
                if (value == null)
                {
                    needsUpdating = true;
                }
                fmtVal = data;
            }
        } else
        {
            fmtVal = data;
        }
        
        setText(fmtVal);

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
            return UIHelper.getDate(getText(), formatter.getDateWrapper());
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
    
    /**
     * @param dl
     */
    public void addDocumentListener(final DocumentListener dl)
    {
        documentListener = dl;
        //for (JFormattedDoc document : documents)
        //{
        //    document.addDocumentListener(dl);
        //}
    }

    //--------------------------------------------------------
    // DocumentListener
    //--------------------------------------------------------

    protected void changeOccurred()
    {
        isChanged = true;
        if (changeListener != null && !shouldIgnoreNotifyDoc)
        {
            //validateState();
            changeListener.stateChanged(new ChangeEvent(this));
            documentListener.changedUpdate(null);
        }
        currCachedValue = null;
    }
    
    public void changedUpdate(DocumentEvent e)
    {
        changeOccurred();
    }

    public void insertUpdate(DocumentEvent e)
    {
        changeOccurred();
    }

    public void removeUpdate(DocumentEvent e)
    {
        changeOccurred();
    }
    
    //--------------------------------------------------------
    // Individual Text Field
    //--------------------------------------------------------
    class BGTextField extends JTextField
    {
        protected String bgStr = "";
        protected Point  pnt   = null;
        protected Insets inner;

        public BGTextField(final int size, final String bgStr)
        {
            super(size);
            this.bgStr = bgStr;
            this.inner = getInsets();
        }
        
        /* (non-Javadoc)
         * @see javax.swing.text.JTextComponent#setText(java.lang.String)
         */
        public void setText(final String text)
        {
            JFormattedDoc document = (JFormattedDoc)getDocument();
            document.setIgnoreNotify(shouldIgnoreNotifyDoc);
            super.setText(text);
            document.setIgnoreNotify(false);
        }
        
        /* (non-Javadoc)
         * @see java.awt.Component#paint(java.awt.Graphics)
         */
        @Override
        public void paint(Graphics g)
        {
            super.paint(g);

            String text = getText();

            if (isEnabled() && text != null && text.length() < bgStr.length())
            {
                FontMetrics fm   = g.getFontMetrics();
                int          w   = fm.stringWidth(text);
                pnt = new Point(inner.left+w, inner.top + fm.getAscent());

                g.setColor(textColor);
                g.drawString(bgStr.substring(text.length(), bgStr.length()), pnt.x, pnt.y);
            }
        }
    }

    
    public class JFormattedDoc extends ValPlainTextDocument
    {
        protected int                       limit;
        protected JTextField                textField;
        protected UIFieldFormatterIFace     docFormatter;
        protected UIFieldFormatterField     docField;

        /**
         * CReate a special formatted document
         * @param textField the textfield the document is associated with
         * @param formatter the formatter
         * @param limit the lengthof the format
         */
        public JFormattedDoc(final JTextField            textField, 
                             final UIFieldFormatterIFace formatter,
                             final UIFieldFormatterField docField)
        {
            super();
            
            this.textField    = textField;
            this.docFormatter = formatter;
            this.docField     = docField;
            this.limit        = docField.getSize();
        }

        /**
         * Check to see if the input was correct (doesn't check against the separator)
         * @param field the field info
         * @param str the str to be checked
         * @returntrue char matches the type of input, false it is in error
         */
        protected boolean isCharOK(final UIFieldFormatterField field, final String str)
        {
            if (field.getType() == UIFieldFormatterField.FieldType.alpha && !StringUtils.isAlpha(str))
            {
                return false;

            } else if (field.getType() == UIFieldFormatterField.FieldType.alphanumeric && !StringUtils.isAlphanumeric(str))
            {
                return false;

            } else if (field.getType() == UIFieldFormatterField.FieldType.numeric && !StringUtils.isNumeric(str))
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
            String contentStr = textField.getText();
            int    newLen     = contentStr.length() + str.length();
            
            if (newLen == limit && str.charAt(0) == '#')
            {
                return true;
            }
            return newLen <= limit && isCharOK(docField, str);
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
                
            if (okToInsertText(str))
            {
                super.insertString(offset, str, attr);
            }
            
            if (getLength() == limit)
            {
                int inx = 0;
                for (UIFieldFormatterField f : fields)
                {
                    int len = fields.size();
                    if (f == docField && inx < len-1)
                    {
                        for (int i=inx+1;i<len;i++)
                        {
                            UIFieldFormatterField nxtField = fields.get(i);
                            if (!nxtField.isByYear() && nxtField.isEntryField())
                            {
                                comps[i].requestFocus();
                                break;
                            }
                        }
                    }
                    inx++;
                }
            }

            validateState();
        }
    }

    /**
    *
    */
    /*
   public static void main(String[] args)
   {
       SwingUtilities.invokeLater(new Runnable() {
           @SuppressWarnings("synthetic-access")
         public void run()
           {
               try
               {
                   UIHelper.OSTYPE osType = UIHelper.getOSType();
                   if (osType == UIHelper.OSTYPE.Windows )
                   {
                       //UIManager.setLookAndFeel(new WindowsLookAndFeel());
                       UIManager.setLookAndFeel(new PlasticLookAndFeel());
                       PlasticLookAndFeel.setPlasticTheme(new ExperienceBlue());
                       
                   } else if (osType == UIHelper.OSTYPE.Linux )
                   {
                       //UIManager.setLookAndFeel(new GTKLookAndFeel());
                       UIManager.setLookAndFeel(new PlasticLookAndFeel());
                      
                   }
                   
                   Vector<UIFieldFormatterField> fields = new Vector<UIFieldFormatterField>();
                   fields.add(new UIFieldFormatterField(UIFieldFormatterField.FieldType.year,   4, "YYYY", false, true));
                   fields.add(new UIFieldFormatterField(UIFieldFormatterField.FieldType.separator, 1, "-", false));
                   fields.add(new UIFieldFormatterField(UIFieldFormatterField.FieldType.alpha,   2, "XX", false));
                   fields.add(new UIFieldFormatterField(UIFieldFormatterField.FieldType.separator, 1, "-", false));
                   fields.add(new UIFieldFormatterField(UIFieldFormatterField.FieldType.numeric,   4, "NNNN", true));
                   
                   UIFieldFormatter uif = new UIFieldFormatter("Accession", false, null, Accession.class, true, true, fields);

                   ValFormattedTextField formattedTextField = new ValFormattedTextField(uif, false);
                   
                   CustomDialog dlg = new CustomDialog(null, "Test", true, formattedTextField);
                   formattedTextField.setText("2005-IT-001");
                   dlg.setVisible(true);
                   
                   System.out.println(formattedTextField.getText());
                   dlg.dispose();
               }
               catch (Exception e)
               {
                   log.error("Can't change L&F: ", e);
               }
           }
       });
   }*/
}

/* Filename:    $RCSfile: JAutoCompComboBox.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2006/01/27 19:59:54 $
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
package edu.ku.brc.specify.ui.db;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * An editable JComboBox that enables auto-completion which is supported through PickList/PickListItem. 
 * The searches in the list can be case-sensitive or insensitive
 * 
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class JAutoCompComboBox extends JComboBox
{
    protected int                caretPos        = 0;
    protected boolean            enableAdditions = true;
    protected boolean            caseInsensitve  = true;

    protected JTextField         tf              = null;
    protected JAutoCompComboBox  cbx             = null;
    protected boolean            foundMatch      = false;
    protected boolean            ignoreFocus     = false;
    protected boolean            askBeforeSave   = true;
    
    protected PickListDBAdapter  dbAdapter       = null;

    /**
     * Constructor with Adaptor
     * @param dBAdaptor the adaptor for enabling autocomplete
     */
    public JAutoCompComboBox(PickListDBAdapter dbAdapter)
    {
        super(dbAdapter.getList());
        
        this.dbAdapter = dbAdapter;
        init();
    }
    
    /**
     * Initializes the combobox to enable the typing of values 
     */
    protected void init()
    {
        this.setEditor(new BasicComboBoxEditor());
        this.setEditable(true);
        cbx = this;
        setSelectedItem("");     
    }
    
    /**
     * Sets whether new items can be added
     * @param enableAdditions indicates items can be added
     */
    public void setEnableAdditions(final boolean enableAdditions)
    {
        this.enableAdditions = enableAdditions;
    }

    /**
     * Sets whether to ask via a dialog if a value should be added
     * @param askBeforeSave indicates it should ask
     */
    public void setAskBeforeSave(final boolean askBeforeSave)
    {
        this.askBeforeSave = askBeforeSave;
    }
    
    /**
     * Sets wehether the searches for the items are case insensitive or not
     * @param caseInsensitve
     */
    public void setCaseInsensitive(final boolean caseInsensitve)
    {
        this.caseInsensitve = caseInsensitve;
    }

    /* (non-Javadoc)
     * @see javax.swing.JComboBox#setSelectedIndex(int)
     */
    public void setSelectedIndex(int index)
    {
        super.setSelectedIndex(index);
        if (index > -1)
        {
	        tf.setText(((PickListItem)getItemAt(index)).getTitle());
	        tf.setSelectionEnd(caretPos + tf.getText().length());
	        tf.moveCaretPosition(caretPos);
        }
    }
    
    /**
     * It may or may not ask if it can add, and then it adds the new item
     * @param strArg the string that is to be added
     * @return whether it was added
     */
    protected boolean askToAdd(String strArg)
    {
        if (ignoreFocus)
        {
            return false;
        }
        
        if (strArg != null && strArg.length() > 0)
        {
            ignoreFocus = true;
            
	        if (!askBeforeSave || JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, 
                                                            "Add new value `"+strArg+"` to the list?", "Add New Item", JOptionPane.YES_NO_OPTION))
	        {
                PickListItem pli;
	            if (dbAdapter != null)
	            {
                    pli = dbAdapter.addItem(strArg, null);
	            } else
                {
                    pli = new PickListItem(strArg, null);
                }
                this.addItem(pli);
                this.setSelectedItem(pli);
                
	            ignoreFocus = false;
                
	            return true;
	        }
            ignoreFocus = false;            
        }
        return false;
    }
    
    /**
     * Check to see if it can add the the item that is in the combobox'es testfield
     */
    protected void addNewItemFromTextField()
    {
        if (getSelectedIndex() != -1) // accepting value and setting the selection to null 
        {
            tf.setSelectionStart(0);
            tf.setSelectionEnd(0);
            tf.moveCaretPosition(0);
            
        } else
        {
            // Need to add a new value
            if (enableAdditions)
            {
	            if (askToAdd(tf.getText()))
	            {
	                tf.setSelectionStart(0);
	                tf.setSelectionEnd(0);
	                tf.moveCaretPosition(0);	                                    
	            } else 
	            {
	                tf.setText("");
	            }
            }
        }        
    }

    /* (non-Javadoc)
     * @see javax.swing.JComboBox#setEditor(javax.swing.ComboBoxEditor)
     */
    public void setEditor(ComboBoxEditor anEditor)
    {
        super.setEditor(anEditor);
        if (anEditor.getEditorComponent() instanceof JTextField)
        {
            tf = (JTextField) anEditor.getEditorComponent();
            tf.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e)
                {
                    addNewItemFromTextField();
                }
            });
            
            //System.out.println(tf.getKeyListeners());
            tf.addKeyListener(new KeyAdapter()
            {
                protected int prevCaretPos = -1;
                
                public void keyPressed(KeyEvent ev)
                {
                    prevCaretPos = tf.getCaretPosition();
                }
                
                public void keyReleased(KeyEvent ev)
                {
                    char key = ev.getKeyChar();
                    if (ev.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        String s = tf.getText();
                        //System.out.println(s+" getSelectedIndex() "+getSelectedIndex());
                        if (foundMatch)
                        {
                            //System.out.println(s+"["+s.substring(0, s.length()-1)+"]");
                            tf.setText(s.substring(0, s.length()-1));
                            
                        }
                        
                    } else if ((!(Character.isLetterOrDigit(key) || Character.isSpaceChar(key))) && 
                            ev.getKeyCode() != KeyEvent.VK_DELETE)
                    {
                        if (ev.getKeyCode() == KeyEvent.VK_ENTER) 
                        {
                            addNewItemFromTextField();
                        }
                        //System.out.println("Key Code "+ev.getKeyCode()+"  Pos: "+tf.getCaretPosition()+"  Del: "+KeyEvent.VK_DELETE);
                        
                        if (ev.getKeyCode() == KeyEvent.VK_END)// || ev.getKeyCode() == KeyEvent.VK_SHIFT)
                        {
                            tf.setSelectionStart(prevCaretPos);
                            tf.setSelectionEnd(tf.getText().length());
                        }

                        //System.out.println("Returning...");                            
                        return;
                    }
                    //System.out.println("NOT Returning");
                    
                    String s = tf.getText();
                    if (s.length() == 0)
                    {
                        setSelectedIndex(-1);
                    }
                    //System.out.println(s);
                    caretPos = tf.getCaretPosition();
                    String text = "";
                    try
                    {
                        text = tf.getText(0, caretPos);
                        
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    
                    String textLowerCase = text.toLowerCase();
                    
                    foundMatch = true;
                    int n = getItemCount();
                    for (int i = 0; i < n; i++)
                    {
                        int ind;
                        if (caseInsensitve) 
                        {
                            String item = ((PickListItem)getItemAt(i)).getTitle().toLowerCase();
                            ind = item.indexOf(textLowerCase);
                        } else
                        {
                            ind = ((PickListItem)getItemAt(i)).getTitle().indexOf(text);
                        }
                        if (ind == 0)
                        {
                            setSelectedIndex(i);
                            return;
                        }
                    }
                    foundMatch = false;
                }
            });
        }
    }
    
  
    /**
     * Creates an AutoComplete JComboBox with the "ID" of the pick list it is to use.
     * @param id the id of the pick list
     * @return the AutoComplete JComboBox
     */
    public static JAutoCompComboBox create(final int id)
    {
        PickListDBAdapter adaptor = new PickListDBAdapter(1);
        return new JAutoCompComboBox(adaptor);
    }
  

}

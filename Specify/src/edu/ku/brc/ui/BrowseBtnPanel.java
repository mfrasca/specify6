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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.ui.forms.validation.ValTextField;

/**
 * This is a JPanel that contains a JTextField abd a Button that enables the user to browser for a file
 * and sets the the file and path into the text field.
 
 * @code_status Beta
 **
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class BrowseBtnPanel extends JPanel implements GetSetValueIFace
{
    protected JTextField textField;
    protected JButton    browseBtn;
    protected boolean    isForInput;

    /**
     * Constructor.
     * @param value the value is set into the text field using "toString"
     * @param cols the number of columns for the text field
     */
    public BrowseBtnPanel(final Object  value,
                          final int     cols,
                          final boolean doDirsOnly,
                          final boolean isForInput)
    {
        super(new BorderLayout());
        
        setOpaque(false);
        
        this.isForInput = isForInput;
        
        createUI(value, cols, doDirsOnly, isForInput);
    }

    /**
     * Constructor.
     * @param textField the text field to use (most likely is a ValTextField)
     */
    public BrowseBtnPanel(final JTextField textField, final boolean doDirsOnly, final boolean isForInput)
    {
        super(new BorderLayout());
        this.textField = textField;

        createUI(null, -1, doDirsOnly, isForInput);
   }

    /**
     * Creates the UI and figures out whether it needs to create a JTextField or use the one it was given.
     * @param value the value for the new TextField
     * @param cols the number of columns for the new TextField
     * @param doDirsOnly
     * @param isForInputArg
     */
    protected void createUI(final Object value, final int cols, final boolean doDirsOnly, final boolean isForInputArg)
    {
        PanelBuilder panelBuilder = new PanelBuilder(new FormLayout("f:p:g, 2dlu, r:p", "p"), this);
        CellConstraints cc = new CellConstraints();

        if (textField == null)
        {
            textField = new ValTextField(value != null ? value.toString() : "", cols);
        }
        panelBuilder.add(textField, cc.xy(1,1));

        browseBtn = new JButton(getResourceString("Browse"));
        browseBtn.addActionListener(new BrowseAction(textField, doDirsOnly, isForInputArg));
        panelBuilder.add(browseBtn, cc.xy(3,1));

        setOpaque(false);

    }

    /**
     * The text field.
     * @return the text field
     */
    public JTextField getTextField()
    {
        return textField;
    }


    /* (non-Javadoc)
     * @see java.awt.Component#setEnabled(boolean)
     */
    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
        browseBtn.setEnabled(enabled);
    }

    //-----------------------------------------------------
    // GetSetValueIFace
    //-----------------------------------------------------

     /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#setValue(java.lang.Object, java.lang.String)
     */
    public void setValue(Object value, String defaultValue)
    {
        if (value instanceof String)
        {
            String newValue = (String)value;
            String oldValue = textField.getText();
            textField.setText(newValue);
            firePropertyChange("setValue", oldValue, newValue);
        }
        
        if (value == null)
        {
            String oldValue = textField.getText();
            textField.setText(defaultValue);
            firePropertyChange("setValue", oldValue, defaultValue);
            
            // We had to put a repaint() call in here.  Swing should have done this for us.
            textField.repaint();
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.GetSetValueIFace#getValue()
     */
    public Object getValue()
    {
        return textField.getText();
    }

    //---------------------------------------------------------
    // Inner Class
    //---------------------------------------------------------

    /**
     * Action used to pop up the File Dialog.
     * @author rods
     *
     */
    public class BrowseAction implements ActionListener
    {
        private JTextField   txtField;
        private JFileChooser chooser = null;
        private boolean      dirsOnly;
        private boolean      isForInputBA;

        /**
         * Constructor with CommandAction.
         * @param textField the text control of the Browse Action
         */
        public BrowseAction(final JTextField textField, 
                            final boolean dirsOnly, 
                            final boolean isForInput)
        {
            this.txtField   = textField;
            this.dirsOnly   = dirsOnly;
            this.isForInputBA = isForInput;
        }

        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            if (chooser == null)
            {
                this.chooser    = new JFileChooser();
                this.chooser.setFileSelectionMode(dirsOnly ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
            }

            int returnVal;
            if (isForInputBA)
            {
                returnVal = chooser.showOpenDialog(UIRegistry.getTopWindow());
            } else
            {
                returnVal = chooser.showSaveDialog(UIRegistry.getTopWindow());
            }
            
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                txtField.setText(chooser.getSelectedFile().getAbsolutePath());
                txtField.repaint();
            }
        }
    }


}

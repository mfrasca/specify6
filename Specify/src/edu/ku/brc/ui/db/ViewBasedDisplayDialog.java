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
package edu.ku.brc.ui.db;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;

import edu.ku.brc.ui.UICacheManager;
import edu.ku.brc.ui.forms.MultiView;

/**
 * This is just the JFrame portion of a Frame used to display the fields in a data object. Instances of this class are created
 * by the implementation of ViewBasedDialogFactoryIFace interface. This class is consideraed to be a reference implementation.
 *
 * @code_status Complete
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class ViewBasedDisplayDialog extends JDialog implements ViewBasedDisplayIFace
{
    protected ViewBasedDisplayPanel mainPanel;

    /**
     * Constructs a search dialog from form infor and from search info.
     * @param frame the parent frame
     * @param viewSetName the viewset name
     * @param viewName the form name from the viewset
     * @param displayName the search name, this is looked up by name in the "search_config.xml" file
     * @param title the title (should be already localized before passing in)
     * @param closeBtnTitle the title of close btn
     * @param className the name of the class to be created from the selected results
     * @param idFieldName the name of the field in the clas that is the primary key which is filled in from the search table id
     * @param options the options needed for creating the form
     */
    public ViewBasedDisplayDialog(final Frame  frame,
                                  final String viewSetName,
                                  final String viewName,
                                  final String displayName,
                                  final String title,
                                  final String closeBtnTitle,
                                  final String className,
                                  final String idFieldName,
                                  final boolean isEdit,
                                  final int     options)
    {
        super(frame, title);
        
        init(viewSetName,
                viewName,
                displayName,
                title,
                closeBtnTitle,
                className,
                idFieldName,
                isEdit,
                options);
    }
    /**
     * Constructs a search dialog from form infor and from search info.
     * @param dlg the parent frame
     * @param viewSetName the viewset name
     * @param viewName the form name from the viewset
     * @param displayName the search name, this is looked up by name in the "search_config.xml" file
     * @param title the title (should be already localized before passing in)
     * @param closeBtnTitle the title of close btn
     * @param className the name of the class to be created from the selected results
     * @param idFieldName the name of the field in the clas that is the primary key which is filled in from the search table id
     * @param options the options needed for creating the form
     */
    public ViewBasedDisplayDialog(final Dialog dlg,
                                  final String viewSetName,
                                  final String viewName,
                                  final String displayName,
                                  final String title,
                                  final String closeBtnTitle,
                                  final String className,
                                  final String idFieldName,
                                  final boolean isEdit,
                                  final int     options)
    {
        super(dlg, title);
        
        init(viewSetName,
                viewName,
                displayName,
                title,
                closeBtnTitle,
                className,
                idFieldName,
                isEdit,
                options);
    }
    
    /**
     * Creates a search dialog from form infor and from search info.
     * @param frame the parent frame
     * @param viewSetName the viewset name
     * @param viewName the form name from the viewset
     * @param displayName the search name, this is looked up by name in the "search_config.xml" file
     * @param title the title (should be already localized before passing in)
     * @param closeBtnTitle the title of close btn
     * @param className the name of the class to be created from the selected results
     * @param idFieldName the name of the field in the clas that is the primary key which is filled in from the search table id
     * @param options the options needed for creating the form
     */
    protected void init(final String viewSetName,
                        final String viewName,
                        final String displayName,
                        final String title,
                        final String closeBtnTitle,
                        final String className,
                        final String idFieldName,
                        final boolean isEdit,
                        final int     options)
    {
        mainPanel = new ViewBasedDisplayPanel(this, 
                                              viewSetName, 
                                              viewName, 
                                              displayName, 
                                              closeBtnTitle, 
                                              className, 
                                              idFieldName, 
                                              isEdit, 
                                              options);

        setContentPane(mainPanel);
        pack();
        this.setModal(true);

        setLocationRelativeTo(UICacheManager.get(UICacheManager.FRAME));
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if (mainPanel.getCancelBtn() != null)
        {
            addWindowListener(new WindowAdapter()
                    {
                        @Override
                        public void windowClosing(WindowEvent e)
                        {
                            mainPanel.getCancelBtn().doClick();
                        }
                    });
        }
    }

    //------------------------------------------------------------
    //-- ViewBasedDisplayIFace Interface
    //------------------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#showDisplay(boolean)
     */
    public void showDisplay(boolean show)
    {
        setVisible(show);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#getMultiView()
     */
    public MultiView getMultiView()
    {
        return mainPanel.getMultiView();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#setCloseListener(java.beans.PropertyChangeListener)
     */
    public void setCloseListener(final PropertyChangeListener propertyChangeListener)
    {
        mainPanel.setCloseListener(propertyChangeListener);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#setData(java.lang.Object)
     */
    public void setData(final Object dataObj)
    {
        mainPanel.setData(dataObj);
        /*if (mainPanel.getMultiView().getCurrentView().getValidator() != null)
        {
            mainPanel.getMultiView().getCurrentView().getValidator().validateForm();
        }*/
        pack(); // this is because the data may be a selector to change the form
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#isEditMode()
     */
    public boolean isEditMode()
    {
        return mainPanel.isEditMode();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#shutdown()
     */
    public void shutdown()
    {
        setVisible(true);
        mainPanel.shutdown();
    }

}

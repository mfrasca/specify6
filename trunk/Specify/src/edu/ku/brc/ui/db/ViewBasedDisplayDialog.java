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

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.forms.FormHelper;
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
public class ViewBasedDisplayDialog extends CustomDialog implements ViewBasedDisplayIFace, ActionListener
{
    protected ViewBasedDisplayPanel         viewBasedPanel = null;
    protected ViewBasedDisplayActionAdapter vbdaa          = null;
    
    
    
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
    public ViewBasedDisplayDialog(final Frame  parentFrame,
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
        this(parentFrame, 
             viewSetName, 
             viewName, 
             displayName, 
             title, 
             closeBtnTitle, 
             className, 
             idFieldName, 
             isEdit, 
             true, 
             null, 
             null, 
             options,
             (isEdit ? CustomDialog.OKCANCEL : CustomDialog.OK_BTN) | CustomDialog.HELP_BTN);
    }

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
     * @param doRegOKBtn Indicates whether the OK btn should be registered so it calls save
     * @param options the options needed for creating the form
     * @param dlgBtnOptions which btns the dialog should display
     */
    public ViewBasedDisplayDialog(final Frame     parentFrame,
                                  final String    viewSetName,
                                  final String    viewName,
                                  final String    displayName,
                                  final String    title,
                                  final String    closeBtnTitle,
                                  final String    className,
                                  final String    idFieldName,
                                  final boolean   isEdit,
                                  final boolean   doRegOKBtn,
                                  final String    cellName,
                                  final MultiView mvParent,
                                  final int       options,
                                  final int       dlgBtnOptions)
    {
        super(parentFrame, title, true, dlgBtnOptions, null);
        
        viewBasedPanel = new ViewBasedDisplayPanel(this, 
                viewSetName, 
                viewName, 
                displayName, 
                className, 
                idFieldName, 
                isEdit, 
                doRegOKBtn,
                cellName,
                mvParent,
                options | MultiView.NO_SCROLLBARS);
        
        if (StringUtils.isNotEmpty(closeBtnTitle))
        {
            this.setOkLabel(closeBtnTitle);
        }
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
    public ViewBasedDisplayDialog(final Dialog  parentDialog,
                                  final String  viewSetName,
                                  final String  viewName,
                                  final String  displayName,
                                  final String  title,
                                  final String  closeBtnTitle,
                                  final String  className,
                                  final String  idFieldName,
                                  final boolean isEdit,
                                  final int     options)
    {
        this(parentDialog, viewSetName, viewName, displayName, title, closeBtnTitle, className, idFieldName, isEdit, true, null, null, options);
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
     * @param doRegOKBtn Indicates whether the OK btn should be registered so it calls save
     * @param options the options needed for creating the form
     */
    public ViewBasedDisplayDialog(final Dialog    parentDialog,
                                  final String    viewSetName,
                                  final String    viewName,
                                  final String    displayName,
                                  final String    title,
                                  final String    closeBtnTitle,
                                  final String    className,
                                  final String    idFieldName,
                                  final boolean   isEdit,
                                  final boolean   doRegOKBtn,
                                  final String    cellName,
                                  final MultiView mvParent,
                                  final int       options)
    {
        super(parentDialog, title, true, (isEdit ? CustomDialog.OKCANCEL : CustomDialog.OK_BTN) | CustomDialog.HELP_BTN, null);
        
        viewBasedPanel = new ViewBasedDisplayPanel(this, 
                viewSetName, 
                viewName, 
                displayName, 
                className, 
                idFieldName, 
                isEdit, 
                doRegOKBtn,
                cellName,
                mvParent,
                options | MultiView.NO_SCROLLBARS);

        if (StringUtils.isNotEmpty(closeBtnTitle))
        {
            this.setOkLabel(closeBtnTitle);
        }
    }
    
    /**
     * Enables the caller to have the UI pre=created before the setVisible 
     */
    public void preCreateUI()
    {
        createUI();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#createUI()
     */
    @Override
    public void createUI()
    {
        super.createUI();

        viewBasedPanel.setOkCancelBtns(okBtn, cancelBtn);
        
        mainPanel.add(viewBasedPanel, BorderLayout.CENTER);
        
        pack();
        
        addAL(okBtn);
        addAL(cancelBtn);
        addAL(applyBtn);
        addAL(helpBtn);
    }

    /**
     * Helper for adding action listeners
     * @param btn the btn
     */
    protected void addAL(final JButton btn)
    {
        if (btn != null)
        {
            btn.addActionListener(this);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see java.awt.Dialog#setVisible(boolean)
     */
    @Override
    public void setVisible(final boolean visible)
    {
        if (viewBasedPanel != null && !visible)
        {
            viewBasedPanel.aboutToShow(visible);
        }
        super.setVisible(visible);
    }
    
    //------------------------------------------------------------
    //-- ActionListener Interface
    //------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == okBtn)
        {
            FormHelper.updateLastEdittedInfo(viewBasedPanel.getMultiView().getData());
        }
        
        if (vbdaa != null)
        {
            if (e.getSource() == okBtn)
            {
                vbdaa.okPressed(this);
                
            } else if (e.getSource() == cancelBtn)
            {
                vbdaa.cancelPressed(this);
                
            } else if (e.getSource() == applyBtn)
            {
                vbdaa.applyPressed(this);
                
            } else if (e.getSource() == helpBtn)
            {
                vbdaa.helpPressed(this);
            }
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
        return viewBasedPanel.getMultiView();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#setCloseListener(edu.ku.brc.ui.db.ViewBasedDisplayActionAdapter)
     */
    public void setCloseListener(final ViewBasedDisplayActionAdapter vbdaa)
    {
        this.vbdaa = vbdaa;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#setData(java.lang.Object)
     */
    public void setData(final Object dataObj)
    {
        viewBasedPanel.setData(dataObj);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#isEditMode()
     */
    public boolean isEditMode()
    {
        return viewBasedPanel.isEditMode();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.db.ViewBasedDisplayIFace#shutdown()
     */
    public void shutdown()
    {
        setVisible(false);
        viewBasedPanel.shutdown();
    }


}

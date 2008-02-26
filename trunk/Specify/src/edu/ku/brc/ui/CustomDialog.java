/*
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
package edu.ku.brc.ui;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.specify.ui.HelpMgr;

/**
 * CustomDialog is designed to enable developers to "customize" a dialog's contents and 
 * have the Dialog's buttons automatically created with any label on them.
 * The dialog can have up to four different button: OK, Cancel, Apply, and Help.
 * 
 * You can use the class two ways. Create one directly and pass in the "contents" pane, or
 * derive your own class and override the "createUI" method. Note: you should/must call super.createUI
 * as the first line (or nearly the first line), this call will create all your buttons. Any changes to the labels
 * of the four btns should be done before the call to createUI.
 * 
 * Also note that the setVisible call will automatically call createUI, but it is OK to call it manually
 * yourself before the setVisible. BUT! if you do you may need to call "pack" before call setVisible.
 * 
 * If you override createUI and want to set your own contents pane internally do it like this:<br>
 * <pre>
 * contentPanel = myNewPanel;
 * mainPanel.add(contentPanel, BorderLayout.CENTER);
 * </pre>
 * 
 * IMPORTANT: The setVisible method will register and unregister the dialog with the UIRegistry window stack.
 * 
 * @code_status Complete
 * 
 * @author rods
 * 
 */
@SuppressWarnings("serial")
public class CustomDialog extends JDialog
{
    // Static Data Members
    public static final int NONE_BTN           = 0;
    public static final int OK_BTN             = 1;
    public static final int CANCEL_BTN         = 2;
    public static final int HELP_BTN           = 4;
    public static final int APPLY_BTN          = 8;
    
    public static final int OKCANCEL           = OK_BTN | CANCEL_BTN;
    public static final int OKHELP             = OK_BTN | HELP_BTN;
    public static final int OKCANCELHELP       = OK_BTN | CANCEL_BTN | HELP_BTN;
    public static final int OKCANCELAPPLY      = OK_BTN | CANCEL_BTN | APPLY_BTN;
    public static final int OKCANCELAPPLYHELP  = OK_BTN | CANCEL_BTN | APPLY_BTN | HELP_BTN;
    
    // Data Members
    protected JButton           okBtn            = null;
    protected JButton           cancelBtn        = null;
    protected JButton           helpBtn          = null;
    protected JButton           applyBtn         = null;
    
    // Button Labels
    protected String            okLabel          = null;
    protected String            cancelLabel      = null;
    protected String            helpLabel        = null;
    protected String            applyLabel       = null;

    protected ImageIcon         icon             = null;
    protected boolean           isCancelled      = true;
    protected int               btnPressed       = NONE_BTN;
    
    protected JPanel            mainPanel;

    // Needed for delayed building of Dialog
    protected int               whichBtns        = OK_BTN;
    protected int               defaultBtn       = OK_BTN;
    protected String            helpContext      = null;
    protected Component         contentPanel     = null;
    
    /**
     * Constructor.
     * 
     * @param frame parent frame
     * @param title the title of the dialog
     * @param isModal whether or not it is model
     * @param contentPanel the contentpane
     * @throws HeadlessException
     */
    public CustomDialog(final Frame     frame, 
                        final String    title, 
                        final boolean   isModal,
                        final Component contentPanel) throws HeadlessException
    {
        this(frame, title, isModal, OK_BTN | CANCEL_BTN, contentPanel);
    }

    /**
     * @param frame parent frame
     * @param title the title of the dialog
     * @param isModal whether or not it is model
     * @param whichBtns which button to use for the dialog
     * @param contentPanel the contentPanel
     * @throws HeadlessException
     */
    public CustomDialog(final Frame     frame, 
                        final String    title, 
                        final boolean   isModal,
                        final int       whichBtns,
                        final Component contentPanel) throws HeadlessException
    {
        super(frame, title, isModal);
        
        this.whichBtns    = whichBtns;
        this.contentPanel = contentPanel;
    }

    /**
     * @param frame parent frame
     * @param title the title of the dialog
     * @param isModal whether or not it is model
     * @param whichBtns which button to use for the dialog
     * @param contentPanel the contentPanel
     * @throws HeadlessException
     */
    public CustomDialog(final Frame     frame, 
                        final String    title, 
                        final boolean   isModal,
                        final int       whichBtns,
                        final Component contentPanel,
                        final int defaultBtn) throws HeadlessException
    {
        super(frame, title, isModal);
        
        this.whichBtns    = whichBtns;
        this.contentPanel = contentPanel;
        this.defaultBtn = defaultBtn;
    }

    /**
     * @param frame parent frame
     * @param title the title of the dialog
     * @param isModal whether or not it is model
     * @param whichBtns which button to use for the dialog
     * @param contentPanel the contentPanel
     * @throws HeadlessException
     */
    public CustomDialog(final Dialog    dialog, 
                        final String    title, 
                        final boolean   isModal,
                        final int       whichBtns,
                        final Component contentPanel) throws HeadlessException
    {
        super(dialog, title, isModal);
        
        this.whichBtns    = whichBtns;
        this.contentPanel = contentPanel;
    }

    /**
     * gets JButton corresponding to defaultBtn value.
     */
    protected JButton findDefaultBtn()
    {
        if (defaultBtn == CANCEL_BTN)
        {
            return cancelBtn;
        }
        if (defaultBtn == HELP_BTN)
        {
            return helpBtn;
        }
        if (defaultBtn == APPLY_BTN)
        {
            return applyBtn;
        }
        return okBtn;
    }
    
    /**
     * @return the main panel.
     */
    protected JPanel createMainPanel()
    {
        return new JPanel(new BorderLayout());
    }
    
    /**
     * Create the UI for the dialog.
     */
    public void createUI()
    {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        mainPanel = createMainPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 2, 5, 2));

        if (contentPanel != null)
        {
            mainPanel.add(contentPanel, BorderLayout.CENTER);
        }

        // Bottom Button UI
        okBtn = new JButton(StringUtils.isNotEmpty(okLabel) ? okLabel : getResourceString("OK"));
        okBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                okButtonPressed();
            }
        });
        
        
        if ((whichBtns & CANCEL_BTN) == CANCEL_BTN)
        {
            cancelBtn = new JButton(StringUtils.isNotEmpty(cancelLabel) ? cancelLabel : getResourceString("Cancel"));
            cancelBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    cancelButtonPressed();
                }
            });
        }
        
        if ((whichBtns & HELP_BTN) == HELP_BTN)
        {
            helpBtn = new JButton(StringUtils.isNotEmpty(helpLabel) ? helpLabel : getResourceString("Help"));
            if (StringUtils.isNotEmpty(helpContext))
            {
                HelpMgr.registerComponent(helpBtn, helpContext);
            } else
            {
                helpBtn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                        helpButtonPressed();
                    }
                }); 
            }
        }
        
        if ((whichBtns & APPLY_BTN) == APPLY_BTN)
        {
            applyBtn = new JButton(StringUtils.isNotEmpty(applyLabel) ? applyLabel : getResourceString("Apply"));
            applyBtn.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    applyButtonPressed();
                }
            });
        }
        
        getRootPane().setDefaultButton(findDefaultBtn());
        
        JPanel bb;
        if (whichBtns == OK_BTN)
        {
            bb = ButtonBarFactory.buildOKBar(okBtn);
            
        } else if (whichBtns == OKCANCEL)
        {
            bb = ButtonBarFactory.buildOKCancelBar(okBtn, cancelBtn);
            
        } else if (whichBtns == OKCANCELAPPLY)
        {
            bb = ButtonBarFactory.buildOKCancelApplyBar(okBtn, cancelBtn, applyBtn);
            
        } else if (whichBtns == OKHELP)
        {
            bb = ButtonBarFactory.buildOKHelpBar(okBtn, helpBtn);
            
        } else if (whichBtns == OKCANCELHELP)
        {
            bb = ButtonBarFactory.buildOKCancelHelpBar(okBtn, cancelBtn, helpBtn);
            
        } else if (whichBtns == OKCANCELAPPLYHELP)
        {
            bb = ButtonBarFactory.buildOKCancelApplyHelpBar(okBtn, cancelBtn, applyBtn, helpBtn);
            
        } else
        {
            bb = ButtonBarFactory.buildOKBar(okBtn);
        }
        
        Component bbComp = bb;
        if (UIHelper.getOSType() == UIHelper.OSTYPE.MacOSX) // adjust for intruding resizer on Mac OS X
        {
            PanelBuilder    builder    = new PanelBuilder(new FormLayout("p:g,15px", "p"));
            CellConstraints cc         = new CellConstraints();
            builder.add(bb, cc.xy(1,1));
            
            bbComp = builder.getPanel();
        }

        mainPanel.add(bbComp, BorderLayout.SOUTH);


        setContentPane(mainPanel);
        
        pack();
        
        setLocationRelativeTo(this.getOwner());

    }
    
    public Component getContentPanel()
    {
        return contentPanel;
    }

    public void setContentPanel(Component contentPanel)
    {
        this.contentPanel = contentPanel;
    }

    /**
     * Performs cancel action.
     */
    protected void cancelButtonPressed()
    {
        isCancelled = true;
        btnPressed  = CANCEL_BTN;
        setVisible(false);
    }

    /**
     * Performs ok action.
     */
    protected void okButtonPressed()
    {
        isCancelled = false;
        btnPressed  = OK_BTN;
        setVisible(false);
    }

    /**
     * Performs help action.
     */
    protected void helpButtonPressed()
    {
        isCancelled = false;
        btnPressed  = HELP_BTN;
    }

    /**
     * Performs apply action.
     */
    protected void applyButtonPressed()
    {
        isCancelled = false;
        btnPressed  = APPLY_BTN;
    }

    /**
     * Returns whether it was cancelled.
     * @return whether it was cancelled
     */
    public boolean isCancelled()
    {
        return isCancelled;
    }

    /**
     * @param text
     */
    public void setOkLabel(final String text)
    {
        this.okLabel = text;
        
        if (okBtn != null)
        {
            okBtn.setText(okLabel);
        }
    }

    /**
     * @param text
     */
    public void setCancelLabel(final String text)
    {
        this.cancelLabel = text;

        if (cancelBtn != null)
        {
            cancelBtn.setText(cancelLabel);
        }
    }

    /**
     * @return
     */
    public int getBtnPressed()
    {
        return btnPressed;
    }

    /**
     * @param applyLabel
     */
    public void setApplyLabel(String applyLabel)
    {
        this.applyLabel = applyLabel;
        
        if (applyBtn != null)
        {
            applyBtn.setText(applyLabel);
        }
    }

    /**
     * @param helpContext
     */
    public void setHelpContext(String helpContext)
    {
        this.helpContext = helpContext;
    }

    /**
     * @param helpLabel
     */
    public void setHelpLabel(String helpLabel)
    {
        this.helpLabel = helpLabel;
        
        if (helpBtn != null)
        {
            helpBtn.setText(helpLabel);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Dialog#setVisible(boolean)
     */
    @Override
    public void setVisible(final boolean visible)
    {
        if (visible)
        {
            UIRegistry.pushWindow(this);
        } else
        {
            UIRegistry.popWindow(this);
        }
        
        if (okBtn == null && visible)
        {
            createUI();
        }
        
        UIHelper.centerWindow(this);
        super.setVisible(visible);
    }

    /**
     * @return the applyBtn
     */
    public JButton getApplyBtn()
    {
        return applyBtn;
    }

    /**
     * @return the cancelBtn
     */
    public JButton getCancelBtn()
    {
        return cancelBtn;
    }

    /**
     * @return the helpBtn
     */
    public JButton getHelpBtn()
    {
        return helpBtn;
    }

    /**
     * @return the okBtn
     */
    public JButton getOkBtn()
    {
        return okBtn;
    }
    
    /**
     * Method for derived classes to override.
     */
    public void cleanUp()
    {
        // no op
    }
}

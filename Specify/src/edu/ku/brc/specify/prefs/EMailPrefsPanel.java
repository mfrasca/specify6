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
package edu.ku.brc.specify.prefs;

import static edu.ku.brc.ui.UIHelper.createDuplicateJGoodiesDef;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.prefs.GenericPrefsPanel;
import edu.ku.brc.af.prefs.PrefsPanelIFace;
import edu.ku.brc.af.prefs.PrefsSavable;
import edu.ku.brc.helpers.EMailHelper;
import edu.ku.brc.helpers.Encryption;
import edu.ku.brc.helpers.SwingWorker;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.CommandListener;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.forms.validation.ValComboBox;
import edu.ku.brc.ui.forms.validation.ValPasswordField;

/**
 * Preference Panel for setting EMail Preferences.
 *
 * This also includes a method that kicks off a dialog on a thread to check to make sure all the email settings are correct.
 *
 * @code_status Alpha
 *
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class EMailPrefsPanel extends GenericPrefsPanel implements PrefsSavable, CommandListener, PrefsPanelIFace
{
    // Checker
    protected ImageIcon    checkIcon     = IconManager.getIcon("Checkmark", IconManager.IconSize.Std24);
    protected ImageIcon    exclaimIcon   = IconManager.getIcon("Error", IconManager.IconSize.Std24);
    protected ImageIcon    exclaimYWIcon = IconManager.getIcon("Warning", IconManager.IconSize.Std24);

    protected JDialog      checkerDialog = null;
    protected JLabel[]     checkerLabels;
    protected JLabel[]     checkerIcons;
    protected JButton      closeCheckerBtn;
    protected JProgressBar progressBar;
    protected JPanel       checkPanel;

    protected String testMessage = "Specify Test Message";
    
    protected EMailCheckerRunnable emailCheckerRunnable;


    /**
     * Constructor of the EMail setting panel.
     */
    public EMailPrefsPanel()
    {
        super();
        
        createUI();
    }

    /**
     * Create the UI for the panel
     */
    protected void createUI()
    {
        createForm("Preferences", "EMail");

        if (formView != null & form != null && form.getUIComponent() != null)
        {
            ValComboBox cbx = (ValComboBox)form.getCompById("accounttype");
            if (cbx.getComboBox().getSelectedIndex() == -1)
            {
                cbx.getComboBox().setSelectedIndex(0);
            }
            form.getValidator().validateForm();

            CommandDispatcher.register("EmailPref", this);
        }
    }

    /**
     * Sends a test mail message.
     * @return true if the message was sent ok, false if there was an error.
     */
    protected boolean simpleTestSettings()
    {
        // -- this use IDs instead of names
        String usernameStr     = (String)form.getDataFromUIComp("username");
        String passwordStr     = (String)form.getDataFromUIComp("password");
        String smtpStr         = (String)form.getDataFromUIComp("smtp");
        String emailStr        = (String)form.getDataFromUIComp("email");
        //String serverNameStr   = (String)form.getDataFromUIComp("servername");
        //String acctTypeStr     = (String)form.getDataFromUIComp("accounttype");
        //String localMailBoxStr = (String)form.getDataFromUIComp("localmailbox");
        
        //EMailHelper.AccountType acctType = EMailHelper.getAccountType(acctTypeStr);

        Component comp = form.getValidator().getComp("password");
        if (comp != null && comp instanceof ValPasswordField && ((ValPasswordField)comp).isEncrypted())
        {
            passwordStr = Encryption.decrypt(passwordStr);
        }

        String htmlMsg = "<html><body>" + testMessage + "</body></html>";
        return EMailHelper.sendMsg(smtpStr, usernameStr, passwordStr, emailStr, emailStr, testMessage, htmlMsg, EMailHelper.HTML_TEXT, null);
    }

    /**
     * Test to make the mailbox is present and we can read it and
     * then check to see if we can download messages
     */
    protected void testSettings()
    {
/*
        // -- this use IDs instead of names
        String usernameStr     = (String)form.getDataFromUIComp("username");
        String passwordStr     = (String)form.getDataFromUIComp("password");
        String smtpStr         = (String)form.getDataFromUIComp("smtp");
        String emailStr        = (String)form.getDataFromUIComp("email");
        String serverNameStr   = (String)form.getDataFromUIComp("servername");
        String acctTypeStr     = (String)form.getDataFromUIComp("accounttype");
        String localMailBoxStr = (String)form.getDataFromUIComp("localmailbox");
        
        EMailHelper.AccountType acctType = EMailHelper.getAccountType(acctTypeStr);

        Component comp = form.getValidator().getComp("password");
        if (comp != null && comp instanceof ValPasswordField && ((ValPasswordField)comp).isEncrypted())
        {
            passwordStr = Encryption.decrypt(passwordStr);
        }

        boolean checkSendMail = true;
        if (checkSendMail)
        {
            String htmlMsg = "<html><body>" + testMessage + "</body></html>";
            if (!EMailHelper.sendMsg(smtpStr, usernameStr, passwordStr, emailStr, emailStr, testMessage, htmlMsg, EMailHelper.HTML_TEXT, null))
            {
                // XXX Get response error message from Helper and display it.
                //JOptionPane.showMessageDialog(UIRegistry.getTopWindow(), "Error Sending EMail");
                checkerIcons[0].setIcon(exclaimIcon);
                checkerLabels[0].setText(EMailHelper.getLastErrorMsg());
            } else
            {
                checkerIcons[0].setIcon(checkIcon);
            }
        } else
        {
            checkerIcons[0].setIcon(checkIcon);
        }

        // Open Local Box if POP
        if (acctType == EMailHelper.AccountType.POP3)
        {
            try
            {
                Properties props = new Properties();
                Session session = Session.getDefaultInstance(props);

                Store store = session.getStore(new URLName("mstor:"+localMailBoxStr));
                store.connect();

                java.util.List<javax.mail.Message> msgList = new java.util.ArrayList<javax.mail.Message>();
                if (EMailHelper.getMessagesFromInbox(store, msgList))
                {
                    String msgStr = String.format(getResourceString("messagewerefound"), new Object[] {(msgList.size())});
                    checkerIcons[1].setIcon(checkIcon);
                    checkerLabels[1].setText(msgStr);
                    msgList.clear();
                } else
                {
                    checkerIcons[1].setIcon(exclaimIcon);
                    checkerLabels[1].setText(EMailHelper.getLastErrorMsg());
                }

            } catch (Exception ex)
            {
                checkerIcons[1].setIcon(exclaimIcon);
                checkerLabels[1].setText(ex.toString());

                JOptionPane.showMessageDialog(UIRegistry.getTopWindow(), ex.toString());
                ex.printStackTrace();
            }


        } else if (acctType == EMailHelper.AccountType.IMAP)
        {
            checkerIcons[1].setIcon(checkIcon);

        } else
        {
            throw new RuntimeException("Unknown Account Type ["+acctTypeStr+"] must be POP3 or IMAP"); // XXX FIXME
        }

        // Try to download message from pop account
        try
        {
            Properties props = System.getProperties();
            //props.put("mail.smtp.host", serverName.getText());
            //props.put( "mail.smtp.auth", "true");

            boolean foundMsg = false;

            if (acctType == EMailHelper.AccountType.POP3)
            {
                //props.put("mail.pop3.host", serverName);
                //props.put("mail.pop3.user", username);

                Session session = Session.getInstance(props, null);
                Store store = session.getStore("pop3");
                store.connect(serverNameStr, usernameStr, passwordStr);

                java.util.List<javax.mail.Message> msgList = new java.util.ArrayList<javax.mail.Message>();
                if (EMailHelper.getMessagesFromInbox(store, msgList))
                {
                    String msgStr = String.format(getResourceString("messagewerefound"), new Object[] {(msgList.size())});
                    for (javax.mail.Message msg : msgList)
                    {
                        String subject = msg.getSubject();
                        if (subject != null && subject.indexOf(testMessage) != -1)
                        {
                            foundMsg = true;
                        }
                    }
                    checkerIcons[2].setIcon(checkIcon);
                    checkerLabels[2].setText(msgStr);
                    msgList.clear();
                    EMailHelper.closeAllMailBoxes();

                } else
                {
                    checkerIcons[2].setIcon(exclaimIcon);
                    checkerLabels[2].setText(EMailHelper.getLastErrorMsg());
                }
                checkerLabels[2].invalidate();

            } else if (acctType == EMailHelper.AccountType.IMAP)
            {
                Session session = Session.getInstance(props, null);
                Store store = session.getStore("imap");
                store.connect(serverNameStr, usernameStr, passwordStr);

                java.util.List<javax.mail.Message> msgList = new java.util.ArrayList<javax.mail.Message>();
                if (EMailHelper.getMessagesFromInbox(store, msgList))
                {
                    String msgStr = String.format(getResourceString("messagewerefound"), new Object[] {(msgList.size())});
                    for (javax.mail.Message msg : msgList)
                    {
                        String subject = msg.getSubject();
                        if (subject != null && subject.indexOf(testMessage) != -1)
                        {
                            foundMsg = true;
                        }
                    }
                    checkerIcons[2].setIcon(checkIcon);
                    checkerLabels[2].setText(msgStr);
                    msgList.clear();
                    EMailHelper.closeAllMailBoxes();

                } else
                {
                    checkerIcons[2].setIcon(exclaimIcon);
                    checkerLabels[2].setText(EMailHelper.getLastErrorMsg());
                }
                checkerLabels[2].invalidate();

            } else
            {
                String msgStr = "Unknown Account Type ["+acctTypeStr+"] must be POP3 or IMAP"; // XXX I18N
                checkerIcons[2].setIcon(exclaimIcon);
                checkerLabels[2].setText(msgStr);
                throw new RuntimeException(msgStr); // XXX FIXME
            }

            if (foundMsg)
            {
                checkerIcons[3].setIcon(checkIcon);
                checkerLabels[3].setText(getResourceString("fndtestmsg"));
            } else
            {
                checkerIcons[3].setIcon(exclaimIcon);
                checkerLabels[3].setText(getResourceString("nofndtestmsg"));
            }

        } catch (Exception ex)
        {
            checkerIcons[2].setIcon(exclaimIcon);
            checkerLabels[2].setText(ex.toString());

            ex.printStackTrace();
        }

        checkPanel.doLayout();
        progressBar.setVisible(false);

        checkerDialog.getContentPane().doLayout();
        checkerDialog.getContentPane().repaint();
        checkerDialog.setSize(checkerDialog.getPreferredSize());
*/
    }

    //--------------------------------------------------------------------
    // CommandListener Interface
    //--------------------------------------------------------------------
    public void doCommand(CommandAction cmdAction)
    {
        startEMailSettingsTest();
    }

    //--------------------------------------------------------------------
    // PrefsSavable Interface
    //--------------------------------------------------------------------

    /**
     * This creates a dialog and start a thread to check to make sure all the email settings work.
     */
    protected void startEMailSettingsTest()
    {

        String rowDef = createDuplicateJGoodiesDef("p","4dlu", 6);
        PanelBuilder    builder    = new PanelBuilder(new FormLayout("p, 2dlu, p", rowDef));
        CellConstraints cc         = new CellConstraints();

        int row = 1;
        int col = 1;

        builder.addSeparator(getResourceString("checkingemailsettings"), cc.xyw(col,row,3));
        row += 2;

        //String[] labels = {getResourceString("chksendingmail"), getResourceString("chkmailbox"),
        //        getResourceString("chkgetmail"), getResourceString("chkforsentmsg")};
        String[] labels = {getResourceString("chksendingmail")};
        checkerIcons  = new JLabel[labels.length];
        checkerLabels = new JLabel[labels.length];
        for (int i=0;i<labels.length;i++)
        {
            builder.add(checkerIcons[i] = new JLabel(exclaimYWIcon), cc.xy(col,row));
            builder.add(checkerLabels[i] = UIHelper.createLabel(labels[i]), cc.xy(col+2,row));
            row += 2;
        }

        col = 1;
        builder.add(progressBar = new JProgressBar(0,100), cc.xyw(col,row,3));
        progressBar.setIndeterminate(true);
        row += 2;

        builder.getPanel().setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        JPanel panel = new JPanel(new BorderLayout());
        checkPanel = builder.getPanel();
        panel.add(builder.getPanel(), BorderLayout.CENTER);

        builder = new PanelBuilder(new FormLayout("c:p:g", "c:p:g"));
        closeCheckerBtn = UIHelper.createButton(getResourceString("CLOSE"));
        builder.add(closeCheckerBtn, cc.xy(1,1));
        panel.add(builder.getPanel(), BorderLayout.SOUTH);

        closeCheckerBtn.addActionListener(new ActionListener()
                {
            public void actionPerformed(ActionEvent e)
            {
                checkerDialog.setVisible(false);
                emailCheckerRunnable = null;
            }
        });

        panel.doLayout();
        checkPanel.doLayout();
        builder.getPanel().doLayout();

        checkerDialog = new JDialog();
        checkerDialog.setModal(true);

        checkerDialog.setContentPane(panel);
        checkerDialog.pack();
        checkerDialog.doLayout();
        //checkerDialog.setPreferredSize(checkerDialog.getPreferredSize());
        checkerDialog.setSize(checkerDialog.getPreferredSize());

        emailCheckerRunnable = new EMailCheckerRunnable(checkerDialog);
        emailCheckerRunnable.start();

        UIHelper.centerAndShow(checkerDialog);
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.af.prefs.GenericPrefsPanel#getHelpContext()
     */
    @Override
    public String getHelpContext()
    {
        return "PrefsEMail";
    }
    
    //----------------------------------------------------------------------------
    // Runnable to check for the email settings
    //----------------------------------------------------------------------------



    public class EMailCheckerRunnable extends SwingWorker
    {
        protected JDialog parentDlg;
        protected boolean status = false;

        /**
         * Constructs a an object to execute an SQL staement and then notify the listener
         */
        public EMailCheckerRunnable(final JDialog parentDlg)
        {
            this.parentDlg = parentDlg;
        }

        public Object construct()
        {
            status = simpleTestSettings();
            return null;
        }

        //Runs on the event-dispatching thread.
        public void finished()
        {
            if (!status)
            {
                // XXX Get response error message from Helper and display it.
                UIRegistry.showError("Error Sending EMail"); // XXX I18N
                
            } else
            {
                JOptionPane.showMessageDialog(UIRegistry.getTopWindow(), "Message Sent\nCheck your mailbox to see if it worked.");
            }
            parentDlg.setVisible(false);
            parentDlg.dispose();
        }
    }
}

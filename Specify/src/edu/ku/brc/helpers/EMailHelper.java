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
package edu.ku.brc.helpers;

import static edu.ku.brc.ui.UIRegistry.getResourceString;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.sun.mail.smtp.SMTPTransport;

import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.ui.UIRegistry;

/**
 * Sends an email with optional attachment

 * @code_status Complete
 **
 * @author rods
 */

public class EMailHelper
{
    private static final Logger log  = Logger.getLogger(EMailHelper.class);

    public enum AccountType {Unknown, POP3, IMAP}

    public static final String POP3_STR   = "POP3";
    public static final String IMAP_STR   = "IMAP";
    public static final String PLAIN_TEXT = "text/plain";
    public static final String HTML_TEXT  = "text/html";

    protected static final EMailHelper instance     = new EMailHelper();

    // Data Members
    protected String            lastErrorMsg = "";
    protected boolean           isDebugging  = true;
    protected List<MailBoxInfo> mailBoxCache = new ArrayList<MailBoxInfo>();

    /**
     * Default Constructor.
     *
     */
    public EMailHelper()
    {
    }

    /**
     * Send an email. Also sends it as a gmail if applicable, and does password checking.
     * @param host host of SMTP server
     * @param uName username of email account
     * @param pWord password of email account
     * @param fromEMailAddr the email address of who the email is coming from typically this is the same as the user's email
     * @param toEMailAddr the email addr of who this is going to
     * @param subject the Textual subject line of the email
     * @param bodyText the body text of the email (plain text???)
     * @param fileAttachment and optional file to be attached to the email
     * @return true if the msg was sent, false if not
     */
    public static boolean sendMsg(final String host,
                                  final String uName,
                                  final String pWord,
                                  final String fromEMailAddr,
                                  final String toEMailAddr,
                                  final String subject,
                                  final String bodyText,
                                  final String mimeType,
                                  final File   fileAttachment)
    {
        String userName = uName;
        String password = pWord;
        
        if (isGmailEmail())
        {
            return sendMsgAsGMail(host, userName, password, fromEMailAddr, toEMailAddr, subject, bodyText, mimeType, fileAttachment);
        }
        
        Boolean fail = false;
        ArrayList<String> userAndPass = new ArrayList<String>();
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put( "mail.smtp.auth", "true");

        Session session = Session.getInstance(props, null);

        session.setDebug(instance.isDebugging);
        if (instance.isDebugging)
        {
            log.debug("Host:     " + host);
            log.debug("UserName: " + userName);
            log.debug("Password: " + password);
            log.debug("From:     " + fromEMailAddr);
            log.debug("To:       " + toEMailAddr);
            log.debug("Subject:  " + subject);
        }


        try
        {
            // create a message
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(fromEMailAddr));
            if (toEMailAddr.indexOf(",") > -1)
            {
                StringTokenizer st = new StringTokenizer(toEMailAddr, ",");
                InternetAddress[] address = new InternetAddress[st.countTokens()];
                int i = 0;
                while (st.hasMoreTokens())
                {
                    String toStr = st.nextToken().trim();
                    address[i++] = new InternetAddress(toStr);
                }
                msg.setRecipients(Message.RecipientType.TO, address);
            } else
            {
                InternetAddress[] address = {new InternetAddress(toEMailAddr)};
                msg.setRecipients(Message.RecipientType.TO, address);
            }
            msg.setSubject(subject);
            
            //msg.setContent( aBodyText , "text/html;charset=\"iso-8859-1\"");

            // create the second message part
            if (fileAttachment != null)
            {
                // create and fill the first message part
                MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setContent(bodyText, mimeType);//"text/html;charset=\"iso-8859-1\"");
                //mbp1.setContent(bodyText, "text/html;charset=\"iso-8859-1\"");

                MimeBodyPart mbp2 = new MimeBodyPart();

               // attach the file to the message
                FileDataSource fds = new FileDataSource(fileAttachment);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());

                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);

                // add the Multipart to the message
                msg.setContent(mp);

            } else
            {
                // add the Multipart to the message
                msg.setContent(bodyText, mimeType);
            }


            // set the Date: header
            msg.setSentDate(new Date());

            // send the message
            int cnt = 0;
            do
            {
                cnt++;
                SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
                try {
                    t.connect(host, userName, password);
    
                    t.sendMessage(msg, msg.getAllRecipients());
                    
                    fail = false;
    
                } catch (MessagingException mex)
                {
                    instance.lastErrorMsg = mex.toString();
                    
                    Exception ex = null;
                    if ((ex = mex.getNextException()) != null) 
                    {
                      ex.printStackTrace();
                      instance.lastErrorMsg = instance.lastErrorMsg + ", " + ex.toString();
                    }
                    
                    //wrong username or password, get new one
                    if (mex.toString().equals("javax.mail.AuthenticationFailedException"))
                    {
                        fail = true;
                        userAndPass = askForUserAndPassword((Frame)UIRegistry.getTopWindow());
                        
                        if (userAndPass == null)
                        {//the user is done
                            return false;
                        }
                        userName = userAndPass.get(0);
                        password = userAndPass.get(1);
                    }
                } finally
                {
                     log.debug("Response: " + t.getLastServerResponse());
                     t.close();
                }
                
            } while (fail && cnt < 6);

        } catch (MessagingException mex)
        {
            instance.lastErrorMsg = mex.toString();
            
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) 
            {
              ex.printStackTrace();
              instance.lastErrorMsg = instance.lastErrorMsg + ", " + ex.toString();
            }
            return false;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        if (fail)
        {
            return false;
        }//else
        
        return true;
    }
    
    /**
     * Asks for a password.
     * @param topframe the parent frame
     * @return the password
     */
    public static String askForPassword(final Frame topframe)
    {
        PanelBuilder    builder   = new PanelBuilder(new FormLayout("p,2px,p", "p,2px,p"));
        CellConstraints cc        = new CellConstraints();
        JLabel          label     = new JLabel(getResourceString("password")+":", SwingConstants.RIGHT);
        JPasswordField  passField = new JPasswordField(25);
        JCheckBox       savePassword = new JCheckBox(getResourceString("SAVE_PASSWORD"));

        builder.add(label, cc.xy(1,1));
        builder.add(passField, cc.xy(3,1));
        builder.add(savePassword, cc.xy(3,3));
        JOptionPane.showConfirmDialog(topframe, builder.getPanel(), 
                getResourceString("PASSWORD_TITLE"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
       
        
        String passwordText = new String(passField.getPassword());
        if (savePassword.isSelected())
        {
            AppPreferences appPrefs = AppPreferences.getRemote();
            if (StringUtils.isNotEmpty(passwordText))
            {
                appPrefs.put("settings.email.password", Encryption.encrypt(passwordText));
            }
        }

        return passwordText;
    } 
   
    /**
     * Asks for a username and password.
     * @param topframe the parent frame
     * @return the list of user[index 1] and password[index 2], or 'null' if cancel was pressed
     */
    public static ArrayList<String> askForUserAndPassword(final Frame topframe)
    {
        ArrayList<String> userAndPass = new ArrayList<String>();
        //get remote prefs
        AppPreferences remotePrefs = AppPreferences.getRemote();
        //get remote password
        String remoteUsername = remotePrefs.get("settings.email.username",null );
        
        PanelBuilder    builder   = new PanelBuilder(new FormLayout("p,2px,p", "p,2px,p,2px,p"));
        CellConstraints cc        = new CellConstraints();
        JLabel          plabel    = new JLabel(getResourceString("password")+":", SwingConstants.RIGHT);
        JLabel          ulabel    = new JLabel(getResourceString("username")+":", SwingConstants.RIGHT);
        JPasswordField  passField = new JPasswordField(25);
        JTextField      userField = new JTextField(remoteUsername, 25);
        JCheckBox       savePassword = new JCheckBox(getResourceString("SAVE_PASSWORD"));

        
        builder.add(ulabel, cc.xy(1,1));
        builder.add(userField, cc.xy(3,1));
        builder.add(plabel, cc.xy(1,3));
        builder.add(passField, cc.xy(3,3));
        builder.add(savePassword, cc.xy(3,5));
        
        Integer option = JOptionPane.showConfirmDialog(topframe, builder.getPanel(), 
                getResourceString("PASSWORD_TITLE"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        String passwordText = new String(passField.getPassword());
        String userText     = new String(userField.getText());
        
        if (savePassword.isSelected())
        {
            
            if (StringUtils.isNotEmpty(passwordText))
            {
                remotePrefs.put("settings.email.password", Encryption.encrypt(passwordText));
            }
        }
        
        if (option == JOptionPane.CANCEL_OPTION)
        {
            return null;
        }//else
        
        userAndPass.add(0, userText);
        userAndPass.add(1, passwordText);
        return userAndPass;
    } 
    
    

    /**
     * Returns whether all the email prefs needed for sending mail have been filled in.
     * @return whether all the email prefs needed for sending mail have been filled in.
     */
    public static boolean isEMailPrefsOK(final Hashtable<String, String> emailPrefs)
    {
        AppPreferences appPrefs       = AppPreferences.getRemote();
        boolean        allOK          = true;
        String[]       emailPrefNames = { "servername", "username", "password", "email"};
        
        for (String pName : emailPrefNames)
        {
            String key   = "settings.email."+pName;
            String value = appPrefs.get(key, "");
            if (StringUtils.isNotEmpty(value) || pName.equals("password"))
            {
                emailPrefs.put(pName, value);
                
            } else
            {
                log.info("Key["+key+"] is empty");
                allOK = false;
                
                // XXX For Demo
                if (true)
                {
                    emailPrefs.put("accountname", "IMAP");
                    emailPrefs.put("servername",  "imap.ku.edu");
                    emailPrefs.put("username",    "rods");
                    emailPrefs.put("password",    appPrefs.get("settings.email.password", ""));
                    emailPrefs.put("email",       "rods@ku.edu");
                    for (String n : emailPrefs.keySet())
                    {
                        appPrefs.put("settings.email."+n, emailPrefs.get(n));
                    }
                    allOK = true;
                }
                break;
            }
        }
        return allOK;
    }

    /**
     * Returns true if the account type string is "POP3".
     * @param acctType the account type string
     * @return Returns true if the account type string is "POP3"
     */
    public static AccountType getAccountType(final String acctType)
    {
        if (acctType != null)
        {
            try
            {
                return AccountType.valueOf(acctType);
            } catch (Exception ex)
            {
                return AccountType.Unknown;
            }
        }
        return AccountType.Unknown;
    }

    /**
     * Returns true if the account type string is "POP3".
     * @param acctType the account type string
     * @return Returns true if the account type string is "POP3"
     */
    public static boolean isIMAP(final String acctType)
    {
        return acctType != null && acctType.equals(IMAP_STR);
    }

    /**
     * Returns whether the email settings have been filled in (but this doesn't indicate whether they will actually work).
     * @param usernameStr username
     * @param passwordStr password
     * @param emailStr email
     * @param smtpStr smtp
     * @param serverNameStr server name
     * @param acctTypeStr account type (POP3, IMAP)
     * @param localMailBoxStr URL location of mailbox (can be a file URL)
     * @return Returns whether the email settings have been filled in (but this doesn't indicate whether they will actually work)
     */
    public static boolean hasEMailSettings(final String usernameStr,
                                           final String passwordStr,
                                           final String emailStr,
                                           final String smtpStr,
                                           final String serverNameStr,
                                           final String acctTypeStr,
                                           final String localMailBoxStr)
    {
        EMailHelper.AccountType acctType = EMailHelper.getAccountType(acctTypeStr);

        return acctType != AccountType.Unknown &&
               isNotEmpty(usernameStr) && isNotEmpty(passwordStr) && isNotEmpty(emailStr) &&
               isNotEmpty(smtpStr) && isNotEmpty(serverNameStr) &&
               (acctType == AccountType.IMAP || acctType == AccountType.POP3 || isNotEmpty(localMailBoxStr));
    }

    /**
     * Returns whether the email settings have been filled in (but this doesn't indicate whether they will actually work).
     * @return Returns whether the email settings have been filled in (but this doesn't indicate whether they will actually work)
     */
    public static boolean hasEMailSettings()
    {
        String usernameStr     = AppPreferences.getRemote().get("settings.email.username", null);
        String passwordStr     = Encryption.decrypt(AppPreferences.getRemote().get("settings.email.password", null));
        String emailStr        = AppPreferences.getRemote().get("settings.email.email", null);
        String smtpStr         = AppPreferences.getRemote().get("settings.email.smtp", null);
        String serverNameStr   = AppPreferences.getRemote().get("settings.email.servername", null);
        String acctTypeStr     = AppPreferences.getRemote().get("settings.email.accounttype", null);
        String localMailBoxStr = AppPreferences.getRemote().get("settings.email.localmailbox", null);

        return hasEMailSettings(usernameStr, passwordStr, emailStr, smtpStr, serverNameStr, acctTypeStr, localMailBoxStr);
    }

    /**
     * Retrieves all the message from the INBOX.
     * @param store the store to retrieve them from
     * @param msgList the list to add them to
     * @return true if successful, false if their was an exception
     */
    public static boolean getMessagesFromInbox(final Store store,
                                               final java.util.List<javax.mail.Message> msgList)
    {
        try
        {
            Folder inbox = store.getDefaultFolder().getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);

            javax.mail.Message[] messages = inbox.getMessages();

            Collections.addAll(msgList, messages);

            MailBoxInfo mbx = instance.new MailBoxInfo(store, inbox);

            instance.mailBoxCache.add(mbx);

            return true;

        } catch (MessagingException mex)
        {
            instance.lastErrorMsg = mex.toString();
        }
        return false;
    }

    /**
     * Retrieves a list of all the available messages. For POP3 it checks the local mailbox and downloads any on the server.
     *  For IMAP it returns any in the INBOX.
     * @param msgList the list to be filled
     * @return true if not erros, false if an error occurred.
     */
    public static boolean getAvailableMsgs(java.util.List<javax.mail.Message> msgList)
    {
        boolean status = false; // assume it will fail

        msgList.clear();

        try
        {
            String usernameStr     = AppPreferences.getRemote().get("settings.email.username", null);
            String passwordStr     = Encryption.decrypt(AppPreferences.getRemote().get("settings.email.password", null));
            String emailStr        = AppPreferences.getRemote().get("settings.email.email", null);
            String smtpStr         = AppPreferences.getRemote().get("settings.email.smtp", null);
            String serverNameStr   = AppPreferences.getRemote().get("settings.email.servername", null);
            String acctTypeStr     = AppPreferences.getRemote().get("settings.email.accounttype", null);
            String localMailBoxStr = AppPreferences.getRemote().get("settings.email.localmailbox", null);

            EMailHelper.AccountType acctType = EMailHelper.getAccountType(acctTypeStr);

            if (!hasEMailSettings(usernameStr, passwordStr, emailStr, smtpStr, serverNameStr, acctTypeStr, localMailBoxStr))
            {
                JOptionPane.showMessageDialog(UIRegistry.getMostRecentWindow(), getResourceString("emailsetnotvalid"));
            }

            // Open Local Box if POP
            if (acctTypeStr.equals(getResourceString("pop3")))
            {
                try
                {
                    Properties props = new Properties();
                    Session session = Session.getDefaultInstance(props);

                    Store store = session.getStore(new URLName("mstor:"+localMailBoxStr));
                    store.connect();
                    status = getMessagesFromInbox(store, msgList); // closes everything

                } catch (Exception ex)
                {

                    instance.lastErrorMsg = ex.toString();
                    ex.printStackTrace();
                    status = false;
                }
            } else
            {
                throw new RuntimeException("Unknown Account Type ["+acctTypeStr+"] must be POP3 or IMAP"); // XXX FIXME
            }

            // Try to download message from pop account
            try
            {
                Properties props   = System.getProperties();
                Session    session = Session.getInstance(props, null);

                if (acctType == AccountType.POP3)
                {
                    Store store = session.getStore("pop3");
                    store.connect(serverNameStr, usernameStr, passwordStr);
                    status = getMessagesFromInbox(store, msgList); // closes everything

                } else if (acctType == AccountType.IMAP)
                {
                    Store store = session.getStore("imap");
                    store.connect(serverNameStr, usernameStr, passwordStr);
                    status = getMessagesFromInbox(store, msgList); // closes everything

                } else
                {
                    String msgStr = "Unknown Account Type ["+acctTypeStr+"] must be POP3 or IMAP"; // XXX I18N
                    instance.lastErrorMsg  = msgStr;
                    throw new RuntimeException(msgStr); // XXX FIXME
                }
            } catch (Exception ex)
            {
                instance.lastErrorMsg  = ex.toString();
                status = false;
            }

        } catch (Exception ex)
        {
            instance.lastErrorMsg  = ex.toString();
            status = false;
        }
        return status;
    }

    /**
     * Returns a the last error message string it typ[ically comes from an exception that we catch.
     * @return Returns a the last error message string it typ[ically comes from an exception that we catch
     */
    public static String getLastErrorMsg()
    {
        return instance.lastErrorMsg;
    }

    public static boolean isDebugging()
    {
        return instance.isDebugging;
    }

    public static void setDebugging(boolean isDebugging)
    {
        instance.isDebugging = isDebugging;
    }

    /**
     * Close all Message Store and their folders
     */
    public static void closeAllMailBoxes()
    {
        for (MailBoxInfo mbx : instance.mailBoxCache)
        {
            mbx.close();
        }
        instance.mailBoxCache.clear();
    }

    //---------------------------------------------------------
    // Inner classes
    //---------------------------------------------------------
    class MailBoxInfo
    {
        protected Store  store;
        protected Folder folder;

        public MailBoxInfo(final Store store, final Folder folder)
        {
            this.store  = store;
            this.folder = folder;
        }

        public Folder getFolder()
        {
            return folder;
        }

        public Store getStore()
        {
            return store;
        }

        public void close()
        {
            try
            {
                folder.close(false);
                store.close();
            } catch (MessagingException mex)
            {
                instance.lastErrorMsg = mex.toString();
            }
        }
    }

    /**
     * Send an email. Also sends it as a gmail if applicable, and does password checking.
     * @param host host of SMTP server
     * @param uName username of email account
     * @param pWord password of email account
     * @param fromEMailAddr the email address of who the email is coming from typically this is the same as the user's email
     * @param toEMailAddr the email addr of who this is going to
     * @param subject the Textual subject line of the email
     * @param bodyText the body text of the email (plain text???)
     * @param fileAttachment and optional file to be attached to the email
     * @return true if the msg was sent, false if not
     */
    public static boolean sendMsgAsGMail(final String host,
                                         final String uName,
                                         final String pWord,
                                         final String fromEMailAddr,
                                         final String toEMailAddr,
                                         final String subject,
                                         final String bodyText,
                                         final String mimeType,
                                         final File   fileAttachment)
    {
        String  userName = uName;
        String  password = pWord;
        Boolean fail     = false;
        
        ArrayList<String> userAndPass = new ArrayList<String>();
        
        Properties props = System.getProperties();
        
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        
        boolean usingSSL = false;
        if (usingSSL)
        {
            props.put("mail.smtps.port", "587");
            props.put("mail.smtp.starttls.enable", "true");
            
        }

        Session session = Session.getInstance(props, null);

        session.setDebug(instance.isDebugging);
        if (instance.isDebugging)
        {
            log.debug("Host:     " + host);
            log.debug("UserName: " + userName);
            log.debug("Password: " + password);
            log.debug("From:     " + fromEMailAddr);
            log.debug("To:       " + toEMailAddr);
            log.debug("Subject:  " + subject);
        }


        try
        {
            // create a message
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(fromEMailAddr));
            if (toEMailAddr.indexOf(",") > -1)
            {
                StringTokenizer st = new StringTokenizer(toEMailAddr, ",");
                InternetAddress[] address = new InternetAddress[st.countTokens()];
                int i = 0;
                while (st.hasMoreTokens())
                {
                    String toStr = st.nextToken().trim();
                    address[i++] = new InternetAddress(toStr);
                }
                msg.setRecipients(Message.RecipientType.TO, address);
            } else
            {
                InternetAddress[] address = {new InternetAddress(toEMailAddr)};
                msg.setRecipients(Message.RecipientType.TO, address);
            }
            msg.setSubject(subject);
            
            //msg.setContent( aBodyText , "text/html;charset=\"iso-8859-1\"");

            // create the second message part
            if (fileAttachment != null)
            {
                // create and fill the first message part
                MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setContent(bodyText, mimeType);//"text/html;charset=\"iso-8859-1\"");
                //mbp1.setContent(bodyText, "text/html;charset=\"iso-8859-1\"");

                MimeBodyPart mbp2 = new MimeBodyPart();

               // attach the file to the message
                FileDataSource fds = new FileDataSource(fileAttachment);
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());

                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);

                // add the Multipart to the message
                msg.setContent(mp);

            } else
            {
                // add the Multipart to the message
                msg.setContent(bodyText, mimeType);
            }


            // set the Date: header
            msg.setSentDate(new Date());

            // send the message
            int cnt = 0;
            do
            {
                cnt++;
                SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
                try 
                {
                    t.connect(host, userName, password);
    
                    t.sendMessage(msg, msg.getAllRecipients());
                    
                    fail = false;
                }
                catch (MessagingException mex)
                {
                    instance.lastErrorMsg = mex.toString();
                    
                    Exception ex = null;
                    if ((ex = mex.getNextException()) != null) 
                    {
                      ex.printStackTrace();
                      instance.lastErrorMsg = instance.lastErrorMsg + ", " + ex.toString();
                    }
                    
                    //wrong username or password, get new one
                    if (mex.toString().equals("javax.mail.AuthenticationFailedException"))
                    {
                        fail = true;
                        userAndPass = askForUserAndPassword((Frame)UIRegistry.getTopWindow());
                        
                        if (userAndPass == null)
                        {//the user is done
                            return false;
                        }
                        // else
                        //try again
                        userName = userAndPass.get(0);
                        password = userAndPass.get(1);
                    }
                }
                finally
                {
    
                     log.debug("Response: " + t.getLastServerResponse());
                     t.close();
                }
            } while (fail && cnt < 6);

        } catch (MessagingException mex)
        {
            instance.lastErrorMsg = mex.toString();

            //mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) 
            {
              ex.printStackTrace();
              instance.lastErrorMsg = instance.lastErrorMsg + ", " + ex.toString();
            }
            return false;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        if (fail){
            return false;
        }//else
        return true;
    }
    
    
   
    /*
    public static Boolean correctPassword(Hashtable<String, String> emailPrefs)
    {

        if (hasEMailSettings())
        {
            if (!isEMailPrefsOK(emailPrefs))
            {
                System.err.println("not everything is filled in");
                JOptionPane.showMessageDialog(UIRegistry.get(UIRegistry.TOPFRAME), 
                        getResourceString("NO_EMAIL_PREF_INFO"), 
                        getResourceString("NO_EMAIL_PREF_INFO_TITLE"), JOptionPane.WARNING_MESSAGE);
                
                return false;
            }
            
            
            //get remote prefs
            AppPreferences remotePrefs = AppPreferences.getRemote();
            //get remote password
            String remotePassword = Encryption.decrypt(remotePrefs.get("settings.email.password",null ));
            
            //get current password
            String userPassword = Encryption.decrypt(emailPrefs.get("password"));
            
            if (StringUtils.isBlank(userPassword) || !remotePassword.equals(userPassword))
            {
                System.out.println("try again...");
                userPassword = askForPassword((Frame)UIRegistry.get(UIRegistry.TOPFRAME));
            }
            if (remotePassword.equals(userPassword))
            {
                System.out.println("fine");
                return true;
            }
            
        
        }
        //else
        return false;
    }*/
    
   
    
    public static boolean isGmailEmail()
    {
        
         String smtpStr  = AppPreferences.getRemote().get("settings.email.smtp", null);
         
         if (StringUtils.isNotEmpty(smtpStr) && smtpStr.equals("smtp.gmail.com"))
         {
             return true;
         }
         //else
         return false;
    }
    
   
    
    
}

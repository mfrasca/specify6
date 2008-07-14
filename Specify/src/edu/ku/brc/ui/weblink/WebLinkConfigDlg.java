/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.ui.weblink;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.ui.AddRemoveEditPanel;
import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Apr 13, 2008
 *
 */
public class WebLinkConfigDlg extends CustomDialog
{
    protected JList              list;
    protected AddRemoveEditPanel itemsPanelADE;
    protected WebLinkMgr         wlMgr;
    protected boolean            hasChanged = false;
    protected boolean            isTableMode;
    
    protected DBTableInfo        tableInfo;
    
    /**
     * @throws HeadlessException
     */
    public WebLinkConfigDlg(final WebLinkMgr  wlMgr,
                            final DBTableInfo tableInfo,
                            final boolean     isTableMode) throws HeadlessException
    {
        super((Frame)UIRegistry.getTopWindow(), UIRegistry.getResourceString("WebLinkConfigDlg.WEB_LNK_EDT"), true, isTableMode ? OKHELP : OKCANCELHELP, null); // I18N //$NON-NLS-1$
        
        this.wlMgr       = wlMgr;
        this.tableInfo   = tableInfo;
        this.isTableMode = isTableMode;
        
        if (isTableMode)
        {
            okLabel = UIRegistry.getResourceString("CLOSE"); //$NON-NLS-1$
        }
        helpContext = "WEBLNK_EDITOR";
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#createUI()
     */
    @Override
    public void createUI()
    {
        super.createUI();
        
        ActionListener addItemAL = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addWebLink();
            }
        };
        
        ActionListener delItemAL = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                delWebLink();
            }
        };
        
        ActionListener editItemAL = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                editWebLink();
            }
        };
        
        PanelBuilder    pb = new PanelBuilder(new FormLayout("f:p:g", "p,2px,f:p:g,2px,p")); //$NON-NLS-1$ //$NON-NLS-2$
        CellConstraints cc = new CellConstraints();
        
        pb.add(UIHelper.createLabel(UIRegistry.getResourceString("WebLinkConfigDlg.WEB_LINKS"), SwingConstants.CENTER), cc.xy(1, 1)); //$NON-NLS-1$
        
        list = new JList(new DefaultListModel());
        JScrollPane sp = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pb.add(sp, cc.xy(1, 3));
        
        itemsPanelADE = new AddRemoveEditPanel(addItemAL, delItemAL, editItemAL);
        pb.add(itemsPanelADE, cc.xy(1, 5));
        
        for (WebLinkDef wld : wlMgr.getWebLinkDefs(isTableMode ? tableInfo : null))
        {
            ((DefaultListModel)list.getModel()).addElement(wld);
        }
        
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    enableUI();
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    editWebLink();
                }
                super.mouseClicked(e);
            }
            
        });
        
        pb.getPanel().setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        
        contentPanel = pb.getPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        pack();
        
        enableUI();
    }
    
    /**
     * @param wbName
     */
    public void setWebLink(final String wbName)
    {
        if (!isTableMode && wbName != null)
        {
            DefaultListModel model = (DefaultListModel)list.getModel();
            for (int i=0;i<model.size();i++)
            {
                WebLinkDef wld = (WebLinkDef)model.getElementAt(i);
                if (wld.getName().equals(wbName))
                {
                    list.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CustomDialog#okButtonPressed()
     */
    @Override
    protected void okButtonPressed()
    {
        if (hasChanged)
        {
            wlMgr.write();
        }
        
        super.okButtonPressed();
    }

    /**
     * 
     */
    protected void enableUI()
    {
        WebLinkDef wld = (WebLinkDef)list.getSelectedValue();
        boolean enabled = wld != null;
        if (enabled)
        {
            itemsPanelADE.getAddBtn().setEnabled(true);
            itemsPanelADE.getDelBtn().setEnabled(true);
            itemsPanelADE.getEditBtn().setEnabled(true);
            
        } else
        {
            itemsPanelADE.getAddBtn().setEnabled(true);
            itemsPanelADE.getDelBtn().setEnabled(false);
            itemsPanelADE.getEditBtn().setEnabled(false);
        }
    }
    
    /**
     * Add a new WebLinkDef to the Manager.
     */
    protected void addWebLink()
    {
        WebLinkDef    wld = new WebLinkDef();
        WebLinkArgDlg dlg = new WebLinkArgDlg(wld);
        dlg.setVisible(true);
        if (!dlg.isCancelled())
        {
            if (dlg.hasChanged())
            {
                if (tableInfo != null)
                {
                    if (isTableMode)
                    {
                        wld.setTableName(tableInfo.getName());
                    } else
                    {
                        wld.getUsedByList().add(new WebLinkUsedBy(tableInfo.getName()));  
                    }
                }
                
                ((DefaultListModel)list.getModel()).addElement(wld);
                list.setSelectedValue(wld, true);
                wlMgr.add(wld);
                hasChanged = true;
            } else
            {
                System.err.println("Not changed.");
            }
        }
    }

    /**
     * Remove a WebLinkDef from the manager.
     */
    protected void delWebLink()
    {
        WebLinkDef wld = (WebLinkDef)list.getSelectedValue();
        if (tableInfo != null)
        {
            for (WebLinkUsedBy wlub : wld.getUsedByList())
            {
                if (wlub.getTableName().equals(tableInfo.getName()))
                {
                    wld.getUsedByList().remove(wlub);
                    break;
                }
            }
        }
        ((DefaultListModel)list.getModel()).removeElement(wld);
        wlMgr.remove(wld);
        hasChanged = true;
    }

    /**
     * Show the editor for the WebLinkDef.
     */
    protected void editWebLink()
    {
        WebLinkDef    wld = (WebLinkDef)list.getSelectedValue();
        WebLinkArgDlg dlg = new WebLinkArgDlg(wld);
        dlg.setEdit(true);
        dlg.setVisible(true);
        
        if (!dlg.isCancelled())
        {
            hasChanged = true;
            wlMgr.setHasChanged(true);
        }
    }

    /**
     * @return the hasChanged
     */
    public boolean hasChanged()
    {
        return hasChanged;
    }
    
    /**
     * @return
     */
    public WebLinkDef getSelectedItem()
    {
        return (WebLinkDef)list.getSelectedValue();
    }
}

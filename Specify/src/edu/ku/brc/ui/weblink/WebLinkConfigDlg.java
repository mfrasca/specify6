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

import edu.ku.brc.dbsupport.DBFieldInfo;
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
    protected WebLinkMgr         wlMgr      = WebLinkMgr.getInstance();
    protected boolean            hasChanged = false;
    /**
     * @throws HeadlessException
     */
    public WebLinkConfigDlg(final DBTableInfo tableInfo,
                            final DBFieldInfo fieldInfo) throws HeadlessException
    {
        super((Frame)UIRegistry.getTopWindow(), "Web Link Editor", true, OKHELP, null); // I18N
        
        okLabel = UIRegistry.getResourceString("Close");
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
        
        PanelBuilder    pb = new PanelBuilder(new FormLayout("f:p:g", "p,2px,f:p:g,2px,p"));
        CellConstraints cc = new CellConstraints();
        
        pb.add(UIHelper.createLabel("Web Links", SwingConstants.CENTER), cc.xy(1, 1)); // I18N
        
        list = new JList(new DefaultListModel());
        JScrollPane sp = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pb.add(sp, cc.xy(1, 3));
        
        itemsPanelADE = new AddRemoveEditPanel(addItemAL, delItemAL, editItemAL);
        pb.add(itemsPanelADE, cc.xy(1, 5));
        
        for (WebLinkDef wld : wlMgr.getWebLinkDefs())
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
     * 
     */
    protected void addWebLink()
    {
        WebLinkDef wld = new WebLinkDef();
        WebLinkArgDlg dlg = new WebLinkArgDlg(wld);
        dlg.setVisible(true);
        if (!dlg.isCancelled())
        {
            ((DefaultListModel)list.getModel()).addElement(wld);
            list.setSelectedValue(wld, true);
            wlMgr.add(wld);
            hasChanged = true;
        }
    }

    /**
     * 
     */
    protected void delWebLink()
    {
        WebLinkDef wld = (WebLinkDef)list.getSelectedValue();
        ((DefaultListModel)list.getModel()).removeElement(wld);
        wlMgr.remove(wld);
        hasChanged = true;
    }

    /**
     * 
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

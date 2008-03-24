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
/**
 * 
 */
package edu.ku.brc.af.core.expresssearch;

import static edu.ku.brc.ui.UIHelper.createButton;
import static edu.ku.brc.ui.UIHelper.createCheckBox;
import static edu.ku.brc.ui.UIHelper.createI18NLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIRegistry;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Nov 27, 2007
 *
 */
public class RelatedTableInfoPanel extends JPanel
{
    protected SearchConfig config;
    
    protected JList                relatedTablesList;
    protected DefaultListModel     relatedTablesModel  = new DefaultListModel();
    protected JLabel               descLbl;
    protected JTextArea            relatedTableDescTA;
    
    protected JLabel               activeLbl;
    protected JCheckBox            activeCBX;
    protected JButton              selectAllBtn;
    protected JButton              deselectAllBtn;
    
    protected Vector<RelatedQuery> relatedQueriesInUse = new Vector<RelatedQuery>();
    
    protected boolean                                ignoreChanges = false;
    
    /**
     * 
     */
    public RelatedTableInfoPanel(final SearchConfig config)
    {
        this.config = config;
        
        createUI();
    }

    /**
     * 
     */
    protected void createUI()
    {
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        relatedTablesList = new JList(relatedTablesModel);
        TableNameRenderer nameRender = new TableNameRenderer(IconManager.IconSize.Std24);
        nameRender.setUseIcon("PlaceHolder");
        relatedTablesList.setCellRenderer(nameRender);
        
        CellConstraints cc = new CellConstraints();
        PanelBuilder    pb = new PanelBuilder(new FormLayout("p:g,8px,p,2px,f:p:g", 
                                                             "p,2px,p,2px,p,2px,t:p:g,f:p:g,2px,p"), this);
        //pb.add(createI18NLabel("ES_RELATED_ACTIVATE"), cc.xy(1, 1));
        
        JScrollPane sp = new JScrollPane(relatedTablesList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pb.add(createI18NLabel("ES_RELATED_SEARCHES"), cc.xy(1, 1));
        pb.add(sp, cc.xywh(1, 3, 1, 6));
        
        activeCBX = createCheckBox("                      ");
        pb.add(activeLbl = createI18NLabel("ES_RELATED_ACTIVATE", SwingConstants.RIGHT), cc.xy(3, 3));
        pb.add(activeCBX, cc.xy(5, 3));
        
        relatedTableDescTA = new JTextArea(8, 40);
        relatedTableDescTA.setEditable(false);
        relatedTableDescTA.setWrapStyleWord(true);
        relatedTableDescTA.setBackground(Color.WHITE);
        relatedTableDescTA.setLineWrap(true);
        sp = new JScrollPane(relatedTableDescTA, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pb.add(descLbl = createI18NLabel("ES_RELATED_DESC", SwingConstants.RIGHT), cc.xy(3, 5));
        pb.add(sp, cc.xy(5, 5));
        
        PanelBuilder btnPB = new PanelBuilder(new FormLayout("p,f:p:g,p", "p"));
        selectAllBtn   = createButton(UIRegistry.getResourceString("SelectAll"));
        deselectAllBtn = createButton(UIRegistry.getResourceString("DeselectAll"));
        btnPB.add(selectAllBtn, cc.xy(1, 1));
        btnPB.add(deselectAllBtn, cc.xy(3, 1));
        pb.add(btnPB.getPanel(), cc.xy(1, 10));
        
        activeCBX.addChangeListener(new ChangeListener() {

            /* (non-Javadoc)
             * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
             */
            public void stateChanged(ChangeEvent e)
            {
                RelatedQuery rq = (RelatedQuery)relatedTablesList.getSelectedValue();
                if (rq != null)
                {
                    rq.setIsActive(activeCBX.isSelected());
                    relatedTablesList.repaint();
                }
            }
        });
        
        relatedTablesList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    rtRelatedTableSelected();
                    updateEnableUI();
                }
            }
        });
        
        selectAllBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setAllActive(true);
            }
            
        });
        
        deselectAllBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                setAllActive(false);
            }
            
        });
        
        updateEnableUI();
    }
    
    /**
     * @param isActive
     */
    protected void setAllActive(final boolean isActive)
    {
        relatedTablesList.clearSelection();
        for (RelatedQuery rq : relatedQueriesInUse)
        {
            rq.setIsActive(isActive);
        }
        relatedTablesList.repaint();
    }
    
    /**
     * 
     */
    protected void updateEnableUI()
    {
        boolean enabled = relatedTablesList.getSelectedIndex() > -1;
        activeCBX.setEnabled(enabled);
        activeLbl.setEnabled(enabled);
        descLbl.setEnabled(enabled);
        relatedTableDescTA.setEnabled(enabled);
    }
    
    /**
     * 
     */
    protected void rtRelatedTableSelected()
    {
        RelatedQuery rq = (RelatedQuery)relatedTablesList.getSelectedValue();
        if (rq != null)
        {
            activeCBX.setText(rq.getPlainTitle());
            activeCBX.setSelected(rq.getIsActive());
            ExpressResultsTableInfo erti = ExpressSearchConfigCache.getSearchIdToTableInfoHash().get(rq.getId());
            if (erti != null)
            {
                relatedTableDescTA.setText(erti.getDescription());
            } else
            {
                relatedTableDescTA.setText("");
            }
        } else
        {
            relatedTableDescTA.setText("");
            activeCBX.setText("");
            activeCBX.setSelected(false);
        }
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#setVisible(boolean)
     */
    public void setVisible(final boolean vis)
    {
        RelatedQuery.setAddRealtedQueryTitle(!vis);
        RelatedQuery.setAddActiveTitle(vis);
        
        if (vis)
        {
            relatedQueriesInUse.clear();
            relatedTablesModel.clear();
            for (RelatedQuery rq : config.getRelatedQueries())
            {
                if (rq.isInUse())
                {
                    relatedQueriesInUse.add(rq);
                }
            }
            
            Collections.sort(relatedQueriesInUse);
            for (RelatedQuery rq : relatedQueriesInUse)
            {
                relatedTablesModel.addElement(rq);
            }
        }
        super.setVisible(vis);
    }
    
}

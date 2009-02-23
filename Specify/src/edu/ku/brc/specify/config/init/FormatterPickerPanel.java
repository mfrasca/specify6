/*
 * Copyright (C) 2008  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 *
 */
package edu.ku.brc.specify.config.init;

import static edu.ku.brc.ui.UIHelper.centerAndShow;
import static edu.ku.brc.ui.UIHelper.createButton;
import static edu.ku.brc.ui.UIHelper.createCheckBox;
import static edu.ku.brc.ui.UIHelper.createComboBox;
import static edu.ku.brc.ui.UIHelper.createI18NFormLabel;
import static edu.ku.brc.ui.UIHelper.createLabel;
import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.core.db.DBFieldInfo;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.db.DBTableInfo;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatter;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.af.ui.forms.formatters.UIFormatterEditorDlg;
import edu.ku.brc.af.ui.forms.formatters.UIFormatterListEdtDlg;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.CollectionObject;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Jan 18, 2008
 *
 */
public class FormatterPickerPanel extends BaseSetupPanel
{
    protected JCheckBox isNumericChk    = createCheckBox(getResourceString("IS_NUM_CHK"));
    protected JComboBox formatterCBX    = createComboBox(new DefaultComboBoxModel());
    //protected JButton   addFmtBtn       = createButton("...");
    protected JLabel    isNumericLbl    = createLabel("");
    
    protected boolean                     doingCatNums;
    protected List<UIFieldFormatterIFace> fmtList;
    protected UIFieldFormatterIFace       newFormatter = null;
    protected int                         newFmtInx    = 0;
    
    /**
     * @param nextBtn
     * @param doingCatNums
     */
    public FormatterPickerPanel(final JButton nextBtn, 
                                final boolean doingCatNums)
    {
        super("autonumber", nextBtn);
        
        this.doingCatNums = doingCatNums;
        
        formatterCBX.addActionListener(new ActionListener() {
            
            /* (non-Javadoc)
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = formatterCBX.getSelectedIndex();
                if (index > 0)
                {
                    UIFieldFormatterIFace fmt = fmtList.get(index-1);
                    if (fmt != null)
                    {
                        isNumericLbl.setText(getResourceString(fmt.isNumeric() ? "YES" : "NO"));
                    }
                }
            }
        });
        
        loadFormatCbx(null);

        CellConstraints cc = new CellConstraints();
        PanelBuilder    pb = new PanelBuilder(new FormLayout("p,4px,p,2px,p,f:p:g", "p,10px,p,4px,p"), this);
        int y = 1;
        String label = getResourceString(doingCatNums ? "CHOOSE_FMT_CAT" : "CHOOSE_FMT_ACC");
        pb.add(createLabel(label, SwingConstants.CENTER), cc.xywh(1, y, 6, 1)); 
        y +=2;
        
        JLabel lbl = createI18NFormLabel("FORMAT", SwingConstants.RIGHT);
        lbl.setFont(bold);
        pb.add(lbl, cc.xy(1, y));
        pb.add(formatterCBX, cc.xy(3, y));
        //pb.add(addFmtBtn,    cc.xy(5, y));
        y +=2;

        if (doingCatNums)
        {
            pb.add(createI18NFormLabel("IS_NUM", SwingConstants.RIGHT), cc.xy(1, y));
            pb.add(isNumericLbl, cc.xy(3, y));
            y +=2;
        }
        
        /*addFmtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addFieldFormatter();
            }
        });*/
        
        formatterCBX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (formatterCBX.getSelectedIndex() == newFmtInx)
                {
                    addFieldFormatter();
                }
            }
        });
    }
    
    /**
     * Launches the Field Formatter editor.
     */
    protected void addFieldFormatter()
    {
        DBFieldInfo fieldInfo;
        if (doingCatNums)
        {
            DBTableInfo ti = DBTableIdMgr.getInstance().getInfoById(CollectionObject.getClassTableId());
            fieldInfo = ti.getFieldByColumnName("CatalogNumber");
        } else
        {
            DBTableInfo ti = DBTableIdMgr.getInstance().getInfoById(Accession.getClassTableId());
            fieldInfo = ti.getFieldByColumnName("AccessionNumber");
        }
        
        newFormatter = new UIFieldFormatter();
        UIFormatterEditorDlg dlg = new UIFormatterEditorDlg(null, fieldInfo, newFormatter, true, false, UIFieldFormatterMgr.getInstance());
        dlg.setVisible(true);
        if (!dlg.isCancelled())
        {
            //loadFormatCbx(newFormatter);
        } else
        {
            newFormatter = null;
            
            //formatterCBX.setSelectedIndex(-1);
        }
        loadFormatCbx(newFormatter);
        
    }
    
    /**
     * 
     */
    protected void loadFormatCbx(final UIFieldFormatterIFace selectedFmt)
    {
        ((DefaultComboBoxModel)formatterCBX.getModel()).removeAllElements();
        
        fmtList = new Vector<UIFieldFormatterIFace>(UIFieldFormatterMgr.getInstance().getFormatterList(doingCatNums ? CollectionObject.class : Accession.class));
        if (newFormatter != null)
        {
            fmtList.add(newFormatter);
        }
        
        Collections.sort(fmtList, new Comparator<UIFieldFormatterIFace>() {
            @Override
            public int compare(UIFieldFormatterIFace o1, UIFieldFormatterIFace o2)
            {
                return o1.getName().compareTo(o2.getName());
            }
        });
        
        if (!doingCatNums)
        {
            ((DefaultComboBoxModel)formatterCBX.getModel()).addElement(getResourceString("NONE"));
            newFmtInx++;
        }
        ((DefaultComboBoxModel)formatterCBX.getModel()).addElement(getResourceString("Create"));

        for (UIFieldFormatterIFace fmt : fmtList)
        {
            ((DefaultComboBoxModel)formatterCBX.getModel()).addElement(fmt.getName());
        }

        if (selectedFmt != null)
        {
            formatterCBX.setSelectedItem(selectedFmt.getName());
            nextBtn.setEnabled(true);
        } else
        {
            formatterCBX.setSelectedIndex(-1);
            nextBtn.setEnabled(false);
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.BaseSetupPanel#getValues()
     */
    @Override
    public void getValues(final Properties props)
    {
        if (doingCatNums || formatterCBX.getSelectedIndex() > 0)
        {
            props.put(doingCatNums ? "catnumfmt" : "accnumfmt", formatterCBX.getSelectedItem());
        } else
        {
            props.remove(doingCatNums ? "catnumfmt" : "accnumfmt");
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.BaseSetupPanel#setValues(java.util.Hashtable)
     */
    @Override
    public void setValues(Properties values)
    {
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.BaseSetupPanel#isUIValid()
     */
    @Override
    public boolean isUIValid()
    {
        return true;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.BaseSetupPanel#updateBtnUI()
     */
    @Override
    public void updateBtnUI()
    {
    }
}

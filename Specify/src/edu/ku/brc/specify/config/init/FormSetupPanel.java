/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.config.init;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.af.ui.db.ViewBasedDisplayPanel;
import edu.ku.brc.af.ui.forms.FormDataObjIFace;
import edu.ku.brc.af.ui.forms.FormViewObj;
import edu.ku.brc.af.ui.forms.FormViewObj.FVOFieldInfo;
import edu.ku.brc.af.ui.forms.validation.UIValidator;
import edu.ku.brc.af.ui.forms.validation.ValidationListener;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.util.Pair;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Sep 27, 2008
 *
 */
public class FormSetupPanel extends ViewBasedDisplayPanel implements SetupPanelIFace, ValidationListener
{
    protected String           panelName;
    protected FormDataObjIFace dataObj;
    protected JButton          nextBtn;
    protected FormViewObj      formViewObj;
    protected JButton          valBtn;
    
    protected JPanel           enclosingPanel;
    
    /**
     * @param viewSetName
     * @param viewName
     * @param className
     * @param isEdit
     * @param options
     */
    public FormSetupPanel(final String  panelName,
                          final String  viewSetName,
                          final String  viewName,
                          final String  className,
                          final boolean isEdit,
                          final int     options,
                          final FormDataObjIFace dataObj,
                          final JButton nextBtn)
    {
        super(null, viewSetName, viewName, null, className, null, isEdit, true, null, null, options);
        
        this.panelName = panelName;
        this.dataObj   = dataObj;
        this.nextBtn   = nextBtn;
        
        formViewObj = multiView.getCurrentViewAsFormViewObj();
        formViewObj.getValidator().addValidationListener(this);
        
        valBtn = FormViewObj.createValidationIndicator(formViewObj.getUIComponent(), formViewObj.getValidator());
        multiView.getCurrentValidator().setValidationBtn(valBtn);
        
        setData(dataObj);
        
        multiView.setIsNewForm(true, true);
        //formViewObj.setHasNewData(true);
        formViewObj.getValidator().validateForm();
        
        nextBtn.setEnabled(false);
        
        CellConstraints cc = new CellConstraints();
        PanelBuilder    builder = new PanelBuilder(new FormLayout("f:p:g,p", "p,1px,p"));
        builder.add(this,   cc.xywh(1, 1, 2, 1));
        builder.add(valBtn, cc.xy(2, 3));
        
        enclosingPanel = builder.getPanel();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#getHelpContext()
     */
    @Override
    public String getHelpContext()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#doingNext()
     */
    @Override
    public void doingNext()
    {
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#doingPrev()
     */
    @Override
    public void doingPrev()
    {
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#getPanelName()
     */
    @Override
    public String getPanelName()
    {
        return panelName;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#getValues(java.util.Properties)
     */
    @Override
    public void getValues(Properties props)
    {
        multiView.getDataFromUI();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#isUIValid()
     */
    @Override
    public boolean isUIValid()
    {
        return formViewObj.getValidator().isFormValid();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#setValues(java.util.Properties)
     */
    @Override
    public void setValues(Properties values)
    {
        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#updateBtnUI()
     */
    @Override
    public void updateBtnUI()
    {
        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#getSummary()
     */
    @Override
    public List<Pair<String, String>> getSummary()
    {
        List<Pair<String, String>> list = new ArrayList<Pair<String, String>>();
        for (FVOFieldInfo info : formViewObj.getComps())
        {
            FVOFieldInfo lblInfo = formViewObj.getLabelInfoFor(info.getId());
            if (info.getComp() instanceof GetSetValueIFace)
            {
                list.add(new Pair<String, String>(lblInfo.getLabel(), ((GetSetValueIFace)info.getComp()).getValue().toString()));
            }
        }
        return list;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.config.init.SetupPanelIFace#getUIComponent()
     */
    @Override
    public Component getUIComponent()
    {
        return enclosingPanel;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.forms.validation.ValidationListener#wasValidated(edu.ku.brc.af.ui.forms.validation.UIValidator)
     */
    @Override
    public void wasValidated(UIValidator validator)
    {
        nextBtn.setEnabled(formViewObj.getValidator().isFormValid());
    }

}

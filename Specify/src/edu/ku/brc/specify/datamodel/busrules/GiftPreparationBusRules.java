/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
package edu.ku.brc.specify.datamodel.busrules;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.ku.brc.af.ui.forms.BaseBusRules;
import edu.ku.brc.af.ui.forms.FormViewObj;
import edu.ku.brc.af.ui.forms.MultiView;
import edu.ku.brc.af.ui.forms.TableViewObj;
import edu.ku.brc.af.ui.forms.Viewable;
import edu.ku.brc.af.ui.forms.validation.ValSpinner;
import edu.ku.brc.specify.datamodel.Gift;
import edu.ku.brc.specify.datamodel.GiftPreparation;
import edu.ku.brc.ui.CommandAction;
import edu.ku.brc.ui.CommandDispatcher;
import edu.ku.brc.ui.CommandListener;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Jan 29, 2007
 *
 */
public class GiftPreparationBusRules extends BaseBusRules implements CommandListener
{
    
    /**
     * 
     */
    public GiftPreparationBusRules()
    {
        super(GiftPreparation.class);
        
        CommandDispatcher.register(GiftBusRules.CMDTYPE, this);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.forms.BaseBusRules#initialize(edu.ku.brc.af.ui.forms.Viewable)
     */
    @Override
    public void initialize(Viewable viewableArg)
    {
        super.initialize(viewableArg);
        
        
        JButton newBtn = getNewBtn();
        if (newBtn != null)
        {
            // Remove all ActionListeners, there should only be one
            for (ActionListener al : newBtn.getActionListeners())
            {
                newBtn.removeActionListener(al);
            }
            
            newBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    MultiView giftMV = null;
                    if (viewable instanceof FormViewObj)
                    {
                        giftMV = formViewObj.getMVParent().getMultiViewParent();
                        formViewObj.getDataFromUI();
                        
                    } else if (viewable instanceof TableViewObj)
                    {
                        TableViewObj tblViewObj = (TableViewObj)viewable; 
                        giftMV = tblViewObj.getMVParent().getMultiViewParent();
                    }
                    
                    if (giftMV != null)
                    {
                        if (formViewObj != null)
                        {
                            formViewObj.getDataFromUI();
                        }
                        CommandDispatcher.dispatch(new CommandAction(GiftBusRules.CMDTYPE, "AddToGift", giftMV.getCurrentViewAsFormViewObj().getCurrentDataObj()));
                    }
                }
            });
        }
    }
    
    @Override
    public void afterFillForm(Object dataObj)
    {
        Component comp = formViewObj.getControlByName("quantity");
        if (comp instanceof ValSpinner && dataObj != null)
        {
            GiftPreparation  giftPrep   = (GiftPreparation)dataObj;
            
            //boolean    isNewObj         = giftPrep.getId() == null;
            ValSpinner quantity         = (ValSpinner)comp;
            
            // TODO I think this would be better if the Max Range 
            // was set to the available number of items.
            if (quantity != null)
            {
                quantity.setRange(0, giftPrep.getQuantity(), giftPrep.getQuantity());
            }
            
            //quantityReturned.setEnabled(!isNewObj);
            //int max = Math.max(loanPrep.getQuantity(), loanPrep.getQuantityReturned());
            //quantityReturned.setRange(0, max, loanPrep.getQuantityReturned());
            //formViewObj.getLabelFor(quantityReturned).setEnabled(!isNewObj);
        }

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.ui.forms.BaseBusRules#formShutdown()
     */
    @Override
    public void formShutdown()
    {
        super.formShutdown();
        
        CommandDispatcher.unregister(GiftBusRules.CMDTYPE, this);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.CommandListener#doCommand(edu.ku.brc.ui.CommandAction)
     */
    @Override
    public void doCommand(CommandAction cmdAction)
    {
        if (cmdAction.isType(GiftBusRules.CMDTYPE) && cmdAction.isAction("REFRESH_GIFT_PREPS"))
        {
            if (formViewObj != null)
            {
                MultiView giftMV = formViewObj.getMVParent().getMultiViewParent();
                if (giftMV != null)
                {
                    if (formViewObj.getValidator() != null)
                    {
                        Gift gift = (Gift)giftMV.getData();
                        formViewObj.setDataObj(gift.getGiftPreparations());
                        formViewObj.getValidator().setHasChanged(true);
                        formViewObj.getValidator().validateRoot();
                    }
                }

            } else if (viewable instanceof TableViewObj)
            {
                TableViewObj tvo = (TableViewObj)viewable;
                // Make sure the Loan form knows there is a change
                MultiView giftMV = tvo.getMVParent().getMultiViewParent();
                giftMV.getCurrentValidator().setHasChanged(true);
                giftMV.getCurrentValidator().validateRoot();
                
                // Refresh list in the grid
                tvo.refreshDataList();
            }
        }
    }
}

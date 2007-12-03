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
* Copyright (C) 2006  The University of Kansas
*
* [INSERT KU-APPROVED LICENSE TEXT HERE]
*
*/
package edu.ku.brc.specify.datamodel.busrules;

import static edu.ku.brc.ui.UIRegistry.getLocalizedMessage;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.specify.datamodel.Accession;
import edu.ku.brc.specify.datamodel.Loan;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.specify.datamodel.Shipment;
import edu.ku.brc.ui.forms.DraggableRecordIdentifier;
import edu.ku.brc.ui.forms.FormViewObj;
import edu.ku.brc.ui.forms.MultiView;
import edu.ku.brc.ui.forms.Viewable;

/**
 * Business rules for validating a Loan.
 * 
 * @code_status Complete
 *
 * @author rods
 *
 */
public class LoanBusRules extends AttachmentOwnerBaseBusRules
{  
    /**
     * Constructor.
     */
    public LoanBusRules()
    {
        super(Loan.class);
    }
    
    

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#fillForm(java.lang.Object, edu.ku.brc.ui.forms.Viewable)
     */
    @Override
    public void afterFillForm(final Object dataObj, final Viewable viewable)
    {
        if (viewable instanceof FormViewObj)
        {
            FormViewObj formViewObj = (FormViewObj)viewable;
            if (formViewObj.getDataObj() instanceof Loan)
            {
                MultiView mvParent = formViewObj.getMVParent();
                Loan      loan     = (Loan)formViewObj.getDataObj();
                boolean   isNewObj = MultiView.isOptionOn(mvParent.getOptions(), MultiView.IS_NEW_OBJECT);
                boolean   isEdit   = mvParent.isEditable();
    
                Component comp     = formViewObj.getControlByName("generateInvoice");
                if (comp instanceof JCheckBox)
                {
                    ((JCheckBox)comp).setVisible(isEdit);
                }
                comp = formViewObj.getControlByName("ReturnLoan");
                if (comp instanceof JButton)
                {
                    comp.setVisible(!isNewObj && isEdit);
                    comp.setEnabled(!loan.getIsClosed());
                }
            }
        }
    }



    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.AttachmentOwnerBaseBusRules#beforeSaveCommit(java.lang.Object, edu.ku.brc.dbsupport.DataProviderSessionIFace)
     */
    @Override
    public void beforeMerge(Object dataObj, DataProviderSessionIFace session)
    {
         Loan loan = (Loan)dataObj;
        
        System.out.println("beforeSaveCommit loanNum: "+loan.getLoanNumber());
        
        for (Shipment shipment : loan.getShipments())
        {
            if (shipment.getShipmentId() == null)
            {
                shipment.setShipmentNumber(loan.getLoanNumber());
            }
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.AttachmentOwnerBaseBusRules#beforeSaveCommit(java.lang.Object, edu.ku.brc.dbsupport.DataProviderSessionIFace)
     */
    @Override
    public boolean beforeSaveCommit(Object dataObj, DataProviderSessionIFace session) throws Exception
    {
        /*Loan loan = (Loan)dataObj;
        
        System.out.println("beforeSaveCommit loanNum: "+loan.getLoanNumber());
        
        for (Shipment shipment : loan.getShipments())
        {
            //if (shipment.getShipmentId() == null)
            //{
            String shipmentNum = shipment.getShipmentNumber();
            if (StringUtils.isEmpty(shipmentNum))
            {
                shipmentNum = loan.getLoanNumber();
                
            } else if (StringUtils.contains(shipmentNum, '#')) // XXX Need to check the formatter!
            {
                shipment.setShipmentNumber(loan.getLoanNumber());
            }
        }*/
        return super.beforeSaveCommit(dataObj, session);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#processBusiessRules(java.lang.Object)
     */
    public STATUS processBusinessRules(Object dataObj)
    {
        errorList.clear();
        
        if (dataObj == null || !(dataObj instanceof Loan))
        {
            return STATUS.Error;
        }
        
        /*
        Loan loan = (Loan)dataObj;
        
        for (LoanAgent loanAgent : loan.getLoanAgents())
        {
            Agent agent = loanAgent.getAgent();
            if (agent != null)
            {
                Set<Address> addr = agent.getAddresses();
                if (addr.size() == 0)
                {
                    errorList.add("The select agent you loaning to,\nneeds to have at least one address."); // Thsi shouldn't eveer happen
                    return STATUS.Error;
                }
                
            } else
            {
                errorList.add("Loan Agent is missing an Agent"); // Thsi shouldn't eveer happen
                return STATUS.Error;
            }     
        }
        */  
        return STATUS.OK;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#okToDelete(java.lang.Object)
     */
    public boolean okToEnableDelete(Object dataObj)
    {
        return true;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#deleteMsg(java.lang.Object)
     */
    public String getDeleteMsg(final Object dataObj)
    {
        if (dataObj instanceof Accession)
        {
            return getLocalizedMessage("LOAN_DELETED", ((Loan)dataObj).getLoanNumber());
        }
        // else
        return super.getDeleteMsg(dataObj);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#setObjectIdentity(java.lang.Object, edu.ku.brc.ui.DraggableIcon)
     */
    public void setObjectIdentity(final Object dataObj, final DraggableRecordIdentifier draggableIcon)
    {
        if (dataObj == null)
        {
            draggableIcon.setLabel("");
        }
        
        if (dataObj instanceof Loan)
        {
            Loan loan = (Loan)dataObj;
            
            draggableIcon.setLabel(loan.getLoanNumber());
            
            Object data = draggableIcon.getData();
            if (data == null)
            {
                RecordSet rs = new RecordSet();
                rs.initialize();
                rs.addItem(loan.getLoanId());
                data = rs;
                draggableIcon.setData(data);
                
            } else if (data instanceof RecordSetIFace)
            {
                RecordSetIFace rs = (RecordSetIFace)data;
                rs.getItems().clear();
                rs.addItem(loan.getLoanId());
            }
        }
     }
}

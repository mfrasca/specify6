/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.tasks;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import javax.persistence.Transient;

import edu.ku.brc.specify.datamodel.CollectionType;
import edu.ku.brc.specify.datamodel.GeologicTimePeriod;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDef;
import edu.ku.brc.specify.datamodel.GeologicTimePeriodTreeDefItem;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.forms.FormViewObj;

/**
 * Task that handles the UI for viewing geologic time period data.
 * 
 * @code_status Beta
 * @author jstewart
 */
public class GtpTreeTask extends BaseTreeTask<GeologicTimePeriod,GeologicTimePeriodTreeDef,GeologicTimePeriodTreeDefItem>
{
	public static final String GTP = "GeoTimePeriodTree";
	
	/**
	 * Constructor.
	 */
	public GtpTreeTask()
	{
        super(GTP, getResourceString(GTP));
        treeDefClass = GeologicTimePeriodTreeDef.class;
        icon = IconManager.getIcon(GTP,IconManager.IconSize.Std24);
        
        menuItemText     = getResourceString("GeoTimePeriodMenu");
        menuItemMnemonic = getResourceString("GeoTimePeriodMnemonic");
        starterPaneText  = getResourceString("GeoTimePeriodStarterPaneText");
        commandTypeString = GTP;
        
        initialize();
	}
    
    @Transient
    @Override
    protected GeologicTimePeriodTreeDef getCurrentTreeDef()
    {
        return CollectionType.getCurrentCollectionType().getGeologicTimePeriodTreeDef();
    }

//    protected void adjustTreeDefForm(FormViewObj form)
//    {
//    }
//    
//    protected void adjustTreeDefItemForm(FormViewObj form)
//    {
//    }
    
    @Override
    public void adjustForm(FormViewObj form)
    {
        if (form.getDataObj() instanceof GeologicTimePeriod)
        {
            adjustNodeForm(form);
        }
//        else if (form.getDataObj() instanceof GeologicTimePeriodTreeDef)
//        {
//            adjustTreeDefForm(form);
//        }
//        else if (form.getDataObj() instanceof GeologicTimePeriodTreeDefItem)
//        {
//            adjustTreeDefItemForm(form);
//        }
    }
}

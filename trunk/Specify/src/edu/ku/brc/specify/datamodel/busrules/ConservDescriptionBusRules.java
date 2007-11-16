package edu.ku.brc.specify.datamodel.busrules;

import edu.ku.brc.specify.datamodel.ConservDescription;

public class ConservDescriptionBusRules extends AttachmentOwnerBaseBusRules
{
    public ConservDescriptionBusRules()
    {
        super(ConservDescription.class);
    }

    @Override
    public boolean okToEnableDelete(Object dataObj)
    {
        return true;
    }

}

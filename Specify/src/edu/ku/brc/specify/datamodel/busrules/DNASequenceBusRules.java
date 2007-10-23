package edu.ku.brc.specify.datamodel.busrules;

import edu.ku.brc.specify.datamodel.DNASequence;

public class DNASequenceBusRules extends AttachmentOwnerBaseBusRules
{
    public DNASequenceBusRules()
    {
        super(DNASequence.class);
    }

    @Override
    public boolean okToDelete(Object dataObj)
    {
        return true;
    }
}

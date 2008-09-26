/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.datamodel.busrules;

import edu.ku.brc.af.ui.forms.BaseBusRules;
import edu.ku.brc.af.ui.forms.FormDataObjIFace;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Jan 11, 2008
 *
 */
public class CatalogNumberingSchemeBusRules extends BaseBusRules
{

    /**
     * Constructor.
     */
    public CatalogNumberingSchemeBusRules()
    {
        
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#okToEnableDelete(java.lang.Object)
     */
    @Override
    public boolean okToEnableDelete(Object dataObj)
    {
        reasonList.clear();
        
        return okToDelete("collection", "CatalogNumberingSchemeID", ((FormDataObjIFace)dataObj).getId());
    }

}

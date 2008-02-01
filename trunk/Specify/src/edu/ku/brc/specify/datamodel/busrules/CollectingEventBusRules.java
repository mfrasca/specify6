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
 */package edu.ku.brc.specify.datamodel.busrules;

import edu.ku.brc.specify.datamodel.CollectingEvent;
import edu.ku.brc.specify.datamodel.Locality;

/**
 * @author rod 
 *
 * (original author was JDS)
 *
 * @code_status Alpha
 *
 * Jan 10, 2008
 *
 */
public class CollectingEventBusRules extends AttachmentOwnerBaseBusRules
{
    /**
     * 
     */
    public CollectingEventBusRules()
    {
        super(CollectingEvent.class);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#okToEnableDelete(java.lang.Object)
     */
    @Override
    public boolean okToEnableDelete(final Object dataObj)
    {
        return true;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#addChildrenToNewDataObjects(java.lang.Object)
     */
    @Override
    public void addChildrenToNewDataObjects(final Object newDataObj)
    {
        super.addChildrenToNewDataObjects(newDataObj);
        
        if (false)
        {
            CollectingEvent ce = (CollectingEvent)newDataObj;
            
            if (ce.getLocality() == null)
            {
                Locality locality = new Locality();
                locality.initialize();
                
                ce.addReference(locality, "locality");
            }
        }
    }
    
}

/* Copyright (C) 2013, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.specify.datamodel.busrules;

import edu.ku.brc.specify.datamodel.FieldNotebook;
import edu.ku.brc.specify.datamodel.FieldNotebookPage;
import edu.ku.brc.specify.datamodel.FieldNotebookPageSet;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Feb 11, 2008
 *
 */
public class FieldNotebookBusRules extends AttachmentOwnerBaseBusRules
{
    public FieldNotebookBusRules()
    {
        super(FieldNotebook.class);
    }

    
    /**
     * @param attOwner
     */
    @Override
    protected void addExtraObjectForProcessing(final Object dObjAtt)
    {
        super.addExtraObjectForProcessing(dObjAtt);
        
        FieldNotebook fnb = (FieldNotebook)dObjAtt;
        
        for (FieldNotebookPageSet pageSet : fnb.getPageSets())
        {
            super.addExtraObjectForProcessing(pageSet);
            
            for (FieldNotebookPage page : pageSet.getPages())
            {
                super.addExtraObjectForProcessing(page);
            }
        }
    }
}

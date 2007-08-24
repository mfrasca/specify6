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
 * 
 */
package edu.ku.brc.specify.datamodel.busrules;

import edu.ku.brc.specify.datamodel.Agent;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Jan 24, 2007
 *
 */
public class AgentBusRules extends BaseBusRules
{

    public AgentBusRules()
    {
        super(Agent.class);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#okToDelete(java.lang.Object)
     */
    @Override
    public boolean okToDelete(Object dataObj)
    {
        String[] tableInfo = 
        {
                "specifyuser",  "AgentID",
                "agent",        "ParentOrganizationID",
                "groupperson", "GroupID",
                "loanreturnphysicalobject", "ReceivedByID",
                "author",      "AgentID",
                "borrowreturnmaterial", "ReturnedByID",
                "preparation",  "PreparedByID",
                "exchangein",   "CatalogedByID",
                "exchangein",   "ReceivedFromOrganizationID",
                "project",      "ProjectAgentID",
                "shipment",     "ShippedByID",
                "shipment",     "ShipperID",
                "shipment",     "ShippedToID",
                "collector",   "AgentID",
                "exchangeout",  "CatalogedByID",
                "exchangeout",  "SentToOrganizationID",
                "repositoryagreement",  "AgentID",
                "deaccessionagent",  "AgentID",
                "permit",       "IssuedToID",
                "permit",       "IssuedByID",
                "borrowagent",  "AgentID",
            };

        Agent agent = (Agent)dataObj;
        
        Integer agentId = agent.getId();
        if (agentId==null)
        {
            return true;
        }
        
        boolean result = okToDelete(tableInfo, agent.getId());
        
        return result;
    }

}

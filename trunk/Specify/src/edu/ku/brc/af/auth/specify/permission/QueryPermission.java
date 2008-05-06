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
package edu.ku.brc.af.auth.specify.permission;

import org.apache.log4j.Logger;

import edu.ku.brc.ui.UIRegistry;

@SuppressWarnings("serial") //$NON-NLS-1$
public class QueryPermission extends BasicSpPermission
{
    protected static final Logger log = Logger.getLogger(QueryPermission.class);

    /**
     * @param id
     * @param name
     * @param actions
     */
    public QueryPermission(final String name, final String actions)
    {
        super(name, actions);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     * @param name
     */
    public QueryPermission(final String name)
    {
        super(name);
        // TODO Auto-generated constructor stub
    }

}

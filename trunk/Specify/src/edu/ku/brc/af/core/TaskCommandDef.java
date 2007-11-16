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
package edu.ku.brc.af.core;

import java.util.Properties;

/**
 * Class that defines a command that is issued by UI components in a Task's SubPane or from the side bar.
 * These commands are typically serviced by the task itself.
 *
 * @code_status Complete
 *
 * @author rods
 *
 */
public class TaskCommandDef
{
    protected String iconName;
    protected String name;
    protected Properties params;

    /**
     * Constructs a TaskCommand Definition.
     * @param name the name of the command
     * @param iconName the icon for the command (to be looked up in the IconManager by name)
     * @param params a Hash Map of parameters
     */
    public TaskCommandDef(final String name, final String iconName, final Properties params)
    {
        this.iconName = iconName;
        this.name     = name;
        this.params   = params;
    }

    public String getIconName()
    {
        return iconName;
    }

    public String getName()
    {
        return name;
    }

    public Properties getParams()
    {
        return params;
    }

}

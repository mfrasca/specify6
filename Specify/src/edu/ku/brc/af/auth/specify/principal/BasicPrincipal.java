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
package edu.ku.brc.af.auth.specify.principal;

import java.io.Serializable;
import java.security.Principal;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class BasicPrincipal implements Principal, Serializable
{
    protected static final Logger log = Logger.getLogger(BasicPrincipal.class);
    
    protected Integer id; // Hibernate table identifier collected from the original SpPrincipal 
    protected String name;
    
    public BasicPrincipal(Integer id, String name)
    {
    	this.id   = id;
        this.name = name;
    }
    
    public BasicPrincipal()
    {
    	this.id = null;
        this.name = "";
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Return a string representation of this <code>BasicPrincipal</code>.
     * @return a string representation of this <code>BasicPrincipal</code>.
     */
    public String toString()
    {
        String className = getClass().getName();
        return className.substring(className.lastIndexOf('.') + 1) + ": " + name + " [" + getName() + "]";
    }

    /**
     * Compares the specified Object with this <code>BasicPrincipal</code>
     * for equality.  Returns true if the given object is also a  <code>BasicPrincipal</code> 
     * and the two RdbmsPrincipals have the same name.
     * 
     * @param o Object to be compared for equality with this <code>BasicPrincipal</code>.
     * @return true if the specified Object is equal equal to this <code>BasicPrincipal</code>.
     */
    public boolean equals(Object o)
    {
        if (o == null)
            return false;

        if (this == o) { return true; }

        if (name.equals(null))
        {
            log.debug("BasicPrincipal.equals - Name is null");
        }
        if (o instanceof AdminPrincipal)
        {
            if (((AdminPrincipal)o).getName().equals(name))
            {
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return super.equals(o);
        }
    }
}

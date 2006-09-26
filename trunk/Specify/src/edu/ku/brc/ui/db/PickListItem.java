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
package edu.ku.brc.ui.db;

import java.util.Date;

/**
 * PickListItem generated by hbm2java
 *
 * @code_status Unknown (auto-generated)
 * 
 * @author rods
 *
 */
@SuppressWarnings("serial")
public class PickListItem implements java.io.Serializable, Comparable<PickListItem>
{

    // Fields

    private String title;
    private String value;
    private Date   createdDate;

    // Constructors

    /** default constructor */
    public PickListItem()
    {
        // do nothing
    }

    // Property accessors

    public PickListItem(String title, String value, Date createdDate)
    {
        super();
        this.title = title;
        this.value = value;
        this.createdDate = createdDate;
    }

    /**
     * 
     */
    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * 
     */
    public String getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public int compareTo(PickListItem obj)
    {
        if (title.equals(obj.title))
        {
            return 0;

        }
        // else
        return title.compareTo(obj.title);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return title;
    }

    /**
     * 
     */
    public Date getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

}

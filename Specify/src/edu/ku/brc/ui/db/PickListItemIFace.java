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

import java.sql.Timestamp;

/**
 * Represents a PickListItem.
 * 
 * Created Date: Nov 10, 2006
 *
 * @author rods
 * @code_status Beta
 */
public interface PickListItemIFace extends Comparable<PickListItemIFace>
{
    public Integer getId();
    
	public void setPickList(PickListIFace pickList);
	
	public PickListIFace getPickList();
	
    public String getTitle();

    public void setTitle(String title);

    public String getValue();

    public void setValue(String value);
    
    public Timestamp getTimestampCreated();

    public void setTimestampCreated(Timestamp createdDate);
    
    // Non-Presisted Field
    
    public Object getValueObject();

    public void setValueObject(Object valueObject);
    
}

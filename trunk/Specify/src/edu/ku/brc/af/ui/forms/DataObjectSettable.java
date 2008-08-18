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
package edu.ku.brc.af.ui.forms;

/*
 * @code_status Beta
 **
 * @author rods
 *
 */
public interface DataObjectSettable
{

    /**
     * Sets data for a field into the parent data objectinto 
     * @param dataObj the parent data object
     * @param fieldName the name of the field
     * @param data the field's data
     */
    public void setFieldValue(Object dataObj, String fieldName, Object data);
    
    /**
     * Returns true if dots (".") should be used to walk an object tree when setting the value, or
     * false when "." in the name are meaningless.
     * @return true if dots (".") should be used to walk an object tree when setting the value, or
     * false when "." in the name are meaningless.
     */
    public boolean usesDotNotation();
}

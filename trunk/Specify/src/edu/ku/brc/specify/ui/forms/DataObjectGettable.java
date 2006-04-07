/* Filename:    $RCSfile: DataObjectGettable.java,v $
 * Author:      $Author: rods $
 * Revision:    $Revision: 1.1 $
 * Date:        $Date: 2005/10/12 16:52:27 $
 *
 * This library is free software; you can redistribute it and/or
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
package edu.ku.brc.specify.ui.forms;

/**
 * Interface that defines a standard way to get data from an object. There are at least three ways:<br>
 * <ul>
 * <li>The data object may be a java object and each field is a data member.</li>
 * <li>The data object may be a resultset (list of objects) and each row may be a field.</li>
 * <li>The data object may be a XML document and the field name may be a XPath to the data.</li></ul>
 * <br>
 * <br>
 * NOTE: Arguments format and formatName are mutually exclusive and that formatName is checked first and then format
 * is checked.<br>
 * <BR>
 * <B><span color="red">IMPORTANT:</span></b> There is no requirement for any implementing class to have to support either the "format"
 * or the "formatName" arguments (and they may throw an exception to tell you).
 * 
 * @author rods
 *
 */
public interface DataObjectGettable
{
    /**
     * Returns a field's value
     * @param dataObj the data object that contains the field
     * @param fieldName the fields name
     * @return the value of the field
     */
    public Object getFieldValue(Object dataObj, String fieldName);
    
    /**
     * Returns a field's value
     * @param dataObj the data object that contains the field
     * @param fieldName the fields name
     * @param formatName the name of the DataObjFieldFormMgr to use (this is mutually exclusive with format)
     * @param format format the field before returning it (this is mutually exclusive with formatName)
     * @return the value of the field
     */
    public Object getFieldValue(Object dataObj, String fieldName, String formatName, String format);

}

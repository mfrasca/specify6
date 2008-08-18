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

package edu.ku.brc.ui.forms.formatters;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.af.core.db.DBFieldInfo;
import edu.ku.brc.af.core.db.DBRelationshipInfo;

/**
 * Wrapper for data object formatters.
 * Created to modify toString() method and display item nicely on JList.
 *  
 * @author ricardo
 *
 * @code_status Alpha
 *
 *
 */
public class DataObjDataFieldWrapper
{
	protected DataObjDataField fmtField;
	
	DataObjDataFieldWrapper(DataObjDataField fmtField)
	{
		this.fmtField = fmtField; 
	}
	
	public DataObjDataField getFormatterField()
	{
		return fmtField;
	}
	
	public boolean isPureField()
	{
		return fmtField.isPureField();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		if (StringUtils.isNotEmpty(fmtField.getDataObjFormatterName()))
		{
			// data obj switch formatter
			return fmtField.getDataObjFormatterName() + " (display format)";
		}

		// try and get the field formatter from static instance
		if (fmtField.getUiFieldFormatter() == null && StringUtils.isNotEmpty(fmtField.getUiFieldFormatterName()))
		{
			UIFieldFormatterIFace fmt = UIFieldFormatterMgr.getInstance().getFormatter(fmtField.getUiFieldFormatterName());
			fmtField.setUiFieldFormatter(fmt);
		}
		
		// field
		DBFieldInfo        fieldInfo = fmtField.getFieldInfo();
		DBRelationshipInfo relInfo   = fmtField.getRelInfo();
		
		String result = (relInfo != null)? relInfo.getTitle() + "." : "";
		String pattern = (fmtField.getUiFieldFormatter() != null)? " (" + fmtField.getUiFieldFormatter().toPattern() + ")" : "";
		return (fieldInfo != null)? result + fieldInfo.getTitle() + pattern : result + pattern;
	}
}

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
package edu.ku.brc.ui.forms.formatters;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.dbsupport.DBFieldInfo;
import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.DBTableInfo;

/**
 * This describes a single field that is used as part of a DataObjectFormatter.
 * The fields are appended together to construct the final value.
 * 
 * @author rods
 * 
 * @code_status Complete.
 * 
 * Created Date: Jan 17, 2007
 * 
 */
public class DataObjDataField {
	protected String name;
	protected Class<?> type;
	protected String format;
	protected String sep;
	protected String uiFieldFormatter;
	protected String dataObjFormatterName;
	protected DBTableInfo tableInfo;
	protected DBFieldInfo fieldInfo;
	protected DataObjSwitchFormatter objFormatter;
	

	public DataObjDataField(final String name, final Class<?> type,
			final String format, final String sep,
			final String dataObjFormatterName, final String uiFieldFormatter) {
		super();

		this.name = name;
		this.type = type;
		this.format = format;
		this.sep = sep;
		this.dataObjFormatterName = dataObjFormatterName;
		this.uiFieldFormatter = uiFieldFormatter;

		// table info is set during parent (DataObjDataFieldFormat) construction
	}

	public String toString() {
		if (objFormatter != null)
		{
			return objFormatter.toString();
		}
		
		if (tableInfo == null || fieldInfo == null)
		{
			//System.err.println("Table or field info is null");
			return "null";
		}
		
		String prefix = "";
		if (StringUtils.isNotEmpty(sep))
		{
			prefix = sep;
		}
		return prefix + "[" + tableInfo.getTitle() + "." + fieldInfo.getTitle() + "]";
	}

	public String getFormat() {
		return format;
	}

	public String getName() {
		return name;
	}

	public String getSep() {
		return sep;
	}

	public Class<?> getType() {
		return type;
	}

	public String getDataObjFormatterName() {
		return dataObjFormatterName;
	}

	/**
	 * @return the uiFieldFormatter
	 */
	public String getUiFieldFormatter() {
		return uiFieldFormatter;
	}

	public DBTableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(DBTableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	public DBFieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(DBFieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	
	public void setTableAndFieldInfo(DBTableInfo tableInfo)
	{
		setTableInfo(tableInfo);
		fieldInfo = tableInfo.getFieldByName(name);
		if (StringUtils.isNotEmpty(dataObjFormatterName))
		{
			objFormatter = DataObjFieldFormatMgr.getFormatter(dataObjFormatterName);
		}
	}

}
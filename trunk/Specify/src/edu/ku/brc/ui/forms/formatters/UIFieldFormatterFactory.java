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

import edu.ku.brc.dbsupport.DBFieldInfo;

public abstract class UIFieldFormatterFactory
{
	protected DBFieldInfo fieldInfo;
	
	public UIFieldFormatterFactory(DBFieldInfo fieldInfo)
	{
		this.fieldInfo = fieldInfo;
	}
	
	/**
	 * Creates a UIFieldFormatter that corresponds to the format string or throws an exception if the
	 * format string is invalid. Implementors are responsible for a given field type (number, text, etc)
	 * 
	 * @param formattingString String defining the format
	 * @return New formatter corresponding to the provided format string
	 * @throws UIFieldFormatterParsingException
	 */
	public abstract UIFieldFormatter createFormat(final String formattingString) throws UIFieldFormatterParsingException;

	/**
	 * Returns the help message (in HTML format) to be displayed in the formatter dialog.
	 * @return the help message (in HTML format) to be displayed in the formatter dialog.
	 */
	public abstract String getHelpHtml();
}

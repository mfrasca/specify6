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
package edu.ku.brc.af.core.expresssearch;

import static edu.ku.brc.helpers.XMLHelper.getAttr;

import org.dom4j.Element;

import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.db.DBTableInfo;

/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Jul 10, 2007
 *
 */
public class ERTIJoinColInfo
{
    protected String  joinTableId;
    protected int     joinTableIdAsInt;
    protected String  colName;
    protected boolean isPrimary;
    
    // Transient
    private DBTableInfo tableInfo = null;
    
    /**
     * @param element
     */
    public ERTIJoinColInfo(final Element element)
    {
        joinTableId      = getAttr(element, "tableid", null); //$NON-NLS-1$
        joinTableIdAsInt = getAttr(element, "tableid", -1); //$NON-NLS-1$
        colName          = element.getTextTrim();
        isPrimary        = getAttr(element, "primary", false); //$NON-NLS-1$
    }

    public String getJoinTableId()
    {
        return joinTableId;
    }

    public int getJoinTableIdAsInt()
    {
        return joinTableIdAsInt;
    }

    public String getColName()
    {
        return colName;
    }

    public boolean isPrimary()
    {
        return isPrimary;
    }

    /**
     * @return the tableInfo
     */
    public DBTableInfo getTableInfo()
    {
        if (tableInfo == null)
        {
            tableInfo = DBTableIdMgr.getInstance().getInfoById(joinTableIdAsInt);
        }
        return tableInfo;
    }
    
    
}

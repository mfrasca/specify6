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
package edu.ku.brc.ui.forms.persist;

/*
 * @code_status Beta
 **
 * @author rods
 *
 */
public class FormCellSeparator extends FormCell implements Cloneable, FormCellSeparatorIFace
{
    protected String label;
    protected String collapseCompName;

    public FormCellSeparator(final String id, 
                             final String name, 
                             final String label, 
                             final int    colspan)
    {
        this(id, name, label, null, colspan);
    }    
    
    public FormCellSeparator(final String id, 
                             final String name, 
                             final String label, 
                             final String collapseCompName,
                             final int    colspan)
     {
         super(FormCellIFace.CellType.separator, id, name);
         
         this.label   = label;
         this.collapseCompName   = collapseCompName;
         this.colspan = colspan;
         this.ignoreSetGet = true;
     }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.persist.FormCellSeparatorIFace#getLabel()
     */
    public String getLabel()
    {
        return label;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.persist.FormCellSeparatorIFace#setLabel(java.lang.String)
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.persist.FormCellSeparatorIFace#getCollapseCompName()
     */
    public String getCollapseCompName()
    {
        return collapseCompName;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.persist.FormCellSeparatorIFace#clone()
     */
    public Object clone() throws CloneNotSupportedException
    {
        FormCellSeparator cellSep = (FormCellSeparator)super.clone();
        cellSep.label             = label;
        cellSep.collapseCompName  = collapseCompName;
        return cellSep;      
    }
    
}

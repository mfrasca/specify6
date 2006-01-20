/* Filename:    $RCSfile: FormCellLabel.java,v $
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
package edu.ku.brc.specify.ui.forms.persist;

public class FormCellLabel extends FormCellSeparator
{
    protected String labelFor;
    
    public FormCellLabel(String name, String label, String labelFor, int colspan)
    {
        super(name, label, colspan);
        
        this.type     = FormCell.CellType.label;        
        this.labelFor = labelFor;
    }
    
    public String getLabelFor()
    {
        return labelFor;
    }

    public void setLabelFor(String labelFor)
    {
        this.labelFor = labelFor;
    }

}

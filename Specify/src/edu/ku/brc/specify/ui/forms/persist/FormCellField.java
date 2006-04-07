/* Filename:    $RCSfile: FormCellField.java,v $
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

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * This represents all the information about a cell in the form.
 * @author rods
 *
 */
public class FormCellField extends FormCell
{
    protected String   uiType;
    protected String   dspUIType;
    protected String   format;   
    protected String   formatName;   
    protected boolean  isRequired     = false;
    protected boolean  isEncrypted    = false;
    protected String   label          = null;
    
    protected String   pickListName       = null; // Comboboxes and TextFields
    
    // Needed for Text Components
    protected int      cols    = 10; // TextField and TextArea
    protected int      rows    = 1; // Text Area Only
    
    protected String   validationType = "";
    protected String   validationRule = "";      
    protected String   initialize     = "";   
    protected boolean  isTextField    = false;
   
    /**
     * Constructor
     * @param type type of cell
     * @param name the name
     * @param colspan the number of columns to span
     * @param rowspan the number of rows to span
     */ 
    protected FormCellField(final FormCell.CellType type, 
                            final String            name,
                            final int               colspan, 
                            final int               rowspan)
    {
        super(type, name, colspan, rowspan);
    }

    /**
     * @param type type of cell
     * @param name the name
     * @param uiType the type of ui component to be created (i.e. "checkbox", "textfield")
     * @param format the format for a text field
     * @param isRequired whether the control MUST have value
     * @param cols the number of default columns to make the text field/area
     * @param rows the number of default rows to make TextArea
     * @param colspan the number of columns to span
     * @param rowspan the number of rows to span
     * @param validationType the type of validation (when to validate)
     * @param validationRule the rule on how to validate
     * @param isEncrypted whether the control should have its value encrypted
     */
    public FormCellField(final FormCell.CellType type, 
                         final String  name, 
                         final String  uiType, 
                         final String  dspUIType, 
                         final String  format,
                         final String  formatName,
                         final boolean isRequired, 
                         final int     cols, 
                         final int     rows, 
                         final int     colspan, 
                         final int     rowspan,
                         final String  validationType,
                         final String  validationRule,
                         final boolean isEncrypted)
    {
        this(type, name, colspan, rowspan);
        
        this.uiType         = uiType;
        this.dspUIType      = dspUIType;
        this.format         = format;
        this.formatName     = formatName;
        this.cols           = cols;
        this.rows           = rows;
        this.validationRule = validationRule;
        this.validationType = validationType;
        this.isRequired     = isRequired;
        this.isEncrypted    = isEncrypted;
        
        this.isTextField = isEmpty(uiType) || uiType.equals("dsptextfield") || uiType.equals("text");

    }

    public int getCols()
    {
        return cols;
    }

    public String getFormat()
    {
        return format;
    }

    public String getName()
    {
        return name;
    }

    public String getUiType()
    {
        return uiType;
    }

    public String getDspUIType()
    {
        return dspUIType;
    }

    public int getRows()
    {
        return rows;
    }

    public String getPickListName()
    {
        return pickListName;
    }

    public String getValidationRule()
    {
        return validationRule;
    }
    
    public String getInitialize()
    {
        return initialize;
    }
    
    public boolean isRequired()
    {
        return isRequired;
    }
    public String getValidationType()
    {
        return validationType;
    }

    public boolean isEncrypted()
    {
        return isEncrypted;
    }

    public String getFormatName()
    {
        return formatName;
    }

    public String getLabel()
    {
        return label;
    }

    public boolean isTextField()
    {
        return isTextField;
    }

    public void setInitialize(String initialize)
    {
        this.initialize = initialize;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void setPickListName(String pickListName)
    {
        this.pickListName = pickListName;
    }
    
}

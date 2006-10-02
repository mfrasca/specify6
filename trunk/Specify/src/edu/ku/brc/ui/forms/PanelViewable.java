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
package edu.ku.brc.ui.forms;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.ui.forms.persist.FormCell;
import edu.ku.brc.ui.forms.persist.FormCellLabel;
import edu.ku.brc.ui.forms.persist.FormCellPanel;
import edu.ku.brc.ui.forms.persist.FormCellSubView;

/**
 * A simple container using the JGoodies layout for better organzing UI components. It also enables
 * the user to creat a button bar. The params to the JGoodies layout are defined in the FormCellPanel object
 * that is passed in.
 * 
 * @author rods
 *
 * @code_status Complete
 *
 */
public class PanelViewable extends JPanel implements ViewBuilderIFace
{
    public enum PanelType {Unknown, Panel, ButtonBar};
    
    protected DefaultFormBuilder builder;
    protected CellConstraints    cc = new CellConstraints();
   
    /**
     * Creates a class that is used to organize or help crafted the layout. It is light-weight
     * and is merely for arranging UI components.
     * @param cellPanel the cell definition
     */
    public PanelViewable(final FormCellPanel cellPanel)
    {
        builder = new DefaultFormBuilder(new FormLayout(cellPanel.getColDef(), cellPanel.getRowDef()), this);
       
    }
    

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#addControlToUI(java.awt.Component, int, int, int, int)
     */
    public void addControlToUI(final Component control, final int colInx, final int rowInx, final int colSpan, final int rowSpan)
    {
        builder.add(control, cc.xywh(colInx, rowInx, colSpan, rowSpan));

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#addLabel(edu.ku.brc.ui.forms.persist.FormCellLabel, javax.swing.JLabel)
     */
    public void addLabel(final FormCellLabel formCell, final JLabel label)
    {
        // not supported

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#addSeparator(java.lang.String, int, int, int)
     */
    public Component createSeparator(final String title, final int colInx, final int rowInx, final int colSpan)
    {
        return builder.addSeparator(title, cc.xyw(colInx, rowInx, colSpan));
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#addRecordIndentifier(java.lang.String, javax.swing.ImageIcon)
     */
    public JComponent createRecordIndentifier(final String title, final ImageIcon icon)
    {
        // not supported
        return null;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#addSubView(edu.ku.brc.ui.forms.persist.FormCell, edu.ku.brc.ui.forms.MultiView, int, int, int, int)
     */
    public void addSubView(final FormCellSubView formCell,
                           final MultiView subView,
                           final int colInx,
                           final int rowInx,
                           final int colSpan,
                           final int rowSpan)
    {
        // not supported

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#closeSubView(edu.ku.brc.ui.forms.persist.FormCellSubView)
     */
    public void closeSubView(FormCellSubView formCell)
    {
        // not supported
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#registerControl(edu.ku.brc.ui.forms.persist.FormCell, java.awt.Component)
     */
    public void registerControl(final FormCell formCell, final Component control)
    {
        // not supported
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#setDataObjectRep(edu.ku.brc.ui.forms.DraggableRecordIdentifier)
     */
    public void setDataObjectRep(final DraggableRecordIdentifier draggableRI)
    {
        // not supported
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.ViewBuilderIFace#shouldFlatten()
     */
    public boolean shouldFlatten()
    {
        return false;
    }
    
    /**
     * @param btns
     * @return
     */
    public static JComponent buildButtonBar(final JButton[] btns)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildCenteredBar(btns);
    }
    
    /**
     * @param typeStr
     * @return
     */
    public static PanelType getType(final String typeStr)
    {
        return StringUtils.isNotEmpty(typeStr) && typeStr.equalsIgnoreCase("buttonbar") ? PanelType.ButtonBar : PanelType.Panel;
    }

}

/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tasks.subpane.wb;

import javax.swing.ImageIcon;

import edu.ku.brc.af.core.expresssearch.TableFieldPair;
import edu.ku.brc.dbsupport.DBFieldInfo;
import edu.ku.brc.dbsupport.DBTableInfo;
import edu.ku.brc.ui.IconManager;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Apr 6, 2007
 *
 */
public class FieldInfo extends TableFieldPair implements TableListItemIFace
{
    protected static ImageIcon checkMark   = IconManager.getIcon("Checkmark", IconManager.IconSize.Std16);
    protected String name;
    
    public FieldInfo(DBTableInfo tableinfo, DBFieldInfo fieldInfo)
    {
        super(tableinfo, fieldInfo);
        
        name = fieldInfo.getColumn();
        int inx = name.indexOf(" (");
        if (inx > -1)
        {
            name = name.substring(0, inx);
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#getIcon()
     */
    public ImageIcon getIcon()
    {
        return checkMark;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#getText()
     */
    public String getText()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#isChecked()
     */
    public boolean isChecked()
    {
        return isInUse;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#isExpandable()
     */
    public boolean isExpandable()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#isExpanded()
     */
    public boolean isExpanded()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#setChecked(boolean)
     */
    public void setChecked(boolean checked)
    {
       setInUse(checked);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.tasks.subpane.wb.TableListItemIFace#setExpanded(boolean)
     */
    public void setExpanded(boolean expand)
    {
        // no-op
    }

}

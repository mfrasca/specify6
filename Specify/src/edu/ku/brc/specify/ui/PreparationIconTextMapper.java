/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.specify.ui;

import javax.swing.ImageIcon;

import edu.ku.brc.af.ui.forms.formatters.DataObjFieldFormatMgr;
import edu.ku.brc.specify.datamodel.Preparation;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.IconManager.IconSize;

/**
 * This class is an implementation of both {@link ObjectTextMapper} and {@link ObjectIconMapper}
 * that handles {@link Preparation} objects.
 *
 * @code_status Beta
 * @author jstewart
 */
public class PreparationIconTextMapper implements ObjectTextMapper, ObjectIconMapper
{
    /**
     * Create an instance.
     */
    public PreparationIconTextMapper()
    {
        // do nothing
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.ObjectIconMapper#getMappedClasses()
     */
    public Class<?>[] getMappedClasses()
    {
        Class<?>[] mappedClasses = new Class[1];
        mappedClasses[0] = Preparation.class;
        return mappedClasses;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.ObjectTextMapper#getString(java.lang.Object)
     */
    public String getString(Object o)
    {
        Preparation prep = (Preparation)o;
        if (prep != null)
        {
            return DataObjFieldFormatMgr.getInstance().format(prep, "Preparation");
        }
        return null;
    }

    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.ui.ObjectIconMapper#getIcon(java.lang.Object)
     */
    public ImageIcon getIcon(Object o)
    {
        Preparation p = (Preparation)o;
        String prepTypeName = p.getPrepType().getName();
        //IconSize size = IconSize.Std32;
        String name = null;
        if(prepTypeName.equalsIgnoreCase("skeleton"))
        {
            name = "skeleton";
        }
        else if(prepTypeName.equalsIgnoreCase("C&S"))
        {
            name = "cs";
        }
        else if(prepTypeName.equalsIgnoreCase("EtOH"))
        {
            name = "etoh";
        }
        else if(prepTypeName.equalsIgnoreCase("x-ray") || prepTypeName.equalsIgnoreCase("xray"))
        {
            name = "xray";
        }
        else
        {
            name = "unknown";
        }
        
        ImageIcon icon = IconManager.getIcon(name);
        if (icon==null)
        {
            return null;
        }
        return IconManager.getScaledIcon(icon, null, IconSize.Std32);
    }
}

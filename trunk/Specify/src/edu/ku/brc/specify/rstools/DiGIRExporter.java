package edu.ku.brc.specify.rstools;

import static edu.ku.brc.ui.UIRegistry.getResourceString;

import java.util.List;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.ku.brc.dbsupport.RecordSetIFace;
import edu.ku.brc.specify.datamodel.CollectionObject;
import edu.ku.brc.specify.datamodel.RecordSet;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.UIRegistry;

/**
 * An implementation of {@link RecordSetToolsIFace} that handles {@link List}s and
 * {@link RecordSet}s of {@link CollectionObject} objects.  The resulting output
 * is formatted according to the DiGIR schema.
 * 
 * @author jstewart
 * @code_status Alpha
 */
public class DiGIRExporter implements RecordSetToolsIFace
{
    public static final String NAME = "DiGIR";
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.rstools.RecordSetToolsIFace#processRecordSet(edu.ku.brc.dbsupport.RecordSetIFace, java.util.Properties)
     */
    public void processRecordSet(RecordSetIFace data, Properties reqParams)
    {
        JFrame topFrame = (JFrame)UIRegistry.getTopWindow();
        Icon icon = IconManager.getIcon(NAME);
        JOptionPane.showMessageDialog(topFrame, "Not yet implemented", NAME + " data export", JOptionPane.ERROR_MESSAGE, icon);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#exportList(java.util.List, java.util.Properties)
     */
    public void processDataList(List<?> data, Properties reqParams)
    {
        JFrame topFrame = (JFrame)UIRegistry.getTopWindow();
        Icon icon = IconManager.getIcon(NAME);
        JOptionPane.showMessageDialog(topFrame, "Not yet implemented", NAME + " data export", JOptionPane.ERROR_MESSAGE, icon);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getHandledClasses()
     */
    public Class<?>[] getHandledClasses()
    {
        return new Class<?>[] {CollectionObject.class};
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getIconName()
     */
    public String getIconName()
    {
        return NAME;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getName()
     */
    public String getName()
    {
        return NAME;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporter#getDescription()
     */
    public String getDescription()
    {
        return getResourceString(NAME+"_Description");
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetExporterIFace#isVisible()
     */
    public boolean isVisible()
    {
        return false;
    }
    
    
    /* (non-Javadoc)
     * @see edu.ku.brc.specify.exporters.RecordSetToolsIFace#getTableIds()
     */
    public int[] getTableIds()
    {
        return null;
    }
}

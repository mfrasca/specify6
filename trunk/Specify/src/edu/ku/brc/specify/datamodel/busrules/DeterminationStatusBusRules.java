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
package edu.ku.brc.specify.datamodel.busrules;

import static edu.ku.brc.ui.UIRegistry.getLocalizedMessage;

import java.awt.Component;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.specify.datamodel.Discipline;
import edu.ku.brc.specify.datamodel.DeterminationStatus;
import edu.ku.brc.ui.GetSetValueIFace;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.forms.BaseBusRules;
import edu.ku.brc.ui.forms.FormViewObj;
import edu.ku.brc.ui.forms.Viewable;

/**
 * Business Rules for DeterminationStatus.
 * 
 * @author rods
 *
 * @code_status Beta
 *
 * Created Date: Dec 19, 2006
 *
 */
public class DeterminationStatusBusRules extends BaseBusRules
{
    
    /**
     * Constructor.
     */
    public DeterminationStatusBusRules()
    {
        super(DeterminationStatusBusRules.class);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#getDeleteMsg(java.lang.Object)
     */
    public String getDeleteMsg(Object dataObj)
    {
        if (dataObj instanceof DeterminationStatus)
        {
            return getLocalizedMessage("DETERMINATION_STATUS_DELETED", ((DeterminationStatus)dataObj).getName());
        }
        // else
        return super.getDeleteMsg(dataObj);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#okToDelete(java.lang.Object)
     */
    public boolean okToEnableDelete(Object dataObj)
    {
        reasonList.clear();
        
        DeterminationStatus DeterminationStatus = (DeterminationStatus)dataObj;
        if (DeterminationStatus.getId() == null)
        {
            return true;
        }
        
        Connection conn = null;
        Statement  stmt = null;
        try
        {
            conn = DBConnection.getInstance().createConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from determination where determination.DeterminationStatusID = "+DeterminationStatus.getId());
            return rs.next() && rs.getInt(1) == 0;
            
        } catch (Exception ex)
        {
            ex.printStackTrace();
            
        } finally
        {
            try 
            {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#addChildrenToNewDataObjects(java.lang.Object)
     */
    @Override
    public void addChildrenToNewDataObjects(final Object newDataObj)
    {
        super.addChildrenToNewDataObjects(newDataObj);
        
        
        DeterminationStatus ds = (DeterminationStatus)newDataObj;
        
        Discipline.getCurrentDiscipline().addReference(ds, "determinationStatuss");
        
        String sqlStr = "select type From DeterminationStatus WHERE disciplineId = " + Discipline.getCurrentDiscipline().getDisciplineId();
        
        DataProviderSessionIFace session = null;
        try
        {
            session = DataProviderFactory.getInstance().createSession();
            Object dataObj = session.getDataList(sqlStr);
            if (dataObj instanceof List<?>)
            {
                byte maxType = DeterminationStatus.USERDEFINED-1;
                for (Iterator<?> iter = ((List<?>)dataObj).iterator(); iter.hasNext();)
                {
                    Byte bType = (Byte)iter.next();
                    maxType = (byte)Math.max(bType.intValue(), maxType);
                }
                maxType++;
                ds.setType(maxType);
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
            
        } finally
        {
            if (session != null)
            {
                session.close();        
            }
        }
        
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.specify.datamodel.busrules.BaseBusRules#afterFillForm(java.lang.Object, edu.ku.brc.ui.forms.Viewable)
     */
    @Override
    public void afterFillForm(final Object dataObj, final Viewable viewable)
    {
        super.afterFillForm(dataObj, viewable);
        
        if (viewable instanceof FormViewObj)
        {
            FormViewObj formViewObj = (FormViewObj)viewable;
            if (formViewObj.getDataObj() instanceof DeterminationStatus)
            {
                DeterminationStatus ds = (DeterminationStatus)formViewObj.getDataObj();
                
                Component comp = formViewObj.getControlByName("typeDesc");
                String desc = "";
                switch (ds.getType())
                {
                    case DeterminationStatus.CURRENT :
                        desc = UIRegistry.getResourceString("DTS_ISCURRENT");
                        break;
                        
                    case DeterminationStatus.OLDDETERMINATION :
                        desc = UIRegistry.getResourceString("DTS_OLDDET");
                        break;
                        
                    case DeterminationStatus.NOTCURRENT :
                        desc = UIRegistry.getResourceString("DTS_NOTCURRENT");
                        break;
                        
                    default:
                        desc = UIRegistry.getResourceString("DTS_USERDEF");
                }
                if (comp instanceof GetSetValueIFace)
                {
                    ((GetSetValueIFace)comp).setValue(desc, "");
                }
            }
        }
    }
    
    
}

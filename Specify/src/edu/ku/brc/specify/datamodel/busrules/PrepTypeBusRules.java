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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import edu.ku.brc.dbsupport.DBConnection;
import edu.ku.brc.specify.datamodel.PrepType;
import edu.ku.brc.ui.forms.DraggableRecordIdentifier;

/**
 * Business Rules for PrepTypes.
 * 
 * @author rods
 *
 * @code_status Beta
 *
 * Created Date: Dec 19, 2006
 *
 */
public class PrepTypeBusRules extends BaseBusRules
{

    /**
     * Constructor.
     */
    public PrepTypeBusRules()
    {
        super(PrepType.class);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#getDeleteMsg(java.lang.Object)
     */
    public String getDeleteMsg(final Object dataObj)
    {
        if (dataObj instanceof PrepType)
        {
            return getLocalizedMessage("PREPTYPE_DELETED", ((PrepType)dataObj).getName());
        }
        // else
        return super.getDeleteMsg(dataObj);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#getWarningsAndErrors()
     */
    public List<String> getWarningsAndErrors()
    {
        // TODO Auto-generated method stub
        return errorList;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#okToDelete(java.lang.Object)
     */
    public boolean okToDelete(Object dataObj)
    {
        PrepType prepType = (PrepType)dataObj;
        if (prepType.getId() == null)
        {
            return true;
        }
        
        Connection conn = null;
        Statement  stmt = null;
        try
        {
            conn = DBConnection.getInstance().createConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from preparation where preparation.PrepTypeID = "+prepType.getId());
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
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#processBusinessRules(java.lang.Object)
     */
    public STATUS processBusinessRules(Object dataObj)
    {
        errorList.clear();
        
        if (dataObj == null || !(dataObj instanceof PrepType))
        {
            return STATUS.Error;
        }       
        return STATUS.OK;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.BusinessRulesIFace#setObjectIdentity(java.lang.Object, edu.ku.brc.ui.forms.DraggableRecordIdentifier)
     */
    public void setObjectIdentity(Object dataObj, DraggableRecordIdentifier draggableIcon)
    {
        // TODO Auto-generated method stub

    }

}

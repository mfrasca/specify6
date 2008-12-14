/*
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 */
/**
 * 
 */
package edu.ku.brc.af.auth.specify.principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.ku.brc.af.auth.specify.policy.DatabaseService;
import edu.ku.brc.specify.datamodel.SpPrincipal;

/**
 * @author megkumin
 * 
 * @code_status Alpha
 * 
 */
public class UserPrincipalSQLService
{
    protected static final Logger log = Logger.getLogger(UserPrincipalSQLService.class);

    /**
     * 
     */
    public UserPrincipalSQLService()
    {
    }

    /**
     * Retrieves the SpecifyUser ID of a given user principal 
     */
    static public int getSpecifyUserId(SpPrincipal principal)
    {
    	int result = -1; 
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = DatabaseService.getInstance().getConnection();
            if (conn != null)
            {
                pstmt = conn.prepareStatement("SELECT specifyuser_spprincipal.SpecifyUserID "
                                + " FROM specifyuser_spprincipal WHERE SpPrincipalID=?");
                pstmt.setInt(1, principal.getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next())
                {
                	result = rs.getInt("SpecifyUserID");
                }
            } else
            {
                log.error("getSpecifyUserId - database connection was null");
            }
        } catch (SQLException e)
        {
            log.error("Exception caught: " + e);
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null)  conn.close();
                if(pstmt != null)  pstmt.close(); 
            } catch (SQLException e)
            {
                log.error("Exception caught: " + e.toString());
                e.printStackTrace();
            }
        }
        return result;
    }
    
    static public ResultSet getUsersPrincipalsByUserId(String userId)
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try
        {
            log.debug("getUsersPrincipalsByUserId: " + userId); //$NON-NLS-1$
            conn = DatabaseService.getInstance().getConnection();
            if (conn != null)
            {
                pstmt = conn.prepareStatement("SELECT specifyuser_spprincipal.SpPrincipalID " //$NON-NLS-1$
                                + " FROM specifyuser_spprincipal WHERE SpecifyUserID=?"); //$NON-NLS-1$
                pstmt.setString(1, userId);
                log.debug("executing: " + pstmt.toString()); //$NON-NLS-1$
                rs = pstmt.executeQuery();
            } else
            {
                log.error("getUsersPrincipalsByUserId - database connection was null"); //$NON-NLS-1$
            }
        } catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null)  conn.close();
                if(pstmt != null)  pstmt.close(); 
            } catch (SQLException e)
            {
                log.error("Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return rs;
    }

    static public boolean isPrincipalAdmin(Integer principalId)
    {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        try
        {
            log.debug("isAdmin: " + principalId); //$NON-NLS-1$
            conn = DatabaseService.getInstance().getConnection();
            if (conn != null)
            {
                pstmt = conn.prepareStatement("SELECT count(up.SpPrincipalID) as ct" //$NON-NLS-1$
                		+ " FROM specifyuser_spprincipal as up" //$NON-NLS-1$
                		+ " INNER JOIN spprincipal as p on (up.SpPrincipalID=p.SpPrincipalID)" //$NON-NLS-1$
                        + " WHERE p.GroupSubClass='" + AdminPrincipal.class.getCanonicalName() + "'"
                        + "  AND up.SpecifyUserID=?"); //$NON-NLS-1$
                pstmt.setInt(1, principalId);
                log.debug("executing: " + pstmt.toString()); //$NON-NLS-1$
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    result = rs.getInt(1) >= 1;
                }
            } else
            {
                log.error("getUsersPrincipalsByUserId - database connection was null"); //$NON-NLS-1$
            }
        } catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null)  conn.close();
                if(pstmt != null)  pstmt.close();
                if   (rs != null)  rs.close();
            } catch (SQLException e)
            {
                log.error("Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return result;
    }

    static public boolean deleteFromUserGroup(Integer userId, Integer userGroupId)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = DatabaseService.getInstance().getConnection();
            if (conn != null)
            {
                 pstmt = conn
                        .prepareStatement("DELETE FROM specifyuser_spprincipal WHERE SpecifyUserID=? AND SpPrincipalID=?"); //$NON-NLS-1$
                pstmt.setString(1, userId + ""); //$NON-NLS-1$
                pstmt.setString(2, userGroupId + ""); //$NON-NLS-1$
                return 0 < pstmt.executeUpdate();
            } else
            {
                log.error("deleteFromUserGroup - database connection was null"); //$NON-NLS-1$
            }
        } catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null)  conn.close();
                if(pstmt != null)  pstmt.close(); 
            } catch (SQLException e)
            {
                log.error("Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return false;
    }

    static public boolean addToUserGroup(Integer userId, Integer userGroupId)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = DatabaseService.getInstance().getConnection();
            if (conn != null)
            {
                pstmt = conn .prepareStatement("INSERT INTO specifyuser_spprincipal VALUES (?, ?)"); //$NON-NLS-1$
                pstmt.setString(1, userId + ""); //$NON-NLS-1$
                pstmt.setString(2, userGroupId + ""); //$NON-NLS-1$
                return 0 < pstmt.executeUpdate();
            }
            else
            {
                log.error("addToUserGroup - database connection was null"); //$NON-NLS-1$
            }
        } catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        }finally
        {
            try
            {
                if (conn != null)   conn.close();
                if (pstmt != null)  pstmt.close();
            } catch (SQLException e)
            {
                log.error("addToUserGroup Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return false;
    }

    static public void addUser(String username, String password) throws SQLException
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try
        {
            conn = DatabaseService.getInstance().getConnection();
            pstmt = conn.prepareStatement(
                    "INSERT INTO specifyuser (Name, Password) VALUES (?, ?)"); //$NON-NLS-1$
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
        } 
        catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        }finally
        {
            try
            {
                if (conn != null)   conn.close();
                if (pstmt != null)  pstmt.close();
            } catch (SQLException e)
            {
                log.error("addUser Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
    }

    static public String getUsersIdByName(String userName)
    {
        Connection conn = null;
        String id = null;
        PreparedStatement pstmt = null;
        try
        {
            log.debug("getUsersIdByName: " + userName); //$NON-NLS-1$
            conn = DatabaseService.getInstance().getConnection();
            pstmt = conn.prepareStatement("SELECT specifyuser.SpecifyUserID FROM specifyuser WHERE name=?"); //$NON-NLS-1$
            pstmt.setString(1, userName);
            log.debug("executing: " + pstmt.toString()); //$NON-NLS-1$
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                id = rs.getString("SpecifyUserID"); //$NON-NLS-1$
            }
        } catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null)   conn.close();
                if (pstmt != null)  pstmt.close();
            } catch (SQLException e)
            {
                log.error("addUser Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     * @param user
     * @param pass
     * @param driverClass
     * @param url
     * @return
     * @throws Exception
     */
    public static Set<SpPrincipal> getUsersGroupsByUsername(String user) throws Exception
    {
        log.debug("findGroups() called"); //$NON-NLS-1$
        Set<SpPrincipal> principals = new HashSet<SpPrincipal>();
        Connection conn = null;
        PreparedStatement pstmt =null;
        try
        {
            conn = DatabaseService.getInstance().getConnection();
            log.debug("findGroups() called.  user:" + user); //$NON-NLS-1$
            conn = DatabaseService.getInstance().getConnection();
            String myUserId = UserPrincipalSQLService.getUsersIdByName(user);
            String sql = "SELECT specifyuser_spprincipal.SpPrincipalID " //$NON-NLS-1$
                + "FROM specifyuser_spprincipal WHERE SpecifyUserID=?"; //$NON-NLS-1$
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, myUserId);
            log.debug("executing: " + pstmt.toString()); //$NON-NLS-1$
            ResultSet spPrincipalIDSet = pstmt.executeQuery();
            while (spPrincipalIDSet.next())
            {
                String princId = spPrincipalIDSet.getString("SpPrincipalID"); //$NON-NLS-1$
                Integer princIdInt = spPrincipalIDSet.getInt("SpPrincipalID"); //$NON-NLS-1$
                sql = "SELECT distinct(spprincipal.name)," + "spprincipal.groupsubclass " //$NON-NLS-1$ //$NON-NLS-2$
                        + "FROM specifyuser_spprincipal, spprincipal " //$NON-NLS-1$
                        + "WHERE (specifyuser_spprincipal.specifyuserid= ? " //$NON-NLS-1$
                        + "AND specifyuser_spprincipal.spprincipalid= ? " //$NON-NLS-1$
                        + "AND spprincipal.spprincipalid= ?)"; //$NON-NLS-1$
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, myUserId);
                pstmt.setString(2, princId);
                pstmt.setString(3, princId);
                log.debug("findGroups() executing query:" + pstmt.toString()); //$NON-NLS-1$
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {
                    String groupName = rs.getString("name"); //$NON-NLS-1$
                    String className = rs.getString("groupsubclass"); //$NON-NLS-1$
                    SpPrincipal grp = new SpPrincipal(princIdInt);
                    grp.setName(groupName);
                    grp.setGroupSubClass(className);
                    principals.add(grp);
                }
            }

        } catch (SQLException e)
        {
            log.error("Exception caught: " + e); //$NON-NLS-1$
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null)   conn.close();
                if (pstmt != null)  pstmt.close();
            } catch (SQLException e)
            {
                log.error("addUser Exception caught: " + e.toString()); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return principals;
    }
}

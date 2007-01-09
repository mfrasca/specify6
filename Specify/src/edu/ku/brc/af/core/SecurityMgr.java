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
package edu.ku.brc.af.core;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.log4j.Logger;

import edu.ku.brc.ui.forms.persist.View;


/**
 * 
 * @code_status Unknown
 * 
 * @author megkumin
 *
 */
public abstract class SecurityMgr
{
    private static final Logger  log = Logger.getLogger(SecurityMgr.class);
    public enum SECURITY_LEVEL {None, Read, ReadWrite, ReadWriteDelete}
    protected static SecurityMgr instance = null;
    protected SECURITY_LEVEL currentLevel = SECURITY_LEVEL.ReadWriteDelete;
    
    /**
     * Returns a Permission level by name
     * @return the security level
     */
    public abstract SECURITY_LEVEL getPermissionLevel();
    
    /**
     * Returns whether a users is authenticated to use this database
     * @return boolean
     */
    public abstract boolean authenticate();
    /**
     * @param args - 
     * void
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

    }
    /**
     * Returns the instance of the SecurityMgr.
     * @return the instance of the SecurityMgr.
     */
    public static SecurityMgr getInstance()
    {
        if (instance != null)
        {
            return instance;
            
        }
        // else
        String factoryName = AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    return System.getProperty(
                    "edu.ku.brc.af.core.SecurityMgrFactory");}});
            
        if (factoryName != null) 
        {
            try 
            {
                instance = (SecurityMgr)Class.forName(factoryName).newInstance();
                return instance;
                 
            } catch (Exception e) 
            {
                InternalError error = new InternalError("Can't instantiate SecurityMgr factory " + factoryName);
                error.initCause(e);
                throw error;
            }
        }
        return null;
    }
}

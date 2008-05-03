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
package edu.ku.brc.af.auth;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import edu.ku.brc.af.auth.specify.module.DbLoginCallbackHandler;
import edu.ku.brc.af.auth.specify.module.SpDBConfiguration;
import edu.ku.brc.af.auth.specify.policy.CompositePolicy;
import edu.ku.brc.af.auth.specify.policy.DatabasePolicy;
import edu.ku.brc.specify.datamodel.SpecifyUser;

/**
 * @author megkumin
 *
 * @code_status Beta
 *
 * Created Date: Jul 19, 2007
 *
 */
public class JaasContext
{
    private static final Logger log    = Logger.getLogger(JaasContext.class);
    public static String        url    = "";
    public static String        driver = "";

    /**
     * Composite policy will allow us to piggy back our own policy definition onto of the 
     * default system policy.
     * 
     * We have a database backed policy definition, so rather than granting our permissions 
     * through the file system, ours are stored within the database
     */
    public void createDatabaseBackedPolicyDefinition()
    {
        Policy defaultPolicy = Policy.getPolicy();
        DatabasePolicy dbPolicy = new DatabasePolicy();
        List<Policy> policies = new ArrayList<Policy>(2);
        policies.add(defaultPolicy);
        policies.add(dbPolicy);
        CompositePolicy p = new CompositePolicy(policies);
        Policy.setPolicy(p);
        SecurityManager sm = new SecurityManager();
        System.setSecurityManager(sm);
    }
    
    /**
     * Methods
     * @param user - the username credential supplied by the user
     * @param pass - the password credential supplied by the user
     * @param url - the url of the database
     * @param driver -  the driver for the database
     */
    public boolean jaasLogin(String user, String pass, String url, String driver)
    {
    	if (1==1) return true;
    	
        JaasContext.url = url;
        JaasContext.driver = driver;
        boolean loginSuccess = false;
        try
        {
        	Configuration.setConfiguration(SpDBConfiguration.getInstance());
            createDatabaseBackedPolicyDefinition();
            DbLoginCallbackHandler spcbh = new DbLoginCallbackHandler(user, pass, url, driver);
            LoginContext lc = new LoginContext("SpLogin", spcbh);
            lc.login();
            loginSuccess = true;
            SpecifyUser.setCurrentSubject(lc.getSubject());
            
        } catch (LoginException lex)
        {
            log.error("jaasLogin() - " + lex.getClass().getName() + ": " + lex.getMessage());
            log.error("jaasLogin() - user failed to login using through jaas framework");
        } catch (Exception ex)
        {
            log.error("jaasLogin() - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
        return loginSuccess;
    }
}

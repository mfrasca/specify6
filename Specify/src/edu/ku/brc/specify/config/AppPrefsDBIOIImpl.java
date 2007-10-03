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
package edu.ku.brc.specify.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;

import org.apache.log4j.Logger;

import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.prefs.AppPreferences.AppPrefsIOIFace;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.specify.datamodel.SpAppResource;
import edu.ku.brc.specify.datamodel.SpAppResourceData;
import edu.ku.brc.specify.datamodel.SpecifyUser;

/**
 * This class is responsible for performing the "database" based IO for the prefs.
 * 
 * @code_status Complete
 * 
 * @author rods
 *
 */
public class AppPrefsDBIOIImpl implements AppPrefsIOIFace
{
    protected static final Logger log       = Logger.getLogger(AppPrefsDBIOIImpl.class);
    protected static final String PREF_NAME = "preferences";
    
    protected AppPreferences appPrefsMgr   = null;
    protected SpAppResource  spAppResource = null;
    protected boolean        found         = false;
    
    /**
     * Constructor.
     */
    public AppPrefsDBIOIImpl()
    {
        
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.prefs.AppPreferences.AppPrefsIOIFace#setAppPrefsMgr(edu.ku.brc.af.prefs.AppPreferences)
     */
    public void setAppPrefsMgr(final AppPreferences appPrefsMgr)
    {
        this.appPrefsMgr = appPrefsMgr;
    }
    
    /**
     * Check to make sure we have prefs.
     */
    protected void checkForAppPrefs()
    {
        if (appPrefsMgr == null)
        {
            throw new RuntimeException("The AppPreferences is null for " + this.getClass().getCanonicalName());
        }
        
        load();
    }

    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.prefs.AppPreferences.AppPrefsIOIFace#exists(edu.ku.brc.af.prefs.AppPreferences)
     */
    public boolean exists()
    {
        load(); // throws exception on error
        return found;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.prefs.AppPreferences.AppPrefsIOIFace#lastSavedDate(edu.ku.brc.af.prefs.AppPreferences)
     */
    public Date lastSavedDate()
    {
        load(); // throws exception on error
        
        return spAppResource.getTimestampModified();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.af.prefs.AppPreferences.AppPrefsIOIFace#load(edu.ku.brc.af.prefs.AppPreferences)
     */
    public void load()
    {
        log.debug("loading AppPrefsDBIOIImpl");
        if (spAppResource == null && appPrefsMgr != null)
        {
            log.debug("loading creating Properties");
            Properties properties = new Properties(); // must be done fist thing
            appPrefsMgr.setProperties(properties);

            DataProviderSessionIFace session = null;
            try
            {
                session = DataProviderFactory.getInstance().createSession();
                if (session != null)
                {
                    List<?> list = session.getDataList(SpAppResource.class, "name", PREF_NAME);
                    if (list.size() == 0)
                    {
                        log.debug("creating AppResource");
                        spAppResource = new SpAppResource();
                        spAppResource.initialize();
                        
                        spAppResource.setName(PREF_NAME);
                        spAppResource.setLevel((short)3); // TODO WHAT LEVEL IS USER???????
                        SpecifyUser user = SpecifyUser.getCurrentUser();
                        spAppResource.setSpecifyUser(user);

                        SpAppResourceData appData = new SpAppResourceData();
                        appData.initialize();
                        appData.setSpAppResource(spAppResource);
                        spAppResource.getSpAppResourceDatas().add(appData);
                        
                        found = false;
                        
                    } else
                    {
                        spAppResource = (SpAppResource)list.get(0);
                        SpAppResourceData ard = null;
                        if (spAppResource.getSpAppResourceDatas().size() == 0)
                        {
                            /*
                            ard = new AppResourceData();
                            ard.initialize();
                            appResource.getSpAppResourceDatas().add(ard);
                            ard.setAppResource(appResource);
                            Transaction trans = session.beginTransaction();
                            session.save(appResource);
                            trans.commit();
                            */
                            throw new RuntimeException("AppResource pref name["+PREF_NAME+"] has not AppResourceData object.");
                            
                        }
                        // else
                        ard = spAppResource.getSpAppResourceDatas().iterator().next();
                         
                        properties.load(new ByteArrayInputStream(ard.getData()));
                        
                        found = true;
                    }
                }
                
            } catch (Exception ex)
            {
                ex.printStackTrace();
                log.error(ex);
                
            } finally 
            {
                 if (session != null)
                 {
                     session.close();
                 }
            }
        }    
    }
    

    /* (non-Javadoc)
     * @see edu.ku.brc.af.prefs.AppPreferences.AppPrefsIOIFace#save(edu.ku.brc.af.prefs.AppPreferences)
     */
    public void flush() throws BackingStoreException
    {
        load(); // throws exception on error
        
        if (spAppResource != null && appPrefsMgr.isChanged())
        {
            
            if (spAppResource.getSpAppResourceDatas().size() == 0)
            {
                throw new RuntimeException("AppResource has no AppResourceData object!");
            }
            
            try
            {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                appPrefsMgr.getProperties().store(byteOut, "Remote User Prefs");
                appPrefsMgr.setChanged(false);
                
                SpAppResourceData apData = spAppResource.getSpAppResourceDatas().iterator().next();
                if (apData != null)
                {
                    //String dataStr = new String(byteOut.toByteArray());
                    //log.debug(dataStr);
                    apData.setData(byteOut.toByteArray());
                    
                } else
                {
                    log.error("AppResourceData shouldn't be null!");
                }
                byteOut.close();
                
                DataProviderSessionIFace session = null;
                try 
                {
                    session = DataProviderFactory.getInstance().createSession();
                    if (session != null)
                    {
                        session.beginTransaction();
                        
                        session.saveOrUpdate(spAppResource);
                        
                        session.commit();
                    }

                } catch (Exception ex) 
                {
                    if (session != null)
                    {
                        session.rollback();
                    }
                    
                    log.error(ex);
                    
                    throw new BackingStoreException(ex);
                    
                } finally 
                {
                    if (session != null)
                    {
                        session.close();
                    }
                } 
                
            } catch (IOException ex)
            {
                throw new BackingStoreException(ex);
            }
        } else
        {
            log.error("Number of ResourceData objects: "+spAppResource.getSpAppResourceDatas().size());
        }
    }
}

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

import static edu.ku.brc.helpers.XMLHelper.getAttr;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import edu.ku.brc.af.core.AppContextMgr;
import edu.ku.brc.af.core.AppResourceIFace;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.dbsupport.DataProviderFactory;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.exceptions.ConfigurationException;
import edu.ku.brc.helpers.XMLHelper;
import edu.ku.brc.specify.datamodel.Agent;
import edu.ku.brc.specify.datamodel.AppResource;
import edu.ku.brc.specify.datamodel.AppResourceDefault;
import edu.ku.brc.specify.datamodel.Collection;
import edu.ku.brc.specify.datamodel.CollectionType;
import edu.ku.brc.specify.datamodel.PickList;
import edu.ku.brc.specify.datamodel.PickListItem;
import edu.ku.brc.specify.datamodel.SpecifyUser;
import edu.ku.brc.specify.datamodel.ViewSetObj;
import edu.ku.brc.ui.ChooseFromListDlg;
import edu.ku.brc.ui.CustomDialog;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.ToggleButtonChooserDlg;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;
import edu.ku.brc.ui.ToggleButtonChooserDlg.Type;
import edu.ku.brc.ui.db.PickListItemIFace;
import edu.ku.brc.ui.db.ViewBasedSearchDialogIFace;
import edu.ku.brc.ui.forms.FormDataObjIFace;
import edu.ku.brc.ui.forms.ViewSetMgr;
import edu.ku.brc.ui.forms.persist.View;
import edu.ku.brc.ui.forms.persist.ViewSet;

/**
 * This class provides the current context of the Specify application. The context consists of the following:<br>
 * <ol>
 * <li>The User Name
 * <li>The Database Name (database connection)
 * <li>The Specify User Object
 * <li>The Collection
 * <li>The CollectionType
 * <li>The Discipline Name
 * </ol>
 * <p>The SpecifyAppResourceDefaultMgr will place data in a <i>username</i>/<i>databaseName</i> directory in the "application data" directory of the user.
 * On Windows this is <code>\Documents and Settings\&lt;User Name&gt;\Application Data\Specify</code>.
 * On Unix platforms it is <code>/<i>user home</i>/.Specify</code> (Note: the app data dir is created by UIRegistry)</p>
 * <p>
 * The ViewSetMgrManager needs to load the "backstop" ViewSetMgr and the "user" ViewSetMgr in order for the application to work correctly.
 * So this class uses the "discipline name" to initialize the APPDATA dir with the appropriate data, which includes a "standard" set of
 * Views for that discipline. The APPDATA dir is really the "working space" of the application for a particular username/database.
 * </p>
 *
 * @code_status Complete
 *
 * @author rods
 */
public class SpecifyAppContextMgr extends AppContextMgr
{
    private static final Logger  log = Logger.getLogger(SpecifyAppContextMgr.class);

    protected List<AppResourceDefault>         appResourceList = new ArrayList<AppResourceDefault>();
    protected Hashtable<String, List<ViewSet>> viewSetHash     = new Hashtable<String, List<ViewSet>>();
    

    protected String         databaseName          = null;
    protected String         userName              = null;
    protected SpecifyUser    user                  = null;

    protected ViewSetMgr     backStopViewSetMgr    = null;
    protected AppResourceMgr backStopAppResMgr     = null;
    protected Agent          currentUserAgent      = null;


    /**
     * Singleton Constructor.
     */
    public SpecifyAppContextMgr()
    {
        // no-op
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.AppContextMgr#getInstance()
     */
    public static SpecifyAppContextMgr getInstance()
    {
        return (SpecifyAppContextMgr)AppContextMgr.getInstance();
    }


    /**
     * Returns the backstop ViewSetMgr.
     * @return the backstop ViewSetMgr.
     */
    public ViewSetMgr getBackstopViewSetMgr()
    {
        return backStopViewSetMgr;
    }

    /**
     * Return the DatabaseName
     * @return the DatabaseName
     */
    public String getDatabaseName()
    {
        return databaseName;
    }

    /**
     * Return the UserName
     * @return the UaserName
     */
    public String getUserName()
    {
        return userName;
    }
    
    /**
     * Returns the number of Collection that this user is connected to.
     * @return the number of Collection that this user is connected to.
     */
    public int getNumOfCollectionsForUser()
    {
        String sqlStr = "select count(cs) From CollectionType as ct Inner Join ct.specifyUser as user Inner Join ct.collections as cs where user.specifyUserId = "+user.getSpecifyUserId();
        
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        Object                   result     = session.getData(sqlStr);
        int                      count      =  result != null ? (Integer)result : 0;
        session.close();
        return count;
        
    }
    
    /**
     * @param sessionArg
     * @return
     */
    public List<Integer> getCollectionIdList(final DataProviderSessionIFace sessionArg)
    {
        Vector<Integer> list   = new Vector<Integer>();
        SpecifyUser     spUser = SpecifyUser.getCurrentUser();
        if (spUser != null)
        {
            sessionArg.attach(spUser);
            for (CollectionType types : spUser.getCollectionTypes())
            {
                for (Collection collection : types.getCollections())
                {
                    list.add(collection.getCollectionId().intValue());
                }
            }
        } else
        {
            log.error("SpecifyUser was null!");
        }
        return list;
    }

    /**
     * Sets up the "current" Collection by first checking prefs for the most recent primary key,
     * if it can't get it then it asks the user to select one. (Note: if there is only one it automatically chooses it)
     * @param user the user object of the current object
     * @param alwaysAsk indicates the User should always be asked which Collection to use
     * @return the current Collection or null
     */
    @SuppressWarnings("unchecked")
    protected Collection setupCurrentCollection(final DataProviderSessionIFace sessionArg, final SpecifyUser user, final boolean alwaysAsk)
    {
        final String prefName = mkUserDBPrefName("recent_collection_id");
        
        Collection collection = Collection.getCurrentCollection();

        if (Collection.getCurrentCollection() == null || alwaysAsk)
        {
            AppPreferences appPrefs    = AppPreferences.getRemote();
            boolean        askToSelect = true;
            if (!alwaysAsk)
            {
                String recentIds = appPrefs.get(prefName, null);
                if (StringUtils.isNotEmpty(recentIds))
                {
                    List list = sessionArg.getDataList("From Collection where collectionId = " + recentIds);
                    if (list.size() == 1)
                    {
                        collection  = (Collection)list.get(0);
                        askToSelect = false;
                    }
                }
            }

            if (askToSelect)
            {
                String queryStr = "select cs From CollectionType as ct Inner Join ct.specifyUser as user Inner Join ct.collections as cs where user.specifyUserId = "+user.getSpecifyUserId();
                Hashtable<String, Collection> collectionHash = new Hashtable<String, Collection>();
                for (Object obj : sessionArg.getDataList(queryStr))
                {
                    Collection cs = (Collection)obj;
                    collectionHash.put(cs.getCollectionName(), cs);
                }
                
                if (collectionHash.size() == 1)
                {
                    collection = collectionHash.elements().nextElement();

                } else if (collectionHash.size() > 0)
                {
                    //Collections.sort(list); // Why doesn't this work?
                    
                    List<Collection> list = new Vector<Collection>();
                    list.addAll(collectionHash.values());
                    int selectColInx = -1;
                    if (collection != null)
                    {
                        int i = 0;
                        for (Collection c : list)
                        {
                            if (c.getId().intValue() == collection.getId().intValue())
                            {
                                selectColInx = i;
                                break;
                            }
                            i++;
                        }
                    }

                    ToggleButtonChooserDlg<Collection> dlg = new ToggleButtonChooserDlg<Collection>((Frame)UIRegistry.get(UIRegistry.FRAME),
                                                                                                  "Choose a Collection",  // TODO I18N
                                                                                                  null,
                                                                                                  list,
                                                                                                  IconManager.getIcon("Collection"),
                                                                                                  CustomDialog.OKCANCEL, Type.RadioButton);
                    dlg.setSelectedIndex(selectColInx);
                    dlg.setModal(true);

                    UIHelper.centerAndShow(dlg);

                    if (!dlg.isCancelled())
                    {
                        collection = dlg.getSelectedObject();
                    }
                    
                } else
                {
                    // Accession / Registrar / Director may not be assigned to any Collection
                    // Or for a stand alone Accessions Database there may not be any 
                }

                if (collection != null)
                {
                    appPrefs.put(prefName, (Long.toString(collection.getCollectionId())));
                }
            }
        }
        
        Collection.setCurrentCollection(collection);
        Collection.setCurrentCollectionIds(getCollectionIdList(sessionArg));
        
        return collection;
    }

    /**
     * Helper function to create a user and database centric pref name
     * @param prefName the pref names
     * @return  a user and database centric pref name
     */
    protected String mkUserDBPrefName(final String prefName)
    {
        return prefName + "." + userName+ "." + databaseName;
    }

    /**
     * Finds a AppResourceDefault from an "object" list where it matches the user, CatSeries and ColObjdef.
     * @param appResDefList the list to search
     * @param userArg the Specify user
     * @param catSeries the Collection
     * @param collType the CollectionType
     * @return the AppResourceDefault object or null
     */
    protected AppResourceDefault find(final List<?>          appResDefList,
                                      final SpecifyUser      userArg,
                                      final Collection    catSeries,
                                      final CollectionType collType)
    {
        log.debug("finding AppResourceDefault");
        for (Object obj : appResDefList)
        {
            AppResourceDefault ard = (AppResourceDefault)obj;

            SpecifyUser      spUser = ard.getSpecifyUser();
            Collection    cs        = ard.getCollection();
            CollectionType ct    = ard.getCollectionType();

            if (spUser != null && spUser.getSpecifyUserId() == userArg.getSpecifyUserId() &&
                cs != null && cs.getCollectionId() == catSeries.getCollectionId() &&
                ct != null && ct.getCollectionTypeId() == collType.getCollectionTypeId())
            {
                return ard;
            }
        }
        return null;
    }

    /**
     * Creates an AppResourceDefault object from a directory (note the Id will be null).
     * @param dir the directory in question)
     * @return a new AppResourceDefault object
     */
    protected AppResourceDefault createAppResourceDefFromDir(final File dir)
    {
        log.debug("Creating AppResourceDef from Dir");
        AppResourceDefault appResDef = new AppResourceDefault();
        appResDef.initialize();

        ViewSetMgr viewSetMgr = new ViewSetMgr(dir);
        for (ViewSet vs : viewSetMgr.getViewSets())
        {
            ViewSetObj vso = new ViewSetObj();
            vso.initialize();

            // Set up File Name to load the ViewSet
            vso.setFileName(dir.getAbsoluteFile() + File.separator + vs.getFileName());

            if (false)
            {
                String dataStr = vso.getDataAsString(); // causes the file to be loaded and returned into this string

                // now clear the file names o it thinks it is a database object,
                // and not created from disk
                vso.setFileName(null);

                // Now set the Blob from the string
                vso.setDataAsString(dataStr);
            }

            vso.setLevel((short)0);
            vso.setName(vs.getFileName());

            vso.getAppResourceDefaults().add(appResDef);
            appResDef.getViewSets().add(vso);

        }

        AppResourceMgr appResMgr = new AppResourceMgr(dir);
        for (AppResource appRes : appResMgr.getAppResources())
        {
            appRes.getAppResourceDefaults().add(appResDef);
            appResDef.getAppResources().add(appRes);
        }
        return appResDef;
    }

    /**
     * For debug purposes, display the contents of a AppResourceDefault
     * @param appResDef AppResourceDefault
     * @return string of info
     */
    protected String getAppResDefAsString(final AppResourceDefault appResDef)
    {
        SpecifyUser      spUser = appResDef.getSpecifyUser();
        Collection    cs   = appResDef.getCollection();
        CollectionType ct  = appResDef.getCollectionType();

        StringBuilder strBuf = new StringBuilder();
        strBuf.append("CS["+(cs != null ? cs.getCollectionName() : "null") + "]");
        strBuf.append(" SU["+(spUser != null ? spUser.getName() : "null") + "]");
        strBuf.append(" COD["+(ct != null ? ct.getName() : "null") + "]");
        strBuf.append(" DSP["+appResDef.getDisciplineType() + "]");
        strBuf.append(" UTYP["+appResDef.getUserType() + "]");
        log.debug("AppResDefAsString - "  + strBuf.toString());
        return strBuf.toString();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.AppContextMgr#setContext(java.lang.String, java.lang.String, boolean)
     */
    public CONTEXT_STATUS setContext(final String  databaseName,
                                     final String  userName,
                                     final boolean startingOver)
    {
        log.debug("setting context - databaseName: [" + databaseName + "] userName: [" + userName + "]");
        this.databaseName = databaseName;
        this.userName     = userName;
        
        // This is where we will read it in from the Database
        // but for now we don't need to do that.
        //
        // We need to search for User, Collection, CollectionType and UserType
        // Then

        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();

        List<?> list = session.getDataList(SpecifyUser.class, "name", userName);
        if (list.size() == 1)
        {
            user = (SpecifyUser)list.get(0);
            user.getAgent(); // makes sure the Agent is not lazy loaded
            user.getAgent().getName();
            session.evict( user.getAgent());
            SpecifyUser.setCurrentUser(user);

        } else
        {
            throw new RuntimeException("The user ["+userName+"] could  not be located as a Specify user.");
        }

        // First we start by getting all the Collection that the User want to
        // work with for this "Context" then we need to go get all the Default View and
        // additional XML Resources.
        
        // Ask the User to choose which Collection they will be working with
        Collection collection = setupCurrentCollection(session, user, startingOver);
        if (collection == null)
        {
            // Return false but don't mess with anything that has been set up so far
            currentStatus  = currentStatus == CONTEXT_STATUS.Initial ? CONTEXT_STATUS.Error : CONTEXT_STATUS.Ignore;
            return currentStatus;
        }
        Hashtable<String, String> dispHash = new Hashtable<String, String>();
        
        String userType = user.getUserType();
        log.debug("User["+user.getName()+"] Type["+userType+"]");

        userType = StringUtils.replace(userType, " ", "").toLowerCase();
        log.debug("Def Type["+userType+"]");
        
        appResourceList.clear();
        viewSetHash.clear();

        List<?> appResDefList = session.getDataList( "From AppResourceDefault where specifyUserId = "+user.getSpecifyUserId());

        CollectionType ct = collection.getCollectionType();
        CollectionType.setCurrentCollectionType(ct);
    
            
        log.debug("Adding AppResourceDefs from Collection and ColObjDefs");

        log.debug("  ColObjDef["+ct.getName()+"]");
        
        dispHash.put(ct.getDiscipline(), ct.getDiscipline());
        
        AppResourceDefault appResourceDef = find(appResDefList, user, collection, ct);
        if (appResourceDef != null)
        {
            log.debug("Adding1 "+getAppResDefAsString(appResourceDef));
            appResourceList.add(appResourceDef);
        }


        // Add Backstop for Discipline and User Type
        for (String discipline : dispHash.keySet())
        {
            log.debug("****** Trying add Backstop for ["+discipline+"]["+userType+"]");

            File dir = XMLHelper.getConfigDir(discipline.toLowerCase() + File.separator + userType);
            if (dir.exists())
            {
                AppResourceDefault appResDef = createAppResourceDefFromDir(dir);
                appResDef.setDisciplineType(discipline);
                appResDef.setUserType(userType);
                appResDef.setSpecifyUser(user);//added to fix not-null constraints
                appResDef.setCollection(Collection.getCurrentCollection());
                appResDef.setCollectionType(CollectionType.getCurrentCollectionType());
                
                log.debug("Adding2 "+getAppResDefAsString(appResDef));
                appResourceList.add(appResDef);
                
            } else
            {
                log.debug("***** Couldn't add Backstop for ["+discipline+"]["+userType+"] ["+dir.getAbsolutePath()+"]");
            }
        }

        // Add Backstop for just the Discipline
        for (String discipline : dispHash.keySet())
        {
            log.debug("***** Trying add Backstop for ["+discipline.toLowerCase()+"]");
            File dir = XMLHelper.getConfigDir(discipline.toLowerCase());
            if (dir.exists())
            {
                AppResourceDefault appResDef = createAppResourceDefFromDir(dir);
                appResDef.setDisciplineType(discipline);
                appResDef.setSpecifyUser(user);
                
                appResDef.setCollection(collection);
                appResDef.setCollectionType(ct);

                log.debug("Adding3 "+getAppResDefAsString(appResDef));
                appResourceList.add(appResDef);
                
            } else
            {
                log.debug("***** Couldn't add Backstop for ["+discipline+"] ["+dir.getAbsolutePath()+"]");
            }
        }

        backStopViewSetMgr = new ViewSetMgr(XMLHelper.getConfigDir("backstop"));
        backStopAppResMgr  = new AppResourceMgr(XMLHelper.getConfigDir("backstop"));
        
        currentStatus = CONTEXT_STATUS.OK;
        
        session.close();
        
        return currentStatus;
    }

    /**
     * Returns a list of ViewSets from a AppResourceDefault, The ViewSets are created from the ViewSetObj.
     * @param appResDef the AppResourceDefault
     * @return list of ViewSet objects
     */
    protected List<ViewSet> getViewSetList(final AppResourceDefault appResDef)
    {
        log.debug("Looking up["+appResDef.getUniqueIdentifer()+"]["+appResDef.getVerboseUniqueIdentifer()+"]");
        
        Boolean reloadViews = AppPreferences.getLocalPrefs().getBoolean("reload_views", false);
        if (reloadViews)
        {
            viewSetHash.clear();
        }
        
        List<ViewSet> viewSetList = viewSetHash.get(appResDef.getUniqueIdentifer());
        if (viewSetList == null)
        {
            DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
            if (appResDef.getAppResourceDefaultId() != null)
            {
                session.attach(appResDef);
            }
            viewSetList = new Vector<ViewSet>();
            for (ViewSetObj vso : appResDef.getViewSets())
            {
                try
                {
                    Element root = XMLHelper.readStrToDOM4J(vso.getDataAsString());
                    viewSetList.add(new ViewSet(root));

                } catch (Exception ex)
                {
                    log.error(vso.getName());
                    log.error(ex);
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
            viewSetHash.put(appResDef.getUniqueIdentifer(), viewSetList);
            session.close();
        }
        return viewSetList;
    }

    /**
     * Finds a View by name using a CollectionType.
     * @param viewName the name of the view
     * @param collType the CollectionType
     * @return the view or null
     */
    public View getView(final String viewName, final CollectionType collType)
    {
        log.debug("Looking Up View ["+viewName+"] collType["+(collType != null ? collType.getName() : "null")+"]");
        
        boolean fndColObjDef = false;
        for (AppResourceDefault appResDef : appResourceList)
        {
            log.debug("Looking["+(appResDef.getCollectionType() != null ? appResDef.getCollectionType().getName() : "null")+"]["+(collType != null ? collType.getName() : "null")+"]");
            
            if (appResDef.getCollectionType() != null && appResDef.getCollectionType() == collType)
            {
                fndColObjDef = true;
                for (ViewSet vs : getViewSetList(appResDef))
                {
                    View view = vs.getView(viewName);
                    if (view != null)
                    {
                        return view;
                    }
                }
            }
        }

        // This is searching the BackStops by Discipline and User Type
        // which were created dynamically
        if (!fndColObjDef)
        {
            String disciplineName = collType != null ? collType.getDiscipline() : null;
            String userType       = SpecifyUser.getCurrentUser().getUserType();
            userType = StringUtils.replace(userType, " ", "").toLowerCase();

            // Search Using the colObjectDef's discipline
            for (AppResourceDefault appResDef : appResourceList)
            {
                String dType = appResDef.getDisciplineType();
                String uType = appResDef.getUserType();

                log.debug("appResDef's DisciplineType["+dType+"] appResDef's UserType["+uType+"] User's userType["+userType+"]");
                
                if ((dType != null && disciplineName != null && dType.equals(disciplineName) || 
                    (dType == null || disciplineName == null)) && (uType == null || uType.equals(userType)) ||
                    (userType != null && dType != null && dType.equals(userType)))
                {
                    for (ViewSet vs : getViewSetList(appResDef))
                    {
                        View view = vs.getView(viewName);
                        if (view != null)
                        {
                            return view;
                        }
                    }
                }
            }
        }
        
        for (ViewSet vs : backStopViewSetMgr.getViewSets())
        {
            View view = backStopViewSetMgr.getView(vs.getName(), viewName);
            if (view != null)
            {
                return view;
            }
        }
        throw new RuntimeException("Can't find View ["+viewName+"] collType["+(collType != null ? collType.getName() : "null")+"] ["+fndColObjDef+"]");
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.AppResourceDefaultIFace#getView(java.lang.String, java.lang.String)
     */
    public View getView(final String viewSetName, final String viewName)
    {

        if (StringUtils.isEmpty(viewName))
        {
            throw new RuntimeException("Sorry the View Name cannot be empty.");
        }

        if (StringUtils.isEmpty(viewSetName))
        {
            throw new RuntimeException("Sorry not empty or null ViewSetNames use the call with CollectionType instead.");
        }

        for (AppResourceDefault appResDef : appResourceList)
        {
            log.debug("getView "+getAppResDefAsString(appResDef)+"  ["+appResDef.getUniqueIdentifer()+"]");

            for (ViewSet vs : getViewSetList(appResDef))
            {
                log.debug("VS  ["+vs.getName()+"]["+viewSetName+"]");

                if (vs.getName().equals(viewSetName))
                {
                    View view = vs.getView(viewName);
                    if (view != null)
                    {
                        return view;
                    }
                }
            }
        }

        return backStopViewSetMgr.getView(viewSetName, viewName);

    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.AppResourceDefaultIFace#getResource(java.lang.String)
     */
    public AppResourceIFace getResource(final String name)
    {
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        try
        {
            for (AppResourceDefault appResDef : appResourceList)
            {
                if (appResDef.getAppResourceDefaultId() != null)
                {
                    session.attach(appResDef);
                }
                
                for (AppResourceIFace appRes : appResDef.getAppResources())
                {
                    if (appRes.getName().equals(name))
                    {
                        return appRes;
                    }
                }
            }
        } catch (Exception ex)
        {
            log.error(ex);
            
        } finally 
        {
            session.close();
            session = null;

        }
        
        if (backStopAppResMgr == null)
        {
            throw new RuntimeException("The backStopAppResMgr is null which means somehow a call was made to this method before the backStopAppResMgr was initialized.");
        }
        return backStopAppResMgr.getAppResource(name);
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.AppResourceDefaultMgr#getResourceAsDOM(java.lang.String)
     */
    public Element getResourceAsDOM(final String name)
    {
        AppResourceIFace appResource = getResource(name);
        if (appResource != null && appResource instanceof AppResource)
        {
            DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
            AppResource              appRes  = (AppResource)appResource;
            try
            {
                if (appRes.getAppResourceId() != null)
                {
                    session.attach(appRes);
                }
                
                if (appRes.getMimeType() != null && appRes.getMimeType().equals("text/xml"))
                {
                    try
                    {
                        return XMLHelper.readStrToDOM4J(appRes.getDataAsString());
    
                    } catch (Exception ex)
                    {
                        log.error(ex);
                        throw new RuntimeException(ex);
                    }
                }
                // else
                throw new RuntimeException("MimeType was not 'text/xml'");
                
            } catch (Exception ex)
            {
                log.error(ex);
                
            } finally 
            {
                session.close();
                session = null;
            }
        } else
        {
            log.debug("Couldn't find ["+name+"]");
        }
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.af.core.AppResourceDefaultMgr#getResourceByMimeType(java.lang.String)
     */
    public List<AppResourceIFace> getResourceByMimeType(final String mimeType)
    {
        List<AppResourceIFace> list = new ArrayList<AppResourceIFace>();
        for (AppResourceDefault appResDef : appResourceList)
        {
            for (AppResourceIFace appRes : appResDef.getAppResources())
            {
                //log.debug("["+appRes.getMimeType()+"]["+mimeType+"]");
                if (appRes.getMimeType() != null && appRes.getMimeType().equals(mimeType))
                {
                    list.add(appRes);
                }
            }
        }
        return list;
    }



    //----------------------------------------------------------------
    //-- Inner Classes
    //----------------------------------------------------------------
    class AppResourceMgr
    {
        protected File locationDir;
        protected Hashtable<String, AppResource> appResources = null;

        public AppResourceMgr(final File file)
        {
            locationDir = file;
            appResources = new Hashtable<String, AppResource>();
            init(locationDir);

        }

        public AppResource getAppResource(final String name)
        {

            return appResources.get(name);
        }

        /**
         * @param appRes
         */
        public void addAppRes(final AppResource appRes)
        {
            appResources.put(appRes.getName(), appRes);
        }

        /**
         * Reads in the App Resource for a discipline
         */
        protected void init(final File file)
        {
            if (file.exists())
            {
                try
                {
                    Element root = XMLHelper.readFileToDOM4J(new FileInputStream(new File(file.getAbsoluteFile() + File.separator + "app_resources.xml")));
                    if (root != null)
                    {
                        for ( Iterator<?> i = root.elementIterator( "file" ); i.hasNext(); )
                        {
                            Element fileElement = (Element) i.next();
                            String  name        = getAttr(fileElement, "name", null);
                            if (appResources.get(name) == null)
                            {
                                Integer level    = getAttr(fileElement, "level", 0);
                                String mimeType  = getAttr(fileElement, "mimetype", null);
                                String desc      = getAttr(fileElement, "description", null);
                                String fileName  = getAttr(fileElement, "file", null);
                                String metaData  = getAttr(fileElement, "metadata", null);

                                // these can go away once we validate the XML
                                if (level == null)
                                {
                                    throw new RuntimeException("AppResource level cannot be null!");
                                }
                                if (StringUtils.isEmpty(mimeType))
                                {
                                    throw new RuntimeException("AppResource mimeType cannot be null!");
                                }
                                if (StringUtils.isEmpty(fileName))
                                {
                                    throw new RuntimeException("AppResource file cannot be null!");
                                }

                                File resFile = new File(file.getAbsoluteFile() + File.separator + fileName);
                                if (!resFile.exists())
                                {
                                    //throw new RuntimeException("AppResource file cannot be found at["+resFile.getAbsolutePath()+"]");
                                    log.error("AppResource file cannot be found at["+resFile.getAbsolutePath()+"]");
                                }

                                AppResource appRes = new AppResource();
                                appRes.initialize();
                                appRes.setLevel(level.shortValue());
                                appRes.setName(name);
                                appRes.setMimeType(mimeType);
                                appRes.setDescription(desc);
                                appRes.setMetaData(metaData);
                                appRes.setSpecifyUser(user);//added to fix not-null constraint issue

                                appRes.setFileName(resFile.getAbsolutePath());

                                log.debug("Adding ["+name+"] ["+resFile.getAbsolutePath()+"]");
                                
                                appResources.put(name, appRes);

                            } else
                            {
                                log.error("AppResource Name["+name+"] is in use.");
                            }
                        }
                    } else
                    {
                        String msg = "The root element for the document was null!";
                        log.error(msg);
                        throw new ConfigurationException(msg);
                    }

                } catch (Exception ex)
                {
                    ex.printStackTrace();
                    log.error(ex);
                }
            }
        }

        /**
         * Returns a list of all the AppResources
         * @return a list of all the AppResources
         */
        public List<AppResource> getAppResources()
        {
            return Collections.list(appResources.elements());
        }

    }

    
    
    /**
     * Returns a Default Object from Prefs if there is one.
     */
    @SuppressWarnings("cast")
    public PickListItemIFace getDefaultPickListItem(final String pickListName, final String title)
    {
        PickListItemIFace dObj        = null;
        Collection     catSeries   = Collection.getCurrentCollection();
        String            prefName    = (catSeries != null ? catSeries.getIdentityTitle() : "") + pickListName + "_DefaultId";
        AppPreferences    appPrefs    = AppPreferences.getRemote();
        String            idStr       = appPrefs.get(prefName, null);
        
        if (StringUtils.isNotEmpty(idStr))
        {
            DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
            dObj = (PickListItemIFace)session.get(PickListItem.class, Integer.valueOf(idStr));
            session.close();
            
            if (dObj != null)
            {
                return dObj;
            }            
        }
            
        DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
        try
        {
            PickList pickList = (PickList)session.getData(PickList.class, "name", pickListName, DataProviderSessionIFace.CompareType.Equals);
            if (pickList != null)
            {
                Vector<PickListItemIFace> list = new Vector<PickListItemIFace>();
                list.addAll(pickList.getItems());
                ChooseFromListDlg<PickListItemIFace> dlg = new ChooseFromListDlg<PickListItemIFace>(null, 
                        UIRegistry.getLocalizedMessage("CHOOSE_DEFAULT_OBJECT", title), list);
                dlg.setModal(true);
                dlg.setVisible(true);
                if (!dlg.isCancelled())
                {
                    appPrefs.put(prefName, dlg.getSelectedObject().getId().toString());
                    return dlg.getSelectedObject();
                }
            }
            throw new RuntimeException("PickList name["+pickListName+"] doesn't exist.");
            
        } catch (Exception ex)
        {
            log.error(ex);
            
        } finally 
        {
            session.close();
        }
        return dObj;
    }

    
    
    /**
     * Returns a Default Object from Prefs if there is one.
     */
    @SuppressWarnings("cast")
    public FormDataObjIFace getDefaultObject(final Class<?> classObj, 
                                             final String prefPrefix,
                                             final String title,
                                             final boolean ask, 
                                             boolean useAllItems)
    {
        Collection    catSeries   = Collection.getCurrentCollection();
        FormDataObjIFace dObj        = null;
        String           prefName    = (catSeries != null ? catSeries.getIdentityTitle() : "") + prefPrefix + "_DefaultId";
        AppPreferences   appPrefs    = AppPreferences.getRemote();
        String           idStr       = appPrefs.get(prefName, null);
        if (StringUtils.isEmpty(idStr) && ask)
        {
            if (useAllItems)
            {
                class Item {
                    public FormDataObjIFace data;
                    public Item(FormDataObjIFace d) { data = d; }
                    public String toString() { return data.getIdentityTitle(); }
                }
                DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
                List<Item> items = new Vector<Item>();
                for (Object o : session.getDataList(classObj))
                {
                    items.add(new Item((FormDataObjIFace)o));
                }
                session.close();
                
                
                ChooseFromListDlg<Item> dlg = new ChooseFromListDlg<Item>(null, title, items);
                dlg.setModal(true);
                dlg.setVisible(true);
                if (!dlg.isCancelled())
                {
                    dObj = (FormDataObjIFace)dlg.getSelectedObject().data;
                    appPrefs.put(prefName, dObj.getId().toString());
                    return dObj;
                }
                
            } else
            {
                try
                {
                    ViewBasedSearchDialogIFace dlg = UIRegistry.getViewbasedFactory().createSearchDialog(null, classObj.getSimpleName()+"Search");
                    if (dlg != null)
                    {
                        dlg.setTitle(title);
                        dlg.getDialog().setVisible(true);
                        if (!dlg.isCancelled())
                        {
                            dObj = (FormDataObjIFace)dlg.getSelectedObject();
                            appPrefs.put(prefName, dObj.getId().toString());
                            return dObj;
                        }
                    }
                } catch (Exception ex)
                {
                    // it's ok 
                    // we get when it can't find the search dialog
                }
            }
        } else
        {
            DataProviderSessionIFace session = DataProviderFactory.getInstance().createSession();
            dObj = (FormDataObjIFace)session.get(classObj, Integer.valueOf(idStr));
            session.close();
        }
        return dObj;
    }
    
    //--------------------------------------------------------
    // There is no greate place for this because the Pref system
    // has to have been initialized and the Prefs are defined
    // in package edu.ku.brc.af.prefs
    //--------------------------------------------------------
    
    protected static Boolean isNewJavaVersion = null;
    
    /**
     * Returns true is the Pref's java.version match the current System properties java.version and
     * sets the Prefs appropriately (so if it has changed it will only return true the first time 
     * is is called. (see isNewJavaVersionAtAppStart).
     * @return true is the Pref's java.version match the current System properties java.version.
     */
    public static boolean isNewJavaVersion()
    {
        String javaVersionPropName = "java.version";

        String prefsJavaVersion  = AppPreferences.getLocalPrefs().get(javaVersionPropName, null);
        String systemJavaVersion = System.getProperty("java.version");
        
        boolean isNewVersion = StringUtils.isEmpty(prefsJavaVersion) || 
                               StringUtils.isEmpty(systemJavaVersion) ||
                               !prefsJavaVersion.equals(systemJavaVersion);
        if (isNewVersion)
        {
            AppPreferences.getLocalPrefs().put(javaVersionPropName, System.getProperty("java.version"));
        }
        
        if (isNewJavaVersion == null)
        {
            isNewJavaVersion = isNewVersion;
        }
        return isNewVersion;
    }
    
    /**
     * Returns whether the java.version was different when the app started, this will return 
     * the same answer each time it is called until the application terminates.
     * (see isNewJavaVersion).
     * @return
     */
    public static boolean isNewJavaVersionAtAppStart()
    {
        if (isNewJavaVersion == null)
        {
            return isNewJavaVersion();
        }
        return isNewJavaVersion;
    }


    
}

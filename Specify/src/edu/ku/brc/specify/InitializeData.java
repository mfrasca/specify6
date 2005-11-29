package edu.ku.brc.specify;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.ku.brc.specify.dbsupport.HibernateUtil;

/**
 * This is a helper class that is used for initializing data for testing 
 *
 * @author Rod Spears <rods@ku.edu>
 */
public class InitializeData 
{
    protected static Hashtable prepTypeMapper    = new Hashtable();
    protected static int       attrsId           = 0;
    protected static boolean   classesWereLoaded = false;
    protected final static Logger   log = Logger.getLogger(InitializeData.class);
    public static int getIndex(String[] aOldNames, String aNewName)
    {
        for (int i=0;i<aOldNames.length;i++)
        {
            String fieldName = aOldNames[i].substring(aOldNames[i].indexOf(".")+1, aOldNames[i].length());            
            if (aNewName.equals(fieldName))
            {
                return i;
            }
        }
        return -1;
    }
    
    /*
     * 
     */
    public static void loadAttrs()
    {
        try
        {
            
            //------------------------------
            // Load PrepTypes and Prep Attrs
            //------------------------------
            /*String[] pages = {"Formatting", "Colors", "Application"};
            String[] formattingPrefs = {"date", "java.lang.String"};
            
            PrefGroupDAO prefGroupDAO = new PrefGroupDAO();
            PrefGroup    prefGroup    = new PrefGroup();
            
            prefGroup.setName("Formatting");
            prefGroup.setCreated(new Date());
            HashSet<Preference> set = new HashSet<Preference>();
            prefGroup.setPreferences(set);
            
            for (int i=0;i<formattingPrefs.length;i++)
            {
                Preference pref = new Preference();
                pref.setName(formattingPrefs[i++]);
                pref.setValueType(formattingPrefs[i]);
                pref.setValue("");
                pref.setCreated(new Date());
                pref.setPrefGroup(prefGroup);
                
                set.add(pref);
            }
            prefGroupDAO.makePersistent(prefGroup);
            */
            
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
              
            // Clean up after ourselves
            //sessionFactory.close();
             
        } catch (Exception e)
        {
            log.error("loadAttr - ", e);
        }
        
    }
    
    /**
     * Utility method to associate an artist with a catObj
     */
    //private static void addCatalogObjCollectionEvent(CatalogObj catObj, CollectionEvent artist) {
    //    catObj.getCollectionEvent().add(artist);
    //}

    public static void main(String args[]) throws Exception 
    {
        boolean doingHibernate = false;
        if (doingHibernate) 
        {
            // Create a configuration based on the properties file we've put
            // in the standard place.
            Configuration config = new Configuration();
    
            // Tell it about the classes we want mapped, taking advantage of
            // the way we've named their mapping documents.
    
            // Get the session factory we can use for persistence
            SessionFactory sessionFactory = config.buildSessionFactory();
    
            // Ask for a session using the JDBC information we've configured
            Session session = sessionFactory.openSession();
            
            // Clean up after ourselves
            sessionFactory.close();
            log.info("Done.");
        } else
        {
            loadAttrs();
        }
    }
}

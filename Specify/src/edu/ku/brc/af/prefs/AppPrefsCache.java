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
package edu.ku.brc.af.prefs;

import java.text.SimpleDateFormat;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import edu.ku.brc.ui.ColorWrapper;
import edu.ku.brc.ui.DateWrapper;

/**
 * Creates and maintains a cache of preference entries that listen for changes to the preferences so they can always be up-to-date
 * <br><br>
 * ColorWrapper and SimpleDateFormat object can be registered into the cache and their mutal value will always be up to date, this saves
 * the consumer from having to go get them each time.
 *
 * @code_status Complete
 * 
 * @author rods
 *
 */
public class AppPrefsCache
{
    protected static final String NOT_INIT = "AppPrefs have not been initialized.";
    protected static final String BAD_ARGS = "Empty fully qualified pref name.";
    
    protected static AppPrefsCache instance = new AppPrefsCache();
    
    protected Hashtable<String, AppPrefsCacheEntry> hash     = new Hashtable<String, AppPrefsCacheEntry>();
    
    
    /**
     * Protected Default Constructor
     */
    protected AppPrefsCache()
    {
        super();
    }
    
    
    /**
     * Gets the singleton.
     * @return the singleton
     */
    public static AppPrefsCache getInstance()
    {
         return instance;
    }
    
    /**
     * Resets the internal state of the cache.
     */
    public static void reset()
    {
        AppPrefsCache.instance.hash.clear();
    }
    
    /**
     * Creates a fully qualified name using "." notation
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     * @return return the name as section.pref.attr
     */
    protected static String makeKey(final String section, final String pref, final String attrName)
    {
        return section + "." + pref + "." + attrName;
    }
    
    /**
     * Checks to make sure that the fully qualified name of the pref is not empty
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute     
     */
    protected static void checkName(final String section, final String pref, final String attrName)
    {
        if (StringUtils.isEmpty(section) || StringUtils.isEmpty(pref) || StringUtils.isEmpty(attrName))
        {
            throw new RuntimeException(BAD_ARGS);
        }
    }
    

    /**
     * The internal non-static version for registering a string
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     * @param defValue the default value
     * @return the entry for this pref
     */
    protected AppPrefsCacheEntry registerInternal(final String section, final String pref, final String attrName, final String defValue)
    {
        checkName(section, pref, attrName);
        
        String             name            = makeKey(section, pref, attrName);
        AppPrefsCacheEntry prefsCacheEntry = hash.get(name);
        if (prefsCacheEntry == null)
        {
            prefsCacheEntry = new AppPrefsCacheEntry(attrName, AppPreferences.getRemote().get(name, defValue), defValue);
            AppPreferences.getRemote().addChangeListener(name, prefsCacheEntry);
            hash.put(makeKey(section, pref, attrName), prefsCacheEntry);
        }
        return prefsCacheEntry;
    }
    
    /**
     * Register a simple string value
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     * @param defValue the default value
     */
    public static void register(final String section, final String pref, final String attrName, final String defValue)
    {
        getInstance().registerInternal(section, pref, attrName, defValue);
    }
    
    /**
     * Register a simple string value
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     */
    public static boolean remove(final String section, final String pref, final String attrName)
    {
        checkName(section, pref, attrName);
        
        // TODO error checking
        String name = makeKey(section, pref, attrName);
        AppPreferences.getRemote().remove(name);
        getInstance().hash.remove(name);
        return true;
    }
    
    /**
     * Returns a string object from the pref. NOTE: The value retrieved using this method would most
     * likely be set via the Prefs Dialog or someone else directly manipulating the 
     * preferences nodes and calling flush to set off the pref listeners.
     * 
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     * @return the string value or default value
     */
    public static String getValue(final String section, final String pref, final String attrName)
    {
        checkName(section, pref, attrName);
        
        AppPrefsCacheEntry prefsCacheEntry = getInstance().hash.get(makeKey(section, pref, attrName));
        return prefsCacheEntry != null ? prefsCacheEntry.getValue() : "";
    }
    
    /**
     * Get the prefValue, if it is new than it sets the default into the prefs, if not it gets the value and returns it
     * @param prefNode the pref's name
     * @param defValue the default value
     * @return return the pref's value
     */
    protected String checkForPref(final String fullName, final String defValue)
    {
        AppPreferences appPrefs = AppPreferences.getRemote();
        
        String prefVal;

        if (!appPrefs.exists(fullName))
        {
            appPrefs.put(fullName, defValue);
            //prefNode.flush();
                
            prefVal = defValue;
        }  else
        {
            prefVal = appPrefs.get(fullName, defValue);
        }

        return prefVal;
    }
    
    //--------------------------------------------------------------------
    // Color Support
    //--------------------------------------------------------------------

    /**
     * Creates or gets the pref node, creates an entry and then hooks it up as a listener .
     * The current value of the color because the default.
     * @param colorWrapper the color object wrapper
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     */
    public void registerInternal(final ColorWrapper colorWrapper, 
                                 final String       section, 
                                 final String       pref, 
                                 final String       attrName)
    {
        AppPreferences appPrefs = AppPreferences.getRemote();
        
        checkName(section, pref, attrName);
        
        String fullName = makeKey(section, pref, attrName);
        if (hash.get(fullName) == null)
        {
            String defValue = colorWrapper.toString();
            String prefVal  = checkForPref(fullName, defValue);
            colorWrapper.setRGB(prefVal);
            
            ColorCacheEntry colorEntry = new ColorCacheEntry(colorWrapper, fullName, prefVal, defValue);
            
            appPrefs.addChangeListener(fullName, colorEntry);
            hash.put(fullName, colorEntry);
        }

    }
    
    /**
     * Creates or gets the pref node, creates an entry and then hooks it up as a listener.
     * The current value of the color because the default.
     * @param color the color object wrapper
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     */
    public static void register(final ColorWrapper color, 
                                final String       section, 
                                final String       pref, 
                                final String       attrName)
    {
        getInstance().registerInternal(color, section, pref, attrName);
    }

    /**
     * Gets a ColorWrapper Object
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     * @return the object
     */
    public static ColorWrapper getColorWrapper(final String section, 
                                               final String pref, 
                                               final String attrName)
    {
        checkName(section, pref, attrName);
        
        ColorCacheEntry colorEntry = (ColorCacheEntry)getInstance().hash.get(makeKey(section, pref, attrName));
        if (colorEntry != null)
        {
            return colorEntry.getColorWrapper();
        }
        // else
        throw new RuntimeException("Couldn't find Date Entry ["+makeKey(section, pref, attrName)+"]");
    }


    //--------------------------------------------------------------------
    // Date Format Support
    //--------------------------------------------------------------------

    /**
     * Creates or gets the pref node, creates an entry and then hooks it up as a listener.
     * The current value of the SimpleDateFormat because the default.
     * @param simpleFormat the SimpleDateFormat object 
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     */
    public void registerInternal(final SimpleDateFormat simpleFormat, 
                                 final String           section, 
                                 final String           pref, 
                                 final String           attrName)
    {
        checkName(section, pref, attrName);
        
        String fullName = makeKey(section, pref, attrName);
        if (hash.get(fullName) == null)
        {
            String defValue = simpleFormat.toPattern();
            String prefVal  = checkForPref(fullName, defValue);
            simpleFormat.applyPattern(prefVal);
            DateFormatCacheEntry dateEntry = new DateFormatCacheEntry(simpleFormat, fullName, prefVal, defValue);
            AppPreferences.getRemote().addChangeListener(fullName, dateEntry);
            hash.put(fullName, dateEntry);
        }
    }
    
    /**
     * Creates or gets the pref node, creates an entry and then hooks it up as a listener.
     * The current value of the SimpleDateFormat because the default. 
     * @param simpleFormat the SimpleDateFormat object 
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     */
    public static void register(final SimpleDateFormat simpleFormat, 
                                final String       section, 
                                final String       pref, 
                                final String       attrName)
    {
        getInstance().registerInternal(simpleFormat, section, pref, attrName);
    }
    
    /**
     * Returns a SimpleDateFormat for date with two month and day digits and 4 year digits.
     * @return a SimpleDateFormat for date with two month and day digits and 4 year digits.
     */
    public static SimpleDateFormat getDefaultDatePattern()
    {
        SimpleDateFormat sdf = new SimpleDateFormat();
        
        String[] pieces = sdf.toPattern().split(" ");
        if (pieces != null)
        {
            String pattern = pieces[0];
            //System.out.println(pattern);
            
            //System.out.println("Months: "+ StringUtils.countMatches(pattern, "M"));
            //System.out.println("Days:   "+ StringUtils.countMatches(pattern, "d"));
            //System.out.println("Years:  "+ StringUtils.countMatches(pattern, "y"));
            
            int months = StringUtils.countMatches(pattern, "M");
            int days   = StringUtils.countMatches(pattern, "d");
            int years  = StringUtils.countMatches(pattern, "y");
            
            if (months == 1 && days == 1 && years == 2)
            {
                pattern = pattern.replace("M", "MM");
                pattern = pattern.replace("d", "dd");
                pattern = pattern.replace("yy", "yyyy");
            }
            //System.out.println(pattern);
            //System.out.println("Months: "+ StringUtils.countMatches(pattern, "M"));
            //System.out.println("Days:   "+ StringUtils.countMatches(pattern, "d"));
            //System.out.println("Years:  "+ StringUtils.countMatches(pattern, "y"));
            return new SimpleDateFormat(pattern);
            
        }
        
        return new SimpleDateFormat("MM/dd/yyyy");
    }

    /**
     * Gets a DateWrapper Object.
     * @param section the section or category of the pref
     * @param pref the pref's name
     * @param attrName the actual attribute
     * @return the object
     */
    public static DateWrapper getDateWrapper(final String section, 
                                             final String pref, 
                                             final String attrName)
    {
        checkName(section, pref, attrName);
        
        DateFormatCacheEntry dateEntry = (DateFormatCacheEntry)getInstance().hash.get(makeKey(section, pref, attrName));
        if (dateEntry != null)
        {
            return dateEntry.getDateWrapper();
            
        }
        // else
        dateEntry = instance.new DateFormatCacheEntry(getDefaultDatePattern(), section, pref, attrName);
        getInstance().hash.put(makeKey(section, pref, attrName), dateEntry);
        return dateEntry.getDateWrapper();
    }

    /**
     * Simple class to wrap color changes and get the notification of changes.
     *
     */
    public class ColorCacheEntry extends AppPrefsCacheEntry
    {
        protected ColorWrapper colorWrapper;
        
        public ColorCacheEntry(final ColorWrapper colorWrapper, String attrName, String value, String defValue)
        {
            super(attrName, value, defValue);
            this.colorWrapper = colorWrapper;
        }
        
        @Override
        public void preferenceChange(AppPrefsChangeEvent evt)
        {
            super.preferenceChange(evt);
            colorWrapper.setRGB(getValue());
        }

        public ColorWrapper getColorWrapper()
        {
            return colorWrapper;
        }
    }

    /**
     * Simple class to wrap Data format changes and get the notification of changes.
     *
     */
    public class DateFormatCacheEntry extends AppPrefsCacheEntry
    {
        protected DateWrapper dateWrapper;
        
        public DateFormatCacheEntry(final SimpleDateFormat simpleDateFormat, String attrName, String value, String defValue)
        {
            super(attrName, value, defValue);
            
            dateWrapper = new DateWrapper(simpleDateFormat);
        }
        
        @Override
        public void preferenceChange(AppPrefsChangeEvent evt)
        {
            super.preferenceChange(evt);
            
            dateWrapper.getSimpleDateFormat().applyPattern(getValue());
        }

        public DateWrapper getDateWrapper()
        {
            return dateWrapper;
        }
    }
}

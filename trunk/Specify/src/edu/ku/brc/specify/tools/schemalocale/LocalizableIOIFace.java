/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tools.schemalocale;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import edu.ku.brc.specify.datamodel.PickList;


/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Sep 28, 2007
 *
 */
public interface LocalizableIOIFace
{
    /**
     * @return
     */
    public abstract boolean load();
    
    /**
     * @return
     */
    public abstract boolean didModelChangeDuringLoad();
    
    /**
     * @param item
     * @param l
     */
    public abstract void getContainer(LocalizableJListItem item, LocalizableIOIFaceListener l);
    
    /**
     * @param container
     */
    public abstract void containerChanged(LocalizableContainerIFace container);
    
    /**
     * @param container
     * @param item
     * @return
     */
    public abstract LocalizableItemIFace getItem(LocalizableContainerIFace container, LocalizableJListItem item);
    
    /**
     * @return
     */
    public abstract Vector<LocalizableJListItem> getContainerDisplayItems();
    
    /**
     * @param container
     * @return
     */
    public abstract Vector<LocalizableJListItem> getDisplayItems(LocalizableJListItem container);
    
    /**
     * @param locale
     * @return
     */
    public abstract boolean isLocaleInUse(final Locale locale);
    
    /**
     * @return
     */
    public abstract Vector<Locale> getLocalesInUse();
    
    /**
     * @param src
     * @param dst
     */
    public abstract void copyLocale(Locale src, Locale dst);
    
    /**
     * @return
     */
    public abstract boolean save();
    
    /**
     * @param expportFile
     * @return
     */
    public abstract boolean exportToDirectory(File expportFile);
    
    /**
     * @return
     */
    public abstract boolean createResourceFiles();
    
    /**
     * The implementor MUST return all the 'common' and all the discipline specific picklists.
     * @param disciplineName the name of the discipline
     * @return
     */
    public abstract List<PickList> getPickLists(String disciplineName);
    
    /**
     * @return true if the PickLists can be created/updates/deleted.
     */
    public abstract boolean hasUpdatablePickLists();
    
    
    /**
     * @return whether the application tables should be included to be configured.
     */
    public abstract boolean shouldIncludeAppTables();
}

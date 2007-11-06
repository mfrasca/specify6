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

import java.util.Locale;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Sep 28, 2007
 *
 */
public interface LocalizableStrFactory
{

    /**
     * @return
     */
    public abstract LocalizableStrIFace create();
    
    /**
     * @param text
     * @param locale
     * @return
     */
    public abstract LocalizableStrIFace create(final String text, final Locale locale);
    
}

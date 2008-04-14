/*
     * Copyright (C) 2008  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.ui.weblink;

import com.thoughtworks.xstream.XStream;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Apr 13, 2008
 *
 */
public class WebLinkDefArg implements Cloneable
{
    protected String  name;
    protected boolean prompt;
    
    /**
     * 
     */
    public WebLinkDefArg()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @param name
     * @param prompt
     */
    public WebLinkDefArg(String name, boolean prompt)
    {
        super();
        this.name = name;
        this.prompt = prompt;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the prompt
     */
    public boolean getPrompt()
    {
        return prompt;
    }

    /**
     * @param prompt the prompt to set
     */
    public void setPrompt(boolean prompt)
    {
        this.prompt = prompt;
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        WebLinkDefArg arg = (WebLinkDefArg)super.clone();
        arg.name   = name;
        arg.prompt = prompt;
        return arg;
    }

    /**
     * @param xstream
     */
    public static void configXStream(final XStream xstream)
    {
        xstream.alias("weblinkdefarg", WebLinkDefArg.class); 
    }
}

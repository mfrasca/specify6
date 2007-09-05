/*
     * Copyright (C) 2007  The University of Kansas
     *
     * [INSERT KU-APPROVED LICENSE TEXT HERE]
     *
     */
/**
 * 
 */
package edu.ku.brc.specify.tools.fielddesc;

import java.util.List;
import java.util.Vector;

/**
 * @author rod
 *
 * @code_status Alpha
 *
 * Sep 4, 2007
 *
 */
public class Table
{
    protected String      name;
    protected List<Field> fields = new Vector<Field>();
    
    public Table(final String name)
    {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the fields
     */
    public List<Field> getFields()
    {
        return fields;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<Field> fields)
    {
        this.fields = fields;
    }
    
    public String toString()
    {
        return name;
    }
}

/* Copyright (C) 2009, University of Kansas Center for Research
 * 
 * Specify Software Project, specify@ku.edu, Biodiversity Institute,
 * 1345 Jayhawk Boulevard, Lawrence, Kansas, 66045, USA
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package edu.ku.brc.af.ui.forms.formatters;

import static edu.ku.brc.helpers.XMLHelper.xmlAttr;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author rods
 *
 * @code_status Alpha
 *
 * Created Date: Jan 17, 2007
 *
 * TODO: RSP: We should consider turning this class into a class hierarchy to remove all the type checks and switches 
 */
public class UIFieldFormatterField implements Cloneable
{
    public enum FieldType {numeric, alphanumeric, alpha, separator, year, anychar, constant}
                                                  
    private static String alphaSample        = "";
    private static String anyCharSample      = "";
    private static String alphaNumericSample = "";
    private static String anyNumericSample   = "";
    
    protected FieldType type;
    protected int       size;
    protected String    value;
    protected boolean   incrementer;
    protected boolean   byYear;
    
    static {
        for (int i=0;i<255;i++)
        {
            alphaNumericSample += "A";
            alphaSample        += "a";
            anyCharSample      += "X";
            anyNumericSample   += "N";
     }
    }
    
    /**
     * Default constructor
     */
    public UIFieldFormatterField()
    {
        type        = FieldType.alphanumeric;
        size        = 1;
        value       = "";
        incrementer = false;
        byYear      = false;
    }
    
    /**
     * @param type
     * @param size
     * @param value
     * @param incrementer
     * @param byYear
     */
    public UIFieldFormatterField(final FieldType type, 
                                 final int       size, 
                                 final String    value, 
                                 final boolean   incrementer, 
                                 final boolean   byYear)
    {
        super();
        
        this.type        = type;
        this.size        = size;
        this.value       = value;
        this.incrementer = incrementer;
        this.byYear      = byYear;
        
        if (incrementer)
        {
            this.value = UIFieldFormatterMgr.getFormatterPattern(incrementer, null, size);
        }
    }
    
    /**
     * @param type
     * @param size
     * @param value
     * @param incrementer
     */
    public UIFieldFormatterField(final FieldType type, 
                                 final int       size, 
                                 final String    value, 
                                 final boolean   incrementer)
    {
        this(type, size, value, incrementer, false);
    }
    
    /**
     * Factory that creates a new UIFieldFormatterField from a formatting string
     * @param formattingString Formatting string that defines the field 
     * @return The UIFieldFormatter corresponding to the formatting string
     * @throws UIFieldFormattingParsingException (if formatting string is invalid)
     * 
     * Note: use one of the constructors to get a separator field
     */
    public static UIFieldFormatterField factory(final String   formattingString) throws UIFieldFormatterParsingException 
    {
    	UIFieldFormatterField field = new UIFieldFormatterField();
    	
    	//Pattern pattern = Pattern.compile("^(A+|a+|N+|\\#+|YEAR|Y{4}|Y{2}|M{2,3})$");
    	// restricting set of valid formats to those that can be applied to String for now
    	// will open up possibilities when supporting dates and numeric fields
    	Pattern pattern = Pattern.compile("^(A+|X+|a+|N+|\\#+|YEAR|\"[^\"]*\")$");
    	Matcher matcher = pattern.matcher(formattingString);
    	
    	if (matcher.find()) 
    	{
    		String val = formattingString.substring(matcher.start(), matcher.end());
    		field.setValue(val);
    		field.setSize(val.length());
    		
    		char firstChar = val.charAt(0);
    		switch (firstChar) 
    		{
        		case 'A': 
        			field.setType(FieldType.alphanumeric); 
        			break;
        			
        		case 'a': 
        			field.setType(FieldType.alpha); 
        			break;
        			
        		case 'N': 
        			field.setType(FieldType.numeric);
        			field.setIncrementer(false);
        			break;
        			
        		case '#': 
        			field.setType(FieldType.numeric);
        			field.setIncrementer(true);
        			break;
        			
        		case 'Y': 
        			field.setType(FieldType.year); 
        			break;
        			
        		case '"':
        			field.setType(FieldType.constant);
        			break;
    			
    		// TODO: treat byyear case
    		}
    		
    	} 
    	else 
    	{
    		// didn't find a match: formatting code is invalid
    		throw new UIFieldFormatterParsingException("Invalid formatting string: " + formattingString, formattingString);
    	}
    	
    	return field;
    }
    
    
    /**
     * @return
     */
    public int getSize()
    {
        return size;
    }

    /**
     * @return
     */
    public FieldType getType()
    {
        return type;
    }

    /**
     * @return
     */
    public String getValue()
    {
        return value == null ? "" : value;
    }

    /**
     * @return
     */
    public String getSample()
    {
    	String sample = "";

		if (type == FieldType.separator)
		{
			return value;
		}

		if (type == FieldType.alphanumeric)
		{
			return alphaNumericSample.substring(0, value.length());
		}

		if (type == FieldType.alpha)
		{
			return alphaSample.substring(0, value.length());
		}

		if (type == FieldType.numeric)
		{
		    if (isIncrementer())
		    {
		        sample = String.format("%0"+size+"d", 1);
		    } else
		    {
		        return anyNumericSample.substring(0, value.length());
		    }
		}

		if (type == FieldType.year)
		{
			sample = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		}
		
        if (type == FieldType.constant)
        {
            return value.substring(0, value.length());
        }
        
        if (type == FieldType.anychar)
        {
            return value.substring(0, value.length());
        }
        
		if (sample.length() == 0)
		{
			return "";
		}
		
		return sample.substring(0, value.length()); 
    }

    /**
     * @return whether the field is typed into or not.
     */
    public boolean isEntryField()
    {
        return !isIncrementer() && type != UIFieldFormatterField.FieldType.separator;
    }

    public boolean isIncrementer()
    {
        return incrementer;
    }
    
    /**
     * @return the byYear
     */
    public boolean isByYear()
    {
        return byYear;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Type["+type+"]  size["+size+"]  value["+value+"] incr["+incrementer+"]";
    }
    
    /**
     * Appends a presentation of itself in XML to the StringBuilder
     * @param sb the StringBuilder
     */
    public void toXML(StringBuilder sb)
    {
        sb.append("    <field");
        xmlAttr(sb, "type", type.toString());
        xmlAttr(sb, "size", size);
        
        if (type != FieldType.numeric)
        {
        	// XML encode any double quotes
            xmlAttr(sb, "value", value.replaceAll("\"", "&quot;"));
        }
        if (incrementer)
        {
            xmlAttr(sb, "inc", incrementer);
        }
        if (byYear)
        {
            xmlAttr(sb, "byyear", byYear);
        }
        sb.append("/>\n");
    }

	public void setType(FieldType type)
    {
        this.type = type;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setIncrementer(boolean incrementer)
    {
        this.incrementer = incrementer;
    }

    public void setByYear(boolean byYear)
    {
        this.byYear = byYear;
    }

    public boolean isCurrentYear()
    {
        return ((type == FieldType.year) && (value.equals("YEAR")));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}

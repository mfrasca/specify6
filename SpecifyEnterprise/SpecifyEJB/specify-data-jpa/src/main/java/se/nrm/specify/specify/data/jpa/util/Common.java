package se.nrm.specify.specify.data.jpa.util;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author idali
 */
public class Common {

    private static Common instance = null;

    public static synchronized Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }
     
    /**
     * To parse a numeric string to integer, if string is not a numeric string, return 0
     * @param intStr
     * @return int
     */
    public int stringToInt(String intStr) { 
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }   
    
    /**
     * To parse a numeric string to integer, if string is not a numeric string, return 0
     * @param intStr
     * @return int
     */
    public String uppercaseFirstCharacter(final String string) {
        
        if (string == null || string.length() == 0) {
            throw new NullPointerException("string");
        }
        
        String name = string;
        if(StringUtils.contains(string, ".")) {
            name = StringUtils.substringAfterLast(string, ".");
        } 
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}

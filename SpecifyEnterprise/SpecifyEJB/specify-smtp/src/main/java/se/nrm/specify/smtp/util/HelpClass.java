package se.nrm.specify.smtp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author idali
 */
public class HelpClass {
     
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String getNow() {
        
        Date now = new Date(); 
        return FORMAT.format(now);
    }
    
}

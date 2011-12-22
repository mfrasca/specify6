package se.nrm.specify.smtp.util;

/**
 *
 * @author idali
 */
public class CSSName {
    
    public final String INACTIVE_BUTTON = "inactivebutton";
    public final String ACTIVE_BUTTON = "activebutton"; 
      
    public final String DISABLE_TRUE = "true";
    public final String DISABLE_FALSE = "false";
    public final String DIV_VISIBLE = "divvisible";
    public final String DIV_INVISIBLE = "divinvisible";
    public final String SAMPLE_NUMBER_ENABLED = "samplenumberenabledstyle";
    public final String SMAPLE_NUMBER_DISABLED = "samplenumberdisabledstyle";
    
    public final String DIALOGBOX_VISIBLE = "dialogboxvisible";
    public final String DIALOGBOX_INVISIBLE = "dialogboxinvisible";
    
    public final String DIALOGBOXEDIT_VISIBLE = "dialogboxeditvisible";
    public final String DIALOGBOXEDIT_INVISIBLE = "dialogboxeditinvisible";
    
    public final String NEW_RECORD_DIV_VISIBLE = "newrecorddivvisible";
    public final String NEW_RECORD_DIV_INVISIBLE = "newrecorddivinvisible";
    
    private static CSSName instance = null;
    
    public static synchronized CSSName getInstance() {
        if (instance == null) {
            instance = new CSSName();
        }
        return instance;
    } 
}

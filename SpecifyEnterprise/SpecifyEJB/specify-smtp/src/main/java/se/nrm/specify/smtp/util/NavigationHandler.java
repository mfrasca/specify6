package se.nrm.specify.smtp.util;

/**
 *
 * @author idali
 */
public class NavigationHandler {
    
    public final String BACK_TO_SAME_PAGE = "";
    public final String SUCCESS = "success";
    public final String SAMPLELIST_VIEW = "/views/samplelistview.xhtml";
    public final String STATISTICS_VIEW = "/views/statisticsview.xhtml";
    public final String HOME = "/views/inventoryview.xhtml";
    public final String CREATE_SORTING_VIEW = "/views/uploadingview.xhtml";
    
    private static NavigationHandler instance = null;
    
    public static synchronized NavigationHandler getInstance() {
        if (instance == null) {
            instance = new NavigationHandler();
        }
        return instance;
    } 
    
}

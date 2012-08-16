package se.nrm.specify.specify.data.jpa.util;

/**
 *
 * @author idali
 */
public class Constants {
    
    public final String TABLE_NAME = "tableName";
    public final String PRIMARY_FIELD_NAME = "primaryFieldName";  
    public final String ID = "id";
    public final String SPECIAL_FIELD = "specialField";
    public final String SPECIAL_FIELD_VALUE = "specialFieldValue";
    
    public final String RELETED_TABLE = "relatedTable"; 
    public final String DUPLICATION_NAMEDQRY = "checkForDuplicationNamedQry";
    public final String NAMED_QUERY = "namedQuery";
     
    
    
    
    private static Constants instance = null;

    public static synchronized Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }
        return instance;
    }
    
}

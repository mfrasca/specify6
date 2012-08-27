package se.nrm.specify.specify.data.jpa.util;

/**
 *
 * @author idali
 */
public class ConstantsClass {

    public final String TABLE_NAME = "tableName";
    public final String PRIMARY_FIELD_NAME = "primaryFieldName";
    public final String ID = "id";
    public final String SPECIAL_FIELD = "specialField";
    public final String SPECIAL_FIELD_VALUE = "specialFieldValue";
    public final String RELETED_TABLE = "relatedTable";
    public final String DUPLICATION_NAMEDQRY = "checkForDuplicationNamedQry";
    public final String NAMED_QUERY = "namedQuery";
    public final String COLLECTIONOBJECT = "collectionObject";
    public final String ENTITY_PACKAGE = "se.nrm.specify.datamodel.";
    public final String JAVA_UTIL_COLLECTION = "java.util.Collection";
    private static ConstantsClass instance = null;

    public static synchronized ConstantsClass getInstance() {
        if (instance == null) {
            instance = new ConstantsClass();
        }
        return instance;
    }
}

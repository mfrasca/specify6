package se.nrm.specify.ui.form.data.util;

import java.lang.reflect.Field; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 *
 * @author idali
 */
public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
    
    private final static String ENTITY_PACKAGE = "se.nrm.specify.datamodel.";

    public static Field getField(Class clazz, String fieldName) { 
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                return null;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static Class getClassByName(String classname) throws ClassNotFoundException {
         
        classname = UIXmlUtil.entityNameConvert(classname);
        return Class.forName(ENTITY_PACKAGE + classname); 
    } 
}

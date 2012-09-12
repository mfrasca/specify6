package se.nrm.specify.ui.form.data.util;

import java.lang.reflect.Field; 
import javax.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
     
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

    /**
     * To convert a classname string to class.
     * @param classname
     * @return Class
     * @throws ClassNotFoundException 
     */
    public static Class getClassByName(String classname) throws ClassNotFoundException { 
        classname = UIXmlUtil.entityNameConvert(classname);
        return Class.forName(CommonString.getInstance().ENTITY_PACKAGE + classname); 
    } 
    
    
    /**
     * Find id field name for the entity bean
     * @param bean
     * @return String, name of the id field of this entity bean
     */
    public static String getIDFieldName(SpecifyBean bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null;
    } 
}

package se.nrm.specify.specify.data.jpa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static List<String> addAllRequiredFields(SpecifyBean bean) {
        List<String> list = new ArrayList<String>();
        Field[] fs = bean.getClass().getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(NotNull.class)) {
                list.add(f.getName());
            }
        }
        String idName = getIDFieldName(bean);
        list.add(idName);
        list.add("version");
        list.add("timestampCreated");

        return list;
    }

    public static String getEntityNameByType(Field field) {
 
        String entityName = "";
        Type type = field.getGenericType(); 
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type; 
            for (Type t : pt.getActualTypeArguments()) { 
                String strType = t.toString();
                String[] strArray = StringUtils.split(strType, ".");
                entityName = strArray[strArray.length - 1];  
            }
        }  
        return entityName;
    }

    public static String getIDFieldName(SpecifyBean bean) {

        String idName = "";
        Field[] fs = bean.getClass().getDeclaredFields();
        for (Field f : fs) {
            if (f.isAnnotationPresent(Id.class)) {
                return f.getName();
            }
        }
        return idName;
    }
}

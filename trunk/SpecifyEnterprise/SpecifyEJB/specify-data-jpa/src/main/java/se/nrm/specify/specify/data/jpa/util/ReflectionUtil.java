package se.nrm.specify.specify.data.jpa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;  
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import se.nrm.specify.datamodel.SpecifyBean; 
import se.nrm.specify.specify.data.jpa.exceptions.DataReflectionException;

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

    /**
     * Make private field accessible
     * 
     * @param field 
     */
    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * Add required fields into field list.  Required fields must be fetched.
     * 
     * @param bean
     * @return 
     */
    public static List<String> addAllRequiredFields(SpecifyBean bean) {
        
//        logger.info("addAllRequiredFields : {}", bean);
        
        List<String> list = new ArrayList<String>();
        Field[] fs = bean.getClass().getDeclaredFields();
      
        for (Field f : fs) {
            if(f.isAnnotationPresent(Id.class)) {
                list.add(f.getName());                                          // add id field into list
            } else if (f.isAnnotationPresent(NotNull.class)) {
                if (f.getType().getName().equals(ConstantsClass.getInstance().JAVA_UTIL_COLLECTION)) {
                    String name = getEntityNameByType(f);
                    addSubList(list, name, f.getName());
                } else if (f.getType().getName().contains(ConstantsClass.getInstance().ENTITY_PACKAGE)) {
                    String name = f.getType().getName();
                    String[] strArray = StringUtils.split(name, ".");
                    name = strArray[strArray.length - 1];
                    addSubList(list, name, f.getName());
                } else {
                    list.add(f.getName());
                }                                       // add required fields into list
            }
        } 
        list.add("version");                                                    // add version field
        list.add("timestampCreated");            
         
        return list;
    }
    
    
    public static boolean isXmlTransient(Field field) {
        return field.isAnnotationPresent(XmlTransient.class);
    }
    
    private static void addSubList(List<String> list, String entityName, String fieldName) {
        
//        logger.info("add sub required fields : {} -- {}", entityName, fieldName);
        SpecifyBean subBean = JPAUtil.createNewInstance(entityName);
        List<String> subList = addAllRequiredFields(subBean);
        for(String string : subList) {
            list.add(fieldName + "." + string);
        }  
    }

    /**
     * Find the type of the field for collections.  
     * @param field
     * @return The name of the field type
     */
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
    
    
    public static void setEntityValue(SpecifyBean target, SpecifyBean source) { 
        try { 
            Class parentClazz = target.getClass(); 
            Field field = getField(parentClazz, source.getEntityName());

            ReflectionUtil.makeAccessible(field);
            field.set(target, source);
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage());
        } catch (NoSuchFieldException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    
    public static void setChildrenValue(SpecifyBean parent, List<SpecifyBean> children) { 
        
//        logger.info("setChildrenValue : {} -- {}", parent, children); 
        try {
            for(SpecifyBean bean : children) {
                Class beanClazz = bean.getClass(); 
                Field field = getField(beanClazz, parent.getEntityName());
                ReflectionUtil.makeAccessible(field);
                field.set(bean, parent);
            } 
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage());
        } catch (NoSuchFieldException ex) {
            logger.error(ex.getMessage());
        }
    }
    
       /**
     * Find id field name for the entity bean
     * @param bean
     * @return String, name of the id field of this entity bean
     */
    public static String getIDFieldName(SpecifyBean bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        return null;
    } 
}

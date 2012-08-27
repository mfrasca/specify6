package se.nrm.specify.specify.data.jpa.util;

import java.lang.reflect.Field; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map; 
import org.apache.commons.lang.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.exceptions.DataReflectionException;

/**
 *
 * @author idali
 */
public class JPAUtil {

    private static final String STRING_SEPARATOR = ".";
    private static final String ENTITY_PACKAGE = "se.nrm.specify.datamodel.";
    
    private final static Logger logger = LoggerFactory.getLogger(JPAUtil.class.getName());

    /**
     * Create a map for entity mapping
     * 
     * @param classname
     * @param fields - fields for partial fetch
     * @return Map<String, List<String>> - key as entity name, List is a list of fields shall be fetched for the 
     */
    public static Map<String, List<String>> createMap(String classname, List<String> fields) {

        Map<String, List<String>> map = new HashMap<String, List<String>>();

        for (String fieldname : fields) { 
            String value = fieldname;
            String key = classname; 
            if (StringUtils.contains(fieldname, STRING_SEPARATOR)) {  
                String[] strlist = StringUtils.split(fieldname, STRING_SEPARATOR);
                
                key = getKey(classname, strlist);
                value = getValue(fieldname, strlist[0]); 
            }

            if (map.containsKey(key)) {
                List<String> list = map.get(key);
                list.add(value);
            } else {
                List<String> values = new ArrayList<String>();
                values.add(value);
                map.put(key, values);
            }
        }
        return map;
    }

    /**
     * Create a new instance with the given class name
     * @param <T>
     * @param classname
     * @return 
     */
    public static <T extends SpecifyBean> T createNewInstance(String classname) {
 
        try {
            Class classDefinition = Class.forName(ENTITY_PACKAGE + classname);
            return (T) classDefinition.newInstance();
        } catch (InstantiationException ex) {
            throw new DataReflectionException(ex);
        } catch (IllegalAccessException ex) {
            throw new DataReflectionException(ex);
        } catch (NoClassDefFoundError ex) {
            throw new DataReflectionException(ex);
        }catch (ClassNotFoundException ex) {
            throw new DataReflectionException(ex);
        } 
    }
   
    public static List<String> addValidFields(List<String> fields, String classname) { 
        
//        logger.info("addValidFields : fields : {} --  Classname : {}", fields, classname);
        
        if (fields == null) {
            fields = new ArrayList<String>();
        } 
        List<String> removelist = new ArrayList<String>();
        SpecifyBean bean;
        try {
            bean = createNewInstance(classname);
        } catch(DataReflectionException e) {
            throw e;
        } 
        Map<String, SpecifyBean> beanmap = new HashMap<String, SpecifyBean>();
        beanmap.put(classname, bean);

        Map<String, SpecifyBean> map = new HashMap<String, SpecifyBean>();
        map.put(classname, bean);

        for (String string : fields) {
            if (StringUtils.contains(string, STRING_SEPARATOR)) {

                String[] strs = StringUtils.split(string, STRING_SEPARATOR);
                SpecifyBean parent = bean;
                for (int i = 0; i < strs.length - 1; i++) {
                    String entityname = strs[i];
                    String key = StringUtils.substringBefore(string, entityname) + entityname; 
                    try {
                        if (!map.containsKey(key)) {
                            if (i > 0) {
                                parent = beanmap.get(strs[i - 1]);
                            }
                            Field field = ReflectionUtil.getField(parent.getClass(), entityname);
                            String name;

                            if (field.getType().getName().equals("java.util.Collection")) {
                                name = ReflectionUtil.getEntityNameByType(field);
                            } else {
                                name = field.getType().getSimpleName();
                            }

                            SpecifyBean sb;
                            try {
                                sb = createNewInstance(name);
                            } catch (DataReflectionException e) {
                                throw e;
                            }
                            map.put(key, sb);
                            beanmap.put(entityname, sb);
                        }
                    } catch (IllegalArgumentException ex) {
                        logger.error("IllegalArgumentException: {} - {}", ex.getMessage(), parent);
                    } catch (NoSuchFieldException ex) {
                        logger.error("NoSuchFieldException: {} - {}", ex.getMessage(), parent);
                        removelist = removeFetchGroup(key, fields, removelist);
                        break;
                    }
                }
            }
        } 
        addRequiredFields(map, fields, classname);
        fields.removeAll(removelist);


        return new ArrayList(new HashSet(fields));
    }
    

     
    private static List<String> removeFetchGroup(String field, List<String> fields, List<String> removelist) {
        for (String string : fields) {
            if (string.contains(field)) {
                removelist.add(string);
            }
        }
        return new ArrayList(new HashSet(removelist));
    }

    private static String getKey(String classname, String[] strlist) {
        
        StringBuilder sbKey = new StringBuilder();
        sbKey.append(classname);
        sbKey.append(STRING_SEPARATOR);
        sbKey.append(strlist[0]);
        return sbKey.toString();
    }

    private static String getValue(String fieldname, String string) {
        return StringUtils.substringAfterLast(fieldname, string + STRING_SEPARATOR);
    }

    private static void addRequiredFields(Map<String, SpecifyBean> map, List<String> fields, String classname) {
        for (String key : map.keySet()) {
            SpecifyBean entity = (SpecifyBean) map.get(key);

            if (entity != null) {
                List<String> rfs = ReflectionUtil.addAllRequiredFields(entity);
                for (String string : rfs) { 
                    if(!key.equals(classname.trim())) {
                        string = key + STRING_SEPARATOR + string;
                    } 
                    if (!fields.contains(string)) {
                        fields.add(string);
                    }
                }
            }
        } 
    }
}

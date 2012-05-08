package se.nrm.specify.specify.data.jpa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.FetchGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.SpecifyBean;

/**
 *
 * @author idali
 */
public class JPAUtil {

    private static final String STRING_SEPARATOR = ".";
    private static final String ENTITY_PACKAGE = "se.nrm.specify.datamodel.";
    private final static Logger logger = LoggerFactory.getLogger(JPAUtil.class.getName());

    public static Map<String, List<String>> createMap(String classname, List<String> fields) {

        Map<String, List<String>> map = new HashMap<String, List<String>>();

        for (String fieldname : fields) {
            List<String> values = new ArrayList<String>();

            String value = fieldname;
            String key = classname;

            if (StringUtils.contains(fieldname, STRING_SEPARATOR)) {
                StringBuilder sbKey = new StringBuilder();

                String[] strlist = StringUtils.split(fieldname, STRING_SEPARATOR);
                sbKey.append(classname);
                sbKey.append(STRING_SEPARATOR);
                sbKey.append(strlist[0]);
                key = sbKey.toString();
                value = StringUtils.substringAfterLast(fieldname, strlist[0] + STRING_SEPARATOR);
            }

            if (map.containsKey(key)) {
                List<String> list = map.get(key);
                list.add(value);
            } else {
                values.add(value);
                map.put(key, values);
            }
        }
        return map;
    }

    public static <T extends SpecifyBean> T createNewInstance(String classname) { 

        T bean = null;
        try {
            Class classDefinition = Class.forName(ENTITY_PACKAGE + classname);
            bean = (T) classDefinition.newInstance();
        } catch (InstantiationException ex) {
            logger.error("InstantiationException: {}", ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error("IllegalAccessException: {}", ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.error("ClassNotFoundException: {}", ex.getMessage());
        }
        return bean;
    }

    public static List<String> addFetchGroup(List<String> fields, Query query, String classname) {

        List<String> removelist = new ArrayList<String>();
        FetchGroup group = new FetchGroup();

        SpecifyBean bean = createNewInstance(classname);

        if (bean != null) {
            List<String> reqFields = ReflectionUtil.addAllRequiredFields(bean);
            fields.addAll(reqFields);
        }

        Map<String, SpecifyBean> beanmap = new HashMap<String, SpecifyBean>();
        beanmap.put(classname, bean);

        Map<String, SpecifyBean> map = new HashMap<String, SpecifyBean>();
        if (fields != null) {
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
                                String name = field.getName(); 

                                if (field.getType().getName().equals("java.util.Collection")) {
                                    name = ReflectionUtil.getEntityNameByMethod(parent, name);
                                } else {
                                    name = field.getType().getSimpleName();
                                } 
                                SpecifyBean sb = createNewInstance(name);
                                map.put(key, sb);
                                beanmap.put(entityname, sb);
                            }
                        } catch (IllegalArgumentException ex) {
                            logger.error("IllegalArgumentException: {}", ex.getMessage());
                        } catch (NoSuchFieldException ex) {
                            logger.error("NoSuchFieldException: {}", ex.getMessage());
                            removelist = removeFetchGroup(key, fields, removelist);
                        }
                    }
                }
            }


            for (String key : map.keySet()) {

                SpecifyBean entity = (SpecifyBean) map.get(key);

                if (entity != null) {
                    List<String> rfs = ReflectionUtil.addAllRequiredFields(entity);
                    for (String string : rfs) {
                        String field = key + STRING_SEPARATOR + string;
                        if (!fields.contains(field)) {
                            fields.add(field);
                        }
                    }
                } 
            }

            fields = new ArrayList(new HashSet(fields));
            for (String field : fields) {
                if (!removelist.contains(field)) { 
                    group.addAttribute(field);
                }
            }
            query.setHint(QueryHints.FETCH_GROUP, group);
        }

        return removelist;
    }

    private static List<String> removeFetchGroup(String field, List<String> fields, List<String> removelist) {
        for (String string : fields) {
            if (string.contains(field)) {
                removelist.add(string);
            }
        }
        return new ArrayList(new HashSet(removelist));
    }
}

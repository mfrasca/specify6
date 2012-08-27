package se.nrm.specify.specify.data.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.exceptions.DataReflectionException; 
import se.nrm.specify.specify.data.jpa.util.JPAUtil;
import se.nrm.specify.specify.data.jpa.util.ReflectionUtil;

/**
 *
 * @author idali
 */
@Stateless
public class EntityMapping {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final String JAVA_UTIL_COLLECTION = "java.util.Collection";

    public EntityMapping() {
    }

    public void setEntityValue(SpecifyBean o, String classname, List<String> fields, Map<String, SpecifyBean> beanmap) {
  
//        logger.info("setEntityValue: {} - {}", o, classname);
        
        SpecifyBean bean = beanmap.get(classname);
        Map<String, List<String>> map = JPAUtil.createMap(classname, fields);
 
        List<String> fieldlist = map.get(classname);
        try {
            for (String strfield : fieldlist) {
                Field field = ReflectionUtil.getField(bean.getClass(), strfield);
                setFieldValue(o, bean, field);
            } 

            for (String key : map.keySet()) {
                if (key.contains(".")) {
                    String[] strarry = StringUtils.split(key, ".");
                    Field field = ReflectionUtil.getField(bean.getClass(), strarry[1]);
                    if (field.getType().getName().equals(JAVA_UTIL_COLLECTION)) {

                        ReflectionUtil.makeAccessible(field);
                        Collection<SpecifyBean> collectionbeans = (Collection<SpecifyBean>) field.get(bean); 

                        Collection<SpecifyBean> newcollections = new ArrayList<SpecifyBean>();
                        for (SpecifyBean sb : collectionbeans) {
                            SpecifyBean subo = JPAUtil.createNewInstance(sb.getClass().getSimpleName());

                            beanmap.put(sb.getClass().getSimpleName(), sb);
                            setEntityValue(subo, sb.getClass().getSimpleName(), map.get(key), beanmap);
                            newcollections.add(subo);

                            Field newField = ReflectionUtil.getField(o.getClass(), field.getName());
                            ReflectionUtil.makeAccessible(newField);
                            newField.set(o, newcollections);
                        } 
                    } else {
                        SpecifyBean subo = JPAUtil.createNewInstance(field.getType().getSimpleName()); 
                        setSubEntity(subo, o, bean, field, map.get(key)); 
                    } 
                }
            }
            
        } catch (DataReflectionException ex) {
            logger.error(ex.getMessage());
        } catch (NoSuchFieldException ex) {
            logger.error("NoSuchFieldException: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException: {}", ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error("IllegalAccessException: {}", ex.getMessage());
        }
    }

    private void setSubEntity(SpecifyBean subo, SpecifyBean o, SpecifyBean bean, Field field, List<String> fields) { 
        
//        logger.info("setSubEntity: {} - {}", o, bean);
        
        logger.info("fields : {} -- {}", field.getName(), fields);
        
        String classname = field.getName(); 
        Map<String, List<String>> map = JPAUtil.createMap(classname, fields);  
        ReflectionUtil.makeAccessible(field);
        try {
            if (field.get(bean) instanceof SpecifyBean) {
                SpecifyBean value = (SpecifyBean) field.get(bean);
                List<String> fieldlist = map.get(classname);
                if (value != null) {
                    for (String strfield : fieldlist) {
                        Field subfield = ReflectionUtil.getField(value.getClass(), strfield);
                        setFieldValue(subo, value, subfield);
                    }
                    map.remove(classname);
                    fields.removeAll(fieldlist);

                    for (String key : map.keySet()) {

                        
                        logger.info("entity mapping : {}", key);
                        
                        
                        
                        
                        
                        
                        if (key.contains(".")) {
                            String[] strarry = StringUtils.split(key, ".");
                            Field subfield = ReflectionUtil.getField(subo.getClass(), strarry[1]);
                            
                            logger.info("sub fields : {}", subfield);
                            if (subfield.getType().getName().equals(JAVA_UTIL_COLLECTION)) {
                                
                                
                                
                                Map<String, SpecifyBean> beanmap = new HashMap<String, SpecifyBean>();
                                
                                ReflectionUtil.makeAccessible(subfield);
                                Collection<SpecifyBean> collectionbeans = (Collection<SpecifyBean>) subfield.get(value);

                                
                                if(collectionbeans != null) {
                                    int size = collectionbeans.size();      // this is needed for IndirectList: not instantiated
                                    logger.info("collection beans : {}", collectionbeans);
                                    if (size > 0) {
                                        Collection<SpecifyBean> newcollections = new ArrayList<SpecifyBean>();
                                        for (SpecifyBean sb : collectionbeans) {
                                            
                                             
                                                SpecifyBean gsubo = JPAUtil.createNewInstance(sb.getClass().getSimpleName());

                                                beanmap.put(sb.getClass().getSimpleName(), sb);
                                                setEntityValue(gsubo, sb.getClass().getSimpleName(), map.get(key), beanmap);
                                                newcollections.add(gsubo);

//                                                Field newField = ReflectionUtil.getField(o.getClass(), field.getName());
//                                                ReflectionUtil.makeAccessible(newField);
//                                                newField.set(o, newcollections);
                                        
                                            
                                            
                                            
                                            
                                            
                                            
//                                            SpecifyBean gsubo = JPAUtil.createNewInstance(sb.getClass().getSimpleName());
//                                            logger.info("gsubo : {}", gsubo);
//                                            
//                                            List<String> list = map.get(key);
//                                            Field f = ReflectionUtil.getField(subo.getClass(), gsubo.getClass().getSimpleName());
//                                            setSubEntity(ggsubo, gsubo, gsubo, f, list);
//                                            
                                            
                                            
                                            
                                            
//                                            for (String string : list) {
//                                                
//                                                logger.info("string : {}", string);
//                                                logger.info("is true ? {}", string.contains("."));
//                                                if(string.contains(".")) { 
//                                                     
//                                                    String[] strs = StringUtils.split(string, ".");
//                                                    Field f = ReflectionUtil.getField(subo.getClass(), gsubo.getClass().getSimpleName());
//                                                    
//                                                    String subClassName = Common.getInstance().uppercaseFirstCharacter(strs[0]);
//                                                    
//                                                    SpecifyBean ggsubo = JPAUtil.createNewInstance(subClassName);
//                                                    
//                                                    logger.info("entities : {} --- {}", ggsubo + " --- " + gsubo, f + "---" + list);
//                                                    setSubEntity(ggsubo, gsubo, gsubo, f, list);
//                                                }
//                                                Field f = ReflectionUtil.getField(sb.getClass(), string);
//                                                setFieldValue(gsubo, sb, f);
//                                            }

                                            newcollections.add(gsubo);  
                                        }
                                        Field newField = ReflectionUtil.getField(subo.getClass(), subfield.getName());
                                        ReflectionUtil.makeAccessible(newField);
                                        newField.set(subo, newcollections);
                                        
                                        logger.info("new field with new collection : {} --- {}", subo, newField.getName() + " ---" + newcollections);
                                    }
                                }
                                
                            } else {
                                SpecifyBean gsubo = JPAUtil.createNewInstance(subfield.getType().getSimpleName());
                                setSubEntity(gsubo, subo, value, subfield, map.get(key)); 
                            } 
                        }
                    }


                    field.set(o, subo);
                } else {
                    map.remove(classname);
                }
            }

        } catch (NoSuchFieldException ex) {
            logger.error("NoSuchFieldException: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException: {}", ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error("IllegalAccessException", ex.getMessage());
        } catch (SecurityException ex) {
            logger.error("SecurityException: {}", ex.getMessage());
        }
    }

    private void setFieldValue(SpecifyBean o, SpecifyBean bean, Field field) {  
        try {
            Field f = ReflectionUtil.getField(o.getClass(), field.getName());
            ReflectionUtil.makeAccessible(f);
            f.set(o, f.get(bean));
        } catch (NoSuchFieldException ex) {
            logger.error("NoSuchFieldException: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException: {}", ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error("IllegalAccessException: {}", ex.getMessage());
        } catch (SecurityException ex) {
            logger.error("SecurityException: {}", ex.getMessage());
        }
    }
}

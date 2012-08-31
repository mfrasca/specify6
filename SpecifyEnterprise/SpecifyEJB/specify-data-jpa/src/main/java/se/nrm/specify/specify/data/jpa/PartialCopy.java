package se.nrm.specify.specify.data.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;   
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.exceptions.DataReflectionException; 
import se.nrm.specify.specify.data.jpa.util.ConstantsClass;
import se.nrm.specify.specify.data.jpa.util.JPAUtil;
import se.nrm.specify.specify.data.jpa.util.ReflectionUtil;

/**
 *
 * @author idali
 */
@Stateless
public class PartialCopy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final String STRING_SEPARATOR = ".";
     
    private List<MergeData> mergeDataList;  
    private boolean isRelatedEntityChanged = false;
    private boolean isCollectionChanged = false;
    
    private EntityManager entityManager;

    public PartialCopy() {
    }

  

    public boolean isIsCollectionChanged() {
        return isCollectionChanged;
    }
 
    public List<MergeData> getMergeDataList() {
        return mergeDataList;
    }

    public boolean isIsRelatedEntityChanged() {
        return isRelatedEntityChanged;
    }
    
    public void copyPartialEntityForMerge(SpecifyBean source, SpecifyBean target, List<String> fields, EntityManager entityManager) {
        this.entityManager = entityManager;
        mergeDataList = new ArrayList<MergeData>();
        copyPartialEntity(source, target, fields, true);
    }
     
    
    public void copyPartialEntity(SpecifyBean source, SpecifyBean target, List<String> fields, boolean isMerge) {

//        logger.info("copyPartialFetchedEntity : fields : {}, source -- target : {}", fields, source + " --- " + target);

        List<String> list = getEntityFields(fields);

        copyEntity(source, target, list);
        fields.removeAll(list);
        if (isMerge) {
            copySubEntity(source, target, fields, true);
        } else {
            copySubEntity(source, target, fields, false);
        }
    }
    
    private void copySubEntity(SpecifyBean source, SpecifyBean target, List<String> fields, boolean isMerged) {
        
//        logger.info("copySubEntity :  source -- target : {}, is for merge : {}", source + " --- " + target, isMerged);
         
        if (fields != null && !fields.isEmpty()) { 
            Map<String, List<String>> fieldMap = createFieldMap(fields); 
            
            for(Map.Entry<String, List<String>> entry : fieldMap.entrySet()) { 
                try {  
                    List<String> list = entry.getValue();
                    String entityName = entry.getKey(); 
                    if (list != null && !list.isEmpty()) {
                          
                        Field field = ReflectionUtil.getField(source.getClass(), entityName);
                        ReflectionUtil.makeAccessible(field);
                    
                        if (field.getType().getName().equals(ConstantsClass.getInstance().JAVA_UTIL_COLLECTION)) { 
                            List<SpecifyBean> sourceCollection = (List<SpecifyBean>) field.get(source);
                            if(isMerged) { 
                                copyCollectionForMerge(field, sourceCollection, target, list);
                            } else {
                                copyCollection(field, sourceCollection, target, list);
                            }
                            
                        } else { 
                            SpecifyBean subSource = (SpecifyBean) field.get(source);
                            if (isMerged) {
                                copySubEntityForMerge(field, target, subSource, list);
                            } else {
                                if (subSource != null) {
                                    SpecifyBean subTarget = JPAUtil.createNewInstance(field.getType().getSimpleName());
                                    copyPartialEntity(subSource, subTarget, list, false);
                                    field.set(target, subTarget);                               // set sub entity into entity 
                                }
                            } 
                        }
                    } 
                } catch (IllegalAccessException ex) {
                    logger.error("IllegalAccessException: {}", ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    logger.error("IllegalArgumentException: {}", ex.getMessage());
                } catch (NoSuchFieldException ex) {
                    logger.error("NoSuchFieldException: {}", ex.getMessage());
                } 
            }
        }        
    }
    
    private void copySubEntityForMerge(Field field, SpecifyBean target, SpecifyBean subSource, List<String> list) {
        
//        logger.info("copySubEntityForMerge -- {} -- {}", field.getName(), subSource + " -- " + list);
        try {
            SpecifyBean subTarget = (SpecifyBean) field.get(target); 
            if (subSource == null && subTarget != null) {
                if(!ReflectionUtil.isXmlTransient(field)) {
                    subTarget = null;                                           // if related entity is removed at frontend, remove foreign key relationship
                    field.set(target, subTarget);                               // set sub entity into entity 
                } 
            } else if (subSource != null && subTarget == null) {            // if target bean not exist, create a new instance   
                MergeData data = new MergeData(target, subSource, field.getName(), list, true);
                mergeDataList.add(data);
                isRelatedEntityChanged = true;
            } else if (subSource != null && subTarget != null) {
                if (!subSource.getIdentityString().equals(subTarget.getIdentityString())) {
                    isRelatedEntityChanged = true;
                    MergeData data = new MergeData(target, subSource, field.getName(), list, false);
                    mergeDataList.add(data);
                } else {
                    copyPartialEntity(subSource, subTarget, list, true);
                    field.set(target, subTarget);                           // set sub entity into entity 
                }
            }
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage());
        }
    }
    
    private void copyCollection(Field field, List<SpecifyBean> sourceCollection, SpecifyBean target, List<String> list) {
        sourceCollection.size(); // this is needed for IndirectList: not instantiated
     
        if (sourceCollection != null && !sourceCollection.isEmpty()) {
            try {
                Collection<SpecifyBean> targetCollection = new ArrayList<SpecifyBean>();
                for (SpecifyBean subSource : sourceCollection) {
                    List<String> iniNewList = new ArrayList<String>();
                    iniNewList.addAll(list);                                    // this is needed, because the elements in the new list will be removed recrusively when copys sub entities

                    SpecifyBean subTarget = JPAUtil.createNewInstance(subSource.getClass().getSimpleName());
                    copyPartialEntity(subSource, subTarget, iniNewList, false);
                    targetCollection.add(subTarget);
                }
                field.set(target, targetCollection); 
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
     
    private void copyCollectionForMerge(Field field, List<SpecifyBean> sourceCollection, SpecifyBean target, List<String> list) {  
        
//        logger.info("copyCollectionForMerge : {} -- {}", field, sourceCollection + " --- " + target);
        try {
            List<SpecifyBean> targetCollection = (List<SpecifyBean>) field.get(target); 
            if(targetCollection != null) {
                targetCollection.size();                                                // this is needed for IndirectList: not instantiated
            } else {
                targetCollection = new ArrayList<SpecifyBean>();
            }  
            
            if (sourceCollection != null && !sourceCollection.isEmpty()) {
                List<SpecifyBean> removedTargets = new ArrayList<SpecifyBean>();
                for (SpecifyBean subTarget : targetCollection) {                    // check through target collection, if it is not in source collection, remove from target collection
                    if (isTargetBeanRemoved(sourceCollection, subTarget)) {
                        removedTargets.add(subTarget);  
                    } else { 
                        entityManager.detach(subTarget);                            // entity need detached from entityManager for merge later
                    }
                } 
                
                targetCollection.removeAll(removedTargets);
                if (targetCollection.isEmpty())  {
                    isCollectionChanged = true;
                    for(SpecifyBean subSource : sourceCollection) {
                        targetCollection.add(subSource);
                    }
                    MergeData data = new MergeData(target, targetCollection, field.getName(), list);
                    mergeDataList.add(data);
                } else {
                    for (SpecifyBean subSource : sourceCollection) {
                        SpecifyBean subTarget = getTargetBean(targetCollection, subSource);

                        if (subTarget == null) {                         
                            isCollectionChanged = true;
                            targetCollection.add(subSource);
                        } else {
                            List<String> iniNewList = new ArrayList<String>();
                            iniNewList.addAll(list);       
                             
                            copyPartialEntity(subSource, subTarget, iniNewList, true);
                            
                            isCollectionChanged = true;
                        }
                    }
                    if (isCollectionChanged) { 
                        MergeData data = new MergeData(target, targetCollection, field.getName(), list);
                        mergeDataList.add(data);
                    }
                }
            } else {
                if(!ReflectionUtil.isXmlTransient(field)) {
                    field.set(target, null);                    // if collection is removed at frontend, delete from database  
                }
            } 
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage());
        }
    }
 
               
    
    /**
     * Is target bean removed from source collection
     * 
     * @param sourceCollection
     * @param targetBean
     * @return boolean - true if removed, false not remove.
     */
    private boolean isTargetBeanRemoved(Collection<SpecifyBean> sourceCollection, SpecifyBean subTarget) {

//        logger.info("isTargetBeanRemoved: {} - {}", sourceCollection, subTarget);

        for (SpecifyBean bean : sourceCollection) { 
            if(subTarget.getClass().isInstance(bean)) {
                
                if (bean.getIdentityString().equals(subTarget.getIdentityString())) {
                    return false;
                }
            } else {
                throw new DataReflectionException("Wrong data type");
            }
        }
        return true;
    }

 
    private SpecifyBean getTargetBean(Collection<SpecifyBean> targetCollection, SpecifyBean subSource) {

//        logger.info("getTargetBean: {} - {}", targetCollection, subSource);

        for (SpecifyBean bean : targetCollection) {
            if(subSource.getClass().isInstance(bean)) {
                if (bean.getIdentityString().equals(subSource.getIdentityString())) {
                    return bean;
                }
            } 
        }
        return null;
    }  
    
 
    
    
    /**
     * get required fields excludes all the fields for sub entity
     * 
     * @param fields
     * @param entity
     * @return List<String> fields
     */
    private static List<String> getEntityFields(List<String> fields) {
        List<String> list = new ArrayList<String>();

        for (String field : fields) {
            if (!field.contains(STRING_SEPARATOR)) {
                list.add(field);
            }
        }
        return list;
    }
    
    private void copyEntity(SpecifyBean source, SpecifyBean target, List<String> list) {

//        logger.info("copyEntity : {} - {}", list, source + " -- " + target);
        for (String string : list) {
            try {
                Field field = ReflectionUtil.getField(source.getClass(), string);
                ReflectionUtil.makeAccessible(field);
                field.set(target, field.get(source)); 
            } catch (IllegalArgumentException ex) {
                logger.error("IllegalArgumentException : {} -- {}", string, ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error("IllegalArgumentException : {} -- {}", string, ex.getMessage());
            } catch (NoSuchFieldException ex) {
                logger.error("IllegalArgumentException : {} -- {}", string, ex.getMessage());
            } 
        }
    }

    private Map<String, List<String>> createFieldMap(List<String> fields) {

        Map<String, List<String>> fieldMap = new HashMap<String, List<String>>();

        
        for (String string : fields) {
            if (string.contains(STRING_SEPARATOR)) {
                String[] strArray = StringUtils.split(string, STRING_SEPARATOR);
                String entityName = strArray[0];
                String fieldName = StringUtils.substringAfter(string, entityName + STRING_SEPARATOR);
                if (fieldMap.containsKey(entityName)) {
                    List<String> list = fieldMap.get(entityName);
                    list.add(fieldName);
                } else {
                    List<String> values = new ArrayList<String>();
                    values.add(fieldName);
                    fieldMap.put(entityName, values);
                }
            }
        }
        return fieldMap;
    } 
 
    
    
    public SpecifyBean copyEntity(SpecifyBean bean, List<String> fields, String classname) {

        logger.info("copyEntity: {} - {}", bean, classname);

        Map<String, SpecifyBean> beanmap = new HashMap<String, SpecifyBean>();
        beanmap.put(classname, bean);
        SpecifyBean copyObject = JPAUtil.createNewInstance(classname);

        copyPartialEntity(bean, copyObject, fields, false);

        return copyObject;
    }
}

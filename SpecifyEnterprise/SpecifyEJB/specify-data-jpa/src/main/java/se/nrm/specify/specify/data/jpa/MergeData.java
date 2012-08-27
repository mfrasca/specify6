package se.nrm.specify.specify.data.jpa;

import java.io.Serializable;
import java.util.List; 
import se.nrm.specify.datamodel.SpecifyBean;
import se.nrm.specify.specify.data.jpa.util.Common; 

/**
 *
 * @author idali
 */
public class MergeData implements Serializable {
    
    private SpecifyBean parent;
    private SpecifyBean child;
    private List<SpecifyBean> children;
    private List<String> fetchFields;
    private boolean isList;
    private String fieldName;
    
    public MergeData() {
        
    }
    
    public MergeData(SpecifyBean parent, SpecifyBean child, List<String> fetchFields) {
        this.parent = parent;
        this.child = child;
        this.fetchFields = fetchFields;
        this.isList = false;
    }
    
    public MergeData(SpecifyBean parent, List<SpecifyBean> children, List<String> fetchFields ) {
        this.parent = parent;
        this.children = children;
        this.fetchFields = fetchFields; 
        this.isList = true;
    }

    public SpecifyBean getChild() {
        return child;
    }

    public List<SpecifyBean> getChildren() {
        return children;
    }

    public List<String> getFetchFields() {
        return fetchFields;
    }

    public SpecifyBean getParent() {
        return parent;
    }

    public String getFieldName() {
        return fieldName;
    }
    
    

    public boolean isIsList() {
        return isList;
    }
     
    public int getParentId() {
//        BaseEntity baseEntity = (BaseEntity)parent;  
        return Common.getInstance().stringToInt(parent.getIdentityString());
    }
    
    public boolean isNew() {
//        BaseEntity baseEntity = (BaseEntity)child; 
        return child.getIdentityString().equals("0") ? true : false;
    }
    
     
    @Override
    public String toString() {
        return "MergeData : [ parent : " + parent + " child : " + child + " children : " + children + " fetchFields : " + fetchFields + " ]";
    }
}

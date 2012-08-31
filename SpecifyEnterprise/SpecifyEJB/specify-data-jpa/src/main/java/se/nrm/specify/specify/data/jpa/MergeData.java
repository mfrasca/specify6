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
    private boolean targetEmpty;
    
    public MergeData() {
        
    }
    
    public MergeData(SpecifyBean parent, SpecifyBean child, String fieldName, List<String> fetchFields, boolean targetEmpty) {
        this.parent = parent;
        this.child = child;
        this.fieldName = fieldName;
        this.fetchFields = fetchFields;
        this.isList = false;
        this.targetEmpty = targetEmpty;
    }
    
    public MergeData(SpecifyBean parent, List<SpecifyBean> children, String fieldName, List<String> fetchFields) {
        this.parent = parent;
        this.children = children;
        this.fieldName = fieldName;
        this.fetchFields = fetchFields; 
        this.isList = true;
    }
    
    
    public int getEntityId(SpecifyBean bean) {
        return Common.getInstance().stringToInt(bean.getIdentityString());
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
        return Common.getInstance().stringToInt(parent.getIdentityString());
    }
    
    public boolean isNew() { 
        return (child.getIdentityString() == null || child.getIdentityString().equals("0")) ? true : false;
    }

    public boolean isTargetEmpty() {
        return targetEmpty;
    }

    
    
    
     
    @Override
    public String toString() {
        return "MergeData : [ parent : " + parent + " child : " + child + " children : " + children + " fetchFields : " + fetchFields + " ]";
    }
}

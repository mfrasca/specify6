package se.nrm.specify.business.logic.validation;
 
import javax.xml.bind.annotation.XmlRootElement;
import se.nrm.specify.datamodel.BaseEntity;
import se.nrm.specify.datamodel.SpecifyBean;


/**
 *
 * @author idali
 */ 
@XmlRootElement
public class SpecifyBeanId {
     
    private final String id; 
    private final String className;
    
    public SpecifyBeanId() {
        id = "";
        className = "";
    }
      
    public SpecifyBeanId(final String id, final String className) {  
        this.id = id;
        this.className = className;
    }
    
    public SpecifyBeanId(final SpecifyBean bean) {
        
        BaseEntity baseE = (BaseEntity)bean;
        this.id = baseE.getIdentityString();
        this.className = bean.getClass().getSimpleName();
    }
    
    public int asInt() {  
        try { 
            return Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return 0;
        } 
    }

    public String asString() {
        return id;
    }

    public String getClassName() {
        return className;
    }  
    
    public Class getClazz() {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
    
    @Override
    public String toString() {
        return "<" + getClass().getSimpleName() + "  " + asString() + ">";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof SpecifyBeanId) {
            SpecifyBeanId that = (SpecifyBeanId) o;
            return id.equals(that.asString());
        }
        return false;
    }
    
}

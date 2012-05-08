package se.nrm.specify.ui.form.data.xml.model;
 
import java.io.Serializable;
import java.util.List;  

/**
 *
 * @author idali
 */
public class View implements Serializable {
 
    private String viewname;
    private String classname;
    private List<Altview> altlist;
 
    View( String viewname, String classname, List<Altview> altlist) { 
        this.viewname = viewname;
        this.classname = classname;
        this.altlist = altlist;
    }

    public String getClassname() { 
         return classname;
    }

    public String getViewname() {
        return viewname;
    }

    public List<Altview> getAltlist() {
        return altlist;
    }
    
    public String getViewDefName() { 
        for (Altview altview : altlist) { 
            if (altview.isIsdefault()) {
                return altview.getViewdefname();
            }
        }
        return altlist.get(0).getViewdefname();
    }
    
    
    @Override
    public String toString() {
        return "View - [viewNam:" + viewname + " ] - [view classname: " + classname + " ]";
    } 
}

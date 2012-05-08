package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;

/**
 *
 * @author idali
 */
public class Altview implements Serializable {
 
    private String viewdefname;
    private String name;
    private String mode;
    private boolean isdefault;
  
    Altview(String name, String viewdefname, String mode, boolean isdefault) {  
        this.viewdefname = viewdefname;
        this.name = name;
        this.mode = mode;
        this.isdefault = isdefault;
    }

    public boolean isIsdefault() {
        return isdefault;
    } 
    
    public String getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getViewdefname() {
        return viewdefname;
    }
     
    @Override
    public String toString() {
        return "Altview = [ name :" + name + " ] - [ viewdefname : " + viewdefname + " ] - [ mode : " + mode + " ] - [ default : " + isdefault + " ]";
    }
}

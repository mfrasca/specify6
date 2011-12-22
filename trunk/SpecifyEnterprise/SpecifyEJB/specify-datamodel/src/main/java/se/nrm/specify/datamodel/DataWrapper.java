package se.nrm.specify.datamodel;
 
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DataWrapper wrapper different data into object to pass data through web service
 * 
 * @author idali
 */
@XmlRootElement
public class DataWrapper {

    @XmlElement
    private List<String> list; 
    @XmlElement
    private String eventcount;
    @XmlElement
    private String trapcount;
    
    @XmlElement
    private Collectingevent event;
    @XmlElement
    private int numSortedVials;
    @XmlElement 
    private String collectionCode;

    public DataWrapper() {
    }

    public DataWrapper(List<String> list) {
        this.list = list;
    } 
     
    public DataWrapper(List<String> list, String eventcount, String trapcount) {
        this.list = list;
        this.eventcount = eventcount;
        this.trapcount = trapcount;
    }
    
    public DataWrapper(List<String> list, Collectingevent event, int numSortedVails, String collectionCode) {
        this.list = list;
        this.event = event;
        this.numSortedVials = numSortedVails;
        this.collectionCode = collectionCode;
    }
     
    public String getEventcount() {
        return eventcount;
    }

    public String getTrapcount() {
        return trapcount;
    }
     
    public List<String> getList() {
        return list;
    }

    public String getCollectionCode() {
        return collectionCode;
    }
 
    

    public Collectingevent getEvent() {
        return event;
    }

    public int getNumSortedVials() {
        return numSortedVials;
    }
    
    
    
    
    
 
    @Override
    public String toString() {
        return "data is: List: " + list + " event count: " + eventcount + " trap count: " + trapcount;
    }
}

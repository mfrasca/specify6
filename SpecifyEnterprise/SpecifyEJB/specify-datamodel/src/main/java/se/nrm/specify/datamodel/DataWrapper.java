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
    
//    @XmlElement
    private List<Object[]> dataList;
    
    @XmlElement
    private String eventcount;
    @XmlElement
    private String trapcount;
    
    @XmlElement
    private Collectingevent event;
    
    @XmlElement
    private int numSortedVials;
    
    @XmlElement
    private int numOfEvent;
    
    @XmlElement
    private String collectionCode;
     
    public DataWrapper() {
    }
    
    
     
    public DataWrapper(List list) {
        this.list = list;
    }
     

    public DataWrapper(List<String> list, String eventcount, String trapcount) {
        this.list = list;
        this.eventcount = eventcount;
        this.trapcount = trapcount;
    }
    
    public DataWrapper(Collectingevent event, int numOfEvent) {
        this.event = event;
        this.numOfEvent = numOfEvent;
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

    public List<Object[]> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object[]> dataList) {
        this.dataList = dataList;
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

    public int getNumOfEvent() {
        return numOfEvent;
    }
    
    
    
    

    @Override
    public String toString() {
        return "data is: List: " + list + " event count: " + eventcount + " trap count: " + trapcount;
    }
}

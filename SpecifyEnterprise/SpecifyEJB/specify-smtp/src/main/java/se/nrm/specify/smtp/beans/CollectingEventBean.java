package se.nrm.specify.smtp.beans;

import java.io.Serializable;
import se.nrm.specify.datamodel.Collectingevent; 

/**
 *
 * @author idali
 * 
 * Managed bean representing a CollectingEvent
 */
public class CollectingEventBean implements Serializable {
    
    private Collectingevent event;
    private Collectingevent event1;
    private Collectingevent event2;
    private Collectingevent event3;
    private Collectingevent event4;
    private Collectingevent event5;
    private Collectingevent event6;
    private Collectingevent event7;
    private Collectingevent event8;
    private Collectingevent event9;
    private Collectingevent event10;
    private Collectingevent event11;
    

     
    private String eventId;
    
    private String eventNum1;
    private String eventNum2;
    private String eventNum3;
    private String eventNum4;
    private String eventNum5;
    private String eventNum6;
    private String eventNum7;
    private String eventNum8;
    private String eventNum9;
    private String eventNum10;
    private String eventNum11;
    
    private int currentEventIndex;
//    private String eventName;
 
    private boolean[] isEventRetrieved = new boolean[11];               // identify if event for this group is retrieved
    
    public CollectingEventBean() { 
    } 
    
    public void setAllEvent(boolean isEvent) { 
        for(int i = 0; i < isEventRetrieved.length; i++) {
            isEventRetrieved[i] = isEvent;
        } 
    }

    public boolean[] getIsEventRetrieved() {
        return isEventRetrieved;
    }

    public void setIsEventRetrieved(boolean[] isEventRetrieved) {
        this.isEventRetrieved = isEventRetrieved;
    }
    
    public void setNewEvent(int value, boolean eventFound) {
        isEventRetrieved[value - 1] = eventFound;
    }
     
    public void setEvent(int value, Collectingevent event) { 
        setEvent(event); 
        switch (value) {
            case 1: setEvent1(event); break;
            case 2: setEvent2(event); break;
            case 3: setEvent3(event); break;
            case 4: setEvent4(event); break;
            case 5: setEvent5(event); break; 
            case 6: setEvent6(event); break;
            case 7: setEvent7(event); break;
            case 8: setEvent8(event); break;
            case 9: setEvent9(event); break; 
            case 10: setEvent10(event); break;
            case 11: setEvent11(event); break;
        }  
    }
    
    public String getEventId(int value ){
        switch (value) {
            case 1: 
                setEventId(eventNum1);
                return eventNum1; 
            case 2: 
                setEventId(eventNum2);
                return eventNum2; 
            case 3: 
                setEventId(eventNum3);
                return eventNum3; 
            case 4: 
                setEventId(eventNum4);
                return eventNum4; 
            case 5: 
                setEventId(eventNum5);
                return eventNum5; 
            case 6: 
                setEventId(eventNum6);
                return eventNum6; 
            case 7: 
                setEventId(eventNum7);
                return eventNum7; 
            case 8: 
                setEventId(eventNum8);
                return eventNum8; 
            case 9: 
                setEventId(eventNum9);
                return eventNum9; 
            case 10: 
                setEventId(eventNum10);
                return eventNum10; 
            case 11: 
                setEventId(eventNum11);
                return eventNum11; 
        }
        
        return eventNum1;
    }

    public Collectingevent getEvent() {
        return event;
    }

    public void setEvent(Collectingevent event) {
        this.event = event;
    } 

    public Collectingevent getEvent1() {
        return event1;
    }
    
    public void setEvent1(Collectingevent event1) {
        this.event1 = event1;
    }

    public Collectingevent getEvent2() {
        return event2;
    }

    public void setEvent2(Collectingevent event2) {
        this.event2 = event2;
    }

    public Collectingevent getEvent3() {
        return event3;
    }

    public void setEvent3(Collectingevent event3) {
        this.event3 = event3;
    }

    public Collectingevent getEvent4() {
        return event4;
    }

    public void setEvent4(Collectingevent event4) {
        this.event4 = event4;
    }

    public Collectingevent getEvent5() {
        return event5;
    }

    public void setEvent5(Collectingevent event5) {
        this.event5 = event5;
    }

    public Collectingevent getEvent6() {
        return event6;
    }

    public void setEvent6(Collectingevent event6) {
        this.event6 = event6;
    }

    public Collectingevent getEvent7() {
        return event7;
    }

    public void setEvent7(Collectingevent event7) {
        this.event7 = event7;
    }
    
    public Collectingevent getEvent8() {
        return event8;
    }

    public void setEvent8(Collectingevent event8) {
        this.event8 = event8;
    }

    public Collectingevent getEvent9() {
        return event9;
    }

    public void setEvent9(Collectingevent event9) {
        this.event9 = event9;
    }
    

    public Collectingevent getEvent10() {
        return event10;
    }

    public void setEvent10(Collectingevent event10) {
        this.event10 = event10;
    }

    public Collectingevent getEvent11() {
        return event11;
    }

    public void setEvent11(Collectingevent event11) {
        this.event11 = event11;
    }

    
    
    
    
 
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventNum1() {
        return eventNum1;
    }

    public void setEventNum1(String eventNum1) {
        this.eventNum1 = eventNum1;
    }

    public String getEventNum10() {
        return eventNum10;
    }

    public void setEventNum10(String eventNum10) {
        this.eventNum10 = eventNum10;
    }

    public String getEventNum11() {
        return eventNum11;
    }

    public void setEventNum11(String eventNum11) {
        this.eventNum11 = eventNum11;
    }

    public String getEventNum2() {
        return eventNum2;
    }

    public void setEventNum2(String eventNum2) {
        this.eventNum2 = eventNum2;
    }

    public String getEventNum3() {
        return eventNum3;
    }

    public void setEventNum3(String eventNum3) {
        this.eventNum3 = eventNum3;
    }

    public String getEventNum4() {
        return eventNum4;
    }

    public void setEventNum4(String eventNum4) {
        this.eventNum4 = eventNum4;
    }

    public String getEventNum5() {
        return eventNum5;
    }

    public void setEventNum5(String eventNum5) {
        this.eventNum5 = eventNum5;
    }

    public String getEventNum6() {
        return eventNum6;
    }

    public void setEventNum6(String eventNum6) {
        this.eventNum6 = eventNum6;
    }

    public String getEventNum7() {
        return eventNum7;
    }

    public void setEventNum7(String eventNum7) {
        this.eventNum7 = eventNum7;
    }

    public String getEventNum8() {
        return eventNum8;
    }

    public void setEventNum8(String eventNum8) {
        this.eventNum8 = eventNum8;
    }

    public String getEventNum9() {
        return eventNum9;
    }

    public void setEventNum9(String eventNum9) {
        this.eventNum9 = eventNum9;
    }

    
 
 
     

    public int getCurrentEventIndex() {
        return currentEventIndex;
    }

    public void setCurrentEventIndex(int currentEventIndex) {
        this.currentEventIndex = currentEventIndex;
    }

//    public String getEventName() {
//        return eventName;
//    }
//
//    public void setEventName(String eventName) {
//        this.eventName = eventName;
//    }
    
    
} 
    
    

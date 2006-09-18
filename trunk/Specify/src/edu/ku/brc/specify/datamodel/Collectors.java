package edu.ku.brc.specify.datamodel;

import java.util.Date;




/**

 */
public class Collectors  implements java.io.Serializable {

    // Fields    

     protected Long collectorsId;
     protected Integer orderNumber;
     protected String remarks;
     protected Date timestampModified;
     protected Date timestampCreated;
     protected String lastEditedBy;
     protected CollectingEvent collectingEvent;
     protected Agent agent;


    // Constructors

    /** default constructor */
    public Collectors() {
    }
    
    /** constructor with id */
    public Collectors(Long collectorsId) {
        this.collectorsId = collectorsId;
    }
   
    
    

    // Initializer
    public void initialize()
    {
        collectorsId = null;
        orderNumber = null;
        remarks = null;
        timestampModified = null;
        timestampCreated = new Date();
        lastEditedBy = null;
        collectingEvent = null;
        agent = null;
    }
    // End Initializer

    // Property accessors

    /**
     * 
     */
    public Long getCollectorsId() {
        return this.collectorsId;
    }
    
    public void setCollectorsId(Long collectorsId) {
        this.collectorsId = collectorsId;
    }

    /**
     * 
     */
    public Integer getOrderNumber() {
        return this.orderNumber;
    }
    
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 
     */
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     */
    public Date getTimestampModified() {
        return this.timestampModified;
    }
    
    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    /**
     * 
     */
    public Date getTimestampCreated() {
        return this.timestampCreated;
    }
    
    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    /**
     * 
     */
    public String getLastEditedBy() {
        return this.lastEditedBy;
    }
    
    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    /**
     *      * The CollectingEvent the agent participated in
     */
    public CollectingEvent getCollectingEvent() {
        return this.collectingEvent;
    }
    
    public void setCollectingEvent(CollectingEvent collectingEvent) {
        this.collectingEvent = collectingEvent;
    }

    /**
     *      * Link to Collectors's record in Agent table
     */
    public Agent getAgent() {
        return this.agent;
    }
    
    public void setAgent(Agent agent) {
        this.agent = agent;
    }





    // Add Methods

    // Done Add Methods

    // Delete Methods

    // Delete Add Methods
}

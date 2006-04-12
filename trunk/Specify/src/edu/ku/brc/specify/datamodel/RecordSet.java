package edu.ku.brc.specify.datamodel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;




/**
 * RecordSet generated by hbm2java
 */
public class RecordSet  implements java.io.Serializable {

    // Fields

     private Long recordSetID;
     private String name;
     private Integer tableId;
     private Date timestampModified;
     private Date timestampCreated;
     protected Set<RecordSetItem> items;
     protected SpecifyUser owner;


    // Constructors

    /** default constructor */
    public RecordSet() {
    }

    /** constructor with id */
    public RecordSet(Long recordSetID) {
        this.recordSetID = recordSetID;
    }




    // Initializer
    public void initialize()
    {
        recordSetID = null;
        name = null;
        tableId = null;
        timestampModified = null;
        timestampCreated = Calendar.getInstance().getTime();
        items = new HashSet<RecordSetItem>();
        owner = null;
    }
    // End Initializer

    // Property accessors

    /**
     *
     */
    public Long getRecordSetID() {
        return this.recordSetID;
    }

    public void setRecordSetID(Long recordSetID) {
        this.recordSetID = recordSetID;
    }

    /**
     *
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public Integer getTableId() {
        return this.tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
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
    public Set<RecordSetItem> getItems() {
        return this.items;
    }

    public void setItems(Set<RecordSetItem> items) {
        this.items = items;
    }

    /**
     *
     */
    public SpecifyUser getOwner() {
        return this.owner;
    }

    public void setOwner(SpecifyUser owner) {
        this.owner = owner;
    }




    // Add Methods

    public void addItem(final RecordSetItem item)
    {
        this.items.add(item);
    }

    // Done Add Methods
}

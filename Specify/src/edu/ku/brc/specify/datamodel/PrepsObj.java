package edu.ku.brc.specify.datamodel;

import java.util.*;




/**
 *        @hibernate.class
 *         table="PrepsObj"
 *     
 */
public class PrepsObj  implements java.io.Serializable {

    // Fields    

     protected Integer preparationId;
     protected String preparationMethod;
     protected Integer subNumber;
     protected Integer count;
     protected String storagelocation;
     protected String url;
     protected String remarks;
     protected Integer preparedDate;
     protected Date timestampCreated;
     protected Date timestampModified;
     protected String lastEditedBy;
     protected Set loanPhysicalObjects;
     protected Set prepAttrs;
     private CollectionObj collectionObj;
     private Agent agent;
     private CollectionObjectType collectionObjectType;


    // Constructors

    /** default constructor */
    public PrepsObj() {
    }
    
    /** constructor with id */
    public PrepsObj(Integer preparationId) {
        this.preparationId = preparationId;
    }
   
    
    

    // Property accessors

    /**
     *      *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="PrepsObjID"
     *         
     */
    public Integer getPreparationId() {
        return this.preparationId;
    }
    
    public void setPreparationId(Integer preparationId) {
        this.preparationId = preparationId;
    }

    /**
     *      *            @hibernate.property
     *             column="PreparationMethod"
     *             length="50"
     *         
     */
    public String getPreparationMethod() {
        return this.preparationMethod;
    }
    
    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }

    /**
     *      *            @hibernate.property
     *             column="SubNumber"
     *             length="10"
     *         
     */
    public Integer getSubNumber() {
        return this.subNumber;
    }
    
    public void setSubNumber(Integer subNumber) {
        this.subNumber = subNumber;
    }

    /**
     *      *            @hibernate.property
     *             column="Count"
     *             length="10"
     *         
     */
    public Integer getCount() {
        return this.count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *      *            @hibernate.property
     *             column="Storagelocation"
     *             length="50"
     *         
     */
    public String getStoragelocation() {
        return this.storagelocation;
    }
    
    public void setStoragelocation(String storagelocation) {
        this.storagelocation = storagelocation;
    }

    /**
     *      *            @hibernate.property
     *             column="Url"
     *             length="512"
     *         
     */
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *      *            @hibernate.property
     *             column="Remarks"
     *             length="1073741823"
     *         
     */
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     *      *            @hibernate.property
     *             column="PreparedDate"
     *             length="10"
     *         
     */
    public Integer getPreparedDate() {
        return this.preparedDate;
    }
    
    public void setPreparedDate(Integer preparedDate) {
        this.preparedDate = preparedDate;
    }

    /**
     *      *            @hibernate.property
     *             column="TimestampCreated"
     *             length="23"
     *         
     */
    public Date getTimestampCreated() {
        return this.timestampCreated;
    }
    
    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    /**
     *      *            @hibernate.property
     *             column="TimestampModified"
     *             length="23"
     *         
     */
    public Date getTimestampModified() {
        return this.timestampModified;
    }
    
    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }

    /**
     *      *            @hibernate.property
     *             column="LastEditedBy"
     *             length="50"
     *         
     */
    public String getLastEditedBy() {
        return this.lastEditedBy;
    }
    
    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    /**
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="PhysicalObjectID"
     *            @hibernate.collection-one-to-many
     *             class="edu.ku.brc.specify.datamodel.LoanPhysicalObject"
     *         
     */
    public Set getLoanPhysicalObjects() {
        return this.loanPhysicalObjects;
    }
    
    public void setLoanPhysicalObjects(Set loanPhysicalObjects) {
        this.loanPhysicalObjects = loanPhysicalObjects;
    }

    /**
     *      *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="PrepsObjID"
     *            @hibernate.collection-one-to-many
     *             class="edu.ku.brc.specify.datamodel.PrepAttrs"
     *         
     */
    public Set getPrepAttrs() {
        return this.prepAttrs;
    }
    
    public void setPrepAttrs(Set prepAttrs) {
        this.prepAttrs = prepAttrs;
    }

    /**
     *      *            @hibernate.many-to-one
     *             not-null="true"
     * 			cascade="delete"
     *            @hibernate.column name="CollectionObjID"         
     *         
     */
    public CollectionObj getCollectionObj() {
        return this.collectionObj;
    }
    
    public void setCollectionObj(CollectionObj collectionObj) {
        this.collectionObj = collectionObj;
    }

    /**
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="PreparedByID"         
     *         
     */
    public Agent getAgent() {
        return this.agent;
    }
    
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     *      *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="PhysicalObjectTypeID"         
     *         
     */
    public CollectionObjectType getCollectionObjectType() {
        return this.collectionObjectType;
    }
    
    public void setCollectionObjectType(CollectionObjectType collectionObjectType) {
        this.collectionObjectType = collectionObjectType;
    }




}
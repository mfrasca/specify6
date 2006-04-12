package edu.ku.brc.specify.datamodel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;




/**

 */
public class Borrow  implements java.io.Serializable {

    // Fields    

     protected Integer borrowId;
     private String invoiceNumber;
     private Calendar receivedDate;
     private Calendar originalDueDate;
     private Calendar dateClosed;
     protected String remarks;
     private String text1;
     private String text2;
     private Float number1;
     private Float number2;
     private Date timestampModified;
     private Date timestampCreated;
     private String lastEditedBy;
     private Short closed;
     protected Boolean yesNo1;
     protected Boolean yesNo2;
     private Calendar currentDueDate;
     protected Set<BorrowShipment> borrowShipments;
     protected Set<BorrowAgent> borrowAgents;
     protected Set<BorrowMaterial> borrowMaterials;


    // Constructors

    /** default constructor */
    public Borrow() {
    }
    
    /** constructor with id */
    public Borrow(Integer borrowId) {
        this.borrowId = borrowId;
    }
   
    
    

    // Initializer
    public void initialize()
    {
        borrowId = null;
        invoiceNumber = null;
        receivedDate = null;
        originalDueDate = null;
        dateClosed = null;
        remarks = null;
        text1 = null;
        text2 = null;
        number1 = null;
        number2 = null;
        timestampModified = null;
        timestampCreated = Calendar.getInstance().getTime();
        lastEditedBy = null;
        closed = null;
        yesNo1 = null;
        yesNo2 = null;
        currentDueDate = null;
        borrowShipments = new HashSet<BorrowShipment>();
        borrowAgents = new HashSet<BorrowAgent>();
        borrowMaterials = new HashSet<BorrowMaterial>();
    }
    // End Initializer

    // Property accessors

    /**
     *      * Primary key
     */
    public Integer getBorrowId() {
        return this.borrowId;
    }
    
    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    /**
     *      * Lender's loan number
     */
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     *      * Date material was received
     */
    public Calendar getReceivedDate() {
        return this.receivedDate;
    }
    
    public void setReceivedDate(Calendar receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     *      * Original Due date for loan
     */
    public Calendar getOriginalDueDate() {
        return this.originalDueDate;
    }
    
    public void setOriginalDueDate(Calendar originalDueDate) {
        this.originalDueDate = originalDueDate;
    }

    /**
     *      * Date loan was closed
     */
    public Calendar getDateClosed() {
        return this.dateClosed;
    }
    
    public void setDateClosed(Calendar dateClosed) {
        this.dateClosed = dateClosed;
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
     *      * User definable
     */
    public String getText1() {
        return this.text1;
    }
    
    public void setText1(String text1) {
        this.text1 = text1;
    }

    /**
     *      * User definable
     */
    public String getText2() {
        return this.text2;
    }
    
    public void setText2(String text2) {
        this.text2 = text2;
    }

    /**
     *      * User definable
     */
    public Float getNumber1() {
        return this.number1;
    }
    
    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    /**
     *      * User definable
     */
    public Float getNumber2() {
        return this.number2;
    }
    
    public void setNumber2(Float number2) {
        this.number2 = number2;
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
     *      * False until all material has been returned
     */
    public Short getClosed() {
        return this.closed;
    }
    
    public void setClosed(Short closed) {
        this.closed = closed;
    }

    /**
     *      * User definable
     */
    public Boolean getYesNo1() {
        return this.yesNo1;
    }
    
    public void setYesNo1(Boolean yesNo1) {
        this.yesNo1 = yesNo1;
    }

    /**
     *      * User definable
     */
    public Boolean getYesNo2() {
        return this.yesNo2;
    }
    
    public void setYesNo2(Boolean yesNo2) {
        this.yesNo2 = yesNo2;
    }

    /**
     * 
     */
    public Calendar getCurrentDueDate() {
        return this.currentDueDate;
    }
    
    public void setCurrentDueDate(Calendar currentDueDate) {
        this.currentDueDate = currentDueDate;
    }

    /**
     * 
     */
    public Set<BorrowShipment> getBorrowShipments() {
        return this.borrowShipments;
    }
    
    public void setBorrowShipments(Set<BorrowShipment> borrowShipments) {
        this.borrowShipments = borrowShipments;
    }

    /**
     * 
     */
    public Set<BorrowAgent> getBorrowAgents() {
        return this.borrowAgents;
    }
    
    public void setBorrowAgents(Set<BorrowAgent> borrowAgents) {
        this.borrowAgents = borrowAgents;
    }

    /**
     * 
     */
    public Set<BorrowMaterial> getBorrowMaterials() {
        return this.borrowMaterials;
    }
    
    public void setBorrowMaterials(Set<BorrowMaterial> borrowMaterials) {
        this.borrowMaterials = borrowMaterials;
    }




    // Add Methods

    public void addBorrowShipment(final BorrowShipment borrowShipment)
    {
        this.borrowShipments.add(borrowShipment);
    }

    public void addBorrowAgent(final BorrowAgent borrowAgent)
    {
        this.borrowAgents.add(borrowAgent);
    }

    public void addBorrowMaterial(final BorrowMaterial borrowMaterial)
    {
        this.borrowMaterials.add(borrowMaterial);
    }

    // Done Add Methods
}

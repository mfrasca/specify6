package edu.ku.brc.specify.datamodel;

import java.util.Date;
import java.util.Set;




/**

 */
public class ReferenceWork  implements java.io.Serializable {

    // Fields    

     protected Integer referenceWorkId;
     protected Integer containingReferenceWorkId;
     protected Byte referenceWorkType;
     protected String title;
     protected String publisher;
     protected String placeOfPublication;
     protected String workDate;
     protected String volume;
     protected String pages;
     protected String url;
     protected String libraryNumber;
     protected String remarks;
     protected String text1;
     protected String text2;
     protected Float number1;
     protected Float number2;
     protected Date timestampCreated;
     protected Date timestampModified;
     protected String lastEditedBy;
     protected Short published;
     protected Boolean yesNo1;
     protected Boolean yesNo2;
     private Set localityCitations;
     private Set collectionObjectCitations;
     private Set taxonCitations;
     private Set determinationCitations;
     private Journal journal;
     private Set authors;


    // Constructors

    /** default constructor */
    public ReferenceWork() {
    }
    
    /** constructor with id */
    public ReferenceWork(Integer referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }
   
    
    

    // Property accessors

    /**
     *      * PrimaryKey
     */
    public Integer getReferenceWorkId() {
        return this.referenceWorkId;
    }
    
    public void setReferenceWorkId(Integer referenceWorkId) {
        this.referenceWorkId = referenceWorkId;
    }

    /**
     *      * Link to Reference containing (if Section)
     */
    public Integer getContainingReferenceWorkId() {
        return this.containingReferenceWorkId;
    }
    
    public void setContainingReferenceWorkId(Integer containingReferenceWorkId) {
        this.containingReferenceWorkId = containingReferenceWorkId;
    }

    /**
     * 
     */
    public Byte getReferenceWorkType() {
        return this.referenceWorkType;
    }
    
    public void setReferenceWorkType(Byte referenceWorkType) {
        this.referenceWorkType = referenceWorkType;
    }

    /**
     *      * Title of reference
     */
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     */
    public String getPublisher() {
        return this.publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * 
     */
    public String getPlaceOfPublication() {
        return this.placeOfPublication;
    }
    
    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    /**
     * 
     */
    public String getWorkDate() {
        return this.workDate;
    }
    
    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    /**
     *      * Volume/Issue for Journal articles
     */
    public String getVolume() {
        return this.volume;
    }
    
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     *      * Number of pages or Page range for Journal articles
     */
    public String getPages() {
        return this.pages;
    }
    
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * 
     */
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     */
    public String getLibraryNumber() {
        return this.libraryNumber;
    }
    
    public void setLibraryNumber(String libraryNumber) {
        this.libraryNumber = libraryNumber;
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
    public Date getTimestampCreated() {
        return this.timestampCreated;
    }
    
    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
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
    public String getLastEditedBy() {
        return this.lastEditedBy;
    }
    
    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    /**
     * 
     */
    public Short getPublished() {
        return this.published;
    }
    
    public void setPublished(Short published) {
        this.published = published;
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
    public Set getLocalityCitations() {
        return this.localityCitations;
    }
    
    public void setLocalityCitations(Set localityCitations) {
        this.localityCitations = localityCitations;
    }

    /**
     * 
     */
    public Set getCollectionObjectCitations() {
        return this.collectionObjectCitations;
    }
    
    public void setCollectionObjectCitations(Set collectionObjectCitations) {
        this.collectionObjectCitations = collectionObjectCitations;
    }

    /**
     * 
     */
    public Set getTaxonCitations() {
        return this.taxonCitations;
    }
    
    public void setTaxonCitations(Set taxonCitations) {
        this.taxonCitations = taxonCitations;
    }

    /**
     * 
     */
    public Set getDeterminationCitations() {
        return this.determinationCitations;
    }
    
    public void setDeterminationCitations(Set determinationCitations) {
        this.determinationCitations = determinationCitations;
    }

    /**
     *      * Link to Journal containing the reference (if applicable)
     */
    public Journal getJournal() {
        return this.journal;
    }
    
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    /**
     * 
     */
    public Set getAuthors() {
        return this.authors;
    }
    
    public void setAuthors(Set authors) {
        this.authors = authors;
    }




}
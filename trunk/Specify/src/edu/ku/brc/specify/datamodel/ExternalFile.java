package edu.ku.brc.specify.datamodel;

import java.util.Date;
import java.util.Set;




/**
 * ExternalFile generated by hbm2java
 */
public interface ExternalFile
{

    //public Integer getExternalFileId();
    
    //public void setExternalFileId(Integer externalFileId);


    public String getMimeType();
    
    public void setMimeType(String mimeType);


    public String getFileName();
    
    public void setFileName(String fileName);


    public Integer getFileCreatedDate();
    
    public void setFileCreatedDate(Integer fileCreatedDate);


    public String getRemarks();
    
    public void setRemarks(String remarks);


    public String getExternalLocation();
    
    public void setExternalLocation(String externalLocation);


    public Date getTimestampCreated();
    
    public void setTimestampCreated(Date timestampCreated);


    public Date getTimestampModified();
    
    public void setTimestampModified(Date timestampModified);


    public String getLastEditedBy();
    
    public void setLastEditedBy(String lastEditedBy);


    public Agent getCreatedByAgent();
    
    public void setCreatedByAgent(Agent createdByAgent);
    
    /**
     * 
     */
    public Set getExternalFiles();
    
    public void setExternalFiles(Set externalFiles);
}

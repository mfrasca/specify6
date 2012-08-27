package se.nrm.specify.datamodel;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version; 
import javax.xml.bind.annotation.XmlAttribute; 
//import javax.validation.constraints.NotNull;

/**
 *
 * @author idali
 */
@MappedSuperclass 
public abstract class BaseEntity implements Serializable, SpecifyBean {

    private static final long serialVersionUID = 1L;
    
    @XmlAttribute
    @Version
    @Column(name = "Version") 
    Integer version;
    
    @Basic(optional = false)
//    @NotNull
    @Column(name = "TimestampCreated")
    @Temporal(TemporalType.TIMESTAMP)
    Date timestampCreated;
    
    @Column(name = "TimestampModified")
    @Temporal(TemporalType.TIMESTAMP)
    Date timestampModified;
     
    public BaseEntity() {
    }

    public BaseEntity(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

//    public abstract String getIdentityString();
     
 
    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Date getTimestampModified() {
        return timestampModified;
    }

    public void setTimestampModified(Date timestampModified) {
        this.timestampModified = timestampModified;
    }
}

package se.nrm.specify.smtp.beans;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author idali
 */
public class SampleBean implements Serializable {
    
    private String sampleName; 
    private String sampleName2;
    private String sampleNumber; 
    
    private String totalSample;  
    
    private String sampleNameId; 
    private String desc;
     
    public SampleBean() { 
    }
     
    public SampleBean(String sampleName, String sampleNumber, String sampleName2) {
        this.sampleName = sampleName; 
        this.sampleNumber = sampleNumber; 
        this.sampleName2 = sampleName2;
    }
    
    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    } 
     
    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    } 

    public String getTotalSample() {
        return totalSample;
    }

    public void setTotalSample(List<SampleBean> beanlist) {
        
        int count = 0;
        for(SampleBean bean : beanlist) {
            count += Integer.parseInt(bean.getSampleNumber());
        }
        this.totalSample = String.valueOf(count);
    }

    public String getSampleNameId() { 
        return sampleName2.replace(" ", "");
    } 

    public String getSampleName2() {
        return sampleName2;
    }

    public void setSampleName2(String sampleName2) {
        this.sampleName2 = sampleName2;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    } 
    
    
    
    
    @Override
    public String toString() {
        return "SampleBean - [samplename: " + sampleName + "] - [samplenumber: " + sampleNumber + "]" + " [sampleid: " + sampleNameId + "] sample name 2: [" + sampleName2 + "]";
    } 
}

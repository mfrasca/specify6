package se.nrm.specify.smtp.beans;

import java.io.Serializable;
import java.util.List;
import se.nrm.specify.smtp.util.Constant;

/**
 *
 * @author idali
 */
public class SampleGroupBean implements Serializable {

    private String groupName;
    private List<SampleBean> sampleBeanList;
    private List<SampleBean> selectedSampleBeanList;

    /**
     * Default constructor 
     */
    public SampleGroupBean() {
    }

    public SampleGroupBean(String groupName, List<SampleBean> sampleBeanList) {
        this.groupName = groupName;
        this.sampleBeanList = sampleBeanList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<SampleBean> getSampleBeanList() {
        return sampleBeanList;
    }

    public void setSampleBeanList(List<SampleBean> sampleBeanList) {
        this.sampleBeanList = sampleBeanList;
    }

    public List<SampleBean> getSelectedSampleBeanList() {
        return selectedSampleBeanList;
    }

    public void setSelectedSampleBeanList(List<SampleBean> selectedSampleBeanList) {
        this.selectedSampleBeanList = selectedSampleBeanList;
    }
     
    

    public void initSampleGroupBean(int value) {
        switch (value) {
            case 1:
                setGroupName(Constant.SORTING_GROUP_NAME_RAW_SAMPLE);
                setSampleBeanList(Constant.getRawTrapSampleList());
                break;
            case 2:
                setGroupName(Constant.SORTING_GROUP_NAME_BRACHCERA);
                setSampleBeanList(Constant.getBrachyceraList());
                break;
            case 3:
                setGroupName(Constant.SORTING_GROUP_NAME_NEMATOCERA);
                setSampleBeanList(Constant.getNematoceraList());
                break;
            case 4:
                setGroupName(Constant.SORTING_GROUP_NAME_HYMENOPTERA);
                setSampleBeanList(Constant.getHymenopteraList());
                break;
            case 5:
                setGroupName(Constant.SORTING_GROUP_NAME_BRACONIDAE);
                setSampleBeanList(Constant.getBraconidaeList());
                break;
            case 6:
                setGroupName(Constant.SORTING_GROUP_NAME_ICHNEUMONIDAE);
                setSampleBeanList(Constant.getIchneumonidaeList());
                break;
            case 7:
                setGroupName(Constant.SORTING_GROUP_NAME_CHALCIDOIDEA);
                setSampleBeanList(Constant.getChalcidoideaList());
                break; 
        } 
    }
}

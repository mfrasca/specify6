package se.nrm.specify.smtp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.nrm.specify.datamodel.Collectingevent;
import se.nrm.specify.datamodel.DataWrapper;
import se.nrm.specify.datamodel.Determination;
import se.nrm.specify.datamodel.Specifyuser;
import se.nrm.specify.datamodel.Taxon;
import se.nrm.specify.smtp.beans.CollectingEventBean;
import se.nrm.specify.smtp.beans.SampleBean;
import se.nrm.specify.smtp.beans.SampleGroupBean;
import se.nrm.specify.smtp.beans.StatisticsBean;
import se.nrm.specify.smtp.beans.StyleBean; 
import se.nrm.specify.smtp.util.CSSName;
import se.nrm.specify.smtp.util.Constant;
import se.nrm.specify.smtp.util.HelpClass;
import se.nrm.specify.smtp.util.NavigationHandler;
import se.nrm.specify.smtp.webclient.DataServiceClient;

/**
 *
 * @author idali
 */
public class SmtpController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private Specifyuser user;
    private String collection = "smtpinv";                          // radio button group default value is SMTP Inventory
    
    private String numberOfDetermination;
    private String numberOfDeterminationSorted;  
    private List<String> taxonNameList = new ArrayList<String>();
    private String strDate; 
     
    private SampleGroupBean sampleGroupBean; 
    private SampleBean sampleBean; 
    private CollectingEventBean eventBean;
    private StyleBean styleBean;
    private StatisticsBean statisticsBean;
    
    @Inject
    private DataServiceClient service;                  // web service client

    public SmtpController() {
    }

    public CollectingEventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(CollectingEventBean eventBean) {
        this.eventBean = eventBean;
    }

    public StyleBean getStyleBean() {
        return styleBean;
    }

    public void setStyleBean(StyleBean styleBean) {
        this.styleBean = styleBean;
    }

    public StatisticsBean getStatisticsBean() {
        return statisticsBean;
    }

    public void setStatisticsBean(StatisticsBean statisticsBean) {
        this.statisticsBean = statisticsBean;
    }

    public DataServiceClient getService() {
        return service;
    }

    public void setService(DataServiceClient service) {
        this.service = service;
    }

    public Specifyuser getUser() {
        user = new Specifyuser();
        user.setName("Ida Li");
        return user;
    }

    public void setUser(Specifyuser user) {
        this.user = user;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    } 

    public List<String> getTaxonNameList() {
        return taxonNameList;
    }

    public void setTaxonNameList(List<String> taxonNameList) {
        this.taxonNameList = taxonNameList;
    }

    public SampleBean getSampleBean() {
        return sampleBean;
    }

    public void setSampleBean(SampleBean sampleBean) {
        this.sampleBean = sampleBean;
    }

    public SampleGroupBean getSampleGroupBean() {
        return sampleGroupBean;
    }

    public void setSampleGroupBean(SampleGroupBean sampleGroupBean) {
        this.sampleGroupBean = sampleGroupBean;
    } 
    
    public String getNumberOfDetermination() {
        return numberOfDetermination;
    }

    public void setNumberOfDetermination(String numberOfDetermination) {
        this.numberOfDetermination = numberOfDetermination;
    }

    public String getNumberOfDeterminationSorted() {
        return numberOfDeterminationSorted;
    }

    public void setNumberOfDeterminationSorted(String numberOfDeterminationSorted) {
        this.numberOfDeterminationSorted = numberOfDeterminationSorted;
    }
    
    

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
 
    
    
    
    
    

    /**
     * selectSMTPCollection - use customized DINA-SMTP interface
     * @return - navigation-rule
     */
    public String selectSMTPCollection() {

        logger.info("selectSMTPCollection");

        styleBean.initStyleBean();                                      // set initial ui
        eventBean.setAllEvent(false);                                   // set event for all group retrieved to false
        
        if(taxonNameList == null || taxonNameList.size() <= 0) {        // retrieve all the taxon name from database
            taxonNameList = service.getAllTaxonNames();
        }   
        return NavigationHandler.getInstance().SUCCESS;                 // navigation role to next page
    }

    /**
     * opens specify client
     * @return 
     */
    public String selectCollection() {

        logger.info("selectCollection");
        
        // this method is not implemented
        
        collection = "smtpinv";     
        return "";   // TODO:
    }

    /**
     * Method getevent retrieves Collectingevent by StationFieldNumber from database
     * 
     * @param value - stationfieldnumber
     * @return outcome page
     */
    public String getevent(int value) {

        logger.info("getevent: {}", value);

        String stationFieldNumber = "Event ID " + eventBean.getEventId(value);          // eventId is prefixed by EventID
        Collectingevent event = service.getCollectingevent(stationFieldNumber);         // get collectingevent by stationFieldNumber

        boolean eventFound = true;
        if (event == null) {                // if event not found, return error message
            eventFound = false;
            FacesMessage fm = new FacesMessage("Event with id: " + stationFieldNumber + " not found.");
            FacesContext.getCurrentInstance().addMessage("Invalid event id", fm);
        }
        eventBean.setNewEvent(value, eventFound);
        eventBean.setEvent(value, event);
        styleBean.resetStyle(eventBean.getIsEventRetrieved());

        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }

    /**
     * Openform 
     * @param value
     * @return outcome page
     */
    public String openform(int value) {

        logger.info("openform: {}", value);
 
        sampleGroupBean.initSampleGroupBean(value);  
         
        eventBean.setCurrentEventIndex(value);
        return NavigationHandler.getInstance().SAMPLELIST_VIEW;   // navigation role to next page
    }

    /**
     * create sample list
     * @return outcome page
     */
    public String createlist() {

        logger.info("createlist");

        List<SampleBean> createdSampleBeans = new ArrayList<SampleBean>();
        for (SampleBean bean : sampleGroupBean.getSampleBeanList()) {
            if (bean.getSampleNumber() != null && bean.getSampleNumber().length() > 0) {
                createdSampleBeans.add(bean);
            }
        }
        sampleGroupBean.setSelectedSampleBeanList(createdSampleBeans);
        
        styleBean.setNewrecorddiv(CSSName.getInstance().NEW_RECORD_DIV_INVISIBLE);
        styleBean.setDialogbox(CSSName.getInstance().DIALOGBOX_INVISIBLE);
        styleBean.setDialogboxedit(CSSName.getInstance().DIALOGBOXEDIT_INVISIBLE); 
        styleBean.setSamplelistdiv(false);
        styleBean.setSamplelisteditdiv(true); 
        
        if(!service.validateSampleList(createdSampleBeans)) {                   // validate sample list
            return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;           // if sample list not valid, return to same page
        }
        return NavigationHandler.getInstance().SUCCESS;
    }
    
    
    public String removesampleedit() {
        
        logger.info("removesampleedit");
        
        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }

    /**
     * Cancel go to home
     * 
     * @return start page
     */
    public String cancellist() {

        logger.info("cancellist");

        return NavigationHandler.getInstance().HOME;
    }

    public String submitlist() {

        logger.info("submitlist"); 
        String taxonName = sampleGroupBean.getGroupName();
        if(taxonName.equals(Constant.SORTING_GROUP_NAME_RAW_SAMPLE)) {
            taxonName = Constant.RAW_SAMPLE_TAXON;
        } 
  
        int collectingEventId = eventBean.getEvent().getCollectingEventID();
        List<Determination> determination = service.getDeterminationsByTaxonAndCollection(taxonName, String.valueOf(collectingEventId), collection);
        numberOfDetermination = String.valueOf(determination.size());
        numberOfDeterminationSorted = numberOfDetermination;
         
        String navigationPath = NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
        if(determination.size() > 0) {
            styleBean.setDialogbox(CSSName.getInstance().DIALOGBOX_VISIBLE);
            styleBean.setDialogboxedit(CSSName.getInstance().DIALOGBOXEDIT_INVISIBLE);
            styleBean.setSamplelistdiv(true);
            styleBean.setSamplelisteditdiv(false);
        } else {
            navigationPath = NavigationHandler.getInstance().CREATE_SORTING_VIEW;
        }
        
        styleBean.setNewrecorddiv(CSSName.getInstance().NEW_RECORD_DIV_INVISIBLE);
        sampleBean = new SampleBean();
        return navigationPath;
    }

    public String addnewsample() {

        logger.info("addnewsample");
 
        styleBean.setNewrecorddiv(CSSName.getInstance().NEW_RECORD_DIV_VISIBLE);

        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }

    /**
     * Add new sample in sample list
     * @return 
     */
    public String createnewsample() {

        logger.info("createnewsample");  
        
        boolean isInList = false;
        
        if(sampleBean.getSampleName() == null || sampleBean.getSampleName().length() <= 0  
                                              || sampleBean.getSampleNumber() == null
                                              || sampleBean.getSampleNumber().length() <= 0) {    
            FacesMessage fm = new FacesMessage("Field can not be empty.");
            FacesContext.getCurrentInstance().addMessage("empty field", fm);
        } else {
            for(SampleBean bean : sampleGroupBean.getSelectedSampleBeanList()) {
                if(bean.getSampleName().equals(sampleBean.getSampleName())) {
                    isInList = true; 
                    FacesMessage fm = new FacesMessage("Sample: " + bean.getSampleName() + " is in the list.");
                    FacesContext.getCurrentInstance().addMessage("Duplicate sample", fm);
                }
            }
               
            if(!isInList) {
                sampleBean.setSampleName2(sampleBean.getSampleName());
                sampleGroupBean.getSelectedSampleBeanList().add(sampleBean);
                sampleBean = new SampleBean();
                styleBean.setNewrecorddiv(CSSName.getInstance().NEW_RECORD_DIV_INVISIBLE);
            }
            
        }  
        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }
    
    public String canceladdnewsample() {
        
        logger.info("canceladdnewsample");
        
        styleBean.setNewrecorddiv(CSSName.getInstance().NEW_RECORD_DIV_INVISIBLE);
        sampleBean = new SampleBean();
        
        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }

    /**
     * Remove sample from sample list
     * 
     * @param sample
     * @return 
     */
    public String removesample(SampleBean bean) {

        logger.info("removesample: {}", bean.getSampleName());

        List<SampleBean> newlist = new ArrayList<SampleBean>();
        newlist.addAll(sampleGroupBean.getSelectedSampleBeanList());

        newlist.remove(bean);
        sampleGroupBean.setSelectedSampleBeanList(newlist);

        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }

    /**
     * Edit sample in sample list
     * 
     * @param bean
     * @return 
     */
    public String editsample(SampleBean bean) {

        logger.info("editsample: {}", bean.getSampleName());  
        
        if(bean.getSampleName() == null || bean.getSampleName().length() <= 0) {
            for(SampleBean samplebean : sampleGroupBean.getSelectedSampleBeanList()) {  
                if(bean.getSampleName2().equals(samplebean.getSampleName2())) { 
                    bean.setSampleName(samplebean.getSampleName2()); 
                } 
            }
        } 
        
        if(bean.getSampleNumber() == null || bean.getSampleNumber().length() <= 0) {
            bean.setSampleNumber("0");
        }
        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }
     
    /**
     * Autocompletion
     * 
     * @param query
     * @return List<String> - list of taxonnames
     */
    public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();

        if(taxonNameList == null || taxonNameList.size() <= 0) {
            taxonNameList = service.getAllTaxonNames();
        } 
        
        for (String string : taxonNameList) {
            if (string.toLowerCase().startsWith(query.toLowerCase())) {
                results.add(string);
            }
        }
        return results;
    }

    public String openstatisticsform() {

        logger.info("openstatisticsform");

        strDate = HelpClass.getNow();

        Collectingevent event = eventBean.getEvent();
        List<String> determinations = service.getDeterminationsByCollectingevent(event, collection);

        statisticsBean.setDeterminations(determinations); 
        statisticsBean.setStatisticsName(Constant.SMTP_RAW_SAMPLE);
        styleBean.setStatisticeventcountdiv(false); 
        styleBean.setStatistictrapcountdiv(false);
        styleBean.setStatisticeventdiv(true);
        styleBean.setStatistictrapdiv(true);
        styleBean.setStatisticrawsample(false);

        return NavigationHandler.getInstance().STATISTICS_VIEW;
    }

    public String openstatisticstripform() {

        logger.info("openstatisticstripform");

        strDate = HelpClass.getNow();

        String locality = String.valueOf(eventBean.getEvent9().getLocalityID().getLocalityID());
        List<String> determinations = service.getDeterminationByLocality(locality, collection);
        
        statisticsBean.setDeterminations(determinations); 
        statisticsBean.setStatisticsName(Constant.SMTP_TRAP);
        
        styleBean.setStatisticeventdiv(false);
        styleBean.setStatistictrapdiv(true);
        styleBean.setStatisticeventcountdiv(false);
        styleBean.setStatistictrapcountdiv(false);
        styleBean.setStatisticrawsample(true);
        

        return NavigationHandler.getInstance().STATISTICS_VIEW;
    }
    
    public String openstatisticstaxonform() {
        
        logger.info("openstatisticsTaxonform");
         
        strDate = HelpClass.getNow();
        DataWrapper data = service.getDeterminationByTaxon(statisticsBean.getTaxon(), collection);
         
        statisticsBean.setDeterminations(data.getList());
        statisticsBean.setTotalEvents(data.getEventcount());
        statisticsBean.setTotalTraps(data.getTrapcount());
        statisticsBean.setStatisticsName(Constant.SMTP_TAXON + eventBean.getEventNum10());
        
        styleBean.setStatisticeventdiv(false);
        styleBean.setStatistictrapdiv(false);
        styleBean.setStatisticeventcountdiv(true);
        styleBean.setStatistictrapcountdiv(true);
        styleBean.setStatisticrawsample(false);
        
        return NavigationHandler.getInstance().STATISTICS_VIEW;
    }
    
    public String openstatisticsprogressform() {
        
        logger.info("openstatisticsprogressform");
        
        statisticsBean.setStatisticsName(Constant.SMTP_PROGRESS);
        return "";
    }

    public String canceldialogbox(String value) {

        logger.info("CancelDialogbox");
 
        if (value.equals("dialog")) {
            styleBean.setDialogbox(CSSName.getInstance().DIALOGBOX_INVISIBLE);
        } else {
            styleBean.setDialogboxedit(CSSName.getInstance().DIALOGBOXEDIT_INVISIBLE);
        }
        
        styleBean.setSamplelistdiv(false);
        styleBean.setSamplelisteditdiv(true);
         
        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }


    public String editsorting() {

        logger.info("editsorting");

        styleBean.setDialogbox(CSSName.getInstance().DIALOGBOX_INVISIBLE);
        styleBean.setDialogboxedit(CSSName.getInstance().DIALOGBOXEDIT_VISIBLE);
        numberOfDeterminationSorted = numberOfDetermination;

        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }

    public String createsorting(String value) {

        logger.info("createsorting"); 
        
        if(value.equals("no")) {
            numberOfDeterminationSorted = "0";
        } 
        return NavigationHandler.getInstance().CREATE_SORTING_VIEW;
    }

    /**
     * navigate to list edit page
     * 
     * @return navigation role
     */
    public String editlist() {
        
        logger.info("editlist"); 
        
        styleBean.setDialogbox(CSSName.getInstance().DIALOGBOX_INVISIBLE);
        styleBean.setDialogboxedit(CSSName.getInstance().DIALOGBOXEDIT_INVISIBLE);
        styleBean.setSamplelistdiv(false);
        styleBean.setSamplelisteditdiv(true);
        
        return NavigationHandler.getInstance().SUCCESS;
    }

    public String upload() {

        logger.info("upload"); 

        service.uploadData(sampleGroupBean.getSelectedSampleBeanList(), eventBean.getEvent(), numberOfDeterminationSorted, collection);
        sampleBean.setTotalSample(sampleGroupBean.getSelectedSampleBeanList()); 

        strDate = HelpClass.getNow();

        return NavigationHandler.getInstance().SUCCESS;
    }

    public String gettrap() {

        logger.info("gettrap");

        List<Collectingevent> events = service.getCollectingeventByLocality(eventBean.getEventNum9());
        statisticsBean.setTotalEvents(String.valueOf(events.size()));

        eventBean.setNewEvent(9, true);
        eventBean.setEvent(9, events.get(0));
        styleBean.resetStyle(eventBean.getIsEventRetrieved());

        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    }
    
    public String gettaxon() {
        
        logger.info("gettaxon");
           
        Taxon taxon = service.getTaxonByTaxonname(eventBean.getEventNum10());
        
        StringBuilder taxontreesb = new StringBuilder();
        if(taxon != null) {
            Taxon parent = taxon.getParentID(); 
            if(parent != null) {
                Taxon gparent = parent.getParentID();
                if(gparent != null) {
                    Taxon ggparent = gparent.getParentID();
                    if(ggparent != null) {
                        Taxon gggparent = ggparent.getParentID();
                        if(gggparent != null) {
                            taxontreesb.append(gggparent.getFullName());
                            taxontreesb.append(" : ");
                        } 
                        taxontreesb.append(ggparent.getFullName());
                        taxontreesb.append(" : ");
                    }
                    taxontreesb.append(gparent.getFullName());
                    taxontreesb.append(" : "); 
                }
                taxontreesb.append(parent.getFullName());
                taxontreesb.append(" : "); 
            } 
            taxontreesb.append(taxon.getFullName()); 
        }
         
        statisticsBean.setTaxontree(taxontreesb.toString());
        eventBean.setNewEvent(10, true); 
        styleBean.resetStyle(eventBean.getIsEventRetrieved());
         
        statisticsBean.setTaxon(taxon);
        
        return NavigationHandler.getInstance().BACK_TO_SAME_PAGE;
    } 
}

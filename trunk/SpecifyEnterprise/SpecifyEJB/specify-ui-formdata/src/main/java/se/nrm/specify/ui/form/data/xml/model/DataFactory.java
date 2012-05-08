package se.nrm.specify.ui.form.data.xml.model;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException; 
import se.nrm.specify.ui.form.data.xml.XmlHandler;
import se.nrm.specify.ui.form.data.xml.XmlHandlerImpl; 

/**
 *
 * @author idali
 */
public class DataFactory {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final String DATASOURCE_PATH = "/xmlfiles/";
    private static final String VIEW_XML = ".views.xml";
    
    private XmlHandler xmlHandler = new XmlHandlerImpl();

    public static DataFactory getInstance() {
        return new DataFactory();
    }

    /**
     * This method take an InputStream for all the metadata and return ViewData object
     * @param inputStream
     * @return ViewData
     */
    public ViewXMLData getViewdataByInputStream(InputStream inputStream) {

        Document dom = null;
        try {
            dom = xmlHandler.parseXml(inputStream);
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new ViewXMLData(dom);
    }

    public ViewXMLData getViewDataFromResource(String resource) {
         
        InputStream inputStream = DataFactory.class.getResourceAsStream(resource);
        return getViewdataByInputStream(inputStream);
    }

    public ViewXMLData getViewDataByDiscipline(String discipline) {

        logger.info("get resourc: {}", discipline);

        StringBuilder sb = new StringBuilder();
        sb.append(DATASOURCE_PATH);
        sb.append(discipline);
        sb.append(VIEW_XML);
         
        InputStream inputStream = DataFactory.class.getResourceAsStream(sb.toString()); 
        return getViewdataByInputStream(inputStream);
    }

    public DataObjFormatter getDataFormatterByInputStream(InputStream inputStream) {

        Document dom = null;
        try {
            dom = xmlHandler.parseXml(inputStream);
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new DataObjFormatter(dom);
    }

    public DataObjFormatter getDataFormatterByResource(String resource) {  
        InputStream inputStream = DataFactory.class.getResourceAsStream(resource); 
        return getDataFormatterByInputStream(inputStream);
    }
}

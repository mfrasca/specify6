package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathExpressionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import se.nrm.specify.ui.form.data.util.CommonString;
import se.nrm.specify.ui.form.data.util.UIXmlUtil;

/**
 *
 * @author idali
 */
public class DataObjFormatter implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String XPATH_FORMATTER = "/formatters/format";
    private final String XPATH_SWITCH_SINGLE = "switch/@single";
    private final String XPATH_SWITCH_FIELD = "switch/@field";
    private final String XPATH_SWITCH_FIELDS = "switch/fields";
    private final String XPATH_FIELD = "field";
    
    private final DomCarrier domCarrier;
    
    private static Map<String, DataFormatter> formatterMap;
    private List<DataFormatter> formatters;
  
    DataObjFormatter(Document document) {
        this.domCarrier = new DomCarrier(document);
        initData();
    }

    DataObjFormatter(DomCarrier domCarrier) {
        this.domCarrier = domCarrier;
        initData();
    }

    public static DataFormatter getDataFormatter(String name) {
        return formatterMap.get(name);
    }

    public Document getDomDocument() {
        return domCarrier.getDocument();
    }

    public DomCarrier getDomCarrier() {
        return domCarrier;
    }

    private void initData() {
        formatters = new ArrayList<DataFormatter>();
        formatterMap = new HashMap<String, DataFormatter>();
        try {
            NodeList formatterNodeList = domCarrier.getListByXpath(XPATH_FORMATTER);
            for (int i = 0; i < formatterNodeList.getLength(); i++) {
                Node singleNode = formatterNodeList.item(i);
                singleNode.getParentNode().removeChild(singleNode);

                String name = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_NAME);
                String title = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_TITLE);
                String classname = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_CLASS);
                boolean isDefault = UIXmlUtil.isTrue(domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_DEFAULT));
                boolean isSingleSwitchValue = UIXmlUtil.isTrue(domCarrier.getStringByXpathAndNode(XPATH_SWITCH_SINGLE, singleNode));
                String field = domCarrier.getStringByXpathAndNode(XPATH_SWITCH_FIELD, singleNode);

                List<String> fields = new ArrayList<String>();
                NodeList fieldsNodeList = domCarrier.getListByXpathAndNode(XPATH_SWITCH_FIELDS, singleNode);
                for (int j = 0; j < fieldsNodeList.getLength(); j++) {
                    Node node = fieldsNodeList.item(j);
                    node.getParentNode().removeChild(node); 
                    
                    NodeList fieldNodeList = domCarrier.getListByXpathAndNode(XPATH_FIELD, node); 
                    for (int k = 0; k < fieldNodeList.getLength(); k++) {
                        Node fieldnode = fieldNodeList.item(k);
                        fieldnode.getParentNode().removeChild(fieldnode);  
                          
                        fields.add(fieldnode.getTextContent()); 
                    } 
                }  
                
                if(field != null && field.length() > 0) {
                    fields.add(field);
                }
                DataFormatter dataformatter = new DataFormatter(name, title, classname, isDefault, isSingleSwitchValue, new ArrayList(new HashSet(fields)));
                formatters.add(dataformatter);
                formatterMap.put(name.toLowerCase(), dataformatter);
            } 
        } catch (XPathExpressionException ex) {
            logger.error("XPathExpressionException: {}", ex.getMessage());
        }
    }

    public List<DataFormatter> getDataFormatterList() {  
        return formatters;
    }
}

package se.nrm.specify.ui.form.data.xml.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPathExpressionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import se.nrm.specify.ui.form.data.util.CommonString;
import se.nrm.specify.ui.form.data.util.FormDataType;
import se.nrm.specify.ui.form.data.util.UIXmlUtil;

/**
 *
 * @author idali
 */
public class ViewXMLData {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final String xpath_view = "/viewset/views/view";
    private final String xpath_altview = "altviews/altview";
    private final String xpath_viewdef = "/viewset/viewdefs/viewdef";
    private final String xpath_row = "rows/row";
    private final String xpath_cell = "cell";
    
    private final DomCarrier domCarrier;

    ViewXMLData(Document document) {
        this.domCarrier = new DomCarrier(document);
    }

    ViewXMLData(DomCarrier domCarrier) {
        this.domCarrier = domCarrier;
    }

    public View getViewByViewName(String viewName) {
 
        try {
            NodeList viewNodeList = domCarrier.getListByXpath(xpath_view);
            Node viewNode = domCarrier.getNodeByNameAttValue(viewNodeList, viewName);

            if (viewNode != null) {
                List<Altview> altlist = new ArrayList<Altview>();
                NodeList altviewNodeList = domCarrier.getListByXpathAndNode(xpath_altview, viewNode);
                for (int i = 0; i < altviewNodeList.getLength(); i++) {
                    Node node = altviewNodeList.item(i);
                    node.getParentNode().removeChild(node);

                    String altName = domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_NAME);
                    String viewdefName = domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_VIEWDEF);
                    String mode = domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_MODE);
                    boolean isDefault = UIXmlUtil.isTrue(domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_DEFAULT));

                    Altview altview = new Altview(altName, viewdefName, mode, isDefault);
                    altlist.add(altview);
                }

                String name = domCarrier.getNodeAttrValue(viewNode, CommonString.getInstance().XML_ATT_NAME);
                String classname = domCarrier.getNodeAttrValue(viewNode, CommonString.getInstance().XML_ATT_CLASS);
                return new View(name, UIXmlUtil.entityNameConvert(classname), altlist);
            } 
        } catch (XPathExpressionException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public Viewdef getViewDefByView(View view) { 

        NodeList viewdefNodeList;
        try {
            viewdefNodeList = domCarrier.getListByXpath(xpath_viewdef);
            String defname = view.getViewDefName();

            Node defNode = domCarrier.getNodeByNameAttValue(viewdefNodeList, defname);
            if (defNode != null) { 
                List<Formdata> formdatalist = new ArrayList<Formdata>();
                NodeList defDataNodeList = domCarrier.getListByXpathAndNode(xpath_row, defNode);
                for (int i = 0; i < defDataNodeList.getLength(); i++) {
                    Node node = defDataNodeList.item(i);
                    node.getParentNode().removeChild(node); 
                    NodeList celllist = domCarrier.getListByXpathAndNode(xpath_cell, node);
                    for (int j = 0; j < celllist.getLength(); j++) {
                        Node cellnode = celllist.item(j);
                        cellnode.getParentNode().removeChild(cellnode);

                        FormDataType type = UIXmlUtil.getType(domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_TYPE));
                        String name = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_NAME);
                        String subviewname = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_VIEWNAME);
                      
                        String initialize = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_INIIALIZE);
                        String uifieldformatter = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_UIFIELDFORMATTER);

                        FormDataType uitype = UIXmlUtil.getType(domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_UITYPE));

                        Formdata formdata = new Formdata(name, subviewname, type, uitype, initialize, uifieldformatter);
                        formdatalist.add(formdata);
                    } 
                }
                String defName = domCarrier.getNodeAttrValue(defNode, CommonString.getInstance().XML_ATT_NAME);
                String defClass = domCarrier.getNodeAttrValue(defNode, CommonString.getInstance().XML_ATT_CLASS);
        
                String defType = domCarrier.getNodeAttrValue(defNode, CommonString.getInstance().XML_ATT_TYPE);
                return new Viewdef(defName, defClass, defType, formdatalist);
            }
        } catch (XPathExpressionException e) {
            logger.error("XPathExpressionException: {}", e.getMessage());
        }
        return null;
    }

    public ViewData getViewData(String viewName) {
        View view = getViewByViewName(viewName);
        Viewdef viewdef = getViewDefByView(view);
        return new ViewData(view, viewdef);
    }
}

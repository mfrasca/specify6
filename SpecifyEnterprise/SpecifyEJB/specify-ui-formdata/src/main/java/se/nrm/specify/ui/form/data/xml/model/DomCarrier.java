package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import se.nrm.specify.ui.form.data.util.CommonString;

/**
 * This is a POJO class that has a DOM-document as a property
 * which can be read and manipulated by helper methods
 */
public class DomCarrier implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private XPathFactory xPathFactory = XPathFactory.newInstance();
    private final Document document;

    public DomCarrier(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    /**
     * get string by xpath
     * 
     * @param xPathExpression
     * @return
     * @throws XPathExpressionException 
     */
    public String getStringByXpath(String xPathExpression) throws XPathExpressionException {

        try {
            XPath xPath = xPathFactory.newXPath();
            return xPath.evaluate(xPathExpression, document);
        } catch (Exception ex) {
            throw new XPathExpressionException("Could not get string by xpath, expression " + xPathExpression + ", msg: " + ex.getMessage());
        }
    }

    public String getStringByXpathAndNode(String xPathExpression, Node node) throws XPathExpressionException {

        try {
            XPath xPath = xPathFactory.newXPath();
            return xPath.evaluate(xPathExpression, node);
        } catch (Exception ex) {
            throw new XPathExpressionException("Could not get string by xpath, expression " + xPathExpression + ", msg: " + ex.getMessage());
        }
    }

    public boolean nodeExists(String xPathExpression) throws XPathExpressionException {
        return (getElementByXpath(xPathExpression) != null);
    }

    /**
     * get element by xpath
     * 
     * @param xPathExpression
     * @return
     * @throws XPathExpressionException 
     */
    private Element getElementByXpath(String xPathExpression) throws XPathExpressionException {
        XPath xPath = xPathFactory.newXPath();
        return (Element) xPath.evaluate(xPathExpression, document, XPathConstants.NODE);
    }

    public String getNodeAttrValue(Node node, String attr) {
        Node attNode = node.getAttributes().getNamedItem(attr);
        return attNode == null ? null : attNode.getNodeValue();
    }

    /**
     * Get nodelist by xpath
     * 
     * @param xPathExpression
     * @return
     * @throws XPathExpressionException 
     */
    public NodeList getListByXpath(String xPathExpression) throws XPathExpressionException {
        XPath xPath = xPathFactory.newXPath();
        return (NodeList) xPath.evaluate(xPathExpression, document, XPathConstants.NODESET);
    }

    /**
     * Get nodelist by xpath
     * 
     * @param xPathExpression
     * @return
     * @throws XPathExpressionException 
     */
    public NodeList getListByXpathAndNode(String xPathExpression, Node node) throws XPathExpressionException {

        XPath xPath = xPathFactory.newXPath();

        return (NodeList) xPath.evaluate(xPathExpression, node, XPathConstants.NODESET);
    }

    public Node getNodeByNameAttValue(NodeList nodeList, String name) {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node singleNode = nodeList.item(i);
            singleNode.getParentNode().removeChild(singleNode);

            String attname = getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_NAME);
            if (StringUtils.equals(name, attname)) {
                 return singleNode;
            } 
        }  
        return null;
    }
}

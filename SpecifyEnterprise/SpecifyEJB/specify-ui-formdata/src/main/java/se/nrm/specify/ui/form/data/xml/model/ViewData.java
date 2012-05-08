package se.nrm.specify.ui.form.data.xml.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 *
 * @author idali
 */
public class ViewData implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private View view;
    private Viewdef viewdef;

    public ViewData(View view, Viewdef viewdef) {
        this.view = view;
        this.viewdef = viewdef;
    }

    public View getView() {
        return view;
    }

    public Viewdef getViewdef() {
        return viewdef;
    } 

    @Override
    public String toString() {
        return "ViewData: [ view: " + view.toString() + " ] - [ viewdef: " + viewdef.toString() + " ]";
    }
//    ViewData(Document document) {
//        this.domCarrier = new DomCarrier(document);
//        initData();
//    }
//
//    ViewData(DomCarrier domCarrier) {
//        this.domCarrier = domCarrier;
//        initData();
//    }
//
//    private void initData() {
//        createViewList();
//        createViewDefList();
//    }
//
//    public List<Viewdef> getViewDefList() {
//        return viewDefList;
//    }
//
//    public List<View> getViewList() {
//        return viewList;
//    }
//
//    private void createViewList() {
//
//        logger.info("create viewlist");
//
//        viewList = new ArrayList<View>();
//        NodeList viewNodeList;
//        try {
//            viewNodeList = domCarrier.getListByXpath("/viewset/views/view");
// 
//            for (int i = 0; i < viewNodeList.getLength(); i++) {
//                Node singleNode = viewNodeList.item(i);
//                singleNode.getParentNode().removeChild(singleNode);
//
//                List<Altview> altlist = new ArrayList<Altview>();
//                NodeList altviewNodeList = domCarrier.getListByXpathAndNode("altviews/altview", singleNode);
//                for (int j = 0; j < altviewNodeList.getLength(); j++) {
//                    Node node = altviewNodeList.item(j);
//                    node.getParentNode().removeChild(node);
//                    
//                    String name = domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_NAME);
//                    String viewdef = domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_VIEWDEF);
//                    String mode = domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_MODE);
//                    boolean isDefault = UIXmlUtil.isTrue(domCarrier.getNodeAttrValue(node, CommonString.getInstance().XML_ATT_DEFAULT));
//                    Altview altview = new Altview(name, viewdef, mode, isDefault);
//                    altlist.add(altview);
//                }
//                
//                String name = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_NAME);
//                String classname = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_CLASS);
//                View view = new View(name, UIXmlUtil.entityNameConvert(classname), altlist);
//                
//                viewList.add(view);
//                viewmap.put(domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_NAME), view);
//            }
//        } catch (XPathExpressionException e) {
//            logger.error(e.getMessage());
//        }
//    }
//  
//    private void createViewDefList() {
//
//        logger.info("createViewDefList");
//        
//        viewDefList = new ArrayList<Viewdef>();
//        NodeList viewdefNodeList;
//        try {
//            viewdefNodeList = domCarrier.getListByXpath("/viewset/viewdefs/viewdef");
//
//            for (int i = 0; i < viewdefNodeList.getLength(); i++) {
//                Node singleNode = viewdefNodeList.item(i);
//                singleNode.getParentNode().removeChild(singleNode);
//
//                List<DefData> defdatalist = new ArrayList<DefData>();
//                NodeList defDataNodeList = domCarrier.getListByXpathAndNode("rows/row", singleNode);
//                for (int j = 0; j < defDataNodeList.getLength(); j++) {
//                    Node node = defDataNodeList.item(j);
//                    node.getParentNode().removeChild(node);
//
//                    List<Formdata> formdatalist = new ArrayList<Formdata>();
//                    NodeList celllist = domCarrier.getListByXpathAndNode("cell", node);
//                    for (int k = 0; k < celllist.getLength(); k++) {
//                        Node cellnode = celllist.item(k);
//                        cellnode.getParentNode().removeChild(cellnode);
// 
//                        FormDataType type = UIXmlUtil.getType(domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_TYPE));
//                        String name = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_NAME);
//                        if(type == FormDataType.SUBVIEW) {
//                            name = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_VIEWNAME);
//                        } 
//                        String initialize = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_INIIALIZE);
//                        String uifieldformatter = domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_UIFIELDFORMATTER);
//                         
//                        FormDataType uitype = UIXmlUtil.getType(domCarrier.getNodeAttrValue(cellnode, CommonString.getInstance().XML_ATT_UITYPE));
//                        
//                        Formdata formdata = new Formdata(name, type, uitype, initialize, uifieldformatter);
//                        formdatalist.add(formdata);
//                    }
//                    DefData defdata = new DefData(formdatalist);
//                    defdatalist.add(defdata);
//                }
//
//                String defName = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_NAME);
//                String defClass = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_CLASS);
//                String defType = domCarrier.getNodeAttrValue(singleNode, CommonString.getInstance().XML_ATT_TYPE);
//                Viewdef viewdef = new Viewdef(defName, defClass, defType, defdatalist);
//                viewDefList.add(viewdef);
//
//                defmap.put(defName, viewdef);
//            }
//        } catch (XPathExpressionException e) {
//            logger.error(e.getMessage());
//        }
//    }  
//    
//    public boolean isViewExist(String viewname) {
//        return viewmap.containsKey(viewname) ? true : false;
//    }
//                
//    public View getViewByViewName(String viewname) {
//        return viewmap.get(viewname);
//    }
//
//    public Viewdef getViewDefByDefname(String name) {
//        return defmap.get(name);
//    }
//
//    public static Map<String, Viewdef> getDefmap() {
//        return defmap;
//    }
//
//    public static Map<String, View> getViewmap() {
//        return viewmap;
//    }
//  
//    public String getViewName() {
//        String value = "";
//        try {
//            final String xPathExpression = "/viewset/@name";
//            value = domCarrier.getStringByXpath(xPathExpression);
//        } catch (XPathExpressionException ex) {
//            logger.error(ex.getMessage());
//        }
//        return value;
//    } 
//    
//    public Document getDomDocument() {
//        return domCarrier.getDocument();
//    }
//
//    public DomCarrier getDomCarrier() {
//        return domCarrier;
//    } 
}

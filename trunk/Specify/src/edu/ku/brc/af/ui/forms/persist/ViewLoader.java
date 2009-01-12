/* This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package edu.ku.brc.af.ui.forms.persist;

import static edu.ku.brc.helpers.XMLHelper.getAttr;
import static edu.ku.brc.ui.UIHelper.createDuplicateJGoodiesDef;
import static edu.ku.brc.ui.UIRegistry.getResourceString;
import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.betwixt.XMLIntrospector;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import edu.ku.brc.af.core.db.DBFieldInfo;
import edu.ku.brc.af.core.db.DBTableChildIFace;
import edu.ku.brc.af.core.db.DBTableIdMgr;
import edu.ku.brc.af.core.db.DBTableInfo;
import edu.ku.brc.af.prefs.AppPreferences;
import edu.ku.brc.af.ui.forms.FormDataObjIFace;
import edu.ku.brc.af.ui.forms.FormHelper;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterIFace;
import edu.ku.brc.af.ui.forms.formatters.UIFieldFormatterMgr;
import edu.ku.brc.af.ui.forms.validation.TypeSearchForQueryFactory;
import edu.ku.brc.exceptions.ConfigurationException;
import edu.ku.brc.ui.CustomFrame;
import edu.ku.brc.ui.UIHelper;
import edu.ku.brc.ui.UIRegistry;

/**
 * Factory that creates Views from ViewSet files. This class uses the singleton ViewSetMgr to verify the View Set Name is unique.
 * If it is not unique than it throws an exception.<br> In this case a "form" is really the definition of a form. The form's object hierarchy
 * is used to creates the forms using Swing UI objects. The classes will also be used by the forms editor.
 
 * @code_status Beta
 **
 * @author rods
 */
public class ViewLoader
{
    public static final int DEFAULT_ROWS         = 4;
    public static final int DEFAULT_COLS         = 10;
    public static final int DEFAULT_SUBVIEW_ROWS = 5;
    
    // Statics
    private static final Logger     log = Logger.getLogger(ViewLoader.class);
    private static final ViewLoader instance = new ViewLoader();

    private static final String NAME       = "name";
    private static final String TYPE       = "type";
    private static final String LABEL      = "label";
    private static final String DESC       = "desc";
    private static final String CLASSNAME  = "class";
    private static final String GETTABLE   = "gettable";
    private static final String SETTABLE   = "settable";
    private static final String RESOURCELABELS = "useresourcelabels";
    
    // Data Members
    protected boolean doingResourceLabels = false;
    protected String  viewSetName         = null;
    
    // Members needed for verification
    protected static boolean               doFieldVerification = true;
    protected static boolean               isTreeClass         = false;
    protected static DBTableInfo           fldVerTableInfo     = null;
    protected static FormViewDef           fldVerFormViewDef   = null;
    
    protected FieldVerifyTableModel        fldVerTableModel    = null;
    
    // Debug
    //protected static ViewDef gViewDef = null;
    
    static
    {
        doFieldVerification = AppPreferences.getLocalPrefs().getBoolean("verify_field_names", false);
    }

    /**
     * Default Constructor
     *
     */
    protected ViewLoader()
    {
        // do nothing
    }

    /**
     * Creates the view.
     * @param element the element to build the View from
     * @param altViewsViewDefName the hashtable to track the AltView's ViewDefName
     * @return the View
     * @throws Exception
     */
    protected static ViewIFace createView(final Element element,
                                          final Hashtable<AltViewIFace, String> altViewsViewDefName) throws Exception
    {
        String   name              = element.attributeValue(NAME);
        String   objTitle          = getAttr(element, "objtitle", null);
        String   className         = element.attributeValue(CLASSNAME);
        String   desc              = getDesc(element);
        String   businessRules     = getAttr(element, "busrules", null);
        boolean  isInternal        = getAttr(element, "isinternal", true);
        
        DBTableInfo ti = DBTableIdMgr.getInstance().getByClassName(className);
        if (ti != null && StringUtils.isEmpty(objTitle))
        {
            objTitle = ti.getTitle();
        }
        
        View view = new View(instance.viewSetName, 
                             name, 
                             objTitle, 
                             className, 
                             businessRules != null ? businessRules.trim() : null, 
                             getAttr(element, "usedefbusrule", true),
                             isInternal,
                             desc);
        
        // Later we should get this from a properties file.
        if (ti != null)
        {
            view.setTitle(ti.getTitle());
        }
        
        /*if (!isInternal)
        {
            System.err.println(StringUtils.replace(name, " ", "_")+"="+UIHelper.makeNamePretty(name));
        }*/

        Element altviews = (Element)element.selectSingleNode("altviews");
        if (altviews != null)
        {
            AltViewIFace defaultAltView = null;
            
            AltView.CreationMode defaultMode  = AltView.parseMode(getAttr(altviews, "mode", ""), AltViewIFace.CreationMode.VIEW);
            String               selectorName = altviews.attributeValue("selector");
            
            view.setDefaultMode(defaultMode);
            view.setSelectorName(selectorName);
            
            Hashtable<String, Boolean> nameCheckHash = new Hashtable<String, Boolean>();
            
            // iterate through child elements
            for ( Iterator<?> i = altviews.elementIterator( "altview" ); i.hasNext(); )
            {
                Element altElement = (Element) i.next();

                AltView.CreationMode mode = AltView.parseMode(getAttr(altElement, "mode", ""), AltViewIFace.CreationMode.VIEW);
                
                String altName      = altElement.attributeValue(NAME);
                String viewDefName  = altElement.attributeValue("viewdef");
                String title        = altElement.attributeValue("title");
                
                boolean isValidated = getAttr(altElement, "validated", mode == AltViewIFace.CreationMode.EDIT);
                boolean isDefault   = getAttr(altElement, "default", false);

                // Make sure we only have one default view
                if (defaultAltView != null && isDefault)
                {
                    isDefault = false;
                }
                
                // Check to make sure all the AlViews have different names.
                Boolean nameExists = nameCheckHash.get(altName);
                if (nameExists == null) // no need to check the boolean
                {
                    AltView altView = new AltView(view, altName, title, mode, isValidated, isDefault, null); // setting a null viewdef
                    altViewsViewDefName.put(altView, viewDefName);
                    
                    if (StringUtils.isNotEmpty(selectorName))
                    {
                        altView.setSelectorName(selectorName);
                        
                        String selectorValue = altElement.attributeValue("selector_value");
                        if (StringUtils.isNotEmpty(selectorValue))
                        {
                            altView.setSelectorValue(selectorValue);
                            
                        } else
                        {
                            throw new RuntimeException("Selector Value is missing for viewDefName["+viewDefName+"] altName["+altName+"]");
                        }
                    }
                    
                    if (defaultAltView == null && isDefault)
                    {
                        defaultAltView = altView;
                    }
    
                    view.addAltView(altView);
                    nameCheckHash.put(altName, true);
                    
                } else
                {
                    log.error("The altView name["+altName+"] already exists!");
                }
                nameCheckHash.clear(); // why not?
            }

            // No default Alt View was indicated, so choose the first one (if there is one)
            if (defaultAltView == null && view.getAltViews() != null && view.getAltViews().size() > 0)
            {
                view.getAltViews().get(0).setDefault(true);
            }
        }

        return view;
    }

    /**
     * Creates a ViewDef
     * @param element the element to build the ViewDef from
     * @return a viewdef
     * @throws Exception
     */
    private static ViewDef createViewDef(final Element element) throws Exception
    {
        String   name              = element.attributeValue(NAME);
        String   className         = element.attributeValue(CLASSNAME);
        String   gettableClassName = element.attributeValue(GETTABLE);
        String   settableClassName = element.attributeValue(SETTABLE);
        String   desc              = getDesc(element);
        
        String   resLabels         = getAttr(element, RESOURCELABELS, "false");
        boolean  useResourceLabels = resLabels.equals("true");
        
        if (isEmpty(name))
        {
            throw new RuntimeException("name is null.");
        }

        if (isEmpty(className))
        {
            throw new RuntimeException("className is null. name["+name+"]");
        }

        if (isEmpty(gettableClassName))
        {
            throw new RuntimeException("gettableClassName Name is null.name["+name+"] classname["+className+"]");
        }

        if (isEmpty(settableClassName))
        {
            //throw new RuntimeException("settableClassName Name is null.name["+name+"] classname["+className+"] settableClassName["+settableClassName+"]");
        }
        
        DBTableInfo tableinfo = DBTableIdMgr.getInstance().getByClassName(className);

        ViewDef.ViewType type;
        try
        {
            type = ViewDefIFace.ViewType.valueOf(element.attributeValue(TYPE));

        } catch (Exception ex)
        {
            edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
            edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(ViewLoader.class, ex);
            log.error("view["+name+"] has illegal type["+element.attributeValue(TYPE)+"]", ex);
            throw ex;
        }

        ViewDef viewDef = null;//new ViewDef(type, name, className, gettableClassName, settableClassName, desc);

        switch (type)
        {
            case rstable:
            case formtable :
            case form :
                viewDef = createFormViewDef(element, type, name, className, gettableClassName, settableClassName, desc, useResourceLabels, tableinfo);
                break;

            case table :
                //view = createTableView(element, id, name, className, gettableClassName, settableClassName,
                //                       desc, instance.doingResourceLabels, isValidated);
                break;

            case field :
                //view = createFormView(FormView.ViewType.field, element, id, name, gettableClassName, settableClassName,
                //                      className, desc, instance.doingResourceLabels, isValidated);
               break;
               
            case iconview:
                viewDef = createIconViewDef(type, name, className, gettableClassName, settableClassName, desc, useResourceLabels);
                break;
        }
        return viewDef;
    }


    /**
     * Gets the optional description text
     * @param element the parent element of the desc node
     * @return the string of the text or null
     */
    protected static String getDesc(final Element element)
    {
        String desc = null;
        Element descElement = (Element)element.selectSingleNode(DESC);
        if (descElement != null)
        {
            desc = descElement.getTextTrim();
        }
        return desc;
    }

    /**
     * Fill the Vector with all the views from the DOM document
     * @param doc the DOM document conforming to form.xsd
     * @param views the list to be filled
     * @throws Exception for duplicate view set names or if a Form ID is not unique
     */
    public static String getViews(final Element doc,
                                  final Hashtable<String, ViewIFace> views,
                                  final Hashtable<AltViewIFace, String> altViewsViewDefName) throws Exception
    {
        instance.viewSetName = doc.attributeValue(NAME);
        
        /*
        System.err.println("#################################################");
        System.err.println("# "+instance.viewSetName);
        System.err.println("#################################################");
        */
        
        Element viewsElement = (Element)doc.selectSingleNode("views");
        if (viewsElement != null)
        {
            for ( Iterator<?> i = viewsElement.elementIterator( "view" ); i.hasNext(); )
            {
                Element   element = (Element) i.next(); // assume element is NOT null, if it is null it will cause an exception
                ViewIFace view    = createView(element, altViewsViewDefName);
                
                if (views.get(view.getName()) == null)
                {
                    views.put(view.getName(), view);
                } else
                {
                    String msg = "View Set ["+instance.viewSetName+"] ["+view.getName()+"] is not unique.";
                    log.error(msg);
                    throw new ConfigurationException(msg);
                }
            }
        }

        return instance.viewSetName;
    }

    /**
     * Fill the Vector with all the views from the DOM document
     * @param doc the DOM document conforming to form.xsd
     * @param viewDefs the list to be filled
     * @param doMapDefinitions tells it to map and clone the definitions for formtables (use false for the FormEditor)
     * @return the viewset name
     * @throws Exception for duplicate view set names or if a ViewDef name is not unique
     */
    public static String getViewDefs(final Element doc, 
                                     final Hashtable<String, ViewDefIFace> viewDefs,
                                     @SuppressWarnings("unused") final Hashtable<String, ViewIFace>    views,
                                     final boolean doMapDefinitions) throws Exception
    {
        instance.viewSetName = doc.attributeValue(NAME);
        
        Element viewDefsElement = (Element)doc.selectSingleNode("viewdefs");
        if (viewDefsElement != null)
        {
            for ( Iterator<?> i = viewDefsElement.elementIterator( "viewdef" ); i.hasNext(); )
            {
                Element  element = (Element) i.next(); // assume element is NOT null, if it is null it will cause an exception
                ViewDef  viewDef = createViewDef(element);
                
                //log.debug("Loaded ViewDef["+viewDef.getName()+"]");
                if (viewDefs.get(viewDef.getName()) == null)
                {
                    viewDefs.put(viewDef.getName(), viewDef);
                    
                } else
                {
                    String msg = "View Set ["+instance.viewSetName+"] the View Def Name ["+viewDef.getName()+"] is not unique.";
                    log.error(msg);
                    throw new ConfigurationException(msg);
                }
            }
            
            if (doMapDefinitions)
            {
                mapDefinitionViewDefs(viewDefs);
            }
        }

        return instance.viewSetName;
    }
    
    /**
     * Re-maps and clones the definitions.
     * @param viewDefs the hash table to be mapped
     * @throws Exception
     */
    public static void mapDefinitionViewDefs(final Hashtable<String, ViewDefIFace> viewDefs)  throws Exception
    {
        // Now that all the definitions have been read in
        // cycle thru and have all the tableform objects clone there definitions
        for (ViewDefIFace viewDef : new Vector<ViewDefIFace>(viewDefs.values()))
        {
            if (viewDef.getType() == ViewDefIFace.ViewType.formtable)
            {
                String viewDefName = ((FormViewDefIFace)viewDef).getDefinitionName();
                if (viewDefName != null)
                {
                    //log.debug(viewDefName);
                    ViewDefIFace actualDef = viewDefs.get(viewDefName);
                    if (actualDef != null)
                    {
                        viewDefs.remove(viewDef.getName());
                        actualDef = (ViewDef)actualDef.clone();
                        actualDef.setType(ViewDefIFace.ViewType.formtable);
                        actualDef.setName(viewDef.getName());
                        viewDefs.put(actualDef.getName(), actualDef);
                        
                    } else
                    {
                        throw new RuntimeException("Couldn't find the ViewDef for formtable definition name["+((FormViewDefIFace)viewDef).getDefinitionName()+"]");
                    }
                }
            }
        } 
    }


    /**
     * Processes all the AltViews
     * @param aFormView the form they should be associated with
     * @param aElement the element to process
     */
    public static Hashtable<String, String> getEnableRules(final Element element)
    {
        Hashtable<String, String> rulesList = new Hashtable<String, String>();

        if (element != null)
        {
            Element enableRules = (Element)element.selectSingleNode("enableRules");
            if (enableRules != null)
            {
                // iterate through child elements of root with element name "foo"
                for ( Iterator<?> i = enableRules.elementIterator( "rule" ); i.hasNext(); )
                {
                    Element ruleElement = (Element) i.next();
                    String id = getAttr(ruleElement, "id", "");
                    if (isNotEmpty(id))
                    {
                        rulesList.put(id, ruleElement.getTextTrim());
                    } else
                    {
                        throw new RuntimeException("The name is missing for rule["+ruleElement.getTextTrim()+"] is missing.");
                    }
                }
            }
        } else
        {
            log.error("View Set ["+instance.viewSetName+"] element ["+element+"] is null.");
        }
        return rulesList;
    }

    /**
     * Gets the string (or creates one) from a columnDef
     * @param element the DOM element to process
     * @param attrName the name of the element to go get all the elements (strings) from
     * @param numRows the number of rows
     * @param item
     * @return the String representing the column definition for JGoodies
     */
    protected static String createDef(final Element element, 
                                      final String attrName, 
                                      final int numRows,
                                      final FormViewDef.JGDefItem item)
    {
        Element cellDef = null;
        if (attrName.equals("columnDef"))
        {
            // For columnDef(s) we can mark one or more as being platform specific
            // but if we can't find a default one (no 'os' defined)
            // then we ultimately pick the first one.
            List<?> list = element.selectNodes(attrName);
            if (list.size() == 1)
            {
                cellDef = (Element)list.get(0); // pick the first one if there is only one.
            } else
            {
                Element defCD = null;
                for (Object obj : list)
                {
                    Element ce     = (Element)obj;
                    String  osType = getAttr(ce, "os", null);
                    if (osType == null)
                    {
                        defCD = ce; // ok we found the default one
                        
                    } else 
                    {
                        // Look for the Platform specific one
                        switch (UIHelper.getOSType())
                        {
                            case Windows:
                                if (osType.equals("win"))
                                {
                                    cellDef = ce;
                                }
                                break;
                                
                            case MacOSX:
                                if (osType.equals("mac"))
                                {
                                    cellDef = ce;
                                }
                                break;
                                
                            case Linux:
                                if (osType.equals("lnx"))
                                {
                                    cellDef = ce;
                                }
                                break;
                                
                            default:
                                break;
                        } // switch
                    }
                }
                
                // ok, we couldn't find one for our platform, so use the default
                // or pick the first one.
                if (cellDef == null)
                {
                    cellDef = defCD != null ? defCD : (Element)list.get(0);
                }
            }
        } else
        {
            // this is for rowDef
            cellDef = (Element)element.selectSingleNode(attrName);
        }
        
        if (cellDef != null)
        {
            String cellText = cellDef.getText();
            String cellStr  = getAttr(cellDef, "cell", null);
            String sepStr   = getAttr(cellDef, "sep", null);
            
            item.setDefStr(cellText);
            item.setCellDefStr(cellStr);
            item.setSepDefStr(sepStr);
            
            if (StringUtils.isNotEmpty(cellStr) && StringUtils.isNotEmpty(sepStr))
            {
                boolean auto = getAttr(cellDef, "auto", false);
                
                item.setAuto(auto);

                if (auto)
                {
                    String autoStr = createDuplicateJGoodiesDef(cellStr, sepStr, numRows) + 
                                         (StringUtils.isNotEmpty(cellText) ? ("," + cellText) : "");
                    
                    item.setDefStr(autoStr);
                    return autoStr;
                }
                // else
                throw new RuntimeException("Element ["+element.getName()+"] Cell or Sep is null for 'dup' or 'auto 'on column def.");
            }
            // else
            item.setAuto(false);

            return cellText;
        }
        // else
        log.error("Element ["+element.getName()+"] must have a columnDef");
        return "";
    }


    /**
     * Returns a resource string if it is suppose to
     * @param label the label or the label key
     * @return Returns a resource string if it is suppose to
     */
    protected static String getResourceLabel(final String label)
    {
        if (isNotEmpty(label) && StringUtils.deleteWhitespace(label).length() > 0)
        {
            return instance.doingResourceLabels  ? getResourceString(label) : label;
        }
        // else
        return "";

    }

    /**
     * Returns a Label from the cell and gets the resource string for it if necessary
     * @param cellElement the cell
     * @param labelId the Id of the resource or the string
     * @return the localized string (if necessary)
     */
    protected static String getLabel(final Element cellElement)
    {
        String lbl = getAttr(cellElement, LABEL, null);
        if (lbl == null || lbl.equals("##"))
        {
            return "##";
        }
        return getResourceLabel(lbl);
    }

    /**
     * Processes all the rows
     * @param element the parent DOM element of the rows
     * @param cellRows the list the rows are to be added to
     */
    protected static void processRows(final Element            element, 
                                      final List<FormRowIFace> cellRows,
                                      final DBTableInfo        tableinfo)
    {
        Element rowsElement = (Element)element.selectSingleNode("rows");
        if (rowsElement != null)
        {
            byte rowNumber = 0;
            for ( Iterator<?> i = rowsElement.elementIterator( "row" ); i.hasNext(); ) 
            {
                Element rowElement = (Element) i.next();

                FormRow formRow = new FormRow();
                formRow.setRowNumber(rowNumber);

                for ( Iterator<?> cellIter = rowElement.elementIterator( "cell" ); cellIter.hasNext(); )
                {
                    Element cellElement = (Element)cellIter.next();
                    String  cellId      = getAttr(cellElement, "id", "");
                    String  cellName    = getAttr(cellElement, NAME, cellId); // let the name default to the id if it doesn't have a name
                    int     colspan     = getAttr(cellElement, "colspan", 1);
                    int     rowspan     = getAttr(cellElement, "rowspan", 1);
                    
                    /*boolean isReq    = getAttr(cellElement, "isrequired", false);
                    if (isReq)
                    {
                        System.err.println(String.format("%s\t%s\t%s\t%s", gViewDef.getName(), cellId, cellName, tableinfo != null ? tableinfo.getTitle() : "N/A"));
                    }*/

                    FormCell.CellType cellType = FormCellIFace.CellType.valueOf(cellElement.attributeValue(TYPE));
                    FormCellIFace     cell     = null;
                    
                    
                    if (doFieldVerification &&
                        fldVerTableInfo != null && 
                        cellType == FormCellIFace.CellType.field && 
                        StringUtils.isNotEmpty(cellId) && 
                        !cellName.equals("this"))
                    {
                        if (fldVerTableInfo.getFieldByName(cellName) == null)
                        {
                            if (fldVerTableInfo.getRelationshipByName(cellName) == null)
                            {
                                String msg = " ViewSet["+instance.viewSetName+"]\n ViewDef["+fldVerFormViewDef.getName()+"]\n The cell name ["+cellName+"] for cell with Id ["+cellId+"] is not a field\n in Data Object["+fldVerTableInfo.getName()+"]\n on Row ["+rowNumber+"]";
                                if (!isTreeClass)
                                {
                                    //if (!StringUtils.contains(cellName, "."))
                                    //{
                                        //JOptionPane.showMessageDialog(null, msg, "Cell Name Error", JOptionPane.ERROR_MESSAGE);
                                        instance.fldVerTableModel.addRow(instance.viewSetName, fldVerFormViewDef.getName(), cellId, cellName, Integer.toString(rowNumber));
                                        
                                    //} else
                                    //{
                                    //    log.debug("Couldn't verify field name ["+cellName+"] is contains '.' notation.");
                                    //}
                                }
                                log.error(msg);
                            }
                        }
                    }

                    switch (cellType)
                    {
                        case label:
                        {
                            cell = formRow.addCell(new FormCellLabel(cellId, 
                                                                     cellName, 
                                                                     getLabel(cellElement), 
                                                                     getAttr(cellElement, "labelfor", ""),
                                                                     getAttr(cellElement, "icon", null),
                                                                     getAttr(cellElement, "recordobj", false), 
                                                                     colspan));
                            String initialize = getAttr(cellElement, "initialize", null);
                            if (StringUtils.isNotEmpty(initialize))
                            {
                                cell.setProperties(UIHelper.parseProperties(initialize));
                            }
                            break;
                        }
                        case separator:
                        {
                            cell = formRow.addCell(new FormCellSeparator(cellId, 
                                                                         cellName, 
                                                                         getLabel(cellElement), 
                                                                         getAttr(cellElement, "collapse", ""),
                                                                         colspan));
                            String initialize = getAttr(cellElement, "initialize", null);
                            if (StringUtils.isNotEmpty(initialize))
                            {
                                cell.setProperties(UIHelper.parseProperties(initialize));
                            }
                            break;
                        }
                        
                        case field:
                        {
                            String uitypeStr      = getAttr(cellElement, "uitype", "");
                            String format         = getAttr(cellElement, "format", "");
                            String formatName     = getAttr(cellElement, "formatname", "");
                            String uiFieldFormatterName = getAttr(cellElement, "uifieldformatter", "");
                            int    cols           = getAttr(cellElement, "cols", DEFAULT_COLS); // XXX PREF for default width of text field
                            int    rows           = getAttr(cellElement, "rows", DEFAULT_ROWS);  // XXX PREF for default heightof text area
                            String validationType = getAttr(cellElement, "valtype", "Changed");
                            String validationRule = getAttr(cellElement, "validation", "");
                            String initialize     = getAttr(cellElement, "initialize", "");
                            boolean isRequired    = getAttr(cellElement, "isrequired", false);
                            String  pickListName  = getAttr(cellElement, "picklist", "");
                            
                            if (isNotEmpty(format) && isNotEmpty(formatName))
                            {
                                //throw new RuntimeException("Both format and formatname cannot both be set! ["+cellName+"]");
                                log.error("Both format and formatname cannot both be set! ["+cellName+"] ignoring format");
                                format = "";
                            }
                            
                            Properties properties = UIHelper.parseProperties(initialize);
                            
                            if (isEmpty(uitypeStr))
                            {
                                // XXX DEBUG ONLY PLease REMOVE LATER
                                //log.debug("***************************************************************************");
                                //log.debug("***** Cell Id["+cellId+"] Name["+cellName+"] uitype is empty and should be 'text'. (Please Fix!)");
                                //log.debug("***************************************************************************");
                                uitypeStr = "text";
                            }

                            // THis switch is used to get the "display type" and 
                            // set up other vars needed for creating the controls
                            FormCellFieldIFace.FieldType uitype       = FormCellFieldIFace.FieldType.valueOf(uitypeStr);
                            String                  dspUITypeStr = null;
                            switch (uitype)
                            {
                                case textarea:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", "dsptextarea");
                                    break;
                                
                                case  querycbx:
                                {
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", "textfieldinfo");
                                    
                                    String fmtName = TypeSearchForQueryFactory.getDataObjFormatterName(properties.getProperty("name"));
                                    if (isEmpty(formatName) && isNotEmpty(fmtName))
                                    {
                                        formatName = fmtName;
                                    }
                                    break;
                                }

                                case formattedtext:
                                {
                                    validationRule = getAttr(cellElement, "validation", "formatted"); // XXX Is this OK?
                                    dspUITypeStr   = getAttr(cellElement, "dspuitype", "formattedtext");
                                    
                                    //-------------------------------------------------------
                                    // This part should be moved to the ViewFactory
                                    // because it is the only part that need the Schema Information
                                    //-------------------------------------------------------
                                    if (isNotEmpty(uiFieldFormatterName))
                                    {
                                        UIFieldFormatterIFace uiFormatter = UIFieldFormatterMgr.getInstance().getFormatter(uiFieldFormatterName);
                                        if (uiFormatter == null)
                                        {
                                            log.error("Couldn't find formatter["+uiFieldFormatterName+"]");
                                            uiFieldFormatterName = "";
                                            uitype = FormCellFieldIFace.FieldType.text;
                                        }
                                        
                                    } else // ok now check the schema for the UI formatter
                                    {
                                        DBFieldInfo fieldInfo = tableinfo.getFieldByName(cellName);
                                        if (fieldInfo != null)
                                        {
                                            if (fieldInfo.getFormatter() != null)
                                            {
                                                uiFieldFormatterName = fieldInfo.getFormatter().getName();
                                                
                                            } else if (fieldInfo.getDataClass().isAssignableFrom(Date.class) ||
                                                       fieldInfo.getDataClass().isAssignableFrom(Calendar.class))
                                            {
                                                log.debug("Missing Date Formatter for ["+cellName+"]");
                                                uiFieldFormatterName = "Date";
                                                UIFieldFormatterIFace uiFormatter = UIFieldFormatterMgr.getInstance().getFormatter(uiFieldFormatterName);
                                                if (uiFormatter == null)
                                                {
                                                    uiFieldFormatterName = "";
                                                    uitype = FormCellFieldIFace.FieldType.text;
                                                }
                                            } else
                                            {
                                                uiFieldFormatterName = "";
                                                uitype = FormCellFieldIFace.FieldType.text;
                                            }
                                        }
                                    }
                                    
                                    break;
                                }

                                case url:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", uitypeStr);
                                    properties = UIHelper.parseProperties(initialize);
                                    break;
                                    
                                case list:
                                case image:
                                case tristate:
                                case checkbox:
                                case password:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", uitypeStr);
                                    break;
                                
                                case plugin:
                                case button:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", uitypeStr);
                                    properties   = UIHelper.parseProperties(initialize);
                                    String ttl = properties.getProperty("title");
                                    if (ttl != null)
                                    {
                                        properties.put("title", getResourceLabel(ttl));
                                    }
                                    break;
                                    
                                case spinner:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", "dsptextfield");
                                    properties   = UIHelper.parseProperties(initialize);
                                    break;
                                    
                                case combobox:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", "textpl");
                                    break;
                                    
                                default:
                                    dspUITypeStr = getAttr(cellElement, "dspuitype", "dsptextfield");
                                    break;
                                
                            } //switch

                            FormCellFieldIFace.FieldType dspUIType = FormCellFieldIFace.FieldType.valueOf(dspUITypeStr);
                            
                            // check to see see if the validation is a node in the cell
                            if (isEmpty(validationRule))
                            {
                                Element valNode = (Element)cellElement.selectSingleNode("validation");
                                if (valNode != null)
                                {
                                    String str = valNode.getTextTrim();
                                    if (isNotEmpty(str))
                                    {
                                        validationRule = str;
                                    }
                                }
                            }

                            boolean isEncrypted = getAttr(cellElement, "isencrypted", false);
                            boolean isReadOnly  = uitype == FormCellFieldIFace.FieldType.dsptextfield ||
                                                  uitype == FormCellFieldIFace.FieldType.dsptextarea ||
                                                  uitype == FormCellFieldIFace.FieldType.label;
                            
                            FormCellField field = new FormCellField(FormCellIFace.CellType.field, cellId, 
                                                                    cellName, uitype, dspUIType, format, formatName, uiFieldFormatterName, isRequired,
                                                                    cols, rows, colspan, rowspan, validationType, validationRule, isEncrypted);
                            String labelStr = uitype == FormCellFieldIFace.FieldType.checkbox ? getLabel(cellElement) : getAttr(cellElement,        "label",    "");
                            field.setLabel(labelStr);
                            field.setReadOnly(getAttr(cellElement,     "readonly", isReadOnly));
                            field.setDefaultValue(getAttr(cellElement, "default",  ""));
                            field.setPickListName(pickListName);
                            field.setChangeListenerOnly(getAttr(cellElement, "changesonly", true) && !isRequired);
                            field.setProperties(properties);
                            
                            cell = formRow.addCell(field);
                            break;
                        }
                        case command:
                        {
                            cell =  formRow.addCell(new FormCellCommand(cellId, cellName,
                                                                getLabel(cellElement),
                                                                getAttr(cellElement, "commandtype", ""),
                                                                getAttr(cellElement, "action", "")));
                            String initialize = getAttr(cellElement, "initialize", null);
                            if (StringUtils.isNotEmpty(initialize))
                            {
                                cell.setProperties(UIHelper.parseProperties(initialize));
                            }
                            break;
                        }
                        case panel:
                        {
                            FormCellPanel cellPanel = new FormCellPanel(cellId, cellName,
                                                                        getAttr(cellElement, "paneltype", ""),
                                                                        getAttr(cellElement, "coldef", "p"),
                                                                        getAttr(cellElement, "rowdef", "p"),
                                                                        colspan, rowspan);
                            
                            processRows(cellElement, cellPanel.getRows(), tableinfo);

                            fixLabels(cellPanel.getName(), cellPanel.getRows(), tableinfo);
                            
                            cell = formRow.addCell(cellPanel);
                            break;
                        }
                        case subview:
                        {
                            Properties properties = UIHelper.parseProperties(getAttr(cellElement, "initialize", null));

                            String svViewSetName = cellElement.attributeValue("viewsetname");
                            if (isEmpty(svViewSetName))
                            {
                                svViewSetName = null;
                            }
                            
                            String viewName = getAttr(cellElement, "viewname", null);

                            cell = formRow.addCell(new FormCellSubView(cellId, 
                                                   cellName,
                                                   svViewSetName,
                                                   viewName,
                                                   cellElement.attributeValue("class"),
                                                   getAttr(cellElement, "desc", ""),
                                                   getAttr(cellElement, "defaulttype", null),
                                                   getAttr(cellElement, "rows", DEFAULT_SUBVIEW_ROWS),
                                                   colspan,
                                                   rowspan,
                                                   getAttr(cellElement, "single", false)));
                            cell.setProperties(properties);
                            break;
                        }
                        case iconview:
                        {
                            String vsName = cellElement.attributeValue("viewsetname");
                            if (isEmpty(vsName))
                            {
                                vsName = instance.viewSetName;
                            }
                            
                            String viewName = getAttr(cellElement, "viewname", null);

                            cell = formRow.addCell(new FormCellSubView(cellId, cellName,
                                    vsName,
                                    viewName,
                                    cellElement.attributeValue("class"),
                                    getAttr(cellElement, "desc", ""),
                                    colspan,
                                    rowspan));
                            break;
                        }

                        case statusbar:
                        {
                            cell = formRow.addCell(new FormCell(FormCellIFace.CellType.statusbar, cellId, cellName, colspan, rowspan));
                            break;
                        }
                        default:
                        {
                            // what is this?
                            log.error("Encountered unknown cell type");
                            continue;
                        }
                    } // switch
                    cell.setIgnoreSetGet(getAttr(cellElement, "ignore", false));
                }
                cellRows.add(formRow);
                rowNumber++;
            }
        }
    }
   
    /**
     * @param element the DOM element for building the form
     * @param type the type of form to be built
     * @param id the id of the form
     * @param name the name of the form
     * @param className the class name of the data object
     * @param gettableClassName the class name of the getter
     * @param settableClassName the class name of the setter
     * @param desc the description
     * @param useResourceLabels whether to use resource labels
     * @param tableinfo table info
     * @return a form view of type "form"
     */
    protected static FormViewDef createFormViewDef(final Element element,
                                                   final ViewDef.ViewType type,
                                                   final String  name,
                                                   final String  className,
                                                   final String  gettableClassName,
                                                   final String  settableClassName,
                                                   final String  desc,
                                                   final boolean useResourceLabels,
                                                   final DBTableInfo tableinfo)
    {
        FormViewDef formViewDef = new FormViewDef(type, name, className, gettableClassName, settableClassName, desc, useResourceLabels);
        
        fldVerTableInfo = null;

        if (type != ViewDefIFace.ViewType.formtable)
        {
            
            if (doFieldVerification)
            {
                if (instance.fldVerTableModel == null)
                {
                    instance.createFieldVerTableModel();
                }
                
                try
                {
                    Class<?> classObj = Class.forName(className);
                    log.debug(className);
                    if (FormDataObjIFace.class.isAssignableFrom(classObj))
                    {
                        fldVerTableInfo   = DBTableIdMgr.getInstance().getByClassName(className);
                        isTreeClass       = fldVerTableInfo != null && fldVerTableInfo.getFieldByName("highestChildNodeNumber") != null;
                        fldVerFormViewDef = formViewDef;
                    }
                    
                } catch (Exception ex)
                {
                    edu.ku.brc.af.core.UsageTracker.incrHandledUsageCount();
                    edu.ku.brc.exceptions.ExceptionTracker.getInstance().capture(ViewLoader.class, ex);
                    
                }
            }
            List<FormRowIFace> rows = formViewDef.getRows();
            
            instance.doingResourceLabels = useResourceLabels;
            
            //gViewDef = formViewDef;
            
            processRows(element, rows, tableinfo);
            
            instance.doingResourceLabels = false;
            
            createDef(element, "columnDef", rows.size(), formViewDef.getColumnDefItem());
            createDef(element, "rowDef",    rows.size(), formViewDef.getRowDefItem());
            
            formViewDef.setEnableRules(getEnableRules(element));
            
            fixLabels(formViewDef.getName(), rows, tableinfo);
            
        } else
        {
            Node defNode = element.selectSingleNode("definition");
            if (defNode != null)
            {
                String defName = defNode.getText();
                if (StringUtils.isNotEmpty(defName))
                {
                    formViewDef.setDefinitionName(defName);
                    return formViewDef;
                }
            }
            throw new RuntimeException("formtable is missing or has empty <defintion> node");
        }

        return formViewDef;
    }
    
    /**
     * @param fieldName
     * @param tableInfo
     * @return
     */
    protected static String getTitleFromFieldName(final String fieldName, final DBTableInfo tableInfo)
    {
        DBTableChildIFace derivedCI = null;
        if (fieldName.indexOf(".") > -1)
        {
            derivedCI = FormHelper.getChildInfoFromPath(fieldName, tableInfo);
            if (derivedCI == null)
            {
                String msg = "The name 'path' ["+fieldName+"] was not valid in ViewSet ["+instance.viewSetName+"]";
                UIRegistry.showError(msg);
                log.error(msg);
                return "";
            }
        }
        DBTableChildIFace tblChild = derivedCI != null ? derivedCI : tableInfo.getItemByName(fieldName);
        if (tblChild == null)
        {
            String msg = "The Field Name ["+fieldName+"] was not in the Table ["+tableInfo.getTitle()+"] in ViewSet ["+instance.viewSetName+"]";
            //UIRegistry.showError(msg);
            log.error(msg);
            return "";
        }
        return tblChild.getTitle();
    }
    
    /**
     * @param rows
     * @param tableInfo
     */
    protected static void fixLabels(final String name,
                                    final List<FormRowIFace> rows, 
                                    final DBTableInfo tableInfo)
    {
        if (tableInfo == null)
        {
            return;
        }
        
        Hashtable<String, String> fldIdMap = new Hashtable<String, String>();
        for (FormRowIFace row : rows)
        {
            for (FormCellIFace cell : row.getCells())
            {
                if (cell.getType() == FormCellIFace.CellType.field ||
                        cell.getType() == FormCellIFace.CellType.subview)
                {
                    fldIdMap.put(cell.getIdent(), cell.getName()); 
                }/* else
                {
                    System.err.println("Skipping ["+cell.getIdent()+"] " + cell.getType());
                }*/
            }
        }
        
        for (FormRowIFace row : rows)
        {
            for (FormCellIFace cell : row.getCells())
            {
                if (cell.getType() == FormCellIFace.CellType.label)
                {
                    FormCellLabelIFace lblCell = (FormCellLabelIFace)cell;
                    String             label   = lblCell.getLabel();
                    if (label.length() == 0 || label.equals("##"))
                    {
                        String idFor = lblCell.getLabelFor();
                        if (StringUtils.isNotEmpty(idFor))
                        {
                            String fieldName = fldIdMap.get(idFor);
                            if (StringUtils.isNotEmpty(fieldName))
                            {
                                if (!fieldName.equals("this"))
                                {
                                    lblCell.setLabel(getTitleFromFieldName(fieldName, tableInfo));
                                }
                                
                            } else
                            {
                                String msg = "Setting Label - Form control with id["+idFor+"] is not in ViewDef or Panel ["+name+"] in ViewSet ["+instance.viewSetName+"]";
                                UIRegistry.showError(msg);
                                log.error(msg);
                            }
                        }
                    }
                } else if (cell.getType() == FormCellIFace.CellType.field && cell instanceof FormCellFieldIFace &&
                           ((((FormCellFieldIFace)cell).getUiType() == FormCellFieldIFace.FieldType.checkbox) ||
                            (((FormCellFieldIFace)cell).getUiType() == FormCellFieldIFace.FieldType.tristate)))
                {
                    FormCellFieldIFace fcf = (FormCellFieldIFace)cell;
                    if (fcf.getLabel().equals("##"))
                    {
                        fcf.setLabel(getTitleFromFieldName(cell.getName(), tableInfo));
                    }
                }
                    
            }
        }
    }
    /**
     * @param type the type of form to be built
     * @param name the name of the form
     * @param className the class name of the data object
     * @param gettableClassName the class name of the getter
     * @param settableClassName the class name of the setter
     * @param desc the description
     * @param useResourceLabels whether to use resource labels
     * @return a form view of type "form"
     */
    protected static ViewDef createIconViewDef(final ViewDef.ViewType type,
                                               final String  name,
                                               final String  className,
                                               final String  gettableClassName,
                                               final String  settableClassName,
                                               final String  desc,
                                               final boolean useResourceLabels)
    {
        
        ViewDef formView = new ViewDef(type, name, className, gettableClassName, settableClassName, desc, useResourceLabels);

        //formView.setEnableRules(getEnableRules(element));

        return formView;
    }

    /**
     * Creates a Table Form View
     * @param typeName the type of form to be built
     * @param element the DOM element for building the form
     * @param name the name of the form
     * @param className the class name of the data object
     * @param gettableClassName the class name of the getter
     * @param settableClassName the class name of the setter
     * @param desc the description
     * @param useResourceLabels whether to use resource labels
     * @return a form view of type "table"
     */
    protected static TableViewDefIFace createTableView(final Element element,
                                                   final String  name,
                                                   final String  className,
                                                   final String  gettableClassName,
                                                   final String  settableClassName,
                                                   final String  desc,
                                                   final boolean useResourceLabels)
    {
        TableViewDefIFace tableView = new TableViewDef( name, className, gettableClassName, settableClassName, desc, useResourceLabels);

        //tableView.setResourceLabels(resLabels);

        Element columns = (Element)element.selectSingleNode("columns");
        if (columns != null)
        {
            for ( Iterator<?> i = columns.elementIterator( "column" ); i.hasNext(); ) {
                Element colElement = (Element) i.next();

                FormColumn column = new FormColumn(colElement.attributeValue(NAME),
                        colElement.attributeValue(LABEL),
                        getAttr(colElement, "dataobjformatter", null),
                        getAttr(colElement, "format", null)
                        );

                tableView.addColumn(column);
            }
        }

        return tableView;
    }

    /**
     * Save out a viewSet to a file
     * @param viewSet the viewSet to save
     * @param filename the filename (full path) as to where to save it
     */
    public static void save(final ViewSet viewSet, final String filename)
    {
        try
        {
            Vector<ViewSet> viewsets = new Vector<ViewSet>();
            viewsets.add(viewSet);

            File       file = new File(filename);
            FileWriter fw   = new FileWriter(file);

            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            BeanWriter beanWriter = new BeanWriter(fw);
            XMLIntrospector introspector = beanWriter.getXMLIntrospector();
            introspector.getConfiguration().setWrapCollectionsInElement(false);
            beanWriter.getBindingConfiguration().setMapIDs(false);
            beanWriter.setWriteEmptyElements(false);

            beanWriter.enablePrettyPrint();
            beanWriter.write(viewSet);

            fw.close();

        } catch(Exception ex)
        {
            log.error("error writing views", ex);
        }
    }
    
    //--------------------------------------------------------------------------------------------
    //-- Field Verify Methods, Classes, Helpers
    //--------------------------------------------------------------------------------------------
    
    public void createFieldVerTableModel()
    {
        fldVerTableModel = new FieldVerifyTableModel();
    }
    
    /**
     * @return the doFieldVerification
     */
    public static boolean isDoFieldVerification()
    {
        return doFieldVerification;
    }

    /**
     * @param doFieldVerification the doFieldVerification to set
     */
    public static void setDoFieldVerification(boolean doFieldVerification)
    {
        ViewLoader.doFieldVerification = doFieldVerification;
    }

    public static void clearFieldVerInfo()
    {
        if (instance.fldVerTableModel != null)
        {
            instance.fldVerTableModel.clear();
        }
    }
    
    public static void displayFieldVerInfo()
    {
        if (instance.fldVerTableModel != null && instance.fldVerTableModel.getRowCount() > 0)
        {
            JTable table = new JTable(instance.fldVerTableModel);
            UIHelper.calcColumnWidths(table);
            
            JScrollPane sp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            CustomFrame dlg = new CustomFrame("Field Errors : "+instance.fldVerTableModel.getRowCount(), CustomFrame.OK_BTN, sp);
            dlg.setOkLabel(getResourceString("CLOSE"));
            dlg.createUI();
            dlg.setSize(500, 500);
            dlg.setVisible(true);
        }
    }
    
    class FieldVerifyTableModel extends DefaultTableModel
    {
        protected Vector<List<String>>       rowData  = new Vector<List<String>>();
        protected String[]                   colNames = {"ViewSet", "View Def", "Cell Id", "Cell Name", "Row"};
        protected Hashtable<String, Boolean> nameHash = new Hashtable<String, Boolean>();
        
        public FieldVerifyTableModel()
        {
            super();
        }
        
        public void clear()
        {
            for (List<String> list : rowData)
            {
                list.clear();
            }
            rowData.clear();
            nameHash.clear();
        }
        
        public void addRow(final String viewSet, 
                           final String viewDef, 
                           final String cellId, 
                           final String cellName, 
                           final String rowInx)
        {
            String key = viewSet + viewDef + cellId;
            if (nameHash.get(key) == null)
            {
                List<String> row = new ArrayList<String>(5);
                row.add(viewSet);
                row.add(viewDef);
                row.add(cellId);
                row.add(cellName);
                row.add(rowInx);
                rowData.add(row);
                nameHash.put(key, true);
            }
        }
        
        /* (non-Javadoc)
         * @see javax.swing.table.DefaultTableModel#getColumnCount()
         */
        @Override
        public int getColumnCount()
        {
            return colNames.length;
        }

        /* (non-Javadoc)
         * @see javax.swing.table.DefaultTableModel#getColumnName(int)
         */
        @Override
        public String getColumnName(int column)
        {
            return colNames[column];
        }

        /* (non-Javadoc)
         * @see javax.swing.table.DefaultTableModel#getRowCount()
         */
        @Override
        public int getRowCount()
        {
            return rowData == null ? 0 : rowData.size();
        }

        /* (non-Javadoc)
         * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
         */
        @Override
        public Object getValueAt(int row, int column)
        {
            List<String> rowList = rowData.get(row);
            
            return rowList.get(column);
        }
        
    }
}


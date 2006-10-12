/**
 * Copyright (C) 2006  The University of Kansas
 *
 * [INSERT KU-APPROVED LICENSE TEXT HERE]
 * 
 */
package edu.ku.brc.ui.forms;

import static edu.ku.brc.ui.UICacheManager.getResourceString;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.ku.brc.dbsupport.DBTableIdMgr;
import edu.ku.brc.dbsupport.DataProviderSessionIFace;
import edu.ku.brc.dbsupport.DBTableIdMgr.TableInfo;
import edu.ku.brc.dbsupport.DBTableIdMgr.TableRelationship;
import edu.ku.brc.ui.DropDownButtonStateful;
import edu.ku.brc.ui.IconManager;
import edu.ku.brc.ui.IconTray;
import edu.ku.brc.ui.UICacheManager;
import edu.ku.brc.ui.ViewBasedDialogFactoryIFace.FRAME_TYPE;
import edu.ku.brc.ui.db.ViewBasedDisplayIFace;
import edu.ku.brc.ui.forms.persist.AltView;
import edu.ku.brc.ui.forms.persist.FormViewDef;
import edu.ku.brc.ui.forms.persist.View;
import edu.ku.brc.ui.forms.persist.ViewDef;
import edu.ku.brc.ui.forms.persist.AltView.CreationMode;
import edu.ku.brc.ui.validation.FormValidator;

/**
 * A Viewable that will display a set of FormDataObjIFace objects in a file
 * browser-like UI.
 *
 * @code_status Beta
 * @author jds
 */
public class IconViewObj implements Viewable
{
    protected static final Logger log = Logger.getLogger(IconViewObj.class);
    
    // Data Members
    protected DataProviderSessionIFace      session;
    protected MultiView                     mvParent;
    protected View                          view;
    protected AltView                       altView;
    protected ViewDef                       viewDef;
    protected String                        cellName;
    protected Vector<AltView>               altViewsList;
    protected int                           viewOptions;
    
    protected FormDataObjIFace              parentDataObj;
    protected Set<Object>                   dataSet;
    protected String                        dataClassName;
    protected String                        dataSetFieldName;

    // UI stuff
    protected IconTray                      iconTray;
    protected JPanel mainComp;
    protected JPanel southPanel;
    protected JButton editButton;
    protected JButton newButton;
    protected JButton deleteButton;
    protected DropDownButtonStateful        switcherUI;
    protected List<Object>                  list;
    protected JButton                       validationInfoBtn;
    
    protected boolean dataTypeError;

    protected BusinessRulesIFace            businessRules; 

    /**
     * Constructor.
     * 
     * @param view the View
     * @param altView the altView
     * @param mvParent the parent MultiView
     * @param options the view options
     */
    public IconViewObj(final View view, final AltView altView, final MultiView mvParent, final int options)
    {
        this.view = view;
        this.altView = altView;
        this.mvParent = mvParent;
        this.viewOptions = options;
        this.viewDef = altView.getViewDef();
        this.dataTypeError = false;
        this.businessRules = view.getBusinessRule();
    }
    
    protected void initMainComp()
    {
        editButton = createButton("EditForm", getResourceString("EditRecord"));
        newButton = createButton("CreateObj", getResourceString("NewRecord"));
        deleteButton = createButton("CreateObj", getResourceString("DeleteRecord"));

        altViewsList = new Vector<AltView>();
        switcherUI   = FormViewObj.createSwitcher(mvParent, view, altView, altViewsList);
        
        validationInfoBtn = new JButton(IconManager.getImage("ValidationValid"));
        validationInfoBtn.setToolTipText(getResourceString("ShowValidationInfoTT"));
        validationInfoBtn.setMargin(new Insets(1,1,1,1));
        validationInfoBtn.setBorder(BorderFactory.createEmptyBorder());
        validationInfoBtn.setFocusable(false);
        validationInfoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae)
            {
                log.error("validation button clicked: not yet implemented");
            }
        });
        
        iconTray = new IconTray(IconTray.SINGLE_ROW);
        //iconTray = new IconTray(IconTray.MULTIPLE_ROWS);
        
        addActionListenerToEditButton();
        
        if (altView.getMode() == CreationMode.View)
        {
            newButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
        else
        {
            addActionListenerToNewButton();
            addActionListenerToDeleteButton();
        }

        mainComp = new JPanel();
        mainComp.setLayout(new BorderLayout());
        if (mvParent == null)
        {
            mainComp.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        }
        
        PanelBuilder builder = new PanelBuilder(new FormLayout("f:1px:g,p,1px,p,1px,p,1px,p,1px,p", "p"));
        CellConstraints cc  = new CellConstraints();
        
        builder.add(editButton, cc.xy(2,1));
        builder.add(newButton, cc.xy(4,1));
        builder.add(deleteButton, cc.xy(6,1));
        builder.add(validationInfoBtn, cc.xy(8,1));
        builder.add(switcherUI, cc.xy(10,1));
        southPanel = builder.getPanel();

        mainComp.add(iconTray,BorderLayout.CENTER);
        mainComp.add(southPanel,BorderLayout.SOUTH);
    }

    protected void addActionListenerToEditButton()
    {
        editButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                FormDataObjIFace selection = iconTray.getSelection();
                if (selection==null)
                {
                    return;
                }
                
                ViewBasedDisplayIFace dialog = getEditObjectDialog(selection,false);
                dialog.setCloseListener(new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent evt)
                    {
                        String action = evt.getPropertyName();
                        if (action.equals("OK"))
                        {
                            log.warn("User clicked OK");
                            log.error("Do I need to do a Hibernate update?");
                        }
                        else if (action.equals("Cancel"))
                        {
                            log.warn("User clicked Cancel");
                        }
                    }
                });
                dialog.setData(selection);
                dialog.showDisplay(true);
            }
        });
    }
    
    protected void addActionListenerToNewButton()
    {
        newButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                //TODO: create a new object
                //FormHelper.createAndNewDataObj(classObj);
                //TODO: display an edit dialog for a new object
                log.warn("Display edit dialog for a new object");
                
                final FormDataObjIFace newObject = FormHelper.createAndNewDataObj(dataClassName);
                
                // get an edit dialog for the object
                ViewBasedDisplayIFace dialog = getEditObjectDialog(newObject,true);
                dialog.setCloseListener(new PropertyChangeListener()
                {
                    public void propertyChange(PropertyChangeEvent evt)
                    {
                        String action = evt.getPropertyName();
                        if (action.equals("OK"))
                        {
                            log.warn("User clicked OK.  Adding " + newObject.getIdentityTitle() + " into " + dataSetFieldName + ".");
                            parentDataObj.addReference(newObject, dataSetFieldName);
                            iconTray.addItem(newObject);
                            log.error("Do I need to do a Hibernate save?");
                            // TODO: what to do?
                        }
                        else if (action.equals("Cancel"))
                        {
                            log.warn("User clicked Cancel");
                            // TODO: what to do?
                        }
                    }
                });
                dialog.setData(newObject);
                dialog.showDisplay(true);
            }
        });
    }
    
    protected void addActionListenerToDeleteButton()
    {
        deleteButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                FormDataObjIFace selection = iconTray.getSelection();
                if (selection==null)
                {
                    return;
                }
                
                iconTray.removeItem(selection);
                log.warn("Delete " + selection.getIdentityTitle());
                log.warn("How should this be implemented?");
                // TODO: what to do?
            }
        });
    }
    
    protected ViewBasedDisplayIFace getEditObjectDialog(FormDataObjIFace objectToEdit, boolean isNewObject)
    {
        TableInfo setTI = DBTableIdMgr.lookupByClassName(objectToEdit.getClass().getName());
        String defFormName = setTI.getEditObjDialog();

        log.warn("Are these the correct settings for this popup?");
        // TODO: check these settings

        boolean isEdit = (altView.getMode() == CreationMode.Edit) ? true : false;
        int options = MultiView.HIDE_SAVE_BTN;
        String title = (isNewObject && isEdit) ? getResourceString("Edit") : objectToEdit.getIdentityTitle();
        ViewBasedDisplayIFace dialog = UICacheManager.getViewbasedFactory().createDisplay(defFormName,
                                                                    title,
                                                                    getResourceString("OK"),
                                                                    isEdit,
                                                                    options,
                                                                    FRAME_TYPE.DIALOG);
        return dialog;
    }

    /**
     * A utility method used to create the 'edit', 'new' and 'delete' buttons.
     * 
     * @param iconName the name of the icon to use for the button
     * @param toolTip the tooltip text for the button
     * @return a button
     */
    protected JButton createButton(String iconName, String toolTip)
    {
        JButton btn = new JButton(IconManager.getIcon(iconName, IconManager.IconSize.Std16));
        btn.setToolTipText(toolTip);
        btn.setFocusable(false);
        btn.setMargin(new Insets(1,1,1,1));
        btn.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        return btn;
    }

    //-------------------------------------------------
    // Viewable
    //-------------------------------------------------

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getName()
     */
    public String getName()
    {
        return "Icon View";
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getId()
     */
    public int getId()
    {
        return -1;//tableViewDef.getId();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getType()
     */
    public ViewDef.ViewType getType()
    {
        return viewDef.getType();
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getUIComponent()
     */
    public synchronized Component getUIComponent()
    {
        if (mainComp==null)
        {
            initMainComp();
        }
        return mainComp;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#isSubform()
     */
    public boolean isSubform()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getCompById(java.lang.String)
     */
    public Component getCompById(final String id)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getLabelById(java.lang.String)
     */
    public JLabel getLabelFor(final String id)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getControlMapping()
     */
    public Map<String, Component> getControlMapping()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getValidator()
     */
    public FormValidator getValidator()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#setDataObj(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public void setDataObj(Object dataObj)
    {
        if (dataObj instanceof Set)
        {
            dataSet = (Set)dataObj;
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getDataObj()
     */
    public Object getDataObj()
    {
        return dataSet;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#setParentDataObj(java.lang.Object)
     */
    public void setParentDataObj(Object parentDataObj)
    {
        if (!(parentDataObj instanceof FormDataObjIFace))
        {
            dataTypeError = true;
            return;
        }
        this.parentDataObj = (FormDataObjIFace)parentDataObj;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getParentDataObj()
     */
    public Object getParentDataObj()
    {
        return parentDataObj;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#setDataIntoUI()
     */
    public synchronized void setDataIntoUI()
    {
        if (mainComp == null)
        {
            initMainComp();
        }
        
        for (Object o: dataSet)
        {
            if (!(o instanceof FormDataObjIFace))
            {
                log.error("Icon view data set contains non-FormDataObjIFace objects.  Item being ignored.");
                mainComp.removeAll();
                mainComp.add( new JLabel(getResourceString("Error")));
                dataTypeError = true;
                return;
            }
            
            iconTray.addItem((FormDataObjIFace)o);
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getDataFromUI()
     */
    public void getDataFromUI()
    {
        if (dataTypeError)
        {
            return;
        }
        
        dataSet.clear();
        Set<FormDataObjIFace> iconTrayItems = iconTray.getItems();
        for (FormDataObjIFace fdo: iconTrayItems)
        {
            dataSet.add(fdo);
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getDataFromUIComp(java.lang.String)
     */
    public Object getDataFromUIComp(final String name)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#setDataIntoUIComp(java.lang.String, java.lang.Object)
     */
    public void setDataIntoUIComp(final String name, Object data)
    {
        // do nothing
    }


    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getSubView(java.lang.String)
     */
    public MultiView getSubView(final String name)
    {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getFieldIds(java.util.List)
     */
    public void getFieldIds(final List<String> fieldIds)
    {
        // do nothing
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#aboutToShow(boolean)
     */
    public void aboutToShow(boolean show)
    {
        if (switcherUI != null)
        {
            switcherUI.setCurrentIndex(altViewsList.indexOf(altView));
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getView()
     */
    public View getView()
    {
        return view;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getViewDef()
     */
    public FormViewDef getViewDef()
    {
        return (FormViewDef)altView.getViewDef();
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#getAltView()
     */
    public AltView getAltView()
    {
        return altView;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#hideMultiViewSwitch(boolean)
     */
    public void hideMultiViewSwitch(boolean hide)
    {
        // do nothing
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#dataHasChanged()
     */
    public void validationWasOK(boolean wasOK)
    {
        // do nothing
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#setSession(org.hibernate.Session)
     */
    public void setSession(final DataProviderSessionIFace session)
    {
        this.session = session;
    }

    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#setCellName(java.lang.String)
     */
    public void setCellName(String cellName)
    {
        this.cellName = cellName;
        
        if (parentDataObj == null)
        {
            this.dataClassName = viewDef.getClassName();
        }
        else
        {
            TableInfo parentTI = DBTableIdMgr.lookupByClassName(parentDataObj.getClass().getName());
            TableRelationship rel = parentTI.getRelationshipByName(cellName);
            this.dataClassName = rel.getClassName();
        }
    }
    
    /* (non-Javadoc)
     * @see edu.ku.brc.ui.forms.Viewable#shutdown()
     */
    public void shutdown()
    {
        mvParent      = null;
        mainComp      = null;
    }
}
